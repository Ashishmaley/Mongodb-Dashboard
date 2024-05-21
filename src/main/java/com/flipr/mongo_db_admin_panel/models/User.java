package com.flipr.mongo_db_admin_panel.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String userName;
    private String password;
    private String Uri;
    private boolean enable;
    private String verificationCode;
}

