package org.example.day12todo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.exception.InvalidContextException;
import org.example.day12todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  public List<Todo> findAll() {
    return todoRepository.findAll();
  }

  public Todo create(Todo todo) throws InvalidContextException {
    if (todo.getText().trim().isEmpty()) {
      throw new InvalidContextException("Todo text cannot be empty");
    }
    return todoRepository.save(todo);
  }
}