Feature: HomeDashboard scenarios
  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User click on button "Home" using span text

    @Test
  Scenario: OLS-1139 Validate all sections are present or not
    When UserUserName validate "Welcome" header is displayed as "Welcome <User FullName>"
    And User validate "Welcome" header is displayed as "Welcome <User FullName>"
    Then User validate "value" "ACCOUNT OVERVIEW" based on tag name "div" and attribute name "class", attribute value "account-overview"
    And User validate presence of "View invoice" field with "span" tag
    And User validate presence of "Payment options" field with "span" tag
    Then User validate "Presence" "Home Dashboard Chart" based on tag name "div" and attribute name "class", attribute value "spending-chart-main-div"
    Then User validate "value" "RECENT TRANSACTIONS" based on tag name "div" and attribute name "class", attribute value "headerTitle"
    Then User validate "value" "RECENT REPORTS" based on tag name "div" and attribute name "class", attribute value "headerTitle"

  Scenario: OLS-1142 Validate Account overview section in Home Dashboard when account status is not active
    When User select an account from account picker which "DoesNot has", "1 - Active" status and "Active" sub status
    Then User validate "value" "ACCOUNT OVERVIEW" based on tag name "div" and attribute name "class", attribute value "account-overview"
    And User validate message "Account is in inactive status. For further assistance, please contact your fleet administrator or  " based on tag name "div"
    And User validate below types of balances in Account overview section
    |CurrentBalance,StatementBalance,AvailableBalance|
#    And User validate "Next payment date" field value is displayed in the format "MON DD, YYYY>
    And User validate presence of "View invoice" field with "span" tag
    And User validate presence of "Payment options" field with "span" tag

  Scenario: OLS-1142 Validate the user is able to download the statement by clicking on "View statement" option
    When User select an account from account picker which "DoesNot has", "1 - Active" status and "Active" sub status
    And User click on button "View invoice" using span text
    Then User validate downloaded file name is equal to "fileName" value of "reports-fileName"

  Scenario: OLS-1142 Validate chart behavior based on the relationship of Credit limit, Available balance and current balance
    When User select an account from account picker which is having "Current balance<70% of credit limit"
    And User validate chart is displayed in "Green" color
    When User select an account from account picker which is having "Current balance>70% and <85% of credit limit"
    And User validate chart is displayed in "Amber" color
    When User select an account from account picker which is having "Current balance>85% of credit limit"
    And User validate chart is displayed in "Red" color

  Scenario: OLS-1143 Validate Recent Transactions section
    When User select an account which "is" having "transactions"
    Then User validate "value" "RECENT TRANSACTIONS" based on tag name "div" and attribute name "class", attribute value "headerTitle"
    Then User validate 4 records displayed in "Recent Transactions" section with field values
    And User click on "View all" option for "transactions" module
    Then User validate header of module "Transactions"

#  @Test
  Scenario: OLS-1143 Validate Recent Transactions section when an account selected which does not contain any transaction
    When User select an account which "not" having "transactions"
    And User validate presence of "No transactions available at this time" field with "div" tag

  Scenario: OLS-1144 Validate all reports in by clicking no "View all" option Recent Reports section
    When User select an account which "is" having "reports"
    Then User validate "value" "RECENT REPORTS" based on tag name "div" and attribute name "class", attribute value "headerTitle"
    And User click on "View all" option for "reports" module
    Then User validate header of module "Reports"

  Scenario: OLS-1144 Validate all reports in by clicking no "View all" option Recent Reports section
    When User select an account which "is" having "reports"
    Then User validate "value" "RECENT REPORTS" based on tag name "div" and attribute name "class", attribute value "headerTitle"
    And User click three dot icon in Home dash board for recent reports section
    And User click on button "Download report"
#    Then User validate downloaded file name is equal to "fileName" value of "reports-fileName"

  Scenario: OLS-1144 Validate "No reports available at this time" message when no reports available for selected account
    When User select an account which "not" having "reports"
    Then User validate "value" "RECENT REPORTS" based on tag name "div" and attribute name "class", attribute value "headerTitle"
    And User validate presence of "No reports available at this time" field with "div" tag