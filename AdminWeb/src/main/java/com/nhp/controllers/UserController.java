/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.pojo.User;
import com.nhp.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @RequestMapping("/")
    public String norIndex(Model model){
        return "redirect:/admin/";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute(value = "user") User u, BindingResult rs) {
        Map<String, String> params = new HashMap<>();
        params.put("username",u.getUsername());
        params.put("email",u.getEmail());
        rs.addError(this.userService.checkUnique(params));
        if (!rs.hasErrors()) {
            if (this.userService.addTeacher(u) == true) {
            return "redirect:/users";
            }
        }return "add";
    }

    @GetMapping("/expire/{id}")
    public String expire(@PathVariable(value = "id") int id) {
        if (this.userService.resetUser(id) == true) {
            return "redirect:/expire";
        }
        return "expire";
    }
    @PostMapping("/request/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accept(@PathVariable(value = "id") int id) {
        this.userService.acceptUser(id);
    }
    @DeleteMapping("/request/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void denied(@PathVariable(value = "id") int id) {
        this.userService.deniedUser(id);
    }
}
