package stepDefinitions.stepDefinitionsUI;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
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
import java.util.Map;
import java.util.Properties;

public class HomeDashboard {

    private WebDriver driver;
    private LoginPage loginPage;
    private GmailPage gmailPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private HomePage homePage;
    private UsersPage usersPage;
    private CommonPage commonPage;
    private ReportsPage reportsPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public HomeDashboard(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        gmailPage = new GmailPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        homePage = new HomePage(driver);
        commonPage = new CommonPage(driver);
        reportsPage = new ReportsPage(driver);
    }

    @When("^User validate \"(.*)\" header is displayed as \"(.*)\"$")
    public void userClickOnDownloadButtonValidateFormatOfExcelSheet(String messageType, String message) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (messageType.equalsIgnoreCase("Welcome")) {
                String query = "select NAME from users where logon_id = '" + PropUtils.getPropValue(baseUtils.inputProp, "UserName") + "'";
                Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
                commonPage.assertTwoStrings(logger, basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='user-name']")), "Welcome " + queryResults.get("NAME"), "Welcome Message with username");
            } else {
                commonPage.assertTwoStrings(logger, message, basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='message-content ng-star-inserted']")), "Banner value");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select an account from account picker which is having \"(.*)\"$")
    public void userClickOnDownloadButtonValidateFormatOfExcelSheet(String balancePercentage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select a.ACTUAL_BALANCE, a.CREDIT_LIMIT, mcust.customer_no from accounts a\n" +
                    "left join m_customers mcust on mcust.customer_mid = a.customer_mid\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "inner join user_members um on um.member_oid = mcust.customer_mid\n" +
                    "inner join users u on u.user_oid = um.user_oid where mc.short_name = '" +
                    PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'and u.logon_id = '" +
                    PropUtils.getPropValue(baseUtils.inputProp, "UserName") + "'";
            logger.log(query);
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            int percentage = 0;
            for (int i = 0; i <= queryResults.size() - 1; i++) {
                if (Integer.parseInt(queryResults.get(i).get("CREDIT_LIMIT").split("\\.")[0]) == 0 || queryResults.get(i).get("ACTUAL_BALANCE").contains("-")) {
                    percentage = 86;
                } else {
                    percentage = Integer.parseInt(queryResults.get(i).get("ACTUAL_BALANCE").split("\\.")[0]) * 100 / Integer.parseInt(queryResults.get(i).get("CREDIT_LIMIT").split("\\.")[0]);
                }
                if (percentage != 86) {
                    if (balancePercentage.equalsIgnoreCase("Current balance<70% of credit limit") && (percentage < 70)) {
                        commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, queryResults.get(i).get("CUSTOMER_NO"));
                        break;
                    } else if (balancePercentage.equalsIgnoreCase("Current balance>70% and <85% of credit limit") && (percentage >= 70 && percentage < 85)) {
                        commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, queryResults.get(i).get("CUSTOMER_NO"));
                        break;
                    } else if (balancePercentage.equalsIgnoreCase("Current balance>85% of credit limit") && (percentage >= 85)) {
                        commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, queryResults.get(i).get("CUSTOMER_NO"));
                        break;
                    }
                } else if (balancePercentage.equalsIgnoreCase("Current balance>85% of credit limit")) {
                    commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, queryResults.get(i).get("CUSTOMER_NO"));
                    break;
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate chart is displayed in \"(.*)\" color$")
    public void userValidateChartColorIsDisplayedBasedOnAccountBalance(String color) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (color.equalsIgnoreCase("red") && basePage.whetherElementPresent(logger, By.cssSelector("div[class='progress-bar-limit red']"))) {
                logger.log("Chart is displayed in " + color + " as expected");
            } else if (color.equalsIgnoreCase("Green") && basePage.whetherElementPresent(logger, By.cssSelector("div[class='progress-bar-limit green']"))) {
                logger.log("Chart is displayed in " + color + " as expected");
            } else if (color.equalsIgnoreCase("Amber") && basePage.whetherElementPresent(logger, By.cssSelector("div[class='progress-bar-limit green amber']"))) {
                logger.log("Chart is displayed in " + color + " as expected");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate below types of balances in Account overview section$")
    public void userValidateBelowTypesOfBalancesInAccountOverviewSection(DataTable dt) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<String> values = dt.asList(String.class);
            String query = "select a.ACTUAL_BALANCE, a.CREDIT_LIMIT from accounts a\n" +
                    "left join m_customers mcust on mcust.customer_mid = a.customer_mid\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where mc.short_name = '" +
                    PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) +
                    "' and mcust.customer_no = " +
                    PropUtils.getPropValue(properties, "accountNumber");
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            for (int i = 0; i < values.size() - 1; i++) {
                if (values.get(i).equalsIgnoreCase("CurrentBalance")) {
                    commonPage.assertTwoStrings(logger, queryResults.get("ACTUAL_BALANCE"), basePage.userGetTextFromWebElement(logger, By.xpath("(//div[@class='left-header-section']/div/span)[1]")), values.get(i));
                } else if (values.get(i).equalsIgnoreCase("AvailableBalance")) {
                    commonPage.assertTwoStrings(logger, commonPage.getCurrencySymbolBasedOnClientCountry() + " " + (Integer.parseInt(queryResults.get("CREDIT_LIMIT")) - Integer.parseInt(queryResults.get("ACTUAL_BALANCE"))), basePage.userGetTextFromWebElement(logger, By.xpath("(//div[@class='left-header-section']/div/span)[1]")), values.get(i));
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate \"(.*)\" \"(.*)\" based on tag name \"(.*)\" and attribute name \"(.*)\", attribute value \"(.*)\"$")
    public void userValidateValueBasedOnTagNameAndAttributeValue(String validationType, String message, String tagName, String attributeName, String attributeValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (validationType.equalsIgnoreCase("value")) {
                List<WebElement> elements = basePage.getListOfElements(logger, By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']"));
                logger.log("locator is : " + By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']"));
                for (int i = 0; i <= elements.size() - 1; i++) {
                    if (message.equalsIgnoreCase(elements.get(i).getText())) {
                        logger.log("Expected value is : " + message + " actual value is : " + elements.get(i).getText());
                        break;
                    }
                    if (i == elements.size() - 1) {
                        logger.log("Expected value is : " + message + " actual value is : " + elements.get(i).getText());
                    }
                }
            } else if (validationType.equalsIgnoreCase("Presence")) {
                basePage.whetherElementPresent(logger, By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select an account which \"(.*)\" having \"(.*)\"$")
    public void userSelectAnAccountWhichIsHavingOrNotHavingTransactions(String isHaveOrNot, String recordType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.selectAnAccountBasedOnTransactionAvailability(logger, isHaveOrNot, recordType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate 4 records displayed in \"(.*)\" section with field values$")
    public void userValidate4TransactionsDisplayedInRecentTransactionsSection(String sectionType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.validateRecordDataBaseOnRecordType(logger, sectionType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click three dot icon in Home dash board for recent reports section$")
    public void userClickThreeDotIconInHomeDashboardForRecentReportsSection() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (reportsPage.getReportRecordCount(PropUtils.getPropValue(properties, "accountNumber")) > 0) {
                commonPage.clickUsingJSExecutor(logger, basePage.getWebElementUsingLocator(logger, By.xpath("(//div[@class='home-header-menu ng-star-inserted']/button/div/mav-svg-icon/fa-icon)[1]")));
            } else {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on \"(.*)\" option for \"(.*)\" module$")
    public void userClickOnButtonBasedOnModuleName(String buttonName, String moduleHeader) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (moduleHeader.equals("transactions")) {
                basePage.userClick(logger, By.xpath("(//div[@class='ng-star-inserted']/a)[1]"));
            } else if (moduleHeader.equals("reports")) {
                basePage.userClick(logger, By.xpath("(//div[@class='ng-star-inserted']/a)[2]"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}