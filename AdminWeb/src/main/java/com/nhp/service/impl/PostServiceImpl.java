/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.nhp.pojo.Post;
import com.nhp.repository.PostRepository;
import com.nhp.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ad
 */
@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
    
    @Override
    public List<Post> getPublicPosts() {
        return this.postRepository.getPublicPosts();
    }

    @Override
    public List<Post> getUserPosts(int id) {
        return this.postRepository.getUserPosts(id);
    }
    
}
