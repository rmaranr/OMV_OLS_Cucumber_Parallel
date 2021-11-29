package stepDefinitions.stepDefinitionsAPI;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.Assert;
import utilities.api.PropUtils;
import utilities.api.RequestMethodsUtils;

public class OLSLogout extends RequestMethodsUtils {
    public io.cucumber.java.Scenario logger;

    public OLSLogout(HooksAPI hooksAPI){
        this.logger = hooksAPI.logger;
    }

    @Given("^Get access token from the login API$")
    public void get_access_token_from_the_login_API() throws Throwable  {
        RestAssured.baseURI = PropUtils.getPropValue(inputProp,"baseURL");

    }
    @When("^user post the request for logout endpoint \"([^\"]*)\"$")
    public void user_post_the_request_for_logout_endpoint(String endPoint) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        response = postRequestAsBearerAuthWithNoBodyData(endPoint, PropUtils.getPropValue(inputProp, "AuthorizationToken"));
    }

    @Then("^the response status code should be (\\d+)$")
    public void the_response_status_code_should_be(int statusCode) {
        // Write code here that turns the phrase above into concrete actions
        int responseStatus = response.getStatusCode();
        System.out.println("Response code is ::" +responseStatus);
        if (response.statusCode() == statusCode) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }


    }


}
