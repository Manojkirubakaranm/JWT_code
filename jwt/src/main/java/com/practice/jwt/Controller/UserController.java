package com.practice.jwt.Controller;


import com.practice.jwt.Model.Users;
import com.practice.jwt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getAll")
    public List<Users> get(){
        return userService.getAll();
    }
    @PostMapping("/post")
    public Users save(@RequestBody Users user){
        return userService.saveU(user);
    }
    @PostMapping("/login")
    public String log(@RequestBody Users user){

        return userService.check(user);
    }


}
