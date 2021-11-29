Feature: UI:ClientUser - Vehicle scenarios

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User make sure account is not pinned for logged in user
    Then User get account Number which contain records based on module "vehicles" for user "OMV"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Vehicles"
    Then User click on X icon of specific filter which is at position "1"
    Then User click on "All statuses" based on tag name "div"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on "Apply" based on tag name "span"

  @UI-Vehicles
  Scenario: OMV-677 Validate the vehicles default view and expanded view
    And User validate count of records for module "vehicles" based on its field "" with value "" is from property file "" with the database for user "OMV"
    When User enter search keywords "vehicle-cardNo" by selecting search attribute as "Card number"
    Then User click on search icon
    And User validate default and expanded view of vehicle record

  @UI-Vehicles
  Scenario: OMV User:OMV-1735 User must be able to edit a vehicle
    And User validate count of records for module "vehicles" based on its field "status" with value "Active" is from property file "No" with the database for user "OMV"
    When User enter search keywords "vehicle-cardNo" by selecting search attribute as "Card number"
    Then User click on search icon
    When User click on button contains "three dot icon" using locator "(//div[@class='header-menu authorize-click ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Edit" based on tag name "button"
    And User validate presence of "Edit vehicle" field with "h2" tag
    Then User perform action "Edit" for fields based on validation control for section "editVehicle"
    And User click on "Save" using tag name "span" if any field is edited based on property "isVehicleEdited"
    Then User validate presence of "Vehicle has been edited successfully" field with "div" tag

  @UI-Vehicles
  Scenario:OMV-677 Validate "All statuses" filter
#    And User validate count of records for module "vehicles" based on its field "status" with value "Active" is from property file "No" with the database for user "OMV"
    Then User click on "All statuses" based on tag name "div"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on button contains "Inactive" using locator "//span[@class='mat-option-text'][contains(text(),'Inactive')]" which has locator type "xpath" using method "click"
    And User click on "Apply" based on tag name "span"
#    And User validate count of records for module "vehicles" based on its field "status" with value "Inactive" is from property file "No" with the database for user "OMV"

  @UI-Vehicles
  Scenario: OMV-677 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    When User enter below three characters "ab" in search text box the search icon will not be enabled
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "License plate"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Card number"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."

  @UI-Vehicles
  Scenario: OMV-677 Validate Clear Search functionality
    And User validate count of records for module "vehicles" based on its field "" with value "" is from property file "" with the database for user "OMV"
    When User enter search keywords "vehicle-cardNo" by selecting search attribute as "Card number"
    Then User click on search icon
    And User click on clear search icon
    And User validate count of records for module "vehicles" based on its field "" with value "" is from property file "" with the database for user "OMV"

  @UI-Vehicles
  Scenario: OMV-677 Validate the sort functionality
    And User verifies the "Vehicles" page sort option Sort by Newest
    And User verifies the "Vehicles" page sort option Sort by Oldest

#  @Regression
#  Scenario: Validate Export sheet data
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "Vehicles" based on primary key "CardNumber"

  @UI-Vehicles
  Scenario: OMV-677 Validate search attribute functionality
    When User enter wrong search keywords "Test" by selecting search attribute as "License plate"
    Then User click on search icon
    Then User selects a random search attribute and verify whether the selected attribute is highlighted

  @UI-Vehicles @Test
  Scenario: Search a vehicle with full card no
    When User enter search keywords "vehicle-fullCardNo" by selecting search attribute as "Card number"
    Then User click on search icon
    And User validate presence of "1 vehicle" field with "div" tag

  Scenario: OMV-1734 User validate filter value should be selected as 'Active' by default,'Edit' icon presence for 'Active' vehicles
    Then User validate presence of element using locator "//div[@class='text-ellipsis'][contains(text(),'Active')]" with locator type "xpath"
    And User validate count of records for module "vehicles" based on its field "status" with value "Active" is from property file "No" with the database for user "OMV"
    When User click on button contains "three dot icon" using locator "(//div[@class='header-menu authorize-click ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User verify button "Edit" status "enabled" using tag name "button"
    And User click on "View card details" based on tag name "button"

  Scenario: OMV-1734 User Validate 'Edit' icon will not be present for inactive vehicles and validate count of records
    Then User validate presence of element using locator "//div[@class='text-ellipsis'][contains(text(),'Active')]" with locator type "xpath"
    And User validate count of records for module "vehicles" based on its field "status" with value "Active" is from property file "No" with the database for user "OMV"
    And User click on X icon of specific filter which is at position "1"
    Then User click on "All statuses" based on tag name "div"
    And User click on button contains "All" using locator "//span[@class='mat-option-text'][contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User click on button contains "Inactive" using locator "//span[@class='mat-option-text'][contains(text(),'Inactive')]" which has locator type "xpath" using method "click"
    And User click on "Apply" based on tag name "span"
    And User validate count of records for module "vehicles" based on its field "status" with value "Inactive" is from property file "No" with the database for user "OMV"
    When User click on button contains "three dot icon" using locator "(//div[@class='header-menu authorize-click ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User verify button "Edit" status "notEnabled" using tag name "button"
    And User click on "View card details" based on tag name "button"
