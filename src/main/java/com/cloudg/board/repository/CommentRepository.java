package com.cloudg.board.repository;

import com.cloudg.board.entity.Comment;
import com.cloudg.board.entity.Post;
import com.cloudg.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //findAll()
    //findById()
    //save()
    //deleteById()


    List<Comment> findByPostAndDeleteDateIsNull(Post post);
    List<Comment> findByPostAndDeleteDateIsNullOrderByCreateDateAsc(Post post);

}

