Feature: Country User Adhoc Fee module scenarios

  Scenario: Country User : OMV-3462 Add adhoc fee using Type as 'Fee'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User verify the logged in user is eligible to create "new" adhoc fee or not
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Adhoc fee"
    When User make the driver sleep for few seconds "3"
    Then User click on button contains "Fee" using locator "mat-radio-button[ng-reflect-value='Fee']" which has locator type "cssSelector" using method "click"
    And User click drop down "feeType" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    When User enter amount value in adhoc fee module based on configuration in operation limit
    Then User select date "effectiveDate" based on client processing date future or past "future" days "0"
    And User enter "reference" as "RandomAlphanumeric" in "adhocFee" module having length "6" in "add" form using input tag
    And User enter value "DetailedNote" using locator "textarea[ng-reflect-name='notes']" with locator type "cssSelector" is from property file "No"
    And User click on button "Submit" using span text
    Then User validate presence of "A fee has been applied to the account" field with "div" tag
    And User validate Transaction fee record in database to verify it has been posted successfully

  Scenario: Country User : OMV-3462 Add adhoc fee using Type as 'Fee Reversal'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User verify the logged in user is eligible to create "new" adhoc fee or not
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Adhoc fee"
    Then User click on button contains "Fee reversal" using locator "mat-radio-button[ng-reflect-value='Fee reversal']" which has locator type "cssSelector" using method "click"
    And User click drop down "feeType" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    When User enter amount value in adhoc fee module based on configuration in operation limit
    Then User select date "effectiveDate" based on client processing date future or past "future" days "0"
    And User enter "reference" as "RandomAlphanumeric" in "adhocFee" module having length "6" in "add" form using input tag
    And User enter value "DetailedNote" using locator "textarea[ng-reflect-name='notes']" with locator type "cssSelector" is from property file "No"
    And User click on button "Submit" using span text
    Then User validate presence of "A fee has been applied to the account" field with "div" tag
    And User validate Transaction fee record in database to verify it has been posted successfully

  Scenario: Country User : OMV-3462 Verify error message when duplicate reference name entered
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User verify the logged in user is eligible to create "old" adhoc fee or not
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Adhoc fee"
    Then User click on button contains "Fee reversal" using locator "mat-radio-button[ng-reflect-value='Fee']" which has locator type "cssSelector" using method "click"
    And User click drop down "feeType" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    When User enter amount value in adhoc fee module based on configuration in operation limit
    Then User select date "effectiveDate" based on client processing date future or past "future" days "0"
    And User enter "reference" as "RandomAlphanumeric" in "adhocFee" module having length "6" in "add" form using input tag
    And User enter value "OldReference" using locator "mav-input[ng-reflect-name='reference']>input" with locator type "cssSelector" is from property file "Yes"
    And User enter value "DetailedNote" using locator "textarea[ng-reflect-name='notes']" with locator type "cssSelector" is from property file "No"
    And User click on button "Submit" using span text
    Then User validate presence of "Sundry Adjustment already processed" field with "div" tag