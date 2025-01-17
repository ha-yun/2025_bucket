package com.example._2025_bucket.dto;


import com.example._2025_bucket.entity.User;


import lombok.*;

/**
 * - User 엔티티와 데이터 교환
 *  - 디비용도, 통신용도(로그인, 회원가입등 전달데이터 받는구조)
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private String confirmPassword;
    private String nickname;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setNickname(nickname);
        return user;

    }
}