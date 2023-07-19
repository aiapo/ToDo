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
    // Create a new CategoryManager instance
    CategoryManager Categories = new CategoryManager();

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
    private ListView<Category> categoryList = new ListView<Category>();
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
                    if(newTask!=null)
                        updateDetails(newTask.id);
                }
        );
    }

    // Updates the details panel based on the currently selected task
    protected void updateDetails(Integer id){
        itemName.setText(Tasks.retrieve(id).name);
        itemDescription.setText(Tasks.retrieve(id).description);
        itemCreate.setValue(LocalDate.parse(Tasks.retrieve(id).creation));
        itemDue.setValue(LocalDate.parse(Tasks.retrieve(id).due));
        if (Tasks.retrieve(id).completion == 1)
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
            categoryList.setItems(Categories.add(null,newCat,""));
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
            itemList.setItems(Tasks.add(null,newTask,"",nowTime,nowTime,0));
        }
    }

    // Save edits to a task
    @FXML
    protected void onSaveClick() {
        if(itemList.getSelectionModel().selectedItemProperty().get()!=null) {
            Integer currID = itemList.getSelectionModel().selectedItemProperty().get().id;
            Integer isComplete = 0;
            if (itemCompleted.isSelected())
                isComplete = 1;
            itemList.setItems(Tasks.update(currID, itemName.getText(), itemDescription.getText(), itemCreate.getValue().toString(), itemDue.getValue().toString(), isComplete));
        }else
            System.out.println("Error: Can't edit a non-existent task!");
    }

    // Deletes a task
    @FXML
    protected void onDeleteClick() {
        if(itemList.getSelectionModel().selectedItemProperty().get()!=null) {
            Integer currID = itemList.getSelectionModel().selectedItemProperty().get().id;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Task?");
            alert.setHeaderText("Delete "+Tasks.retrieve(currID).name+"?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                itemList.setItems(Tasks.delete(currID));
            } else {
                System.out.println(Tasks.retrieve(currID).name+" not deleted!");
            }
        }else
            System.out.println("Error: Can't delete a non-existent task!");
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