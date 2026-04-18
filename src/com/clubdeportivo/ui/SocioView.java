package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.service.InvitacionService;
import com.clubdeportivo.service.PagoService;
import com.clubdeportivo.service.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SocioView extends VBox {

    private TableView<Socio> table;
    private ObservableList<Socio> listaOriginal;

    public SocioView() {

        table = new TableView<>();

        PagoService pagoService = new PagoService();
        InvitacionService invitacionService = new InvitacionService();

        // HEADER
        Label titulo = new Label("Gestión de Socios");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label usuarioLabel = new Label("Bienvenido, " + Sesion.getUsuario().getUsername());

        Button logoutBtn = new Button("Cerrar sesión");
        logoutBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        HBox rightHeader = new HBox(10, usuarioLabel, logoutBtn);
        rightHeader.setAlignment(Pos.CENTER_RIGHT);

        Region spacerHeader = new Region();
        HBox.setHgrow(spacerHeader, Priority.ALWAYS);

        HBox header = new HBox(10, titulo, spacerHeader, rightHeader);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-padding: 10;");

        logoutBtn.setOnAction(e -> {
            Sesion.cerrarSesion();
            new LoginView().show();
            ((Stage) this.getScene().getWindow()).close();
        });

        // BUSCADOR
        TextField buscadorField = new TextField();
        buscadorField.setPromptText("Buscar socio...");
        buscadorField.setPrefWidth(200);

        // BOTONES
        Button btnAñadir = new Button("Alta socio");
        Button btnEditar = new Button("Editar socio");
        Button btnEliminar = new Button("Baja socio");

        Button btnRecargar = new Button("Recargar");

        Button btnPago = new Button("Registrar pago");
        Button btnInvitacion = new Button("Usar invitación");

        Button btnAdmin = new Button("Nuevo admin");
        Button btnKpi = new Button("KPI´s club");

        // ESTILOS
        btnAñadir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnEditar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        btnRecargar.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");

        btnPago.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnInvitacion.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        btnAdmin.setStyle("-fx-background-color: #673AB7; -fx-text-fill: white;");
        btnKpi.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

        // AGRUPACIONES
        HBox bloqueSocios = new HBox(10, btnAñadir, btnEditar, btnEliminar);
        HBox bloqueSistema = new HBox(10, btnRecargar);
        HBox bloquePagos = new HBox(10, btnPago, btnInvitacion);
        HBox bloqueAdmin = new HBox(10, btnAdmin);
        HBox bloqueKpi = new HBox(10, btnKpi);

        // SPACER PARA EMPUJAR BUSCADOR
        Region spacerBotones = new Region();
        HBox.setHgrow(spacerBotones, Priority.ALWAYS);

        // CONTENEDOR PRINCIPAL
        HBox contenedorBotones = new HBox(25,
                bloqueSocios,
                bloqueSistema,
                bloquePagos,
                bloqueAdmin,
                bloqueKpi,
                spacerBotones,
                buscadorField
        );

        contenedorBotones.setAlignment(Pos.CENTER_LEFT);
        contenedorBotones.setStyle("-fx-padding: 20 10 10 10;");

        // TABLA

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

        TableColumn<Socio, String> estadoCol = new TableColumn<>("Estado");
        estadoCol.setCellValueFactory(data -> {

            Socio socio = data.getValue();

            String estado = pagoService.obtenerEstadoPago(
                    socio.getIdSocio(),
                    LocalDate.now().getMonthValue(),
                    LocalDate.now().getYear()
            );

            return new SimpleStringProperty(estado);
        });

        estadoCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);

                if (empty || estado == null) {
                    setText(null);
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

        TableColumn<Socio, String> invitacionesCol = new TableColumn<>("Invitaciones");
        invitacionesCol.setCellValueFactory(data -> {
            int disponibles = invitacionService.obtenerDisponibles(data.getValue().getIdSocio());
            return new SimpleStringProperty(String.valueOf(disponibles));
        });

        table.getColumns().addAll(
                idCol, nombreCol, apellidoCol, emailCol,
                telefonoCol, cuotaCol, estadoCol, invitacionesCol
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // FILTRO
        buscadorField.textProperty().addListener((obs, oldVal, newVal) -> {

            String filtro = newVal.toLowerCase();

            ObservableList<Socio> filtrados = FXCollections.observableArrayList();

            for (Socio s : listaOriginal) {

                if (s.getNombre().toLowerCase().contains(filtro) ||
                        s.getApellidos().toLowerCase().contains(filtro)) {

                    filtrados.add(s);
                }
            }

            table.setItems(filtrados);
        });

        // ACCIONES

        btnRecargar.setOnAction(e -> cargarSocios());

        btnEliminar.setOnAction(e -> {
            Socio s = table.getSelectionModel().getSelectedItem();
            if (s == null) return;

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("¿Eliminar socio?");
            Optional<ButtonType> r = a.showAndWait();

            if (r.isPresent() && r.get() == ButtonType.OK) {
                new SocioDAO().eliminarSocio(s.getIdSocio());
                cargarSocios();
            }
        });

        btnAñadir.setOnAction(e -> new SocioForm(null, this::cargarSocios).show());
        btnEditar.setOnAction(e -> {
            Socio s = table.getSelectionModel().getSelectedItem();
            if (s != null) new SocioForm(s, this::cargarSocios).show();
        });

        btnPago.setOnAction(e -> new PagoForm().show());

        btnInvitacion.setOnAction(e -> {
            Socio s = table.getSelectionModel().getSelectedItem();
            if (s == null) return;
            invitacionService.usarInvitacion(s.getIdSocio());
            cargarSocios();
        });

        btnAdmin.setOnAction(e -> new UsuarioForm().show());
        btnKpi.setOnAction(e -> new KpiView().show());

        cargarSocios();

        this.setSpacing(25);
        this.setStyle("-fx-padding: 15;");

        this.getChildren().addAll(header, contenedorBotones, table);
    }

    private void cargarSocios() {
        List<Socio> socios = new SocioDAO().obtenerTodos();
        listaOriginal = FXCollections.observableArrayList(socios);
        table.setItems(listaOriginal);
    }
}