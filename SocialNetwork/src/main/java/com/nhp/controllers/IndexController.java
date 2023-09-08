/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ad
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("msg","My Web");
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/users")
    public String users(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("users", this.userService.getUsers(params));
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.userService.countUsers();
        model.addAttribute("counter", Math.ceil(count*1.0/pageSize));
        return "users";
    }
}
