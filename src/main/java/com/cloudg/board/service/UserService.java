package com.cloudg.board.service;

import com.cloudg.board.entity.User;
import com.cloudg.board.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public User registerUser(String username, String password, String email) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, encodedPassword, email);
        return userRepository.save(user);
    }

    // 로그인
    public User loginUser(String username, String password) {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                        new IllegalArgumentException("아이디 또는 비밀번호가 틀립니다.")
                );

        // 🔐 비밀번호 비교 (raw vs encoded)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀립니다.");
        }

        return user; // 로그인 성공
    }

}
