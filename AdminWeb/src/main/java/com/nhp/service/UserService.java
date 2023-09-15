/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import com.nhp.dto.UserDTO;
import com.nhp.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ad
 */
public interface UserService extends UserDetailsService{
    List<User> getUsers(Map<String, String> params);
    User getUserById(int id);
    User getUserByUsername(String username);
    String authUser(String username, String password);
    boolean addTeacher(User u);
    boolean resetUser(int id);
    User updateUser(UserDTO userDTO);
    ObjectError checkUnique(Map<String, String> params);
    long countUsers(Map<String, String> params);
    User addUser(UserDTO userDTO);
    boolean acceptUser(int id);
    boolean deniedUser(int id);
}
