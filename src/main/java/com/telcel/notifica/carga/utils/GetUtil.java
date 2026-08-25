package com.telcel.notifica.carga.utils;

import com.telcel.notifica.carga.factura.Factura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GetUtil {
    private static final String RUTACONF = ConfigReader.get("ruta.destinatarios");

    public static List<Factura> getDestinatariosFactura(String cadena) throws Exception {
        System.out.println("entra a get destinatarios con Cadena " + cadena);
        List<Factura> dest = new ArrayList<Factura>();
        File destFile = new File(RUTACONF);

        if (destFile.exists()) {
            try (BufferedReader bf = new BufferedReader(new FileReader(destFile));) {
                String linea = "";
                while ((linea = bf.readLine().trim()) != null && !linea.equals("") &&
                        !linea.contains("final")) {
                    linea = linea.trim();

                    if (linea.isEmpty() || linea.contains("final")) {
                        continue;
                    }

                    String[] linDest = linea.split(",");
                    if (cadena.contains(linDest[0])) {
                        System.out.println(linDest[0] + " - " + cadena);

                        /*
                        if ((!linDest[2].equals("") || !linDest[3].equals("")) && (
                                !dest.contains(linDest[2]) || !dest.contains(linDest[3]))) {
                            dest.add(new Factura(linDest[0], linDest[2], linDest[3]));
                            System.out.println("Se agrego correo a la lista");
                        }
                         */
                        if (!linDest[2].isBlank() || !linDest[3].isBlank()) {
                            dest.add(
                                    new Factura(
                                            linDest[0],
                                            linDest[2],
                                            linDest[3]));

                        }
                    }
                }

            } catch (Exception e) {
                throw e;
            }
        } else {
            System.out.println("No se encontro el archivo de Destinaratios");
            return dest;
        }
        return dest;
    }

    public static String getFirmaComercioElectronico(String telefono1, String ext, String telefono2) {
        StringBuilder html = new StringBuilder("<i><FONT SIZE=3 COLOR=navy Face='Arial'>Cualquier duda u observaci&oacute;n, favor de enviarla a la cuenta &nbsp;&nbsp;<b><a href='mailto:comercio.electronico@mail.telcel.com'>comercio.electronico@mail.telcel.com</a></b></font></i>");
        html.append("<br><br><b><font size=2 color=navy face=Arial>Comercio Electr&oacute;nico M&oacute;vil</font></b>");
        html.append("<br><br><font size=2 color=blue face=Wingdings>( &nbsp;</font><font size=2 color=navy face=Arial style=italic>" + telefono1 + "&nbsp;&nbsp; Ext: " + ext + "</font>");
        html.append("<br><br><font size=2 color=blue face=Wingdings>( &nbsp;</font><font size=2 color=navy face=Arial style=italic>Directo: " + telefono2 + "</font>");
        html.append("<br><br><font size=2 color=blue face=Wingdings>* &nbsp;</font><font size=2 color=navy face=Arial>comercio.electronico@mail.telcel.com</font>");
        return html.toString();
    }
}

