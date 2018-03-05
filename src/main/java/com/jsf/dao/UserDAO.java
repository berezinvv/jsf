package com.jsf.dao;

import com.jsf.db.DataConnection;
import com.jsf.managedbean.UserMB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static Connection connection;
    private static PreparedStatement ps;

    public static boolean signIn(String email, String password) {

        try {
            connection = DataConnection.getConnection();
            ps = connection.prepareStatement("Select email, password from Users where email = ? and password = ?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            return false;
        } finally {
            DataConnection.close(connection);
        }
        return false;
    }

    public static boolean createNewUser(UserMB userMB) {
        String insertTableUsers = "INSERT INTO USERS"
                + "(email, password, userName) VALUES"
                + "(?,?,?)";

        try {
            connection = DataConnection.getConnection();
            ps = connection.prepareStatement(insertTableUsers);
            ps.setString(1, userMB.getEmail());
            ps.setString(2, userMB.getPassword());
            ps.setString(3, userMB.getUserName());

            ps.executeUpdate();

        } catch (SQLException ex) {
            return false;
        } finally {
            DataConnection.close(connection);
        }
        return true;
    }
}
