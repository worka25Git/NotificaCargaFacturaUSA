package com.telcel.notifica.carga.database;

import com.telcel.notifica.carga.utils.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;

public class InformixConnection {

    private InformixConnection() {
    }

    public static Connection getConnection() throws Exception {

        Class.forName(ConfigReader.get("db.informix.driver"));

        return DriverManager.getConnection(
                ConfigReader.get("db.informix.url"),
                ConfigReader.get("db.informix.user"),
                ConfigReader.get("db.informix.password"));
    }

}