package stepDefinitions.stepDefinitionsAPI;

import com.github.javafaker.Faker;
import com.mongodb.util.JSON;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;

import org.json.simple.parser.ParseException;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AccountsModule extends RequestMethodsUtils {
    CommonMethods commonMethods = new CommonMethods();
    CommonDBUtils common = new CommonDBUtils();
    Faker faker = new Faker();
    public String customerNumber, countryId;
    String cusNo;
    Random rand = new Random();
    public Scenario logger;
    org.json.simple.JSONObject accountInformationResponse = null;
    org.json.simple.JSONObject customerValueResponse = null;
    org.json.simple.JSONArray customerTypeResponse = null;
    org.json.simple.JSONObject customerTypeObject = null;
    JSONParser parser = new JSONParser();
    HashSet<String> set = new HashSet();
    org.json.simple.JSONObject financialResponse = null;
    org.json.simple.JSONObject applicationObject = null;
    org.json.simple.JSONObject financialAccountObject = null;
    org.json.simple.JSONObject financialAccountCreditInfoObject = null;


    public AccountsModule(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @When("^user should able to get the list of accounts for associated user$")
    public void user_should_able_to_get_the_list_of_accounts_for_associated_user() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String accountsEndPoint = PropUtils.getPropValue(inputProp, "accountsEndPoint");
        response = getRequestAsBearerAuth(accountsEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for accounts " + response.jsonPath().prettyPrint());
        logger.log("Response for accounts " + response.jsonPath().prettyPrint());
    }


    @When("^user should able to get the specific account information based on scenario \"([^\"]*)\"$")
    public void user_should_able_to_get_the_specific_account_information_based_on_scenario(String scenarioType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String accountEndPoint = PropUtils.getPropValue(inputProp, "GET_ACCOUNT");
        if (scenarioType.equals("associatedUser")) {
            customerNumber = common.getCustomerNumberFromDB(scenarioType);


        } else if (scenarioType.equals("invalidAccountNo")) {
            customerNumber = faker.number().digits(10);
        } else {
            customerNumber = common.getCustomerNumberFromDB("");
        }


        response = getRequestAsBearerAuth(accountEndPoint + customerNumber, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        //	System.out.println("Response for accounts "+response.jsonPath().prettyPrint());

    }


    @Then("^validate the account details and error response status number \"([^\"]*)\" and status message \"([^\"]*)\"$")
    public void validate_the_account_details_and_error_response_status_number_and_status_message(String statusNumber,
                                                                                                 String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        Map<String, String> accountInfo = new HashMap<String, String>();
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            System.out.println("Inside of if statement");
            logger.log("Response for accounts is " + response.jsonPath().prettyPrint());
            accountInfo = common.validateSpecificAccountInformations(customerNumber);

            if (response.jsonPath().get("accountNumber").equals(accountInfo.get("CUSTOMER_NO"))
                    && response.jsonPath().get("customerName").equals(accountInfo.get("NAME"))
                    && response.jsonPath().get("accountStatusType").equals(accountInfo.get("DESCRIPTION"))) {
                System.out.println("Inside of if ");
                logger.log(" Validated the specific account information from response and DB");

            } else {
                logger.log("Validation is not done for specific account information");
            }

        } else {
            int statusNumberFromResponse = response.path("errors.statusNumber");
            System.out.println("Status Number from response is " + statusNumberFromResponse);

            validateStatusNumber(statusNumberFromResponse, Integer.parseInt(statusNumber), logger,
                    "Expected status number from response is " + statusNumberFromResponse);

            String statusMessageFromResponse = response.path("errors.statusMessage");
            System.out.println("Status Message from response is " + statusMessageFromResponse);

            validateResponseMessage(statusMessageFromResponse, statusMessage, logger,
                    "Expected status message from response is " + statusMessageFromResponse);

        }

    }

    @When("^user can able to update the account information and contact details$")
    public void user_can_able_to_update_the_account_information_and_contact_details() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        JSONObject jobjStreetAddressValues = new JSONObject();

        Map<String, String> accountInfo = new HashMap<String, String>();
        Map<String, String> newAccountDetails = new HashMap<String, String>();
        String customerEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");
        customerNumber = common.getCustomerNumberFromDB("associatedUser");
        accountInfo = common.validateSpecificAccountInformations(customerNumber);
        requestParams.put("contactName", faker.name().firstName() + "_contact");
        requestParams.put("emailAddress", faker.name().firstName() + "@gmail.com");
        requestParams.put("phoneBusiness", faker.number().digits(10));
        requestParams.put("phoneFax", faker.number().digits(5));
        requestParams.put("phoneMobile1", faker.number().digits(10));

        jobjStreetAddressValues.put("addressLine", faker.name().fullName() + "_address");
        jobjStreetAddressValues.put("postalCode", faker.number().digits(5));
        jobjStreetAddressValues.put("suburb", faker.name().lastName());

        requestParams.put("streetAddress", jobjStreetAddressValues);
        System.out.println("Request params or accounts " + requestParams);
        logger.log("Request params or accounts " + requestParams);
        response = putRequestAsBearerAuthWithBodyData(customerEndPoint +"/"+ customerNumber, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        if (response.getStatusCode() == 200) {

            newAccountDetails = common.validateSpecificAccountInformations(customerNumber);
            if (!(accountInfo.get("CONTACT_NAME").equals(newAccountDetails.get("CONTACT_NAME")))
                    && !(accountInfo.get("EMAIL_ADDRESS").equals(newAccountDetails.get("EMAIL_ADDRESS")))
                    && !(accountInfo.get("PHONE_BUSINESS").equals(newAccountDetails.get("PHONE_BUSINESS")))
                    && !(accountInfo.get("PHONE_FAX").equals(newAccountDetails.get("PHONE_FAX")))
                    && !(accountInfo.get("PHONE_MOBILE_1").equals(newAccountDetails.get("PHONE_MOBILE_1")))
                    && !(accountInfo.get("ADDRESS_LINE").equals(newAccountDetails.get("ADDRESS_LINE")))
                    && !(accountInfo.get("POSTAL_CODE").equals(newAccountDetails.get("POSTAL_CODE")))
                    && !(accountInfo.get("SUBURB").equals(newAccountDetails.get("SUBURB")))) {
                System.out.println("Inside of if");
                System.out.println("Account details validated");
                logger.log("Validated the updated customer contact information from response and DB");

            } else {
                System.out.println("Inside of else if");
                logger.log("Validation is not done for updating customer contact information");
            }
        }

    }

    @When("User hit the accounts search API without any filters")
    public void userHitTheAccountsSearchAPIWithoutAnyFilters() throws Throwable {
        String accountsSearch = PropUtils.getPropValue(inputProp, "accountsSearch");
        logger.log("Request Path --> " + accountsSearch);
        requestBodyParams = Json.createObjectBuilder()
                .add("clientGroupMid", "4723")
                .build();
        logger.log("Request body --> " + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(accountsSearch, requestBodyParams.toString(), authorizationToken);
    }

    @When("^User hit the accounts search API with filter parameter as \"([^\"]*)\"$")
    public void userHitTheAccountsSearchAPIWithFilters(String filterParam) throws Throwable {
        String accountsSearch = PropUtils.getPropValue(inputProp, "accountsSearch");
        logger.log("Request Path --> " + accountsSearch);
        String accountNumber, accountName, country, cardNo, clientGroupMid;
        Map<String, String> getCusNoWithCards = common.getListOfCustomerNosHavingCards();
        accountNumber = getCusNoWithCards.get("CUSTOMER_NO");
        accountName = getCusNoWithCards.get("NAME");
        cardNo = getCusNoWithCards.get("CARD_NO");
//To get clientGroupMid from userContext API
        String userContextEndPoint = PropUtils.getPropValue(inputProp, "userContext");
        Response responseContext = getRequestAsBearerAuth(userContextEndPoint, authorizationToken);
        clientGroupMid = responseContext.jsonPath().getString("clientGroupMid");
//To get country from countries API
        String countriesEndPoint = PropUtils.getPropValue(inputProp, "lookupForCountries");
        Response responseCountry = getRequestAsBearerAuth(countriesEndPoint, authorizationToken);
        JSONArray jsonarray = new JSONArray(responseCountry.asString());
        int actualCount = jsonarray.length();
        int row = rand.nextInt(actualCount);
        country = jsonarray.getString(row);
//Conditions based on filters
        if (filterParam.equalsIgnoreCase("accountNo")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("accountNumber", accountNumber)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else if (filterParam.equalsIgnoreCase("accountNoWildChar")) {
            int length = accountNumber.length();
            System.out.println("accountNumber--> " + length);
            accountNumber = accountNumber.substring(length / 2, length);
            requestBodyParams = Json.createObjectBuilder()
                    .add("accountNumber", accountNumber)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else if (filterParam.equalsIgnoreCase("Country")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("country", country)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else if (filterParam.equalsIgnoreCase("cardNo")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("cardNo", cardNo)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else if (filterParam.equalsIgnoreCase("cardNoWildChar")) {
            int length = cardNo.length();
            System.out.println("cardNo length --> " + length);
            cardNo = cardNo.substring(length - 6, length);
            requestBodyParams = Json.createObjectBuilder()
                    .add("cardNo", cardNo)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else if (filterParam.equalsIgnoreCase("accountName")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("accountName", accountName)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else if (filterParam.equalsIgnoreCase("accountNameWildChar")) {
            int length = accountName.length();
            System.out.println("accountName length --> " + length);
            accountName = accountName.substring(0, length / 2);
            requestBodyParams = Json.createObjectBuilder()
                    .add("accountName", accountName)
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        } else
            requestBodyParams = Json.createObjectBuilder()
                    .add("clientGroupMid", clientGroupMid)
                    .build();
        logger.log("Request body --> " + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(accountsSearch, requestBodyParams.toString(), authorizationToken);
        logger.log("Response body --> " + response.getBody().prettyPrint());
    }

    @When("^user gets the specific account details based on country Id as \"([^\"]*)\"$")
    public void userGetsTheSpecificAccountDetailsBasedOnCountryIdAs(String filterSearch) throws Throwable {
        String accountDetailsEndPoint = PropUtils.getPropValue(inputProp, "accountDetails");
        JSONArray arrayResponseForAccounts = new JSONArray(response.asString());
        int actualCount = arrayResponseForAccounts.length();
        int row = rand.nextInt(actualCount);
        JSONObject specificJSONObject = arrayResponseForAccounts.getJSONObject(row);
        logger.log("Specific JSON Object for account search response " + specificJSONObject);
        customerNumber = specificJSONObject.get("accountNumber").toString();
        System.out.println("Account number from account search response " + customerNumber);
        countryId = specificJSONObject.get("countryId").toString();
        System.out.println("Country Id from account search response " + countryId);
        requestParams.put("accountNumber", customerNumber);
        if (filterSearch.equals("validCountryId")) {
            requestParams.put("countryId", countryId);
        } else {
            requestParams.put("countryId", "abc");
        }

        logger.log("Request param for account details " + requestParams);
        logger.log("Request path for account details " + accountDetailsEndPoint);
        response = postRequestAsBearerAuthWithBodyData(accountDetailsEndPoint, requestParams.toString(),
                PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        //   logger.log("Response body --> " + response.getBody().prettyPrint());
    }


    @When("^user gets the specific account information based on customer number \"([^\"]*)\"$")
    public void userGetsTheSpecificAccountInformationBasedOnCustomerNumber(String filterSearch) throws Throwable {
        String customerEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");
        //   String cusNo;
        if (filterSearch.equals("validCustomer")) {
            cusNo = commonMethods.getCustomerNoForCSRLogin();
        } else {
            cusNo = "1234";
        }
        logger.log("Request path for get account information " + customerEndPoint + "/" + cusNo);
        response = getRequestAsBearerAuth(customerEndPoint + "/" + cusNo,
                PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response body --> " + response.getBody().prettyPrint());
    }

    @When("^user gets the specific financial information based on customer number \"([^\"]*)\"$")
    public void userGetsTheSpecificFinancialInformationBasedOnCustomerNumber(String filterSearch) throws Throwable {
        String customerEndPoint = PropUtils.getPropValue(inputProp, "GET_CUSTOMER");

        if (filterSearch.equals("validCustomer")) {
            cusNo = commonMethods.getCustomerNoForCSRLogin();
        } else {
            cusNo = "1234";
        }
        logger.log("Request path for get financial information " + customerEndPoint);
        response = getRequestAsBearerAuth(customerEndPoint + PropUtils.getPropValue(inputProp, "financialEndpoint") + "/" + cusNo,
                PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response body --> " + response.getBody().prettyPrint());
    }

    @Then("validate the specific account information response with \"([^\"]*)\" and status message \"([^\"]*)\"$")
    public void validate_the_specific_account_information_response_with_and_status_message(String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> accountInformationDetails = null;

        try {
            accountInformationResponse = (org.json.simple.JSONObject) parser.parse(response.asString());
            org.json.simple.JSONObject accountObject = (org.json.simple.JSONObject) accountInformationResponse.get("account");
            org.json.simple.JSONObject accountCreditInfoObject = (org.json.simple.JSONObject) accountInformationResponse.get("accountCreditInfo");
            if (statusNumber.equals("") && statusMessage.equals("")
                    || (statusNumber.equals(null) && statusMessage.equals(null))) {
                System.out.println("Inside of if statement");
                accountInformationDetails = common.getAccountInformationFieldsFromDb(cusNo);
                if (!(accountInformationDetails.get("EMAIL_ADDRESS") == null)) {
                    System.out.println("Inside of if loop");
                    if (accountInformationResponse.get("emailAddress").equals(accountInformationDetails.get("EMAIL_ADDRESS"))) {
                        logger.log("Validated the email address field in account information");
                    }
                } else {
                    System.out.println("Inside of else loop");
                    logger.log("Email address field is not available for the customer");
                }
                if (!(accountInformationDetails.get("PHONE_MOBILE_1") == null)) {
                    if (accountInformationResponse.get("phoneMobile1").equals(accountInformationDetails.get("PHONE_MOBILE_1"))) {
                        logger.log("Validated the phone mobile field in account information");
                    }
                } else {
                    logger.log("Mobile phone field is not available for the customer");
                }
                if (!(accountInformationDetails.get("NAME") == null)) {
                    if (accountInformationResponse.get("name").equals(accountInformationDetails.get("NAME"))) {
                        logger.log("Validated the name field in account information");
                    }
                } else {
                    logger.log("Name field is not available for the customer");
                }
                if (!(accountInformationDetails.get("PHONE_FAX") == null)) {
                    if (accountInformationResponse.get("phoneFax").equals(accountInformationDetails.get("PHONE_FAX"))) {
                        logger.log("Validated the fax field in account information");
                    }
                } else {
                    logger.log("Fax field is not available for the customer");
                }
                if (!(accountInformationDetails.get("EMBOSSING_NAME") == null)) {
                    if (accountInformationResponse.get("embossingName").equals(accountInformationDetails.get("EMBOSSING_NAME"))) {
                        logger.log("Validated the embossing name field in account information");
                    }
                } else {
                    logger.log("Embossing name is not available for the customer");
                }
                if (!(accountInformationDetails.get("CONTACT_NAME") == null)) {
                    if (accountInformationResponse.get("contactName").equals(accountInformationDetails.get("CONTACT_NAME"))) {
                        logger.log("Validated the contact name field in account information");
                    }
                } else {
                    logger.log("Contact name field is not available for the customer");
                }
                if (!(accountInformationDetails.get("DESCRIPTION") == null)) {
                    if (accountInformationResponse.get("customerType").equals(accountInformationDetails.get("DESCRIPTION"))) {
                        logger.log("Validated the customer type field in account information");
                    }
                } else {
                    logger.log("Customer type field is not available for the customer");
                }
                if (!(accountInformationDetails.get("STATUS_DESCRIPTION") == null)) {
                    if (accountObject.get("accountStatus").equals(accountInformationDetails.get("STATUS_DESCRIPTION"))) {
                        logger.log("Validated the Active status field in account information");
                    }
                } else {
                    logger.log("Active status field is not available for the customer");
                }
                if (!(accountInformationDetails.get("EXT_ACCOUNT_REF") == null)) {
                    if (accountInformationResponse.get("extAccountRef").equals(accountInformationDetails.get("EXT_ACCOUNT_REF"))) {
                        logger.log("Validated the Ext Account ref field in account information");
                    }
                } else {
                    logger.log("Ext Account ref field is not available for the customer");
                }
                if (!(accountInformationDetails.get("AUTHENTICATION_ANSWER") == null)) {
                    if (accountInformationResponse.get("authenticationAnswer").equals(accountInformationDetails.get("AUTHENTICATION_ANSWER"))) {
                        logger.log("Validated the Authentication answer field in account information");
                    }
                } else {
                    logger.log("Authentication answer field is not available for the customer");
                }
                if (!(accountInformationDetails.get("BAND") == null)) {
                    if (accountInformationResponse.get("band").equals(accountInformationDetails.get("BAND"))) {
                        logger.log("Validated the Band field in account information");
                    }
                } else {
                    logger.log("Band field is not available for the customer");
                }
                if (!(accountInformationDetails.get("CUSTOMER_VALUE2_DESC") == null)) {
                    if (accountInformationResponse.get("customerControlValue1").equals(accountInformationDetails.get("CUSTOMER_VALUE2_DESC"))) {
                        logger.log("Validated the customerControlValue1 field in account information");
                    }
                } else {
                    logger.log("customerControlValue1 field is not available for the customer");
                }
                if (!(accountInformationDetails.get("CUSTOMER_VALUE3_DESC") == null)) {
                    if (accountInformationResponse.get("customerControlValue2").equals(accountInformationDetails.get("CUSTOMER_VALUE3_DESC"))) {
                        logger.log("Validated the customerControlValue2 field in account information");
                    }
                } else {
                    logger.log("customerControlValue2 field is not available for the customer");
                }
                if (!(accountInformationDetails.get("EXTERNAL_CUSTOMER_CODE") == null)) {
                    if (accountInformationResponse.get("externalCustomerCode").equals(accountInformationDetails.get("EXTERNAL_CUSTOMER_CODE"))) {
                        logger.log("Validated the Customer segment field in account information");
                    }
                } else {
                    logger.log("Customer segment field is not available for the customer");
                }
                if (!(accountInformationDetails.get("EXT_DELIVERY_NAME") == null)) {
                    if (accountInformationResponse.get("extDeliveryName").equals(accountInformationDetails.get("EXT_DELIVERY_NAME"))) {
                        logger.log("Validated the Ext delivery name field in account information");
                    }
                } else {
                    logger.log("Ext delivery name field is not available for the customer");
                }
                if (!(accountInformationDetails.get("EXT_DELIVERY_REF") == null)) {
                    if (accountInformationResponse.get("extDeliveryRef").equals(accountInformationDetails.get("EXT_DELIVERY_REF"))) {
                        logger.log("Validated the Delivery ref field in account information");
                    }
                } else {
                    logger.log("Delivery ref field is not available for the customer");
                }
                //Need to check the below code
//                if (!(accountInformationDetails.get("NACE_CODE") == null)) {
//                    if (accountCreditInfoObject.get("naceCode").equals(accountInformationDetails.get("NACE_CODE"))) {
//                        logger.log("Validated the Nace code field in account information");
//                    }
//                } else {
//                    logger.log("Nace code field is not available for the customer");
//                }
//                if (!(accountInformationDetails.get("INFORMATION_CODE_NUMBER") == null)) {
//                    if (accountCreditInfoObject.get("informationCodeNumber").equals(accountInformationDetails.get("INFORMATION_CODE_NUMBER"))) {
//                        logger.log("Validated the informationCodeNumber code field in account information");
//                    }
//                } else {
//                    logger.log("informationCodeNumber code field is not available for the customer");
         //       }
//                if (!accountInformationDetails.get("IS_INTEREST_ON_ARREARS").equals(null)) {
//                    if (response.jsonPath().get("isInterestOnArrears").equals(accountInformationDetails.get("IS_INTEREST_ON_ARREARS"))) {
//                        logger.log("Validated the interest on arrears code field in account information");
//                    }
//                } else {
//                    logger.log("interest on arrears field is not available for the customer");
//                }
            } else {
                commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, Integer.parseInt(statusNumber), logger);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @When("user updates the account information based on customer number \"([^\"]*)\"$")
    public void user_updates_the_account_information_based_on_customer_number(String customerNoScenarios) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String specificCustomerType = "";
        if (customerNoScenarios.equals("validCustomer")){
            cusNo = commonMethods.getCustomerNoForCSRLogin();
        }
        else {
            cusNo = "1234";
        }
        //Customer value lookup API
        String customerValuesEndpoint = PropUtils.getPropValue(inputProp, "lookupForCustomerValues");
        response = getRequestAsBearerAuth(customerValuesEndpoint + 1, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        if (response.getStatusCode() == 200) {
            customerValueResponse = (org.json.simple.JSONObject) parser.parse(response.asString());
            String bandValue = customerValueResponse.keySet().toString();
            set.add(bandValue);
            System.out.println("Band value is " + set);
        }
        //Customer type API
        String lookupForCustomerTypes = PropUtils.getPropValue(inputProp, "lookupForCustomerTypes");
        response = getRequestAsBearerAuth(lookupForCustomerTypes, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for customer types "+response.jsonPath().prettyPrint());
        if (response.getStatusCode() == 200) {
            customerTypeResponse = (org.json.simple.JSONArray) parser.parse(response.asString());
            for (int i = 0; i < customerTypeResponse.size(); i++) {
                customerTypeObject = (org.json.simple.JSONObject) customerTypeResponse.get(i);
                specificCustomerType = customerTypeObject.get("description").toString();

            }
        }
        String updateAccountInfo = PropUtils.getPropValue(inputProp, "updateAccountInfo");
//        accountInformationResponse = (org.json.simple.JSONObject) parser.parse(response.asString());
//        org.json.simple.JSONObject accountObject = (org.json.simple.JSONObject) accountInformationResponse.get("account");
//        org.json.simple.JSONObject accountCreditInfoObject = (org.json.simple.JSONObject) accountInformationResponse.get("accountCreditInfo");
        requestBodyParams = Json.createObjectBuilder()
                .add("name", faker.name().firstName())
                .add("emailAddress", "test1@wexinc.com")
                .add("phoneMobile1" ,faker.number().digits(10))
                .add("phoneMobile2", faker.number().digits(5))
                .add("phoneFax", faker.number().digits(4))
                .add("embossingName", faker.name().lastName())
                .add("tradingName", "Business99")
                .add("contactName", "Contact "+faker.name().name())
                .add("band", "1069")
                .add("additionalInvoiceText", "Invoice")
                .add("customerType", specificCustomerType)
                .add("cardProgram", "OMV AT Cards")
                .add("extAccountRef", "1234")
                .add("extDeliveryRef", "1234")
                .add("externalCustomerCode", "customer segment1")
                .add("extDeliveryName", "Group1")
                .add("authenticationAnswer", "yes")
                .add("account", Json.createObjectBuilder()
                        .add("accountCreditInfo", Json.createObjectBuilder().add("naceCode", 11)
                                .add("isInterestOnArrears", true)
                                .add("creditInformationNo", 123)))
                                .build();
        System.out.println("Request param for update account information " + requestBodyParams);
        response = putRequestAsBearerAuthWithBodyData(updateAccountInfo + cusNo, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        if (response.getStatusCode() == 200) {
            response = getRequestAsBearerAuth(PropUtils.getPropValue(inputProp, "GET_CUSTOMER") + "/" + cusNo,
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            logger.log("Response body --> " + response.getBody().prettyPrint());
            logger.log("Account information details are getting updated correctly");
        } else {
            logger.log("Response for update account information " + response.jsonPath().prettyPrint());
        }
    }

    @Then("^validate the specific financial information response with \"([^\"]*)\" and status message \"([^\"]*)\"$")
    public void validate_the_specific_financial_information_response_with_and_status_message(String statusNumber, String statusMessage) throws Throwable{
        // Write code here that turns the phrase above into concrete actions
        financialResponse = (org.json.simple.JSONObject) parser.parse(response.asString());
        financialAccountObject = (org.json.simple.JSONObject) financialResponse.get("account");
        applicationObject = (org.json.simple.JSONObject) financialResponse.get("application");
        financialAccountCreditInfoObject = (org.json.simple.JSONObject) financialResponse.get("accountCreditInfo");
        Map<String, String> financialAccountFromDb = common.getFinancialAccountDetails(cusNo);
        Map<String, String> financialCreditAlertFromDb = common.getFinancialCreditAlertDetails(cusNo);

        //Account details validation
        if (financialResponse.containsKey("account")){
            if (!(financialAccountFromDb.get("STATUS") == null)) {
                if (financialAccountObject.get("accountStatus").equals(financialAccountFromDb.get("STATUS"))) {
                    logger.log("Validated the account status field in financial information");
                }
            } else {
                logger.log("Account status field is not present ");
            }
            if (!(financialAccountFromDb.get("SUBSTATUS") == null)) {
                if (financialAccountObject.get("accountSubStatus").equals(financialAccountFromDb.get("SUBSTATUS"))) {
                    logger.log("Validated the account sub status field in financial information");
                }
            } else {
                logger.log("Account sub status field is not present ");
            }

            if (!(financialCreditAlertFromDb.get("CURRENTCREDITLIMIT").equals(0))) {
                if (financialAccountObject.get("creditLimit").equals(financialCreditAlertFromDb.get("CURRENTCREDITLIMIT"))) {
                    logger.log("Validated the credit limit field in financial information");
                }
            } else {
                logger.log("credit limit field is not present");
            }
            if (!(financialCreditAlertFromDb.get("CREDITPLAN")==null)) {
                if (financialAccountObject.get("creditPlan").equals(financialCreditAlertFromDb.get("CREDITPLAN"))) {
                    logger.log("Validated the credit plan field in financial information");
                }
            } else {
                logger.log("credit plan field is not present");
            }
            if (!(financialCreditAlertFromDb.get("CREDITEXTENSION")==null)) {
                if (financialAccountObject.get("creditExtension").equals(financialCreditAlertFromDb.get("CREDITEXTENSION"))) {
                    logger.log("Validated the credit extension field in financial information");
                }
            } else {
                logger.log("credit extension field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("PERCENTAGEOVERALLOWED")==null)) {
                if (financialAccountObject.get("percentOverLimit").equals(financialCreditAlertFromDb.get("PERCENTAGEOVERALLOWED"))) {
                    logger.log("Validated the percentage over allowed field in financial information");
                }
            } else {
                logger.log("percentage over allowed field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("CALCULATED_LIMIT")==null)) {
                if (financialAccountObject.get("calculatedLimit").equals(financialCreditAlertFromDb.get("CALCULATED_LIMIT"))) {
                    logger.log("Validated the calculatedLimit field in financial information");
                }
            } else {
                logger.log("calculatedLimit field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("IS_OVER_CREDIT_LIMIT")==null)) {
                if (financialAccountObject.get("overCreditLimit").equals(financialCreditAlertFromDb.get("IS_OVER_CREDIT_LIMIT"))) {
                    logger.log("Validated the overCreditLimit field in financial information");
                }
            } else {
                logger.log("overCreditLimit field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("CREDITLIMITTHRESHOLDPERC")==null)) {
                if (financialAccountObject.get("creditLimitThresholdPercentage").equals(financialCreditAlertFromDb.get("CREDITLIMITTHRESHOLDPERC"))) {
                    logger.log("Validated the creditLimitThresholdPercentage field in financial information");
                }
            } else {
                logger.log("creditLimitThresholdPercentage field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("ALERTTHRESHOLDPERC")==null)) {
                if (financialAccountObject.get("calculatedAlertThreshold").equals(financialCreditAlertFromDb.get("ALERTTHRESHOLDPERC"))) {
                    logger.log("Validated the percentage over allowed field in financial information");
                }
            } else {
                logger.log("percentage over allowed field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("ALERTTHRESHOLDPERC")==null)) {
                if (financialAccountObject.get("calculatedAlertThreshold").equals(financialCreditAlertFromDb.get("ALERTTHRESHOLDPERC"))) {
                    logger.log("Validated the percentage over allowed field in financial information");
                }
            } else {
                logger.log("percentage over allowed field is not present ");
            }
            if (!(financialCreditAlertFromDb.get("DEBTORNUMBER")==null)) {
                if (financialAccountObject.get("debtorNumber").equals(financialCreditAlertFromDb.get("DEBTORNUMBER"))) {
                    logger.log("Validated the DEBTORNUMBER field in financial information");
                }
            } else {
                logger.log("DEBTORNUMBER field is not present ");
            }
        }
        if (!(financialCreditAlertFromDb.get("ORIGINALCREDITLIMIT").equals(0))) {
            if (applicationObject.get("originalCreditLimit").equals(financialCreditAlertFromDb.get("ORIGINALCREDITLIMIT"))) {
                logger.log("Validated the original credit limit field in financial information");
            }
        } else {
            logger.log("original credit limit field is not present");
        }

        if (financialResponse.containsKey("accountCreditInfo")){
            if (!(financialAccountFromDb.get("STATUS") == null)) {
                if (financialAccountCreditInfoObject.get("accountStatus").equals(financialAccountFromDb.get("STATUS"))) {
                    logger.log("Validated the account status field in financial information");
                }
            } else {
                logger.log("Account status field is not present ");
            }


        }

    }

    @When("^user get subscription to the selected customer\"([^\"]*)\"$")
    public void user_get_subscription_to_the_selected_customer(String customerNumber)throws ParseException{
        String getSubscriptionEndPoint = PropUtils.getPropValue(inputProp, "Get_Subscription");
        customerNumber = commonMethods.getCustomerNoForSubscription();
        requestBodyParams = Json.createObjectBuilder()
                .add("accountNumber",customerNumber)
                .build();
        logger.log("Request params for get subscription----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(getSubscriptionEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for get subscription"+response.getBody().prettyPrint());
//        inputProp.setProperty("Subscriptioncustomerno", customerNumber);
        JSONParser parser = new JSONParser();
//        org.json.JSONObject getSubsription = (org.json.JSONObject) parser.parse(response.asString());
        org.json.simple.JSONArray subsriptionOids = (org.json.simple.JSONArray) parser.parse(response.asString());
        System.out.println("getsubscription object:"+subsriptionOids.get(0));
        List<String> subscriptionOidList = new ArrayList<String>();
        for (int i = 0; i < subsriptionOids.size(); ++i) {
            org.json.simple.JSONObject  rec = (org.json.simple.JSONObject) subsriptionOids.get(i);
            System.out.println(rec.get("subscriptionOid"));
            String accountSubscription =  rec.get("subscriptionOid").toString();
            subscriptionOidList.add(accountSubscription);
//            int id = rec.getInt("subscriptionOid");
//          String loc = rec.getString("loc");
            // ...
            
        }

        List<Map<String, String>> expectedResult = common.getListOfSubscriptionOid(customerNumber);
        ArrayList expectedSubscriptionOidList = new ArrayList<>();
        boolean isPresent = false;
        Collections.sort(subscriptionOidList);
        Collections.sort(expectedSubscriptionOidList);
        for (int i = 0; i < expectedResult.size(); ++i) {
            String actualResult = subscriptionOidList.get(i);
            String expResult = expectedResult.get(i).get("SUBSCRIPTION_OID");

            isPresent = actualResult.equals(expResult);

        }

        if (isPresent) {
                System.out.println("Actual and Expected subscription list are match each other");
            } else {
                System.out.println("Actual and Expected subscription list are not match");
            }

    }
    @When("^user add subscription to the selected customer\"([^\"]*)\"$")
    public void user_add_subscription_to_the_selected_customer(String customerNumber) throws java.text.ParseException {
        String addSubscriptionEndPoint = PropUtils.getPropValue(inputProp, "Add_Subscription");
        customerNumber =commonMethods.getCustomerNoForCSRLogin();
        List<Map<String, String>> subscriptionOffer = common.getSubscriptionOffers();
        int size = getRandomNumberInBetween(0,subscriptionOffer.size());
        String subscriptionOfferName = subscriptionOffer.get(size).get("DESCRIPTION");
        List<Map<String, String>> products = common.getListOfProducts();
        int productsize = getRandomNumberInBetween(0,products.size());
        String subscribedProducts = products.get(productsize).get("DESCRIPTION");
        System.out.println("subscriptionOfferName :"+subscriptionOfferName);
        String effectiveDate = common.changingDateFormat("Current", "OLS");
        System.out.println("currentDate --> " + effectiveDate);
        String expiryDate = common.changingDateFormat("WayFuture", "OLS");
        System.out.println("currentDate --> " + expiryDate);
        requestBodyParams = Json.createObjectBuilder()
                .add("accountNumber", customerNumber)
                .add("subscriptionOffer",subscriptionOfferName)
                .add("electricityProductRole","Business to Consumer")
                .add("fee","Subscription Fee")
                .add("subscriptionNumber",faker.number().digits(4))
                .add("sEffectiveDate",effectiveDate)
                .add("sExpiryDate",expiryDate)
                .add("subscriptionTariff","Pure Minute SMATRICS ")
                .add("optInFlag","Y")
                .add("subscribedProducts", Json.createArrayBuilder().add(0, subscribedProducts))
                .build();
        logger.log("Request params for add subscription----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(addSubscriptionEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for add subscription" + response.getBody().prettyPrint());

    }

    @When("^user edit subscription to the selected customer\"([^\"]*)\"$")
    public void user_edit_subscription_to_the_selected_customer(String customerNumber) throws ParseException, java.text.ParseException {
        String editSubscriptionEndPoint = PropUtils.getPropValue(inputProp, "Edit_Subscription");
        String effectiveDate = common.changingDateFormat("Current", "OLS");
        System.out.println("currentDate --> " + effectiveDate);
        String expiryDate = common.changingDateFormat("WayFuture", "OLS");
        System.out.println("currentDate --> " + expiryDate);
        boolean presenceCheck = false;
        String subscriptionNumber = faker.number().digits(4);
        String getSubscriptionEndPoint = PropUtils.getPropValue(inputProp, "Get_Subscription");
        customerNumber = commonMethods.getCustomerNoForSubscription();
        requestBodyParams = Json.createObjectBuilder()
                .add("accountNumber", customerNumber)
                .build();
        logger.log("Request params for get subscription----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(getSubscriptionEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("get subscriptions repsonse"+response.asString());
        JSONParser parser = new JSONParser();
//        System.out.println("response arrayonject :"+response.getBody());
//        org.json.simple.JSONObject getSubscriptions = (org.json.simple.JSONObject) parser.parse(response.asString());
//        System.out.println("getreport object:"+getSubscriptions.get(0));
        org.json.simple.JSONArray subscriptionsObject = (org.json.simple.JSONArray) parser.parse(response.asString());
        System.out.println("Reposnse of reportObject"+subscriptionsObject.get(0));
        org.json.simple.JSONObject getSubscription = (org.json.simple.JSONObject)subscriptionsObject.get(0);
        System.out.println("Get Subscription :"+getSubscription);
//        getSubscription.replace("sEffectiveDate",effectiveDate);
//        getSubscription.replace("sExpiryDate",expiryDate);
        if (getSubscription.containsKey("effectiveDate")) {
            getSubscription.remove("effectiveDate");
        }
        if (getSubscription.containsKey("expiryDate")) {
            getSubscription.remove("expiryDate");
        }
        if (getSubscription.containsKey("subscriptionNumber")) {
            getSubscription.remove("subscriptionNumber");
        }


//        getSubscription.replace   ("subscriptionNumber",faker.number().digits(4));

        inputProp.setProperty("subscriptionoid", String.valueOf(getSubscription.get("subscriptionOid")));
        JsonObject editSubscriptionObject;
        editSubscriptionObject = Json.createObjectBuilder()
                .add("accountNumber",customerNumber)
                .add("sEffectiveDate",effectiveDate)
                .add("sExpiryDate",expiryDate)
                .add("subscriptionNumber",subscriptionNumber)
                .addAll(Json.createObjectBuilder(getSubscription))
                .build();
//        inputProp.setProperty("subscriptionNumber", String.valueOf(getSubscription.get("subscriptionNumber")));

        response = putRequestAsBearerAuthWithBodyDataWithQueryParam(editSubscriptionEndPoint, editSubscriptionObject.toString(), authorizationToken, false);
        System.out.println("get updated subscription repsonse : "+response.asString());
//        String subscriptionNumber = PropUtils.getPropValue(inputProp, "subscriptionNumber");
//        System.out.println("subscriptionNumber :"+subscriptionNumber);
        String subscriptionoid = PropUtils.getPropValue(inputProp, "subscriptionoid");
        System.out.println("subscriptionoid :"+subscriptionoid);
        String DBeffectiveDate = common.changingDateFormat("Current", "DBFormatWithouTime").toUpperCase();
        String DBexpiryDate = common.changingDateFormat("WayFuture", "DBFormatWithouTime").toUpperCase();
        presenceCheck = common.checkingThePresenceOfSubscriptionupdatedValueDB(DBeffectiveDate,DBexpiryDate,subscriptionNumber,subscriptionoid);
        if(presenceCheck ){
            logger.log("DB Validation passed-Updated Subscription values found in DB");
        }
        else{
            softFail("DB Validation failed-Updated Subscription values not found in DB",logger);
        }


    }

}















