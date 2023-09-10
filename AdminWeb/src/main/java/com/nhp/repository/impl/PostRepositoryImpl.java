/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.repository.impl;

import com.nhp.pojo.Post;
import com.nhp.repository.CommentRepository;
import com.nhp.repository.PostRepository;
import com.nhp.repository.ReactionRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    @Override
    public List<Post> getPublicPosts() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
        Root<Post> root = criteria.from(Post.class);
        criteria.select(root)
                .where(builder.and(
                        builder.notEqual(root.get("status"), "HIDD"),
                        builder.notEqual(root.get("status"), "DEL")
                )).orderBy(builder.desc(root.get("createdDate")));;
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Post> getUserPosts(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
        Root<Post> root = criteria.from(Post.class);
        criteria.select(root)
                .where(builder.and(
                        builder.equal(root.get("userId"), id),
                        builder.notEqual(root.get("status"), "DEL")
                ));
        List<Post> posts =session.createQuery(criteria).getResultList();
        for (Post post : posts) {
            post.setCountComment(this.commentRepository.countCommentsByPostId(post.getId()));
            post.setCountReaction(this.reactionRepository.countReactionsByPostId(post.getId()));
        }
        return posts;
    }

    @Override
    public Post addPost(Post post) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(post);
        return post;
    }
}
