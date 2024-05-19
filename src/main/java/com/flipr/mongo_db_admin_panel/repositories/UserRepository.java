package com.flipr.mongo_db_admin_panel.repositories;

import com.flipr.mongo_db_admin_panel.modles.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByUserName(String email);

    public User findByverificationCode(String code);
}

