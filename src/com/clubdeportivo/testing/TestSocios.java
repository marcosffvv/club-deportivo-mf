package com.clubdeportivo.testing;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;

import java.util.List;

public class TestSocios {

    public static void main(String[] args) {

        SocioDAO socioDAO = new SocioDAO();

        List<Socio> socios = socioDAO.obtenerTodos();

        for (Socio s : socios) {
            System.out.println(s.getNombre() + " " + s.getApellidos());
        }
    }
}