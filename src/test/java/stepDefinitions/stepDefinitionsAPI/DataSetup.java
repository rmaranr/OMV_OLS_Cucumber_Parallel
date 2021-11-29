package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utilities.api.*;

import java.util.*;

public class DataSetup extends RequestMethodsUtils {

    public io.cucumber.java.Scenario logger;
    CommonDBUtils common = new CommonDBUtils();
    UserMethodsAPI userMethodsAPI = new UserMethodsAPI();
    List<String> queries = new ArrayList<>();
    BaseUtils baseUtils = new BaseUtils();

    public DataSetup(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @Given("^User execute and update query to insert data \"(.*)\", \"(.*)\"$")
    public void get_access_token_from_the_login_API(String storedReportOID, String reportName) throws Throwable {
        common.initializeDBEnvironment();
        String query = "INSERT INTO IFCS_APAC_DEV_6.STORED_REPORTS \n" +
                "    (STORED_REPORT_OID, UPDATE_COUNT, LAST_UPDATED_AT, LAST_UPDATED_BY,\n" +
                "    REPORT_TYPE_OID, FILE_FORMAT_CID, CREATED_ON, FILE_NAME, RPT_EXTR_CUSTOMER_OID,\n" +
                "    MEMBER_TYPE_CID, MEMBER_OID, IS_DUPLEX, DELIVERY_TYPE_CID, REPORT_STATUS_CID,\n" +
                "    NUM_OF_PAGES, SEND_TO_ADDRESS_OID, IS_ATTACHMENT_COMPRESSED, REPORT_ASSIGNMENT_OID)\n" +
                "    VALUES ('" + storedReportOID + "', '3', TO_DATE('2020-07-02 14:05:46', 'YYYY-MM-DD HH24:MI:SS'), \n" +
                "    N'ifcs', '3402', '2101', TO_DATE('2020-07-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),\n" +
                "    N'/data/ifcsdocs/AWS_OLS_RESKIN_QA/CVMY/PDF_Reports/Customer Reports/EOD/31Dec2027/\n" +
                "    60-Customer Statement - StarCash 01.01.2028-Account 0700000999-5118" + reportName + ".pdf',\n" +
                "    '5230', '103', '4801', 'N', '1124', '12601', '1', '423621', 'N', '198303')";
        common.executeUpdateQuery(query);
    }

    @When("^User generate an extent Report based on property values$")
    public void generateAnExtentReportBasedOnPropertyValues() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
//        for (int i = 500; i <= 590; i++) {
//            logger.log(PropUtils.getPropValue(properties, "Val-controls-insertQuery" + i));
//            logger.log(PropUtils.getPropValue(properties, "Val-fields-insertQuery" + i));
//            logger.log(PropUtils.getPropValue(properties, "Val-controls-updateQuery" + i));
////                    logger.log("--------------------------------");
//        }
        for(int i=0;i<=2000;i++){
            logger.log(PropUtils.getPropValue(properties,"menuInsertQuery-"+i));
            logger.log(PropUtils.getPropValue(properties,"menuUpdateQuery-"+i));
        }
    }


    @When("^User execute and update query to insert data for \"([^\"]*)\" and \"([^\"]*)\" in report assignments table$")
    public void user_execute_and_update_query_to_insert_data_for_and_in_report_assignments_table(String reportAssignmentOid, String templateName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //   common.initializeDBEnvironment();
        String query = "INSERT INTO \"IFCS_APAC_DEV_6\".\"REPORT_ASSIGNMENTS\" (REPORT_ASSIGNMENT_OID, UPDATE_COUNT, LAST_UPDATED_AT, LAST_UPDATED_BY, REPORT_TYPE_OID, \r\n" +
                "FREQUENCY_OID, DELIVERY_TYPE_CID, CREATED_ON, IS_PULL_CODE_USED, LAST_REPORTED_ON, IS_ADHOC_REPORT, IS_ENABLED, IS_INTERNET_USER,\r\n" +
                "DESCRIPTION, USER_OID, EMAIL_ADDRESS,IS_ATTACHMENT_COMPRESSED) VALUES\r\n" +
                " ('" + reportAssignmentOid + "', '1', TO_DATE('2020-07-22 11:34:04', 'YYYY-MM-DD HH24:MI:SS'), 'ifcs', '3394', '1706', '1102', TO_DATE('2020-07-22 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), \r\n" +
                " 'N', TO_DATE('2028-01-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'N', 'Y', 'Y','Arvid_report111" + templateName + "' , '5862','Domingo@gmail.com', 'N')";

        common.executeUpdateQuery(query);
    }
    @When("^User insert menu scripts config data based on fields clientGroupMid \"(.*)\" get menu oid using \"(.*)\",\"(.*)\", \"(.*)\" , \"(.*)\", \"(.*)\" and get accessLevelCid using \"(.*)\" and get accessGroupOid using \"(.*)\" with Serial No \"(.*)\"$")
    public void userInsertMenuScriptsConfigData(String clientGroupName, String menu, String parentMenu, String screenAction, String endPoint, String method, String accessLvlDescription, String accessGrpDes, String sno) throws Throwable {
        String clientGroupMidCondition = "(select client_group_mid from m_client_groups where name = '"+clientGroupName+"')";
        String menuCondition = "";
        String parentMenuCondition = "";
        String screenActionCondition = "";
        String endPointCondition = "";
        String accessLvlDesCondition = "";
        String accessGrpDesCondition = "";
        if(!menu.equalsIgnoreCase("") && !parentMenu.equalsIgnoreCase("")){
            menuCondition = "(select menu_oid from menus where TITLE = '"+menu+"' and PARENT_MENU_OID = (select" +
                    " menu_oid from menus m where m.TITLE = '"+parentMenu+"' and m.PARENT_MENU_OID is null))";
        }else{
            menuCondition = "(select menu_oid from menus where TITLE = '"+menu+"' and parent_menu_oid is null)";
        }
        if(!screenAction.equalsIgnoreCase("")){
            screenActionCondition = " and screen_action_oid = (select SCREEN_ACTION_OID from screen_actions where SCREEN_ACTION_KEY = '"+screenAction+"')";
        }
        if(!endPoint.equalsIgnoreCase("") && !method.equalsIgnoreCase("")){
            endPointCondition = " and endpoint_oid = (select endpoint_oid from endpoints where method = '"+method+"' and endpoint = '"+endPoint+"')";
        }
//        else if(!endPoint.equalsIgnoreCase("")&& method.equalsIgnoreCase("")){
//            endPointCondition = " and endpoint_oid = (select endpoint_oid from endpoints where method is null and endpoint = '"+endPoint+"')";
//        }
        if(!accessLvlDescription.equalsIgnoreCase("")){
            accessLvlDesCondition = "(select constant_oid from constants where description = '"+accessLvlDescription+"')";
        }
        if(!accessGrpDes.equalsIgnoreCase("")){
            accessGrpDesCondition = "(select access_group_oid from access_groups where description = '"+accessGrpDes+"' and client_group_mid = "+clientGroupMidCondition+")";
        }
        String menuActionQuery = "(select menu_action_oid from menu_actions where menu_oid = "+menuCondition+" "+screenActionCondition+" "+endPointCondition+")";
        PropUtils.setProps("menuInsertQuery-"+sno,"insert into access_group_permissions(access_group_oid, menu_action_oid, access_level_cid) values" +
                " ("+accessGrpDesCondition+","+menuActionQuery+","+accessLvlDesCondition+");",baseUtils.testDataFilePath);
    }

    @When("^User update menu scripts config data based on fields clientGroupMid \"(.*)\" get menu oid using \"(.*)\",\"(.*)\", \"(.*)\" , \"(.*)\", \"(.*)\" and get accessLevelCid using \"(.*)\" and get accessGroupOid using \"(.*)\" with Serial No \"(.*)\"$")
    public void userUpdateMenuScriptsConfigData(String clientGroupName, String menu, String parentMenu, String screenAction, String endPoint, String method, String accessLvlDescription, String accessGrpDes, String sno) throws Throwable {
        String clientGroupMidCondition = "(select client_group_mid from m_client_groups where name = '"+clientGroupName+"')";
        String menuCondition = "";
        String parentMenuCondition = "";
        String screenActionCondition = "";
        String endPointCondition = "";
        String accessLvlDesCondition = "";
        String accessGrpDesCondition = "";
        if(!menu.equalsIgnoreCase("") && !parentMenu.equalsIgnoreCase("")){
            menuCondition = "(select menu_oid from menus where TITLE = '"+menu+"' and PARENT_MENU_OID = (select" +
                    " menu_oid from menus m where m.TITLE = '"+parentMenu+"' and m.PARENT_MENU_OID is null))";
        }else{
            menuCondition = "(select menu_oid from menus where TITLE = '"+menu+"' and parent_menu_oid is null)";
        }
        if(!screenAction.equalsIgnoreCase("")){
            screenActionCondition = " and screen_action_oid = (select SCREEN_ACTION_OID from screen_actions where SCREEN_ACTION_KEY = '"+screenAction+"')";
        }
        if(!endPoint.equalsIgnoreCase("") && !method.equalsIgnoreCase("")){
            endPointCondition = " and endpoint_oid = (select endpoint_oid from endpoints where method = '"+method+"' and endpoint = '"+endPoint+"')";
        }
//        else if(!endPoint.equalsIgnoreCase("")&& method.equalsIgnoreCase("")){
//            endPointCondition = " and endpoint_oid = (select endpoint_oid from endpoints where method is null and endpoint = '"+endPoint+"')";
//        }
        if(!accessLvlDescription.equalsIgnoreCase("")){
            accessLvlDesCondition = "(select constant_oid from constants where description = '"+accessLvlDescription+"')";
        }
        if(!accessGrpDes.equalsIgnoreCase("")){
            accessGrpDesCondition = "(select access_group_oid from access_groups where description = '"+accessGrpDes+"' and client_group_mid = "+clientGroupMidCondition+")";
        }
        String menuActionQuery = "(select menu_action_oid from menu_actions where menu_oid = "+menuCondition+" "+screenActionCondition+" "+endPointCondition+")";
        PropUtils.setProps("menuUpdateQuery-"+sno,"update access_group_permissions set access_level_cid = ("+accessLvlDesCondition+")\n" +
                "where access_group_oid = ("+accessGrpDesCondition+") and menu_action_oid = ("+menuActionQuery+");",baseUtils.testDataFilePath);
    }

    @And("User insert data in validation controls based on fields client_mid \"(.*)\" clientGroupMid \"(.*)\" columnName \"(.*)\" fieldName \"(.*)\" userType \"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\" systemModulecid \"(.*)\" field status \"(.*)\",\"(.*)\" for Scenario \"(.*)\" from \"(.*)\" to \"(.*)\" with \"(.*)\"$")
    public void verifyThePresenceOfThePageHeader(String clientMid, String clientGroupMID, String columnName, String fieldName, String isOLS, String isCSR, String isOLSEditable, String isCSREditable, String systemModuleCid, String columnControlCidForOLS, String columnControlCidForCSR, String scenario, String fromEnv, String toEnv, String index) {
        try {
            String insertQuery = "";
            String dbNameFromEnv = userMethodsAPI.getDbnameUserNamePasswordForAnEnv(fromEnv).split("&")[0];
            String dbUserNameFromEnv = userMethodsAPI.getDbnameUserNamePasswordForAnEnv(fromEnv).split("&")[1];
            String dbPasswordFromEnv = userMethodsAPI.getDbnameUserNamePasswordForAnEnv(fromEnv).split("&")[2];
            String dbNameToEnv = userMethodsAPI.getDbnameUserNamePasswordForAnEnv(toEnv).split("&")[0];
            String dbUserNameToEnv = userMethodsAPI.getDbnameUserNamePasswordForAnEnv(toEnv).split("&")[1];
            String dbPasswordToEnv = userMethodsAPI.getDbnameUserNamePasswordForAnEnv(toEnv).split("&")[2];
            String queryOfFromEnv = " select * from validation_fields where COLUMN_NAME = '" + columnName + "' and system_module_cid = " + systemModuleCid;
            logger.log("ClientMid : " + clientMid + " , ClientGroupMid : " + clientGroupMID + " , columnName : " + columnName + " , systemModuleCid : " + systemModuleCid);
            Map<String, String> valFieldsFromEnv = DataBaseUtils.getMySQLConnectionAndEntireRowValue(queryOfFromEnv, dbNameFromEnv, dbUserNameFromEnv, dbPasswordFromEnv);
            Map<String, String> valControlsToEnv = new HashMap<>();
            Map<String, String> valControlFromEnv = new HashMap<>();
            Map<String, String> valFieldToEnv = new HashMap<>();
            queryOfFromEnv = "select * from validation_controls where validation_field_oid = " + valFieldsFromEnv.get("VALIDATION_FIELD_OID") + " order by VALIDATION_CONTROL_OID desc";
            valControlFromEnv = DataBaseUtils.getMySQLConnectionAndEntireRowValue(queryOfFromEnv, dbNameFromEnv, dbUserNameFromEnv, dbPasswordFromEnv);
            String query = "";
            try {
                query = " select * from validation_fields where COLUMN_NAME = '" + columnName + "' and system_module_cid = " + systemModuleCid;
                valFieldToEnv = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv);
                if (valFieldToEnv.get("VALIDATION_FIELD_OID") == null || valFieldToEnv.size() == 0) {
                    query = "Insert into VALIDATION_FIELDS (LAST_UPDATED_BY,SYSTEM_MODULE_CID,DESCRIPTION,COLUMN_NAME,TABLE_NAME)\n" +
                            " values ('OMV_OLS_UI'," + systemModuleCid + ",'" + fieldName + "','" + columnName + "',null)";
                    PropUtils.setProps("Val-fields-insertQuery" + index, query, baseUtils.testDataFilePath);
                    if (!toEnv.equalsIgnoreCase("demo")) {
                        DataBaseUtils.executeQueryAndCommit(query, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv);
                        logger.log("Inserted validation field by using the query :: " + query);
                    }
                    query = " select * from validation_fields where COLUMN_NAME = '" + columnName + "' and system_module_cid = " + systemModuleCid;
                    valFieldToEnv = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv);
                }
                query = "select * from validation_controls where validation_field_oid = " + valFieldToEnv.get("VALIDATION_FIELD_OID") + " order by VALIDATION_CONTROL_OID desc";
                valControlsToEnv = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv);
                if (valFieldsFromEnv.get("VALIDATION_FIELD_OID") != null || valFieldsFromEnv.size() != 0) {
                    valControlFromEnv = DataBaseUtils.getMySQLConnectionAndEntireRowValue(queryOfFromEnv, dbNameFromEnv, dbUserNameFromEnv, dbPasswordFromEnv);
                    if (valControlFromEnv.get("VALIDATION_CONTROL_OID") != null || valControlFromEnv.size() != 0) {
                        System.out.println(valControlsToEnv.get("VALIDATION_CONTROL_OID"));
                        String mask = null;
                        if (valControlFromEnv.get("MASK") != null) {
                            mask = "'" + valControlFromEnv.get("MASK") + "'";
                        }
                        String maskErrorMessage = null;
                        if (valControlFromEnv.get("MASK_ERROR_MESSAGE") != null) {
                            maskErrorMessage = "'" + valControlFromEnv.get("MASK_ERROR_MESSAGE") + "'";
                        }
                        if (valControlsToEnv.get("VALIDATION_CONTROL_OID") == null || valControlsToEnv.size() == 0) {
                            userMethodsAPI.insertQueryBasedOnOLSEditableField(isOLS, isOLSEditable, isCSR, isCSREditable, index, valControlFromEnv, systemModuleCid, clientMid, mask, maskErrorMessage, columnName, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv, toEnv, columnControlCidForOLS,logger);
//                            DataBaseUtils.executeQueryAndCommit(insertQuery, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv);
                        } else {
                            userMethodsAPI.updateQueryBasedOnOLSEditableField(isOLS, isOLSEditable, isCSR, valControlsToEnv, valControlFromEnv, systemModuleCid, mask, maskErrorMessage, columnName, index, clientMid, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv, toEnv, columnControlCidForOLS,logger);
                        }
//                        else {
//                            logger.log("Validation control record is already exists in db '" + toEnv + "' ");
//                        }
                    } else {
                        userMethodsAPI.insertNewRecordWhenThereIsNoValidationControl(logger, valControlsToEnv, isOLS, isOLSEditable, isCSR, systemModuleCid, columnName, index, clientMid, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv, toEnv, columnControlCidForCSR, columnControlCidForOLS);
                    }
                } else {
                    userMethodsAPI.insertNewRecordWhenThereIsNoValidationControl(logger, valControlsToEnv, isOLS, isOLSEditable, isCSR, systemModuleCid, columnName, index, clientMid, dbNameToEnv, dbUserNameToEnv, dbPasswordToEnv, toEnv, columnControlCidForCSR, columnControlCidForOLS);
                }
            } catch (Exception e) {
                logger.log("Validation field is not present in db " + fromEnv + " of field " + columnName);
            }
            Properties properties = PropUtils.getProps(baseUtils.testDataFile);
//            PropUtils.setProps("InsertQuery" + index, insertQuery, baseUtils.testDataFilePath);
        }catch(Exception e){
            logger.log("");
        }
    }

}
