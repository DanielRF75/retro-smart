module com.kiosk.kiosklauncher {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kiosk.kiosklauncher to javafx.fxml;
    exports com.kiosk.kiosklauncher;
}