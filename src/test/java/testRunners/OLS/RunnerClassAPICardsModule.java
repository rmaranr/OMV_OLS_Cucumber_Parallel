package testRunners.OLS;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.ui.WebDriverInitialization;

@CucumberOptions(features = { "classpath:apifeatures//OLSCardsModule.feature" },
        glue = {"stepDefinitions/stepDefinitionsAPI"}
        ,plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
//@CucumberOptions(features = { "config//resources//apifeatures" }, glue = { "stepDefintions.stepDefinitionsAPI" }
//, tags = { "@OLSSearchCards" }
//, tags = { "@OLSGetCardDetails" }
// ,tags = { "@OLSOrderCardDetails" }
// ,tags = { "@OLSDefaultCardCtrlProf" }
// ,tags = { "@OLSProductRestriction" }
// ,tags = { "@OLSEditCardDetails" }
// ,tags = { "@OLSCardsChangePin" }
// ,tags = { "@OLSCardsReissuePin" }
// ,tags = { "@OLSEditCardControlProfiles" }
//,tags = { "@OLSOrderCardDetails,@OLSEditCardControlProfiles,@OLSEditCardDetails" }
// ,tags = { "@OLSCardsChangePin,@OLSCardsReissuePin" }
)

@Test
public class RunnerClassAPICardsModule extends AbstractTestNGCucumberTests {
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
