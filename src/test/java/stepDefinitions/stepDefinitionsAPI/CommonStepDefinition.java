package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.SkipException;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonStepDefinition extends RequestMethodsUtils {

    public io.cucumber.java.Scenario logger;
    String customerNumber, cardNumber, driverName, vehicleId, VRN, cardsEndPoint, responseBody;
    //    JSONObject requestCardDetailsParams = new JSONObject();
    Map<String, String> cardsInformation = new HashMap<String, String>();
    ArrayList<String> cardNumbersInPageResponse = new ArrayList<String>();
    CommonDBUtils common = new CommonDBUtils();

    public CommonStepDefinition(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @Given("^User gets the base url EndPoint and passing \"([^\"]*)\" to create test$")
    public void user_gets_the_base_url_EndPoint_and_passing_to_create_test(String testName) throws Throwable {
        logger.log("testName----->" + testName);
        RestAssured.baseURI = PropUtils.getPropValue(inputProp, "baseURL");
        System.out.println("RestAssured.baseURI  " + RestAssured.baseURI);
        logger.log("Base Url ---->  " + RestAssured.baseURI);
    }

    @When("^user pass customer number\"([^\"]*)\" as parameter$")
    public void user_pass_customer_number_as_parameter(String paramCust) throws Throwable {
        String customerEndpoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");
        common.initializeDBEnvironment();
        if (paramCust.equalsIgnoreCase("invalidCus")) {
            customerNumber = "1234567890";
        } else
            customerNumber = common.getCustomerNumberFromDB(paramCust);
        System.out.println("cus no " + customerNumber);
        customerEndPoint = customerEndpoint +"/"+ customerNumber;
        System.out.println("customerEndPoint " + customerEndPoint);
    }

    @When("^user pass account endpoint along with customer number\"([^\"]*)\" as parameter$")
    public void user_pass_account_endpoint_along_with_customer_number_as_parameter(String paramCust) throws Throwable {
        String accountEndpoint = PropUtils.getPropValue(inputProp, "GET_ACCOUNT");
        common.initializeDBEnvironment();
        if (paramCust.equalsIgnoreCase("invalidCus")) {
            customerNumber = "1234567890";
        } else
            customerNumber = common.getCustomerNumberFromDB(paramCust);
        System.out.println("cus no " + customerNumber);
        customerEndPoint = accountEndpoint + customerNumber;
        System.out.println("customerEndPoint " + customerEndPoint);
//        logger.log(RestAssured.baseURI+customerEndpoint);
    }

    @Then("^verify the response status should be (\\d+)$")
    public void verify_the_response_status_should_be(int statusCode) throws Throwable {
        Properties properties = PropUtils.getProps(inputDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            int responseStatus = response.getStatusCode();
            System.out.println("Response code is " + responseStatus);
            validateStatusNumber(responseStatus, statusCode, logger, "Expected status code from response is --> " + statusCode);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^user pass \"([^\"]*)\" string parameter for the key \"([^\"]*)\" to request body$")
    public void userPassStringParameterForTheKeyToRequestBody(String param, String key) throws Throwable {
        String customerCostCentreCode = "", description = "costCentre", shortDescription = "costCentre",
                invalidValues = "co";
        String temp1 = "";
        String costCentreEndPoint = PropUtils.getPropValue(inputProp, "GET_COSTCENTRE");
        costCentreEndPoint = customerEndPoint + costCentreEndPoint;
        String[] customerNo = customerEndPoint.split("/");
        System.out.println(customerNo[2]);

        if (param.equals("new")) {
            customerCostCentreCode = fakerAPI().name().firstName();
            System.out.println("customerCostCentreCode" + customerCostCentreCode);
            temp1 = customerCostCentreCode;
        } else if (param.equals("existing")) {
            common.initializeDBEnvironment();
//		        contactName = common.getContactNameForSpecificCustomerFromDB(customerNo[2]);
            temp1 = common.getCustomerCostCentreCodeFromDB(customerNo[2]);
            System.out.println("costCentreCodeValue" + temp1);
//			costCentreCodeValue = customerCostCentreCode;
            System.out.println("inside existing");
            System.out.println("customerCostCentreCode2" + customerCostCentreCode);
            System.out.println("costCentreCodeValue---->" + temp1);
        } else if (param.equals("empty")) {
            temp1 = "";
        } else if (param.equals("specialChar")) {
            customerCostCentreCode = fakerAPI().name().firstName();
            invalidValues = customerCostCentreCode + "@";
            temp1 = customerCostCentreCode + "@";
        } else if (param.equals("invalid")) {
            temp1 = fakerAPI().name().firstName().substring(0,1);
        } else if (param.equals("max")) {
            temp1 = shortDescription + shortDescription + shortDescription
                    + shortDescription;
        } else if (param.equals("long")) {
            temp1 = shortDescription + "CCC";
        } else if (param.equals("costCentreCode")) {
            temp1 = common.getCustomerCostCentreCodeFromDB(customerNo[2]);
        } else if (param.equals("long11")) {
            temp1 = shortDescription + "C";
        }
        requestCardDetailsParams.put(key, temp1);
        System.out.println("requestCardDetailsParams :" + requestCardDetailsParams);
//		System.out.println("PropUtils.getPropValue(inputProp, \"AuthorizationToken\")---->"+PropUtils.getPropValue(inputProp, "AuthorizationToken"));
//        logger.log("Request body is "+requestCardDetailsParams);
    }

    @Then("^user pass (\\d+) integer parameter to request body$")
    public void userPassStatusNumberIntegerParameterToRequestBody() {

    }

    @Then("^validate the response status code \"([^\"]*)\"$")
    public void validate_the_response_status_code(String statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" + responseStatus);
        Assert.assertEquals(responseStatus, Integer.parseInt(statusCode));
        logger.log("Actual response status code is " + responseStatus);
        logger.log("Expected response status code is " + statusCode);

    }

    @Then("^validate and verify each details from response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyEachDetailsFromResponseWithAndStatusNumber(String ParamStatusMessage, int paramStatusNumber) {

    }
}


