/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.controllers;

import com.nhp.components.JwtService;
import com.nhp.pojo.Post;
import com.nhp.pojo.User;
import com.nhp.service.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ad
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/login/")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        String status =this.userService.authUser(user.getUsername(), user.getPassword());
        if ("ACTIVE".equals(status)) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } 
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> details(Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
    @PostMapping(path = "/users/", 
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, 
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<User> addUser(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar, @RequestPart MultipartFile background) {
        User user = this.userService.addUser(params, avatar, background);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @GetMapping("/test/")
    public ResponseEntity<List<User>> gU() {
        Map<String, String> params = new HashMap<>();
        params.put("status","REQUESTING");
        params.put("role","ROLE_USER");
        return new ResponseEntity<>(this.userService.getUsers(params), HttpStatus.OK);
    }
}
