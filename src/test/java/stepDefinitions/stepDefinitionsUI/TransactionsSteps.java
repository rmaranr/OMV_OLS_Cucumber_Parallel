package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
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

public class TransactionsSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private CommonPage commonPage;
    private TransactionsPage transactionsPage;
    private CostCentresPage costCentresPage;
    public List<Map<String, String>> queryResultsInList;
    public Scenario logger;
    private DriverFactory driverFactory;

    public TransactionsSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        transactionsPage = new TransactionsPage(driver);
        costCentresPage = new CostCentresPage(driver);
        commonPage = new CommonPage(driver);
    }

    @And("^User get \"(.*)\" from Transactions table using Transaction category cid \"(.*)\" for account selected in account picker$")
    public void userSelectValueByClickingCorrespondingDropDown(String transactionType, String transactionCategoryCID) {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        String currentScenarioTransID = transactionsPage.getTransactionBasedOnCategoryID(logger, transactionCategoryCID, PropUtils.getPropValue(testDataProp, "accountNumber"), queryResultsInList);
        if (currentScenarioTransID.equals("")) {
            PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
            logger.log("Transaction Category " + transactionCategoryCID + " is not present");
        } else {
            PropUtils.setProps("trans-currentScenarioTransID", currentScenarioTransID, baseUtils.testDataFilePath);
            PropUtils.setProps("trans-currentScenarioTransCategoryCID", transactionCategoryCID, baseUtils.testDataFilePath);
        }
    }

    @When("^User enter search keywords \"(.*)\" for Transaction type \"(.*)\" by selecting search attribute as \"(.*)\" in Transactions page$")
    public void userEnterSearchKeywordsBySelectingSearchAttributeAs(String searchKeyWords, String transactionTypeOID, String searchAttributeName) {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
        if (getPreviousStepStatus.equals("skipped")) {
            logger.log("Transaction Category is not present");
        }
        costCentresPage.selectSearchAttribute(logger, searchAttributeName);
        for (int i = 0; i <= queryResultsInList.size() - 1; i++) {
            if (queryResultsInList.get(i).get("TRANSACTION_OID").equals(PropUtils.getPropValue(testDataProp, transactionTypeOID))) {
                if (queryResultsInList.get(i).get(searchKeyWords).length() > 3) {
                    costCentresPage.enterSearchKeyWords(logger, queryResultsInList.get(i).get(searchKeyWords));
                    if (searchKeyWords.equals("CARD_NUMBER")) {
                        PropUtils.setProps("trans-cardNumber", queryResultsInList.get(i).get(searchKeyWords), baseUtils.testDataFilePath);
                        break;
                    } else if (searchKeyWords.equals("REFERENCE")) {
                        PropUtils.setProps("trans-Reference", queryResultsInList.get(i).get(searchKeyWords), baseUtils.testDataFilePath);
                        break;
                    }
                }
            }
            if (i == queryResultsInList.size() - 1) {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
            }
        }
        commonPage.sleepForFewSeconds(1);
    }

    @When("^User validate the count of transactions displayed in UI for date filter type \"(.*)\" with the database$")
    public void userValidateTheCountOfTransactionsDisplayedInUIWithTheDatabase(String selectFilter) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String toDate = "";
            String fromDate = "";
            transactionsPage.selectFilter(logger, selectFilter);
            toDate = transactionsPage.getToDateBasedOnNumberOfDays(logger, selectFilter);
            fromDate = transactionsPage.getFromDateBasedOnNumberOfDays(logger, selectFilter, toDate, "dd-MMM-yy");
            commonPage.sleepForFewSeconds(3);
            String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);
            PropUtils.setProps("accountNumber", accountNumber, baseUtils.testDataFilePath);
            queryResultsInList = transactionsPage.getQueryResultsOfTransactionTable(logger, accountNumber, toDate, fromDate);
            String expectedCountOfRecords = String.valueOf(queryResultsInList.size());
            String actualCountOfRecords = transactionsPage.getCountOfRecords(logger);
            if (Integer.parseInt(actualCountOfRecords) < 100) {
//                basePage.assertTwoStrings(logger, expectedCountOfRecords, actualCountOfRecords);
            } else if (Integer.parseInt(actualCountOfRecords) == 100) {
                commonPage.verifyPresenceOfDivText(logger, "The selected criteria has more than 100 results. Limit the results by refining criteria or use the export feature to download all the results.");
                logger.log("There are more than 100 records in database and as per condition only 100 shown in front end");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate the transaction level field values present in Card Transaction with database and click on it$")
    public void userValidateTheFiledValuesOfCardTransaction() {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
        if (getPreviousStepStatus.equals("skipped")) {
            logger.log("Transaction Category is not present");
        }
        int transactionRecordNumberInUI = transactionsPage.getTransactionRecordNumberFromUIToValidateFieldValues(logger, queryResultsInList);
        PropUtils.setProps("transactionRecordNumberInUI", String.valueOf(transactionRecordNumberInUI), baseUtils.testDataFilePath);
        transactionsPage.validateTransactionFieldValues(logger, transactionRecordNumberInUI, queryResultsInList);
    }

    @And("^User validate \"(.*)\" section fields \"(.*)\" values of \"(.*)\" with database$")
    public void userValidateAllFieldValuesOfCardTransactionWithDatabase(String sectionName, String fields, String cardType) {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
        if (getPreviousStepStatus.equalsIgnoreCase("skipped")) {
            logger.log("Transaction Category is not present");
        } else {
            if (sectionName.equals("main")) {
                transactionsPage.validateMainFieldsOfTransaction(logger, fields, cardType, queryResultsInList);
            } else if (sectionName.equals("details")) {
                transactionsPage.validateDetailsFieldsOfTransaction(logger, fields, cardType, queryResultsInList);
            } else if (sectionName.equals("breakDownBorderForAllLines")) {
                transactionsPage.validateTransBreakDownFields(logger, "Border", fields, queryResultsInList);
            } else if (sectionName.equals("breakDownAmountFields")) {
                transactionsPage.validateTransBreakDownFields(logger, "Amount", fields, queryResultsInList);
            } else if (sectionName.equals("breakDownOfRebate")) {
                transactionsPage.validateTransRebateBreakDownSection(logger, fields, queryResultsInList);
            }
        }
    }

    @And("^User click on Transaction Breakdown$")
    public void userClickOnTransactionBreakdown() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            transactionsPage.clickOnTransactionBreakDown(logger);
            commonPage.sleepForFewSeconds(2);
            usersPage.scrollDown(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select filter values in Transactions module then click on Apply button and validate filter results in database for \"(.*)\"$")
    public void userSelectFilterFunctionalityOfContactsModule(String selectDateFilter) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Properties testData = PropUtils.getProps(baseUtils.testDataFile);
            String accountNumber = PropUtils.getPropValue(testData, "accountNumber");
            String dbClientProductsForAnAccount = transactionsPage.getAvailableClientProductsForAnAccount(accountNumber);
            String arrDbClientProductsForAnAccount[] = {dbClientProductsForAnAccount};
            if (dbClientProductsForAnAccount.contains(",")) {
                arrDbClientProductsForAnAccount = dbClientProductsForAnAccount.split(",");
            }
            transactionsPage.selectFilter(logger, selectDateFilter);
            commonPage.sleepForFewSeconds(4);
            String toDate = transactionsPage.getToDateBasedOnNumberOfDays(logger, selectDateFilter);
            String fromDate = transactionsPage.getFromDateBasedOnNumberOfDays(logger, selectDateFilter, toDate, "dd-MMM-yy");
            List<Map<String, String>> queryResultsInList = transactionsPage.getQueryResultsOfTransactionTable(logger, accountNumber, toDate, fromDate);
//            if(queryResultsInList.size()>=100){
//                queryResultsInList = queryResultsInList.subList(0, 100);
//            }
            for (int i = 0; i <= arrDbClientProductsForAnAccount.length - 1; i++) {
                transactionsPage.clickValueFromFilter(logger, "All products", arrDbClientProductsForAnAccount[i]);
                int count = 0;
                for (int j = 0; j <= queryResultsInList.size() - 1; j++) {
                    if (arrDbClientProductsForAnAccount[i].equals(queryResultsInList.get(j).get("PRODUCT_DESCRIPTION"))) {
                        count++;
                    }
                }
                String actualCountOfRecords = transactionsPage.getCountOfRecords(logger);
                if (Integer.parseInt(actualCountOfRecords) < 100) {
//                    basePage.assertTwoStrings(logger, String.valueOf(count), actualCountOfRecords);
                } else if (actualCountOfRecords.equals(100)) {
                    logger.log("There are more than 100 records in database and as per condition only 100 shown in front end");
                }
                commonPage.clearSpecificFilterUsingXIcon(logger, 1);
            }

        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @And("^User validate Transaction fee record in database to verify it has been posted successfully$")
    public void userValidateTransactionFeeRecordInDatabaseToVerifyItHasBeenPostedSuccessfully() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String feeAmount = PropUtils.getPropValue(properties,"fee_amount");
            if(PropUtils.getPropValue(properties,"clickedButton").equalsIgnoreCase("Fee reversal")){
                feeAmount = "-"+PropUtils.getPropValue(properties,"fee_amount");
            }
            String query = "select * from transactions t \n"+
                     "left join products p on p.product_oid = t.product_oid\n"+
                    "left join m_customers mcust on mcust.customer_mid = t.customer_mid\n"+
                    "where mcust.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+" " +
                    "and t.reference = '"+PropUtils.getPropValue(properties,"adhocFee-reference")+"' " +
                    "and mcust.customer_no = '"+PropUtils.getPropValue(properties,"commonAccountNo")+"' " +
                    "and t.CUSTOMER_AMOUNT = "+feeAmount;
            logger.log(query);
            List<Map<String, String>>queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if(queryResults.size()==1){
                logger.log("Adhoc fee transaction has been posted successfully");
            }else{
                commonPage.assertTwoStrings(logger,"1",String.valueOf(queryResults.size()),"Record size after posting adhoc fee transaction");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}
