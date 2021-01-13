/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Employee;
import DTO.EmployeeDetails;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author QAQ
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    private Connection conn;

    public EmployeeDAOImpl() throws Exception {
        conn = DataBaseDAO.getInstance().connect();
    }

    public EmployeeDAOImpl(Connection conn) throws Exception {
        if (conn == null) {
            throw new Exception("Connection is NULL");
        }
        this.conn = conn;
    }

    @Override
    public Employee register(String employee_account, String employee_name, int department_id, int position_id, String password, int status, String date) throws Exception {
        Employee emp = null;
        employee_account = Util.Utils.replceSymbol(employee_account);
        password = BCrypt.hashpw(Util.Utils.replceSymbol(password), BCrypt.gensalt());
        if (employee_account == null || employee_name == null || password == null) {
            throw new Exception("Missing Required Parameters");
        }
        if (employee_account.equals("") || employee_name.equals("") || department_id < 1 || position_id < 1 || password.equals("") || status < 0) {
            throw new Exception("Missing Required Parameters");
        }
        if (date == null) {
            date = "";
        }
        int eid = 0;
        String query = "SELECT * FROM `employee` WHERE employee_account = \"" + employee_account + "\"";
        String sql = "INSERT INTO `employee`("
                + "`employee_account`,"
                + "`employee_name`, "
                + "`department_id`, "
                + "`position_id`, "
                + "`password`,"
                + "`status`,"
                + "`date`"
                + ") VALUES "
                + "(\"" + employee_account + "\","
                + "\"" + employee_name + "\","
                + "\"" + department_id + "\","
                + "\"" + position_id + "\","
                + "\"" + password + "\","
                + "\"" + status + "\","
                + "\"" + date + "\""
                + ");";
        String selectSql = "SELECT LAST_INSERT_ID();";
        ResultSet rs;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(query);
            if (!rs.next()) {
                if (stmt.executeUpdate(sql) == 1) {
                    rs = stmt.executeQuery(selectSql);
                    while (rs.next()) {
                        eid = rs.getInt("LAST_INSERT_ID()");
                    }
                } else {
                    throw new Exception("Register Failed!");
                }
            } else {
                throw new Exception("User Already Exists");
            }

            String insetDetailquery = "INSERT INTO `employee_details`"
                    + "(`employee_id`) "
                    + "VALUES (" + eid + ")";
            if (stmt.executeUpdate(insetDetailquery) != 1) {
                throw new Exception("CreateEmployeeDetailsFailed");
            } else {
                String reSelectEmployee = "SELECT "
                        + "`employee`.`employee_id`,"
                        + "`employee`.`employee_account`,"
                        + "`employee`.`employee_name`,"
                        + "`employee`.`status`,"
                        + "`employee`.`department_id`,"
                        + "`employee`.`position_id`,"
                        + "`employee`.`date`,"
                        + "`department`.`department_name`,"
                        + "`position`.`position_name`,"
                        + "`employee_details`.`genre`,"
                        + "`employee_details`.`birthday`,"
                        + "`employee_details`.`email`,"
                        + "`employee_details`.`address`"
                        + "FROM `employee`"
                        + "LEFT JOIN `department` ON `department`.`department_id` = `employee`.`department_id`"
                        + "LEFT JOIN `position` ON `position`.`position_id` = `employee`.`position_id`"
                        + "LEFT JOIN `employee_details` ON `employee_details`.`employee_id` = `employee`.`employee_id` "
                        + "WHERE `employee`.`employee_id` =" + eid;
                rs = stmt.executeQuery(reSelectEmployee);
                while (rs.next()) {
                    emp = new Employee();
                    EmployeeDetails empDetail = new EmployeeDetails();
                    String new_account = rs.getString("employee_account");
                    String new_name = rs.getString("employee_name");
                    int new_status = rs.getInt("status");
                    int new_department_id = rs.getInt("department_id");
                    String new_department_name = rs.getString("department_name");
                    int new_position_id = rs.getInt("position_id");
                    String new_position_name = rs.getString("position_name");
                    String new_date = rs.getString("date");
                    int new_genre = rs.getInt("genre");
                    String new_birthday = rs.getString("birthday");
                    String new_email = rs.getString("email");
                    String new_address = rs.getString("address");

                    emp.setEmployee_id(eid);
                    emp.setEmployee_account(new_account);
                    emp.setEmployee_name(new_name);
                    emp.setStatus(new_status);
                    emp.setDepartment_id(new_department_id);
                    emp.setDepartment_name(new_department_name);
                    emp.setPosition_id(new_position_id);
                    emp.setPosition_name(new_position_name);
                    emp.setDate(new_date);

                    empDetail.setGenre(new_genre);
                    empDetail.setBirthday(new_birthday);
                    empDetail.setEmail(new_email);
                    empDetail.setAddress(new_address);

                    emp.setEmployee_details(empDetail);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date myDate = new Date();
                int year = Integer.parseInt(sdf.format(myDate));
                String insert_vacation_limit = "INSERT INTO `vacation_limit`(`employee_id`, `year`, `total_days`, `remain_days`) VALUES (" + eid + "," + year + ",30,30);";
                if (stmt.executeUpdate(insert_vacation_limit) != 1) {
                    throw new Exception("CreateVacationLimitFailed");
                }
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }
        return emp;
    }

    @Override
    public Employee login(String employee_account, String password) throws Exception {
        if (employee_account == null || employee_account.equals("") || password == null || password.equals("")) {
            throw new Exception("Missing Required Parameters");
        }
        Employee emp = null;
        String psw = "";
        employee_account = Util.Utils.replceSymbol(employee_account);
        password = Util.Utils.replceSymbol(password);
        int eid = -1;
        String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";
        ResultSet rs;
        String employee_name = "";
        boolean isResult = false;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                isResult = true;
                psw = rs.getString("password");
                eid = rs.getInt("employee_id");
                employee_name = rs.getString("employee_name");
            }
            if (!isResult) {
                throw new Exception("NO SUCH ACCOUNT");
            }
            if (BCrypt.checkpw(password, psw)) {
                emp = new Employee();
                emp.setEmployee_id(eid);
                emp.setEmployee_name(employee_name);
                String sqlVacationLimit = "SELECT * FROM `vacation_limit` WHERE `employee_id` = " + eid + " AND `year` = (SELECT DATE_FORMAT(NOW(), '%Y'))";
                rs = stmt.executeQuery(sqlVacationLimit);
                while (rs.next()) {
                    emp.setTotal_days(rs.getInt("total_days"));
                    emp.setRemain_days(rs.getInt("remain_days"));
                }
            } else {
                throw new Exception("Wrong Password!");
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }
        return emp;
    }

    @Override
    public List<Employee> getAllEMployees() throws Exception {
        List<Employee> empList = new ArrayList();
        String sql = "SELECT \n"
                + "`employee`.`employee_id`,\n"
                + "`employee`.`employee_account`,\n"
                + "`employee`.`employee_name`, \n"
                + "`employee`.`status`,\n"
                + "`employee`.`department_id`,\n"
                + "`employee`.`position_id`,\n"
                + "`employee`.`date`,\n"
                + "`department`.`department_name`,\n"
                + "`position`.`position_name`,\n"
                + "`employee_details`.`genre`,\n"
                + "`employee_details`.`birthday`,\n"
                + "`employee_details`.`email`,\n"
                + "`employee_details`.`address`\n"
                + "FROM `employee`\n"
                + "LEFT JOIN `department` ON `department`.`department_id` = `employee`.`department_id` \n"
                + "LEFT JOIN `position` ON `position`.`position_id` = `employee`.`position_id`\n"
                + "LEFT JOIN `employee_details` ON `employee_details`.`employee_id` = `employee`.`employee_id`";
        ResultSet rs;
        boolean hasResult = false;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                hasResult = true;
                Employee emp = new Employee();
                emp.setEmployee_id(rs.getInt("employee_id"));
                emp.setEmployee_account(rs.getString("employee_account") != null ? rs.getString("employee_account") : "");
                emp.setEmployee_name(rs.getString("employee_name") != null ? rs.getString("employee_name") : "");
                emp.setPosition_id(rs.getInt("position_id"));
                emp.setDepartment_id(rs.getInt("department_id"));
                emp.setDepartment_name(rs.getString("department_name") != null ? rs.getString("department_name") : "");
                emp.setPosition_name(rs.getString("position_name") != null ? rs.getString("position_name") : "");
                emp.setDate(rs.getString("date") != null ? rs.getString("date") : "");
                emp.setStatus(rs.getInt("status"));
                EmployeeDetails ed = new EmployeeDetails();
                ed.setGenre(rs.getInt("genre"));
                ed.setBirthday(rs.getString("birthday") != null ? rs.getString("birthday") : "");
                ed.setEmail(rs.getString("email") != null ? rs.getString("email") : "");
                ed.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
                emp.setEmployee_details(ed);
                empList.add(emp);
            }

            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
            if (!hasResult) {
                throw new Exception("NoDataFromDatabase");
            }
        }
        return empList;
    }

    @Override
    public boolean updateEmployee(int employee_id, int department_id, int position_id, int status, String date) throws Exception {
        if (employee_id <= 0 || department_id <= 0 || position_id <= 0 || status < 0) {
            throw new Exception("Wrong Required Parameters");
        }
        if (date == null) {
            date = "";
        }
        String query = "UPDATE `employee` SET "
                + "`position_id`=" + position_id + ","
                + "`department_id`=" + department_id + ","
                + "`status`=" + status + ","
                + "`date`=\"" + date + "\" "
                + "WHERE "
                + "`employee_id` = " + employee_id;
        int updateLine;
        try (Statement stmt = conn.createStatement()) {
            updateLine = stmt.executeUpdate(query);
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
        }

        return updateLine == 1;

    }

    @Override
    public boolean changePassword(int employee_id, String old_password, String new_password) throws Exception {
        if (employee_id < 1) {
            throw new Exception("Error Employee ID");
        }
        if (old_password == null || new_password == null || old_password.equals("") || new_password.equals("")) {
            throw new Exception("Missing Required Parameters");
        }
        if (old_password.equals(new_password)) {
            throw new Exception("New Password is same as Old Password");
        }
        String query = "SELECT `password` FROM `employee` WHERE `employee_id`=" + employee_id;
        ResultSet rs;
        boolean isUpdate;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(query);
            String password = null;
            while (rs.next()) {
                password = rs.getString("password");
            }
            boolean isCorrect = false;
            isUpdate = false;
            if (BCrypt.checkpw(old_password, password)) {
                isCorrect = true;
            }
            if (isCorrect) {
                password = BCrypt.hashpw(Util.Utils.replceSymbol(new_password), BCrypt.gensalt());
                String sql = "UPDATE `employee` SET `password`=\"" + password + "\" WHERE `employee_id` = " + employee_id;
                if (stmt.executeUpdate(sql) == 1) {
                    isUpdate = true;
                } else {
                    throw new Exception("Changed Password Failed");
                }
            } else {
                throw new Exception("Wrong Old Password");
            }
            conn.close();
            DataBaseDAO.getInstance().close();
            stmt.close();
            rs.close();
        }

        return isUpdate;
    }

}
