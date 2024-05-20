package com.flipr.mongo_db_admin_panel.repositories;

import com.flipr.mongo_db_admin_panel.models.MongoDBInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBInstanceRepository extends MongoRepository<MongoDBInstance, String> {

}

