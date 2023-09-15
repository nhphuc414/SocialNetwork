/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ad
 */
@Data
public class CommentDTO {
    private int id;
    private String content;
    private MultipartFile image;
    private String status;
    private int userId;
    private int postId;
}
