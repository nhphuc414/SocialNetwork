/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhp.dto.CommentDTO;
import com.nhp.pojo.Comment;
import com.nhp.pojo.Post;
import com.nhp.repository.CommentRepository;
import com.nhp.service.CommentService;
import com.nhp.service.PostService;
import com.nhp.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ad
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Override
    public List<Comment> getComments(int postId) {
        return this.commentRepository.getComments(postId);
    }

    @Override
    public Comment add(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setStatus("PUBLIC");
        comment.setPostId(this.postService.getPostById(commentDTO.getPostId()));
        comment.setUserId(this.userService.getUserById(commentDTO.getUserId()));
        comment.setCreatedDate(new Date());
        if (!comment.getImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(comment.getImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                comment.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.commentRepository.add(comment);
    }

    @Override
    public boolean update(CommentDTO commentDTO) {
        Comment comment = this.commentRepository.getCommentById(commentDTO.getId());
        if (!commentDTO.getContent().isBlank()) {
            comment.setContent(commentDTO.getContent());
        }
        if (!commentDTO.getStatus().isBlank()) {
            comment.setStatus(commentDTO.getStatus());
        }
        if (!commentDTO.getImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(commentDTO.getImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                comment.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (comment.getUserId().getUsername().equals(authentication.getName())) {
            return commentRepository.update(comment);
        }
        if (authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")) 
                && commentDTO.getStatus().equals("DEL"))
            return commentRepository.update(comment);
        return false;
    }

    @Override
    public Comment getCommentById(int id) {
        return this.commentRepository.getCommentById(id);
    }
    
}
