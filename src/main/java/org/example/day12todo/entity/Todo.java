package org.example.day12todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

  @Id
  private String id;
  private String text;
  private boolean done;

  public static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @PrePersist
  public void ensureId() {
    if (this.id == null) {
      this.id = java.util.UUID.randomUUID().toString();
    }
    if (!isInteger(this.id)) {
      this.id = java.util.UUID.randomUUID().toString();
    }
  }
}
