package org.example.day12todo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.exception.InvalidContextException;
import org.example.day12todo.repository.TodoRepository;
import org.example.day12todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
  private final TodoService todoService;
  @GetMapping
  public List<Todo> index() {
    return todoService.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Todo create(@RequestBody Todo todo) throws InvalidContextException {
    return todoService.create(todo);
  }
}
