package com.example.product_category_service.service;

import com.example.product_category_service.dto.CreateUserDto;
import com.example.product_category_service.dto.UserAuthDto;
import com.example.product_category_service.mapper.UserMapper;
import com.example.product_category_service.model.Role;
import com.example.product_category_service.model.User;
import com.example.product_category_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;


    @Test
    void findByEmail() {

        CreateUserDto createUserDto = CreateUserDto.builder()
                .name("aram")
                .surname("aramyan")
                .email("aram@mail.com")
                .password("aram")
                .build();

        Optional<User> optionalUser = Optional.of(userMapper.map(createUserDto));
        when(userRepository.findByEmail(createUserDto.getEmail())).thenReturn(optionalUser);

        Optional<User> byEmail = userService.findByEmail(createUserDto);
        assertEquals(byEmail, optionalUser);
        verify(userRepository, times(1)).findByEmail(createUserDto.getEmail());
    }

    @Test
    void save() {
        User user = User.builder()
                .name("aram")
                .surname("aramyan")
                .email("aram@mail.com")
                .password("aram")
                .role(Role.ADMIN)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findByAuthEmail() {
        User user = User.builder()
                .name("aram")
                .surname("aramyan")
                .email("aram@mail.com")
                .password("aram")
                .role(Role.ADMIN)
                .build();

        UserAuthDto userAuthDto = UserAuthDto.builder()
                .email("aram@mail.com")
                .password("aram")
                .build();

        when(userRepository.findByEmail(userAuthDto.getEmail())).thenReturn(Optional.of(user));

        userService.findByAuthEmail(userAuthDto);
        verify(userRepository, times(1)).findByEmail(any());

    }
}