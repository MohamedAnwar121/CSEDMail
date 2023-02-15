package com.example.mailbe.Controller;

import com.example.mailbe.Service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin()
@RequestMapping("/api/validation")
@RestController
public class ValidationController {

    private final ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/validate")
    public void checkEmailTaken(@RequestBody Map<String,Object> map){
        validationService.chechEmailTaken((String) map.get("email"));
    }
}
