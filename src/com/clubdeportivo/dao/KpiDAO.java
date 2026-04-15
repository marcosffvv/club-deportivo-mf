package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KpiDAO {

    public int getTotalSocios() {
        return ejecutarProcedimiento("CALL club_deportivo.getNumeroSocios()");
    }

    public int getSociosBasica() {
        return ejecutarProcedimiento("CALL club_deportivo.getSociosBasica()");
    }

    public int getSociosGold() {
        return ejecutarProcedimiento("CALL club_deportivo.getSociosGold()");
    }

    public int getSociosPremium() {
        return ejecutarProcedimiento("CALL club_deportivo.getSociosPreium()");
    }

    private int ejecutarProcedimiento(String sql) {

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}