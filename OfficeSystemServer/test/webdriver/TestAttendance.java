/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webdriver;

import java.util.ResourceBundle;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author QAQ
 */
public class TestAttendance {

    private String baseUrl;

    public TestAttendance() {
        ResourceBundle config = ResourceBundle.getBundle("properties.DBConfig");
        baseUrl = config.getString("baseUrl");
    }

    @Test
    public void testPostMethod() {
        //System.setProperty("webdriver.chrome.driver", "D:\\Document\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "D:\\Document\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get(baseUrl);
        driver.findElement(By.linkText("Department")).click();
        Select methodSel = new Select(driver.findElement(By.id("methodSel")));
        methodSel.selectByIndex(0);
        //driver.findElement(By.id("blobParam")).clear();
        //driver.findElement(By.id("blobParam")).sendKeys(sendJson);
        driver.findElement(By.linkText("测试")).click();

        String result = driver.findElement(By.id("rawContent")).getText();

        assertTrue(result.contains("\"status\":\"1\"") || (result.contains("\"status\":\"-2\"") && result.contains("NoDataFromDatabase")));
        driver.close();
    }
}
