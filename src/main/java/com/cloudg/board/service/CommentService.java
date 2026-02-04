package com.cloudg.board.service;

import com.cloudg.board.entity.Comment;
import com.cloudg.board.entity.Post;
import com.cloudg.board.entity.User;
import com.cloudg.board.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 작성
    public void save(String content, User user, Post post) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);
    }

    // 게시글별 댓글 조회
    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPostOrderByCreateDateAsc(post);
    }
}
