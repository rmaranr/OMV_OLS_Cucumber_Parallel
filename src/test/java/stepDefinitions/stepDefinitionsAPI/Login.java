package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.testng.Assert;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;
import utilities.ui.PasswordGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends RequestMethodsUtils {


    public io.cucumber.java.Scenario logger;
    // JSONObject requestParams = new JSONObject();
    private String user, olsPassword;
    //Scenario loggerInfo = null;

    String responseBody;
    String password_created_at;
    String tokenFromDB;
    HashMap<String, String> errorResponse = new HashMap<String, String>();
    HashMap<String, String> userStatusErrorResponse = new HashMap<String, String>();
    HashMap<String, String> invalidTokenErrorResponse = new HashMap<String, String>();
    CommonDBUtils common = new CommonDBUtils();
    ArrayList<String> invalidLoginResponseStatusNumber = new ArrayList<String>();
    ArrayList<String> invalidLoginResponseStatusMessage = new ArrayList<String>();
    List<Map<String, String>> listOfCustomerNumbersBasedOnClient = null;
    Map<String, String> specificClientName = null;
    public static ArrayList<String> listOfPasswords = new ArrayList<String>();
    String countryCode,clientName;


    public Login(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @Given("^the user wants to be set grant type  to use the API$")
    public void the_user_wants_to_be_set_grant_type_to_use_the_API() throws Throwable {
        RestAssured.baseURI = PropUtils.getPropValue(inputProp, "baseURL");
        requestParams.put("grant_type", PropUtils.getPropValue(inputProp, "GRANT_TYPE"));
    }

    @Given("^user wants to be set base url to access the API$")
    public void user_wants_to_be_set_base_url_to_access_the_API() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        RestAssured.baseURI = PropUtils.getPropValue(inputProp, "baseURL");
        System.out.println("RestAssured.baseURI  " + RestAssured.baseURI);
        logger.log("Base Url ---->  " + RestAssured.baseURI);
    }


    @When("^user enters the  Username \"(.*?)\" and Password \"(.*?)\"$")
    public void user_enters_the_Username_and_Password(String userName, String password) throws Throwable {
        user = getOLSUerName();
        olsPassword = getOLSPassword();
        requestParams.put("grant_type", PropUtils.getPropValue(inputProp, "GRANT_TYPE"));
        if (userName.equals("prop.invalid")) {

            requestParams.put("username", PropUtils.getPropValue(inputProp, "Invalid_UserName"));
            logger.log("Passing invalid username is " + PropUtils.getPropValue(inputProp, "Invalid_UserName"));
        } else {
            requestParams.put("username", user);
            logger.log("Passing valid username " + user);
        }

        if (password.equals("prop.invalid")) {

            requestParams.put("password", PropUtils.getPropValue(inputProp, "Invalid_Password"));
            logger.log("Passing invalid password is " + PropUtils.getPropValue(inputProp, "Invalid_Password"));
        } else {

            requestParams.put("password", olsPassword);
            logger.log("Passing valid password " + olsPassword);
        }
    }

    @When("^user enters the client ID \"(.*?)\" ,client Secret \"(.*?)\" and \"(.*?)\"$")
    public void user_enters_the_client_ID_client_Secret_and(String clientId, String clientSecret, String endPoint)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String accessKey;
        if (clientId.equals("") && clientSecret.equals("") || clientId.equals(null) && clientSecret.equals(null)) {
            accessKey = base64Encoder(clientId, clientSecret);

        } else {
            accessKey = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                    PropUtils.getPropValue(inputProp, "SECERT_ID"));
        }

        // Post request for login
        response = postRequestAsBasicAuthWithBodyData(endPoint, requestParams, accessKey);
        logger.log("Request Params is ---->" + requestParams);
        logger.log("Response Body is -->" + response.getBody().prettyPrint());

    }

    @Then("^the error message response status code should be \"(.*?)\"$")
    public void the_error_message_response_status_code_should_be(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" + responseStatus);
        try {
            Assert.assertEquals(responseStatus, statusCode);
            logger.log("Expected error response status code is " + responseStatus);
        } catch (AssertionError e) {
            logger.log("Expected error response status code is " + responseStatus+" is not came");
        }
    }

    @Then("^error message response should be \"([^\"]*)\" and \"([^\"]*)\"$")
    public void error_message_response_should_be_and(String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        errorResponse = response.jsonPath().get("errors");
        String errorStatusNumber = errorResponse.get("statusNumber");

        validateResponseMessage(errorStatusNumber, statusNumber, logger, "Expected status number from response is " + errorStatusNumber);
        //  logger.log("Expected status number from response is " + errorStatusNumber);
        logger.log("Validated the actual and expected response");
        String errorMessage = errorResponse.get("statusMessage");
        validateResponseMessage(errorMessage, statusMessage, logger, "Expected status message from response is " + errorMessage);
//        logger.log("Expected status message from response is " + errorMessage);
        logger.log("Validated the actual and expected response");
    }


    @When("^verify the logon status for the user is \"([^\"]*)\"$")
    public void verify_the_logon_status_for_the_user_is(String expectedLogonStatus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String olsUser = getOLSUerName();
        String actLogonStatusCID = common.getLogonStatusCIDFromDB(olsUser);
        System.out.println("Actual logon status from DB is " + actLogonStatusCID);
        if (actLogonStatusCID.equals(expectedLogonStatus)) {
            logger.log("Actual user logon status is " + actLogonStatusCID);
            logger.log("Expected user logon status is " + expectedLogonStatus);

        } else {
            logger.log("Actual user log on status is " + actLogonStatusCID);
            common.updateLogonStatusCID(olsUser, 2501);
        }


    }

    // Positive scenarios
    @When("^the user posts the request for an authorization token \"(.*?)\"$")
    public void the_user_posts_the_request_for_an_authorization_token(String endPoint) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        user = getOLSUerName();
        requestParams.put("grant_type", PropUtils.getPropValue(inputProp, "GRANT_TYPE"));
        requestParams.put("username", user);
        olsPassword = getOLSPassword();
        Boolean passwordCondition = verifyPassword(olsPassword);
        System.out.println("Password condition is " + passwordCondition);

        if (passwordCondition) {
            requestParams.put("password", olsPassword);
        } else {
            System.out.println(
                    "Password has atleast one upper case,one lower case, one integer and one special character");
        }

        String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                PropUtils.getPropValue(inputProp, "SECERT_ID"));

        // Post a request for login API call
        response = postRequestAsBasicAuthWithBodyData(endPoint, requestParams, accessToken);
        jsonPathEvaluator = response.jsonPath();
        logger.log("Request params-->" + requestParams.toString());
        logger.log(" Response Body -->" + response.getBody().prettyPrint());
        logger.log("token is " + response.jsonPath().get("token"));
        System.out.println("INSIDE THE AUTH TOKEN " + response.jsonPath().get("token"));

    }

    @Then("^the bearer token response status code should be (\\d+)$")
    public void the_bearer_token_response_status_code_should_be(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" + responseStatus);

        try {
            Assert.assertEquals(responseStatus, statusCode);
            logger.log("Response status code is " + responseStatus);
        } catch (AssertionError e) {
            logger.log("Response status code is " + responseStatus+" is not came");
        }

    }

    @Then("^the API should return a valid authentication token$")
    public void the_API_should_return_a_valid_authentication_token() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        authorizationToken = jsonPathEvaluator.get("token");
        System.out.println("Authorization token " + authorizationToken);

        PropUtils.setProps("AuthorizationToken", authorizationToken, inputDataFilePath);

        refreshPropertiesFile();
        countryCode = jsonPathEvaluator.get("country_code");
        listOfCustomerNumbersBasedOnClient = common.getClientCountryCodeHavingCustomers(countryCode);
        System.out.println("List of customer numbers having client from db" + listOfCustomerNumbersBasedOnClient);
        specificClientName = listOfCustomerNumbersBasedOnClient.get(0);
        clientName = specificClientName.get("NAME");
        if(listOfCustomerNumbersBasedOnClient.isEmpty()){
            logger.log("Client has no customer numbers");
        }
        else {
            PropUtils.setProps("OMV_AT", clientName, inputDataFilePath);
        }
        refreshPropertiesFile();
        System.out.println("After Set Token in Prop--> " + PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("After Set client name in Prop-->" + PropUtils.getPropValue(inputProp, "OMV_AT"));
        logger.log(
                "Authorization token is updated in " + System.getProperty("environment") + "  Prop--> " + PropUtils.getPropValue(inputProp, "AuthorizationToken"));

    }

    @Then("^token type should be BearerType$")
    public void token_type_should_be_BearerType() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String tokenType = response.jsonPath().get("token_type");
        System.out.println("Token type is " + tokenType);
        try {
            Assert.assertEquals(tokenType, "BearerToken");
            logger.log("Token type is " + tokenType);
        } catch (AssertionError e) {
            logger.log("Token type is " + tokenType+" is not came");
        }
    }

    @Then("^validated the LOGON_STATUS_CID in DB should be \"([^\"]*)\"$")
    public void validated_the_LOGON_STATUS_CID_in_DB_should_be(String explogonStatusCID) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String olsUser = getOLSUerName();
        String actLogonStatusCID = common.getLogonStatusCIDFromDB(olsUser);

        try {
            Assert.assertEquals(actLogonStatusCID, explogonStatusCID);
            logger.log("LogonStatusCID is " + actLogonStatusCID);
        } catch (AssertionError e) {
            logger.log("LogonStatusCID is " + actLogonStatusCID+" is not came");
        }
    }

    @When("^user enters the Username \"([^\"]*)\" and Expiredpassword \"([^\"]*)\"$")
    public void user_enters_the_Username_and_Expiredpassword(String userName, String expiredPassword) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String olsUser = getOLSUerName();
        String olsPassword = getOLSPassword();
        requestParams.put("grant_type", PropUtils.getPropValue(inputProp, "GRANT_TYPE"));

        requestParams.put("username", olsUser);
        if (expiredPassword.equals("validExpiredPassword")) {
            common.initializeDBEnvironment();

            password_created_at = common.getCreatedPasswordAtDateFromDB(olsUser);
            System.out.println("Current Password for user is " + password_created_at);

            String currentPaswordToExpiredPassword = common.enterADateValueInStatusBeginDateField("Past",
                    password_created_at, "dbFormat");
            System.out.println("Expired Password " + currentPaswordToExpiredPassword);
            common.updateCreatedPasswordAtDateThroughDB(currentPaswordToExpiredPassword, olsUser);

            requestParams.put("password", olsPassword);

        } else if (expiredPassword.equals("invalidPassword")) {
            requestParams.put("password", PropUtils.getPropValue(inputProp, "Invalid_Password"));
        }


    }


    @Then("^user will update the valid password if it is expired \"([^\"]*)\"$")
    public void user_will_update_the_valid_password_if_it_is_expired(String expiredPassword) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String olsUser = getOLSUerName();
        if (expiredPassword.equals("validExpiredPassword")) {
            common.initializeDBEnvironment();
            common.setCurrentSystemDateForExpiredPassword(password_created_at, olsUser);
            logger.log("Updated the current password created at date for user");
            System.out.println("Updated the current Password date");

        } else if (expiredPassword.equals("invalidPassword")) {
            logger.log("For invalid password no need to change the password");
        }
    }


    @When("^user able to enters the credentials based on max_password_attempts and endpoint \"([^\"]*)\"$")
    public void user_able_to_enters_the_credentials_based_on_max_password_attempts_and_endpoint(String endPoint)
            throws Throwable {
        Map<String, String> userDetails = new HashMap<String, String>();
        int max_Password_attempt;

        String logonId = getOLSUerName();
        userDetails = common.getUserDetailsFromDB(logonId);
        int failed_Logon_Count;

        failed_Logon_Count = Integer.parseInt(userDetails.get("FAILED_LOGON_COUNT"));
        max_Password_attempt = Integer.parseInt(common.getPasswordLimitFromAccessRoleTable());
        System.out.println("Max password attempt is" + max_Password_attempt);
        logger.log("Max PAssword Attempt is " + max_Password_attempt);

        for (int i = 1; i <= max_Password_attempt + 1; i++) {
            requestParams.put("grant_type", "password");
            requestParams.put("username", logonId);
            logger.log("Passing valid username is " + logonId);
            requestParams.put("password", PropUtils.getPropValue(inputProp, "Invalid_Password"));
            logger.log("Passing invalid password is " + PropUtils.getPropValue(inputProp, "Invalid_Password"));

            String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                    PropUtils.getPropValue(inputProp, "SECERT_ID"));

            // Post a request for login API call
            response = postRequestAsBasicAuthWithBodyData(endPoint, requestParams, accessToken);
            jsonPathEvaluator = response.jsonPath();
            logger.log("Request params-->" + requestParams.toString());
            logger.log(" Response Body -->" + response.getBody().prettyPrint());
            int responseStatus = response.getStatusCode();
            System.out.println("Response code is ::" + responseStatus);
            logger.log("Response code is " + responseStatus);
            errorResponse = response.jsonPath().get("errors");
            String errorStatusNumber = errorResponse.get("statusNumber");
            System.out.println("Status number from response " + errorStatusNumber);
            invalidLoginResponseStatusNumber.add(errorStatusNumber);
            String errorMessage = errorResponse.get("statusMessage");
            System.out.println("Status message from response " + errorMessage);
            invalidLoginResponseStatusMessage.add(errorMessage);
            failed_Logon_Count++;
        }
        if (failed_Logon_Count == max_Password_attempt + 1) {
            System.out.println("User has reached max password attempts and verified the Failed log on count from DB is " + failed_Logon_Count);
            common.updateLogonStatusCID(logonId, 2501);
        }
    }

    @Then("^validates the error response message based on max password attempts as beforLastAttempt \"([^\"]*)\" and maxPasswordAttempt \"([^\"]*)\"$")
    public void validates_the_error_response_message_based_on_max_password_attempts_as_beforLastAttempt_and_maxPasswordAttempt(String beforePasswordAttemptResponsestatus, String lastAttempResponseStatus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String userPasswordAttemptResponse[] = beforePasswordAttemptResponsestatus.split(":");
        String userLastAttemptResponseMessage[] = lastAttempResponseStatus.split(":");

        for (int i = 0; i < invalidLoginResponseStatusNumber.size(); i++) {
            if (!(invalidLoginResponseStatusNumber.get(i).equals(userPasswordAttemptResponse[0])) && !(invalidLoginResponseStatusNumber.get(i).equals(userLastAttemptResponseMessage[0]))) {
                System.out.println("First Attempt " + invalidLoginResponseStatusNumber.get(i));
                validateResponseMessage(invalidLoginResponseStatusNumber.get(i), PropUtils.getPropValue(inputProp, "status_Number"), logger, "Expected status Number from response " + invalidLoginResponseStatusNumber.get(i));
                validateResponseMessage(invalidLoginResponseStatusMessage.get(i), PropUtils.getPropValue(inputProp, "status_message"), logger, "Expected status message from response " + invalidLoginResponseStatusMessage.get(i));

            } else if (invalidLoginResponseStatusNumber.get(i).equals(userPasswordAttemptResponse[0])) {
                validateResponseMessage(invalidLoginResponseStatusNumber.get(i), userPasswordAttemptResponse[0], logger, "Expected status Number from response " + invalidLoginResponseStatusNumber.get(i));
                validateResponseMessage(invalidLoginResponseStatusMessage.get(i), userPasswordAttemptResponse[1], logger, "Expected status message from response " + invalidLoginResponseStatusMessage.get(i));

            } else if (invalidLoginResponseStatusNumber.get(i).equals(userLastAttemptResponseMessage[0])) {
                validateResponseMessage(invalidLoginResponseStatusNumber.get(i), userLastAttemptResponseMessage[0], logger, "Expected status Number from response " + invalidLoginResponseStatusNumber.get(i));
                validateResponseMessage(invalidLoginResponseStatusMessage.get(i), userLastAttemptResponseMessage[1], logger, "Expected status message from response " + invalidLoginResponseStatusMessage.get(i));
            }
        }
    }
    //Reset password mail
    @When("^user able to generate reset password mail based on user logon status \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_able_to_generate_reset_password_mail_based_on_user_logon_status_and(String userLogonStatus,
                                                                                         String loginEndPoint) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String logonId = getOLSUerNameForResetAndChangePassword();
        String resetPasswordAPIURL;
        String resetPasswordEndpoint = PropUtils.getPropValue(inputProp, "resetPasswordAPIEndpoint");
        String resetPasswordURL = PropUtils.getPropValue(inputProp, "resetPasswordParamURL");
        System.out.println("Get gen-reset-password-link endpoint from properties " + resetPasswordEndpoint);


        if (userLogonStatus.equals("Active")) {
            common.updateLogonStatusCID(logonId, 2505);
        } else if (userLogonStatus.equals("Inactive")) {
            common.updateLogonStatusCID(logonId, 2506);
            System.out.println("Inactive user");

        } else if (userLogonStatus.equals("LoggedOn")) {
            common.updateLogonStatusCID(logonId, 2501);

        } else if (userLogonStatus.equals("Loggedoff")) {
            common.updateLogonStatusCID(logonId, 2502);

        } else if (userLogonStatus.equals("TemporaryLock")) {
            common.updateLogonStatusCID(logonId, 2503);

        } else if (userLogonStatus.equals("PermenantLock")) {
            common.updateLogonStatusCID(logonId, 2504);

        }
        resetPasswordAPIURL = loginEndPoint + resetPasswordEndpoint;
        String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                PropUtils.getPropValue(inputProp, "SECERT_ID"));
        response = getRequestAsBasicAuthWithQueryParams(resetPasswordAPIURL, accessToken, logonId, resetPasswordURL);
        logger.log("Passing query params as logon id for reset password mail " + logonId);
        logger.log("Passing query params for reset password mail " + resetPasswordURL);

    }

    @Then("^validate the response status code for the user \"([^\"]*)\"$")
    public void validate_the_response_status_code_for_the_user(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" + responseStatus);
        try {
            Assert.assertEquals(responseStatus, statusCode);
            logger.log("Expected response status code is " + responseStatus);
        } catch (AssertionError e) {
            logger.log("Expected response status code is " + responseStatus+" is not came");
        }
    }


    @Then("^validate the error message response statusnumber \"([^\"]*)\" and statusmessage \"([^\"]*)\"$")
    public void validate_the_error_message_response_statusnumber_and_statusmessage(String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        HashMap<String, Integer> userStatusNumberFromResponse = new HashMap<String, Integer>();

        if (statusNumber.equals("") && statusMessage.equals("") || (statusNumber.equals(null) && statusMessage.equals(null))) {
            logger.log("API does not return response value for active, logged on and logged off status");
        } else {

            userStatusErrorResponse = response.jsonPath().get("errors");
            userStatusNumberFromResponse = response.jsonPath().get("errors");

            System.out.println("Error Response " + userStatusErrorResponse);
            String statusNumberFromResponse = userStatusNumberFromResponse.get("statusNumber").toString();
            System.out.println("Status number from response " + statusNumberFromResponse);
            validateResponseMessage(statusNumberFromResponse, statusNumber, logger, "Expected status Number from response " + statusNumberFromResponse);
//            logger.log("Expected status Number from response " + statusNumberFromResponse);
            String statusMessageFromResponse = userStatusErrorResponse.get("statusMessage");
//            System.out.println("Status Message from response " + statusMessageFromResponse);
            validateResponseMessage(statusMessageFromResponse, statusMessage, logger, "Expected status message from response " + statusMessageFromResponse);
//            logger.log("Expected status message from response " + statusMessageFromResponse);
        }
    }

    @When("^user able to validate the token link is active or expired using login  \"([^\"]*)\" endpoint and  \"([^\"]*)\"$")
    public void user_able_to_validate_the_token_link_is_active_or_expired_using_login_endpoint_and(String loginEndPoint, String tokenType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> resetPasswordTokenDetails = new HashMap<String, String>();
        Faker faker = new Faker();
        String invalidToken;
        String logonId = getOLSUerNameForResetAndChangePassword();
        String tokenEndPoint = PropUtils.getPropValue(inputProp, "GET_VALIDATETOKEN");
        String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                PropUtils.getPropValue(inputProp, "SECERT_ID"));

        String tokenValidationLink = loginEndPoint + tokenEndPoint;
        if (tokenType.equals("validToken")) {
            resetPasswordTokenDetails = common.getTokenDetailsFromDB(logonId);
            tokenFromDB = resetPasswordTokenDetails.get("TOKEN");
            System.out.println("Reset Password token db is " + tokenFromDB);
            response = getRequestAsBasicAuthWithQueryParamsForTokenValidation(tokenValidationLink, accessToken, tokenFromDB);
            logger.log("After recieved the reset password mail and we have recieved the token from db " + tokenFromDB);

        } else {
            invalidToken = faker.number().digits(10);
            response = getRequestAsBasicAuthWithQueryParamsForTokenValidation(tokenValidationLink, accessToken, invalidToken);
            System.out.println("Invalid token error response " + response.jsonPath().prettyPrint());
            logger.log("Invalid token error response " + response.jsonPath().prettyPrint());
        }
    }

    @Then("^validate the error response statusNumber \"([^\"]*)\" and statusMessage \"([^\"]*)\"$")
    public void validate_the_error_response_statusNumber_and_statusMessage(String tokenResponseStatusNumber, String tokenResponseStatusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String statusNumber, statusMessage;
        HashMap<String, Integer> invalidTokenStatusNumberFromResponse = new HashMap<String, Integer>();
        invalidTokenErrorResponse = response.jsonPath().get("errors");
        System.out.println("Invalid error resposne " + invalidTokenErrorResponse);

        invalidTokenStatusNumberFromResponse = response.jsonPath().get("errors");
        statusNumber = invalidTokenStatusNumberFromResponse.get("statusNumber").toString();
        System.out.println("Invalid token status number from response " + statusNumber);
        validateResponseMessage(statusNumber, tokenResponseStatusNumber, logger, "Expected status number from response " + statusNumber);
//        logger.log("Expected status number from response " + statusNumber);
        statusMessage = invalidTokenErrorResponse.get("statusMessage");
        validateResponseMessage(statusMessage, tokenResponseStatusMessage, logger, "Expected status message from response " + statusMessage);
//        logger.log("Expected status message from response " + statusMessage);
    }


    @Then("^validate the response status code should be (\\d+)$")
    public void validate_the_response_status_code_should_be(int statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" + responseStatus);

        try {
            Assert.assertEquals(responseStatus, statusCode);
            logger.log("Resposne status code is " + responseStatus);
        } catch (AssertionError e) {
            logger.log("Resposne status code is " + responseStatus+ " is not came");
        }

    }

    @Then("^user able to generate reset password using login end point \"([^\"]*)\"$")
    public void user_able_to_generate_reset_password_using_login_end_point(String loginEndPoint) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {

            String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                    PropUtils.getPropValue(inputProp, "SECERT_ID"));

            requestParams.put("token", tokenFromDB);

            common.updateCurrentPasswordToNull(
                    PropUtils.getPropValue(inputProp, "userNameForChangeAndRestPasswordAPI"));

            String randomNewPassword = PasswordGenerator.generateRandomPassword(logger, 12);

            PropUtils.setProps("resetAndChangePasswordAPI", randomNewPassword, inputDataFilePath);
            System.out.println("Updated password in apacdev1 property " + randomNewPassword);

            requestParams.put("newPassword", randomNewPassword);
            requestParams.put("confirmPassword", randomNewPassword);

            String resetPasswordAPIEndPoint = PropUtils.getPropValue(inputProp, "reset-passwordAPIEndPoint");

            resetPasswordAPIEndPoint = loginEndPoint + resetPasswordAPIEndPoint;

            System.out.println("reset-password endPoint " + resetPasswordAPIEndPoint);
            // Post a request for login API call
            response = postRequestAsBasicAuthWithBodyData(resetPasswordAPIEndPoint, requestParams, accessToken);
            jsonPathEvaluator = response.jsonPath();
            logger.log("Request params-->" + requestParams.toString());
            // logger.log(" Response Body -->" + response.getBody().prettyPrint());
        } catch (Exception e) {

        }

    }

    @Then("^verify the generated token used \"([^\"]*)\" column value in database$")
    public void verify_the_generated_token_used_column_value_in_database(String usedColumn) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> tokenFromPasswordlink = new HashMap<String, String>();
        tokenFromPasswordlink = common.getTokenUsedColumnFromDB(tokenFromDB);
        String usedColumnFromPasswordLink = tokenFromPasswordlink.get("USED");
        try {
            Assert.assertEquals(usedColumnFromPasswordLink, usedColumn);
            logger.log("UsedColumnFromDB  is " + usedColumnFromPasswordLink);
        } catch (AssertionError e) {

        }


    }


    @When("^user able to change the password for logged on user with password type \"([^\"]*)\" and login endpoint \"([^\"]*)\"$")
    public void user_able_to_change_the_password_for_logged_on_user_with_password_type_and_login_endpoint(
            String passwordType, String loginEndPoint) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        String userName = getOLSUerNameForResetAndChangePassword();

        String changePasswordEndPoint = PropUtils.getPropValue(inputProp, "change-PasswordAPIEndPoint");
        String changePasswordAPIURL = loginEndPoint + changePasswordEndPoint;
        System.out.println("Change password API end point " + changePasswordAPIURL);

        common.updateCurrentPasswordToNull(userName);


        requestParams.put("username", userName);
        requestParams.put("password", "null");
        String newPassword = PasswordGenerator.generateRandomPassword(logger, 12);
        System.out.println("New password " + newPassword);

        requestParams.put("newPassword", newPassword);

        if (passwordType.equals("ValidConfirmPassword")) {
            //  PropUtils.setProps("resetAndChangePasswordAPI", updatedPassword, inputDataFilePath);


            requestParams.put("confirmPassword", newPassword);
            logger.log("Request params-->" + requestParams.toString());
            System.out.println("Request params " + requestParams.toString());
            // Post a request for login API call
            response = postRequestAsBearerAuthWithBodyData(changePasswordAPIURL, requestParams.toString(),
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            PropUtils.setProps("passwordForResetAndChangePasswordAPI", newPassword, inputDataFilePath);
            refreshPropertiesFile();

        } else {

            requestParams.put("confirmPassword", PropUtils.getPropValue(inputProp, "Invalid_Password"));
            logger.log("Request params-->" + requestParams.toString());


            // Post a request for login API call
            response = postRequestAsBearerAuthWithBodyData(changePasswordAPIURL, requestParams.toString(),
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            jsonPathEvaluator = response.jsonPath();

            logger.log(" Response Body -->" + response.getBody().prettyPrint());
        }
    }

    @Then("^validate the response status code should be \"([^\"]*)\"$")
    public void validate_the_response_status_code_should_be(String statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" + responseStatus);

        try {
            Assert.assertEquals(Integer.toString(responseStatus), statusCode);
            logger.log("Actual response status code is " + responseStatus);
            logger.log("Expected response status code is " + statusCode);
        } catch (AssertionError e) {

        }

    }


    @Then("^validate the statusNumber \"([^\"]*)\" and statusMessage \"([^\"]*)\" from response$")
    public void validate_the_statusNumber_and_statusMessage_from_response(String statusNumber, String statusMessage) throws Throwable {
        HashMap<String, Integer> invalidPasswordStatusNumber = new HashMap<String, Integer>();
        // Write code here that turns the phrase above into concrete actions
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            logger.log("API does not return any response value for success scenario");
        } else {
            String errorStatusMessage = response.path("errors.statusMessage");
            System.out.println("Status message from response " + errorStatusMessage);
            validateResponseMessage(statusMessage, errorStatusMessage, logger, "Validated the status message from response " + errorStatusMessage);
//            logger.log("Validated the status message from response " + errorStatusMessage);

            invalidPasswordStatusNumber = response.jsonPath().get("errors");

            String errorStatusNumber = invalidPasswordStatusNumber.get("statusNumber").toString();
            System.out.println("Status number from response is " + errorStatusNumber);
            validateResponseMessage(statusNumber, errorStatusNumber, logger, "Validated the status number from response " + errorStatusNumber);
//            logger.log("Validated the status number from response " + errorStatusNumber);
        }
    }


    @When("^user should able to change the password based on logged on user role access$")
    public void user_should_able_to_change_the_password_based_on_logged_on_user_role_access() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> userDetails = new HashMap<String, String>();

        String userNameForChangePassword = getOLSUerNameForResetAndChangePassword();
        common.updateCurrentPasswordToNull(userNameForChangePassword);
        String changePasswordEndPoint = PropUtils.getPropValue(inputProp, "change-PasswordAPIEndPoint");
        String changePasswordAPIURL = "/login" + changePasswordEndPoint;
        System.out.println("Change password API end point " + changePasswordAPIURL);

        userDetails = common.getUserDetailsFromDB(userNameForChangePassword);
        String accessGroupOid = userDetails.get("ACCESS_GROUP_OID");
        System.out.println("Access group oid for user " + accessGroupOid);
        String userPasswordLimit = common.getPasswordLimitFromAccessRoleTable();

        logger.log("Unique password constraints for user is " + userPasswordLimit);

        requestParams.put("username", userNameForChangePassword);

        for (int i = 0; i <= Integer.parseInt(userPasswordLimit) + 1; i++) {
            System.out.println("Inisde of  for loop");
            String newPassword = PasswordGenerator.generateRandomPassword(logger, 12);
            System.out.println("New password " + newPassword);
            listOfPasswords.add(newPassword);
            System.out.println("List of new password " + listOfPasswords);
            requestParams.put("password", "null");
            requestParams.put("newPassword", newPassword);
            requestParams.put("confirmPassword", newPassword);
            response = postRequestAsBearerAuthWithBodyData(changePasswordAPIURL, requestParams.toString(),
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));

            if (response.getStatusCode() == 200) {
                System.out.println("Reponse code is " + response.getStatusCode());
                System.out.println("User has been changed password");
            } else {
                System.out.println("Inside of else if statement");
            }

            common.updateCurrentPasswordToNull(userNameForChangePassword);

        }

    }


    @Then("^validate the expected response for user new password is used any of the previous passwords based on \"([^\"]*)\"$")
    public void validate_the_expected_response_for_user_new_password_is_used_any_of_the_previous_passwords_based_on(
            String passwordCount) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String userNameForChangePassword = getOLSUerNameForResetAndChangePassword();
        String changePasswordEndPoint = PropUtils.getPropValue(inputProp, "change-PasswordAPIEndPoint");
        String changePasswordAPIURL = "/login" + changePasswordEndPoint;
        System.out.println("List of password size is " + listOfPasswords.size());
        if (passwordCount.equals("") || passwordCount.equals(null)) {
            logger.log("User has been generated list of passwords based on user unique password constraints ");
        } else if (passwordCount.equals("userLastPassword")) {
            requestParams.put("username", userNameForChangePassword);
            requestParams.put("password", "null");
            requestParams.put("newPassword", listOfPasswords.get(listOfPasswords.size() - 1));
            requestParams.put("confirmPassword", listOfPasswords.get(listOfPasswords.size() - 1));
            logger.log("Request params " + requestParams);
            response = postRequestAsBearerAuthWithBodyData(changePasswordAPIURL, requestParams.toString(),
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        } else if (passwordCount.equals("userFirstPassword")) {
            requestParams.put("username", userNameForChangePassword);
            requestParams.put("password", "null");
            requestParams.put("newPassword", listOfPasswords.get(0));
            requestParams.put("confirmPassword", listOfPasswords.get(0));
            logger.log("Request params " + requestParams);
            response = postRequestAsBearerAuthWithBodyData(changePasswordAPIURL, requestParams.toString(),
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        }


        if (response.getStatusCode() == 200) {
            common.updateCurrentPasswordToNull(userNameForChangePassword);
            logger.log("User has been changed the password");
        } else {
            System.out.println("Inside of else if statement");
            System.out.println("Reponse body:"+response.getBody().prettyPrint());
//            int statusNumberFromResponse = response.path("errors.statusNumber");
//            System.out.println("Status Number from response is " + statusNumberFromResponse);
//
//            validateStatusNumber(statusNumberFromResponse, 30021, logger,
//                    "Expected status number from response is " + statusNumberFromResponse);
//
//            String statusMessageFromResponse = response.path("errors.statusMessage");
//            System.out.println("Status Message from response is " + statusMessageFromResponse);
//
//            validateResponseMessage(statusMessageFromResponse,
//                    "Password cannot be any of the last 10 passwords previously used", logger,
//                    "Expected status message from response is " + statusMessageFromResponse);
        }

    }

}



   
