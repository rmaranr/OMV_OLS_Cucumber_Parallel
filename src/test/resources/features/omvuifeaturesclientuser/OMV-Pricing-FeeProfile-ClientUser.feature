Feature: Client User : Add Fee profile scenarios

  Scenario: Client User : OMV-3462 Add public Fee profile of type 'Card fee'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Card fee" using locator "mat-radio-button[ng-reflect-value='Card fee']" which has locator type "cssSelector" using method "click"
    When User select grouping and value in fee profiles in "add" form to "CreateNew" public profile
    And User click on button "Next" using span text
    And User select no of records "0" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "3" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "RandomAlphanumeric" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "No"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Public fee profile has been created" field with "div" tag

  Scenario: Client User : Validate error message 'Fee profile name must be unique' with out pinning an account for fee type "Card fee"
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get existing "public" fee profile details of fee type "Card fee"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Card fee" using locator "mat-radio-button[ng-reflect-value='Card fee']" which has locator type "cssSelector" using method "click"
    When User select grouping and value in fee profiles in "add" form to "validateErrorFor" public profile
    And User click on button "Next" using span text
    And User select no of records "0" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "3" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "existingFeeName" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "Yes"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Fee profile name must be unique" field with "mat-error" tag

  Scenario: Client User : OMV-3462 Add public Fee profile of type 'Account and Transaction fee'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Account and transaction fee" using locator "mat-radio-button[ng-reflect-value='Account and transaction fee']" which has locator type "cssSelector" using method "click"
    When User select grouping and value in fee profiles in "add" form to "createNew" public profile
    And User click on button "Next" using span text
    And User select no of records "3" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "2" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "RandomAlphanumeric" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "No"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Public fee profile has been created" field with "div" tag

  Scenario: Client User : Validate error message 'Fee profile name must be unique' with out pinning an account for fee type "Account and Transaction fee"
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get existing "public" fee profile details of fee type "Account and Transaction fee"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Account and transaction fee" using locator "mat-radio-button[ng-reflect-value='Account and transaction fee']" which has locator type "cssSelector" using method "click"
    When User select grouping and value in fee profiles in "add" form to "validateErrorFor" public profile
    And User click on button "Next" using span text
    And User select no of records "3" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "2" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "existingFeeName" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "Yes"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Fee profile name must be unique" field with "mat-error" tag

  Scenario: Client User : Add private Fee profile of type 'Card fee'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get an account number which "is" eligible to create private "Card fee" profile
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Card fee" using locator "mat-radio-button[ng-reflect-value='Card fee']" which has locator type "cssSelector" using method "click"
    When User click drop down "cardOffer" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    When User click drop down "cardProduct" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on button "Next" using span text
    And User select no of records "0" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "3" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "RandomAlphanumeric" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "No"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Fee profile has been created" field with "div" tag

  Scenario: Client User : Add private Fee profile of type 'Account and Transaction fee'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get an account number which "is" eligible to create private "Account and Transaction fee" profile
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Account and transaction fee" using locator "mat-radio-button[ng-reflect-value='Account and transaction fee']" which has locator type "cssSelector" using method "click"
    And User click on button "Next" using span text
    And User select no of records "3" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "2" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "RandomAlphanumeric" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "No"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Fee profile has been created" field with "div" tag

  Scenario: Client User : Validate error message 'Fee profile name must be unique' after pinning an account for fee type "Card fee"
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get an account number which "is" eligible to create private "Card fee" profile
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Card fee" using locator "mat-radio-button[ng-reflect-value='Card fee']" which has locator type "cssSelector" using method "click"
    When User click drop down "cardOffer" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    When User click drop down "cardProduct" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on button "Next" using span text
    And User select no of records "0" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "3" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "existingFeeName" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "Yes"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Fee profile name must be unique" field with "mat-error" tag

  Scenario: Client User : Validate error message 'Fee profile name must be unique' after pinning an account for fee type 'Account and Transaction fee'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get an account number which "is" eligible to create private "Account and Transaction fee" profile
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User click on button "+ New" using span text
    Then User click on button contains "Account and transaction fee" using locator "mat-radio-button[ng-reflect-value='Account and transaction fee']" which has locator type "cssSelector" using method "click"
    And User click on button "Next" using span text
    And User select no of records "3" "Card Issue" fees based on client in "add" form pinning an account status "without"
    And User select no of records "2" "Card Fee" fees based on client in "add" form pinning an account status "without"
    And User click on button "Next" using span text
    Then User select date "effectiveStart" based on client processing date future or past "future" days "0"
    When User select date "effectiveEnd" based on client processing date future or past "future" days "30"
    Then User enter value "existingFeeName" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "Yes"
    And User click on button "Review" using span text
    And User click on button "Submit" using span text
    Then User validate presence of "Fee profile name must be unique" field with "div" tag

  Scenario: Client User : Assign public card fee profile to a customer
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get existing "public" fee profile details of fee type "Card fee"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User enter value "existingFeeName" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "Yes"
    And User select fee profile search criteria for fee category "public" based on existing profile and click on search
    Then User click three dot icon of "1" record in module "pricing" based on "feeName"
    And User click on "Assign to account" based on tag name "button"
    And User click on "+Add" based on tag name "a"
    Then User get account no based on the search criteria of fee profile
    Then User enter value "commonAccountNo" using locator "mav-input[ng-reflect-name='accountNumber']>input" with locator type "cssSelector" is from property file "Yes"
    And User click on "Search" based on tag name "span"
    When User click on radio button contains "Account" using locator "div[class='fee-table']>div>div>mat-radio-button" which has locator type "cssSelector"
    Then User click on "Add" based on tag name "span"
    And User click on "Next" based on tag name "span"
    When User click drop down "cardOffer" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    And User make the driver sleep for few seconds "2"
    When User click drop down "cardProduct" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on "Review" based on tag name "span"
    And User click on "Submit" based on tag name "span"
    Then User validate presence of "Fee Profile has been assigned" field with "div" tag
#    And User validate the assigned "card" fee profile to a customer "commonAccountNo"

  @Test
  Scenario: Client User : Assign public account fee profile to a customer
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "ClientUserName"
    Then User get existing "public" fee profile details of fee type "Account and Transaction fee"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Fee profiles"
    Then User enter value "existingFeeName" using locator "mav-input[ng-reflect-name='feeProfileName']>input" with locator type "cssSelector" is from property file "Yes"
    And User select fee profile search criteria for fee category "public" based on existing profile and click on search
    Then User click three dot icon of "1" record in module "pricing" based on "feeName"
    And User click on "Assign to account" based on tag name "button"
    And User click on "+Add" based on tag name "a"
    Then User get account no based on the search criteria of fee profile
    Then User enter value "commonAccountNo" using locator "mav-input[ng-reflect-name='accountNumber']>input" with locator type "cssSelector" is from property file "Yes"
    And User click on "Search" based on tag name "span"
    When User click on radio button contains "Account" using locator "div[class='fee-table']>div>div>mat-radio-button" which has locator type "cssSelector"
    Then User click on "Add" based on tag name "span"
    And User click on "Review" based on tag name "span"
    And User click on "Submit" based on tag name "span"
    Then User validate presence of "Fee Profile has been assigned" field with "div" tag
    And User validate the assigned "account" fee profile to a customer "commonAccountNo"