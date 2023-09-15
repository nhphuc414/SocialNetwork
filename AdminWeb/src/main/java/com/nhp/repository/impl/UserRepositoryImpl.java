/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.repository.impl;

import com.nhp.pojo.Identity;
import com.nhp.pojo.User;
import com.nhp.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

/**
 *
 * @author ad
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);
        q.select(root);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String role = params.get("role");
            if (role != null && !role.isEmpty()) {
                predicates.add(b.equal(root.get("role"), role));
            }
            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(b.equal(root.get("status"), status));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }
        q.orderBy(b.asc(root.get("id")));
        Query query = session.createQuery(q);
        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

                query.setMaxResults(pageSize);
                query.setFirstResult((p - 1) * pageSize);
            }
        }
        List<User> users = query.getResultList();
        if ("REQUESTING".equals(params.get("role"))) {
            for (User user : users) {
                query = session.createQuery("SELECT identity FROM Identity WHERE userId.id=:id");
                query.setParameter("id", user.getId());
                user.setIdentity(query.getSingleResult().toString());
            }
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE id=:i");
        q.setParameter("i", id);
        return (User) q.getSingleResult();
    }

    @Override
    public boolean addTeacher(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(u);
            return true;
        } catch (HibernateException ex) {

        }
        System.out.println("LOI");
        return false;
    }

    @Override
    public boolean resetUser(int id) {
        User u = this.getUserById(id);
        u.setStatus("ACTIVE");
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(u);
            return true;
        } catch (HibernateException ex) {

        }
        System.out.println("LOI");
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE username=:un");
        q.setParameter("un", username);
        List<User> users = q.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public String authUser(String username, String password) {
        User u = this.getUserByUsername(username);
        if (u == null) {
            return "USERNAME NOT FOUND";
        }
        if ("ROLE_TEACHER".equals(u.getRole()) && this.passEncoder.matches("ou@123", u.getPassword())) {
            Date now = new Date();
            Date d = u.getUpdatedDate() == null ? u.getCreatedDate() : u.getUpdatedDate();
            long timeDifference = now.getTime() - d.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);
            boolean isGreaterThan24Hours = Math.abs(hoursDifference) > 24;
            if (isGreaterThan24Hours) {
                u.setStatus("EXPIRE");
                this.updateUser(u);
                return "EXPIRE";
            }
        }
        if (!this.passEncoder.matches(password, u.getPassword())) {
            return "WRONG PASSWORD";
        }
        return u.getStatus();
    }

    @Override
    public User addUser(User u, String identity) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(u);
            Identity iU = new Identity();
            iU.setIdentity(identity);
            iU.setUserId(u);
            s.save(iU);
            return u;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User updateUser(User u) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(u);
            return u;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public long countUsers(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root root = q.from(User.class);
        q.select(b.count(root));
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String role = params.get("role");
            if (role != null && !role.isEmpty()) {
                predicates.add(b.equal(root.get("role"), role));
            }
            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(b.equal(root.get("status"), status));
            }
            q.where(predicates.toArray(Predicate[]::new));
        }
        return session.createQuery(q).getSingleResult();
    }

    @Override
    public boolean acceptUser(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(id);
        u.setStatus("ACTIVE");
        try {
            s.update(u);
            return true;
        } catch (HibernateException ex) {
            printStackTrace(ex);
            return false;
        }
    }

    @Override
    public boolean deniedUser(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            Query q = session.createQuery("DELETE FROM Identity WHERE userId.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            q = session.createQuery("DELETE FROM User WHERE id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            return true;
        } catch (HibernateException ex) {
            printStackTrace(ex);
            return false;
        }
    }

    @Override
    public ObjectError checkUnique(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        if (!params.isEmpty()) {
            String username = params.get("username");
            if (username != null && !username.isEmpty()) {
                Query query = session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
                query.setParameter("username", username);
                if (!query.getResultList().isEmpty()) {
                    return new ObjectError("unique", "Username has existed");
                }
            }
            String email = params.get("email");
            if (email != null && !email.isEmpty()) {
                Query query = session.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
                query.setParameter("email", email);
                if (!query.getResultList().isEmpty()) {
                    return new ObjectError("unique", "Email has existed");
                }
            }
            String identity = params.get("identity");
            if (identity != null && !identity.isEmpty()) {
                Query query = session.createQuery("SELECT i FROM Identity i WHERE i.identity = :identity", User.class);
                query.setParameter("identity", identity);
                if (!query.getResultList().isEmpty()) {
                    return new ObjectError("unique", "Identity has existed");
                }
            }
        }
        return null;
    }
}
