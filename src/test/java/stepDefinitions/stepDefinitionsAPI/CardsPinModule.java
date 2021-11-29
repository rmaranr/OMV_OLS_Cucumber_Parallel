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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardsPinModule extends RequestMethodsUtils {

    public Scenario logger;
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    Map<String, String> cardsDetailsMap = new HashMap<>();
    String cardDetailsEndPoint = PropUtils.getPropValue(inputProp, "cardDetails");
    String cardUpdateEndPoint = PropUtils.getPropValue(inputProp, "cardUpdate");
    String defaultProfileEndPoint = PropUtils.getPropValue(inputProp, "defaultProfile");

    public CardsPinModule(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    String customerNumber = "", cardNumber = "", referenceNo = "", statusFromDB, customerNumberFromDB;
    String firstCardNumberfromDB = null, secondCardNumberFromDB = null, cusNo;
    public String reissueActionCid, statusFromResponse, cardNumberBasedOnStatus, futureDate;
    boolean presenceCheck = false;
    String expCardNumber = "";

    @Then("^user pass \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" as parameters to \"([^\"]*)\" API card pin$")
    public void userPassAndAndAsParametersToAPICardPin(String paramCustNumber, String paramCardNumber, String paramPinContact, String type, String pinOffset, String confirmPinOffset, String endpoint) throws Throwable {
        common.initializeDBEnvironment();
        String country = "", state = "";
        String[] countryRegex = clientCountry.split("_");
        country = countryRegex[1];
        System.out.println("country-->" + country);
        state = common.getStateAndCountriesBasedOnClient("associated");
        System.out.println("state-->" + state);
        String name = fakerAPI().name().firstName();
        String contactName = name, addressLine = "Mystic falls", suburb = "Oval", postalCode = "6666";
        JsonObject pinReqBody = null;
        List<Map<String, String>> customerAndCardNoList;
        Map<String, String> customerAndCardNo;
        if (paramCustNumber.equalsIgnoreCase("associatedUser") &&
                (paramCardNumber.equalsIgnoreCase("associatedCard") || paramCardNumber.equalsIgnoreCase("notApplicable"))) {

            if (paramCardNumber.equalsIgnoreCase("notApplicable"))
                customerAndCardNoList = common.getValidListOfCustomerAndCardNumbersBasedOnCardPinCtrl(endpoint, false);
            else
                customerAndCardNoList = common.getValidListOfCustomerAndCardNumbersBasedOnCardPinCtrl(endpoint, true);

            if (customerAndCardNoList.isEmpty()) {
//                logger.log("Card not available for " + endpoint + ", please order card and retest", log);
//                logFailWithoutStackTrace("Card not available for " + endpoint + ", please order card and retest",logger);
                PropUtils.setProps("testStatus", "Skipped", inputDataFilePath);
                PropUtils.setProps("skipReason", "Card not available for " + endpoint + ", please order card and retest", inputDataFilePath);
            } else {
                customerNumber = customerAndCardNoList.get(0).get("CUSTOMER_NO");
                cardNumber = customerAndCardNoList.get(0).get("CARD_NO");
            }
        } else {
            customerAndCardNo = commonMethods.getCardNoForSpecificCustomer(paramCustNumber, paramCardNumber);
            customerNumber = customerAndCardNo.get("customerNumber");
            cardNumber = customerAndCardNo.get("cardNumber");
        }

        if (!PropUtils.getPropValue(PropUtils.getProps(inputDataFile), "testStatus").equalsIgnoreCase("Skipped")) {
            System.out.println("cardNumber -->" + cardNumber);
            System.out.println("customerNumber -->" + customerNumber);
            System.out.println("endpoint--> " + endpoint);

            if (endpoint.equalsIgnoreCase("ChangePin") || endpoint.equalsIgnoreCase("changePinTypeWishPin")) {

                if ("pinContact".equalsIgnoreCase(paramPinContact)) {
                    pinReqBody = Json.createObjectBuilder().
                            add("card", Json.createObjectBuilder()
                                    .add("cardNo", cardNumber)
                                    .add("pinOffset", pinOffset)
                                    .add("confirmPinOffset", confirmPinOffset)
                            ).
                            add("pinContact", Json.createObjectBuilder()
                                    .add("pinContactMailer", true)
                                    .add("accountContactMailer", false))
                            .build();
                } else if ("primaryContact".equalsIgnoreCase(paramPinContact)) {
                    pinReqBody = Json.createObjectBuilder().
                            add("card", Json.createObjectBuilder()
                                    .add("cardNo", cardNumber)
                                    .add("pinOffset", pinOffset)
                                    .add("confirmPinOffset", confirmPinOffset)
                            ).
                            add("pinContact", Json.createObjectBuilder()
                                    .add("pinContactMailer", false)
                                    .add("accountContactMailer", true))
                            .build();
                }

            } else {
                if ("pinContact".equalsIgnoreCase(paramPinContact)) {
                    pinReqBody = Json.createObjectBuilder().
                            add("card", Json.createObjectBuilder()
                                    .add("cardNo", cardNumber)).
                            add("pinContact", Json.createObjectBuilder()
                                    .add("pinContactMailer", true)
                                    .add("accountContactMailer", false))
                            .build();
                } else if ("primaryContact".equalsIgnoreCase(paramPinContact)) {
                    pinReqBody = Json.createObjectBuilder().
                            add("card", Json.createObjectBuilder()
                                    .add("cardNo", cardNumber)).
                            add("pinContact", Json.createObjectBuilder()
                                    .add("pinContactMailer", false)
                                    .add("accountContactMailer", true))
                            .build();
                }
            }
            System.out.println("Type --> " + type);

            if (endpoint.equalsIgnoreCase("changePinTypeWishPin")) {
                pinReqBody = Json.createObjectBuilder().
                        addAll(Json.createObjectBuilder(pinReqBody)).
                        add("systemPinSelected", false).
                        add("wishPinSelected", true).
                        add("groupPinSelected", false).
                        build();
            } else if (endpoint.equalsIgnoreCase("changePinTypeSysPin")) {
                pinReqBody = Json.createObjectBuilder().
                        addAll(Json.createObjectBuilder(pinReqBody)).
                        add("systemPinSelected", true).
                        add("wishPinSelected", false).
                        add("groupPinSelected", false).
                        build();
            } else if (endpoint.equalsIgnoreCase("changePinTypeGrpPin"))
                pinReqBody = Json.createObjectBuilder().
                        addAll(Json.createObjectBuilder(pinReqBody)).
                        add("systemPinSelected", false).
                        add("wishPinSelected", false).
                        add("groupPinSelected", true).
                        build();

            if ("OLS".equalsIgnoreCase(type)) {
                requestBodyParams = Json.createObjectBuilder().
                        addAll(Json.createObjectBuilder(pinReqBody)).
                        add("customer", Json.createObjectBuilder().
                                add("customerNo", customerNumber)).
                        build();
            } else if ("CSR".equalsIgnoreCase(type)) {
                requestBodyParams = pinReqBody;
            }
            System.out.println("requestBodyParams :" + requestBodyParams.toString());
            logger.log("requestBodyParams :" + requestBodyParams.toString());
            String pinEndpoint = PropUtils.getPropValue(inputProp, endpoint + "Endpoint");
            logger.log("Request Path --> " + pinEndpoint);
            response = putRequestAsBearerAuthWithBodyData(pinEndpoint, requestBodyParams.toString(), authorizationToken);
        }
    }
}
