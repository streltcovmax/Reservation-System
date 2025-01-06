package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "AdminData")
public class AdminData {
    @Id
    private String name;
    private String password;
    private Role role;
}
