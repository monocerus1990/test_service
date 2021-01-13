/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Employee;
import java.util.List;

/**
 *
 * @author QAQ
 */
public interface EmployeeDAO {

    public Employee register(String employee_account, String empoyee_name, int department_id, int position_id, String password, int status, String date) throws Exception;

    public Employee login(String employee_account, String password) throws Exception;

    public List<Employee> getAllEMployees() throws Exception;

    public boolean updateEmployee(int employee_id, int department_id, int position_id, int status, String date) throws Exception;

    public boolean changePassword(int employee_id, String old_password, String new_password) throws Exception;

}
