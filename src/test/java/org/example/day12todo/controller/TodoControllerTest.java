package org.example.day12todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.day12todo.entity.Todo;
import org.example.day12todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TodoRepository todoRepository;

  @Test
  void should_return_empty_response_when_index_with_no_any_todo() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/todos").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }
  @Test
  void should_return_one_response_when_index_is_valid() throws Exception {
    Todo todo=new Todo(null,"Buy m11k",false);
    todoRepository.save(todo);

    MockHttpServletRequestBuilder requestBuilder = get("/todos").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].text").value("Buy m11k"))
        .andExpect(jsonPath("$[0].done").value(false));
  }
}