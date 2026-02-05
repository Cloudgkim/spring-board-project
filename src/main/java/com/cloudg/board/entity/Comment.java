package com.cloudg.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor : 매개변수가 없는 기본 생성자를 만들어줌. JPA에서 엔티티에 필수!
//@AllArgsConstructor : 클래스의 모든 필드를 매개변수로 받는 생성자를 만들어줌.
//@RequiredArgsConstructor : final이나 @NonNull이 붙은 필드만 매개변수로 받는 생성자를 만들어줌.
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void delete() {
        this.deleteDate = LocalDateTime.now();
    }

}
