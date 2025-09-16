package org.example.day12todo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.day12todo.entity.Todo;
import org.example.day12todo.exception.InvalidContextException;
import org.example.day12todo.exception.InvalidIdException;
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
    if (todo.getText() == null || todo.getText().trim().isEmpty()) {
      throw new InvalidContextException("Todo text cannot be empty");
    }
    return todoRepository.save(todo);
  }

  public Todo update(String id, Todo updateTodo) throws InvalidIdException, InvalidContextException {
    if (updateTodo.getText() == null || updateTodo.getText().trim().isEmpty()) {
      throw new InvalidContextException("text is invalid");
    }
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo == null) {
      throw new InvalidIdException("ID is invalid");
    }
    updateTodo.setId(id);
    return todoRepository.save(updateTodo);
  }

  public void delete(String id) throws InvalidIdException {
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo == null) {
      throw new InvalidIdException("ID is invalid");
    }
    todoRepository.deleteById(id);
  }
}