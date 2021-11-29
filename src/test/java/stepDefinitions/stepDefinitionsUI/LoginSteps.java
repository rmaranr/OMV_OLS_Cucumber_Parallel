package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.CommonPage;
import pages.GmailPage;
import pages.LoginPage;
import pages.UsersPage;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.*;

import java.util.Map;
import java.util.Properties;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private GmailPage gmailPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private CommonPage commonPage;
    private UsersPage usersPage;
    public io.cucumber.java.Scenario logger;
    private DriverFactory driverFactory;

    public LoginSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        gmailPage = new GmailPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        commonPage = new CommonPage(driver);
        usersPage = new UsersPage(driver);
    }

    @Given("^User is on Login page and validate WEX logo text \"(.*)\", for scenario \"(.*)\"$")
    public void userOpenChromeBrowserWithURL(String title, String scenarioName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (scenarioName.equals("")) {
                logger.log(scenarioName);
            }
            PropUtils.setProps("testStatus", "", baseUtils.testDataFilePath);
            logger.log("The Scenario Name is " + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), "Scenario_Name"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter \"(.*)\" and \"(.*)\"$")
    public void userEnterUserNameAndPassWord(String userName, String passWord) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            loginPage.enterUserName(logger, PropUtils.getPropValue(inputProperties, userName));
            logger.log("UserName: '" + (PropUtils.getPropValue(inputProperties, userName) + "' is entered successfully"));
            commonPage.sleepForFewSeconds(1);
            inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            loginPage.enterPassWord(logger, PropUtils.getPropValue(inputProperties, passWord));
            logger.log("PassWord: '" + (PropUtils.getPropValue(inputProperties, passWord) + "' is entered successfully"));
            String query = "update users set PASSWORD_CREATED_AT = sysdate-10 where logon_id = '" + PropUtils.getPropValue(inputProperties, userName) + "'";
            commonUtils.executeUpdateQuery(query);
            query = "update users set logon_status_cid = 2501 where logon_id = '" + PropUtils.getPropValue(inputProperties, userName)  + "'";
            commonUtils.executeUpdateQuery(query);
            commonPage.sleepForFewSeconds(1);
            PropUtils.setProps("currentUserName",PropUtils.getPropValue(inputProperties, userName),baseUtils.commonPropertyFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on login button$")
    public void userClickOnLoginButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.clickLogin(logger);
//            ((JavascriptExecutor)driver).executeScript("document.body.style.zoom='90%';");
            logger.log("Clicked on the Login button");
            commonPage.sleepForFewSeconds(3);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^Verify user \"(.*)\" logged in time in database$")
    public void verifyUserLoggedInTimeInDatabase(String logonID) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyUserLoggedInTime(logger, PropUtils.getPropValue(baseUtils.inputProp, logonID));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User navigated to Home page$")
    public void userNavigatedToHomePage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyUserHomePageTitlePresence(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify login button status by entering username \"(.*)\" and password \"(.*)\"$")
    public void userVerifyLoginButtonStatusByEnteringUserNameAndPassword(String userName, String passWord) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.enterUserName(logger, userName);
            loginPage.enterPassWord(logger, passWord);
            loginPage.verifyLoginButtonStatus(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on Forgot password link$")
    public void userClickOnForgotPasswordLink() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.clickForgotPassWordLink(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verify page header is \"(.*)\"$")
    public void userVerifyPageHeaderIs(String header) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyPageHeader(logger, header);
            logger.log("Page header is " + header);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verify page header in detail is \"(.*)\"$")
    public void userVerifyPageHeaderInDetailIs(String headerDetail) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyPageHeaderInDetail(logger, headerDetail);
            logger.log("Page header details is " + headerDetail);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify Send reset email button status by entering username \"(.*)\"$")
    public void userVerifySendResetEmailButtonSTatusByEnteringUserName(String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.enterUserName(logger, userName);
            loginPage.verifySendResetEmailButtonStatus(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter username is \"(.*)\"$")
    public void userEnterUserNameIs(String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.enterUserName(logger, PropUtils.getPropValue(baseUtils.inputProp, userName));
            logger.log("UserName is entered '" + PropUtils.getPropValue(baseUtils.inputProp, userName) + "'");
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter wrong password \"(.*)\"$")
    public void userEnterWrongPasswordIs(String password) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.enterPassWord(logger, password);
            logger.log("UserName is entered '" + password + "'");
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter wrong username \"(.*)\"$")
    public void userEnterWrongUserNameIs(String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.enterUserName(logger, userName);
            logger.log("UserName is entered '" + userName + "'");
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on Send reset email button$")
    public void userClickOnSendResetEmailButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.clickSendResetEmailButton(logger);
            logger.log("Send reset email button is clicked");
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the message header is \"(.*)\", error message detail is \"(.*)\"$")
    public void userVerifyTheErrorMessageHeaderAndDetail(String messageHeader, String messageDetail) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyErrorMessageInvalidPassword(logger, messageHeader, messageDetail);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the page header is \"(.*)\", error message header is \"(.*)\"$")
    public void userVerifyTheErrorPageHeaderAndMessageHeader(String pageHeader, String messageHeader) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyErrorPageHeaderAndMessageHeader(logger, pageHeader, messageHeader);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the presence of text \"(.*)\" in the page$")
    public void userVerifyThePresenceOfTextInThePage(String text) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.verifyPresenceOfText(logger, text);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User login to gmail with valid username \"(.*)\" and password \"(.*)\"$")
    public void userLoginToGmailWithValidUserNameAndPassWord(String userName, String passWord) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            gmailPage.loginToGmail(logger, PropUtils.getPropValue(baseUtils.inputProp, userName), PropUtils.getPropValue(baseUtils.inputProp, passWord));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validated the eye icon status and click on password field$")
    public void userValidateTheEyeIconStatusAndClickOnIt() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.validateStatusOfEyeIcon(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User reset the password by clicking on here link by using searchText \"(.*)\" and subject \"(.*)\"$")
    public void userResetPasswordByClickingHereLinkBasedOnSubject(String searchText, String subject) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
//            gmailPage.resetPassword(logger, searchText, subject);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User switched to a window number \"(.*)\"$")
    public void userSwitchedToResetPasswordWindow(int windowNumber) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.switchToNewWindow(logger, windowNumber);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User change the password created date past \"(.*)\" days for user \"(.*)\"$")
    public void userResetThePasswordByClickingOnResetPassWordLink(String noOfDaysToExpirePassword, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select ar.PASSWORD_EXPIRY_DAYS, ar.IS_PASSWORD_EXPIRY_FORCED from access_roles ar\n" +
                    "inner join user_roles ur on ur.access_role_oid = ar.access_role_oid\n" +
                    "inner join users u on u.user_oid = ur.user_oid \n" +
                    "where u.logon_id = '"+PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile),"currentUserName")+"'";
            Map<String, String>queryResults = commonUtils.getQueryResultsOnMap(query);
            String pwdExipryDays = commonPage.getSpecificValueFromMapObject(queryResults,"PASSWORD_EXPIRY_DAYS");
            if(!pwdExipryDays.equalsIgnoreCase("")) {
                if (Integer.parseInt(queryResults.get("PASSWORD_EXPIRY_DAYS")) >= 0) {
//                    loginPage.setPasswordExpireDate(logger, String.valueOf((Integer.parseInt(queryResults.get("PASSWORD_EXPIRY_DAYS")) + 10)), PropUtils.getPropValue(baseUtils.inputProp, userName));
                    loginPage.setPasswordExpireDate(logger, "2", PropUtils.getPropValue(baseUtils.inputProp, userName));
                } else {
                    PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
                    PropUtils.setProps("skipReason", "Password cannot be expired based on day number for logged in user", baseUtils.testDataFilePath);
                }
            }else {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "Password cannot be expired based on day number for logged in user", baseUtils.testDataFilePath);
            }
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies the message header \"(.*)\" and message detail \"(.*)\"$")
    public void userVerifiesThePasswordExpiredMessageHeaderAndDetail(String messageHeader, String messageDetail) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.validatePasswordExpireMessageHeaderAndDetail(logger, messageHeader, messageDetail);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate the presence of new password \"(.*)\" along with their radio button status not Selected \"(.*)\"$")
    public void userValidatePresenceOfNewPasswordSpecificationsWithTheirRadioButtonStatus(String data, Boolean radioButtonStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String specifications[] = data.split(",");
            loginPage.validateNewPasswordSpecifications(logger, radioButtonStatus, specifications);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter new password \"(.*)\" and validate that it contains all specifications \"(.*)\" and corresponding radio button status Selected \"(.*)\"$")
    public void userValidatePresenceOfNewPasswordSpecificationsWithTheirRadioButtonStatus(String newPassword, String data, Boolean radioButtonStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String specifications[] = data.split(",");
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            PropUtils.setProps("New_PassWord", PasswordGenerator.generateRandomPassword(logger, 15), baseUtils.inputDataFilePath);
            inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            logger.log("New Password is: " + PropUtils.getPropValue(inputProperties, newPassword));
            loginPage.validateNewPassword(logger, PropUtils.getPropValue(inputProperties, newPassword), specifications, radioButtonStatus);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter confirm new password \"(.*)\"$")
    public void userEnterConfirmNewPassword(String confirmNewPassword) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            loginPage.enterConfirmNewPassword(logger, PropUtils.getPropValue(inputProperties, confirmNewPassword));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter the new password \"(.*)\"$")
    public void userEnterTheNewPassword(String newPassword) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            PropUtils.setProps("New_PassWord", PasswordGenerator.generateRandomPassword(logger, 15), baseUtils.inputDataFilePath);
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            loginPage.enterNewPassword(logger, PropUtils.getPropValue(inputProperties, newPassword));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validates and click on submit button enabled if new password and confirm new password are same$")
    public void userValidateAndClickOnSubmitButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            String newPassword = PropUtils.getPropValue(inputProperties, "New_PassWord");
            Boolean isMatched = loginPage.verifyPasswordMatchesOld6Passwords(logger);
            loginPage.validateAndClickSubmitButton(logger);
            loginPage.updateTheNewPasswordInPropertyFile(isMatched, newPassword);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies new password \"(.*)\" has been updated in database for user \"(.*)\"$")
    public void userVerifiesNewPasswordHasBeenUpdatedInDatabase(String newPassword, String logonID) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.verifyNewPasswordUpdatedInDB(logger, PropUtils.getPropValue(baseUtils.inputProp, newPassword), PropUtils.getPropValue(baseUtils.inputProp, logonID));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies the displayed message \"(.*)\" along with username \"(.*)\" in generated email with search text \"(.*)\"$")
    public void userVerifiesTheDisplayedMessageInGeneratedEmail(String message, String username, String searchText) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            gmailPage.validateMessageInExpiryChangePasswordEmail(logger, message, PropUtils.getPropValue(baseUtils.inputProp, username), searchText);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter valid userName \"(.*)\" and wrong password \"(.*)\" and click on login button for \"(.*)\"$")
    public void userEnterValidUserNameAndWrongPasswordAndClickLoginButton(String userName, String passWord, String maxTimesValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select ar.MAX_PASSWORD_ATTEMPTS from access_roles ar\n" +
                    "left join access_role_clients arc on arc.access_role_oid = ar.access_role_oid\n" +
                    "left join user_roles ur on ur.access_role_oid = ar.access_role_oid\n" +
                    "left join users u on u.user_oid = ur.user_oid\n" +
                    "where u.logon_id = '"+PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile),userName)
                    +"' and arc.client_mid = "+PropUtils.getPropValue(PropUtils.getProps(baseUtils.testDataFile),"ClientMID");
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            String maxAttempts = queryResults.get("MAX_PASSWORD_ATTEMPTS");
            logger.log("Max attempts are " + maxAttempts);
            int value;
            if (maxTimesValue.equals("MaxTimes")) {
                value = Integer.parseInt(maxAttempts);
            } else if (maxTimesValue.equals("MaxTimes-1")) {
                value = Integer.parseInt(maxAttempts) - 1;
            } else {
                value = Integer.parseInt(maxAttempts) + 1;
            }
            logger.log("Max password attempts : " + maxAttempts);
            int failedLogonCount = loginPage.getFailedLogOnCount(PropUtils.getPropValue(baseUtils.inputProp, userName));
            logger.log("failed logon count is :" + failedLogonCount);
            if ((failedLogonCount == Integer.parseInt(maxAttempts)) || (failedLogonCount == (Integer.parseInt(maxAttempts)) - 1)) {
                failedLogonCount = loginPage.unlockAccountIfItIsLocked(userName);
                commonPage.sleepForFewSeconds(1);
            }
            for (int i = failedLogonCount; i <= value; i++) {
                if (i == value) {
                    break;
                }
                commonPage.sleepForFewSeconds(2);
                loginPage.enterUserName(logger, PropUtils.getPropValue(baseUtils.inputProp, userName));
                commonPage.sleepForFewSeconds(2);
                loginPage.enterPassWord(logger, passWord);
                commonPage.sleepForFewSeconds(2);
                loginPage.clickLogin(logger);
                commonPage.sleepForFewSeconds(2);
            }
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User unlock the account for user \"(.*)\" if it is locked$")
    public void userUnlockTheAccountIfItIsLocked(String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            //Changing the password created date if it is about expire or expired
            commonUtils.setPasswordExpiryDate("10", PropUtils.getPropValue(baseUtils.inputProp, userName));
            String query = "select ar.MAX_PASSWORD_ATTEMPTS from access_roles ar\n" +
                    "left join access_role_clients arc on arc.access_role_oid = ar.access_role_oid\n" +
                    "left join user_roles ur on ur.access_role_oid = ar.access_role_oid\n" +
                    "left join users u on u.user_oid = ur.user_oid\n" +
                    "where u.logon_id = '"+PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile),userName)
                    +"' and arc.client_mid = "+PropUtils.getPropValue(PropUtils.getProps(baseUtils.testDataFile),"ClientMID");
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            String maxAttempts = queryResults.get("MAX_PASSWORD_ATTEMPTS");
            logger.log("Max password attempts : " + maxAttempts);
            int failedLogonCount = loginPage.getFailedLogOnCount(PropUtils.getPropValue(baseUtils.inputProp, userName));
            logger.log("failed logon count is :" + failedLogonCount);
            if (!maxAttempts.equals("0")) {
                if ((failedLogonCount == Integer.parseInt(maxAttempts)) || (failedLogonCount == (Integer.parseInt(maxAttempts)) - 1)) {
                    failedLogonCount = loginPage.unlockAccountIfItIsLocked(userName);
                    commonPage.sleepForFewSeconds(1);
                }
            }
            commonUtils.updateLogonStatusCID(PropUtils.getPropValue(baseUtils.inputProp, userName), 2501);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User change the password$")
    public void userChangeThePassword() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.changeThePassWord();
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies error message \"(.*)\"$")
    public void userVerifiesErrorMessageYourAccountHasBeenLocked(String errorMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.assertTwoStrings(logger, errorMessage, loginPage.getMessageHeader(logger));
//            Assert.assertEquals(errorMessage,loggerinPage.getMessageHeader(logger));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User refresh the window$")
    public void userRefreshTheWindow() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            driver.navigate().refresh();
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Given("^User update the LOGON_STATUS_CID to \"(.*)\" for user \"(.*)\"$")
    public void userUpdateTheLogonStatusCID(int logonStatusCID, String logonID) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonUtils.updateLogonStatusCID(PropUtils.getPropValue(baseUtils.inputProp, logonID), logonStatusCID);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on return to login link$")
    public void userClickOnReturnToLoginLink() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.clickReturnToLogin(logger);
            logger.log("Clicked on return to login link");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User selects languages from \"(.*)\" to \"(.*)\"$")
    public void userSelectsLanguagesFromOneLanguageToAnother(String fromLang, String toLang) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            ExcelUtils excelUtils = new ExcelUtils(driver);
            basePage.userClick(logger, By.xpath("//div[@class='hhB0V'][1]/div"));
            basePage.userTypeIntoTextField(logger, By.cssSelector("div[class='language-list']>input[id='sl_list-search-box']"), fromLang);
            basePage.userClick(logger, By.cssSelector("div[class='language_list_item']>div>b"));
            basePage.userClick(logger, By.xpath("//div[@class='hhB0V'][2]/div"));
            basePage.userTypeIntoTextField(logger, By.cssSelector("div[class='language-list']>input[id='tl_list-search-box']"), toLang);
            basePage.userClick(logger, By.cssSelector("div[class='language_list_item']>div>b"));
            excelUtils.translateAllWords(logger, 0, 1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
