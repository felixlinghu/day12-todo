package org.example.day12todo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {
  @Autowired
  private TodoRepository todoRepository;
  @GetMapping
  public List<Todo> index() {
    return todoRepository.findAll();
  }
}
