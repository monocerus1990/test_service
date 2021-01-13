/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mokito;

import DAO.VacationDAOImpl;
import DTO.Vacation;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author QAQ
 */
public class VacationDAOImplTestWithMockito {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private Vacation v1;
    private Vacation v2;
    private Vacation v3;
    private List<Vacation> vacList;

    public VacationDAOImplTestWithMockito() {
        this.v1 = new Vacation(1, 65, "BO", "2020-03-01", "2020-03-05", 1, "EnjoyLife", false, 0);
        this.v2 = new Vacation(2, 65, "BO", "2020-03-05", "2020-03-07", 2, "EnjoyLife", false, 1);
        this.v3 = new Vacation(3, 66, "JI", "2020-03-07", "2020-03-017", 1, "EnjoyLife", false, -1);
        this.vacList = new ArrayList();
        vacList.add(v1);
        vacList.add(v2);
        vacList.add(v3);
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
     * Test of applyVacation method, of class VacationDAOImpl.
     */
    @Test
    public void testApplyVacation1() throws Exception {

        int employee_id = v1.getEmployee_id();
        String date_from = v1.getDate_from();
        String date_end = v1.getDate_end();
        int type = v1.getType();
        String description = v1.getDescription();
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
                + "" + -1 + ","
                + "\"" + description + "\""
                + ")";
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(1);

        VacationDAOImpl instance = new VacationDAOImpl(conn);
        boolean expResult = true;
        boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
        assertEquals(expResult, result);
    }

    @Test
    public void testApplyVacation2() throws Exception {

        int employee_id = v1.getEmployee_id();
        String date_from = v1.getDate_from();
        String date_end = v1.getDate_end();
        int type = v1.getType();
        String description = v1.getDescription();
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
                + "" + -1 + ","
                + "\"" + description + "\""
                + ")";
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(0);

        VacationDAOImpl instance = new VacationDAOImpl(conn);
        boolean expResult = false;
        boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
        assertEquals(expResult, result);
    }

    @Test
    public void testApplyVacation3() throws Exception {
        try {
            int employee_id = 0;
            String date_from = v1.getDate_from();
            String date_end = v1.getDate_end();
            int type = v1.getType();
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testApplyVacation4() throws Exception {
        try {
            int employee_id = v1.getEmployee_id();
            String date_from = null;
            String date_end = v1.getDate_end();
            int type = v1.getType();
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testApplyVacation5() throws Exception {
        try {
            int employee_id = v1.getEmployee_id();
            String date_from = "";
            String date_end = v1.getDate_end();
            int type = v1.getType();
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testApplyVacation6() throws Exception {
        try {
            int employee_id = v1.getEmployee_id();
            String date_from = v1.getDate_from();
            String date_end = null;
            int type = v1.getType();
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testApplyVacation7() throws Exception {
        try {
            int employee_id = v1.getEmployee_id();
            String date_from = v1.getDate_from();
            String date_end = "";
            int type = v1.getType();
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testApplyVacation8() throws Exception {
        try {
            int employee_id = v1.getEmployee_id();
            String date_from = v1.getDate_from();
            String date_end = v1.getDate_end();
            int type = 0;
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testApplyVacation9() throws Exception {
        try {
            int employee_id = v1.getEmployee_id();
            String date_from = v1.getDate_end();
            String date_end = v1.getDate_from();
            int type = v1.getType();
            String description = v1.getDescription();
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
                    + "" + -1 + ","
                    + "\"" + description + "\""
                    + ")";
            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(0);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = false;
            boolean result = instance.applyVacation(employee_id, date_from, date_end, type, description);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("EndDateIsBeforeStartDate"));
        }
    }

    /**
     * Test of getAllVaction method, of class VacationDAOImpl.
     */
    @Test
    public void testGetAllVaction1() throws Exception {

        String query = "SELECT  `vacation`.`id`,`vacation`.`employee_id`, "
                + "`employee`.`employee_name`,"
                + "`vacation`.`date_from`,"
                + "`vacation`.`date_end`,"
                + "`vacation`.`type`,"
                + "`vacation`.`description`,"
                + "`vacation`.`is_salary`,"
                + "`vacation`.`status` "
                + "FROM `vacation`,`employee` "
                + "WHERE `vacation`.`employee_id` = `employee`.`employee_id`";

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(query)).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, true, false);
        when(rs.getInt("id")).thenReturn(v1.getId(), v2.getId(), v3.getId());
        when(rs.getInt("employee_id")).thenReturn(v1.getEmployee_id(), v2.getEmployee_id(), v3.getEmployee_id());
        when(rs.getString("employee_name")).thenReturn(v1.getEmployee_name(), v2.getEmployee_name(), v3.getEmployee_name());
        when(rs.getString("date_from")).thenReturn(v1.getDate_from(), v2.getDate_from(), v3.getDate_from());
        when(rs.getString("date_end")).thenReturn(v1.getDate_end(), v2.getDate_end(), v3.getDate_end());
        when(rs.getInt("type")).thenReturn(v1.getType(), v2.getType(), v3.getType());
        when(rs.getString("description")).thenReturn(v1.getDescription(), v2.getDescription(), v3.getDescription());
        when(rs.getBoolean("is_salary")).thenReturn(v1.isIs_salary(), v2.isIs_salary(), v3.isIs_salary());
        when(rs.getInt("status")).thenReturn(v1.getStatus(), v2.getStatus(), v3.getStatus());

        VacationDAOImpl instance = new VacationDAOImpl(this.conn);
        List<Vacation> expResult = this.vacList;
        List<Vacation> result = instance.getAllVaction();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllVaction2() throws Exception {
        try {

            String query = "SELECT  `vacation`.`id`,`vacation`.`employee_id`, "
                    + "`employee`.`employee_name`,"
                    + "`vacation`.`date_from`,"
                    + "`vacation`.`date_end`,"
                    + "`vacation`.`type`,"
                    + "`vacation`.`description`,"
                    + "`vacation`.`is_salary`,"
                    + "`vacation`.`status` "
                    + "FROM `vacation`,`employee` "
                    + "WHERE `vacation`.`employee_id` = `employee`.`employee_id`";

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeQuery(query)).thenReturn(rs);
            when(rs.next()).thenReturn(false);

            VacationDAOImpl instance = new VacationDAOImpl(this.conn);
            List<Vacation> expResult = this.vacList;
            List<Vacation> result = instance.getAllVaction();
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("NoDataFromDatabase"));
        }
    }

    /**
     * Test of ApprovalVacation method, of class VacationDAOImpl.
     */
    @Test
    public void testApprovalVacation1() throws Exception {
        int id = v1.getId();
        boolean is_salary = v1.isIs_salary();
        int status = v1.getStatus();
        String query = "UPDATE `vacation` SET`is_salary`=" + is_salary + ",`status`=" + status + " WHERE `id`=" + id;

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(1);

        VacationDAOImpl instance = new VacationDAOImpl(conn);
        boolean expResult = true;
        boolean result = instance.ApprovalVacation(id, is_salary, status);
        assertEquals(expResult, result);
    }

    @Test
    public void testApprovalVacation2() throws Exception {
        int id = v1.getId();
        boolean is_salary = v1.isIs_salary();
        int status = v1.getStatus();
        String query = "UPDATE `vacation` SET`is_salary`=" + is_salary + ",`status`=" + status + " WHERE `id`=" + id;

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(0);

        VacationDAOImpl instance = new VacationDAOImpl(conn);
        boolean expResult = false;
        boolean result = instance.ApprovalVacation(id, is_salary, status);
        assertEquals(expResult, result);
    }

    @Test
    public void testApprovalVacation3() throws Exception {
        try {
            int id = 0;
            boolean is_salary = v1.isIs_salary();
            int status = v1.getStatus();
            String query = "UPDATE `vacation` SET`is_salary`=" + is_salary + ",`status`=" + status + " WHERE `id`=" + id;

            when(conn.createStatement()).thenReturn(stmt);
            when(stmt.executeUpdate(query)).thenReturn(1);

            VacationDAOImpl instance = new VacationDAOImpl(conn);
            boolean expResult = true;
            boolean result = instance.ApprovalVacation(id, is_salary, status);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Employee ID"));
        }
    }
}
