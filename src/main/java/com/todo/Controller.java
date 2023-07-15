package com.todo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    TaskManager Tasks = new TaskManager();
    @FXML
    private Label welcomeText;
    @FXML
    private ListView categoryList = new ListView();
    @FXML
    private ListView itemList = new ListView();

    public Controller() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet tasksInit = Tasks.get("Tasks", new String[]{"name"});
            while(tasksInit.next()) {
                itemList.getItems().add(tasksInit.getString("name"));
            }
            ResultSet catInit = Tasks.get("Categories", new String[]{"name"});
            while(catInit.next()) {
                categoryList.getItems().add(catInit.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onCategoryAddClick() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Category");
        dialog.setHeaderText("Create a new category");
        dialog.setContentText("Category name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String newCat = result.get();
            categoryList.getItems().add(newCat);
        }

    }

    @FXML
    protected void onItemAddClick() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Item");
        dialog.setHeaderText("Create a new item");
        dialog.setContentText("Item name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String newTask = result.get();
            Tasks.add(newTask,"test","today","tomorrow",0);
            itemList.getItems().add(newTask);
        }
    }

    @FXML
    protected void onAboutClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About ToDo");
        alert.setHeaderText("ToDo");
        alert.setContentText("Created by Aidan Gitschlag and Karl Dahlstrom");

        alert.showAndWait();

    }
}