package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParalleluserLogin {

    private RemoteWebDriver driver;
    private String Status = "failed";



    @BeforeMethod
    @org.testng.annotations.Parameters(value = {"platform"})
    public void setup(Method m, ITestContext ctx, String platform) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", platform);
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "127");
        caps.setCapability("build", "netsferetest-11");
        caps.setCapability("name", m.getName() + this.getClass().getName());

        caps.setCapability("plugin", "git-testng");

        /*
        Enable Smart UI Project
        caps.setCapability("smartUI.project", "<Project Name>");
        */

        String[] Tags = new String[] { "Feature", "Magicleap", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    @org.testng.annotations.Parameters(value = {"email", "password"})
    public void basicTest(String email, String password) throws InterruptedException {
        try {
            String spanText;
            System.out.println("Loading Url");

            driver.get("https://web.netsfere.com/");
            System.out.println(email + password);
            Thread.sleep(10000);

            WebElement emailValue ;

            try {
                driver.findElement(By.xpath("//input[@placeholder='Enter email address']")).sendKeys(email);
            }
            catch(Exception e) {
                driver.findElementByClassName("input[placeholder='Enter email address']").sendKeys(email);

            }

            driver.findElementByXPath("//button[@type='submit']").click();
            Thread.sleep(5000);
            driver.findElementByXPath("//input[@placeholder='Enter password']").sendKeys("Abcd@1234567");
            driver.findElementByXPath("//button[@type='submit']").click();
            Thread.sleep(5000);


// Clicking on close button
            try {
                Thread.sleep(15000);
                driver.findElementByXPath("(//div[@class='click-ripple' and @style='position: absolute; inset: 0px; overflow: visible; z-index: -1; pointer-events: none;'])[13]").click();
            }
            catch(Exception e) {
                Thread.sleep(3000);
                for(int i = 0; i < 5; i++){
                    driver.findElementByXPath("/html/body/div[1]/div/div[3]/div[2]/div/div/div/div/button[2]").click();
                    Thread.sleep(5000);
                }
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            System.out.println("executing" + now);
            Thread.sleep(10000);

            now = LocalDateTime.now();
            System.out.println("executing" + now );

            Status = "passed";
            // driver.findElementByXPath("//div[@title='lotus, lotus101 & 247 Others']//div[contains(text(),'lotus, lotus101 & 247 Others')]").click();
            System.out.println("TestFinished");
            driver.executeScript("lambda-status=" + Status);
        }
        catch(Exception e) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}