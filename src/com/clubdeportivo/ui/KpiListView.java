package com.clubdeportivo.ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class KpiListView extends Stage {

    public KpiListView(String titulo, String[] columnas, List<String[]> datos) {

        TableView<String[]> table = new TableView<>();

        for (int i = 0; i < columnas.length; i++) {
            final int colIndex = i;

            TableColumn<String[], String> col = new TableColumn<>(columnas[i]);
            col.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(data.getValue()[colIndex])
            );

            table.getColumns().add(col);
        }

        table.setItems(FXCollections.observableArrayList(datos));

        VBox root = new VBox(table);

        Scene scene = new Scene(root, 400, 300);

        this.setTitle(titulo);
        this.setScene(scene);
    }
}