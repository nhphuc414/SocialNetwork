/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.dto;

import lombok.Data;

/**
 *
 * @author ad
 */
@Data
public class ReactionDTO {
    private String id;
    private String type;
    private String userId;
    private String postId;
    private String commentId;
    private String status;
}
