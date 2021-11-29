package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.*;

public class DriverAndVehicleModules extends RequestMethodsUtils {

    public Scenario logger;
    String customerNumber, cardNumber, driverName, vehicleId, VRN, cardsEndPoint, responseBody;
    //    JSONObject requestCardDetailsParams = new JSONObject();
    Map<String, String> cardsInformation = new HashMap<String, String>();
    ArrayList<String> cardNumbersInPageResponse = new ArrayList<String>();
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    JSONArray arrayFormatForCusNo = new JSONArray();
    boolean presenceCheck = false;

    Random rand = new Random();
    public DriverAndVehicleModules(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }

    @Then("^user hit the get request to get the list of driver details and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userHitTheGetRequestToGetTheListOfDriverDetailsAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        boolean presenceCheck = false;
        String driversEndPoint = PropUtils.getPropValue(inputProp, "GET_DRIVERS");
        driversEndPoint = customerEndPoint + driversEndPoint;
        System.out.println("costCentreEndPoint " + driversEndPoint);
        logger.log("Request path -->"+driversEndPoint);
        response = getRequestWithPath(driversEndPoint, authorizationToken);
        logger.log("Response body is " + response.getBody().prettyPrint());
        if (response.getStatusCode() == 200) {
            System.out.println("inside getNestedArray");

            presenceCheck = commonMethods.getDriverDetailsAndValidate();
            if(presenceCheck){
                logger.log("DB Validation passed-Driver Details found in DB");
            }
            else{
                softFail("DB Validation failed-Driver Details not found in DB",logger);
            }
        } else
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
    }

    @Then("^user hit the get request to get the list of vehicle details and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userHitTheGetRequestToGetTheListOfVehicleDetailsAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        boolean presenceCheck = false;
        String vehiclesEndPoint = PropUtils.getPropValue(inputProp, "GET_VEHICLES");
        vehiclesEndPoint = customerEndPoint + vehiclesEndPoint;
        System.out.println("costCentreEndPoint " + vehiclesEndPoint);
        logger.log("Request path -->"+vehiclesEndPoint);
        response = getRequestWithPath(vehiclesEndPoint, authorizationToken);
        logger.log("Response body is " + response.getBody().prettyPrint());
        if (response.getStatusCode() == 200) {
//            System.out.println("inside getNestedArray");

            presenceCheck = commonMethods.getVehicleDetailsAndValidate();
            if(presenceCheck){
                logger.log("DB Validation passed-Vehicle Details found in DB");
            }
            else{
                softFail("DB Validation failed-Vehicle Details not found in DB",logger);
            }
        } else
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
    }
    @When("^user able to gets the driver details based on customer numbers \"([^\"]*)\"$")
    public void user_able_to_gets_the_driver_details_based_on_customer_numbers(String customerScenarios) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String driversEndPoint = PropUtils.getPropValue(inputProp, "GET_DRIVERS");
        String firstCustomerNoFromDB = null;
        if(customerScenarios.equals("singleCusNo")){
            List<Map<String, String>> getListOfCusNumbers = common.getListOfCustomerNumbers();
            int actualCount = rand.nextInt(getListOfCusNumbers.size());
            Map<String,String> specificCustomerNo = getListOfCusNumbers.get(actualCount);
            firstCustomerNoFromDB = specificCustomerNo.get("CUSTOMER_NO");
        }
        else if (customerScenarios.equals("invalidAccountNo")){
            firstCustomerNoFromDB = "1234";
        }
        driversEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER") + "/" + firstCustomerNoFromDB +driversEndPoint;
        logger.log("Driver end point "+driversEndPoint);
        response = getRequestAsBearerAuth(driversEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for drivers search " + response.jsonPath().prettyPrint());
    }
    @When("^user able to gets the vehicles details based on customer numbers for \"([^\"]*)\"$")
    public void userAbleToGetsTheVehiclesDetailsBasedOnCustomerNumbersFor(String customerScenarios) throws Throwable {
        String vehiclesEndPoint = PropUtils.getPropValue(inputProp, "GET_VEHICLES");

        //   vehiclesEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");
        String firstCustomerNoFromDB = null;
        if(customerScenarios.equals("singleCusNo")){
            List<Map<String, String>> getListOfCusNumbers = common.getListOfCustomerNumbers();
            int actualCount = rand.nextInt(getListOfCusNumbers.size());
            Map<String,String> specificCustomerNo = getListOfCusNumbers.get(actualCount);
            firstCustomerNoFromDB = specificCustomerNo.get("CUSTOMER_NO");
        }
        else if (customerScenarios.equals("invalidAccountNo")){
            firstCustomerNoFromDB = "1234";
        }
        vehiclesEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER") + "/" + firstCustomerNoFromDB +vehiclesEndPoint;
        logger.log("Vehicle end point is " + vehiclesEndPoint);
        response = getRequestAsBearerAuth(vehiclesEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for vehicle search " + response.jsonPath().prettyPrint());
    }

    @When("user able to gets the List of cost centres details based on customer numbers for \"([^\"]*)\"$")
    public void userAbleToGetsTheListOfCostCentresDetailsBasedOnCustomerNumbersFor(String customerScenarios) throws Throwable {
        String costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_COSTCENTRE");
        //   costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");
        String firstCustomerNoFromDB = null;
        if(customerScenarios.equals("singleCusNo")){
            List<Map<String, String>> getListOfCusNumbers = common.getListOfCustomerNumbers();
            int actualCount = rand.nextInt(getListOfCusNumbers.size());
            Map<String,String> specificCustomerNo = getListOfCusNumbers.get(actualCount);
            firstCustomerNoFromDB = specificCustomerNo.get("CUSTOMER_NO");
        }
        else if (customerScenarios.equals("invalidAccountNo")){
            firstCustomerNoFromDB = "1234";
        }
        costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER") + "/" + firstCustomerNoFromDB +costCentreEndPoint;
        response = getRequestAsBearerAuth(costCentreEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for cost centres search " + response.jsonPath().prettyPrint());
    }
    @Then("^Verify the actual and expected response based on \"([^\"]*)\" and \"([^\"]*)\" and moduleType as \"([^\"]*)\"$")
    public void verifyTheActualAndExpectedResponseBasedOnAndAndModuleTypeAs(String statusNumber, String statusMessage, String moduleType) throws Throwable {
        if (response.getStatusCode() == 200) {
            System.out.println("inside getNestedArray");
            if (moduleType.equals("drivers")) {
                presenceCheck = commonMethods.getDriverDetailsAndValidate();
            }
            else if(moduleType.equals("vehicles")){
                presenceCheck = commonMethods.getVehicleDetailsAndValidate();
            }
            else if(moduleType.equals("costCentres")){
                presenceCheck = commonMethods.getCostCentreDetailsAndValidate();
            }
            if(presenceCheck){
                logger.log("DB Validation passed- "+ moduleType+" Details found in DB");
            }
            else{
                softFail("DB Validation failed- "+moduleType+" Details not found in DB",logger);
            }
        } else
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, Integer.parseInt(statusNumber), logger);

    }
}