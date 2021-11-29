package stepDefinitions.stepDefinitionsAPI;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.api.BaseUtils;
import utilities.api.PropUtils;


public class HooksAPI extends BaseUtils{
//	BaseUtils baseUtils = new BaseUtils();

    private BaseUtils baseUtils;
    public Scenario logger;

    public HooksAPI(BaseUtils baseUtils) {
        this.baseUtils = baseUtils;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        PropUtils.setProps("Scenario_Name", scenarioName, baseUtils.inputDataFilePath);
        String scenarioIds[] = scenario.getId().split(";");
        String featureName = scenarioIds[0];
        this.logger = scenario;
        PropUtils.setProps("Feature_Name", featureName, baseUtils.inputDataFilePath);
    }
    @After
    public void afterScenario(Scenario scenario) throws Exception {
        System.out.println("After scenario");
        String statusOfScenario = scenario.getStatus().toString();
        System.out.println(statusOfScenario);
        baseUtils.updateTestScenarioStatus(statusOfScenario);
    }
}
