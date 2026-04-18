package com.clubdeportivo;

import com.clubdeportivo.ui.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        // lanzar login
        LoginView login = new LoginView();
        login.show();
    }

    public static void main(String[] args) {
        launch();
    }
}