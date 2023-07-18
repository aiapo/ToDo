package com.todo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    TaskManager Tasks = new TaskManager();

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
    ObservableList<Task> items = FXCollections.observableArrayList();

    public Controller() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet tasksInit = Tasks.get("Tasks", new String[]{"*"});
            while(tasksInit.next()) {
                addTask(tasksInit.getInt("ID"),
                        tasksInit.getString("name"),
                        tasksInit.getString("description"),
                        tasksInit.getString("creation_date"),
                        tasksInit.getString("due_date"),
                        tasksInit.getInt("completion"));
            }
            ResultSet catInit = Tasks.get("Categories", new String[]{"name"});
            while(catInit.next()) {
                categoryList.getItems().add(catInit.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        itemList.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    updateDetails(new_val.id);
                }
        );
    }

    private void addTask(Integer id, String name,String description,String creation,String due,Integer completion) throws SQLException {
        if(id == null)
            id = Tasks.get("Tasks", new String[]{"ID"},"MAX",null).getInt("max_id");
        items.add(new Task(id,name,description,creation,due,completion));
        itemList.setItems(items);
    }

    protected void updateDetails(Integer id){
        id--;
        itemName.setText(itemList.getItems().get(id).name);
        itemDescription.setText(itemList.getItems().get(id).description);
        itemCreate.setValue(LocalDate.parse(itemList.getItems().get(id).creation));
        itemDue.setValue(LocalDate.parse(itemList.getItems().get(id).due));
        if(itemList.getItems().get(id).completion==1)
            itemCompleted.setSelected(true);
        else
            itemCompleted.setSelected(false);
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
    protected void onItemAddClick() throws SQLException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Item");
        dialog.setHeaderText("Create a new item");
        dialog.setContentText("Item name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String newTask = result.get();
            String nowTime = String.valueOf(LocalDate.now());
            Tasks.add(newTask,"",nowTime,nowTime,0);
            addTask(null,newTask,"",nowTime,nowTime,0);
        }
    }

    @FXML
    protected void onSaveClick() {
        Integer currID = itemList.getSelectionModel().selectedItemProperty().get().id;
        System.out.println(currID+": "+itemName.getText());
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