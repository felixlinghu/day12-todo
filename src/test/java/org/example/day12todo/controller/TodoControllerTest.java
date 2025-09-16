package org.example.day12todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.day12todo.entity.Todo;
import org.example.day12todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  public void setUp() {
    todoRepository.deleteAll();
  }

  @Test
  void should_return_empty_response_when_index_with_no_any_todo() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/todos").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void should_return_one_response_when_index_is_valid() throws Exception {
//    Todo todo=new Todo(null,"Buy m11k",false);
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getTodo()));
    MockHttpServletRequestBuilder requestBuilder = get("/todos").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].text").value("Buy m11k"))
        .andExpect(jsonPath("$[0].done").value(false));
  }


  @Test
  void should_return_one_todo_when_create_one_todo() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getTodo())).andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void should_return_422_error_when_create_invalid_todo() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getErrorTodo())).andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_return_one_todo_with_id_when_create_one_todo_with_id() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId())).andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("1234567"));
  }


  @Test
  void should_return_422_error_when_text_is_null() throws Exception {
    String todo =
        """
            {
            
            "done":false
            }
            """;
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(todo)).andExpect(status().isUnprocessableEntity())
    ;
  }

  //todo
  @Test
  void should_return_one_todo_with_id_when_create_one_todo_with_error_id() throws Exception {
    String todo =
        """
            {
            "id":"client-sent",
            "text":"Buy m11k",
            "done":false
            }
            """;
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(todo)).andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("client-sent"));
  }

  @Test
  void should_update_one_todo_when_update_one_todo() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));

    mockMvc.perform(put("/todos/1234567").contentType(MediaType.APPLICATION_JSON).content(getTodo())).andExpect(status().isOk())
        .andExpect(jsonPath("$.done").value(false));
  }


  private static String getTodo() {
    return """
        {
        "text":"Buy m11k",
        "done":false
        }
        """;
  }

  private static String getErrorTodo() {
    return """
        {
        "text":"",
        "done":false
        }
        """;
  }

  private static String getToDoWithId() {
    return """
        {
        "id":"1234567",
        "text":"Buy m11k",
        "done":true
        }
        """;
  }

}