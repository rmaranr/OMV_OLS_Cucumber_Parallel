package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import edu.emory.mathcs.backport.java.util.Arrays;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CostCentresModules extends RequestMethodsUtils {

    public Scenario logger;
    String customerNumber, cardNumber, driverName, vehicleId, VRN, cardsEndPoint, responseBody;
    //    JSONObject requestCardDetailsParams = new JSONObject();
    Map<String, String> cardsInformation = new HashMap<String, String>();
    ArrayList<String> cardNumbersInPageResponse = new ArrayList<String>();
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();

    public CostCentresModules(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }

    @Then("^user post request body to create cost centre and validate with \"([^\"]*)\"and\"([^\"]*)\"and\"([^\"]*)\"and\"([^\"]*)\"and(\\d+) as expected values$")
    public void userPostRequestBodyToCreateCostCentreAndValidateWithAndAndAndAndStatusNumberAsExpectedValues(String paramCostCentreCode, String paramDescription,
                                                                                                             String paramShortDescription, String paramStatusMsg, int paramStatusNumber)  throws Throwable {
        String costCentreCodeValue = "";
        boolean presenceCheck = false;
        String costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_COSTCENTRE");
        costCentreEndPoint = customerEndPoint + costCentreEndPoint;
        System.out.println("costCentreEndPoint " + costCentreEndPoint);
        logger.log("Request path -->"+costCentreEndPoint);
        logger.log("Request body is ---> "+requestCardDetailsParams);

        String customerCostCentreCode = requestCardDetailsParams.getString("customerCostCentreCode");
        String description = requestCardDetailsParams.getString("description");
        String shortDescription = requestCardDetailsParams.getString("shortDescription");

        response = postRequestAsBearerAuthWithBodyData(costCentreEndPoint, requestCardDetailsParams.toString(), authorizationToken);
        if(response.getStatusCode()==200){
            presenceCheck = common.checkingThePresenceOfCostCentreDB(customerCostCentreCode,description,shortDescription);
            if(presenceCheck){
                logger.log("DB Validation passed-Created CostCentre found in DB");
            }
            else{
                softFail("DB Validation failed-Created CostCentre not found in DB",logger);
            }
        }else {
            logger.log("Response body is " + response.getBody().prettyPrint());

            commonMethods.userNeedsToValidateTheResponseBodyObtainedFromTheAPI(costCentreCodeValue, paramStatusMsg, paramStatusNumber,
                    paramCostCentreCode, paramDescription, paramShortDescription, logger);
        }
        ArrayList<String> keyParams =
                new ArrayList<String>(Arrays.asList(new String[]{"customerCostCentreCode","description","shortDescription"}));
        System.out.println("keyParams---->"+keyParams);
        commonMethods.removeRequestParams(keyParams);
    }

    @Then("^user put request body to edit cost centre and validate with \"([^\"]*)\"and\"([^\"]*)\"and\"([^\"]*)\"and\"([^\"]*)\"and(\\d+) as expected values$")
    public void userPutRequestBodyToEditCostCentreAndValidateWithAndAndAndAndStatusNumberAsExpectedValues(String paramCostCentreCode, String paramDescription,
                                                                                                          String paramShortDescription, String paramStatusMsg, int paramStatusNumber)  throws Throwable {
        String costCentreCodeValue = "";
        boolean presenceCheck = false;
        String costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_COSTCENTRE");
        costCentreEndPoint = customerEndPoint + costCentreEndPoint;
        System.out.println("costCentreEndPoint " + costCentreEndPoint);
        logger.log("Request path -->"+costCentreEndPoint);
        logger.log("Request body is ---> "+requestCardDetailsParams);

        String customerCostCentreCode = requestCardDetailsParams.getString("customerCostCentreCode");
        String description = requestCardDetailsParams.getString("description");
        String shortDescription = requestCardDetailsParams.getString("shortDescription");

        response = putRequestAsBearerAuthWithBodyData(costCentreEndPoint, requestCardDetailsParams.toString(), authorizationToken);
        if(response.getStatusCode()==200){
            presenceCheck = common.checkingThePresenceOfCostCentreDB(customerCostCentreCode,description,shortDescription);
            if(presenceCheck){
                logger.log("DB Validation passed-Created CostCentre found in DB");
            }
            else{
                softFail("DB Validation failed-Created CostCentre not found in DB",logger);
            }
        }else {
            logger.log("Response body is " + response.getBody().prettyPrint());

            commonMethods.userNeedsToValidateTheResponseBodyObtainedFromTheAPI(costCentreCodeValue, paramStatusMsg, paramStatusNumber,
                    paramCostCentreCode, paramDescription, paramShortDescription, logger);
        }
        ArrayList<String> keyParams =
                new ArrayList<String>(Arrays.asList(new String[]{"customerCostCentreCode","description","shortDescription","existingCustomerCostCentreCode"}));
        System.out.println("keyParams---->"+keyParams);
        commonMethods.removeRequestParams(keyParams);
    }

    @Then("^user hit the get request to get the list of cost centre details$")
    public void userHitTheGetRequestToListCostCentreDetails() throws Throwable {
        String costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_COSTCENTRE");
        costCentreEndPoint = customerEndPoint + costCentreEndPoint;
        System.out.println("costCentreEndPoint " + costCentreEndPoint);
        logger.log("Request path -->"+costCentreEndPoint);
        response = getRequestWithPath(costCentreEndPoint, authorizationToken);
        logger.log("Response body is " + response.getBody().prettyPrint());
    }

    @Then("^user hit the get request to get the list of cost centre details and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userHitTheGetRequestToGetTheListOfCustomerContactDetailsAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        String costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_COSTCENTRE");
        costCentreEndPoint = customerEndPoint + costCentreEndPoint;
        System.out.println("costCentreEndPoint " + costCentreEndPoint);
        logger.log("Request path -->"+costCentreEndPoint);
        response = getRequestWithPath(costCentreEndPoint, authorizationToken);
        logger.log("Response body is " + response.getBody().prettyPrint());
        if (response.getStatusCode() == 200) {
//            presenceCheck = common.checkingThePresenceOfCostCentreDB(customerCostCentreCode,description,shortDescription);
//            if(presenceCheck){
//                logger.log("DB Validation passed-Created Contact Details found in DB",logger);
//            }
//            else{
//                softFail("DB Validation failed-Created Contact Details not found in DB",logger);
//            }
        } else
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
    }
}


