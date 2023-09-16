/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.dto.CommentDTO;
import com.nhp.pojo.Comment;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ad
 */
public interface CommentService {
    ResponseEntity<List<Comment>> getComments(int postId);
    Comment getCommentById(int id);
    ResponseEntity<Comment> add(CommentDTO commentDTO);
    ResponseEntity update(CommentDTO commentDTO);
}
