/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Attendence;
import java.util.List;

/**
 *
 * @author unico
 */
public interface AttendanceDAO {

    public List<Attendence> getEmployeeAttendence(int employee_id,int from, int to) throws Exception;

    public boolean signAttendence(int employee_id, int seconds) throws Exception;
}
