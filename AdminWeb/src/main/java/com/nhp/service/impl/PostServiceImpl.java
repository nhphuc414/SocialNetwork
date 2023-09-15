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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public List<Post> getPublicPosts() {
        return this.postRepository.getPublicPosts();
    }

    @Override
    public List<Post> getUserPosts(int id) {
        return this.postRepository.getUserPosts(id);
    }

    @Override
    public Post addPost(PostDTO post) {
        Post newpost = new Post();
        newpost.setContent(post.getContent());
        newpost.setStatus("PUBLIC");
        newpost.setCreatedDate(new Date());
        newpost.setUserId(this.userService.getUserById(Integer.parseInt(post.getUserId())));
        if (!post.getImage().isEmpty()) {
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
    public Post getPostById(int id) {
        return this.postRepository.getPostById(id);
    }

    @Override
    public boolean update(PostDTO post) {
        Post updatePost = this.postRepository.getPostById(Integer.parseInt(post.getId()));
        if (!post.getContent().isBlank()) {
            updatePost.setContent(post.getContent());
        }
        if (!post.getStatus().isBlank()) {
            updatePost.setStatus(post.getStatus());
        }
        if (!post.getImage().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(post.getImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                updatePost.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (updatePost.getUserId().getUsername().equals(authentication.getName())) {
            return postRepository.update(updatePost);
        }
        if (authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")) 
                && post.getStatus().equals("DEL"))
            return postRepository.update(updatePost);
        return false;
    }
}
