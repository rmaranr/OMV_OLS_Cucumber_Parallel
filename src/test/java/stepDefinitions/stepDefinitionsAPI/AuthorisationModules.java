package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AuthorisationModules extends RequestMethodsUtils {

    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    JSONArray arrayFormatForLocationNos = new JSONArray();
    JSONArray arrayFormatForCostCentres = new JSONArray();
    JSONArray arrayFormatForCustomerNos = new JSONArray();
    JSONArray arrayFormatForInvalidCustomerNos = new JSONArray();
    JSONArray arrayFormatForLocationNames = new JSONArray();
    JSONArray arrayFormatForInvalidLocationNos = new JSONArray();
    public Scenario logger;

    public AuthorisationModules(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }

    String customerNo = "", currentProcessingDate;
    public static String cardNo = "", reference = "";
    String cusNoFromDB, cardNoFromDB, referenceFromDB, costCentresFromDB, driverNameFromDB, locationNosFromDB,
            licensePlateFromDB, locationNameFromDB;
    List<String> locationNameList = new ArrayList<>();
    List<String> locationNumberList = new ArrayList<>();
    List<String> cusNoArrayList = new ArrayList<>();
    List<String> costCenterList = new ArrayList<>();

    @When("^user gets the list of OMV authorisation transactions based on filters and searches \"([^\"]*)\"$")
    public void userGetsTheListOfOMVAuthorisationTransactionsBasedOnFiltersAndSearches(String filterAndSearches) throws Throwable {
        String authTransSearchEndPoint = PropUtils.getPropValue(inputProp, "searchAuthTransaction");
        System.out.println("Filter Combination for transaction API is " + filterAndSearches);
        // String transactionOidFromDB = common.getTransactionOidfromTransactionTable();
        List<Map<String, String>> listOfAuthTransactionLogOidFromDB = null;
        listOfAuthTransactionLogOidFromDB = common.getAuthTransactionDetailsfromAuthTransaction();
        Map<String, String> firstRecordAuthTransactionDetails = listOfAuthTransactionLogOidFromDB.get(0);
        Map<String, String> secondRecordAuthTransactionDetails = listOfAuthTransactionLogOidFromDB.get(1);

        System.out.println("First transaction oid from transaction table is " + firstRecordAuthTransactionDetails);

        System.out.println("Second transaction oid from transaction table is " + secondRecordAuthTransactionDetails);

        cusNoFromDB = firstRecordAuthTransactionDetails.get("CUSTOMER_NO");
        cardNoFromDB = firstRecordAuthTransactionDetails.get("CARD_NO");
        referenceFromDB = firstRecordAuthTransactionDetails.get("REFERENCE");
        costCentresFromDB = firstRecordAuthTransactionDetails.get("CUSTOMER_COST_CENTRE_CODE");
        driverNameFromDB = firstRecordAuthTransactionDetails.get("DRIVER_NAME");
        locationNosFromDB = firstRecordAuthTransactionDetails.get("LOCATION_NO");
        licensePlateFromDB = firstRecordAuthTransactionDetails.get("LICENSE_PLATE");
        locationNameFromDB = firstRecordAuthTransactionDetails.get("LOCATION_NAME");

        arrayFormatForCustomerNos.add(cusNoFromDB);
        arrayFormatForCostCentres.add(costCentresFromDB);
        arrayFormatForLocationNos.add(locationNosFromDB);
        arrayFormatForLocationNames.add(locationNameFromDB);
        cusNoArrayList.add(cusNoFromDB);
        costCenterList.add(costCentresFromDB);
        locationNameList.add(locationNameFromDB);
        locationNumberList.add(locationNosFromDB);

        if (filterAndSearches.equals("allFiltersAndSearches")) {
            requestParams.put("customerNos", arrayFormatForCustomerNos);
            requestParams.put("cardNumber", cardNoFromDB);
            requestParams.put("reference", referenceFromDB);
            requestParams.put("costCentres", arrayFormatForCostCentres);
            requestParams.put("driverName", driverNameFromDB);
            requestParams.put("locationNos", arrayFormatForLocationNos);
            requestParams.put("licensePlate", licensePlateFromDB);
            requestParams.put("locationNames", arrayFormatForLocationNames);
        } else if (filterAndSearches.contains("customerNumber")) {
            if (filterAndSearches.equals("customerNumberAsSingleSelect")) {
                requestParams.put("customerNos", arrayFormatForCustomerNos);
            } else {
                String customerNo = secondRecordAuthTransactionDetails.get("CUSTOMER_NO");
                System.out.println("Second transaction customer no is " + customerNo);
                arrayFormatForCustomerNos.add(customerNo);
                cusNoArrayList.add(customerNo);
                System.out.println("Array format for customer no is " + arrayFormatForCustomerNos);
                requestParams.put("customerNos", arrayFormatForCustomerNos);

            }
        } else if (filterAndSearches.equals("dateRange")) {
            requestParams.put("sEffectiveDateFrom", "");
            requestParams.put("sEffectiveDateTo", "");
        } else if (filterAndSearches.contains("CardNumber")) {
            if (filterAndSearches.equals("fullCardNumberSearch")) {
                requestParams.put("cardNumber", cardNoFromDB);
            } else {
                String last5DigitCardNo = cardNoFromDB.substring(13);
                requestParams.put("cardNumber", last5DigitCardNo);
            }

        } else if (filterAndSearches.contains("Reference") && !(referenceFromDB.isEmpty()) ) {

            if (filterAndSearches.equals("fullReferenceSearch")) {
                requestParams.put("reference", referenceFromDB);
            } else {
                String wilCardReference = referenceFromDB.substring(1);
                requestParams.put("reference", wilCardReference);
            }

        } else if (filterAndSearches.contains("locationNumber")) {
            if (filterAndSearches.equals("locationNumberAsSingleSelect")) {
                requestParams.put("locationNos", arrayFormatForLocationNos);
            } else {
                String locationNumber = secondRecordAuthTransactionDetails.get("LOCATION_NO");
                System.out.println("Second transaction location no is " + locationNumber);
                arrayFormatForLocationNos.add(locationNumber);
                locationNumberList.add(locationNumber);
                System.out.println("Array format for location no is " + arrayFormatForLocationNos);
                requestParams.put("locationNos", arrayFormatForLocationNos);
            }

        } else if (filterAndSearches.contains("locationName")) {
            if (filterAndSearches.equals("locationNameAsSingleSelect")) {
                requestParams.put("locationNames", arrayFormatForLocationNames);
            } else {
                String secondLocationName = secondRecordAuthTransactionDetails.get("LOCATION_NAME");
                System.out.println("Second transaction location name is " + secondLocationName);
                arrayFormatForLocationNames.add(secondLocationName);
                locationNameList.add(secondLocationName);
                System.out.println("Array format for location name is " + secondLocationName);

                requestParams.put("locationNames", arrayFormatForLocationNames);
            }


        } else if (filterAndSearches.contains("costCentre")) {
            if (filterAndSearches.equals("costCentreAsSingleSelect")) {
                requestParams.put("costCentres", arrayFormatForCostCentres);
            } else {
                String multipleCostCentre = secondRecordAuthTransactionDetails.get("CUSTOMER_COST_CENTRE_CODE");
                System.out.println("Second transaction cost centre is " + multipleCostCentre);
                arrayFormatForCostCentres.add(multipleCostCentre);
                costCenterList.add(multipleCostCentre);
                System.out.println("Array format for cost centre is " + multipleCostCentre);

                requestParams.put("costCentres", arrayFormatForCostCentres);
            }

        } else if (filterAndSearches.contains("driverName")) {
            if (filterAndSearches.equals("driverNameSearch")) {
                requestParams.put("driverName", driverNameFromDB);
            } else if(driverNameFromDB != null){
                String partialDriverNameSearch = driverNameFromDB.substring(1, 2).toLowerCase();
                requestParams.put("driverName", partialDriverNameSearch);
            }

        } else if (filterAndSearches.contains("licensePlate")) {
            if (filterAndSearches.equals("licensePlateSearch")) {
                requestParams.put("licensePlate", licensePlateFromDB);
            } else if(licensePlateFromDB != null) {
                String partialLPSearch = licensePlateFromDB;
                System.out.println("PartialLicensePlate"+partialLPSearch);
                requestParams.put("licensePlate", partialLPSearch);
            }
            else {
                String licensePlate = null;
            }

        } else if (filterAndSearches.contains("customerNoWithCardNo")) {

            requestParams.put("customerNos", arrayFormatForCustomerNos);
            requestParams.put("cardNumber", cardNoFromDB);

        } else if (filterAndSearches.equals("unassociatedCardNo")) {
            requestParams.put("cardNumber", "1235896");
        } else if (filterAndSearches.equals("invalidCusNo")) {
            arrayFormatForInvalidCustomerNos.add("1235896");
            requestParams.put("customerNos", arrayFormatForInvalidCustomerNos);

        } else if (filterAndSearches.equals("invalidLocationNo")) {
            arrayFormatForInvalidLocationNos.add("z");
            requestParams.put("locationNos", arrayFormatForInvalidLocationNos);
        }
        logger.log("Request path for transaction search is " + authTransSearchEndPoint);
        logger.log("Request param for transaction search is " + requestParams);
        response = postRequestAsBearerAuthWithBodyData(authTransSearchEndPoint, requestParams.toString(), authorizationToken);
        logger.log("Response for transaction search is " + response.jsonPath().prettyPrint());

    }

    @Then("^user validates the authorisations default view details with \"([^\"]*)\" and \"([^\"]*)\" and based on filter search as \"([^\"]*)\"$")
    public void userValidatesTheTransactionDefaultViewDetailsWithAndAndBasedOnFilterSearchAs(String statusMessage, String statusNumber, String filterSearch) throws Throwable {
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            Random rand = new Random();
            JSONParser parser = new JSONParser();
            JSONObject transactionResponse = (JSONObject) parser.parse(response.asString());
            if (transactionResponse.containsKey("authTransactions")) {
                JSONArray transactionList = (JSONArray) transactionResponse.get("authTransactions");

                System.out.println("List of transactions " + transactionList.size());
                int actualCount = rand.nextInt(transactionList.size());
                JSONObject specificTransactionFromResponse = (JSONObject) transactionList.get(actualCount);
                logger.log("Specific transaction object from response is " + specificTransactionFromResponse);
                if (filterSearch.equals("allFiltersAndSearches")) {
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("cardNo").toString(), cardNoFromDB, "cardNo", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("reference").toString(), referenceFromDB, "reference", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("accountNo").toString(), cusNoFromDB, "accountNo", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("costCentreCode").toString(), costCentresFromDB, "costCentreCode", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("driverName").toString(), driverNameFromDB, "driverName", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("locationNo").toString(), locationNosFromDB, "locationNo", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("licensePlate").toString(), licensePlateFromDB, "licensePlate", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("locationName").toString(), locationNameFromDB, "locationName", logger);
                } else if (filterSearch.contains("customerNumber")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("accountNo").toString(), cusNoArrayList, "accountNo", logger);
                } else if (filterSearch.equals("customerNoWithCardNo")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("accountNo").toString(), cusNoArrayList, "accountNo", logger);
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("cardNo").toString(), cardNoFromDB, "cardNo", logger);
                } else if (filterSearch.contains("locationNumber")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("locationNo").toString(), locationNumberList, "locationNo", logger);
                } else if (filterSearch.contains("locationName")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("locationName").toString(), locationNameList, "locationName", logger);
                } else if (filterSearch.contains("costCentre")) {
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("costCentreCode").toString(), costCentresFromDB, "costCentreCode", logger);
                } else if (filterSearch.contains("CardNumber")) {
                    if(filterSearch.contains("Partial"))
                        commonMethods.responseValidationsCheckingContains(specificTransactionFromResponse.get("cardNo").toString(), cardNoFromDB, "cardNo", logger);
                    else
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("cardNo").toString(), cardNoFromDB, "cardNo", logger);
                } else if (filterSearch.contains("Reference") && referenceFromDB != null) {
                    if(filterSearch.contains("Partial"))
                        commonMethods.responseValidationsCheckingContains(specificTransactionFromResponse.get("reference").toString(), referenceFromDB, "reference", logger);
                    else
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("reference").toString(), referenceFromDB, "reference", logger);
                } else if (filterSearch.contains("driverName") && driverNameFromDB != null) {
                    if(filterSearch.contains("Partial")) {
                        commonMethods.responseValidationsCheckingContains(specificTransactionFromResponse.get("driverName").toString(), referenceFromDB, "driverName", logger);
                    }
                    else{
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("driverName").toString(), driverNameFromDB, "driverName", logger);
                }
                }
                else if (licensePlateFromDB != null) {

                    if (filterSearch.contains("licensePlate")) {
                        if (filterSearch.contains("Partial"))
                            commonMethods.responseValidationsCheckingContains(specificTransactionFromResponse.get("licensePlate").toString(), licensePlateFromDB, "licensePlate", logger);
                        else
                            commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("licensePlate").toString(), licensePlateFromDB, "licensePlate", logger);
                    }
                }

            } else {
                logger.log("Not getting the transactions for specific customer ");
            }
        } else {
            String statusMessageFromResponse = response.path("errors.statusMessage");
            System.out.println("Status Message from response is " + statusMessageFromResponse);

            validateResponseMessage(statusMessageFromResponse, statusMessage, logger,
                    "Expected status message from response is " + statusMessageFromResponse);
            int statusNumberFromResponse = response.path("errors.statusNumber");
            System.out.println("Status Number from response is " + statusNumberFromResponse);

            validateStatusNumber(statusNumberFromResponse, Integer.parseInt(statusNumber), logger,
                    "Expected status number from response is " + statusNumberFromResponse);
        }

    }

    @When("^user gets the list of authorisation transactions based on filters and searches \"([^\"]*)\"$")
    public void userGetsTheListOfAuthorisationTransactionsBasedOnFiltersAndSearches(String filterAndSearches) throws Throwable {
        String authTransSearchEndPoint = PropUtils.getPropValue(inputProp, "searchAuthTransaction");
        System.out.println("Filter Combination for transaction API is " + filterAndSearches);
        // String transactionOidFromDB = common.getTransactionOidfromTransactionTable();
        List<Map<String, String>> listOfAuthTransactionLogOidFromDB = null;
        listOfAuthTransactionLogOidFromDB = common.getAuthTransactionDetailsfromAuthTransaction();
        Map<String, String> firstRecordAuthTransactionDetails = listOfAuthTransactionLogOidFromDB.get(0);
        Map<String, String> secondRecordAuthTransactionDetails = listOfAuthTransactionLogOidFromDB.get(1);

        System.out.println("First transaction oid from transaction table is " + firstRecordAuthTransactionDetails);

        System.out.println("Second transaction oid from transaction table is " + secondRecordAuthTransactionDetails);

        cusNoFromDB = firstRecordAuthTransactionDetails.get("CUSTOMER_NO");
        cardNoFromDB = firstRecordAuthTransactionDetails.get("CARD_NO");
        referenceFromDB = firstRecordAuthTransactionDetails.get("REFERENCE");
        costCentresFromDB = firstRecordAuthTransactionDetails.get("CUSTOMER_COST_CENTRE_CODE");
        driverNameFromDB = firstRecordAuthTransactionDetails.get("DRIVER_NAME");
        locationNosFromDB = firstRecordAuthTransactionDetails.get("LOCATION_NO");
        licensePlateFromDB = firstRecordAuthTransactionDetails.get("LICENSE_PLATE");
        locationNameFromDB = firstRecordAuthTransactionDetails.get("LOCATION_NAME");
        System.out.println("reference from db :"+referenceFromDB);
        System.out.println("driver name from db :"+driverNameFromDB);

        arrayFormatForCustomerNos.add(cusNoFromDB);
        arrayFormatForCostCentres.add(costCentresFromDB);
        arrayFormatForLocationNos.add(locationNosFromDB);
        arrayFormatForLocationNames.add(locationNameFromDB);
        cusNoArrayList.add(cusNoFromDB);
        costCenterList.add(costCentresFromDB);
        locationNameList.add(locationNameFromDB);
        locationNumberList.add(locationNosFromDB);

        if (filterAndSearches.equals("allFiltersAndSearches")) {
//            requestParams.put("customerNos", arrayFormatForCustomerNos);
            requestParams.put("cardNumber", cardNoFromDB);
            requestParams.put("reference", referenceFromDB);
            requestParams.put("costCentres", arrayFormatForCostCentres);
            requestParams.put("driverName", driverNameFromDB);
            requestParams.put("locationNos", arrayFormatForLocationNos);
            requestParams.put("licensePlate", licensePlateFromDB);
            requestParams.put("locationNames", arrayFormatForLocationNames);
//        } else if (filterAndSearches.contains("customerNumber")) {
//            if (filterAndSearches.equals("customerNumberAsSingleSelect")) {
//                requestParams.put("customerNos", arrayFormatForCustomerNos);
//            } else {
//                String customerNo = secondRecordAuthTransactionDetails.get("CUSTOMER_NO");
//                System.out.println("Second transaction customer no is " + customerNo);
//                arrayFormatForCustomerNos.add(customerNo);
//                cusNoArrayList.add(customerNo);
//                System.out.println("Array format for customer no is " + arrayFormatForCustomerNos);
//                requestParams.put("customerNos", arrayFormatForCustomerNos);
//
//            }
        } else if (filterAndSearches.equals("dateRange")) {
            requestParams.put("sEffectiveDateFrom", "");
            requestParams.put("sEffectiveDateTo", "");
        } else if (filterAndSearches.contains("CardNumber")) {
            if (filterAndSearches.equals("fullCardNumberSearch")) {
                requestParams.put("cardNumber", cardNoFromDB);
            } else {
                String last5DigitCardNo = cardNoFromDB.substring(11);
                requestParams.put("cardNumber", last5DigitCardNo);
            }

        } else if (filterAndSearches.contains("Reference") && referenceFromDB != null) {

            if (filterAndSearches.equals("fullReferenceSearch")) {
                requestParams.put("reference", referenceFromDB);
            } else {
                String wilCardReference = referenceFromDB.substring(0,1);
                requestParams.put("reference", wilCardReference);
            }

        } else if (filterAndSearches.contains("locationNumber")) {
            if (filterAndSearches.equals("locationNumberAsSingleSelect")) {
                requestParams.put("locationNos", arrayFormatForLocationNos);
            } else {
                String locationNumber = secondRecordAuthTransactionDetails.get("LOCATION_NO");
                System.out.println("Second transaction location no is " + locationNumber);
                arrayFormatForLocationNos.add(locationNumber);
                locationNumberList.add(locationNumber);
                System.out.println("Array format for location no is " + arrayFormatForLocationNos);
                requestParams.put("locationNos", arrayFormatForLocationNos);
            }

        } else if (filterAndSearches.contains("locationName")) {
            if (filterAndSearches.equals("locationNameAsSingleSelect")) {
                requestParams.put("locationNames", arrayFormatForLocationNames);
            } else {
                String secondLocationName = secondRecordAuthTransactionDetails.get("LOCATION_NAME");
                System.out.println("Second transaction location name is " + secondLocationName);
                arrayFormatForLocationNames.add(secondLocationName);
                locationNameList.add(secondLocationName);
                System.out.println("Array format for location name is " + secondLocationName);

                requestParams.put("locationNames", arrayFormatForLocationNames);
            }


        } else if (filterAndSearches.contains("costCentre")) {
            if (filterAndSearches.equals("costCentreAsSingleSelect")) {
                requestParams.put("costCentres", arrayFormatForCostCentres);
            } else {
                String multipleCostCentre = secondRecordAuthTransactionDetails.get("CUSTOMER_COST_CENTRE_CODE");
                System.out.println("Second transaction cost centre is " + multipleCostCentre);
                arrayFormatForCostCentres.add(multipleCostCentre);
                costCenterList.add(multipleCostCentre);
                System.out.println("Array format for cost centre is " + multipleCostCentre);

                requestParams.put("costCentres", arrayFormatForCostCentres);
            }

        }
        else if (filterAndSearches.contains("driverName") &&  (driverNameFromDB != null)) {
            if (filterAndSearches.equals("driverNameSearch")) {


                requestParams.put("driverName", driverNameFromDB);
            } else {
                String partialDriverNameSearch = driverNameFromDB.substring(1, 2).toLowerCase();
                requestParams.put("driverName", partialDriverNameSearch);
            }

        }


        else if (filterAndSearches.contains("licensePlate")) {
            if (filterAndSearches.equals("licensePlateSearch")) {
                requestParams.put("licensePlate", licensePlateFromDB);
            } else if(licensePlateFromDB != null) {
                String partialLPSearch = licensePlateFromDB;
                System.out.println("PartialLicensePlate"+partialLPSearch);
                requestParams.put("licensePlate", partialLPSearch);
            }
            else {
                String licensePlate = null;
            }

        } else if (filterAndSearches.contains("customerNoWithCardNo")) {

            requestParams.put("customerNos", arrayFormatForCustomerNos);
            requestParams.put("cardNumber", cardNoFromDB);

        } else if (filterAndSearches.equals("unassociatedCardNo")) {
            requestParams.put("cardNumber", "1235896");
        }  else if (filterAndSearches.equals("invalidLocationNo")) {
            arrayFormatForInvalidLocationNos.add("z");
            requestParams.put("locationNos", arrayFormatForInvalidLocationNos);
        }

        if (filterAndSearches.equals("invalidCusNo")) {
            arrayFormatForInvalidCustomerNos.add("1235896");
            requestParams.put("customerNos", arrayFormatForInvalidCustomerNos);
        } else
            requestParams.put("customerNos", arrayFormatForCustomerNos);

        logger.log("Request path for transaction search is " + authTransSearchEndPoint);
        logger.log("Request param for transaction search is " + requestParams);
        response = postRequestAsBearerAuthWithBodyData(authTransSearchEndPoint, requestParams.toString(), authorizationToken);
        logger.log("Response for transaction search is " + response.jsonPath().prettyPrint());

    }

}
