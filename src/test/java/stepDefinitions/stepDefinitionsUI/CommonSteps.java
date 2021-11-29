package stepDefinitions.stepDefinitionsUI;

import com.paulhammant.ngwebdriver.NgWebDriver;
import io.cucumber.java.Scenario;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommonSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private AccountInfoPage accountInfoPage;
    private UsersPage usersPage;
    private ContactsPage contactsPage;
    private CommonPage commonPage;
    private ExcelUtils excelUtils;
    private OMVContextPage omvContextPage;
    private ReportsPage reportsPage;
    private ReportSetupPage reportSetupPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public CommonSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        contactsPage = new ContactsPage(driver);
        commonPage = new CommonPage(driver);
        excelUtils = new ExcelUtils(driver);
        omvContextPage = new OMVContextPage(driver);
        reportsPage = new ReportsPage(driver);
        accountInfoPage = new AccountInfoPage(driver);
        reportSetupPage = new ReportSetupPage(driver);
    }

    @Then("^User click \"(.*)\" button using Java Script executor which is present at position \"(.*)\" using tag name \"(.*)\"$")
    public void userClickOnRequiredButtonUsingJavaScriptExecutor(String buttonName, String positionOfButton, String tagName) {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testDataProp, "testStatus").equalsIgnoreCase("Skipped")) {
            String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
            usersPage.scrollDown(logger);
            commonPage.sleepForFewSeconds(2);
            commonPage.clickOnButtonUsingJSExecutor(logger, buttonName, positionOfButton, tagName);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(testDataProp, "skipReason"));
        }
    }

    @When("^User click on download button validate format of excel file is \"(.*)\"$")
    public void userClickOnDownloadButtonValidateFormatOfExcelSheet(String formatOfExcelFile) {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testDataProp, "testStatus").equalsIgnoreCase("Skipped")) {
            String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
            commonPage.clickOnDownloadButton(logger);
        } else {
            logger.log(PropUtils.getPropValue(testDataProp, "skipReason"));
        }
    }

    @When("^User click three dot icon of \"(.*)\" record in module \"(.*)\" based on \"(.*)\"$")
    public void userClickThreeDotIconOfRecordInModuleBasedOnValue(String recordNumber, String moduleName, String dbFieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            String recordCountInUI = "";
            By locator = null;
            List<Map<String, String>> queryResults = null;
            if (moduleName.equalsIgnoreCase("users")) {
                query = "select * from (select name as name, logon_id as userId, email_address as email_address,mobile_phone as mobile_phone,\n" +
                        "other_phone as other_phone, (select description from constants where constant_oid=(select logon_status_cid \n" +
                        "from users where user_oid=u.user_oid)) as user_status, (select description from access_groups where \n" +
                        "access_group_oid=(select access_group_oid from users where user_oid=u.user_oid)) as user_role \n" +
                        "from users u, user_members um, accounts ac where u.user_oid = um.user_oid \n" +
                        "and um.member_oid = ac.customer_mid and ac.account_no=" + usersPage.getAccountNumberFromUsersPage(logger) + " " +
                        "order by initcap(u.name)asc)";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='grid-header']/div/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("contacts")) {
                query = "select con.contact_oid,con.update_count,con.last_updated_at,con.last_updated_by,con.member_oid,con.member_type_cid\n" +
                        ",con.contact_type_oid ,con.is_default,con.contact_name ,con.postal_address_oid,con.street_address_oid,con.contact_title\n" +
                        ",con.email_address ,con.phone_business ,con.phone_fax ,con.phone_mobile_1 ,con.phone_mobile_2 ,ct.description as contact_type_description\n" +
                        ",cr.contact_hierarchy_cid ,cr.sequence_no ,ad.ADDRESS_LINE, ad.SUBURB ,ad.POSTAL_CODE ,st.description as state\n" +
                        "FROM contacts con INNER JOIN contact_types ct ON ct.contact_type_oid = con.contact_type_oid\n" +
                        "inner JOIN contact_rankings cr ON ct.contact_type_oid = cr.contact_type_oid\n" +
                        "RIGHT JOIN addresses ad ON con.STREET_ADDRESS_OID = ad.ADDRESS_OID\n" +
                        "RIGHT JOIN states st ON ad.STATE_OID = st.state_oid\n" +
                        "inner join m_customers mcust on mcust.customer_mid = con.member_oid\n" +
                        "WHERE mcust.customer_no='" + usersPage.getAccountNumberFromUsersPage(logger) + "'\n" +
                        "and con.member_type_cid=103 ORDER BY initcap(con.contact_name) asc";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='grid-header']/div/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("costCentres")) {
                query = "select CUSTOMER_COST_CENTRE_CODE,DESCRIPTION,SHORT_DESCRIPTION from customer_cost_centres\n" +
                        "where customer_mid in (select customer_mid from accounts_view where customer_no='" +
                        usersPage.getAccountNumberFromUsersPage(logger) + "' \n" +
                        "and client_mid in (select client_mid from m_clients where short_name = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "')) order by " +
                        "initcap(CUSTOMER_COST_CENTRE_CODE) asc";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='grid-header']/div/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("drivers")) {
                query = "select driver_name DRIVER_NAME,description DESCRIPTION,card_no CARD_NO,driver_id DRIVER_ID \n" +
                        "from (select s.DRIVER_NAME,  c.Description, SUBSTR(s.CARD_NO, LENGTH(s.CARD_NO) - 5, 6) as Card_no, s.DRIVER_ID\n" +
                        "from search_driver_list_view s, constants c where s.DRIVER_STATUS_CID = c.CONSTANT_OID and s.customer_mid in \n" +
                        "(select CUSTOMER_MID from accounts_view where account_no= " + usersPage.getAccountNumberFromUsersPage(logger) + " \n" +
                        "and CLIENT_MID=(select client_mid from m_clients where short_name='" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "' )) \n" +
                        "order by initcap (s.Driver_Name), s.card_no Asc )";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='grid-header']/div/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("vehicles")) {
                query = "select v.DESCRIPTION as Vehicles_Description, c.Description as Status, LICENSE_PLATE,  \n" +
                        "SUBSTR(v.CARD_NO, LENGTH(v.CARD_NO) - 5, 6) as Card_no \n" +
                        "from card_vehicles_enquiry v, constants c where v.VEHICLE_STATUS_CID = c.CONSTANT_OID and v.customer_mid in\n" +
                        "(select CUSTOMER_MID from accounts_view where account_no=" + usersPage.getAccountNumberFromUsersPage(logger) + " \n" +
                        "and CLIENT_MID=(select client_mid from m_clients where short_name='" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'))\n" +
                        "order by initcap (Vehicles_Description) Asc";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='grid-header']/div/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("reports")) {
                query = "select * from stored_reports where created_on <=sysdate and created_on >=sysdate-30\n" +
                        "and MEMBER_TYPE_CID in (select member_type_cid from user_members where user_oid=(\n" +
                        "select user_oid from users where logon_id = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, "ReadOnlyUserName") + "'))\n" +
                        "and member_oid in (select customer_mid from m_customers where customer_no='" +
                        usersPage.getAccountNumberFromUsersPage(logger) + "')\n" +
                        "order by created_on desc";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='grid-header']/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='view-page-list ng-star-inserted']"));
                recordCountInUI = recordCountInUI.substring(0, recordCountInUI.indexOf(' '));
            } else if (moduleName.equalsIgnoreCase("invoices")) {
                query = "select * from stored_reports where report_type_oid=(select report_type_oid from report_types\n" +
                        "where DESCRIPTION='Customer Statement' and client_mid=(select CLIENT_MID from M_CLIENTS\n" +
                        "where SHORT_NAME='Chevron MY'))and MEMBER_TYPE_CID in (select member_type_cid from user_members\n" +
                        "where user_oid=(select user_oid from users where logon_id = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, "ReadOnlyUserName") + "'))\n" +
                        "and member_oid in (select customer_mid from m_customers where customer_no='" +
                        usersPage.getAccountNumberFromUsersPage(logger) + "')\n" +
                        "and created_on <=sysdate and created_on >=sysdate-30 order by created_on desc";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='header-menu ng-star-inserted']/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("reportTemplates")) {
                query = "select * from report_assignments ra\n" +
                        "inner join report_types rt on rt.report_type_oid = ra.report_type_oid\n" +
                        "inner join users u on u.user_oid = ra.user_oid\n" +
                        "where u.logon_id = '" + PropUtils.getPropValue(baseUtils.inputProp, "ReadOnlyUserName") + "'  and ra.created_on >= sysdate-30";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                locator = By.xpath("(//div[@class='report-template-grid-header']/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='view-page-list ng-star-inserted']"));
                recordCountInUI = recordCountInUI.substring(0, recordCountInUI.indexOf(' '));
            } else if (moduleName.equalsIgnoreCase("Transactions") || moduleName.equalsIgnoreCase("Cards") || moduleName.equalsIgnoreCase("Pricing")) {
                locator = By.xpath("(//td[@role='gridcell']/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            } else {
                locator = By.xpath("(//div[@class='grid-header']/div/div/button/div/mav-svg-icon)[1]");
                recordCountInUI = usersPage.getCountOfRecords(logger);
            }
//                if (Integer.parseInt(usersPage.getCountOfRecords(logger))) >= 1 || queryResults.size() >= 1) {
//                    basePage.userClick(logger, locator);
//                } else {
//                    PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
//                }
            if (moduleName.equalsIgnoreCase("accounts")) {
                PropUtils.setProps("accounts-" + dbFieldName, basePage.userGetTextFromWebElement(logger, By.xpath("(//figure/div[1]/div)[1]")), baseUtils.testDataFilePath);
            }
            if (Integer.parseInt(recordCountInUI) >= 1 || queryResults.size() >= 1) {
                basePage.userClick(logger, locator);
                logger.log("Clicked on three dot icon in module '" + moduleName + "'");
            } else {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "This scenario will be skipped because no records present in UI", baseUtils.testDataFilePath);
            }
            if (moduleName.equalsIgnoreCase("accounts")) {
                PropUtils.setProps("accounts-" + dbFieldName, basePage.userGetTextFromWebElement(logger, By.xpath("(//figure/div[1]/div)[1]")), baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on \"(.*)\" based on tag name \"(.*)\"$")
    public void userClickOnButtonBasedOnTagName(String buttonName, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.clickButtonUsingSpecificTagName(logger, buttonName, tagName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }


    @When("^User set accountNumber value is property file like \"(.*)\" in Transactions module$")
    public void userGetAccountNumberFromAccountPickerAndSaveInPropertyFile(String valueOfAccountNumberProperty) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (valueOfAccountNumberProperty.equalsIgnoreCase("accountNumberFromAccountPicker")) {
                if (usersPage.getAccountNumberFromUsersPage(logger).equalsIgnoreCase("Select account")) {
                    PropUtils.setProps("accountNumberInTransactionsModule", "", baseUtils.testDataFilePath);
                } else {
                    PropUtils.setProps("accountNumberInTransactionsModule", usersPage.getAccountNumberFromUsersPage(logger), baseUtils.testDataFilePath);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on \"(.*)\" in module \"(.*)\" based on tag name \"(.*)\" which is at position \"(.*)\" and \"(.*)\" mentioned in property file$")
    public void userClickOnButtonBasedOnTagNameWhichIsAtPosition(String buttonName, String moduleName, String tagName, String position, String isMentioned) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (isMentioned.equalsIgnoreCase("is") || (isMentioned.equalsIgnoreCase("Yes"))) {
                basePage.userClick(logger, By.xpath("(//div[contains(text(),'" + buttonName + "')])[1]"));
            } else {
                basePage.userClick(logger, By.xpath("//div[contains(text(),'" + buttonName + "')]"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User open the excel sheet and validate the data present in it with the database for module \"(.*)\" based on primary key \"(.*)\"$")
    public void userOpenTheExcelSheetAndValidateTheDataPresentInItWithTheDatabase(String moduleName, String primaryKey) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            File files = new File(System.getProperty("user.home") + "/Downloads/");
            File file = commonPage.getLastModifiedFile(logger, files);
            logger.log("File is : " + file.getName());
            logger.log("File path is : " + file.getAbsolutePath());
            List<Map<String, String>> queryResultsInList = null;
//            Thread.sleep(3000);
            String accountNumber = "";
//            if (!moduleName.equalsIgnoreCase("accountSelection") && !moduleName.contains("OMV")) {
//                accountNumber = usersPage.getAccountNumberFromUsersPage(logger));
//            }
            String query = "";
            if (moduleName.equalsIgnoreCase("Users")) {
                query = "select mcust.customer_no as accountNumber, mcust.name as accountName," +
                        "u.logon_id as Username,u.name as Fullname,u.email_address as Emailaddress," +
                        "u.mobile_phone as Mobilephone, u.other_phone as Otherphone,\n" +
                        "ag.description as Role, c.description as Status From users u\n" +
                        "inner join constants c on c.constant_oid = u.logon_status_cid\n" +
                        "inner join access_groups ag on ag.access_group_oid = u.access_group_oid\n" +
                        "inner join user_members um on um.user_oid = u.user_oid\n" +
                        "inner join m_customers mcust on mcust.customer_mid = um.member_oid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mcust.customer_no = '" + accountNumber + "' and mc.short_name = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "' order by initcap(u.name), initcap(u.logon_id) asc";
            }
            if (moduleName.equalsIgnoreCase("Drivers")) {
                query = "select d.customer_no as Accountnumber, av.Name as Accountname,\n" +
                        "SUBSTR(d.CARD_NO, LENGTH(d.CARD_NO) - 5, 6) as Cardnumber,d.DRIVER_ID as DriverID, " +
                        "d.DRIVER_NAME as DriverName, d.SHORT_NAME as ShortName,\n" +
                        "c.Description as Status\n" +
                        "from search_driver_list_view d\n" +
                        "inner join constants c on d.DRIVER_STATUS_CID = c.CONSTANT_OID\n" +
                        "inner join accounts_view av on av.customer_mid=d.customer_mid\n" +
                        "where d.customer_mid in\n" +
                        "(select av.CUSTOMER_MID from accounts_view av where account_no=" + accountNumber + "\n" +
                        "and CLIENT_MID=(select client_mid from m_clients where short_name='" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'))\n" +
                        "order by initcap (d.Driver_Name)Asc";
            }
            if (moduleName.equalsIgnoreCase("Vehicles")) {
                query = "select v.customer_no as Accountnumber, av.Name as Accountname,\n" +
                        "SUBSTR(v.CARD_NO, LENGTH(v.CARD_NO) - 5, 6) as CardNumber,  v.DESCRIPTION as VehicleDescription,\n" +
                        " v.LICENSE_PLATE as LicensePlate, v.vehicle_id as Vehicleid,c.Description as Status\n" +
                        "from card_vehicles_enquiry v\n" +
                        "inner join constants c on v.VEHICLE_STATUS_CID= c.CONSTANT_OID\n" +
                        "inner join accounts_view av on av.customer_mid=v.customer_mid\n" +
                        "where v.customer_mid in\n" +
                        "(select av.CUSTOMER_MID from accounts_view av where account_no=" + "0700000006" + "\n" +
                        "and CLIENT_MID=(select client_mid from m_clients where short_name='" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'))\n" +
                        "order by initcap (v.description) Asc";
            }
            if (moduleName.equalsIgnoreCase("CostCentres")) {
                query = "select cc.CUSTOMER_COST_CENTRE_CODE as CostCentre, cc.SHORT_DESCRIPTION as Name,\n" +
                        "cc.DESCRIPTION as Description, mcust.Name as AccountName,mcust.customer_no as AccountNumber\n" +
                        "from Customer_cost_centres cc\n" +
                        "inner join m_customers mcust on mcust.customer_mid = cc.customer_mid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mcust.customer_no = '" + accountNumber + "' and mc.short_name = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'" +
                        "order by initcap(cc.CUSTOMER_COST_CENTRE_CODE)";
            }
            if (moduleName.equalsIgnoreCase("Cards")) {
                query = "select mcust.customer_no as accountNumber, mcust.name as accountName,\n" +
                        "c.card_no as cardNumber, cso.description as cardStatus, cf.description as cardOffer,\n" +
                        "cp.description as cardProduct, const.description as cardType, c.EXPIRES_ON as expiryDate,\n" +
                        "mcust.EMBOSSING_NAME as embossingLine1 from card_offers cf\n" +
                        "inner join card_program_offers cfo on cfo.card_offer_oid = cf.card_offer_oid\n" +
                        "inner join m_customers mcust on mcust.card_program_oid = cfo.card_program_oid\n" +
                        "inner join cards c on c.customer_mid = mcust.customer_mid\n" +
                        "inner join card_products cp on cp.card_product_oid = c.card_product_oid\n" +
                        "inner join constants const on const.constant_oid = cp.CARD_TYPE_CID\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "inner join card_status_ols_mapping csom on csom.card_status_oid = c.card_status_oid\n" +
                        "inner join card_status_ols cso on cso.card_status_ols_oid = csom.card_status_ols_oid\n" +
                        "where mcust.customer_no = '" + accountNumber + "' and mc.short_name = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "' order by c.card_No asc";
            }
            if (moduleName.equalsIgnoreCase("Contacts")) {
                query = "select mcust.customer_no as accountNumber, mcust.name as accountName, c.CONTACT_NAME as NAME, c.email_address as emailAddress,\n" +
                        "ct.description as contactType, c.is_default as contactLevel, c.PHONE_MOBILE_1 as mobilePhone, c.PHONE_BUSINESS as otherPhone,\n" +
                        "c.PHONE_FAX as fax, c.CONTACT_TITLE as jobTitle, a.address_line as Contactstreetaddress, a.SUBURB as Cityortown,\n" +
                        "s.description as Stateorprovince, a.POSTAL_CODE as postalCode from contacts c\n" +
                        "inner join contact_types ct on ct.CONTACT_TYPE_OID = c.CONTACT_TYPE_OID\n" +
                        "inner join addresses a on a.ADDRESS_OID = c.STREET_ADDRESS_OID\n" +
                        "inner join states s on s.state_oid = a.state_oid\n" +
                        "inner join m_customers mcust on mcust.customer_mid = c.member_oid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mcust.customer_no = '" + accountNumber + "' and mc.short_name = '" +
                        PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'";
            }
            if (moduleName.equalsIgnoreCase("AccountSelection")) {
                query = "select mcg.name as client, mcust.name as AccountName, mcust.customer_No as AccountNumber," +
                        "mc.name as country, ast.description as status  from m_customers mcust\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "inner join m_client_groups mcg on mcg.client_group_mid = mc.client_group_mid\n" +
                        "inner join accounts a on a.CUSTOMER_MID = mcust.CUSTOMER_MID\n" +
                        "inner join account_status ast on ast.account_status_oid = a.account_status_oid\n" +
                        "where mc.name = 'Chevron Philippines, Inc.'";
            }
            if (moduleName.equalsIgnoreCase("OMVTransactions")) {
                query = "select te.customer_no as AccountNo,te.customer_name as AccountName,te.card_no as cardNumber,\n" +
                        "ptrans.description as product, te.reference,te.authorisation_no as authorisationNumber,\n" +
                        "const1.description as status,\n" +
                        "CASE WHEN const.CONSTANT_OID = te.transaction_category_cid THEN c.description Else null End as transactionCategory \n" +
                        ", tt.TRANSACTION_TYPE as transctiontype,\n" +
                        "ml.Location_No as locationID,ml.name as locationName, a.ADDRESS_LINE as locationAddress,\n" +
                        "a.SUBURB as locationcity,a.POSTAL_CODE as locationPostalZipCode,s.description as Locationstate,\n" +
                        "c.COUNTRY_CODE as locationCountry,te.extra_card_no as secondCardNumber,te.odometer,\n" +
                        "ct.description as captureType,v.VEHICLE_ID as vehicleID,v.description as vehicleDescription,\n" +
                        "ccc.description as costcentre,te.terminal_id as terminalID, d.driver_id as driverID, \n" +
                        "d.driver_name as driverName,te.fleet_id as fleetID,to_char(tli.quantity,'9999990.000') as fuelquantity, \n" +
                        "to_char(tli.CUSTOMER_UNIT_PRICE/100,'9999990.0000') as fuelunitprice,tli.ORIGINAL_VALUE as fuelTransactionValue,\n" +
                        " to_char(te.customer_amount,'9999990.00') as totalTransactionAmount,te.CUSTOMER_CURRENCY_CODE_CHAR as customerCurrencyCode,\n" +
                        "  to_char(te.CUSTOMER_REBATE_TOTAL,'9999990.00') as totalRebates,to_char(te.FEE_TOTAL_TAX,'9999990.00') as totalTaxes,\n" +
                        " to_char(te.FEE_TOTAL_AMOUNT,'9999990.00') as totalFees, te.invoice_no as invoiceNumber, \n" +
                        " at.description as adjustmentType, te.approved_by as approvedBy from transaction_enquiry te\n" +
                        " left join transaction_types tt on tt.transaction_type_oid = te.transaction_type_oid\n" +
                        " left join m_locations ml on ml.location_mid = te.location_mid\n" +
                        " left join constants const on const.constant_oid = te.transaction_category_cid\n" +
                        "left join constants const1 on const1.constant_oid = te.POS_TRANSACTION_STATUS_CID\n" +
                        " left join addresses a on a.address_oid = CASE WHEN ml.POSTAL_ADDRESS_OID is not null THEN \n" +
                        "ml.POSTAL_ADDRESS_OID ELSE ml.STREET_ADDRESS_OID END\n" +
                        "left join states s on s.state_oid = a.state_oid\n" +
                        "left join countries c on c.country_oid = a.country_oid\n" +
                        "left join capture_types ct on ct.capture_type_oid = te.capture_type_oid\n" +
                        "left join vehicles v on v.vehicle_oid = te.vehicle_oid\n" +
                        "left join customer_cost_centres ccc on ccc.customer_cost_centre_oid = te.customer_cost_centre_oid\n" +
                        "left join drivers d on d.driver_oid = te.driver_oid\n" +
                        "left join transaction_line_items tli on tli.transaction_oid = te.transaction_oid and tli.line_number = 1\n" +
                        "left join products p on p.product_oid = tli.product_oid\n" +
                        "left join adjustment_types at on at.adjustment_type_oid = te.adjustment_type_oid\n" +
                        "left join product_translations ptrans on ptrans.external_code = tli.product_code and ptrans.product_oid = tli.product_oid\n" +
                        "where te.customer_no = " + basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'account-number')]")) + " " +
                        "and te.reference = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.testDataFile), "LatestTransactionReference") + "' order by te.effective_at asc";
            }
            if (moduleName.equalsIgnoreCase("OMVAuthorisations")) {
                query = "select AccountNo, ACCOUNTNAME, CARDNUMBER, PRODUCT, TRACENUMBER, AUTHORISATIONNUMBER, TRANSACTIONTIME, STATUS, \n" +
                        "RESPONSECODE, LOCATIONID, LOCATIONNAME, LOCATIONADDRESS,locationcity,locationPostalZipCode,\n" +
                        "LOCATIONSTATE,LOCATIONCOUNTRY, SECONDCARDNUMBER, ODOMETER, VEHICLEID,\n" +
                        "VEHICLEDESCRIPTION, COSTCENTRE, TERMINALID, DRIVERID, DRIVERNAME,\n" +
                        "FUELQUANTITY,FUELUNITPRICE, FUELTRANSACTIONVALUE, OTHERQUANTITY, OTHERUNITPRICE, OTHERTRANSACTIONVALUE,totalValue,currencyCode from \n" +
                        "(select mcust.customer_no as accountNo, mcust.name as accountName, at.card_no as cardNumber,\n" +
                        "pt.description as product, at.reference as traceNumber, at.AUTHORISATION_NO as authorisationNumber,\n" +
                        "at.RECEIVED_AT as transactionTime,const.description as status,at.RESPONSE_CODE as responseCode,\n" +
                        "ml.LOCATION_no as locationID,at.LOCATION_NAME as locationName,a.ADDRESS_LINE as locationAddress,\n" +
                        "a.SUBURB as locationcity,a.POSTAL_CODE as locationPostalZipCode,\n" +
                        "s.description as locationstate,c.description as locationCountry,at.second_card_no as secondCardNumber,at.odometer,\n" +
                        "v.VEHICLE_ID as vehicleID,v.description as vehicleDescription,\n" +
                        "ccc.CUSTOMER_COST_CENTRE_CODE as costcentre,\n" +
                        "RANK()over( ORDER BY cc.LAST_UPDATED_AT asc) AS rank,\n" +
                        "at.terminal_id as terminalID, d.driver_id as driverID, \n" +
                        "d.driver_name as driverName,to_char(at.ORIGINAL_AMOUNT,'9999990.00') as totalValue,at.currency_code as currencycode,\n" +
                        "to_char(FUELQUANTITY,'9999990.000')as FUELQUANTITY,to_char(OTHERQUANTITY,'9999990.000')as OTHERQUANTITY,\n" +
                        "to_char(FUELUNITPRICE,'9999990.0000')as FUELUNITPRICE, \n" +
                        "to_char(OTHERUNITPRICE,'9999990.0000')as OTHERUNITPRICE, \n" +
                        "to_char(FUELTRANSACTIONVALUE,'9999990.00')as FUELTRANSACTIONVALUE, \n" +
                        "to_char(OTHERTRANSACTIONVALUE,'9999990.00')as OTHERTRANSACTIONVALUE from auth_transaction_logs at\n" +
                        "left join  \n" +
                        "(\n" +
                        "select sum(FUELQUANTITY) as FUELQUANTITY,\n" +
                        "sum(Otherquantity) as Otherquantity,\n" +
                        "sum(fuelUnitPrice) as fuelUnitPrice,\n" +
                        "sum(OtherunitPrice) as OtherunitPrice,\n" +
                        "sum(fuelTransactionValue) as fuelTransactionValue,\n" +
                        "sum(OtherTransactionValue) as OtherTransactionValue,\n" +
                        "AUTH_TRANSACTION_LOG_OID,\n" +
                        "product_oid,\n" +
                        "product_code\n" +
                        "from (select case when is_fuel ='Y' then  sum(quantity)else  null end as FUELQUANTITY,case when is_fuel ='N' then  sum(quantity)\n" +
                        "else  null end as Otherquantity,case when is_fuel ='Y' then  sum(unit_price)else  null end as fuelUnitPrice,\n" +
                        "case when is_fuel ='N' then  sum(unit_price)else  null end as OtherunitPrice,case when is_fuel ='Y' then  sum(value)\n" +
                        "else  null end as fuelTransactionValue,case when is_fuel ='N' then  sum(value)else  null end as OtherTransactionValue,\n" +
                        "is_fuel,AUTH_TRANSACTION_LOG_OID,product_oid,product_code\n" +
                        "from auth_trans_line_items group by is_fuel,AUTH_TRANSACTION_LOG_OID,product_oid,product_code) group by AUTH_TRANSACTION_LOG_OID,product_oid,product_code )atli on atli.AUTH_TRANSACTION_LOG_OID = at.AUTH_TRANSACTION_LOG_OID\n" +
                        "left join product_translations pt on pt.product_oid = atli.product_oid and atli.product_code = pt.external_code\n" +
                        "left join m_customers mcust on mcust.customer_mid = at.customer_mid\n" +
                        "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "left join m_locations ml on ml.location_no = at.location_code\n" +
                        "left join addresses a on a.address_oid = ml.STREET_ADDRESS_OID\n" +
                        "left join constants const on const.constant_oid = at.HOST_AUTH_PROCESS_STATUS_CID\n" +
                        "left join countries c on c.country_oid = a.country_oid\n" +
                        "left join states s on s.state_oid = a.state_oid\n" +
                        "left join cards c on c.card_no = at.card_no\n" +
                        "left join vehicles v on v.card_oid = c.card_oid\n" +
                        "left join drivers d on d.card_oid = c.card_oid\n" +
                        "left join  cost_centres cc on cc.card_oid = c.card_oid\n" +
                        "left join customer_cost_centres ccc on ccc.customer_cost_centre_oid = cc.customer_cost_Centre_oid\n" +
                        "where mcust.customer_no = '" + PropUtils.getPropValue(properties, "customerNumber") + "' " +
                        "and mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ") and at.reference = '" + PropUtils.getPropValue(properties, "LatestTraceNo") + "'\n" +
                        " order by ccc.LAST_UPDATED_AT desc ) A\n" +
                        "where A.rank =1";
            }
            logger.log(query);
            queryResultsInList = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            logger.log("Size of query results " + queryResultsInList.size());
            if (queryResultsInList.size() > 0) {
                logger.log("file name is : " + file.getName());
                String convertedFileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + ".xlsx";
                logger.log("Converted File Name is : " + convertedFileName);
                excelUtils.convertCsvToXlsx(logger, file.getPath(), convertedFileName, queryResultsInList.size());
                excelUtils.readDataFromExcelSheetAndCompareWithDb(logger, convertedFileName, queryResultsInList, primaryKey, moduleName);
                commonPage.sleepForFewSeconds(2);
            } else {
                logger.log("No records available to export the data in to an excel sheet");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on sort option \"(.*)\"$")
    public void userClickOnSortOption(String sortOption) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(2);
            commonPage.clickButtonUsingSpan(logger, "Sort");
            commonPage.clickUsingDiv(logger, sortOption);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate error message using \"(.*)\" tag \"(.*)\"$")
    public void userValidateErrorMessageUsingSpan(String errorMessage, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (!tagName.equalsIgnoreCase("Span") && (!tagName.equalsIgnoreCase("div"))) {
                commonPage.assertTwoStrings(logger, errorMessage, basePage.userGetTextFromWebElement(logger, By.tagName(tagName)), "ErrorMesssage");
            } else {
                commonPage.validateTextUsingtag(logger, errorMessage, tagName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate message \"(.*)\" based on tag name \"(.*)\"$")
    public void userValidateMessageBasedOnTagName(String message, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (!tagName.equalsIgnoreCase("Span") && (!tagName.equalsIgnoreCase("div"))) {
                commonPage.assertTwoStrings(logger, message, basePage.userGetTextFromWebElement(logger, By.tagName(tagName)), "Message");
            } else {
                commonPage.validateTextUsingtag(logger, message, tagName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate presence of \"(.*)\" field with \"(.*)\" tag$")
    public void userValidationPresentOfField(String fieldName, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (tagName.equalsIgnoreCase("div")) {
                commonPage.verifyPresenceOfDivText(logger, fieldName);
            } else if (tagName.equalsIgnoreCase("span")) {
                commonPage.verifyPresenceOSpanText(logger, fieldName);
            } else {
                commonPage.verifyPresenceOfText(logger, fieldName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @When("^User wait until the locator \"(.*)\" available by using locator type \"(.*)\"$")
    public void userWaitUntilTheLocatorAvailable(String locator, String typeOfLocator) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By eleLocator = null;
            if (typeOfLocator.equalsIgnoreCase("xpath")) {
                eleLocator = By.xpath(locator);
            } else if (typeOfLocator.equalsIgnoreCase("cssSelector")) {
                eleLocator = By.cssSelector(locator);
            }
            WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(eleLocator));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate presence of \"(.*)\" field with \"(.*)\" tag which contains special characters so verify using pageSource$")
    public void userValidatePresenceOfTextUsingPageSource(String fieldName, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (driver.getPageSource().contains(fieldName)) {
                logger.log("'" + fieldName + "' is present as expected");
            } else {
                logger.log("'" + fieldName + "' is not present");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click three dot icon based on fieldName \"(.*)\" present in column \"(.*)\" is from property file \"(.*)\", name of property file \"(.*)\"$")
    public void userClickOnThreeDotIconBasedOnFieldNamePresentInRow(String fieldName, String columnNumber, String isFromPropertyFile, String propertyFileName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String propFieldName = "";
            if (isFromPropertyFile.equalsIgnoreCase("Yes") || isFromPropertyFile.equalsIgnoreCase("True")) {
                if (propertyFileName.equalsIgnoreCase("TestDataProperties")) {
                    propFieldName = PropUtils.getPropValue(properties, fieldName);
                } else if (propertyFileName.equalsIgnoreCase("InputDataProperties") || propertyFileName.equalsIgnoreCase("InputProperties")) {
                    propFieldName = PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), fieldName);
                }
            }
            boolean isClickedOnIcon = false;
            List<WebElement> allRows = basePage.getListOfElements(logger, By.xpath("//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')]"));
            for (int i = 1; i <= allRows.size(); i++) {
                if (basePage.userGetTextFromWebElement(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')][" + i + "])/td[" + columnNumber + "]")).trim().equalsIgnoreCase(propFieldName.trim())) {
                    PropUtils.setProps(fieldName, (basePage.userGetTextFromWebElement(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')][" + i + "])/td[" + columnNumber + "]"))), baseUtils.testDataFilePath);
                    List<WebElement> allRowData = basePage.getListOfElements(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')][" + i + "])/td"));
                    commonPage.sleepForFewSeconds(2);
                    usersPage.scrollDown(logger);
                    commonPage.sleepForFewSeconds(1);
                    basePage.userClick(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')][" + i + "])/td[" + allRowData.size() + "]"));
                    commonPage.sleepForFewSeconds(2);
                    isClickedOnIcon = true;
                }
            }
            if (!isClickedOnIcon) {
                logger.log("Record is not present");
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "Record is not present hence cannot proceed with test case execution", baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate presence of \"(.*)\" with property value \"(.*)\" from \"(.*)\" using tag \"(.*)\"$")
    public void userValidatePresenceOfTextWithPropertyValue(String fieldName, String propValue, String propFileType, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            fieldName = fieldName + PropUtils.getPropValue(properties, propValue);
            if (tagName.equalsIgnoreCase("div")) {
                commonPage.verifyPresenceOfDivText(logger, fieldName);
            } else if (tagName.equalsIgnoreCase("span")) {
                commonPage.verifyPresenceOSpanText(logger, fieldName);
            } else {
                commonPage.verifyPresenceOfText(logger, fieldName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter value \"(.*)\" using tag name \"(.*)\", attribute name \"(.*)\", attribute value \"(.*)\" with remaining locator address \"(.*)\"$")
    public void userEnterValueUsingTagNameAttributeNameAttributeValueWithRemainingLocatorAddress(String fieldValue, String tagName, String attributeName, String attributeValue, String remainingLocatorAddress) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userTypeIntoTextField(logger, By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']" + remainingLocatorAddress), fieldValue);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User verify button \"(.*)\" status \"(.*)\" using tag name \"(.*)\"$")
    public void userValidationPresentOfField(String buttonName, String statusOfButton, String tagName) {
        boolean expStatus = false;
        if (statusOfButton.equalsIgnoreCase("enabled")) {
            expStatus = true;
        } else {
            expStatus = false;
        }
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By locator = null;
            Boolean actStatus = null;
            locator = By.xpath("//" + tagName + "[contains(text(),'" + buttonName + "')]");
            try {
                basePage.waitUntilElementLocatedOrRefreshed(locator);
                NgWebDriver ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
                ngWebDriver.waitForAngularRequestsToFinish();
                actStatus = driver.findElement(locator).isEnabled();
            } catch (Exception e) {
                try {
                    actStatus = driver.findElement(locator).isDisplayed();
                } catch (Exception e1) {
                    actStatus = false;
                    logger.log("Element is not displayed as expected");
                }
            }
            if (expStatus == actStatus) {
                logger.log("Button '" + buttonName + "' is '" + statusOfButton + "' as expected: the act status is : " + actStatus);
            } else {
                logger.log("Button '" + buttonName + "' is not'" + statusOfButton + "' which is not expected: the act status is : " + actStatus);
            }
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate button \"(.*)\" status \"(.*)\" using tag name \"(.*)\", attribute name \"(.*)\", attribute value \"(.*)\" and get status of button using attribute \"(.*)\"$")
    public void userValidateButtonStatusUsingTagNameAndAttributeName(String buttonName, String statusOfButton, String tagName, String attributeName, String attributeValue, String getAttributeValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String expStatusOfButton = statusOfButton;
            if (statusOfButton.equalsIgnoreCase("disabled")) {
                statusOfButton = "true";
            } else {
                statusOfButton = "false";
            }
            //log = test.createNode(new GherkinKeyword("When"), "User validate button " + buttonName + " status " + statusOfButton + " using tag name " + tagName + ", attribute name " + attributeName + ", attribute value " + attributeValue + " and get status of button using attribute " + getAttributeValue);
            if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
                logger.log("Locator is : " + By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']"));
                String actStatus = commonPage.getAttributeValue(By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']"), getAttributeValue);
                if (statusOfButton.equalsIgnoreCase(actStatus)) {
                    logger.log("Button '" + buttonName + "' is '" + expStatusOfButton + "' as expected");
                } else {
                    logger.log("Button '" + buttonName + "' is not'" + expStatusOfButton + "' which is not expected");
                }
                commonPage.sleepForFewSeconds(1);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click button \"(.*)\" using tag name \"(.*)\", attribute name \"(.*)\", attribute value \"(.*)\" based on position \"(.*)\"$")
    public void userClickButtonUsingTagNameAttributeNameAttributeValueBasedOnItsPosition(String buttonName, String tagName, String attributeName, String attributeValue, String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(1);
            logger.log("Locator is : " + By.xpath("(//" + tagName + "[@" + attributeName + "='" + attributeValue + "'])[" + position + "]"));
            if (buttonName.equalsIgnoreCase("threeDotIcon")) {
                basePage.userClick(logger, By.xpath("(//" + tagName + "[@" + attributeName + "='" + attributeValue + "'])[" + position + "]"));
            } else {
                basePage.userClick(logger, By.xpath("(//" + tagName + "[@" + attributeName + "='" + attributeValue + "'])[" + position + "]"));
            }
            logger.log("Clicked on the field " + buttonName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User make the driver sleep for few seconds \"(.*)\"$")
    public void userMakeTheDriverSleepForFewSeconds(String waitForFewSeconds) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(Integer.parseInt(waitForFewSeconds));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click field \"(.*)\" is from properties file \"(.*)\" based on tagname \"(.*)\" in position \"(.*)\"$")
    public void userClickFieldIsFromPropertiesBasedOnTagNameForPosition(String field, String isFromPropertyFile, String tagName, String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (isFromPropertyFile.equalsIgnoreCase("Yes")) {
                basePage.userClick(logger, By.xpath("(//" + tagName + "[contains(text(),'" + PropUtils.getPropValue(properties, field) + "')])[" + Integer.parseInt(position) + "]"));
                logger.log("Clicked on the field " + PropUtils.getPropValue(properties, field));
            } else {
                basePage.userClick(logger, By.xpath("(//" + tagName + "[contains(text(),'" + field + "')])[" + Integer.parseInt(position) + "]"));
                logger.log("Clicked on the field " + field);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User perform action \"(.*)\" for fields based on validation control for section \"(.*)\"$")
    public void userClickFieldIsFromPropertiesBasedOnTagNameForPosition(String action, String sectionName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.handleFieldBehaviourBasedOnValidationControlForSpecificModule(logger, sectionName, action);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User verify the presence of locator \"(.*)\" and locator type \"(.*)\"$")
    public void userVerifyPresenceOfValueBasedOnLocatorAndItsType(String locator, String locatorType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By locatorValue = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                locatorValue = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                locatorValue = By.cssSelector(locator);
            }
            basePage.whetherElementPresent(logger, locatorValue);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on \"(.*)\" using tag name \"(.*)\" if any field is edited based on property \"(.*)\"")
    public void userclickOnFieldUsingTagNameBasedOnPropertyValue(String buttonName, String tagName, String propertyField) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (PropUtils.getPropValue(properties, propertyField).equalsIgnoreCase("true")) {
                commonPage.clickButtonUsingSpecificTagName(logger, buttonName, tagName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User handle address component in \"(.*)\" section in \"(.*)\" module with \"(.*)\" address$")
    public void userHandleAddressComponentInSectionBasedOnModule(String sectionName, String moduleName, String addressAction) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.handleAddressComponentOfASectionBasedOnModule(logger, sectionName, moduleName, addressAction);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User set property \"(.*)\" value as \"(.*)\" in \"(.*)\"$")
    public void userClickButtonUsingTagNameAttributeNameAttributeValue(String propertyName, String propValue, String fileName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String filePath = "";
        if (fileName.equalsIgnoreCase("TestDataProperties")) {
            filePath = baseUtils.testDataFilePath;
        } else if (fileName.equalsIgnoreCase("inputDataProperties")) {
            filePath = baseUtils.inputDataFilePath;
        }
        if(propertyName.equalsIgnoreCase("testStatus")){
            PropUtils.setProps(propertyName,propValue,filePath);
        }else {
            if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
                if (propValue.equalsIgnoreCase("pinnedAccountNumberInUI")) {
                    PropUtils.setProps(propertyName, " = " + usersPage.getAccountNumberFromUsersPage(logger), filePath);
                } else {
                    PropUtils.setProps(propertyName, propValue, filePath);
                }
            } else {
                logger.log(PropUtils.getPropValue(properties, "skipReason"));
            }
        }
    }

    @When("^User clear property file \"(.*)\"$")
    public void userClearPropertyFile(String fileName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String filePath = "";
        if (fileName.equalsIgnoreCase("TestDataProperties")) {
            filePath = baseUtils.testDataFilePath;
        } else if (fileName.equalsIgnoreCase("inputDataProperties")) {
            filePath = baseUtils.inputDataFilePath;
        }
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            PropUtils.clearProps(filePath);
            WebDriverInitialization webDriverInitialization = new WebDriverInitialization();
            webDriverInitialization.getClientGroupMidForLoggedInUser(PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate address stored in database for section \"(.*)\" based on its action \"(.*)\" from propertyFile \"(.*)\"$")
    public void userValidateAddressStoredInDatabaseForAModuleBasedOnItsAction(String sectionName, String action, String isFromPropertyFile) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String filePath = "";
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (isFromPropertyFile.equalsIgnoreCase("Yes") || isFromPropertyFile.equalsIgnoreCase("True")) {
                action = PropUtils.getPropValue(properties, action);
            }
            commonPage.validationAddressInDBForAModule(logger, sectionName, action);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User skip this scenario if the following text \"(.*)\" present using tag name \"(.*)\"$")
    public void userSkipThisScenarioIfTheTextDisplayeBasedOnTagName(String text, String tagName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String filePath = "";
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            try {
                if (basePage.getStatusOfElement(By.xpath("//" + tagName + "[contains(text(),'" + text + "')]"))) {
                    logger.log("Cannot execute this scenario because error '" + text + "' displayed");
                    PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                    PropUtils.setProps("skipReason", "Cannot execute this scenario because error '" + text + "' displayed", baseUtils.testDataFilePath);
                }
            } catch (Exception e) {

            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate field value \"(.*)\" using tag \"(.*)\", attributeName \"(.*)\", attributeValue \"(.*)\" with the property value \"(.*)\"$")
    public void userClickButtonUsingTagNameAttributeNameAttributeValue(String fieldName, String tagName, String attributeName, String attributeValue, String property) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.assertTwoStrings(logger, basePage.userGetTextFromWebElement(logger, By.cssSelector(tagName + "[" + attributeName + "='" + attributeValue + "']")), PropUtils.getPropValue(properties, property), fieldName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select client and country based on logged in user \"(.*)\"$")
    public void userSelectClientBasedOnloggedInUser(String UserName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select distinct mc.client_mid,mc.name,c.description from users u\n" +
                    "inner join user_roles ur on ur.user_oid = u.user_oid\n" +
                    "inner join access_role_clients arc on arc.access_role_oid = ur.access_role_oid\n" +
                    "inner join m_clients mc on mc.client_mid = arc.client_mid\n" +
                    "inner join m_customers mcust on mcust.client_mid = mc.client_mid\n" +
                    "left join countries c on c.country_oid = mc.country_oid\n" +
                    "where u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName") + "' and mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            if (PropUtils.getPropValue(properties, "ClientMID").equalsIgnoreCase("")) {
                PropUtils.setProps("ClientMID", queryResults.get("CLIENT_MID"), baseUtils.testDataFilePath);
            }
            if (PropUtils.getPropValue(properties, "Country").equalsIgnoreCase("")) {
                PropUtils.setProps("Country", queryResults.get("NAME"), baseUtils.testDataFilePath);
            }
            PropUtils.setProps("CountryCode", queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME"), baseUtils.testDataFilePath);
            if (basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='accountcli-name']")).trim().equalsIgnoreCase(queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME"))) {
                logger.log("Required country code '" + queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME") + "' has been selected");
            } else {
                basePage.userClick(logger, By.cssSelector("div[class='accountcli-name']"));
                commonPage.clickButtonUsingSpecificTagName(logger, queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME"), "div");
            }
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select client and country \"(.*)\" based on logged in user type \"(.*)\"$")
    public void userSelectClientBasedOnloggedInUserType(String countryCode, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select distinct mc.client_mid,mc.name,c.description from users u\n" +
                    "inner join user_roles ur on ur.user_oid = u.user_oid\n" +
                    "inner join access_role_clients arc on arc.access_role_oid = ur.access_role_oid\n" +
                    "inner join m_clients mc on mc.client_mid = arc.client_mid\n" +
                    "inner join m_customers mcust on mcust.client_mid = mc.client_mid\n" +
                    "left join countries c on c.country_oid = mc.country_oid\n" +
                    "where u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName") + "' and mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            if (PropUtils.getPropValue(properties, "ClientMID").equalsIgnoreCase("")) {
                PropUtils.setProps("ClientMID", queryResults.get("CLIENT_MID"), baseUtils.testDataFilePath);
            }
            if (PropUtils.getPropValue(properties, "Country").equalsIgnoreCase("")) {
                PropUtils.setProps("Country", queryResults.get("NAME"), baseUtils.testDataFilePath);
            }
            PropUtils.setProps("CountryCode", queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME"), baseUtils.testDataFilePath);
            if (basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='accountcli-name']")).trim().equalsIgnoreCase(queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME"))) {
                logger.log("Required country code '" + queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME") + "' has been selected");
            } else {
                basePage.userClick(logger, By.cssSelector("div[class='accountcli-name']"));
                //commonPage.clickButtonUsingSpecificTagName(logger, queryResults.get("DESCRIPTION") + " - " + queryResults.get("NAME"), "div");
                Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
                String buttonName = PropUtils.getPropValue(inputProperties, countryCode);
                commonPage.clickButtonUsingSpecificTagName(logger, buttonName, "div");
            }
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select account from account picker which has transactions contains transaction types \"(.*)\" and \"(.*)\" based on field value \"(.*)\"$")
    public void userSelectAccountFromAccountPickerWhichHasTransactionsBasedOnType(String transactionType, String notContainTransactionTypesInDB, String searchableFieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String exactFieldName = omvContextPage.getDBTransactionFieldNameForCorrespondingUIField(searchableFieldName);
        String query = "";
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String notContainTransactionTypes = "";
            String[] arrNotContainTransactionTypes = {notContainTransactionTypesInDB};
            if (notContainTransactionTypesInDB.contains(",")) {
                arrNotContainTransactionTypes = notContainTransactionTypesInDB.split(",");
            }
            for (int i = 0; i <= arrNotContainTransactionTypes.length - 1; i++) {
                if (i == arrNotContainTransactionTypes.length - 1) {
                    notContainTransactionTypes = notContainTransactionTypes + "'%" + arrNotContainTransactionTypes[i] + "%'";
                } else {
                    notContainTransactionTypes = notContainTransactionTypes + "'%" + arrNotContainTransactionTypes[i] + "%'" + ",";
                }
            }
            PropUtils.setProps("notContainsTransactionTypes", notContainTransactionTypes, baseUtils.testDataFilePath);
            String transactionTypeInDB = "";
            if (transactionType.equalsIgnoreCase("Sundry")) {
                transactionTypeInDB = "'%" + transactionType + "%'";
            } else {
                transactionTypeInDB = "'%" + transactionType + "'";
            }
            transactionTypeInDB = " and tt.description like " + transactionTypeInDB;
            notContainTransactionTypes = " and tt.description not in (" + notContainTransactionTypes + ")";
            PropUtils.setProps("TransactionTypeInDB", transactionTypeInDB, baseUtils.testDataFilePath);
            query = "select mcust.customer_no from transaction_enquiry te \n" +
                    "inner join transaction_types tt on tt.transaction_type_oid = te.transaction_type_oid \n" +
                    "inner join m_customers mcust on mcust.customer_mid = te.customer_mid \n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid \n" +
                    "left join constants c on c.constant_oid = te.POS_TRANSACTION_STATUS_CID \n" +
                    "left join user_members um on um.member_oid = mcust.customer_mid \n" +
                    "left join users u on u.user_oid = um.user_oid \n" +
                    "where mc.client_mid in ( " + PropUtils.getPropValue(properties, "ClientMID") + "\n" +
                    " ) and te." + exactFieldName + " is not null " + transactionTypeInDB + "\n" +
                    notContainTransactionTypes + " and u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), "UserName") + "' \n" +
                    "and te.effective_at < sysdate and te.effective_at > sysdate-900";
            List<Map<String, String>> allRowsOfQueryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (allRowsOfQueryResults.size() == 0) {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                ;
                PropUtils.setProps("skipReason", "None of the account has sundry transactions hence this scenario will get skipped", baseUtils.testDataFilePath);
            } else {
                commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, allRowsOfQueryResults.get(0).get("CUSTOMER_NO"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on X icon of specific filter which is at position \"(.*)\"$")
    public void userClickOnXIconOfSpecificFilterBasedOnItsPosition(String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.clearSpecificFilterUsingXIcon(logger, Integer.parseInt(position));
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select account from account picker which has authorisations based on field \"(.*)\"$")
    public void userSelectAccountFromAccountPickerWhichHasAuthorisations(String fieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String dbFieldName = omvContextPage.getDBAuthorizationFieldNameForCorrespondingUIField(fieldName);
            String query = "select distinct mcust.customer_no from auth_transaction_logs at\n" +
                    "left join m_customers mcust on mcust.customer_mid = at.customer_mid\n" +
                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                    "left join users u on u.user_oid = um.user_oid\n" +
                    "where at." + dbFieldName + " is not null and u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), "UserName") + "'\n" +
                    "and mcust.customer_no != '0700000042'\n" +
                    "and at.received_at < sysdate and at.received_at > sysdate - 900";
            logger.log(query);
            List<Map<String, String>> allRowsOfQueryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (allRowsOfQueryResults.size() == 0) {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                ;
                PropUtils.setProps("skipReason", "None of the account has Authorisations hence this scenario will get skipped", baseUtils.testDataFilePath);
            } else {
                commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, allRowsOfQueryResults.get(0).get("CUSTOMER_NO"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User get the value from grid based on its position \"(.*)\" and validate with value \"(.*)\"$")
    public void userClickOnXIconOfSpecificFilterBasedOnItsPosition(String position, String expValue) {
        if (usersPage.getCountOfRecords(logger).equals("0")) {
            PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
        }
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (!expValue.equals("")) {
                commonPage.assertTwoStrings(logger, expValue, basePage.userGetTextFromWebElement(logger, By.xpath("(//span[@class='cardStatus-0 ng-star-inserted'])[2]")), "FieldFromGrid-WhichIsAtPosition " + position);
            } else {
                logger.log("Field value from Grid based on its position " + position);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click drop down \"(.*)\" then select value \"(.*)\" using tag \"(.*)\" and attribute \"(.*)\"$")
    public void userSelectValueThenClickDropDownUsingTagAndAttribute(String dropName, String dropValue, String tagName, String attributeName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (dropValue.equalsIgnoreCase("Random")) {
                commonPage.clickAndPerformUsingActions(basePage.getWebElementUsingLocator(logger, By.cssSelector(tagName + "[" + attributeName + "='" + dropName + "']")));
                basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                PropUtils.setProps(dropName, commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='" + dropName + "']>mat-select"), "ng-reflect-model"), baseUtils.testDataFilePath);
            } else {
                basePage.userClick(logger, By.cssSelector(tagName + "[" + attributeName + "='" + dropName + "']"));
                commonPage.clickButtonUsingSpan(logger, dropValue);
            }
            logger.log(dropValue + " has been selected from drop down : " + dropName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User make sure account is not pinned for logged in user$")
    public void userMakeSureAccountIsNotPinnedForLoggedInUser() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(3);
            if (!basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'account-number')]")).equalsIgnoreCase("Select account")) {
                basePage.userClick(logger, By.xpath("//div[contains(@class,'account-number')]"));
                commonPage.clickButtonUsingButtonLocator(logger, "Return to customer care");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User press \"(.*)\" key from keyboard$")
    public void userMakeSureAccountIsNotPinnedForLoggedInUser(String keyName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.handleActionsClassForKeyboardActions(keyName);
            logger.log("Pressed " + keyName + " from keyboard");
            commonPage.sleepForFewSeconds(5);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click drop down \"(.*)\" using locator \"(.*)\" which has locator type \"(.*)\" then select value \"(.*)\"$")
    public void userSelectValueThenClickDropDownUsingTagAndAttributeBasedOnItsPosition(String dropName, String locator, String locatorType, String dropValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By elementLocator = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                elementLocator = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                elementLocator = By.cssSelector(locator);
            }
            if (dropValue.equalsIgnoreCase("Random")) {
                basePage.userClick(logger, elementLocator);
                basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
            } else {
                basePage.userClick(logger, elementLocator);
                commonPage.clickButtonUsingSpan(logger, dropValue);
            }
            logger.log(dropValue + " has been selected from drop down : " + dropName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click on date field \"(.*)\" and select value no of days \"(.*)\" of \"(.*)\" time$")
    public void userClickOnDateFieldAndSelectValueNoOfDays(String fieldName, String noOfDays, String time) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.clickOnDateFieldAndSelectFutureOrPastDate(logger, fieldName, noOfDays, time);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter value \"(.*)\" in text field \"(.*)\" based on locator \"(.*)\" which has locator type is \"(.*)\" in \"(.*)\" form$")
    public void userEnterValueInTextFieldBasedOnLocatorWithLocatorType(String value, String fieldName, String locator, String locatorType, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By elementLocator = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                elementLocator = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                elementLocator = By.cssSelector(locator);
            }
            if (value.equalsIgnoreCase("RandomAlphanumeric") || value.equalsIgnoreCase("RandomAlphanumericWithFewSpecialChars") || value.equalsIgnoreCase("Numeric")) {
                value = PasswordGenerator.generateRandomPassword(logger, 10);
            }
            Actions actions = new Actions(driver);
            actions.moveToElement(basePage.getWebElementUsingLocator(logger, elementLocator));
            commonPage.sleepForFewSeconds(2);
            if (formType.equalsIgnoreCase("Add")) {
                basePage.userTypeIntoTextField(logger, elementLocator, value);
            } else if (formType.equalsIgnoreCase("Edit")) {
                commonPage.assertTwoStrings(logger, commonPage.getAttributeValue(elementLocator, "ng-reflect-value"), PropUtils.getPropValue(properties, fieldName), fieldName);
            }
            if (fieldName.equalsIgnoreCase("sites")) {
                commonPage.clickButtonUsingSpan(logger, "Apply");
            }
            PropUtils.setProps(fieldName, value, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click on button contains \"(.*)\" using locator \"(.*)\" which has locator type \"(.*)\" using method \"(.*)\"$")
    public void userEnterValueInTextFieldBasedOnLocatorWithLocatorType(String buttonName, String locator, String locatorType, String methodToPerformClick) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            PropUtils.setProps("replaceCardAvailability", "Yes", baseUtils.testDataFilePath);
            By elementLocator = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                elementLocator = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                elementLocator = By.cssSelector(locator);
            }
            JavascriptExecutor js = ((JavascriptExecutor) driver);
            js.executeScript("arguments[0].scrollIntoView(true);", basePage.getWebElementUsingLocator(logger, elementLocator));
            if (methodToPerformClick.equalsIgnoreCase("click")) {
                basePage.userClick(logger, elementLocator);
            } else if (methodToPerformClick.equalsIgnoreCase("jsExecutor")) {
                commonPage.clickUsingJSExecutor(logger, basePage.getWebElementUsingLocator(logger, elementLocator));
            } else if (methodToPerformClick.equalsIgnoreCase("action")) {
                commonPage.clickAndPerformUsingActions(basePage.getWebElementUsingLocator(logger, elementLocator));
            } else if (methodToPerformClick.equalsIgnoreCase("double")) {
                Actions actions = new Actions(driver);
                actions.moveToElement(basePage.getWebElementUsingLocator(logger, elementLocator)).doubleClick().perform();
            }
            PropUtils.setProps("clickedButton", buttonName, baseUtils.testDataFilePath);
            logger.log("Clicked on the button '" + buttonName + "' using method '" + methodToPerformClick + "'");
        } else {
            PropUtils.setProps("replaceCardAvailability", "No", baseUtils.testDataFilePath);
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click on radio button contains \"(.*)\" using locator \"(.*)\" which has locator type \"(.*)\"$")
    public void userClickOnRadioButtonUsingLocatorBasedOnItsLocatorType(String buttonName, String locator, String locatorType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By elementLocator = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                elementLocator = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                elementLocator = By.cssSelector(locator);
            }
            commonPage.clickAndPerformUsingActions(basePage.getWebElementUsingLocator(logger, elementLocator));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click on body of the webPage to come out of multiselect dropdown$")
    public void userClickOnBodyOfTheWebPageToComeOutOfMultiselectDrpDown() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(2);
            Actions actions = new Actions(driver);
            actions.moveToElement(basePage.getWebElementUsingLocator(logger, By.cssSelector("div[class='cdk-overlay-connected-position-bounding-box']"))).doubleClick().perform();
//                commonPage.clickAndPerformUsingActions(basePage.getWebElementUsingLocator(logger, By.cssSelector("div[class='cdk-overlay-connected-position-bounding-box']")));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select first check box from the search results$")
    public void userSelectFirstCheckBoxFromTheSearchResults() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='acctLgt ng-star-inserted']")).contains("0")) {
                commonPage.clickButtonUsingSpecificTagName(logger, "Cancel", "a");
            } else {
                basePage.userClick(logger, By.xpath("(//div[@class='account-table']/div/div/mat-checkbox)[1]/label/div/input"));
                commonPage.clickButtonUsingSpan(logger, "Add");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get account Number which contain records based on module \"(.*)\" for user \"(.*)\"$")
    public void userGetAccountNumberWhichHasRecordsBasedOnModule(String moduleName, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.getAccountNumberBasedOnUserTypeAndModule(userType, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate count of records for module \"(.*)\" based on its field \"(.*)\" with value \"(.*)\" is from property file \"(.*)\" with the database for user \"(.*)\"$")
    public void userGetAccountNumberWhichHasRecordsBasedOnModule(String moduleName, String fieldName, String fieldValue, String isFromPropertyFile, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (commonPage.getCountOfRecordsBasedOnModuleAndUserTypeAndCondition(logger, moduleName, fieldName, fieldValue, isFromPropertyFile, userType).equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, "0", usersPage.getCountOfRecords(logger), "Count of record for module " + moduleName);
            } else {
                commonPage.assertTwoStrings(logger, commonPage.getCountOfRecordsBasedOnModuleAndUserTypeAndCondition(logger, moduleName, fieldName, fieldValue, isFromPropertyFile, userType), usersPage.getCountOfRecords(logger), "Count of record for module " + moduleName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select an account from account picker based on value \"(.*)\" is from property file \"(.*)\"$")
    public void userSelectAnAccountFromAccountPickerBasedOnValue(String value, String isFromPropertyFile) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (!basePage.userGetTextFromWebElement(logger, By.xpath("//div[@class='account-number']")).equalsIgnoreCase(PropUtils.getPropValue(properties, "accountNo"))) {
                basePage.userClick(logger, By.cssSelector("div[class='account-name']"));
                basePage.userClick(logger, By.xpath("//div[@class='account-number'][contains(text(),'" + PropUtils.getPropValue(properties, "accountNo") + "')]"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate presence of element using locator \"(.*)\" with locator type \"(.*)\"$")
    public void userValidatePresenceOfElementUserlocatorAndItsType(String locator, String typeOfLocator) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (typeOfLocator.equalsIgnoreCase("xpath")) {
                basePage.whetherElementPresent(logger, By.xpath(locator));
            } else if (typeOfLocator.equalsIgnoreCase("cssSelector")) {
                basePage.whetherElementPresent(logger, By.cssSelector(locator));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get account number which \"(.*)\" records for \"(.*)\" module$")
    public void userGetAnAccountNoWhichHasOrNotRecordsBasedOnModule(String hasOrNot, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.getAnAccountNumberWhichHasOrNotRecordsBasedOnModule(hasOrNot, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User save the value for field \"(.*)\" from locator \"(.*)\" using locator type \"(.*)\" in property file$")
    public void userSaveTheValueOfLocatorInPropertyFileBasedOn(String propertyName, String locator, String locatorType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            By locatorValue = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                locatorValue = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                locatorValue = By.cssSelector(locator);
            }
            PropUtils.setProps(propertyName, basePage.userGetTextFromWebElement(logger, locatorValue), baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate presence of \"(.*)\" field with \"(.*)\" tag from property file using property name \"(.*)\"$")
    public void userValidatePresenceOfFieldWithTagFromPropertyFile(String fieldName, String tagName, String propertyName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.whetherElementPresent(logger, By.xpath("//" + tagName + "[contains(text(),'" + PropUtils.getPropValue(properties, propertyName) + "')]"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User validate parent account is displayed in Select accounts stepper and validate field values with database")
    public void validateParentHierarchyDetails() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String actuvalAccountNo = basePage.userGetTextFromWebElement(logger, By.xpath("//mat-grid-list/div/mat-grid-tile[3]/figure/div/div"));
            String expectedAccountNo = PropUtils.getPropValue(properties, "notHavingHierarchy-accountNumber");
            commonPage.assertTwoStrings(logger, expectedAccountNo, actuvalAccountNo, "Account number");
            omvContextPage.getCustomerNameUsingNumber(logger, expectedAccountNo);
            properties = PropUtils.getProps(baseUtils.testDataFile);
            String expectedCustomerName = PropUtils.getPropValue(properties, "notHavingHierarchy-accountName");
            String actuvalcustomerName = basePage.userGetTextFromWebElement(logger, By.xpath("//mat-grid-list/div/mat-grid-tile[1]/figure/div/div"));
            commonPage.assertTwoStrings(logger, expectedCustomerName.trim().replaceAll(" ", ""), actuvalcustomerName.trim().replaceAll(" ", ""), "Customer Name");
            String actuvalDates = basePage.userGetTextFromWebElement(logger, By.xpath("//mat-grid-list/div/mat-grid-tile[2]/figure/div/div"));
            String expectedEffectiveDate = PropUtils.getPropValue(properties, "Date-effectiveDate");
            String expectedExpireDate = PropUtils.getPropValue(properties, "Date-endDate");

            String[] splitedDates = StringUtils.split(actuvalDates);

            System.out.println("Effective date from UI" + splitedDates[0]);


            System.out.println("Effective date from DB" + expectedEffectiveDate);

            commonPage.assertTwoStrings(logger, expectedEffectiveDate, splitedDates[0], "Effective date");
            commonPage.assertTwoStrings(logger, expectedExpireDate, splitedDates[splitedDates.length - 1], "Effective date");

            String actuvalNodes = basePage.userGetTextFromWebElement(logger, By.xpath("//mat-grid-list/div/mat-grid-tile[4]/figure/div/div"));

            System.out.println("node from UI" + actuvalNodes);
            commonPage.assertContainInString(logger, actuvalNodes, "Authorisation node", "Node");
            commonPage.assertContainInString(logger, actuvalNodes, "Billing node", "Node");
            commonPage.assertContainInString(logger, actuvalNodes, "Payer node", "Node");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }

    }

    @Then("^User get an account number which is in \"(.*)\" status for \"(.*)\" user$")
    public void userGetAnAccountNumberWhichIsInSpecificStatusBasedOnUserType(String status, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.getAccountNumberBasedOnStatusAndUserType(logger, status, userType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User get the reports associated for the pinned account and verify count of reports")
    public void verifyReportsCountInReportSetupPage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String customerNo = PropUtils.getPropValue(properties, "commonAccountNo");
            String countInDB = reportSetupPage.customerAssociatedReportsFromDB(logger, customerNo);
            String countInUI = reportSetupPage.customerAssociatedReportsFromUI(logger);
            if (countInDB.equalsIgnoreCase(countInUI)) {
                logger.log("Reports are verified with DB");
            } else {
                logger.log("DB and UI reports count not matched ");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate \"(.*)\" section of field values which is at position \"(.*)\"$")
    public void verifyReportSetupDetails(String reportView, String reportPosition) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String customerNo = PropUtils.getPropValue(properties, "havingReportSetup");
            reportSetupPage.getReportSetupDetailsFromDB(logger, customerNo);
            if (reportView.equalsIgnoreCase("Default")) {
                reportSetupPage.validatereportSetupDetails(logger);
            } else if (reportView.equalsIgnoreCase("Expanded")) {
                reportSetupPage.expandReportSetup(logger);
                reportSetupPage.validateReportSetupDetailsFromExpandedUI(logger);
            } else {
                logger.log("Report setup details verification failed");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate expected value \"(.*)\" from property file \"(.*)\" actual value from locator \"(.*)\" which has locator type \"(.*)\" get value using attribute \"(.*)\"$")
    public void userValidateFieldValueIsPopulatedExpectedOrNot(String fieldName, String isFromPropertyFile, String locator, String locatorType, String attributeName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String fieldValue = fieldName;
            if (isFromPropertyFile.equalsIgnoreCase("Yes") || isFromPropertyFile.equalsIgnoreCase("True")) {
                fieldValue = PropUtils.getPropValue(properties, fieldName);
            }
            By locatorValue = null;
            if (locatorType.equalsIgnoreCase("xpath")) {
                locatorValue = By.xpath(locator);
            } else if (locatorType.equalsIgnoreCase("cssSelector")) {
                locatorValue = By.cssSelector(locator);
            }
            if (!fieldName.equalsIgnoreCase("ccp-cardProduct-notEligibleForAddCCP")) {
                commonPage.assertTwoStrings(logger, fieldValue, commonPage.getAttributeValue(locatorValue, attributeName), fieldName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select date \"(.*)\" based on client processing date future or past \"(.*)\" days \"(.*)\"$")
    public void userSelectDateFutureOrPastBasedOnClientProcessingDate(String fieldName, String futureOrPast, String days) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (PropUtils.getPropValue(properties, "hierarchyWithExp").equalsIgnoreCase("hierarchyWithExp")) {
                if (fieldName.equalsIgnoreCase("effectiveDate")) {
                    days = PropUtils.getPropValue(properties, "daysEffective");
                } else if (fieldName.equalsIgnoreCase("endDate")) {
                    days = PropUtils.getPropValue(properties, "daysExpiry");
                }

            }
            String[] dateToBeSelected = commonPage.getDesiredDateInSpecificFormat(logger, commonPage.getClientProcessingDate().split(" ")[0], Integer.parseInt(days), futureOrPast, "yyyy-MM-dd", "dd-MMM-yyyy").split("-");
//            Actions actions = new Actions(driver);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basePage.getWebElementUsingLocator(logger, By.cssSelector("mav-input[ng-reflect-name='" + fieldName + "']>input")));
//            actions.moveToElement().click().perform();
            basePage.userClick(logger, By.cssSelector("mav-input[ng-reflect-name='" + fieldName + "']>input"));
            commonPage.handleDatePickerForPastOrFutureDateSelection(logger, dateToBeSelected[1], dateToBeSelected[0], dateToBeSelected[2]);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User logout from the application$")
    public void userLogoutFromTheApplication() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if(basePage.getStatusOfElement(By.cssSelector("mav-button[id='user-profile-btn']>button"))) {
                basePage.userClick(logger, By.cssSelector("mav-button[id='user-profile-btn']>button"));
                commonPage.clickButtonUsingSpan(logger, "Log out");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User modify flags \"(.*)\" for logged in user \"(.*)\"$")
    public void userModifyFlagsBasedOnLoggedInUser(String flagsAndTheirValues, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String userCondition = "op.user_oid = u.user_oid";
            String accessGroupCondition = "op.access_group_oid = arc.access_group_oid";
            String userQuery = "select u.logon_id,op.user_oid,op.access_group_oid,op.client_mid,CAN_APPROVE_CUST_MASTER_DATA, CAN_APPROVE_ACCT_MASTER_DATA, CAN_APPROVE_TAX_MASTER_DATA,\n" +
                    "CAN_APPROVE_CUST_HIER_DATA, CAN_APPROVE_OWN from operation_limit op\n" +
                    "left join users u on u.user_oid = op.user_oid\n" +
                    "inner join user_roles ur on ur.user_oid = u.user_oid\n" +
                    "inner join access_role_clients arc on arc.access_role_oid = ur.access_role_oid\n" +
                    "where u.logon_id in ('" + PropUtils.getPropValue(inputProperties, userName) + "') and arc.client_mid = " +
                    PropUtils.getPropValue(properties, "ClientMID") + " and " + userCondition;
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(userQuery);
            String query = "";
            if (queryResults == null || queryResults.size() == 0) {
                String logonIdQuery = "select user_oid from users where logon_id = '" + PropUtils.getPropValue(inputProperties, userName) + "'";
                Map<String, String> lQR = commonUtils.getQueryResultsOnMap(logonIdQuery);
                String[] flagsArr = flagsAndTheirValues.split(",");
                query = "INSERT INTO OPERATION_LIMIT (LAST_UPDATED_AT, LAST_UPDATED_BY, UPDATE_COUNT, CLIENT_MID, USER_OID,\n" +
                        " ACCESS_GROUP_OID, MAXIMUM_REBATE_AMOUNT, MAXIMUM_REBATE_PERCENT, MAXIMUM_CREDIT_LIMIT, MAXIMUM_CREDIT_PERCENT, MAX_SUNDRY_ADJ_AUTH_REQ_AMNT,\n" +
                        " MAX_SUNDRY_ADJ_APPR_AMNT, CAN_AUTH_OWN_SUNDRY_ADJ, MAX_SUNDRY_PYMNT_ENTRY_AMNT, MAX_SUNDRY_PYMNT_AUTH_REQ_AMNT, MAX_SUNDRY_PYMNT_APPR_AMNT,\n" +
                        " CAN_AUTH_OWN_SUNDRY_PYMNT, MAX_MANUAL_PAYMENT_AMOUNT, MAX_SUNDRY_ADJ_ENTRY_AMNT, MAX_CREDIT_NOTE_ENTRY_AMNT, MAX_CREDIT_NOTE_AUTH_REQ_AMNT,\n" +
                        " MAX_CREDIT_NOTE_APPR_AMNT, CAN_AUTH_OWN_CREDIT_NOTE, CAN_APPROVE_CUST_MASTER_DATA, CAN_APPROVE_ACCT_MASTER_DATA, CAN_APPROVE_TAX_MASTER_DATA,\n" +
                        " CAN_APPROVE_CUST_HIER_DATA, CAN_APPROVE_PRICING, CAN_APPROVE_OWN) VALUES (TO_DATE('2021-09-29 13:41:41', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                        " N'W431951', '3', '" + PropUtils.getPropValue(properties, "ClientMID") + "', '" + lQR.get("USER_OID") + "', '542', '9999999', '99', '999999999999', '99', '999999', '999999', 'Y', '999999', '999999', '99999999',\n" +
                        " 'Y', '99999', '999999', '99999', '99999', '99', 'Y', '" + flagsArr[0].split("=")[1] + "', '" + flagsArr[1].split("=")[1] + "', '" + flagsArr[2].split("=")[1] + "', '" + flagsArr[3].split("=")[1] + "', '" + flagsArr[4].split("=")[1] + "', '" + flagsArr[5].split("=")[1] + "')";
            } else {
                query = "update operation_limit set " + flagsAndTheirValues + " where user_oid = (select u.user_oid from users u\n" +
                        "inner join user_roles ur on ur.user_oid = u.user_oid\n" +
                        "inner join access_role_clients arc on arc.access_role_oid = ur.access_role_oid\n" +
                        "left join operation_limit op on op.user_oid = u.user_oid\n" +
                        "where u.logon_id = '" + PropUtils.getPropValue(inputProperties, userName) +
                        "' and arc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID") + " and " + userCondition + ")";
            }
            commonUtils.executeUpdateQuery(query);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User set the effective date and end date$")
    public void setHierarchyDates() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String parentEffectiveDate = basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[1]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[0];
            String parentExpiryDate = basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[1]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[1];
            String processingDate = commonPage.getClientProcessingDate().split(" ")[0];
            String processingDateWithFormat = processingDate.split("-")[2] + "/" + processingDate.split("-")[1] + "/" + processingDate.split("-")[0];
            int difference = basePage.differnceOfTwoDates(logger, processingDateWithFormat, parentExpiryDate, "dd/MM/yyyy");
            PropUtils.setProps("daysEffective", Integer.toString(difference + 1), baseUtils.testDataFilePath);
            PropUtils.setProps("daysExpiry", Integer.toString(difference + 10), baseUtils.testDataFilePath);
            PropUtils.setProps("hierarchyWithExp", "hierarchyWithExp", baseUtils.testDataFilePath);


        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User Validate parent account in hierarchy if child account pinned$")
    public void validateParentAccountInHierarchyifChildPinned() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            logger.log("Entered into last step");
            omvContextPage.getParentAccountNumberWithChildAccountInHierarchy(logger, PropUtils.getPropValue(properties, "havingPendingHierarchy-childAccountNumber"));
            List<WebElement> elements = driver.findElements(By.cssSelector("figure > div > div"));
            properties = PropUtils.getProps(baseUtils.testDataFile);
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "havingHierarchy-parentAccountNumber"), elements.get(2).getText());

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User skip few steps if property \"(.*)\" value is \"(.*)\"$")
    public void userSkipFewStepsIfPropValueIsAvailable(String propKey, String propValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if(PropUtils.getPropValue(properties,propKey).equalsIgnoreCase(propValue)){
                PropUtils.setProps("testStatus","Skipped",baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason","Already customer available with request so no need to create a new request",baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

}
