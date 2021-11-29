package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

//import listeners.ExtentReportListener;

public class Lookups extends RequestMethodsUtils {

    public io.cucumber.java.Scenario logger;
    //public ExtentTest test;
    // JSONObject requestParams = new JSONObject();
    private String user, olsPassword;
    //Scenario loggerInfo = null;
     
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    Random rand = new Random();
    String specificLocNo, specificCostCentresValue, customerNo;

    public  Lookups(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;

    }
    @Given("user gets the base url for lookup API and passing \"([^\"]*)\" to create test")
    public void userGetsTheBaseUrlForLookupAPIAndPassingToCreateTest(String testName) throws Throwable {
        System.out.println("testName----->"+testName);
        //log = test.createNode(new GherkinKeyword("Given"), "User wants to be set grant type  to use the API");
        RestAssured.baseURI = PropUtils.getPropValue(inputProp,"baseURL");
        System.out.println("RestAssured.baseURI  " + RestAssured.baseURI);
        logger.log("Base Url ---->  " + RestAssured.baseURI);
    }


    @When("^user able to get the list of countries based on user access level \"([^\"]*)\"$")
    public void userAbleToGetTheListOfCountriesBasedOnUserAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("When"), "User able to get the list of countries based on client level and country level");
        String countriesEndPoint = PropUtils.getPropValue(inputProp, "lookupForCountries");
        logger.log("Request path is "+countriesEndPoint);
        if(accessLevel.equals("clientLevel")){
            response = getRequestAsBearerAuth(countriesEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));

        }
        else if(accessLevel.equals("countryLevel")){
            commonMethods.loginSetUpForCountryLevel();
            refreshPropertiesFile();
            response = getRequestAsBearerAuth(countriesEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        }
        logger.log("Response for countries lookups"+response.jsonPath().prettyPrint());
    }

    @Then("Validates the countries from response and DB based on access level \"([^\"]*)\"$")
    public void validatesTheCountriesFromResponseAndDBBasedOnAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("Then"), "User validates the client countries from response and DB");
        List<Map<String,String>> listOfCountriesFromDB = null;
        Map<String,String> countryDetailsFromDB;
        String countryDescription;
        JSONParser parser = new JSONParser();
        JSONArray countriesResponse = (JSONArray) parser.parse(response.asString());
        System.out.println("JSON Array format for countries response is "+countriesResponse);
        System.out.println("Size of countries is "+countriesResponse.size());
        if(accessLevel.equals("clientLevel")){
            System.out.println("Inside of if");
            listOfCountriesFromDB = common.getListOfClientCountriesFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
        }
        else if(accessLevel.equals("countryLevel")){
            System.out.println("Inside of else if");
            listOfCountriesFromDB = common.getListOfClientCountriesFromDB(PropUtils.getPropValue(inputProp, "countryLevelUserNameAPI"));
        }
        for(int i=0;i<listOfCountriesFromDB.size();i++){
            countryDetailsFromDB = listOfCountriesFromDB.get(i);
            System.out.println("Country details from dB is "+countryDetailsFromDB);
            countryDescription = countryDetailsFromDB.get("NAME").toString();
            System.out.println("Specific country description from dB is "+countryDescription);
            for(int j=0;j<countriesResponse.size();j++){
                if(countriesResponse.get(j).equals(countryDescription)){
                    System.out.println("Inside of validation if");
                    logger.log("Validated the country name from response and DB");
                    break;
                }

            }


        }
        
    }

    @When("^user able to get the list of cost centres based on user access level \"([^\"]*)\"$")
    public void userAbleToGetTheListOfCostCentresBasedOnUserAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("When"), "User able to get the list of cost centres based on "+accessLevel);
        String customerCostCentre = PropUtils.getPropValue(inputProp, "lookupForCustomerCostCentres");
        Map<String,String> specificCostCentreInList = null;
        List<Map<String,String>> costCentreValue = common.getCustomerCostCentreCode(accessLevel);
        int actualCount = costCentreValue.size();
        int count = rand.nextInt(actualCount);
        System.out.println("Number of cost centre values "+actualCount);
        specificCostCentreInList = costCentreValue.get(count);
        specificCostCentresValue = specificCostCentreInList.get("CUSTOMER_COST_CENTRE_CODE");

        if (accessLevel.equals("clientLevel")) {
            response = getRequestAsBearerAuthWithSingleQueryParam(customerCostCentre,
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"), "costCentreCode", specificCostCentresValue);
        } else if (accessLevel.equals("countryLevel")) {
            commonMethods.loginSetUpForCountryLevel();
            refreshPropertiesFile();
            response = getRequestAsBearerAuthWithSingleQueryParam(customerCostCentre,
                    PropUtils.getPropValue(inputProp, "CountryLevelToken"), "costCentreCode", specificCostCentresValue);
        }
        logger.log("Response for Customer cost centre lookups " + response.jsonPath().prettyPrint());
    }

    @When("^user able to get the list of customer numbers based on user access level \"([^\"]*)\"$")
    public void userAbleToGetTheListOfCustomerNumbersBasedOnUserAccessLevel(String accessLevel)throws Throwable {

        //log = test.createNode(new GherkinKeyword("When"), "User able to get the list of customer numbers based on "+accessLevel);
        String customerNumberEndPoint = PropUtils.getPropValue(inputProp, "lookupForCustomerNumbers");
        logger.log("Lookup end point for"+customerNumberEndPoint);
        Map<String,String> specificCusNoFromList = null;
        List<Map<String,String>> listOfCustomers = common.getListOfCustomerNumbers(accessLevel);
        int actualCount = listOfCustomers.size();
        int count = rand.nextInt(actualCount);
        System.out.println("Number of Customers "+actualCount);
        specificCusNoFromList = listOfCustomers.get(count);
        customerNo = specificCusNoFromList.get("CUSTOMER_NO");
        if (accessLevel.equals("clientLevel")) {
            response = getRequestAsBearerAuthWithMultipleQueryParams(customerNumberEndPoint,
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"), "customerNumber", customerNo, "countryCode", "AT");
        } else if (accessLevel.equals("countryLevel")) {
            commonMethods.loginSetUpForCountryLevel();
            refreshPropertiesFile();
            response = getRequestAsBearerAuthWithMultipleQueryParams(customerNumberEndPoint,
                    PropUtils.getPropValue(inputProp, "CountryLevelToken"), "customerNumber", customerNo, "countryCode", "AT");
        }
        logger.log("Response for Customer Number lookups " + response.jsonPath().prettyPrint());
    }


    @When("^user able to get the list of location numbers based on user access level \"([^\"]*)\"$")
    public void userAbleToGetTheListOfLocationNumbersBasedOnUserAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("When"), "User able to get the list of location numbers based on "+accessLevel);
        String customerCostCentre = PropUtils.getPropValue(inputProp, "lookupForLocationNumbers");
        Map<String,String> specificLocationNumberInList = null;
        List<Map<String,String>> locationNumbers = common.getListOfLocationNumbers(accessLevel);
        int actualCount = locationNumbers.size();
        int count = rand.nextInt(actualCount);
        System.out.println("Number of Location numbers "+actualCount);
        specificLocationNumberInList = locationNumbers.get(count);
        specificLocNo = specificLocationNumberInList.get("LOCATION_NO");
        if (accessLevel.equals("clientLevel")) {
            response = getRequestAsBearerAuthWithSingleQueryParam(customerCostCentre,
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"), "locationNumber", specificLocNo);
        } else if (accessLevel.equals("countryLevel")) {
            commonMethods.loginSetUpForCountryLevel();
            refreshPropertiesFile();
            response = getRequestAsBearerAuthWithSingleQueryParam(customerCostCentre,
                    PropUtils.getPropValue(inputProp, "CountryLevelToken"), "locationNumber", specificLocNo);
        }
        logger.log("Response for location numbers lookups " + response.jsonPath().prettyPrint());
    }

    @Then("^Validate the cost centre values from response and DB based on access level \"([^\"]*)\"$")
    public void validateTheCostCentreValuesFromResponseAndDBBasedOnAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("Then"), "Validate Customer cost centre based on "+accessLevel);
        JSONParser parser = new JSONParser();
        JSONArray costCentreResponse = (JSONArray) parser.parse(response.asString());
        for(int i=0;i<costCentreResponse.size();i++){
            String costCentre = costCentreResponse.get(i).toString();
            System.out.println("Cost centre from response "+costCentre);
            commonMethods.responseValidationsCheckingEquals( costCentre,specificCostCentresValue, "Cost centre ",logger);
        }
    }

    @Then("Validate the customer number from response and DB based on access level \"([^\"]*)\"$")
    public void validateTheCustomerNumberFromResponseAndDBBasedOnAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("Then"), "Validate customer number based on "+accessLevel);
        JSONParser parser = new JSONParser();
        JSONArray customerNumbersList = (JSONArray) parser.parse(response.asString());
        for(int i=0;i<customerNumbersList.size();i++){
            String custNoFromResponse = customerNumbersList.get(i).toString();
            System.out.println("Customer number from response "+custNoFromResponse);
            commonMethods.responseValidationsCheckingEquals( custNoFromResponse,customerNo, "Customer number ",logger);
        }
    }

    @Then("Validate the location numbers from response and DB based on access level \"([^\"]*)\"$")
    public void validateTheLocationNumbersFromResponseAndDBBasedOnAccessLevel(String accessLevel) throws Throwable {
        //log = test.createNode(new GherkinKeyword("Then"), "Validate Location number based on "+accessLevel);
        JSONParser parser = new JSONParser();
        JSONArray locationNumbersList = (JSONArray) parser.parse(response.asString());
        for(int i=0;i<locationNumbersList.size();i++){
            String locNoFromResponse = locationNumbersList.get(i).toString();
            System.out.println("Location number from response "+locNoFromResponse);
            commonMethods.responseValidationsCheckingEquals( locNoFromResponse,specificLocNo, "Location number ",logger);
        }
    }

}






   
