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
public class UserDTO {
    private String id;
    private String displayName;
    private String email;
    private String username;
    private String password;
    private MultipartFile avatar;
    private MultipartFile background;
    private String status;
    private String identity;
}
