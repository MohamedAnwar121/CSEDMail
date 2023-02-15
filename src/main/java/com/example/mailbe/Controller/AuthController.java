package com.example.mailbe.Controller;


import com.example.mailbe.Model.User;
import com.example.mailbe.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@CrossOrigin()
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody User user) {
        userService.saveUser(user);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Invalid Domain");
    }
}
