package com.cloudg.board.service;

import com.cloudg.board.entity.Category;
import com.cloudg.board.entity.Post;
import com.cloudg.board.entity.User;
import com.cloudg.board.repository.CategoryRepository;
import com.cloudg.board.repository.CommentRepository;
import com.cloudg.board.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
    }

    // 전체 게시글 페이징 + 정렬
    public Page<Post> getPosts(int page, int size, String sort, Long categoryId) {
        PageRequest pageRequest = PageRequest.of(page, size,
                sort.equals("old") ? Sort.by("createDate").ascending() : Sort.by("createDate").descending()); // 0부터 시작

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리"));
            return postRepository.findByCategory(category, pageRequest);
        }

        return postRepository.findAll(pageRequest);
    }

    //  제목 검색 페이징 + 정렬
    public Page<Post> searchByTitle(String keyword, int page, int size, String sort, Long categoryId) {
        PageRequest pageRequest = PageRequest.of(page, size,
                sort.equals("old") ? Sort.by("createDate").ascending() : Sort.by("createDate").descending());

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리"));
            return postRepository.findByTitleContainingAndCategory(keyword, category, pageRequest);
        }

        // keyword를 두 번 전달
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageRequest);
    }

    // 게시글 상세 조회 시 -> 조회수 +1 증가
    @Transactional
    public Post getBoardDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        // 조회수 증가
        post.setViewCount(post.getViewCount() + 1);

        // 트랜잭션 안에서 엔티티 상태 변경만 해도 JPA가 자동으로 업데이트
        return post;
    }

    @Transactional
    public void update(Long id, Post post, User loginUser) {

        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Post savedPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!savedPost.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        savedPost.setTitle(post.getTitle());
        savedPost.setCategory(post.getCategory());
        savedPost.setContent(post.getContent());
        savedPost.setUpdateDate(LocalDateTime.now());
    }



    @Transactional
    public void delete(Long postId, User loginUser) {

        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));

        if (!post.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        // 댓글 먼저 삭제
        commentRepository.deleteByPost(post);

        // 그 다음 게시글 삭제
        postRepository.delete(post);
    }


}
