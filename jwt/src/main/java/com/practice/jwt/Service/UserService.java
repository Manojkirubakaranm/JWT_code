package com.practice.jwt.Service;

import com.practice.jwt.Model.Users;
import com.practice.jwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    BCryptPasswordEncoder endcoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authMan;

//    @Autowired
//    private JwtService jwtService;


    public Users saveU(Users user){
        System.out.println(user);
//        System.out.println("checking  ="+userRepo.findByUsername(user.getUsername()));
        user.setPassword(endcoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public List<Users> getAll(){
        return userRepo.findAll();
    }

    public String check(Users user) {
        System.out.println("hi");
        System.out.println("checking  ="+userRepo.findByUsername(user.getUsername()));
        Authentication authentication = authMan.
                authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));
        System.out.println(authentication.isAuthenticated()+"   looking");
        if(authentication.isAuthenticated()){
            System.out.println("token authing ==================================");
            return new JwtService().generateToken(user.getUsername());
        }
        return "fail";
    }
}
