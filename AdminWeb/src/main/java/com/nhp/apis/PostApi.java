/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.apis;

import com.nhp.dto.PostDTO;
import com.nhp.pojo.Post;
import com.nhp.service.PostService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ad
 */
@RestController
@CrossOrigin
@RequestMapping("/api/post")
public class PostApi {
    @Autowired
    private PostService postService;
    
    @PostMapping(path = "/add/",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(@Valid @ModelAttribute("post") PostDTO post, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Post newPost = postService.addPost(post);
        return ResponseEntity.ok(newPost);
    }
}
