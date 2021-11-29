package stepDefinitions.stepDefinitionsUI;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import pages.*;
import utilities.api.BaseUtils;
import utilities.api.CommonDBUtils;
import utilities.api.PropUtils;
import utilities.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CardsSteps {

    private WebDriver driver;
    private AccountInfoPage accountInfoPage;
    private LoginPage loginPage;
    private BasePage basePage;
    private BaseUtils baseUtils;
    private CommonDBUtils commonUtils;
    private UsersPage usersPage;
    private ContactsPage contactsPage;
    private CommonPage commonPage;
    private ExcelUtils excelUtils;
    private CardsPage cardsPage;
    private List<Map<String, String>> cardData;
    private WebDriverInitialization webDriverInitialization;
    private String accountNumber = "";
    public Scenario logger;
    List<Map<String, String>> validationControlFields = new ArrayList<>();
    private DriverFactory driverFactory;

    public CardsSteps(Hooks hooks, DriverFactory driverFactory) {
        this.driverFactory=driverFactory;
        this.logger = hooks.logger;
        this.driver = driverFactory.driver;
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        commonUtils = new CommonDBUtils();
        baseUtils = new BaseUtils();
        usersPage = new UsersPage(driver);
        contactsPage = new ContactsPage(driver);
        commonPage = new CommonPage(driver);
        excelUtils = new ExcelUtils(driver);
        cardsPage = new CardsPage(driver);
        accountInfoPage = new AccountInfoPage(driver);
    }

    @When("^User select an account from account picker which \"(.*)\", \"(.*)\" status and \"(.*)\" sub status$")
    public void userSelectAndAccountFromAccountPickerWhichIsInSpecifiedStatus(String isContains, String accountStatus, String accountSubStatus) {
        Boolean has = false;
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (isContains.equalsIgnoreCase("has")) {
                has = true;
            }
            accountNumber = cardsPage.selectAccountBasedOnStatus(logger, accountStatus, accountSubStatus);
            if (accountStatus.equals("")) {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
            } else {
                PropUtils.setProps("orderCard-AccountNo", accountNumber, baseUtils.testDataFilePath);
                PropUtils.setProps("accountNumber", accountNumber, baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User get an account number based on status \"(.*)\" and substatus \"(.*)\"$")
    public void userSelectAndAccountFromAccountPickerWhichIsInSpecifiedStatus(String accountStatus, String accountSubStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountNumber = cardsPage.getAccountNumberBasedOnStatus(logger, accountStatus, accountSubStatus);
            PropUtils.setProps("commonAccountNo", accountNumber, baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User store change PIN and Reissue PIN related field values in test data property file$")
    public void userStoreChangePINAndReissuePINRelatedFieldValuesInTestDataPropertyFile(String isContains, String accountStatus, String accountSubStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select mc.client_mid from m_clients mc\n" +
                    "inner join m_customers mcust on mcust.client_mid = mc.client_mid\n" +
                    "where mcust.customer_no = " + PropUtils.getPropValue(properties, "orderCard-AccountNo") +
                    " and mc.short_name = '" + PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'";
            Map<String, String> queryResults = null;
            queryResults = commonUtils.getQueryResultsOnMap(query);
            String clientMID = queryResults.get("CLIENT_MID");
            query = "select is_pin_req from m_customers where customer_no = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = '" + clientMID + "'";
            queryResults = commonUtils.getQueryResultsOnMap(query);
            PropUtils.setProps("IS_PIN_REQ", queryResults.get("IS_PIN_REQ"), baseUtils.testDataFilePath);

            query = "select pin_offset from m_customers where customer_no = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = '" + clientMID + "'";
            queryResults = commonUtils.getQueryResultsOnMap(query);
            PropUtils.setProps("PIN_OFFSET", queryResults.get("PIN_OFFSET"), baseUtils.testDataFilePath);

            query = "select CARD_PIN_CONTROL_CID from card_products where DESCRIPTION = '" + PropUtils.getPropValue(properties, "orderCard-cardProduct") + "'";
            queryResults = commonUtils.getQueryResultsOnMap(query);
            PropUtils.setProps("CARD_PIN_CONTROL_CID", queryResults.get("CARD_PIN_CONTROL_CID"), baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select account based on the account number \"(.*)\"$")
    public void userSelectAccountBasedOnAccountNumber(String accountNo) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, accountNo);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select account based on the account number from property file \"(.*)\"$")
    public void userSelect(String accountNo) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            accountNo = PropUtils.getPropValue(properties, accountNo);
            commonPage.selectAccountFromAccountPickerBasedOnAccountNo(logger, accountNo);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate \"(.*)\" field value using \"(.*)\" tag name for \"(.*)\" form$")
    public void userValidateFieldValueUsingAttribute(String fieldName, String tagName, String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String fieldValue = "";
            String isDisabled = commonPage.getAttributeValue(By.cssSelector(tagName + "[name='" + fieldName + "']>mat-select"), "aria-disabled");
            if (!isDisabled.equalsIgnoreCase("True")) {
                if (basePage.checkIsEnabledOrDisabled(logger, By.cssSelector(tagName + "[name='" + fieldName + "']>mat-select>div>div>span"))) {
                    fieldValue = basePage.userGetTextFromWebElement(logger, By.cssSelector(tagName + "[name='" + fieldName + "']>mat-select>div>div>span>span"));
                    if (action.equalsIgnoreCase("add")) {
                        PropUtils.setProps("orderCard-" + fieldName, fieldValue, baseUtils.testDataFilePath);
                    } else if (action.equalsIgnoreCase("edit")) {
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "orderCard-" + fieldName), fieldValue, fieldName);
                    }
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate EmbossingName is populated based on the account selected from account picker for \"(.*)\" form$")
    public void userValidateFieldNameIsPopulatedBasedOnSelectedAccount(String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String actEmbossingName = commonPage.getAttributeValue(By.cssSelector("mav-input[name='line1']>input"), "ng-reflect-model");
            Map<String, String> validationControlRecord = commonPage.getValidationControlFieldMapObjectBasedOnSpecificName("cardVO.embossing_name", validationControlFields);
            cardsPage.checkLabelStatusBasedOnValidationControl(logger, validationControlRecord, "Company name");
            if (validationControlRecord.get("FIELDSTATUS").equalsIgnoreCase("Mandatory") || validationControlRecord.get("FIELDSTATUS").equalsIgnoreCase("Optional")) {
                if (action.equalsIgnoreCase("add")) {
                    cardData = cardsPage.getCardDataBasedOnAccount(logger);
                    String expEmbossingName = commonPage.getSpecificValueFromMapObject(cardData.get(0), "EMBOSSING_NAME");
                    if (expEmbossingName.equals("")) {
                        expEmbossingName = commonPage.getSpecificValueFromMapObject(cardData.get(0), "NAME");
                    }
                    commonPage.assertTwoStrings(logger, expEmbossingName, actEmbossingName, "EmbossingName");
                    actEmbossingName = PasswordGenerator.generateAlphaNumeric(logger, 12);
                    basePage.userTypeIntoTextField(logger, By.cssSelector("mav-input[name='line1']>input"), actEmbossingName);
                    PropUtils.setProps(action + "-embossingName", actEmbossingName, baseUtils.testDataFilePath);
                } else if (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("clone")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, action + "-embossingName"), actEmbossingName, "EmbossingName");
                    if (PropUtils.getPropValue(properties, action + "-fieldWhichHasCardReissueTypeCid").equalsIgnoreCase("Embossing")) {
                        actEmbossingName = PasswordGenerator.generateAlphaNumeric(logger, 12);
                        basePage.userTypeIntoTextField(logger, By.cssSelector("mav-input[name='line1']>input"), actEmbossingName);
                        PropUtils.setProps(action + "-embossingName", actEmbossingName, baseUtils.testDataFilePath);
                    }
                }
            } else {
                logger.log("Based on validation control field status is :" + validationControlRecord.get("FIELDSTATUS") + " which is not eligible to perform any action for this field");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate Expiry Date is populated as clientProcessingDate plus ExpiryMonths of card product and user can select until MaxCardExpiryDate reaches for \"(.*)\" form$")
    public void userValidateExpiryDateWhileOrderingACard(String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String actExpiryDate = commonPage.getAttributeValue(By.cssSelector("mav-input[name='expiryDate']>input"), "ng-reflect-model");
            Map<String, String> validationControlRecord = commonPage.getValidationControlFieldMapObjectBasedOnSpecificName("cardVO.expires_on", validationControlFields);
            cardsPage.checkLabelStatusBasedOnValidationControl(logger, validationControlRecord, "Expiry date");
            if (action.equalsIgnoreCase("add")) {
                String expExpiryDate = cardsPage.getExpiryDateToBePopulatedForACard(cardsPage.getCardTypesBasedOnCardOfferAndCardProduct(PropUtils.getPropValue(properties, "add-cardOffer"), PropUtils.getPropValue(properties, "add-cardProduct")).get(0).get("EXPIRY_MONTHS"));
                if (actExpiryDate.contains(expExpiryDate)) {
                    logger.log("Expiry Date is populated as expected");
                } else {
                    logger.log("Expiry Date is not populated as expected");
                }
                PropUtils.setProps(action + "-ExpiryDate", actExpiryDate, baseUtils.testDataFilePath);
            } else if (action.equalsIgnoreCase("clone")) {
//                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "orderCard-ExpiryDate"), actExpiryDate, "Expirty Date InClone page");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate Expiry Date in action \"(.*)\" as order card date and change it to \"(.*)\" days \"(.*)\"$")
    public void userValidateExpiryDateInActionAndChangeItToFutureOrPast(String action, String futureOrPast, String noOfDays) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String expExpiryDate = cardsPage.getExpiryDateToBePopulatedForACard("");
            String actExpiryDate = "";
            if (action.equalsIgnoreCase("editCard")) {
                expExpiryDate = PropUtils.getPropValue(properties, "orderCard-ExpiryDate");
                actExpiryDate = commonPage.getAttributeValue(By.cssSelector("mav-input[name='expiryDate']>input"), "ng-reflect-model");
                if (actExpiryDate.contains(expExpiryDate)) {
                    logger.log("Expiry Date is populated as expected");
                } else {
                    logger.log("Expiry Date is not populated as expected");
                }
            }
            if (!action.equalsIgnoreCase("editCard")) {
                String[] arrEffectiveFrom = commonPage.getAttributeValue(By.cssSelector("mav-input[name='effectiveFrom']>input"), "ng-reflect-model").split(" ");
                String currentEffectiveDate = arrEffectiveFrom[1] + " " + arrEffectiveFrom[2] + " " + arrEffectiveFrom[3];
                logger.log(currentEffectiveDate);
//                basePage.userClick(logger, By.cssSelector("mav-input[name='effectiveFrom']>input"));
                basePage.userClick(logger, By.cssSelector("button[class='mat-focus-indicator mat-icon-button mat-button-base']"));
                String dateToBeSelected = "";
                dateToBeSelected = commonPage.getDesiredDateInSpecificFormat(logger, currentEffectiveDate, Integer.parseInt(noOfDays), futureOrPast, "MMM dd yyyy", "MMMM d, yyyy");
                commonPage.handleDatePickerForPastOrFutureDateSelection(logger, dateToBeSelected.split(" ")[0].substring(0, 3), dateToBeSelected.split(" ")[1], dateToBeSelected.split(" ")[2]);
//                basePage.userClickJSExecutor(logger, By.cssSelector("td[aria-label='" + dateToBeSelected + "']>div"));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User handle \"(.*)\" section for module \"(.*)\" and perform action \"(.*)\"$")
    public void userHandleProductSelectionStepperForSpecificModule(String sectionName, String moduleName, String actionOfSection) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            PropUtils.setProps("CurrentModuleForProductSelection", moduleName, baseUtils.testDataFilePath);
            accountInfoPage.handleFieldBehaviourBasedOnValidationControlForSpecificModule(logger, sectionName, actionOfSection);
            commonPage.sleepForFewSeconds(3);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User handle field \"(.*)\" in cards module based on validation control in \"(.*)\" form$")
    public void userHandleFieldInCardsModuleBasedOnValidtionControlInSpecificForm(String fieldName, String form) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.handleFieldBasedOnValidationControl(logger, fieldName, validationControlFields, form);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate velocity limits in \"(.*)\" page for \"(.*)\" form$")
    public void userValidateVelocityLimitsBasedOnModule(String moduleName, String actionOfModule) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.handleVelocityLimitsBasedOnModule(logger, moduleName, actionOfModule);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate all stepper values in Review page of \"(.*)\"$")
    public void userValidateAllStepperValuesInReviewPageBasedOnmoduleNameAndAction(String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.validateReviewPageOfCardControlProfile(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate card control profile added or updated in the database based on action \"(.*)\"$")
    public void userValidateCardControlProfileAddedOrUpdatedInDatabaseBasedOnAction(String actionOfCCP) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select ccp.description from card_control_profiles ccp\n" +
                    "left join card_offers co on co.card_offer_oid = ccp.card_offer_oid\n" +
                    "left join card_products cp on cp.card_product_oid = ccp.card_product_oid\n" +
                    "left join m_customers mcust on mcust.customer_mid = ccp.customer_mid\n" +
                    "where mcust.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
            List<Map<String, String>> queryResults = new ArrayList<>();
            String condition = "";
            if (actionOfCCP.equalsIgnoreCase("Add")) {
                condition = " and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule") +
                        "' and co.description = '" + PropUtils.getPropValue(properties, "ccp-cardOffer") + "' \n" +
                        " and cp.description  = '" + PropUtils.getPropValue(properties, "ccp-cardProduct") + "'";
                logger.log(query + condition);
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query + condition);
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "newCardControlProfile-cardControlProfileName"), queryResults.get(0).get("DESCRIPTION"), "CardControlProfileName based on action : '" + actionOfCCP + "'");
            } else if (actionOfCCP.equalsIgnoreCase("Edit")) {
                condition = " and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule-notEligibleForAddCCP") +
                        "' and co.description = '" + PropUtils.getPropValue(properties, "ccp-cardOffer-notEligibleForAddCCP") + "' \n" +
                        " and cp.description  = '" + PropUtils.getPropValue(properties, "ccp-cardProduct-notEligibleForAddCCP") + "'";
                queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query + condition);
                logger.log(query + condition);
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "CCP-cardControlProfileName"), queryResults.get(0).get("DESCRIPTION"), "CardControlProfileName based on action : '" + actionOfCCP + "'");
            }
            commonPage.assertTwoStrings(logger, "1", String.valueOf(queryResults.size()), "Count of records in database");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User handle section \"(.*)\" in cards module based on validation control in \"(.*)\" form for cardControl \"(.*)\" with profile \"(.*)\"$")
    public void userValidatePOSPromptsInCardDetailsStepper(String section, String form, String cardControlType, String profileType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.handleSectionsBasedOnValidationControls(logger, section, form, cardControlType, profileType, validationControlFields);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User handle section \"(.*)\" in cards module in \"(.*)\" form for cardControl \"(.*)\" based on scenario \"(.*)\"$")
    public void userHandleSectionInCardsModuleBasedOnScenario(String section, String form, String cardControlType, String scenario) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (section.equalsIgnoreCase("locationRestriction")) {
                cardsPage.handleLocationRestriction(logger, form, cardControlType, scenario);
            } else if (section.equalsIgnoreCase("timeLimits")) {
                cardsPage.handleTimeLimitsInCardsModule(logger, form, cardControlType, scenario);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate ProductSection values in \"(.*)\" form$")
    public void userValidatePOSPromptsInCardDetailsStepper(String pageAction) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.clickButtonUsingSpan(logger, "Next");
            List<WebElement> productNames = null;
            List<WebElement> productCheckBoxes = null;
            productNames = basePage.getListOfElements(logger, By.xpath("//div[@class='ng-star-inserted']/mat-checkbox/label/span"));
            productCheckBoxes = basePage.getListOfElements(logger, By.xpath("//div[@class='ng-star-inserted']/mat-checkbox/label/div/input"));
            String selectedProducts = "";
            for (int i = 0; i <= productCheckBoxes.size() - 1; i++) {
                if (productCheckBoxes.get(i).getAttribute("aria-checked").equalsIgnoreCase("True")) {
//                    if (i == productCheckBoxes.size() - 1) {
                    selectedProducts = selectedProducts + productNames.get(i).getText();
//                    } else {
//                        selectedProducts = selectedProducts + productNames.get(i).getText() + ",";
//                    }
                }
            }
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "Cards-SelectedProducts"), selectedProducts, "Production selection stepper");
            commonPage.clickButtonUsingSpan(logger, "Back");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate Purchase Controls section fields and their values$")
    public void userValidatePurchaseControlsSectionFieldsAndTheirValues(DataTable dt) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<String> values = dt.asList(String.class);
            String arrValues[] = {values.get(0)};
            if (values.get(0).contains(",")) {
                arrValues = values.get(0).split(",");
            }
            String action = PropUtils.getPropValue(properties, "card_currentAction");
            cardsPage.validatePurchaseControlFieldsAndValues(logger, arrValues, action);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter Alternate address based on value \"(.*)\" for \"(.*)\" form for section \"(.*)\"$")
    public void userEnterAlternateAddressBasedOnValue(String isAlternateAddressRequired, String pageAction, String sectionOfAddress) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (isAlternateAddressRequired.equalsIgnoreCase("Yes")) {
                PropUtils.setProps(pageAction + "-" + sectionOfAddress, "Alternate address", baseUtils.testDataFilePath);
                if (pageAction.equalsIgnoreCase("add")) {
                    cardsPage.enterAlternateAddressOrSelectPrimaryAddress(logger, "Alternate address");
                }
                if (pageAction.equalsIgnoreCase("clone") || pageAction.equalsIgnoreCase("edit")) {
                    if (PropUtils.getPropValue(properties, "add-" + sectionOfAddress).equalsIgnoreCase("Alternate address")) {
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cards-contactName"), usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, "contactName"), "contactName value in : " + pageAction + " form");
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cards-addressLine"), usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, "addressLine"), "addressLine value in : " + pageAction + " form");
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cards-suburb"), usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, "suburb"), "suburb value in : " + pageAction + " form");
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cards-postalCode"), usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, "postalCode"), "postalCode value in : " + pageAction + " form");
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "cards-state"), basePage.userGetTextFromWebElement(logger, By.cssSelector("mav-select[name='state']>mat-select>div>div>span>span")), "contactName value in : " + pageAction + " form");
                    } else {
                        cardsPage.enterAlternateAddressOrSelectPrimaryAddress(logger, "Alternate address");
                    }
                }
                cardsPage.enterAlternateAddress(logger, pageAction);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select value \"(.*)\" in cost centre field from the list for \"(.*)\" form")
    public void userSelectValueInCostCentreFieldFromTheList(String costCentre, String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String costCentreValue = "";
            if (action.equalsIgnoreCase("add")) {
                if (!costCentre.equals("")) {
                    costCentreValue = PropUtils.getPropValue(properties, costCentre);
                }
                if (costCentreValue.equals("")) {
                    if (Integer.parseInt(cardsPage.getCountOfCustomerCostCentres()) > 0) {
                        cardsPage.selectCostCentreInCardsModule(logger, costCentre, action);
                    }
                } else {
                    cardsPage.searchAndSelectCostCentreInOrderCardPage(logger, costCentreValue);
                    cardsPage.selectCostCentreInCardsModule(logger, costCentre, action);
                }
            } else if (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("clone")) {
//                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, action+"-costCentre"), commonPage.getAttributeValue(By.cssSelector("ols-autocomplete[ng-reflect-name='costCentre']>mat-form-field>div>div>div>input"), "ng-reflect-model"), "Cost Centre");
                if (!costCentre.equals("")) {
                    costCentreValue = PropUtils.getPropValue(properties, costCentre);
                }
                if (costCentreValue.equals("")) {
                    if (Integer.parseInt(cardsPage.getCountOfCustomerCostCentres()) > 0) {
                        cardsPage.selectCostCentreInCardsModule(logger, costCentre, action);
                    }
                } else {
                    cardsPage.searchAndSelectCostCentreInOrderCardPage(logger, costCentreValue);
                    cardsPage.selectCostCentreInCardsModule(logger, costCentre, action);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate identification method$")
    public void userValidateIdentificationMethod() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.validateIdentificationMethod(logger);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate specific field \"(.*)\" value \"(.*)\" in cards table from database$")
    public void userValidateSpecificFieldValueFromCardsTable(String fieldName, String fieldValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select c.replace_card_oid from cards c\n" +
                    "  left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                    "  left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "  where mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ")\n" +
                    " and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule") + "'" +
                    " and c.card_no like '%" + PropUtils.getPropValue(properties, "cardNumberToPerformAnAction") + "'";
            logger.log(query);
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            if (fieldValue == null || fieldValue.equalsIgnoreCase("")) {
                if (commonPage.getSpecificValueFromMapObject(queryResults, fieldName).equals("")) {
                    logger.log("field value is " + fieldValue + " for field name " + fieldName);
                } else {
                    logger.log("field value is not'" + fieldValue + "' for field name " + fieldName + " which is not expected");
                }
            } else {
                if (!queryResults.get(fieldName).equals("")) {
                    logger.log("field value is " + fieldValue + " for field name " + fieldName);
                } else {
                    logger.log("field value is " + fieldValue + " for field name " + fieldName);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate address in Delivery address stepper from \"(.*)\"$")
    public void userValidatePrimaryAccountAddressInDeliveryAddressStepper(String validateLocation) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.sleepForFewSeconds(3);
            By locator = null;
            String radioButtonType = "";
            String expPrimaryAccountAddress = "";
            String actPrimaryAccountAddress = "";
            if (validateLocation.equalsIgnoreCase("deliveryAddressStepper")) {
                locator = By.cssSelector("mat-radio-button[class='mat-radio-button mat-accent mat-radio-checked']>label>div[class='mat-radio-label-content']");
                radioButtonType = basePage.userGetTextFromWebElement(logger, locator);
                expPrimaryAccountAddress = cardsPage.getDeliveryAddressOfACard(PropUtils.getPropValue(properties, "orderCard-AccountNo"), radioButtonType.replaceAll("^\"|\"$", ""));
                actPrimaryAccountAddress = cardsPage.getPrimaryAddressFromDeliveryAddressStepper(logger);
            } else {
                expPrimaryAccountAddress = cardsPage.getDeliveryAddressOfACard(PropUtils.getPropValue(properties, "orderCard-AccountNo"), "addressAssociatedToCard");
                actPrimaryAccountAddress = PropUtils.getPropValue(properties, "cards-contactName") + "\n" +
                        PropUtils.getPropValue(properties, "cards-addressLine") + "\n" +
                        PropUtils.getPropValue(properties, "cards-suburb") + ", " + PropUtils.getPropValue(properties, "state")
                        + ", " + PropUtils.getPropValue(properties, "cards-postalCode");
            }
            basePage.assertTwoStrings(logger, expPrimaryAccountAddress, actPrimaryAccountAddress);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User \"(.*)\" change PIN and Reissue PIN options$")
    public void userModifyChangePINAndReissuePINOptions(String optionType) {
        commonPage.sleepForFewSeconds(3);
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select mc.client_mid from m_clients mc\n" +
                    "inner join m_customers mcust on mcust.client_mid = mc.client_mid\n" +
                    "where mcust.customer_no = " + PropUtils.getPropValue(properties, "accountNumber") +
                    " and mc.short_name = '" + PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "'";
            Map<String, String> queryResults = null;
            queryResults = commonUtils.getQueryResultsOnMap(query);
            String clientMID = queryResults.get("CLIENT_MID");
            String cardProduct = "";
            if (optionType.equalsIgnoreCase("enable")) {
                query = "update m_customers set is_pin_req = 'Y' where customer_no  = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = " + clientMID;
                commonUtils.executeUpdateQuery(query);
                query = "update m_customers set pin_offset = 1111 where customer_no  = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = " + clientMID;
                commonUtils.executeUpdateQuery(query);
                query = "update card_products set CARD_PIN_CONTROL_CID = 5150 where DESCRIPTION = '" + PropUtils.getPropValue(properties, "orderCard-cardProduct").toUpperCase() + "'";
                commonUtils.executeUpdateQuery(query);
            } else if (optionType.equalsIgnoreCase("setDefault")) {
                query = "update m_customers set is_pin_req = 'N' where customer_no  = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = " + clientMID;
                commonUtils.executeUpdateQuery(query);
                query = "update m_customers set pin_offset = Null where customer_no  = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = " + clientMID;
                commonUtils.executeUpdateQuery(query);
//                query = "update card_products set CARD_PIN_CONTROL_CID = 5100 where DESCRIPTION = '" + PropUtils.getPropValue(properties, "orderCard-cardProduct").toUpperCase() + "'";
//                commonUtils.executeUpdateQuery(query);
            } else {
                query = "update m_customers set is_pin_req = '" + PropUtils.getPropValue(properties, "IS_PIN_REQ") + "' where customer_no  = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = " + clientMID;
                commonUtils.executeUpdateQuery(query);
                query = "update m_customers set pin_offset = " + PropUtils.getPropValue(properties, "PIN_OFFSET") + " where customer_no  = " + PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-AccountNo") + " and client_mid = " + clientMID;
                commonUtils.executeUpdateQuery(query);
                query = "update card_products set CARD_PIN_CONTROL_CID = " + PropUtils.getPropValue(properties, "CARD_PIN_CONTROL_CID") + " where DESCRIPTION = '" + PropUtils.getPropValue(properties, "orderCard-cardProduct").toUpperCase() + "'";
                commonUtils.executeUpdateQuery(query);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User enter \"(.*)\" as \"(.*)\" in \"(.*)\" module having length \"(.*)\" in \"(.*)\" form based on cardType$")
    public void userEnterFieldValueInSpecificModule(String fieldName, String fieldValue, String moduleName, String length, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String cardType = PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-cardType");
            if (cardType.equalsIgnoreCase("open") ||
                    (cardType.equalsIgnoreCase("Vehicle") && (
                            fieldName.equalsIgnoreCase("vehicleDescription") || fieldName.equalsIgnoreCase("licensePlate"))) ||
                    (cardType.equalsIgnoreCase("Driver") && (
                            fieldName.equalsIgnoreCase("driverId") || fieldName.equalsIgnoreCase("driverName")))) {
                usersPage.enterValueBasedOfItsType(logger, fieldValue, fieldName, formType, moduleName, length, "-_");
                String newValue = usersPage.getValueFromCorrespondingTextFieldInEditUser(logger, fieldName);
                PropUtils.setProps(moduleName + "-" + fieldName, newValue, baseUtils.testDataFilePath);
                PropUtils.setProps("NewlyCreatedUser", newValue, baseUtils.inputDataFilePath);
                logger.log("Field " + fieldName + " value is " + fieldValue + " entered");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User verify the drop down \"(.*)\" value is pre-selected if the drop down has one or more than one value and validate its status for \"(.*)\" form")
    public void userVerifyTheDropDownValueIsPreSelectedAndDisabledHasOnlyOneValue(String nameOfDropDown, String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (action.equalsIgnoreCase("add")) {
                cardsPage.handleCardOfferCardProductCardTypeDropDownsInOrderCardPage(logger, nameOfDropDown, action, validationControlFields);
            } else if (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("clone")) {
                String cardQuery = "select ccc.CUSTOMER_COST_CENTRE_CODE as costCentre,d.DRIVER_ID,d.DRIVER_NAME,v.DESCRIPTION,v.LICENSE_PLATE,\n" +
                        "c.CARD_CONTROL_PROFILE_OID,ccp.card_oid as ccpCardOid,ccp.description as cardControlProfile,mi.description as mailIndicator,c.EMBOSSING_NAME,c.EXTERNAL_REF,c.CUSTOMER_PIN_GROUP_OID,c.IS_GENERATED_PIN,c.PIN_OFFSET,co.description as cardOffer,cp.description as cardProduct, const.description as cardType from cards c\n" +
                        "inner join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                        "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "left join card_products cp on cp.card_product_oid = c.card_product_oid\n" +
                        "left join card_offers co on co.card_offer_oid = cp.card_offer_oid\n" +
                        "left join constants const on const.constant_oid = c.card_type_cid\n" +
                        "left join drivers d on d.driver_oid = c.driver_oid\n" +
                        "left join vehicles v on v.vehicle_oid = c.vehicle_oid\n" +
                        "left join cost_centres cc on cc.card_oid = c.card_oid\n" +
                        "left join mail_indicators mi on mi.mail_indicator_oid = c.mail_indicator_oid\n" +
                        "left join customer_cost_centres ccc on ccc.customer_cost_centre_oid = cc.customer_cost_centre_oid\n" +
                        "left join card_control_profiles ccp on ccp.card_control_profile_oid = c.card_control_profile_oid\n" +
                        "where c.card_no like '%" + PropUtils.getPropValue(properties, "cardNumberToPerformAnAction") + "%' " +
                        "and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule") + "' and cp.description = '"+PropUtils.getPropValue(properties,action + "-cardProduct")+"'" +
                        "and mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID")+" order by c.LAST_UPDATED_AT asc";//+" and cc.expires_on like '31-DEC-99' ";// + " and ccp.card_oid is not null";
                Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(cardQuery);

                PropUtils.setProps(action + "-cardOffer", queryResults.get("CARDOFFER"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-cardProduct", queryResults.get("CARDPRODUCT"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-cardType", queryResults.get("CARDTYPE"), baseUtils.testDataFilePath);
                commonPage.assertTwoStrings(logger, queryResults.get("CARDOFFER"), basePage.userGetTextFromWebElement(logger, By.cssSelector("mav-select[ng-reflect-name='cardOffer']>mat-select>div>div>span>span")), "Selected Card Offer in '" + action + "' card page");
                commonPage.assertTwoStrings(logger, queryResults.get("CARDPRODUCT"), basePage.userGetTextFromWebElement(logger, By.cssSelector("mav-select[ng-reflect-name='cardProduct']>mat-select>div>div>span>span")), "Selected Card Product in '" + action + "' card page");
                commonPage.assertTwoStrings(logger, queryResults.get("CARDTYPE"), commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='cardType']>input"), "ng-reflect-model"), "Selected Card Type in '" + action + "' card page");
                if (!commonPage.getSpecificValueFromMapObject(queryResults, "CUSTOMER_PIN_GROUP_OID").equalsIgnoreCase("")) {
                    PropUtils.setProps(action + "-IdentificationMethod", "Group pin", baseUtils.testDataFilePath);
                } else if (commonPage.getSpecificValueFromMapObject(queryResults, "IS_GENERATED_PIN").equalsIgnoreCase("Y")) {
                    PropUtils.setProps(action + "-IdentificationMethod", "System generated pin", baseUtils.testDataFilePath);
                } else if (!commonPage.getSpecificValueFromMapObject(queryResults, "PIN_OFFSET").equalsIgnoreCase("")) {
                    PropUtils.setProps(action + "-IdentificationMethod", "Self select pin", baseUtils.testDataFilePath);
                }
                PropUtils.setProps(action + "-cardControlProfile", commonPage.getSpecificValueFromMapObject(queryResults, "CARDCONTROLPROFILE"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-costCentre", commonPage.getSpecificValueFromMapObject(queryResults, "COSTCENTRE"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-driverId", commonPage.getSpecificValueFromMapObject(queryResults, "DRIVER_ID"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-driverName", commonPage.getSpecificValueFromMapObject(queryResults, "DRIVER_NAME"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-vehicleID", commonPage.getSpecificValueFromMapObject(queryResults, "DESCRIPTION"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-licensePlate", commonPage.getSpecificValueFromMapObject(queryResults, "LICENSE_PLATE"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-cardControlProfileOID", commonPage.getSpecificValueFromMapObject(queryResults, "CARD_CONTROL_PROFILE_OID"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-ccpCardOid", commonPage.getSpecificValueFromMapObject(queryResults, "CCPCARDOID"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-mailIndicator", commonPage.getSpecificValueFromMapObject(queryResults, "MAILINDICATOR"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-embossingName", commonPage.getSpecificValueFromMapObject(queryResults, "EMBOSSING_NAME"), baseUtils.testDataFilePath);
                PropUtils.setProps(action + "-externalRef", commonPage.getSpecificValueFromMapObject(queryResults, "EXTERNAL_REF"), baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
//            } else if (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("clone")) {
//                valueOfElement = commonPage.getAttributeValue(By.cssSelector("mav-input[name='" + nameOfDropDown + "']>input"), "ng-reflect-model");
//                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(baseUtils.testDataProperties, "orderCard-" + nameOfDropDown), valueOfElement, nameOfDropDown+" in "+action+" page");
//            }
    }

    @Then("^User enter \"(.*)\" as \"(.*)\" in \"(.*)\" module having length \"(.*)\" in \"(.*)\" form using input tag$")
    public void userEnterFieldValueInCorrespondingField(String fieldName, String fieldValue, String moduleName, String length, String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.enterValueBasedOfItsType(logger, fieldValue, fieldName, formType, moduleName, length, "-_");
            String newValue = cardsPage.getValueFromCorrespondingTextFieldUsingInputTag(logger, fieldName);
            PropUtils.setProps(moduleName + "-" + fieldName, commonPage.getAttributeValue(By.cssSelector("mav-input[ng-reflect-name='" + fieldName + "']>input"), "ng-reflect-model"), baseUtils.testDataFilePath);
            logger.log("Field " + fieldName + " value is " + fieldValue + " entered");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate all stepper values in Review page in form \"(.*)\"$")
    public void userValidateAlStepperValuesInReviewPage(String formType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.validateAllStepperValues(logger, formType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate success message of \"(.*)\" for no of card \"(.*)\"$")
    public void userValidateSuccessMessageOfOrderCard(String actionOfCard, String noOfCard) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.validateOrderCardSuccessMessage(logger, actionOfCard, noOfCard);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User change the newly created card \"(.*)\" status to Active$")
    public void userChangeTheNewlyCreatedCardStatusToActive(String cardNo) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.makeCardStatusToActive(properties, cardNo);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get latest cardNo based on accountNo \"(.*)\"$")
    public void userValidateSuccessMessageOfOrderCard(String accountNumber) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select SUBSTR(c.card_no, LENGTH(c.card_no) - 5, 6) as card_no from cards c\n" +
                    "left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID")
                    + " and mcust.customer_no = '" + PropUtils.getPropValue(properties, accountNumber) + "' order by c.LAST_UPDATED_AT desc";
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() < 1) {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "No card is available to execute this test case", baseUtils.testDataFilePath);
            } else {
                PropUtils.setProps("Cards-cardNumber", queryResults.get(0).get("CARD_NO"), baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate the status of card in the database after performing \"(.*)\" card action$")
    public void userValidateStatusOfCardInDatabaseAfterPerformingAnAction(String actionOfCard) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "";
            Map<String, String> queryResults = null;
            query = "select cs.card_status_oid,cs.description as status from cards c\n" +
                    "left join card_status cs on cs.card_status_oid = c.card_status_oid\n" +
                    "left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where card_no like ('%" + PropUtils.getPropValue(properties, "cardNumberToPerformAnAction") + "')" + "\n" +
                    " and mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + "\n" +
                    ") and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule") + "'";
            queryResults = commonUtils.getQueryResultsOnMap(query);
            if (actionOfCard.equalsIgnoreCase("ChangeCardStatus")) {
                commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, "changeCardStatusValue"), queryResults.get("STATUS"), "Card status after performing 'Change Card status' action");
            } else if (actionOfCard.equalsIgnoreCase("ReplaceCard")) {

            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate \"(.*)\" drop down values are coming based on database or not and select one value based on cardReissueActionCid \"(.*)\"$")
    public void userValidateChangeCardStatusDropValuesAndSelectOneValue(String actionOfCard, String cardReissueActionCid) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select card_status_oid from cards c\n" +
                    "left join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                    "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                    "where card_no like ('%" + PropUtils.getPropValue(properties, "cardNumberToPerformAnAction") + "')" +
                    " and mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + "\n" +
                    ") and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule") + "'";
            logger.log(query);
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            query = "Select cs2.card_reissue_action_cid,cs1.description as from_Status,cs2.description as to_Status,\n" +
                    "csc.card_status_auto_change_cid,csc.is_used_by_card_remote from card_status_changes csc\n" +
                    "inner join card_status cs1 on cs1.card_status_oid = csc.FROM_CARD_STATUS_OID\n" +
                    "inner join card_status cs2 on cs2.card_status_oid = csc.TO_CARD_STATUS_OID\n" +
                    "where csc.card_status_auto_change_cid=7901 and csc.is_used_by_card_remote='Y'\n" +
                    "and csc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID")
                    + " and cs1.card_status_oid=" + queryResults.get(0).get("CARD_STATUS_OID") + "\n" +
                    "and cs2.card_reissue_action_cid in (" + cardReissueActionCid + ")";
            queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            logger.log(query);
            if (queryResults.size() >= 1) {
                for (int i = 0; i <= queryResults.size() - 1; i++) {
                    commonPage.verifyPresenceOSpanText(logger, queryResults.get(i).get("TO_STATUS"));
                    if (i == queryResults.size() - 1) {
                        logger.log(queryResults.get(i).get("TO_STATUS"));
                        commonPage.clickButtonUsingSpecificTagName(logger, queryResults.get(i).get("TO_STATUS"), "span");
                        PropUtils.setProps("changeCardStatusValue", queryResults.get(i).get("TO_STATUS"), baseUtils.testDataFilePath);
                        PropUtils.setProps("cardReissueActionCid", queryResults.get(i).get("CARD_REISSUE_ACTION_CID"), baseUtils.testDataFilePath);
                    }
                }
            } else {
                commonPage.sleepForFewSeconds(1);
                basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                commonPage.clickButtonUsingSpecificTagName(logger, "Cancel", "a");
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "There is no record with the combination " + actionOfCard + " and " + cardReissueActionCid, baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User select or enter field value \"(.*)\" for a field \"(.*)\" based on its behavior \"(.*)\" from \"(.*)\"$")
    public void userSelectOrEnterFieldValueBasedOnItsBehavior(String fieldValue, String fieldName, String fieldBehavior, String fileName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (fileName.equalsIgnoreCase("properties")) {
                if (fieldName.equalsIgnoreCase("customerNumber")) {
                    basePage.userTypeIntoTextField(logger, By.xpath("//ols-autocomplete[@ng-reflect-name='" + fieldName + "']/mat-form-field/div/div/div[3]/input"), PropUtils.getPropValue(properties, fieldValue));
                    commonPage.clickButtonUsingSpan(logger, "Apply");
                    commonPage.sleepForFewSeconds(2);
                } else {
                    basePage.userTypeIntoTextField(logger, By.cssSelector("mav-input[ng-reflect-name='" + fieldName + "']>input"), PropUtils.getPropValue(properties, fieldValue));
                }
                logger.log(fieldName + " value is entered as : " + PropUtils.getPropValue(properties, fieldValue));
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate action page or popup header is \"(.*)\" with last 5 digits of card for action \"(.*)\"$")
    public void userValidateActionPageOrPopUpHeaderIs(String expActionHeaderText, String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            expActionHeaderText = expActionHeaderText + PropUtils.getPropValue(properties, "cardNumberToPerformAnAction").substring(PropUtils.getPropValue(properties, "cardNumberToPerformAnAction").length() - 6);
            String actActionHeaderText = "";
            if (action.equalsIgnoreCase("Reset pin")) {
                actActionHeaderText = basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='action-dialog-title']>h2"));
            } else {
                actActionHeaderText = basePage.userGetTextFromWebElement(logger, By.cssSelector("h2[class='mat-dialog-title']"));
            }
            commonPage.assertTwoStrings(logger, expActionHeaderText, actActionHeaderText, action);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User skip this scenario if card is not available to perform replaceCard scenario$")
    public void userSkipScenarioIfCardIsNotAvailableToPerformReplaceCardScenario() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (!PropUtils.getPropValue(properties, "replaceCardAvailability").equalsIgnoreCase("Yes")) {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "There is no card to perform replace card scenarios", baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User update card ifcs status from \"(.*)\" to \"(.*)\"$")
    public void userUpdateCardIfcsStatusFromOneStatusToAnother(String fromStatus, String toStatus) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.changeCardStatusFromRequestedNotIssuedToNoTransactions(logger, fromStatus, toStatus);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate \"(.*)\" message \"(.*)\" in \"(.*)\" module$")
    public void userValidateSnackBarMessage(String msgType, String expMessage, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (moduleName.equalsIgnoreCase("cards") || (expMessage.equalsIgnoreCase("Card status has been updated for 1 card(s)"))) {
                if (PropUtils.getPropValue(properties, "countOfRecords").equalsIgnoreCase("1")) {
                    expMessage = "Card status has been updated for all card(s)";
                }
            }
            commonPage.validateSnackBarOrHeaderText(logger, expMessage, msgType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate velocity limits in \"(.*)\" page for \"(.*)\" form based on \"(.*)\"$")
    public void userValidateVelocityLimitsInSpecificPage(String page, String pageAction, String cardControlProfileAction) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.handleVelocityLimitsInOrderCardPage(logger, pageAction, cardControlProfileAction);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select all subscriptions from the list if more than one subscription displayed based on customer and client for \"(.*)\" form$")
    public void userSelectAllSubscriptionsBasedOnCustomerAndClient(String pageAction) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            StringBuilder sb = new StringBuilder();
            String query = "";
            if (pageAction.equalsIgnoreCase("add")) {
                query = "select s.SUBSCRIPTION_OID, mc.processing_date,sf.description,s.OPT_IN_FLAG,a.account_oid from subscription_offers sf\n" +
                        "left join subscriptions s on s.subscription_offer_oid = sf.subscription_offer_oid\n" +
                        "left join accounts a on a.account_oid = s.account_oid\n" +
                        "left join m_customers mcust on mcust.customer_no = a.account_no\n" +
                        "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID") + " and mcust.customer_no = '" +
                        PropUtils.getPropValue(properties, "accountNumberInCardsModule")
                        + "' and s.card_oid is null and s.EXPIRES_ON >= mc.processing_date";
            } else {
                query = "select s.SUBSCRIPTION_OID, mc.processing_date,sf.description,s.OPT_IN_FLAG,a.account_oid from subscription_offers sf\n" +
                        "left join subscriptions s on s.subscription_offer_oid = sf.subscription_offer_oid\n" +
                        "left join accounts a on a.account_oid = s.account_oid\n" +
                        "left join m_customers mcust on mcust.customer_no = a.account_no\n" +
                        "left join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                        "where mc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID") + " and mcust.customer_no = '" +
                        PropUtils.getPropValue(properties, "accountNumberInCardsModule")
                        + "' and s.card_oid = " + PropUtils.getPropValue(properties, "cardNumberToPerformAnAction") + " and s.EXPIRES_ON >= mc.processing_date";
            }
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() >= 1) {
                if (PropUtils.getPropValue(PropUtils.getProps(baseUtils.commonPropertyFile), "environment").equalsIgnoreCase("uidev")) {
                    if (commonPage.getAttributeValue(By.cssSelector("div[class='price-value sub-display all-sub']>div>mat-checkbox"), "ng-reflect-checked").equalsIgnoreCase("false")) {
                        basePage.userClick(logger, By.cssSelector("div[class='price-value sub-display all-sub']>div>mat-checkbox"));
                        basePage.userClick(logger, By.cssSelector("div[class='price-value sub-display all-sub']>div>mat-checkbox"));
                    } else {
                        basePage.userClick(logger, By.cssSelector("div[class='price-value sub-display all-sub']>div>mat-checkbox"));
                    }
                } else {
                    List<WebElement> subscriptions = basePage.getListOfElements(logger, By.cssSelector("div[class='price-value sub-display ng-star-inserted']>div>div[class='sub-text sub-offer']>strong"));
                    List<WebElement> checkBoxes = basePage.getListOfElements(logger, By.cssSelector("div[class='price-value sub-display ng-star-inserted']>div>div[class='sub-section margin-2rem']>mat-checkbox"));
                    int optInSubscriptions = 0;
                    for (int i = 0; i <= queryResults.size() - 1; i++) {
                        for (int j = 0; j <= subscriptions.size() - 1; j++) {
                            if (queryResults.get(i).get("DESCRIPTION").equalsIgnoreCase(subscriptions.get(j).getText())) {
                                if (queryResults.get(i).get("OPT_IN_FLAG").equalsIgnoreCase("Y")) {
                                    commonPage.assertTwoStrings(logger, "true", checkBoxes.get(i).getAttribute("ng-reflect-checked"), "Check box status for subscription : " + queryResults.get(j).get("DESCRIPTION"));
                                    optInSubscriptions++;
                                } else {
                                    commonPage.assertTwoStrings(logger, "false", checkBoxes.get(i).getAttribute("ng-reflect-checked"), "Check box status for subscription : " + queryResults.get(j).get("DESCRIPTION"));
                                }
                                break;
                            }
                        }
                    }
                    if (optInSubscriptions == queryResults.size()) {
                        commonPage.assertTwoStrings(logger, "true", commonPage.getAttributeValue(By.cssSelector("div[class='price-value sub-display all-sub']>div>mat-checkbox"), "ng-reflect-checked"), "All Subscription check box status");
                        sb.append("ALL SUBSCRIPTIONS");
                    } else {
                        basePage.userClick(logger, By.xpath("//div[@class='all-sub-text']/strong[contains(text(),'ALL SUBSCRIPTIONS')]"));
                        sb.delete(0, sb.length());
                        sb.append("ALL SUBSCRIPTIONS");
                    }
                }
            } else {
                commonPage.verifyPresenceOfDivText(logger, "No subscriptions selected");
            }
            PropUtils.setProps(pageAction + "-Subscriptions", sb.toString(), baseUtils.testDataFilePath);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate delivery address stepper based on isAlternateAddressRequired \"(.*)\" for form \"(.*)\"$")
    public void userValidateByDefaultCardContactOrPrimaryAccountAddressWillBeSelectedBasedOnAvailability(String isAlternateAddressRequired, String pageAction) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.validateSendYourCardPinToTheFollowingAddressSection(logger, isAlternateAddressRequired, pageAction);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
    @When("^User get an account which is having \"(.*)\" records based on user type \"(.*)\"$")
    public void userGetAnAccountWhihcHasCardsBasedOnLoggedInUser(String moduleName, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.getAnAccountNumberWhichHasCardsBasedOnLoggedInUser(logger,userType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate specific address section \"(.*)\"$")
    public void validateSpecifcAddressSectopm(String typeOfAddress) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.validateSpecificAddress(logger, typeOfAddress);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User validate mail indicator value in \"(.*)\" page$")
    public void userValidateMailIndicatorValue(String pageType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.handleMailIndicator(logger, pageType, properties);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User select \"(.*)\" option from \"(.*)\" drop down$")
    public void userSelectOptionFromDropDown(String constantID, String dropDownName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select distinct ols.description,cs.CARD_REISSUE_ACTION_CID from card_status_ols ols\n" +
                    "inner join card_status cs on cs.card_status_oid = ols.card_status_oid\n" +
                    "inner join m_clients mc on mc.client_mid = cs.client_mid\n" +
                    "inner join m_customers mcust on mcust.client_mid = mc.client_mid\n" +
                    "where mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ")" +
                    " and cs.CARD_REISSUE_ACTION_CID = " + constantID;
            if (!basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'account-number')]")).equalsIgnoreCase("Select account")) {
                query = query +
                        " and mcust.customer_no = '" + basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'account-number')]")) + "'\n";
            }
            Map<String, String> queryResults = commonUtils.getQueryResultsOnMap(query);
            commonPage.selectValueFromMatSelectDropDown(logger, "Card " + queryResults.get("DESCRIPTION"), dropDownName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @When("^User enter \"(.*)\" field name \"(.*)\" in change pin popup with \"(.*)\" value$")
    public void userEnterFieldValueInChangePINPopUp(String fieldName, String className, String valueType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String pin = PropUtils.getPropValue(properties, "newPIN");
            if (valueType.equalsIgnoreCase("same")) {
                basePage.userTypeIntoTextField(logger, By.cssSelector("card-pin[ng-reflect-name = '" + className + "']>input"), pin);
            } else {
                String newPIN = PasswordGenerator.generateNumeric(4);
                basePage.userTypeIntoTextField(logger, By.cssSelector("card-pin[ng-reflect-name = '" + className + "']>input"), newPIN);
                PropUtils.setProps("newPIN", newPIN, baseUtils.testDataFilePath);
            }
            basePage.userClick(logger, By.cssSelector("card-pin[formcontrolname='pin']"));
            commonPage.sleepForFewSeconds(1);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select address based on radio button name \"(.*)\"$")
    public void userEnterFieldValueInChangePINPopUp(String radioButtonName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.enterAlternateAddressOrSelectPrimaryAddress(logger, radioButtonName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select \"(.*)\" value from filter \"(.*)\"$")
    public void userSelectsDesiredValueFromSpecificFilter(String value, String filterName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            usersPage.selectValuesFromFilter(logger, filterName, value);
            PropUtils.setProps("bulkCards-filterValues", value, baseUtils.testDataFilePath);
            String countOfRecords = usersPage.getCountOfRecords(logger);
            if (Integer.parseInt(countOfRecords) == 0) {
                PropUtils.setProps("testStatus", "skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "No records found to perform action", baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User verify the \"(.*)\" of status \"(.*)\" from status drop down$")
    public void userVerifyThePresenceOfStatus(String actionForStatus, String statusValue) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            basePage.userClick(logger, By.cssSelector("mav-svg-icon[class='icon-mask last-mask-icon mat-icon ng-star-inserted']>fa-icon>svg"));
            basePage.userClick(logger, By.xpath("//mav-select[@name='status']/mat-select/div/div[1]"));
            String[] statusValueArr = {statusValue};
            By statusLocator = null;
            if (statusValue.contains(",")) {
                statusValueArr = statusValue.split(",");
            }
            for (int i = 0; i < statusValueArr.length; i++) {
                statusLocator = By.xpath("//span[contains(text(),'" + statusValueArr[i] + "')]");
                if (actionForStatus.equalsIgnoreCase("Presence")) {
                    if (basePage.whetherElementPresent(logger, statusLocator)) {
                        logger.log("'" + statusValue + "' displayed as expected");
                    }
                } else {
                    basePage.userClick(logger, statusLocator);
                    commonPage.clickButtonUsingSpan(logger, "Save");
                }
            }
            PropUtils.setProps("bulkCards-actionOfCardStatus", actionForStatus, baseUtils.testDataFilePath);
            commonPage.sleepForFewSeconds(2);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select check box which is at position \"(.*)\"$")
    public void userSelectCheckBoxWhichIsAtPosition(String position) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",basePage.getWebElementUsingLocator(logger,By.xpath("(//label[@class='mat-checkbox-layout']/div/input)[" + position + "]")));
            basePage.userClickJSExecutor(logger, By.xpath("(//label[@class='mat-checkbox-layout']/div/input)[" + position + "]"));
            logger.log("Check box has been checked which is at position " + position);
//            PropUtils.setProps("countOfRecords", usersPage.getCountOfRecords(logger), baseUtils.testDataFilePath);
            if (position.equals("2")) {
                PropUtils.setProps("bulkCards-cardNumberForWhichStatusChanged", basePage.userGetTextFromWebElement(logger, By.xpath("(//div[@class='panel-header-field header-field-primary'])[1]")).substring(4), baseUtils.testDataFilePath);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter account number in corresponding field which \"(.*)\", \"(.*)\" status and \"(.*)\" based on logged in user \"(.*)\"$")
    public void userEnterAccountNumberInCorrespondingFieldBasedOnItsStatusForLoggedInUser(String isContains, String status, String subStatus, String userName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            boolean has = false;
            if (isContains.equalsIgnoreCase("has")) {
                has = true;
            }
            cardsPage.enterAccountNumberBasedOnStatus(logger, status, subStatus, has);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select \"(.*)\" as radio button \"(.*)\" for \"(.*)\" form when logged in user \"(.*)\"$")
    public void userSelectRadioButtonBasedOnItsName(String sectionName, String radioButton, String action, String loggedInUser) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (radioButton.equalsIgnoreCase("any")) {
                try {
                    driver.findElement(By.cssSelector("mat-radio-button[ng-reflect-value='self']"));
                    radioButton = "self";
                } catch (Exception e) {
                    try {
                        driver.findElement(By.cssSelector("mat-radio-button[ng-reflect-value='system']"));
                        radioButton = "system";
                    } catch (Exception e1) {
                        driver.findElement(By.cssSelector("mat-radio-button[ng-reflect-value='group']"));
                        radioButton = "group";
                    }
                }
            }
            if (action.equalsIgnoreCase("add")) {
                basePage.userClick(logger, By.cssSelector("mat-radio-button[ng-reflect-value='" + radioButton + "']"));
                if (radioButton.equalsIgnoreCase("self")) {
                    PropUtils.setProps(action + "-identificationMethod", "Self select pin", baseUtils.testDataFilePath);
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[1]"), "9");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[2]"), "0");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[3]"), "3");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[4]"), "4");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[1]"), "9");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[2]"), "0");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[3]"), "3");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[4]"), "4");
                    logger.log("Identification method has been selected as self select PIN");
                }
                if (radioButton.equalsIgnoreCase("group")) {
                    PropUtils.setProps(action + "-identificationMethod", "Group pin", baseUtils.testDataFilePath);
                    logger.log("Identification method has been selected as Group PIN");
                    basePage.userClick(logger, By.cssSelector("mav-select[ng-reflect-name='gpin']>mat-select"));
                    basePage.selectRandomValueFromDropDown(logger, By.cssSelector("span[class='mat-option-text']"));
                    PropUtils.setProps(action + "-GroupPINValue", commonPage.getAttributeValue(By.cssSelector("mav-select[ng-reflect-name='gpin']>mat-select"), "ng-reflect-model"), baseUtils.testDataFilePath);
                } else if (radioButton.equalsIgnoreCase("system")) {
                    PropUtils.setProps(action + "-identificationMethod", "System generated PIN", baseUtils.testDataFilePath);
                    logger.log("Identification method has been selected as System generated PIN");
                }
            } else if (action.equalsIgnoreCase("edit")) {
                if (PropUtils.getPropValue(properties, action + "-IdentificationMethod").equalsIgnoreCase("Group pin")) {
                    commonPage.assertTwoStrings(logger, "group", radioButton, "Selected Identification method");
                } else if (PropUtils.getPropValue(properties, action + "-IdentificationMethod").equalsIgnoreCase("Self select pin")) {
                    commonPage.assertTwoStrings(logger, "self", radioButton, "Selected Identification method");
                } else if (PropUtils.getPropValue(properties, action + "-IdentificationMethod").equalsIgnoreCase("System generated pin")) {
                    commonPage.assertTwoStrings(logger, "system", radioButton, "Selected Identification method");
                }
            } else if (action.equalsIgnoreCase("clone")) {
                if (loggedInUser.equalsIgnoreCase("CSR")) {
                    if (!basePage.userGetTextFromWebElement(logger, By.cssSelector("mat-radio-group[ng-reflect-name='pinType']>mat-radio-button[class='cp-div-pinrad mat-radio-button mat-accent mat-radio-checked']>label>div[class='mat-radio-label-content']")).equalsIgnoreCase("Self-select PIN")) {
                        commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, action + "-identificationMethod"), basePage.userGetTextFromWebElement(logger, By.cssSelector("mat-radio-group[ng-reflect-name='pinType']>mat-radio-button[class='cp-div-pinrad mat-radio-button mat-accent mat-radio-checked']>label>div[class='mat-radio-label-content']")), "Identification method in " + action + " page");
                    }
                } else if (loggedInUser.equalsIgnoreCase("Custoemr")) {
                    commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, action + "-identificationMethod"), basePage.userGetTextFromWebElement(logger, By.cssSelector("mat-radio-group[ng-reflect-name='pinType']>mat-radio-button[class='cp-div-pinrad mat-radio-button mat-accent mat-radio-checked']>label>div[class='mat-radio-label-content']")), "Identification method in " + action + " page");
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter values for \"(.*)\" with \"(.*)\" values$")
    public void userEnterNewPinAndconfirmPInValues(String pinAction, String typeOfValues) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (pinAction.equalsIgnoreCase("selfSelectPin") || pinAction.equalsIgnoreCase("groupPin")) {
                basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[1]"), "9");
                commonPage.sleepForFewSeconds(1);
                logger.log("1st PIN number entered successfully");
                basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[2]"), "0");
                commonPage.sleepForFewSeconds(1);
                logger.log("2nd PIN number entered successfully");
                basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[3]"), "3");
                commonPage.sleepForFewSeconds(1);
                logger.log("3rd PIN number entered successfully");
                basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[1]/input[4]"), "4");
                commonPage.sleepForFewSeconds(1);
                logger.log("4th PIN number entered successfully");
                if (typeOfValues.equalsIgnoreCase("valid")) {
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[1]"), "9");
                    commonPage.sleepForFewSeconds(1);
                    logger.log("1st PIN number entered successfully");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[2]"), "0");
                    commonPage.sleepForFewSeconds(1);
                    logger.log("2nd PIN number entered successfully");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[3]"), "3");
                    commonPage.sleepForFewSeconds(1);
                    logger.log("3rd PIN number entered successfully");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[4]"), "4");
                    commonPage.sleepForFewSeconds(1);
                    logger.log("4th PIN number entered successfully");
                    PropUtils.setProps("cardActions-enteredPin", "9034", baseUtils.testDataFilePath);
                } else {
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[1]"), "9");
                    logger.log("1st PIN number entered successfully");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[2]"), "0");
                    logger.log("2nd PIN number entered successfully");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[3]"), "3");
                    logger.log("3rd PIN number entered successfully");
                    basePage.userTypeIntoTextField(logger, By.xpath("(//div[@class='cp-div-pinmain ng-star-inserted']/div[@class='cp-div-pin'])[2]/input[4]"), "8");
                    logger.log("4th PIN number entered successfully");
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User get card number for which action applicable \"(.*)\" and action type \"(.*)\"$")
    public void userSelectRadioButtonBasedOnItsName(String actionApplicable, String actionType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.getCardNumberBasedOnSpecificAction(logger, actionApplicable, actionType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate bulk card change status behavior when no of cards selected \"(.*)\"$")
    public void userSelectRadioButtonBasedOnItsName(String noOfCardsSelected) {
        try {
            driver.findElement(By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')])/td[1]"));
        } catch (Exception e) {
            PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
            PropUtils.setProps("skipReason", "Cards not available to test this scenario", baseUtils.testDataFilePath);
        }
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            List<WebElement> records = basePage.getListOfElements(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')])/td[1]"));
            if (records.size() < Integer.parseInt(noOfCardsSelected)) {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                PropUtils.setProps("skipReason", "Cards not available to test this scenario", baseUtils.testDataFilePath);
            }
        }
        properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (noOfCardsSelected.equalsIgnoreCase("0")) {
                commonPage.assertTwoStrings(logger, "No cards selected", basePage.userGetTextFromWebElement(logger, By.cssSelector("div[ng-reflect-ng-class='list-unselected-msg']")), "header message when no of card selected " + noOfCardsSelected);
            } else {
                if (Integer.parseInt(noOfCardsSelected) <= Integer.parseInt(basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='view-page-list']")).substring(0, basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='view-page-list']")).indexOf(' ')).trim())) {
                    String selectedCardNos = "";
                    String cardNo = "";
                    List<WebElement> allRecords = basePage.getListOfElements(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')])/td[1]"));
                    for (int i = 0; i < Integer.parseInt(noOfCardsSelected); i++) {
                        allRecords.get(i).click();
                        cardNo = basePage.userGetTextFromWebElement(logger, By.xpath("(//tbody[@role='rowgroup']/tr[contains(@class,'table-element-row')][" + (i + 1) + "])/td[2]"));
                        if (i == Integer.parseInt(noOfCardsSelected) - 1) {
                            selectedCardNos = selectedCardNos + cardsPage.getCardOidBasedOnCardNo(cardNo.substring(cardNo.length() - 6));
                        } else {
                            selectedCardNos = selectedCardNos + cardsPage.getCardOidBasedOnCardNo(cardNo.substring(cardNo.length() - 6)) + ",";
                        }
                    }
                    PropUtils.setProps("selectedCardOidsForBulkChangeStatus", selectedCardNos, baseUtils.testDataFilePath);
                    if (allRecords.size() > Integer.parseInt(noOfCardsSelected)) {
                        commonPage.assertTwoStrings(logger, noOfCardsSelected + " cards selected", basePage.userGetTextFromWebElement(logger, By.cssSelector("div[ng-reflect-ng-class='list-selected-msg']")), "header message when no of card selected " + noOfCardsSelected);
                    } else {
                        commonPage.assertTwoStrings(logger, "All cards selected", basePage.userGetTextFromWebElement(logger, By.cssSelector("div[ng-reflect-ng-class='list-selected-msg']")), "header message when no of card selected " + noOfCardsSelected);
                    }
                    commonPage.sleepForFewSeconds(3);
                    basePage.userClick(logger, By.cssSelector("span[ng-reflect-message='Change card status']>mav-svg-icon[ng-reflect-value='far sync-alt']"));
                    basePage.userClick(logger, By.cssSelector("mav-select[name='status']"));
                    String query = "Select cs2.card_reissue_action_cid,cs1.description as from_Status,cs2.description as to_Status,\n" +
                            "csc.card_status_auto_change_cid,csc.is_used_by_card_remote from card_status_changes csc\n" +
                            "inner join card_status cs1 on cs1.card_status_oid = csc.FROM_CARD_STATUS_OID\n" +
                            "inner join card_status cs2 on cs2.card_status_oid = csc.TO_CARD_STATUS_OID\n" +
                            "where csc.card_status_auto_change_cid=7901 and csc.is_used_by_card_remote='Y'\n" +
                            "and csc.client_mid = " + PropUtils.getPropValue(properties, "ClientMID")
                            + " and cs1.description = '" + PropUtils.getPropValue(properties, "selectedCardStatuses") + "'\n" +
                            "and cs2.card_reissue_action_cid in (5901)";
                    logger.log(query);
                    List<Map<String, String>> allQueryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    if (allQueryResults.size() >= 1) {
                        for (int i = 0; i <= allQueryResults.size() - 1; i++) {
                            commonPage.verifyPresenceOSpanText(logger, allQueryResults.get(i).get("TO_STATUS"));
                            if (i == allQueryResults.size() - 1) {
                                commonPage.clickButtonUsingSpecificTagName(logger, allQueryResults.get(i).get("TO_STATUS"), "span");
                                logger.log("status " + allQueryResults.get(i).get("TO_STATUS") + " has been selected");
                                PropUtils.setProps("bulkChangeCardStatusValue", allQueryResults.get(i).get("TO_STATUS"), baseUtils.testDataFilePath);
                                PropUtils.setProps("cardReissueActionCid", allQueryResults.get(i).get("CARD_REISSUE_ACTION_CID"), baseUtils.testDataFilePath);
                            }
                        }
                    } else {
                        commonPage.clickButtonUsingSpecificTagName(logger, "Cancel", "a");
                        PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                        PropUtils.setProps("skipReason", "There is no record with the combination 5901,5902,5903 and " + PropUtils.getPropValue(properties, "selectedCardStatuses"), baseUtils.testDataFilePath);
                    }
                    properties = PropUtils.getProps(baseUtils.testDataFile);
                    commonPage.clickButtonUsingSpan(logger, "Save");
                    commonPage.verifyPresenceOfText(logger, "Card status has been updated for  " + noOfCardsSelected + " card(s)");
                    cardsPage.validateCardStatusBasedOnCardOid(logger, PropUtils.getPropValue(properties, "selectedCardOidsForBulkChangeStatus"), PropUtils.getPropValue(properties, "bulkChangeCardStatusValue"));
                } else {
                    commonPage.clickButtonUsingSpecificTagName(logger, "Cancel", "a");
                    PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User select card statuses which are applicable for change card status action$")
    public void userGetCardStatusesWhichAreApplicableForChangeCardStatusAction() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select distinct cs.description from cards c \n" +
                    " inner join card_products cp on cp.card_product_oid = c.card_product_oid\n" +
                    " inner join card_offers co on co.card_offer_oid = cp.card_offer_oid\n" +
                    " inner join m_customers mcu on mcu.customer_mid = c.customer_mid\n" +
                    " inner join m_clients mc on mc.client_mid = mcu.client_mid\n" +
                    " left join card_status cs on cs.card_status_oid = c.card_status_oid\n" +
                    " left join card_status_ols_mapping csom on csom.card_status_oid = cs.card_status_oid\n" +
                    " where c.replace_Card_oid is null and mcu.is_pin_req ='Y' \n" +
                    " and mc.client_mid in (" + PropUtils.getPropValue(properties, "ClientMID") + ")\n" +
                    " and cs.is_Valid='Y' and cs.is_active='Y' and csom.is_Status_change='Y'\n" +
                    " and mcu.customer_no = '" + PropUtils.getPropValue(properties, "accountNumberInCardsModule") + "'";
            logger.log(query);
            Map<String, String> firstStatuses = commonUtils.getQueryResultsOnMap(query);
            if (firstStatuses.size() == 0) {
                PropUtils.setProps("testStatus", "Skipped", baseUtils.testDataFilePath);
            } else {
                usersPage.scrollDown(logger);
                basePage.userClick(logger, By.cssSelector("mat-select[ng-reflect-name='onlineStatus']"));
                basePage.userClick(logger, By.cssSelector("mat-option[ng-reflect-value='" + firstStatuses.get("DESCRIPTION") + "']>mat-pseudo-checkbox"));
                PropUtils.setProps("selectedCardStatuses", firstStatuses.get("DESCRIPTION"), baseUtils.testDataFilePath);
                Actions actions = new Actions(driver);
                actions.moveToElement(basePage.getWebElementUsingLocator(logger, By.xpath("//span[contains(text(),'Search')]"))).click().perform();
                commonPage.sleepForFewSeconds(5);
            }
            if (basePage.userGetTextFromWebElement(logger, By.xpath("//div[contains(@class,'account-number')]")).equalsIgnoreCase("Select account")) {
                basePage.userTypeIntoTextField(logger, By.xpath("//ols-autocomplete[@ng-reflect-name='customerNumber']/mat-form-field/div/div/div[3]/input"), PropUtils.getPropValue(properties, "accountNumberInCardsModule"));
                commonPage.clickButtonUsingSpan(logger, "Apply");
                commonPage.sleepForFewSeconds(2);
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User validate records displayed based on the selected values from filters 'All products' and 'All statuses'$")
    public void userValidateRecordsDisplayedBasedOnSelectedValuesFromFilters() {
        Properties testData = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(testData, "testStatus").equalsIgnoreCase("Skipped")) {
            String accountNumber = PropUtils.getPropValue(testData, "orderCard-AccountNo");
            cardData = cardsPage.getCardDataBasedOnAccount(logger);
            String arrAllCardProducts[] = null;
            String dbAllCardProducts = cardsPage.getAllCardProductsBasedOnAccount(cardData, accountNumber);
            arrAllCardProducts = dbAllCardProducts.split(",");
            String arrAllCardStatuses[] = null;
            String dbAllCardStatuses = cardsPage.getAllCardStatusesBasedOnClient();
            arrAllCardStatuses = dbAllCardStatuses.split(",");
            String cardStatuses = "";
            String cardProductIds = "";
            String query = "";
            for (int i = 0; i <= arrAllCardProducts.length - 1; i++) {
                for (int j = 0; j <= arrAllCardStatuses.length - 1; j++) {
                    // Validate count of records for each status and role
                    if (i == arrAllCardProducts.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All statuses", dbAllCardStatuses);
                        cardStatuses = cardsPage.getCardStatusesInSingleQuite(dbAllCardStatuses);
                        usersPage.selectValuesFromFilter(logger, "All products", arrAllCardProducts[i]);
                        cardProductIds = cardsPage.getAllCardProductIdsBasedOnProductDescription(cardData, arrAllCardProducts[i]);
                    } else if (j == arrAllCardStatuses.length - 1) {
                        usersPage.selectValuesFromFilter(logger, "All statuses", arrAllCardStatuses[j]);
                        cardStatuses = arrAllCardStatuses[j];
                        usersPage.selectValuesFromFilter(logger, "All products", dbAllCardProducts);
                        cardProductIds = cardsPage.getAllCardProductIdsBasedOnProductDescription(cardData, dbAllCardProducts);
                    } else {
                        usersPage.selectValuesFromFilter(logger, "All statuses", arrAllCardStatuses[j]);
                        usersPage.selectValuesFromFilter(logger, "All products", arrAllCardProducts[i]);
                        cardStatuses = arrAllCardStatuses[j];
                        cardProductIds = cardsPage.getAllCardProductIdsBasedOnProductDescription(cardData, arrAllCardProducts[i]);
                    }
                    query = "select * from cards c\n" +
                            "inner join m_customers mcust on mcust.customer_mid = c.customer_mid\n" +
                            "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                            "where mc.short_name = '" + PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "' and mcust.customer_no = " + accountNumber + "\n" +
                            "and card_status_oid in (select csom.card_status_oid from card_status_ols_mapping csom\n" +
                            "inner join card_status_ols cso on cso.card_status_ols_oid = csom.card_status_ols_oid\n" +
                            "inner join m_clients mc on mc.client_mid = cso.client_mid\n" +
                            "where cso.description in ('" + cardStatuses + "') and mc.short_name = '" + PropUtils.getPropValue(baseUtils.inputProp, BaseUtils.clientCountry) + "')" +
                            " and card_product_oid in (" + cardProductIds + ")";
                    logger.log(query);
                    List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
                    String expRecordsCount = usersPage.getCountOfRecords(logger);
                    String actualRecordsCount = String.valueOf(queryResults.size());
                    basePage.assertTwoStrings(logger, expRecordsCount, actualRecordsCount);
                    usersPage.clickResetAllFilters(logger);
                }
            }
        } else {
            logger.log(PropUtils.getPropValue(testData, "skipReason"));
        }
    }

    @Then("^User get account number which is \"(.*)\" for create a new cardControlProfile$")
    public void userGetAccountNumberWhichIsEligibleForCreateNewCardControlProfile(String action) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            cardsPage.getAccountNumberWhichIsEligibleToCreateCCP(logger, action);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get an account number which is eligible to order or edit a card based on \"(.*)\" and \"(.*)\" for form \"(.*)\" as user \"(.*)\"$")
    public void userGetAnAccountNumberWhichIsEligibleToOrderACard(String cardReissueTypeCid, String identificationMethod, String cardType, String userType) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String cardPinControlCid = "5151";
//            if (identificationMethod.equalsIgnoreCase("self")) {
//                cardPinControlCid = "5121";
//            }
//            if (identificationMethod.equalsIgnoreCase("system") || identificationMethod.equalsIgnoreCase("group")) {
//                cardPinControlCid = "5110";
//            }
            cardsPage.getAnEligibleAccountNumberToOrderACard(logger, cardPinControlCid, cardReissueTypeCid, cardType,userType);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User get field status based on validation controls in cards module$")
    public void userGetFieldStatusBasedOnValidationControls() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            validationControlFields = accountInfoPage.getValidationControlRecordsBasedOnSectionColumnNames(logger, "cards", "CSR");
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate and click Continue if reissue is required for form \"(.*)\"$")
    public void userValidateAndClickContinueIfReissueForForm(String form) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            if (!PropUtils.getPropValue(properties, form + "-fieldWhichHasCardReissueTypeCid").equalsIgnoreCase("")) {
                commonPage.verifyPresenceOfText(logger, "These changes will require a reissue of this card");
                commonPage.verifyPresenceOfText(logger, "Do you want to continue?");
                commonPage.clickButtonUsingSpan(logger, "Continue");
                cardsPage.validateSendYourCardPinToTheFollowingAddressSection(logger, "No", form);
                commonPage.clickButtonUsingSpan(logger, "Review");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate newly created group pin is saved in the database or not$")
    public void userValidationNewlyCreatedGroupPinIsSavedInTheDatabaseOrNot() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String query = "select * from customer_pin_groups cpg\n" +
                    "left join m_customers mcust on mcust.customer_mid = cpg.customer_mid\n" +
                    "where cpg.name = '" + PropUtils.getPropValue(properties, "groupPin-pinName") + "' " +
                    "and mcust.customer_no = '" + PropUtils.getPropValue(properties, "accountNo") + "' " +
                    "and mcust.client_mid = " + PropUtils.getPropValue(properties, "ClientMID");
            List<Map<String, String>> queryResults = commonUtils.getAllRowsOfQueryResultsOnListMap(query);
            if (queryResults.size() == 1) {
                logger.log("Newly created group pin '" + PropUtils.getPropValue(properties, "groupPin-pinName") + "' has been saved in the database");
            } else {
                logger.log("Newly created group pin is not saved in the database");
            }
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @Then("^User validate count of \"(.*)\" from property file based on property key \"(.*)\" in module \"(.*)\"$")
    public void userValidateCountOfRecordsBasedOnModuleAndUserType(String recordsName, String propertyKey, String moduleName) {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            commonPage.assertTwoStrings(logger, PropUtils.getPropValue(properties, propertyKey) + " " + recordsName, basePage.userGetTextFromWebElement(logger, By.cssSelector("div[class='grid-meta-top ng-star-inserted']>div")), "Count of records from module " + moduleName);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }

    @And("^User enter amount value in adhoc fee module based on configuration in operation limit$")
    public void userEnterAmountValueBasedOnConfigurationInOperationLimit() {
        Properties properties = PropUtils.getProps(baseUtils.testDataFile);
        if (!PropUtils.getPropValue(properties, "testStatus").equalsIgnoreCase("Skipped")) {
            String value = String.valueOf(cardsPage.getIntegerNumberInBetweenLimit("1", String.valueOf(Integer.parseInt(PropUtils.getPropValue(properties, "maxSundryAdjEntryAmnt")))));
            PropUtils.setProps("fee_amount", value, baseUtils.testDataFilePath);
            basePage.userTypeIntoTextField(logger, By.cssSelector("mav-input[ng-reflect-name='amount']>input"), value);
        } else {
            logger.log(PropUtils.getPropValue(properties, "skipReason"));
        }
    }
}