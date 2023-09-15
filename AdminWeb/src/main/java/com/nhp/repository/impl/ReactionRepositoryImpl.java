/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.repository.impl;

import com.nhp.pojo.Comment;
import com.nhp.pojo.Reaction;
import com.nhp.repository.ReactionRepository;
import javax.persistence.Query;
import org.hibernate.HibernateException;
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
    @Override
    public long countReactionsByCommentId(int commentId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT count(r) From Reaction r Where r.commentId.id=:id");
        q.setParameter("id", commentId);
        return Long.parseLong(q.getSingleResult().toString());
    }
    @Override
    public boolean add(Reaction reaction) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(reaction);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Reaction reaction) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(reaction);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            Query q = session.createQuery("DELETE FROM Reaction WHERE id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public Reaction getById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Reaction reaction = session.get(Reaction.class,
                id);
        return reaction;
    }
}
