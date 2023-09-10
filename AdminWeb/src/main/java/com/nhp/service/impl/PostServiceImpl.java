/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ad
 */
@Service
public class PostServiceImpl implements PostService{
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
    public Post addPost(Map<String, String> params, MultipartFile image) {
        Post post = new Post();
        post.setContent(params.get("content"));
        post.setStatus("PUBLIC");
        post.setCreatedDate(new Date());
        post.setUserId(this.userService.getUserById(Integer.parseInt(params.get("userId"))));
        if (!image.isEmpty()) {
            try { 
                Map res = this.cloudinary.uploader().upload(image.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                post.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else post.setImage(" ");
        return this.postRepository.addPost(post);
    }
    
}
