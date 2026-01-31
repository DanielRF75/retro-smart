package com.kiosk.kiosklauncher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        Button retro = createImageButton("/images/retro.png", 250);
        Button browser = createImageButton("/images/browser.png", 250);
        Button media = createImageButton("/images/media.png", 250);
        Button exit = createImageButton("/images/exit.png", 200);

        retro.setOnAction(e -> launchCommand("retroarch"));
        browser.setOnAction(e -> launchCommand("firefox"));
        media.setOnAction(e ->
                launchCommand("chromium --kiosk https://www.primevideo.com/")
        );
        exit.setOnAction(e -> Platform.exit());

        HBox menu = new HBox(30, retro, browser, media, exit);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("""
            -fx-background-color: black;
            -fx-padding: 40;
        """);


        Scene scene = new Scene(menu);

        scene.setOnKeyPressed(e -> {

    
            // Alt + E -> abrir Nautilus
            if (e.getCode() == KeyCode.E && e.isAltDown()) {
                launchCommand("nautilus");
            }
        });



        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    private Button createImageButton(String imagePath, double width) {
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        ImageView view = new ImageView(img);

        view.setPreserveRatio(true);
        view.setFitWidth(width);

        Button button = new Button();
        button.setGraphic(view);

        button.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-padding: 20;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 20, 0.3, 0, 6);
        """);
        button.setOnMouseEntered(e -> button.setStyle("""
            -fx-background-color: #b1b1b1;
            -fx-background-radius: 20;
            -fx-padding: 20;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.45), 25, 0.4, 0, 8);
        """));
        button.setOnMouseExited(e -> button.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 20;
            -fx-padding: 20;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 20, 0.3, 0, 6);
        """));

        return button;
    }


    private void launchCommand(String command) {
        try {
            new ProcessBuilder("bash", "-c", command)
                    .inheritIO()
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
