Feature: UI:ClientUser - Financial Information scenarios from Accounts module
    ###################### Financial information section validation ########################

  @UI-FinancialInformation  @Testing
  Scenario: TC-01 : Client User : OMV-1403 : Validate user must be able to view financial account related information in the account details for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    And User enter account number in corresponding field which "has", "Active" status and "Active" based on logged in user "ClientUserName"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Financial information"
    And User validate message "Financial information" based on tag name "div"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "ACCOUNT DETAILS" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "CREDIT OVERVIEW" for user "CSR"
#    And User verify based on validation control user should be able to "view" fields then validate in database for section "CREDIT / ALERT DETAILS" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "CARD BALANCE ALERTS" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "BILLING DETAILS" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "BANK DETAILS" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "SECURITY DEPOSIT" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "DUNNING" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "SECURITIES/SOLVENCY" for user "CSR"
    And User verify based on validation control user should be able to "view" fields then validate in database for section "GUARANTEE" for user "CSR"
############3
  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1011 Validate edit functionality of account details section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    And User verify based on validation control user should be able to "Edit" fields then validate in database for section "ACCOUNT DETAILS" for user "CSR"

  Scenario: TC-01 : Client User : OMV-1013 Validate edit functionality of Credit/Alerts Details section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    And User verify based on validation control user should be able to "Edit" fields then validate in database for section "CREDIT / ALERT DETAILS" for user "CSR"

  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1012 Validate edit functionality of CARD BALANCE ALERTS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "CARD BALANCE ALERTS" for user "CSR"

  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1017 Validate edit functionality of BANK DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "BANK DETAILS" for user "CSR"

    ##Done
  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1018 Validate edit functionality of Security deposit section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "SECURITY DEPOSIT" for user "CSR"

  Scenario: TC-01 : Client User : OMV-1014 Validate edit functionality of Billing DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "BILLING DETAILS" for user "CSR"

  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1064 Validate edit functionality of Dunning DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "DUNNING" for user "CSR"

  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1000 Validate edit functionality of Security/Solvency DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "SECURITIES/SOLVENCY" for user "CSR"

  @UI-FinancialInformation
  Scenario: TC-01 : Client User : OMV-1014 Validate edit functionality of Guarantee DETAILS section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "GUARANTEE" for user "CSR"

  Scenario: TC-01 : Client User : OMV-1749 Validate view multiple bank account sections in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number which has more than one bank account
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "view" fields then validate in database for section "mulltipleBankAccounts" for user "CSR"

  Scenario: TC-01 : Client User : OMV-1053 Validate edit functionality of GUARANTEES section in 'Financial information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    Then User click on the "submenu" "Financial information"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "GUARANTEES" for user "CSR"

     ## Approve and decline approval flow scenarios for Billing details section
  Scenario: TC01: Client User :OMV-2030 Validate Decline flow on the processing date for Billing details section
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    And User get account number which does not has pending status for the effective date as "processingdate"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Financial information" based on tag name "div"
    And User verify based on validation control user should be able to "Edit" fields then validate in database for section "BILLING DETAILS" for user "CSR"
    And User click on "Submit" based on tag name "span"
    When User validate message "Billing Details section has been updated" based on tag name "div"
    And User validate updated values of Billing Details section are "not populated"
    Then User validate newly "updated" Billing details record "present" "staging" table with approval status as "Pending"
    And User "Decline" the Billing details record which has been recently "updated"
    And User validate message "Billing Details section has been declined" based on tag name "div"
    Then User validate newly "updated" Billing details record "present" "staging" table with approval status as "Declined"
    Then User validate newly "updated" Billing details record "notPresent" "main" table with approval status as "NA"

  Scenario: TC03:  Client User :OMV-2030 Validate Approve flow on the system date for Billing details section
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    And User get account number which does not has pending status for the effective date as "processingdate"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Financial information" based on tag name "div"
    And User verify based on validation control user should be able to "Edit" fields then validate in database for section "BILLING DETAILS"
    And User click on "Submit" based on tag name "span"
    When User validate message "Billing Details section has been updated" based on tag name "div"
    And User validate updated values of Billing Details section are "not populated"
    Then User validate newly "updated" Billing details record "present" "staging" table with approval status as "Pending"
    And User "Approve" the Billing details record which has been recently "updated"
    And User validate message "Billing Details section has been approved" based on tag name "div"
    Then User validate newly "updated" Billing details record "present" "staging" table with approval status as "Approved"
    Then User validate newly "updated" Billing details record "Present" "main" table with approval status as "NA"