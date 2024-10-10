module cqu.drsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens cqu.drsystem to javafx.fxml;
    exports cqu.drsystem;
}
