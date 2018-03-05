package com.jsf.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnection {
    private static final String DB_DRIVER = "org.sqlite.JDBC";
    private static final String DB_CONNECTION = "jdbc:sqlite:schedule.db";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->"
                    + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        if (con == null) return;
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
