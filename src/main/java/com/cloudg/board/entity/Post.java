package com.cloudg.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목을 입력하세요.")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 조회수 필드 추가
    @Column(name = "view_count")
    private int viewCount = 0; // 기본값 0

    // ✅ 카테고리 FK (Category.code 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Post 테이블의 FK 컬럼
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;


}
