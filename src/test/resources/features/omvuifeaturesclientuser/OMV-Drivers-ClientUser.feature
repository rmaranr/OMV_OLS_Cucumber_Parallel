Feature: UI:ClientUser - Driver scenarios

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User make sure account is not pinned for logged in user
    Then User get account Number which contain records based on module "drivers" for user "OMV"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Drivers"
    Then User click on X icon of specific filter which is at position "1"
    Then User click on "All statuses" based on tag name "div"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on "Apply" based on tag name "span"

  @UI-Drivers
  Scenario: OMV-677 Validate the drivers default view and expanded view
    And User validate count of records for module "Drivers" based on its field "" with value "" is from property file "" with the database for user "OMV"
    When User enter search keywords "driver-cardNo" by selecting search attribute as "Card number"
    Then User click on search icon
    And User click on Three dot icon in module "Drivers" based on search keywords "driver-driverName"
    And User verifies whether the drivers "Driver Name" "Status" "Card Number" "Driver Id" are matching the information with db
    And User click on button "View card details"

  @UI-Drivers
  Scenario:OMV-677 Validate "All statuses" filter
    Then User click on "All statuses" based on tag name "div"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on button contains "Inactive" using locator "//span[@class='mat-option-text'][contains(text(),'Inactive')]" which has locator type "xpath" using method "click"
    And User click on "Apply" based on tag name "span"
#    And User validate count of records for module "drivers" based on its field "status" with value "Inactive" is from property file "No" with the database for user "OMV"

  @UI-Drivers
  Scenario: OMV-677 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    When User enter below three characters "ab" in search text box the search icon will not be enabled
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Card number"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Driver ID"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."

  @UI-Drivers
  Scenario: OMV-677 Validate Clear Search functionality
    When User enter search keywords "driver-driverName" by selecting search attribute as "Driver name"
    Then User click on search icon
    And User click on clear search icon
    And User validate count of records for module "drivers" based on its field "" with value "" is from property file "" with the database for user "OMV"

  @UI-Drivers
  Scenario: OMV-677 Validate the sort functionality
    And User verifies the "Drivers" page sort option Sort by Newest
    And User verifies the "Drivers" page sort option Sort by Oldest

  @UI-Vehicles
  Scenario: Search a vehicle with full card no
    When User enter search keywords "driver-fullCardNo" by selecting search attribute as "Card number"
    Then User click on search icon
    And User validate presence of "1 vehicle" field with "div" tag

  @UI-Drivers
  Scenario: OMV-677 Validate search attribute functionality
    When User enter wrong search keywords "Test" by selecting search attribute as "Driver name"
    Then User click on search icon
    Then User selects a random search attribute and verify whether the selected attribute is highlighted
    And User press "escape" key from keyboard
    And User press "tab" key from keyboard

#  @Regression
#  Scenario: Validate Export sheet data
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "Drivers" based on primary key "CardNumber"

