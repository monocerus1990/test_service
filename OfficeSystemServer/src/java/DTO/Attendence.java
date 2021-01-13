/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import org.json.simple.JSONObject;

/**
 *
 * @author unico
 */
public class Attendence {

    private int employee_id;
    private int seconds;

    public Attendence() {
    }

    public Attendence(int employee_id, int seconds) {
        this.employee_id = employee_id;
        this.seconds = seconds;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        json.put("employee_id", employee_id);
        json.put("seconds", seconds);
        return json;
    }
}
