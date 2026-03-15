package com.clubdeportivo.testing;

import com.clubdeportivo.dao.SocioDAO;

public class TestDeleteSocio {

    public static void main(String[] args) {

        SocioDAO socioDAO = new SocioDAO();

        socioDAO.eliminarSocio(2); // ID del socio a eliminar

    }
}