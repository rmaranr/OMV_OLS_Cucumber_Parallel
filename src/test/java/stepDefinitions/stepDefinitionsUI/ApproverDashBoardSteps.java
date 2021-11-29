package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.WebDriverInitialization;

import java.util.Properties;

public class ApproverDashBoardSteps {

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
    private ApproverDashBoardPage approverDashBoardPage;
    private DriverFactory driverFactory;

    public ApproverDashBoardSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        costCentresPage = new CostCentresPage(driver);
        accountInfoPage = new AccountInfoPage(driver);
        commonPage = new CommonPage(driver);
        approverDashBoardPage = new ApproverDashBoardPage(driver);
    }

    @Then("^User get \"(.*)\" record which \"(.*)\" created by logged in user \"(.*)\"$")
    public void userGetPendingRecordBasedOnLoggedInUser(String recordType, String IsCreated, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }


    @When("^User click three dot icon of latest pending record of \"(.*)\", which \"(.*)\" created by logged in user \"(.*)\"$")
    public void userClickThreeDotIconOfLatestPendingRecordOfWhichCreatedByLoggedInUser(String workFlowName, String flag, String user) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            approverDashBoardPage.clickSortByHighest();
            usersPage.scrollDown(logger);
//            if (!workFlowName.equals("Customer Hierarchy")) {
                approverDashBoardPage.clickElipsisInDashBoard(logger,workFlowName, flag, user);
//            } else {
//                approverDashBoardPage.clickElipsisInDashBoard(logger,flag, user);
//            }

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate data populated for record \"(.*)\"$")
    public void userValidateDataPopulatedForRecord(String workFlow) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            approverDashBoardPage.verifyTaxRecordData(logger,workFlow);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate \"(.*)\" record is not present in ui$")
    public void userValidateRecordIsNotPresentInUi(String workFlowType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            approverDashBoardPage.isRecordNotExist(workFlowType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User update flags \"(.*)\" for logged in user \"(.*)\"$")
    public void userUpdateFlagsForLoggedInUser(String flags, String user) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        Properties inputProperties = PropUtils.getProps(baseUtils.inputDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            approverDashBoardPage.setFlagsWithQuery(flags, PropUtils.getPropValue(inputProperties, user));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }


}
