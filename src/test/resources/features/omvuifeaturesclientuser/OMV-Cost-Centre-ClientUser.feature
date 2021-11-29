Feature: UI:ClientUser - Cost center scenarios from Admin module

  @UI-CostCentre
  Scenario: View the List of cards associated to selected cost centre from CostCentre module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number which has cost centres for user type "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "costCentreHavingAccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Cost centres"
    When User enter search keywords "costCentreCode" by selecting search attribute as "Cost centre"
    Then User click on search icon
    When User click on button contains "three dot icon" using locator "(//div[@class='header-menu authorize-click ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "View linked cards" based on tag name "button"
    Then User validate no of cards displayed based on the cost centre for user type "OMVClient"