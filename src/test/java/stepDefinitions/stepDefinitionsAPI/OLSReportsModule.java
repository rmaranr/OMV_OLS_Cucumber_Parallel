package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utilities.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OLSReportsModule extends RequestMethodsUtils {

	public io.cucumber.java.Scenario logger;
	 
	CommonDBUtils common = new CommonDBUtils();
	ReportsMethodsAPI reportMethods = new ReportsMethodsAPI();
	CommonMethods commonMethods = new CommonMethods();
	public static String reportTypeDescription,createdOnToDate,createdOnFromDate,templateNameDescription,fileName;

	public OLSReportsModule(HooksAPI hooksAPI){
		this.logger = hooksAPI.logger;
	}
	JSONObject userReportDetails;

	public  String customerNumber,userOid,memberTypeDescriptionFromDB,scheduledReportTemplateName,emailAddressForEditReport,
			frequencyDescForEditReport,deliveryTypeForEditReport,templateNameForEditReport,editReportTypeDescFromResponse,reportTypeFromResponse,reportTypeDesc,isEnabledStatus;
	public Map<String, String> userdetails = new HashMap<String,String>();
	Random rand = new Random();
	Faker faker = new Faker();
	@When("^user should able to get all the report type description for \"([^\"]*)\" report$")
	public void user_should_able_to_get_all_the_report_type_description_for_report(String reportTypeValue)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String reportTypeLookUpEndPoint = PropUtils.getPropValue(inputProp, "reportTypeLookUpEndPoint");
		//	response = getRequestAsBearerAuthWithQueryParamForReportType(reportTypeLookUpEndPoint,
		//			PropUtils.getPropValue(inputProp, "AuthorizationToken"), "Customer", reportTypeValue);
		response = getRequestAsBearerAuthWithMultipleQueryParams(reportTypeLookUpEndPoint,PropUtils.getPropValue(inputProp, "AuthorizationToken"),"memberType","Customer","reportTypeValue",reportTypeValue);
		logger.log("Response for report types "+response.jsonPath().prettyPrint());

	}

	@When("^user should able to get the reports based on the filter combination \"([^\"]*)\"$")
	public void user_should_able_to_get_the_reports_based_on_the_filter_combination(String filterSearch)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String reportSearchEndPoint = PropUtils.getPropValue(inputProp, "reportSearchEndpoint");

		if (filterSearch.equals("associatedUser")) {
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
		}

		if (filterSearch.equals("reportTypeFilterWithCustomerNo")) {
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			logger.log("Report type description is "+reportTypeDescription);
			requestParams.put("reportType", reportTypeDescription);
		} else if (filterSearch.equals("dateRangeWithCustomerNo")) {
			createdOnToDate = commonMethods.getCreatedOnToDate();
			//	PropUtils.setProps("createdOnToDateFroStoredReports", createdOnToDate, inputDataFilePath);
			createdOnFromDate = reportMethods.getCreatedOnFromDate(common.getProcessingDateFromMClients());
			System.out.println("Created on from date is"+createdOnFromDate);
			logger.log("Created on from date " + createdOnFromDate);
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			requestParams.put("sCreatedOnFrom", createdOnFromDate);
			requestParams.put("sCreatedOnTo", createdOnToDate);
		} else if (filterSearch.equals("filenamewithCustomerNo")) {
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			requestParams.put("fileName", fileName);
		} else if (filterSearch.equals("reportTypeWithdateRange")) {
			createdOnToDate = commonMethods.getCreatedOnToDate();
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			requestParams.put("reportType", reportTypeDescription);
			requestParams.put("sCreatedOnFrom", reportMethods.getCreatedOnFromDate(common.getProcessingDateFromMClients()));
			requestParams.put("sCreatedOnTo", createdOnToDate);
		} else if (filterSearch.equals("reportTypeWithFileName")) {
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			requestParams.put("reportType", reportTypeDescription);
			requestParams.put("fileName", fileName);
		} else if (filterSearch.equals("allFilterCombination")) {
			createdOnToDate = commonMethods.getCreatedOnToDate();
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			requestParams.put("reportType", reportTypeDescription);
			requestParams.put("fileName", fileName);
			requestParams.put("sCreatedOnFrom", reportMethods.getCreatedOnFromDate(common.getProcessingDateFromMClients()));
			requestParams.put("sCreatedOnTo", createdOnToDate);
		} else if(filterSearch.equals("invalidCustomerNo")) {
			requestParams.put("customerNo", "123");
			requestParams.put("reportType", PropUtils.getPropValue(inputProp, "reportTypeDescriptionForStoredReports"));
		}
		else if(filterSearch.equals("invalidReportType")) {
			requestParams.put("customerNo", common.getCustomerNumberFromDB("associatedUser"));
			requestParams.put("reportType", "invalidreport");
		}
		logger.log("Request params for stored reports "+requestParams);
		System.out.println("Request params "+requestParams);
		response = postRequestAsBearerAuthWithBodyData(reportSearchEndPoint,requestParams.toString(),PropUtils.getPropValue(inputProp, "AuthorizationToken"));
		logger.log("Response for stored reports "+response.jsonPath().prettyPrint());
	}

	@Then("^Validate the stored report details from response$")
	public void validate_the_stored_report_details_from_response() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Random rand = new Random();
		JSONObject storedReportDetails;
		JSONParser jsonParser = new JSONParser();
		JSONObject jobj;

		boolean hasMoreReports = false;
		String reportsResponse = response.getBody().asString();
		if (response.getStatusCode() == 200) {

			hasMoreReports = response.jsonPath().get("hasMoreReports");

			if (hasMoreReports||hasMoreReports==false&&reportsResponse.contains("storedReportOid")) {
				jobj = (JSONObject) jsonParser.parse(reportsResponse);
				System.out.println("JSON object " + jobj);
				JSONArray storedReportsResponse = (JSONArray) jobj.get("storedReportsList");
				System.out.println("Total number of Stored reports " + storedReportsResponse.size());
				int actualCount = rand.nextInt(storedReportsResponse.size());
				System.out.println("Actual count " + actualCount);
				storedReportDetails = (JSONObject) storedReportsResponse.get(actualCount);

				logger.log("Response for specific report " + storedReportDetails);
				reportTypeDescription = storedReportDetails.get("reportTypeDescription").toString();
				fileName = storedReportDetails.get("fileName").toString();
				//		PropUtils.setProps("reportTypeDescriptionForStoredReports", reportTypeDescription, inputDataFilePath);
				//		PropUtils.setProps("fileNameForStoredReports", fileName, inputDataFilePath);

				//	refreshPropertiesFile();
				//	System.out.println("Report type description is "
				//			+ PropUtils.getPropValue(inputProp, "reportTypeDescriptionForStoredReports"));
				//	System.out.println(
				//			"File name description is " + PropUtils.getPropValue(inputProp, "fileNameForStoredReports"));
				System.out.println("Has more reports " + hasMoreReports);
				reportMethods.validateStoredReportFilenameFromDB(reportTypeDescription);
				logger.log("Validated the filename from DB");

			}
			else{
				System.out.println("Customer does not have any reports");
			}
		}
		else if (response.getStatusCode() == 401) {
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);
			validateStatusNumber(statusNumberFromResponse, 97102, logger,
					"Expected status number from response is " + statusNumberFromResponse);
			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);
			validateResponseMessage(statusMessageFromResponse, "API User is unauthorized to access this customer", logger,
					"Expected status message from response is " + statusMessageFromResponse);
		}
		else if (response.getStatusCode()==400) {
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);
			validateStatusNumber(statusNumberFromResponse, 6, logger,
					"Expected status number from response is " + statusNumberFromResponse);
			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);
			validateResponseMessage(statusMessageFromResponse, "Invalid entry", logger,
					"Expected status message from response is " + statusMessageFromResponse);
		}
	}

	@When("^user should able to get the report categories$")
	public void user_should_able_to_get_the_report_categories() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String reportCategoryEndPoint = PropUtils.getPropValue(inputProp, "lookupForReportCategory");
		userdetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
		userOid = userdetails.get("USER_OID");
		memberTypeDescriptionFromDB = common.getMemberTypeDescriptionFromConstantsTable(userOid);
		response = getRequestAsBearerAuthWithSingleQueryParam(reportCategoryEndPoint,PropUtils.getPropValue(inputProp, "AuthorizationToken"),"memberType",memberTypeDescriptionFromDB);
		//  System.out.println("Response for report categories "+response.jsonPath().prettyPrint());
		logger.log("Report categories from response" +response.jsonPath().prettyPrint());
	}
	@Then("^Validate the report categories from response$")
	public void validate_the_report_categories_from_response() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String reportCategoryDescription = reportMethods.getJSONArrayDesc();
		PropUtils.setProps("reportCategory", reportCategoryDescription, inputDataFilePath);
		refreshPropertiesFile();
		logger.log("Specific report category from response "+reportCategoryDescription);
	}

	@When("^user should able to get the report types based on report category$")
	public void user_should_able_to_get_the_report_types_based_on_report_category() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String reportTypeEndPoint =  PropUtils.getPropValue(inputProp, "lookupForReportTypes");
		String reportCategory = PropUtils.getPropValue(inputProp, "reportCategory");
		System.out.println("Report category from property file "+reportCategory);
		response = getRequestAsBearerAuthWithSingleQueryParam(reportTypeEndPoint+reportCategory,PropUtils.getPropValue(inputProp, "AuthorizationToken"),"memberType",memberTypeDescriptionFromDB);
		logger.log("Report types response "+response.jsonPath().prettyPrint());
	}


	@Then("^validate the report types from response$")
	public void validate_the_report_types_from_response() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		boolean isValidatedReportTypesBasedOnReportCategory = false;
		String reportypeDesc = reportMethods.getReportTypesBasedOnReportCategories();
		PropUtils.setProps("reportTypeBasedOnReportCategory", reportypeDesc, inputDataFilePath);
		isValidatedReportTypesBasedOnReportCategory = reportMethods.validateReportResponseBasedOnReportcategoryDesc();
		if(isValidatedReportTypesBasedOnReportCategory) {
			logger.log("Validations is done for report types based on report categories both isAdhoc and isOnlineScheduled is Y as validated");
		}
		else{
			logger.log("Validations is not done for report types from response and DB");
		}
	}
	@When("^user should able to get the report params based on selected report types and customer number \"([^\"]*)\"$")
	public void user_should_able_to_get_the_report_params_based_on_selected_report_types_and_customer_number(String customerScenarioType) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String reportParamEndPoint = PropUtils.getPropValue(inputProp, "reportParamEndPoint");
		System.out.println("Report param end point "+reportParamEndPoint);
		String reportTypeFromProp = PropUtils.getPropValue(inputProp, "reportTypeBasedOnReportCategory");
		String cusNo = "";
		System.out.println("Customer scenario type is "+customerScenarioType);
		if(customerScenarioType.equals("associatedCustomerNo")) {
			cusNo = common.getCustomerNumberFromDB("associatedUser");
		}
		else if(customerScenarioType.equals("invalidCustomerNo")) {
			cusNo = "123";
		}
		else if(customerScenarioType.equals("unassociatedCusNo")) {
			cusNo = common.getCustomerNumberFromDB("") ;
		}
		response = getRequestAsBearerAuthWithSingleQueryParam(reportParamEndPoint+cusNo,PropUtils.getPropValue(inputProp, "AuthorizationToken"),"reportType",reportTypeFromProp);
		logger.log("Report params response "+response.jsonPath().prettyPrint());
	}
	@Then("^validate the response message for status number \"([^\"]*)\" and statusMessage \"([^\"]*)\"$")
	public void validate_the_response_message_for_status_number_and_statusMessage(String statusNumber, String statusMessage) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (statusNumber.equals("") && statusMessage.equals("")
				|| (statusNumber.equals(null) && statusMessage.equals(null))) {
			System.out.println("Inside of if statement");
			boolean isReportParamsValidated = false;
			isReportParamsValidated = reportMethods.validateReportParamsFromResponseAndDB();
			if(isReportParamsValidated) {
				System.out.println("Inside of IF statement");
				logger.log("Validations is done for report params and validated the repsonse and DB");
			}
			else{
				logger.log("Validations is not done for report params and not validated the repsonse and DB");
			}
		}
		else {
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);
			validateStatusNumber(statusNumberFromResponse,Integer.parseInt(statusNumber), logger,
					"Expected status number from response is " + statusNumberFromResponse);
			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);
			validateResponseMessage(statusMessageFromResponse,statusMessage , logger,
					"Expected status message from response is " + statusMessageFromResponse);
		}
	}
	@When("^user wants to create scheduled report with key as \"([^\"]*)\" and value as \"([^\"]*)\"$")
	public void user_wants_to_create_scheduled_report_with_key_as_and_value_as(String param, String value)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Faker faker = new Faker();
		System.out.println("Param is " + param);
		System.out.println("Value is " + value);
		String createScheduledReportEndPoint = PropUtils.getPropValue(inputProp, "createScheduledReport");
		if (value.equals("validReportParams")) {
			requestParams.put(param, reportMethods.createReportTemplateParams());
		} else if (value.equals("validReportType")) {
			requestParams.put(param, commonMethods
					.getDescriptionAsJSONObjectFormat(PropUtils.getPropValue(inputProp, "reportTypeBasedOnReportCategory")));
		} else if (value.equals("invalidReportType")) {
			requestParams.put(param, commonMethods.getDescriptionAsJSONObjectFormat(""));
		} else if (value.equals("Y")) {
			requestParams.put(param, "Y");
		} else if (value.equals("N")) {
			requestParams.put(param, "N");
		} else if (value.equals("validEmailAddress")) {
			requestParams.put(param, faker.name().firstName() + "@gmail.com");
		} else if (value.equals("validScheduleReportName")) {
			scheduledReportTemplateName = faker.name().firstName() + "_report";
			requestParams.put(param,scheduledReportTemplateName );
		} else if (value.equals("existingScheduleReportName")) {
			List<Map<String,String>> templateDetails = null;
			Map<String, String> specificReportDetails = null;
			userdetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
			userOid = userdetails.get("USER_OID");
			templateDetails = common.getReportTypeAndTemplateNameDescription(userOid,PropUtils.getPropValue(inputProp, "reportTypeBasedOnReportCategory"));
			System.out.println("List of reports is "+templateDetails.size());
			int actualCount = rand.nextInt(templateDetails.size());
			System.out.println("Actual count "+actualCount);
			specificReportDetails = templateDetails.get(actualCount);
			System.out.println("Template name is "+specificReportDetails.get("REPORT_TEMPLATE_DESCRIPTION"));
			requestParams.put(param, specificReportDetails.get("REPORT_TEMPLATE_DESCRIPTION"));
		} else if (value.equals("validFrequency")) {
			requestParams.put(param, reportMethods.getReportFrequencyDesc(PropUtils.getPropValue(inputProp, "reportTypeBasedOnReportCategory")));
		}
		else if (value.equals("invalidFrequency")) {
			requestParams.put(param, faker.name().firstName());
		}
		else if (value.equals("validDeliveryType")) {
			requestParams.put(param, reportMethods.getReportDeliveryTypeDesc(PropUtils.getPropValue(inputProp, "reportTypeBasedOnReportCategory")));
		} else if (value.equals("invalidDeliveryType")) {
			requestParams.put(param, faker.name().firstName());
		}
		logger.log("Request params "+requestParams);
		System.out.println("Request params for create sceduled report " + requestParams);
		response = postRequestAsBearerAuthWithBodyData(createScheduledReportEndPoint, requestParams.toString(),
				PropUtils.getPropValue(inputProp, "AuthorizationToken"));

	}
	@Then("^user validates the status number \"([^\"]*)\" and statusMessage \"([^\"]*)\" for created schedule report$")
	public void user_validates_the_status_number_and_statusMessage_for_created_schedule_report(String statusNumber, String statusMessage) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (statusNumber.equals("") && statusMessage.equals("")
				|| (statusNumber.equals(null) && statusMessage.equals(null))) {
			System.out.println("Inside of if statement");
			//	PropUtils.setProps("scheduledTemplateNameDesc",scheduledReportTemplateName , inputDataFilePath);
			//	refreshPropertiesFile();
			boolean isNewScheduledReportGenerated = reportMethods.validateNewScheduleReportFromDB(scheduledReportTemplateName);

			if(isNewScheduledReportGenerated) {
				System.out.println("Inside of IF statement");
				logger.log("Validations is done for schedule report creation and validated the repsonse and DB");
			}
			else{
				logger.log("Validations is not done for schedule report creation and not validated the repsonse and DB");
			}
		}
		else {
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);
			validateStatusNumber(statusNumberFromResponse,Integer.parseInt(statusNumber), logger,
					"Expected status number from response is " + statusNumberFromResponse);
			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);
			validateResponseMessage(statusMessageFromResponse,statusMessage , logger,
					"Expected status message from response is " + statusMessageFromResponse);
		}
	}
	@When("^user should able to get the report details of user scheduled report with possible combination as \"([^\"]*)\"$")
	public void user_should_able_to_get_the_report_details_of_user_scheduled_report_with_possible_combination_as(
			String scenarioType) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("Possible combinations is " + scenarioType);
		String scheduleReportEndPoint = PropUtils.getPropValue(inputProp, "detailsOfScheduledReportEndPoint");

		JSONParser jsonParser = new JSONParser();
		JSONObject jobj;
		String UserScheduledReportResponse = response.getBody().asString();

		jobj = (JSONObject) jsonParser.parse(UserScheduledReportResponse);

		JSONArray reportAssignmentsList = (JSONArray) jobj.get("reportAssignmentList");
		System.out.println("Total number of Scheduled reports " + reportAssignmentsList.size());
		int actualCount = rand.nextInt(reportAssignmentsList.size());

		userReportDetails = (JSONObject) reportAssignmentsList.get(actualCount);


		if (scenarioType.equals("associatedReportTypeWithValidDescription")) {

			requestParams.put("reportType",commonMethods.getDescriptionAsJSONObjectFormat(userReportDetails.get("reportType").toString()));
			requestParams.put("description", userReportDetails.get("description"));
		} else if (scenarioType.equals("unassociatedReportTypeWithValidDescription")) {
			String reportDescription = reportMethods.getReportTypeBasedOnUserFromDB(userOid,"");
			requestParams.put("reportType", commonMethods.getDescriptionAsJSONObjectFormat(reportDescription));
			requestParams.put("description", userReportDetails.get("description"));
		} else if (scenarioType.equals("associatedReportTypeWithInvalidDescription")) {
			requestParams.put("reportType",commonMethods.getDescriptionAsJSONObjectFormat(userReportDetails.get("reportType").toString()));
			requestParams.put("description", reportMethods.getTemplateNameFromDB("invalidTemplateName"));
		}
		logger.log("Request params for scheduled report details " + requestParams);
		response = postRequestAsBearerAuthWithBodyData(scheduleReportEndPoint, requestParams.toString(),
				PropUtils.getPropValue(inputProp, "AuthorizationToken"));

	}
	@Then("^validates the user scheduled report details from response$")
	public void validates_the_user_scheduled_report_details_from_response() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if(response.getStatusCode()==200) {
			logger.log("Response payload for scheduled report details "+response.jsonPath().prettyPrint());
			boolean isSchedulredReportDetailsValidated = reportMethods.validateScheduledReportDetailsBasedOnTemplateName();
			if(isSchedulredReportDetailsValidated) {
				logger.log("Validation is done for scheduled rpeort details from response and DB ");

			}
			else {
				logger.log("Validation is failed for scheduled rpeort details from response and DB ");
			}
		}
		else {
			System.out.println("For that user does not have any reports");
			logger.log("For respective user does not have any reports");
		}

	}
	@When("^user gets the list of scheduled reports based on fliter combination \"([^\"]*)\"$")
	public void user_gets_the_list_of_scheduled_reports_based_on_fliter_combination(String filterCombination) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		userdetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
		userOid = userdetails.get("USER_OID");
		if(filterCombination.equals("withoutPassingAnyfilters")) {

		}
		else if(filterCombination.equals("reportTypeWithTemplateName")) {
			requestParams.put("reportTypes", reportMethods.getReportTypeBasedOnArrayFormat(userOid,filterCombination,"associatedUserScheduledReport" ));
			requestParams.put("description", reportMethods.getTemplateNameFromDB("validTemplateName"));
		}

		else if(filterCombination.equals("allCombinationFilter")) {
			requestParams.put("reportTypes", reportMethods.getReportTypeBasedOnArrayFormat(userOid,filterCombination,"associatedUserScheduledReport"));
			requestParams.put("description", reportMethods.getTemplateNameFromDB("validTemplateName"));
			requestParams.put("isEnabled",reportMethods.getIsEnbaledStatusFromDB());
			requestParams.put("sCreatedOnTo", reportMethods.getCreatedOnToDateForScheduledreport());

		}
		else if(filterCombination.equals("multipleReportTypeFilter")) {
			requestParams.put("reportTypes", reportMethods.getReportTypeBasedOnArrayFormat(userOid,filterCombination,"associatedUserScheduledReport"));

		}
		else if(filterCombination.equals("invalidTemplateNameWithReportType")) {

			requestParams.put("reportTypes", reportMethods.getReportTypeBasedOnArrayFormat(userOid,filterCombination,"associatedUserScheduledReport"));
			requestParams.put("description", reportMethods.getTemplateNameFromDB("invalidTemplateName"));
		}
		else if(filterCombination.equals("unassociatedReportTypeWithTemplateName")) {

			requestParams.put("reportTypes", reportMethods.getReportTypeBasedOnArrayFormat(userOid,filterCombination,""));
			requestParams.put("description", reportMethods.getTemplateNameFromDB("validTemplateName"));
		}
		logger.log("Request params is "+requestParams.toString());

		response = postRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "searchScheduledReportEndPoint"),requestParams.toString(),PropUtils.getPropValue(inputProp, "AuthorizationToken"));

		logger.log("Response for search scheduled report is "+response.jsonPath().prettyPrint());

	}

	@Then("^validate the specific report details object from response$")
	public void validate_the_specific_report_details_object_from_response() throws Throwable {
		// Write code here that turns the phrase above into concrete actions


		Map<String, String> UserScheduledReportDetails = new HashMap<String, String>();

		userdetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
		userOid = userdetails.get("USER_OID");
		boolean hasMoreReports;

		JSONParser jsonParser = new JSONParser();
		JSONObject jobj;
		String scheduledReportResponse = response.getBody().asString();
		if (response.getStatusCode() == 200) {

			hasMoreReports = response.jsonPath().get("hasMoreReports");

			if (hasMoreReports || hasMoreReports == false && scheduledReportResponse.contains("reportAssignmentList")) {
				jobj = (JSONObject) jsonParser.parse(scheduledReportResponse);

				JSONArray reportAssignmentsList = (JSONArray) jobj.get("reportAssignmentList");
				System.out.println("Total number of Scheduled reports " + reportAssignmentsList.size());
				int actualCount = rand.nextInt(reportAssignmentsList.size());

				userReportDetails = (JSONObject) reportAssignmentsList.get(actualCount);
				System.out.println("User scheduled report details is " + userReportDetails);
				String reportType = userReportDetails.get("reportType").toString();
				templateNameDescription = userReportDetails.get("description").toString();
				System.out.println("descrition is " + templateNameDescription);
				String createdOn = userReportDetails.get("createdOn").toString();
				System.out.println("createdOn is " + createdOn);
				UserScheduledReportDetails = common.getAllScheduledRepordetails(userOid, templateNameDescription,reportType);
				if (reportType.equals(UserScheduledReportDetails.get("REPORT_TYPE_DESCRITPTION"))
						&& templateNameDescription
						.equals(UserScheduledReportDetails.get("REPORT_ASSIGNMENT_DESCRIPTION"))
						&& userReportDetails.get("frequencyDescription").toString()
						.equals(UserScheduledReportDetails.get("FREQUENCY_DESCRIPTION"))
						&& userReportDetails.get("isEnabled").toString()
						.equals(UserScheduledReportDetails.get("IS_ENABLED"))||userReportDetails.get("emailAddress").toString().equals(UserScheduledReportDetails.get("EMAIL_ADDRESS"))) {
					logger.log("Validations is done for search scheduled reports");
				} else {
					logger.log("Validation is not done for search scheduled report ");
				}
			}
			else {
				logger.log("For invalid cases does not have any reports");
			}
		}
	}

	@SuppressWarnings("unchecked")
	@When("^user able to edit a scheduled report with all inputs based on scenario type as \"([^\"]*)\"$")
	public void user_able_to_edit_a_scheduled_report_with_all_inputs_based_on_scenario_type_as(String editReportScenarioType) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("REsponse for report deatils "+response.jsonPath().prettyPrint());
		JSONParser jsonParser = new JSONParser();
		JSONObject jobjRequestParams, reportparamObject;
		JSONArray reportAssignmentParams =  new JSONArray();
		String scheduledReportResponse = response.getBody().asString();
		jobjRequestParams = (JSONObject) jsonParser.parse(scheduledReportResponse);
		String templateDescFromResponse =  jobjRequestParams.get("description").toString();
		editReportTypeDescFromResponse = jobjRequestParams.get("reportType").toString();

		System.out.println("Request params for edit report "+jobjRequestParams);

		if(editReportScenarioType.equals("validInputs")) {

			String frequencyDescriptionFromResponse = jobjRequestParams.get("frequencyDescription").toString();
			System.out.println("Frequency Description from response is "+frequencyDescriptionFromResponse);
			String deliverTypeFromResponse = jobjRequestParams.get("deliveryType").toString();
			System.out.println("Delivery type from response is "+deliverTypeFromResponse);
			if(jobjRequestParams.containsKey("emailAddress")) {
				emailAddressForEditReport = faker.name().firstName() +"@gmail.com";
				jobjRequestParams.replace("emailAddress", emailAddressForEditReport);
			}
			else {
				logger.log("For Edit request params does not have email address");
			}
			templateNameForEditReport = faker.name().lastName()+"_report";
			//updating template name
			jobjRequestParams.replace("description",templateNameForEditReport );
			//updating report assignment params
			reportAssignmentParams = (JSONArray) jobjRequestParams.get("reportAssignmentParameters");
			reportparamObject = (JSONObject) reportAssignmentParams.get(0);
			System.out.println("REport param object "+reportparamObject);
			jobjRequestParams.replace("value", common.getCustomerNumberFromDB("associatedUser"));
			//updating frequency and delivery type
			frequencyDescForEditReport = reportMethods.getReportFrequencyDesc(editReportTypeDescFromResponse);
			deliveryTypeForEditReport = reportMethods.getReportDeliveryTypeDesc(editReportTypeDescFromResponse);
			if(!frequencyDescriptionFromResponse.equals(frequencyDescForEditReport)) {
				System.out.println("Inside of frequency description if");
				jobjRequestParams.replace("frequencyDescription", frequencyDescForEditReport);
			}
			else {
				frequencyDescForEditReport = reportMethods.getReportFrequencyDesc(editReportTypeDescFromResponse);
				jobjRequestParams.replace("frequencyDescription", frequencyDescForEditReport);
			}
			if(!deliverTypeFromResponse.equals(deliveryTypeForEditReport)) {
				System.out.println("Inside of delivery description if");
				jobjRequestParams.replace("deliveryType", deliveryTypeForEditReport);
			}
			else {
				deliveryTypeForEditReport = reportMethods.getReportDeliveryTypeDesc(editReportTypeDescFromResponse);
				jobjRequestParams.replace("deliveryType", deliveryTypeForEditReport);
			}
		}
		else if(editReportScenarioType.equals("existingTemplateName")){
			List<Map<String,String>> templateDetails = null;
			Map<String, String> specificReportDetails = null;
			templateDetails = common.getReportTypeAndTemplateNameDescription(userOid,editReportTypeDescFromResponse);
			System.out.println("List of reports is "+templateDetails.size());
			int actualCount = rand.nextInt(templateDetails.size());
			System.out.println("Actual count "+actualCount);
			specificReportDetails = templateDetails.get(actualCount);
			System.out.println("Template name is "+specificReportDetails.get("REPORT_TEMPLATE_DESCRIPTION"));

			if(templateDescFromResponse.equals(specificReportDetails.get("REPORT_TEMPLATE_DESCRIPTION"))) {
				logger.log("Report tempalte name is same as not able to edit the report details ");
			}
			else {
				jobjRequestParams.replace("description", specificReportDetails.get("REPORT_TEMPLATE_DESCRIPTION"));
			}
		}
		else if(editReportScenarioType.equals("invalidFrequencyType")){
			jobjRequestParams.replace("frequencyDescription", "invalidFrequency");
		}
		else if(editReportScenarioType.equals("invalidDeliverType")){
			jobjRequestParams.replace("deliveryType", "invalidDelivery");
		}
		logger.log("Request params for edit report is "+jobjRequestParams);
		response = putRequestAsBearerAuthWithBodyData(PropUtils.getPropValue(inputProp, "detailsOfScheduledReportEndPoint"),jobjRequestParams.toString(),PropUtils.getPropValue(inputProp,  "AuthorizationToken"));
	}
	@Then("^validate the updated scheduled report details from response and DB with statusNumber as \"([^\"]*)\" and statusMessage \"([^\"]*)\"$")
	public void validate_the_updated_scheduled_report_details_from_response_and_DB_with_statusNumber_as_and_statusMessage(String statusNumber, String statusMessage) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if ((response.getStatusCode()==200)&&statusNumber.equals("") && statusMessage.equals("")
				|| (statusNumber.equals(null) && statusMessage.equals(null))) {

			Map<String,String> newReportDetails = new HashMap<String,String>();
			newReportDetails = common.getAllScheduledRepordetails(userOid,templateNameForEditReport,editReportTypeDescFromResponse);
			if(templateNameForEditReport.equals(newReportDetails.get("REPORT_ASSIGNMENT_DESCRIPTION"))
					&& frequencyDescForEditReport.equals(newReportDetails.get("FREQUENCY_DESCRIPTION"))
					&&	deliveryTypeForEditReport.equals(newReportDetails.get("DELIVERY_TYPE_DESCRIPTION")) ){
				System.out.println("Inside of if statement");
				logger.log("Validation is done for edit report details from DB");
			}
			else {
				logger.log("Validations is not done for edit report details from DB")	;
			}
		}
		else if(response.getStatusCode()==400||response.getStatusCode()==401){
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);

			validateStatusNumber(statusNumberFromResponse,Integer.parseInt(statusNumber), logger,
					"Expected status number from response is " + statusNumberFromResponse);

			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);

			validateResponseMessage(statusMessageFromResponse,statusMessage , logger,
					"Expected status message from response is " + statusMessageFromResponse);

		}
		else {
			logger.log("Not getting expected response ");
		}

	}


	@Then("^user able to change the scheduled report status based on \"([^\"]*)\"$")
	public void user_able_to_change_the_scheduled_report_status_based_on(String statusScenarioType) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		JSONParser parser = new JSONParser();
		String editStatusEndPoint = PropUtils.getPropValue(inputProp, "editReportStatus");
		JSONObject reportResponse = (JSONObject) parser.parse(response.asString());

		JSONArray listOfReports = (JSONArray) reportResponse.get("reportAssignmentList");
		// System.out.println("Json Array "+listOfReports);
		int actualCount = rand.nextInt(listOfReports.size());
		System.out.println("Actual count for array list is " + actualCount);
		JSONObject specificStatusObject = (JSONObject) listOfReports.get(actualCount);
		reportTypeFromResponse = specificStatusObject.get("reportType").toString();
		reportTypeDesc = specificStatusObject.get("description").toString();
		isEnabledStatus = specificStatusObject.get("isEnabled").toString();
		requestParams.put("reportType", commonMethods.getDescriptionAsJSONObjectFormat(reportTypeFromResponse));
		requestParams.put("description", reportTypeDesc);
		if (statusScenarioType.equals("validStatus")) {
			if (isEnabledStatus.equals("Y")) {
				requestParams.put("isEnabled", "N");
			} else if (isEnabledStatus.equals("N")) {
				requestParams.put("isEnabled", "Y");
			}
		} else {
			requestParams.put("isEnabled", "invalidStatus");
		}
		logger.log("Requets params for edit scheduled report " + requestParams);
		response = putRequestAsBearerAuthWithBodyData(editStatusEndPoint, requestParams.toString(),
				PropUtils.getPropValue(inputProp, "AuthorizationToken"));

	}

	@Then("^Validate the updated scheduled report details from response$")
	public void validate_the_updated_scheduled_report_details_from_response() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (response.getStatusCode() == 200) {
			System.out.println("Inside of if");
			userdetails = common.getUserDetailsFromDB(PropUtils.getPropValue(inputProp, "UserNameAPI"));
			userOid = userdetails.get("USER_OID");
			Map<String, String> scheduledReportDetails;
			scheduledReportDetails = common.getAllScheduledRepordetails(userOid, reportTypeDesc,
					reportTypeFromResponse);
			String isEnabledStatusFromDB = scheduledReportDetails.get("isEnabled");
			if (!isEnabledStatus.equals(isEnabledStatusFromDB)) {
				logger.log("Validation is done for edit scheduled report status");

			} else {
				logger.log("Validation is not done for edit scheduled report status");
			}

		} else {
			int statusNumberFromResponse = response.path("errors.statusNumber");
			System.out.println("Status Number from response is " + statusNumberFromResponse);

			validateStatusNumber(statusNumberFromResponse, 13520, logger,
					"Expected status number from response is " + statusNumberFromResponse);

			String statusMessageFromResponse = response.path("errors.statusMessage");
			System.out.println("Status Message from response is " + statusMessageFromResponse);

			validateResponseMessage(statusMessageFromResponse, "Invalid Report Template status.", logger,
					"Expected status message from response is " + statusMessageFromResponse);

		}
	}
}
