package com.clubdeportivo.ui;

import com.clubdeportivo.dao.SocioDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class KpiDrilldownView extends Stage {

    public KpiDrilldownView() {

        SocioDAO dao = new SocioDAO();

        // 🔥 EJES
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Fecha de alta");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Número de socios");

        // 🔥 GRÁFICO
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Evolución de socios");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Altas de socios");

        // 🔥 DATOS
        List<String[]> datos = dao.obtenerEvolucionSocios();

        for (String[] d : datos) {
            series.getData().add(
                    new XYChart.Data<>(d[0], Integer.parseInt(d[1]))
            );
        }

        chart.getData().add(series);

        // 🔥 CONTENEDOR
        VBox root = new VBox(20, chart);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 700, 500);

        this.setTitle("Detalle KPI - Socios");
        this.setScene(scene);
    }
}