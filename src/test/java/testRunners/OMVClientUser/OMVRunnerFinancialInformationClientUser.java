package testRunners.OMVClientUser;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utilities.ui.WebDriverInitialization;

@CucumberOptions(
        features = {"classpath:omvuifeaturesclientuser//OMV-Financial-Information-ClientUser.feature"}
        , glue = {"stepDefinitions"}, monochrome = true
        , plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        , tags = "@UI-FinancialInformation"
)

public class OMVRunnerFinancialInformationClientUser extends AbstractTestNGCucumberTests {
    WebDriverInitialization webDriverInitialization = new WebDriverInitialization();

    @BeforeClass
    @Parameters({"UserName", "Password"})
    public void beforeClass(String userName, String passWord, ITestContext ctx) {
        webDriverInitialization.beforeClass(userName, passWord, ctx);
    }

    @AfterClass
    public void afterClass(ITestContext ctx) {
        webDriverInitialization.afterClass(ctx);
    }
}