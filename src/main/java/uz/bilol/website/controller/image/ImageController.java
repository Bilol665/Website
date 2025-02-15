package uz.bilol.website.controller.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.bilol.website.domain.dto.response.ApiResponse;
import uz.bilol.website.service.image.ImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/img")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        ApiResponse response = imageService.saveImage(file);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
