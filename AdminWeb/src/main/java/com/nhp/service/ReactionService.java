/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.dto.ReactionDTO;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ad
 */
public interface ReactionService {
    long countReactionsByPostId(int postId);
    long countReactionsByCommentId(int commentId);
    ResponseEntity addOrUpdateOrDelete(ReactionDTO reactionDTO);
}
