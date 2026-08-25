package com.telcel.notifica.carga.factura;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.telcel.notifica.carga.provider.DatabaseProvider;
import com.telcel.notifica.carga.provider.ProviderFactory;
import com.telcel.notifica.carga.mapper.FacturaMapper;

public class ConsultasDAO {

    private Connection con;

    private final DatabaseProvider provider = ProviderFactory.getProvider();


    public List<Factura> consulta() throws SQLException {
        List<Factura> carga = new ArrayList<>();
        try {
            this.con = provider.getConnection();
            String sql = provider.getConsultarFacturas();
            System.out.println("Consultando facturas: " + sql);
            PreparedStatement pstm = this.con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            FacturaMapper mapper = provider.getFacturaMapper();

            while (rs.next()) {
                Factura factura = mapper.map(rs);
                carga.add(factura);
                System.out.println(
                        "[BD] Factura=" + factura.getFactura()
                                + " | Cadena=" + factura.getNombre()
                                + " | Región=" + factura.getRegion()
                                + " | Fecha=" + factura.getFecha());

            }

            System.out.println("[BD] Total de facturas obtenidas: " + carga.size());

            rs.close();
            pstm.close();
            this.con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(
                    "Hubo un error en la BD al obtener info: "
                            + ex.getMessage());
        }

        return carga;

    }


    public void actualiza(Set<String> notificadas) throws SQLException {
        try {
            this.con = provider.getConnection();
            String sql = provider.getActualizarFactura();
            PreparedStatement pstm = this.con.prepareStatement(sql);
            for (String datos : notificadas) {
                String[] tokens = datos.split(",");
                if (Integer.parseInt(tokens[1]) == 0) {
                    pstm.setInt(1, 0);
                    pstm.setString(2, tokens[0]);
                    pstm.setString(3, tokens[2]);
                    int registros = pstm.executeUpdate();
                    if (registros > 0) {
                        System.out.println(
                                "[BD] Factura actualizada: "
                                        + tokens[0]);
                    } else {
                        System.out.println(
                                "[BD] No se encontró la factura: "
                                        + tokens[0]);
                    }
                } else {
                    System.out.println(
                            "[BD] Factura NO actualizada: "
                                    + tokens[0]);
                }
            }
            pstm.close();
            this.con.close();
            System.out.println("[BD] Actualización terminada.");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(
                    "Hubo un error en la BD al actualizar información: "
                            + ex.getMessage());
        }

    }
}