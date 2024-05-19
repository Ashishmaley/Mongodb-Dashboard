package com.flipr.mongo_db_admin_panel.modles;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collection;

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
    private String role;
    private boolean enable;
    private String verificationCode;
}

