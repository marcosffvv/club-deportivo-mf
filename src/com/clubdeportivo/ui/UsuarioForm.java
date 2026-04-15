package com.clubdeportivo.ui;

import com.clubdeportivo.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UsuarioForm extends Stage {

    public UsuarioForm() {

        TextField userField = new TextField();
        userField.setPromptText("Usuario");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Contraseña");

        Button crearBtn = new Button("Crear administrador");

        VBox root = new VBox(10,
                new Label("Nuevo administrador"),
                userField,
                passField,
                crearBtn
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        crearBtn.setOnAction(e -> {

            String user = userField.getText();
            String pass = passField.getText();

            if (user.isEmpty() || pass.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Completa todos los campos").showAndWait();
                return;
            }

            AuthService auth = new AuthService();
            auth.crearAdmin(user, pass);

            new Alert(Alert.AlertType.INFORMATION, "Administrador creado").showAndWait();

            this.close();
        });

        this.setScene(new Scene(root, 300, 200));
        this.setTitle("Crear administrador");
    }
}