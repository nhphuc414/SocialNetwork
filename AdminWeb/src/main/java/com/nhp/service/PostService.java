/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.dto.PostDTO;
import com.nhp.pojo.Post;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ad
 */
public interface PostService {
    ResponseEntity<List<Post>> getPublicPosts();
    ResponseEntity<List<Post>> getUserPosts(int id);
    ResponseEntity<Post> getPostById(int id);
    Post addPost(PostDTO post);
    ResponseEntity update(PostDTO post);
}
