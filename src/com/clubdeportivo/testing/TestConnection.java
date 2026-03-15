package com.clubdeportivo.testing;

import com.clubdeportivo.config.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn != null) {
                System.out.println("Conexion a MySQL correcta");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}