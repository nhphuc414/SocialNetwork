/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.nhp.dto.ReactionDTO;
import com.nhp.pojo.Reaction;
import com.nhp.repository.ReactionRepository;
import com.nhp.service.CommentService;
import com.nhp.service.PostService;
import com.nhp.service.ReactionService;
import com.nhp.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ad
 */
@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public long countReactionsByPostId(int postId) {
        return this.reactionRepository.countReactionsByPostId(postId);
    }

    @Override
    public long countReactionsByCommentId(int commentId) {
        return this.reactionRepository.countReactionsByCommentId(commentId);
    }

    @Override
    public ResponseEntity addOrUpdateOrDelete(ReactionDTO reactionDTO) {
        if (reactionDTO.getStatus().equals("add")) {
            Reaction reaction = new Reaction();
            reaction.setCreatedDate(new Date());
            reaction.setUserId(this.userService.getUserById(Integer.parseInt(reactionDTO.getUserId())));
            reaction.setType(reactionDTO.getType());
            if (reactionDTO.getPostId() != null && !reactionDTO.getPostId().isEmpty()) {
                reaction.setPostId(this.postService.getPostById(Integer.parseInt(reactionDTO.getPostId())).getBody());
            }else if (reactionDTO.getCommentId() != null && !reactionDTO.getPostId().isEmpty()) {
                reaction.setCommentId(this.commentService.getCommentById(Integer.parseInt(reactionDTO.getCommentId())));
            }
            this.reactionRepository.add(reaction);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Reaction reaction = this.reactionRepository.getById(Integer.parseInt(reactionDTO.getId()));
            if (!this.userService.getUserById(reaction.getUserId().getId()).getUsername().equals(authentication.getName())) {
                return new ResponseEntity("YOU DON'T HAVE PERMISSION", HttpStatus.BAD_REQUEST);
            }
            if (reactionDTO.getStatus().equals("delete")) {
                this.reactionRepository.delete(Integer.parseInt(reactionDTO.getId()));
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            if (reactionDTO.getStatus().equals("update")) {
            reaction.setType(reactionDTO.getType());
            this.reactionRepository.update(reaction);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity("SOMETHING WRONG",HttpStatus.BAD_REQUEST);
    }

}
