Feature: Account information

  Background:
    Given  User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User click on button "Home" using span text
    And User click on the "menu" "Admin"
    And User click on the "menu" "Account Information"

  @Smoke @Regression
  Scenario: OLS-564: Validate the account information page
    And Verify the presence of the page header "Account information"
    And Verify the tabs in account information page "Summary"

  @Regression
  Scenario: OLS-565 Validate the status of the accounts
    And  User verifies the account status
    And  User verifies whether the respective warning message is displayed when the status is other than 1 - Active

  @Smoke @Regression
  Scenario: OLS-565 Validate the account details information in summary tab
    And User validate whether the account details section has "Account name" "Doing business as" "Account number" "Status"
    And User click on the edit address
    And User validate whether the Address has "addressLine" "suburb" "postalCode" "state" and the respective information are fetching from the db based on the account
    And User click on "Cancel" based on tag name "a"
    And User click on the edit contact
    And User validate whether the contact information section has "contactName" "emailAddress" "mobilePhone" "otherPhone" "fax" and the respective information are fetching from the db based on the account
    And User click on "Cancel" based on tag name "a"
    Then User Verify whether the credit overview section has "Account balance" "Credit limit" "Available" details and the respective information are fetching from the db based on the account

  @Regression
  Scenario: OLS-565 Validate the Edit contact information
    And User click on the edit contact
    And User enter "contactName" as "RandomAlphanumericWithFewSpecialChars" in module "accountInformation" having length "10" in "Edit contact" form
    And User enter "emailAddress" as "vignesh.babu@wexinc.com" in module "accountInformation" having length "10" in "Edit contact" form
    And User enter "mobilePhone" as "Numeric" in module "accountInformation" having length "10" in "Edit contact" form
    And User enter "otherPhone" as "Numeric" in module "accountInformation" having length "10" in "Edit contact" form
    And User enter "fax" as "Numeric" in module "accountInformation" having length "10" in "Edit contact" form
    And User click on button "Save" using span text
#    And User click on "Save" button in contact
    And User validated success message of Edit status is "Contact information has been updated"
    And User validate edit contact data is saved in database

  @Regression
  Scenario: OLS-565 Add and Validate the Edit Address information
    And User click on the edit address
    And User enter "addressLine" as "123 Mount Road,Mount Garden" in module "accountInformation" having length "90" in "Edit Address" form
    And User enter "suburb" as "Canterbury" in module "accountInformation" having length "90" in "Edit Address" form
    And User enter "postalCode" as "Numeric" in module "accountInformation" having length "10" in "Edit Address" form
    And User verify by default "Use same as Contact Address" check box is selected or not
    And User click on button "Save" using span text
    Then User validate "snackbar-text" message "Contact address has been updated" in "accountInfo" module
    And User validate edit address data is saved in database

  Scenario: OLS User:OMV-1753: User validate account sub status has been displayed as constant description which is associated in accounts table
    Then User validate account substatus is displayed as constant description

  Scenario: TC-01 : OLS User: OMV-2078 User should be able to click on "Site Locator" from admin module and navigate to corresponding window
    And User click on the "menu" "Site locator"
    Then User validate new window is opened or not and close the window

  @AccountInformation
  Scenario: TC-01 : Country User : OMV-1754 Validate user must be able to view additional account related information in the account details for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User enter account number in corresponding field which "has", "Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "Account Summary"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "ACCOUNT DETAILS"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "APPLICATION DETAILS"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "ADDITIONAL DETAILS"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "CREDIT OVERVIEW"

  Scenario: TC01: Country User :  OMV-1754 : User must be able to edit 'Account Summary' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Account Summary"

  Scenario: TC01: Country User :  OMV-1754 : User must be able to edit 'Additional details' section in Account Information module
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Additional details"

  Scenario: TC01: Country User : OMV-1754 : User must be able to edit 'Account Details' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Account Details"

  Scenario: TC01: Country User : OMV-1754 : User must be able to edit 'Application details' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Application details"


  Scenario: TC01 : Country User : OMV-1754 Validate user is able to edit 'Account summary' details and approve them
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Account Summary"
    And User validate presence of "Needs approval" field with "span" tag
    And User click on three dot icon for "Account Summary" section and click on "Approve" button and validate message beside AccountSummary section

  Scenario: TC01: Country User :  OMV-1754 : User must be able to edit 'Account Summary' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control Edit icon is enabled and modify eligible fields then validate in database for "account Summary"


  Scenario: TC01 : Country User : OMV-1754 Validate user is able to view approved upates in 'view updates' pop up of 'Account summary' details section
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on three dot icon for "Account Summary" section and click on "View updates" button then validate latest updated values are present or not

  Scenario: TC01: Country User : OMV-1754 : User must be able to edit 'Pricing control','Athentication','Additional details' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Pricing control"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Athentication"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Additional details"

    ###################### Financial information section validation ########################
  Scenario: TC-01 : Country User : OMV-1754 : Validate user must be able to view financial account related information in the account details for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "CountryUserName"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Financial information"
    And User validate message "Financial information" based on tag name "div"
    ##AccountDetails-FinancialInfo
    And User verify based on validation control user should be able to "view" fields then validate in database for section "ACCOUNT DETAILS"
    And User validate fields based on validation control and their values for section "CREDIT OVERVIEW" in "Financial Information" module
    And User validate fields based on validation control and their values for section "ACCOUNT DETAILS" in "Financial Information" module
    And User validate fields based on validation control and their values for section "CREDIT/ALERT DETAILS" in "Financial Information" module
    And User validate fields based on validation control and their values for section "CARD BALANCE ALERTS" in "Financial Information" module
    And User validate fields based on validation control and their values for section "BILLING DETAILS" in "Financial Information" module
    And User validate fields based on validation control and their values for section "BANK DETAILS" in "Financial Information" module
    And User validate fields based on validation control and their values for section "SECURITY DETAILS" in "Financial Information" module
    And User validate fields based on validation control and their values for section "DUNNING" in "Financial Information" module
    And User validate fields based on validation control and their values for section "SECURITIES/SOLVENCY" in "Financial Information" module
    And User validate fields based on validation control and their values for section "GUARANTEE" in "Financial Information" module
############3
  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of account details section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "account details"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Credit/Alerts Details section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Credit/Alerts Details"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of CARD BALANCE ALERTS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "CARD BALANCE ALERTS"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of BANK DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "BANK DETAILS"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Security deposit section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Security Deposit"


  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Billing DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Billing DETAILS"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Dunning DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Dunning DETAILS"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Security/Solvency DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Security/Solvency DETAILS"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Guarantee DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Guarantee DETAILS"

  Scenario: TC-01 : Country User : OMV-1754 Validate view multiple bank account sections in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number which has more than one bank account
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "view" fields then validate in database for section "mulltipleBankAccounts"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of GUARANTEES section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "GUARANTEES"

  Scenario: TC-01 : Country User : OMV-1754 Validate edit functionality of Authentication details section in 'Account information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Authentication details"

