package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utilities.api.CommonDBUtils;
import utilities.api.CommonMethods;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DocumentSetupModule extends RequestMethodsUtils {
    public Scenario logger;
    String cardUpdateEndPoint = PropUtils.getPropValue(inputProp, "cardUpdate");
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    public DocumentSetupModule(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @When("^user assign report to the selected customer\"([^\"]*)\"$")
    public void user_assign_report_to_the_selected_customer(String customerNumber)
    {
        String createdocumentsetupEndPoint = PropUtils.getPropValue(inputProp, "Document_Setup");
        customerNumber = commonMethods.getCustomerNoForCSRLogin();
        List<Map<String, String>> description = common.getReportType();
        int size = getRandomNumberInBetween(0,description.size());
        String frequency="";
        String descriptionname = description.get(size).get("DESCRIPTION");
        if (descriptionname.equals("Bulk Card Order") ||  descriptionname.equals("Bulk Card Update"))
        {
            frequency = "Report Immediate";
        }
        else if (descriptionname.equals("Client Credit Limit Alert") || descriptionname.equals("Customer Credit Limit Alert"))
        {
            frequency = "Alerts Immediate";
        }
        else{
            frequency = "Invoice cycle";
    }
        System.out.println("Description name="+descriptionname);
        requestBodyParams = Json.createObjectBuilder()
        .add("reportType", Json.createObjectBuilder()
                .add("description",descriptionname))
                .add("customerNo", customerNumber)
                .add("description",descriptionname)
                .add("frequencyDescription", frequency)
                .add("deliveryType", "Email")
                .add("contactHierarchy", "Card")
                .build();
        logger.log("Request params for assign report----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(createdocumentsetupEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for assign report"+response.getBody().prettyPrint());
        inputProp.setProperty("RepotType", descriptionname);
        inputProp.setProperty("pinnedcustomerno", customerNumber);


    }

    @Then("^validate and verify report type$")
    public void validate_and_verify_report_type() throws Throwable{

        if(response.getStatusCode() == 200)
        {
            String reportName = PropUtils.getPropValue(inputProp, "RepotType");
            String customerNumber = PropUtils.getPropValue(inputProp, "pinnedcustomerno");
            System.out.println("reportName =" + reportName);
            System.out.println("customerNo =" + customerNumber);
            boolean isReportNamedisplayed = common.checkingThePresenceofReportTypeNamefromDB(reportName, customerNumber);
            if (isReportNamedisplayed) {
                System.out.println("Report added successfully");
            } else {
                System.out.println("Report not added successfully");
            }
        }
        else if (response.getStatusCode() == 400)
        {
            System.out.println("statuscode= "+response.getStatusCode());
        }

    }

    @When("^user edit report to the selected customer\"([^\"]*)\"$")
    public void user_edit_report_to_the_selected_customer(String customerNumber) throws ParseException {
        String editdocumentEndPoint = PropUtils.getPropValue(inputProp, "Edit_Report");
        String getdocumentEndPoint = PropUtils.getPropValue(inputProp, "Get_Report");
        customerNumber = commonMethods.getCustomerNoForCSRLogin();
        response = getRequestAsBearerAuth(getdocumentEndPoint + "/" + customerNumber, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("get rport repsonse"+response.asString());
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject getReports = (org.json.simple.JSONObject) parser.parse(response.asString());
        System.out.println("getreport object:"+getReports.getOrDefault("reportAssignmentList",1));
        org.json.simple.JSONArray reportObject = (JSONArray) getReports.getOrDefault("reportAssignmentList",0);
        System.out.println("Reposnse of reportObject"+reportObject.get(0));
        org.json.simple.JSONObject getReport = (org.json.simple.JSONObject)reportObject.get(0);
        getReport.replace("deliveryType","Email");
        getReport.replace("contactHierarchy","Management");

        inputProp.setProperty("assignmentoid", String.valueOf(getReport.get("reportAssignmentOid")));

        JsonObject editReportObject;
        editReportObject = Json.createObjectBuilder().add("customerNo",customerNumber).addAll(Json.createObjectBuilder(getReport)).build();
        response = putRequestAsBearerAuthWithBodyDataWithQueryParam(editdocumentEndPoint, editReportObject.toString(), authorizationToken, false);
        System.out.println("get updated rport repsonse : "+response.asString());

    }

    @Then ("^validate and verify updated fields \"([^\"]*)\" and \"([^\"]*)\" from db$")
    public void validate_and_verify_updated_fields_and_from_db(String deliveryType, String contactHierarchy) throws Throwable{
//        String expectedDeliveryType = deliveryType;
//        String expectedContactHierarchy = contactHierarchy;
        List<String> expectedDeliveryTypeandContactHierarchy= new ArrayList<>(Arrays.asList(deliveryType, contactHierarchy));
        System.out.println("expected :"+expectedDeliveryTypeandContactHierarchy);
        if(response.getStatusCode() == 200) {
            String reportAssignmentOid = PropUtils.getPropValue(inputProp, "assignmentoid");
            List<Map<String, String>> deliveryTypeandContactHierarchy = common.getDeliverytTypeandContactHierarcy(reportAssignmentOid );
            List<String> actualDeliveryTypeandContactHierarchy= new ArrayList<>(Arrays.asList(deliveryTypeandContactHierarchy.get(0).get("DESCRIPTION"),deliveryTypeandContactHierarchy.get(1).get("DESCRIPTION")));
            System.out.println("Actual :"+actualDeliveryTypeandContactHierarchy);
            System.out.println("expectedDeliveryTypeandContactHierarchy : "+expectedDeliveryTypeandContactHierarchy.size());
            System.out.println("actualDeliveryTypeandContactHierarchy : "+actualDeliveryTypeandContactHierarchy.size());
            if(expectedDeliveryTypeandContactHierarchy.equals(actualDeliveryTypeandContactHierarchy)){
                System.out.println("Report updated successfully");

            }
            else{
                System.out.println("Report not updated");
            }

            }
        else{
            System.out.println("Errormessage"+response.getStatusCode());
        }

    }
}
