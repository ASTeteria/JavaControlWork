package javapring.javacw.controller;
//
//
//import jakarta.validation.Valid;
//import javapring.javacw.dto.UserDto;
//import javapring.javacw.service.UserService;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping
//    @Secured("ROLE_ADMIN") // Тільки для ADMIN
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    @GetMapping("/{id}")
//    @Secured({"ROLE_ADMIN", "ROLE_USER"}) // Доступ для ADMIN і USER
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
//
//    @PutMapping("/update")
//    @Secured({"ROLE_ADMIN", "ROLE_USER"}) // Тільки користувачі можуть оновлювати свої дані
//    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, Authentication authentication) {
//        return ResponseEntity.ok(userService.updateUser(userDto, authentication));
//    }
//
//    @DeleteMapping("/delete")
//    @Secured({"ROLE_ADMIN", "ROLE_USER"}) // Тільки користувач може видалити свій профіль
//    public ResponseEntity<Void> deleteUser(Authentication authentication) {
//        userService.deleteUser(authentication);
//        return ResponseEntity.noContent().build();
//    }
//}

import jakarta.validation.Valid;
import javapring.javacw.dto.UserDto;
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

    @GetMapping
    @Secured("ROLE_ADMIN") // Доступ лише для адміністратора
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"}) // Доступ для адміна і самого юзера
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update")
    @Secured("ROLE_USER") // Оновлювати може тільки сам користувач
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(userDto, authentication));
    }

    @DeleteMapping("/delete")
    @Secured("ROLE_USER") // Видалити можна лише свій акаунт
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.deleteUser(authentication);
        return ResponseEntity.noContent().build();
    }
}
