package javapring.javacw.service;

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
