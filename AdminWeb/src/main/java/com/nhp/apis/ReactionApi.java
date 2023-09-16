/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.apis;

import com.nhp.dto.CommentDTO;
import com.nhp.dto.ReactionDTO;
import com.nhp.service.ReactionService;
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
@RequestMapping("/api/reaction/")
public class ReactionApi {
    @Autowired
    private ReactionService reactionService;
    
    @PostMapping(path = "/add/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(@Valid @ModelAttribute("reaction") ReactionDTO reactionDTO, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        reactionDTO.setStatus("add");
        return this.reactionService.addOrUpdateOrDelete(reactionDTO);
    }
    @PostMapping(path = "/update/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@Valid
            @ModelAttribute("reaction") ReactionDTO reactionDTO, BindingResult rs) {
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        reactionDTO.setStatus("update");
       return this.reactionService.addOrUpdateOrDelete(reactionDTO);
    }
    @GetMapping(path = "/delete/{id}/")
    public ResponseEntity delete(@PathVariable(name = "id") int id) {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setId(String.valueOf(id));
        reactionDTO.setStatus("delete");
        return this.reactionService.addOrUpdateOrDelete(reactionDTO);
    }
}
