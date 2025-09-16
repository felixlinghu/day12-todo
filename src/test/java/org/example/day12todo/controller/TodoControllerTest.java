package org.example.day12todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  @Test
  void should_return_empty_response_when_index_with_no_any_todo() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get("/todos").contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(requestBuilder).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }
}