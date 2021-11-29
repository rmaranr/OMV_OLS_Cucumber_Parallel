package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.BasePage;
import utilities.ui.PasswordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PricingPage {
    private WebDriver driver;
    private BasePage basePage;
    private CommonDBUtils commonUtils;
    private BaseUtils baseUtils;
    private CommonPage commonPage;
    public Scenario logger;

    /* Constructor to get the driver object */
    public PricingPage(WebDriver driver) {
        this.driver = driver;
        basePage = new BasePage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        commonPage = new CommonPage(driver);
    }
    /*
    Method to select card or account fee type values
     */
    public void selectCardOrAccountFeeTypeValues(Scenario logger,String noOfRecords, String issueType, String form){
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String query = "";
        List<Map<String, String>> queryResults = new ArrayList<>();
        By dropLocator = null;
        if(issueType.equalsIgnoreCase("Card Issue")){
            query = "select f.description from fees f\n" +
                    "left join constants c on c.constant_oid = f.fee_type_cid\n" +
                    "where c.description = 'Card Issue' and f.client_mid = "+ PropUtils.getPropValue(properties,"ClientMID");
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            dropLocator = By.xpath("(//mav-select[@ng-reflect-name='dropdown'])[1]");
            basePage.userClick(logger, dropLocator);
            for(int i=0;i<=queryResults.size()-1;i++){
                commonPage.verifyPresenceOSpanText(logger,queryResults.get(i).get("DESCRIPTION"));
            }
            basePage.selectRandomValueFromDropDown(logger,By.cssSelector("span[class='mat-option-text']"));
            PropUtils.setProps(form+"-"+issueType,commonPage.getAttributeValue(dropLocator,"ng-reflect-model"),baseUtils.testDataFilePath);
        }else if(issueType.equalsIgnoreCase("Account fees")){
            query = "select f.description from fees f\n" +
                    "left join constants c on c.constant_oid = f.fee_type_cid\n" +
                    "where c.description = 'Account' and f.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            dropLocator = By.xpath("(//mav-select[@ng-reflect-name='dropdown'])[1]");
            basePage.userClick(logger, dropLocator);
            for(int i=0;i<=queryResults.size()-1;i++){
                commonPage.verifyPresenceOSpanText(logger,queryResults.get(i).get("DESCRIPTION"));
            }
            basePage.selectRandomValueFromDropDown(logger,By.cssSelector("span[class='mat-option-text']"));
            for(int i=0;i<=Integer.parseInt(noOfRecords);i++) {
                basePage.userClick(logger, By.xpath("(//mav-select[@ng-reflect-name='dropdown'])["+(i+1)+"]"));
                basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                PropUtils.setProps(form + "-" + issueType+"-"+i, commonPage.getAttributeValue(dropLocator, "ng-reflect-model"), baseUtils.testDataFilePath);
                basePage.userTypeIntoTextField(logger,By.xpath("(//mav-input[@name='value']/input)["+(i+1)+"]"), PasswordGenerator.generateNumeric(1));
                PropUtils.setProps(form + "-value"+"-"+i, commonPage.getAttributeValue(dropLocator, "ng-reflect-model"), baseUtils.testDataFilePath);
                basePage.userClick(logger,By.xpath("(//span[contains(text(),'+Add new')])[1]"));
            }
            PropUtils.setProps("accountFeesSelected",noOfRecords,baseUtils.testDataFilePath);
        }else if(issueType.equalsIgnoreCase("Card fees")){
            query = "select f.description from fees f\n" +
                    "left join constants c on c.constant_oid = f.fee_type_cid\n" +
                    "where c.description = 'Card' and f.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            basePage.userClick(logger, By.xpath("(//mav-select[@ng-reflect-name='dropdown'])[2]"));
            for(int i=0;i<=queryResults.size()-1;i++){
                commonPage.verifyPresenceOSpanText(logger,queryResults.get(i).get("DESCRIPTION"));
            }
            int recordNo = 2;
            if(Integer.parseInt(PropUtils.getPropValue(properties,"accountFeesSelected"))>1){
                recordNo = 2+Integer.parseInt(PropUtils.getPropValue(properties,"accountFeesSelected"));
            }
            basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
            for(int i=1;i<=Integer.parseInt(noOfRecords);i++) {
                basePage.userClick(logger, By.xpath("(//mav-select[@ng-reflect-name='dropdown'])["+(i+recordNo)+"]"));
                basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                PropUtils.setProps(form + "-" + issueType+"-"+i, commonPage.getAttributeValue(By.xpath("(//mav-select[@ng-reflect-name='dropdown'])["+(i+recordNo)+"]"), "ng-reflect-model"), baseUtils.testDataFilePath);
                basePage.userTypeIntoTextField(logger,By.xpath("(//mav-input[@name='value']/input)["+(i+1)+"]"), PasswordGenerator.generateNumeric(1));
                PropUtils.setProps(form + "-value"+"-"+i, commonPage.getAttributeValue(dropLocator, "ng-reflect-model"), baseUtils.testDataFilePath);
                if(i!=Integer.parseInt(noOfRecords)) {
                    commonPage.clickButtonUsingSpan(logger, "+Add new");
                }else{
                    break;
                }
            }
        }else if(issueType.equalsIgnoreCase("Transaction fees")){
            query = "select f.description from fees f\n" +
                    "left join constants c on c.constant_oid = f.fee_type_cid\n" +
                    "where c.description = 'Transaction on Acct' and f.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            dropLocator = By.xpath("(//mav-select[@ng-reflect-name='dropdown'])[2]");
            basePage.userClick(logger, dropLocator);
            for(int i=0;i<=queryResults.size()-1;i++){
                commonPage.verifyPresenceOSpanText(logger,queryResults.get(i).get("DESCRIPTION"));
            }
            basePage.selectRandomValueFromDropDown(logger,By.cssSelector("span[class='mat-option-text']"));
            for(int i=0;i<=Integer.parseInt(noOfRecords);i++){
                basePage.userClick(logger, By.xpath("(//mav-select[@ng-reflect-name='dropdown'])["+(i+2)+"]"));
                basePage.selectRandomValueFromDropDown(logger,By.cssSelector("span[class='mat-option-text']"));
            }
            PropUtils.setProps(form+"-"+issueType,commonPage.getAttributeValue(dropLocator,"ng-reflect-model"),baseUtils.testDataFilePath);
            basePage.userClick(logger,By.xpath("(//span[contains(text(),'+Add new')])[2]"));
        }
    }
    /*
    Method to get customer no which is eligible to create private fee profile
     */
    public void getCustomerNoWhichIsEligibleToCreatePrivateFeeProfile(Scenario logger,String feeType, String isEligibleOrNot){
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String activeAccounts = "select mcust.customer_no from m_customers mcust\n" +
                "inner join accounts a on a.account_no = mcust.customer_no\n" +
                "inner join account_status ast on ast.account_status_oid = a.account_status_oid\n" +
                "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                "where mc.client_mid = "+PropUtils.getPropValue(properties,"ClientMID")+" and ast.description like '%Active%'";
        List<Map<String, String>>activeAccountsQR = commonUtils.getAllRowsOfQueryResultsOnListMap(activeAccounts);
        logger.log("Accounts query : "+activeAccounts);
        String query = "";
        List<Map<String, String>>queryResults = new ArrayList<>();
        if(feeType.equalsIgnoreCase("Card fee")){
            query = "select distinct mcust.customer_no from customer_card_profiles ccp\n" +
                    "inner join fee_profiles fpe on fpe.fee_profile_oid = ccp.fee_profile_oid\n"+
                    "inner join constants c on c.constant_oid = fpe.profile_type_cid\n" +
                    "inner join constants c1 on c1.constant_oid = fpe.profile_category_cid\n" +
                    "inner join m_customers mcust on mcust.customer_mid = fpe.customer_mid\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where c.description = 'Card Fee' and c1.description = 'Private' and mc.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
        }else if(feeType.equalsIgnoreCase("Account and Transaction fee")){
            query = "select distinct mcust.customer_no from accounts a\n" +
                    "inner join fee_profiles fpe on fpe.fee_profile_oid = a.fee_profile_oid\n"+
                    "inner join constants c on c.constant_oid = fpe.profile_type_cid\n" +
                    "inner join constants c1 on c1.constant_oid = fpe.profile_category_cid\n" +
                    "inner join accounts a on a.account_oid = fpe.account_oid\n" +
                    "inner join m_customers mcust on mcust.customer_no = a.account_no\n" +
                    "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where c.description = 'Account Fee' and c1.description = 'Private' and mc.client_mid = "+PropUtils.getPropValue(properties,"ClientMID");
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
        }
        logger.log("Query of : "+feeType+" : "+query);
        if(isEligibleOrNot.equalsIgnoreCase("isnot")){
            if(queryResults.size()>1) {
                PropUtils.setProps("commonAccountNo", queryResults.get(0).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
            }else{
                PropUtils.setProps("testStatus","Skipped",baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason","No customer available to execute this scenario",baseUtils.testDataFilePath);
            }
        }else {
            outerloop:
            for (int i = 0; i <= activeAccountsQR.size() - 1; i++) {
                for (int j = 0; j <= queryResults.size() - 1; j++) {
                    if (activeAccountsQR.get(i).get("CUSTOMER_NO").equalsIgnoreCase(queryResults.get(j).get("CUSTOMER_NO"))) {
                        break;
                    } else if (!activeAccountsQR.get(i).get("CUSTOMER_NO").equalsIgnoreCase(queryResults.get(j).get("CUSTOMER_NO"))) {
                        if (j == queryResults.size() - 1) {
                            PropUtils.setProps("commonAccountNo", activeAccountsQR.get(i).get("CUSTOMER_NO"), baseUtils.testDataFilePath);
                            break outerloop;
                        }
                    }
                }
            }
        }
    }
    /*
    Method to get customer no and fee description
     */
    public void getCustomerNoAndFeeDescription(Scenario logger,String feeCategory, String feeType){
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        String query = "";
        List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
        if(feeType.equalsIgnoreCase("Card fee")){
            if(feeCategory.equalsIgnoreCase("private")) {
                query = "select distinct mcust.customer_no, fpe.description, fpe.FEE_PROFILE_OID from fee_profiles_enquiry fpe\n" +
                        "inner join constants c on c.constant_oid = fpe.profile_type_cid\n" +
                        "inner join constants c1 on c1.constant_oid = fpe.profile_category_cid\n" +
                        "inner join m_customers mcust on mcust.customer_mid = fpe.customer_mid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where c.description = 'Card Fee' and c1.description = 'Private' and fpe.expires_on >= mc.processing_date and mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
            }else if(feeCategory.equalsIgnoreCase("public")){
                query = "select c1.description as FeeCategory,fpe.PROFILE_CATEGORY_CID,fpe.PROFILE_TYPE_CID,fpe.FEE_PROFILE_OID,mcust.customer_no,fpe.description,cp.description as cardProgram,\n" +
                        "apt.description as applicationType,mc.name as country from fee_profiles_enquiry fpe\n" +
                        "inner join constants c on c.constant_oid = fpe.profile_type_cid\n"+
                        "inner join constants c1 on c1.constant_oid = fpe.profile_category_cid\n" +
                        "left join card_programs cp on cp.card_program_oid = fpe.card_program_oid\n" +
                        "left join application_types apt on apt.application_type_oid = fpe.application_type_oid\n" +
                        "left join m_clients mc on mc.client_mid = fpe.client_mid \n" +
                        "left join m_customers mcust on mcust.customer_mid = fpe.customer_mid\n" +
                        "where (fpe.card_program_oid is not null or fpe.application_type_oid is not null or fpe.client_mid is not null)\n" +
                        "and c.description = 'Card Fee' and c1.description = 'Reference' and fpe.expires_on >= mc.processing_date";
            }
        }else if(feeType.equalsIgnoreCase("Account and Transaction fee")){
            if(feeCategory.equalsIgnoreCase("private")) {
                query = "select distinct mcust.customer_no, fpe.description,fpe.FEE_PROFILE_OID from fee_profiles_enquiry fpe\n" +
                        "inner join constants c on c.constant_oid = fpe.profile_type_cid\n" +
                        "inner join constants c1 on c1.constant_oid = fpe.profile_category_cid\n" +
                        "inner join accounts a on a.account_oid = fpe.account_oid\n" +
                        "inner join m_customers mcust on mcust.customer_no = a.account_no\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where c.description = 'Account Fee' and c1.description = 'Private' and fpe.expires_on >= mc.processing_date and mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
            }else if(feeCategory.equalsIgnoreCase("Public")){
                query = "select c1.description as FeeCategory,fpe.PROFILE_CATEGORY_CID,fpe.PROFILE_TYPE_CID,fpe.FEE_PROFILE_OID,mcust.customer_no,fpe.description,cp.description as cardProgram,\n" +
                        "apt.description as applicationType,mc.name as country from fee_profiles_enquiry fpe\n" +
                        "inner join constants c on c.constant_oid = fpe.profile_type_cid\n"+
                        "inner join constants c1 on c1.constant_oid = fpe.profile_category_cid\n" +
                        "left join card_programs cp on cp.card_program_oid = fpe.card_program_oid\n" +
                        "left join application_types apt on apt.application_type_oid = fpe.application_type_oid\n" +
                        "left join m_clients mc on mc.client_mid = fpe.client_mid \n" +
                        "left join m_customers mcust on mcust.customer_mid = fpe.customer_mid\n" +
                        "where (fpe.card_program_oid is not null or fpe.application_type_oid is not null or fpe.client_mid is not null)\n" +
                        "and c.description = 'Account Fee' and c1.description = 'Reference' and fpe.expires_on >= mc.processing_date";
            }
        }
        queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
        logger.log("query : "+query);
        if(feeCategory.equalsIgnoreCase("Public")){
            if(queryResults.size()>1){
                PropUtils.setProps("cardProgram","",baseUtils.testDataFilePath);
                PropUtils.setProps("applicationType","",baseUtils.testDataFilePath);
                PropUtils.setProps("country","",baseUtils.testDataFilePath);
                PropUtils.setProps("cardProgram",queryResults.get(0).get("CARDPROGRAM"),baseUtils.testDataFilePath);
                PropUtils.setProps("applicationType",queryResults.get(0).get("APPLICATIONTYPE"),baseUtils.testDataFilePath);
                PropUtils.setProps("country",queryResults.get(0).get("COUNTRY"),baseUtils.testDataFilePath);
            }
        }
        if(queryResults.size()>1){
            PropUtils.setProps("commonAccountNo",queryResults.get(0).get("CUSTOMER_NO"),baseUtils.testDataFilePath);
            PropUtils.setProps("existingFeeName",queryResults.get(0).get("DESCRIPTION"),baseUtils.testDataFilePath);
            PropUtils.setProps("feeProfileOid",queryResults.get(0).get("FEE_PROFILE_OID"),baseUtils.testDataFilePath);
        }else{
            PropUtils.setProps("testStatus","Skipped",baseUtils.testDataFilePath);
            PropUtils.setProps("skipReason","No fee profiles are availabel to execute this scenario",baseUtils.testDataFilePath);
        }
    }
    /*
    Method to handle search criteria for public fee profiles
     */
    public void enterSearchCriteriaForPublicFeeProfiles(Scenario logger){
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "applicationType").equalsIgnoreCase("")) {
            if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='segment']>mat-select"), "aria-disabled").equalsIgnoreCase("true")) {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "applicationType"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='segment']>mat-select"), "ng-reflect-model"), "Selected Application type");
            } else {
                basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='segment']>mat-select"));
                commonPage.clickButtonUsingSpan(logger, PropUtils.getPropValue(properties, "applicationType"));
            }
        } else if (!PropUtils.getPropValue(properties, "country").equalsIgnoreCase("")) {
            if (commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='feeCountry']>mat-select"), "aria-disabled").equalsIgnoreCase("true")) {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "country"), commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='feeCountry']>mat-select"), "ng-reflect-model"), "Selected Country");
            } else {
                basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='feeCountry']>mat-select"));
                basePage.userClick(logger,By.xpath("//span[@class='mat-option-text'][contains(text(),'"+PropUtils.getPropValue(properties, "country")+"')]"));
            }
        } else if (!PropUtils.getPropValue(properties, "cardProgram").equalsIgnoreCase("")) {
            if (commonPage.getAttributeValue(By.cssSelector("mat-select[ng-reflect-name='cardProgram']"), "aria-disabled").equalsIgnoreCase("true")) {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cardProgram"), basePage.userGetTextFromWebElement(logger,By.cssSelector("mat-select[ng-reflect-name='cardProgram']>div>div>span>mat-select-trigger")), "Selected card Program");
            } else {
                basePage.userClick(logger, By.cssSelector("mat-select[ng-reflect-name='cardProgram']"));
                commonPage.clickButtonUsingSpan(logger, PropUtils.getPropValue(properties, "cardProgram"));
            }
        }
    }
}
