package com.ZoomCart.User.service;

import com.ZoomCart.User.model.UserModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServiceInterface {
    List<UserModel> getAllUsers();

    boolean register(UserModel userModel);

    Map<String, Object> loginUser(String email, String pass, String role);

    String resetPassword(Integer userId, String pass);

    boolean deleteUser(int userId);

    Optional<UserModel> getUser(int userId);

    boolean isUserExists(String userEmail);

    boolean logout(Integer userId,String token);
}
