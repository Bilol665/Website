package uz.bilol.website.service.user;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.bilol.website.domain.dto.response.ApiResponse;
import uz.bilol.website.domain.dto.response.JwtResponse;
import uz.bilol.website.domain.dto.user.LoginDto;
import uz.bilol.website.domain.dto.user.UserCreateDto;
import uz.bilol.website.domain.entity.user.UserEntity;
import uz.bilol.website.domain.entity.user.UserState;
import uz.bilol.website.exception.DataNotFoundException;
import uz.bilol.website.repository.user.UserRepository;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserEntityByEmail(username).orElseThrow(
                () -> new DataNotFoundException(username));
    }

    public ApiResponse saveUser(UserCreateDto user) {
        UserEntity mappedUser = modelMapper.map(user, UserEntity.class);
        mappedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        mappedUser.setState(UserState.UNVERIFIED);
        UserEntity savedUser = userRepository.save(mappedUser);
        return ApiResponse.builder()
                .message("All good")
                .status(HttpStatus.OK)
                .success(true)
                .data(savedUser)
                .build();
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    public ApiResponse login(LoginDto loginDto) {
        if (loginDto.getEmail() == null) {
            Optional<UserEntity> userOptional = userRepository.findUserEntityByUsername(loginDto.getUsername());
            if (userOptional.isEmpty()) {
                return ApiResponse.builder()
                        .message("User with this username not found!")
                        .status(HttpStatus.NOT_FOUND)
                        .success(false)
                        .build();
            }
            UserEntity user = userOptional.get();
            if (user.getState() == UserState.INACTIVE) {
                return ApiResponse.builder()
                        .message("This user is inactive!")
                        .status(HttpStatus.FORBIDDEN)
                        .success(false)
                        .build();
            } else if (user.getState() == UserState.DELETED) {
                return ApiResponse.builder()
                        .message("This user is deleted!")
                        .status(HttpStatus.FORBIDDEN)
                        .success(false)
                        .build();
            } else if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(
                                JwtResponse.builder()
                                        .token(jwtService.generateAccessToken(user))
                                        .refreshToken(jwtService.generateRefreshToken(user))
                                        .build()
                        )
                        .build();
            }
            return ApiResponse.builder()
                    .message("Invalid password!")
                    .success(false)
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            if (!isValidEmail(loginDto.getEmail())) {
                return ApiResponse.builder()
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .success(false)
                        .message("Email invalid!")
                        .build();
            }
            Optional<UserEntity> userOptional = userRepository.findUserEntityByEmail(loginDto.getEmail());
            if (userOptional.isEmpty()) {
                return ApiResponse.builder()
                        .message("User with this email not found!")
                        .status(HttpStatus.NOT_FOUND)
                        .success(false)
                        .build();
            }
            UserEntity user = userOptional.get();
            if (user.getState() == UserState.INACTIVE) {
                return ApiResponse.builder()
                        .message("This user is inactive!")
                        .status(HttpStatus.FORBIDDEN)
                        .success(false)
                        .build();
            } else if (user.getState() == UserState.DELETED) {
                return ApiResponse.builder()
                        .message("This user was deleted!")
                        .status(HttpStatus.FORBIDDEN)
                        .success(false)
                        .build();
            } else if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(
                                JwtResponse.builder()
                                        .token(jwtService.generateAccessToken(user))
                                        .refreshToken(jwtService.generateRefreshToken(user))
                                        .build()
                        )
                        .build();
            }
            return ApiResponse.builder()
                    .message("Password failed!")
                    .status(HttpStatus.UNAUTHORIZED)
                    .success(false)
                    .build();
        }

    }
}
