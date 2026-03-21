package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.service.InvitacionService;
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
        InvitacionService invitacionService = new InvitacionService();

        // 🔥 TÍTULO
        Label titulo = new Label("Gestión de Socios");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // COLUMNAS
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

        // INVITACIONES
        TableColumn<Socio, String> invitacionesCol = new TableColumn<>("Invitaciones");

        invitacionesCol.setCellValueFactory(data -> {
            Socio socio = data.getValue();
            int disponibles = invitacionService.obtenerDisponibles(socio.getIdSocio());
            return new SimpleStringProperty(String.valueOf(disponibles));
        });

        invitacionesCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(value);

                    int num = Integer.parseInt(value);

                    if (num == 0) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: green;");
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
                estadoCol,
                invitacionesCol
        );

        // 🔥 MEJORAS TABLA
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No hay datos disponibles"));
        table.setStyle("-fx-selection-bar: #90CAF9;");

        // BOTONES
        Button btnAñadir = new Button("Añadir socio");
        Button btnEditar = new Button("Editar socio");
        Button btnEliminar = new Button("Eliminar socio");
        Button btnRecargar = new Button("Recargar");
        Button btnPago = new Button("Registrar pago");
        Button btnInvitacion = new Button("Usar invitación");

        // 🔥 ESTILO BOTONES
        btnAñadir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnEditar.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnRecargar.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");
        btnPago.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnInvitacion.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");

        HBox botones = new HBox(10);
        botones.setStyle("-fx-padding: 10; -fx-alignment: center-left;");
        botones.getChildren().addAll(
                btnAñadir,
                btnEditar,
                btnEliminar,
                btnRecargar,
                btnPago,
                btnInvitacion
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

        btnPago.setOnAction(e -> {
            PagoForm form = new PagoForm();
            form.show();
        });

        btnInvitacion.setOnAction(e -> {

            Socio socio = table.getSelectionModel().getSelectedItem();

            if (socio == null) return;

            boolean ok = invitacionService.usarInvitacion(socio.getIdSocio());

            Alert alert;

            if (ok) {
                int disponibles = invitacionService.obtenerDisponibles(socio.getIdSocio());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Invitación usada. Disponibles: " + disponibles);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No quedan invitaciones disponibles");
            }

            alert.showAndWait();

            cargarSocios();
        });

        cargarSocios();

        // 🔥 ESTILO GENERAL
        this.setSpacing(15);
        this.setStyle("-fx-padding: 15;");

        this.getChildren().addAll(titulo, botones, table);
    }

    private void cargarSocios() {

        SocioDAO socioDAO = new SocioDAO();
        List<Socio> socios = socioDAO.obtenerTodos();

        ObservableList<Socio> lista = FXCollections.observableArrayList(socios);
        table.setItems(lista);
    }
}