package com.telcel.notifica.carga.sql;

public final class OracleSQL {

    private OracleSQL() {
    }

    public static final String CONSULTA_FACTURAS =
            "SELECT " +
                    "LF.ROWID AS ID_FACTURA, " +
                    "CL.NOMBRE AS NOMBRE, " +
                    "LF.FACTURA AS FACTURA, " +
                    "LF.FECHA AS FECHA, " +
                    "LF.OBSERVACIONES AS OBSERVACIONES, " +
                    "LF.MONTO_COMPRA AS MONTO_COMPRA, " +
                    "LF.REGION AS REGION, " +
                    "LF.STATUS AS STATUS, " +
                    "TO_CHAR(LF.FECHA_TELCEL,'ddmmyyyyhh24miss') AS FECHA_TELCEL " +
                    "FROM LOG_FAC_SAP LF " +
                    "INNER JOIN CLIENTE CL " +
                    "ON LF.RFC = CL.RFC " +
                    "INNER JOIN REGION_BOLSA RB " +
                    "ON CL.ID_CLIENTE = RB.ID_CLIENTE " +
                    "AND RB.REGION = LF.REGION " +
                    "WHERE LF.FECHA_TELCEL > SYSDATE - 1 " +
                    "AND LF.MENSAJE = 1 " +
                    "ORDER BY LF.FECHA_TELCEL ASC";


    public static final String CONSULTA_FACTURAS_CPS =
            "SELECT " +
                    "LF.ROWID AS ID_FACTURA, " +
                    "CL.NOMBRE AS NOMBRE, " +
                    "LF.FACTURA AS FACTURA, " +
                    "LF.FECHA AS FECHA, " +
                    "LF.OBSERVACIONES AS OBSERVACIONES, " +
                    "LF.MONTO_COMPRA AS MONTO_COMPRA, " +
                    "LF.REGION AS REGION, " +
                    "LF.STATUS AS STATUS, " +
                    "TO_CHAR(LF.FECHA_TELCEL,'ddmmyyyyhh24miss') AS FECHA_TELCEL " +
                    "FROM LOG_FAC_SAP_CPS LF " +
                    "INNER JOIN CLIENTE_CPS CL ON (LF.RFC = CL.RFC) " +
                    "INNER JOIN REGION_BOLSA_CPS RB ON (CL.ID_CLIENTE = RB.ID_CLIENTE AND RB.REGION = LF.REGION) " +
                    "WHERE LF.FECHA_TELCEL > SYSDATE - 30 AND LF.MENSAJE = 1 " +
                    "ORDER BY LF.FECHA_TELCEL ASC";

    public static final String ACTUALIZA_FACTURA =
            "UPDATE LOG_FAC_SAP " +
                    "SET MENSAJE=? " +
                    "WHERE FACTURA=? " +
                    "AND ROWID=?";

    public static final String ACTUALIZA_FACTURA_CPS =
            "UPDATE LOG_FAC_SAP_CPS " +
                    "SET MENSAJE=? " +
                    "WHERE FACTURA=? " +
                    "AND ROWID=?";
}