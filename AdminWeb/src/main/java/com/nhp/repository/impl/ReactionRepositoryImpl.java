/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.repository.impl;

import com.nhp.repository.ReactionRepository;
import javax.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ad
 */
@Repository
@Transactional
public class ReactionRepositoryImpl implements ReactionRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public long countReactionsByPostId(int postId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT count(r) From Reaction r Where r.postId.id=:id");
        q.setParameter("id", postId);
        return Long.parseLong(q.getSingleResult().toString());
    }
    
}
