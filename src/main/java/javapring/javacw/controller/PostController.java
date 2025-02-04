package javapring.javacw.controller;
//
//import jakarta.validation.Valid;
//import javapring.javacw.dto.PostDto;
//import javapring.javacw.service.PostService;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/posts")
//@RequiredArgsConstructor
//public class PostController {
//
//    private final PostService postService;
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable Long userId) {
//        return ResponseEntity.ok(postService.getUserPosts(userId));
//    }
//
//    @PostMapping
//    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto, Authentication authentication) {
//        return ResponseEntity.ok(postService.createPost(postDto, authentication));
//    }
//
//    @PutMapping("/{postId}")
//    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody @Valid PostDto postDto, Authentication authentication) {
//        return ResponseEntity.ok(postService.updatePost(postId, postDto, authentication));
//    }
//
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Authentication authentication) {
//        postService.deletePost(postId, authentication);
//        return ResponseEntity.noContent().build();
//    }
//}
//
//import jakarta.validation.Valid;
//import javapring.javacw.dto.PostDto;
//import javapring.javacw.service.PostService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/posts")
//@RequiredArgsConstructor
//public class PostController {
//
//    private final PostService postService;
//
//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts() {
//        return ResponseEntity.ok(postService.getAllPosts());
//    }
//
//    @PostMapping
//    @Secured("ROLE_USER")
//    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto, Authentication authentication) {
//        return ResponseEntity.ok(postService.createPost(postDto, authentication));
//    }
//
//    @PutMapping("/{id}")
//    @Secured("ROLE_USER")
//    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody @Valid PostDto postDto, Authentication authentication) {
//        return ResponseEntity.ok(postService.updatePost(id, postDto, authentication));
//    }
//
//    @DeleteMapping("/{id}")
//    @Secured("ROLE_USER")
//    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
//        postService.deletePost(id, authentication);
//        return ResponseEntity.noContent().build();
//    }
//}


import jakarta.validation.Valid;
import javapring.javacw.dto.PostDto;
import javapring.javacw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto, Authentication authentication) {
        return ResponseEntity.ok(postService.createPost(postDto, authentication));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody @Valid PostDto postDto, Authentication authentication) {
        return ResponseEntity.ok(postService.updatePost(id, postDto, authentication));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        postService.deletePost(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
