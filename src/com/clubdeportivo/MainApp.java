package com.clubdeportivo;

import com.clubdeportivo.ui.SocioView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        SocioView view = new SocioView();

        Scene scene = new Scene(view, 900, 550);

        stage.setTitle("Club Deportivo - Gestión de socios");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}