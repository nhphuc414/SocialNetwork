/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.service.StatsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ad
 */
@Controller
@RequestMapping("/admin")
public class StatController {
    @Autowired
    private StatsService statService;
    
    @GetMapping("/stats-user")
    @ResponseBody
    public ResponseEntity statUser(@RequestParam Map<String, String> params) {
        return new ResponseEntity(statService.countUsers(params), HttpStatus.OK);
    }
    @GetMapping("/stats-post")
    @ResponseBody
    public ResponseEntity statPost(@RequestParam Map<String, String> params) {
        return new ResponseEntity(statService.countPosts(params), HttpStatus.OK);
    }
}
