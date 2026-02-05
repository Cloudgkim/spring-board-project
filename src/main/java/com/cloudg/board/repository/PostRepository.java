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

    // 전체 게시글 + 페이징 + 정렬 + 카테고리 찾기
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    // 제목/내용 검색 + 페이징 + 정렬 + 카테고리 찾기
    Page<Post> findByTitleContainingAndCategory(String title, Category category, Pageable pageable);
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);






    // 전체 게시글 + 페이징 + 정렬 + 카테고리 찾기
    Page<Post> findByDeleteDateIsNull(Pageable pageable);
    Page<Post> findByCategoryAndDeleteDateIsNull(Category category, Pageable pageable);


    // 제목/내용 검색 + 페이징 + 정렬 + 카테고리 찾기
    Page<Post> findByDeleteDateIsNullAndTitleContainingOrDeleteDateIsNullAndContentContaining(
            String title, String content, Pageable pageable
    );
    Page<Post> findByTitleContainingAndCategoryAndDeleteDateIsNull(
            String title, Category category, Pageable pageable
    );


}

