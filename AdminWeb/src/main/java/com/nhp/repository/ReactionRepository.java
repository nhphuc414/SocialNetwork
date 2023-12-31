/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.repository;
import com.nhp.pojo.Reaction;

/**
 *
 * @author ad
 */
public interface ReactionRepository {
    long countReactionsByPostId(int postId);
    long countReactionsByCommentId(int commentId);
    Reaction getById(int id);
    boolean add(Reaction reaction);
    boolean update(Reaction reaction);
    boolean delete(int id);
}
