package com.clubdeportivo.service;

import com.clubdeportivo.dao.InvitacionDAO;
import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.dao.TipoCuotaDAO;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.model.TipoCuota;

import java.time.LocalDate;

public class InvitacionService {

    private InvitacionDAO invitacionDAO = new InvitacionDAO();
    private SocioDAO socioDAO = new SocioDAO();
    private TipoCuotaDAO tipoCuotaDAO = new TipoCuotaDAO();

    // USAR INVITACIÓN
    public boolean usarInvitacion(int idSocio) {

        int mes = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();

        // Obtener socio
        Socio socio = socioDAO.obtenerPorId(idSocio);
        if (socio == null) return false;

        // Obtener cuota
        TipoCuota cuota = tipoCuotaDAO.obtenerPorId(socio.getIdTipoCuota());
        if (cuota == null) return false;

        int maxInvitaciones = cuota.getMaxInvitacionesMes();

        // Obtener usadas
        int usadas = invitacionDAO.obtenerUsadas(idSocio, mes, anio);

        // Validar límite
        if (usadas >= maxInvitaciones) {
            return false;
        }

        // Insertar o actualizar
        if (usadas == 0) {
            invitacionDAO.insertar(idSocio, mes, anio, 1);
        } else {
            invitacionDAO.actualizar(idSocio, mes, anio, usadas + 1);
        }

        return true;
    }

    // Obtener disponibles
    public int obtenerDisponibles(int idSocio) {

        int mes = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();

        Socio socio = socioDAO.obtenerPorId(idSocio);
        TipoCuota cuota = tipoCuotaDAO.obtenerPorId(socio.getIdTipoCuota());

        int max = cuota.getMaxInvitacionesMes();
        int usadas = invitacionDAO.obtenerUsadas(idSocio, mes, anio);

        return max - usadas;
    }
}