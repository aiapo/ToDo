module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            
    opens com.todo to javafx.fxml;
    exports com.todo;
}