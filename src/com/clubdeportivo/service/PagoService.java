package com.clubdeportivo.service;

import com.clubdeportivo.dao.PagoDAO;
import com.clubdeportivo.dao.SocioDAO;
import com.clubdeportivo.dao.TipoCuotaDAO;
import com.clubdeportivo.model.Pago;
import com.clubdeportivo.model.Socio;
import com.clubdeportivo.model.TipoCuota;

import java.time.LocalDate;

public class PagoService {

    private PagoDAO pagoDAO = new PagoDAO();
    private SocioDAO socioDAO = new SocioDAO();
    private TipoCuotaDAO tipoCuotaDAO = new TipoCuotaDAO();

    // 🔥 REGISTRAR PAGO (MÉTODO QUE TE FALTABA)
    public boolean registrarPago(int idSocio, int mes, int anio) {

        // 1. Obtener socio
        Socio socio = socioDAO.obtenerPorId(idSocio);
        if (socio == null) return false;

        // 2. Obtener cuota
        TipoCuota cuota = tipoCuotaDAO.obtenerPorId(socio.getIdTipoCuota());
        if (cuota == null) return false;

        // 3. Comprobar si ya existe pago
        boolean yaExiste = pagoDAO.existePago(idSocio, mes, anio);
        if (yaExiste) return false;

        // 4. Crear pago automático con importe correcto
        Pago pago = new Pago(
                idSocio,
                mes,
                anio,
                LocalDate.now(),
                cuota.getPrecio()
        );

        // 5. Guardar
        pagoDAO.insertarPago(pago);

        return true;
    }

    // 🔹 ESTADO DE PAGO
    public String obtenerEstadoPago(int idSocio, int mes, int anio) {

        boolean existePago = pagoDAO.existePago(idSocio, mes, anio);

        if (existePago) {
            return "AL CORRIENTE";
        } else {
            return "PENDIENTE";
        }
    }
}