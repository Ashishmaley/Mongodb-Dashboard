package com.flipr.mongo_db_admin_panel;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
@RequiredArgsConstructor
public class MongoDbAdminPanelApplication {
	public static void main(String[] args) {
		SpringApplication.run(MongoDbAdminPanelApplication.class, args);
	}

}
