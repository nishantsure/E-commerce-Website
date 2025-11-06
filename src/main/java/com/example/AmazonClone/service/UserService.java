//package com.example.AmazonClone.service;
//
//import com.example.AmazonClone.models.Users;
//import com.example.AmazonClone.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepo repo;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private Environment env;
//
////    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
//
//    public Users register(Users user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return repo.save(user);
//    }
//
////    public String login(Users user) {
////        Authentication authentication=authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
////        if(authentication.isAuthenticated())
////            return jwtService.generateToken(user.getUsername());
////        return "fail";
////
////    }
//
//
//}
