/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.apis;

import com.nhp.components.JwtService;
import com.nhp.dto.PostDTO;
import com.nhp.dto.UserDTO;
import com.nhp.pojo.Post;
import com.nhp.pojo.User;
import com.nhp.service.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ad
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user/")
public class UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    
    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> details(Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
    
    @PostMapping("/login/")
    public ResponseEntity<String> login(@RequestBody User user) {
        String status =this.userService.authUser(user.getUsername(), user.getPassword());
        if ("ACTIVE".equals(status)) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } 
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
    @PostMapping(path = "/update/",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult rs) {
        Map<String, String> params = new HashMap<>();
        params.put("username",userDTO.getUsername());
        params.put("email",userDTO.getEmail());
        params.put("identity",userDTO.getIdentity());
        rs.addError(this.userService.checkUnique(params));
        if (rs.hasErrors()) {
            return new ResponseEntity(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(this.userService.updateUser(userDTO));
    }
}
