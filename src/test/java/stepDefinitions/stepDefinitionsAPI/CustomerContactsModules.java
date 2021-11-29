package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Map;

public class CustomerContactsModules extends RequestMethodsUtils {

    public Scenario logger;
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    Map<String,String> contactTypeTableValues;
    JsonObject requestBodyParamsCreate;
    JsonObject requestBodyParamsUpdate;

    public CustomerContactsModules(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }
     
    @Then("^user pass \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"as parameters$")
    public void user_pass_and_and_and_and_and_and_and_and_and_and_and_and_and_as_parameters(String contactName, String emailAddress, String contactType, String contactTitle, String phoneMobile1,
                                                                                            String phoneBusiness, String phoneFax, String addressSt, String suburbSt, String postalCodeSt, String addressPt, String suburbPt, String postalCodePt,String contactOid) throws Throwable {
        System.out.println("////////////////////////////////////////////////////////////////////////////////////");
//        String contactEndPoint = PropUtils.getPropValue(inputProp, "GET_CONTACTS");
//        contactEndPoint = customerEndPoint + contactEndPoint;
//        logger.log("Request path -->" + contactEndPoint);
        String cusNoAry[]=customerEndPoint.split("/");
        String cusNo=cusNoAry[2];
        System.out.println("cusNo"+cusNo);
        System.out.println("shortName--->" + PropUtils.getPropValue(inputProp, clientCountry));
        String country = "", state = "";
//        String contactType="";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);

        if (contactName.equalsIgnoreCase("maxName")) {
            System.out.println("inside maxName");
            contactName = "abcdefghijklmnopqrstuvwxyzabcdefghij";
        } if (phoneMobile1.equalsIgnoreCase("maxPhone")||phoneFax.equalsIgnoreCase("maxPhone")) {
            System.out.println("inside maxPhone1");
            phoneMobile1 = "12345678901234567890";
        } if (phoneBusiness.equalsIgnoreCase("maxPhone")||postalCodeSt.equalsIgnoreCase("maxPhone")) {
            System.out.println("inside maxPhone2");
            phoneBusiness = "12345678901234567890";
        }  if (phoneFax.equalsIgnoreCase("maxPhone")) {
            System.out.println("inside maxPhone1");
            phoneFax = "12345678901234567890";
        } if (postalCodeSt.equalsIgnoreCase("maxPhone")) {
            System.out.println("inside maxPhone2");
            postalCodeSt = "12345678901234567890";
        }
        if (suburbSt.equalsIgnoreCase("country")) {
            System.out.println("inside country");
            country = "KZ";
        }
        if (contactType.equalsIgnoreCase("associatedY")||contactType.equalsIgnoreCase("associatedN")) {
            if(contactType.equalsIgnoreCase("associatedY")) {
                System.out.println("inside associatedY");
                contactTypeTableValues = common.getContactTypeBasedOnClient("associated", true);
                contactType=contactTypeTableValues.get("DESCRIPTION");
                System.out.println("contactType111"+contactTypeTableValues.get("DESCRIPTION"));
            }else {
                System.out.println("inside associatedN");
                contactTypeTableValues = common.getContactTypeBasedOnClient("associated", false);
                contactType=contactTypeTableValues.get("DESCRIPTION");
                System.out.println("contactType111"+contactTypeTableValues.get("DESCRIPTION"));
            }
        }else if (contactType.equalsIgnoreCase("unAssociated")) {
            System.out.println("inside unAssociated");
            contactTypeTableValues = common.getContactTypeBasedOnClient("unAssociated",true);
            contactType=contactTypeTableValues.get("DESCRIPTION");
            System.out.println("contactType111"+contactTypeTableValues.get("DESCRIPTION"));
        }

        contactOid = common.getContactOidCustomerFromDB(cusNo,contactOid);
        System.out.println("contactOid"+contactOid);

        requestBodyParamsCreate = Json.createObjectBuilder()
                .add("contactName", contactName).add("emailAddress", emailAddress)
                .add("contactType", contactType).add("contactTitle", contactTitle)
                .add("phoneMobile1", phoneMobile1).add("phoneBusiness", phoneBusiness)
                .add("phoneFax", phoneFax).add("isDefault", "N")
                .add("streetAddress", Json.createObjectBuilder()
                        .add("addressLine", addressSt)
                        .add("country", country).add("suburb", suburbSt)
                        .add("postalCode", postalCodeSt)

                )
                .add("postalAddress", Json.createObjectBuilder().add("addressLine", addressPt)
                        .add("country", country).add("suburb", suburbPt)
                        .add("postalCode", postalCodePt)

                )
                .build();

        requestBodyParamsUpdate = Json.createObjectBuilder().add("contactOid", contactOid)
                .add("contactName", contactName).add("emailAddress", emailAddress)
                .add("contactType", contactType).add("contactTitle", contactTitle)
                .add("phoneMobile1", phoneMobile1).add("phoneBusiness", phoneBusiness)
                .add("phoneFax", phoneFax).add("isDefault", "N")
                .add("streetAddress", Json.createObjectBuilder().add("addressLine", addressSt)
                        .add("country", country).add("suburb", suburbSt)
                        .add("postalCode", postalCodeSt)
                )
                .add("postalAddress", Json.createObjectBuilder().add("addressLine", addressPt)
                        .add("country", country).add("suburb", suburbPt)
                        .add("postalCode", postalCodePt)

                )
                .build();

//        System.out.println("contactDetailsParams :"+requestBodyParams.toString());
//        logger.log("Request body -->" + requestBodyParams.toString());
        System.out.println("////////////////////////////////////////////////////////////////////////////////////");
    }

    @Then("^user post request body to create contacts and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPostRequestBodyToCreateContactsAndValidateWithAndAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        System.out.println("authToken :" + PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("authToken :" + authorizationToken);
        String contactEndPoint = PropUtils.getPropValue(inputProp, "GET_CONTACTS");
        contactEndPoint = customerEndPoint + contactEndPoint;
        logger.log("Request path -->" + contactEndPoint);
        logger.log("Request body -->" + requestBodyParamsCreate.toString());
        boolean presenceCheck = false;
        String userId = getOLSUerName();
        String contactName = requestBodyParamsCreate.getString("contactName");
        String emailAddress = requestBodyParamsCreate.getString("emailAddress");
        String phoneMobile1 = requestBodyParamsCreate.getString("phoneMobile1");

        response = postRequestAsBearerAuthWithBodyData(contactEndPoint, requestBodyParamsCreate.toString(), authorizationToken);
        if((response.getStatusCode() == 200) && statusNumber == 42029){
            System.out.println("you were tricked");
            response = postRequestAsBearerAuthWithBodyData(contactEndPoint, requestBodyParamsCreate.toString(), authorizationToken);
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        } else if (response.getStatusCode() == 200) {
            presenceCheck = common.checkingThePresenceOfContactsInDB(userId,contactName,emailAddress,phoneMobile1,contactTypeTableValues.get("CONTACT_TYPE_OID"));
            if (presenceCheck) {
                logger.log("DB Validation passed-Created Contact Details found in DB");
            } else {
                softFail("DB Validation failed-Created Contact Details not found in DB", logger);
            }
        } else {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }

    @Then("^user put request body to create contacts and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPutRequestBodyToCreateContactsAndValidateWithAndAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        System.out.println("authToken :" + PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("authToken :" + authorizationToken);
        String contactEndPoint = PropUtils.getPropValue(inputProp, "editContact");
        contactEndPoint = customerEndPoint + contactEndPoint;
        logger.log("Request path -->" + contactEndPoint);
        logger.log("Request body -->" + requestBodyParamsUpdate.toString());
        boolean presenceCheck = false;
        String userId = getOLSUerName();
        String contactName = requestBodyParamsUpdate.getString("contactName");
        String emailAddress = requestBodyParamsUpdate.getString("emailAddress");
        String phoneMobile1 = requestBodyParamsUpdate.getString("phoneMobile1");

        response = putRequestAsBearerAuthWithBodyData(contactEndPoint, requestBodyParamsUpdate.toString(), authorizationToken);
        if((response.getStatusCode() == 200) && statusNumber == 42029){
            System.out.println("you were tricked");
            response = putRequestAsBearerAuthWithBodyData(contactEndPoint, requestBodyParamsUpdate.toString(), authorizationToken);
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        } else if (response.getStatusCode() == 200) {
            presenceCheck = common.checkingThePresenceOfContactsInDB(userId,contactName,emailAddress,phoneMobile1,contactTypeTableValues.get("CONTACT_TYPE_OID"));
            if (presenceCheck) {
                logger.log("DB Validation passed-Created Contact Details found in DB");
            } else {
                softFail("DB Validation failed-Created Contact Details not found in DB", logger);
            }
        } else {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }

    @Then("^user hit the get request to get the list of customer contact details and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userHitTheGetRequestToGetTheListOfCustomerContactDetailsAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        logger.log("Request path -->" + customerEndPoint);
        response = getRequestWithPath(customerEndPoint, authorizationToken);
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

    @Then("^user hit delete request body to delete a contact and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userHitDeleteRequestBodyToDeleteAContactAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        System.out.println("authToken :" + PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("authToken :" + authorizationToken);
        String contactEndPoint = PropUtils.getPropValue(inputProp, "GET_CONTACTS");
        contactEndPoint = customerEndPoint + contactEndPoint;
        boolean presenceCheck = false;
        logger.log("Request path -->" + contactEndPoint);
        logger.log("Request body -->" + requestBodyParamsUpdate.toString());
        String contactOid = requestBodyParamsUpdate.getString("contactOid");

        response = deleteRequestAsBearerAuth(contactEndPoint, requestBodyParamsUpdate.toString(), authorizationToken);
        if (response.getStatusCode() == 200) {
            presenceCheck = common.checkingThePresenceOfContactBasedOnContactOid(contactOid);
            if (!presenceCheck) {
                logger.log("DB Validation passed-Contact Details successfully deleted");
            } else {
                softFail("DB Validation failed-Unable to delete Contact Details", logger);
            }
        } else {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }
}


