package com.telcel.notifica.carga.service;

import clientealarma.ClienteALARMA;
import com.telcel.notifica.carga.factura.Factura;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmsService {

    private static final Logger logger = Logger.getLogger(SmsService.class.getName());

    public boolean enviar(Factura factura, List<Factura> contactos) {

        boolean enviado = false;
        logger.info("INICIA ENVIO DE MENSAJE DE TEXTO SMS...");
        String mensaje = crearMensaje(factura);
        for (Factura contacto : contactos) {
            if (contacto.getTelefono() == null
                    || contacto.getTelefono().isBlank()) {
                continue;
            }
            logger.info("Telefono destino: "
                    + contacto.getTelefono());

            String respuesta = enviarSms(
                            contacto.getTelefono(),
                            mensaje);

            if ("0".equals(respuesta)) {
                enviado = true;
                logger.info(
                        "SMS enviado correctamente al telefono "
                                + contacto.getTelefono());
            } else {
                logger.warning(
                        "No fue posible enviar SMS al telefono "
                                + contacto.getTelefono()
                                + ". Respuesta: "
                                + respuesta);
            }
        }
        logger.info("TERMINO ENVIO DE MENSAJE DE TEXTO SMS...");
        return enviado;
    }

    private String crearMensaje(Factura factura) {
        return "Se cargo una factura " + factura.getNombre()
                        + " num: "
                        + factura.getFactura()
                        + " con fecha: "
                        + factura.getFecha()
                        + " monto: $"
                        + factura.getMontoCompra()
                        + " Obs: "
                        + factura.getObservaciones();

    }

    private String enviarSms(String telefono, String mensaje) {
        try {
            ClienteALARMA cliente = new ClienteALARMA();
            String respuesta = cliente.enviarSMSConCurl(telefono, mensaje);
            logger.info("Respuesta SMS: " + respuesta);
            return respuesta;

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error enviando SMS", ex);
            return "-1";

        }
    }
}