package com.kiosk.kiosklauncher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button retro = new Button("🎮 Retro Games");
        Button browser = new Button("🌐 Navegador");
        Button netflix = new Button("🎬 Netflix");
        Button exit = new Button("⏻ Salir");

        retro.setOnAction(e -> launchCommand("retroarch"));
        browser.setOnAction(e -> launchCommand("firefox"));
        netflix.setOnAction(e -> launchCommand("chromium --kiosk https://netflix.com"));
        exit.setOnAction(e -> Platform.exit());

        VBox menu = new VBox(20, retro, browser, netflix, exit);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("""
            -fx-background-color: black;
            -fx-padding: 40;
        """);

        menu.getChildren().forEach(n ->
                n.setStyle("""
                -fx-font-size: 28px;
                -fx-background-color: #222;
                -fx-text-fill: white;
            """)
        );

        Scene scene = new Scene(menu);

        // Navegación por teclado / mando
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
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
