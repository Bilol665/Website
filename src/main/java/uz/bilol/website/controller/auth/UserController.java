package uz.bilol.website.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.bilol.website.domain.dto.response.ApiResponse;
import uz.bilol.website.service.user.UserService;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/addemail")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> addEmail(
            @RequestParam(name = "email", defaultValue = "null") String email,
            Principal principal
    ) {
        if (Objects.equals(email, "null")) {
            return ResponseEntity.badRequest()
                    .body(
                            ApiResponse.builder()
                                    .message("Email should be provided!")
                                    .success(false)
                                    .status(HttpStatus.BAD_REQUEST)
                                    .build()
                    );
        }
        ApiResponse response = userService.updateEmail(email, principal);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
