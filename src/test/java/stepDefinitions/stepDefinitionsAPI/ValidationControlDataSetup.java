package stepDefinitions.stepDefinitionsAPI;

import utilities.api.CommonDBUtils;
import utilities.api.RequestMethodsUtils;

public class ValidationControlDataSetup extends RequestMethodsUtils {

    public io.cucumber.java.Scenario logger;
    private CommonDBUtils commonDBUtils;
    public ValidationControlDataSetup(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }
    public ValidationControlDataSetup(){
        commonDBUtils = new CommonDBUtils();
    }
//    @And("User connect to \"(.*)\" database$")
//    public void verifyThePresenceOfThePageHeader(String dbName) {
//         
//        try {
//            //log = test.createNode(new GherkinKeyword("And"), "User connect to "+dbName+" database");
////            if(dbName.equalsIgnoreCase("apacdev1")) {
////                dbName = "jdbc:oracle:thin:@aec1ifcdbb000d.c5g5dwtbxj95.eu-central-1.rds.amazonaws.com:1521/IFCDB";
////                dbUserName = "IFCS_APAC_DEV_1";
////                dbPassword = "ifcspassword1#";
////            }else if(dbName.equalsIgnoreCase("uidev")){
////                dbName = "jdbc:oracle:thin:@aec1ifcdbv000d.ciogcsbotmwj.eu-central-1.rds.amazonaws.com:1521/IFCDB";
////                dbUserName = "IFCS_OMV_DEV_UIDEV_1 ";
////                dbPassword = "ifcspassword1#";
////            }
//            dbName = "jdbc:oracle:thin:@aec1ifcdbb000d.c5g5dwtbxj95.eu-central-1.rds.amazonaws.com:1521/IFCDB";
//            dbUserName = "IFCS_APAC_DEV_1";
//            dbPassword = "ifcspassword1#";
//            String query = " select * from validation_fields where COLUMN_NAME = 'credit_limit' and system_module_cid = 1510";
//            Map<String, String> vcApacDev1 = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbName, dbUserName, dbPassword);
//            query = "select * from validation_controls where validation_field_oid = "+vcApacDev1.get("VALIDATION_FIELD_OID")+" order by VALIDATION_CONTROL_OID desc";
//            vcApacDev1 = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbName, dbUserName, dbPassword);
//
//             //connecting to ui dev database
//            dbName = "jdbc:oracle:thin:@aec1ifcdbv000d.ciogcsbotmwj.eu-central-1.rds.amazonaws.com:1521/IFCDB";
//            dbUserName = "IFCS_OMV_DEV_UIDEV_1 ";
//            dbPassword = "ifcspassword1#";
//            query = " select * from validation_fields where COLUMN_NAME = 'credit_limit' and system_module_cid = 1510";
//            Map<String, String>vcUIDev = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbName, dbUserName, dbPassword);
//            query = "select * from validation_controls where validation_field_oid = "+vcUIDev.get("VALIDATION_FIELD_OID")+" order by VALIDATION_CONTROL_OID desc";
//            vcUIDev = DataBaseUtils.getMySQLConnectionAndEntireRowValue(query, dbName, dbUserName, dbPassword);
//            System.out.println(vcUIDev.get("VALIDATION_CONTROL_OID"));
//
//        } catch (Exception e) {
//            ExtentReportListener.testStepHandleAPI("FAIL", logger, e);
//        }
//    }
}
