package com.cloudg.board.repository;

import com.cloudg.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //findAll()
    //findById()
    //save()
    //deleteById()
    Optional<User> findByUsername(String username); // username 중복 체크용
    boolean existsByUsername(String username); //
    boolean existsByEmail(String email);
}

