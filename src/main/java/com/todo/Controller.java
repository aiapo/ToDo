package com.todo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Controller {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView categoryList = new ListView();
    @FXML
    private ListView itemList = new ListView();

    @FXML
    protected void onCategoryAddClick() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Category");
        dialog.setHeaderText("Create a new category");
        dialog.setContentText("Category name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            categoryList.getItems().add(result.get());
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
            itemList.getItems().add(result.get());
        }

    }
}