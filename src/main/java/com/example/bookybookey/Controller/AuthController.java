package com.example.bookybookey.Controller;

import com.example.bookybookey.Api.ApiResponse;
import com.example.bookybookey.Model.User;
import com.example.bookybookey.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/getAll")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid User user){

        authService.register(user);
        return ResponseEntity.ok(new ApiResponse("registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal User auth, @RequestBody @Valid User user){
        authService.updateUser(auth.getUserId(), user);
        return ResponseEntity.ok(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User user){
        authService.deleteUser(user.getUserId());
        return ResponseEntity.ok(new ApiResponse("deleted successfully"));
    }
}
