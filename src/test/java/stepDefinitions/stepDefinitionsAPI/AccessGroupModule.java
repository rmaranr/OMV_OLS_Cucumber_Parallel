package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.HashMap;
import java.util.Map;

//import org.json.JSONArray;
//import org.json.JSONObject;

public class AccessGroupModule extends RequestMethodsUtils {

    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    Map<String, String> cardsDetailsMap = new HashMap<>();
    String cardDetailsEndPoint = PropUtils.getPropValue(inputProp, "cardDetails");
    String cardUpdateEndPoint = PropUtils.getPropValue(inputProp, "cardUpdate");
    String defaultProfileEndPoint = PropUtils.getPropValue(inputProp, "defaultProfile");
    String createContactEndPoint = PropUtils.getPropValue(inputProp, "curdContactEndPoint");
    String updateContactEndPoint = PropUtils.getPropValue(inputProp, "editContactEndPoint");
    String deleteContactEndPoint = PropUtils.getPropValue(inputProp, "curdContactEndPoint");
    String costCentreEndPoint = PropUtils.getPropValue(inputProp, "curdCostCentreEndPoint");
    String customerEndpoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");
    String customerEndpointWithCustomer = PropUtils.getPropValue(inputProp, "customerEndpointWithCustomer");
    String cardOrderEndPoint = PropUtils.getPropValue(inputProp, "cardOrder");
    public Scenario logger;

    public AccessGroupModule(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }

    boolean presenceCheck = false;
    String expCardNumber = "";

    @Then("^user hit the API and verify the user with access group as \"([^\"]*)\"$")
    public void userHitTheAPIAndVerifyTheUserWithAccessGroupAs(String accessGrp) throws Throwable {
        requestParams.put("grant_type", PropUtils.getPropValue(inputProp, "GRANT_TYPE"));
        requestParams.put("username", PropUtils.getPropValue(inputProp, accessGrp + "User"));
        requestParams.put("password", PropUtils.getPropValue(inputProp, accessGrp + "Pwd"));

        String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                PropUtils.getPropValue(inputProp, "SECERT_ID"));

//Login Endpoint
        logger.log("Login Request path -->" + "/login");
        logger.log("Request params-->" + requestParams.toString());
        response = postRequestAsBasicAuthWithBodyData("/login", requestParams, accessToken);
        if (response.statusCode() == 200) {
            jsonPathEvaluator = response.jsonPath();
            authorizationToken = jsonPathEvaluator.get("token");
            logger.log(" Response Body -->" + response.getBody().prettyPrint());
            logger.log("<---------------------------------------------------------------->" );
//Validate Contact Endpoint
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("ValidateContact", "/customer/123456789/validate-contact", accessGrp,requestParams.toString(), logger);
//Contact Endpoint
            commonMethods.verifyAndValidateAccessGrpsForGetMethod("GetContact", customerEndpointWithCustomer, accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("CreateContact", createContactEndPoint, accessGrp,requestParams.toString(), logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("EditContact", updateContactEndPoint, accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForDeleteMethod("DeleteContact", deleteContactEndPoint, accessGrp, logger);
//CostCentre Endpoint
            commonMethods.verifyAndValidateAccessGrpsForGetMethod("GetCostCentre", costCentreEndPoint, accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("CreateCostCentre", costCentreEndPoint, accessGrp,requestParams.toString(), logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("EditCostCentre", costCentreEndPoint, accessGrp, logger);
//Cards Endpoint
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("GetCardsList", customerEndpointWithCustomer+"/cards", accessGrp,requestParams.toString(), logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get SpecificCard", cardDetailsEndPoint,accessGrp,requestParams.toString(), logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("OrderCard", cardOrderEndPoint,accessGrp,requestParams.toString(), logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethodWithQueryParams("EditCard", cardUpdateEndPoint, accessGrp,"reissueAllowed","false", logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("ChangeCardStatus", "/card/status", accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("ChangePin", "/card/reissue-pin", accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("ReissuePin", "/card/change-pin", accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("BulkCardStatusChange", "/card/bulk-card-status", accessGrp, logger);
//Drivers and Vehicles Endpoints
            commonMethods.verifyAndValidateAccessGrpsForGetMethod("GetDrivers", "/customer/123456789/drivers", accessGrp, logger);
            commonMethods.verifyAndValidateAccessGrpsForGetMethod("GetVehicles", "/customer/123456789/vehicles", accessGrp, logger);
//Transactions and export
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get TransactionSearch", "/transaction/search", accessGrp,requestParams.toString(), logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod(" Get TransactionsExport", "/transaction/export", accessGrp,requestParams.toString(), logger);
//Validation Controls Endpoint
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get ValidationControl details", "/validation-controls", accessGrp,requestParams.toString(), logger);


            //user
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Create User",PropUtils.getPropValue(inputProp, "userEndPointAPI"),accessGrp,requestParams.toString(),logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("Edit profile",PropUtils.getPropValue(inputProp, "editProfileEndPointAPI"),accessGrp,logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("Edit status",PropUtils.getPropValue(inputProp, "editStatusEndPointAPI"),accessGrp,logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethodWithQueryParams("Edit account access",PropUtils.getPropValue(inputProp, "accountProfileEndPoint"),accessGrp,"logonId",PropUtils.getPropValue(inputProp, "newUserIDCreation"),logger);
            commonMethods.verifyAndValidateAccessGrpsForGetMethodWithSingleQueryParam("Get user details",PropUtils.getPropValue(inputProp, "userEndPointAPI"),authorizationToken,accessGrp,"logonId",PropUtils.getPropValue(inputProp, "UserNameAPI"),logger);
            //accounts
            commonMethods.verifyAndValidateAccessGrpsForGetMethod("Get accounts",PropUtils.getPropValue(inputProp, "accountsEndPoint"),accessGrp,logger);
            commonMethods.verifyAndValidateAccessGrpsForGetMethod("Get account details for specific customer",PropUtils.getPropValue(inputProp, "accountEndPointByAccountNo"),accessGrp,logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("Update customer details",PropUtils.getPropValue(inputProp, "customerEndpointWithCustomer"),accessGrp,logger);
            //Reports
            String reportTypeBasedOnReportCategory = PropUtils.getPropValue(inputProp, "lookupForReportTypes")+PropUtils.getPropValue(inputProp, "reportCategory");
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Create schedule report",PropUtils.getPropValue(inputProp, "createScheduledReport"),accessGrp,requestParams.toString(),logger);
            commonMethods.verifyAndValidateAccessGrpsForPutMethod("Edit schedule report",PropUtils.getPropValue(inputProp, "detailsOfScheduledReportEndPoint"),accessGrp,logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get list of schedule report details",PropUtils.getPropValue(inputProp, "searchScheduledReportEndPoint"),accessGrp,requestParams.toString(),logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get report details",PropUtils.getPropValue(inputProp, "detailsOfScheduledReportEndPoint"),accessGrp,requestParams.toString(),logger);
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get Stored Reports",PropUtils.getPropValue(inputProp, "reportSearchEndpoint"),accessGrp,requestParams.toString(),logger);
            commonMethods.verifyAndValidateAccessGrpsForGetMethodWithMultipleQueryParam("Get Report types",PropUtils.getPropValue(inputProp, "lookupForReportTypes"),authorizationToken,accessGrp,logger);
            commonMethods.verifyAndValidateAccessGrpsForGetMethodWithSingleQueryParam("Get Report Categories",PropUtils.getPropValue(inputProp, "lookupForReportCategory"),authorizationToken,accessGrp,"memberType","Customer",logger);
            commonMethods.verifyAndValidateAccessGrpsForGetMethodWithSingleQueryParam("Get Report type based on report category",reportTypeBasedOnReportCategory,authorizationToken,accessGrp,"memberType","Customer",logger);

            //Homedashboard
            commonMethods.verifyAndValidateAccessGrpsForPostMethod("Get Home dashboard details",PropUtils.getPropValue(inputProp, "totaltransactionEndPoint"),accessGrp,requestParams.toString(),logger);
            //Logout
            logger.log("Logout Request path -->" + "/logout");
            response = postRequestAsBearerAuthWithNoBodyData("/logout", authorizationToken);
            logger.log("<---------------------------------------------------------------->" );
            logger.log("Successfully ran user having Access group as " + accessGrp);
        } else {
            logger.log("Unable to login to the application, please supply valid creds");
        }
    }
}


