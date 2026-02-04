package com.cloudg.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor : 매개변수가 없는 기본 생성자를 만들어줌. JPA에서 엔티티에 필수!
//@AllArgsConstructor : 클래스의 모든 필드를 매개변수로 받는 생성자를 만들어줌.
//@RequiredArgsConstructor : final이나 @NonNull이 붙은 필드만 매개변수로 받는 생성자를 만들어줌.
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 필수입니다.")
    @Column(name = "user_name", nullable = false, unique = true, length = 200)
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Column(nullable = false, unique = true, length = 100)
    private String password;

    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
