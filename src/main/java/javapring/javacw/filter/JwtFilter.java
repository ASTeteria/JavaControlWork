package javapring.javacw.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import javapring.javacw.service.CustomUserDetailsService;
//import javapring.javacw.util.JwtUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService customUserDetailsService;
//    private final HandlerExceptionResolver exceptionResolver;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        // Перевіряємо, чи є заголовок авторизації та чи починається він з "Bearer "
//        if (StringUtils.isBlank(authHeaderValue) || !authHeaderValue.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authHeaderValue.substring(7); // Видаляємо "Bearer "
//
//        try {
//            if (jwtUtil.isTokenExpired(token)) {
//                log.warn("Expired JWT token");
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String username = jwtUtil.extractUsername(token);
//            if (StringUtils.isBlank(username)) {
//                log.warn("Invalid JWT token: missing username");
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            // Завантажуємо користувача за username (email)
//            UserDetails userDetails;
//            try {
//                userDetails = customUserDetailsService.loadUserByUsername(username);
//            } catch (UsernameNotFoundException e) {
//                log.warn("User not found for token: {}", username);
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            // **Перевіряємо, чи користувач має ролі**
//            if (userDetails.getAuthorities() == null || userDetails.getAuthorities().isEmpty()) {
//                log.warn("Access Denied: No roles assigned to user {}", username);
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            // Встановлюємо користувача в `SecurityContext`
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } catch (Exception e) {
//            log.error("JWT authentication failed: {}", e.getMessage());
//            exceptionResolver.resolveException(request, response, null, e);
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javapring.javacw.service.CustomUserDetailsService;
import javapring.javacw.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authHeaderValue) || !authHeaderValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeaderValue.substring(7);

        try {
            if (jwtUtil.isTokenExpired(token)) {
                log.warn("Expired JWT token");
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtil.extractUsername(token);
            if (StringUtils.isBlank(username)) {
                log.warn("Invalid JWT token: missing username");
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails;
            try {
                userDetails = customUserDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                log.warn("User not found for token: {}", username);
                filterChain.doFilter(request, response);
                return;
            }

            if (userDetails.getAuthorities() == null || userDetails.getAuthorities().isEmpty()) {
                log.warn("Access Denied: No roles assigned to user {}", username);
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.error("JWT authentication failed: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
