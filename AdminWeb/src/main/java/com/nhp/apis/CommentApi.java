/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.apis;

import com.nhp.dto.CommentDTO;
import com.nhp.dto.PostDTO;
import com.nhp.pojo.Comment;
import com.nhp.pojo.Post;
import com.nhp.service.CommentService;
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
@RequestMapping("/api/comment/")
@CrossOrigin
public class CommentApi {
    @Autowired
    private CommentService commentService;
    
    
    @PostMapping(path = "/add/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(@Valid @ModelAttribute("comment") CommentDTO commentDTO, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return this.commentService.add(commentDTO);
    }
    @GetMapping("/comments/{id}")
    public ResponseEntity<List<Comment>> listPostComment(@PathVariable(value = "id") int id) {
        return this.commentService.getComments(id);
    }
    @PostMapping(path = "/update/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@Valid
            @ModelAttribute("post") CommentDTO commentDTO, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
       return this.commentService.update(commentDTO);
    }
    @GetMapping(path = "/delete/{id}/")
    public ResponseEntity delete(@PathVariable(name = "id") int id) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(String.valueOf(id));
        commentDTO.setStatus("DEL");
        return this.commentService.update(commentDTO);
    }
    

}
