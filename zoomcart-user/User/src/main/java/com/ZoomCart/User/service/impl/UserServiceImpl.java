package com.ZoomCart.User.service.impl;

import com.ZoomCart.User.entity.User;
import com.ZoomCart.User.model.UserModel;
import com.ZoomCart.User.repository.UserRepository;
import com.ZoomCart.User.service.ServiceInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements ServiceInterface {
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  @Autowired private RedisTemplate<String, String> redisTemplate;
  @Autowired UserRepository userRepository;

  public List<UserModel> getAllUsers() {
    List<User> userList = userRepository.findAll();
    List<UserModel> userModels = new ArrayList<>();
    for (User user1 : userList) {
      if (Objects.equals(user1.getRole(), "user")) {
        UserModel userModel = new UserModel();
        userModel.setUserId(user1.getUserId());
        userModel.setEmail(user1.getEmail());
        userModel.setPassword(user1.getPassword());
        userModel.setRole(user1.getRole());
        userModels.add(userModel);
      }
    }
    return userModels;
  }

  public boolean register(UserModel userModel) {
    try {
      User user = new User();
      user.setUserId(userModel.getUserId());
      user.setEmail(userModel.getEmail());
      String hashedPassword = passwordEncoder.encode(userModel.getPassword());
      user.setPassword(hashedPassword);
      user.setRole(userModel.getRole());
      userRepository.save(user);
      return true;
    } catch (Exception exc) {
      return false;
    }
  }

  public Map<String, Object> loginUser(String email, String pass, String role) {

    Optional<User> user = userRepository.findByEmailAndRole(email, role);
    log.info(String.valueOf(user));
    User optUser = user.get();

    if (passwordEncoder.matches(pass, optUser.getPassword())) {
      Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
      String token = generateToken(optUser.getEmail(), key);
      Integer userId = user.get().getUserId();

      Map<String, Object> response = new HashMap<>();
      response.put("token", token);
      response.put("userId", userId);
      response.put("role",optUser.getRole());
      return response;
    }
    return null;
  }

  public String resetPassword(Integer userId, String pass) {
    User user = userRepository.findByUserId(userId);
    //      user.setPassword(passwordEncoder.encode(pass));
    user.setPassword(pass);
    userRepository.save(user);
    return "Done";
  }

  public boolean deleteUser(int userId) {
    try {
      userRepository.deleteById(userId);
      return true;
    } catch (Exception exc) {
      return false;
    }
  }

  public Optional<UserModel> getUser(int userId) {
    User user = userRepository.getUserByUserId(userId);
    UserModel userModel = new UserModel();
    userModel.setUserId(user.getUserId());
    userModel.setPassword(user.getPassword());
    userModel.setEmail(user.getEmail());
    userModel.setRole(user.getRole());
    return Optional.of(userModel);
  }


  public boolean isUserExists(String userEmail) {
    return userRepository.existsByEmail(userEmail);
  }


  public boolean logout(Integer userId,String token) {
    if(validateToken(token)){
      expireToken(token);
      return true;
    }
    return false;
  }

  public String getsEmail(Integer userId) {
    User user = userRepository.findByUserId(userId);
    return user.getEmail();
  }


    private String generateToken(String email, Key key) {

        long expirationTime = 5 * 60 * 1000;
        String token =
                Jwts.builder()
                        .setSubject(email)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact();
        redisTemplate.opsForValue().set(token, email, expirationTime, TimeUnit.MILLISECONDS);
        return token;
    }

    private boolean validateToken(String token) {
        String storedEmail = redisTemplate.opsForValue().get(token);

        return storedEmail == null || !storedEmail.equals(getUsernameFromToken(token));
    }

    private void expireToken(String token) {
        String[] tokens = token.split(" ");
        if (tokens.length == 2) {
            deleteTokenEntry(tokens[1]);
        }
    }
    private void deleteTokenEntry(String token) {
        String storedEmail = redisTemplate.opsForValue().get(token);
        if (storedEmail != null) {
            redisTemplate.delete(token);
        }
    }
    private String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
