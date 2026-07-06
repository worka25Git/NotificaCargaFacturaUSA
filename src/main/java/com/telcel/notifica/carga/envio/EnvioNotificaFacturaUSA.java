package com.telcel.notifica.carga.envio;

import com.telcel.notifica.carga.factura.Factura;
import com.telcel.notifica.carga.service.CorreoService;
import com.telcel.notifica.carga.service.FacturaService;
import com.telcel.notifica.carga.service.SmsService;
import com.telcel.notifica.carga.utils.ConfigReader;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

public class EnvioNotificaFacturaUSA {

    private static Logger logger = Logger.getLogger(EnvioNotificaFacturaUSA.class.getName());

    private final FacturaService facturaService = new FacturaService();

    private final CorreoService correoService = new CorreoService();

    private final SmsService smsService = new SmsService();

    private static Handler handler = null;

    private static SimpleFormatter formatter = new SimpleFormatter();

    private List<Factura> consultaFacturas = new ArrayList<Factura>();

    private Set<String> factEnvia = new HashSet<String>();

    static {
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
        Locale locale = new Locale("ES", "MX");
        TimeZone.setDefault(tz);
        Locale.setDefault(locale);
    }

    public static void main(String[] args) throws SQLException, Exception {
        EnvioNotificaFacturaUSA prueba = new EnvioNotificaFacturaUSA();
        prueba.obtenerContactosFactura();
    }

    public void obtenerContactosFactura() throws Exception {

        try {
            DateFormat format = new SimpleDateFormat("'BitacoraNotificaFacturaUSA_'yyyyMMdd'.log'");
            String cadenaArchivo = format.format(new Date());
            String rutaLog = ConfigReader.get("ruta.logs");

            handler = new FileHandler( rutaLog + cadenaArchivo, true);
            handler.setFormatter(formatter);
            logger.addHandler(handler);

            logger.setLevel(Level.ALL);
            logger.info("======================== INICIA PROCESO ========================");
            logger.info("COMENZANDO A OBTENER LAS FACTURAS...");

            this.consultaFacturas = facturaService.obtenerFacturas();
            logger.info("TERMINO DE OBTENER LAS FACTURAS...");

            if (!consultaFacturas.isEmpty()) {
                procesarFacturas();
                logger.info("INICIO DE ACTUALIZACION DE FACTURAS...");

                facturaService.actualizarFacturas(factEnvia);
                logger.info("TERMINO DE ACTUALIZAR LAS FACTURAS...");
            } else {
                logger.info("No hay facturas para enviar.");
            }

            logger.info("======================== TERMINA PROCESO ========================");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fallo proceso.", e);
        }
    }

    private void procesarFacturas() throws Exception {
        for (Factura factura : consultaFacturas) {

            procesarFactura(factura);

        }
    }

    private void procesarFactura(Factura facturaCarg) throws Exception {

        logger.info("COMENZANDO A OBTENER LOS CONTACTOS...");
        List<Factura> dest = facturaService.obtenerContactos(facturaCarg);

        correoService.enviar(facturaCarg, dest);

        factEnvia.add(facturaCarg.getFactura()
                + ",0,"
                + facturaCarg.getIdFactura());

        boolean smsEnviado = smsService.enviar(facturaCarg, dest);
        if (smsEnviado) {
            factEnvia.add(
                    facturaCarg.getFactura()
                            + ",0,"
                            + facturaCarg.getIdFactura());

        }
    }
}