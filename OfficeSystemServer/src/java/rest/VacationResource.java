/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DAO.VacationDAO;
import DAO.VacationDAOImpl;
import DTO.Vacation;
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

/**
 * REST Web Service
 *
 * @author QAQ
 */
@Path("Vacation")
public class VacationResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VacationResource
     */
    public VacationResource() {
    }

    /**
     * Retrieves representation of an instance of rest.VacationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        String status = "0";
        String error = "null";
        JSONArray jsonArray = null;
        try {
            VacationDAO vacDao = new VacationDAOImpl();
            List<Vacation> resultList = vacDao.getAllVaction();
            if (resultList != null) {
                jsonArray = new JSONArray();
                status = "1";
                for (Vacation vac : resultList) {
                    jsonArray.add(vac.getJSONObject());
                }
            } else {
                status = "-1";
                error = "List<Vacation> NULL ERROR";
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

    /**
     * PUT method for updating or creating an instance of VacationResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public String postText(String content) {
        String status = "0";
        String error = "null";
        JSONArray jsonArray = null;
        boolean isResult = false;
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(content);
            JSONObject dataJson = (JSONObject) parser.parse(json.get("data").toString());
            VacationDAO vac = new VacationDAOImpl();
            String method = json.get("method").toString();
            switch (method) {
                case "apply":
                    isResult = vac.applyVacation(Integer.parseInt(dataJson.get("employee_id").toString()),
                            dataJson.get("date_from").toString(),
                            dataJson.get("date_end").toString(),
                            Integer.parseInt(dataJson.get("type").toString()),
                            dataJson.get("description").toString());
                    if (isResult) {
                        status = "1";
                    } else {
                        status = "-1";
                        error = "Apply Failed";
                    }
                    break;
                case "approval":

                    boolean is_salary;
                    try {
                        is_salary = Boolean.getBoolean(dataJson.get("is_salary").toString());
                    } catch (Exception e) {
                        Logger.getLogger(EmployeeResource.class.getName()).log(Level.SEVERE, null, e);
                        is_salary = false;
                    }

                    isResult = vac.ApprovalVacation(Integer.parseInt(dataJson.get("id").toString()),
                            is_salary,
                            Integer.parseInt(dataJson.get("status").toString()));
                    if (isResult) {
                        status = "1";
                    } else {
                        status = "-1";
                        error = "Approval Failed";
                    }
                    break;
                case "myVacation":
                    List<Vacation> vacList = vac.getEmployeeVacation(Integer.parseInt(dataJson.get("employee_id").toString()));
                    if (vacList != null) {
                        jsonArray = new JSONArray();
                        status = "1";
                        for (Vacation vacation : vacList) {
                            jsonArray.add(vacation.getJSONObject());
                        }
                    } else {
                        status = "-1";
                        error = "List<Vacation> NULL ERROR";
                    }
                    break;
                case "user_remain_days":
                    int remainDays = vac.getUserRemainDays(Integer.parseInt(dataJson.get("employee_id").toString()));
                    if (remainDays >= 0) {
                        jsonArray = new JSONArray();
                        status = "1";
                        JSONObject job = new JSONObject();
                        job.put("employee_id", dataJson.get("employee_id").toString());
                        job.put("remain_days", remainDays);
                        jsonArray.add(job);
                    } else {
                        status = "-1";
                        error = "Remainning days ERROR < 0";
                    }
                    break;
                default:
                    status = "-1";
                    error = "NO METHOD HAS BEEN CHOOSED!";
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(EmployeeResource.class.getName()).log(Level.SEVERE, null, ex);
            status = "-1";
            error = ex.toString();
        }
        return BeJson.getJson(status, error, jsonArray).toJSONString();
    }
}
