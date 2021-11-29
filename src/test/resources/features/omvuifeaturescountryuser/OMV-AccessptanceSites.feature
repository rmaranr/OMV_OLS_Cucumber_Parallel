Feature: This feature contains acceptance sites scenarios

  Scenario: Country User : Add Acceptance Site after pinning a customer
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "Active" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Cards"
    Then User click on the "submenu" "Acceptance sites"
    And User click on "+ New" based on tag name "span"
    And User click on "+Add" based on tag name "span"
    When User search a site by selecting field values "Country" and site name
    Then User select check box which is at position "1"
    And User click on "Add" based on tag name "span"
    And User click on "Next" based on tag name "span"
    And User enter "siteName" as "RandomAlphanumeric" in "acceptanceSites" module having length "10" in "add" form using input tag
    Then User click on "Review" based on tag name "span"
    When User validate selected values are populated as expected in Review page of "Add Acceptance sites" scenario
    And User click on "Submit" based on tag name "span"
    Then User validate presence of "Acceptance site has been created" field with "div" tag

