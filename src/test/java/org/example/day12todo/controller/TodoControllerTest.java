package org.example.day12todo.controller;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        .andExpect(jsonPath("$.id").value(not("client-sent")));
  }

  @Test
  void should_update_one_todo_when_update_one_todo() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));

    mockMvc.perform(put("/todos/1234567").contentType(MediaType.APPLICATION_JSON).content(getTodo())).andExpect(status().isOk())
        .andExpect(jsonPath("$.done").value(false));
  }

  @Test
  void should_update_one_todo_when_update_one_todo_with_incorrect_id() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));
    String todo =
        """
            {
            "id":"123",
            "text":"Felix Linghu",
            "done":false
            }
            """;

    mockMvc.perform(put("/todos/1234567").contentType(MediaType.APPLICATION_JSON).content(todo)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1234567"))
        .andExpect(jsonPath("$.text").value("Felix Linghu"));
  }

  @Test
  void should_return_404_when_update_with_non_existent_id() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));

    mockMvc.perform(put("/todos/999").contentType(MediaType.APPLICATION_JSON).content(getTodo())).andExpect(status().isNotFound());

  }

  @Test
  void should_return_422_when_update_with_todo_is_empty() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));
    String todo =
        """
            {
            }
            """;

    mockMvc.perform(put("/todos/1234567").contentType(MediaType.APPLICATION_JSON).content(todo)).andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_delete_one_todo_when_delete_one_todo() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));

    mockMvc.perform(delete("/todos/1234567")).andExpect(status().isNoContent());
    mockMvc.perform(get("/todos")).andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void should_return_404_when_delete_with_non_existent_id() throws Exception {
    mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(getToDoWithId()));

    mockMvc.perform(delete("/todos/999")).andExpect(status().isNotFound());
    mockMvc.perform(get("/todos")).andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void should_allow_core() throws Exception {
    MockHttpServletRequestBuilder request = options("/todos")
        .header("Access-Control-Request-Method", "POST", "GET", "DELETE", "PUT")
        .header("Origin", "http://localhost:3000");

    mockMvc.perform(request).andExpect(status().isOk());
  }


}