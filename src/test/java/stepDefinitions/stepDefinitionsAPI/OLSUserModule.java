package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;
import utilities.api.UserMethodsAPI;
import utilities.ui.PasswordGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OLSUserModule extends RequestMethodsUtils {

    public io.cucumber.java.Scenario logger;
    JSONArray array = new JSONArray();
    List<String> keyList = new ArrayList<String>();
    List<String> valueList = new ArrayList<String>();
    CommonDBUtils common = new CommonDBUtils();
    UserMethodsAPI userMethods = new UserMethodsAPI();
    Faker faker = new Faker();
    String responseBody, existingUserName, newUserId;
    Map<String, String> newUserDetails = new HashMap<String, String>();
    int listOfRoleindex = 0;

    public OLSUserModule(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @When("^user wants to pass the params as \"([^\"]*)\" by value \"([^\"]*)\"$")
    public void user_wants_to_pass_the_params_as_by_value(String params, String value) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Inside of when block");
        System.out.println("Params " + params);
        System.out.println("Value " + value);
        String userEndPoint = PropUtils.getPropValue(inputProp, "userEndPointAPI");
        logger.log("API service for new user creation " + RestAssured.baseURI + userEndPoint);

        if (value.contains("UserId")) {
            newUserId = userMethods.getValidAndInvalidUserId(value);
            System.out.println("Generated new user id is " + newUserId);
            logger.log("New user logon id is " + newUserId);
            requestParams.put(params, newUserId);
        } else if (value.contains("EmailAddress")) {
            String userEmailAddress = userMethods.getValidAndInvalidEmailAddress(value);
            System.out.println("New User email address is " + userEmailAddress);
            logger.log("New user email address is " + userEmailAddress);
            requestParams.put(params, userEmailAddress);
        } else if (value.contains("DisplayName")) {
            String displayName = userMethods.getValidAndInvalidDisplayName(value);
            System.out.println("New User display name is " + displayName);
            logger.log("New user display name is " + displayName);
            requestParams.put(params, displayName);
        } else if (value.contains("MobilePhone")) {
            String mobilePhone = userMethods.getValidAndInvalidInvalidMobilePhone(value);
            System.out.println("Mobile Phone value is " + mobilePhone);
            logger.log("New user Mobile number is " + mobilePhone);
            requestParams.put(params, mobilePhone);
        } else if (value.contains("OtherPhone")) {
            String otherPhoneNumber = userMethods.getValidAndInvalidOtherPhoneNumber(value);
            System.out.println("Other phone is " + otherPhoneNumber);
            logger.log("New user other phone is " + otherPhoneNumber);
            requestParams.put(params, otherPhoneNumber);

        } else if (value.equals("validStatus")) {

            value = "Logged on";
            System.out.println("status is " + value);
            logger.log("User logged on status is " + value);
            requestParams.put(params, value);
            String clientGroupMid = common.getClientGroupMidFromMClients();
            requestParams.put("clientGroupMid", clientGroupMid);

        } else if (value.equals("validRole")) {

            List<Map<String, String>> roleDescriptionKeyField = common.getAccesGroupDescriptionFromAccessGroupsTable();
            Map<String, String> descriptionList = new HashMap<String, String>();
            System.out.println("Description from DB size is " + roleDescriptionKeyField.size());

            descriptionList = roleDescriptionKeyField.get(listOfRoleindex);
            String roleDescription = descriptionList.get("DESCRIPTION");

            requestParams.put(params, userMethods.getRoleDetails(roleDescription));
        } else if (value.contains("role")) {

            String roleDescriptionKeyField = "";

            requestParams.put(params, userMethods.getRoleDetails(roleDescriptionKeyField));
        } else if (value.contains("accounts")) {
            String userName = getOLSUerName();
            requestParams.put(params, userMethods.addTheAccountNumberForNewUser(value, userName));

        }

        System.out.println("Request params " + requestParams);
        logger.log("Requet params for user creation " + requestParams);
        response = postRequestAsBearerAuthWithBodyData(userEndPoint, requestParams.toString(),
                PropUtils.getPropValue(inputProp, "AuthorizationToken"));


    }

    @When("^user wants to update the user details to pass the params as \"([^\"]*)\" by value \"([^\"]*)\"$")
    public void user_wants_to_update_the_user_details_to_pass_the_params_as_by_value(String params, String value) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Params " + params);
        System.out.println("Value " + value);


        String editProfileEndPoint = PropUtils.getPropValue(inputProp, "editProfileEndPointAPI");


        if (value.equals("existingUserId")) {
            existingUserName = PropUtils.getPropValue(inputProp, "newUserIDCreation");
            newUserDetails = common.getUserDetailsFromDB(existingUserName);
            System.out.println("Existing user id is " + existingUserName);
            logger.log("Existing user id from property is " + existingUserName);
            requestParams.put(params, existingUserName);
        } else if (value.contains("EmailAddress")) {
            String existingUserEmailAddress = userMethods.getEmailAddressForUpdateUser(value);
            System.out.println("Updated email address is " + existingUserEmailAddress);
            logger.log("Updated email address is " + existingUserEmailAddress);
            requestParams.put(params, existingUserEmailAddress);
        } else if (value.contains("DisplayName")) {
            String existingUserdisplayName = userMethods.getValidAndInvalidDisplayName(value);
            System.out.println("Updated display name is " + existingUserdisplayName);
            logger.log("Updated display name is " + existingUserdisplayName);
            requestParams.put(params, existingUserdisplayName);
        } else if (value.contains("MobilePhone")) {
            String existingUserMobilePhone = userMethods.getValidAndInvalidInvalidMobilePhone(value);
            System.out.println("Mobile Phone value is " + existingUserMobilePhone);
            logger.log("Updated Mobile number is " + existingUserMobilePhone);
            requestParams.put(params, existingUserMobilePhone);
        } else if (value.contains("OtherPhone")) {
            String existingOtherPhoneNumber = userMethods.getValidAndInvalidOtherPhoneNumber(value);
            System.out.println("Updated Other phone is " + existingOtherPhoneNumber);
            logger.log("Updated other phone is " + existingOtherPhoneNumber);
            requestParams.put(params, existingOtherPhoneNumber);
        } else if (value.equals("validRole")) {

            List<Map<String, String>> roleList = common.getAccesGroupDescriptionFromAccessGroupsTable();
            Map<String, String> updatedescription = new HashMap<String, String>();


            updatedescription = roleList.get(listOfRoleindex + 1);
            String roleDescription = updatedescription.get("DESCRIPTION");

            requestParams.put(params, userMethods.getRoleDetails(roleDescription));
        } else if (value.contains("role")) {

            String updateRoledescriptionAsEmpty = "";

            requestParams.put(params, userMethods.getRoleDetails(updateRoledescriptionAsEmpty));
        }

        System.out.println("Request params " + requestParams);
        logger.log("Requet params for user creation " + requestParams);

        response = putRequestAsBearerAuthWithBodyData(editProfileEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));

        //	response = putRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "AuthorizationToken"),requestParams, userEndPoint+editProfileEndPoint);


    }


    @Then("^validate the user module error response status number \"([^\"]*)\" and status message \"([^\"]*)\"$")
    public void validate_the_user_module_error_response_status_number_and_status_message(String statusNumber,
                                                                                         String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        boolean userIsPresenceInDB = false;
        boolean customerNoIsAddedInDB;

        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            // logger.log("API does not return response value for success scenario");
            PropUtils.setProps("newUserIDCreation", newUserId, inputDataFilePath);
            refreshPropertiesFile();
            String newUserLogOnIdFromProperty = PropUtils.getPropValue(inputProp, "newUserIDCreation");
            System.out.println("New user id from property file " + newUserLogOnIdFromProperty);
            userIsPresenceInDB = common.checkingThePresenceOfNewUserIdInDB(newUserLogOnIdFromProperty);
            customerNoIsAddedInDB = userMethods.validateCustomerNoAddedToTheNewUser(newUserLogOnIdFromProperty);

            if (userIsPresenceInDB && customerNoIsAddedInDB) {
                System.out.println("DB validation is passed");
                logger.log(
                        "DB validation is passed- New user has been created and customer number is added in user_members table");
            } else {
                logger.log("DB validation is failed- New user has not been created and customer number is not added");
            }
        } else {
            userMethods.validateUserModuleStatusNumberAndStatusMessage(statusNumber, statusMessage, logger);
            System.out.println("Validations done for both status number and status message");
        }

    }


    @Then("^validate the valid user details are updated in DB and validate error response status number \"([^\"]*)\" and status message \"([^\"]*)\"$")
    public void validate_the_valid_user_details_are_updated_in_DB_and_validate_error_response_status_number_and_status_message(
            String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            Map<String, String> updatedUserDetails = new HashMap<String, String>();
            updatedUserDetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "newUserIDCreation"));

            if (!(newUserDetails.get("EMAIL_ADDRESS").equals(updatedUserDetails.get("EMAIL_ADDRESS")))
                    && !(newUserDetails.get("NAME").equals(updatedUserDetails.get("NAME")))) {
                System.out.println("Validated from DB for updated user details");
                logger.log("DB Validations- passed for updated user details");
            } else {
                System.out.println("Validation failed");
                logger.log("DB Validations- failed for updated user details");
            }

        } else {
            userMethods.validateUserModuleStatusNumberAndStatusMessage(statusNumber, statusMessage, logger);
            System.out.println("Validations done for both status number and status message");
        }

    }

    @When("^user try to get the user details with user endPoint and logonId \"([^\"]*)\"$")
    public void user_try_to_get_the_user_details_with_user_endPoint_and_logonId(String ScenarioType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        String userEndPoint = PropUtils.getPropValue(inputProp, "userEndPointAPI");
        String newUserId = "";
        System.out.println("User endpoint is " + userEndPoint);
        String bearerToken = PropUtils.getPropValue(inputProp, "AuthorizationToken");

        if (ScenarioType.equals("valid")) {
            newUserId = PropUtils.getPropValue(inputProp, "newUserIDCreation");
            //	newUserId = getOLSUerName();
        } else if (ScenarioType.equals("Invalid")) {
            System.out.println("Inside of if statement");
            newUserId = faker.name().firstName();
            System.out.println("Invalid user name " + newUserId);

        } else if (ScenarioType.equals("InvalidToken")) {
            newUserId = getOLSUerName();
            bearerToken = faker.number().digits(10);
        }

        response = getRequestAsBearerAuthWithSingleQueryParam(userEndPoint, bearerToken, "logonId", newUserId);
        logger.log("Response for user details " + response.jsonPath().prettyPrint());

    }

    @Then("^validate the  logonId user details from response$")
    public void validate_the_logonId_user_details_from_response() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> userDetails = new HashMap<String, String>();
        Map<String, Integer> roleResponse = new HashMap<String, Integer>();

        userDetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "newUserIDCreation"));
        roleResponse = response.jsonPath().get("role");
        System.out.println("Role details " + roleResponse);
        String clientGroupMidFromResponse = Integer.toString(roleResponse.get("clientGroupMid"));
        System.out.println("ClientGroup id from response " + clientGroupMidFromResponse);
        String accessGroupOidFromResponse = Integer.toString(roleResponse.get("accessGroupOid"));


        if (response.jsonPath().get("userId").equals(userDetails.get("LOGON_ID"))
                && Integer.toString(response.jsonPath().get("userOid")).equals(userDetails.get("USER_OID"))
                && response.jsonPath().get("mobilePhone").equals(userDetails.get("MOBILE_PHONE"))

                && response.jsonPath().get("emailAddress").equals(userDetails.get("EMAIL_ADDRESS"))
                && clientGroupMidFromResponse.equals(userDetails.get("CLIENT_GROUP_MID"))
                && accessGroupOidFromResponse.equals(userDetails.get("ACCESS_GROUP_OID"))) {
            System.out.println("Inside of if statement");
            logger.log("Validated the user details from response and DB");
            Assert.assertTrue(true);
        }

    }

    @Then("^validate the account profile details from response$")
    public void validate_the_account_profile_details_from_response() throws Throwable {
        String accountNumbers;

        int index = 0;

        List<Map<String, String>> customerNumbersFromDB = new ArrayList<Map<String, String>>();

        responseBody = response.getBody().asString();
        System.out.println("Response for user details is " + responseBody);

        Map<String, String> customerNo = new HashMap<String, String>();
        customerNumbersFromDB = common.getAllRowsCustomerNumberFromCustomerTable("associatedUser",
                PropUtils.getPropValue(inputProp, "newUserIDCreation"));
        // System.out.println("Customer numbers from DB " + customerNumbersFromDB);

        JSONParser jsonParser = new JSONParser();
        JSONObject jobj = (JSONObject) jsonParser.parse(responseBody);
        System.out.println("JSON object " + jobj);
        JSONArray accountNumberResponse = (JSONArray) jobj.get("accountProfiles");
        System.out.println("Account Profiles from response " + accountNumberResponse);

        System.out.println("Account profile size is " + accountNumberResponse.size());
        for (int i = 0; i < accountNumberResponse.size(); i++) {

            // JSONObject jobj1 = (JSONObject) accountNumberResponse.get(i);
            // System.out.println("Json object "+jobj1);
            // String firstIndexAccountnumer = (String)jobj1.get(i);
            // System.out.println("First index account number "+ firstIndexAccountnumer );

            accountNumbers = accountNumberResponse.get(i).toString();

            System.out.println("Account number is " + accountNumbers);

            String accountNo[] = accountNumbers.split(",");
            System.out.println("Length of account number is " + accountNo.length);

            String indexAccount[] = accountNo[0].split(":");
            String accountNumberFromAccountProfiles = indexAccount[1].replaceAll("\"", "").toString();
            System.out.println("Account number from account profile response " + accountNumberFromAccountProfiles);

            // Comparing the customer number from DB and response

//			boolean customerNumberFound = false;
//
//			do {
//
//				customerNo = customerNumbersFromDB.get(index);
//				// System.out.println("From DB " + customerNo);
//				String customerNumberForAssociatedUser = customerNo.get("CUSTOMER_NO").toString();
//				System.out.println("customer number for associated user is " + customerNumberForAssociatedUser);
//
//				if (accountNumberFromAccountProfiles.equals(customerNumberForAssociatedUser)) {
//					System.out.println("Both customer numbers are equal from response and DB");
//					customerNumberFound = true;
//
//
//				}
//				index = index+1;
//
//			} while (customerNumberFound == false);

            for (int k = 0; k < customerNumbersFromDB.size(); k++) {
                customerNo = customerNumbersFromDB.get(k);
                String customerNumberForAssociatedUser = customerNo.get("CUSTOMER_NO").toString();
                System.out.println("customer number for associated user is " + customerNumberForAssociatedUser);

                if (accountNumberFromAccountProfiles.equals(customerNumberForAssociatedUser)) {
                    System.out.println("Both customer numbers are equal from response and DB");
                    logger.log("Validated the customer numbers are equal from response and DB");

                    break;

                }

            }

        }

    }


    @Then("^validate the error response status number \"([^\"]*)\" and status message \"([^\"]*)\"$")
    public void validate_the_error_response_status_number_and_status_message(String statusNumber, String statusMessage)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int invalidUserStatusNumber;
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            logger.log("For Success scenario does not return any response message");
        } else {
            String errorStatusMessage = response.path("errors.statusMessage");
            System.out.println("Status message from response " + errorStatusMessage);
            validateResponseMessage(errorStatusMessage, statusMessage, logger,
                    "Expected status message from response is " + errorStatusMessage);

            invalidUserStatusNumber = response.path("errors.statusNumber");

            System.out.println("Status Number from response " + invalidUserStatusNumber);

            validateStatusNumber(invalidUserStatusNumber, Integer.parseInt(statusNumber), logger,
                    "Expected status number from response is " + invalidUserStatusNumber);


        }

    }

    @When("^user try to update the accounts as params \"([^\"]*)\" and value as \"([^\"]*)\" for associated user$")
    public void user_try_to_update_the_accounts_as_params_and_value_as_for_associated_user(String param, String accountType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String accountProfileEndPoint = PropUtils.getPropValue(inputProp, "accountProfileEndPoint");
        System.out.println("Account profile end point " + accountProfileEndPoint);

        String newUserName = PropUtils.getPropValue(inputProp, "newUserIDCreation");

        requestParams.put(param, userMethods.updateAccountNumbers(accountType, PropUtils.getPropValue(inputProp, "UserNameAPI")));
        logger.log("request parms for account profiles " + requestParams);

        if (accountType.contains("OtherUser")) {
            response = putRequestAsBearerAuthWithSingleQueryParam(accountProfileEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "logonId", newUserName, requestParams.toString());
        } else {
            response = putRequestAsBearerAuthWithSingleQueryParam(accountProfileEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "logonId", PropUtils.getPropValue(inputProp, "UserNameAPI"), requestParams.toString());
            System.out.println("Response for logged on user " + response.jsonPath().prettyPrint());
        }

    }

    @When("^user able to change the status for other user from status \"([^\"]*)\" to status \"([^\"]*)\"$")
    public void user_able_to_change_the_status_for_other_user_from_status_to_status(String fromStatus, String toStatus)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        String userStatusDescription, userLogOnStatusCid;
        Map<String, String> userDetails = new HashMap<String, String>();
        userDetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "newUserIDCreation"));
        String editStatusEndPoint = PropUtils.getPropValue(inputProp, "editStatusEndPointAPI");
        System.out.println("Edit status endpoint " + editStatusEndPoint);


        requestParams.put("userOid", userDetails.get("USER_OID"));
        requestParams.put("userId", userDetails.get("LOGON_ID"));

        if (fromStatus.equals("Logged on")) {
            // System.out.println("Inside of logged on status");
            common.updateLogonStatusCID(PropUtils.getPropValue(inputProp, "newUserIDCreation"), 2501);
            userStatusDescription = common.getUSerStatusDescription("2501");
            System.out.println("User current Status is " + userStatusDescription);
            logger.log("User current status is " + userStatusDescription);

            requestParams.put("status", toStatus);
            logger.log("Request params is " + requestParams);

        } else if (fromStatus.equals("Active")) {
            // System.out.println("Inside of active status");
            common.updateLogonStatusCID(PropUtils.getPropValue(inputProp, "newUserIDCreation"), 2505);
            userStatusDescription = common.getUSerStatusDescription("2505");
            System.out.println("User current Status is " + userStatusDescription);
            logger.log("User current status is " + userStatusDescription);
            requestParams.put("status", toStatus);
            logger.log("Request params is " + requestParams);
        } else if (fromStatus.equals("Inactive")) {
            // System.out.println("Inside of inactive status");
            common.updateLogonStatusCID(PropUtils.getPropValue(inputProp, "newUserIDCreation"), 2506);
            userStatusDescription = common.getUSerStatusDescription("2506");
            System.out.println("USer current Status is " + userStatusDescription);
            logger.log("User current status is " + userStatusDescription);
            requestParams.put("status", toStatus);
            logger.log("Request params is " + requestParams);
        } else if (fromStatus.equals("Permanent Lockout")) {
            // System.out.println("Inside of permanent lockout status");
            common.updateLogonStatusCID(PropUtils.getPropValue(inputProp, "newUserIDCreation"), 2504);
            userStatusDescription = common.getUSerStatusDescription("2504");
            System.out.println("USer current Status is " + userStatusDescription);
            logger.log("User current status is " + userStatusDescription);
            requestParams.put("status", toStatus);
            logger.log("Request params is" + requestParams);
        } else if (fromStatus.equals("Temporary Lockout")) {
            // System.out.println("Inside of permanent lockout status");
            common.updateLogonStatusCID(PropUtils.getPropValue(inputProp, "newUserIDCreation"), 2503);
            userStatusDescription = common.getUSerStatusDescription("2503");
            System.out.println("User current Status is " + userStatusDescription);
            logger.log("User current status is " + userStatusDescription);
            requestParams.put("status", toStatus);
            logger.log("Request params is" + requestParams);
        } else if (fromStatus.equals("loggedOnUserStatus")) {

            Map<String, String> loggedOnUSerDetails = new HashMap<String, String>();
            loggedOnUSerDetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
            requestParams.put("userOid", loggedOnUSerDetails.get("USER_OID"));
            requestParams.put("userId", loggedOnUSerDetails.get("LOGON_ID"));
            requestParams.put("status", toStatus);
            logger.log("Request params is" + requestParams);
        }

//		if (userStatusDescription.equals(fromStatus)) {
//
//
//			requestParams.put("status", toStatus);
//
//		}
//		else {
//			}
        response = putRequestAsBearerAuthWithBodyData(editStatusEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        //	response = putRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "AuthorizationToken"),requestParams, editStatusEndPoint);
    }

    @Then("^validate the status updation error response statusNumber \"([^\"]*)\" and stausMessage \"([^\"]*)\"$")
    public void validate_the_status_updation_error_response_statusNumber_and_stausMessage(String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            logger.log("API does not return the response value for success scenario");

        } else {
            userMethods.validateUserModuleStatusNumberAndStatusMessage(statusNumber, statusMessage, logger);
            System.out.println("Validations done for both status number and status message");
        }

    }

    @When("^user able to generate a password for the new user using login end point \"([^\"]*)\"$")
    public void user_able_to_generate_a_password_for_the_new_user_using_login_end_point(String loginEndPoint)
            throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> newPasswordToken = new HashMap<String, String>();
        newPasswordToken = common.getTokenDetailsFromDB(PropUtils.getPropValue(inputProp, "newUserIDCreation"));
        String tokenFromDB = newPasswordToken.get("TOKEN");
        System.out.println("Reset Password token db is " + tokenFromDB);
        String accessToken = base64Encoder(PropUtils.getPropValue(inputProp, "CLIENT_ID"),
                PropUtils.getPropValue(inputProp, "SECERT_ID"));

        requestParams.put("token", tokenFromDB);

        String randomNewPassword = PasswordGenerator.generateRandomPassword(logger, 12);

        PropUtils.setProps("newUserIdPassword", randomNewPassword, inputDataFilePath);
        System.out.println("Updated password in " + System.getProperty("environment") + randomNewPassword);

        requestParams.put("newPassword", randomNewPassword);
        requestParams.put("confirmPassword", randomNewPassword);

        String resetPasswordAPIEndPoint = PropUtils.getPropValue(inputProp, "reset-passwordAPIEndPoint");

        resetPasswordAPIEndPoint = loginEndPoint + resetPasswordAPIEndPoint;

        System.out.println("reset-password endPoint " + resetPasswordAPIEndPoint);
        // Post a request for login API call
        response = postRequestAsBasicAuthWithBodyData(resetPasswordAPIEndPoint, requestParams, accessToken);
        jsonPathEvaluator = response.jsonPath();
        //	logger.log("Request params-->" + requestParams.toString());
        logger.log("Password has been generated for new user ");

    }

}
