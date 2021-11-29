package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.ui.BasePage;

public class vatNumbersPage {
    private WebDriver driver;
    private BasePage basePage;
    CommonDBUtils commonUtils;
    private BaseUtils baseUtils;
    private CommonPage commonPage;
    public Scenario logger;

    /* Constructor to get the driver object */
    public vatNumbersPage(WebDriver driver) {
        this.driver = driver;
        basePage = new BasePage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        commonPage = new CommonPage(driver);
    }


}
