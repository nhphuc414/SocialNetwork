/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhp.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ad
 */
public interface StatsRepository {
    List<Object[]> stats(Map<String, String> params);
    long countPostsThisMonth();
    long countUsers();
    long countUsersThisMonth();
    long countPostsThisYear();
}
