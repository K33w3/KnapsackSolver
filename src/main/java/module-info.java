module com.example.phase3try {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.phase3try to javafx.fxml;
    exports com.example.phase3try;
}