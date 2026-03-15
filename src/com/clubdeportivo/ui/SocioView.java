package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class SocioView extends VBox {

    private TableView<Socio> table;

    public SocioView() {

        table = new TableView<>();

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

        table.getColumns().addAll(
                idCol,
                nombreCol,
                apellidoCol,
                emailCol,
                telefonoCol,
                cuotaCol
        );

        Button btnAñadir = new Button("Añadir socio");
        Button btnEditar = new Button("Editar socio");
        Button btnEliminar = new Button("Eliminar socio");
        Button btnRecargar = new Button("Recargar");

        HBox botones = new HBox(10);
        botones.getChildren().addAll(btnAñadir, btnEditar, btnEliminar, btnRecargar);

        btnRecargar.setOnAction(e -> cargarSocios());

        btnEliminar.setOnAction(e -> {

            Socio socioSeleccionado = table.getSelectionModel().getSelectedItem();

            if (socioSeleccionado == null) {
                return;
            }

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