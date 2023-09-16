/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhp.dto.PostDTO;
import com.nhp.pojo.Post;
import com.nhp.repository.PostRepository;
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
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<Post>> getPublicPosts() {
        return new ResponseEntity<>(this.postRepository.getPublicPosts(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Post>> getUserPosts(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (this.userService.getUserById(id).getUsername().equals(authentication.getName())
            ||authentication.getAuthorities().stream().
             anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity<>(this.postRepository.getUserPosts(id,Boolean.TRUE), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.postRepository.getUserPosts(id,Boolean.FALSE), HttpStatus.OK);
    }
    @Override
    public Post addPost(PostDTO post) {
        Post newpost = new Post();
        newpost.setContent(post.getContent());
        newpost.setStatus("PUBLIC");
        newpost.setCreatedDate(new Date());
        newpost.setUserId(this.userService.getUserById(Integer.parseInt(post.getUserId())));
        if (post.getImage()!= null && !post.getImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(post.getImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                newpost.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.postRepository.add(newpost);
    }

    @Override
    public ResponseEntity<Post> getPostById(int id) {
        Post post = this.postRepository.getPostById(id);
        if (post.getStatus().equals("DEL"))
            return new ResponseEntity("POST DELETED", HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (this.userService.getUserById(this.postRepository.getPostById(id).getUserId().getId()).getUsername().equals(authentication.getName())
                || authentication.getAuthorities().stream().
                        anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return new ResponseEntity(post , HttpStatus.OK);
        }
        return new ResponseEntity("YOU DON'T HAVE PERMISSION", HttpStatus.BAD_REQUEST);
        
    }

    @Override
    public ResponseEntity update(PostDTO post) {
        Post updatePost = this.postRepository.getPostById(Integer.parseInt(post.getId()));
        if (updatePost.getStatus().equals("DEL"))
            return new ResponseEntity("POST DELETED", HttpStatus.BAD_REQUEST);
        if (post.getContent()!=null && !post.getContent().isEmpty() ) {
            updatePost.setContent(post.getContent());
        }
        if (post.getStatus()!=null && !post.getStatus().isEmpty()) {
            updatePost.setStatus(post.getStatus());
        }
        if (post.getImage()!=null && !post.getImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(post.getImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                updatePost.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (this.userService.getUserById(updatePost.getUserId().getId()).getUsername().equals(authentication.getName())){
            return new ResponseEntity(postRepository.update(updatePost), HttpStatus.OK);
        }
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))&&
                post.getStatus().equals("DEL")){
             return new ResponseEntity(postRepository.update(updatePost), HttpStatus.OK);
        }
        return new ResponseEntity("YOU DON'T HAVE PERMISSION", HttpStatus.BAD_REQUEST);
    }
}
