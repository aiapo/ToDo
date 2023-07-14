package com.todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static TaskDatabase DB = new TaskDatabase();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ToDo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        TaskDatabase.initTaskDB();
        launch();
    }
}

