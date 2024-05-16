package com.example.finalboard.controller;

import com.example.finalboard.controller.request.LoginRequest;
import com.example.finalboard.controller.request.SignupRequest;
import com.example.finalboard.controller.response.LoginResponse;
import com.example.finalboard.controller.response.SignupResponse;
import com.example.finalboard.model.MyUser;
import com.example.finalboard.repository.UserRepository;
import com.example.finalboard.util.ApiResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResult<String>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        return userRepository.findByUserEmail(loginRequest.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), user.getUserPw())) {
                        session.setAttribute("userId", user.getUserId()); // 세션에 userId 저장
                        return ResponseEntity.ok(ApiResult.<String>success("Login successful"));
                    } else {
                        return ResponseEntity.<ApiResult<String>>badRequest().body(ApiResult.<String>error("Invalid password"));
                    }
                })
                .orElseGet(() -> ResponseEntity.<ApiResult<String>>badRequest().body(ApiResult.<String>error("User not found")));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResult<String>> registerUser(@RequestBody SignupRequest signupData) {
        System.out.println("Received signup request with data: " + signupData);

        if (userRepository.existsByUserEmail(signupData.getEmail())) {
            System.out.println("Email already exists: " + signupData.getEmail());
            return ResponseEntity.badRequest().body(ApiResult.error("Email already registered"));
        }

        try {
            MyUser newUser = new MyUser(signupData.getEmail(), passwordEncoder.encode(signupData.getPassword()), signupData.getNickname());
            userRepository.save(newUser);
            System.out.println("User registered successfully with email: " + newUser.getUserEmail());
            return ResponseEntity.ok(ApiResult.success("User registered successfully"));
        } catch (Exception e) {
            System.out.println("Error during user registration: " + e.getMessage());
            return ResponseEntity.badRequest().body(ApiResult.error("Registration failed"));
        }
    }
}