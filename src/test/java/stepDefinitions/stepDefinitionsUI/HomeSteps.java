package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.TransactionNotFoundException;
import utilities.ui.WebDriverInitialization;

import java.util.Map;
import java.util.Properties;

public class HomeSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private GmailPage gmailPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private HomePage homePage;
    private UsersPage usersPage;
    private CommonPage commonPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public HomeSteps(Hooks hooks, DriverFactory driverFactory) {
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
    }

    @Then("^User verifies weather user badge contains first letter of username \"(.*)\"$")
    public void userVerifyWeatherUserBadgeContainsFirstLetterOfUserName(String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyFirstLetterOfUserBadge(logger, userName);
            logger.log("User badge contains first letter of username '" + PropUtils.getPropValue(baseUtils.inputProp, userName) + "' in Capital letter");
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on user badge$")
    public void userClickOnUserBadge() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.clickUserBadgeIcon(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies presence of Edit profile option$")
    public void userVerifiesPresenceOfMyProfileOption() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyPresenceOfEditProfile(logger);
//            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies presence of Change password option$")
    public void userVerifiesPresenceOfChangePasswordOption() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyPresenceOfChangePassword(logger);
//            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on change password option from user badge$")
    public void userClickOnChangePasswordOption() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.clickOnChangePasswordFromUserBadge(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies presence of Help option$")
    public void userVerifiesPresenceOfHelpOption() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyPresenceOfHelp(logger);
//            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on Log out button$")
    public void userClickOnLogOutButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.clickOnLogOut(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter current password \"(.*)\"$")
    public void userEnterUserNameAndPassWord(String passWord) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            loginPage.enterPassWord(logger, PropUtils.getPropValue(inputProperties, passWord));
            logger.log("PassWord: '" + (PropUtils.getPropValue(inputProperties, passWord) + "' is entered successfully"));

            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies the Password Success message \"(.*)\"$")
    public void userVerifiesThePasswordExpiredMessageHeader(String messageHeader) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.validateMessageHeader(logger, messageHeader);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the presence of \"(.*)\" \"(.*)\"$")
    public void userVerifyTheMenuPresentOrNot(String menuOrSubmenu, String menuName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyMenuPresence(logger, menuName, menuOrSubmenu);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on the \"(.*)\" \"(.*)\"$")
    public void userClickOnTheMenu(String menuOrSubmenu, String menuName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(1);
            homePage.clickMenu(logger, menuName, menuOrSubmenu);
            logger.log("Clicked on the " + menuOrSubmenu + " '" + menuName + "'");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter new password and confirm new password which matches the old 6 passwords used$")
    public void userEnterNewPassword() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String newPassword = loginPage.getSpecificPasswordFromOldUsedPwds(0);
            loginPage.enterNewPassword(logger, newPassword);
            loginPage.enterConfirmNewPassword(logger, newPassword);
            if(newPassword.equalsIgnoreCase("")){
                PropUtils.setProps("testStatus","Skipped",baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason","There is no recent password hence skipping this test case",baseUtils.testDataFilePath);
            }
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validates entered new password, confirm new password and click on submit button")
    public void userClickOnSubmitButton() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            loginPage.validateAndClickSubmitButton(logger);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate New Password error message \"(.*)\"")
    public void userValidateNewPasswordErrorMessage(String errorMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyNewPasswordErrMsgForLastSixPwdMatches(logger, errorMessage);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate Current Password error message \"(.*)\"")
    public void userValidateCurrentPasswordErrorMessage(String errorMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyCurrentPasswordErrMsgForLastSixPwdDoesNotMatch(logger, errorMessage);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate Confirm new password error message \"(.*)\"")
    public void userValidateConfirmNewPasswordErrorMessage(String errorMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.verifyConfirmNewPasswordErrMsgForPwdDoesNotMatch(logger, errorMessage);
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User verifies whether the list of accounts for \"(.*)\" is matching with db$")
    public void userVerifiesWhetherTheListOfAccountsForIsMatchingWithDb(String UserName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
            String query = "select count (*) from user_members where " +
                    "user_oid =(select user_oid from users where logon_id ='" + PropUtils.getPropValue(inputProperties, UserName) + "')";
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            String expectedCountOfRecords = queryResults.get("COUNT(*)");
            String actualCountOfRecords = homePage.noOfAccountInUsers(logger, UserName);
            basePage.assertTwoStrings(logger, expectedCountOfRecords, actualCountOfRecords);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select a random account from account picker associated with the \"(.*)\"$")
    public void userSelectAnotherAccountFromAccountPicker(String UserName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            homePage.clickOnRandomAccounts(logger, UserName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verifies whether the user is in the Home Page")
    public void userVerifiesWhetherTheUserIsInTheHomePage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(2);
            homePage.verifyUserIsInHomepage(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User navigate to url \"(.*)\"$")
    public void userNavigateToUrl(String url) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            driver.navigate().to(PropUtils.getPropValue(baseUtils.inputProp, url));
//            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
