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

    Manager App = new Manager();

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
        itemList.setItems(App.Tasks.populateArray());
        categoryList.setItems(App.Categories.populateArray());

        categoryList.getSelectionModel().selectedItemProperty().addListener(
                (ov, orgCat, newCat) -> {
                    if(newCat!=null)
                        updateTasks(newCat.id);
                }
        );

        itemList.getSelectionModel().selectedItemProperty().addListener(
                (ov, orgTask, newTask) -> {
                    if(newTask!=null)
                        updateDetails(newTask.id);
                }
        );
    }

    // Updates the tasks panel based on the currently selected category
    protected void updateTasks(Integer id){
        System.out.println(id);
    }

    // Updates the details panel based on the currently selected task
    protected void updateDetails(Integer id){
        itemName.setText(App.Tasks.retrieve(id).name);
        itemDescription.setText(App.Tasks.retrieve(id).description);
        itemCreate.setValue(LocalDate.parse(App.Tasks.retrieve(id).creation));
        itemDue.setValue(LocalDate.parse(App.Tasks.retrieve(id).due));
        if (App.Tasks.retrieve(id).completion == 1)
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
            categoryList.setItems(App.Categories.add(null,newCat,""));
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
            itemList.setItems(App.Tasks.add(null,newTask,"",nowTime,nowTime,0));
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
            itemList.setItems(App.Tasks.update(currID, itemName.getText(), itemDescription.getText(), itemCreate.getValue().toString(), itemDue.getValue().toString(), isComplete));
        }else
            System.out.println("Error: Can't edit a non-existent task!");
    }

    // Deletes a task
    @FXML
    protected void onItemDeleteClick() {
        if(itemList.getSelectionModel().selectedItemProperty().get()!=null) {
            Integer currID = itemList.getSelectionModel().selectedItemProperty().get().id;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Task?");
            alert.setHeaderText("Delete "+App.Tasks.retrieve(currID).name+"?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                itemList.setItems(App.Tasks.delete(currID));
            } else {
                System.out.println(App.Tasks.retrieve(currID).name+" not deleted!");
            }
        }else
            System.out.println("Error: Can't delete a non-existent task!");
    }

    // Deletes a category
    @FXML
    protected void onCategoryDeleteClick() {
        if(categoryList.getSelectionModel().selectedItemProperty().get()!=null) {
            Integer currID = categoryList.getSelectionModel().selectedItemProperty().get().id;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Category?");
            alert.setHeaderText("Delete "+App.Categories.retrieve(currID).name+"?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                categoryList.setItems(App.Categories.delete(currID));
            } else {
                System.out.println(App.Categories.retrieve(currID).name+" not deleted!");
            }
        }else
            System.out.println("Error: Can't delete a non-existent category!");
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