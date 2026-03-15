package com.clubdeportivo.testing;

import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.model.Socio;

public class TestUpdateSocio {

    public static void main(String[] args) {

        Socio socio = new Socio();

        socio.setIdSocio(1); // ID que existe en la BD
        socio.setNombre("Juan");
        socio.setApellidos("Perez Modificado");
        socio.setEmail("juan@email.com");
        socio.setTelefono("600111111");
        socio.setActivo(true);
        socio.setIdTipoCuota(2);

        SocioDAO socioDAO = new SocioDAO();
        socioDAO.actualizarSocio(socio);

    }
}