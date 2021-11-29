package testRunners.OMV;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.ui.WebDriverInitialization;

@CucumberOptions(features = { "classpath:omvapifeatures//OMVDriverAndVehiclesAndCostCetres.feature" },
        glue = {"stepDefinitions/stepDefinitionsAPI"}
        ,plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}

//, tags = "@VehiclesDetailsForCSR"

)

@Test
public class OMVRunnerClassAPIDriversVehiclesAndCostCentresModule extends AbstractTestNGCucumberTests {
    WebDriverInitialization webDriverInitialization = new WebDriverInitialization();
    @BeforeClass
    @Parameters({"UserName", "Password"})
    public void beforeClass(String userName, String passWord, ITestContext ctx) {
        webDriverInitialization.beforeClass(userName,passWord,ctx);
    }
    @AfterClass
    public void afterClass(ITestContext ctx){
        webDriverInitialization.afterClass(ctx);
    }
}
