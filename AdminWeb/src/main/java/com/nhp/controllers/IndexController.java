/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;


import com.nhp.service.StatsService;
import com.nhp.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author ad
 */
@Controller
@PropertySource("classpath:configs.properties")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private Environment env;
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("users",this.statsService.countUsers());
        model.addAttribute("monthlyUsers",this.statsService.countUsersThisMonth());
        model.addAttribute("monthlyPosts",this.statsService.countPostsThisMonth());
        model.addAttribute("yearlyPosts",this.statsService.countPostsThisYear());
        return "index";
    }
    @RequestMapping("/stats")
    public String stats(Model model){
        return "stats";
    }
    @RequestMapping("/users")
    public String users(Model model, @RequestParam Map<String, String> params){
        params.put("role","ROLE_TEACHER");
        model.addAttribute("users", this.userService.getUsers(params));
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.userService.countTeachers();
        model.addAttribute("counter", Math.ceil(count*1.0/pageSize));
        return "users";
    }
    @RequestMapping("/expire")
    public String expireUsers(Model model, @RequestParam Map<String, String> params){
        params.put("status","EXPIRE");
        model.addAttribute("users", this.userService.getUsers(params));
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.userService.countExpire();
        model.addAttribute("counter", Math.ceil(count*1.0/pageSize));
        return "expire";
    }
}
