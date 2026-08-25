package com.telcel.notifica.carga.service;

import com.telcel.mail.EnviaMail;
import com.telcel.notifica.carga.factura.Factura;
import com.telcel.notifica.carga.utils.ConfigReader;
import com.telcel.notifica.carga.utils.GetUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CorreoService {

    private static final Logger logger = Logger.getLogger(CorreoService.class.getName());

    public CorreoService() {


    }

    public void enviar(Factura factura, List<Factura> contactos)
            throws Exception {

        String destinatarios = obtenerDestinatarios(contactos);

        if (destinatarios.isBlank()) {
            logger.info("No hay destinatarios.");
            return;
        }
        String saludo = generarSaludo();
        String firma = generarFirma();
        String html = generarHtml(factura, saludo, firma);
        EnviaMail mail = crearClienteCorreo();

        configurarCorreo(mail,
                destinatarios,
                factura.getNombre(),
                html);

        mail.sendMultipart();

        logger.info("Correo enviado.");


    }

    private String generarSaludo() {
        int hora = Calendar.getInstance()
                .get(Calendar.HOUR_OF_DAY);
        if (hora < 12) {
            return "Buenos D&iacute;as";
        }
        if (hora < 19) {
            return "Buenas Tardes";
        }
        return "Buenas Noches";
    }

    private String generarFirma() {

        return GetUtil.getFirmaComercioElectronico(
                ConfigReader.get("firma.telefono1"),
                ConfigReader.get("firma.extension"),
                ConfigReader.get("firma.telefono2"));

    }

    private String generarHtml(Factura factura, String saludo, String firma) {

        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head></head>");
        html.append("<body>");
        html.append("<div style=\"color:'navy';font-family:'Arial';font-size:18px\">");

        html.append(saludo);
        html.append(":<br><br>");
        html.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        html.append("Se ha cargado una factura a la cadena ");
        html.append(factura.getNombre());
        html.append(" num: <b>");
        html.append(factura.getFactura());
        html.append("</b>");

        html.append(" con fecha: ");
        html.append(factura.getFecha());
        html.append(" por un monto de: <b>$");
        html.append(factura.getMontoCompra());
        html.append(" Dls</b>");
        html.append(" con estatus: ");
        html.append(factura.getObservaciones());
        html.append(".");
        html.append("<br><br>");

        html.append("<table align='center'><tr><td align='center'>");
        html.append(firma);
        html.append("</td></tr></table>");
        html.append("</div></body></html>");
        return html.toString();

    }

    private void configurarCorreo(EnviaMail correo,
                                  String destinatarios,
                                  String cadena,
                                  String html) throws Exception {
        correo.setFrom(ConfigReader.get("correo.remitente"));
        correo.setTo(destinatarios);
        correo.setSubject("NOTIFICACION DE FACTURA PARA " + cadena);
        correo.addContent(html);

    }

    private EnviaMail crearClienteCorreo() {

        return new EnviaMail(
                ConfigReader.get("correo.usuario"),
                ConfigReader.get("correo.password"),
                ConfigReader.get("correo.host"),
                generarProperties());
    }

    private Properties generarProperties() {
        Properties smtp = new Properties();
        try {
            String port = ConfigReader.get("correo.smtp.port");
            if (port != null && !port.isBlank()) {
                smtp.put("mail.smtp.port", port);
            }

            String auth = ConfigReader.get("correo.smtp.auth");
            if (auth != null && !auth.isBlank()) {
                smtp.put("mail.smtp.auth", auth);
            }

            String tls = ConfigReader.get("correo.smtp.starttls.enable");
            if (tls != null && !tls.isBlank()) {
                smtp.put("mail.smtp.starttls.enable", tls);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE,
                    "Error al generar las propiedas para el envio del correo ",
                    ex);
        }
        return smtp;
    }


    private String obtenerDestinatarios(List<Factura> contactos) {
        
        StringBuilder correos = new StringBuilder();

        for (Factura contacto : contactos) {
            if (contacto.getCorreo() != null &&
                    !contacto.getCorreo().isBlank()) {

                if (correos.length() > 0) {
                    correos.append(", ");
                }

                correos.append(contacto.getCorreo());
            }
        }
        return correos.toString();
    }
}
