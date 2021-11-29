package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.WebDriverInitialization;

import java.util.Map;
import java.util.Properties;

public class DriverSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private DriversPage driversPage;
    private CostCentresPage costCentresPage;
    private CommonPage commonPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public DriverSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        driversPage = new DriversPage(driver);
        usersPage = new UsersPage(driver);
        costCentresPage = new CostCentresPage(driver);
        commonPage = new CommonPage(driver);
    }

    @When("User get card no of first record from Drivers module")
    public void userGetDriverName() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String cardNo = basePage.userGetTextFromWebElement(logger, By.xpath("(//figure/div[4])[4]/div[@class='panel-header-field']"));
            PropUtils.setProps("Drivers-cardNo", cardNo.substring(5, cardNo.length()), baseUtils.testDataFilePath);
            driversPage.getDriverNameTitle(logger, cardNo.substring(5, cardNo.length()));
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter search keywords in \"(.*)\" by selecting search attribute as \"(.*)\"$")
    public void userEnterSearchKeywordsBySelectingSearchAttributeAs(String searchKeyWords, String searchAttributeName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.selectSearchAttribute(logger, searchAttributeName);
            commonPage.sleepForFewSeconds(2);
            driversPage.enterSearchKeyWords(logger, searchKeyWords);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User select the expanded view of the Drivers")
    public void userSelectTheExpandedViewOfTheDrivers() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            driversPage.selectGrid(logger);
            commonPage.sleepForFewSeconds(3);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies whether the drivers \"(.*)\" \"(.*)\" \"(.*)\" \"(.*)\" are matching the information with db$")
    public void userVerifiesWhetherTheDriversInformationWithDb(String driverName, String status, String cardNumber, String driverID) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String query = "select * from (select s.DRIVER_NAME,  c.Description, SUBSTR(s.CARD_NO, LENGTH(s.CARD_NO) - 5, 6) as Card_no, s.DRIVER_ID\n" +
                    "from search_driver_list_view s, constants c where s.DRIVER_STATUS_CID = c.CONSTANT_OID and s.customer_mid in \n" +
                    "(select CUSTOMER_MID from accounts_view where account_no= " + accountNumber + " \n" +
                    "and CLIENT_MID in (" + PropUtils.getPropValue(testDataProperties, "ClientMID") + ")  and s.card_no like '%" +
                    PropUtils.getPropValue(testDataProperties, "driver-cardNo") + "%')\n" +
                    "order by initcap (s.Driver_Name), s.card_no Asc )";
            logger.log(query);
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
//            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "Drivers-driverName"), commonPage.getNAIfStringIsNullOrEmptyFromMapObject(queryResults, "DRIVER_NAME"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "Drivers-status"), commonPage.getNAIfStringIsNullOrEmptyFromMapObject(queryResults, "DESCRIPTION"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "Drivers-cardNumber").replace("*", ""), commonPage.getNAIfStringIsNullOrEmptyFromMapObject(queryResults, "CARD_NO"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "Drivers-driverID"), commonPage.getNAIfStringIsNullOrEmptyFromMapObject(queryResults, "DRIVER_ID"));
            commonPage.sleepForFewSeconds(3);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
