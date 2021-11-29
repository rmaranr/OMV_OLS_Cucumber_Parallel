package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.CommonPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.UsersPage;
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

public class ContactsSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private ContactsPage contactsPage;
    private CommonPage commonPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public ContactsSteps(Hooks hooks, DriverFactory driverFactory) {
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
    }

    @Then("^User enter \"(.*)\" as \"(.*)\" in module \"(.*)\" having length \"(.*)\" in \"(.*)\" form$")
    public void userEnterFieldValueInCorrespondingFieldContacts(String fieldName, String fieldValue, String moduleName, String length, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (fieldName.contains("mail")) {
                basePage.userTypeIntoTextField(logger, By.cssSelector("ols-email[ng-reflect-name='emailAddress']>input"), fieldValue);
                PropUtils.setProps(moduleName + "-emailAddress", fieldValue, baseUtils.testDataFilePath);
            } else {
                usersPage.enterValueBasedOfItsType(logger, fieldValue, fieldName, formType, moduleName, length, "@&$#_");
                String newValue = usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, fieldName);
                PropUtils.setProps(moduleName + "-" + fieldName, newValue, baseUtils.testDataFilePath);
            }
            logger.log("Field " + fieldName + " value is " + fieldValue + " entered");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select the contact as primary by selecting the checkbox of \"(.*)\"$")
    public void userSelectTheContactAsPrimary(String fieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            contactsPage.selectCheckBoxOfSetAsPrimaryContact(logger, fieldName);
            logger.log("Check box is selected for 'Set as primary contact");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verify the text field \"(.*)\" is disabled in module \"(.*)\" in form \"(.*)\"$")
    public void userEnterFieldValueInCorrespondingFieldContacts(String fieldName, String moduleName, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Boolean isEnabled = basePage.checkIsEnabledOrDisabled(logger, By.cssSelector("mav-input[name='" + fieldName + "']>input"));
            if (isEnabled) {
                logger.log(fieldName + " is enabled");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verify by default \"(.*)\" check box is selected or not in \"(.*)\" form")
    public void userVeifyByDefaultMailingCheckBoxIsSelectedOrNot(String checkBoxName, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            contactsPage.verifyStatusOfCheckBox(logger, checkBoxName, formType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate contact data is saved in database$")
    public void userValidateContactDataIsSavedInDatabase() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            int accountNumber = Integer.parseInt(PropUtils.getPropValue(testDataProperties, "accountNumber"));
            String contactName = PropUtils.getPropValue(testDataProperties, "contacts-contactName");
            String query = "select con.contact_oid,con.update_count,con.last_updated_at,con.last_updated_by,con.member_oid,con.member_type_cid\n" +
                    ",con.contact_type_oid ,con.is_default,con.contact_name ,con.postal_address_oid,con.street_address_oid,con.contact_title\n" +
                    ",con.email_address ,con.phone_business ,con.phone_fax ,con.phone_mobile_1 ,con.phone_mobile_2 ,ct.description as contact_type_description\n" +
                    ",cr.contact_hierarchy_cid ,cr.sequence_no ,ad.ADDRESS_LINE, ad.SUBURB ,ad.POSTAL_CODE ,st.description as state\n" +
                    "FROM contacts con left JOIN contact_types ct ON ct.contact_type_oid = con.contact_type_oid\n" +
                    "left JOIN contact_rankings cr ON ct.contact_type_oid = cr.contact_type_oid\n" +
                    "left JOIN addresses ad ON con.STREET_ADDRESS_OID = ad.ADDRESS_OID\n" +
                    "left JOIN states st ON ad.STATE_OID = st.state_oid\n" +
                    "inner join m_customers mcust on mcust.customer_mid = con.member_oid\n" +
                    "WHERE mcust.customer_no='" + usersPage.getAccountNumberFromUsersPage(logger) + "'\n" +
                    "and con.member_type_cid=103 ORDER BY initcap(con.last_updated_at) desc";
            Map<String, String> costCentreDetails = commonUtils.getQueryResultsOnMap(query);
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-contactName"), costCentreDetails.get("CONTACT_NAME"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-jobTitle"), costCentreDetails.get("CONTACT_TITLE"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-otherPhone"), costCentreDetails.get("PHONE_BUSINESS"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-mobilePhone"), costCentreDetails.get("PHONE_MOBILE_1"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-fax"), costCentreDetails.get("PHONE_FAX"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-contactType"), costCentreDetails.get("CONTACT_TYPE_DESCRIPTION"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-emailAddress"), costCentreDetails.get("EMAIL_ADDRESS"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-addressLine"), costCentreDetails.get("ADDRESS_LINE"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-suburb"), costCentreDetails.get("SUBURB"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-postalCode"), costCentreDetails.get("POSTAL_CODE"));
//            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "contacts-contactState"), costCentreDetails.get("STATE"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select filter values in contact module then click on Apply button and validate filter results in database$")
    public void userSelectFilterFunctionalityOfContactsModule() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testData = PropUtils.getProps(baseUtils.testDataFile);
            String accountNumber = PropUtils.getPropValue(testData, "accountNumber");
            String dbContactTypesForAnAccount = contactsPage.getAvailableContactTypesForAnAccount(accountNumber);
            String arrDbContactTypesForAnAccount[] = {dbContactTypesForAnAccount};
            if (dbContactTypesForAnAccount.contains(",")) {
                arrDbContactTypesForAnAccount = dbContactTypesForAnAccount.split(",");
            }
            String dbContactLevelsForAnAccount = contactsPage.getAvailableContactLevelsForAnAccount(accountNumber);
            String arrDbContactLevelsForAnAccount[] = {dbContactLevelsForAnAccount};
            if (dbContactLevelsForAnAccount.contains(",")) {
                arrDbContactLevelsForAnAccount = dbContactLevelsForAnAccount.split(",");
            }
            String contactTypes = "";
            String contactLevels = "";
            String query = "";
            for (int i = 0; i <= arrDbContactTypesForAnAccount.length - 1; i++) {
                for (int j = 0; j <= arrDbContactLevelsForAnAccount.length - 2; j++) {
                    // Validate count of records for each status and role
                    if (i == arrDbContactTypesForAnAccount.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All contact types", dbContactTypesForAnAccount);
                        contactTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbContactTypesForAnAccount);
                        contactsPage.selectValuesFromContactLevelFilter(logger, "All contact levels", arrDbContactLevelsForAnAccount[j]);
                        contactLevels = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDbContactLevelsForAnAccount[j]);
                    } else if (j == arrDbContactLevelsForAnAccount.length - 2) {
                        usersPage.selectValuesFromFilter(logger, "All contact types", arrDbContactTypesForAnAccount[i]);
                        contactTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDbContactTypesForAnAccount[i]);
                        contactsPage.selectValuesFromContactLevelFilter(logger, "All contact levels", dbContactLevelsForAnAccount);
                        contactLevels = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbContactLevelsForAnAccount);
                    } else {
                        usersPage.selectValuesFromFilter(logger, "All contact types", arrDbContactTypesForAnAccount[i]);
                        contactsPage.selectValuesFromContactLevelFilter(logger, "All contact levels", arrDbContactLevelsForAnAccount[j]);
                        contactTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDbContactTypesForAnAccount[i]);
                        contactLevels = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDbContactLevelsForAnAccount[j]);
                    }
                    if (contactLevels.contains("Y") && contactLevels.contains("N")) {
                        query = "select count(*) from contacts c\n" +
                                "inner join contact_types ct on ct.CONTACT_TYPE_OID = c.CONTACT_TYPE_OID\n" +
                                "inner join addresses a on a.ADDRESS_OID = c.POSTAL_ADDRESS_OID\n" +
                                "left join states s on s.state_oid = a.state_oid\n" +
                                "inner join m_customers mcust on mcust.customer_mid = c.member_oid\n" +
                                "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                                "where mcust.customer_no = '"+accountNumber+"' and mc.client_mid = "+
                                PropUtils.getPropValue(PropUtils.getProps(baseUtils.testDataFile),"ClientMID")+"\n" +
                                "and ct.description in (" + contactTypes + ")";
                    } else {
                        query = "select count(*) from contacts c\n" +
                                "inner join contact_types ct on ct.CONTACT_TYPE_OID = c.CONTACT_TYPE_OID\n" +
                                "inner join addresses a on a.ADDRESS_OID = c.POSTAL_ADDRESS_OID\n" +
                                "left join states s on s.state_oid = a.state_oid\n" +
                                "inner join m_customers mcust on mcust.customer_mid = c.member_oid\n" +
                                "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                                "where mcust.customer_no = '"+accountNumber+"' and mc.client_mid = "+
                                PropUtils.getPropValue(PropUtils.getProps(baseUtils.testDataFile),"ClientMID")+"\n" +
                                "and ct.description in (" + contactTypes + ") and c.is_default in (" + contactLevels + ")";
                    }
                    logger.log(query);
                    Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
                    String actualRecordsCount = usersPage.getCountOfRecords(logger);
                    String expRecordsCount = queryResults.get("COUNT(*)");//queryResults.get("COUNT(*)");
                    basePage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount);
                    usersPage.clickResetAllFilters(logger);
                    commonPage.sleepForFewSeconds(2);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verifies the text \"(.*)\" present in Delete contact pop up$")
    public void userVerifiesTheTextPresentInDeleteContactPopUp(String expText) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            contactsPage.verifyTextOfDeleteContactPopUp(logger, expText);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on button \"(.*)\" using span text$")
    public void userClickOnButtonUsingSpanText(String btnName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (btnName.equalsIgnoreCase("home")) {
                try {
                    driver.findElement(By.xpath("//span[contains(text(),'" + btnName + "')]")).click();
                } catch (Exception e) {
                    driver.quit();
                    WebDriverInitialization webDriverInitialization = new WebDriverInitialization();
                    Properties commonProperties = PropUtils.getProps(baseUtils.commonPropertyFile);
                    webDriverInitialization.loginToApplication(PropUtils.getPropValue(commonProperties, "environment"), PropUtils.getPropValue(commonProperties, "currentUserName"), PropUtils.getPropValue(commonProperties, "currentPassword"));
                }
                logger.log("Clicked on Home menu");
            } else {
                commonPage.sleepForFewSeconds(2);
                commonPage.clickButtonUsingSpan(logger, btnName);
            }
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies the \"(.*)\" button is disabled using locator \"(.*)\"$")
    public void userVerifiesTheButtonIsDisabled(String btnName, String locator) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Boolean buttonStatus = false;
            if (locator.equalsIgnoreCase("button")) {
                buttonStatus = commonPage.statusOfButtonUsingButtonLocator(logger, btnName);
            } else if (locator.equalsIgnoreCase("span")) {
                buttonStatus = commonPage.statusOfButtonUsingSpanLocator(logger, btnName);
            }
            if (buttonStatus) {
                logger.log(btnName + " is disabled as expected.");
            } else {
                logger.log(btnName + " is enabled");
            }
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User clicks the state drop down and select random value")
    public void userClicksTheStateDropDownAndSelectRandomValue() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
//            WebElement dropNameLocator = driver.findElement(By.cssSelector("mav-select[name='state']"));
//            commonPage.clickOnDropDown(logger,"contactState");
//            dropNameLocator.sendKeys(Keys.DOWN, Keys.RETURN);
            String query = "select * from states";
            List<Map<String, String>>queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if(queryResults.size()>0) {
                contactsPage.selectRandomValueFromStateDropDown(logger);
            }else{
                logger.log("No state is available");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User verify whether the Next button is enabled")
    public void userVerifyWhetherTheNextButtonIsEnabled() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            contactsPage.verifyNextButton(logger);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User get an account number which has option \"(.*)\" in hierarchy$")
    public void userGetAnAccountNoWhichHasOptionInHierarchy(String option) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select distinct mcust.customer_no,ra.IS_ACCEPT_CONTACTS from contacts c\n" +
                    "inner join m_customers mcust on mcust.customer_mid = c.member_oid\n" +
                    "left join accounts a on a.account_no = mcust.customer_no\n" +
                    "left join account_status ast on ast.account_status_oid = a.account_status_oid\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "inner join relationships r on r.member_oid = mcust.customer_mid and r.RELATIONSHIP_OID != r.PARENT_RELATIONSHIP_OID\n" +
                    "inner join relationship_assignments ra on ra.relationship_oid = r.relationship_oid\n" +
                    "where mc.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")
                    +" and ra."+option+" and ast.description like '%Active%'";
            List<Map<String, String>>queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if(queryResults.size()!=0){
                PropUtils.setProps("accountNo",queryResults.get(0).get("CUSTOMER_NO"),baseUtils.testDataFilePath);
            }else{
                PropUtils.setProps("testStatus","SKipped",baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason","No Customer available to execute contacts scenarios",baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User delete card delivery contact from database if it is present")
    public void userDeleteCardDeliveryContactFromDatabaseIfItIsPresent() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select * from contacts c\n" +
                    "inner join contact_types ct on ct.contact_type_oid = c.contact_type_oid\n" +
                    "inner join m_customers mcust on mcust.customer_mid = c.member_oid\n" +
                    "inner join m_clients mc on mc.client_mid = ct.client_mid\n" +
                    "where mc.short_name = '" +
                    PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) +
                    "' and mcust.customer_no = " + PropUtils.getPropValue(baseUtils.testDataProperties, "accountNumber") +
                    " and ct.description = 'Card Delivery'";
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() > 0) {
                for (int i = 0; i <= queryResults.size() - 1; i++) {
                    query = "delete from contacts where contact_oid = " + queryResults.get(i).get("CONTACT_OID");
                    commonUtils.executeUpdateQuery(query);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

}
