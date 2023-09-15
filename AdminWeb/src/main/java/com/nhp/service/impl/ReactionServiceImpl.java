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
    public boolean addOrUpdateOrDelete(ReactionDTO reactionDTO) {
        if (reactionDTO.getStatus().equals("add")) {
            Reaction reaction = new Reaction();
            reaction.setCreatedDate(new Date());
            reaction.setUserId(this.userService.getUserById(reactionDTO.getUserId()));
            reaction.setType(reactionDTO.getType());
            if (reactionDTO.getPostId() != 0) {
                reaction.setPostId(this.postService.getPostById(reactionDTO.getPostId()));
            }
            if (reactionDTO.getCommentId() != 0) {
                reaction.setCommentId(this.commentService.getCommentById(reactionDTO.getCommentId()));
            }
            return this.reactionRepository.add(reaction);
        } else {
            Reaction reaction = this.reactionRepository.getById(reactionDTO.getId());
            if (reactionDTO.getStatus().equals("delete")) {
                return this.reactionRepository.delete(reactionDTO.getId());
            }
            reaction.setType(reactionDTO.getType());
            return this.reactionRepository.update(reaction);
        }
    }

}
