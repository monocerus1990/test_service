/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mokito;

import DAO.EmployeeDAOImpl;
import DTO.Employee;
import DTO.EmployeeDetails;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mindrot.jbcrypt.BCrypt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author QAQ
 */
public class EmployeeDAOImplTestWithMockito {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    EmployeeDetails ed1 = new EmployeeDetails(1, 1, "19970214", "123@gmail.com", "Ireland");
    Employee employee = new Employee(1, "D00198320", "JI", "admin", 1, "HR", 1,
            "Management", 1, "", ed1, 30, 30);
    EmployeeDetails ed2 = new EmployeeDetails(1, 1, "19970214", "123@gmail.com", "Ireland");
    Employee employee1 = new Employee(1, "D00198320", "JI", "admin", 1, "HR", 1,
            "Management", 1, "", ed2, 30, 30);
    EmployeeDetails ed3 = new EmployeeDetails(1, 1, "19970214", "123@gmail.com", "Ireland");
    Employee employee2 = new Employee(1, "D00198320", "JI", "admin", 1, "HR", 1,
            "Management", 1, "", ed3, 30, 30);

    String sqlForSelectAll = "SELECT \n"
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

    public EmployeeDAOImplTestWithMockito() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        conn = mock(Connection.class);
        stmt = mock(Statement.class);
        rs = mock(ResultSet.class);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class EmployeeDAOImpl.
     */
    @Test
    public void testLogin1() throws Exception {

        String employee_account = employee.getEmployee_account();
        String password = employee.getPassword();

        String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(sql)).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("password")).thenReturn(
                BCrypt.hashpw(Util.Utils.replceSymbol(employee.getPassword()),
                        BCrypt.gensalt()));
        when(rs.getInt("employee_id")).thenReturn(employee.getEmployee_id());
        when(rs.getString("employee_name")).thenReturn(employee.getEmployee_name());

        EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
        Employee expResult = employee;
        Employee result = instance.login(employee_account, password);
        assertEquals(expResult.getEmployee_id(), result.getEmployee_id());
    }

    @Test
    public void testLogin2() throws Exception {
        try {
            String employee_account = null;
            String password = null;

            String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeQuery(sql)).thenReturn(rs);
            when(rs.next()).thenReturn(true, false);
            when(rs.getString("password")).thenReturn(
                    BCrypt.hashpw(Util.Utils.replceSymbol(employee.getPassword()),
                            BCrypt.gensalt()));
            when(rs.getInt("employee_id")).thenReturn(employee.getEmployee_id());

            EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
            Employee expResult = employee;
            Employee result = instance.login(employee_account, password);
            assertEquals(expResult.getEmployee_id(), result.getEmployee_id());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testLogin3() throws Exception {
        try {
            String employee_account = employee.getEmployee_account();
            String password = employee.getPassword();

            String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeQuery(sql)).thenReturn(rs);
            when(rs.next()).thenReturn(false);
            when(rs.getString("password")).thenReturn(
                    BCrypt.hashpw(Util.Utils.replceSymbol(employee.getPassword()),
                            BCrypt.gensalt()));
            when(rs.getInt("employee_id")).thenReturn(employee.getEmployee_id());

            EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
            Employee expResult = employee;
            Employee result = instance.login(employee_account, password);
            assertEquals(expResult.getEmployee_id(), result.getEmployee_id());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("NO SUCH ACCOUNT"));
        }
    }

    @Test
    public void testLogin4() throws Exception {
        try {
            String employee_account = "";
            String password = "";

            String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeQuery(sql)).thenReturn(rs);
            when(rs.next()).thenReturn(true, false);
            when(rs.getString("password")).thenReturn(
                    BCrypt.hashpw(Util.Utils.replceSymbol(employee.getPassword()),
                            BCrypt.gensalt()));
            when(rs.getInt("employee_id")).thenReturn(employee.getEmployee_id());

            EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
            Employee expResult = employee;
            Employee result = instance.login(employee_account, password);
            assertEquals(expResult.getEmployee_id(), result.getEmployee_id());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testLogin5() throws Exception {
        try {
            String employee_account = employee.getEmployee_account();
            String password = "ThisMustBeAWrongPassword";

            String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeQuery(sql)).thenReturn(rs);
            when(rs.next()).thenReturn(true, false);
            when(rs.getString("password")).thenReturn(
                    BCrypt.hashpw(Util.Utils.replceSymbol(employee.getPassword()),
                            BCrypt.gensalt()));
            when(rs.getInt("employee_id")).thenReturn(employee.getEmployee_id());

            EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
            Employee expResult = employee;
            Employee result = instance.login(employee_account, password);
            assertEquals(expResult.getEmployee_id(), result.getEmployee_id());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Wrong Password!"));
        }
    }

    /**
     * Test of getAllEMployees method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetAllEMployees1() throws Exception {

        List<Employee> expResult = new ArrayList();
        expResult.add(employee);
        expResult.add(employee1);
        expResult.add(employee2);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(sqlForSelectAll)).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, true, false);
        when(rs.getInt("employee_id")).thenReturn(employee.getEmployee_id(), employee1.getEmployee_id(), employee2.getEmployee_id());
        when(rs.getString("employee_account")).thenReturn(employee.getEmployee_account(), employee1.getEmployee_account(), employee2.getEmployee_account());
        when(rs.getString("employee_name")).thenReturn(employee.getEmployee_name(), employee1.getEmployee_name(), employee2.getEmployee_name());
        when(rs.getInt("position_id")).thenReturn(employee.getPosition_id(), employee1.getPosition_id(), employee2.getPosition_id());
        when(rs.getInt("department_id")).thenReturn(employee.getDepartment_id(), employee1.getDepartment_id(), employee2.getDepartment_id());
        when(rs.getString("department_name")).thenReturn(employee.getDepartment_name(), employee1.getDepartment_name(), employee2.getDepartment_name());
        when(rs.getString("position_name")).thenReturn(employee.getPosition_name(), employee1.getPosition_name(), employee2.getPosition_name());
        when(rs.getInt("status")).thenReturn(employee.getStatus(), employee1.getStatus(), employee2.getStatus());
        when(rs.getInt("genre")).thenReturn(employee.getEmployee_details().getGenre(), employee1.getEmployee_details().getGenre(), employee2.getEmployee_details().getGenre());
        when(rs.getString("birthday")).thenReturn(employee.getEmployee_details().getBirthday(), employee1.getEmployee_details().getBirthday(), employee2.getEmployee_details().getBirthday());
        when(rs.getString("email")).thenReturn(employee.getEmployee_details().getEmail(), employee1.getEmployee_details().getEmail(), employee2.getEmployee_details().getEmail());
        when(rs.getString("address")).thenReturn(employee.getEmployee_details().getAddress(), employee1.getEmployee_details().getAddress(), employee2.getEmployee_details().getAddress());
        when(rs.getString("date")).thenReturn(employee.getDate(), employee1.getDate(), employee2.getDate());

        EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);

        List<Employee> result = instance.getAllEMployees();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllEMployees2() throws Exception {
        try {
            List<Employee> expResult = new ArrayList();
            expResult.add(employee);
            expResult.add(employee1);
            expResult.add(employee2);

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeQuery(sqlForSelectAll)).thenReturn(rs);
            when(rs.next()).thenReturn(false);

            EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);

            List<Employee> result = instance.getAllEMployees();
            assertEquals(expResult, result);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("NoDataFromDatabase"));
        }
    }

    /**
     * Test of updateEmployee method, of class EmployeeDAOImpl.
     */
    @Test
    public void testUpdateEmployee1() throws Exception {

        int position_id = employee.getPosition_id();
        int department_id = employee.getDepartment_id();
        int status = employee.getStatus();
        String date = employee.getDate();
        int employee_id = employee.getEmployee_id();

        String query = "UPDATE `employee` SET "
                + "`position_id`=" + position_id + ","
                + "`department_id`=" + department_id + ","
                + "`status`=" + status + ","
                + "`date`=\"" + date + "\" "
                + "WHERE "
                + "`employee_id` = " + employee_id;

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(1);

        EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
        boolean expResult = true;
        boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
        assertEquals(expResult, result);

    }

    @Test
    public void testUpdateEmployee2() throws Exception {

        int position_id = employee.getPosition_id();
        int department_id = employee.getDepartment_id();
        int status = employee.getStatus();
        String date = employee.getDate();
        int employee_id = employee.getEmployee_id();

        String query = "UPDATE `employee` SET "
                + "`position_id`=" + position_id + ","
                + "`department_id`=" + department_id + ","
                + "`status`=" + status + ","
                + "`date`=\"" + date + "\" "
                + "WHERE "
                + "`employee_id` = " + employee_id;

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(-1);

        EmployeeDAOImpl instance = new EmployeeDAOImpl(conn);
        boolean expResult = false;
        boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
        assertEquals(expResult, result);
    }
}
