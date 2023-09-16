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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Comment>> getComments(int postId) {
        if (this.postService.getPostById(postId).getStatusCode().equals(HttpStatus.BAD_REQUEST))
            return new ResponseEntity("YOU DON'T HAVE PERMISSION" , HttpStatus.BAD_REQUEST);
        List<Comment> comments = this.commentRepository.getComments(postId);
        return new ResponseEntity(comments , HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Comment> add(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setStatus("PUBLIC");
        comment.setPostId(this.postService.getPostById(Integer.parseInt(commentDTO.getPostId())).getBody());
        comment.setUserId(this.userService.getUserById(Integer.parseInt(commentDTO.getUserId())));
        comment.setCreatedDate(new Date());
        if (comment.getImage()!=null &&!comment.getImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(comment.getImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                comment.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((comment.getPostId().getStatus().equals("HIDD") 
             && !comment.getPostId().getUserId().getUsername().equals(authentication.getName()))
             ||comment.getPostId().getStatus().equals("READONLY")||comment.getPostId().getStatus().equals("DEL")
           )
            return new ResponseEntity("YOU DON'T HAVE PERMISSION", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(commentRepository.add(comment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(CommentDTO commentDTO) {
        Comment comment = this.commentRepository.getCommentById(Integer.parseInt(commentDTO.getId()));
        if (commentDTO.getContent()!=null && !commentDTO.getContent().isEmpty()) {
            comment.setContent(commentDTO.getContent());
        }
        if (commentDTO.getStatus()!=null && !commentDTO.getStatus().isEmpty()) {
            comment.setStatus(commentDTO.getStatus());
        }
        if (commentDTO.getImage()!=null && !commentDTO.getImage().isEmpty()) {
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
            return new ResponseEntity(commentRepository.update(comment), HttpStatus.OK);
        }
        if ((authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
                || comment.getPostId().getUserId().getUsername().equals(authentication.getName())
                )
                && commentDTO.getStatus().equals("DEL"))
            return new ResponseEntity(commentRepository.update(comment), HttpStatus.OK);
        return new ResponseEntity("YOU DON'T HAVE PERMISSION", HttpStatus.BAD_REQUEST);
    }
    @Override
    public Comment getCommentById(int id) {
        return this.commentRepository.getCommentById(id);
    }
    
}
