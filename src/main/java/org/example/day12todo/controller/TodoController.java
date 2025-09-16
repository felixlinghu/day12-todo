package org.example.day12todo.controller;

import java.util.List;
import org.example.day12todo.entity.Todo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

  @GetMapping
  public List<Todo> index() {
    return List.of();
  }
}
