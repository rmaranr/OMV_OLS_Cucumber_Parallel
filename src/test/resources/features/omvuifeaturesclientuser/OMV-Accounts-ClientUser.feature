Feature: UI:Client User Accounts Module Scenarios

    Scenario: OMV-2257 User validate Home menu has been highlighted when user click on "select account" from any module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User select client and country based on logged in user "ClientUserName"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Discounts"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Pricing rules"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Preferred sites"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Component values"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Account information"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"
    And User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "2"
    Then User click on "Select account" based on tag name "div"
    Then User verifies the highlighted menu is "Home"

  @UI-AccountInformation
  Scenario: ClientUser : Search an account using invalid account number then clear search results validate country drop down is populated correctly
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User select client and country based on logged in user "ClientUserName"
    Then User enter "accountNumber" as "Numeric" in "accounts" module having length "15" in "add" form
    And User click on "Search" button
    And User click on "Clear search" based on tag name "a"
    And User select client and country based on logged in user "ClientUserName"

  @UI-AccountInformation
  Scenario: ClientUser : OMV-10: Validate the menu and sub menu highlighting
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Discounts"
    Then User verifies the highlighted menu is "Pricing"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Pricing rules"
    Then User verifies the highlighted menu is "Pricing"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Preferred sites"
    Then User verifies the highlighted menu is "Pricing"
    And User click on the "menu" "Pricing"
    And User click on the "submenu" "Component values"
    Then User verifies the highlighted menu is "Pricing"
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Account information"
    Then User verifies the highlighted menu is "Admin"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    Then User verifies the highlighted menu is "Cards"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User verifies the highlighted menu is "Transactions"
    And User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "2"
    Then User verifies the highlighted menu is "Transactions"
    And User click on button "Home" using span text

  @UI-AccountInformation
  Scenario: ClientUser : OMV 43: Validate the change context from the context picker when user entitled to more than once account, change account in context picker, Validate the Return to customer care in context picker
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
  #    And User select or enter value from a field "country" based on its behavior "dropdown" for user "ClientUserName" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    Then User click on context picker
    And User validate presence of "Change account" field with "button" tag
    And User validate presence of "Return to customer care" field with "button" tag
    Then User click on button "Change account"
    And User select client and country based on logged in user "ClientUserName"
    And User validate presence of "Customer Care" field with "div" tag

    #Single account is not available so cannot be automated below scenario
#  Scenario: OMV 43: Validate the change context from the context picker when user entitled to one account
#    And  User click on the pinned account
#    And  User verifies whether the "Change account" option is available
#    And  User verifies whether the "Return to customer care" option is available

  @UI-AccountInformation
  Scenario: ClientUser : OMV 43: Validate the select account in context picker
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And  Verify the presence of the page header "Accounts"

  @UI-AccountInformation
  Scenario: ClientUser : OMV 43: Validate the Return to customer care in context picker
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User click on "Search" button
#    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "country" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    Then User click on context picker
    And User validate presence of "Change account" field with "button" tag
    And User validate presence of "Return to customer care" field with "button" tag
    Then User click on button "Change account"
    And User validate presence of "Customer Care" field with "div" tag
    And User validate presence of "Select account" field with "div" tag

  @UI-AccountInformation
  Scenario: TC-01 : Client User : OMV-1761 Validate warning message is displayed when an account is pinned which is not in active status
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User make sure account is not pinned for logged in user
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "inActive" and substatus "inActive"
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User validate message "Your account is suspended." based on tag name "span"
    Then User validate message "Please contact customer care." based on tag name "span"

    ##Search scenarios
  @UI-AccountInformation
  Scenario Outline: ClientUser : OMV-45 User validate "Search By" message in Pre-Search functionality
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User make sure account is not pinned for logged in user
    And User click on button "Home" using span text
    And User select client and country based on logged in user "ClientUserName"
    Then User validate presence of "Search for account" field with "div" tag
    Examples:
      | Scenario                                                                                     | UserName       | PassWord        |
      | TC02 : OMV-45 User validate "Search By" message in Pre-Search functionality for country user | ClientUserName | CountryPassword |

  @UI-AccountInformation
  Scenario Outline: ClientUser : OMV-45 User validate Pre-Search functionality
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User make sure account is not pinned for logged in user
    And User click on button "Home" using span text
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    When User validate presence of "Search for account" field with "div" tag
    And User validate button "Search" status "<statusOfButton>" using tag name "mav-button", attribute name "class", attribute value "<attributeValue>" and get status of button using attribute "ng-reflect-disabled"
    Examples:
      | Scenario                                                              | UserName       | PassWord        | statusOfButton | attributeValue    |
      | TC04 : OMV-45 User validate Pre-Search functionality for country user | ClientUserName | CountryPassword | enabled        | search-bar-button |

  @UI-AccountInformation
  Scenario Outline: ClientUser : OMV-45 User validate Search functionality
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User make sure account is not pinned for logged in user
    And User click on button "Home" using span text
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "accountNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "country" based on its behavior "dropdown" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "cardNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "cardNumber" based on field value
    Examples:
      | Scenario                                                          | UserName       | PassWord        |
      | TC06 : OMV-45 User validate Search functionality for country user | ClientUserName | CountryPassword |

  @UI-AccountInformation
  Scenario Outline: ClientUser : OMV-45 User validate Post-Search functionality
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User make sure account is not pinned for logged in user
    And User click on button "Home" using span text
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "accountNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "country" based on its behavior "dropdown" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "cardNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "cardNumber" based on field value
    And User "expand" search bar and validate search fields with values
    And User "collapse" search bar and validate search fields with values
    Examples:
      | Scenario                                                               | UserName       | PassWord        |
      | TC08 : OMV-45 User validate Post-Search functionality for country user | ClientUserName | CountryPassword |

#  Scenario Outline: OMV-45 Export search results data and validate all data is exported
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    And User click on button "Home" using span text
#     #    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
#    Then User validate header of module "Accounts"
##    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
##    And User select or enter value from a field "accountNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
#    And User select or enter value from a field "country" based on its behavior "dropdown" for user "<UserName>" in module "accounts"
##    And User select or enter value from a field "cardNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
#    And User click on "Search" button
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "accountSelection" based on primary key "ACCOUNTNUMBER"
#    Examples:
#      | Scenario                                                                                    | UserName        | PassWord        |
#      | TC10 : OMV-45 Export search results data and validate all data is exported for country user | ClientUserName | CountryPassword |

  @UI-AccountInformation
  Scenario Outline: ClientUser : OMV-44 User search and switch to Account module from Context using "select account" for client and country level accesses
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    Then User select "Change account" option using tag "button" from context picker if logged in user "<UserName>" has access to more than one customer
    And User validate presence of "Select account" field with "div" tag
    And User validate presence of "Customer Care" field with "div" tag
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User select or enter value from a field "accountNumber" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag

    Examples:
      | Scenario                                                                              | UserName       | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
    #Client level login to select account
      | TC05: OMV-44 : Client user pin the account by searching accounts using accountName   | ClientUserName | CountryPassword | accountName              | text                       |
      | TC06: OMV-44 : Client user pin the account by searching accounts using accountNumber | ClientUserName | CountryPassword | accountNumber            | text                       |
      | TC08: OMV-44 : Client user pin the account by searching accounts using cardNumber    | ClientUserName | CountryPassword | cardNumber               | text                       |

  Scenario: Client User : OMV-2257 :Validate 'Home' should be hielighted when user logged in to the application and clicked on 'change account' button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Vehicles"
    Then User click on button "Change account"
    Then User verifies the highlighted menu is "Home"