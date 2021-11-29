Feature: UI:ClientUser - Group PIN Scenarios from Cards module
    #Groupd pin module for cards
  @UI-GroupPin
  Scenario: OMV-894 : Validate Search and Default field values in Group PIN module after pinning an account for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account which "ishaving" "groupPinRecords" for user type "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    Then User validate count of "group pins" from property file based on property key "countOfGroupPINRecords" in module "GroupPIN"
    Then User enter value "groupPinName" using locator "mav-input[id='search-top-input']>input" with locator type "cssSelector" is from property file "Yes"
    Then User click on search icon
    And User click on clear search icon

  @UI-GroupPin
  Scenario: OLS-902 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account which "ishaving" "groupPinRecords" for user type "OMV"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    When User enter below three characters "ab" in search text box the search icon will not be enabled
    When User enter value "kljakhsldjfhkljhjkagksjdhkljhksjdhfklgjkhlasjdhflkj" in text field "NameOfGroupPin" based on locator "div[class='mat-form-field-infix']>span>mav-input>input" which has locator type is "cssSelector" in "add" form
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."


