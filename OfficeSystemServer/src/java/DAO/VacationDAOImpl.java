/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Vacation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author QAQ
 */
public class VacationDAOImpl implements VacationDAO {

    private Connection conn;

    public VacationDAOImpl() throws Exception {
        conn = DataBaseDAO.getInstance().connect();
    }

    public VacationDAOImpl(Connection conn) throws Exception {
        if (conn == null) {
            throw new Exception("Connection is NULL");
        }
        this.conn = conn;
    }

    @Override
    public boolean applyVacation(int employee_id, String date_from, String date_end, int type, String description) throws Exception {
        // type:1 = sick type:2 = leave
        if (employee_id < 1 || date_from == null || date_from.equals("") || date_end == null || date_end.equals("") || type < 1) {
            throw new Exception("Missing Required Parameters");
        }
        if (description == null) {
            description = "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from = simpleDateFormat.parse(date_from);
        Date end = simpleDateFormat.parse(date_end);
        if (end.before(from)) {
            throw new Exception("EndDateIsBeforeStartDate");
        }
        String query = "INSERT INTO `vacation`"
                + "(`employee_id`, "
                + "`date_from`, "
                + "`date_end`, "
                + "`type`, "
                + "`is_salary`, "
                + "`status`, "
                + "`description`) "
                + "VALUES ("
                + employee_id + ","
                + "\"" + date_from + "\","
                + "\"" + date_end + "\","
                + "" + type + ","
                + "" + false + ","
                + "" + 0 + ","// 0 待审批，-1 不同意，1同意
                + "\"" + description + "\""
                + ")";
        boolean isInsert = false;
        Statement stmt = conn.createStatement();
        if (stmt.executeUpdate(query) == 1) {
            String selectSql = "SELECT LAST_INSERT_ID();";
            ResultSet rs = stmt.executeQuery(selectSql);
            int insertId = 0;
            boolean isUpdate = false;
            while (rs.next()) {
                insertId = rs.getInt("LAST_INSERT_ID()");
            }
            if (insertId > 0) {
                String update_vacation_limit = "UPDATE `vacation_limit` "
                        + "SET "
                        + "remain_days = "
                        + "remain_days-"
                        + "(SELECT datediff((SELECT date_end FROM vacation WHERE id = " + insertId + "),"
                        + "(SELECT date_from FROM vacation WHERE id = " + insertId + ")))-1 "
                        + "WHERE "
                        + "employee_id = "
                        + "(SELECT employee_id FROM vacation WHERE id = " + insertId + ")";
                isUpdate = stmt.executeUpdate(update_vacation_limit) == 1;
            }
            if (isUpdate) {
                conn.close();
                DataBaseDAO.getInstance().close();
                stmt.close();
                isInsert = true;
            }
        }
        return isInsert;
    }

    @Override
    public List<JSONObject> getSubVacationList() throws Exception {

        List<JSONObject> jsonList = new ArrayList();
        String str = "SELECT "
                + "vacation.employee_id,"
                + "employee.employee_name,"
                + "vacation_limit.remain_days, "
                + "COUNT(if(vacation.status = 0,true,null)) AS `count` "
                + "FROM "
                + "vacation,employee,vacation_limit "
                + "WHERE "
                + "vacation.employee_id = employee.employee_id "
                + "AND "
                + "vacation_limit.employee_id = vacation.employee_id "
                + "GROUP BY "
                + "vacation.employee_id";
        ResultSet rs;
        boolean isResult;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(str);
            isResult = false;
            while (rs.next()) {
                isResult = true;
                JSONObject json = new JSONObject();
                json.put("employee_id", rs.getInt("employee_id"));
                json.put("employee_name", rs.getString("employee_name"));
                json.put("remain_days", rs.getInt("remain_days"));
                json.put("count", rs.getInt("count"));
                jsonList.add(json);
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }
        if (!isResult) {
            throw new Exception("NoDataFromDatabase");
        }
        return jsonList;
    }

    @Override
    public List<Vacation> getAllVaction() throws Exception {

        List<Vacation> resultList = new ArrayList();
        String query = "SELECT  `vacation`.`id`,`vacation`.`employee_id`, "
                + "`employee`.`employee_name`,"
                + "`vacation`.`date_from`,"
                + "`vacation`.`date_end`,"
                + "`vacation`.`type`,"
                + "`vacation`.`description`,"
                + "`vacation`.`is_salary`,"
                + "`vacation`.`status` "
                + "FROM `vacation`,`employee` "
                + "WHERE `vacation`.`employee_id` = `employee`.`employee_id` ORDER BY `vacation`.`date_from` desc";
        ResultSet rs;
        boolean isResult;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(query);
            isResult = false;
            while (rs.next()) {
                isResult = true;
                Vacation vacation = new Vacation();
                vacation.setId(rs.getInt("id"));
                vacation.setEmployee_id(rs.getInt("employee_id"));
                vacation.setEmployee_name(rs.getString("employee_name"));
                vacation.setDate_from(rs.getString("date_from"));
                vacation.setDate_end(rs.getString("date_end"));
                vacation.setType(rs.getInt("type"));
                vacation.setDescription(rs.getString("description"));
                vacation.setIs_salary(rs.getBoolean("is_salary"));
                vacation.setStatus(rs.getInt("status"));
                resultList.add(vacation);
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }
        if (!isResult) {
            throw new Exception("NoDataFromDatabase");
        }
        return resultList;
    }

    @Override
    public boolean ApprovalVacation(int id, boolean is_salary, int status) throws Exception {

        if (id < 1) {
            throw new Exception("Wrong Employee ID");
        }
        String query = "UPDATE `vacation` SET`is_salary`=" + is_salary + ",`status`=" + status + " WHERE `id`=" + id;
        boolean isUpdate;
        try (Statement stmt = conn.createStatement()) {
            isUpdate = false;
            if (stmt.executeUpdate(query) == 1) {
                isUpdate = true;
                if (status == -1) {
                    String update_vacation_limit = "UPDATE `vacation_limit` "
                            + "SET "
                            + "remain_days = "
                            + "remain_days+"
                            + "(SELECT datediff((SELECT date_end FROM vacation WHERE id = " + id + "),"
                            + "(SELECT date_from FROM vacation WHERE id = " + id + ")))+1 "
                            + "WHERE "
                            + "employee_id = "
                            + "(SELECT employee_id FROM vacation WHERE id = " + id + ")";
                    isUpdate = stmt.executeUpdate(update_vacation_limit) == 1;
                }
                if (status == -2) {
                    String update_vacation_limit = "UPDATE "
                            + "`vacation_limit` "
                            + "SET "
                            + "remain_days = remain_days + 1 + "
                            + "(IF ("
                            + "(SELECT datediff((SELECT DATE_FORMAT(NOW(), '%Y-%m-%d')),(SELECT date_from FROM vacation WHERE id = " + id + ")))<0,"
                            + "(SELECT datediff((SELECT date_end FROM vacation WHERE id = " + id + "),(SELECT date_from FROM vacation WHERE id = " + id + "))),"
                            + "(SELECT datediff((SELECT DATE_FORMAT(NOW(), '%Y-%m-%d')),(SELECT date_from FROM vacation WHERE id = " + id + "))))) "
                            + "WHERE "
                            + "employee_id = (SELECT employee_id FROM vacation WHERE id = " + id + ");";
                    isUpdate = stmt.executeUpdate(update_vacation_limit) == 1;
                }

            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
        }
        return isUpdate;
    }

    @Override
    public List<Vacation> getEmployeeVacation(int employee_id) throws Exception {
        List<Vacation> resultList = new ArrayList();
        String query = "SELECT  `vacation`.`id`,`vacation`.`employee_id`, "
                + "`employee`.`employee_name`,"
                + "`vacation`.`date_from`,"
                + "`vacation`.`date_end`,"
                + "`vacation`.`type`,"
                + "`vacation`.`description`,"
                + "`vacation`.`is_salary`,"
                + "`vacation`.`status` "
                + "FROM `vacation`,`employee` "
                + "WHERE `vacation`.`employee_id` = `employee`.`employee_id` "
                + "AND `employee`.`employee_id` = " + employee_id + " ORDER BY `vacation`.`date_from` desc";
        ResultSet rs;
        boolean isResult;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(query);
            isResult = false;
            while (rs.next()) {
                isResult = true;
                Vacation vacation = new Vacation();
                vacation.setId(rs.getInt("id"));
                vacation.setEmployee_id(rs.getInt("employee_id"));
                vacation.setEmployee_name(rs.getString("employee_name"));
                vacation.setDate_from(rs.getString("date_from"));
                vacation.setDate_end(rs.getString("date_end"));
                vacation.setType(rs.getInt("type"));
                vacation.setDescription(rs.getString("description"));
                vacation.setIs_salary(rs.getBoolean("is_salary"));
                vacation.setStatus(rs.getInt("status"));
                resultList.add(vacation);
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }
        if (!isResult) {
            throw new Exception("NoDataFromDatabase");
        }
        return resultList;
    }

    @Override
    public int getUserRemainDays(int employee_id) throws Exception {
        if (employee_id < 1) {
            throw new Exception("Wrong Employee ID");
        }
        int remain_days = -1;
        String sql = "SELECT * FROM `vacation_limit` WHERE `employee_id` = " + employee_id + " AND `year` = (SELECT DATE_FORMAT(NOW(), '%Y'))";
        ResultSet rs;
        boolean isResult;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(sql);
            isResult = false;
            while (rs.next()) {
                isResult = true;
                remain_days = rs.getInt("remain_days");
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }
        if (!isResult) {
            throw new Exception("NoDataFromDatabase");
        }
        return remain_days;
    }

}
