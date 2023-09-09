/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.pojo.User;
import com.nhp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ad
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/add")
    public String addTeacher(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute(value = "user") User u) {
        System.out.println(u.getDisplayName());
        if (this.userService.addTeacher(u) == true) {
            return "redirect:/users";
        }
        return "add";
    }

    @GetMapping("/expire/{id}")
    public String expire(@PathVariable(value = "id") int id) {
        if (this.userService.resetUser(id) == true) {
            return "redirect:/expire";
        }
        return "expire";
    }
}
