package uz.bilol.website.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.bilol.website.domain.dto.response.ApiResponse;
import uz.bilol.website.domain.dto.user.LoginDto;
import uz.bilol.website.domain.dto.user.UserCreateDto;
import uz.bilol.website.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/v1/auth")
public class AuthController {
    private final UserService userService;
    @GetMapping("/test")
    public ResponseEntity<String> auth() {
        return ResponseEntity.ok("Hello World!");
    }
    @GetMapping("/test/security")
    public ResponseEntity<String> security() {
        return ResponseEntity.ok("It won't work!");
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(
            @Valid @RequestBody UserCreateDto user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ApiResponse response = ApiResponse.create(bindingResult);
            return new ResponseEntity<>(response, response.getStatus());
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }
    @GetMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ApiResponse response = ApiResponse.create(bindingResult);
            return new ResponseEntity<>(response, response.getStatus());
        }
        ApiResponse response = userService.login(loginDto);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
