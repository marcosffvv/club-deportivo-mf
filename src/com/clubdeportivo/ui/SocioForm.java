package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.dao.TipoCuotaDAO;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.model.TipoCuota;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SocioForm extends Stage {

    public SocioForm(Socio socio, Runnable onSave) {

        boolean modoEdicion = socio != null;

        if (!modoEdicion) {
            socio = new Socio();
        }

        Socio socioActual = socio;

        TextField nombreField = new TextField(socioActual.getNombre());
        TextField apellidosField = new TextField(socioActual.getApellidos());
        TextField emailField = new TextField(socioActual.getEmail());
        TextField telefonoField = new TextField(socioActual.getTelefono());

        ComboBox<TipoCuota> cuotaBox = new ComboBox<>();

        // 🔥 CARGAR DESDE BD (NO HARDCODE)
        TipoCuotaDAO cuotaDAO = new TipoCuotaDAO();
        cuotaBox.setItems(FXCollections.observableArrayList(cuotaDAO.obtenerTodos()));

        // 🔥 MOSTRAR NOMBRE
        cuotaBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(TipoCuota c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNombre());
            }
        });

        cuotaBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TipoCuota c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNombre());
            }
        });

        // 🔥 SELECCIONAR CUOTA ACTUAL
        cuotaBox.setValue(
                cuotaBox.getItems().stream()
                        .filter(c -> c.getId() == socioActual.getIdTipoCuota())
                        .findFirst()
                        .orElse(null)
        );

        Button guardarBtn = new Button("Guardar");
        Button limpiarBtn = new Button("Limpiar");
        Button cancelarBtn = new Button("Cancelar");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nombreField, 1, 0);

        grid.add(new Label("Apellidos"), 0, 1);
        grid.add(apellidosField, 1, 1);

        grid.add(new Label("Email"), 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(new Label("Teléfono"), 0, 3);
        grid.add(telefonoField, 1, 3);

        grid.add(new Label("Tipo de cuota"), 0, 4);
        grid.add(cuotaBox, 1, 4);

        HBox botones = new HBox(10);
        botones.getChildren().addAll(guardarBtn, limpiarBtn, cancelarBtn);

        grid.add(botones, 1, 5);

        guardarBtn.setOnAction(e -> {

            socioActual.setNombre(nombreField.getText());
            socioActual.setApellidos(apellidosField.getText());
            socioActual.setEmail(emailField.getText());
            socioActual.setTelefono(telefonoField.getText());

            if (!modoEdicion) {
                socioActual.setFechaAlta(LocalDate.now());
                socioActual.setActivo(true);
            }

            // 🔥 IMPORTANTE (esto ya lo hacías bien)
            socioActual.setIdTipoCuota(cuotaBox.getValue().getId());

            SocioDAO dao = new SocioDAO();

            if (modoEdicion) {
                dao.actualizarSocio(socioActual);
            } else {
                dao.insertarSocio(socioActual);
            }

            if (onSave != null) {
                onSave.run();
            }

            this.close();
        });

        limpiarBtn.setOnAction(e -> {

            nombreField.clear();
            apellidosField.clear();
            emailField.clear();
            telefonoField.clear();

            if (!cuotaBox.getItems().isEmpty()) {
                cuotaBox.setValue(cuotaBox.getItems().get(0));
            }
        });

        cancelarBtn.setOnAction(e -> this.close());

        Scene scene = new Scene(grid, 320, 300);

        this.setTitle(modoEdicion ? "Editar socio" : "Nuevo socio");
        this.setScene(scene);
    }
}