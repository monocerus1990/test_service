package DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 *
 * @author hasee
 */
public class DataBaseDAO {

    private Connection conn;
    private static DataBaseDAO instance;
    private ResourceBundle config;

    private DataBaseDAO() {
        config = ResourceBundle.getBundle("properties.DBConfig");
    }

    public static DataBaseDAO getInstance() {
        if (instance == null) {
            instance = new DataBaseDAO();
        }
        return instance;
    }

    public Connection connect() throws Exception {
        Class.forName(this.config.getString("DRIVE"));
        String url;
        url = this.config.getString("URL") + this.config.getString("DATABASE_NAME");

        String user = this.config.getString("USER");
        String password = this.config.getString("PSW");
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
        if (instance != null) {
            instance = null;
        }
    }

}
