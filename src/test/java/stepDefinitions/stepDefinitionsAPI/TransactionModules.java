package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TransactionModules extends RequestMethodsUtils {

    public io.cucumber.java.Scenario logger;
    Random rand = new Random();
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    JSONArray arrayFormatForLocationNos = new JSONArray();
    JSONArray arrayFormatForCostCentres = new JSONArray();
    JSONArray arrayFormatForCustomerNos = new JSONArray();
    JSONArray arrayFormatForInvalidCustomerNos = new JSONArray();
    JSONArray arrayFormatForLocationNames = new JSONArray();
    JSONArray arrayFormatForInvalidLocationNos = new JSONArray();
    String customerNo = "", currentProcessingDate;
    public static String cardNo = "", reference = "";
    String specificDisputeReason;
    public String cusNoFromDB, cardNoFromDB, referenceFromDB, costCentresFromDB, driverNameFromDB, locationNosFromDB,
            licensePlateFromDB, locationNameFromDB;

    public TransactionModules(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @Then("^user pass the customer number, card number, date range and reference number based on \"([^\"]*)\" for transaction search endpoint$")
    public void user_pass_the_customer_number_card_number_date_range_and_reference_number_based_on_for_transaction_search_endpoint(String filterCombination) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String searchTransactionEndpoint = PropUtils.getPropValue(inputProp, "transactionSearchEndPoint");

        customerNo = common.getCustomerNumberFromDB("associatedUser");
        currentProcessingDate = common.getProcessingDateFromMClients();
        System.out.println("Filter combination is " + filterCombination);
        requestParams.put("customerNo", customerNo);
        if (filterCombination.equals("customerNoWithLast1MonthOfTransaction")) {
            requestParams.put("sEffectiveDateFrom", common.enterADateValueInStatusBeginDateField("pastOneMonth", currentProcessingDate, "olsFormat"));
            requestParams.put("sEffectiveDateTo", commonMethods.getCreatedOnToDate());
        } else if (filterCombination.equals("customerNoWithDateRange")) {

            requestParams.put("sEffectiveDateFrom", common.enterADateValueInStatusBeginDateField("Past", currentProcessingDate, "olsFormat"));
            requestParams.put("sEffectiveDateTo", commonMethods.getCreatedOnToDate());
        } else if (filterCombination.equals("customerNoWithCardNumber")) {
            requestParams.put("cardNumber", cardNo);

        } else if (filterCombination.equals("customerNoWithReference")) {
            requestParams.put("reference", reference);

        } else if (filterCombination.equals("customerNoWithWildSearch") && !(reference.isEmpty())) {
            String partialReferenceSearch = reference.substring(1);
            System.out.println("Partial reference is " + partialReferenceSearch);
            requestParams.put("reference", "*" + partialReferenceSearch);

        } else if (filterCombination.equals("unAssociatedCustomerNo")) {
            String unAssociatedCustomerNo = common.getCustomerNumberFromDB("");
            requestParams.put("customerNo", unAssociatedCustomerNo);

        } else if (filterCombination.equals("invalidCustomerNo")) {
            String invalidAccountNo = "124566";
            requestParams.put("customerNo", invalidAccountNo);

        }
        //post the request
        if (filterCombination.equals("customerNoWithWildSearch") && (reference.isEmpty())) {
            logger.log("No data for wild search reference ");
        } else {
            logger.log("Request params for search transactions " + requestParams);
            response = postRequestAsBearerAuthWithBodyData(searchTransactionEndpoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        }

    }

    @Then("^user post request body to get specified transactions and validate with \"([^\"]*)\" and \"([^\"]*)\" as expected values$")
    public void user_post_request_body_to_get_specified_transactions_and_validate_with_and_as_expected_values(String statusMessage, String statusNumber) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {

            JSONParser parser = new JSONParser();
            JSONObject transactionResponse = (JSONObject) parser.parse(response.asString());
            if (transactionResponse.containsKey("transactionList")) {
                JSONArray transactionList = (JSONArray) transactionResponse.get("transactionList");

                System.out.println("List of transactions " + transactionList.size());
                int actualCount = rand.nextInt(transactionList.size());
                JSONObject specificTransactionFromResponse = (JSONObject) transactionList.get(actualCount);
                logger.log("Specific transaction object from response is " + specificTransactionFromResponse);
                cardNo = specificTransactionFromResponse.get("cardNumber").toString();
                System.out.println("Transaction for card number from response is " + cardNo);
                reference = specificTransactionFromResponse.get("reference").toString();
                System.out.println("Transaction for reference from response is " + reference);
                Map<String, String> transactionDetailsFromDB = common.getTransactionSearchDetailsFromDB(reference, customerNo);
                if (specificTransactionFromResponse.get("transactionTypeDescription").equals(transactionDetailsFromDB.get("TRANSACTION_TYPE_DESCRIPTION"))
                        && specificTransactionFromResponse.get("cardNumber").equals(transactionDetailsFromDB.get("CARD_NO"))
                        && specificTransactionFromResponse.get("locationName").equals(transactionDetailsFromDB.get("LOCATION_NAME"))
                        && specificTransactionFromResponse.get("locationAddress").equals(transactionDetailsFromDB.get("LOCATION_ADDRESS"))
                        || specificTransactionFromResponse.get("driverName").equals(transactionDetailsFromDB.get("DRIVER_NAME"))
                        && specificTransactionFromResponse.get("reference").equals(transactionDetailsFromDB.get("reference"))
                        && specificTransactionFromResponse.get("transCategoryDescription").equals(transactionDetailsFromDB.get("TRANS_CATEGORY_DESC"))
                        && specificTransactionFromResponse.get("customerAmount").equals(transactionDetailsFromDB.get("CUSTOMER_AMOUNT"))
                        || specificTransactionFromResponse.get("productTranslationDescription").equals(transactionDetailsFromDB.get("PRODUCT_TRANSLATION_DES"))
                        || specificTransactionFromResponse.get("vehicleDescription").equals(transactionDetailsFromDB.get("VEHICLE_DESC"))
                        || specificTransactionFromResponse.get("captureType").equals(transactionDetailsFromDB.get("CAPTURETYPE_DESC"))) {
                    System.out.println("Inside of if ");
                    logger.log("Validation is done for transaction search details from response and DB");

                } else {
                    logger.log("Validation is not done for transaction search details from response and DB");

                }
            } else {
                logger.log("Not getting the transactions for specific customer");
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

    @When("^user gets the list of transactions based on filters and searches \"([^\"]*)\"$")
    public void userGetsTheListOfTransactionsBasedOnFiltersAndSearches(String filterAndSearches) throws Throwable {
        String transSearchEndPoint = PropUtils.getPropValue(inputProp, "searchTransaction");
        System.out.println("Filter Combination for transaction API is " + filterAndSearches);
        List<Map<String, String>> listOfTransactionOidsFromDB = null;
        listOfTransactionOidsFromDB = common.getTransactionOidFromTransactionTable();
        Map<String, String> firstRowData = listOfTransactionOidsFromDB.get(0);
        Map<String, String> secondRowData = listOfTransactionOidsFromDB.get(1);
        String transOid = firstRowData.get("TRANSACTION_OID");
        System.out.println("First transaction oid from transaction table is " + transOid);
        String secondTransOid = secondRowData.get("TRANSACTION_OID");
        System.out.println("Second transaction oid from transaction table is " + secondTransOid);
        Map<String, String> transactionDetailsBasedOnTransOid = common.getTransactionDetailsBasedOnTransOid(transOid);
        Map<String, String> secondTransaction = common.getTransactionDetailsBasedOnTransOid(secondTransOid);
        cusNoFromDB = transactionDetailsBasedOnTransOid.get("CUSTOMER_NO");
        arrayFormatForCustomerNos.add(cusNoFromDB);
        requestParams.put("customerNos", arrayFormatForCustomerNos);
        cardNoFromDB = transactionDetailsBasedOnTransOid.get("CARD_NO");
        referenceFromDB = transactionDetailsBasedOnTransOid.get("REFERENCE");
        costCentresFromDB = transactionDetailsBasedOnTransOid.get("CUSTOMER_COST_CENTRE_CODE");
        arrayFormatForCostCentres.add(costCentresFromDB);
        driverNameFromDB = transactionDetailsBasedOnTransOid.get("DRIVER_NAME");
        locationNosFromDB = transactionDetailsBasedOnTransOid.get("LOCATION_NO");
        arrayFormatForLocationNos.add(locationNosFromDB);
        licensePlateFromDB = transactionDetailsBasedOnTransOid.get("LICENSE_PLATE");
        locationNameFromDB = transactionDetailsBasedOnTransOid.get("LOCATION_NAME");
        arrayFormatForLocationNames.add(locationNameFromDB);
        if (filterAndSearches.equals("allFiltersAndSearches")) {
            requestParams.put("cardNumber", cardNoFromDB);
            requestParams.put("reference", referenceFromDB);
            if (driverNameFromDB != null && licensePlateFromDB != null && costCentresFromDB != null && locationNosFromDB != null) {
                requestParams.put("costCentres", arrayFormatForCostCentres);
                requestParams.put("driverName", driverNameFromDB);
                requestParams.put("locationNos", arrayFormatForLocationNos);
                requestParams.put("licensePlate", licensePlateFromDB);
                requestParams.put("locationNames", arrayFormatForLocationNames);
            } else {
                logger.log("Unable to search the transactions with cost centre, locations, driver details and vehicle details");
            }
        } else if (filterAndSearches.contains("customerNumber")) {
            if (filterAndSearches.equals("customerNumberAsMultiSelect")) {
                String customerNo = secondTransaction.get("CUSTOMER_NO");
                System.out.println("Second transaction customer no is " + customerNo);
                arrayFormatForCustomerNos.add(customerNo);
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

        } else if (filterAndSearches.contains("Reference")) {

            if (filterAndSearches.equals("fullReferenceSearch")) {
                requestParams.put("reference", referenceFromDB);
            } else {
                String wilCardReference = referenceFromDB.substring(1);
                requestParams.put("reference", wilCardReference);
            }

        } else if (filterAndSearches.contains("locationNumber")) {
            if (locationNosFromDB != null && filterAndSearches.equals("locationNumberAsSingleSelect")) {
                requestParams.put("locationNos", arrayFormatForLocationNos);
            } else if (locationNosFromDB != null && filterAndSearches.equals("locationNumberAsMultiSelect")) {
                String locationNumber = secondTransaction.get("LOCATION_NO");
                System.out.println("Second transaction location no is " + locationNumber);
                arrayFormatForLocationNos.add(locationNumber);
                System.out.println("Array format for location no is " + arrayFormatForLocationNos);
                requestParams.put("locationNos", arrayFormatForLocationNos);
            } else {
                logger.log("Unable to search the transaction if location number is null in database");
            }
        } else if (filterAndSearches.contains("locationName")) {
            if (locationNameFromDB != null && filterAndSearches.equals("locationNameAsSingleSelect")) {
                requestParams.put("locationNames", arrayFormatForLocationNames);
            } else if (locationNameFromDB != null && filterAndSearches.equals("locationNameAsMultiSelect")) {
                String secondLocationName = secondTransaction.get("LOCATION_NAME");
                System.out.println("Second transaction location name is " + secondLocationName);
                arrayFormatForLocationNames.add(secondLocationName);
                System.out.println("Array format for location name is " + secondLocationName);
                requestParams.put("locationNames", arrayFormatForLocationNames);
            } else {
                logger.log("Unable to search the transaction if location name is null in database");
            }


        } else if (filterAndSearches.contains("costCentre")) {
            if (costCentresFromDB != null && filterAndSearches.equals("costCentreAsSingleSelect")) {
                requestParams.put("costCentres", arrayFormatForCostCentres);
            } else if (costCentresFromDB != null && filterAndSearches.equals("costCentreAsMultiSelect")) {
                String multipleCostCentre = secondTransaction.get("CUSTOMER_COST_CENTRE_CODE");
                System.out.println("Second transaction cost centre is " + multipleCostCentre);
                arrayFormatForCostCentres.add(multipleCostCentre);
                System.out.println("Array format for cost centre is " + multipleCostCentre);
                requestParams.put("costCentres", arrayFormatForCostCentres);
            } else {
                logger.log("Unable to search the transaction if cost centre value is null in database");
            }


        } else if (filterAndSearches.contains("driverName")) {
            if (driverNameFromDB != null && filterAndSearches.equals("driverNameSearch")) {
                requestParams.put("driverName", driverNameFromDB);
            } else if (driverNameFromDB != null && filterAndSearches.equals("driverNameAsPartialSearch")) {
                String partialDriverNameSearch = driverNameFromDB.substring(1, 2).toLowerCase();
                requestParams.put("driverName", partialDriverNameSearch);
            } else {
                logger.log("Unable to search the transaction if driver name value is null in database");
            }

        } else if (filterAndSearches.contains("licensePlate")) {
            if (licensePlateFromDB != null && filterAndSearches.equals("licensePlateSearch")) {
                requestParams.put("licensePlate", licensePlateFromDB);
            } else if (licensePlateFromDB != null && filterAndSearches.equals("licensePlateAsPartialSearch")) {
                String partialLPSearch = licensePlateFromDB.substring(1);
                requestParams.put("licensePlate", partialLPSearch);
            } else {
                logger.log("Unable to search the transaction if license plate value is null in database");
            }

        } else if (filterAndSearches.contains("customerNoWithCardNo")) {

            //requestParams.put("customerNos", arrayFormatForCustomerNos);
            requestParams.put("cardNumber", cardNoFromDB);

        } else if (filterAndSearches.equals("unassociatedCardNo")) {
            requestParams.put("cardNumber", "1235896");
        } else if (filterAndSearches.equals("invalidCusNo")) {
            arrayFormatForInvalidCustomerNos.add("1235896");
            requestParams.put("customerNos", arrayFormatForInvalidCustomerNos);

        } else if (filterAndSearches.equals("invalidLocationNo")) {
            arrayFormatForInvalidLocationNos.add("z");
            requestParams.put("locationNos", arrayFormatForInvalidLocationNos);
        } else if (filterAndSearches.equals("unassociatedCusNo")) {
            arrayFormatForCustomerNos.remove(0);
            String customerNumber = common.getCustomerNumberFromDB("");
            arrayFormatForCustomerNos.add(customerNumber);
            requestParams.put("customerNos", arrayFormatForCustomerNos);
        } else if (filterAndSearches.equals("emptyRequestBody")) {
            arrayFormatForCustomerNos.remove(0);

            //requestParams.put("customerNos", arrayFormatForCustomerNos);
        }
        logger.log("Request path for transaction search is " + transSearchEndPoint);
        if (!filterAndSearches.equals("emptyRequestBody")) {
            logger.log("Request param for transaction search is " + requestParams);
            response = postRequestAsBearerAuthWithBodyData(transSearchEndPoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        } else {
            response = postRequestAsBearerAuthWithBodyData(transSearchEndPoint, "", PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        }

    }


    @Then("^user validates the transaction default view details with \"([^\"]*)\" and \"([^\"]*)\" and based on filter search as \"([^\"]*)\"$")
    public void userValidatesTheTransactionDefaultViewDetailsWithAndAndBasedOnFilterSearchAs(String statusMessage, String statusNumber, String filterSearch) throws Throwable {
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            Random rand = new Random();
            JSONParser parser = new JSONParser();
            JSONObject transactionResponse = (JSONObject) parser.parse(response.asString());
            if (transactionResponse.containsKey("transactionList")) {
                JSONArray transactionList = (JSONArray) transactionResponse.get("transactionList");
                System.out.println("List of transactions " + transactionList.size());
                int actualCount = rand.nextInt(transactionList.size());
                JSONObject specificTransactionFromResponse = (JSONObject) transactionList.get(actualCount);
                if (filterSearch.equals("allFiltersAndSearches")) {
                    if (driverNameFromDB != null && licensePlateFromDB != null && costCentresFromDB != null && locationNosFromDB != null) {
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("driverName").toString(), driverNameFromDB, "driverName", logger);
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("licensePlate").toString(), licensePlateFromDB, "licensePlate", logger);
                        commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("costCentreCode").toString(), arrayFormatForCostCentres, "cost centre", logger);
                        commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("locationNo").toString(), arrayFormatForLocationNos, "location number", logger);
                        commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("locationName").toString(), arrayFormatForLocationNames, "location name", logger);
                    } else {
                        logger.log("Inside of else statement to validate the transaction search fields");
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("cardNumber").toString(), cardNoFromDB, "cardNumber", logger);
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("reference").toString(), referenceFromDB, "reference", logger);
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("accountNo").toString(), cusNoFromDB, "accountNo", logger);
                    }
                } else if (filterSearch.contains("customerNumber") || (filterSearch.equals("customerNoWithCardNo"))) {
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("accountNo").toString(), cusNoFromDB, "customer number", logger);

                } else if (filterSearch.contains("CardNumber")) {
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("cardNumber").toString(), cardNoFromDB, "cardNumber", logger);
                } else if (filterSearch.contains("Reference")) {
                    commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("reference").toString(), referenceFromDB, "reference", logger);

                } else if (filterSearch.contains("locationNumber")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("locationNo").toString(), arrayFormatForLocationNos, "locationNumber", logger);

                } else if (filterSearch.contains("locationName")) {
                    commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("locationName").toString(), arrayFormatForLocationNames, "locationName", logger);
                } else if (filterSearch.contains("costCentre")) {
                    if (costCentresFromDB != null) {
                        commonMethods.responseValidationsCheckingEqualsList(specificTransactionFromResponse.get("costCentreCode").toString(), arrayFormatForCostCentres, "costCentre", logger);
                    } else {
                        logger.log("Validation is not handled if cost centre is having null value");
                    }
                } else if (filterSearch.contains("driverName")) {
                    if (driverNameFromDB != null) {
                        commonMethods.responseValidationsCheckingContains(specificTransactionFromResponse.get("driverName").toString(), driverNameFromDB, "driverName", logger);
                    } else {
                        logger.log("Validation is not handled if driver name is having null value");
                    }
                } else if (filterSearch.contains("licensePlate")) {
                    if (licensePlateFromDB != null) {
                        commonMethods.responseValidationsCheckingEquals(specificTransactionFromResponse.get("licensePlate").toString(), licensePlateFromDB, "licensePlate", logger);
                    } else {
                        logger.log("Validation is not handled if license plate is having null value");
                    }
                }
            } else {
                logger.log("For respective search has no transaction ");
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


    @When("^user able to view the expanded section transaction details with possible scenarios as \"([^\"]*)\"$")
    public void userAbleToViewTheExpandedSectionTransactionDetailsWithPossibleScenariosAs(String possibleScenarios) throws Throwable {
        String transDetailsEndPoint = PropUtils.getPropValue(inputProp, "transactionDetailsEndPoint");
        if (possibleScenarios.equals("validTransOid")) {
            List<Map<String, String>> listOfTransactionOidsFromDB = null;
            listOfTransactionOidsFromDB = common.getTransactionOidFromTransactionTable();
            Map<String, String> firstRowData = listOfTransactionOidsFromDB.get(0);
            String transOidForTransDetails = firstRowData.get("TRANSACTION_OID");
            logger.log("Request path for transaction details " + transDetailsEndPoint + transOidForTransDetails);

            response = getRequestAsBearerAuth(transDetailsEndPoint + transOidForTransDetails, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        } else {
            response = getRequestAsBearerAuth(transDetailsEndPoint + "123", PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        }

        logger.log("Response for transaction details " + response.jsonPath().prettyPrint());
    }

    @Then("^user validates the transaction details from response and DB with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userValidatesTheTransactionDetailsFromResponseAndDBWithAnd(String statusNumber, String statusMessage) throws Throwable {
        if (statusNumber.equals("") && statusMessage.equals("")
                || (statusNumber.equals(null) && statusMessage.equals(null))) {
            JSONParser parser = new JSONParser();
            JSONObject transactionDetailsResponse = (JSONObject) parser.parse(response.asString());
            //	logger.log("Transaction details response "+transactionDetailsResponse);
            List<Map<String, String>> listOfTransactionOidsFromDB = null;
            List<Map<String, String>> listOfTransactionDetailsFromDB = null;
            listOfTransactionOidsFromDB = common.getTransactionOidFromTransactionTable();
            Map<String, String> firstRowData = listOfTransactionOidsFromDB.get(0);
            String transOidForTransDetails = firstRowData.get("TRANSACTION_OID");
            listOfTransactionDetailsFromDB = common.getAllTransactionDetailsFromTransactionEnquiryTable(transOidForTransDetails);
            System.out.println("List of transaction details from dB is " + listOfTransactionDetailsFromDB.size());
            Map<String, String> firstRowTransDetails = listOfTransactionDetailsFromDB.get(0);
            String captureTypeFromDB = firstRowTransDetails.get("CAPTURE_TYPE_DESCRIPTION");
            System.out.println("First row details is " + captureTypeFromDB);

            if (transactionDetailsResponse.containsKey("lineItems")) {
                JSONArray transactionLineItemList = (JSONArray) transactionDetailsResponse.get("lineItems");
                logger.log("List of transactions line item list is " + transactionLineItemList.size());
                JSONObject specificTransactionFromResponse = (JSONObject) transactionLineItemList.get(0);
                logger.log("Specific transaction object from response is " + specificTransactionFromResponse);
                assertTwoStringsForAPI(specificTransactionFromResponse.get("product").toString(), firstRowTransDetails.get("DESCRIPTION"), logger);
            }
            logger.log("Validating Capture type ");
            assertTwoStringsForAPI(response.jsonPath().get("captureType"), firstRowTransDetails.get("CAPTURE_TYPE_DESCRIPTION"), logger);
            logger.log("Validating Account name field ");
            assertTwoStringsForAPI(response.jsonPath().get("accountName"), firstRowTransDetails.get("CUSTOMER_NAME"), logger);
            logger.log("Validating Authorization number field ");
            assertTwoStringsForAPI(response.jsonPath().get("authorisationNo"), firstRowTransDetails.get("AUTHORISATION_NO"), logger);
            logger.log("Validating Stan number field ");
            assertTwoStringsForAPI(response.jsonPath().get("stan"), firstRowTransDetails.get("STAN"), logger);
            logger.log("Validating batch number field ");
            assertTwoStringsForAPI(response.jsonPath().get("batchNumber"), firstRowTransDetails.get("BATCH_NUMBER"), logger);
            logger.log("Validating Invoice number field ");
            assertTwoStringsForAPI(response.jsonPath().get("invoiceNumber"), firstRowTransDetails.get("INVOICE_NO"), logger);
            logger.log("Validating customer currency code field ");
            assertTwoStringsForAPI(response.jsonPath().get("customerCurrencyCode"), firstRowTransDetails.get("CUSTOMER_CURRENCY_CODE_CHAR"), logger);
            logger.log("Validating fleet id field ");
            assertTwoStringsForAPI(response.jsonPath().get("fleetID"), firstRowTransDetails.get("FLEET_ID"), logger);
            logger.log("Validating Second Card number field ");
            assertTwoStringsForAPI(response.jsonPath().get("secondCardNo"), firstRowTransDetails.get("EXTRA_CARD_NO"), logger);
            logger.log("Validating Odometer field ");
            assertTwoStringsForAPI(response.jsonPath().get("odoMeter"), firstRowTransDetails.get("ODOMETER"), logger);
            logger.log("Validating Transaction class field ");
            assertTwoStringsForAPI(response.jsonPath().get("transactionClass"), firstRowTransDetails.get("TRANSACTION_CLASS_DESC"), logger);


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

    @When("^user able to get the list of dispute reasons to raise the dispute status$")
    public void userAbleToGetTheListOfDisputeReasonsToRaiseTheDisputeStatus() throws Throwable {
        String disputeReasonEndpoint = PropUtils.getPropValue(inputProp, "disputeReasonEndPoint");
        System.out.println("End point " + disputeReasonEndpoint);
        response = getRequestAsBearerAuth(disputeReasonEndpoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        logger.log("Response for dispute reason API " + response.jsonPath().prettyPrint());
    }

    @Then("^user able to post the dispute status based on possible combination \"([^\"]*)\"$")
    public void userAbleToPostTheDisputeStatusBasedOnPossibleCombination(String possibleCombination) throws Throwable {
        String disputeTransactionEndpoint = PropUtils.getPropValue(inputProp, "disputeTransactionEndPoint");
        org.json.JSONObject referenceObject = new org.json.JSONObject();
        List<Map<String, String>> listOfTransactionFromDB = null;
        listOfTransactionFromDB = common.getListOfTransactionForDispute();
        int actualCount = rand.nextInt(listOfTransactionFromDB.size());
        Map<String, String> specificRowData = listOfTransactionFromDB.get(actualCount);
        String referenceForDisputeTransaction = specificRowData.get("REFERENCE");
        System.out.println("Reference for transaction " + referenceForDisputeTransaction);
        referenceObject = jsonObjectBuilder("reference", referenceForDisputeTransaction);
        requestParams.put("transaction", referenceObject);
        requestParams.put("disputeReason", specificDisputeReason);
        logger.log("Request param for dispute transaction API " + requestParams);
        response = postRequestAsBearerAuthWithBodyData(disputeTransactionEndpoint, requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for dispute " + response.getBody());
    }

    @Then("^Validate the dispute reason response$")
    public void validateTheDisputeReasonResponse() throws Throwable {
        JSONParser parser = new JSONParser();
        JSONArray disputeReasonFromResponse = (JSONArray) parser.parse(response.asString());
        int actualCount = rand.nextInt(disputeReasonFromResponse.size());
        specificDisputeReason = disputeReasonFromResponse.get(actualCount).toString();
        System.out.println("Specific Dispute Reason " + specificDisputeReason);


    }
}




