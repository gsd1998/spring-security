package com.diveintodev.SpringSecurity.controller;

import com.diveintodev.SpringSecurity.model.UserInfo;
import com.diveintodev.SpringSecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {

    private final String DEFAULT_ROLE = "ROLE_USER";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addUser(@RequestBody UserInfo userInfo){
        //userInfo.setRoles(DEFAULT_ROLE);
        //By default when a user is added the role will be 'ROLE_USER'
        String encryptPwd = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encryptPwd);
        userRepository.save(userInfo);
        return "user saved";
    }
    //get each user by id --> can be accessed by any user
    @GetMapping("/getUser/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserInfo getUserById(@PathVariable int userId){
        return userRepository.findById(userId).get();
    }
}
