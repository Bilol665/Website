package uz.bilol.website.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/v1/auth")
public class AuthController {
    @GetMapping("/test")
    public ResponseEntity<String> auth() {
        return ResponseEntity.ok("Hello World!");
    }
    @GetMapping("/test/security")
    public ResponseEntity<String> security() {
        return ResponseEntity.ok("Hello Security!");
    }
}
