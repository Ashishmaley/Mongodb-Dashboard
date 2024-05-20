package com.flipr.mongo_db_admin_panel.controller;

import com.flipr.mongo_db_admin_panel.models.MongoUser;
import com.flipr.mongo_db_admin_panel.services.MongoService;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.MongoDatabaseUtils.getDatabase;

@Controller
@RequestMapping("/")
public class MongoController {

    @Autowired
    private MongoService mongoService;

    @GetMapping("/api/mongo")
    public String viewDashboard(@RequestParam(value = "databaseSelect", required = false) String databaseSelect, Model model) {
        Document serverStatus = mongoService.getServerStatus();
        model.addAttribute("serverStatus", serverStatus.toJson());

        List<String> databaseNames = mongoService.getDatabaseNames();
        model.addAttribute("databaseNames", databaseNames);

        String selectedDb;
        if (databaseSelect != null && !databaseSelect.isEmpty()) {
            selectedDb = databaseSelect;
        } else if (!databaseNames.isEmpty()) {
            selectedDb = databaseNames.get(0);
        } else {
            selectedDb = null;
        }

        if (selectedDb != null) {
            List<Document> collections = mongoService.getCollections(selectedDb);
            List<String> collectionNames = collections.stream()
                    .map(collection -> collection.getString("name"))
                    .collect(Collectors.toList());
            model.addAttribute("selectedDb", selectedDb);
            model.addAttribute("collections", collectionNames);
        }

        return "index"; // Ensure this is the Thymeleaf template name
    }
    @GetMapping("/api/mongo/collections")
    @ResponseBody
    public List<String> getCollections(@RequestParam("databaseSelect") String databaseSelect) {
        List<Document> collections = mongoService.getCollections(databaseSelect);
        return collections.stream()
                .map(collection -> collection.getString("name"))
                .collect(Collectors.toList());
    }


    @GetMapping("/api/mongo/selected")
    public String viewDashboardSelected(@RequestParam("databaseSelect") String databaseSelect, Model model) {
        return viewDashboard(databaseSelect, model); // Reuse the viewDashboard method logic
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
    @GetMapping("/api/mongo/db/{dbName}/users")
    @ResponseBody
    public List<String> getUsers(@PathVariable String dbName) {
        List<Document> users = mongoService.getUsers(dbName);
        return users.stream()
                .map(user -> user.getString("user"))
                .collect(Collectors.toList());
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

    @PostMapping("/deleteDatabase")
    public String deleteDatabase(@RequestParam String databaseName, Model model) {
        boolean deleted = mongoService.deleteDatabase(databaseName);
        if(deleted)
            model.addAttribute("message", "Database " + databaseName + " deleted successfully!");
        else
            model.addAttribute("message", "Error while deleting database " + databaseName);
        return "createDatabaseForm";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String username, @RequestParam String databaseName, Model model) {
        boolean deleted = mongoService.deleteUser(username, databaseName);
        if (deleted) {
            model.addAttribute("message", "User " + username + " deleted successfully!");
        } else {
            model.addAttribute("message", "Error while deleting user " + username);
        }
        return "createUserForm";
    }
}
