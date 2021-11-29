Feature: Country User: Preferred sites

  Scenario: Country User : Add Preferred Site after pinning a customer
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
    When User click on the "menu" "Pricing"
    Then User click on the "submenu" "Preferred sites"
    And User click on "+ Sites" based on tag name "span"
#    Then User click on "+Add" based on tag name "a"
    When User click drop down "country" then select value "Austria" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click on "Search" based on tag name "span"
    Then User select check box which is at position "1"
    And User click on "Add" based on tag name "span"
    Then User validate presence of "site(s) has been added" field with "div" tag

  Scenario: Country User : Search Preferred Site
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
    When User click on the "menu" "Pricing"
    Then User click on the "submenu" "Preferred sites"
    Then User enter value "siteName" using locator "mav-input[id='search-top-input']>input" with locator type "cssSelector" is from property file "Yes"
    Then User click on search icon

  Scenario: Country User : Remove Preferred Site
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
    When User click on the "menu" "Pricing"
    Then User click on the "submenu" "Preferred sites"
    And User click on button contains Three dot icon
    And User click on button "Remove site"
    And User click on button "Delete" using span text

  Scenario: Country User : Download Preferred Site
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
    When User click on the "menu" "Pricing"
    Then User click on the "submenu" "Preferred sites"
    And User click on "download-icon" based on tag name "div"
    Then User validate presence of "The file is being downloaded" field with "div" tag

  Scenario: Country User: Sort functionality of Preferred sites
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
    When User click on the "menu" "Pricing"
    Then User click on the "submenu" "Preferred sites"
    And User click on button "Sort" using span text
    Then User click on "Newest" based on tag name "div"
    And User click on button "Sort by newest" using span text
    Then User click on "Oldest" based on tag name "div"