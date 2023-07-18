package com.todo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Create a new TaskManager instance
    TaskManager Tasks = new TaskManager();

    // FX Fields init
    @FXML
    private TextField itemName;
    @FXML
    private TextArea itemDescription;
    @FXML
    private DatePicker itemCreate,itemDue;
    @FXML
    private CheckBox itemCompleted;
    @FXML
    private ListView categoryList = new ListView();
    @FXML
    private ListView<Task> itemList = new ListView<Task>();

    public Controller() throws SQLException {
    }

    // Initialize controller (populate the lists from DB)
    // onChange lists update
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemList.setItems(Tasks.populateArray());

        itemList.getSelectionModel().selectedItemProperty().addListener(
                (ov, orgTask, newTask) -> {
                    updateDetails(newTask.id);
                }
        );
    }

    // adds a task to add to DB and list
    private void addTask(Integer id, String name,String description,String creation,String due,Integer completion){
        itemList.setItems(Tasks.add(id,name,description,creation,due,completion));
    }

    // Updates the details panel based on the currently selected task
    protected void updateDetails(Integer id){
        id--;
        itemName.setText(Tasks.retrieve(id).name);
        itemDescription.setText(Tasks.retrieve(id).description);
        itemCreate.setValue(LocalDate.parse(Tasks.retrieve(id).creation));
        itemDue.setValue(LocalDate.parse(Tasks.retrieve(id).due));
        if(Tasks.retrieve(id).completion==1)
            itemCompleted.setSelected(true);
        else
            itemCompleted.setSelected(false);
    }

    // Add a new category
    @FXML
    protected void onCategoryAddClick(){
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

    // Add a new task
    @FXML
    protected void onItemAddClick(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Item");
        dialog.setHeaderText("Create a new item");
        dialog.setContentText("Item name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String newTask = result.get();
            String nowTime = String.valueOf(LocalDate.now());
            addTask(null,newTask,"",nowTime,nowTime,0);
        }
    }

    // Save edits to a task
    @FXML
    protected void onSaveClick() {
        Integer currID = itemList.getSelectionModel().selectedItemProperty().get().id;
        System.out.println(currID+": "+itemName.getText());
    }

    // About dialog
    @FXML
    protected void onAboutClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About ToDo");
        alert.setHeaderText("ToDo");
        alert.setContentText("Created by Aidan Gitschlag and Karl Dahlstrom");
        alert.showAndWait();
    }
}