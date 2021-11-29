package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.DriverFactory;
import utilities.ui.TransactionNotFoundException;
import utilities.ui.WebDriverInitialization;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class OMVContextPickerSteps {
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
    private OMVTransactionsPage omvTransactionsPage;
    private Map<String, String> transactionRecordBasedOnTransactionType = null;
    private ContactsPage contactsPage;
    private HierarchyPage hierarchyPage;
    private ReportSetupPage reportSetupPage;
    public Scenario logger;
    private DriverFactory driverFactory;

    public OMVContextPickerSteps(Hooks hooks, DriverFactory driverFactory) {
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
        contactsPage = new ContactsPage(driver);
        hierarchyPage = new HierarchyPage(driver);
        reportSetupPage = new ReportSetupPage(driver);
    }

    @When("^User select \"(.*)\" option using tag name \"(.*)\" from context picker if logged in user \"(.*)\" has access to more than one customer$")
    public void userValidationPresentOfField(String fieldName, String tagName, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
//                List<Map<String, String>> allRowsOfQueryResults = omvContextPage.getAllCustomersBasedOnLoggedInUser(userName);
//                if (allRowsOfQueryResults.size() > 1) {
            commonPage.clickButtonUsingSpecificTagName(logger, fieldName, tagName);
//                } else {
//                    logger.log("Only one customer is available for logged in user hence select account option is not available");
//                    PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
//                }
        } else {
            logger.log("scenario is getting skipped");
        }
    }

    @Then("^User select or enter value from a field \"(.*)\" based on its behavior \"(.*)\" for user \"(.*)\" in module \"(.*)\"$")
    public void userSelectOrEnterValueFromAField(String fieldName, String fieldBehavior, String userName, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.enterValueInCorrespondingFieldBasedOnItsType(logger, fieldBehavior, fieldName, moduleName, PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), userName));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get the cardProduct and cardOffer combination which are eligible to create card control profile$")
    public void userGetThecardProductAndCardOfferWhichAreEligibleToCreateCardControlProfile() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
//                omvContextPage.enterValueInCorrespondingFieldBasedOnItsType(logger, fieldBehavior, fieldName, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter accountnumber which has records of \"(.*)\"$")
    public void userSelectOrEnterValueFromAField(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.enterGetAccountNumberWhichHasRecordsForSpecificModule(logger, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate remaining search bar field values \"(.*)\" are auto populated based on searched card number$")
    public void userValidateRemainingSearchBarFieldValuesAreAutoPopulatedBasedOnSearchedCardNumber(String searchBarFieldNames) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Actions actions = new Actions(driver);
            actions.moveToElement(basePage.getWebElementUsingLocator(logger, By.xpath("//div[contains(text(),'Searching by')]"))).click().perform();
            actions.moveToElement(basePage.getWebElementUsingLocator(logger, By.xpath("//span[contains(text(),'Searching by')]"))).click().perform();
            commonPage.sleepForFewSeconds(2);
            logger.log("Pressed out side of the card number text box");
            String[] searchFieldNames = searchBarFieldNames.split(",");
            commonPage.sleepForFewSeconds(1);
            queryResults = omvContextPage.getCardDetailsInMapBasedOnLoggedInUser(logger, commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='cardNumber']>input"), "ng-reflect-model"));
            commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[0].toUpperCase()), commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='" + searchFieldNames[0] + "']>input"), "ng-reflect-model"), searchFieldNames[0]);
            commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[1].toUpperCase()), commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='" + searchFieldNames[1] + "']>input"), "ng-reflect-model"), searchFieldNames[1]);
            commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[2].toUpperCase()), commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='" + searchFieldNames[2] + "']>input"), "ng-reflect-model"), searchFieldNames[2]);
            if (!commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[3].toUpperCase()).equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[3].toUpperCase()), commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='" + searchFieldNames[3] + "']>input"), "ng-reflect-model"), searchFieldNames[3]);
            }
            if (!commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[4].toUpperCase()).equalsIgnoreCase("")) {
                commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(queryResults, searchFieldNames[4].toUpperCase()), commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='" + searchFieldNames[4] + "']>input"), "ng-reflect-model"), searchFieldNames[4]);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User verifies the highlighted menu is \"(.*)\"$")
    public void userVerifiesTheHighlightedMenuIs(String highlightedMenu) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(1);
            if (highlightedMenu.equals(commonPage.getAttributeValue(By.xpath("//a[contains(@class,'user-current-item')]"), "aria-label"))) {
                logger.log("Menu '" + highlightedMenu + "' is highlighted as expected");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate account is displayed at the context picker based on \"(.*)\" value \"(.*)\"$")
    public void userValidateAccountIsDisplayedAtTheContextPickerBasedOnPropertyValue(String propertyOrStringType, String fieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (propertyOrStringType.equalsIgnoreCase("property") || propertyOrStringType.equalsIgnoreCase("properties")) {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, fieldName), basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='account-name']")), "accountName");
            } else {
                commonPage.assertTwoStrings(logger, fieldName, basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='account-name']")), "accountName");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate based on the search criteria the record count displayed in module \"(.*)\" or not for field \"(.*)\" based on field value$")
    public void userValidateBasedOnSearchCriteriaTheRecordCountDisplayedInModuleOrNot(String moduleName, String fieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateCountOfRecordsBasedOnSearchCriteria(logger, moduleName, fieldName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User expand the \"(.*)\" record of \"(.*)\" module$")
    public void userExpandTheSpecificRecordOfModule(String noOfRecord, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userClick(logger, By.xpath("(//tbody[@role='rowgroup']/tr)[" + Integer.parseInt(noOfRecord) + "]"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User expand the record for which contains \"(.*)\" and not contains \"(.*)\" which is at column position \"(.*)\" in \"(.*)\" module$")
    public void userExpandTheSpecificRecordOfModule(String valueContains, String valueNotContains, String positionOfField, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        commonPage.sleepForFewSeconds(2);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<WebElement> elements = basePage.getListOfElements(logger, By.xpath("//tbody[@role='rowgroup']/tr/td[" + positionOfField + "]"));
            String[] arrValueNotContains = {valueNotContains};
            if (valueNotContains.contains(",")) {
                arrValueNotContains = valueNotContains.split(",");
            }
            elements.get(0).click();
            boolean isContains = false;
//            for (int i = 0; i <= elements.size() - 1; i++) {
//                if (elements.get(i).getText().contains(valueContains)) {
//                    for (int j = 0; j <= arrValueNotContains.length - 1; j++) {
//                        if (elements.get(i).getText().contains(arrValueNotContains[j])) {
//                            isContains = true;
//                            break;
//                        }
//                    }
//                    if (!isContains) {
//                        elements.get(i).click();
//                        PropUtils.setProps("TransactionRecordPosition", String.valueOf(i + 1), baseUtils.testDataFilePath);
//                        break;
//                    }
//                }
//            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User \"(.*)\" search bar and validate search fields with values$")
    public void userExpandOrCollapseSearchBarAndValidateSearchFieldsWithValues(String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userClickJSExecutor(logger, By.xpath("(//div[@class='ng-star-inserted']/mat-expansion-panel/mat-expansion-panel-header)[1]"));
            logger.log("Search bar has been " + action);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select \"(.*)\" option using tag \"(.*)\" from context picker if logged in user \"(.*)\" has access to more than one customer$")
    public void userSelectOptionFromAccountPickerUsingSpecificTagAndBasedOnLoggedInUser(String option, String tagName, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            allRowsOfQueryResults = omvContextPage.getAllCustomersBasedOnLoggedInUser(userName);
            if (allRowsOfQueryResults.size() > 1) {
                basePage.userClick(logger, By.cssSelector("div[class='mat-menu-trigger account-labels hover-link-active ng-star-inserted']"));
                commonPage.clickButtonUsingSpecificTagName(logger, option, tagName);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

//    @Then("^User get clientGroupMID for logged in user \"(.*)\"$")
//    public void userSelectOrEnterValueFromAFieldBasedOnModuleName(String userName) {
//        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
//        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
//            System.out.println("enetring in to client group MID" + baseUtils.inputDataFilePath);
//            omvContextPage.getClientGroupMidForLoggedInUser(logger, PropUtils.getPropValue(baseUtils.inputProp, userName));
//
//        } else {
//            logger.log(PropUtils.getPropValue(properties, "skipReason"));
//        }
//    }

    @Then("^User click on context picker$")
    public void userClickOnContextPicker() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userClick(logger, By.cssSelector("div[class='mat-menu-trigger account-labels hover-link-active ng-star-inserted']"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get transaction record based on transaction type \"(.*)\" and \"(.*)\" based on field name \"(.*)\"$")
    public void userGetTransactionRecordBasedOnTransactionTypeAndFieldName(String transactionType, String notContainTransactionTypesInDB, String fieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            PropUtils.setProps("TransactionType", transactionType, baseUtils.testDataFilePath);
            String notContainTransactionTypes = "";
            String[] arrNotContainTransactionTypes = {notContainTransactionTypesInDB};
            if (notContainTransactionTypesInDB.contains(",")) {
                arrNotContainTransactionTypes = notContainTransactionTypesInDB.split(",");
            }
            for (int i = 0; i <= arrNotContainTransactionTypes.length - 1; i++) {
                if (i == arrNotContainTransactionTypes.length - 1) {
                    notContainTransactionTypes = notContainTransactionTypes + "'%" + arrNotContainTransactionTypes[i] + "%'";
                } else {
                    notContainTransactionTypes = notContainTransactionTypes + "'%" + arrNotContainTransactionTypes[i] + "%'" + ",";
                }
            }
            PropUtils.setProps("notContainsTransactionTypes", notContainTransactionTypes, baseUtils.testDataFilePath);
            String transactionTypeInDB = "";
            if (transactionType.equalsIgnoreCase("Sundry")) {
                transactionTypeInDB = "'%" + transactionType + "%'";
            } else {
                transactionTypeInDB = "'%" + transactionType + "'";
            }
            PropUtils.setProps("TransactionTypeInDB", transactionTypeInDB, baseUtils.testDataFilePath);
            omvContextPage.getTransactionRecordBasedOnTransactionType(logger, transactionTypeInDB, fieldName, "");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate \"(.*)\" omv section fields \"(.*)\" values of \"(.*)\" with database$")
    public void userValidateBasedOnSearchCriteriaTheRecordCountDisplayedInModuleOrNot(String sectionName, String fields, String transactionType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateFieldValuesBasedOnSection(logger, sectionName, fields, transactionType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate \"(.*)\" omv section fields \"(.*)\" values of Authorisation record with database$")
    public void userValidateOMVSectionFieldValuesOfAuthorisationRecordWithDatabase(String sectionName, String fields) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateAuthorisationModuleFieldValuesBasedOnSection(logger, sectionName, fields);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate \"(.*)\" omv section field values \"(.*)\" with database based on searched field \"(.*)\" based on user \"(.*)\"$")
    public void userValidateOMVCardSectionFieldValuesWithTheDatabase(String sectionName, String fields, String searchedField, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateCardSectionFieldValuesBasedOnSection(logger, searchedField, sectionName, fields, userName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate \"(.*)\" omv Card Control Profile section field values \"(.*)\" with database based on searched field \"(.*)\"$")
    public void userValidateOMVSectionFieldValuesForCardcontrolProfilesSection(String sectionName, String fields, String searchedField) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateCardControlProfileModuleFieldValuesBasedOnSection(logger, searchedField, sectionName, fields);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter values for fields \"(.*)\" based on their behaviour \"(.*)\" in module \"(.*)\" and verify count of records after clicking on search button$")
    public void userEnterValuesForFieldsBasedOnTheirBehaviorInSearchPanel(String fieldNames, String behaviour, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.enterFieldValuesBasedOnTheirBehaviourInSpecificModule(logger, fieldNames, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate record count of authorisations is displayed as mentioned in property \"(.*)\"$")
    public void userValidateRecordCountOfAuthorisationsIsDisplayedAsMentionedInPropertyOrNot(String fieldNames) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "dbRecordCount"), usersPage.getCountOfRecords(logger), "countOfSearchedResults");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User \"(.*)\" search bar and validate search fields with values \"(.*)\"$")
    public void userSeachBarAndValidateSearchFieldsWithValues(String actionOfSearchBar, String fields) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (actionOfSearchBar.equalsIgnoreCase("expand")) {
                basePage.userClick(logger, By.cssSelector("mav-svg-icon[class='mav-svg-icon-panel-header mat-icon ng-star-inserted']"));
                omvContextPage.validateFieldValuesAfterExpandingSearchBarBasedOnModule(fields, actionOfSearchBar, logger);
            } else if (actionOfSearchBar.equalsIgnoreCase("collapse")) {
                basePage.userClick(logger, By.cssSelector("mav-svg-icon[class='mav-svg-icon-panel-header mat-icon ng-star-inserted']"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get account Number which has records based on module \"(.*)\"$")
    public void userGetAccountNumberWhichHasRecordsBasedOnModule(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.enterGetAccountNumberWhichHasRecordsForSpecificModule(logger, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate filter \"(.*)\" which is at position \"(.*)\" values are displayed and enabled or disabled based on searched filed \"(.*)\" as per the database or not$")
    public void userSearchBarAndValidateSearchFieldsWithValues(String filterName, String positionOfFilter, String searchedField) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String dbAuthStatuses = omvContextPage.allStatusesOfAuthorisationModule(logger, filterName);
            String arrDbAuthStatuses[] = {dbAuthStatuses};
            String selectedStatuses = "";
            StringBuffer query = new StringBuffer();
            if (dbAuthStatuses.contains(",")) {
                arrDbAuthStatuses = dbAuthStatuses.split(",");
            }
            query.delete(0, query.length());
            if (PropUtils.getPropValue(properties, "Country").trim().equalsIgnoreCase("AllCountries")) {
                query.append("");
            } else {

            }
            for (int i = 0; i < arrDbAuthStatuses.length - 1; i++) {
                if (i < arrDbAuthStatuses.length - 1) {
                    usersPage.selectValuesFromFilter(logger, filterName, arrDbAuthStatuses[i]);
                    selectedStatuses = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDbAuthStatuses[i]);
                } else {
                    usersPage.selectValuesFromFilter(logger, filterName, dbAuthStatuses);
                    selectedStatuses = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbAuthStatuses);
                }
                query.delete(0, query.length());
                query.append("select count(*) from auth_transaction_logs at\n" +
                        "left join trans_auth_status_view tas on tas.AUTH_TRANSACTION_LOG_OID = at.AUTH_TRANSACTION_LOG_OID\n" +
                        "left join constants c on c.constant_oid = auth_status_cid\n" +
                        "left join m_customers mcust on mcust.customer_mid = at.customer_mid\n" +
                        "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where at.received_at < sysdate and at.received_at > sysdate - 900\n" +
//                            " and mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ") " +
                        "and c.DESCRIPTION in (" + selectedStatuses + ")");
                queryResults = commonUtils.getQueryResultsOnMap(query.toString());
//                        commonPage.assertTwoStrings(logger, queryResults.get("COUNT(*)"), usersPage.getCountOfRecords(logger)), "CountOfRecordsBasedOnFilter-" + filterName);
                commonPage.clearSpecificFilterUsingXIcon(logger, 1);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate filter \"(.*)\" which is at position \"(.*)\" values are displayed based on searched filed \"(.*)\" as per the database or not$")
    public void userSearchBarAndValidateSearchFieldsWithValuesInTransactionModule(String filterName, String positionOfFilter, String searchedField) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String dbAuthStatuses = omvContextPage.allStatusesOfAuthorisationModule(logger, filterName);
            String arrDbAuthStatuses[] = {dbAuthStatuses};
            String selectedStatuses = "";
            StringBuffer query = new StringBuffer();
            if (dbAuthStatuses.contains(",")) {
                arrDbAuthStatuses = dbAuthStatuses.split(",");
            }
            query.delete(0, query.length());
            if (PropUtils.getPropValue(properties, "Country").trim().equalsIgnoreCase("AllCountries")) {
                query.append("");
            } else {

            }
            for (int i = 0; i < arrDbAuthStatuses.length - 1; i++) {
                if (i < arrDbAuthStatuses.length - 1) {
                    usersPage.selectValuesFromFilter(logger, filterName, arrDbAuthStatuses[i]);
                    selectedStatuses = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDbAuthStatuses[i]);
                } else {
                    usersPage.selectValuesFromFilter(logger, filterName, dbAuthStatuses);
                    selectedStatuses = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbAuthStatuses);
                }
                query.delete(0, query.length());
                query.append("select count(*) from auth_transaction_logs at\n" +
                        "left join trans_auth_status_view tas on tas.AUTH_TRANSACTION_LOG_OID = at.AUTH_TRANSACTION_LOG_OID\n" +
                        "left join constants c on c.constant_oid = auth_status_cid\n" +
                        "left join m_customers mcust on mcust.customer_mid = at.customer_mid\n" +
                        "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where at.received_at < sysdate and at.received_at > sysdate - 900\n" +
                        " and mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ") " +
                        "and c.DESCRIPTION in (" + selectedStatuses + ")");
                queryResults = commonUtils.getQueryResultsOnMap(query.toString());
                commonPage.assertTwoStrings(logger, queryResults.get("COUNT(*)"), usersPage.getCountOfRecords(logger), "CountOfRecordsBasedOnFilter-" + filterName);
                commonPage.clearSpecificFilterUsingXIcon(logger, 1);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select filter values in OMV-Transactions module then click on Apply button and validate filter results in database$")
    public void userSelectFilterFunctionalityOfOMVTransactionsModule() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = PropUtils.getPropValue(properties, "accountNumber");
            String dbTransactionTypes = omvContextPage.getDBValuesForAFilterBasedOnDateAndClient(logger, "transactionTypes");
            String dbIssuerCountries = omvContextPage.getDBValuesForAFilterBasedOnDateAndClient(logger, "issuerCountries");
            String dbProducts = omvContextPage.getDBValuesForAFilterBasedOnDateAndClient(logger, "products");
            String[] arrDBTransactionTypes = {dbTransactionTypes};
            if (dbTransactionTypes.contains(",")) {
                arrDBTransactionTypes = dbTransactionTypes.split(",");
            }
            String[] arrDBIssuerCountries = {dbIssuerCountries};
            if (dbIssuerCountries.contains(",")) {
                arrDBIssuerCountries = dbIssuerCountries.split(",");
            }
            String[] arrDBProducts = {dbProducts};
            if (dbProducts.contains(",")) {
                arrDBProducts = dbProducts.split(",");
            }
            String transactionTypes = "";
            String issuerCountries = "";
            String products = "";
            String query = "";
            String condition = "";
            if (arrDBTransactionTypes.length > 1) {
                for (int i = 0; i <= arrDBTransactionTypes.length - 1; i++) {
                    if (arrDBIssuerCountries.length > 1) {
                        for (int j = 0; j <= arrDBIssuerCountries.length - 1; j++) {
                            if (arrDBProducts.length > 1) {
                                for (int k = 0; k <= arrDBProducts.length - 1; k++) {
                                    // Validate count of records for each status and role
                                    if (i == arrDBTransactionTypes.length - 1) {
                                        usersPage.selectValuesFromFilter(logger, "All transaction types", dbTransactionTypes);
                                        transactionTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbTransactionTypes);
                                        usersPage.selectValuesFromFilter(logger, "All issuer countries", arrDBIssuerCountries[j]);
                                        issuerCountries = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBIssuerCountries[j]);
//                                            usersPage.selectValuesFromFilter(logger, "All products", arrDBProducts[k]);
//                                            products = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBProducts[k]);
                                    } else if (j == arrDBIssuerCountries.length - 1) {
                                        usersPage.selectValuesFromFilter(logger, "All transaction types", arrDBTransactionTypes[i]);
                                        transactionTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBTransactionTypes[i]);
                                        usersPage.selectValuesFromFilter(logger, "All issuer countries", dbIssuerCountries);
                                        issuerCountries = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbIssuerCountries);
//                                            usersPage.selectValuesFromFilter(logger, "All products", dbProducts);
//                                            products = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbProducts);
                                    } else if (k == arrDBProducts.length - 1) {
                                        usersPage.selectValuesFromFilter(logger, "All transaction types", arrDBTransactionTypes[i]);
                                        transactionTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBTransactionTypes[i]);
                                        usersPage.selectValuesFromFilter(logger, "All issuer countries", dbIssuerCountries);
                                        issuerCountries = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbIssuerCountries);
//                                            usersPage.selectValuesFromFilter(logger, "All products", dbProducts);
//                                            products = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(dbProducts);
                                    } else {
                                        usersPage.selectValuesFromFilter(logger, "All transaction types", arrDBTransactionTypes[i]);
                                        usersPage.selectValuesFromFilter(logger, "All issuer countries", arrDBIssuerCountries[j]);
//                                            usersPage.selectValuesFromFilter(logger, "All products", arrDBProducts[k]);
                                        transactionTypes = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBTransactionTypes[i]);
                                        issuerCountries = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBIssuerCountries[j]);
//                                            products = contactsPage.getContactFilterValuesInCommaSeparatedWithSingleQuotes(arrDBProducts[k]);
                                    }
                                    if (!transactionTypes.equals("")) {
                                        condition = "tt.description in (" + transactionTypes + ")" + " and ";
                                    }
                                    if (!issuerCountries.equals("")) {
                                        condition = condition + "c.description in (" + issuerCountries + ")" + " and ";
                                    }
                                    if (!products.equals("")) {
                                        condition = condition + "pt.description in (" + products + ")";
                                    }
                                    if (basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'account-number')]")).equalsIgnoreCase("Select account")) {
                                        query = "select distinct te.transaction_oid from transactions t\n" +
                                                "left join transaction_line_items tli on tli.TRANSACTION_OID = t.transaction_oid\n" +
                                                "left join transaction_enquiry te on te.transaction_oid = tli.transaction_oid\n" +
                                                "left join transaction_types tt on tt.transaction_type_oid = te.transaction_type_oid\n" +
                                                "left join product_translations pt on pt.product_translation_oid = tli.product_translation_oid\n" +
                                                "left join m_customers mcust on mcust.customer_mid = te.customer_mid\n" +
                                                "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                                                "left join detail_group_trans dgt on dgt.transaction_oid = te.transaction_oid and dgt.line_number = 0\n" +
                                                "left join detail_groups dg on dg.detail_group_oid = dgt.detail_group_oid\n" +
                                                "left join countries c on c.country_oid = dg.country_oid\n" +
                                                "where mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ")\n" +
                                                "and " + condition + " \n" +
                                                "and te.effective_at < sysdate and te.effective_at > sysdate-" + PropUtils.getPropValue(properties, "trans-date");
                                    } else {
                                        query = "select distinct te.transaction_oid from transactions t\n" +
                                                "left join transaction_line_items tli on tli.TRANSACTION_OID = t.transaction_oid\n" +
                                                "left join transaction_enquiry te on te.transaction_oid = tli.transaction_oid\n" +
                                                "left join transaction_types tt on tt.transaction_type_oid = te.transaction_type_oid\n" +
                                                "left join product_translations pt on pt.product_translation_oid = tli.product_translation_oid\n" +
                                                "left join m_customers mcust on mcust.customer_mid = te.customer_mid\n" +
                                                "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                                                "left join detail_group_trans dgt on dgt.transaction_oid = te.transaction_oid and dgt.line_number = 0\n" +
                                                "left join detail_groups dg on dg.detail_group_oid = dgt.detail_group_oid\n" +
                                                "left join countries c on c.country_oid = dg.country_oid\n" +
                                                "where mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ")\n" +
                                                "and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberEnteredInField") + "'\n" +
                                                "and " + condition + " \n" +
                                                "and te.effective_at < sysdate and te.effective_at > sysdate-" + PropUtils.getPropValue(properties, "trans-date");
                                    }
                                    logger.log(query);
                                    Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
                                    if (queryResults.size() == 0) {
                                        commonPage.validateTextUsingtag(logger, "No results found", "div");
                                        basePage.userClick(logger, By.xpath("//a[contains(text(),'Reset filters')]"));
                                    } else {
                                        String actualRecordsCount = usersPage.getCountOfRecords(logger);
                                        String expRecordsCount = String.valueOf(queryResults.size());
//                                            basePage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount);
                                        if (basePage.getListOfElements(logger, By.xpath("//div[@class='filter-dropdowns']/filter-dropdown-component/div/mat-form-field/div/div/div/button/span/div/span/mav-svg-icon/fa-icon[@ng-reflect-icon='fal,times']")).size() > 1) {
                                            usersPage.clickResetAllFilters(logger);
                                        } else {
                                            basePage.userClick(logger, By.xpath("//div[@class='filter-dropdowns']/filter-dropdown-component/div/mat-form-field/div/div/div/button/span/div/span/mav-svg-icon/fa-icon[@ng-reflect-icon='fal,times']"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get an account which is not having an hierarchy with \"(.*)\"$")
    public void userGetAnAccountWithNoHierarchy(String clientMid) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
//                omvContextPage.getAccountNoWithOutHierarchy(logger, PropUtils.getPropValue(properties, clientMid));
            omvContextPage.getAccountNoWithOutHierarchyWithIndex(logger, PropUtils.getPropValue(properties, clientMid), 1);

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User click on select account")
    public void userClickOnSelectAccount() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userClick(logger, By.xpath("//div[text()=' Select account ']"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User add child accounts and edit added child account \"(.*)\" with \"(.*)\" for \"(.*)\" depth value condition \"(.*)\"$")
    public void addAndEditChildAccounts(String accountType, String clientMid, String hierarchyType, String depthValueCond) {
        int index = 1;
        int childAccounts = 0;
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String parentAccountNo = PropUtils.getPropValue(properties, accountType);
            omvContextPage.getBillingHierarchyValue(logger, parentAccountNo);
            properties = PropUtils.getProps(baseUtils.testDataFile);
            String billingHierarchyValue = PropUtils.getPropValue(properties, "BillingHierarchyValue");
            logger.log("billing hierarchy value" + billingHierarchyValue);
            if (billingHierarchyValue.equalsIgnoreCase("1 Level Only")) {
                childAccounts = 0;
            } else if (billingHierarchyValue.equalsIgnoreCase("Up to 2 Levels")) {
                childAccounts = 1;
            } else if (billingHierarchyValue.equalsIgnoreCase("Up to 3 Levels")) {
                childAccounts = 2;
            } else if (billingHierarchyValue.equalsIgnoreCase("Up to 4 Levels")) {
                childAccounts = 3;
            }
            logger.log("child accounts" + childAccounts);
            int conditionOfForLoop = 1;
            if (PropUtils.getPropValue(properties, "hierarchyScenario").equalsIgnoreCase("Financial")) {
                conditionOfForLoop = childAccounts;
            }
            logger.log("Hierarchy scenario name : " + PropUtils.getPropValue(properties, "hierarchyScenario"));
            logger.log("Condition of For loop : " + conditionOfForLoop);
            PropUtils.setProps("childAccounts", String.valueOf(conditionOfForLoop), baseUtils.testDataFilePath);
            if (depthValueCond.equalsIgnoreCase("Yes") || depthValueCond.equalsIgnoreCase("True")) {
                for (int i = 1; i <= conditionOfForLoop; i++) {
                    index = index + 1;
                    omvContextPage.getAccountNoWithOutHierarchyWithIndex(logger, PropUtils.getPropValue(properties, clientMid), index);
                    properties = PropUtils.getProps(baseUtils.testDataFile);
                    String accountNo = PropUtils.getPropValue(properties, accountType + index);
                    logger.log("Account number from file ::::" + accountNo);
                    hierarchyPage.clickOnThreeDots(logger);
                    hierarchyPage.selectAccountMenuOption(logger, "Add child account");
                    hierarchyPage.searchForChildAccount(logger, accountNo);
                    hierarchyPage.selectAccountFromSearchResults(logger);
                    hierarchyPage.fillDetailsInAccountConfigurationPOpUp(logger, "0");
                    hierarchyPage.expandParentAccount(logger);
                }
                hierarchyPage.clickOnThreeDots(logger);
                if (index == childAccounts) {
                    if (commonPage.getAttributeValue(By.xpath("//button[contains(text(),'Add child account')]"), "aria-disabled").equalsIgnoreCase("false")) {
                        logger.log("'Add child account' button is enabled after reaching max child account addition based on condition '" + billingHierarchyValue + "'");
                    }
                }
            basePage.assertTwoStrings(logger, "true", hierarchyPage.validateAddChildAccountOptionStateForHierarchy(logger));
            hierarchyPage.selectAccountMenuOption(logger, "Edit");
            hierarchyPage.validateAccountConfigurationDetails(logger);
            hierarchyPage.editAccountConfigurationPOpUp(logger, "1");
            }else if(depthValueCond.equalsIgnoreCase("No")){

            }

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate hierarchy has been added for following sections \"(.*)\"$")
    public void validateHierarchy(String hierarchyType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {

            hierarchyPage.goToHierarchyTab(logger, hierarchyType);
            hierarchyPage.validateHierarchyPresence(logger);

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate newly added \"(.*)\" in database$")
    public void validateHeirarchyDetailsFromDB(String heirarchyType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateHierarchyEntryInDB(logger, "Parent", PropUtils.getPropValue(properties, "notHavingHierarchy-accountNumber"));
            properties = PropUtils.getProps(baseUtils.testDataFile);
            for (int i = 1; i <= Integer.parseInt(PropUtils.getPropValue(properties, "childAccounts")); i++) {
                omvContextPage.validateHierarchyEntryInDB(logger, "Child", PropUtils.getPropValue(properties, "notHavingHierarchy-accountNumber" + (i + 1)));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User verify Header,Sections,data in hierarchy in \"(.*)\" module$")
    public void userVerifyHeaderSectionsDataInCorrespondingHierarchySection(String hierarchySection) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            hierarchyPage.validateHierarchyDataInSpecificSection(logger, hierarchySection);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get a \"(.*)\" account which is having an \"(.*)\" with \"(.*)\"$")
    public void getCustomerWithHierarchy(String accountType, String hierarchyType, String clientMid) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getCustomerNumberWithHierarchy(logger, accountType, hierarchyType, PropUtils.getPropValue(properties, clientMid), 0);
            PropUtils.setProps("hierarchyType", hierarchyType, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get an account which is having a \"(.*)\"$")
    public void getCustomerWithHierarchy(String hierarchyType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getAccountsWhichHavingHierarchy(logger, hierarchyType, "parent");
            PropUtils.setProps("hierarchyType", hierarchyType, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get an account which is having a \"(.*)\" with \"(.*)\" status$")
    public void getCustomerWithPendingHierarchy(String hierarchyType, String status) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getAccountNumberWithPendingHierarchy(logger, status);
            PropUtils.setProps("hierarchyType", hierarchyType, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get a child account which is having a \"(.*)\" with \"(.*)\" status$")
    public void getChildCustomerWithPendingHierarchy(String hierarchyType, String status) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getChildAccountNumberWithPendingHierarchy(logger, status);
            PropUtils.setProps("hierarchyType", hierarchyType, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User add,edit child accounts \"(.*)\"$")
    public void addAndEditChildAccountsToExistingHierarchy(String accountType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            properties = PropUtils.getProps(baseUtils.testDataFile);
            omvContextPage.getCustomerNumberWithOutHierarchyUnderSameProgram(logger, PropUtils.getPropValue(properties, "ClientMID"), PropUtils.getPropValue(properties, "havingHierarchy-accountNumber"), 1);
            properties = PropUtils.getProps(baseUtils.testDataFile);
            String accountNo = PropUtils.getPropValue(properties, accountType);
            System.out.println("Account number from file ::::" + accountNo);
            if (PropUtils.getPropValue(properties, "hierarchyType").equalsIgnoreCase("rebateshierarchy") || PropUtils.getPropValue(properties, "hierarchyType").equalsIgnoreCase("rebates")) {
                commonPage.clickButtonUsingSpecificTagName(logger, "Rebates", "div");
            }
            // hierarchyPage.clickOnThreeDotsWithPlus(logger);
            // hierarchyPage.selectAccountMenuOption(logger, "Approve ");
            hierarchyPage.clickOnThreeDotsWithPlus(logger);
            hierarchyPage.selectAccountMenuOption(logger, "Edit");

            usersPage.scrollDown(logger);
            commonPage.sleepForFewSeconds(2);
            commonPage.clickOnButtonUsingJSExecutor(logger, "Next", "1", "span");
            commonPage.sleepForFewSeconds(2);

            hierarchyPage.clickOnThreeDotsWithPlus(logger);
            hierarchyPage.selectAccountMenuOption(logger, " Add child account ");

            hierarchyPage.searchForChildAccount(logger, accountNo);
            PropUtils.setProps(accountType + "1", accountNo, baseUtils.testDataFilePath);
            hierarchyPage.selectAccountFromSearchResults(logger);
            hierarchyPage.fillDetailsInAccountConfigurationPOpUp(logger, "0");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User modify expiry date for parent and child, validate in expiry pop no limitation for parent but child is limited based on parent for date pop up$")
    public void expiryHierarchy() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        hierarchyPage.clickOnThreeDotsWithPlus(logger);
        hierarchyPage.selectAccountMenuOption(logger, "Edit");
        usersPage.scrollDown(logger);
        commonPage.sleepForFewSeconds(2);
        commonPage.clickOnButtonUsingJSExecutor(logger, "Next", "1", "span");
        commonPage.sleepForFewSeconds(2);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<Map<String, String>> getAllAccountsBasedOnParentAccount = hierarchyPage.getChildAccountsForASpecificCustomer(logger, PropUtils.getPropValue(properties, "havingHierarchy-accountNumber"), "All");
            for (int i = 1; i <= getAllAccountsBasedOnParentAccount.size() - 1; i++) {
                hierarchyPage.expandParentAccount(logger);
            }
            String parentExpiryDate = basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[1]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[1];

            String immediateChildExpiryDate = "";
            if (getAllAccountsBasedOnParentAccount.size() > 1) {
                immediateChildExpiryDate = basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[2]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[1];
            }
            int diffBetweenParentAndChildDates = basePage.differnceOfTwoDates(logger, immediateChildExpiryDate, parentExpiryDate, "dd/MM/yyyy");
            hierarchyPage.expiryTheParentHierarchyAndvalidateError(logger, String.valueOf(diffBetweenParentAndChildDates + 1), parentExpiryDate, "dd/MM/yyyy");

            hierarchyPage.clickOnThreeDotsWithPlus(logger);
            hierarchyPage.selectAccountMenuOption(logger, "Edit");
            usersPage.scrollDown(logger);
            commonPage.sleepForFewSeconds(2);
            commonPage.clickOnButtonUsingJSExecutor(logger, "Next", "1", "span");
            commonPage.sleepForFewSeconds(2);
            for (int j = 1; j <= getAllAccountsBasedOnParentAccount.size() - 1; j++) {
                hierarchyPage.expandParentAccount(logger);
            }
            int diff = 0;
            for (int i = getAllAccountsBasedOnParentAccount.size(); i >= 2; i--) {

                diff = hierarchyPage.getDifferenceBetweenEffectiveAndExpiryDate(String.valueOf(i));
                if (diff > 0) {
                    hierarchyPage.expiryTheChildHierarchyBasedOnPosition(logger, Integer.toString(diff - 1), i, basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[" + i + "]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[1].trim(), "dd/MM/yyyy");

                }
            }

            if (basePage.differnceOfTwoDates(logger, basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[1]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[0].trim(), basePage.userGetTextFromWebElement(logger, By.xpath("((//div[@class='tree-table-col-data']/mat-grid-list)[1]/div/mat-grid-tile/figure/div/div)[2]")).split("-")[1].trim(), "dd/MM/yyyy") > 0) {
                hierarchyPage.expiryTheParentHierarchy(logger, String.valueOf(diff - 1), parentExpiryDate, "dd/MM/yyyy");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User get account number \"(.*)\" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is \"(.*)\" atleast \"(.*)\", auto reissue flag \"(.*)\"$")
    public void userGetAccountNumberForAutoReissueModule(String isContainAutoReissueCards, String excludeFromBulkReissue, String noOfCards, String isAutoReissueFlag) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getCustomerNumberForAutoReissueModule(logger, isContainAutoReissueCards, isAutoReissueFlag, noOfCards, excludeFromBulkReissue);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User select or validate \"(.*)\" in manage auto reissue module for \"(.*)\" records which has EXCLUDE_FROM_BULK_REISSUE flag \"(.*)\"$")
    public void userSelectOrValidateChangeAutoReissueValue(String sectionType, String noOfRecords, String excludeFromBulkReissueFlag) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.handleChangeAutoReissueAction(logger, sectionType, noOfRecords, excludeFromBulkReissueFlag);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User validate section \"(.*)\" in manage auto reissue module based on account \"(.*)\"$")
    public void userValidateCountOfRecordsForAutoReissueModule(String section, String autoReissueAccountNoFromPropFile) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.validateSectionBehaviourInManageAutoReissueModule(logger, section, autoReissueAccountNoFromPropFile);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User validate actions are visible based on status of Report")
    public void validateReportSetupActions() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<WebElement> countOfRecords = basePage.getListOfElements(logger, By.cssSelector("div>mat-grid-list"));
            for (int i = 0; i <= countOfRecords.size() - 1; i++) {
                if (basePage.userGetTextFromWebElement(logger, By.xpath("((//mat-grid-list)[" + (i + 1) + "]/div/mat-grid-tile)[4]/figure/div/div")).equalsIgnoreCase("Needs approval")) {
                    basePage.userClick(logger, By.xpath("(//button/div/mav-svg-icon/fa-icon)[" + (i + 1) + "]"));
                    List<WebElement> buttons = driver.findElements(By.xpath("//div[@class ='mat-menu-content']/div/button"));
                    if (buttons.size() == 0) {
                        logger.log("Buttons are not displayed");
                    } else {
                        commonPage.assertTwoStrings(logger, "Edit", buttons.get(0).getText(), "Button name should be Edit");
                        commonPage.assertTwoStrings(logger, "Approve", buttons.get(1).getText(), "Button name should be Approve");
                        commonPage.assertTwoStrings(logger, "Declined", buttons.get(2).getText(), "Button name should be Declined");
                    }
                    break;
                }
                if (i == countOfRecords.size() - 1) {
                    logger.log("None of the report is having 'Needs approval");
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User get an \"(.*)\" account number which \"(.*)\" Document setup for user \"(.*)\"$")
    public void getAccountNumberWithOutReportSetup(String accountStatus, String accountType, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportSetupPage.getAccountNumberForReportSetup(logger, accountType, userType, accountStatus);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User click on button contains Three dot icon")
    public void userClickOnThreeDotsInReportSetupPage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportSetupPage.clickOnThreeDotsOfFirstReportSetup(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User validate DeliveryType,Frequency,Recipient,SortBy field values populated and update any one value")
    public void validateEditReportSetupAndUpdate() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String customerNo = PropUtils.getPropValue(properties, "havingReportSetup");
            reportSetupPage.getReportSetupDetailsFromDB(logger, customerNo);
            reportSetupPage.validateEditReportSetupDetailsAndUpdate(logger);
            String count = PropUtils.getPropValue(properties, "CountOFRecipientForReportSetup");
            logger.log("reci" + PropUtils.getPropValue(properties, "RecipientForReportSetup"));
            if (Integer.parseInt(count) > 1) {
                if (!PropUtils.getPropValue(properties, "RecipientForReportSetup").equalsIgnoreCase(PropUtils.getPropValue(properties, "RecipientDB"))) {
                    commonPage.selectValueFromMatSelectDropDown(logger, PropUtils.getPropValue(properties, "RecipientForReportSetup"), "recipient");
                } else if (!PropUtils.getPropValue(properties, "RecipientForReportSetup1").equalsIgnoreCase(PropUtils.getPropValue(properties, "RecipientDB"))) {
                    commonPage.selectValueFromMatSelectDropDown(logger, PropUtils.getPropValue(properties, "RecipientForReportSetup1"), "recipient");
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User verify the newly created report is saved in the database")
    public void verifyReportSetupEntryInDB() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportSetupPage.validateBDEntryForReportSetup(logger, PropUtils.getPropValue(properties, "not havingReportSetup"), PropUtils.getPropValue(properties, "ReportTypeNotInUse"));
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User change card status to active for few cards to perform card actions")
    public void userChangeCardStatusForFewCardsToPerformCardActions() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select card_status_oid from card_status where description = 'Active' and client_mid = " +
                    PropUtils.getPropValue(properties, "ClientMID");
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            String activeCardStatusOid = queryResults.get(0).get("CARD_STATUS_OID");
            query = "select FROM_CARD_STATUS_OID from card_status_changes where to_card_status_oid = " + queryResults.get(0).get("CARD_STATUS_OID") + " and client_mid =" +
                    PropUtils.getPropValue(properties, "ClientMID");
            logger.log(query);
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            String fromCardStatusOids = "";
            for (int i = 0; i <= queryResults.size() - 1; i++) {
                if (i == queryResults.size() - 1) {
                    fromCardStatusOids = fromCardStatusOids + queryResults.get(i).get("FROM_CARD_STATUS_OID");
                } else {
                    fromCardStatusOids = fromCardStatusOids + queryResults.get(i).get("FROM_CARD_STATUS_OID") + ",";
                }
            }
            query = "select c.card_oid from cards c \n" +
                    "left join card_controls cc on cc.card_control_profile_oid = c.card_control_profile_oid\n" +
                    "left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                    "where mcust.client_mid = " + PropUtils.getPropValue(properties, "ClientMID")
                    + " and cc.product_restriction_oid is not null and c.card_status_oid in (" + fromCardStatusOids + ") and rownum<=10\n" +
                    "order by c.LAST_UPDATED_AT asc";
            logger.log(query);
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            for (int i = 0; i <= queryResults.size() - 1; i++) {
                commonUtils.executeUpdateQuery("update cards set card_status_oid = " + activeCardStatusOid + " where card_oid = " + queryResults.get(i).get("CARD_OID"));
                commonUtils.executeUpdateQuery("update card_status_logs set card_status_oid = " + activeCardStatusOid + " where card_oid = " + queryResults.get(i).get("CARD_OID"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the logged in user is eligible to create \"(.*)\" adhoc fee or not$")
    public void userGetAnAccountNumberWhichIsEligibleToCreateAdhocFee(String newOrOld) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.verifyUserIsEligibleToCreateAdhocFeeOrNot(newOrOld);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User \"(.*)\" the created Financial hierarchy$")
    public void userApproveOrDeclinHierarchy(String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);

        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            hierarchyPage.clickOnThreeDots(logger);
            hierarchyPage.selectAccountMenuOption(logger, action);

        }
    }

    @And("User validate \"(.*)\" status in database$")
    public void validateApprovedStatusDetailsFromDB(String status) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {

                String statusEntryInDB = omvContextPage.validateHierarchyStatusEntryInDB(PropUtils.getPropValue(properties, "notHavingHierarchy-accountNumber"));
                commonPage.assertTwoStrings(logger, statusEntryInDB, status, "Hierarchy status");

        }
    }

    @And("User get an account which is having a \"(.*)\" with no expiry date$")
    public void getCustomerWithNoExpiryHierarchy(String hierarchyType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getCustomerWithNoExpiryDateHierarchy(logger, hierarchyType);
            PropUtils.setProps("hierarchyType", hierarchyType, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get an account which is having a \"(.*)\" with expiry date$")
    public void getCustomerWithExpiryHierarchy(String hierarchyType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            omvContextPage.getCustomerWithExpiryDateHierarchy(logger, hierarchyType);
            PropUtils.setProps("hierarchyType", hierarchyType, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate add new button in hierarchy$")
    public void validateAddNewButtonState() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(3);
            hierarchyPage.validateAddButtonState(logger);
            basePage.assertTwoStrings(logger, "true", hierarchyPage.validateAddButtonState(logger));
            logger.log("Add new button is in disabled state");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User validate the Edit option state for \"(.*)\" hierarchy$")
    public void validateEditOptionForHierarchy(String hierarchyStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(3);
            if (hierarchyStatus.equalsIgnoreCase("Approved")) {
                basePage.assertTwoStrings(logger, "false", hierarchyPage.validateEditOptionStateForHierarchy(logger));
                logger.log("Edit option is in enabled state");
            } else if (hierarchyStatus.equalsIgnoreCase("Pending")) {
                basePage.assertTwoStrings(logger, "true", hierarchyPage.validateEditOptionStateForHierarchy(logger));
                logger.log("Edit option is in disabled state");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("User validate the Approve and Decline options state for \"(.*)\" hierarchy$")
    public void validateApprovedAndDeclinedOptionForHierarchy(String hierarchyStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(3);
            if (hierarchyStatus.equalsIgnoreCase("Approved")) {
                basePage.assertTwoStrings(logger, "true", hierarchyPage.validateApprovedOptionStateForHierarchy(logger));
                logger.log("Approve option is in disabled state");
                basePage.assertTwoStrings(logger, "true", hierarchyPage.validateDeclineOptionStateForHierarchy(logger));
                logger.log("Decline option is in disabled state");
            } else if (hierarchyStatus.equalsIgnoreCase("Pending")) {
                basePage.assertTwoStrings(logger, "false", hierarchyPage.validateApprovedOptionStateForHierarchy(logger));
                logger.log("Approve option is in enable state");
                basePage.assertTwoStrings(logger, "false", hierarchyPage.validateDeclineOptionStateForHierarchy(logger));
                logger.log("Decline option is in enable state");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("Validate User should not allow to add a customer which already present in another hierarchy")
    public void addHierarchyExistingCustomerToAnotherHierarchy() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(3);
            omvContextPage.getAccountsWhichHavingHierarchy(logger, "financialHierarchy", "parent");
            properties = PropUtils.getProps(baseUtils.testDataFile);
            String accountNo = PropUtils.getPropValue(properties, "havingHierarchy-accountNumber");
            logger.log("Account number from file ::::" + accountNo);
            hierarchyPage.clickOnThreeDots(logger);
            hierarchyPage.selectAccountMenuOption(logger, "Add child account");
            hierarchyPage.searchForChildAccount(logger, accountNo);
            commonPage.verifyPresenceOfDivText(logger, "No results found");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }

    }
}
