package stepDefinitions.stepDefinitionsUI;

import com.beust.jcommander.JCommander;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.FooterPage;
import utilities.api.BaseUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.WebDriverInitialization;

import java.util.Properties;

public class FooterSteps {

    private WebDriver driver;
    private BasePage basePage;
    private FooterPage footerPage;
    public Scenario logger;
    private BaseUtils baseUtils;
    private DriverFactory driverFactory;

    public FooterSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        baseUtils = new BaseUtils();
        basePage = new BasePage(driver);
        footerPage = new FooterPage(driver);
    }

    @When("^User Click on the Login link in the footer section and navigate to the login page$")
    public void userClickontheLoginlinkinthefootersectionandnavigatetotheloginpage() throws Throwable {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            footerPage.clickLoginFooterLink(logger);
            footerPage.verifyLoginPageTitlePresence(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^Click on the contact us in the footer section and navigate to the contact us page$")
    public void clickonthecontactusinthefootersectionandnavigatetothecontactuspage() throws Throwable {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            footerPage.clickContactUs(logger);
            basePage.switchToNewWindow(logger, 2);
            footerPage.verifyContacUsPageTitlePresence(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }


    @When("^User Navigate window \"(.*)\" and close the window$")
    public void userNavigateWindowAndCloseIt(String window) throws Throwable {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.switchToNewWindow(logger, Integer.parseInt(window));
            driver.close();
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User Click on the Home link in the footer section$")
    public void clickOnHomeLinkOnTheFotterSection() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            footerPage.clickHome(logger);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.log("Count not wait for 2 seconds");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^Click on Term of use in the footer section and navigate to the Term of use page$")
    public void cickonTermofuseinthefootersectionandnavigatetotheTermofusepage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.switchToNewWindow(logger, 1);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.log("Count not wait for 2 seconds");
            }
            footerPage.clickTermsOfUse(logger);
            basePage.switchToNewWindow(logger, 3);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.log("Count not wait for 2 seconds");
            }
            footerPage.verifyUserTermsOfUSePageTitlePresence(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^Click on Privacy Policy in the footer section and navigate to the Privacy policy page$")
    public void clickonPrivacyPolicyinthefootersectionandnavigatetothePrivacypolicypage() throws Throwable {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.switchToNewWindow(logger, 1);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.log("Count not wait for 2 seconds");
            }
            footerPage.clickPrivacyPolicy(logger);
            basePage.switchToNewWindow(logger, 4);
            footerPage.verifyPrivacyPolicyTitlePresence(logger);
            Thread.sleep(2000);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^Click on Signup link in login page$")
    public void clickonSinupLinkInLoginPage() throws Throwable {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.switchToNewWindow(logger, 1);
            footerPage.clickSignup(logger);
            // footerPage.verifyPrivacyPolicyTitlePresence(logger);
            Thread.sleep(2000);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

}
