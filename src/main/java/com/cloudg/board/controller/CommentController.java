package com.cloudg.board.controller;

import com.cloudg.board.entity.Post;
import com.cloudg.board.entity.User;
import com.cloudg.board.repository.PostRepository;
import com.cloudg.board.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;

    public CommentController(CommentService commentService, PostRepository postRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    // 댓글 작성
    @PostMapping("/board/{postId}/comment")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content,
                             HttpSession Session) {

        // (1) 로그인 체크
        User loginUser = (User) Session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // (2) 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        // (3) 댓글 저장
        commentService.save(content, loginUser, post);

        // (4) 다시 게시글 상세 페이지로
        return "redirect:/board/" + postId;

    }

}
