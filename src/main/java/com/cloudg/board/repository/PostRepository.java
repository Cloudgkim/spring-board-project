package com.cloudg.board.repository;

import com.cloudg.board.entity.Category;
import com.cloudg.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //findAll()
    //findById()
    //save()
    //deleteById()

    // 페이징 조회
    Page<Post> findAll(Pageable pageable);

    // 제목 + 내용 검색
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByTitleContainingAndCategory(String title, Category category, Pageable pageable);


}

