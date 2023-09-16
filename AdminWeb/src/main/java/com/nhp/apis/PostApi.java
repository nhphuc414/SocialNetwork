/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.apis;

import com.nhp.dto.PostDTO;
import com.nhp.pojo.Post;
import com.nhp.service.PostService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/api/post")
public class PostApi {

    @Autowired
    private PostService postService;

    @PostMapping(path = "/add/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(@Valid @ModelAttribute("post") PostDTO post, BindingResult rs) {
        System.out.print("abc");
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Post newPost = postService.addPost(post);
        return ResponseEntity.ok(newPost);
    }

    @GetMapping("/posts/")
    public ResponseEntity<List<Post>> list() {
        return this.postService.getPublicPosts();
    }

    @GetMapping("/posts/{id}/")
    public ResponseEntity<List<Post>> listUserPosts(@PathVariable(value = "id") int id) {
        return this.postService.getUserPosts(id);
    }

    @GetMapping("/{id}/")
    public ResponseEntity getPost(@PathVariable(value = "id") int id) {
        return this.postService.getPostById(id);
    }

    @PostMapping(path = "/update/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@Valid
            @ModelAttribute("post") PostDTO postDTO, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
       return this.postService.update(postDTO);
    }

    @GetMapping("/block-comment/{id}/")
    public ResponseEntity blockComment(@PathVariable int id) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(String.valueOf(id));
        postDTO.setStatus("READONLY");
        return this.postService.update(postDTO);
    }

    @GetMapping("/unlock-comment/{id}/")
    public ResponseEntity unlock(@PathVariable int id) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(String.valueOf(id));
        postDTO.setStatus("PUBLIC");
        return this.postService.update(postDTO);

    }

    @GetMapping("/hidden-post/{id}/")
    public ResponseEntity hidden(@PathVariable int id) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(String.valueOf(id));
        postDTO.setStatus("HIDD");
        return this.postService.update(postDTO);
    }

    @GetMapping(path = "/delete/{id}/")
    public ResponseEntity delete(@PathVariable(name = "id") int id) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(String.valueOf(id));
        postDTO.setStatus("DEL");
        return this.postService.update(postDTO);
    }
}
