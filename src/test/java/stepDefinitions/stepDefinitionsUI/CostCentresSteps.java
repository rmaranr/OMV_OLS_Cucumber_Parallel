package stepDefinitions.stepDefinitionsUI;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import pages.CommonPage;
import pages.CostCentresPage;
import pages.LoginPage;
import pages.UsersPage;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.PasswordGenerator;
import utilities.ui.WebDriverInitialization;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CostCentresSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private CostCentresPage costCentresPage;
    public Scenario logger;
    private CommonPage commonPage;
    private DriverFactory driverFactory;

    public CostCentresSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        commonPage = new CommonPage(driver);
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        costCentresPage = new CostCentresPage(driver);
    }

    @Then("^User enter \"(.*)\" value in \"(.*)\" module as \"(.*)\" having length \"(.*)\" in \"(.*)\" form$")
    public void userEnterFieldValueInCorrespondingFieldBasedOnLength(String fieldName, String moduleName, String fieldValue, String length, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (fieldValue.equalsIgnoreCase("RandomAlphanumericWithFewSpecialChars")) {
                fieldValue = PasswordGenerator.generateAlphabatesWithFewSpecialChars(logger, Integer.parseInt(length), "_-");
                costCentresPage.enterValueInCorrespodingTextFieldCostCentre(logger, fieldName, fieldValue, formType, moduleName);
            } else if (fieldValue.equalsIgnoreCase("RandomAlphanumeric")) {
                fieldValue = PasswordGenerator.generateAlphaNumeric(logger, Integer.parseInt(length));
                costCentresPage.enterValueInCorrespodingTextFieldCostCentre(logger, fieldName, fieldValue, formType, moduleName);
            } else if (fieldValue.equalsIgnoreCase("Numeric")) {
                fieldValue = PasswordGenerator.generateNumeric(Integer.parseInt(length));
                costCentresPage.enterValueInCorrespodingTextFieldCostCentre(logger, fieldName, fieldValue, formType, moduleName);
            } else {
                costCentresPage.enterValueInCorrespodingTextFieldCostCentre(logger, fieldName, fieldValue, formType, moduleName);
            }
            String newValue = costCentresPage.getValueFromCorrespondingTextField(logger, fieldName);
            PropUtils.setProps(moduleName + "-" + fieldName, newValue, baseUtils.testDataFilePath);
            logger.log("Field " + fieldName + " value is " + fieldValue + " entered");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter search keywords \"(.*)\" by selecting search attribute as \"(.*)\"$")
    public void userEnterSearchKeywordsBySelectingSearchAttributeAs(String searchKeyWords, String searchAttributeName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.selectSearchAttribute(logger, searchAttributeName);
            commonPage.sleepForFewSeconds(6);
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            costCentresPage.enterSearchKeyWords(logger, PropUtils.getPropValue(testDataProperties, searchKeyWords));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter wrong search keywords \"(.*)\" by selecting search attribute as \"(.*)\"$")
    public void userEnterWrongSearchKeywordsBySelectingAttribute(String searchKeyWords, String searchAttributeName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.selectSearchAttribute(logger, searchAttributeName);
            commonPage.sleepForFewSeconds(2);
            costCentresPage.enterSearchKeyWords(logger, searchKeyWords);
            commonPage.sleepForFewSeconds(2);
            basePage.waitUntilElementLocatedOrRefreshed(By.cssSelector("div[class='view-page-list']"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter below three characters \"(.*)\" in search text box the search icon will not be enabled$")
    public void userEnterBelowThreeCharactersInSearchTextBoxTheSearchIconWillNotBeEnabed(String searchText) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.verifySearchIconStatusWhenBelowThreeCharactersEntered(logger, searchText);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("User press enter key from keyboard")
    public void userPressEnterKeyFromKeyboard() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.handleActionsClassForKeyboardActions("Enter");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on search icon$")
    public void userClickOnSearchIcon() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("skipped")) {
            costCentresPage.clickOnSearchIcon(logger);
            logger.log("Search icon has been clicked");
            commonPage.sleepForFewSeconds(10);
        } else {
            logger.log("Previous step got skipped");
        }
    }

    @When("^User click on clear search icon$")
    public void userClickOnClearSearchIcon() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.clickOnClearSearchIcon(logger);
            commonPage.sleepForFewSeconds(5);
        } else {
            logger.log("Previous step got skipped");
        }
    }

    @And("^User validate cost center data is saved in database$")
    public void userValidateCostCentreDataIsSavedInDatabase() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testDataProperties = PropUtils.getProps(baseUtils.testDataFile);
            String query = "select CUSTOMER_COST_CENTRE_CODE,DESCRIPTION,SHORT_DESCRIPTION from customer_cost_centres" +
                    " where UPPER(CUSTOMER_COST_CENTRE_CODE) like UPPER('" + PropUtils.getPropValue(testDataProperties, "costCentre-costCentre").toUpperCase() +
                    "') and customer_mid in (select customer_mid from accounts_view where customer_no='" +
                    PropUtils.getPropValue(testDataProperties, "accountNumber") + "' and client_mid in (" +
                    PropUtils.getPropValue(testDataProperties, "ClientMID") + "))";
            logger.log("Cost centre query " + query);
            Map<String, String> costCentreDetails = commonUtils.getQueryResultsOnMap(query);
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "costCentre-costCentre"), costCentreDetails.get("CUSTOMER_COST_CENTRE_CODE"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "costCentre-description"), costCentreDetails.get("DESCRIPTION"));
            basePage.assertTwoStrings(logger, PropUtils.getPropValue(testDataProperties, "costCentre-name"), costCentreDetails.get("SHORT_DESCRIPTION"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User click on Three dot icon in module \"(.*)\" based on search keywords \"(.*)\"$")
    public void userClickOnThreeDotIcon(String moduleName, String searchKeywords) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.clickThreeDotIconBasedOnSearchKeywords(logger, searchKeywords, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate searched record in the database for module \"(.*)\" based on search keywords \"(.*)\"$")
    public void userValidateSearchedRecordInTheDatabase(String moduleName, String searchKeywords) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.validateSearchedRecordWithDatabase(logger, searchKeywords, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User click on button \"(.*)\"$")
    public void userClickOnEditCostCentreButton(String button) {
        commonPage.sleepForFewSeconds(2);
        //log = test.createNode(new GherkinKeyword("When"), "User click on button "+button);
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.clickButtonUsingItsText(logger, button);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate \"(.*)\" field has value and non editable in module \"(.*)\"$")
    public void userValidateContactNameFiledHasValueAndNonEditable(String fieldName, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.validateNonEditableTextFieldValuePresence(logger, PropUtils.getPropValue(properties, moduleName + "-" + fieldName));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate message of search results section \"(.*)\"$")
    public void userValidateMessageOfSearchResultsSection(String searchResultsMessage) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.validateSearchResultsMsg(logger, searchResultsMessage);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter wrong \"(.*)\" and validate error message \"(.*)\" in cost centre module$")
    public void userEnterWrongFiledValueAndValidateCorrespondingErrorMessage(String fieldName, String errorMessage, DataTable dt) {
        List<String> values = dt.asList(String.class);
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String arrValues[] = {values.get(0)};
            if (values.get(0).contains(",")) {
                arrValues = values.get(0).split(",");
            }
            for (int i = 0; i <= arrValues.length - 1; i++) {
                costCentresPage.enterValueInCostCentreFiledAndValidateErrorMessage(logger, fieldName, arrValues[i], errorMessage);
                logger.log("Error message for User Name " + arrValues[i] + " validated");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies the \"(.*)\" page sort option Sort by Newest$")
    public void userVerifiesTheAscendingOrder(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = basePage.userGetTextFromWebElement(logger,By.xpath("//div[contains(@class,'account-number')]"));
            String accountCondition = "";
            if(!accountNumber.equalsIgnoreCase("Select account")){
                accountCondition = " mcust.customer_no = '"+accountNumber+"'";
            }
            PropUtils.setProps("accountNumber", String.valueOf(accountNumber), baseUtils.testDataFilePath);
            commonPage.sleepForFewSeconds(5);
            usersPage.selectAscendingSort(logger);
            commonPage.sleepForFewSeconds(2);
            String query = "";
            if (moduleName.equalsIgnoreCase("users")) {
                query = "select ar.description as user_role,c.description as user_status, u.name, u.email_address,u.logon_id as userId,\n" +
                        "u.mobile_phone, u.other_phone from users u\n" +
                        "left join constants c on c.constant_oid = u.logon_status_cid \n" +
                        "left join user_roles ur on ur.user_oid = u.user_oid\n" +
                        "left join access_roles ar on ar.access_role_oid = ur.access_role_oid\n" +
                        "left join user_members um on um.user_oid = u.user_oid\n" +
                        "left join m_customers mcust on mcust.customer_mid = um.member_oid\n"+
                        "where "+ accountCondition + " order by initcap (name)  Asc";
            } else if (moduleName.equalsIgnoreCase("cost centres")) {
                query = "select CUSTOMER_COST_CENTRE_CODE as name from customer_cost_centres ccc\n" +
                        "inner join m_customers mcust on mcust.customer_mid = ccc.customer_mid\n" +
                        "where mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+" and "+accountCondition;
//                query = "select CUSTOMER_COST_CENTRE_CODE as name from customer_cost_centres where customer_mid in (select customer_mid from accounts_view where " + accountNumber + " and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+") order by initcap (name)  Asc";
            } else if (moduleName.equalsIgnoreCase("contacts")) {
                query = "SELECT contact_name as Name FROM contacts\n" +
                        "WHERE member_oid=(select customer_mid from m_customers where customer_no= " + accountNumber + " and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")\n" +
                        "ORDER BY initcap (Name) ASC";
            } else if (moduleName.equalsIgnoreCase("Drivers")) {
                query = "select Driver_name as Name from drivers where customer_mid in(select CUSTOMER_MID from accounts_view where account_no=" + accountNumber + "\n" +
                        "and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")order by initcap (Driver_name) asc";
            } else if (moduleName.equalsIgnoreCase("Vehicles")) {
                query = "select Vehicle_ID as Name from vehicles where customer_mid in(select CUSTOMER_MID from accounts_view where account_no=" + accountNumber + "\n" +
                        "and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")order by initcap (DESCRIPTION) Asc";
            }
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            String dbexpList = " ";
            for (int i = 0; i < queryResults.size() - 1; i++) {
                dbexpList = queryResults.get(i).get("NAME");
                logger.log("The Values are:" + dbexpList);
            }
            if (dbexpList.equals(usersPage.getNameOfRecords(logger))) {
                logger.log("The list is in ascending order");
            } else {
                logger.log("The list is not in ascending order");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User scroll down in the page$")
    public void userScrollDownInThePage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.scrollDown(logger);
        }else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verifies the \"(.*)\" page sort option Sort by Oldest$")
    public void userVerifiesTheDescendingOrder(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            String accountCondition = "";
            if(!accountNumber.equalsIgnoreCase("Select account")){
                accountCondition = " mcust.customer_no = '"+accountNumber+"'";
            }
            PropUtils.setProps("accountNumber", String.valueOf(accountNumber), baseUtils.testDataFilePath);
            usersPage.selectDescendingSort(logger);
            usersPage.scrollDown(logger);
            commonPage.sleepForFewSeconds(2);
            String query = "";
            if (moduleName.equalsIgnoreCase("users")) {
                query = "select ar.description as user_role,c.description as user_status, u.name, u.email_address,u.logon_id as userId,\n" +
                        "u.mobile_phone, u.other_phone from users u\n" +
                        "left join constants c on c.constant_oid = u.logon_status_cid \n" +
                        "left join user_roles ur on ur.user_oid = u.user_oid\n" +
                        "left join access_roles ar on ar.access_role_oid = ur.access_role_oid\n" +
                        "left join user_members um on um.user_oid = u.user_oid\n" +
                        "left join m_customers mcust on mcust.customer_mid = um.member_oid\n"+
                        "where "+ accountCondition + " order by initcap (name)  Asc";
            } else if (moduleName.equalsIgnoreCase("cost centres")) {
                query = "select CUSTOMER_COST_CENTRE_CODE as name from customer_cost_centres ccc\n" +
                        "inner join m_customers mcust on mcust.customer_mid = ccc.customer_mid\n" +
                        "where mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+" and "+accountCondition;
//                query = "select CUSTOMER_COST_CENTRE_CODE as name from customer_cost_centres where customer_mid " +
//                        "in (select customer_mid from accounts_view where customer_no =" + accountNumber + "" +
//                        " and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+") order by initcap (name)  Desc";
            } else if (moduleName.equalsIgnoreCase("contacts")) {
                query = "SELECT contact_name as Name FROM contacts\n" +
                        "WHERE member_oid=(select customer_mid from m_customers where customer_no= " + accountNumber + " and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")\n" +
                        "ORDER BY initcap (Name) Desc";
            } else if (moduleName.equalsIgnoreCase("Drivers")) {
                query = "select Driver_name as Name from drivers where customer_mid in(select CUSTOMER_MID from accounts_view where account_no=" + accountNumber + "\n" +
                        "and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")order by initcap (Driver_name) Desc";
            } else if (moduleName.equalsIgnoreCase("Vehicles")) {
                query = "select  vehicle_ID as Name from vehicles where customer_mid in(select CUSTOMER_MID from accounts_view where account_no=" + accountNumber + "\n" +
                        "and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")order by initcap (DESCRIPTION) Desc";
            }
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            String dbexpList = "";
            for (int i = 0; i < queryResults.size() - 1; i++) {
                dbexpList = queryResults.get(i).get("NAME");
                logger.log("The Values are:" + dbexpList);
            }
            if (dbexpList.equals(usersPage.getNameOfRecords(logger))) {
                logger.log("The list is in Descending order");
            } else {
                logger.log("The list is not in Descending order");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("User selects a random search attribute and verify whether the selected attribute is highlighted")
    public void userSelectsARandomSearchAttributeA() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            costCentresPage.selectRandomSearchAttribute(logger);
            commonPage.sleepForFewSeconds(2);
            Actions actions = new Actions(driver);
            actions.moveToElement(basePage.getWebElementUsingLocator(logger, By.xpath("//span[contains(text(),'Home')]"))).click().perform();
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @When("^User validate no of cards displayed based on the cost centre for user type \"(.*)\"$")
    public void userValidateNoOfCardsDisplayedBasedOnTheCostCentreSearched(String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            if(userType.contains("OMV")){
                query = "select count(*) from cards c\n" +
                        "left join cost_centres cc on cc.card_oid = c.card_oid\n" +
                        "left join customer_cost_centres ccc on ccc.CUSTOMER_COST_CENTRE_OID = cc.CUSTOMER_COST_CENTRE_OID\n" +
                        "left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                        "where mcust.customer_no = '"+PropUtils.getPropValue(properties,"costCentreHavingAccountNumber")+
                        "' and ccc.customer_cost_centre_code = '"+PropUtils.getPropValue(properties,"costCentreCode")+"'\n" +
                        "and mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            }else if(userType.equalsIgnoreCase("OLS")){
                query = "select count(*) from cards c\n" +
                        "left join cost_centres cc on cc.card_oid = c.card_oid\n" +
                        "left join customer_cost_centres ccc on ccc.CUSTOMER_COST_CENTRE_OID = cc.CUSTOMER_COST_CENTRE_OID\n" +
                        "left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                        "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                        "left join users u on u.user_oid = um.user_oid\n"+
                        "where mcust.customer_no = '"+PropUtils.getPropValue(properties,"costCentreHavingAccountNumber")+
                        "' and ccc.customer_cost_centre_code = '"+PropUtils.getPropValue(properties,"costCentreCode")+"'\n" +
                        "and mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+"\n"+
                        "and u.logon_id = '"+PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile),"currentUserName")+"'";
            }
            logger.log(query);
            Map<String,String>queryResults = commonUtils.getQueryResultsOnMap(query);
            if(queryResults.get("COUNT(*)").equalsIgnoreCase("0")){
                if(basePage.getStatusOfElement(By.xpath("//div[contains(text(),'No results found,')]"))){
                    logger.log("No cards are linked to the searched cost centre '"+PropUtils.getPropValue(properties,"costCentreCode")+"' hence 'No results found' message displayed as expected");
                }
            }else {
                if(queryResults.get("COUNT(*)").equalsIgnoreCase("1")){
//                    commonPage.assertTwoStrings(logger, queryResults.get("COUNT(*)") + " card", basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='view-page-list']")), "No of cards based on cost centre");
                }else {
//                    commonPage.assertTwoStrings(logger, queryResults.get("COUNT(*)") + " cards", basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='view-page-list']")), "No of cards based on cost centre");
                }
            }
            if(userType.equalsIgnoreCase("OMV")) {
//                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "costCentreHavingAccountNumber") + ", " + "All Issuing country" + ", " + PropUtils.getPropValue(properties, "costCentreCode"), basePage.userGetTextFromWebElement(logger, By.cssSelector("span[class='account-text']")), "Searched text in Cards module based on cost centre");
            }else{
                if(basePage.userGetTextFromWebElement(logger, By.cssSelector("span[class='account-text']")).contains(PropUtils.getPropValue(properties, "costCentreCode"))){
                    logger.log(PropUtils.getPropValue(properties, "costCentreCode") +" is populated in search bar as expected.");
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @When("^User get an account number which has cost centres for user type \"(.*)\"$")
    public void userGetAnAccountNumberWhichHasCostCentredBasedOnUserType(String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            if(userType.equalsIgnoreCase("OMV")){
                query = "select distinct mcust.customer_no,mc.name as issuingCountry,ccc.customer_cost_centre_code,ccc.description as description,ccc.short_description as name from customer_cost_centres ccc\n" +
                        "left join m_customers mcust on mcust.customer_mid = ccc.customer_mid\n" +
                        "left join m_clients mc on mc.client_mid = mcust.client_mid\n"+
                        "where mc.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+" and rownum<=100";
            }else if(userType.equalsIgnoreCase("OLS")){
                query = "select distinct mcust.customer_no,mc.name as issuingCountry,ccc.customer_cost_centre_code,ccc.description as description,ccc.short_description as name from customer_cost_centres ccc\n" +
                        "left join m_customers mcust on mcust.customer_mid = ccc.customer_mid\n" +
                        "left join user_members um on um.member_oid = mcust.customer_mid\n" +
                        "left join users u on u.user_oid = um.user_oid\n" +
                        "where u.logon_id = '"+PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile),"currentUserName")+"' and rownum<=100";
            }
            Map<String, String>queryResults = commonUtils.getQueryResultsOnMap(query);
            PropUtils.setProps("costCentreHavingAccountNumber",queryResults.get("CUSTOMER_NO"),baseUtils.testDataFilePath);
            PropUtils.setProps("costCentreCode",queryResults.get("CUSTOMER_COST_CENTRE_CODE"),baseUtils.testDataFilePath);
            PropUtils.setProps("name",queryResults.get("NAME"),baseUtils.testDataFilePath);
            PropUtils.setProps("description",queryResults.get("DESCRIPTION"),baseUtils.testDataFilePath);
            PropUtils.setProps("costCentreHavingIssuingCountry",queryResults.get("ISSUINGCOUNTRY"),baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
