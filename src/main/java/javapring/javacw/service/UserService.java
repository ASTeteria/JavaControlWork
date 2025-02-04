package javapring.javacw.service;
//
//
//import javapring.javacw.dto.UserDto;
//import javapring.javacw.entity.User;
//import javapring.javacw.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserDto register(UserDto userDto) {
//        Optional<User> existingUser = userRepository.findByEmail(userDto.email());
//        if (existingUser.isPresent()) {
//            throw new IllegalArgumentException("Email is already in use");
//        }
//
//        // Якщо ролі не передані - призначаємо роль USER
//        Set<String> roles = userDto.roles() != null ? userDto.roles() : Set.of("USER");
//        User user = User.builder()
//                .email(userDto.email())
//                .password(passwordEncoder.encode(userDto.password()))
//                .roles(roles)
//                .build();
//
//        user = userRepository.save(user);
//        return new UserDto(user.getId(), user.getEmail(), null, user.getRoles()); // Передаємо всі аргументи
//    }
//
//    public List<UserDto> getAllUsers() {
//        return userRepository.findAll()
//                .stream()
//                .map(user -> new UserDto(user.getId(), user.getEmail(), null, user.getRoles()))
//                .toList();
//    }
//
//    public UserDto getUserById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        return new UserDto(user.getId(), user.getEmail(), null, user.getRoles());
//    }
//
//    public UserDto getUserByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        return new UserDto(user.getId(), user.getEmail(), null, user.getRoles());
//    }
//
//    public UserDto updateUser(UserDto userDto, org.springframework.security.core.Authentication authentication) {
//        User user = userRepository.findByEmail(authentication.getName())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        if (userDto.email() != null && !userDto.email().isBlank()) {
//            user.setEmail(userDto.email());
//        }
//        if (userDto.password() != null && !userDto.password().isBlank()) {
//            user.setPassword(passwordEncoder.encode(userDto.password()));
//        }
//
//        userRepository.save(user);
//        return new UserDto(user.getId(), user.getEmail(), null, user.getRoles());
//    }
//
//    public void deleteUser(org.springframework.security.core.Authentication authentication) {
//        User user = userRepository.findByEmail(authentication.getName())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        userRepository.delete(user);
//    }
//}

import javapring.javacw.dto.UserDto;
import javapring.javacw.entity.User;
import javapring.javacw.mapper.UserMapper;
import javapring.javacw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto register(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.password())); // Хешуємо пароль
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userMapper.toUserDto(user);
    }

    public UserDto updateUser(UserDto userDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userDto.email() != null && !userDto.email().isBlank()) {
            user.setEmail(userDto.email());
        }
        if (userDto.password() != null && !userDto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.password()));
        }

        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    public void deleteUser(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }
}
