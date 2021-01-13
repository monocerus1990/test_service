/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DAO.AttendanceDAOImpl;
import DAO.EmployeeDAO;
import DAO.EmployeeDAOImpl;
import DTO.Attendence;
import Util.BeJson;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import DAO.AttendanceDAO;

/**
 * REST Web Service
 *
 * @author unico
 */
@Path("Attendence")
public class AttendenceResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AttendenceResource
     */
    public AttendenceResource() {
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public String poseText(String content) {
        String status = "0";
        String error = "null";
        JSONArray jsonArray = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(content);
            JSONObject dataJson = (JSONObject) parser.parse(json.get("data").toString());
            AttendanceDAO attDao = new AttendanceDAOImpl();
            String method = json.get("method").toString();
            switch (method) {
                case "signed":
                    boolean isSigned = attDao.signAttendence(
                            Integer.parseInt(dataJson.get("employee_id").toString()),
                            Integer.parseInt(dataJson.get("seconds").toString()));
                    if (isSigned) {
                        status = "1";
                    } else {
                        status = "-1";
                        error = "Signed Failed";
                    }
                    break;
                case "get_attendence":
                    List<Attendence> attList = attDao.getEmployeeAttendence(
                            Integer.parseInt(dataJson.get("employee_id").toString()),
                            Integer.parseInt(dataJson.get("from").toString()),
                            Integer.parseInt(dataJson.get("to").toString()));
                    if (attList != null && attList.size() > 0) {
                        jsonArray = new JSONArray();
                        for (Attendence att : attList) {
                            status = "1";
                            jsonArray.add(att.getJSONObject());
                        }
                    } else {
                        status = "-1";
                        error = "List<Employee> NULL ERROR";
                    }
                    break;

            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("NoDataFromDatabase")) {
                status = "-2";
            } else {
                status = "-1";
            }
            error = ex.toString();
            Logger.getLogger(EmployeeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BeJson.getJson(status, error, jsonArray).toJSONString();
    }
}
