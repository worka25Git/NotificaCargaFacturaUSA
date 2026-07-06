package com.telcel.notifica.carga.service;

import com.telcel.notifica.carga.factura.ConsultasDAO;
import com.telcel.notifica.carga.factura.Factura;
import com.telcel.notifica.carga.utils.GetUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class FacturaService {

    private final ConsultasDAO dao = new ConsultasDAO();

    public List<Factura> obtenerFacturas() throws SQLException {
        return dao.consulta();
    }

    public List<Factura> obtenerContactos(Factura factura) throws Exception {
        return GetUtil.getDestinatariosFactura(
                factura.getNombre());
    }

    public void actualizarFacturas(Set<String> facturas)
            throws SQLException {
        dao.actualiza(facturas);
    }
}