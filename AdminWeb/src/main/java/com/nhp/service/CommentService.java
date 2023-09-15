/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.dto.CommentDTO;
import com.nhp.pojo.Comment;
import java.util.List;

/**
 *
 * @author ad
 */
public interface CommentService {
    List<Comment> getComments(int postId);
    Comment getCommentById(int id);
    Comment add(CommentDTO commentDTO);
    boolean update(CommentDTO commentDTO);
}
