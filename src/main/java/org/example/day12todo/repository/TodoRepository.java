package org.example.day12todo.repository;

import org.example.day12todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, String> {

}
