package com.cloudg.board.controller;

import com.cloudg.board.entity.User;
import com.cloudg.board.repository.UserRepository;
import com.cloudg.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // ==================== 회원가입 ====================
    @GetMapping("/signup")
    public String singUpForm(Model model) {

        model.addAttribute("content", "signup");
        model.addAttribute("title", "회원가입");
        return "layout/layout";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signUp(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email) {
        userService.registerUser(username, password, email);
        return "redirect:/login"; //회원가입 후 로그인 페이지로 이동
    }

    // ==================== 로그인 ====================
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("content", "login");
        model.addAttribute("title", "로그인");
        return "layout/layout";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session) {

        User user = userService.loginUser(username, password);
        System.out.println("userController user?????????? = " + user);
        if (user == null) {
            return "redirect:/login?error"; // 로그인 실패
        }

        // 로그인 성공 → 세션에 사용자 정보 저장
        session.setAttribute("loginUser", user);
        return "redirect:/"; // 로그인 성공 후 홈으로 이동
    }

    // ==================== 로그아웃 ====================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        return "redirect:/"; // 홈으로 이동
    }


}
