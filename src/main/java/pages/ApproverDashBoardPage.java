package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ApproverDashBoardPage {

    private WebDriver driver;
    private BasePage basePage;
    private CommonDBUtils commonDBUtils;
    private BaseUtils baseUtils;
    private CommonPage commonPage;
    private CostCentresPage costCentresPage;
    private UsersPage usersPage;
    Map<String, String> queryResultsOfVelocityAssignments = null;
    public Scenario logger;

    public ApproverDashBoardPage(WebDriver driver) {
        this.driver = driver;
        basePage = new BasePage(driver);
        commonDBUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        commonPage = new CommonPage(driver);
        costCentresPage = new CostCentresPage(driver);
        usersPage = new UsersPage(driver);

    }

    private static final By threeDotsWithPlus = By.xpath("//fa-icon[@ng-reflect-icon='far,ellipsis-v']");
    private static final By elipsisIcon = By.xpath("//div[2][@class='ng-star-inserted']//div[normalize-space(text())='Update Account Workflow' ]/following::fa-icon");
    private static final By elipsisIcon1 = By.xpath("//div[@class='ng-star-inserted']//div[normalize-space(text())='Update Tax Number Workflow' ]//following::fa-icon[@ng-reflect-icon='far,ellipsis-v']");
    //Properties properties = PropUtils.getProps(baseUtils.testDataFile);

    public void clickSortByHighest() {
        basePage.userClick(logger, By.xpath("//span[normalize-space(text())='Sort']"));
        //commonPage.sleepForFewSeconds(2);
        basePage.userClick(logger, By.xpath("//div[normalize-space(text())='Highest']"));
    }

    String custNO;

    public void clickElipsisInDashBoard(Scenario logger, String expWorkFlow, String flag, String user) {
        List<Map<String, String>> allRowsOfQueryResults = new ArrayList<>();
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String countFromUI = null;
        String userCondition = "";
        if (flag.equalsIgnoreCase("is")) {
            if(expWorkFlow.equalsIgnoreCase("Customer Hierarchy")) {
                userCondition = "  and wf.LAST_UPDATED_BY = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), user) + "'";
            }else{
                userCondition = "  and fsc.LAST_UPDATED_BY = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), user) + "'";
            }
        } else if (flag.equalsIgnoreCase("isnot")) {
            if(expWorkFlow.equalsIgnoreCase("Customer Hierarchy")) {
                userCondition = "  and wf.LAST_UPDATED_BY != '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), user) + "'";
            }else{
                userCondition = "  and fsc.LAST_UPDATED_BY != '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), user) + "'";
            }
        }
        String reqWorkFlow = null;
        String workflow = expWorkFlow.substring(7);
        reqWorkFlow = workflow.substring(0, workflow.length() - 9);
        String query = "";
        if(expWorkFlow.equalsIgnoreCase("Customer Hierarchy")){
            query = "select distinct wf.wf_hierarchy_oid,wf.description, mcust.name,mcust.customer_no,wf.LAST_UPDATED_AT,wf.LAST_UPDATED_BY,wfra.EFFECTIVE_ON from wf_hierarchies wf\n" +
                    "inner join wf_relationships wfr on wfr.WF_HIERARCHY_OID = wf.WF_HIERARCHY_OID\n" +
                    "inner join wf_relationship_assignments wfra on wfra.WF_RELATIONSHIP_OID = wfr.WF_RELATIONSHIP_OID and wfra.WF_RELATIONSHIP_ASSIGNMENT_OID = wfr.WF_RELATIONSHIP_OID\n" +
                    "inner join m_customers mcust on mcust.customer_mid = wf.owning_member_oid\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n"+
                    "where mc.client_mid = "+PropUtils.getPropValue(properties, "ClientMID")
                    +" and wf.APPROVAL_STATUS_CID in (select constant_oid from constants where description = 'Pending' and constant_type_oid =\n" +
                    "(select constant_type_oid from constant_types where table_name = 'APPROVAL_STATUS'))\n" +
                    "and wf.RECORD_STATUS_CID in (select constant_oid from constants where description = 'Valid' and constant_type_oid =\n" +
                    "(select constant_type_oid from constant_types where table_name = 'RECORD_STATUS')) "+userCondition;
        }else {
            query = "select fsc.LAST_UPDATED_BY,fsc.LAST_UPDATED_AT, fsc.field_set_control_oid, fsc.effective_on, mcust.customer_no, mcust.name from field_set_controls fsc\n" +
                    "left join m_customers mcust on mcust.customer_mid = fsc.customer_mid\n" +
                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "left join field_groups fg on fg.field_group_oid = fsc.field_group_oid\n" +
                    "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID") +
                    " and fg.description = '" + reqWorkFlow + " - Date Effective approvals'\n" +
                    "and fsc.FIELD_SET_CTRL_STATUS_CID in (select constant_oid from constants where description = 'New')\n" +
                    "and fsc.APPROVAL_STATUS_CID in (select constant_oid from constants where description in ('Pending'))" + userCondition;
        }
        allRowsOfQueryResults.addAll(commonDBUtils.getAllRowsOfQueryResultsOnListMap(query));
        if (allRowsOfQueryResults.size() == 0) {
            PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
            PropUtils.setProps("skipReason", "No Record is available to execute this scenario", baseUtils.testDataFilePath);
        } else {
            System.out.println(allRowsOfQueryResults.get(0).get("CUSTOMER_NO"));
            custNO = allRowsOfQueryResults.get(0).get("CUSTOMER_NO");
            PropUtils.setProps("commonAccountNo", custNO, baseUtils.testDataFilePath);
            PropUtils.setProps("commonCustomerName", allRowsOfQueryResults.get(0).get("NAME"), baseUtils.testDataFilePath);
            String custName = allRowsOfQueryResults.get(0).get("NAME");
            PropUtils.setProps("lastUpdatedBy", allRowsOfQueryResults.get(0).get("LAST_UPDATED_BY"), baseUtils.testDataFilePath);
            String[] lastUpdatedAtArr = allRowsOfQueryResults.get(0).get("LAST_UPDATED_AT").split(" ")[0].split("-");
            PropUtils.setProps("lastUpdatedAt", lastUpdatedAtArr[2] + "/" + lastUpdatedAtArr[1] + "/" + lastUpdatedAtArr[0], baseUtils.testDataFilePath);

            if(expWorkFlow.equalsIgnoreCase("Customer Hierarchy")){
                commonPage.clickButtonUsingSpecificTagName(logger,"Hierarchy","div");
                commonPage.sleepForFewSeconds(3);
                PropUtils.setProps("hierarchyName",allRowsOfQueryResults.get(0).get("DESCRIPTION"),baseUtils.testDataFilePath);
            }else{
                PropUtils.setProps("fieldSetControlOid", allRowsOfQueryResults.get(0).get("FIELD_SET_CONTROL_OID"), baseUtils.testDataFilePath);
            }
            String[] effectiveAtArr = allRowsOfQueryResults.get(0).get("EFFECTIVE_ON").split(" ")[0].split("-");
            PropUtils.setProps("effectiveDate", effectiveAtArr[2] + "/" + effectiveAtArr[1] + "/" + effectiveAtArr[0], baseUtils.testDataFilePath);
            getWorkflowRecordDataBasedOnFieldSetControlOid(reqWorkFlow);
            properties = PropUtils.getProps(baseUtils.testDataFile);
            List<WebElement> threeDotIcon = basePage.getListOfElements(logger, By.xpath("(//mat-expansion-panel/mat-expansion-panel-header/span/div)/div/div/button"));
            List<WebElement> records = basePage.getListOfElements(logger, By.xpath("(//mat-expansion-panel/mat-expansion-panel-header/span/div)/div/mat-grid-list"));
            List<WebElement> fields = new ArrayList<>();
            for (int i = 0; i <= records.size() - 1; i++) {
                fields.clear();
                fields.addAll(basePage.getListOfElements(logger, By.xpath("(//mat-expansion-panel/mat-expansion-panel-header/span/div/div/mat-grid-list)[" + (i + 1) + "]/div/mat-grid-tile/figure/div/div")));
                if (reqWorkFlow.equalsIgnoreCase("Tax Number")) {
                    if (fields.get(0).getText().equalsIgnoreCase("Update Tax Number Workflow") && fields.get(1).getText().equalsIgnoreCase(custName) && fields.get(2).getText().equalsIgnoreCase(PropUtils.getPropValue(properties, "effectiveDate")) && fields.get(3).getText().equalsIgnoreCase(custNO)) {
                        basePage.clickUsingActions(logger, threeDotIcon.get(i));
                        break;
                    }
                } else if (reqWorkFlow.equalsIgnoreCase("Customer")) {
                    if (fields.get(0).getText().equalsIgnoreCase("Update Customer Workflow") && fields.get(1).getText().equalsIgnoreCase(custName) && fields.get(2).getText().equalsIgnoreCase(PropUtils.getPropValue(properties, "effectiveDate")) && fields.get(3).getText().equalsIgnoreCase(custNO)) {
                        basePage.clickUsingActions(logger, threeDotIcon.get(i));
                        break;
                    }
                } else if(reqWorkFlow.equalsIgnoreCase("Account")){
                    if (fields.get(0).getText().equalsIgnoreCase("Update Account Workflow") && fields.get(1).getText().equalsIgnoreCase(custName) && fields.get(2).getText().equalsIgnoreCase(PropUtils.getPropValue(properties, "effectiveDate")) && fields.get(3).getText().equalsIgnoreCase(custNO)) {
                        basePage.clickUsingActions(logger, threeDotIcon.get(i));
                        break;
                    }
                } else if(expWorkFlow.equalsIgnoreCase("Customer Hierarchy")){
                    if(fields.get(0).getText().equalsIgnoreCase(PropUtils.getPropValue(properties,"hierarchyName")) && fields.get(1).getText().equalsIgnoreCase(custName) && fields.get(2).getText().equalsIgnoreCase(PropUtils.getPropValue(properties,"effectiveDate")) && fields.get(3).getText().equalsIgnoreCase(custNO)){
                        basePage.clickUsingActions(logger, threeDotIcon.get(i));
                        break;
                    }
                }
            }
//            try {
//                List<WebElement> elements = driver.findElements(By.className("ng-star-inserted"));
//                int count = elements.size();
//                for (int index = 1; index <= count - 1; index++) {
//                    WebElement element = driver.findElement(By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::div[7] "));
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//
//                    String actWorkFlow = driver.findElement(By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::div[2]")).getText();
//                    String actAccountNum = driver.findElement(By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::div[7] ")).getText();
//
//                    if (actWorkFlow.equalsIgnoreCase(expWorkFlow) && actAccountNum.equalsIgnoreCase(custNO)) {
//                        System.out.println(actWorkFlow + actAccountNum);
//                        commonPage.sleepForFewSeconds(3);
//                        System.out.println("Perform click on elipses");
//                        basePage.userClickJSExecutor(logger, By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::fa-icon[" + 1 + "]"));
//                        commonPage.sleepForFewSeconds(5);
//                        break;
//                    }
//
//                }
//            } catch (Exception e) {
//                logger.log("Unable to get the reports count from UI ");
//            }
        }
    }

    /*
    Method to get tax record data based on field set control oid
     */
    public void getWorkflowRecordDataBasedOnFieldSetControlOid(String workFlow) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if(!workFlow.equalsIgnoreCase("Customer Hierarchy")) {
            String taxQuery = "select fgf.field_name, fscv.value from field_group_fields fgf\n" +
                    "inner join field_set_control_values fscv on fscv.field_group_field_oid = fgf.field_group_field_oid\n" +
                    "where fgf.field_group_oid = (select field_group_oid from field_groups where description = '" + workFlow + " - Date Effective approvals')\n" +
                    "and fscv.field_set_control_oid = " + PropUtils.getPropValue(properties, "fieldSetControlOid");
            List<Map<String, String>> queryResults = commonDBUtils.getAllRowsOfQueryResultsOnListMap(taxQuery);
            if (workFlow.equalsIgnoreCase("Tax Number")) {
                for (int i = 0; i <= queryResults.size() - 1; i++) {
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("country_oid")) {
                        Map<String, String> qr = commonDBUtils.getQueryResultsOnMap("select description,country_code from countries where country_oid = " + queryResults.get(i).get("VALUE"));
                        PropUtils.setProps("country", qr.get("DESCRIPTION"), baseUtils.testDataFilePath);
                        PropUtils.setProps("countryCode", qr.get("COUNTRY_CODE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("tax_no")) {
                        PropUtils.setProps("taxNo", queryResults.get(i).get("VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("effective_on")) {
                        PropUtils.setProps("effectiveDate", queryResults.get(i).get("VALUE").split(" ")[0], baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("expires_on")) {
                        PropUtils.setProps("expiryDate", queryResults.get(i).get("VALUE").split(" ")[0], baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("tax_type_cid")) {
                        PropUtils.setProps("taxType", commonDBUtils.getQueryResultsOnMap("select description from constants where constant_oid = " + queryResults.get(i).get("VALUE")).get("DESCRIPTION"), baseUtils.testDataFilePath);
                    }
                }
            } else if (workFlow.equalsIgnoreCase("Customer")) {
                for (int i = 0; i <= queryResults.size() - 1; i++) {
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("name")) {
                        PropUtils.setProps("Accountname", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("customer_type_did")) {
                        Map<String, String> qr = commonDBUtils.getQueryResultsOnMap("select description from customer_types where description_oid = " + queryResults.get(i).get("VALUE"));
                        PropUtils.setProps("Customertype", qr.get("DESCRIPTION"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("streetAddressVO.address_line")) {
                        PropUtils.setProps("Physicalstreetaddress", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("streetAddressVO.postal_code")) {
                        PropUtils.setProps("Physicalpostalcode", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("streetAddressVO.country_oid")) {
                        Map<String, String> qr = commonDBUtils.getQueryResultsOnMap("select description,country_code from countries where country_oid = " + queryResults.get(i).get("VALUE"));
                        PropUtils.setProps("Physicalcountry", qr.get("COUNTRY_CODE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("contact_name")) {
                        PropUtils.setProps("Contactname", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("email_address")) {
                        PropUtils.setProps("Emailaddress", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("phone_mobile_1")) {
                        PropUtils.setProps("Phone", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("phone_mobile_2")) {
                        PropUtils.setProps("Otherphone", commonPage.getSpecificValueFromMapObject(queryResults.get(i), "VALUE"), baseUtils.testDataFilePath);
                    }
                }
            } else if (workFlow.equalsIgnoreCase("Account")) {
                for (int i = 0; i <= queryResults.size() - 1; i++) {
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("billing_plan_oid")) {
                        PropUtils.setProps("BillingPlan", commonDBUtils.getQueryResultsOnMap("select description from billing_plans where billing_plan_oid = " + queryResults.get(i).get("VALUE")).get("DESCRIPTION"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("billing_frequency_oid")) {
                        PropUtils.setProps("BillingFrequency", commonDBUtils.getQueryResultsOnMap("select description from frequencies where frequency_oid = " + queryResults.get(i).get("VALUE")).get("DESCRIPTION"), baseUtils.testDataFilePath);
                    }
                    if (queryResults.get(i).get("FIELD_NAME").equalsIgnoreCase("cycle_frequency_oid")) {
                        PropUtils.setProps("AccountCycle", commonDBUtils.getQueryResultsOnMap("select description from frequencies where frequency_oid = " + queryResults.get(i).get("VALUE")).get("DESCRIPTION"), baseUtils.testDataFilePath);
                    }
                }
            }
        }
    }

    public void clickElipsisInDashBoard(Scenario logger, String flag, String user) {
        String customerNum;
        List<Map<String, String>> allRowsOfQueryResults = new ArrayList<>();
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String countFromUI = null;
        String userCondition = "";
        if (flag.equalsIgnoreCase("is")) {
            userCondition = "  and fsc.LAST_UPDATED_BY = '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), user) + "'";
        } else if (flag.equalsIgnoreCase("isnot")) {
            userCondition = "  and fsc.LAST_UPDATED_BY != '" + PropUtils.getPropValue(PropUtils.getProps(baseUtils.inputDataFile), user) + "'";
        }
        String reqWorkFlow = "Customer Hierarchy";

        String query = "select fsc.LAST_UPDATED_BY, fsc.field_set_control_oid , mcust.customer_no from field_set_controls fsc\n" +
                "left join m_customers mcust on mcust.customer_mid = fsc.customer_mid\n" +
                "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                "left join field_groups fg on fg.field_group_oid = fsc.field_group_oid\n" +
                "where mc.client_mid = 101 and fg.description = '" + reqWorkFlow + " - Date Effective approvals'\n" +
                "and fsc.FIELD_SET_CTRL_STATUS_CID in (select constant_oid from constants where description = 'New')\n" +
                "and fsc.APPROVAL_STATUS_CID in (select constant_oid from constants where description in ('Pending'))" + userCondition;

        allRowsOfQueryResults.addAll(commonDBUtils.getAllRowsOfQueryResultsOnListMap(query));
        if (allRowsOfQueryResults.size() == 0) {
            PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
            PropUtils.setProps("skipReason", "No Record available to execute this scenario", baseUtils.testDataFilePath);
        } else {
            customerNum = allRowsOfQueryResults.get(0).get("CUSTOMER_NO");
            // System.out.println(allRowsOfQueryResults.get(0).get("CUSTOMER_NO"));
            //PropUtils.setProps("cno", allRowsOfQueryResults.get(0).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
            //System.out.println("Customer no:" + PropUtils.getPropValue(properties, "cno"));
            try {
                List<WebElement> elements = driver.findElements(By.className("ng-star-inserted"));
                int count = elements.size();
                for (int index = 1; index <= count - 1; index++) {
                    WebElement element = driver.findElement(By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::div[7] "));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

                    String actWorkFlow = driver.findElement(By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::div[2]")).getText();
                    String actAccountNum = driver.findElement(By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::div[7] ")).getText();

                    if (actAccountNum.equalsIgnoreCase(customerNum)) {
                        System.out.println(actWorkFlow + actAccountNum);
                        commonPage.sleepForFewSeconds(3);
                        System.out.println("Perform click on elipses");
                        basePage.userClickJSExecutor(logger, By.xpath("//app-page-content//mat-accordion/div[" + index + "]//following::fa-icon[" + 1 + "]"));
                        commonPage.sleepForFewSeconds(5);
                        break;
                    }

                }
            } catch (Exception e) {
                logger.log("Unable to get the reports count from UI ");
            }
        }
    }

    public void verifyTaxRecordData(Scenario logger, String workflow) {
        String reqWorkFlow = null;
        String expWorkFlow = workflow.substring(7);
        reqWorkFlow = workflow.substring(0, workflow.length() - 9);
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (workflow.equalsIgnoreCase("Update Tax Number Workflow")) {
            properties = PropUtils.getProps(baseUtils.testDataFile);
            commonPage.assertTwoStrings(logger, "Tax section pending changes", basePage.userGetTextFromWebElement(logger, By.cssSelector("h2[class='mat-dialog-title']")), "Heading of selected record");
            List<WebElement> fieldNames = basePage.getListOfElements(logger, By.xpath("//mat-dialog-content/div/mat-grid-list/div/mat-grid-tile/figure/div/div[1]"));
            List<WebElement> fieldValues = basePage.getListOfElements(logger, By.xpath("//mat-dialog-content/div/mat-grid-list/div/mat-grid-tile/figure/div/div[2]"));
            for (int i = 0; i <= fieldNames.size() - 1; i++) {
                if (fieldNames.get(i).getText().equalsIgnoreCase("Country")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "countryCode"), fieldValues.get(i).getText(), "CountryCode");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Tax Number")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "taxNo"), fieldValues.get(i).getText(), "Tax Number");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Effective On")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "effectiveDate"), fieldValues.get(i).getText(), "Effective On");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Expires On")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "expiryDate"), fieldValues.get(i).getText(), "Expires On");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Tax Type")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "taxType"), fieldValues.get(i).getText(), "Tax Type");
                }
            }
        }
        if (workflow.equalsIgnoreCase("Update Customer Workflow")) {
            commonPage.assertTwoStrings(logger, "Account summary pending changes", basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='dialog-title ng-star-inserted']/h2")), "Heading of selected record");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedBy"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[2]")), "Requested By");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedAt"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[4]")), "Request Date");
            List<WebElement> fieldNames = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[1]"));
            List<WebElement> fieldValues = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[2]"));
            String customerQuery = "select fgf.field_name, fscv.value from field_group_fields fgf\n" +
                    "inner join field_set_control_values fscv on fscv.field_group_field_oid = fgf.field_group_field_oid\n" +
                    "where fgf.field_group_oid = (select field_group_oid from field_groups where description = 'Customer - Date Effective approvals')\n" +
                    "and fscv.field_set_control_oid = " + PropUtils.getPropValue(properties, "fieldSetControlOid");
            List<Map<String, String>> queryResults = commonDBUtils.getAllRowsOfQueryResultsOnListMap(customerQuery);
            commonPage.assertTwoStrings(logger, String.valueOf(queryResults.size()), String.valueOf(fieldNames.size()), "No of fields updated");
            for (int i = 0; i <= fieldNames.size() - 1; i++) {
                if (fieldNames.get(i).getText().equalsIgnoreCase("Account name")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Accountname"), fieldValues.get(i).getText(), "Account name");
                }
//                if (fieldNames.get(i).getText().equalsIgnoreCase("Customer type")) {
//                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Customertype"), fieldValues.get(i).getText(), "Customer type");
//                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Physical street address")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Physicalstreetaddress"), fieldValues.get(i).getText(), "Physical street address");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Physical postal code")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Physicalpostalcode"), fieldValues.get(i).getText(), "Physical postal code");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Physical country")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Physicalcountry"), fieldValues.get(i).getText(), "Physical country");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Contact name")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Contactname"), fieldValues.get(i).getText(), "Contact name");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Email address")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Emailaddress"), fieldValues.get(i).getText(), "Email address");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Phone")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Phone"), fieldValues.get(i).getText(), "Phone");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Other phone")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Otherphone"), fieldValues.get(i).getText(), "Other phone");
                }
            }
            commonPage.clickButtonUsingSpecificTagName(logger, "Current data","div");
            commonPage.sleepForFewSeconds(5);
            commonPage.assertTwoStrings(logger, "Account summary current data", basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='dialog-title ng-star-inserted']/h2")), "Heading of selected record");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedBy"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[2]")), "Requested By");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedAt"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[4]")), "Request Date");
            String currentQuery = "select mcust.name,ct.description,a.address_line,a.postal_code,c.country_code,\n" +
                    "mcust.CONTACT_NAME,mcust.email_address,mcust.phone_mobile_1,mcust.phone_mobile_2 from m_customers mcust\n" +
                    "left join customer_types ct on ct.description_oid = mcust.customer_type_did\n" +
                    "left join addresses a on a.address_oid = mcust.STREET_ADDRESS_OID\n" +
                    "left join countries c on c.country_oid = a.country_oid\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID") + " and mcust.customer_no = '" +
                    PropUtils.getPropValue(properties, "commonAccountNo") + "'";
            Map<String, String> qr = commonDBUtils.getQueryResultsOnMap(currentQuery);
            fieldNames = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[1]"));
            fieldValues = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[2]"));
            for (int i = 0; i <= fieldNames.size() - 1; i++) {
                if (fieldNames.get(i).getText().equalsIgnoreCase("Account name")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"NAME"), fieldValues.get(i).getText(), "Account name");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Customer type")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"DESCRIPTION"), fieldValues.get(i).getText(), "Customer type");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Physical street address")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"ADDRESS_LINE"), fieldValues.get(i).getText(), "Physical street address");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Physical postal code")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"POSTAL_CODE"), fieldValues.get(i).getText(), "Physical postal code");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Physical country")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"COUNTRY_CODE"), fieldValues.get(i).getText(), "Physical country");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Contact name")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"CONTACT_NAME"), fieldValues.get(i).getText(), "Contact name");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Email address")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"EMAIL_ADDRESS"), fieldValues.get(i).getText(), "Email address");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Phone")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"PHONE_MOBILE_1"), fieldValues.get(i).getText(), "Phone");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Other phone")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"PHONE_MOBILE_2"), fieldValues.get(i).getText(), "Other phone");
                }
            }
            commonPage.clickButtonUsingSpecificTagName(logger,"Pending changes","div");
        }
        if(workflow.equalsIgnoreCase("Update Account Workflow")){
            commonPage.assertTwoStrings(logger, "Financial section pending changes", basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='dialog-title ng-star-inserted']/h2")), "Heading of selected record");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedBy"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[2]")), "Requested By");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedAt"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[4]")), "Request Date");
            List<WebElement> fieldNames = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[1]"));
            List<WebElement> fieldValues = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[2]"));
            String accountQuery = "select fgf.field_name, fscv.value from field_group_fields fgf\n" +
                    "inner join field_set_control_values fscv on fscv.field_group_field_oid = fgf.field_group_field_oid\n" +
                    "where fgf.field_group_oid = (select field_group_oid from field_groups where description = 'Account - Date Effective approvals')\n" +
                    "and fscv.field_set_control_oid = " + PropUtils.getPropValue(properties, "fieldSetControlOid");
            List<Map<String, String>> queryResults = commonDBUtils.getAllRowsOfQueryResultsOnListMap(accountQuery);
            commonPage.assertTwoStrings(logger, String.valueOf(queryResults.size()), String.valueOf(fieldNames.size()), "No of fields updated");
            for (int i = 0; i <= fieldNames.size() - 1; i++) {
                if (fieldNames.get(i).getText().equalsIgnoreCase("Billing Plan")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "BillingPlan"), fieldValues.get(i).getText(), "Billing Plan");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Billing Frequency")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "BillingFrequency"), fieldValues.get(i).getText(), "Billing Frequency");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Account Cycle")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "AccountCycle"), fieldValues.get(i).getText(), "Account Cycle");
                }
            }
            commonPage.clickButtonUsingSpecificTagName(logger, "Current data","div");
            commonPage.sleepForFewSeconds(5);
            commonPage.assertTwoStrings(logger, "Financial section current data", basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='dialog-title ng-star-inserted']/h2")), "Heading of selected record");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedBy"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[2]")), "Requested By");
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "lastUpdatedAt"), basePage.userGetTextFromWebElement(logger, By.xpath("//mat-tab-body/div/div[@class='ng-star-inserted']/span[4]")), "Request Date");
            String currentQuery = "select bp.description as billingPlan, f1.description as billingFrequency, f2.description as cycleFrequency from accounts a\n" +
                    "inner join m_customers mcust on mcust.customer_no = a.account_no\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "left join billing_plans bp on bp.billing_plan_oid = a.billing_plan_oid\n" +
                    "left join frequencies f1 on f1.frequency_oid = a.BILLING_FREQUENCY_OID\n" +
                    "left join frequencies f2 on f2.frequency_oid = a.CYCLE_FREQUENCY_OID\n"+
                    "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID") + " and mcust.customer_no = '" +
                    PropUtils.getPropValue(properties, "commonAccountNo") + "'";
            Map<String, String> qr = commonDBUtils.getQueryResultsOnMap(currentQuery);
            fieldNames = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[1]"));
            fieldValues = basePage.getListOfElements(logger, By.xpath("//mat-tab-body/div/div[@class='grid-top-space ng-star-inserted']/mat-grid-list/div/mat-grid-tile/figure/div/div[2]"));
            for (int i = 0; i <= fieldNames.size() - 1; i++) {
                if (fieldNames.get(i).getText().equalsIgnoreCase("Billing Plan")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"BILLINGPLAN"), fieldValues.get(i).getText(), "Billing Plan");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Billing Frequency")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"BILLINGFREQUENCY"), fieldValues.get(i).getText(), "Billing Frequency");
                }
                if (fieldNames.get(i).getText().equalsIgnoreCase("Account Cycle")) {
                    commonPage.assertTwoStrings(logger, commonPage.getSpecificValueFromMapObject(qr,"CYCLEFREQUENCY"), fieldValues.get(i).getText(), "Account Cycle");
                }
            }
            commonPage.clickButtonUsingSpecificTagName(logger,"Pending changes","div");
        }

//        try {
//            List<Map<String, String>> allRowsOfQueryResults = new ArrayList<>();
//            String wf = workflow.substring(7);
//            String reqWorkFlow = wf.substring(0, wf.length() - 9);
//            properties = PropUtils.getProps(baseUtils.testDataFile);
//            String countFromUI = null;
//            String query = "select fsc.field_set_control_oid , mcust.customer_no,fgf.description, fscv.value  from field_set_control_values fscv\n" +
//                    "left join field_group_fields fgf on fgf.field_group_field_oid = fscv.field_group_field_oid\n" +
//                    "left join field_set_controls fsc on fsc.field_set_control_oid = fscv.field_set_control_oid\n" +
//                    "left join m_customers mcust on mcust.customer_mid = fsc.customer_mid\n" +
//                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
//                    "left join field_groups fg on fg.field_group_oid = fgf.field_group_oid and fg.field_group_oid = fsc.field_group_oid\n" +
//                    "where mcust.customer_no = '" + custNO + "' and mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID")
//                    + " and fg.description = '" + reqWorkFlow + " - Date Effective approvals'\n" +
//                    "and fsc.FIELD_SET_CTRL_STATUS_CID in (select constant_oid from constants where description = 'New')\n" +
//                    "and fsc.APPROVAL_STATUS_CID in (select constant_oid from constants where description in ('Pending'))";
//            allRowsOfQueryResults.addAll(commonDBUtils.getAllRowsOfQueryResultsOnListMap(query));
//            if (allRowsOfQueryResults.size() == 0) {
//                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
//                PropUtils.setProps("skipReason", "Location restriction categories are not available to handle 'location restriction' stepper in cards module", baseUtils.testDataFilePath);
//            } else {
//                By locator = By.xpath("//mat-dialog-content[@class='mat-dialog-content']//child::div[@class='panel-expanded-field-label']");
//                // By locator = By.xpath("//mat-dialog-content[@class='mat-dialog-content']//child::div");
//                List<WebElement> elements = basePage.getListOfElements(logger, locator);
//                System.out.println(elements.size());
//                String expVal;
//                //for (int index = 0; index <= allRowsOfQueryResults.size() - 1; index++) {
//                for (int index = 0; index <= elements.size() - 1; index++) {
//                    String desc = allRowsOfQueryResults.get(index).get("DESCRIPTION");
//                    expVal = allRowsOfQueryResults.get(index).get("VALUE");
//                    //taxNumberworkFlow
//                    if (workflow.equalsIgnoreCase("Update Tax Number Workflow")) {
//                        validateTaxNumberData(desc, workflow);
//                    } else if (desc.equalsIgnoreCase("Billing Plan") && workflow.equalsIgnoreCase("Update Account Workflow")) {
//                        Map<String, String> queryResults = commonDBUtils.getQueryResultsOnMap("select bp.description from billing_plans bp where  bp.billing_plan_oid=" + allRowsOfQueryResults.get(index).get("VALUE"));
//                        String strDesc = queryResults.get("DESCRIPTION");
//                        driver.findElement(By.xpath("//div[normalize-space(text())='" + strDesc + "']"));
//                        //  driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
//                    } else if ((desc.equalsIgnoreCase("Billing Frequency") || desc.equalsIgnoreCase("Account Cycle")) && workflow.equalsIgnoreCase("Update Account Workflow")) {
//                        Map<String, String> queryResults = commonDBUtils.getQueryResultsOnMap("select fr.description from frequencies fr where fr.frequency_oid=" + allRowsOfQueryResults.get(index).get("VALUE"));
//                        String strDesc = queryResults.get("DESCRIPTION");
//                        driver.findElement(By.xpath("//div[normalize-space(text())='" + strDesc + "']"));
//                        // driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
//                    } else {
//                        driver.findElement(By.xpath("//div[normalize-space(text())='" + expVal + "']"));
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.log("Unable to get the reports count from UI ");
//
//        }
    }

    public void validateTaxNumberData(String desc, String workflow) {
        if (desc.equalsIgnoreCase("Country") && workflow.equalsIgnoreCase("Update Tax Number Workflow")) {
            //Map<String, String> queryResults = commonDBUtils.getQueryResultsOnMap("select c.country_code from  countries c where c.country_oid=" + allRowsOfQueryResults.get(1).get("VALUE"));
            // String countryCode = queryResults.get("COUNTRY_CODE");
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));

        } else if (desc.equalsIgnoreCase("Tax Type") && workflow.equalsIgnoreCase("Update Tax Number Workflow"))//this type value Non Exempt/Tax Except is common for all tex popup...but db value is diff from UI
        {
            //  driver.findElement(By.xpath("//div[contains(normalize-space(text()),'Exempt')]"));
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
        } else if (desc.contains("On") && workflow.equalsIgnoreCase("Update Tax Number Workflow")) {
                    /*String arrVal[]=allRowsOfQueryResults.get(index).get("VALUE").split(" ");
                    expVal=arrVal[0];
                    String s = "//div[normalize-space(text())='" + expVal + "']";
                    driver.findElement(By.xpath("//div[normalize-space(text())='" + expVal+ "']"));*/
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
        } else if (desc.equalsIgnoreCase("Billing Plan") && workflow.equalsIgnoreCase("Update Account Workflow")) {
                   /* Map<String, String> queryResults = commonDBUtils.getQueryResultsOnMap("select bp.description from billing_plans bp where  bp.billing_plan_oid="+allRowsOfQueryResults.get(index).get("VALUE"));
                    String strDesc= queryResults.get("DESCRIPTION");
                    driver.findElement(By.xpath("//div[normalize-space(text())='" + strDesc+ "']"));*/
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
        } else if ((desc.equalsIgnoreCase("Billing Frequency") || desc.equalsIgnoreCase("Account Cycle")) && workflow.equalsIgnoreCase("Update Account Workflow")) {
                /*    Map<String, String> queryResults = commonDBUtils.getQueryResultsOnMap("select fr.description from frequencies fr where fr.frequency_oid="+allRowsOfQueryResults.get(index).get("VALUE"));
                    String strDesc= queryResults.get("DESCRIPTION");
                    driver.findElement(By.xpath("//div[normalize-space(text())='" + strDesc+ "']"));*/
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
        } else if ((desc.equalsIgnoreCase("Billing Frequency") || desc.equalsIgnoreCase("Account Cycle")) && workflow.equalsIgnoreCase("Update Account Workflow")) {
                /*    Map<String, String> queryResults = commonDBUtils.getQueryResultsOnMap("select fr.description from frequencies fr where fr.frequency_oid="+allRowsOfQueryResults.get(index).get("VALUE"));
                    String strDesc= queryResults.get("DESCRIPTION");
                    driver.findElement(By.xpath("//div[normalize-space(text())='" + strDesc+ "']"));*/
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));
        } else {
            //  driver.findElement(By.xpath("//div[normalize-space(text())='" + expVal+ "']"));
            driver.findElement(By.xpath("//div[normalize-space(text())='" + desc + "']"));

        }

    }

    public void isRecordNotExist(String record) {
        try {
            basePage.whetherElementPresent(logger, By.xpath("//div[text()='No pending request']"));

        } catch (Exception e) {
            logger.log("Record verification failed");

        }
    }

    public void setFlagsWithQuery(String flags, String userID) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String countFromUI = null;
        //get only Y or N add them to query
        String arrFlags[] = flags.trim().split("[,=]");
        String query = "update operation_limit set CAN_APPROVE_CUST_MASTER_DATA = '" + arrFlags[1].trim() + "', CAN_APPROVE_ACCT_MASTER_DATA = '" + arrFlags[3].trim() + "', CAN_APPROVE_TAX_MASTER_DATA = '" + arrFlags[5].trim() + "',\n" +
                "CAN_APPROVE_CUST_HIER_DATA = '" + arrFlags[7].trim() + "', CAN_APPROVE_OWN = '" + arrFlags[9].trim() + "' where user_oid = (select u.user_oid from users u\n" +
                "inner join user_roles ur on ur.user_oid = u.user_oid\n" +
                "inner join access_role_clients arc on arc.access_role_oid = ur.access_role_oid\n" +
                "left join operation_limit op on op.user_oid = u.user_oid\n" +
                "where u.logon_id = '" + userID + "' and arc.client_mid = 101 and (op.user_oid = u.user_oid or op.access_group_oid = arc.access_group_oid))\n" +
                "\n";
        commonDBUtils.executeUpdateQuery(query);

    }
}






