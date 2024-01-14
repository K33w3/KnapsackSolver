module com.bcs2024.knapsack {

    requires javafx.controls;
    requires javafx.fxml;

    opens com.bcs2024.knapsack to javafx.fxml;
    exports com.bcs2024.knapsack;

    exports com.bcs2024.knapsack.renderer to javafx.graphics;
}