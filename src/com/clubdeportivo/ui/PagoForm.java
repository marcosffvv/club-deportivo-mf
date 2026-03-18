package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.service.PagoService;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class PagoForm extends Stage {

    public PagoForm() {

        setTitle("Registrar pago");

        SocioDAO socioDAO = new SocioDAO();
        PagoService pagoService = new PagoService();

        // 🔹 Combo socios
        ComboBox<Socio> comboSocios = new ComboBox<>();
        List<Socio> socios = socioDAO.obtenerTodos();
        comboSocios.setItems(FXCollections.observableArrayList(socios));

        // Mostrar nombre completo
        comboSocios.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Socio s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? null : s.getNombre() + " " + s.getApellidos());
            }
        });

        comboSocios.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Socio s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? null : s.getNombre() + " " + s.getApellidos());
            }
        });

        // 🔹 Mes y año
        TextField txtMes = new TextField(String.valueOf(LocalDate.now().getMonthValue()));
        txtMes.setPromptText("Mes (1-12)");

        TextField txtAnio = new TextField(String.valueOf(LocalDate.now().getYear()));
        txtAnio.setPromptText("Año");

        // 🔹 Botón guardar
        Button btnGuardar = new Button("Registrar pago");

        btnGuardar.setOnAction(e -> {

            Socio socio = comboSocios.getValue();

            if (socio == null) {
                mostrarError("Selecciona un socio");
                return;
            }

            int mes;
            int anio;

            try {
                mes = Integer.parseInt(txtMes.getText());
                anio = Integer.parseInt(txtAnio.getText());
            } catch (NumberFormatException ex) {
                mostrarError("Mes o año inválido");
                return;
            }

            boolean ok = pagoService.registrarPago(
                    socio.getIdSocio(),
                    mes,
                    anio
            );

            if (ok) {
                mostrarInfo("Pago registrado correctamente");
                close();
            } else {
                mostrarError("El pago no es válido o ya existe");
            }

        });

        VBox root = new VBox(10,
                new Label("Socio"),
                comboSocios,
                new Label("Mes"),
                txtMes,
                new Label("Año"),
                txtAnio,
                btnGuardar
        );

        root.setStyle("-fx-padding: 20;");

        setScene(new Scene(root, 300, 300));
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}