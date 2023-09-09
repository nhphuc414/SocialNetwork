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
        return query.getResultList();
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
        return (User) q.getSingleResult();
    }

    @Override
    public long countTeachers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT Count(*) FROM User WHERE role='ROLE_TEACHER'");
        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public long countExpire() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT Count(*) FROM User WHERE status =:st AND role='ROLE_TEACHER'");
        q.setParameter("st", "EXPIRE");
        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public String authUser(String username, String password) {
        User  u = this.getUserByUsername(username);
        if (u.getId()==null){
            return "USERNAME NOT FOUND";
        }
        if ("ROLE_TEACHER".equals(u.getRole())&& this.passEncoder.matches("ou@123", u.getPassword())){
            Date now = new Date();
            Date d = u.getUpdatedDate()==null?u.getCreatedDate():u.getUpdatedDate();
            long timeDifference = now.getTime() - d.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);
            boolean isGreaterThan24Hours = Math.abs(hoursDifference) > 24;
            if (isGreaterThan24Hours) {
                u.setStatus("EXPIRE");
                this.updateUser(u);
                return "EXPIRE";
            }
        }
        if(!this.passEncoder.matches(password, u.getPassword())){
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
       Session s = this.factory.getObject().getCurrentSession();
       s.update(u);
       return u;
    }
}
