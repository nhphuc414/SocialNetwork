/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.nhp.repository.StatsRepository;
import com.nhp.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ad
 */
@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public long countPostsThisMonth() {
        return this.statsRepository.countPostsThisMonth();
    }

    @Override
    public long countUsers() {
        return this.statsRepository.countUsers();
    }

    @Override
    public long countUsersThisMonth() {
        return this.statsRepository.countUsersThisMonth();
    }

    @Override
    public long countPostsThisYear() {
        return this.statsRepository.countPostsThisYear();
    }

    @Override
    public List<Object[]> countUsers(Map<String, String> params) {
        return this.statsRepository.countUsers(params);
    }

    @Override
    public List<Object[]> countPosts(Map<String, String> params) {
        return this.statsRepository.countPosts(params);
    }
}
