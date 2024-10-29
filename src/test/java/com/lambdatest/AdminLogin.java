

package com.lambdatest;


import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminLogin {
    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "jayak";
        String authkey = "dVeaZxDvDpANKUhNP0uPAqlcaLQg5vQmNdP6iJF78WtAimkdiF";
        String hub = "@hub.lambdatest.com/wd/hub";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
//        options.addArguments("--no-proxy-server");
//        options.addArguments("--ignore-ssl-errors=yes");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--ignore-certificate-errors");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--test-type");
//        options.addArguments("--disable-extensions");
//        options.addArguments("window-size=1920x1080");
//        options.addArguments("--remote-allow-origins=*");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 11");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "129");
        caps.setCapability("build", "NetsfereAdmin");
        caps.setCapability("name", "NetsfereAdmin");
        caps.setCapability("plugin", "git-testng");
        caps.setCapability("network", true);
        caps.setCapability("geoLocation", "IN");
        caps.setCapability("w3c", true);

        String[] Tags = new String[]{"Feature", "Falcon", "Severe"};
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");

        driver.get("https://web.netsfere.com/");
        Thread.sleep(100);

        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[1]/form/div[1]/div[2]/input").sendKeys("Lotus@sanu.netsferetest.org");
        Thread.sleep(100);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[1]/form/div[4]/div[2]/button").click();
        Thread.sleep(5000);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[1]/form/div[2]/div[2]/input").sendKeys("Abcd@1234567");
        Thread.sleep(100);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[1]/form/div[5]/div[2]/button").click();
        Thread.sleep(15000);

        // Set up an explicit wait with 10 seconds timeout
        WebDriverWait wait = new WebDriverWait(driver, 120);

// Wait until the button is visible, then click it
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/div[1]/div/div[5]/div/button/span")));
        button.click();

        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[1]/div/div[5]/div/button/span").click();
        Thread.sleep(1000);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[1]/div/div[1]/div[2]/div[1]/div[2]").click();
        Thread.sleep(1000);

        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[1]/div/div[2]/div[3]/div/div[3]/div[2]").click();
        Thread.sleep(100);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[2]/div/div/div/div[2]/div[3]/div[1]/div[1]/button/span").click();
        Thread.sleep(100);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[2]/div/div/div[2]/div[2]/div[3]/textarea").sendKeys("Hello Test Message");
        Thread.sleep(100);
        driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div/div/div[2]/div/div/div[2]/div[2]/button/span").click();
        Thread.sleep(100);
        Status = "passed";
        driver.executeScript("lambda-status=" + Status);
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}