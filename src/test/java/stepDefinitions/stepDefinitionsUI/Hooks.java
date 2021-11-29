package stepDefinitions.stepDefinitionsUI;

//import atu.testrecorder.ATUTestRecorder;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.jsoup.Connection;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CommonPage;
import pages.LoginPage;
import utilities.api.BaseUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.WebDriverInitialization;

import java.util.Properties;

public class Hooks extends DriverFactory {
    private DriverFactory driverFactory;
    private BaseUtils baseUtils;
    private CommonPage commonPage;
    public WebDriver driver;
    private String scenarioName = "";
    static long startTime;
    static long endTime;
    private WebDriverInitialization webDriverInitialization = null;
    public Scenario logger;

    public Hooks(DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        baseUtils = new BaseUtils();
        commonPage = new CommonPage(driver);
        webDriverInitialization = new WebDriverInitialization();
    }

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        PropUtils.setProps("testStatus", "", baseUtils.testDataFilePath);
        scenarioName = scenario.getName();
        PropUtils.setProps("Scenario_Name", scenarioName, baseUtils.inputDataFilePath);
        String[] scenarioIds = scenario.getId().split(";");
        String featureName = scenarioIds[0];
        this.logger = scenario;
        PropUtils.setProps("Feature_Name", featureName, baseUtils.inputDataFilePath);
        String currentTestMethod = PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentTestMethod");
        driverFactory.driver = webDriverInitialization.launchSpecifiedBrowser("Chrome");
        driverFactory.driver = webDriverInitialization.navigateToURL(PropUtils.getPropValue(baseUtils.inputProp, "url"),driverFactory.driver);
//        if(currentTestMethod.equalsIgnoreCase("OLS_Login_And_ChangePassword_Scenarios")){
//            LoginPage loginPage = new LoginPage(driver);
//            loginPage.changeThePassWord();
//        }
        Thread.sleep(3000);
        BasePage basePage = new BasePage(driverFactory.driver);
        basePage.waitUntilElementLocatedOrRefreshed(By.xpath("//h1[contains(text(),'Log in')]"));
        basePage.userTypeIntoTextField(logger, By.cssSelector("mav-input[name='username']>input"), PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile),PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName")));
        basePage.userTypeIntoTextField(logger, By.cssSelector("mav-input[name='password']>input"), PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile),PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentPassword")));

        driverFactory.driver.findElement(By.id("login-button")).click();

        if(basePage.getStatusOfElement(By.xpath("//h1[contains(text(),'User is locked')]")) || basePage.getStatusOfElement(By.xpath("//div[contains(text(),'Invalid credentials')]")) || basePage.getStatusOfElement(By.xpath("//div[contains(text(),'Login Error')]"))){

        }else{
            basePage.waitUntilElementLocatedOrRefreshed(By.xpath("//span[contains(text(),'Home')]"));
        }
        startTime = System.currentTimeMillis();
    }

    @After
    public void afterScenario(Scenario scenario) throws Exception {
        endTime = System.currentTimeMillis();
        long timeDifference = endTime - startTime;
        WebDriverInitialization.scenarios.put(scenario.getName(), timeDifference / 1000);
//        PropUtils.setProps("timeDifference",String.valueOf(timeDifference/1000),baseUtils.testDataFilePath);
        System.out.println("After scenario");
        driverFactory.driver.quit();
        String currentTestMethod = PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentTestMethod");
//        if (currentTestMethod.equalsIgnoreCase("OLS_Login_And_ChangePassword_Scenarios") || (currentTestMethod.equalsIgnoreCase("OMV_Login_Scenarios"))) {
//            driver.quit();
//        } else {
//            driver = WebDriverInitialization.getDriver();
//            Properties properties = PropUtils.getProps(baseUtils.commonPropertyFile);
//            SessionId sessionId = ((ChromeDriver) driver).getSessionId();
//            if (sessionId == null || logger.isFailed()) {
//                driver.quit();
//                webDriverInitialization.launchSpecifiedBrowser("chrome");
//                webDriverInitialization.navigateToURL(PropUtils.getPropValue(baseUtils.inputProp, "url"));
//                driver = WebDriverInitialization.getDriver();
//                driver.findElement(By.cssSelector("mav-input[name='username']>input")).sendKeys(PropUtils.getPropValue(properties, "currentUserName"));
//                driver.findElement(By.cssSelector("mav-input[name='password']>input")).sendKeys(PropUtils.getPropValue(properties, "currentPassword"));
//                driver.findElement(By.id("login-button")).click();
//                webDriverInitialization.getClientGroupMidForLoggedInUser(PropUtils.getPropValue(properties, "currentUserName"));
//                try {
//                    Thread.sleep(3000);
//                } catch (Exception e) {
//
//                }
//            }
//        }
        String statusOfScenario = scenario.getStatus().toString();
        System.out.println(statusOfScenario);
        baseUtils.updateTestScenarioStatus(statusOfScenario);
    }

    @AfterStep
    public void addScreenShots() {
        driver = driverFactory.driver;
        if (logger.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            logger.attach(screenshot, "image/png", "image");
        }
    }
}