Feature: UI:CountryUser - Fee Profiles scenarios

  Scenario: Country User With out pinning account Search a fee record using any search attribute then validate default and expanded view of Card fee profile(Validate multiple records if present)
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User make sure account is not pinned for logged in user
    And User click on button "Home" using span text
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"


  Scenario: Country User after pinning account Search a fee record using any search attribute then validate default and expanded view of Card fee profile(Validate multiple records if present)
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User make sure account is not pinned for logged in user
    Then User get account Number which contain records based on module "feeProfiles" for user "OMV"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Drivers"