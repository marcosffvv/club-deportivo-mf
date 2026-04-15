package com.clubdeportivo.ui;

import com.clubdeportivo.model.Usuario;
import com.clubdeportivo.service.AuthService;
import com.clubdeportivo.service.Sesion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends Stage {

    public LoginView() {

        // 🔹 TÍTULO
        Label titulo = new Label("Acceso al sistema");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // 🔹 CAMPOS
        TextField userField = new TextField();
        userField.setPromptText("Usuario");
        userField.setPrefWidth(220);
        userField.setStyle("-fx-padding: 8;");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");
        passField.setPrefWidth(220);
        passField.setStyle("-fx-padding: 8;");

        // 🔹 BOTÓN LOGIN
        Button loginBtn = new Button("Iniciar sesión");
        loginBtn.setPrefWidth(220);
        loginBtn.setStyle(
                "-fx-background-color: #2196F3;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10;" +
                        "-fx-background-radius: 6;"
        );

        // 🔹 BOTÓN RECUPERAR CONTRASEÑA
        Button recuperarBtn = new Button("¿Olvidaste tu contraseña?");
        recuperarBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #2196F3;" +
                        "-fx-underline: true;"
        );

        recuperarBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Recuperación de contraseña");
            alert.setContentText("Por favor, contacte con el administrador: admin@admin.es");
            alert.showAndWait();
        });

        // 🔹 CONTENEDOR (CARD)
        VBox card = new VBox(15, titulo, userField, passField, loginBtn, recuperarBtn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);"
        );

        // 🔹 ROOT
        VBox root = new VBox(card);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setPadding(new Insets(30));

        // 🔹 ACCIÓN LOGIN
        loginBtn.setOnAction(e -> {

            String user = userField.getText();
            String pass = passField.getText();

            AuthService auth = new AuthService();
            Usuario u = auth.login(user, pass);

            if (u != null) {

                Sesion.setUsuario(u);

                SocioView view = new SocioView();
                Stage mainStage = new Stage();

                Scene scene = new Scene(view, 1000, 600);

                mainStage.setTitle("Club Deportivo - Gestión de socios");
                mainStage.setScene(scene);
                mainStage.show();

                this.close();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos");
                alert.showAndWait();
            }
        });

        // 🔹 ESCENA
        Scene scene = new Scene(root, 400, 350);

        this.setTitle("Login");
        this.setScene(scene);
    }
}