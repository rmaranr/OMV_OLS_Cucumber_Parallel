Feature: CSR User : Invoices Module

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User select client and country based on logged in user "CountryUserName"
    And User click on button "Home" using span text

  Scenario: Search Invoices for the Pinned Customer
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select or enter value from a field "country" based on its behavior "dropdown" for user "CountryUserName" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Reports"
    And User click on the "submenu" "Invoices"
#    When User enter search keywords "OMV-AT" in Invoices module
    When User click drop down "mat-select-arrow" then select value "OMV-AT" using tag "span" and attribute "class"
    Then User click on search icon
#    And User get invoices from database then search and validate the report in module "invoices"

  Scenario: validate the sort functionality
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select or enter value from a field "country" based on its behavior "dropdown" for user "CountryUserName" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Reports"
    And User click on the "submenu" "Invoices"
    And User verifies the "Invoices" page sort option Sort by Newest
    And User verifies the "Invoices" page sort option Sort by Oldest