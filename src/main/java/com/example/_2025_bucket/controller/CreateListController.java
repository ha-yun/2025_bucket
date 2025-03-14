package com.example._2025_bucket.controller;

import com.example._2025_bucket.dto.CategoryDto;
import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.form.TodoForm;
import com.example._2025_bucket.service.CategoryService;
import com.example._2025_bucket.service.TodoService;
import com.example._2025_bucket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// 게시글 작성, 수정

@Controller
public class CreateListController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    private static final String URL = "C:/uploads/images/";

    // 폼 페이지를 반환
    @GetMapping("/create")
    public String showCreateForm(TodoForm todoForm,
                                 Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "create-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(
            @Valid TodoForm todoForm,
            BindingResult bindingResult,
            Authentication authentication) {
        if(bindingResult.hasErrors()) {
            return "create-form";
        }
        try {
            MultipartFile bucketImage = todoForm.getFile();
            String fileName;
            if (!bucketImage.isEmpty()) {
                // 외부 디렉토리에 저장 경로 설정 및 중복제거
                fileName = System.currentTimeMillis() + "_" + bucketImage.getOriginalFilename();
                Path path = Paths.get(URL);

                // 실제 저장될 파일경로
                System.out.println(path);

                // 저장 디렉토리 체크
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                Path filePath = path.resolve(fileName);
                bucketImage.transferTo(filePath.toFile());
            }
            else {
                fileName = "none.jpg";
            }
            System.out.printf(authentication.getName());
            System.out.printf(String.valueOf(userService.getUserByEmail(authentication.getName()).getId()));
            // 저장된 경로를 DTO에 설정
            TodoDto todoDto = TodoDto.builder()
                    .uploadAt(LocalDateTime.now())
                    .checkComplete(todoForm.isCheck_complete()) // 체크박스 값 처리
                    .imagePath("/images/" + fileName)
                    .content(todoForm.getContent())
                    .goalDay(todoForm.getGoal_day())
                    .categoryDto(categoryService.getCategoryById(todoForm.getCategory()))
                    .userDto(userService.getUserByEmail(authentication.getName()))
                    .build();
            System.out.println(todoDto.toString());
            this.todoService.save(todoDto);

        } catch (Exception e) {
            // 파일 입력 실패시 로직
        }
        return "redirect:/list"; // 저장 후 리스트 페이지로 리다이렉트
    }
}
