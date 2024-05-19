package com.flipr.mongo_db_admin_panel.controller;

import com.flipr.mongo_db_admin_panel.services.MongoService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MongoController {

    @Autowired
    private MongoService mongoService;

    @GetMapping("/api/mongo")
    public String viewDashboard(Model model) {
        Document serverStatus = mongoService.getServerStatus();
        model.addAttribute("serverStatus", serverStatus.toJson());

        List<String> databaseNames = mongoService.getDatabaseNames();
        model.addAttribute("databaseNames", databaseNames);

        if (!databaseNames.isEmpty()) {
            String firstDb = databaseNames.get(0);
            List<Document> collections = mongoService.getCollections(firstDb);
            List<String> collectionNames = collections.stream()
                    .map(collection -> collection.getString("name"))
                    .collect(Collectors.toList());
            model.addAttribute("selectedDb", firstDb);
            model.addAttribute("collections", collectionNames);
        }
        return "index";
    }

    @GetMapping("/api/mongo/db/{dbName}/collections")
    public String getCollections(@PathVariable String dbName, Model model) {
        List<Document> collections = mongoService.getCollections(dbName);
        List<String> collectionNames = collections.stream()
                .map(collection -> collection.getString("name"))
                .collect(Collectors.toList());
        model.addAttribute("collections", collectionNames);
        model.addAttribute("selectedDb", dbName);
        return "index :: collectionsFragment";
    }

    @GetMapping("/createDatabaseForm")
    public String showCreateDatabaseForm(Model model) {
        return "createDatabaseForm";
    }

    @PostMapping("/createDatabase")
    public String createDatabase(@RequestParam String databaseName, Model model) {
        boolean created = mongoService.createDatabase(databaseName);
        if(created)
            model.addAttribute("message", "Database " + databaseName + " created successfully!");
        else
            model.addAttribute("message", "error while creating" + databaseName );
        return "createDatabaseForm";
    }

    @GetMapping("/createUserForm")
    public String showCreateUserForm(Model model) {
        return "createUserForm";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam String username, @RequestParam String password,
                             @RequestParam String databaseName, @RequestParam String role, Model model) {
        boolean created = mongoService.createUser(username, password, databaseName, role);
        if(created)
            model.addAttribute("message", "User " + username + " created successfully with role " + role + "!");
        else
            model.addAttribute("message", "error while creating " + username );
        return "createUserForm";
    }
}
