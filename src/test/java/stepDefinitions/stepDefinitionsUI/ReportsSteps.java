package stepDefinitions.stepDefinitionsUI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import pages.CommonPage;
import pages.LoginPage;
import pages.ReportsPage;
import pages.UsersPage;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ReportsSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private ReportsPage reportsPage;
    private CommonPage commonPage;
    private ExcelUtils excelUtils;
    List<Map<String, String>> queryResults;
    public Scenario logger;
    private DriverFactory driverFactory;

    public ReportsSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.driver = driverFactory.driver;
        this.logger = hooks.logger;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        reportsPage = new ReportsPage(driver);
        commonPage = new CommonPage(driver);
        excelUtils = new ExcelUtils(driver);
    }

    @Then("^User validate Report category drop down value should be pre-selected if it has one value, else user need to select value from list$")
    public void userValidateReportCategoryDropDownBehavior() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            queryResults = reportsPage.validateReportCategoryDropDownBehavior(logger, queryResults);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
        commonPage.sleepForFewSeconds(2);
    }

    @Then("^User validate tool tip \"(.*)\" for \"(.*)\" if \"(.*)\" contains more than one value which has \"(.*)\" and \"(.*)\"$")
    public void userValidateToolTipOFReportDropDownsForFieldsWhichHasMoreThanOneValue(String toolTip, String fields, String reportDropDown, String onlineScheduledValue, String adhocValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (reportDropDown.equalsIgnoreCase("Report category")) {
                queryResults = reportsPage.getReportCategories(logger, onlineScheduledValue, adhocValue);
            } else {
                queryResults = reportsPage.getReportTypes(onlineScheduledValue, adhocValue);
            }
            if (queryResults.size() > 0) {
                reportsPage.validateToolTipOfFields(logger, fields, reportDropDown, toolTip, queryResults);
            } else {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "No Report category or Report types available for selected criteria", baseUtils.testDataFilePath);
            }
        } else {
            logger.log("No Report Category or Report types available for selected criteria");
        }
    }

    @And("User click on Reports sub menu \"(.*)\"$")
    public void userClickOnReportsSubMenu(String menuName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.clickOnReportSubmenu(logger, menuName);
            PropUtils.setProps("testStatus", "", baseUtils.testDataFilePath);
        } else {
            logger.log("No Report Category or Report types available for selected criteria");
        }
    }

    @And("User validate header of module \"(.*)\"$")
    public void userValidateHeaderOfModule(String moduleHeaderName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.validateModuleHeader(logger, moduleHeaderName);
        } else {
            logger.log("Scenario is getting skipped");
        }
    }

    @And("^User handle delivery stepper based on report radio button \"(.*)\" and action \"(.*)\"$")
    public void userHandleDeliveryStepperBasedOnReportRadioButton(String radioButtonName, String actionOfReport) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (actionOfReport.equalsIgnoreCase("Email notification only")) {
                actionOfReport = "Suppress With Notification";
            }
            reportsPage.handleReportDeliveryStepper(logger, radioButtonName, actionOfReport);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User handle delivery stepper in Edit report template page$")
    public void userHandleDeliveryStepperInEditReportTemplatePage() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.updateReportName(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate newly created report or updated report \"(.*)\" has been saved in the database or not for field \"(.*)\"$")
    public void userValidateNewlyCreatedReportOrUpdatedReportHasBeenSavedInTheDatabaseOrNot(String propVariable, String dbFieldName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if(basePage.getStatusOfElement(By.xpath("//div[contains(text(),'Validation failed')]"))){
                commonPage.assertTwoStrings(logger,"Report has been created successfully", "Validation failed","Successmessage");
            }else {
                String query = "select ra." + dbFieldName + " from report_assignments ra\n" +
                        "inner join report_types rt on rt.report_type_oid = ra.report_type_oid\n" +
                        "inner join users u on u.user_oid = ra.user_oid\n" +
                        "where u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName") + "' and ra.description = '" +
                        PropUtils.getPropValue(properties, "newReportName") + "'";
                List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                if (queryResults.size() == 1) {
                    logger.log("Report name has been saved successfully in " + PropUtils.getPropValue(properties, propVariable));
                } else {
                    for (int i = 0; i <= queryResults.size() - 1; i++) {
                        logger.log("ReportName is : " + queryResults.get(i).get(dbFieldName));
                    }
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User validate the success message or error message of adding report and validate header of module for action \"(.*)\", radio button \"(.*)\"$")
    public void userValidateTheSuccessMessageOrErrorMessageOfAddingReport(String actionOfReport, String selectedRadioButton) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.validateSuccessOrErrorMessageOfAddReport(logger, actionOfReport, selectedRadioButton);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("User get newReportName from db$")
    public void userGetNewReportNameFromDB() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select ra.description from report_assignments ra\n" +
                    "inner join report_types rt on rt.report_type_oid = ra.report_type_oid\n" +
                    "inner join users u on u.user_oid = ra.user_oid\n" +
                    "where u.logon_id = '" +
                    PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName") +
                    "' and (ra.last_updated_at = sysdate or ra.last_updated_at > sysdate-30)";
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() >= 1) {
                PropUtils.setProps("newReportName", queryResults.get(0).get("DESCRIPTION"), baseUtils.testDataFilePath);
            } else {
                PropUtils.setProps("testStatus", "SKipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "No report is available to execute this scenario", baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify \"(.*)\" filter value is selected as \"(.*)\" of \"(.*)\"$")
    public void userVerifyFilterValuesSelectedAsExpectedOrNot(String filterNumber, String valueType, String filedValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        Boolean isPropValue = false;
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (valueType.equalsIgnoreCase("propValue")) {
                isPropValue = true;
            }
            reportsPage.validateFilterValueBasedOnNoOfFilter(logger, Integer.parseInt(filterNumber), isPropValue, filedValue);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User get report from database then search and validate the report in module \"(.*)\"$")
    public void userGetReportFromDatabaseThenSearchAndValidateTheReport(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.searchAndValidateReport(logger, moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User get a customer which has invoices$")
    public void userGetACustomerWhichHasInvoices() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select distinct mcust.customer_no from stored_reports sr\n" +
                    "inner join m_customers mcust on mcust.customer_mid = sr.member_oid\n" +
                    "inner join user_members um on um.member_oid = mcust.customer_mid\n" +
                    "inner join users u on u.user_oid = um.user_oid\n" +
                    "and sr.created_on <=sysdate and sr.report_type_oid = 2628 \n" +
                    "and sr.file_name like '%orig%' and sr.member_type_cid = 103 and u.logon_id = '" +
                    PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName") + "'";
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() >= 1) {
                PropUtils.setProps("accountNo", queryResults.get(0).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
            } else {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "No customer is available", baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate downloaded file name is equal to \"(.*)\" value of \"(.*)\"$")
    public void userValidateDownloadedFileNameIsEqualToPropertyValueOrNot(String valueType, String propFileName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            File files = new File(System.getProperty("user.home") + "/Downloads/");
            File file = commonPage.getLastModifiedFile(logger, files);
            String fileName = "";
            if (valueType.equalsIgnoreCase("property")) {
                fileName = PropUtils.getPropValue(properties, propFileName);
            } else {
                List<Map<String, String>> invoices = reportsPage.getInvoicesInDescendingOrder();
                fileName = invoices.get(0).get("FILE_NAME").split("/")[0];
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select another status \"(.*)\" for module \"(.*)\"$")
    public void userSelectAnotherStatusForModule(String status, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            reportsPage.selectStatusInChangeStatusForm(logger, status);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }


    @And("^User enter search keywords \"(.*)\" in Reports module$")
    public void userEnterSearchKeywordsInReportsModule(String searchKeyWords) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (searchKeyWords.equalsIgnoreCase("GetLatestValueFromPropertyFile")) {
                reportsPage.enterSearchKeyWords(logger, PropUtils.getPropValue(properties, PropUtils.getPropValue(properties, "Success-ReportName-" + PropUtils.getPropValue(properties, "Reports-reportType").replaceAll("\\s+", ""))));
            } else {
                reportsPage.enterSearchKeyWords(logger, PropUtils.getPropValue(properties, searchKeyWords));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate report data by applying filters and date of report creation$")
    public void userValdiateReportDataByApplyingFiltersAndDateOfReportCreation() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String reportTypes = reportsPage.getReportTypesInStringArray("Reports");
            String[] arrReportTypes = {};
            arrReportTypes = reportTypes.split(",");
            String[] arrReportDateRange = {"1 day", "Last 7 days", "Last 14 days", "Last 30 days"};
            logger.log("All Date filter values " + arrReportDateRange.length);
            String query = "";
            String reportTypesWithSingleQuotes = "";
            String expRecordsCount = "";
            String actualRecordsCount = "";
            String countOfRecords = "";
            String selectFilter = "";
            for (int i = 0; i <= arrReportDateRange.length - 1; i++) {
                if (arrReportDateRange[i].equalsIgnoreCase("1 day")) {
                    selectFilter = arrReportDateRange[i].substring(0, 1);
                } else if (arrReportDateRange[i].equalsIgnoreCase("Last 30 days")) {
                    selectFilter = "30";
                } else {
                    selectFilter = arrReportDateRange[i].substring(5, 7);
                }
                for (int j = 0; j <= arrReportTypes.length - 1; j++) {
                    if (!arrReportDateRange[i].equals("Last 30 days")) {
                        reportsPage.selectFilter(logger, arrReportDateRange[i], 2);
                    }
                    if (i == arrReportDateRange.length - 1) {
                        reportsPage.clickValueFromFilter(logger, "All report types", arrReportTypes[j]);
                        reportTypesWithSingleQuotes = reportsPage.getReportFilterValuesInCommaSeparatedWithSingleQuotes(arrReportTypes[j]);
                    } else if (j == arrReportTypes.length - 1) {
                        reportsPage.clickValueFromFilter(logger, "All report types", arrReportTypes[j]);
                        reportTypesWithSingleQuotes = reportsPage.getReportFilterValuesInCommaSeparatedWithSingleQuotes(arrReportTypes[j]);
                    } else {
                        reportsPage.clickValueFromFilter(logger, "All report types", arrReportTypes[j]);
                        reportTypesWithSingleQuotes = reportsPage.getReportFilterValuesInCommaSeparatedWithSingleQuotes(arrReportTypes[j]);
                    }
                    query = "select count(*) from stored_reports sr\n" +
                            "inner join report_types rt on rt.report_type_oid = sr.report_type_oid\n" +
                            "inner join user_members um on um.member_oid = sr.member_oid\n" +
                            "inner join users u on u.user_oid = um.user_oid\n" +
                            "where  rt.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")
                            +" and rt.member_type_cid = 103 and u.logon_id = '"+
                            PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile),"currentUserName")+"'\n" +
                            "and sr.created_on <=sysdate and sr.created_on >=sysdate-" + selectFilter;
                    Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
                    countOfRecords = basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'view-page-list')]"));
                    actualRecordsCount = countOfRecords.substring(0, countOfRecords.indexOf(' '));
                    expRecordsCount = queryResults.get("COUNT(*)");
                    commonPage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount, "Count of report type " + reportTypesWithSingleQuotes + " and date range '" + selectFilter + "'");
                    logger.log("Date value is selected as " + arrReportDateRange[i]);
                    if (arrReportDateRange[i].equals("Last 30 days")) {
                        commonPage.clearSpecificFilterUsingXIcon(logger, 1);
                    } else {
                        usersPage.clickResetAllFilters(logger);
                    }
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate report template data by applying filters and date of report template creation$")
    public void userValidateReportTemplateDataByApplyingFiltersAndDateOfReportTemplateCreation() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String reportTypes = reportsPage.getReportTypesInStringArray("ReportTemplates");
            String[] arrReportTypes = {};
            arrReportTypes = reportTypes.split(",");
            String[] arrReportStatuses = {"Active", "On hold"};
            String query = "";
            String reportStatusIds = "";
            String reportTypesWithSingleQuotes = "";
            String expRecordsCount = "";
            String actualRecordsCount = "";
            String countOfRecords = "";
            for (int i = 0; i <= arrReportStatuses.length - 1; i++) {
                for (int j = 0; j <= arrReportTypes.length - 1; j++) {
                    if (i == arrReportStatuses.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All statuses", arrReportStatuses[i]);
                        reportStatusIds = reportsPage.getReportStatusIdsFromDatabase(arrReportStatuses[i]);
                        usersPage.selectValuesFromFilter(logger, "All types", arrReportTypes[j]);
                        reportTypesWithSingleQuotes = reportsPage.getReportFilterValuesInCommaSeparatedWithSingleQuotes(arrReportTypes[j]);
                    } else if (j == arrReportTypes.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All statuses", arrReportStatuses[i]);
                        reportStatusIds = reportsPage.getReportStatusIdsFromDatabase(arrReportStatuses[i]);
                        usersPage.selectValuesFromFilter(logger, "All types", reportTypes);
                        reportTypesWithSingleQuotes = reportsPage.getReportFilterValuesInCommaSeparatedWithSingleQuotes(reportTypes);
                    } else {
                        usersPage.selectValuesFromFilter(logger, "All statuses", arrReportStatuses[i]);
                        reportStatusIds = reportsPage.getReportStatusIdsFromDatabase(arrReportStatuses[i]);
                        usersPage.selectValuesFromFilter(logger, "All types", arrReportTypes[j]);
                        reportTypesWithSingleQuotes = reportsPage.getReportFilterValuesInCommaSeparatedWithSingleQuotes(arrReportTypes[j]);
                    }
                    query = "select count(*) from report_assignments ra\n" +
                            "inner join report_types rt on rt.report_type_oid = ra.report_type_oid\n" +
                            "inner join users u on u.user_oid = ra.user_oid\n" +
                            "where u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName")
                            + "' and ra.created_on <=sysdate and ra.created_on >sysdate-30\n" +
                            "and ra.IS_ENABLED in (" +
                            reportStatusIds + ")";
                    logger.log(query);
                    Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
                    countOfRecords = basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'view-page-list')]"));
                    expRecordsCount = countOfRecords.substring(0, countOfRecords.indexOf(' '));
                    actualRecordsCount = queryResults.get("COUNT(*)");
                    basePage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount);
                    List<WebElement>allElemts = basePage.getListOfElements(logger,By.cssSelector("div[class='filter-drop-button-content']>span>mav-svg-icon>fa-icon[ng-reflect-icon='fal,times']"));
                    for(int ele=0;ele<=allElemts.size()-1;ele++){
                        allElemts.get(ele).click();
                    }
//                    if (basePage.getStatusOfElement(By.xpath("//div[contains(text(),'Reset all filters')]"))) {
//                        usersPage.clickResetAllFilters(logger);
//                    } else {
//                        if(basePage.getStatusOfElement(By.cssSelector("div[class='filter-drop-button-content']>span>mav-svg-icon>fa-icon[ng-reflect-icon='fal,times']"))){
//                            basePage.userClick(logger,By.cssSelector("div[class='filter-drop-button-content']>span>mav-svg-icon>fa-icon[ng-reflect-icon='fal,times']"));
//                        }
//                    }
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate the count of reports displayed in UI for date filter type \"(.*)\" in the position \"(.*)\" with the database$")
    public void userValidateTheCountOfTransactionsDisplayedInUIWithTheDatabase(String selectFilter, String filterPosition) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String toDate = "";
            String fromDate = "";
            reportsPage.selectFilter(logger, selectFilter, Integer.parseInt(filterPosition));
            logger.log(selectFilter);
            int value = Integer.parseInt(selectFilter.substring(5, 7).trim()) + 1;
            logger.log(String.valueOf(value));
            String query = "select count(*) from report_assignments ra\n" +
                    "inner join report_types rt on rt.report_type_oid = ra.report_type_oid\n" +
                    "inner join users u on u.user_oid = ra.user_oid\n" +
                    "where u.logon_id = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "currentUserName")
                    + "' and ra.created_on <=sysdate and ra.created_on >sysdate-" + value;
            logger.log(query);
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            commonPage.sleepForFewSeconds(2);
            String countOfRecords = basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'view-page-list')]"));
            String actualRecordsCount = countOfRecords.substring(0, countOfRecords.indexOf(' '));
            logger.log(countOfRecords);
            String expRecordsCount = queryResults.get("COUNT(*)");
            commonPage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount, selectFilter);
//            basePage.userClick(logger,By.cssSelector("mav-svg-icon[class='margin-bottom3 mat-icon ng-star-inserted']"));
//            commonPage.clearSpecificFilterUsingXIcon(logger, 3);
            List<WebElement>allElemts = basePage.getListOfElements(logger,By.cssSelector("div[class='filter-drop-button-content']>span>mav-svg-icon>fa-icon[ng-reflect-icon='fal,times']"));
            for(int ele=0;ele<=allElemts.size()-1;ele++){
                allElemts.get(ele).click();
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("User validate field values in Add filters stepper for selected report type and radio button \"(.*)\"$")
    public void userValidateFieldValuesInAddFiltersStepperForSelectedReportType(String selectedRadioButtonName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            Map<String, String> labelsAndTheirTypes = excelUtils.getCellValueBasedOnAnotherCellValue(System.getProperty("templates.folder.path") + "/Report_Templates_params.xlsx", 0, 1, reportsPage.getLabelsForSelectedReportType(logger));
            if (labelsAndTheirTypes.size() >= 1) {
                reportsPage.handleReportLabels(logger, labelsAndTheirTypes, selectedRadioButtonName);
            } else {
                logger.log("No report label params are availabel for selected report");
                if (basePage.whetherElementPresent(logger, By.xpath("//div[contains(text(),'No additional filters available for the report type selected')]"))) {
                    logger.log("Warning message is displayed because no report params are available");
                    logger.log("Warning message is : 'No additional filters available for the report type selected'");
                }
            }
        } else {
            logger.log("Previous step got skipped, Hence current step is also will be skipped");
        }
    }

    @And("User validate \"(.*)\" drop down is enabled when \"(.*)\" is selected, Then user selects one value \"(.*)\"$")
    public void userValidateFrequencyDropDownStatusAndSelectsOneValue(String dropDownName, String radioButtonName, String frequencyDropValue) {
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
        if (getPreviousStepStatus.equalsIgnoreCase("skipped")) {
            logger.log("Report Type is not present for desired flag values");
        } else {
            if (radioButtonName.equalsIgnoreCase("Schedule as a recurring report")) {
                if (PropUtils.getPropValue(testDataProp, "report-radioButtonName").equalsIgnoreCase(radioButtonName)) {
                    basePage.userClick(logger, By.cssSelector("mav-select[name='" + dropDownName + "']"));
                    commonPage.clickButtonUsingSpan(logger, frequencyDropValue);
                }
            } else {
                logger.log("Frequency is not required because radio button '" + radioButtonName + "' selected");
            }
        }
    }

    @When("^User validate Report type drop down value should be pre-selected if it has one value, else user need to select value which has \"(.*)\" and \"(.*)\"$")
    public void userValidateReportCategoryDropDownAndItsFieldValues(String isOnLineValue, String isAdhocValue) {
        Boolean isReportTypePresent = true;
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
        if (getPreviousStepStatus.equalsIgnoreCase("skipped")) {
            logger.log("Report Type is not present for desired flag values");
            logger.log("Report Type is not present for desired flag values");
        } else {
            isReportTypePresent = reportsPage.validateReportTypeDropDownBehavior(logger, isOnLineValue, isAdhocValue, queryResults);
            if (!isReportTypePresent) {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
            }
        }
    }

    @When("^User validate \"(.*)\" radio button must be auto selected for selected Report type else select \"(.*)\" radio option$")
    public void userValidateRadioButtonMustBeAutoSelectedBasedOnFlag(String radioButtonName, String selectRadioButtonName) {
        List<WebElement> allRadioButtonLabels = null;
        List<WebElement> allRadioButtons = null;
        Properties testDataProp = PropUtils.getProps(baseUtils.testDataFile);
        commonPage.sleepForFewSeconds(3);
        String getPreviousStepStatus = PropUtils.getPropValue(testDataProp, "testStatus");
        if (getPreviousStepStatus.equalsIgnoreCase("skipped")) {
            logger.log("Report Type is not present for desired flag values");
        } else {
            if (!radioButtonName.equalsIgnoreCase("NoRadioButtons")) {
                if (radioButtonName.equalsIgnoreCase(basePage.userGetTextFromWebElement(logger, By.cssSelector("mat-radio-button[class='mat-radio-button mat-accent mat-radio-checked']>label>div[class='mat-radio-label-content']")))) {
                    logger.log(radioButtonName + " is selected by default");
                    PropUtils.setProps("report-radioButtonName", radioButtonName, baseUtils.testDataFilePath);
                } else {
                    logger.log(radioButtonName + " is not selected by default");
                }
            } else {
                commonPage.sleepForFewSeconds(2);
                commonPage.clickUsingJSExecutor(logger, basePage.getWebElementUsingLocator(logger, By.xpath("//div[contains(text(),'" + selectRadioButtonName + "')]")));
                logger.log("radio button " + selectRadioButtonName + " has been selected successfully");
//                    WebElement element = driver.findElement(By.cssSelector("mat-radio-button[ng-reflect-value='"+selectRadioButtonName+"']"));
//                    commonPage.clickAndPerformUsingActions(element);
//                    allRadioButtonLabels = basePage.getListOfElements(logger, By.cssSelector("mat-radio-button[class='mat-radio-button mat-accent']>label>div[class='mat-radio-label-content']"));
//                    allRadioButtons = basePage.getListOfElements(logger, By.cssSelector("mat-radio-button[class='mat-radio-button mat-accent']>label>div[class='mat-radio-container']>input"));
//                    for (int i = 0; i < allRadioButtonLabels.size() - 1; i++) {
//                        if (allRadioButtonLabels.get(i).getText().equalsIgnoreCase(selectRadioButtonName)) {
//                            commonPage.clickUsingJSExecutor(logger,allRadioButtons.get(i));
//                        }
//                    }
            }
        }
    }

}
