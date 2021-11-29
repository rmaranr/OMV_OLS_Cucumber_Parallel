package pages;import com.aventstack.extentreports.ExtentTest;import io.cucumber.java.Scenario;import org.openqa.selenium.By;import org.openqa.selenium.WebDriver;import utilities.api.BaseUtils;import utilities.api.CommonDBUtils;import utilities.api.PropUtils;import utilities.ui.BasePage;import java.util.Map;import java.util.Properties;public class VehiclesPage {    private WebDriver driver;    private BasePage basePage;    CommonDBUtils commonUtils;    private BaseUtils baseUtils;    private UsersPage usersPage;    public Scenario logger;    /* Constructor to get the driver object */    public VehiclesPage(WebDriver driver) {        this.driver = driver;        basePage = new BasePage(driver);        commonUtils = new CommonDBUtils();        baseUtils = new BaseUtils();        usersPage = new UsersPage(driver);    }    private static final By titleHeader = By.xpath("/html/body/app-root/mav-layout/li-theme-container/li-theme-container/home-landing-page/mat-sidenav-container/mat-sidenav-content/app-page-content/div/div/admin-content/div/div/div[3]/admin-grid/app-page-content/div/div/div/mat-accordion/div[1]/mat-expansion-panel/mat-expansion-panel-header/span/div/div[1]/mat-grid-list/div/mat-grid-tile[1]/figure/div[1]/div");    private static final By txtSearchTextBox = By.cssSelector("div[class='mat-form-field-infix']>span>mav-input>input");    private static final By clickGrid = By.cssSelector("span[class=mat-content]");    /*     * Method to verify presence of Summary     */    public void verifyPresenceOfDriver(Scenario logger) {        String driverName = basePage.userGetTextFromWebElement(logger, titleHeader);        logger.log("The Driver Name is : " + driverName);    }    /*    Method to enter search eky words in search text box     */    public void enterSearchKeyWords(Scenario logger, String driverName) {        driverName = basePage.userGetTextFromWebElement(logger, titleHeader);        basePage.userTypeIntoTextField(logger, txtSearchTextBox, driverName);    }    /*  Method to enter search eky words in search text box   */    public void selectGrid(Scenario logger) {        basePage.userClick(logger, clickGrid);    }    /* Method to enter search eky words in search text box  */    public void getVehicleDescription(Scenario logger) {        Properties properties = PropUtils.getProps(baseUtils.testDataFile);        String accountNumber = usersPage.getAccountNumberFromUsersPage(logger);        String  query="select v.DESCRIPTION as Vehicles_Description, c.Description as Status, LICENSE_PLATE,  SUBSTR(v.CARD_NO, LENGTH(v.CARD_NO) - 5, 6) as Card_no \n" +                "from card_vehicles_enquiry v, constants c where v.VEHICLE_STATUS_CID = c.CONSTANT_OID and v.customer_mid in\n" +                "(select CUSTOMER_MID from accounts_view where account_no="+accountNumber+" \n" +                "and CLIENT_MID="+PropUtils.getPropValue(properties,"ClientMID")+")\n" +                "order by initcap (Vehicles_Description) Asc";        Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);        PropUtils.setProps("Vehicles-cardNumber", queryResults.get("CARD_NO"), baseUtils.testDataFilePath);        PropUtils.setProps("Vehicles-vehicleDescription", queryResults.get("VEHICLES_DESCRIPTION"), baseUtils.testDataFilePath);    }    /*    Method to get vehicle details based on the searched keyword     */    public Map<String, String> getVehicleDetailsBasedOnCardNo(Scenario logger){        Properties properties = PropUtils.getProps(baseUtils.testDataFile);        String query = "select v.DESCRIPTION as Vehicles_Description, c.Description as Status, LICENSE_PLATE,  \n" +                "SUBSTR(v.CARD_NO, LENGTH(v.CARD_NO) - 5, 6) as Card_no from card_vehicles_enquiry v\n" +                "left join constants c on c.constant_oid = v.vehicle_status_cid\n" +                "left join m_customers mcust on mcust.customer_mid = v.customer_mid\n" +                "left join cards c on c.card_oid = v.card_oid\n" +                "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +                "where mcust.customer_no = "+PropUtils.getPropValue(properties,"accountNo")                +" and mc.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")                +" and c.card_no like ('%"+PropUtils.getPropValue(properties,"vehicle-cardNo")+"')";        Map<String, String>queryResults = commonUtils.getQueryResultsOnMap(query);        if(queryResults.size()==0){            PropUtils.setProps("testStatus","Skipped",baseUtils.testDataFilePath);        }        return queryResults;    }}