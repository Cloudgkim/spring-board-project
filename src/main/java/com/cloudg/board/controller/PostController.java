package com.cloudg.board.controller;

import com.cloudg.board.entity.Comment;
import com.cloudg.board.entity.Post;
import com.cloudg.board.entity.User;
import com.cloudg.board.repository.PostRepository;
import com.cloudg.board.service.CommentService;
import com.cloudg.board.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostRepository postRepository,
                          PostService postService,
                          CommentService commentService) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/page")
    public String page(Model model) {
        model.addAttribute("content", "test/page");
        return "layout/layout";
    }

    // 게시글 목록 화면 (DB에서 조회)
    @GetMapping("/")
    public String boards(@RequestParam(defaultValue = "0") int page, // 페이지 번호
                         @RequestParam(defaultValue = "10") int size, // 한 페이지에 보여줄 게시글 수
                         @RequestParam(required = false) String keyword, // 검색어
                         @RequestParam(defaultValue = "new") String sort, // 정렬 최신순(default)
                         @SessionAttribute(name = "loginUser", required = false) User loginUser,
                         Model model) {

        Page<Post> posts;
        if (keyword != null && !keyword.isEmpty()) { // 키워드가 있을 떄
            posts = postService.searchByTitle(keyword, page, size, sort);
            model.addAttribute("keyword", keyword); // 뷰에서 검색어 유지
        } else { // 키워드가 없을 때, 기본 조회
            posts = postService.getPosts(page, size, sort);
            model.addAttribute("keyword", ""); // null 대신 빈 문자열
        }

        model.addAttribute("posts", posts.getContent()); // 실제 게시글 리스트
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("sort", sort); // 뷰에서 현재 정렬 상태 표시

        // 페이지 번호 배열 만들기
        int totalPages = posts.getTotalPages();
        int[] pageNumbers = new int[totalPages];
        for (int i = 0; i < totalPages; i++) {
            pageNumbers[i] = i;
        }
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("content", "boards");
        model.addAttribute("title", "게시판");
        return "layout/layout";
    }

    // 게시글 상세 조회 화면
    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {

        // 게시글 상세 조회 시 -> 조회수 +1 증가
        Post post = postService.getBoardDetail(id);

        // comment를 list에 담아줌
        List<Comment> comments = commentService.findByPost(post);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("content", "boardDetail");
        model.addAttribute("title", "게시글 상세");
        return "layout/layout";
    }

    // 새 게시글 작성 화면
    @GetMapping("/board/new")
    public String boardNew(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                           Model model) {

        if (loginUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("post", new Post());
        model.addAttribute("content", "boardNew");
        model.addAttribute("title", "게시글 작성");
        return "layout/layout";
    }

    // 새 게시글 저장
    @PostMapping("/board/insert")
    public String boardInsert(@Valid Post post,
                              BindingResult bindingResult,
                              @SessionAttribute(name = "loginUser", required = false) User loginUser) {

        if (loginUser == null) {
            return "redirect:/login";
        }

        if(bindingResult.hasErrors()) {
            return "boardNew";
        }

        post.setUser(loginUser);
        post.setCreateDate(LocalDateTime.now());
        post.setUpdateDate(LocalDateTime.now());
        postRepository.save(post);
        return "redirect:/";
    }

    // 게시글 수정 화면
    @GetMapping("/board/{id}/edit")
    public String boardEdit(@PathVariable Long id,
                            @SessionAttribute(name = "loginUser", required = false) User loginUser,
                            Model model) {

        if (loginUser == null) {
            return "redirect:/login";
        }

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        model.addAttribute("post", post);
        model.addAttribute("content", "boardEdit");
        model.addAttribute("title", "게시글 수정");

        return "layout/layout";
    }

    // 게시글 수정
    @PostMapping("/board/{id}/edit")
    public String boardUpdate(@PathVariable Long id,
                           @Valid @ModelAttribute("post") Post post,
                           BindingResult bindingResult,
                           @SessionAttribute(name = "loginUser", required = false) User loginUser) {

        if (bindingResult.hasErrors()) {
            return "boardEdit";
        }

        postService.update(id, post, loginUser);

        return "redirect:/board/" + id;
    }
    // 게시글 삭제
    @PostMapping("/board/{id}/delete")
    public String boardDelete(@PathVariable Long id,
                              @SessionAttribute(name = "loginUser", required = false) User loginUser) {

        if (loginUser == null) {
            return "redirect:/login";
        }

        // postRepository.deleteById(id);
        postService.delete(id, loginUser);
        return "redirect:/";
    }



}
