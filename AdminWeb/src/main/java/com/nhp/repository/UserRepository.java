/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.repository;

import com.nhp.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.validation.ObjectError;

/**
 *
 * @author ad
 */
public interface UserRepository {
    User getUserById(int id);
    List<User> getUsers(Map<String, String> params);
    long countUsers(Map<String, String> params);
    User getUserByUsername(String username);
    boolean addTeacher(User u);
    boolean resetUser(int id);
    String authUser(String username,String password);
    User addUser(User u,String identity);
    ObjectError checkUnique(Map<String, String> params);
    User updateUser(User u);
    boolean acceptUser(int id);
    boolean deniedUser(int id);
}
