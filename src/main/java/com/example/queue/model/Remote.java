package com.example.queue.model;

import com.example.queue.model.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@NoArgsConstructor
public class Remote {

    @Id
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String pass;

    @DBRef
    private Role role;
}
