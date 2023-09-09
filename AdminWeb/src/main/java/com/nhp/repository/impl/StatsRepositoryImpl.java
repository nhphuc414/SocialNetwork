/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.repository.impl;

import com.nhp.pojo.Comment;
import com.nhp.pojo.Post;
import com.nhp.pojo.User;
import com.nhp.repository.StatsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ad
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<Object[]> stats(Map<String, String> params) {
        return null;
    }
    @Override
    public long countPostsThisMonth() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(p) FROM Post p WHERE YEAR(p.createdDate) = YEAR(CURRENT_DATE()) AND MONTH(p.createdDate) = MONTH(CURRENT_DATE())");
        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public long countUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT Count(*) FROM User WHERE status =:st");
        q.setParameter("st", "ACTIVE");
        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public long countUsersThisMonth() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT Count(*) FROM User u WHERE u.status =:st AND YEAR(u.createdDate) = YEAR(CURRENT_DATE()) AND MONTH(u.createdDate) = MONTH(CURRENT_DATE())");
        q.setParameter("st", "ACTIVE");
        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public long countPostsThisYear() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(p) FROM Post p WHERE YEAR(p.createdDate) = YEAR(CURRENT_DATE())");
        return Long.parseLong(q.getSingleResult().toString());
    }
    
    
}
