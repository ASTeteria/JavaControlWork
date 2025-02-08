package javapring.javacw.repository;


import javapring.javacw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // 🔹 Пошук користувачів за частковим збігом email
    List<User> findByEmailContainingIgnoreCase(String email);

    // 🔹 Пошук користувачів за роллю
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findByRole(@Param("role") String role);
}
