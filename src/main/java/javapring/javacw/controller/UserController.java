package javapring.javacw.controller;

import jakarta.validation.Valid;
import javapring.javacw.dto.UserDto;
import javapring.javacw.entity.User;
import javapring.javacw.mapper.UserMapper;
import javapring.javacw.repository.UserRepository;
import javapring.javacw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    @Secured("ROLE_ADMIN") // лише для адмін
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/filter")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserDto>> filterUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role) {

        List<User> users;

        if (email != null) {
            users = userRepository.findByEmailContainingIgnoreCase(email);
        } else if (role != null) {
            users = userRepository.findByRole(role);
        } else {
            users = userRepository.findAll();
        }

        return ResponseEntity.ok(users.stream().map(userMapper::toUserDto).toList());
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"}) // адмін і юзер
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update")
    @Secured("ROLE_USER") // тільки юзер
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(userDto, authentication));
    }

    @DeleteMapping("/delete")
    @Secured("ROLE_USER") // тільки юзер
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.deleteUser(authentication);
        return ResponseEntity.noContent().build();
    }
}
