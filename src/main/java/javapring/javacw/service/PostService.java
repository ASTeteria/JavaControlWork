package javapring.javacw.service;
//
//
//import javapring.javacw.dto.PostDto;
//import javapring.javacw.entity.Post;
//import javapring.javacw.entity.User;
//import javapring.javacw.repository.PostRepository;
//import javapring.javacw.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class PostService {
//
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    public List<PostDto> getUserPosts(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        return postRepository.findByUser(user)
//                .stream()
//                .map(post -> new PostDto(
//                        post.getId(),
//                        post.getText(),
//                        post.getCreatedAt(),
//                        post.getUpdatedAt(),
//                        post.getUser().getId()
//                ))
//                .toList();
//    }
//
//    public PostDto createPost(PostDto postDto, Authentication authentication) {
//        User user = userRepository.findByEmail(authentication.getName())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        Post post = Post.builder()
//                .text(postDto.text())
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .user(user)
//                .build();
//
//        post = postRepository.save(post);
//        return new PostDto(post.getId(), post.getText(), post.getCreatedAt(), post.getUpdatedAt(), user.getId());
//    }
//
//    public PostDto updatePost(Long postId, PostDto postDto, Authentication authentication) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
//
//        if (!post.getUser().getEmail().equals(authentication.getName())) {
//            throw new IllegalStateException("You can only update your own posts");
//        }
//
//        post.setText(postDto.text());
//        post.setUpdatedAt(LocalDateTime.now());
//        post = postRepository.save(post);
//
//        return new PostDto(post.getId(), post.getText(), post.getCreatedAt(), post.getUpdatedAt(), post.getUser().getId());
//    }
//
//    public void deletePost(Long postId, Authentication authentication) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
//
//        if (!post.getUser().getEmail().equals(authentication.getName())) {
//            throw new IllegalStateException("You can only delete your own posts");
//        }
//
//        postRepository.delete(post);
//    }
//}


import javapring.javacw.dto.PostDto;
import javapring.javacw.entity.Post;
import javapring.javacw.entity.User;
import javapring.javacw.mapper.PostMapper;
import javapring.javacw.repository.PostRepository;
import javapring.javacw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toPostDto)
                .toList();
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return postMapper.toPostDto(post);
    }

    public PostDto createPost(PostDto postDto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postMapper.toPost(postDto);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        post = postRepository.save(post);
        return postMapper.toPostDto(post);
    }

    public PostDto updatePost(Long id, PostDto postDto, Authentication authentication) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getUser().getEmail().equals(authentication.getName())) {
            throw new IllegalArgumentException("Access Denied: You can update only your posts");
        }

        post.setText(postDto.text());
        post.setUpdatedAt(LocalDateTime.now());

        post = postRepository.save(post);
        return postMapper.toPostDto(post);
    }

    public void deletePost(Long id, Authentication authentication) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getUser().getEmail().equals(authentication.getName())) {
            throw new IllegalArgumentException("Access Denied: You can delete only your posts");
        }

        postRepository.delete(post);
    }
}
