package com.flipr.mongo_db_admin_panel.modles;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "instances")
public class MongoDBInstance {

    @Id
    private String id;
    private String host;
    private int port;
    private String username;
    private String password;
}

