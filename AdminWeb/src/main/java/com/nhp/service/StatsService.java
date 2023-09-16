/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ad
 */
public interface StatsService {
    long countPostsThisMonth();
    long countUsers();
    long countUsersThisMonth();
    long countPostsThisYear();
    List<Object[]> countUsers(Map<String, String> params);
    List<Object[]> countPosts(Map<String, String> params);
}
