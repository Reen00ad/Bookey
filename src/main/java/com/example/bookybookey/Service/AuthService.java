package com.example.bookybookey.Service;

import com.example.bookybookey.Api.ApiException;
import com.example.bookybookey.Model.User;
import com.example.bookybookey.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;


    public void register(User user) {
        user.setRole("READER");
        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPassword);
        authRepository.save(user);
    }

    public List<User> getAllUsers(){
        return authRepository.findAll();
    }


    public void updateUser(Integer userId, User user) {
        User u = authRepository.findUserByUserId(userId);

        if (u == null) {
            throw new ApiException("User not found for ID: " + userId);
        }

        // Update user fields
        u.setName(user.getName());
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setPhoneNumber(user.getPhoneNumber());

        // Update password securely
        String hashpass = new BCryptPasswordEncoder().encode(user.getPassword());
        u.setPassword(hashpass);

        // Save the updated user
        authRepository.save(u);
    }
//
//
//    public void updateUser(Integer userId,User user) {
//        User u = authRepository.findUserByUserId(userId);
//
//        u.setName(user.getName());
//        u.setUsername(user.getUsername());
//        u.setEmail(user.getEmail());
//        u.setPhoneNumber(user.getPhoneNumber());
//        u.setEmail(user.getEmail());
//
//        String hashpass=new BCryptPasswordEncoder().encode(user.getPassword());
//        u.setPassword(hashpass);
//
//        authRepository.save(u);
//    }

    public void deleteUser(Integer userId) {
        User u = authRepository.findUserByUserId(userId);

        authRepository.delete(u);
    }


}
