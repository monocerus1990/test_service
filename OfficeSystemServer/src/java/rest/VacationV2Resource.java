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
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * REST Web Service
 *
 * @author unico
 */
@Path("VacationV2")
public class VacationV2Resource extends VacationResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VacationV2Resource
     */
    public VacationV2Resource() {
    }

    /**
     * Retrieves representation of an instance of rest.VacationV2Resource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Override
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        String status = "0";
        String error = "null";
        JSONArray jsonArray = null;
        try {
            VacationDAO vacDao = new VacationDAOImpl();
            List<JSONObject> resultList = vacDao.getSubVacationList();
            if (resultList != null) {
                jsonArray = new JSONArray();
                status = "1";
                for (JSONObject vac : resultList) {
                    jsonArray.add(vac);
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
}
