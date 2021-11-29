package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.TransactionNotFoundException;
import utilities.ui.WebDriverInitialization;

import java.util.List;
import java.util.Properties;

public class VehiclesSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private VehiclesPage vehiclesPage;
    private CostCentresPage costCentresPage;
    private CommonPage commonPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public VehiclesSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        vehiclesPage = new VehiclesPage(driver);
        usersPage = new UsersPage(driver);
        costCentresPage = new CostCentresPage(driver);
        commonPage = new CommonPage(driver);
    }

    @And("^User validate default and expanded view of vehicle record$")
    public void userVerifiesWhetherTheVehiclesInformationWithDb() {
        Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testDataProperties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-vehicleID"), basePage.userGetTextFromWebElement(logger, By.xpath("((//mat-grid-list)[1]/div/mat-grid-tile)[1]/figure/div[1]/div")), "Vehicle ID");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-status"), basePage.userGetTextFromWebElement(logger, By.xpath("((//mat-grid-list)[1]/div/mat-grid-tile)[2]/figure/div[2]/div")), "Vehicle Status");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-licensePlate"), basePage.userGetTextFromWebElement(logger, By.xpath("((//mat-grid-list)[1]/div/mat-grid-tile)[3]/figure/div[3]/div")), "Vehicle License Plate");
            commonPage.assertTwoStrings(logger, "****" + PropUtils.getPropValue(testDataProperties, "vehicle-cardNo"), basePage.userGetTextFromWebElement(logger, By.xpath("((//mat-grid-list)[1]/div/mat-grid-tile)[4]/figure/div[4]/div")), "Card No");
            basePage.userClickJSExecutor(logger, By.xpath("(//mat-grid-list)[1]"));
            List<WebElement> fieldNames = basePage.getListOfElements(logger, By.xpath("(//div[@class='mat-expansion-panel-body'])[1]/mat-grid-list/div/mat-grid-tile/figure/div/div[1]"));
            List<WebElement> fieldValues = basePage.getListOfElements(logger, By.xpath("(//div[@class='mat-expansion-panel-body'])[1]/mat-grid-list/div/mat-grid-tile/figure/div/div[2]"));
            if (PropUtils.getPropValue(testDataProperties, "vehicle-status").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(0).getText(), "status");
            } else {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-status"), fieldValues.get(0).getText(), "status");
            }
            //**
            if (PropUtils.getPropValue(testDataProperties, "vehicle-licensePlate").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(1).getText(), "License Plate");
            } else {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-licensePlate"), fieldValues.get(1).getText(), "License Plate");
            }
            //**
            if (PropUtils.getPropValue(testDataProperties, "vehicle-cardNo").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(2).getText(), "cardNo");
            } else {
                commonPage.assertTwoStrings(logger, "****" + PropUtils.getPropValue(testDataProperties, "vehicle-cardNo"), fieldValues.get(2).getText(), "cardNo");
            }
            //**
            if (PropUtils.getPropValue(testDataProperties, "vehicle-vehicleType").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(3).getText(), "vehicleType");
            } else {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-vehicleType"), fieldValues.get(3).getText(), "vehicleType");
            }
            //**
            if (PropUtils.getPropValue(testDataProperties, "vehicle-vehicleMake").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(4).getText(), "vehicleMake");
            } else {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-vehicleMake"), fieldValues.get(4).getText(), "vehicleMake");
            }
            //**
            if (PropUtils.getPropValue(testDataProperties, "vehicle-model").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(5).getText(), "vehicle-model");
            } else {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-model"), fieldValues.get(5).getText(), "vehicle-model");
            }
            //**
            if (PropUtils.getPropValue(testDataProperties, "vehicle-description").equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "N/A", fieldValues.get(6).getText(), "vehicle-description");
            } else {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "vehicle-description"), fieldValues.get(6).getText(), "vehicle-description");
            }
        } else {
            logger.log(PropUtils.getPropValue(testDataProperties, "skipReason"));
        }
    }

    @And("^User validate \"(.*)\" module opened with search results of \"(.*)\"$")
    public void userVerifiesWhetherTheVehiclesInformationWithDb(String moduleName, String fieldValue) {
        Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testDataProperties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.assertTwoStrings(logger, "true", String.valueOf(basePage.whetherElementPresent(logger, By.xpath("//div[@class='title'][contains(text(),'" + moduleName + "')]"))), "Presence of field");
            commonPage.assertTwoStrings(logger, "1", usersPage.getCountOfRecords(logger), "Count of records");
            commonPage.assertTwoStrings(logger, "****" + PropUtils.getPropValue(testDataProperties, fieldValue), basePage.userGetTextFromWebElement(logger, By.xpath("//tbody[@role='rowgroup']/tr/td[2]")), "Field Value");
        } else {
            logger.log(PropUtils.getPropValue(testDataProperties, "skipReason"));
        }
    }
}
