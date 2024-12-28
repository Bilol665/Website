package uz.bilol.website.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.bilol.website.domain.dto.response.ApiResponse;
import uz.bilol.website.domain.dto.user.UserCreateDto;
import uz.bilol.website.domain.entity.user.UserEntity;
import uz.bilol.website.domain.entity.user.UserState;
import uz.bilol.website.exception.DataNotFoundException;
import uz.bilol.website.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
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
}
