package com.clubdeportivo.ui;

import com.clubdeportivo.dao.KpiDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KpiView extends Stage {

    public KpiView() {

        KpiDAO dao = new KpiDAO();

        int total = dao.getTotalSocios();
        int basica = dao.getSociosBasica();
        int gold = dao.getSociosGold();
        int premium = dao.getSociosPremium();

        // 🔥 TÍTULO
        Label titulo = new Label("Panel de KPI´s del Club");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 🔥 GRID CENTRADO
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        grid.add(crearTarjeta("Socios Totales", total), 0, 0);
        grid.add(crearTarjeta("Básica", basica), 1, 0);
        grid.add(crearTarjeta("Gold", gold), 0, 1);
        grid.add(crearTarjeta("Premium", premium), 1, 1);

        // 🔥 CONTENEDOR GENERAL
        VBox root = new VBox(20, titulo, grid);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 400);

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

        // 🔥 TAMAÑO FIJO (CLAVE)
        box.setPrefSize(150, 120);

        // 🔥 ESTILO MÁS PRO
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