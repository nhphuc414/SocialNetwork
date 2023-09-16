/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.repository;

import com.nhp.pojo.Comment;
import java.util.List;

/**
 *
 * @author ad
 */
public interface CommentRepository {
    List<Comment> getComments(int postId);
    Comment getCommentById(int id);
    long countCommentsByPostId(int postId);
    Comment add(Comment comment);
    Comment update(Comment comment);
    
}
