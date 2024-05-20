package com.flipr.mongo_db_admin_panel.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "databases")
public class Database {

    @Id
    private String id;
    private String name;
    private String instanceId;
}

