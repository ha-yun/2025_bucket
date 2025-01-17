package com.example._2025_bucket.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class TodoForm {
    @NotEmpty(message = "내용을 입력하세요")
    private String content;
    @NotNull(message = "날짜를 선택하세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate goal_day;
    @NotNull(message = "카테고리를 선택하세요")
    private int category;
    private MultipartFile file;

    private boolean check_complete;


}
