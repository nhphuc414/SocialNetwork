/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.pojo.Post;
import com.nhp.service.PostService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ad
 */
@RestController
@RequestMapping("/api")
public class ApiPostController {
    @Autowired
    private PostService postService;
    
    @GetMapping("/posts/")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Post>> list() {
        return new ResponseEntity<>(this.postService.getPublicPosts(), HttpStatus.OK);
    }
    @GetMapping("/posts/{id}/")
    @CrossOrigin
    public ResponseEntity<List<Post>> listUserPosts(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.postService.getUserPosts(id), HttpStatus.OK);
    }
}
