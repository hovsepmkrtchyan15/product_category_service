package com.example.product_category_service.endpoint;

import com.example.product_category_service.dto.CreateUserDto;
import com.example.product_category_service.dto.UserAuthDto;
import com.example.product_category_service.dto.UserAuthResponseDto;
import com.example.product_category_service.mapper.UserMapper;
import com.example.product_category_service.model.User;
import com.example.product_category_service.service.UserService;
import com.example.product_category_service.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto) {
        Optional<User> existingUser = userService.findByEmail(createUserDto);
        if(existingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userService.save(userMapper.map(createUserDto));
        return ResponseEntity.ok(userMapper.map(user));
    }
    @PostMapping("/user/auth")
    public ResponseEntity<?> auth(@RequestBody UserAuthDto userAuthDto){
        Optional<User> byEmail = userService.findByAuthEmail(userAuthDto);
        User user = byEmail.get();
        if(byEmail.isPresent()){
            if(passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())){
                return ResponseEntity.ok(UserAuthResponseDto.builder()
                        .token(jwtTokenUtil.generateToken(user.getEmail()))
                        .user(userMapper.map(user))
                        .build());

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
