module com.todo {
    requires javafx.controls;
    requires javafx.fxml;

            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.todo to javafx.fxml;
    exports com.todo;
}