package stepDefinitions.stepDefinitionsUI;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.CommonPage;
import pages.LoginPage;
import pages.UsersPage;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UsersSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private CommonPage commonPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public UsersSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        commonPage = new CommonPage(driver);
    }

    @Then("^User verifies the count of records in \"(.*)\" page matches with the database or not$")
    public void userVerifiesTheCountOfRecordsInUsersPageMatchesWithTheDatabaseOrNot(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            PropUtils.setProps("accountNumber", String.valueOf(accountNumber), baseUtils.testDataFilePath);
            String query = "";
            Map<String, String> queryResults = null;
            String expectedCountOfRecords = "";
            String actualCountOfRecords = "";
            if (moduleName.equalsIgnoreCase("users")) {
                query = "select count(*) From users u\n" +
                        "inner join constants c on c.constant_oid = u.logon_status_cid\n" +
                        "inner join user_members um on um.user_oid = u.user_oid\n" +
                        "inner join m_customers mcust on mcust.customer_mid = um.member_oid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n"+
                        "where mcust.customer_no = '" + accountNumber + "' and mc.client_mid in " +
                        "(" + PropUtils.getPropValue(properties, "ClientMID") + ") order by initcap(u.name), initcap(u.logon_id) asc";
                queryResults = commonUtils.getQueryResultsOnMap(query);
                expectedCountOfRecords = queryResults.get("COUNT(*)");
                actualCountOfRecords = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("cost centres")) {
                query = "select count(*) from customer_cost_centres ccc\n" +
                        "inner join m_customers mcust on mcust.customer_mid = ccc.customer_mid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mcust.customer_no = '" + accountNumber + "' and mc.client_mid in " +
                        "(" + PropUtils.getPropValue(properties, "ClientMID") + ")";
                queryResults = commonUtils.getQueryResultsOnMap(query);
                expectedCountOfRecords = queryResults.get("COUNT(*)");
                actualCountOfRecords = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("contacts")) {
                query = "select count(*) from contacts\n" +
                        "inner join m_customers on m_customers.customer_mid = contacts.member_oid\n" +
                        "where m_customers.customer_No = '" + accountNumber + "'";
                queryResults = commonUtils.getQueryResultsOnMap(query);
                expectedCountOfRecords = queryResults.get("COUNT(*)");
                actualCountOfRecords = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("Drivers")) {
                query = "select count(*) from drivers where customer_mid in(select CUSTOMER_MID from accounts_view where account_no='" + accountNumber + "' \n" +
                        "and CLIENT_MID=(select client_mid from m_clients where mc.client_mid in (" +
                        PropUtils.getPropValue(properties, "ClientMID") + ")))";
                queryResults = commonUtils.getQueryResultsOnMap(query);
//                 if(Integer.parseInt(queryResults.get("COUNT(*)"))>100) {
//                     expectedCountOfRecords = "100";
//                 }else{
                expectedCountOfRecords = queryResults.get("COUNT(*)");
//                 }
                actualCountOfRecords = usersPage.getCountOfRecords(logger);
            } else if (moduleName.equalsIgnoreCase("Vehicles")) {
                query = "select count(*) from vehicles where customer_mid in(select CUSTOMER_MID from accounts_view where account_no='" + accountNumber + "' \n" +
                        "and CLIENT_MID=(select client_mid from m_clients where mc.client_mid in (" +
                        PropUtils.getPropValue(properties, "ClientMID") + ")))";
                queryResults = commonUtils.getQueryResultsOnMap(query);
//                 if(Integer.parseInt(queryResults.get("COUNT(*)"))>100) {
//                     expectedCountOfRecords = "100";
//                 }else{
                expectedCountOfRecords = queryResults.get("COUNT(*)");
//                 }
                actualCountOfRecords = usersPage.getCountOfRecords(logger);
            }
            logger.log(query);
            basePage.assertTwoStrings(logger, expectedCountOfRecords, actualCountOfRecords);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter \"(.*)\" as \"(.*)\" in \"(.*)\" module having length \"(.*)\" in \"(.*)\" form$")
    public void userEnterFieldValueInCorrespondingField(String fieldName, String fieldValue, String moduleName, String length, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (fieldName.contains("mail")) {
                basePage.userTypeIntoTextField(logger, By.cssSelector("ols-email[ng-reflect-name='emailAddress']>input"), fieldValue);
                PropUtils.setProps(moduleName + "-emailAddress", fieldValue, baseUtils.testDataFilePath);
            } else {
                usersPage.enterValueBasedOfItsType(logger, fieldValue, fieldName, formType, moduleName, length, "-_");
                String newValue = usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, fieldName);
                PropUtils.setProps(moduleName + "-" + fieldName, newValue, baseUtils.testDataFilePath);
                PropUtils.setProps(formType + "-" + fieldName, newValue, baseUtils.testDataFilePath);
            }
            logger.log("Field " + fieldName + " value is " + fieldValue + " entered");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter wrong \"(.*)\" and validate error message \"(.*)\"$")
    public void userEnterWrongFiledValueAndValidateCorrespondingErrorMessage(String fieldName, String errorMessage, DataTable dt) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<String> values = dt.asList(String.class);
            String arrValues[] = {values.get(0)};
            if (values.get(0).contains(",")) {
                arrValues = values.get(0).split(",");
            }
            for (int i = 0; i <= arrValues.length - 1; i++) {
                usersPage.enterValueInUserModule(logger, fieldName, arrValues[i], errorMessage);
                logger.log("Error message for User Name " + arrValues[i] + " validated");

            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select value \"(.*)\" by clicking drop down \"(.*)\"$")
    public void userSelectValueByClickingDropDown(String dropDownValue, String dropDownName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.selectValueFromDropDown(logger, dropDownValue, dropDownName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select value \"(.*)\" in \"(.*)\" module by clicking drop down \"(.*)\"$")
    public void userSelectValueByClickingCorrespondingDropDown(String dropDownValue, String moduleName, String dropDownName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String selectedValue = usersPage.selectValueInCorrespodingDropdown(logger, dropDownValue, dropDownName);
            PropUtils.setProps(moduleName + "-" + dropDownName, selectedValue, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify dropdown \"(.*)\" is selected with value \"(.*)\" and disabled in module \"(.*)\"$")
    public void userVerifyDropDownIsDisabledOrNot(String dropDownName, String dropdownValue, String moduleName) {
        Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testDataProperties, "testStatus").equalsIgnoreCase("Skipped")) {
            By contactTypeLocator = By.xpath("//span[contains(text(),'" + PropUtils.getPropValue(testDataProperties, dropdownValue) + "')]");
            if (basePage.whetherElementPresent(logger, contactTypeLocator)) {
                logger.log("Contact type is showing a expected");
            } else {
                logger.log("Contact type is not showing as expected");
            }
        } else {
            logger.log(PropUtils.getPropValue(testDataProperties, "skipReason"));
        }
    }

    @Then("^User click on Next button$")
    public void userClickOnNextButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.clickOnNextOnTheForm(logger);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click on \"(.*)\" button$")
    public void userClickOnSubmitButton(String buttonName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.scrollDown(logger);
            usersPage.clickOnSubmitButton(logger, buttonName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User click on radio button based on the account number \"(.*)\"$")
    public void userClickOnRadiButtonBasedOnAccountNumber(String accountNumber) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            accountNumber = PropUtils.getPropValue(testDataProperties, accountNumber);
            usersPage.selectAccountBasedOnAccountNumber(logger, accountNumber);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validated success message of add module is \"(.*)\"$")
    public void userValidatesSuccessMessageOfAddUser(String expSuccessMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.validateSuccessMessageOfAddUser(logger, expSuccessMessage);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validated success message of module is \"(.*)\"$")
    public void userValidateSuccessMessageOfEditUser(String expSuccessMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.validateSuccessMessageOfEditUser(logger, expSuccessMessage);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validated success message of Edit status is \"(.*)\"$")
    public void userValidatedSuccessMessageOfEditStatusInUserModuleIs(String expSuccessMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.validateSuccessMessageOfEditStatus(logger, expSuccessMessage);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User click on Save button$")
    public void userClickOnSaveButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.clickSaveButton(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate user data is saved in database$")
    public void userValidateUserDataIsSavedInDatabase() {
        Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testDataProperties, "testStatus").equalsIgnoreCase("Skipped")) {
            int accountNumber = Integer.parseInt(PropUtils.getPropValue(testDataProperties, "accountNumber"));
            String userName = PropUtils.getPropValue(testDataProperties, "users-userName");
            String query = "select ar.description as user_role,c.description as user_status, u.name, u.email_address,u.logon_id as userId,\n" +
                    "u.mobile_phone, u.other_phone from users u\n" +
                    "left join constants c on c.constant_oid = u.logon_status_cid \n" +
                    "left join user_roles ur on ur.user_oid = u.user_oid\n" +
                    "left join access_roles ar on ar.access_role_oid = ur.access_role_oid\n" +
                    "where logon_id = '" + userName + "' ";
            logger.log(query);
            Map<String, String> userDetails = commonUtils.getQueryResultsOnMap(query);
            basePage.assertTwoStrings(logger, userDetails.get("NAME"), PropUtils.getPropValue(testDataProperties, "users-fullName"));
            basePage.assertTwoStrings(logger, userDetails.get("EMAIL_ADDRESS"), PropUtils.getPropValue(testDataProperties, "users-emailAddress"));
            basePage.assertTwoStrings(logger, userDetails.get("MOBILE_PHONE"), PropUtils.getPropValue(testDataProperties, "users-mobilePhone"));
            basePage.assertTwoStrings(logger, userDetails.get("OTHER_PHONE"), PropUtils.getPropValue(testDataProperties, "users-otherPhone"));
            basePage.assertTwoStrings(logger, userDetails.get("USER_ROLE"), PropUtils.getPropValue(testDataProperties, "users-role"));
        } else {
            logger.log(PropUtils.getPropValue(testDataProperties, "skipReason"));
        }
    }

    @And("^User clear the filters$")
    public void userClearTheFilters() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = PropUtils.getPropValue(properties, "accountNumber");
            usersPage.clickResetAllFilters(logger);
            String query = "select count(*) from (select name as name, logon_id as userId, email_address as email_address,mobile_phone as mobile_phone, other_phone as other_phone, (select description from constants where constant_oid=(select logon_status_cid from users where user_oid=u.user_oid)) as user_status, (select description from access_groups where access_group_oid=(select access_group_oid from users where user_oid=u.user_oid)) as user_role from users u, user_members um, accounts ac where u.user_oid = um.user_oid and um.member_oid = ac.customer_mid and ac.account_no=" + accountNumber + ")";
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            String expectedCountOfRecords = queryResults.get("COUNT(*)");
            String actualCountOfRecords = usersPage.getCountOfRecords(logger);
            basePage.assertTwoStrings(logger, expectedCountOfRecords, actualCountOfRecords);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate the non editable value of field \"(.*)\" in module \"(.*)\" in form \"(.*)\"$")
    public void userValidateNonEditableValueOfField(String fieldName, String moduleName, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String actualValue = usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, fieldName);
            PropUtils.setProps("accountNumber", usersPage.getAccountNumberFromUsersPage(logger), baseUtils.testDataFilePath);
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            if (fieldName.equalsIgnoreCase("accountNumber")) {
                basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "accountNumber"), actualValue);
            } else {
                basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, moduleName + "-" + fieldName), actualValue);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select filter values then click on Apply button and validate filter results in database")
    public void userSelectFilterValue() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testData = PropUtils.getProps(baseUtils.testDataFile);
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String[] arrDbUserStatusesForAnAccount = null;
            String dbUserStatusesForAnAccount = usersPage.getAvailableUserStatusesForAnAccount();
            arrDbUserStatusesForAnAccount = dbUserStatusesForAnAccount.split(",");
            String[] arrDbUserRolesForAnAccount = null;
            String dbUserRolesForAnAccount = usersPage.getAvailableUserRolesForAnAccount(logger, accountNumber);
            logger.log(dbUserRolesForAnAccount);
            arrDbUserRolesForAnAccount = dbUserRolesForAnAccount.split(",");
            String userStatusesIds = "";
            String userRolesIds = "";
            String query = "";
            for (int i = 0; i <= arrDbUserStatusesForAnAccount.length - 1; i++) {
                for (int j = 0; j <= arrDbUserRolesForAnAccount.length - 1; j++) {
                    // Validate count of records for each status and role
                    if (i == arrDbUserStatusesForAnAccount.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All status", dbUserStatusesForAnAccount);
                        userStatusesIds = usersPage.getUserStatusCodesBasedOnUserStatusDescription(dbUserStatusesForAnAccount);
                        usersPage.selectValuesFromFilter(logger, "All roles", arrDbUserRolesForAnAccount[j]);
                        userRolesIds = usersPage.getUserRoleCodesBasedOnUserRolesDescription(arrDbUserRolesForAnAccount[j]);
                        logger.log("Role name :" + arrDbUserRolesForAnAccount[j]);
                    } else if (j == arrDbUserRolesForAnAccount.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All status", arrDbUserStatusesForAnAccount[i]);
                        userStatusesIds = usersPage.getUserStatusCodesBasedOnUserStatusDescription(arrDbUserStatusesForAnAccount[i]);
                        usersPage.selectValuesFromFilter(logger, "All roles", dbUserRolesForAnAccount);
                        userRolesIds = usersPage.getUserRoleCodesBasedOnUserRolesDescription(dbUserRolesForAnAccount);
                        logger.log("Role name :" + dbUserRolesForAnAccount);
                    } else {
                        usersPage.selectValuesFromFilter(logger, "All status", arrDbUserStatusesForAnAccount[i]);
                        usersPage.selectValuesFromFilter(logger, "All roles", arrDbUserRolesForAnAccount[j]);
                        userStatusesIds = usersPage.getUserStatusCodesBasedOnUserStatusDescription(arrDbUserStatusesForAnAccount[i]);
                        userRolesIds = usersPage.getUserRoleCodesBasedOnUserRolesDescription(arrDbUserRolesForAnAccount[j]);
                        logger.log("Role name :" + arrDbUserRolesForAnAccount[j]);
                    }
                    query = "select count(*) from users u \n" +
                            "inner join user_roles ur on ur.user_oid = u.user_oid\n" +
                            "inner join access_roles ar on ar.access_role_oid = ur.access_role_oid\n" +
                            "inner join constants c on c.constant_oid = u.logon_status_cid \n" +
                            "left join user_members um on um.user_oid = u.user_oid\n" +
                            "left join m_customers mcust on mcust.customer_mid = um.member_oid \n" +
                            "inner join m_clients mc on mc.client_mid = mcust.client_mid\n"+
                            "where mcust.customer_no = '" + accountNumber + "' and\n" +
                            "u.logon_status_cid in (" + userStatusesIds + ") and ar.access_role_oid in (" + userRolesIds + ") and mc.client_mid = "+PropUtils.getPropValue(testData,"ClientMID")+"\n" +
                            "order by initcap(u.name), initcap(u.logon_id) asc";
                    logger.log(query);
                    Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
                    String expRecordsCount = usersPage.getCountOfRecords(logger);
                    commonPage.sleepForFewSeconds(1);
                    String actualRecordsCount = queryResults.get("COUNT(*)");
                    basePage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount);
                    usersPage.clickResetAllFilters(logger);
                    commonPage.sleepForFewSeconds(3);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select all accounts from the list of accounts if more than one account displayed for logged in user \"(.*)\"$")
    public void userSelectOneAccountFromTheListOfAccountsIfMoreThanOneAccountDisplayed(String logonId) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProp = PropUtils.getProps(baseUtils.inputDataFile);
            String allAccounts = usersPage.getAccountNumbersBasedOnUser(logger, PropUtils.getPropValue(inputProp, logonId));
            usersPage.selectAccountNumberForLoggedInUserInAccountStatusForm(logger, allAccounts);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate the accounts should match for logged in user \"(.*)\" and latestUpdated user \"(.*)\"$")
    public void userSelectOneAccountFromTheListOfAccountsIfMoreThanOneAccountDisplayed(String logonId, String newlyUpdatedUser) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProp = PropUtils.getProps(baseUtils.inputDataFile);
            String allAccountsOfLoggedInUser = usersPage.getAccountNumbersBasedOnUser(logger, PropUtils.getPropValue(inputProp, logonId));
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            newlyUpdatedUser = PropUtils.getPropValue(testDataProperties, newlyUpdatedUser);
            String allAccountsOfNewlyUpdatedUser = usersPage.getAccountNumbersBasedOnUser(logger, newlyUpdatedUser);
            String arrAllAccountsOfLoggedInUser[] = usersPage.getArrayVariableFromStringVariable(allAccountsOfLoggedInUser);
            String arrAllAccountsOfNewlyUpdatedUser[] = usersPage.getArrayVariableFromStringVariable(allAccountsOfNewlyUpdatedUser);
            if (Arrays.equals(arrAllAccountsOfLoggedInUser, arrAllAccountsOfNewlyUpdatedUser)) {
                logger.log("Accounts are same for logged in user and newly updated user");
            } else {
                logger.log("Accounts are not same for logged in user and newly updated user");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the selected status is match with the user status \"(.*)\"$")
    public void userVerifyTheSelectedStatusIsMatchWithTheUserStatus(String userStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.verifySelectedStatusInChangeStatusWithSelectedUserStatus(logger, userStatus);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select another status \"(.*)\" for user \"(.*)\"$")
    public void userVerifyTheSelectedStatusIsMatchWithTheUserStatus(String newStatus, String user) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.selectStatusInChangeStatusForm(logger, newStatus);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate the newly selected status \"(.*)\" for user \"(.*)\" is stored in database or not$")
    public void userValidateTheNewlySelectedStatusForUserIsStoredInDatabaseOrNot(String newStatus, String user) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            usersPage.validateChangedStatusInDatabaseForAUser(logger, newStatus, PropUtils.getPropValue(testDataProperties, user));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter value \"(.*)\" using locator \"(.*)\" with locator type \"(.*)\" is from property file \"(.*)\"$")
    public void userEnterValueBasedOnLocatorFromPropFile(String value, String locator, String locatortype, String isFromPropertyFile) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            String valueToBeEntered = value;
            if (isFromPropertyFile.equalsIgnoreCase("Yes") || isFromPropertyFile.equalsIgnoreCase("True")) {
                valueToBeEntered = PropUtils.getPropValue(properties, value);
            }else if(valueToBeEntered.equalsIgnoreCase("RandomAlphanumeric")){
                valueToBeEntered = PasswordGenerator.generateAlphaNumeric(logger, 10);
            }else if(valueToBeEntered.equalsIgnoreCase("RandomAlphanumericWithFewSpecialChars")){
                valueToBeEntered = PasswordGenerator.generateAlphabatesWithFewSpecialChars(logger, 10,",-");
            }else if(valueToBeEntered.equalsIgnoreCase("Numeric")){
                valueToBeEntered = PasswordGenerator.generateNumeric(10);
            }
            By locatorValue = null;
            if (locatortype.equalsIgnoreCase("xpath")) {
                locatorValue = By.xpath(locator);
            } else if (locatortype.equalsIgnoreCase("cssSelector")) {
                locatorValue = By.cssSelector(locator);
            }
            basePage.userTypeIntoTextField(logger, locatorValue, valueToBeEntered);
            PropUtils.setProps(value,valueToBeEntered,baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
