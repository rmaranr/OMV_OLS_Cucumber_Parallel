package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OLSHomeDashboard extends RequestMethodsUtils {

	public io.cucumber.java.Scenario logger;
	CommonDBUtils common = new CommonDBUtils();
	CommonMethods commonMethods = new CommonMethods();
	String toDate,fromDate, expectedDateToChange,clientProcessingDate;

	public OLSHomeDashboard(HooksAPI hooksAPI){
		this.logger = hooksAPI.logger;
	}
	@When("^user must be able to get the sum of transactions based on the period range as \"([^\"]*)\"$")
	public void user_must_be_able_to_get_the_sum_of_transactions_based_on_the_period_range_as(String periodRange) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String totaltransactionEndPoint= PropUtils.getPropValue(inputProp, "totaltransactionEndPoint");
		clientProcessingDate = common.getProcessingDateFromMClients();
		toDate = commonMethods.getCreatedOnToDate();


		requestParams.put("customerNo",common.getCustomerNumberFromDB("associatedUser"));
		if(periodRange.equals("lastMonth")){
			expectedDateToChange = "pastOneMonth";
			fromDate = common.enterADateValueInStatusBeginDateField(expectedDateToChange,clientProcessingDate,"olsFormat");
			requestParams.put("sEffectiveDateFrom",fromDate);
			requestParams.put("sEffectiveDateTo",toDate);
			requestParams.put("periodType","Month");
		}
		else  if(periodRange.equals("lastThreeMonth")){
			expectedDateToChange = "Past";
			fromDate = common.enterADateValueInStatusBeginDateField("Past",clientProcessingDate,"olsFormat");
			requestParams.put("sEffectiveDateFrom",fromDate);
			requestParams.put("sEffectiveDateTo",toDate);

			requestParams.put("periodType","Month");
		}
		else if(periodRange.equals("lastSixMonth")){
			expectedDateToChange = "PastSix";
			fromDate = common.enterADateValueInStatusBeginDateField(expectedDateToChange,clientProcessingDate,"olsFormat");
			requestParams.put("sEffectiveDateFrom",fromDate);
			requestParams.put("sEffectiveDateTo",toDate);

			requestParams.put("periodType","Month");
		}
		else if(periodRange.equals("clientProcessingDateMonthAsWeek")){

			requestParams.put("sEffectiveDateFrom",toDate);
			requestParams.put("sEffectiveDateTo",toDate);

			requestParams.put("periodType","Week");
		}
		else  if(periodRange.equals("clientProcessingDateMonthAsMonth")){

			requestParams.put("sEffectiveDateFrom",toDate);
			requestParams.put("sEffectiveDateTo",toDate);

			requestParams.put("periodType","Month");
		}
		else if(periodRange.equals("lastMonthAsWeek")){
			expectedDateToChange = "pastOneMonth";
			fromDate = common.enterADateValueInStatusBeginDateField(expectedDateToChange,clientProcessingDate,"olsFormat");
			requestParams.put("sEffectiveDateFrom",fromDate);
			requestParams.put("sEffectiveDateTo",toDate);

			requestParams.put("periodType","Week");
		}
		else if(periodRange.equals("lastMonthAsWeekForUnassociatedCusNo")){
			expectedDateToChange = "PastSix";
			String cusNo =  common.getCustomerNumberFromDB("");
			String fromDate = common.enterADateValueInStatusBeginDateField(expectedDateToChange,clientProcessingDate,"olsFormat");
			requestParams.put("customerNo",cusNo);
			requestParams.put("sEffectiveDateFrom",fromDate);
			requestParams.put("sEffectiveDateTo",toDate);

			requestParams.put("periodType","Week");
		}
		logger.log("Request params for total transactions "+requestParams);
		response = postRequestAsBearerAuthWithBodyData(totaltransactionEndPoint,requestParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
		logger.log("REsponse for total transactions "+response.jsonPath().prettyPrint());

	}


	@Then("^verify the total spending amount of transactions from response based on period range as \"([^\"]*)\"$")
	public void verify_the_total_spending_amount_of_transactions_from_response_based_on_period_range_as(
			String periodRange) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String fDate = null;
		String toDate = "", totalAmountFromDB;
		String totalAmountFromResponse = response.getBody().asString();

		if (response.getStatusCode() == 200 && totalAmountFromResponse.contains("totalTransactionsAmount")) {
			float totalSpendingAmount = response.jsonPath().get("totalSpendingAmount");
			int totalAmount = (int) totalSpendingAmount;
			System.out.println("Rounded amount " + totalAmount);
			// String clientProcessingDate = common.getProcessingDateFromMClients();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
			try {
				Date date = sdf.parse(clientProcessingDate);
				toDate = dateFormatter.format(date);
				System.out.println("To date " + toDate);
			} catch (ParseException ex) {
			}

			if (periodRange.contains("last")) {
				fDate = common.enterADateValueInStatusBeginDateField(expectedDateToChange, clientProcessingDate,
						"dbFormat");
				System.out.println("Formatted date is " + fDate);
				totalAmountFromDB = common.getTotalSpendingAmountOfTransactions(
						common.getCustomerNumberFromDB("associatedUser"), fDate, toDate);
			} else {
				totalAmountFromDB = common.getTotalSpendingAmountOfTransactions(
						common.getCustomerNumberFromDB("associatedUser"), toDate, toDate);
			}

			if (Integer.toString(totalAmount).equals(totalAmountFromDB)) {
				logger.log("Validation is passed for total transactions");

			} else {
				logger.log("Amount is mismatch");
			}

		} else if (response.getStatusCode() == 204) {
			logger.log("Specific transactions has no response");

		} else if (response.getStatusCode() == 401) {
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);

			validateStatusNumber(statusNumberFromResponse, 97102, logger,
					"Expected status number from response is " + statusNumberFromResponse);

			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);

			validateResponseMessage(statusMessageFromResponse, "API User is unauthorized to access this customer", logger,
					"Expected status message from response is " + statusMessageFromResponse);

		} else {
			logger.log("No Transactions has been processed in specific date range");
		}
	}

}
