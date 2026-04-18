package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KpiDrilldownView extends Stage {

    public KpiDrilldownView() {

        SocioDAO dao = new SocioDAO();

        // EJES
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Mes - Año");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Número de socios");

        // GRÁFICO
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Evolución de socios");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total acumulado");

        // FORMATEADOR
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

        // DATOS
        List<String[]> datos = dao.obtenerEvolucionSocios();

        for (String[] d : datos) {

            // convertir String → LocalDate
            LocalDate fecha = LocalDate.parse(d[0]);

            // formatear a mes-año
            String fechaFormateada = fecha.format(formatter);

            series.getData().add(
                    new XYChart.Data<>(fechaFormateada, Integer.parseInt(d[1]))
            );
        }

        chart.getData().add(series);

        // CONTENEDOR
        VBox root = new VBox(20, chart);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 700, 500);

        this.setTitle("Detalle KPI - Socios");
        this.setScene(scene);
    }
}