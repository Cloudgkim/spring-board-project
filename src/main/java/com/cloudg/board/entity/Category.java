package com.cloudg.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // notice, qna, free...

    @NotBlank(message = "아이디는 필수입니다.")
    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String name; // 공지사항, Q&A, 자유게시판

}
