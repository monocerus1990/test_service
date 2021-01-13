/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Attendence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author unico
 */
public class AttendanceDAOImpl implements AttendanceDAO {

    private Connection conn;

    public AttendanceDAOImpl() throws Exception {
        this.conn = DataBaseDAO.getInstance().connect();
    }

    public AttendanceDAOImpl(Connection conn) throws Exception {
        if (conn == null) {
            throw new Exception("Connection is NULL");
        }
        this.conn = conn;
    }

    @Override
    public List<Attendence> getEmployeeAttendence(int employee_id, int from, int to) throws Exception {
        String sql = "SELECT * FROM `attendance` WHERE `employee_id` = " + employee_id + " AND `seconds` > " + from + " AND `seconds` < " + to + ";";
        ResultSet rs;
        List<Attendence> signList;
        boolean hasResult = false;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(sql);
            signList = new ArrayList();
            while (rs.next()) {
                hasResult = true;
                Attendence att = new Attendence();
                att.setEmployee_id(rs.getInt("employee_id"));
                att.setSeconds(rs.getInt("seconds"));
                signList.add(att);
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
            if (!hasResult) {
                throw new Exception("NoDataFromDatabase");
            }
        }
        return signList;
    }

    @Override
    public boolean signAttendence(int employee_id, int seconds) throws Exception {

        if (employee_id < 1 || seconds < 0) {
            throw new Exception("Missing/Error Required Parameters");
        }
        String sql = "INSERT INTO `attendance` VALUES (" + employee_id + "," + seconds + ");";
        boolean isInsert = false;
        Statement stmt = conn.createStatement();
        if (stmt.executeUpdate(sql) == 1) {
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            isInsert = true;
        }
        return isInsert;
    }

}
