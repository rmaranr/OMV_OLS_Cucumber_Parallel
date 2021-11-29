Feature: UI:ClientUser - Document setup scenarios from Accounts module


    ########################### Report setup scenarios #######################
  @UI-DocumentSetup
  Scenario: TC-01 : OMV-1020 : Client User : Validate the configured reports for the selected account
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an "Active" account number which "having" Document setup for user "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "havingReportSetup" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "Document setup"
    And User validate message "Document setup" based on tag name "div"
    Then User get the reports associated for the pinned account and verify count of reports
    And User validate "Default" section of field values which is at position "1"
    And User validate "Expanded" section of field values which is at position "1"

  @UI-DocumentSetup
  Scenario: TC-02 : OMV-1020 : Client User : User validate actions are visible based on status of report
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an "Active" account number which "having" Document setup for user "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "havingReportSetup" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "Document setup"
    Then User validate actions are visible based on status of Report

  @UI-DocumentSetup
  Scenario: TC-01 : OMV-1021 : Client User : User create a new report setup for account which does not contains report setups and validate 'Cancel' button functionality in add new report setup page
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an "Active" account number which "not having" Document setup for user "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "not havingReportSetup" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "Document setup"
    And User validate message "Document setup" based on tag name "div"
    Then User click on "+ New" based on tag name "span"
    And User validate message "Add new document setup" based on tag name "h1"
    And User click on "Cancel" based on tag name "a"
    And User validate message "Document setup" based on tag name "div"
    And User click on "+ New" based on tag name "span"
    Then User perform action "Add" for fields based on validation control for section "addNewReportSetup"
#    And User enter all mandatory fields based on corresponding look up values and field should be disabled if it has only one value
    Then User click on "Submit" based on tag name "span"
    And User validate message "Document setup has been created" based on tag name "div"
    And User verify the newly created report is saved in the database

    @UI-DocumentSetup
  Scenario: TC01 : Client User : OMV-1022 Validate edit functionality of report configuration
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an "Active" account number which "having" Document setup for user "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "havingReportSetup" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "Document setup"
    And User click on button contains Three dot icon
    And User click on "Edit" based on tag name "button"
    Then User validate DeliveryType,Frequency,Recipient,SortBy field values populated and update any one value
    And User click on "Save" based on tag name "span"
    And User validate message "Document setup has been updated" based on tag name "div"

  Scenario: TC01: Client User :OMV-1023 Validate approve and decline functionality in Report setup module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an "Active" account number which "having" report setup for user "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "Document setup"
    When User click threedot icon in "Document setup" module for which the record is having "Needs approval" status
    Then User click on "Approve" based on tag name "button"
    And User validate message "Document setup has been approved" based on tag name "div"
    When User click threedot icon in "Report setup" module for which the record is having "Needs approval" status
    Then User click on "Declined" based on tag name "button"
    And User validate message "Document setup has been declined" based on tag name "div"