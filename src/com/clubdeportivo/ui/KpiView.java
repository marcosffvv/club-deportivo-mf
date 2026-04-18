package com.clubdeportivo.ui;

import com.clubdeportivo.dao.KpiDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class KpiView extends Stage {

    public KpiView() {

        KpiDAO dao = new KpiDAO();

        int total = dao.getTotalSocios();
        int basica = dao.getSociosBasica();
        int gold = dao.getSociosGold();
        int premium = dao.getSociosPremium();

        int mes = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();

        int pendientes = dao.getSociosPendientes(mes, anio).size();
        int conInvitaciones = dao.getSociosConInvitaciones().size();

        Label titulo = new Label("Panel de KPI´s del Club");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        VBox tarjetaTotal = crearTarjeta("Socios Totales", total);
        tarjetaTotal.setOnMouseClicked(e -> new KpiDrilldownView().show());

        VBox tarjetaPendientes = crearTarjeta("Pendientes pago", pendientes);
        VBox tarjetaInvitaciones = crearTarjeta("Con invitaciones", conInvitaciones);

        tarjetaPendientes.setOnMouseClicked(e -> {

            List<String[]> datos = dao.getSociosPendientes(mes, anio);

            new KpiListView(
                    "Socios pendientes de pago",
                    new String[]{"Nombre", "Apellidos"},
                    datos
            ).show();
        });

        // DRILLDOWN INVITACIONES
        tarjetaInvitaciones.setOnMouseClicked(e -> {

            List<String[]> datos = dao.getSociosConInvitaciones();

            new KpiListView(
                    "Socios con invitaciones disponibles",
                    new String[]{"Nombre", "Apellidos", "Invitaciones"},
                    datos
            ).show();
        });

        // GRID
        grid.add(tarjetaTotal, 0, 0);
        grid.add(crearTarjeta("Básica", basica), 1, 0);
        grid.add(crearTarjeta("Gold", gold), 0, 1);
        grid.add(crearTarjeta("Premium", premium), 1, 1);

        grid.add(tarjetaPendientes, 0, 2);
        grid.add(tarjetaInvitaciones, 1, 2);

        VBox root = new VBox(20, titulo, grid);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 550, 500);

        this.setTitle("KPI´s del Club");
        this.setScene(scene);
    }

    private VBox crearTarjeta(String titulo, int valor) {

        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label valorLabel = new Label(String.valueOf(valor));
        valorLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #2196F3;");

        VBox box = new VBox(10, tituloLabel, valorLabel);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(150, 120);

        box.setStyle(
                "-fx-border-color: #ccc;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-padding: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5,0,0,2);"
        );

        return box;
    }
}