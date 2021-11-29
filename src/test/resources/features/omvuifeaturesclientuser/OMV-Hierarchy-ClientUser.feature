Feature: UI:ClientUser - Hierarchy scenarios from Accounts module

  Scenario: TC01: Client User : OMV-151 Validate user is able to taken an action for a hierarchy until it approved or decliened
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "any" account which is having an "financialhierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Admin" "Hierarchy"
    And User verify three dot icon is present for approved hierarchy

  Scenario: OMV-239 : Account hierarchy validation for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "Parent" account which is having an "financialhierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User verify Header,Sections,data in hierarchy in "Financial management" module

  Scenario: OMV-239 : Account hierarchy validation for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "Parent" account which is having an "rebatesHierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User verify Header,Sections,data in hierarchy in "Rebates" module

  Scenario: OMV-2258 : TC-02 : Client User : Report hierarchy validation for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "Parent" account which is having an "reportHierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User verify Header,Sections,data in hierarchy in "Reports" module

  @UI-Hierarchies
  Scenario: TC-01 : Client User : OMV-173,1345,1346,1347 Add a hierarchy for Financial Management by logging in using Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Financial" in "TestDataProperties"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "notHavingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User click on button "+ New" using span text
    Then User click on button contains "hierarchyName" using locator "mav-input[ng-reflect-name='hierarchyName']" which has locator type "cssSelector" using method "click"
    And User click on button contains "effectiveDate" using locator "mav-input[ng-reflect-name='effectiveDate']" which has locator type "cssSelector" using method "click"
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "double"
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    Then User validate presence of "Select a valid type" field with "mat-error" tag
    Then User validate presence of "Enter a valid name" field with "mat-error" tag
    Then User validate presence of "Select a valid effective date" field with "mat-error" tag
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Financial management " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "FinancialHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    Then User click on "Cancel" based on tag name "a"
    And User validate message "No Financial management hierarchy" based on tag name "div"
    And User click on button "+ New" using span text
    When User validate message "Hierarchy type" based on tag name "div"
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Financial management " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "FinancialHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate message "Assign accounts" based on tag name "div"
    When User validate message "Select accounts" based on tag name "div"
    Then User validate parent account is displayed in Select accounts stepper and validate field values with database
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    Then User validate message " Hierarchy has been created " based on tag name "div"
    And User validate hierarchy has been added for following sections "Financial management"
    And User click on "Rebates" based on tag name "div"
    Then User validate message "No Rebates hierarchy" based on tag name "div"
    And User validate newly added "financialhierarchy" in database


  @UI-Hierarchies
  Scenario: TC-02 : Client User : OMV-173,1345,1346,1347 Add a hierarchy for Rebates by logging in using Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    Then User set property "hierarchyScenario" value as "Rebates" in "TestDataProperties"
    And User select or enter field value "notHavingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    Then User click on "Rebates" based on tag name "div"
    And User click on button "+ New" using span text
    Then User click on button contains "hierarchyName" using locator "mav-input[ng-reflect-name='hierarchyName']" which has locator type "cssSelector" using method "click"
    And User click on button contains "effectiveDate" using locator "mav-input[ng-reflect-name='effectiveDate']" which has locator type "cssSelector" using method "click"
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "double"
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    Then User validate presence of "Select a valid type" field with "mat-error" tag
    Then User validate presence of "Enter a valid name" field with "mat-error" tag
    Then User validate presence of "Select a valid effective date" field with "mat-error" tag
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Rebates " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "RebateHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    Then User click on "Cancel" based on tag name "a"
    And User validate message "No Rebates hierarchy" based on tag name "div"
    And User click on button "+ New" using span text
    When User validate message "Hierarchy type" based on tag name "div"
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Rebates " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "RebateHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate message "Assign accounts" based on tag name "div"
    When User validate message "Select accounts" based on tag name "div"
    Then User validate parent account is displayed in Select accounts stepper and validate field values with database
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    Then User validate message "Hierarchy has been created" based on tag name "div"
    And User validate hierarchy has been added for following sections "Rebates"
    And User click on "Financial management" based on tag name "div"
    Then User validate message "No Financial management hierarchy" based on tag name "div"
    And User validate newly added "rebateHierarchy" in database

  Scenario: TC-01 : Client User : OMV-2258 Add a hierarchy for Report hierarchy by logging in using Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Report" in "TestDataProperties"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "notHavingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User click on button "+ New" using span text
    When User validate message "Hierarchy type" based on tag name "div"
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button "Reports" using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "ReportHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate message "Assign accounts" based on tag name "div"
    When User validate message "Select accounts" based on tag name "div"
    Then User validate parent account is displayed in Select accounts stepper and validate field values with database
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    Then User validate message " Hierarchy has been created " based on tag name "div"
    And User validate hierarchy has been added for following sections "Report"

  @UI-Hierarchies
  Scenario: TC-03 : Client User : OMV-173,1345,1346,1347 Add a hierarchy for Financial Management,Rebates by logging in using Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Financial,Rebates" in "TestDataProperties"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "notHavingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User click on button "+ New" using span text
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Financial management " using span text
    And User click on button " Rebates " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "FinancialHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    Then User click on "Cancel" based on tag name "a"
    And User validate message "No Financial management hierarchy" based on tag name "div"
    And User click on button "+ New" using span text
    When User validate message "Hierarchy type" based on tag name "div"
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Financial management " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "FinancialHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User click on date field "effectiveDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "10" of "future" time
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate message "Assign accounts" based on tag name "div"
    When User validate message "Select accounts" based on tag name "div"
    Then User validate parent account is displayed in Select accounts stepper and validate field values with database
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    Then User validate message " Hierarchy has been created " based on tag name "div"
    And User validate hierarchy has been added for following sections "Financial management"
    And User click on "Rebates" based on tag name "div"
    Then User validate message "No Rebates hierarchy" based on tag name "div"
    And User validate newly added "financialhierarchy" in database


    #### Approve hierarchy scenarios
  Scenario: TC-01 : Client User : OMV-762 Validate user is able to approve a hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is "having" an "hierarchy" with "Pending" status
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Admin" "Hierarchy"
    Then User validate message " Needs approval " based on tag name "span"
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Approve " based on tag name "button"
    And User validate message "Hierarchy has been approved" based on tag name "div"

  Scenario: TC-02 : Client User : OMV-762 Validate user is able to Decline a hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is "having" an "hierarchy" with "Pending" status
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate message " Needs approval " based on tag name "span"
    And User click on button contains "threeDotIcon" using locator "button[class='menu-button-style mat-menu-trigger']>div>mav-svg-icon>fa-icon>svg" which has locator type "cssSelector" using method "click"
    And User click on "Decline" based on tag name "button"
    And User validate message "Hierarchy has been declined" based on tag name "div"

    ##### Adding child accounts to a hierarchy
  @UI-Hierarchies
  Scenario: TC-01 : Client User : OMV-989 Validate user is able to add child accounts to a financial hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "Parent" account which is having an "financialhierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User add,edit child accounts "notHavingHierarchy-accountNumber"
    And User validate newly added "financialhierarchy" in database

  @UI-Hierarchies
  Scenario: TC-02 : Client User : OMV-989 Validate user is able to add child accounts to a rebates hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "any" account which is having an "rebateshierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User add,edit child accounts "notHavingHierarchy-accountNumber"
#    And User validate newly added "rebateshierarchy" in database

  @UI-Hierarchies
  Scenario: TC-01 : Client User : OMV-999 Validate user is able to expire an account from hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "any" account which is having an "financialhierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User modify expiry date for parent and child, validate in expiry pop no limitation for parent but child is limited based on parent for date pop up

  Scenario: TC-01 : Client User : OMV-2258 Validate user is able to expire an account from report hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "any" account which is having an "reporthierarchy" with "ClientMID"
    And User select client and country based on logged in user "ClientUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User navigate to "reports" tab
    Then User modify expiry date for parent and child, validate in expiry pop no limitation for parent but child is limited based on parent for date pop up