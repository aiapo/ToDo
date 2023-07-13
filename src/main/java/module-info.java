module com.todo {
    requires javafx.controls;
    requires javafx.fxml;

            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.todo to javafx.fxml;
    exports com.todo;
}