/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.repository.impl;

import com.nhp.pojo.Comment;
import com.nhp.pojo.Post;
import com.nhp.repository.CommentRepository;
import java.util.List;
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
public class CommentRepositoryImpl implements CommentRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public List<Comment> getComments(int postId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Comment Where postId.id=:id");
        q.setParameter("id", postId);
        return q.getResultList();
    }
    @Override
    public long countCommentsByPostId(int postId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT count(c) From Comment c Where c.postId.id=:id");
        q.setParameter("id", postId);
        return Long.parseLong(q.getSingleResult().toString());
    }

    @Override
    public Comment add(Comment comment) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(comment);
        return comment;
    }

    @Override
    public boolean update(Comment comment) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(comment);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public Comment getCommentById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Comment comment = session.get(Comment.class,
                id);
        return comment;
    }
}
