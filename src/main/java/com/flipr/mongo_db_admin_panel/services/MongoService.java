package com.flipr.mongo_db_admin_panel.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoService {

    private final MongoClient mongoClient;

    public MongoService(@Value("${spring.data.mongodb.uri}") String connectionString) {
        this.mongoClient = MongoClients.create(connectionString);
    }

    public List<String> getDatabaseNames() {
        List<String> databaseNames = new ArrayList<>();
        mongoClient.listDatabaseNames().forEach(databaseNames::add);
        return databaseNames;
    }

    public MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    public List<Document> getCollections(String dbName) {
        List<Document> collections = new ArrayList<>();
        getDatabase(dbName).listCollections().forEach(collections::add);
        return collections;
    }

    public Document getServerStatus() {
        return getDatabase("admin").runCommand(new Document("serverStatus", 1));
    }

    public boolean createDatabase(String dbName) {
        try {
            getDatabase(dbName).createCollection("temp");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createUser(String username, String password, String databaseName, String role) {
        try {
            MongoDatabase adminDb = getDatabase("admin");
                MongoDatabase adminDatabase = mongoClient.getDatabase("admin");
                Document command = new Document("createUser", username)
                        .append("pwd", password)
                        .append("roles", List.of(new Document("role", role).append("db", databaseName)));
            adminDb.runCommand(command);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
