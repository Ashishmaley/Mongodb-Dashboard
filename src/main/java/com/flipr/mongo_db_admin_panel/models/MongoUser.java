package com.flipr.mongo_db_admin_panel.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MongoUser {
    private String username;
    private String password; // Hashed password
    private String database;
    private String role;
    private String firstName;
    private String lastName;
}