package com.example._2025_bucket.dto;

import com.example._2025_bucket.entity.Review;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private long id;
    private boolean check_complete;
    private String content;
    private LocalDate goal_day;
    private LocalDateTime create_at;
    private LocalDateTime modified_at;
    private User user;
    private List<Review> reviews;
    private byte[] bucketImage; // bucketImage 필드 추가

    public Todo toEntity(){
        return Todo.builder()
                .id(this.id)
                .check_complete(this.check_complete)
                .content(this.content)
                .goal_day(this.goal_day)
                .create_at(this.create_at)
                .modified_at(this.modified_at)
                .user(this.user)
                .reviews(this.reviews)
                .bucketImage(this.bucketImage) // bucketImage 포함
                .build();
    }
}