package javapring.javacw.repository;
//
//import javapring.javacw.entity.Post;
//import javapring.javacw.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//@Repository
//public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findByUser(User user);
//}

import javapring.javacw.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

