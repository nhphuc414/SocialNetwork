/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.dto.ReactionDTO;

/**
 *
 * @author ad
 */
public interface ReactionService {
    long countReactionsByPostId(int postId);
    long countReactionsByCommentId(int commentId);
    boolean addOrUpdateOrDelete(ReactionDTO reactionDTO);
}
