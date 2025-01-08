package com.example._2025_bucket.service;

import com.example._2025_bucket.dto.TodoDto;
import com.example._2025_bucket.entity.Todo;
import com.example._2025_bucket.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoDto getTodo(long id) throws Exception{
        Optional<Todo> ot = todoRepository.findById(id);
        if(ot.isPresent()){
            Todo todo = ot.get();
            return TodoDto.builder()
                    .id(todo.getId())
                    .check_complete(todo.isCheck_complete())
                    .user(todo.getUser())
                    .reviews(todo.getReviews())
                    .content(todo.getContent())
                    .create_at(todo.getCreate_at())
                    .modified_at(todo.getModified_at())
                    .goal_day(todo.getGoal_day())
                    .image_path(todo.getImage_path())
                    .category(todo.getCategory())
                    .build();
        }
        else {
            System.out.println("해당 TODO 없음");
            throw new NoSuchElementException("해당 Todo없음");
        }
    }

    public List<TodoDto> getAllTodos() {
        List<Todo> lts = this.todoRepository.findAll();
        List<TodoDto> ldts = new ArrayList<>();

        for (Todo td : lts) {
            ldts.add(TodoDto.builder()
                            .image_path(td.getImage_path())
                            .user(td.getUser())
                            .id(td.getId())
                            .content(td.getContent())
                            .reviews(td.getReviews())
                            .check_complete(td.isCheck_complete())
                            .create_at(td.getCreate_at())
                            .modified_at(td.getModified_at())
                            .goal_day(td.getGoal_day())
                            .category(td.getCategory())
                    .build());
        }
        return ldts;
    }

    public void update(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

    public void save(TodoDto todoDto) {
        this.todoRepository.save(todoDto.toEntity());
    }

    public void remove(TodoDto todoDto) {
        this.todoRepository.delete(todoDto.toEntity());
    }
}