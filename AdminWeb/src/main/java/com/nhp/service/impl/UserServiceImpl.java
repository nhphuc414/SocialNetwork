/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhp.dto.UserDTO;
import com.nhp.pojo.User;
import com.nhp.repository.UserRepository;
import com.nhp.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ad
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepository.getUsers(params);
    }
    @Override
    public User getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    @Override
    public boolean addTeacher(User u) {
            u.setRole("ROLE_TEACHER");
            u.setStatus("ACTIVE");
            u.setAvatar("https://res.cloudinary.com/dm5nn54wh/image/upload/v1694164906/avatar_btgfil.jpg");
            u.setBackground("https://res.cloudinary.com/dm5nn54wh/image/upload/v1694164909/background_wde7gz.jpg");
            u.setPassword(this.passwordEncoder.encode("ou@123"));
            u.setCreatedDate(new Date());
        return this.userRepository.addTeacher(u);
    }

    @Override
    public boolean resetUser(int id) {
        return this.userRepository.resetUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u =  this.userRepository.getUserByUsername(username);
        if (u != null) {
        // Gán vai trò dựa trên logic của bạn
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));
        return new org.springframework.security.core.userdetails.User(
            u.getUsername(),
            u.getPassword(),
            authorities
        );
    } else {
        throw new UsernameNotFoundException("User not found or inactive");
    }
    }
    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }
    @Override
    public String authUser(String username, String password) {
        return this.userRepository.authUser(username, password);
    }

    @Override
    public User addUser(UserDTO userDTO) {
        User u = new User();
        u.setDisplayName(userDTO.getDisplayName());
        u.setEmail(userDTO.getEmail());
        u.setUsername(userDTO.getUsername());
        u.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        u.setRole("ROLE_USER");
        u.setCreatedDate(new Date());
        u.setStatus("REQUESTING");
        if (!userDTO.getAvatar().isEmpty()) {
            try { 
                Map res = this.cloudinary.uploader().upload(userDTO.getAvatar().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        if (!userDTO.getBackground().isEmpty()) {
            try { 
                Map res = this.cloudinary.uploader().upload(userDTO.getBackground().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setBackground(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        return this.userRepository.addUser(u,userDTO.getIdentity());
    }

    @Override
    public long countUsers(Map<String, String> params) {
        return this.userRepository.countUsers(params);
    }

    @Override
    public boolean acceptUser(int id) {
        return this.userRepository.acceptUser(id);
    }

    @Override
    public boolean deniedUser(int id) {
        return this.userRepository.deniedUser(id);
    }

    @Override
    public ObjectError checkUnique(Map<String, String> params) {
        return this.userRepository.checkUnique(params);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User u = this.userRepository.getUserById(Integer.parseInt(userDTO.getId()));
        if (userDTO.getDisplayName()!=null){
            u.setDisplayName(userDTO.getDisplayName());
        }
        if(userDTO.getPassword()!=null){
           u.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getEmail()!=null){
            u.setEmail(userDTO.getEmail());
        }
        if (userDTO.getStatus()!=null){
            u.setStatus(userDTO.getStatus());
        }
        if (!userDTO.getAvatar().isEmpty()) {
            try { 
                Map res = this.cloudinary.uploader().upload(userDTO.getAvatar().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        if (!userDTO.getBackground().isEmpty()) {
            try { 
                Map res = this.cloudinary.uploader().upload(userDTO.getBackground().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setBackground(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        return this.userRepository.updateUser(u);
    }
}
