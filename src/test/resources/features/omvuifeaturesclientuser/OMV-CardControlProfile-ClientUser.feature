Feature: UI:ClientUser - Card Control Profile Scenarios from Cards module

  @UI-CardControlProfile
  Scenario: OMV-885 Client User validate search criteria should be retained in card control profile module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get account number which "has" records for "cardControlProfile" module
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    When User click on the "menu" "Card control profile"
    Then User enter value "test" using locator "mav-input[ng-reflect-name='cardControl']>input" with locator type "cssSelector" is from property file "No"
    Then User click on "Search" based on tag name "span"
    And User save the value for field "countOfRecords" from locator "div[class='view-page-list']" using locator type "cssSelector" in property file
    Then User click on "+ New" based on tag name "span"
    Then User click on "Cancel" based on tag name "a"
    And User validate presence of "countOfRecords" field with "div" tag from property file using property name "countOfRecords"


  @UI-CardControlProfile
  Scenario: OMV-885 Client User Add new private card control profile
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User set property "cardControlNameErrorChecking" value as "false" in "TestDataProperties"
    And User set property "ccpType" value as "Add" in "TestDataProperties"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get account number which is "eligible" for create a new cardControlProfile
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    When User click on the "menu" "Card control profile"
    And User click on the "Add Cards button" " + New "
    Then User handle "Card offer" section for module "Card control profile" and perform action "Edit"
    Then User handle "Card product" section for module "Card control profile" and perform action "Edit"
    Then User handle "Card control profile name" section for module "Card control profile" and perform action "Edit"
    Then User perform action "edit" for fields based on validation control for section "POSPrompts"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User handle "productSelection" section for module "CardControlProfile" and perform action "Edit"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User click drop down "acceptanceClient" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User click on body of the webPage to come out of multiselect dropdown
#    Then User click drop down "networks" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User click on body of the webPage to come out of multiselect dropdown
    #Need to add some more steps
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And  User click on " +Add" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    And User click "Add" button using Java Script executor which is present at position "1" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Tuesday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User validate velocity limits in "cardControlProfile" page for "add" form
    And User click on "Review" button
#    Then User validate all stepper values in Review page of "cardControlProfile"
    And User click on "Submit" button
    Then User validate message "New card control profile has been created" based on tag name "div"

  @UI-CardControlProfile
  Scenario: OMV-892 Client User Edit new private card control profile for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario "TC01 : OMV-892 Client User edit a cardControlProfile for cardOffer"
    And User set property "cardControlNameErrorChecking" value as "false" in "TestDataProperties"
    And User set property "ccpType" value as "Edit" in "TestDataProperties"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get account number which is "notEligible" for create a new cardControlProfile
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNumberInCardsModule-notEligibleForAddCCP" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    When User click on the "menu" "Card control profile"
    Then User enter value "ccp-cardControlProfileName-Exists" using locator "mav-input[ng-reflect-name='cardControl']>input" with locator type "cssSelector" is from property file "Yes"
    Then User click on "Search" based on tag name "span"
    And User click three dot icon based on fieldName "ccp-cardControlProfileName-Exists" present in column "2" is from property file "Yes", name of property file "TestDataProperties"
    And User click on "Edit card control profile" based on tag name "button"
    Then User make the driver sleep for few seconds "5"
    Then User validate button "CardOffer" status "disabled" using tag name "mav-select", attribute name "ng-reflect-name", attribute value "cardOffer" and get status of button using attribute "ng-reflect-is-disabled"
    Then User validate expected value "ccp-cardOffer-notEligibleForAddCCP" from property file "Yes" actual value from locator "mav-select[ng-reflect-name='cardOffer']" which has locator type "cssSelector" get value using attribute "ng-reflect-model"
    And User validate button "CardProduct" status "disabled" using tag name "mav-select", attribute name "ng-reflect-name", attribute value "cardProduct" and get status of button using attribute "ng-reflect-is-disabled"
    When User validate expected value "ccp-cardProduct-notEligibleForAddCCP" from property file "Yes" actual value from locator "mav-select[ng-reflect-name='cardProduct']" which has locator type "cssSelector" get value using attribute "ng-reflect-model"
    Then User enter "cardControlProfileName" as "RandomAlphanumeric" in "CCP" module having length "15" in "add" form
    Then User perform action "edit" for fields based on validation control for section "POSPrompts"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User validate default products selected in Product Selection stepper based on the card control profile for "editCardControlProfile" form "<actionOfCarControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User click drop down "acceptanceClient" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User click on body of the webPage to come out of multiselect dropdown
#    Then User click drop down "networks" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User click on body of the webPage to come out of multiselect dropdown
    #Need to add some more steps
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    And  User click on " +Add" based on tag name "div"
#    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
#    And User click "Add" button using Java Script executor which is present at position "1" using tag name "span"
#    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
#    And User click " Edit" button using Java Script executor which is present at position "1" using tag name "div"
#    And User click on button contains "Tuesday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
#    Then User click on button "Update" using span text
#    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User validate velocity limits in "Card control profile" page for "Edit" form
    And User click on "Review" button
    Then User validate all stepper values in Review page of "cardControlProfile"
    And User click on "Submit" button
    Then User validate message "Card control profile has been updated" based on tag name "div"
#    And User validate card control profile added or updated in the database based on action "Edit"

  @UI-CardControlProfile
  Scenario: OMV-885 Client User validate error messages for card Product and card control profile name
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get account number which is "notEligible" for create a new cardControlProfile
    And User select client and country based on logged in user "ClientUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    When User click on the "menu" "Card control profile"
    And User click on the "Add Cards button" " + New "
    Then User handle "Card offer" section for module "Card control profile" and perform action "Edit"
    Then User handle "Card product" section for module "Card control profile" and perform action "Edit"
    Then User handle "Card control profile name" section for module "Card control profile" and perform action "Edit"
    Then User perform action "edit" for fields based on validation control for section "POSPrompts"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User validate message "Private profile already exists" based on tag name "div"
    Then User validate presence of "Select a different card product value" field with "mav-error" tag
    And User set property "CardControlProfileAction" value as "eligible" in "TestDataProperties"
    And User make sure account is not pinned for logged in user
    And User set property "cardControlNameErrorChecking" value as "true" in "TestDataProperties"
    Then User get account number which is "eligible" for create a new cardControlProfile
    Then User select "Select account" option using tag name "div" from context picker if logged in user "ClientUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Cards"
    When User click on the "menu" "Card control profile"
    And User click on the "Add Cards button" " + New "
    Then User handle "Card offer" section for module "Card control profile" and perform action "Edit"
    Then User handle "Card product" section for module "Card control profile" and perform action "Edit"
    And User set property "cardControlProfileAction" value as "NotEligible" in "TestDataProperties"
    Then User handle "Card control profile name" section for module "Card control profile" and perform action "Edit"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User validate presence of "Enter a different name" field with "mav-error" tag

  @UI-CardControlProfile
  Scenario Outline: OMV-884 : Client User Validate Search and Default field values in Card control profile module after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User enter accountnumber which has records of "Card control profile"
    And User click on "Search" button
#    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "accountNumber" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Cards"
    And User click on the "Submenu" "Card control profile"
    And User validate message "card control profiles" based on tag name "div"
    And User select or enter value from a field "<fieldNameOfCardProfilesearch>" based on its behavior "<CardProfilesearchFieldBehavior>" for user "<UserName>" in module "CardControlProfile"
    And User click on " Search " button
    And User validate "Default" omv Card Control Profile section field values "Profilename,Card offer,Products,Type,AcquiringClient,Networks,Sites" with database based on searched field "<fieldNameOfCardProfilesearch>"

    Examples:
      | Scenario                                                                                                                                                                          | UserName        | fieldNameOfCardProfilesearch | CardProfilesearchFieldBehavior |
      | TC-01 : OMV-884 : Validate Search and Default field values in Card control profile module after pinning an account by logging in using csrClient and search using CardControlName | ClientUserName | cardControl                  | text                           |
#      | TC-02 : OMV-884 : Validate Search and Default field values in Card control profile module after pinning an accountby logging in using csrClient and search using Products         | ClientUserName | products                     | dropDown                       |

  ##Export list of card control profiles at single account level
  Scenario: TC01: Client User OMV-893 Validate Export sheet data of Cardcontrol profile module after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Cards"
    When User click on the "Submenu" "Card control profile"
    And User click on "Search" based on tag name "button"
    And User click on download button validate format of excel file is ".csv"
    And User open the excel sheet and validate the data present in it with the database for module "cardControlProfile" based on primary key "profileName"

    ##Expanded view of card control profile after pinning an account
  Scenario: OMV-1746 :Client User : Validate Expanded section field values in Card Control profile module with out pinning an account for Purchase,Refund,rebate transaction
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    And User select or enter value from a field "<fieldNameOfCardsearch>" based on its behavior "<CardsearchFieldBehavior>" for user "<UserName>" in module "Cards"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Cards" or not for field "<fieldNameOfCardsearch>" based on field value
    And User validate "POSPrompts" omv section field values "posPrompts" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "Location Restrictions" omv section field values "AcceptanceCountry, Networks,sitegroups,Excludedsites" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "DayAndTimeconfiguration" omv section field values "PriceValidAt" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "AllowedProducts" omv section field values "allowedFuelProducts,allowedNonFuelProducts,valueAddedServices" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
