package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.TransactionNotFoundException;
import utilities.ui.WebDriverInitialization;

import java.util.*;

import static utilities.api.BaseUtils.clientCountry;
import static utilities.api.BaseUtils.test;

public class AccountInfoSteps {

    public Scenario logger;
    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private UsersPage usersPage;
    private CostCentresPage costCentresPage;
    private CommonDBUtils commonUtils;
    private AccountInfoPage accountInfoPage;
    private String Active;
    private CommonPage commonPage;
    private DriverFactory driverFactory;

    public AccountInfoSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.logger = hooks.logger;
        this.driver = driverFactory.driver;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        costCentresPage = new CostCentresPage(driver);
        accountInfoPage = new AccountInfoPage(driver);
        commonPage = new CommonPage(driver);
    }

    @And("Verify the presence of the page header \"(.*)\"$")
    public void verifyThePresenceOfThePageHeader(String header) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.verifyAccountPageHeader(logger, header);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("Verify the tabs in account information page \"(.*)\"$")
    public void verifyTheTabShownByDefault(String Summary) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.verifyPresenceOfSummary(logger, Summary);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("Users select the Notification tab")
    public void usersClickOnTheNotificationTab() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.clickNotificationTab(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate whether the account details section has \"(.*)\" \"(.*)\" \"(.*)\" \"(.*)\"$")
    public void validateWhetherTheAccountDetailsPageHas(String accname, String tradname, String accno, String status) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            PropUtils.setProps("accountNumber", String.valueOf(accountNumber), baseUtils.testDataFilePath);
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            String query = "select name, trading_name, customer_no, ass.description from m_customers mc inner join " +
                    "accounts a on a.customer_mid =mc.customer_mid Inner Join account_status c on c.account_status_oid=a.account_status_oid inner join account_sub_status ass on ass.ACCOUNT_SUB_STATUS_OID = a.ACCOUNT_SUB_STATUS_OID" +
                    " where mc.customer_no=" + accountNumber + " and mc.client_mid=(select client_mid from m_clients where short_name='" + (PropUtils.getPropValue(baseUtils.inputProp, clientCountry)) + "')";
            Map<String, String> accountDetails = commonUtils.getQueryResultsOnMap(query);
            accountInfoPage.verifyAccountName(logger, accountDetails.get("NAME"));
            accountInfoPage.verifyTradingName(logger, accountDetails.get("TRADING_NAME"));
            accountInfoPage.verifyAccountNo(logger, accountDetails.get("CUSTOMER_NO"));
            accountInfoPage.verifyAccountStatus(logger, accountDetails.get("DESCRIPTION"));
            commonPage.sleepForFewSeconds(2);
            usersPage.scrollDown(logger);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User verifies the account status")
    public void verifyTheAccountStatus() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            PropUtils.setProps("accountNumber", String.valueOf(accountNumber), baseUtils.testDataFilePath);
            String query = "select c.description from m_customers mc inner join accounts a on a.customer_mid=mc.customer_mid Inner Join account_sub_status c on c.ACCOUNT_SUB_STATUS_OID=a.ACCOUNT_SUB_STATUS_OID where customer_no= " + accountNumber + " " +
                    "and mc.client_mid=(select client_mid from m_clients where short_name='" + (PropUtils.getPropValue(baseUtils.inputProp, clientCountry)) + "')";
            Map<String, String> accountDetails = commonUtils.getQueryResultsOnMap(query);
            accountInfoPage.verifyAccountStatus(logger, accountDetails.get("DESCRIPTION"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User verifies whether the respective warning message is displayed when the status is other than 1 - Active")
    public void verifyTheStatusWarningMessage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.verifyAccountErrormsg(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User Verify whether the credit overview section has \"(.*)\" \"(.*)\" \"(.*)\" details and the respective information are fetching from the db based on the account$")
    public void userVerifiesCreditLimit(String AccBalance, String CreditLimit, String Available) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            PropUtils.setProps("accountNumber", String.valueOf(accountNumber), baseUtils.testDataFilePath);
            String query = "select credit_limit, actual_balance,(credit_limit-actual_balance) as Available from accounts where account_no= " + accountNumber + " ";
            Map<String, String> creditOveriew = commonUtils.getQueryResultsOnMap(query);
            query = "select CURRENCY_SYMBOL from currencies c\n" +
                    "inner join m_customers mcust on mcust.CURRENCY_OID = c.CURRENCY_OID\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where mcust.customer_no = " + PropUtils.getPropValue(baseUtils.testDataProperties, "accountNumber") + " \n" +
                    "and mc.short_name = '" + PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'";
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            accountInfoPage.verifyAccountBalance(logger, creditOveriew.get("ACTUAL_BALANCE"), queryResults.get("CURRENCY_SYMBOL"));
            accountInfoPage.verifycreditLimit(logger, creditOveriew.get("CREDIT_LIMIT"), queryResults.get("CURRENCY_SYMBOL"));
            accountInfoPage.verifyAvailable(logger, creditOveriew.get("AVAILABLE"), queryResults.get("CURRENCY_SYMBOL"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User Verify whether the user have an option to Edit or set up credit limit")
    public void verifyTheAlert() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.verifyTheALert(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User click on the edit contact")
    public void userClickOnTheEditContact() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(2);
            accountInfoPage.clickEditContact(logger);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate whether the contact information section has \"(.*)\" \"(.*)\" \"(.*)\" \"(.*)\" \"(.*)\" and the respective information are fetching from the db based on the account$")
    public void verifyTheEditContactInformation(String contactName, String emailAddress, String mobilePhone, String otherPhone, String fax) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String editquery = "select NVL(contact_name, 'N/A') as CONTACT_NAME, NVL(email_address, 'N/A') as EMAIL_ADDRESS, " +
                    "NVL(phone_fax, 'N/A')as PHONE_FAX, NVL(phone_business, 'N/A')as PHONE_BUSINESS,  NVL(phone_mobile_1, 'N/A')as PHONE_MOBILE_1 from m_customers where customer_no= " + accountNumber + "  " +
                    "and client_mid=(select client_mid from m_clients where short_name='" + (PropUtils.getPropValue(baseUtils.inputProp, clientCountry)) + "')";
            Map<String, String> editContact = commonUtils.getQueryResultsOnMap(editquery);
            basePage.assertTwoStrings(logger, (editContact.get("CONTACT_NAME")), accountInfoPage.getValueFromContactName(logger, contactName));
            basePage.assertTwoStrings(logger, (editContact.get("EMAIL_ADDRESS")), accountInfoPage.getValueFromContactName(logger, emailAddress));
            basePage.assertTwoStrings(logger, (editContact.get("PHONE_MOBILE_1")), accountInfoPage.getValueFromContactName(logger, mobilePhone));
            basePage.assertTwoStrings(logger, (editContact.get("PHONE_BUSINESS")), accountInfoPage.getValueFromContactName(logger, otherPhone));
            basePage.assertTwoStrings(logger, (editContact.get("PHONE_FAX")), accountInfoPage.getValueFromContactName(logger, fax));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate edit contact data is saved in database")
    public void userValidateEditContactDataIsSavedInDatabase() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String editContactQuery = "select  NVL(contact_name, 'N/A') as CONTACT_NAME,  NVL(email_address, 'N/A') as EMAIL_ADDRESS, " +
                    "NVL(phone_fax, 'N/A')as PHONE_FAX, NVL(phone_business, 'N/A')as PHONE_BUSINESS,  NVL(phone_mobile_1, 'N/A')as PHONE_MOBILE_1 from m_customers where customer_no=" + accountNumber + "" +
                    "and client_mid=(select client_mid from m_clients where short_name='" + (PropUtils.getPropValue(baseUtils.inputProp, clientCountry)) + "')";
            Map<String, String> editContact = commonUtils.getQueryResultsOnMap(editContactQuery);
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-contactName"), editContact.get("CONTACT_NAME"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-emailAddress"), editContact.get("EMAIL_ADDRESS"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-mobilePhone"), editContact.get("PHONE_MOBILE_1"));
//            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-otherPhone"), editContact.get("PHONE_BUSINESS"));
//            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-fax"), editContact.get("PHONE_FAX"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User click on the edit address")
    public void userClickOnTheEditAddress() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(2);
            accountInfoPage.clickEditAddress(logger);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate whether the Address has \"(.*)\" \"(.*)\" \"(.*)\" \"(.*)\" and the respective information are fetching from the db based on the account")
    public void userValidatetheAddressOfAccountInformation(String contactAddressLine1, String contactCity, String contactZipCode, String contactState) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String editAddressQuery = "select a.ADDRESS_LINE, s.description, a.POSTAL_CODE, a.SUBURB from m_customers m,ADDRESSES a, states s \n" +
                    "where  m.STREET_ADDRESS_OID = a.ADDRESS_OID and  a.state_oid= s.state_oid and customer_no=" + accountNumber + "  " +
                    "and client_mid=(select client_mid from m_clients where short_name='" + (PropUtils.getPropValue(baseUtils.inputProp, clientCountry)) + "')";
            Map<String, String> editAddress = commonUtils.getQueryResultsOnMap(editAddressQuery);
            basePage.assertTwoStrings(logger, (editAddress.get("ADDRESS_LINE").replaceAll("\n", "")), accountInfoPage.getValueFromContactName(logger, contactAddressLine1));
            basePage.assertTwoStrings(logger, (editAddress.get("SUBURB")), accountInfoPage.getValueFromContactName(logger, contactCity));
            basePage.assertTwoStrings(logger, (editAddress.get("POSTAL_CODE")), accountInfoPage.getValueFromContactName(logger, contactZipCode));
            basePage.assertTwoStrings(logger, (editAddress.get("DESCRIPTION")), accountInfoPage.getValueForState(logger, contactState));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate edit address data is saved in database")
    public void userValidateEditAddressDataIsSavedInDatabase() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String editAddressQuery = "select a.ADDRESS_LINE, s.description, a.POSTAL_CODE, a.SUBURB from m_customers m,ADDRESSES a, states s \n" +
                    "where  m.STREET_ADDRESS_OID = a.ADDRESS_OID and  a.state_oid= s.state_oid and customer_no=" + accountNumber + "  " +
                    "and client_mid=(select client_mid from m_clients where short_name='" + (PropUtils.getPropValue(baseUtils.inputProp, clientCountry)) + "')";
            Map<String, String> editAdress = commonUtils.getQueryResultsOnMap(editAddressQuery);
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-addressLine"), editAdress.get("ADDRESS_LINE"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-suburb"), editAdress.get("SUBURB"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountInformation-postalCode"), editAdress.get("POSTAL_CODE"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User get an account number for which account level taxnumber presence \"(.*)\" and country level taxnumbers presence \"(.*)\"$")
    public void userGetAccountNumberBasedOnTaxNumberAvailability(String isAccountLvlTaxNo, String isCountryLvlTaxNo) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.handleTaxNumbers(logger, isAccountLvlTaxNo, isCountryLvlTaxNo);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify \"(.*)\" value visibility \"(.*)\" based on tagName \"(.*)\" with position \"(.*)\"$")
    public void userVerifyPresenceOfValueBasedOnTagName(String value, String visibility, String tagName, String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Boolean isPresent = basePage.whetherElementPresent(logger, By.xpath("(//div[contains(text(),'PRIMARY TAX NUMBER')])[1]"));
            String actVisibility = "";
            if (isPresent) {
                actVisibility = "Yes";
            } else {
                actVisibility = "No";
            }
            commonPage.assertTwoStrings(logger, visibility, actVisibility, value + " visibility");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click \"(.*)\" then select date based on property value \"(.*)\" of date format property \"(.*)\" no of days \"(.*)\" of \"(.*)\" time$")
    public void userClickFieldAndSelectDateBasedOnPropertyValue(String fieldName, String expiryDateProperty, String dateFormatProperty, String noOfDays, String futureOrPast) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            try {
                driver.findElement(By.cssSelector("mav-input[name='" + fieldName + "']>input")).click();
            } catch (Exception e) {
                basePage.userClick(logger, By.cssSelector("mav-input[ng-reflect-name='" + fieldName + "']>input"));
            }
            String dateToBeSelected = commonPage.getDesiredDateInSpecificFormat(logger, PropUtils.getPropValue(properties, expiryDateProperty), 0, "future", PropUtils.getPropValue(properties, dateFormatProperty), "MMMM d, yyyy");
            commonPage.handleDatePickerForPastOrFutureDateSelection(logger, dateToBeSelected.split(" ")[0].substring(0, 3), dateToBeSelected.split(" ")[1], dateToBeSelected.split(" ")[2]);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User expand the record based on number \"(.*)\"$")
    public void userVerifyPresenceOfValueBasedOnTagName(String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userClick(logger, By.xpath("(//div/mat-grid-list)[" + position + "]"));
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User clear the text field using locator \"(.*)\" type of locator \"(.*)\"$")
    public void userClearTheTextFieldBasedOnItsLocator(String locator, String locatorType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (locatorType.equalsIgnoreCase("cssSelector")) {
                basePage.userTypeIntoTextField(logger, By.cssSelector(locator), "Test");
                basePage.getWebElementUsingLocator(logger, By.cssSelector(locator)).clear();
            } else if (locatorType.equalsIgnoreCase("xpath")) {
                basePage.userTypeIntoTextField(logger, By.xpath(locator), "Test");
                basePage.getWebElementUsingLocator(logger, By.xpath(locator)).clear();
            }
            commonPage.sleepForFewSeconds(3);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select \"(.*)\" values in 'addNewVatNumber' page$")
    public void userSelectCountryAdnTaxExemptValues(String dropName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select description as country from countries where rownum<=10";
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            if (dropName.equalsIgnoreCase("country")) {
                PropUtils.setProps("addVatNumber-Country", queryResults.get("COUNTRY"), baseUtils.testDataFilePath);
                basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='vatCountry']"));
                commonPage.clickButtonUsingSpan(logger, queryResults.get("COUNTRY"));
            }
            //select tax exempt
            if (dropName.equalsIgnoreCase("taxExempt")) {
                query = "select description as taxExempt from constants where constant_type_oid = 139";
                queryResults = commonUtils.getQueryResultsOnMap(query);
                PropUtils.setProps("addVatNumber-TaxExempt", queryResults.get("TAXEXEMPT"), baseUtils.testDataFilePath);
                basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='vatTaxExempt']"));
                commonPage.clickButtonUsingSpan(logger, queryResults.get("TAXEXEMPT"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate \"(.*)\" section of tax numbers module for \"(.*)\" for record which is at position \"(.*)\"$")
    public void userVerifyPresenceOfValueBasedOnTagName(String sectionType, String sectionName, String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.validateTaxNoFieldValuesBasedOnSections(logger, sectionType, sectionName, position);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify in the database newly created Vat number has been added$")
    public void userVerifyNewlyCreatedVatNumberInDatabase() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String effectiveDate = PropUtils.getPropValue(properties, "Date-vatEffectiveData");
            String expiryDate = PropUtils.getPropValue(properties, "Date-vatExpiryDate");
            String query = "select c.description as country,c.country_code,const.description as taxType,\n" +
                    "tn.effective_on,tn.expires_on,tn.tax_no from tax_numbers tn\n" +
                    "left join countries c on c.country_oid = tn.country_oid\n" +
                    "left join constants const on const.constant_oid = tn.tax_type_cid\n" +
                    "left join m_customers mcust on mcust.customer_mid = tn.member_oid\n" +
                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where mcust.customer_no = '" + PropUtils.getPropValue(properties, "commonAccountNo") + "' and \n" +
                    "mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ") \n" +
                    "and c.description = '" + PropUtils.getPropValue(properties, "addNewVatNumber-Country") + "' \n" +
                    "and const.description = '" + PropUtils.getPropValue(properties, "addNewVatNumber-TaxExempt") + "'\n" +
                    "and tn.effective_on = '" + effectiveDate.split("/")[0] + "-" +
                    commonPage.getShortNameOfMonthBasedOnIndex(Integer.parseInt(effectiveDate.split("/")[1]))
                    + "-" + effectiveDate.split("/")[2].substring(effectiveDate.split("/")[2].length() - 2)
                    + "' and tn.expires_on = '" + expiryDate.split("/")[0] + "-" +
                    commonPage.getShortNameOfMonthBasedOnIndex(Integer.parseInt(expiryDate.split("/")[1]))
                    + "-" + expiryDate.split("/")[2].substring(expiryDate.split("/")[2].length() - 2)
                    + "' and tn.tax_no = '" +
                    PropUtils.getPropValue(properties, "add-vatTaxNo") + "'";
            logger.log(query);
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() == 1) {
                logger.log("Newly created vat number has been successfully saved in the database");
            } else {
                logger.log("Newly created vat number is not saved in the database");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify based on validation control user should be able to \"(.*)\" fields then validate in database for section \"(.*)\" for user \"(.*)\"$")
    public void verifyBasedOnValControlEditIconIsEnabledAndModifyEligibleFields(String actionOfSection, String sectionName, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (sectionName.equalsIgnoreCase("DUNNING") || sectionName.equalsIgnoreCase("SECURITIES/SOLVENCY") || sectionName.contains("GUARANTEE")) {
                basePage.userClick(logger, By.xpath("//div[contains(text(),'Other')]"));
            }
            PropUtils.setProps("sectionName", sectionName, baseUtils.testDataFilePath);
            accountInfoPage.validateSectionBehaviorBasedOnValidationControls(logger, sectionName, actionOfSection, userType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User get an account number which is having status \"(.*)\" for approval requests for module \"(.*)\" based on flag \"(.*)\" for logged in user \"(.*)\"$")
    public void userGetAnAccountNumberWhichIsNotHavingPendingOrPostedApprovalRequestsForModule(String requestType,String moduleName, String canApproveOwnFlag, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.getAnAccountNumberWhichIsNotHavingPendingOrPostedApprovalRequest(logger,moduleName,requestType,canApproveOwnFlag,userName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User click on button Three dot icon when vat number record is matching with pending record or not$")
    public void userClickThreeDotIconWhenVatNoRecordIsMatchingWithPendingOrNot() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountInfoPage.clickThreeDotIconForTaxNumber(logger,properties);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User search a site by selecting field values \"(.*)\" and site name$")
    public void userSearchASiteBySelectingFieldValuesAndSiteName(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User get an account which \"(.*)\" \"(.*)\" for user type \"(.*)\"$")
    public void userGetAnAccountWhichIsHavingOrNotHavingRecords(String isHavingOrNot, String moduleName, String userType) {
        Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
        Properties commonProperties = PropUtils.getProps(baseUtils.commonPropertyFile);
        if (!PropUtils.getPropValue(testDataProperties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            List<Map<String, String>> queryResults = new ArrayList<>();
            if (userType.equalsIgnoreCase("OLS")) {
                if (isHavingOrNot.equalsIgnoreCase("isHaving") || isHavingOrNot.equalsIgnoreCase("having")) {
                    query = "select mcust.customer_no,cpg.name as groupPinName from customer_pin_groups cpg\n" +
                            "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                            "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                            "left join users u on u.user_oid = um.user_oid\n" +
                            "where u.logon_id = '" + PropUtils.getPropValue(commonProperties, "currentUserName") + "' and rownum<=10 order by cpg.name asc";
                    queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    logger.log(query);
                    PropUtils.setProps("accountNo", queryResults.get(0).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
                    PropUtils.setProps("groupPinName", queryResults.get(0).get("GROUPPINNAME"), baseUtils.testDataFilePath);
                    if (PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), "Scenario_Name").contains("Group PIN name must be unique") || PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile),"Scenario_Name").contains("User change the pin name in 'change Name' action")) {
                        query = "select mcust.customer_no from m_customers mcust\n" +
                                "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                                "left join users u on u.user_oid = um.user_oid\n" +
                                "where u.logon_id = '" + PropUtils.getPropValue(commonProperties, "currentUserName") + "' and rownum<=10 order by mcust.name asc";
                        queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                        Map<String, String> groupPinRecordCount = new HashMap<>();
                        for (int i = 0; i <= queryResults.size() - 1; i++) {
                            query = "select count(*) from customer_pin_groups cpg\n" +
                                    "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                                    "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                                    "left join users u on u.user_oid = um.user_oid\n" +
                                    "where u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName") +
                                    "' and mcust.customer_no = '" + queryResults.get(i).get("CUSTOMER_NO") + "'";
                            groupPinRecordCount = commonUtils.getQueryResultsOnMap(query);
                            if (Integer.parseInt(groupPinRecordCount.get("COUNT(*)")) > 2) {
                                PropUtils.setProps("groupPinName1", queryResults.get(1).get("GROUPPINNAME"), baseUtils.testDataFilePath);
                                break;
                            }
                        }
                    }
                } else {
                    query = "select mcust.customer_no from m_customers mcust\n" +
                            "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                            "left join users u on u.user_oid = um.user_oid\n" +
                            "where u.logon_id = '" + PropUtils.getPropValue(commonProperties, "currentUserName") + "' and rownum<=100 order by mcust.name asc";
                    queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    logger.log(query);
                    List<Map<String, String>> groupPinRecords = new ArrayList<>();
                    for (int i = 0; i <= queryResults.size() - 1; i++) {
                        query = "select cpg.name as groupPinName from customer_pin_groups cpg\n" +
                                "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                                "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                                "left join users u on u.user_oid = um.user_oid\n" +
                                "where u.logon_id = '" + PropUtils.getPropValue(testDataProperties, "currentUserName") +
                                "' and mcust.customer_no = '" + queryResults.get(i).get("CUSTOMER_NO") + "'";
                        logger.log(query);
                        groupPinRecords = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                        if (groupPinRecords.size() >= 1) {
                            PropUtils.setProps("accountNo", queryResults.get(i).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
                            PropUtils.setProps("groupPinName", groupPinRecords.get(i).get("GROUPPINNAME"), baseUtils.testDataFilePath);
                            PropUtils.setProps("countOfGroupPINRecords", String.valueOf(groupPinRecords.size()), baseUtils.testDataFilePath);

                        }
                    }
                }
            } else if (userType.equalsIgnoreCase("OMV")) {
                if (isHavingOrNot.equalsIgnoreCase("isHaving") || isHavingOrNot.equalsIgnoreCase("having")) {
                    query = "select mcust.customer_no,cpg.name as groupPinName from customer_pin_groups cpg\n" +
                            "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                            "where mcust.client_mid = " + PropUtils.getPropValue(testDataProperties, "ClientMID") + " and rownum<=10 order by mcust.name asc";
                    queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    logger.log(query);
                    PropUtils.setProps("accountNo", queryResults.get(0).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
                    PropUtils.setProps("groupPinName", queryResults.get(0).get("GROUPPINNAME"), baseUtils.testDataFilePath);
                    PropUtils.setProps("groupPinName1", queryResults.get(1).get("GROUPPINNAME"), baseUtils.testDataFilePath);
                } else {
                    query = "select mcust.customer_no from m_customers where client_mid = " + PropUtils.getPropValue(testDataProperties, "ClientMID")
                            + " and rownum<=100 order by mcust.name asc";
                    queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    logger.log(query);
                    List<Map<String, String>> groupPinRecords = new ArrayList<>();
                    for (int i = 0; i <= queryResults.size() - 1; i++) {
                        query = "select mcust.customer_no,cpg.name as groupPinName from customer_pin_groups cpg\n" +
                                "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                                "where mcust.client_mid = " + PropUtils.getPropValue(testDataProperties, "ClientMID")
                                + " and mcust.customer_no = '" + queryResults.get(i).get("CUSTOMER_NO") +
                                "' and rownum<=10 order by mcust.name asc";
                        logger.log(query);
                        groupPinRecords = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                        if (groupPinRecords.size() >= 1) {
                            PropUtils.setProps("accountNo", queryResults.get(i).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
                            PropUtils.setProps("groupPinName", groupPinRecords.get(i).get("GROUPPINNAME"), baseUtils.testDataFilePath);
                        }
                    }
                }
                Properties properties = PropUtils.getProps(baseUtils.testDataFile);
                query = "select count(*) from customer_pin_groups cpg\n" +
                        "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                        "where mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNo") + "'\n" +
                        "and mcust.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
                Map<String, String> queryResultsOfMap = commonUtils.getQueryResultsOnMap(query);
                PropUtils.setProps("countOfGroupPINRecords", queryResultsOfMap.get("COUNT(*)"), baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(testDataProperties, "skipReason"));
        }
    }

    @And("^User get an account for which should not have pending vat number status for \"(.*)\"$")
    public void userGetAnAccountForWhichShouldNotHavePendingVatNumberStatusAsSystemDate(String date) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}