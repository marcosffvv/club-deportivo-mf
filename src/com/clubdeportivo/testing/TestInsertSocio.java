package com.clubdeportivo.testing;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;

import java.time.LocalDate;

public class TestInsertSocio {

    public static void main(String[] args) {

        Socio socio = new Socio();

        socio.setNombre("Maria");
        socio.setApellidos("Gomez");
        socio.setEmail("maria@email.com");
        socio.setTelefono("600000000");
        socio.setFechaAlta(LocalDate.now());
        socio.setActivo(true);
        socio.setIdTipoCuota(2);

        SocioDAO socioDAO = new SocioDAO();
        socioDAO.insertarSocio(socio);

    }
}