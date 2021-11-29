package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.ExcelUtils;
import utilities.ui.WebDriverInitialization;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PricingSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private ReportsPage reportsPage;
    private CommonPage commonPage;
    List<Map<String, String>> queryResults;
    public Scenario logger;
    private PricingPage pricingPage;
    private DriverFactory driverFactory;

    public PricingSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        pricingPage = new PricingPage(driver);
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        reportsPage = new ReportsPage(driver);
        commonPage = new CommonPage(driver);
    }

    @Then("^User select grouping and value in fee profiles in \"(.*)\" form to \"(.*)\" public profile$")
    public void userSelectGroupAndValueInFeeProfiles(String form, String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (form.equalsIgnoreCase("add")) {
                if (action.equalsIgnoreCase("createNew")) {
                    basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='grouping']>mat-select"));
                    basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                    String query = "";
                    List<Map<String, String>> queryResults = new ArrayList<>();
                    if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grouping']>mat-select"), "ng-reflect-model").equalsIgnoreCase("applicationType")) {
                        query = "select at.description from application_types at\n" +
                                "left join card_programs cp on cp.card_program_oid = at.card_program_oid\n" +
                                "left join m_clients mc on mc.client_mid = cp.client_mid\n" +
                                "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
                        queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    } else if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grouping']>mat-select"), "ng-reflect-model").equalsIgnoreCase("clientName")) {
                        String clientCountry = "select name as DESCRIPTION from m_clients where client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
                        queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(clientCountry);
                        commonPage.assertTwoStrings(logger, queryResults.get(0).get("DESCRIPTION"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-model"), "Selected value from 'Group Value' drop down");
                        commonPage.assertTwoStrings(logger, "true", commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-is-disabled"), "Selected value from 'Group Value' drop down");
                    } else if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grouping']>mat-select"), "ng-reflect-model").equalsIgnoreCase("Card program")) {
                        query = "select description from card_programs where client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
                        queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    }
                    logger.log(query);
                    if (queryResults.size() > 1) {
                        basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"));
                        basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                    } else {
                        commonPage.assertTwoStrings(logger, queryResults.get(0).get("DESCRIPTION"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-model"), "Selected value from 'Group Value' drop down");
                        commonPage.assertTwoStrings(logger, "true", commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-is-disabled"), "Selected value from 'Group Value' drop down");
                    }
                } else if (action.equalsIgnoreCase("validateErrorFor")) {
                    basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='grouping']>mat-select"));
                    if (!PropUtils.getPropValue(properties, "applicationType").equalsIgnoreCase("")) {
                        commonPage.clickButtonUsingSpan(logger, "Segment");
                        if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-disabled").equalsIgnoreCase("true")) {
                            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "applicationType"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-model"), "Selected Application type");
                        } else {
                            basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"));
                            commonPage.clickButtonUsingSpan(logger, PropUtils.getPropValue(properties, "applicationType"));
                        }
                    } else if (!PropUtils.getPropValue(properties, "country").equalsIgnoreCase("")) {
                        commonPage.clickButtonUsingSpan(logger, "Issuing country");
                        if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-disabled").equalsIgnoreCase("true")) {
                            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "country"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-model"), "Selected Country");
                        } else {
                            basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"));
                            commonPage.clickButtonUsingSpan(logger, PropUtils.getPropValue(properties, "country"));
                        }
                    } else if (!PropUtils.getPropValue(properties, "cardProgram").equalsIgnoreCase("")) {
                        commonPage.clickButtonUsingSpan(logger, "Card program");
                        if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-disabled").equalsIgnoreCase("true")) {
                            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cardProgram"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"), "ng-reflect-model"), "Selected card Program");
                        } else {
                            basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='grpValue']>mat-select"));
                            commonPage.clickButtonUsingSpan(logger, PropUtils.getPropValue(properties, "cardProgram"));
                        }
                    }
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
        commonPage.sleepForFewSeconds(2);
    }

    @And("^User select no of records \"(.*)\" \"(.*)\" fees based on client in \"(.*)\" form pinning an account status \"(.*)\"$")
    public void userSelectNoOfRecordsBasedOnClient(String noOfRecords, String issueType, String form, String isAccountPinned) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            pricingPage.selectCardOrAccountFeeTypeValues(logger, noOfRecords, issueType, form);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User get an account number which \"(.*)\" eligible to create private \"(.*)\" profile$")
    public void userGetAnAccountNumberWhichIsEligibleToCreatePrivateFeeProfile(String isEligibleOrNot, String feeType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            pricingPage.getCustomerNoWhichIsEligibleToCreatePrivateFeeProfile(logger, feeType, isEligibleOrNot);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User get existing \"(.*)\" fee profile details of fee type \"(.*)\"$")
    public void userGetAnAccountNoAndFeeNameToValidateErrorMessage(String feeCategory, String feeType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            pricingPage.getCustomerNoAndFeeDescription(logger, feeCategory, feeType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User get account no based on the search criteria of fee profile$")
    public void userGetAccountNoBasedOnTheSearchCriteriaOfFeeProfile() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            if (!PropUtils.getPropValue(properties, "applicationType").equalsIgnoreCase("")) {
                query = "select mcust.customer_no from application_types apt\n" +
                        "inner join applications ap on ap.application_type_oid = apt.application_type_oid\n" +
                        "inner join m_customers mcust on mcust.application_oid = ap.application_oid\n" +
                        "where mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+
                        " and apt.description = '"+PropUtils.getPropValue(properties, "applicationType")+"'";
            } else if (!PropUtils.getPropValue(properties, "country").equalsIgnoreCase("")) {
                query = "select mcust.customer_no from m_customers mcust\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mc.name = '"+PropUtils.getPropValue(properties, "country")+"'";
            } else if (!PropUtils.getPropValue(properties, "cardProgram").equalsIgnoreCase("")) {
                query = "select mcust.customer_no from m_customers mcust\n" +
                        "inner join card_programs cp on cp.card_program_oid = mcust.card_program_oid\n" +
                        "where mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")
                        +" and cp.description = '"+PropUtils.getPropValue(properties, "cardProgram")+"'";
            }
            List<Map<String, String>>queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if(queryResults.size()>=1){
                PropUtils.setProps("commonAccountNo",queryResults.get(0).get("CUSTOMER_NO"),baseUtils.testDataFilePath);
            }else{
                PropUtils.setProps("testStatus","Skipped",baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason","No customer is availabel to execute this scenario",baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select fee profile search criteria for fee category \"(.*)\" based on existing profile and click on search$")
    public void userGetAnAccountNoAndFeeNameToValidateErrorMessage(String feeCategory) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if(feeCategory.equalsIgnoreCase("public")){
                pricingPage.enterSearchCriteriaForPublicFeeProfiles(logger);
            }
            Actions actions = new Actions(driver);
            actions.moveToElement(basePage.getWebElementUsingLocator(logger, By.xpath("//span[contains(text(),'Search')]"))).click().perform();
            commonPage.sleepForFewSeconds(5);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User validate the assigned \"(.*)\" fee profile to a customer \"(.*)\"$")
    public void userValidateTheAssignedFeeProfileToACustomer(String feeType, String customerNo) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            List<Map<String, String>>queryResults = new ArrayList<>();
            if(feeType.equalsIgnoreCase("Card")){
                query = "select ccp.FEE_PROFILE_OID from customer_card_profiles ccp\n" +
                        "inner join m_customers mcust on mcust.customer_mid = ccp.customer_mid\n" +
                        "where mcust.customer_no = '"+PropUtils.getPropValue(properties,"commonAccountNo")+
                        "' and mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            }else if(feeType.equalsIgnoreCase("Account")){
                    query = "select a.fee_profile_oid from accounts a\n" +
                            "inner join m_customers mcust on mcust.customer_no = a.account_no\n" +
                            "where mcust.customer_no = '"+PropUtils.getPropValue(properties,"commonAccountNo")
                            +"' and mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            }
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            commonPage.assertTwoStrings(logger,PropUtils.getPropValue(properties,"feeProfileOid"),queryResults.get(0).get("FEE_PROFILE_OID"),"Assigned Fee Profile");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
