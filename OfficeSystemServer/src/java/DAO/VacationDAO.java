/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Vacation;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author QAQ
 */
public interface VacationDAO {

    public boolean applyVacation(int employee_id, String date_from, String date_end, int type, String description) throws Exception;

    public List<Vacation> getAllVaction() throws Exception;

    public List<Vacation> getEmployeeVacation(int employee_id) throws Exception;

    public boolean ApprovalVacation(int employee_id, boolean is_salary, int status) throws Exception;

    public int getUserRemainDays(int employee_id) throws Exception;
    
    public List<JSONObject> getSubVacationList() throws Exception;

}
