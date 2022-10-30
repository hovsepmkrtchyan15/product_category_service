package com.example.product_category_service.service;

import com.example.product_category_service.dto.CreateUserDto;
import com.example.product_category_service.dto.UserAuthDto;
import com.example.product_category_service.model.Role;
import com.example.product_category_service.model.User;
import com.example.product_category_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(CreateUserDto createUserDto) {
        return userRepository.findByEmail(createUserDto.getEmail());
    }

    public User save(User map) {
        map.setRole(Role.USER);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        User save = userRepository.save(map);
        return save;
    }

    public Optional<User> findByAuthEmail(UserAuthDto userAuthDto) {
        return userRepository.findByEmail(userAuthDto.getEmail());
    }
}
