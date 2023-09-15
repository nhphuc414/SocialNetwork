/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.dto.PostDTO;
import com.nhp.pojo.Post;
import com.nhp.service.PostService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @CrossOrigin
    public ResponseEntity<List<Post>> list() {
        return new ResponseEntity<>(this.postService.getPublicPosts(), HttpStatus.OK);
    }
    @GetMapping("/posts/{id}/")
    @CrossOrigin
    public ResponseEntity<List<Post>> listUserPosts(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.postService.getUserPosts(id), HttpStatus.OK);
    }
    @PostMapping(path = "/post/", 
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, 
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity addPost(@Valid @ModelAttribute("post") PostDTO post, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Post newPost = postService.addPost(post);
        return ResponseEntity.ok(newPost);
    }
}
