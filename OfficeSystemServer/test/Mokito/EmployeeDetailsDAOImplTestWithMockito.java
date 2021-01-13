/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mokito;

import DAO.EmployeeDetailsDAOImpl;
import DTO.EmployeeDetails;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class EmployeeDetailsDAOImplTestWithMockito {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    EmployeeDetails ed = new EmployeeDetails(1, 1, "19970102", "123@gmail.com", "Ireland");

    public EmployeeDetailsDAOImplTestWithMockito() {
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

    /**
     * Test of getEmployeeDetails method, of class EmployeeDetailsDAOImpl.
     */
    @Test
    public void testGetEmployeeDetails() throws Exception {
        int employee_id = 1;
        String query = "SELECT * FROM `employee_details` WHERE `employee_id` = " + employee_id;

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(query)).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt("genre")).thenReturn(ed.getGenre());
        when(rs.getString("address")).thenReturn(ed.getAddress());
        when(rs.getString("birthday")).thenReturn(ed.getBirthday());
        when(rs.getString("email")).thenReturn(ed.getEmail());

        EmployeeDetailsDAOImpl instance = new EmployeeDetailsDAOImpl(conn);
        EmployeeDetails expResult = ed;
        EmployeeDetails result = instance.getEmployeeDetails(employee_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateEmployeeDetails method, of class EmployeeDetailsDAOImpl.
     */
    @Test
    public void testUpdateEmployeeDetails() throws Exception {
        int employee_id = ed.getEmployee_id();
        int genre = ed.getGenre();
        String birthday = ed.getBirthday();
        String email = ed.getEmail();
        String address = ed.getAddress();

        String query = "UPDATE `employee_details` "
                + "SET "
                + "`genre`=" + genre + ","
                + "`birthday`=\"" + birthday + "\","
                + "`email`=\"" + email + "\","
                + "`address`=\"" + address + "\" "
                + "WHERE `employee_id`= " + employee_id;

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeUpdate(query)).thenReturn(1);

        EmployeeDetailsDAOImpl instance = new EmployeeDetailsDAOImpl(conn);
        int expResult = 1;
        int result = instance.updateEmployeeDetails(employee_id, genre, birthday, email, address);
        assertEquals(expResult, result);
    }

}
