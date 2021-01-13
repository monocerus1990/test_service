/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Objects;
import org.json.simple.JSONObject;

/**
 *
 * @author QAQ
 */
public class Vacation {

    private int id;
    private int employee_id;
    private String employee_name;
    private String date_from;
    private String date_end;
    private int type;
    private String description;
    private boolean is_salary;
    private int status;

    public Vacation() {
    }

    public Vacation(int id, int employee_id, String employee_name,
            String date_from, String date_end,
            int type, String description,
            boolean is_salary, int status) {
        this.id = id;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.date_from = date_from;
        this.date_end = date_end;
        this.type = type;
        this.description = description;
        this.is_salary = is_salary;
        this.status = status;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_salary() {
        return is_salary;
    }

    public void setIs_salary(boolean is_salary) {
        this.is_salary = is_salary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("employee_id", this.employee_id);
        json.put("employee_name", this.employee_name);
        json.put("date_from", this.date_from);
        json.put("date_end", this.date_end);
        json.put("type", this.type);
        json.put("description", this.description);
        json.put("is_salary", this.is_salary);
        json.put("status", this.status);
        return json;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vacation other = (Vacation) obj;
        if (!Objects.equals(this.employee_id, other.employee_id)) {
            return false;
        }
        return true;
    }

}
