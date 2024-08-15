package com.ZoomCart.User.controller;

import com.ZoomCart.User.ApiConstant;
import com.ZoomCart.User.model.UserModel;
import com.ZoomCart.User.service.impl.UserServiceImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping(value = ApiConstant.USER_API)
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping(value = "/getUsers")
    List<UserModel> getUsers(){
        return userServiceImpl.getAllUsers();
    }
    
    @PostMapping(value = "/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel) {
        String userEmail = userModel.getEmail();

        if (userServiceImpl.isUserExists(userEmail)) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        boolean registrationSuccessful = userServiceImpl.register(userModel);
        if (registrationSuccessful) {
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    }

  @PostMapping(value = "/loginUser")
  public ResponseEntity<Object> loginUser(@RequestBody UserModel userModel) {
        log.info("Inside /loginUser got call to login: {}", userModel);
    Map<String, Object> response =
        userServiceImpl.loginUser(userModel.getEmail(), userModel.getPassword(), userModel.getRole());

    if (response != null) {
        return ResponseEntity.ok(response);
    }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
  }
    @PutMapping(value = "/resetPassword/{userId}/{newPass}")
    public ResponseEntity<String> reset(
            @PathVariable String newPass, @PathVariable Integer userId,
            @RequestBody UserModel userModel
            ) {
        String resetResult = userServiceImpl.resetPassword(userId, newPass);

        if (!"null".equals(resetResult)) {
            return ResponseEntity.ok("Password reset");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resetResult);
        }
    }

    @DeleteMapping(value = "/deleteUser/{userId}")
    public ResponseEntity<String> delete(@PathVariable Integer userId,
                                         @RequestHeader("Authorization") String token){

        UserModel userModel = new UserModel();
        userModel.setUserId(userId);
        boolean isSuccess = userServiceImpl.deleteUser(userModel.getUserId());
        if (isSuccess) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping(value = "/logout/{userId}")
    public ResponseEntity<String> logout(@PathVariable Integer userId,
                                         @RequestHeader("Authorization") String token){
        boolean logoutSuccess = userServiceImpl.logout(userId,token);
        
        if (logoutSuccess) {
            return ResponseEntity.ok("Done");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
    @GetMapping(value = "/getUser/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Integer userId){
        Optional<UserModel> user = userServiceImpl.getUser(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    
    @PostMapping("/getEmail")
    public String getEmail(@RequestBody Integer userId){
        return userServiceImpl.getsEmail(userId);
    }
}
