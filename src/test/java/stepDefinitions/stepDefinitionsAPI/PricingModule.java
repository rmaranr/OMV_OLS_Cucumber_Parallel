package stepDefinitions.stepDefinitionsAPI;
import com.github.javafaker.Faker;
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

import javax.json.Json;
import javax.json.JsonObject;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import utilities.api.RequestMethodsUtils;

public class PricingModule extends RequestMethodsUtils {
    Faker faker = new Faker();
    public Scenario logger;
    CommonDBUtils common = new CommonDBUtils();
    CommonMethods commonMethods = new CommonMethods();
    public PricingModule(HooksAPI hooksAPI) {
        this.logger = hooksAPI.logger;
    }

    @When("^user add pricing rules for the client$")
    public void user_add_pricing_rules_for_the_client() throws ParseException {
        String addPricingRuleEndPoint = PropUtils.getPropValue(inputProp, "Add_PricingRule");
        List<Map<String, String>> products = common.getListOfProducts();
        int productsize = getRandomNumberInBetween(0, products.size());
        String productDescription = products.get(productsize).get("DESCRIPTION");
        System.out.println("productDescription :" + productDescription);
        List<Map<String, String>> outletDiscriptions = common.getListOfOutlets();
        int outletsize = getRandomNumberInBetween(0, outletDiscriptions.size());
        String outlet = outletDiscriptions.get(outletsize).get("DESCRIPTION");
        System.out.println("outlet :" + outlet);
        List<Map<String, String>> networkDiscriptions = common.getListOfNetworks();
        int networksize = getRandomNumberInBetween(0, networkDiscriptions.size());
        String network = networkDiscriptions.get(networksize).get("DESCRIPTION");
        System.out.println("network :" + network);
        List<Map<String, String>> merchantDiscriptions = common.getListOfMerchants();
        int merchantsize = getRandomNumberInBetween(0, merchantDiscriptions.size());
        String merchant = merchantDiscriptions.get(merchantsize).get("DESCRIPTION");
        System.out.println("merchant :" + merchant);
        String effectiveDate = common.changingDateFormat("Current", "DBFormatforPricing");
        System.out.println("currentDate --> " + effectiveDate);
        String description = faker.name().firstName();
        String userid = PropUtils.getPropValue(inputProp, "ClientUserName");
        requestBodyParams = Json.createObjectBuilder()
                .add("service_description", "OMV-AT")
                .add("card_product_group_description", "AT OMV Stationcard Products")
                .add("network_description", network)
                .add("sub_network_description", outlet)
                .add("merchant_description", merchant)
                .add("product_description", productDescription)
                .add("pricing_evaluation_rule_description", "Best Off")
                .add("agreement_description", "Pump Customer")
                .add("description", description)
                .add("approval_header", Json.createObjectBuilder()
                        .add("header", Json.createObjectBuilder()
                                .add("created_by_user", userid)
                                .add("created_date", effectiveDate))
                        .add("record_status", "Invalid")
                        .add("approval_status", "Pending")
                        .add("valid_from_date", effectiveDate))
                .build();
        logger.log("Request params for add pricing rule----> " + requestBodyParams);
        response = postRequestAsBearerAuthWithBodyData(addPricingRuleEndPoint, requestBodyParams.toString(), PropUtils.getPropValue(inputProp, "AuthorizationToken"));
        System.out.println("Response for add pricing rule" + response.getBody().prettyPrint());
        if (response.getStatusCode() == 200) {


            boolean isPricingRuleNamedisplayed = common.checkingThePresenceofPricingRuleNamefromDB(description);
            if (isPricingRuleNamedisplayed) {
                System.out.println("pricing Rule added successfully");
            } else {
                System.out.println("Pricing Rule not added successfully");
            }
        } else if (response.getStatusCode() == 400) {
            System.out.println("statuscode= " + response.getStatusCode());
        }
    }



}
