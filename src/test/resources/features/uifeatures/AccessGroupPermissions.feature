Feature: Access group permissions scenarios are covered in current feature file

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User enter "ReadOnlyUserName" and "ReadOnlyPassWord"
    And User click on login button

  Scenario: Verify User module access when logged in user has read only access
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    Then User click three dot icon of "1" record in module "users" based on "Name"
    And User verify button "Account access" status "disabled" using tag name "button"

  Scenario: Verify Contacts module access when logged in user has read only access
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Contacts"
    #And User verify button "+ Contact" status "disabled" using tag name "span"
    Then User click three dot icon of "1" record in module "contacts" based on "Name"
    And User verify button "Edit contact information" status "disabled" using tag name "button"
    And User verify button "Edit contact address" status "disabled" using tag name "button"
    And User verify button "Delete contact" status "disabled" using tag name "button"

  Scenario: Verify Cost Centres module access when logged in user has read only access
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Cost centres"
    #And User verify button "+ Cost centre" status "disabled" using tag name "span"
    Then User click three dot icon of "1" record in module "costCentres" based on "CostCentreCode"
    And User verify button "Edit cost centre" status "disabled" using tag name "button"

  Scenario: Verify Drivers module access when logged in user has read only access
    And User click on the "submenu" "Drivers"
    Then User click three dot icon of "1" record in module "drivers" based on "driverName"
    And User click on button "View card details"
    Then User validate header of module "Cards"

  Scenario: Verify Vehicles module access when logged in user has read only access
    And User click on the "submenu" "Vehicles"
    Then User click three dot icon of "1" record in module "vehicles" based on "vehicleName"
    And User click on button "View card details"
    Then User validate header of module "Cards"

#  @Test
#  Scenario: Verify Cards module access when logged in user has ready only access
#    And User click on the "submenu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    Then User click three dot icon of "1" record in module "cards" based on "cardNumber"
#    And User verify button "Edit card details" status "disabled" using tag name "button"
#    And User verify button "Change card status" status "disabled" using tag name "button"
#    And User verify button "Replace card" status "disabled" using tag name "button"

  Scenario: Verify Reports module access when logged in user has ready only access
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Reports"
    Then User click three dot icon of "1" record in module "reports" based on "reportName"
    And User verify button "Download report" status "enabled" using tag name "button"

  Scenario: Verify Report Templates module access when logged in user has ready only access
    And User click on the "menu" "Reports"
    And User click on the "submenu" "Report Templates"
    Then User click three dot icon of "1" record in module "reportTemplates" based on "reportName"
    And User verify button "Edit template" status "enabled" using tag name "button"
    And User verify button "Change status" status "disabled" using tag name "button"
    And User verify button "View reports" status "enabled" using tag name "button"

  Scenario: Verify Invoices module access when logged in user has ready only access
    And User click on the "menu" "Reports"
    And User click on the "submenu" "Invoices"
    Then User click three dot icon of "1" record in module "invoices" based on "invoiceName"
    And User verify button "Download invoice" status "enabled" using tag name "button"
