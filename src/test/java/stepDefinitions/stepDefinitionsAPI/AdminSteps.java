package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utilities.api.*;

import java.util.Random;

public class AdminSteps extends RequestMethodsUtils {

    public Scenario logger;
    CommonDBUtils commonDBUtils = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    CommonDBQueries commonDBQueries = new CommonDBQueries();
    JSONParser parser = new JSONParser();
    JSONArray arrayFormatForCusNo = new JSONArray();
    Random rand = new Random();

    @When("^User get vat numbers for an active customer$")
    public void userGetVatNumbersForAnActiveCustomer() throws Throwable {
//        arrayFormatForCusNo.add(commonDBQueries.getCustomerNoWhichHasVatNumbers());
        String getVatNumberEndPoint = PropUtils.getPropValue(inputProp,"getVatNumbersEndPoint").replace("<CustomerNo>",commonDBQueries.getCustomerNoWhichHasVatNumbers());
        logger.log("Request path for get vat number : "+getVatNumberEndPoint);
        response = getRequestAsBearerAuth(getVatNumberEndPoint,PropUtils.getPropValue(inputProp,"AuthorizationToken"));
        logger.log("Response body : "+response.getBody().prettyPrint());
    }
    @When("^validate vatNumbers response with the data base$")
    public void validateVatNumbersResponseWithTheDataBase() throws Throwable {
        JSONObject taxNumbersResponse = (JSONObject) parser.parse(response.asString());
        taxNumbersResponse.get("accountNumber");

        if(taxNumbersResponse.containsKey("taxNumbersList")){
            JSONArray taxNumbersList = (JSONArray) taxNumbersResponse.get("taxNumbersList");
            System.out.println("List of Cards from response is " + taxNumbersList.size());
            int actualCount = rand.nextInt(taxNumbersList.size());
            org.json.JSONObject details = new org.json.JSONObject();
            org.json.JSONObject taxObj = new org.json.JSONObject();
            for(int i=0;i<=taxNumbersList.size()-1;i++){
                taxObj = details.getJSONObject(String.valueOf(i));
//                commonMethods.assertTwoStringsForAPI(taxObj.get(""),);
//                commonMethods.assertTwoStringsForAPI(details.get("taxNo"));
            }
        }
    }
}
