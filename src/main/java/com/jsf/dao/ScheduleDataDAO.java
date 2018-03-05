package com.jsf.dao;

import com.jsf.db.DataConnection;
import com.jsf.model.ScheduleData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDataDAO {

    private static Connection connection;
    private static PreparedStatement ps;

    public static boolean findAll() {
        String selectTableScheduleData = "SELECT * FROM ScheduleData";

        try {
            connection = DataConnection.getConnection();
            ps = connection.prepareStatement(selectTableScheduleData);

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

    public static boolean deleteAll() {
        String deleteTableScheduleData = "DELETE FROM ScheduleData";

        try {
            connection = DataConnection.getConnection();
            ps = connection.prepareStatement(deleteTableScheduleData);

            ps.executeUpdate();

        } catch (SQLException ex) {
            return false;
        } finally {
            DataConnection.close(connection);
        }
        return true;
    }

    public static boolean create(ScheduleData scheduleData) {
        String insertTableScheduleData = "INSERT INTO ScheduleData"
                + "(date, note, value) VALUES"
                + "(?,?,?)";

        try {
            connection = DataConnection.getConnection();
            ps = connection.prepareStatement(insertTableScheduleData);
            ps.setLong(1, scheduleData.getDate());
            ps.setString(2, scheduleData.getNote());
            ps.setInt(3, scheduleData.getValue());

            ps.executeUpdate();

        } catch (SQLException ex) {
            return false;
        } finally {
            DataConnection.close(connection);
        }
        return true;
    }

    public static List<ScheduleData> findAllByPeriod(Long dateFrom, Long dateTo) {
        String selectTableScheduleData = "SELECT * FROM ScheduleData where date >= ? and date <= ?";

        List<ScheduleData> scheduleDataList = new ArrayList<>();

        try {
            connection = DataConnection.getConnection();
            ps = connection.prepareStatement(selectTableScheduleData);
            ps.setLong(1, dateFrom);
            ps.setLong(2, dateTo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ScheduleData scheduleData = new ScheduleData();
                scheduleData.setId(rs.getInt("id"));
                scheduleData.setDate(rs.getLong("date"));
                scheduleData.setNote(rs.getString("note"));
                scheduleData.setValue(rs.getInt("value"));
                scheduleDataList.add(scheduleData);
            }
            //return scheduleDataList;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return scheduleDataList;
    }
}
