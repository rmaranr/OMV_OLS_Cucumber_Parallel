package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.WebDriverInitialization;

import java.util.List;
import java.util.Map;

public class OMVTransactionStepDefinitions{

    private WebDriver driver;
    private LoginPage loginPage;
    private GmailPage gmailPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private UsersPage usersPage;
    private CostCentresPage costCentresPage;
    private CommonDBUtils commonUtils;
    private AccountInfoPage accountInfoPage;
    private CommonPage commonPage;
    private OMVContextPage omvContextPage;
    private Map<String, String> queryResults = null;
    private List<Map<String, String>> allRowsOfQueryResults = null;
    private OMVTransactionsPage omvTransactionsPage = null;
    private Map<String, String> transactionRecordBasedOnTransactionType = null;
    public Scenario logger;
    private DriverFactory driverFactory;

    public OMVTransactionStepDefinitions(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        gmailPage = new GmailPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        costCentresPage = new CostCentresPage(driver);
        accountInfoPage = new AccountInfoPage(driver);
        commonPage = new CommonPage(driver);
        omvContextPage = new OMVContextPage(driver);
        omvTransactionsPage = new OMVTransactionsPage(driver);
    }

}
