/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.pojo.Post;
import java.util.List;

/**
 *
 * @author ad
 */
public interface PostService {
    List<Post> getPublicPosts();
    List<Post> getUserPosts(int id);
}
