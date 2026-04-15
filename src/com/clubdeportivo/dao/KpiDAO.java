package com.clubdeportivo.dao;

import com.clubdeportivo.config.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public List<String[]> getSociosPendientes(int mes, int anio) {

        List<String[]> lista = new ArrayList<>();

        String sql = "{CALL getSociosPendientes(?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, anio);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("nombre"),
                        rs.getString("apellidos")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<String[]> getSociosConInvitaciones() {

        List<String[]> lista = new ArrayList<>();

        String sql = "{CALL getSociosConInvitaciones()}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        String.valueOf(rs.getInt("invitaciones_restantes"))
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}