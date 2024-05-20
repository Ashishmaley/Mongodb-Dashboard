package com.flipr.mongo_db_admin_panel.repositories;

import com.flipr.mongo_db_admin_panel.models.Database;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseRepository extends MongoRepository<Database, String> {
}

