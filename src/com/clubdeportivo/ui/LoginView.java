package com.clubdeportivo.ui;

import com.clubdeportivo.model.Usuario;
import com.clubdeportivo.service.AuthService;
import com.clubdeportivo.service.Sesion; // 🔥 NUEVO
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends Stage {

    public LoginView() {

        // 🔹 Campos
        TextField userField = new TextField();
        userField.setPromptText("Usuario");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");

        Button loginBtn = new Button("Iniciar sesión");

        Label titulo = new Label("Acceso al sistema");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // 🔹 Layout
        VBox root = new VBox(10, titulo, userField, passField, loginBtn);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // 🔹 Acción login
        loginBtn.setOnAction(e -> {

            String user = userField.getText();
            String pass = passField.getText();

            AuthService auth = new AuthService();

            Usuario u = auth.login(user, pass);

            if (u != null) {

                // 🔥 GUARDAR SESIÓN
                Sesion.setUsuario(u);

                // 🔥 abrir app principal
                SocioView view = new SocioView();
                Stage mainStage = new Stage();

                Scene scene = new Scene(view, 900, 550);

                mainStage.setTitle("Club Deportivo - Gestión de socios");
                mainStage.setScene(scene);
                mainStage.show();

                // cerrar login
                this.close();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos");
                alert.showAndWait();
            }
        });

        // 🔹 Escena
        Scene scene = new Scene(root, 300, 250);

        this.setTitle("Login");
        this.setScene(scene);
    }
}