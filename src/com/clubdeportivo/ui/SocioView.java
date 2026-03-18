package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.service.PagoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SocioView extends VBox {

    private TableView<Socio> table;

    public SocioView() {

        table = new TableView<>();
        PagoService pagoService = new PagoService();

        TableColumn<Socio, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getIdSocio()))
        );

        TableColumn<Socio, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNombre())
        );

        TableColumn<Socio, String> apellidoCol = new TableColumn<>("Apellidos");
        apellidoCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getApellidos())
        );

        TableColumn<Socio, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEmail())
        );

        TableColumn<Socio, String> telefonoCol = new TableColumn<>("Teléfono");
        telefonoCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTelefono())
        );

        // CUOTA
        TableColumn<Socio, String> cuotaCol = new TableColumn<>("Cuota");
        cuotaCol.setCellValueFactory(data -> {

            int cuota = data.getValue().getIdTipoCuota();

            String nombreCuota;

            switch (cuota) {
                case 1: nombreCuota = "Básica"; break;
                case 2: nombreCuota = "Gold"; break;
                case 3: nombreCuota = "Premium"; break;
                default: nombreCuota = "Desconocida";
            }

            return new SimpleStringProperty(nombreCuota);
        });

        // ESTADO
        TableColumn<Socio, String> estadoCol = new TableColumn<>("Estado");

        estadoCol.setCellValueFactory(data -> {

            Socio socio = data.getValue();

            int mes = LocalDate.now().getMonthValue();
            int anio = LocalDate.now().getYear();

            String estado = pagoService.obtenerEstadoPago(
                    socio.getIdSocio(),
                    mes,
                    anio
            );

            return new SimpleStringProperty(estado);
        });

        // COLOR
        estadoCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);

                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);

                    if (estado.equals("AL CORRIENTE")) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });

        table.getColumns().addAll(
                idCol,
                nombreCol,
                apellidoCol,
                emailCol,
                telefonoCol,
                cuotaCol,
                estadoCol
        );

        // BOTONES
        Button btnAñadir = new Button("Añadir socio");
        Button btnEditar = new Button("Editar socio");
        Button btnEliminar = new Button("Eliminar socio");
        Button btnRecargar = new Button("Recargar");
        Button btnPago = new Button("Registrar pago"); // 👈 NUEVO

        HBox botones = new HBox(10);
        botones.getChildren().addAll(
                btnAñadir,
                btnEditar,
                btnEliminar,
                btnRecargar,
                btnPago // 👈 AÑADIDO
        );

        btnRecargar.setOnAction(e -> cargarSocios());

        btnEliminar.setOnAction(e -> {

            Socio socioSeleccionado = table.getSelectionModel().getSelectedItem();

            if (socioSeleccionado == null) return;

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("Eliminar socio");
            alerta.setContentText("¿Seguro que deseas eliminar este socio?");

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                SocioDAO dao = new SocioDAO();
                dao.eliminarSocio(socioSeleccionado.getIdSocio());

                cargarSocios();
            }
        });

        btnAñadir.setOnAction(e -> {
            SocioForm form = new SocioForm(null, this::cargarSocios);
            form.show();
        });

        btnEditar.setOnAction(e -> {

            Socio socioSeleccionado = table.getSelectionModel().getSelectedItem();

            if (socioSeleccionado != null) {
                SocioForm form = new SocioForm(socioSeleccionado, this::cargarSocios);
                form.show();
            }
        });

        // 🔥 BOTÓN PAGOS
        btnPago.setOnAction(e -> {
            PagoForm form = new PagoForm();
            form.show();
        });

        cargarSocios();

        this.setSpacing(10);
        this.getChildren().addAll(botones, table);
    }

    private void cargarSocios() {

        SocioDAO socioDAO = new SocioDAO();
        List<Socio> socios = socioDAO.obtenerTodos();

        ObservableList<Socio> lista = FXCollections.observableArrayList(socios);
        table.setItems(lista);
    }
}