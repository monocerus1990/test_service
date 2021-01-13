/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integration;

import DAO.EmployeeDAOImpl;
import DTO.Employee;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author QAQ
 */
public class EmployeeDAOImplTest {

    public EmployeeDAOImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of register method, of class EmployeeDAOImpl.
     */
    @Test
    public void testRegister1() throws Exception {
        String employee_account = System.currentTimeMillis() + "";
        String employee_name = "XiaoBo";
        int department_id = 1;
        int position_id = 1;
        String password = "admin";
        int status = 1;
        String date = "";
        EmployeeDAOImpl instance = new EmployeeDAOImpl();
        int expResult = 1;
        Employee result = instance.register(employee_account, employee_name, department_id, position_id, password, status, date);
        assertTrue(expResult <= result.getEmployee_id());
    }

    @Test
    public void testRegister2() throws Exception {
        try {
            String employee_account = "D00198320";
            String employee_name = "XiaoBo";
            int department_id = 1;
            int position_id = 1;
            String password = "admin";
            int status = 1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            int expResult = 1;
            Employee result = instance.register(employee_account, employee_name, department_id, position_id, password, status, date);
            assertTrue(expResult <= result.getEmployee_id());
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("User Already Exists"));
        }
    }

    @Test
    public void testRegister3() throws Exception {
        try {
            String employee_account = null;
            String employee_name = null;
            int department_id = 1;
            int position_id = 1;
            String password = null;
            int status = 1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            int expResult = 1;
            Employee result = instance.register(employee_account, employee_name, department_id, position_id, password, status, date);
            assertTrue(expResult <= result.getEmployee_id());
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testRegister4() throws Exception {
        try {
            String employee_account = System.currentTimeMillis() + "";
            String employee_name = "Name";
            int department_id = 0;
            int position_id = -1;
            String password = "";
            int status = 1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            int expResult = 1;
            Employee result = instance.register(employee_account, employee_name, department_id, position_id, password, status, date);
            assertTrue(expResult <= result.getEmployee_id());
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    /**
     * Test of login method, of class EmployeeDAOImpl.
     */
    @Test
    public void testLogin1() throws Exception {
        String employee_account = "D00198309";
        String password = "admin";
        EmployeeDAOImpl instance = new EmployeeDAOImpl();
        Employee result = instance.login(employee_account, password);
        assertTrue(result != null);
    }

    @Test
    public void testLogin2() throws Exception {
        try {
            String employee_account = "D00198309";
            String password = "wrong";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            Employee result = instance.login(employee_account, password);
            assertTrue(result != null);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Password!"));
        }
    }

    @Test
    public void testLogin3() throws Exception {
        try {
            String employee_account = "";
            String password = "admin";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            Employee result = instance.login(employee_account, password);
            assertTrue(result != null);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testLogin4() throws Exception {
        try {
            String employee_account = null;
            String password = "admin";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            Employee result = instance.login(employee_account, password);
            assertTrue(result != null);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testLogin5() throws Exception {
        try {
            String employee_account = "D00198309";
            String password = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            Employee result = instance.login(employee_account, password);
            assertTrue(result != null);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testLogin6() throws Exception {
        try {
            String employee_account = "D00198309";
            String password = null;
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            Employee result = instance.login(employee_account, password);
            assertTrue(result != null);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Missing Required Parameters"));
        }
    }

    @Test
    public void testLogin7() throws Exception {
        try {
            String employee_account = System.currentTimeMillis() + "";
            String password = "admin";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            Employee result = instance.login(employee_account, password);
            assertTrue(result != null);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("NO SUCH ACCOUNT"));
        }
    }

    /**
     * Test of getAllEMployees method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetAllEMployees() throws Exception {
        try {
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            List<Employee> result = instance.getAllEMployees();
            assertTrue(result != null);
            assertTrue(result.size() > 0);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("NoDataFromDatabase"));
        }
    }

    /**
     * Test of updateEmployee method, of class EmployeeDAOImpl.
     */
    @Test
    public void testUpdateEmployee1() throws Exception {
        try {

            int employee_id = 0;
            int department_id = 1;
            int position_id = 1;
            int status = 1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            boolean expResult = false;
            boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Required Parameters"));
        }
    }

    @Test
    public void testUpdateEmployee2() throws Exception {
        try {

            int employee_id = 66;
            int department_id = 0;
            int position_id = 1;
            int status = 1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            boolean expResult = false;
            boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Required Parameters"));
        }
    }

    @Test
    public void testUpdateEmployee3() throws Exception {
        try {

            int employee_id = 66;
            int department_id = 1;
            int position_id = 0;
            int status = 1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            boolean expResult = false;
            boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Required Parameters"));
        }
    }

    @Test
    public void testUpdateEmployee4() throws Exception {
        try {

            int employee_id = 66;
            int department_id = 1;
            int position_id = 1;
            int status = -1;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            boolean expResult = false;
            boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Required Parameters"));
        }
    }

    @Test
    public void testUpdateEmployee5() throws Exception {
        try {

            int employee_id = 66;
            int department_id = 1;
            int position_id = 1;
            int status = 2;
            String date = "";
            EmployeeDAOImpl instance = new EmployeeDAOImpl();
            boolean expResult = true;
            boolean result = instance.updateEmployee(employee_id, department_id, position_id, status, date);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Wrong Required Parameters"));
        }
    }
}
