Feature: UI:CountryUser - Hierarchy scenarios from Accounts module


  Scenario Outline: TC-01 : Country User : OMV-173,1345,1346,1347 Add a hierarchy for Financial Management by logging in using Country user and approve hierarchy
    Then User modify flags "<flags>" for logged in user "<Approver user name>"
    Given User logout from the application
    Then User enter "<Approver user name>" and "<Approver password>"
    And User click on login button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Financial" in "TestDataProperties"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
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
#    And User click on date field "effectiveDate" and select value no of days "2" of "Past" time
    And User select date "effectiveDate" based on client processing date future or past "future" days "2"
 #   And User click on date field "endDate" and select value no of days "10" of "future" time
    And User select date "endDate" based on client processing date future or past "future" days "10"
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
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID" for "FinancialHierarchy" depth value condition "<depthCondition>"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "<Action of hierarchy>" based on tag name "<Tag name of action>"
    And User validate message "<Message>" based on tag name "<Tag name of message>"
    And User validate hierarchy has been added for following sections "Financial management"
    And User click on "Rebates" based on tag name "div"
    Then User validate message "No Rebates hierarchy" based on tag name "div"
    And User validate newly added "financialhierarchy" in database
    And User validate "<statufOfHierarchy>" status in database

    Examples:
      | Approver user name | Approver password  | flags                                                                                                                                                                                | Message                                                      | Tag name of message | Action of hierarchy | Tag name of action | statufOfHierarchy |depthCondition|
      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Hierarchy has been approved                                  | div                 | Approve             | button             | Approved          |No           |

  Scenario Outline: TC-01 : Country User : OMV-173,1345,1346,1347 Add a hierarchy for Financial Management by logging in using Country user and approve hierarchy
    Then User modify flags "<flags>" for logged in user "<Approver user name>"
    Given User logout from the application
    Then User enter "<Approver user name>" and "<Approver password>"
    And User click on login button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And  And User get an account which is having a "hierarchy" with "Pending" status
    Then User set property "hierarchyScenario" value as "Financial" in "TestDataProperties"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
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
#    And User click on date field "effectiveDate" and select value no of days "2" of "Past" time
    And User select date "effectiveDate" based on client processing date future or past "future" days "2"
 #   And User click on date field "endDate" and select value no of days "10" of "future" time
    And User select date "endDate" based on client processing date future or past "future" days "10"
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
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID" for "FinancialHierarchy" depth value condition "<depthCondition>"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    And User set property "testStatus" value as " " in "TestDataProperties"
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "<Action of hierarchy>" based on tag name "<Tag name of action>"
    And User validate message "<Message>" based on tag name "<Tag name of message>"
    And User validate "<statufOfHierarchy>" status in database

    Examples:
      | Approver user name | Approver password  | flags                                                                                                                                                                                | Message                                                      | Tag name of message | Action of hierarchy | Tag name of action | statufOfHierarchy |depthCondition|
#      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 | Approve             | button             | Not Approved      |No            |
#      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | This user is not allowed to Approve or Decline these records | div                 | Approve             | button             | Not Approved      |No            |
#      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 | Approve             | button             | Not Approved      |No            |
#
#      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Hierarchy has been declined                                  | div                 | Decline             | button             | Declined          |No            |
#      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 | Decline             | button             | Not Approved      |No            |
#      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | This user is not allowed to Approve or Decline these records | div                 | Decline             | button             | Not Approved      |No            |
#      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 | Decline             | button             | Not Approved      |No            |


  Scenario: OMV-239 : Account hierarchy validation for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User get a "Parent" account which is having an "financialhierarchy" with "ClientMID"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User verify Header,Sections,data in hierarchy in "Financial management" module

  Scenario: OMV-2258 : Report hierarchy validation for Country User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "Parent" account which is having an "reportHierarchy" with "ClientMID"
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User verify Header,Sections,data in hierarchy in "Reports" module

  Scenario: OMV-239 : Account hierarchy validation for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User get a "Parent" account which is having an "rebatesHierarchy" with "ClientMID"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User verify Header,Sections,data in hierarchy in "Rebates" module

  @UI-Hierarchies
  Scenario: TC-01 : Country User : OMV-2258 Add a hierarchy for Report hierarchy by logging in using Country User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Report" in "TestDataProperties"
    And User select client and country based on logged in user "CountryUserName"
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
  Scenario: TC-02 : Country User : OMV-173,1345,1346,1347 Add a hierarchy for Rebates by logging in using Country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
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

  @UI-Hierarchies
  Scenario: TC-03 : Country User : OMV-173,1345,1346,1347 Add a hierarchy for Financial Management,Rebates by logging in using Country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Financial,Rebates" in "TestDataProperties"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
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
  Scenario: TC-01 : Country User : OMV-762 Validate user is able to approve a hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is "having" an "hierarchy" with "Pending" status
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Admin" "Hierarchy"
    Then User validate message " Needs approval " based on tag name "span"
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Approve " based on tag name "button"
    And User validate message "Hierarchy has been approved" based on tag name "div"

  Scenario: TC-02 : Country User : OMV-762 Validate user is able to Decline a hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is "having" an "hierarchy" with "Pending" status
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
@Test
  Scenario Outline: TC-01 : Country User : OMV-989 Validate user is able to add child accounts to a financial hierarchy
    Then User modify flags "<flags>" for logged in user "<Approver user name>"
    Given User logout from the application
    Then User enter "<Approver user name>" and "<Approver password>"
    And User click on login button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "Parent" account which is having an "financialhierarchy" with "ClientMID"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User add,edit child accounts "notHavingHierarchy-accountNumber"
    And User validate message "<Message>" based on tag name "<Tag name of message>"
    And User validate newly added "financialhierarchy" in database

    Examples:
      | Approver user name | Approver password  | flags                                                                                                                                                                                | Message                                                      | Tag name of message |
      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Hierarchy has been updated                                  | div                 |
#      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 |
#      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | This user is not allowed to Approve or Decline these records | div                 |
#      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 |


  @UI-Hierarchies
  Scenario: TC-02 : Country User : OMV-989 Validate user is able to add child accounts to a rebates hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "any" account which is having an "rebateshierarchy" with "ClientMID"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User add,edit child accounts "notHavingHierarchy-accountNumber"
#    And User validate newly added "rebateshierarchy" in database

  Scenario Outline: TC-01 : Country User : OMV-999 Validate user is able to expire an account from hierarchy
    Then User modify flags "<flags>" for logged in user "<Approver user name>"
    Given User logout from the application
    Then User enter "<Approver user name>" and "<Approver password>"
    And User click on login button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account which is having a "financialHierarchy"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User modify expiry date for parent and child, validate in expiry pop no limitation for parent but child is limited based on parent for date pop up
    And User validate message "<Message>" based on tag name "<Tag name of message>"

    Examples:
      | Approver user name | Approver password  | flags                                                                                                                                                                                | Message                                                      | Tag name of message |
      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Hierarchy has been updated                                  | div                 |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='Y',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 |
      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | This user is not allowed to Approve or Decline these records | div                 |
      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | This user is not allowed to Approve or Decline these records | div                 |

  Scenario: TC-01 : Country User : OMV-2258 Validate user is able to expire an account from report hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a "any" account which is having an "reporthierarchy" with "ClientMID"
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User navigate to "reports" tab
    Then User modify expiry date for parent and child, validate in expiry pop no limitation for parent but child is limited based on parent for date pop up


  Scenario: TC-01 : Country User : Validate Add new button status if active hierarchy dosen't have expiry on date
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is having a "financialhierarchy" with no expiry date
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate add new button in hierarchy

  Scenario: TC-01 : Country User : Validate Edit option status if hierarchy approved and pending
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is having a "financialHierarchy"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate the Edit option state for "Approved" hierarchy
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User make sure account is not pinned for logged in user
    And User get an account which is having a "hierarchy" with "Pending" status
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingPendingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate the Edit option state for "Pending" hierarchy

  Scenario: TC-01 : Country User : Validate Approve and decline options state if hierarchy approved and pending
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is having a "hierarchy" with "Pending" status
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingPendingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate the Approve and Decline options state for "Pending" hierarchy
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User make sure account is not pinned for logged in user
    And User get an account which is having a "financialHierarchy"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate the Approve and Decline options state for "Approved" hierarchy


  Scenario: TC-01 : Validate user is able to approve a hierarchy from child account
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a child account which is having a "financialhierarchy" with "Pending" status
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingPendingHierarchy-childAccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User validate message "Needs approval" based on tag name "span"
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Approve " based on tag name "button"
    And User validate message "Hierarchy has been approved" based on tag name "div"


  Scenario: TC-01 : Country User :  Validate User should not allow to add a customer which already present in another hierarchy
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is not having an hierarchy with "ClientMID"
    Then User set property "hierarchyScenario" value as "Financial" in "TestDataProperties"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "notHavingHierarchy-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
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
    And Validate User should not allow to add a customer which already present in another hierarchy


  Scenario: TC-01 : Country User :  Validate User able to add new hierarchy if active hierarchy have expiery on date
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get an account which is having a "financialhierarchy" with expiry date
    Then User set property "hierarchyScenario" value as "Financial" in "TestDataProperties"
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingHierarchyWithExp-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "SubMenu" "Hierarchy"
    And User set the effective date and end date
    And User click on button "+ New" using span text
    When User validate message "Hierarchy type" based on tag name "div"
    Then User click on button contains "hierarchyType" using locator "mat-select[ng-reflect-name='hierarchyType']" which has locator type "cssSelector" using method "click"
    And User click on button " Financial management " using span text
    And User click on button contains "profile" using locator "//mav-button[@id='user-profile-btn']/button/span" which has locator type "xpath" using method "action"
    And User enter value "FinancialHierarchy" in text field "hierarchyName" based on locator "mav-input[ng-reflect-name='hierarchyName']>input" which has locator type is "cssSelector" in "add" form
    And User select date "effectiveDate" based on client processing date future or past "future" days ""
    And User select date "endDate" based on client processing date future or past "future" days ""
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate message "Assign accounts" based on tag name "div"
    When User validate message "Select accounts" based on tag name "div"
    Then User add child accounts and edit added child account "notHavingHierarchy-accountNumber" with "ClientMID" for "FinancialHierarchy" depth value condition "Yes"
#   And User validate tree structure of all accounts
    And User click on button "Submit" using span text
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Approve " based on tag name "button"
    And User validate message "Hierarchy has been approved" based on tag name "div"
    And User validate hierarchy has been added for following sections "Financial management"
    And User click on "Rebates" based on tag name "div"
    Then User validate message "No Rebates hierarchy" based on tag name "div"
    And User validate newly added "financialhierarchy" in database
    And User validate "Approved" status in database


  Scenario: TC-01 : Validate If pinned to child account as csr user then i should see hierarchy like parent customer
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get a child account which is having a "financialhierarchy" with "Approved" status
    And User click on select account
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter field value "havingPendingHierarchy-childAccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "Menu" "Admin"
    And User click on the "submenu" "Hierarchy"
    Then User Validate parent account in hierarchy if child account pinned





