/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.repository;

import com.nhp.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ad
 */
public interface UserRepository {
    List<User> getUsers(Map<String, String> params);
    User getUserById(int id);
    User getUserByUsername(String username);
    boolean addTeacher(User u);
    long countTeachers();
    long countExpire();
    boolean resetUser(int id);
    String authUser(String username,String password);
    User addUser(User u,String identity);
    User updateUser(User u);
}
