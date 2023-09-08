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
    boolean addUser(User user, String identityNumber);
    boolean addOrUpdateTeacher(User user);
    List<User> getUsers(Map<String, String> params);
    long countUsers();
}
