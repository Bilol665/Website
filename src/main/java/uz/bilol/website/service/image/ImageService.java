package uz.bilol.website.service.image;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.bilol.website.domain.dto.response.ApiResponse;
import uz.bilol.website.domain.dto.response.ImageResponse;
import uz.bilol.website.domain.entity.image.ImageEntity;
import uz.bilol.website.repository.image.ImageRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    public ApiResponse saveImage(MultipartFile file) {
        try {
            ImageEntity imageEntity = ImageEntity.builder()
                    .name(file.getOriginalFilename())
                    .data(file.getBytes())
                    .build();
            ImageEntity savedImage = imageRepository.save(imageEntity);
            ImageResponse mappedImg = modelMapper.map(savedImage, ImageResponse.class);
            return ApiResponse.builder()
                    .message("Image saved successfully!")
                    .status(HttpStatus.CREATED)
                    .data(mappedImg)
                    .success(true)
                    .build();
        } catch (IOException e) {
            return ApiResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.CONFLICT)
                    .success(false)
                    .build();
        }
    }
}
