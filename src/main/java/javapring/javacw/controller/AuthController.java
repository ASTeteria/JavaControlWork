package javapring.javacw.controller;


import jakarta.validation.Valid;
import javapring.javacw.dto.AuthenticationDto;
import javapring.javacw.dto.JwtResponseDto;
import javapring.javacw.dto.UserDto;
import javapring.javacw.service.CustomUserDetailsService;
import javapring.javacw.service.UserService;
import javapring.javacw.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid AuthenticationDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password())
        );

        UserDetails user = customUserDetailsService.loadUserByUsername(authDto.email());
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return ResponseEntity.ok(new JwtResponseDto(accessToken, refreshToken));
    }
}
