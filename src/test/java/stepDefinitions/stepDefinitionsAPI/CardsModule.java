package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.SkipException;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.lang.management.ThreadInfo;
import java.util.*;

public class CardsModule extends RequestMethodsUtils {

    public Scenario logger;
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    Map<String, String> cardsDetailsMap = new HashMap<>();
    String cardDetailsEndPoint = PropUtils.getPropValue(inputProp, "cardDetails");
    String cardUpdateEndPoint = PropUtils.getPropValue(inputProp, "cardUpdate");
    String defaultProfileEndPoint = PropUtils.getPropValue(inputProp, "defaultProfile");
    Random rand = new Random();

    String customerNumber = "", cardNumber = "", referenceNo = "", statusFromDB, customerNumberFromDB;
    String firstCardNumberfromDB = null, secondCardNumberFromDB = null, cusNo;
    public String reissueActionCid, statusFromResponse, cardNumberBasedOnStatus, futureDate;
    boolean presenceCheck = false;
    String expCardNumber = "";
    JSONParser parser = new JSONParser();
    JSONArray arrayFormatForCusNo = new JSONArray();
    JSONArray arrayFormatForCountries = new JSONArray();
    JSONArray arrayFormatForStatus = new JSONArray();
    JSONArray arrayFormatForCostCentres = new JSONArray();
    JSONObject specificCardObjectFromResponse = null;
    JSONObject cardObject = null;
    String customerNoFromDB, cardControlProfileOidValue;
    String cardOfferFromDB = "";
    String cardProductFromDB = "";
    String cusNoFromDB, cardNoFromDB, costCentreFromDB, driverNameFromDB, licensePlateFromDB, countryFromDB, cardStatusFromDB;
    JSONArray productRestrictionResponse;
    JSONArray locationRestrictionTypeFromResponse;
    JSONArray locationRestrictionTo;
    JSONArray timeLimitArrayResponse;
    String specificProductGroup, specificLocationGroup, specificLocationRestrictedToValue, specificLocationType, timeLimitValue;

    public CardsModule(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @Then("^user post request body to get cards and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPostRequestBodyToGetCardsAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
        System.out.println("authToken :" + PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("authToken :" + authorizationToken);
        String cardsEndpoint = PropUtils.getPropValue(inputProp, "GET_CARDS");
        String searchCardsEndPoint = customerEndPoint + cardsEndpoint;
        logger.log("Request path -->" + searchCardsEndPoint);

        requestBodyParams = Json.createObjectBuilder().build();

        System.out.println("contactDetailsParams :" + requestBodyParams.toString());
        logger.log("Request body -->" + requestBodyParams.toString());

        response = postRequestAsBearerAuthWithBodyData(searchCardsEndPoint, requestBodyParams.toString(), authorizationToken);
//        commonMethods.responseValidation(statusMessage,statusNumber,logger);

        if (response.getStatusCode() == 200) {
            logger.log("Response --> " + response.getBody().prettyPrint());
//            presenceCheck = common.checkingThePresenceOfContactsInDB(userId,contactName,emailAddress,phoneMobile1,contactTypeTableValues.get("CONTACT_TYPE_OID"));
            presenceCheck = true;
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

    @Then("^user get the card details and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and (\\d+) as expected values$")
    public void userGetTheCardDetailsAndValidateWithAndStatusNumberAsExpectedValues(String paramCustNumber, String paramCardNumber, String statusMessage, int statusNumber) throws Throwable {

        boolean presenceCheck = false;
        String cardDetailsEndPoint = PropUtils.getPropValue(inputProp, "cardDetails");
        logger.log("Request path -->" + cardDetailsEndPoint);

        Map<String, String> customerAndCardNo;
        if (paramCustNumber.equalsIgnoreCase("associatedUser") && paramCardNumber.equalsIgnoreCase("associatedCard")) {
            customerAndCardNo = common.getValidCardNumberAndItsCustomer();
            customerNumber = customerAndCardNo.get("CUSTOMERNUMBER");
            cardNumber = customerAndCardNo.get("CARDNUMBER");
        } else {
            customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, paramCardNumber);
            customerNumber = customerAndCardNo.get("customerNumber");
            cardNumber = customerAndCardNo.get("cardNumber");
        }

        requestBodyParams = Json.createObjectBuilder().add("cardNo", cardNumber).build();

        System.out.println("cardDetailsParams :" + requestBodyParams.toString());
        logger.log("Request body -->" + requestBodyParams.toString());

        response = postRequestAsBearerAuthWithBodyData(cardDetailsEndPoint, requestBodyParams.toString(), authorizationToken);
        logger.log("Response --> " + response.getBody().prettyPrint());
        if (response.getStatusCode() == 200) {
            cardsDetailsMap = common.getCardDetailsFromDB(cardNumber);
        } else {
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }

    @Then("^user put the edit card request to update the card details of specific card and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPutTheEditCardRequestToUpdateTheCardDetailsOfSpecificCardAndValidateWithAndStatusNumberAsExpectedValues(String paramCustNumber, String paramCardNumber, String newCard, String statusMessage, int statusNumber) throws Throwable {
        cardsDetailsMap.clear();
        expCardNumber = "";
        String name = fakerAPI().name().firstName();
        String embossName = name, requestedBy = name, driverName = name, driverId = name;
        String vehiDes = "Range Rover", license = name + 11, contactName = name, addressLine = "Mystic falls", suburb = "Oval", postalCode = "6666";
        Map<String, String> customerAndCardNo;
        if (paramCustNumber.equalsIgnoreCase("associatedUser") && paramCardNumber.equalsIgnoreCase("associatedCard")) {
            customerAndCardNo = common.getValidCardNumberAndItsCustomer();
            customerNumber = customerAndCardNo.get("CUSTOMERNUMBER");
            cardNumber = customerAndCardNo.get("CARDNUMBER");
        } else {
            customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, paramCardNumber);
            customerNumber = customerAndCardNo.get("customerNumber");
            cardNumber = customerAndCardNo.get("cardNumber");
        }
        expCardNumber = cardNumber;

        String country = "", state = "";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);

//        Get Card Part
        requestBodyParams = Json.createObjectBuilder().add("cardNo", cardNumber).build();
        System.out.println("cardDetailsParams :" + requestBodyParams.toString());
        logger.log("Request body -->" + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(cardDetailsEndPoint, requestBodyParams.toString(), authorizationToken);

//        Edit Card Part
        JSONParser parser = new JSONParser();
        JSONObject getCards = (JSONObject) parser.parse(response.asString());
        JSONObject cardObj = (JSONObject) getCards.get("card");
        cardObj.replace("embossingName", embossName);
        getCards.replace("card", cardObj);
//        For Validations
        cardsDetailsMap.put("CARDOFFER", (String) getCards.get("cardOffer"));
        cardsDetailsMap.put("CARDPRODUCT", (String) getCards.get("cardProduct"));
        cardsDetailsMap.put("CARDPROGRAM", (String) getCards.get("cardProgram"));
        cardsDetailsMap.put("embossingName", (String) cardObj.get("embossingName"));
        cardsDetailsMap.put("state", state);
        cardsDetailsMap.put("country", country);
        cardsDetailsMap.put("contactName", contactName);
        cardsDetailsMap.put("requestedBy", requestedBy);
        cardsDetailsMap.put("driverName", driverName);
        cardsDetailsMap.put("driverId", driverId);
        cardsDetailsMap.put("vehicleDescription", vehiDes);
        cardsDetailsMap.put("licensePlate", license);
        cardsDetailsMap.put("addressLine", addressLine);
        cardsDetailsMap.put("suburb", suburb);
        cardsDetailsMap.put("postalCode", postalCode);
//        cardsDetailsMap.put("profDescription",description);

        //        DriverObject for driver card
        JsonObject driverObject = Json.createObjectBuilder()
                .add("driver", Json.createObjectBuilder()
                        .add("driverId", driverId).add("driverName", driverName))
                .build();
//        VehicleObject for vehicle card
        JsonObject vehicleObject = Json.createObjectBuilder()
                .add("vehicle", Json.createObjectBuilder()
                        .add("description", vehiDes).add("licensePlate", license))
                .build();
//            Address
        JsonObject addressObject = Json.createObjectBuilder().
                add("cardContact", Json.createObjectBuilder().add("contactName", contactName).
                        add("streetAddress", Json.createObjectBuilder().add("addressLine", addressLine).
                                add("country", country).add("suburb", suburb).
                                add("postalCode", postalCode).add("state", state))).build();
        String cardType = (String) getCards.get("cardType");
        JsonObject driverVehicleObject;
//        if (cardType.equalsIgnoreCase("vehicle")) {
//            driverVehicleObject = Json.createObjectBuilder().addAll(Json.createObjectBuilder(vehicleObject)).build();
//        } else if (cardType.equalsIgnoreCase("driver")) {
//            driverVehicleObject = Json.createObjectBuilder().addAll(Json.createObjectBuilder(driverObject)).build();
//        } else
        driverVehicleObject = Json.createObjectBuilder().addAll(Json.createObjectBuilder(driverObject))
                .addAll(Json.createObjectBuilder(vehicleObject))
                .build();

        getCards.remove("cardContact");
        getCards.putAll(addressObject);
        if (getCards.containsKey("driver")) {
            getCards.remove("driver");
        }
        if (getCards.containsKey("vehicle")) {
            getCards.remove("vehicle");
        }
        getCards.putAll(driverVehicleObject);

        response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardUpdateEndPoint, getCards.toString(), authorizationToken, false);
        if (response.getStatusCode() == 200) {
            if (response.getBody().prettyPrint().equalsIgnoreCase("Record updated OK")) {
                logger.log("Edit Card Response body is  -->   " + response.getBody().prettyPrint());
                expCardNumber = cardNumber;
                logger.log("Card Successfully Updated as expected");
            }
        } else if (/*newCard.equalsIgnoreCase("new") &&*/ response.getBody().prettyPrint().contains("Reissue required")) {
            response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardUpdateEndPoint, getCards.toString(), authorizationToken, true);
            logger.log("Newly Generated Card is  -->   " + response.getBody().prettyPrint());
            expCardNumber = response.path("cardNo");
            logger.log("Card Successfully Updated as expected");
        } else {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }

    @Then("^user put the edit card request to update the card control profiles of specific card and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPutTheEditCardRequestToUpdateTheCardControlProfilesOfSpecificCardAndValidateWithAndStatusNumberAsExpectedValues(String paramCustNumber, String paramCardNumber, String cardCtrlProfiles, String statusMessage, int statusNumber) throws Throwable {
        cardsDetailsMap.clear();
        expCardNumber = "";
        Map<String, String> customerAndCardNo;
        if (paramCustNumber.equalsIgnoreCase("associatedUser") && paramCardNumber.equalsIgnoreCase("associatedCard")) {
            customerAndCardNo = common.getValidCardNumberAndItsCustomer();
            customerNumber = customerAndCardNo.get("CUSTOMERNUMBER");
            cardNumber = customerAndCardNo.get("CARDNUMBER");
        } else {
            customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, paramCardNumber);
            customerNumber = customerAndCardNo.get("customerNumber");
            cardNumber = customerAndCardNo.get("cardNumber");
        }

        String cardDetailsEndPoint = PropUtils.getPropValue(inputProp, "cardDetails");
        String cardUpdateEndPoint = PropUtils.getPropValue(inputProp, "cardUpdate");
        requestBodyParams = Json.createObjectBuilder().add("cardNo", cardNumber).build();
        System.out.println("cardDetailsParams :" + requestBodyParams.toString());
        logger.log("Request body -->" + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(cardDetailsEndPoint, requestBodyParams.toString(), authorizationToken);

      /*Edit Card Part - Seperating each objects and manipulating objects
      and finally adding to form single object*/
        JSONParser parser = new JSONParser();
        JSONObject getCardObj = (JSONObject) parser.parse(response.asString());
        JSONArray cardCtrlObjArr = (JSONArray) getCardObj.get("cardControlProfiles");
        JSONObject cardCtrlObj = (JSONObject) cardCtrlObjArr.get(0);
        JSONObject cardCtrl = (JSONObject) cardCtrlObj.get("cardControl");
        JSONObject veloCtrl = (JSONObject) cardCtrl.get("velocityAssignment");
        System.out.println("veloCtrl  -->" + veloCtrl.toString());
//        commonMethods.getVelocityLimits();
        String currentTransLimitValue = (String) veloCtrl.get("velocityTypeValue2");

        Response responseVelocityLimits = getRequestAsBearerAuth("/card/velocity-value-options", authorizationToken);
        JSONParser parser1 = new JSONParser();
        JSONObject getVelocityLimitObj = (JSONObject) parser1.parse(responseVelocityLimits.asString());
        JSONArray transLimitArr = (JSONArray) getVelocityLimitObj.get("Daily Transaction Limit (2)");
        String transLimitValue = (String) transLimitArr.get(0);
        System.out.println("currentTransLimitValue -->" + currentTransLimitValue);
        System.out.println("transLimitValue1  -->" + transLimitValue);
        if (currentTransLimitValue.equalsIgnoreCase(transLimitValue)) {
            transLimitValue = (String) transLimitArr.get(4);
            System.out.println("transLimitValue2  -->" + transLimitValue);
        }
        veloCtrl.replace("velocityTypeValue2", transLimitValue);
        cardsDetailsMap.put("transLimitValue", transLimitValue);

        cardCtrl.replace("velocityAssignment", veloCtrl);
        cardCtrlObj.replace("cardControl", cardCtrl);
        cardCtrlObjArr.set(0, cardCtrlObj);
        getCardObj.replace("cardControlProfiles", cardCtrlObjArr);

        String description = (String) cardCtrlObj.get("description");
        cardsDetailsMap.put("profDescription", description);

        response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardUpdateEndPoint, getCardObj.toString(), authorizationToken, false);
        if (response.getStatusCode() == 200) {
            if (response.getBody().prettyPrint().equalsIgnoreCase("Record updated OK")) {
                logger.log("Edit Card Response body is  -->   " + response.getBody().prettyPrint());
                expCardNumber = cardNumber;
                logger.log("Card Successfully Updated as expected");
            }
        } else if (response.getBody().prettyPrint().contains("Reissue required")) {
            response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardUpdateEndPoint, getCardObj.toString(), authorizationToken, true);
            logger.log("Newly Generated Card is  -->   " + response.getBody().prettyPrint());
            expCardNumber = response.path("cardNo");
            logger.log("Card Successfully Updated as expected");
        } else {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }

    @Then("^user hit default card ctrl profile API and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and (\\d+) as expected values$")
    public void userHitDefaultCardCtrlProfileAPIAndValidateWithAndAndAndStatusCodeAsExpectedValues(String paramCustNumber, String paramCardOffers, String paramCardProduct, int statusCode) throws Throwable {
        String Date = null, cardOffer = null, cardProduct = null;
        cardsDetailsMap.clear();
        expCardNumber = "";
        Map<String, String> customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, "");
        customerNumber = customerAndCardNo.get("customerNumber");
        List<Map<String, String>> cardProductDetails;

//        Default CardControl Profile Part
        cardProductDetails = paramCustNumber.equalsIgnoreCase("invalidCus") ?
                common.getCustomerCardOfferProductAndType(paramCustNumber)
                : common.getCustomerCardOfferProductAndType(customerNumber);

        cardOffer = paramCardOffers.equalsIgnoreCase("invalid") ? "Stark" : cardProductDetails.get(0).get("CARDOFFER");
        cardProduct = paramCardProduct.equalsIgnoreCase("invalid") ? "StarCard - No Sub" : cardProductDetails.get(0).get("CARDPRODUCT");

        requestBodyParams = Json.createObjectBuilder()
                .add("customer", Json.createObjectBuilder()
                        .add("customerNo", customerNumber))
                .add("cardOffer", cardOffer)
                .add("cardProduct", cardProduct)
                .build();
        logger.log("Request body -->" + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(defaultProfileEndPoint, requestBodyParams.toString(), authorizationToken);
        logger.log("Response --> " + response.getBody().prettyPrint());
        if (response.statusCode() == 200)
            cardsDetailsMap = common.getCustomerDefaultProf(customerNumber, cardOffer);
    }

    @Then("^user order a card with the card details and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and (\\d+) and (\\d+) as expected values$")
    public void userOrderACardWithTheCardDetailsAndValidateWithAndAndAndAndStatusNumberAsExpectedValues(String paramCustNumber, String paramExpiry, String paramCtrlProfiles, String statusMessage, int statusNumber, int statusCode) throws Throwable {
        String Date = null;
        cardsDetailsMap.clear();
        expCardNumber = "";
        String name = fakerAPI().name().firstName();
        String embossName = name, requestedBy = name, driverName = name, driverId = name;
        String vehiDes = "Range Rover", license = name + 11, contactName = name,
                addressLine = "Mystic falls", suburb = "Oval", postalCode = "6666";

        Map<String, String> customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, "");
        customerNumber = customerAndCardNo.get("customerNumber");
        String defaultProfileEndPoint = PropUtils.getPropValue(inputProp, "defaultProfile");
        String cardOrderEndPoint = PropUtils.getPropValue(inputProp, "cardOrder");

        String country = "", state = "";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);

//        Default CardControl Profile Part
        List<Map<String, String>> cardProductDetails = common.getCustomerCardOfferProductAndType(customerNumber);

        requestBodyParams = Json.createObjectBuilder()
                .add("customer", Json.createObjectBuilder()
                        .add("customerNo", customerNumber))
                .add("cardOffer", cardProductDetails.get(0).get("CARDOFFER"))
                .add("cardProduct", cardProductDetails.get(0).get("CARDPRODUCT"))
                .build();
        System.out.println("cardDetailsParams :" + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(defaultProfileEndPoint, requestBodyParams.toString(), authorizationToken);

        if ("MoreThanProduct".equalsIgnoreCase(paramExpiry)) {
            Date = common.changingDateFormat("MoreThanProduct", "OLS");
            System.out.println("expiryDate --> " + Date);
        } else if ("invalid".equalsIgnoreCase(paramExpiry)) {
            Date = common.changingDateFormat("Past", "OLS");
            System.out.println("currentDate --> " + Date);
        } else {
            Date = common.changingDateFormat("WayFuture", "OLS");
            System.out.println("currentDate --> " + Date);
        }
//        Order Card Part
        JsonObject driverVehicleObject;
        JSONParser parser = new JSONParser();
        JSONObject cardsDefaultProf = (JSONObject) parser.parse(response.asString());
        cardsDefaultProf.remove("expiresOn");
        cardsDefaultProf.remove("card");
        cardsDefaultProf.remove("maxExpiresOn");
        cardsDefaultProf.remove("cardProductList");
        String description = "";
        JSONArray cardCtrlObjArr = (JSONArray) cardsDefaultProf.get("cardControlProfiles");
        JSONObject cardCtrlObj = (JSONObject) cardCtrlObjArr.get(0);

        if (!cardCtrlObj.containsKey("description")) {
            Map<String, String> cardsMap = common.getCustomerDefaultProf(customerNumber, cardProductDetails.get(0).get("CARDOFFER"));
            cardCtrlObj.put("description", cardsMap.get("DESCRIPTION"));
        }
        description = cardCtrlObj.get("description").toString();
        System.out.println("profDescription --> " + description);

        if (paramCtrlProfiles.equalsIgnoreCase("new")) {
            cardCtrlObj.remove("description");
            cardCtrlObjArr.set(0, cardCtrlObj);
            cardsDefaultProf.replace("cardControlProfiles", cardCtrlObjArr);
        }


        cardsDetailsMap.put("profDescription", description);
        cardsDetailsMap.putAll(cardProductDetails.get(0));
        cardsDetailsMap.put("embossingName", embossName);
        cardsDetailsMap.put("requestedBy", requestedBy);
        cardsDetailsMap.put("driverName", driverName);
        cardsDetailsMap.put("driverId", driverId);
        cardsDetailsMap.put("vehicleDescription", vehiDes);
        cardsDetailsMap.put("licensePlate", license);

        cardsDetailsMap.put("state", state);
        cardsDetailsMap.put("country", country);
        cardsDetailsMap.put("contactName", contactName);
        cardsDetailsMap.put("addressLine", addressLine);
        cardsDetailsMap.put("suburb", suburb);
        cardsDetailsMap.put("postalCode", postalCode);


//        Temporarily done
        JsonObject cardObject = Json.createObjectBuilder()
                .add("card", Json.createObjectBuilder()
                        .add("requestedBy", requestedBy).add("embossingName", embossName)
                        .add("sExpiresOn", Date).add("externalRef", "")
                        .add("isPinReq", "Y").add("isSignatureReq", "N")
                        .add("type", cardProductDetails.get(0).get("CARDTYPE")))
                .build();
//        DriverObject for driver card
        JsonObject driverObject = Json.createObjectBuilder()
                .add("driver", Json.createObjectBuilder()
                        .add("driverId", driverId).add("driverName", driverName))
                .build();
//        VehicleObject for vehicle card
        JsonObject vehicleObject = Json.createObjectBuilder()
                .add("vehicle", Json.createObjectBuilder()
                        .add("description", vehiDes).add("licensePlate", license))
                .build();
//            Address
        JsonObject addressObject = Json.createObjectBuilder().
                add("cardContact", Json.createObjectBuilder().add("contactName", contactName).
                        add("streetAddress", Json.createObjectBuilder().add("addressLine", addressLine).
                                add("country", country).add("suburb", suburb).
                                add("postalCode", postalCode).add("state", state))).build();

        driverVehicleObject = Json.createObjectBuilder().addAll(Json.createObjectBuilder(driverObject))
                .addAll(Json.createObjectBuilder(vehicleObject))
                .build();

        requestArrayParams = Json.createArrayBuilder().
                add(0, Json.createObjectBuilder()
                        .add("customer", Json.createObjectBuilder().add("customerNo", customerNumber))
                        .addAll(Json.createObjectBuilder(cardsDefaultProf))
                        .addAll(Json.createObjectBuilder(cardObject))
                        .addAll(Json.createObjectBuilder(driverVehicleObject))
                        .addAll(Json.createObjectBuilder(addressObject))
                )
                .build();
        logger.log("Request path -->" + cardOrderEndPoint);
        logger.log("Request body -->" + requestArrayParams.toString());

        response = postRequestAsBearerAuthWithBodyData(cardOrderEndPoint, requestArrayParams.toString(), authorizationToken);
        if (response.getStatusCode() == 200 && response.getBody().prettyPrint().contains("errors")) {
            logger.log("Response body -->" + response.body().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPIWithArray(statusMessage, statusNumber, logger);
        } else if (response.getStatusCode() == 200) {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            expCardNumber = response.path("[0].cardNo");
        } else {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }

    @Then("^user get the product restriction code by post with card details and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and (\\d+) as expected values$")
    public void userGetTheProductRestrictionCodeByPostWithCardDetailsAndValidateWithAndAndAndAndStatusNumberAsExpectedValues(String paramCustNumber, String paramCardOffers, String paramProducts, String statusMessage, int statusNumber) throws Throwable {
        Map<String, String> customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, "");
        customerNumber = customerAndCardNo.get("customerNumber");
        String productRestrictionEndPoint = PropUtils.getPropValue(inputProp, "productRestriction");
        logger.log("Request Path --> " + productRestrictionEndPoint);
        List<Map<String, String>> cardProductDetails = common.getCustomerCardOfferProductAndType(customerNumber);
        List<String> selectedItems = new ArrayList<>(Arrays.asList(new String[]{"All Petrols", "Workshop & Services"}));

        requestBodyParams = Json.createObjectBuilder()
                .add("customer", Json.createObjectBuilder()
                        .add("customerNo", customerNumber))
                .add("cardOffer", cardProductDetails.get(0).get("CARDOFFER"))
                .add("uiProductRestriction", Json.createObjectBuilder()
                        .add("selectedProducts", Json.createArrayBuilder()
                                .addAll(Json.createArrayBuilder(selectedItems)))
                )
                .build();

        logger.log("Request body -->" + requestBodyParams.toString());
        response = postRequestAsBearerAuthWithBodyData(productRestrictionEndPoint, requestBodyParams.toString(), authorizationToken);

        if (response.getStatusCode() == 200) {
            logger.log("Response --> " + response.getBody().prettyPrint());
//            presenceCheck = common.checkingThePresenceOfContactsInDB(userId,contactName,emailAddress,phoneMobile1,contactTypeTableValues.get("CONTACT_TYPE_OID"));
            presenceCheck = true;
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

    @Then("^user pass \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" as parameters to change card pin$")
    public void userPassAndAndAsParametersToChangeCardPin(String paramCustNumber, String paramCardNumber, String paramPinOffset, String paramConfirmPinOffset) throws Throwable {
        Map<String, String> customerAndCardNo;
        if (paramCustNumber.equalsIgnoreCase("associatedUser") && paramCardNumber.equalsIgnoreCase("associatedCard")) {
            customerAndCardNo = common.getValidCardNumberAndItsCustomer();
            customerNumber = customerAndCardNo.get("CUSTOMERNUMBER");
            cardNumber = customerAndCardNo.get("CARDNUMBER");
        } else {
            customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, paramCardNumber);
            customerNumber = customerAndCardNo.get("customerNumber");
            cardNumber = customerAndCardNo.get("cardNumber");
        }

        requestBodyParams = Json.createObjectBuilder().
                add("customer", Json.createObjectBuilder().add("customerNo", customerNumber)).
                add("card", Json.createObjectBuilder().add("cardNo", cardNumber).
                        add("pinOffset", paramPinOffset).add("confirmPinOffset", paramConfirmPinOffset)).build();
        logger.log("requestBodyParams :" + requestBodyParams.toString());
        String ChangePinEndpoint = PropUtils.getPropValue(inputProp, "ChangePinEndpoint");
        logger.log("Request Path --> " + ChangePinEndpoint);
        response = putRequestAsBearerAuthWithBodyData(ChangePinEndpoint, requestBodyParams.toString(), authorizationToken);
    }

    @Then("^user put the change pin request to update the card pin of a specific card and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPutTheChangePinRequestToUpdateTheCardPinOfASpecificCardAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
//        String ChangePinEndpoint = PropUtils.getPropValue(inputProp,"ChangePinEndpoint");
//        logger.log("Request Path --> "+ChangePinEndpoint);
//        response = putRequestAsBearerAuthWithBodyData(ChangePinEndpoint,requestBodyParams.toString(),authorizationToken);
        Properties properties = PropUtils.getProps(inputDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (response.getStatusCode() == 200) {
                logger.log("Response --> " + response.getBody().prettyPrint());
//            presenceCheck = common.checkingThePresenceOfContactsInDB(userId,contactName,emailAddress,phoneMobile1,contactTypeTableValues.get("CONTACT_TYPE_OID"));
                presenceCheck = true;
                if (presenceCheck) {
                    logger.log("DB Validation passed-Created Contact Details found in DB");
                } else {
                    softFail("DB Validation failed-Created Contact Details not found in DB", logger);
                }
            } else {
                logger.log("Response body is --> " + response.getBody().prettyPrint());
                commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^user pass \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" as parameters to \"([^\"]*)\" card pin$")
    public void userPassAndAndAsParametersToReissueCardPin(String paramCustNumber, String paramCardNumber, String paramCardContact, String endpoint) throws Throwable {
        common.initializeDBEnvironment();
        String country = "", state = "";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);
        String name = fakerAPI().name().firstName();
        String contactName = name, addressLine = "Mystic falls", suburb = "Oval", postalCode = "6666";

        Map<String, String> customerAndCardNo;
        if (paramCustNumber.equalsIgnoreCase("associatedUser") && paramCardNumber.equalsIgnoreCase("associatedCard")) {
            customerAndCardNo = common.getValidCardNumberAndItsCustomer();
            customerNumber = customerAndCardNo.get("CUSTOMERNUMBER");
            cardNumber = customerAndCardNo.get("CARDNUMBER");
        } else {
            customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, paramCardNumber);
            customerNumber = customerAndCardNo.get("customerNumber");
            cardNumber = customerAndCardNo.get("cardNumber");
        }

        System.out.println("cardNumber -->" + cardNumber);
        System.out.println("customerNumber -->" + customerNumber);
        if ("cardContact".equalsIgnoreCase(paramCardContact)) {
            requestBodyParams = Json.createObjectBuilder().
                    add("customer", Json.createObjectBuilder().add("customerNo", customerNumber)).
                    add("card", Json.createObjectBuilder().add("cardNo", cardNumber)).
                    add("cardContact", Json.createObjectBuilder().add("contactName", contactName).
                            add("streetAddress", Json.createObjectBuilder().add("addressLine", addressLine).
                                    add("country", country).add("suburb", suburb).
                                    add("postalCode", postalCode).add("state", state))).build();
        } else {
            requestBodyParams = Json.createObjectBuilder().
                    add("customer", Json.createObjectBuilder().add("customerNo", customerNumber)).
                    add("card", Json.createObjectBuilder().add("cardNo", cardNumber)).build();
        }
        logger.log("requestBodyParams :" + requestBodyParams.toString());
        String pinEndpoint = PropUtils.getPropValue(inputProp, endpoint);
        logger.log("Request Path --> " + pinEndpoint);
        response = putRequestAsBearerAuthWithBodyData(pinEndpoint, requestBodyParams.toString(), authorizationToken);
    }

    @Then("^user put the reissue pin request to update the card pin of a specific card and validate with \"([^\"]*)\" and (\\d+) as expected values$")
    public void userPutTheReissuePinRequestToUpdateTheCardPinOfASpecificCardAndValidateWithAndStatusNumberAsExpectedValues(String statusMessage, int statusNumber) throws Throwable {
//        String ReissuePinEndpoint = PropUtils.getPropValue(inputProp,"ReissuePinEndpoint");
//        logger.log("Request Path --> "+ReissuePinEndpoint);
//        response = putRequestAsBearerAuthWithBodyData(ReissuePinEndpoint,requestBodyParams.toString(),authorizationToken);
        Properties properties = PropUtils.getProps(inputDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (response.getStatusCode() == 200) {
                logger.log("Response --> " + response.getBody().prettyPrint());
//            presenceCheck = common.checkingThePresenceOfContactsInDB(userId,contactName,emailAddress,phoneMobile1,contactTypeTableValues.get("CONTACT_TYPE_OID"));
                presenceCheck = true;
                if (presenceCheck) {
                    logger.log("DB Validation passed-Created Contact Details found in DB");
                } else {
                    softFail("DB Validation failed-Created Contact Details not found in DB", logger);
                }
            } else {
                logger.log("Response body is --> " + response.getBody().prettyPrint());
                commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^user hit the get card API for validation$")
    public void userHitTheGetCardAPIForValidation() throws Throwable {
        logger.log("CardNo used  --> " + expCardNumber);
        requestBodyParams = Json.createObjectBuilder().add("cardNo", expCardNumber).build();
        responseValidation = postRequestAsBearerAuthWithBodyData(cardDetailsEndPoint, requestBodyParams.toString(), authorizationToken);
        logger.log("Get Card Response body is --> " + responseValidation.getBody().prettyPrint());
    }

    @Then("^validate and verify card details from response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyCardDetailsFromResponseWithAndStatusNumber(String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyCardEmbossNameDetails(responseValidation, cardsDetailsMap, expCardNumber, logger);
        }
    }

    @Then("^validate and verify card product offers from response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyCardProductOffersFromResponseWithAndAndStatusNumber(String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyCardProductOfferAndProgram(responseValidation, cardsDetailsMap, logger);
            commonMethods.validateAndVerifyCardProgram(responseValidation, cardsDetailsMap, logger);
        } else {
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
        }
    }


    @Then("^validate and verify card control profile from response with \"([^\"]*)\" and \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyCardControlProfileFromResponseWithAndAndStatusNumber(String paramCtrlProfiles, String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyCardCtrlProfiles(responseValidation, cardsDetailsMap, paramCtrlProfiles, logger);
        }
    }

    @Then("^validate and verify card driver details from response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyDriverDetailsFromResponseWithAndAndStatusNumber(String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyDriverDetails(responseValidation, cardsDetailsMap, logger);
        }
    }

    @Then("^validate and verify card vehicle details from response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyVehicleDetailsFromResponseWithAndAndStatusNumber(String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyVehicleDetails(responseValidation, cardsDetailsMap, logger);
        }
    }

    @Then("^validate and verify card address from response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyCardAddressFromResponseWithAndAndStatusNumber(String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyCardAddress(responseValidation, cardsDetailsMap, logger);
        }
    }

    @Then("^validate and verify Cards API response with \"([^\"]*)\" and (\\d+)$")
    public void validateAndVerifyCardsAPIResponseWithAndStatusNumber(String statusMessage, int statusNumber) throws Throwable {
        if (response.getStatusCode() == 200) {
            commonMethods.validateAndVerifyCardDefautProf(response, cardsDetailsMap, logger);
        } else
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, statusNumber, logger);
    }

    @Then("^user can change the status with customerNumber as param \"([^\"]*)\" and based on changeCardStatus scenarios \"([^\"]*)\"$")
    public void user_can_change_the_status_with_customerNumber_as_param_and_based_on_changeCardStatus_scenarios(
            String customerScenarioType, String cardScenarios) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String cardEndPoint = PropUtils.getPropValue(inputProp, "cardStatus");

        customerNumberFromDB = common.getCustomerNumberFromDB(customerScenarioType);
        System.out.println("Scenario type is " + customerScenarioType);
        String country = "", state = "";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);
        if (cardScenarios.equals("validCardWithAssociatedCusNo")
                || cardScenarios.equals("validCardWithAssociatedCusNoAndFutureDate")) {

            if (reissueActionCid.equals("5901")) {
                System.out.println("Inside of if statement");
                requestBodyParams = Json.createObjectBuilder().add("accountNumber", customerNumberFromDB)
                        .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                        .add("onlineStatus", statusFromResponse).add("sEffectiveAt", commonMethods.getCreatedOnToDate())
                        .build();
                logger.log("Request params for change card status " + requestBodyParams);
                response = putRequestAsBearerAuthWithBodyData(cardEndPoint, requestBodyParams.toString(),
                        PropUtils.getPropValue(inputProp, "AuthorizationToken"));
                System.out.println("Response for change card status " + response.getBody().asString());
            } else if (reissueActionCid.equals("5903")
                    && cardScenarios.equals("validCardWithAssociatedCusNoAndFutureDate")
                    || reissueActionCid.equals("5902")
                    && cardScenarios.equals("validCardWithAssociatedCusNoAndFutureDate")) {
                System.out.println("Inside of else if statement");
                String currentDate = common.getProcessingDateFromMClients();
                String futureDate = common.enterADateValueInStatusBeginDateField("futureDate", currentDate, "olsFormat");
//				System.out.println("Future date is "+futureDate);
//				 DateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
//				 dateFormatter.parse(futureDate);
//				Date newDate;
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(dateFormatter.parse(futureDate));
//				cal.add(Calendar.DAY_OF_MONTH, 1);

                // DateFormat df = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");

                // String incrementedDate = dateFormatter.format(cal.getTime());
                System.out.println("Future date for card status is " + futureDate);
                requestBodyParams = Json.createObjectBuilder().add("accountNumber", customerNumberFromDB)
                        .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                        .add("onlineStatus", statusFromResponse).add("sEffectiveAt", futureDate)
                        .add("reissueFlagValue", reissueActionCid)
                        .add("cardContact",
                                Json.createObjectBuilder().add("contactName", "contactName123").add("streetAddress",
                                        Json.createObjectBuilder().add("addressLine", "addressSt")
                                                .add("country", Json.createObjectBuilder().add("countryCode", country))
                                                .add("suburb", "suburbSt").add("postalCode", "postalCodeSt")
                                                .add("state", Json.createObjectBuilder().add("description", state))))
                        .build();
                logger.log("Request params for change card status " + requestBodyParams);
                response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardEndPoint, requestBodyParams.toString(),
                        PropUtils.getPropValue(inputProp, "AuthorizationToken"), true);
                System.out.println("Response for change card status " + response.getBody().asString());

            } else {
                System.out.println("Inside of else statement");
                requestBodyParams = Json.createObjectBuilder().add("accountNumber", customerNumberFromDB)
                        .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                        .add("onlineStatus", statusFromResponse).add("sEffectiveAt", commonMethods.getCreatedOnToDate())
                        .add("reissueFlagValue", reissueActionCid)
                        .add("cardContact",
                                Json.createObjectBuilder().add("contactName", "contactName123").add("streetAddress",
                                        Json.createObjectBuilder().add("addressLine", "addressSt")
                                                .add("country", Json.createObjectBuilder().add("countryCode", country))
                                                .add("suburb", "suburbSt").add("postalCode", "postalCodeSt")
                                                .add("state", Json.createObjectBuilder().add("description", state))))
                        .build();

                logger.log("Request params for change card status " + requestBodyParams);
                response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardEndPoint, requestBodyParams.toString(),
                        PropUtils.getPropValue(inputProp, "AuthorizationToken"), true);
                System.out.println("Response for change card status " + response.getBody().asString());
            }

        } else if (cardScenarios.equals("validCardWithUnassociatedCusNoAsInvalid")) {
            customerNumberFromDB = "7770049999";

        } else if (cardScenarios.equals("unassociatedCardNoWithCusNoAsInvalid")) {
            List<Map<String, String>> listOfCardNumberWithStatus = null;

            listOfCardNumberWithStatus = common.getCardNumberWithStatusForSpecificCustomer(" ",
                    customerNumberFromDB);
            Map<String, String> unassociatedCardNo = listOfCardNumberWithStatus.get(0);


            cardNumberBasedOnStatus = unassociatedCardNo.get("CARD_NO");

        } else if (cardScenarios.equals("validCardWithInvalidStatus")) {
            statusFromDB = "invalidStatus";

        }
        if (cardScenarios.contains("Invalid")) {
            requestBodyParams = Json.createObjectBuilder().add("accountNumber", customerNumberFromDB)
                    .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                    .add("onlineStatus", statusFromResponse).add("sEffectiveAt", commonMethods.getCreatedOnToDate())
                    .build();
            response = putRequestAsBearerAuthWithBodyData(cardEndPoint, requestBodyParams.toString(),
                    PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            logger.log("Request params for change card status " + requestBodyParams);
            System.out.println("Response for change card status " + response.getBody().asString());

        }

    }

    @Then("^validate the response status number \"([^\"]*)\" and  status message \"([^\"]*)\" for change card status$")
    public void validate_the_response_status_number_and_status_message_for_change_card_status(String statusNumber,
                                                                                              String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> cardnumberWithStatusFromDB;

        if (!PropUtils.getPropValue(PropUtils.getProps(inputDataFile), "testStatus").equalsIgnoreCase("Skipped")
                && statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {

            String responseMessage = response.getBody().asString();

            if (responseMessage.equals("Record updated OK")) {
                cardnumberWithStatusFromDB = common.getUpdatedStatusForSpecificCardNumber(customerNumberFromDB,
                        cardNumberBasedOnStatus);
                if (statusFromResponse.equals(cardnumberWithStatusFromDB.get("OLSSTATUSDESCRIPTION"))) {
                    logger.log("Validation is done for change card status from response and DB");
                } else {
                    logger.log("Validation is not done for change card status");

                }

            } else if (responseMessage.equals("Card Request submitted OK")) {

                boolean isCardRequestSubmitted = common.checkingThePresenceOfCardRequestInDB(customerNumberFromDB,
                        cardNumberBasedOnStatus);
                if (isCardRequestSubmitted) {
                    logger.log("Card Request is submitted in DB");
                } else {
                    logger.log("Card request is not submitted in DB");
                }

            } else if (responseMessage.contains("cardNo")) {
                String cardNo = response.jsonPath().getString("cardNo");

                boolean isNewCardNoGenerated = common
                        .checkingThePresenceOfGeneratedNewCardNumberInDB(customerNumberFromDB, cardNo);
                if (isNewCardNoGenerated) {
                    logger.log("New card number is generated in DB");
                } else {
                    logger.log("New card number is not generated in DB");
                }
            } else {
                logger.log("Record is not updated correctly");
            }
        } else if (response.getStatusCode() == 401 || response.getStatusCode() == 400) {
            System.out.println("Inside of else statement");
            int statusNumberFromResponse = response.path("errors.statusNumber");
            System.out.println("Status Number from response is " + statusNumberFromResponse);
            validateStatusNumber(statusNumberFromResponse, Integer.parseInt(statusNumber), logger,
                    "Expected status number from response is " + statusNumberFromResponse);
            String statusMessageFromResponse = response.path("errors.statusMessage");
            System.out.println("Status Message from response is " + statusMessageFromResponse);

            validateResponseMessage(statusMessageFromResponse, statusMessage, logger,
                    "Expected status message from response is " + statusMessageFromResponse);

        } else {
            logger.log("Data is not available in db, Please order the card and retest");
        }

    }


    @Then("^user post the request for list of cards based on customer number$")
    public void user_post_the_request_for_list_of_cards_based_on_customer_number() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String cardsEndPoint = PropUtils.getPropValue(inputProp, "GET_CARDS");
        String searchCardEndPoint = customerEndPoint + cardsEndPoint;
        requestBodyParams = Json.createObjectBuilder().build();
        response = postRequestAsBearerAuthWithBodyData(searchCardEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for search Cards " + response.jsonPath().prettyPrint());


    }

    @Then("^validate the response for search cards and field values with statusNumber as \"([^\"]*)\" and statusMesage as \"([^\"]*)\" as expected$")
    public void validate_the_response_for_search_cards_and_field_values_with_statusNumber_as_and_statusMesage_as_as_expected(String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            Map<String, String> cardActions = new HashMap<String, String>();


            Random rand = new Random();
            JSONParser parser = new JSONParser();
            JSONObject listOfcardObject = (JSONObject) parser.parse(response.asString());
            JSONArray cardArrayList = (JSONArray) listOfcardObject.get("cardsList");

            int actualCount = rand.nextInt(cardArrayList.size());
            System.out.println("actual count for array list is " + actualCount);
            JSONObject specificCardObject = (JSONObject) cardArrayList.get(actualCount);
            System.out.println("Specific card object is " + specificCardObject);
            String isvalidCardFromResponse = specificCardObject.get("isValid").toString();
            System.out.println("Is valid from response " + isvalidCardFromResponse);
            String isStausChangeAllowed = specificCardObject.get("isStatusChangeAllowed").toString();
            System.out.println("Is status change allowed from response " + isStausChangeAllowed);
            String isEditCardDetails = specificCardObject.get("isEditCardDetails").toString();
            System.out.println("Is editcard details from response " + isEditCardDetails);
            String isChangeCardStatus = specificCardObject.get("isChangeCardStatus").toString();
            System.out.println("Is change card status from response " + isChangeCardStatus);
            String cardNo = specificCardObject.get("cardNo").toString();
            cardActions = common.getCardActions(cardNo);
            if (isvalidCardFromResponse.equals(cardActions.get("IS_VALID")) && isStausChangeAllowed.equals(cardActions.get("IS_STATUS_CHANGE"))) {
                logger.log("Card is valid status and able to perform actions for respective card");

            } else {
                logger.log("Card is invalid status not able to perform actions for respcetive card");
            }

        }
    }

    @When("^user able to get the valid changes status for bulk status change lookup API based on card status \"([^\"]*)\"$")
    public void user_able_to_get_the_valid_changes_status_for_bulk_status_change_lookup_API_based_on_card_status(String cardStatus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String validChangeEndPoint = PropUtils.getPropValue(inputProp, "validChange");
        if (cardStatus.equals("validCardWithActiveStatus")) {
            requestBodyParams = Json.createObjectBuilder().add("statuses",
                    Json.createObjectBuilder().add("Active", "Active")).build();
        } else if (cardStatus.equals("validCardWithStolenAndDamagedStatus")) {
            requestBodyParams = Json.createObjectBuilder().add("statuses",
                    Json.createObjectBuilder().add("Card Stolen", "Stolen").add("Card Damaged", "Damaged")).build();
        }
//        else if (cardStatus.equals("validCardWithRequestedNotIssueStatus")) {
//            requestBodyParams = Json.createObjectBuilder().add("statuses",
//                    Json.createObjectBuilder().add("No Transactions", "Ready for use").add("Requested - Not Issued", "Active")).build();
//        }
        else if (cardStatus.equals("validCardWithLostAndActiveStatus")) {
            requestBodyParams = Json.createObjectBuilder().add("statuses",
                    Json.createObjectBuilder().add("Active", "Active").add("Card Lost", "Card Lost")).build();
        } else if (cardStatus.equals("validCardWithTemporaryBlockStatus")) {
            requestBodyParams = Json.createObjectBuilder().add("statuses",
                    Json.createObjectBuilder().add("Temporary Lock", "Temporary Lock")
                            .add("Active", "Active")).build();
        }

        logger.log("Request params for bulk status lookup is " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(validChangeEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));

    }

    @SuppressWarnings("unchecked")
    @Then("^user able to change the status for bulk cards with param as card number and customer number based on bulk card status lookup API \"([^\"]*)\"$")
    public void user_able_to_change_the_status_for_bulk_cards_with_param_as_card_number_and_customer_number_based_on_bulk_card_status_lookup_API(
            String bulkCardsScenarioType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String customerNumber = " ";
        JSONObject firstObj = new JSONObject();
        JSONObject secondObj = new JSONObject();
        JSONArray arrayFormatForCardList = new JSONArray();

        List<Map<String, String>> listOfCardNumberWithStatus = null;
        Map<String, String> firstRowData = null;
        Map<String, String> secondRowData = null;

        String firstCardNo = null, secondCardNo = null;
        if (response.getStatusCode() == 200) {
            System.out.println("Response for status is " + response.jsonPath().prettyPrint());

            org.json.JSONObject specificStatusObject = commonMethods.getJSONObjectInsideOfJSONArray();
            logger.log("Specific response for bulk card lookup API " + specificStatusObject);
            System.out.println("Specific status object from reponse is " + specificStatusObject);
            reissueActionCid = specificStatusObject.get("reissueActionCid").toString();
            System.out.println("ReissueAction cid as " + reissueActionCid);
            statusFromResponse = specificStatusObject.get("status").toString();
            System.out.println("Status from response " + statusFromResponse);

            customerNumber = common.getCustomerNumberFromDB("associatedUser");
            //customerNumber = "0700000640";

            if (bulkCardsScenarioType.equals("associatedCardNumberAndCusNo")) {
                listOfCardNumberWithStatus = common.getCardNumberWithStatusForSpecificCustomer("associatedCard",
                        customerNumber);
                System.out.println("size of cards from db is " + listOfCardNumberWithStatus.size());


                if (listOfCardNumberWithStatus.isEmpty()) {
                    logger.log("No data found");

                } else if (listOfCardNumberWithStatus.size() == 1) {
                    firstRowData = listOfCardNumberWithStatus.get(0);
                    firstCardNo = firstRowData.get("CARD_NO");
                    firstObj.put("cardNo", firstCardNo);
                    arrayFormatForCardList.add(firstObj);
                } else {
                    firstRowData = listOfCardNumberWithStatus.get(0);
                    firstCardNo = firstRowData.get("CARD_NO");
                    System.out.println("First card no is " + firstCardNo);
                    secondRowData = listOfCardNumberWithStatus.get(1);
                    secondCardNo = secondRowData.get("CARD_NO");
                    firstObj.put("cardNo", firstCardNo);
                    secondObj.put("cardNo", secondCardNo);
                    arrayFormatForCardList.add(firstObj);
                    arrayFormatForCardList.add(secondObj);


                }

            } else if (bulkCardsScenarioType.equals("unassociatedCardNumberAndCusNo")) {

                listOfCardNumberWithStatus = common.getCardNumberWithStatusForSpecificCustomer("", customerNumber);

                if (listOfCardNumberWithStatus.isEmpty()) {
                    logger.log("No data found");

                } else if (listOfCardNumberWithStatus.size() == 1) {
                    firstRowData = listOfCardNumberWithStatus.get(0);
                    firstCardNo = firstRowData.get("CARD_NO");
                    firstObj.put("cardNo", firstCardNo);
                    arrayFormatForCardList.add(firstObj);
                } else {
                    firstRowData = listOfCardNumberWithStatus.get(0);
                    firstCardNo = firstRowData.get("CARD_NO");
                    System.out.println("First card no is " + firstCardNo);
                    secondRowData = listOfCardNumberWithStatus.get(1);
                    secondCardNo = secondRowData.get("CARD_NO");
                    firstObj.put("cardNo", firstCardNo);
                    secondObj.put("cardNo", secondCardNo);
                    arrayFormatForCardList.add(firstObj);
                    arrayFormatForCardList.add(secondObj);


                }
            } else if (bulkCardsScenarioType.equals("associatedCardNumberAndUnassociatedCusNo")) {
                customerNumber = "700000006";
                firstObj.put("cardNo", " ");
                arrayFormatForCardList.add(firstObj);

            }

            requestParams.put("customerNumber", customerNumber);
            requestParams.put("reissueActionCID", reissueActionCid);
            requestParams.put("newStatus", commonMethods.getDescriptionAsJSONObjectFormat(statusFromResponse));
            requestParams.put("cardList", arrayFormatForCardList);
            logger.log("Request params" + requestParams);
            response = putRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "bulkCardStatusEndPoint"),
                    requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        } else if (response.getStatusCode() == 204) {
            logger.log("For this respective card status does not have any possible combinations");

        }

    }

    @Then("^validate the expected response for bulk cards and error response statusNumber \"([^\"]*)\" and statusMessage \"([^\"]*)\"$")
    public void validate_the_expected_response_for_bulk_cards_and_error_response_statusNumber_and_statusMessage(String statusNumber, String statusMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Response code is " + response.getStatusCode());

        if (response.getStatusCode() == 200 && statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            String responseMessage = response.getBody().asString();
            if (responseMessage.equals("Record updated OK")) {
                System.out.println("Inside of if");
                logger.log("Validated the response message");

            } else {
                logger.log("Not getting the expected response message");

            }
        } else if (response.getStatusCode() == 401 || response.getStatusCode() == 400) {
            System.out.println("Inside of else statement");
            int statusNumberFromResponse = response.path("errors.statusNumber");
            System.out.println("Status Number from response is " + statusNumberFromResponse);
            validateStatusNumber(statusNumberFromResponse, Integer.parseInt(statusNumber), logger,
                    "Expected status number from response is " + statusNumberFromResponse);
            String statusMessageFromResponse = response.path("errors.statusMessage");
            System.out.println("Status Message from response is " + statusMessageFromResponse);

            validateResponseMessage(statusMessageFromResponse, statusMessage, logger,
                    "Expected status message from response is " + statusMessageFromResponse);

        } else {
            logger.log("No possible combinations for invalid card status");
        }

    }

    @When("^user able to get the valid changes based on cards current status$")
    public void user_able_to_get_the_valid_changes_based_on_cards_current_status() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        List<Map<String, String>> listOfCardNumberWithStatus = null;
        Map<String, String> specificCardNumberWithStatusfromDB = null;
        Random random = new Random();
        String cardStatusEndPoint = PropUtils.getPropValue(inputProp, "validChange");
        listOfCardNumberWithStatus = common.getCardNumberWithStatusForSpecificCustomer("associatedCard", common.getCustomerNumberFromDB("associatedUser"));
        if (listOfCardNumberWithStatus.isEmpty()) {
            logger.log("No data found");
        } else {
            specificCardNumberWithStatusfromDB = listOfCardNumberWithStatus.get(0);
            statusFromDB = specificCardNumberWithStatusfromDB.get("DESCRIPTION");
            System.out.println("Status from dB is " + statusFromDB);
            String onlineStatus = specificCardNumberWithStatusfromDB.get("OLSSTATUSDESCRIPTION");
            System.out.println("Online status from db is " + onlineStatus);
            cardNumberBasedOnStatus = specificCardNumberWithStatusfromDB.get("CARD_NO");
            System.out.println("Card number from db is " + cardNumberBasedOnStatus);
            response = getRequestAsBearerAuthWithMultipleQueryParams(cardStatusEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "status", statusFromDB, "onlineStatus", onlineStatus);
            org.json.JSONObject specificStatusObject = commonMethods.getJSONObjectInsideOfJSONArray();
            logger.log("Specific response for change card status is " + specificStatusObject);
            reissueActionCid = specificStatusObject.get("reissueActionCid").toString();
            System.out.println("ReissueAction cid as " + reissueActionCid);
            statusFromResponse = specificStatusObject.get("status").toString();
            System.out.println("Status from response " + statusFromResponse);
        }


    }

    @Then("^user can able to change the status for generated new card number$")
    public void userCanAbleToChangeTheStatusForGeneratedNewCardNumber() throws Throwable {
        List<Map<String, String>> listOfCardOidsWithCardNo = null;

        cusNo = common.getCustomerNumberFromDB("associatedUser");
        listOfCardOidsWithCardNo = common.getListOfCardOids(cusNo);
        System.out.println("List of cards requested not issue status size is " + listOfCardOidsWithCardNo.size());
        Map<String, String> cardValues = null;
        cardValues = listOfCardOidsWithCardNo.get(0);
        String firstCardOid = cardValues.get("CARD_OID");
        firstCardNumberfromDB = cardValues.get("CARD_NO");
        common.updateCardStatusFromRequestedStatusToNoTransaction(firstCardOid);
        common.updateCardStatusAsNotransactionInCardStatusLogsTable(firstCardOid);
        logger.log("Status updated in both cards and card status logs table in DB for first card number");
        cardValues = listOfCardOidsWithCardNo.get(1);
        String secondCardOid = cardValues.get("CARD_OID");
        secondCardNumberFromDB = cardValues.get("CARD_NO");
        common.updateCardStatusFromRequestedStatusToNoTransaction(secondCardOid);
        common.updateCardStatusAsNotransactionInCardStatusLogsTable(secondCardOid);
        logger.log("Status updated in both cards and card status logs table in DB for second card number");
    }

    @When("^user able to get the list of cards based on possible combination \"([^\"]*)\"$")
    public void userAbleToGetTheListOfCardsBasedOnPossibleCombination(String possibleCombination) throws Throwable {
        //       ITestContext ctx ;
        //      System.out.println("Test name is "+ctx.getName());
        String cardSearchEndPoint = PropUtils.getPropValue(inputProp, "cardSearch");
        logger.log("Request path for card search API" + cardSearchEndPoint);
        List<Map<String, String>> listOfCardDetails = null;
        Map<String, String> specificRowForCardSearch = null;
        Map<String, String> specificRowForSecondCardDetails = null;
        listOfCardDetails = common.getListOfCardsForCardSearch();
        int actualCount = rand.nextInt(listOfCardDetails.size());
        System.out.println("Actual count " + actualCount);
        specificRowForCardSearch = listOfCardDetails.get(actualCount);
        int secondData = rand.nextInt(listOfCardDetails.size());
        System.out.println("Actual count for second record is " + secondData);
        specificRowForSecondCardDetails = listOfCardDetails.get(secondData);

        cusNoFromDB = specificRowForCardSearch.get("CUSTOMER_NO");
        arrayFormatForCusNo.add(cusNoFromDB);
        cardNoFromDB = specificRowForCardSearch.get("CARD_NO");
        costCentreFromDB = specificRowForCardSearch.get("CUSTOMER_COST_CENTRE_CODE");
        arrayFormatForCostCentres.add(costCentreFromDB);
        driverNameFromDB = specificRowForCardSearch.get("DRIVER_NAME");
        licensePlateFromDB = specificRowForCardSearch.get("LICENSE_PLATE");
        countryFromDB = specificRowForCardSearch.get("COUNTRY_DESC");
        cardStatusFromDB = specificRowForCardSearch.get("STATUS_DESCRIPTION");
        arrayFormatForCountries.add(countryFromDB);
        arrayFormatForStatus.add(cardStatusFromDB);
        if (possibleCombination.equals("emptyPayload")) {
            System.out.println("Inside of Empty payload");

        } else if (possibleCombination.equals("validInputs")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("cardNumber", cardNoFromDB);
            requestParams.put("costCentres", arrayFormatForCostCentres);
            requestParams.put("driverName", driverNameFromDB);
            requestParams.put("licensePlate", licensePlateFromDB);
            requestParams.put("countries", arrayFormatForCountries);
            requestParams.put("status", arrayFormatForStatus);
        } else if (possibleCombination.contains("CardNo")) {
            if (possibleCombination.equals("fullCardNo")) {
                requestParams.put("cardNumber", cardNoFromDB);
            } else {
                //    String cardNoFromDB = specificRowForCardSearch.get("CARD_NO");
                requestParams.put("cardNumber", cardNoFromDB.substring(11));
            }
        } else if (possibleCombination.equals("costCentreFilterAsSingleInput")) {
            requestParams.put("costCentres", arrayFormatForCostCentres);
        } else if (possibleCombination.contains("driverName")) {
            if (possibleCombination.equals("driverNameSearch")) {
                requestParams.put("driverName", driverNameFromDB);
            } else {
                //     String driverNameFromDB = specificRowForCardSearch.get("DRIVER_NAME");
                requestParams.put("driverName", driverNameFromDB.substring(1, 2));
            }

        } else if (possibleCombination.contains("licensePlate")) {
            if (possibleCombination.equals("licensePlateSearch")) {
                requestParams.put("licensePlate", licensePlateFromDB);
            } else {
                //     String licensePlateFromDB = specificRowForCardSearch.get("LICENSE_PLATE");
                requestParams.put("licensePlate", licensePlateFromDB.substring(1));
            }

        } else if (possibleCombination.equals("countriesFilter")) {
            requestParams.put("countries", arrayFormatForCountries);
        } else if (possibleCombination.equals("statusAsSingleInput")) {
            requestParams.put("status", arrayFormatForStatus);
        } else if (possibleCombination.equals("multipleInputs")) {
            arrayFormatForCusNo.add(specificRowForSecondCardDetails.get("CUSTOMER_NO"));
            arrayFormatForCostCentres.add(specificRowForSecondCardDetails.get("CUSTOMER_COST_CENTRE_CODE"));
            arrayFormatForCountries.add(specificRowForSecondCardDetails.get("COUNTRY_DESC"));
            arrayFormatForStatus.add(specificRowForSecondCardDetails.get("STATUS_DESCRIPTION"));
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("costCentres", arrayFormatForCostCentres);
            requestParams.put("countries", arrayFormatForCountries);
            requestParams.put("status", arrayFormatForStatus);
        }
        logger.log("Request params for cards search CSR user" + requestParams);
        response = postRequestAsBearerAuthWithBodyData(cardSearchEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for card search " + response.jsonPath().prettyPrint());
    }

    @Then("^Verify the card search default view response and validate the expected details in DB with \"([^\"]*)\" and \"([^\"]*)\" based on possible combination as \"([^\"]*)\"$")
    public void verifyTheCardSearchDefaultViewResponseAndValidateTheExpectedDetailsInDBWithAndBasedOnPossibleCombinationAs(String statusNumber, String statusMessage, String filterSearch) throws Throwable {
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            JSONParser parser = new JSONParser();
            JSONObject searchResponse = (JSONObject) parser.parse(response.asString());
            if (searchResponse.containsKey("cardsList")) {
                JSONArray cardsList = (JSONArray) searchResponse.get("cardsList");
                System.out.println("List of Cards from response is " + cardsList.size());
                int actualCount = rand.nextInt(cardsList.size());
                JSONObject specificCardObjectFromResponse = (JSONObject) cardsList.get(actualCount);
                if (filterSearch.equals("validInputs")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("customerNumber").toString(), arrayFormatForCusNo, "Customer Number", logger);
                    commonMethods.responseValidationsCheckingEquals(specificCardObjectFromResponse.get("cardNo").toString(), cardNoFromDB, "Card Number", logger);
                    commonMethods.responseValidationsCheckingContains(specificCardObjectFromResponse.get("driverName").toString(), driverNameFromDB, "Driver Name", logger);
                    commonMethods.responseValidationsCheckingContains(specificCardObjectFromResponse.get("licensePlate").toString(), licensePlateFromDB, "License Plate", logger);
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("costCentreCode").toString(), arrayFormatForCostCentres, "Customer Number", logger);
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("issuingCountry").toString(), arrayFormatForCountries, "Customer Number", logger);
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("status").toString(), arrayFormatForStatus, "Customer Number", logger);
                } else if (filterSearch.contains("CardNo")) {
                    commonMethods.responseValidationsCheckingEquals(specificCardObjectFromResponse.get("cardNo").toString(), cardNoFromDB, "Card Number", logger);
                } else if (filterSearch.equals("costCentreFilterAsSingleInput")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("costCentreCode").toString(), arrayFormatForCostCentres, "Customer Number", logger);
                } else if (filterSearch.contains("driverName")) {
                    commonMethods.responseValidationsCheckingContains(specificCardObjectFromResponse.get("driverName").toString(), driverNameFromDB, "Driver Name", logger);
                } else if (filterSearch.contains("licensePlate")) {
                    commonMethods.responseValidationsCheckingContains(specificCardObjectFromResponse.get("licensePlate").toString(), licensePlateFromDB, "License Plate", logger);
                } else if (filterSearch.equals("countriesFilter")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("issuingCountry").toString(), arrayFormatForCountries, "Customer Number", logger);
                } else if (filterSearch.equals("statusAsSingleInput")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("status").toString(), arrayFormatForStatus, "Customer Number", logger);
                } else if (filterSearch.equals("multipleInputs")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("customerNumber").toString(), arrayFormatForCusNo, "Customer Number", logger);
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("costCentreCode").toString(), arrayFormatForCostCentres, "Customer Number", logger);
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("issuingCountry").toString(), arrayFormatForCountries, "Customer Number", logger);
                    commonMethods.responseValidationsCheckingEqualsList(specificCardObjectFromResponse.get("status").toString(), arrayFormatForStatus, "Customer Number", logger);
                } else if (filterSearch.equals("emptyPayload")) {
                    commonMethods.responseValidationsCheckingEquals(specificCardObjectFromResponse.get("cardNo").toString(), cardNoFromDB, "Card Number", logger);
                }
            }
        }
    }

    @When("^user gets the list of card control profiles based on customer number based on possible scenarios \"([^\"]*)\"$")
    public void userGetsTheListOfCardControlProfilesBasedOnCustomerNumberBasedOnPossibleScenarios(String possibleScenarios) throws Throwable {
        String cardProfilesEndPoint = PropUtils.getPropValue(inputProp, "cardProfiles");
        customerNoFromDB = commonMethods.getCustomerNoForCSRLogin();
        List<Map<String, String>> cardOfferAndCardProduct;
        cardOfferAndCardProduct = common.getCustomerCardOfferProductAndType(customerNoFromDB);
        int actualCount = rand.nextInt(cardOfferAndCardProduct.size());
        cardOfferFromDB = cardOfferAndCardProduct.get(actualCount).get("CARDOFFER");
        System.out.println("Card offer from dB " + cardOfferFromDB);
        cardProductFromDB = cardOfferAndCardProduct.get(actualCount).get("CARDPRODUCT");
        System.out.println("Card product from dB " + cardProductFromDB);
        //Passing the inputs
        requestParams.put("customerNumber", customerNoFromDB);
        if (possibleScenarios.equals("validCusNoWithCardOfferAndCardProduct")) {
            requestParams.put("cardOffer", cardOfferFromDB);
            requestParams.put("cardProduct", cardProductFromDB);
        } else if (possibleScenarios.equals("validCusNoWithCardOffer")) {
            cardProductFromDB = null;
            requestParams.put("cardOffer", cardOfferFromDB);
        } else if (possibleScenarios.equals("validCusNoWithUnassociatedCardOffer")) {
            requestParams.put("cardOffer", "StarCard");
        } else if (possibleScenarios.equals("validCusNoWithCardOfferAndUnassociatedCardProduct")) {
            requestParams.put("cardOffer", cardOfferFromDB);
            requestParams.put("cardProduct", "product");
        } else if (possibleScenarios.equals("invalidCusNo")) {
            requestParams.put("customerNumber", "1234");
        }
        logger.log("Request path is " + cardProfilesEndPoint);
        logger.log("Request body -->" + requestParams.toString());
        response = postRequestAsBearerAuthWithBodyData(cardProfilesEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        //     logger.log("Response --> " + response.getBody().prettyPrint());
        int statuscode = response.getStatusCode();
        System.out.println("statuscode :"+statuscode);
        if (response.getStatusCode() == 200) {
            JSONArray cardControlListResponse = (JSONArray) parser.parse(response.asString());
            int cardListCount = rand.nextInt(cardControlListResponse.size());
            System.out.println("Card control profile list is" + cardListCount);
            specificCardObjectFromResponse = (JSONObject) cardControlListResponse.get(cardListCount);
            cardControlProfileOidValue = specificCardObjectFromResponse.get("cardControlProfileOid").toString();
            System.out.println("Card control profile oid is " + cardControlProfileOidValue);
        } else if (response.getStatusCode() == 204) {
            logger.log("Not getting the expected response");
        } else if (response.getStatusCode() == 500) {
            logger.log("Not getting the expected response");
        }

    }



    @Then("^user able to update the status for multiple cards with param as card number based on bulk card status lookup API \"([^\"]*)\"$")
    public void userAbleToUpdateTheStatusForMultipleCardsWithParamAsCardNumberBasedOnBulkCardStatusLookupAPI(String bulkCardsScenarioType) throws Throwable {
        JSONObject firstObj = new JSONObject();
        JSONObject secondObj = new JSONObject();
        JSONArray arrayFormatForCardList = new JSONArray();
        List<Map<String, String>> listOfCardNumberWithStatus = null;
        Map<String, String> firstRowData = null;
        Map<String, String> secondRowData = null;
        String firstCardNo = null, secondCardNo = null;
        if (response.getStatusCode() == 200) {
            System.out.println("Response for status is " + response.jsonPath().prettyPrint());
            org.json.JSONObject specificStatusObject = commonMethods.getJSONObjectInsideOfJSONArray();
            logger.log("Specific response for bulk card status lookup API is " + specificStatusObject);
            System.out.println("Specific status object from response is " + specificStatusObject);
            reissueActionCid = specificStatusObject.get("reissueActionCid").toString();
            System.out.println("ReissueAction cid as " + reissueActionCid);
            statusFromResponse = specificStatusObject.get("ifcsstatus").toString();
            System.out.println("CSR Status from response is " + statusFromResponse);
            if (bulkCardsScenarioType.equals("associatedCardNumberAndCusNo")) {
                listOfCardNumberWithStatus = common.getListOfCardsWithValidStatusForCSRLogin();
                System.out.println("size of cards from db is " + listOfCardNumberWithStatus.size());
                if (listOfCardNumberWithStatus.isEmpty()) {
                    PropUtils.setProps("testStatus", "Skipped", inputDataFilePath);
                    PropUtils.setProps("skipReason", "Cards not available for card status", inputDataFilePath);
                    logger.log("Cards not available for card status");

                } else if (listOfCardNumberWithStatus.size() == 1) {
                    firstRowData = listOfCardNumberWithStatus.get(0);
                    firstCardNo = firstRowData.get("CARD_NO");
                    firstObj.put("cardNo", firstCardNo);
                    arrayFormatForCardList.add(firstObj);
                } else {
                    firstRowData = listOfCardNumberWithStatus.get(0);
                    firstCardNo = firstRowData.get("CARD_NO");
                    System.out.println("First card no is " + firstCardNo);
                    secondRowData = listOfCardNumberWithStatus.get(1);
                    secondCardNo = secondRowData.get("CARD_NO");
                    firstObj.put("cardNo", firstCardNo);
                    secondObj.put("cardNo", secondCardNo);
                    arrayFormatForCardList.add(firstObj);
                    arrayFormatForCardList.add(secondObj);
                }

            } else {
                System.out.println("Inside of unassociated card no");
                firstObj.put("cardNo", "1234557");
                arrayFormatForCardList.add(firstObj);
                System.out.println("Array format for card status " + arrayFormatForCardList);
            }
            if (!PropUtils.getPropValue(PropUtils.getProps(inputDataFile), "testStatus").equalsIgnoreCase("Skipped")
                    && bulkCardsScenarioType.equals("associatedCardNumberAndCusNo")) {
                requestParams.put("reissueActionCID", reissueActionCid);
                requestParams.put("status", commonMethods.getDescriptionAsJSONObjectFormat(statusFromResponse));
                requestParams.put("cardList", arrayFormatForCardList);
                response = putRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "bulkCardStatusEndPoint"),
                        requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            } else if (bulkCardsScenarioType.equals("unassociatedCardNumberAndCusNo")) {
                requestParams.put("reissueActionCID", reissueActionCid);
                requestParams.put("status", commonMethods.getDescriptionAsJSONObjectFormat(statusFromResponse));
                requestParams.put("cardList", arrayFormatForCardList);
                response = putRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "bulkCardStatusEndPoint"),
                        requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            } else {
                logger.log("No data found in DB ");
            }
        } else if (response.getStatusCode() == 204) {
            logger.log("For this respective card status does not have any possible combinations");
        }
    }

    @When("^user gets the list of card status based on valid changes API \"([^\"]*)\"$")
    public void userGetsTheListOfCardStatusBasedOnValidChangesAPI(String statusScenarioType) throws Throwable {
        List<Map<String, String>> listOfCardNumberWithStatus = null;
        Map<String, String> specificCardNumberWithStatusFromDB = null;
        String cardStatusEndPoint = PropUtils.getPropValue(inputProp, "validChange");
        if (statusScenarioType.equals("validStatus")) {
            listOfCardNumberWithStatus = common.getListOfCardsWithValidStatusForCSRLogin();
            if (!listOfCardNumberWithStatus.isEmpty()) {
                specificCardNumberWithStatusFromDB = listOfCardNumberWithStatus.get(0);
                statusFromDB = specificCardNumberWithStatusFromDB.get("STATUS_DESCRIPTION");
                System.out.println("IFCS Status from dB is " + statusFromDB);
                String onlineStatus = specificCardNumberWithStatusFromDB.get("OLS_STATUS_DESCRIPTION");
                System.out.println("Online status from db is " + onlineStatus);
                cardNumberBasedOnStatus = specificCardNumberWithStatusFromDB.get("CARD_NO");
                System.out.println("Card number from db is " + cardNumberBasedOnStatus);
                response = getRequestAsBearerAuthWithMultipleQueryParams(cardStatusEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "status", statusFromDB, "onlineStatus", onlineStatus);
                org.json.JSONObject specificStatusObject = commonMethods.getJSONObjectInsideOfJSONArray();
                logger.log("Specific response for change card status is " + specificStatusObject);
                reissueActionCid = specificStatusObject.get("reissueActionCid").toString();
                System.out.println("ReissueAction cid as " + reissueActionCid);
                statusFromResponse = specificStatusObject.get("ifcsstatus").toString();
                System.out.println("Status from response " + statusFromResponse);
            } else {

                PropUtils.setProps("testStatus", "Skipped", inputDataFilePath);
                PropUtils.setProps("skipReason", "Cards not available for card status", inputDataFilePath);
                logger.log("Cards are not available for card status");
            }
        } else if (statusScenarioType.equals("invalidStatus")) {
            response = getRequestAsBearerAuthWithMultipleQueryParams(cardStatusEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "status", "card Lost", "onlineStatus", "Lost");
        }
    }


    @Then("^verify the card control profiles response message from response and DB based on \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyTheCardControlProfilesResponseMessageFromResponseAndDBBasedOnAnd(String statusNumber, String statusMessage) throws Throwable {
        if (response.getStatusCode() == 200 || (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null)))) {
            System.out.println("Validate if ");
            List<Map<String, String>> listOfCardControlProfiles = common.getListOfCardControlProfilesFromDB(customerNoFromDB, cardOfferFromDB, cardProductFromDB);
            Map<String, String> specificCardDetailsFromDB = null;
            System.out.println("List of card control profiles from DB is " + listOfCardControlProfiles.size());
            JSONArray cardControlListResponse = (JSONArray) parser.parse(response.asString());
            System.out.println("List of card control profiles from response is " + cardControlListResponse.size());
            for (int i = 0; i < cardControlListResponse.size(); i++) {
                specificCardObjectFromResponse = (JSONObject) cardControlListResponse.get(i);
                System.out.println("Specific response is " + specificCardObjectFromResponse);
                for (int j = 0; j < listOfCardControlProfiles.size(); j++) {
                    System.out.println("Inside of nested for loop");
                    specificCardDetailsFromDB = listOfCardControlProfiles.get(j);
                    System.out.println("Specific details from DB is " + specificCardDetailsFromDB);
                    if (specificCardObjectFromResponse.get("profileCategory").toString().equals(specificCardDetailsFromDB.get("PROFILE_CATEGORY").toString())
                            && specificCardObjectFromResponse.get("profileDescription").toString().equals(specificCardDetailsFromDB.get("DESCRIPTION").toString())
                            && specificCardObjectFromResponse.get("cardOffer").toString().equals(specificCardDetailsFromDB.get("CARD_OFFER").toString())) {
                        System.out.println("Inside of if loop");
                        logger.log("Validation passed- Profile category is " + specificCardObjectFromResponse.get("profileCategory").toString() + ", description is " + specificCardObjectFromResponse.get("profileDescription") + "" +
                                " and card offer is " + specificCardObjectFromResponse.get("cardOffer").toString() + " fields are matched with Expected: ");
                        break;
                    }

                    //      commonMethods.responseValidationsCheckingEquals(profileCategoryFromResponse, specificCardDetailsFromDB.get("PROFILE_CATEGORY").toString(), "Profile category", logger);
                    //     commonMethods.responseValidationsCheckingEquals(specificCardObjectFromResponse.get("profileDescription").toString(), specificCardDetailsFromDB.get("DESCRIPTION").toString(), "Profile description", logger);
                    //     commonMethods.responseValidationsCheckingEquals(specificCardObjectFromResponse.get("cardOffer").toString(), specificCardDetailsFromDB.get("CARD_OFFER").toString(), "Card offer", logger);
                    //    break;
                }

            }


        } else {
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, Integer.parseInt(statusNumber), logger);
        }
    }

    @When("^User can validates the private profile is exist or not based on possible scenarios \"([^\"]*)\"$")
    public void userCanValidatesThePrivateProfileIsExistOrNotBasedOnPossibleScenarios(String possibleScenarios) throws Throwable {
        String validatePrivateProfileEndPoint = PropUtils.getPropValue(inputProp, "validateProfileEndPoint");
        customerNoFromDB = commonMethods.getCustomerNoForCSRLogin();
        Map<String, String> cardControlProfileDetails = common.getCreatedCardControlProfileDetails(customerNoFromDB);
        String profileDescription = cardControlProfileDetails.get("DESCRIPTION");
        List<Map<String, String>> cardOfferAndCardProduct;
        cardOfferAndCardProduct = common.getCustomerCardOfferProductAndType(customerNoFromDB);
        int actualCount = rand.nextInt(cardOfferAndCardProduct.size());
        cardOfferFromDB = cardOfferAndCardProduct.get(actualCount).get("CARDOFFER");
        System.out.println("Card offer from dB " + cardOfferFromDB);
        cardProductFromDB = cardOfferAndCardProduct.get(actualCount).get("CARDPRODUCT");
        System.out.println("Card product from dB " + cardProductFromDB);
        //Passing the inputs
        requestParams.put("customerNumber", customerNoFromDB);
        if (possibleScenarios.equals("validCusNoWithCardOfferAndCardProduct")) {
            requestParams.put("cardOffer", cardOfferFromDB);
            requestParams.put("cardProduct", cardProductFromDB);
        } else if (possibleScenarios.equals("validCusNoWithCardOffer")) {
            cardProductFromDB = null;
            requestParams.put("cardOffer", cardOfferFromDB);
        } else if (possibleScenarios.equals("validCusNoWithCardOfferAndCardProductAndProfileDescription")) {
            requestParams.put("cardOffer", cardOfferFromDB);
            requestParams.put("cardProduct", cardProductFromDB);
            requestParams.put("profileDescription", profileDescription);
        }
        logger.log("Request path is " + validatePrivateProfileEndPoint);
        logger.log("Request body -->" + requestParams.toString());
        responseForValidateProfile = postRequestAsBearerAuthWithBodyData(validatePrivateProfileEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));

    }

    @Then("^validate the private profile is existing or not response message$")
    public void validateThePrivateProfileIsExistingOrNotResponseMessage() throws Throwable {
        if (response.getStatusCode() == 400 && response.path("errors.statusMessage").equals("Private Profile Already Exists")) {
            logger.log("Response --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI("Private Profile Already Exists", 92042, logger);
        } else if (response.getStatusCode() == 204) {
            logger.log("User able to create a private profile for the customer no, card offer and card product");
        } else if (response.getStatusCode() == 400 && response.path("errors.statusMessage").equals("Profile Description must be unique")) {
            logger.log("Response --> " + response.getBody().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPI("Profile Description must be unique", 92045, logger);
        } else {
            logger.log("Not getting the expected response");
        }

    }

    @Then("^Validates the specific card control details from response and DB based on \"([^\"]*)\" and \"([^\"]*)\"$")
    public void validatesTheSpecificCardControlDetailsFromResponseAndDBBasedOnAnd(String statusNumber, String statusMessage) throws Throwable {
        if ((statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null)))) {

            Map<String, String> cardProfileDetailsFromDB = common.getSpecificCardControlProfileDetails(customerNoFromDB, cardControlProfileOidValue);
            JSONObject cardControlResponse = (JSONObject) parser.parse(response.asString());
            System.out.println("Card control profile response is" + cardControlResponse);
            cardObject = (JSONObject) cardControlResponse.get("card");
            System.out.println("Card object is" + cardObject);
            String customerNoFromResponse = cardObject.get("customerNumber").toString();
            JSONArray cardControlProfiles = (JSONArray) cardControlResponse.get("cardControlProfiles");
            JSONObject cardControlProfileDescription = (JSONObject) cardControlProfiles.get(0);
            JSONObject cardControlObject = (JSONObject) cardControlProfileDescription.get("cardControl");
            commonMethods.responseValidationsCheckingEquals(cardControlResponse.get("cardOffer").toString(), cardProfileDetailsFromDB.get("CARD_OFFER"), "Card Offer", logger);
            //     commonMethods.responseValidationsCheckingEquals(cardControlResponse.get("cardType").toString(), cardProfileDetailsFromDB.get("CARD_TYPE"), "Card Type", logger);
            commonMethods.responseValidationsCheckingEquals(cardObject.get("customerNumber").toString(), customerNoFromDB, "Customer number", logger);
            commonMethods.responseValidationsCheckingEquals(cardControlProfileDescription.get("description").toString(), cardProfileDetailsFromDB.get("PROFILE_DESCRIPTION"), "Profile Description", logger);
            commonMethods.responseValidationsCheckingEquals(cardControlProfileDescription.get("profileCategory").toString(), cardProfileDetailsFromDB.get("PROFILE_CATEGORY"), "Profile Category", logger);
            commonMethods.responseValidationsCheckingEquals(cardControlObject.get("isOdometerReq").toString(), cardProfileDetailsFromDB.get("IS_ODOMETER_REQ"), "Odometer", logger);
            commonMethods.responseValidationsCheckingEquals(cardControlObject.get("isOdometerValidation").toString(), cardProfileDetailsFromDB.get("IS_ODOMETER_VALIDATION"), "Odometer Validation", logger);
            //        commonMethods.responseValidationsCheckingEquals(cardControlObject.get("productRestriction").toString(), cardProfileDetailsFromDB.get("PRODUCT_DESCRIPTION"), "Product Description", logger);

        } else {
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, Integer.parseInt(statusNumber), logger);
        }
    }

    @When("^User gets the specific card control profile details based on card control profile oid and valid scenarios \"([^\"]*)\"$")
    public void userGetsTheSpecificCardControlProfileDetailsBasedOnCardControlProfileOidAndValidScenarios(String possibleScenarios) throws Throwable {
        String cardEndPoint = PropUtils.getPropValue(inputProp, "cardEndPoint");
        String profileEndPoint = PropUtils.getPropValue(inputProp, "profileEndPoint");
        String getCardControlProfileEndpoint;
        if (response.getStatusCode() ==200){
            if (possibleScenarios.equals("validCusNo")) {
                getCardControlProfileEndpoint = cardEndPoint + "/" + customerNoFromDB + profileEndPoint;
            } else {
                getCardControlProfileEndpoint = cardEndPoint + "/" + "1234" + profileEndPoint;
            }
            System.out.println("Get card control profile end point is" + getCardControlProfileEndpoint);
            logger.log("Request path for get card control profile is " + getCardControlProfileEndpoint);
            response = getRequestAsBearerAuthWithSingleQueryParam(getCardControlProfileEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "cardControlProfileOid", cardControlProfileOidValue);
            logger.log("Response for get card control profile details--> " + response.getBody().prettyPrint());
        }
        else{
            logger.log("From Search card control profile API is not getting the expected response "+response.getStatusCode());
        }
      }

    @When("^user gets the list of velocity type values$")
    public void userGetsTheListOfVelocityTypeValues() throws Throwable {
        String velocityEndPoint = PropUtils.getPropValue(inputProp, "lookupForVelocityTypes");
        logger.log("Request path for get card control profile is " + velocityEndPoint);
        response = getRequestAsBearerAuth(velocityEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response --> " + response.getBody().prettyPrint());
    }

    @Then("^user can change the card status and replace actions based on possible scenarios \"([^\"]*)\"$")
    public void userCanChangeTheCardStatusAndReplaceActionsBasedOnPossibleScenarios(String cardScenarios) throws Throwable {
        if (!PropUtils.getPropValue(PropUtils.getProps(inputDataFile), "testStatus").equalsIgnoreCase("Skipped")
                && response.getStatusCode() == 200) {
            String cardEndPoint = PropUtils.getPropValue(inputProp, "cardStatus");
            String country = "", state = "";
            String[] countryRegex = clientCountry.split("_");
            country = countryRegex[1];
            System.out.println("country-->" + country);
            state = common.getStateAndCountriesBasedOnClient("associated");
            System.out.println("state-->" + state);
            if (cardScenarios.equals("validCardNumber")
                    || cardScenarios.equals("validCardNumberWithFutureDate")) {
                if (reissueActionCid.equals("5901")) {
                    System.out.println("Inside of if statement");
                    requestBodyParams = Json.createObjectBuilder()
                            .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                            .add("newStatus", statusFromResponse).add("sEffectiveAt", commonMethods.getCreatedOnToDate())
                            .build();
                    logger.log("Request params for change card status " + requestBodyParams);
                    response = putRequestAsBearerAuthWithBodyData(cardEndPoint, requestBodyParams.toString(),
                            PropUtils.getPropValue(inputProp, "AuthorizationToken"));
                    System.out.println("Response for change card status " + response.getBody().asString());
                } else if (reissueActionCid.equals("5903")
                        && cardScenarios.equals("validCardWithAssociatedCusNoAndFutureDate")
                        || reissueActionCid.equals("5902")
                        && cardScenarios.equals("validCardWithAssociatedCusNoAndFutureDate")) {
                    System.out.println("Inside of else if statement");
                    String currentDate = common.getProcessingDateFromMClients();
                    String futureDate = common.enterADateValueInStatusBeginDateField("futureDate", currentDate, "olsFormat");
                    System.out.println("Future date for card status is " + futureDate);
                    requestBodyParams = Json.createObjectBuilder()
                            .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                            .add("newStatus", statusFromResponse).add("sEffectiveAt", futureDate)
                            .add("reissueFlagValue", reissueActionCid)
                            .add("cardContact",
                                    Json.createObjectBuilder().add("contactName", "contactName123").add("streetAddress",
                                            Json.createObjectBuilder().add("addressLine", "addressSt")
                                                    .add("country", Json.createObjectBuilder().add("countryCode", country))
                                                    .add("suburb", "suburbSt").add("postalCode", "postalCodeSt")
                                                    .add("state", Json.createObjectBuilder().add("description", state))))
                            .build();
                    logger.log("Request params for change card status " + requestBodyParams);
                    response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardEndPoint, requestBodyParams.toString(),
                            PropUtils.getPropValue(inputProp, "AuthorizationToken"), true);
                    System.out.println("Response for change card status " + response.getBody().asString());

                } else {
                    System.out.println("Inside of else statement");
                    requestBodyParams = Json.createObjectBuilder()
                            .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
                            .add("newStatus", statusFromResponse).add("sEffectiveAt", commonMethods.getCreatedOnToDate())
                            .add("reissueFlagValue", reissueActionCid)
                            .add("cardContact",
                                    Json.createObjectBuilder().add("contactName", "contactName123").add("streetAddress",
                                            Json.createObjectBuilder().add("addressLine", "addressSt")
                                                    .add("country", Json.createObjectBuilder().add("countryCode", country))
                                                    .add("suburb", "suburbSt").add("postalCode", "postalCodeSt")
                                                    .add("state", Json.createObjectBuilder().add("description", state))))
                            .build();
                    logger.log("Request params for change card status " + requestBodyParams);
                    response = putRequestAsBearerAuthWithBodyDataWithQueryParam(cardEndPoint, requestBodyParams.toString(),
                            PropUtils.getPropValue(inputProp, "AuthorizationToken"), true);
                    System.out.println("Response for change card status " + response.getBody().asString());
                }

            } else if (cardScenarios.equals("unassociatedCardNoWithCusNoAsInvalid")) {
                cardNumberBasedOnStatus = "1435";

            } else if (cardScenarios.equals("validCardWithInvalidStatus")) {
                statusFromDB = "invalidStatus";

            }
//            if (cardScenarios.contains("Invalid")) {
//                requestBodyParams = Json.createObjectBuilder()
//                        .add("cardNumber", cardNumberBasedOnStatus).add("status", statusFromDB)
//                        .add("newStatus", statusFromResponse).add("sEffectiveAt", commonMethods.getCreatedOnToDate())
//                        .build();
//                response = putRequestAsBearerAuthWithBodyData(cardEndPoint, requestBodyParams.toString(),
//                        PropUtils.getPropValue(inputProp, "AuthorizationToken"));
//                logger.log("Request params for change card status " + requestBodyParams);
//                System.out.println("Response for change card status " + response.getBody().asString());
//
//            }
        } else {
            logger.log("For card status has no content in response");
        }

    }

    @When("^user able to get the list of cards based on possible combination \"([^\"]*)\" for OLS user$")
    public void userAbleToGetTheListOfCardsBasedOnPossibleCombinationForOLSUser(String possibleCombination) {
        String cardSearchEndPoint = PropUtils.getPropValue(inputProp, "cardSearch");
        logger.log("Request path for card search API" + cardSearchEndPoint);
        List<Map<String, String>> listOfCardDetails = null;
        Map<String, String> specificRowForCardSearch = null;
        Map<String, String> specificRowForSecondCardDetails = null;
        listOfCardDetails = common.getListOfCardsForCardSearch();
        int actualCount = rand.nextInt(listOfCardDetails.size());
        System.out.println("Actual count " + actualCount);
        specificRowForCardSearch = listOfCardDetails.get(actualCount);
        int secondData = rand.nextInt(listOfCardDetails.size());
        System.out.println("Actual count for second record is " + secondData);
        specificRowForSecondCardDetails = listOfCardDetails.get(secondData);
        cusNoFromDB = specificRowForCardSearch.get("CUSTOMER_NO");
        arrayFormatForCusNo.add(cusNoFromDB);
        cardNoFromDB = specificRowForCardSearch.get("CARD_NO");
        costCentreFromDB = specificRowForCardSearch.get("CUSTOMER_COST_CENTRE_CODE");
        arrayFormatForCostCentres.add(costCentreFromDB);
        driverNameFromDB = specificRowForCardSearch.get("DRIVER_NAME");
        licensePlateFromDB = specificRowForCardSearch.get("LICENSE_PLATE");
        countryFromDB = specificRowForCardSearch.get("COUNTRY_DESC");
        cardStatusFromDB = specificRowForCardSearch.get("STATUS_DESCRIPTION");
        arrayFormatForCountries.add(countryFromDB);
        arrayFormatForStatus.add(cardStatusFromDB);
        if (possibleCombination.equals("emptyPayload")) {
            System.out.println("Inside of Empty payload");

        } else if (possibleCombination.equals("validInputs")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("cardNumber", cardNoFromDB);
            requestParams.put("costCentres", arrayFormatForCostCentres);
            requestParams.put("driverName", driverNameFromDB);
            requestParams.put("licensePlate", licensePlateFromDB);
            requestParams.put("countries", arrayFormatForCountries);
            requestParams.put("status", arrayFormatForStatus);
        } else if (possibleCombination.contains("CardNo")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            if (possibleCombination.equals("fullCardNo")) {
                requestParams.put("cardNumber", cardNoFromDB);
            } else {
                //    String cardNoFromDB = specificRowForCardSearch.get("CARD_NO");
                requestParams.put("cardNumber", cardNoFromDB.substring(11));
            }
        } else if (possibleCombination.equals("costCentreFilterAsSingleInput")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("costCentres", arrayFormatForCostCentres);
        } else if (possibleCombination.contains("driverName")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            if (possibleCombination.equals("driverNameSearch")) {
                requestParams.put("driverName", driverNameFromDB);
            } else {
                //     String driverNameFromDB = specificRowForCardSearch.get("DRIVER_NAME");
                requestParams.put("driverName", driverNameFromDB.substring(1, 2));
            }

        } else if (possibleCombination.contains("licensePlate")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            if (possibleCombination.equals("licensePlateSearch")) {
                requestParams.put("licensePlate", licensePlateFromDB);
            } else {
                //     String licensePlateFromDB = specificRowForCardSearch.get("LICENSE_PLATE");
                requestParams.put("licensePlate", licensePlateFromDB.substring(1));
            }

        } else if (possibleCombination.equals("countriesFilter")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("countries", arrayFormatForCountries);
        } else if (possibleCombination.equals("statusAsSingleInput")) {
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("status", arrayFormatForStatus);
        } else if (possibleCombination.equals("multipleInputs")) {
            arrayFormatForCusNo.add(specificRowForSecondCardDetails.get("CUSTOMER_NO"));
            arrayFormatForCostCentres.add(specificRowForSecondCardDetails.get("CUSTOMER_COST_CENTRE_CODE"));
            arrayFormatForCountries.add(specificRowForSecondCardDetails.get("COUNTRY_DESC"));
            arrayFormatForStatus.add(specificRowForSecondCardDetails.get("STATUS_DESCRIPTION"));
            requestParams.put("customerNos", arrayFormatForCusNo);
            requestParams.put("costCentres", arrayFormatForCostCentres);
            requestParams.put("countries", arrayFormatForCountries);
            requestParams.put("status", arrayFormatForStatus);
        }
        logger.log("Request params for cards search OLS user" + requestParams);
        response = postRequestAsBearerAuthWithBodyData(cardSearchEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for card search for OLS user" + response.jsonPath().prettyPrint());
    }


    @When("^user able to create a new card control profile with all valid inputs based on card offer and card product as \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userAbleToCreateANewCardControlProfileWithAllValidInputsBasedOnCardOfferAndCardProductAsAndAndAnd(String paramCardOffer, String paramCardProduct, String paramCusNo, String paramDescription) throws Throwable {
        System.out.println("Card offer param is " + paramCardOffer);
        System.out.println("Card product param is " + paramCardProduct);
        System.out.println("profile description param is " + paramDescription);
        System.out.println("Customer number param is " + paramCusNo);
        System.out.println("Location Restricted to value is " + specificLocationRestrictedToValue);
        String cardControlProfileEndPoint = PropUtils.getPropValue(inputProp, "cardControlProfileEndPoint");
        String velocityMaxValue;
        List<Map<String, String>> listOfVelocityTypesAndValue = null;
        List<Map<String, String>> cardOfferAndCardProduct = null;
        customerNoFromDB = commonMethods.getCustomerNoForCSRLogin();
        cardOfferAndCardProduct = common.getCustomerCardOfferProductAndType(customerNoFromDB);
        int actualCountForCardOffer = rand.nextInt(cardOfferAndCardProduct.size());
        cardOfferFromDB = cardOfferAndCardProduct.get(actualCountForCardOffer).get("CARDOFFER");
        System.out.println("Card offer from dB " + cardOfferFromDB);
        cardProductFromDB = cardOfferAndCardProduct.get(actualCountForCardOffer).get("CARDPRODUCT");
// To get the profile description
        String profileDescription = paramDescription.equalsIgnoreCase("existingProfileDescription") ? "OMV" : "Private profile with card products as " + cardProductFromDB;
        // Velocity type API
        response = getRequestAsBearerAuth(PropUtils.getPropValue(inputProp, "lookupForVelocityTypes"), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for velocity types " + response.jsonPath().prettyPrint());
        JSONObject velocityResponse = (JSONObject) parser.parse(response.asString());
        String velocityTypeValue = velocityResponse.keySet().toString();
        System.out.println("velocity response is" + velocityTypeValue);
        //Velocity type API db validation
        listOfVelocityTypesAndValue = common.getListOfVelocityTypes();
        int actualCount = rand.nextInt(listOfVelocityTypesAndValue.size());
        System.out.println("Velocity type description is " + listOfVelocityTypesAndValue.get(actualCount).get("DESCRIPTION"));
        int actualCountForVelocityType = rand.nextInt(listOfVelocityTypesAndValue.size());
        //     secondSpecificVelocityTypes =  listOfVelocityTypesAndValue.get(actualCountForVelocityType);
        String maxValue = listOfVelocityTypesAndValue.get(0).get("MAX_VALUE");
        System.out.println("First max value is " + maxValue);
        String secondVelocityValue = listOfVelocityTypesAndValue.get(1).get("MAX_VALUE");
        System.out.println("Second max value is " + secondVelocityValue);
        if (maxValue == null || secondVelocityValue == null) {
            velocityMaxValue = "10.00";
        } else {
            velocityMaxValue = maxValue + ".00";
        }
        if (paramCardOffer.equalsIgnoreCase("validCardOffer") && paramCardProduct.equalsIgnoreCase("validCardProduct")
                && paramDescription.equalsIgnoreCase("validDescription") && paramCusNo.equalsIgnoreCase("validCusNo")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("customer", Json.createObjectBuilder()
                            .add("customerNo", customerNoFromDB))
                    .add("cardOffer", cardOfferFromDB)
                    .add("cardProduct", cardProductFromDB)
                    .add("cardControlProfiles", Json.createArrayBuilder()
                            .add(0, Json.createObjectBuilder().add("description", profileDescription)
                                    .add("cardControl", Json.createObjectBuilder()
                                            .add("isVehicleIdReq", "Y")
                                            .add("isDriverIdReq", "Y")
                                            .add("isOrderNumberReq", "Y")
                                            .add("isFleetIdReq", "Y")
                                            .add("isCustomerSelectionReq", "Y")
                                            .add("isOdometerReq", "Y")
                                            .add("isOdometerValidation", "Y")
                                            .add("productRestriction", JsonValue.NULL)
                                            .add("locationRestriction", JsonValue.NULL)
                                            .add("timeLimit", JsonValue.NULL)
                                            .add("timeLimits", Json.createObjectBuilder()
                                                    .add("description", timeLimitValue)
                                                    .add("mondayStartAt", 0)
                                                    .add("mondayEndAt", 1439)
                                                    .add("tuesdayStartAt", 0)
                                                    .add("tuesdayEndAt", 1439)
                                                    .add("wednesdayStartAt", 0)
                                                    .add("wednesdayEndAt", 1439)
                                                    .add("thursdayStartAt", 0)
                                                    .add("thursdayEndAt", 1439)
                                                    .add("fridayStartAt", 0)
                                                    .add("fridayEndAt", 1439)
                                                    .add("saturdayStartAt", 0)
                                                    .add("saturdayEndAt", 1439)
                                                    .add("sundayStartAt", 0)
                                                    .add("sundayEndAt", 1439))
                                            .add("velocityAssignments", Json.createArrayBuilder().add(0, Json.createObjectBuilder()
                                                    .add("maxValue", velocityMaxValue)
                                                    .add("velocityType", listOfVelocityTypesAndValue.get(0).get("DESCRIPTION")))
                                                    .add(1, Json.createObjectBuilder()
                                                            .add("maxValue", velocityMaxValue)
                                                            .add("velocityType", listOfVelocityTypesAndValue.get(1).get("DESCRIPTION"))
                                                    )
                                            )
                                    ))).add("productGroups", Json.createArrayBuilder().add(0, specificProductGroup))
                    .add("locationRestrictionType", specificLocationType)
                    .add("locationRestrictedTo", Json.createArrayBuilder().add(0, specificLocationRestrictedToValue))
                    .build();


        } else if (paramCardOffer.equalsIgnoreCase("validCardOffer") && paramCardProduct.equalsIgnoreCase("cardProductAsEmpty")
                && paramDescription.equalsIgnoreCase("validDescription") && paramCusNo.equalsIgnoreCase("validCusNo")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("customer", Json.createObjectBuilder()
                            .add("customerNo", customerNoFromDB))
                    .add("cardOffer", cardOfferFromDB)
                    .add("cardControlProfiles", Json.createArrayBuilder()
                            .add(0, Json.createObjectBuilder().add("description", profileDescription)
                                    .add("cardControl", Json.createObjectBuilder()
                                            .add("isVehicleIdReq", "Y")
                                            .add("isDriverIdReq", "Y")
                                            .add("isOrderNumberReq", "Y")
                                            .add("isFleetIdReq", "Y")
                                            .add("isCustomerSelectionReq", "Y")
                                            .add("isOdometerReq", "Y")
                                            .add("isOdometerValidation", "Y")
                                            .add("productRestriction", JsonValue.NULL)
                                            .add("locationRestriction", JsonValue.NULL)
                                            .add("timeLimit", JsonValue.NULL)
                                            .add("timeLimits", Json.createObjectBuilder()
                                                    .add("description", timeLimitValue)
                                                    .add("mondayStartAt", 0)
                                                    .add("mondayEndAt", 1439)
                                                    .add("tuesdayStartAt", 0)
                                                    .add("tuesdayEndAt", 1439)
                                                    .add("wednesdayStartAt", 0)
                                                    .add("wednesdayEndAt", 1439)
                                                    .add("thursdayStartAt", 0)
                                                    .add("thursdayEndAt", 1439)
                                                    .add("fridayStartAt", 0)
                                                    .add("fridayEndAt", 1439)
                                                    .add("saturdayStartAt", 0)
                                                    .add("saturdayEndAt", 1439)
                                                    .add("sundayStartAt", 0)
                                                    .add("sundayEndAt", 1439))
                                            .add("velocityAssignments", Json.createArrayBuilder().add(0, Json.createObjectBuilder()
                                                    .add("maxValue", velocityMaxValue)
                                                    .add("velocityType", listOfVelocityTypesAndValue.get(0).get("DESCRIPTION")))
                                                    .add(1, Json.createObjectBuilder()
                                                            .add("maxValue", velocityMaxValue)
                                                            .add("velocityType", listOfVelocityTypesAndValue.get(1).get("DESCRIPTION"))
                                                    )
                                            )
                                    ))).add("productGroups", Json.createArrayBuilder().add(0, specificProductGroup))
                    .add("locationRestrictionType", specificLocationType)
                    .add("locationRestrictedTo", Json.createArrayBuilder().add(0, specificLocationRestrictedToValue))
                    .build();
        } else if (paramCardOffer.equalsIgnoreCase("validCardOffer") && paramCardProduct.equalsIgnoreCase("validCardProduct")
                && paramDescription.equalsIgnoreCase("newGeneratedDescription") && paramCusNo.equalsIgnoreCase("validCusNo")) {
            requestBodyParams = Json.createObjectBuilder()
                    .add("customer", Json.createObjectBuilder()
                            .add("customerNo", customerNoFromDB))
                    .add("cardOffer", cardOfferFromDB)
                    .add("cardProduct", cardProductFromDB)
                    .add("cardControlProfiles", Json.createArrayBuilder()
                            .add(0, Json.createObjectBuilder()
                                    .add("cardControl", Json.createObjectBuilder()
                                            .add("isVehicleIdReq", "Y")
                                            .add("isDriverIdReq", "Y")
                                            .add("isOrderNumberReq", "Y")
                                            .add("isFleetIdReq", "Y")
                                            .add("isCustomerSelectionReq", "Y")
                                            .add("isOdometerReq", "Y")
                                            .add("isOdometerValidation", "Y")
                                            .add("productRestriction", JsonValue.NULL)
                                            .add("locationRestriction", JsonValue.NULL)
                                            .add("timeLimit", JsonValue.NULL)
                                            .add("timeLimits", Json.createObjectBuilder()
                                                    .add("description", timeLimitValue)
                                                    .add("mondayStartAt", 0)
                                                    .add("mondayEndAt", 1439)
                                                    .add("tuesdayStartAt", 0)
                                                    .add("tuesdayEndAt", 1439)
                                                    .add("wednesdayStartAt", 0)
                                                    .add("wednesdayEndAt", 1439)
                                                    .add("thursdayStartAt", 0)
                                                    .add("thursdayEndAt", 1439)
                                                    .add("fridayStartAt", 0)
                                                    .add("fridayEndAt", 1439)
                                                    .add("saturdayStartAt", 0)
                                                    .add("saturdayEndAt", 1439)
                                                    .add("sundayStartAt", 0)
                                                    .add("sundayEndAt", 1439))
                                            .add("velocityAssignments", Json.createArrayBuilder().add(0, Json.createObjectBuilder()
                                                    .add("maxValue", velocityMaxValue)
                                                    .add("velocityType", listOfVelocityTypesAndValue.get(0).get("DESCRIPTION")))
                                                    .add(1, Json.createObjectBuilder()
                                                            .add("maxValue", velocityMaxValue)
                                                            .add("velocityType", listOfVelocityTypesAndValue.get(1).get("DESCRIPTION"))
                                                    )
                                            )
                                    ))).add("productGroups", Json.createArrayBuilder().add(0, specificProductGroup))
                    .add("locationRestrictionType", specificLocationType)
                    .add("locationRestrictedTo", Json.createArrayBuilder().add(0, specificLocationRestrictedToValue))
                    .build();
        }
        logger.log("Request params for create card control profile----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(cardControlProfileEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response code :"+response.getStatusCode());

        if (response.getStatusCode() == 200) {
            Assert.assertEquals(response.getStatusCode(), 200);
            //  logger.log("Actual response status code is " + response.getStatusCode() == 200);
            logger.log("Expected response status code is " + "200");
            logger.log("New card control profile has been created for card offer is " + cardOfferFromDB + " and " + customerNoFromDB);

        } else if (response.getStatusCode() == 400 && !response.getContentType().isEmpty()) {
            System.out.println("Response body :"+response.getBody().prettyPrint());
//            int statusNumber = response.path("[].errors.statusNumber");
//            String statusMessage = response.path("[1].errorField.key.description.statusMessage");
//            if (statusMessage.equalsIgnoreCase("Must be unique")) {
//                Assert.assertEquals(statusMessage, "Must be unique");
//                Assert.assertEquals(statusNumber, "12");
//            }

        } else {
            logger.log("Response for card control details " + response.jsonPath().prettyPrint());
            logger.log("Not getting the expected response is " + response.getStatusCode());
        }


    }

    @Then("^user validates the card control profile response message based on status number \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userValidatesTheCardControlProfileResponseMessageBasedOnStatusNumberAnd(String statusNumber, String statusMessage) throws Throwable {
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            Map<String, String> specificRowForCardControlProfile = null;
            specificRowForCardControlProfile = common.getCreatedCardControlProfileDetails(customerNoFromDB);
            cardControlProfileOidValue = specificRowForCardControlProfile.get("CARD_CONTROL_PROFILE_OID");
            logger.log("Card control profile oid value is " + cardControlProfileOidValue);
        } else {
            commonMethods.validateTheResponseBodyObtainedFromTheAPI(statusMessage, Integer.parseInt(statusNumber), logger);
        }
    }


    @When("^User able to get the list  of reissue cards based on customer no \"([^\"]*)\"$")
    public void userAbleToGetTheListOfReissueCardsBasedOnCustomerNo(String paramCustomerNo) throws Throwable {
        String manageReissueCardEndPoint = PropUtils.getPropValue(inputProp, "manageAutoReissue");
        logger.log("Request path for Manage Reissue card API" + manageReissueCardEndPoint);
        String customerNumber = null;
        if (paramCustomerNo.equalsIgnoreCase("valid")) {
            List<Map<String, String>> listOfCustomerNosHavingAutoReissueCards = common.getCustomerNoForReissueCardsEnabled();
            if (listOfCustomerNosHavingAutoReissueCards.isEmpty()) {
                PropUtils.setProps("testStatus", "Skipped", inputDataFilePath);
                PropUtils.setProps("skipReason", "Cards not available for card status", inputDataFilePath);
                logger.log("Cards not available for manage auto reissue");

            } else {
                int actualCount = rand.nextInt(listOfCustomerNosHavingAutoReissueCards.size());
                customerNumber = listOfCustomerNosHavingAutoReissueCards.get(actualCount).get("CUSTOMER_NO");
                requestBodyParams = Json.createObjectBuilder()
                        .add("customerNo", customerNumber)
                        .build();
                logger.log("Request params for Reissue cards of Country user" + requestBodyParams.toString());
            }

        } else if (paramCustomerNo.equalsIgnoreCase("invalid")) {
            customerNumber = "123";
            requestBodyParams = Json.createObjectBuilder()
                    .add("customerNo", customerNumber)
                    .build();
            logger.log("Request params for Reissue cards of Country user" + requestBodyParams.toString());
        }

        if (!PropUtils.getPropValue(PropUtils.getProps(inputDataFile), "testStatus").equalsIgnoreCase("Skipped")
                && paramCustomerNo.equalsIgnoreCase("valid")) {
            response = postRequestAsBearerAuthWithBodyData(manageReissueCardEndPoint, requestBodyParams.toString(), authorizationToken);
            logger.log("Response for Reissue cards of Country user" + response.getBody().prettyPrint());
            cardNumber = response.path("[0].cardNo");
        } else if (paramCustomerNo.equalsIgnoreCase("invalid")) {
            response = postRequestAsBearerAuthWithBodyData(manageReissueCardEndPoint, requestBodyParams.toString(), authorizationToken);
            logger.log("Response for Reissue cards" + response.getBody().prettyPrint());

        } else
            logger.log("No cards are available for auto reissue");
    }

    @When("^user able to get the card details based on the card scenario \"([^\"]*)\"$")
    public void userAbleToGetTheCardDetailsBasedOnTheCardScenario(String cardScenarioType) throws Throwable {

        String cardNo;
        String cardDetailsEndPoint = PropUtils.getPropValue(inputProp, "cardDetails");
        logger.log("Request path -->" + cardDetailsEndPoint);
        if (cardScenarioType.equals("validCardNo")) {
            List<Map<String, String>> listOfCardDetails = null;
            Map<String, String> specificRowForCardSearch = null;
            listOfCardDetails = common.getListOfCardsForCardSearch();
            int actualCount = rand.nextInt(listOfCardDetails.size());
            System.out.println("Actual count " + actualCount);
            specificRowForCardSearch = listOfCardDetails.get(actualCount);
            cardNo = specificRowForCardSearch.get("CARD_NO");
        } else {
            cardNo = "710101954057001";
        }
        requestBodyParams = Json.createObjectBuilder().add("cardNo", cardNo).build();
        System.out.println("cardDetailsParams :" + requestBodyParams.toString());
        logger.log("Request body -->" + requestBodyParams.toString());
        responseValidation = postRequestAsBearerAuthWithBodyData(cardDetailsEndPoint, requestBodyParams.toString(), authorizationToken);
        logger.log("Response --> " + responseValidation.getBody().prettyPrint());
        if (responseValidation.getStatusCode() == 200) {
            cardsDetailsMap = common.getCardDetailsFromDB(cardNo);
            System.out.println("card details Map is " + cardsDetailsMap);
        } else if (responseValidation.getStatusCode() == 500 || responseValidation.getStatusCode() == 204) {
            logger.log("Not getting the expected response");
        }


    }

    @When("^user gets the list of addresses based on customer number based on \"([^\"]*)\"$")
    public void userGetsTheListOfAddressesBasedOnCustomerNumberBasedOn(String customerNumberScenario) throws Throwable {
        String cardAddressEndpoint;
        if (customerNumberScenario.equals("validCustomer")) {
            String customerNo = commonMethods.getCustomerNoForCSRLogin();
            cardAddressEndpoint = PropUtils.getPropValue(inputProp, "addressEndpoint") + customerNo;
        } else {
            cardAddressEndpoint = PropUtils.getPropValue(inputProp, "addressEndpoint") + "12345";
        }
        logger.log("Request path -->" + cardAddressEndpoint);
        response = getRequestAsBearerAuth(cardAddressEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response --> " + response.getBody().prettyPrint());
        if (response.getStatusCode() == 200) {
            logger.log("Response has been retruned for the address API");
        } else if (response.getStatusCode() == 400) {
            commonMethods.validateTheResponseBodyObtainedFromTheAPI("Validation failed", 12, logger);

        } else if (response.getStatusCode() == 500 || response.getStatusCode() == 204) {
            logger.log("Not getting the expected response");
        }


    }

    @When("^user gets the list of mail indicators based on access level as \"([^\"]*)\"$")
    public void userGetsTheListOfMailIndicatorsBasedOnAccessLevelAs(String mailIndicatorScenario) throws Throwable {
        String mailIndicatorEndpoint;
        mailIndicatorEndpoint = PropUtils.getPropValue(inputProp, "mailIndicatorEndpoint");
        logger.log("Request path -->" + mailIndicatorEndpoint);
        if (mailIndicatorScenario.equals("customerLevel")) {
            String customerNo = commonMethods.getCustomerNoForCSRLogin();
            response = getRequestAsBearerAuthWithSingleQueryParam(mailIndicatorEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"), "customerNumber", customerNo);
            logger.log("Response --> " + response.getBody().prettyPrint());

        } else if (mailIndicatorScenario.equals("clientLevel")) {
            response = getRequestAsBearerAuth(mailIndicatorEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
            logger.log("Response --> " + response.getBody().prettyPrint());
        }
        if (response.getStatusCode() == 200) {
            logger.log("Response has been returned for the mail indicator API");
        } else if ( response.getStatusCode() == 204) {
            logger.log("Mail indicator oid is not configured for the respective customer");
        }
        else if (response.getStatusCode() == 500 ) {
            logger.log("Not getting the expected response");
        }


    }

    @When("^user gets the product group restriction to order a card for specific customer$")
    public void user_gets_the_product_group_restriction_to_order_a_card_for_specific_customer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String productRestrictionEndpoint;
        productRestrictionEndpoint = PropUtils.getPropValue(inputProp, "lookupForProductRestriction");
        logger.log("Request path -->" + productRestrictionEndpoint);
        response = getRequestAsBearerAuth(productRestrictionEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response --> " + response.getBody().prettyPrint());
        productRestrictionResponse = (JSONArray) parser.parse(response.asString());
        int actualCount = rand.nextInt(productRestrictionResponse.size());
        specificProductGroup = productRestrictionResponse.get(actualCount).toString();
        System.out.println("Specific product group from response " + specificProductGroup);

    }


    @Then("^Validate the product group restriction values from response and database$")
    public void validate_the_product_group_restriction_values_from_response_and_database() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        List<Map<String, String>> productRestrictionFromDB;
        productRestrictionFromDB = common.getListOfProductGroups();
//        JSONArray productRestrictionResponse = (JSONArray) parser.parse(response.asString());
//        String specificProduct = productRestrictionResponse.get(0).toString();
        for (int i = 0; i < productRestrictionFromDB.size(); i++) {
            for (int j = 0; j < productRestrictionResponse.size(); j++) {
                Map<String, String> specificProductGroupValueFromDB = productRestrictionFromDB.get(i);
                if (productRestrictionResponse.get(j).toString().equals(specificProductGroupValueFromDB.get("DESCRIPTION"))) {
                    System.out.println("inside of if");
                    logger.log("Validated the product group value from response and db");
                    break;
                } else {
                    System.out.println("inside of else if");

                }
            }

        }
    }

    @When("^user gets the list of location restriction values based on selected location group$")
    public void user_gets_the_list_of_location_restriction_values_based_on_selected_location_group() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject locationObject;
        boolean locationDetails = false;
        String locationRestrictionTypeEndpoint = PropUtils.getPropValue(inputProp, "lookupForLocationRestrictionType");
        logger.log("Request path -->" + locationRestrictionTypeEndpoint);
        response = getRequestAsBearerAuth(locationRestrictionTypeEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response --> " + response.getBody().prettyPrint());
        locationRestrictionTypeFromResponse = (JSONArray) parser.parse(response.asString());
        specificLocationType = locationRestrictionTypeFromResponse.get(1).toString();
        //  logger.log("Specific Location type from response is "+specificLocationType);
//Below lines are to get the location restricted to values and handled 2 APIs in this method
        String locationRestrictionEndpoint = PropUtils.getPropValue(inputProp, "lookupForLocationRestriction");
        requestParams.put("locationType", specificLocationType);
        locationResponse = postRequestAsBearerAuthWithBodyData(locationRestrictionEndpoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for " + locationResponse.getBody().prettyPrint());
        locationRestrictionTo = (JSONArray) parser.parse(locationResponse.asString());
        System.out.println("locationRestrictionTo :"+locationRestrictionTo);
        int actualCount = rand.nextInt(locationRestrictionTo.size());
        locationObject = (JSONObject) locationRestrictionTo.get(actualCount);
        specificLocationRestrictedToValue = locationObject.get("oid").toString();
        System.out.println("specificLocationRestrictedToValue :"+specificLocationRestrictedToValue);
        logger.log("Response for specific Location restricted to value is ---> " + specificLocationRestrictedToValue);
//db validation
        locationDetails = common.checkingThePresenceOfLocationRestrictionDetailsInDB(specificLocationRestrictedToValue);
        if (locationDetails) {
            logger.log("Validated the location restriction details from response and db");

        }
        requestParams.remove("locationType");
    }

    @When("^user gets the time-limit API based on client$")
    public void user_gets_the_time_limit_api_based_on_client() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String timeLimitEndPoint = PropUtils.getPropValue(inputProp, "lookupForTimeLimit");
        logger.log("Request path -->" + timeLimitEndPoint);
        response = getRequestAsBearerAuth(timeLimitEndPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response --> " + response.getBody().prettyPrint());
        timeLimitArrayResponse = (JSONArray) parser.parse(response.asString());
        timeLimitValue = timeLimitArrayResponse.get(1).toString();
        logger.log("Description for time limit is  " + timeLimitValue);


    }

    @When("^user able to create a new card control profile with all invalid inputs for negative scenarios based on card offer and card product and customer number as \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_able_to_create_a_new_card_control_profile_with_all_invalid_inputs_for_negative_scenarios_based_on_card_offer_and_card_product_and_customer_number_as_and_and_and(String paramCardOffer, String paramCardProduct, String paramCusNo, String paramProfileDesc) {
        // Write code here that turns the phrase above into concrete actions
        List<Map<String, String>> cardOfferAndCardProduct = null;
        System.out.println("Card offer param is " + paramCardOffer);
        System.out.println("Card product param is " + paramCardProduct);
        System.out.println("profile description param is " + paramProfileDesc);
        System.out.println("Customer number param is " + paramCusNo);
        String cardControlProfileEndPoint = PropUtils.getPropValue(inputProp, "cardControlProfileEndPoint");
        String customerNumberForCardControlProfile = commonMethods.getCustomerNoForCSRLogin();
        cardOfferAndCardProduct = common.getCustomerCardOfferProductAndType(customerNumberForCardControlProfile);
        int actualCountForCardOffer = rand.nextInt(cardOfferAndCardProduct.size());
        String cardOffer = paramCardOffer.equalsIgnoreCase("invalidCardOffer") ? "TPCI AT Cards" : cardOfferAndCardProduct.get(actualCountForCardOffer).get("CARDOFFER");
        System.out.println("Card offer from dB " + cardOfferFromDB);
        String cardProduct = paramCardProduct.equalsIgnoreCase("invalidCardProduct") ? "cardProduct" : cardOfferAndCardProduct.get(actualCountForCardOffer).get("CARDPRODUCT");
        if (paramCusNo.equalsIgnoreCase("invalidCustomerNumber")) {
            customerNumberForCardControlProfile = "300000";
        }
        requestBodyParams = Json.createObjectBuilder()
                .add("customer", Json.createObjectBuilder()
                        .add("customerNo", customerNumberForCardControlProfile))
                .add("cardOffer", cardOffer)
                .add("cardProduct", cardProduct).build();
        logger.log("Request params for create card control profile----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(cardControlProfileEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));

    }

    @Then("user order a card with the all valid details and validate with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"")
    public void user_order_a_card_with_the_all_valid_details_and_validate_with_and_and(String customerNo, String paramExpiry, String paramCtrlProfiles) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String orderCardEndpoint = PropUtils.getPropValue(inputProp, "cardOrder");
        String Date = null;
        cardsDetailsMap.clear();
        expCardNumber = "";
        String name = fakerAPI().name().firstName();
        String embossName = name, requestedBy = name, driverName = name, driverId = name;
        String vehiDes = "Range Rover", license = name + 11, contactName = name,
                addressLine = "Mystic falls", suburb = "Oval", postalCode = "6666";
        String country = "", state = "";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);
        List<Map<String, String>> cardProductDetails = common.getCustomerCardOfferProductAndType(customerNumber);
        String cardProfilesEndPoint = PropUtils.getPropValue(inputProp, "cardProfiles");
        customerNoFromDB = commonMethods.getCustomerNoForCSRLogin();
        List<Map<String, String>> cardOfferAndCardProduct;
        cardOfferAndCardProduct = common.getCustomerCardOfferProductAndType(customerNoFromDB);
        int actualCount = rand.nextInt(cardOfferAndCardProduct.size());
        cardOfferFromDB = cardOfferAndCardProduct.get(actualCount).get("CARDOFFER");
        System.out.println("Card offer from dB " + cardOfferFromDB);
        cardProductFromDB = cardOfferAndCardProduct.get(actualCount).get("CARDPRODUCT");
        System.out.println("Card product from dB " + cardProductFromDB);
        //Passing the inputs
        requestParams.put("customerNumber", customerNoFromDB);
        requestParams.put("cardOffer", cardOfferFromDB);
        requestParams.put("cardProduct", cardProductFromDB);
        if ("MoreThanProduct".equalsIgnoreCase(paramExpiry)) {
            Date = common.changingDateFormat("MoreThanProduct", "OLS");
            System.out.println("expiryDate --> " + Date);
        } else if ("invalid".equalsIgnoreCase(paramExpiry)) {
            Date = common.changingDateFormat("Past", "OLS");
            System.out.println("currentDate --> " + Date);
        } else {
            Date = common.changingDateFormat("WayFuture", "OLS");
            System.out.println("currentDate --> " + Date);
        }
//        Order Card Part
        JsonObject driverVehicleObject;
        JSONParser parser = new JSONParser();
        JSONObject cardsDefaultProf = (JSONObject) parser.parse(response.asString());
        cardsDefaultProf.remove("expiresOn");
        cardsDefaultProf.remove("card");
        cardsDefaultProf.remove("maxExpiresOn");
        cardsDefaultProf.remove("cardProductList");
        String description = "";
        JSONArray cardCtrlObjArr = (JSONArray) cardsDefaultProf.get("cardControlProfiles");
        JSONObject cardCtrlObj = (JSONObject) cardCtrlObjArr.get(0);

        if (!cardCtrlObj.containsKey("description")) {
            Map<String, String> cardsMap = common.getCustomerDefaultProf(customerNumber, cardProductDetails.get(0).get("CARDOFFER"));
            cardCtrlObj.put("description", cardsMap.get("DESCRIPTION"));
        }
        description = cardCtrlObj.get("description").toString();
        System.out.println("profDescription --> " + description);

        if (paramCtrlProfiles.equalsIgnoreCase("new")) {
            cardCtrlObj.remove("description");
            cardCtrlObjArr.set(0, cardCtrlObj);
            cardsDefaultProf.replace("cardControlProfiles", cardCtrlObjArr);
        }


        cardsDetailsMap.put("profDescription", description);
        cardsDetailsMap.putAll(cardProductDetails.get(0));
        cardsDetailsMap.put("embossingName", embossName);
        cardsDetailsMap.put("requestedBy", requestedBy);
        cardsDetailsMap.put("driverName", driverName);
        cardsDetailsMap.put("driverId", driverId);
        cardsDetailsMap.put("vehicleDescription", vehiDes);
        cardsDetailsMap.put("licensePlate", license);

        cardsDetailsMap.put("state", state);
        cardsDetailsMap.put("country", country);
        cardsDetailsMap.put("contactName", contactName);
        cardsDetailsMap.put("addressLine", addressLine);
        cardsDetailsMap.put("suburb", suburb);
        cardsDetailsMap.put("postalCode", postalCode);


//        Temporarily done
        JsonObject cardObject = Json.createObjectBuilder()
                .add("card", Json.createObjectBuilder()
                        .add("requestedBy", requestedBy).add("embossingName", embossName)
                        .add("sExpiresOn", Date).add("externalRef", "")
                        .add("isPinReq", "Y").add("isSignatureReq", "N")
                        .add("type", cardProductDetails.get(0).get("CARDTYPE")))
                .build();
//        DriverObject for driver card
        JsonObject driverObject = Json.createObjectBuilder()
                .add("driver", Json.createObjectBuilder()
                        .add("driverId", driverId).add("driverName", driverName))
                .build();
//        VehicleObject for vehicle card
        JsonObject vehicleObject = Json.createObjectBuilder()
                .add("vehicle", Json.createObjectBuilder()
                        .add("description", vehiDes).add("licensePlate", license))
                .build();
//            Address
        JsonObject addressObject = Json.createObjectBuilder().
                add("cardContact", Json.createObjectBuilder().add("contactName", contactName).
                        add("streetAddress", Json.createObjectBuilder().add("addressLine", addressLine).
                                add("country", country).add("suburb", suburb).
                                add("postalCode", postalCode).add("state", state))).build();

        driverVehicleObject = Json.createObjectBuilder().addAll(Json.createObjectBuilder(driverObject))
                .addAll(Json.createObjectBuilder(vehicleObject))
                .build();

        requestArrayParams = Json.createArrayBuilder().
                add(0, Json.createObjectBuilder()
                        .add("customer", Json.createObjectBuilder().add("customerNo", customerNumber))
                        .addAll(Json.createObjectBuilder(cardsDefaultProf))
                        .addAll(Json.createObjectBuilder(cardObject))
                        .addAll(Json.createObjectBuilder(driverVehicleObject))
                        .addAll(Json.createObjectBuilder(addressObject))
                )
                .build();
        logger.log("Request path -->" + orderCardEndpoint);
        logger.log("Request body -->" + requestArrayParams.toString());

        response = postRequestAsBearerAuthWithBodyData(orderCardEndpoint, requestArrayParams.toString(), authorizationToken);
        if (response.getStatusCode() == 200 && response.getBody().prettyPrint().contains("errors")) {
            logger.log("Response body -->" + response.body().prettyPrint());
            commonMethods.validateTheResponseBodyObtainedFromTheAPIWithArray("", 6, logger);
        } else if (response.getStatusCode() == 200) {
            logger.log("Response body is --> " + response.getBody().prettyPrint());
            expCardNumber = response.path("[0].cardNo");

        }


    }

    @Then("^user post the request  for create group pin for the selected customer number\"([^\"]*)\"and\"([^\"]*)\"as parameter$")
    public void user_post_the_request_for_create_group_pin_for_the_selected_customer_number_and_as_parameter(String paramCust,String PinName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String addGroupPinEndPoint = PropUtils.getPropValue(inputProp, "CreateGroupPinEndpoint");
        String customerNumber = common.getCustomerNumberFromDB(paramCust);
        String name="";
        if(PinName.equals("Exist")){
             name = common.getGroupPinNamefromDB(customerNumber);
        }
        else if(PinName.equals("New")) {
             name = fakerAPI().name().firstName();
        }
        System.out.println("customer no"+customerNumber);
        System.out.println("Pin name"+name);
        requestBodyParams = Json.createObjectBuilder()
                .add("name", name)
                .add("customerNo", customerNumber)
                .add("newPin", "1234").build();
        logger.log("Request params for create group pin----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(addGroupPinEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for add  pin group"+response);
        //PropUtils.setProps("grouppinname", name,inputDataFilePath);
        inputProp.setProperty("grouppinname", name);
        //System.out.println("commonprop ="+inputDataFile);
        inputProp.setProperty("grouppincustomerno", customerNumber);
        //System.out.println("commonprop ="+inputDataFilePath);
    }
    @Then("^validate and verify group pin name$")
    public void validate_and_verify_group_pin_name() throws Throwable{
        //System.out.println("cp"+inputProp);
        if(response.getStatusCode() == 200)
        {
            String pinName = PropUtils.getPropValue(inputProp, "grouppinname");
            String customerNumber = PropUtils.getPropValue(inputProp, "grouppincustomerno");
            System.out.println("pinname =" + pinName);
            System.out.println("customerNo =" + customerNumber);
            boolean isGroupPinNamedisplayed = common.checkingThePresenceofGroupPinNamefromDB(pinName, customerNumber);
            if (isGroupPinNamedisplayed) {
                System.out.println("Group pin added successfully");
            } else {
                System.out.println("Group pin not added successfully");
            }
        }
        else if (response.getStatusCode() == 400)
        {
            System.out.println("statuscode= "+response.getStatusCode());
        }

    }
    }