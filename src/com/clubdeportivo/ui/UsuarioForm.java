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

            // VALIDACIÓN CAMPOS
            if (user.isEmpty() || pass.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Completa todos los campos").showAndWait();
                return;
            }

            AuthService auth = new AuthService();

            // CLAVE: comprobar resultado
            boolean creado = auth.crearAdmin(user, pass);

            Alert alert;

            if (creado) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Administrador creado correctamente");
                this.close(); // solo cerramos si todo OK
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("El usuario ya existe");
            }

            alert.showAndWait();
        });

        this.setScene(new Scene(root, 300, 200));
        this.setTitle("Crear administrador");
    }
}