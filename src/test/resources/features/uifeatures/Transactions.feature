#Feature: Transactions scenarios
#
#
#  @Smoke @Regression
#  Scenario Outline: Validate Card Transaction data in Transaction page
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<ScenarioName>"
#    When User enter "UserName" and "PassWord"
#    And User click on login button
#    And User click on the "menu" "Transactions"
##    When User validate the count of transactions displayed in UI for date filter type "1 day" with the database
#    When User validate the count of transactions displayed in UI for date filter type "Last 7 days" with the database
#    When User validate the count of transactions displayed in UI for date filter type "Last 14 days" with the database
#    When User validate the count of transactions displayed in UI for date filter type "Last 30 days" with the database
#    When User validate the count of transactions displayed in UI for date filter type "Last 90 days" with the database
#    And User get "<TransCategoryDescription>" from Transactions table using Transaction category cid "<TransCategoryCID>" for account selected in account picker
#    When User enter search keywords "CARD_NUMBER" for Transaction type "<TransCategoryDescription>" by selecting search attribute as "Card number" in Transactions page
#    Then User click on search icon
##    And User validate the icon is displayed based on the transaction category
#    And User validate the transaction level field values present in Card Transaction with database and click on it
#    And User validate "main" section fields "Total amount,DateOfTransaction,Cost centre,Driver name,Vehicle description" values of "CardTransaction" with database
#    And User validate "details" section fields "Location,Location address,Posted time,Reference number,Terminal ID,Capture type,Transaction type,Authorisation number,Odometer" values of "CardTransaction" with database
#    And User click on Transaction Breakdown
#    And User validate "breakDownBorderForAllLines" section fields "Product,Unit price,Total (incl of tax & rebate)" values of "CardTransaction" with database
#    And User validate "breakDownAmountFields" section fields "Fees,Transaction amount,Total taxes,Total rebate" values of "CardTransaction" with database
#
#    Examples:
#      | ScenarioName                                            | TransCategoryDescription    | TransCategoryCID |
#      | Validate card transaction for transaction category 4701 | trans-Purchase-CardTransOID | 4701             |
#      | Validate card transaction for transaction category 4708 | trans-Host-CardTransOID     | 4708             |
#
#  @Smoke @Regression
#  Scenario Outline:Validate Non-Card Transaction data in Transaction page
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<ScenarioName>"
#    When User enter "UserName" and "PassWord"
#    And User click on login button
#    And User click on the "menu" "Transactions"
#    When User validate the count of transactions displayed in UI for date filter type "Last 90 days" with the database
#    And User get "<TransCategoryDescription>" from Transactions table using Transaction category cid "<TransCategoryCID>" for account selected in account picker
#    When User enter search keywords "REFERENCE" for Transaction type "<TransCategoryDescription>" by selecting search attribute as "Reference" in Transactions page
#    Then User click on search icon
#    And User validate the transaction level field values present in Card Transaction with database and click on it
#    And User validate "main" section fields "Total amount,Transaction type,GL account,DateOfTransaction,Posted time,Approved by" values of "NonCardTransaction" with database
#    And User validate "details" section fields "Cheque No,Remittance ID,Note" values of "NonCardTransaction" with database
##    And User validate the icon is displayed based on the transaction category
#
#    Examples:
#      | ScenarioName                                            | TransCategoryDescription             | TransCategoryCID |
#      | Validate Non card transaction for transaction category 4710 | trans-CardAdminFee-NonCardTransOID   | 4710             |
#      | Validate Non card transaction for transaction category 4711 | trans-CardIssueFee-NonCardTransOID   | 4711             |
#      | Validate Non card transaction for transaction category 4713 | trans-TransactionFee-NonCardTransOID | 4713             |
#      | Validate Non card transaction for transaction category 4712 | trans-AccountFee-NonCardTransOID     | 4712             |
#      | Validate Non card transaction for transaction category 4715 | trans-DepositAccount-NonCardTransOID | 4715             |
#      | Validate Non card transaction for transaction category 4719 | trans-Subsidy-NonCardTransOID        | 4719             |
#      | Validate Non card transaction for transaction category 4716 | trans-BillingNote-NonCardTransOID    | 4716             |
#      | Validate Non card transaction for transaction category 4702 | trans-Sundry-NonCardTransOID         | 4702             |
#      | Validate Non card transaction for transaction category 4702 | trans-Loyalty-NonCardTransOID        | 4705             |
#      | Validate Non card transaction for transaction category 4709 | trans-Other-NonCardTransOID          | 4709             |
#      | Validate Non card transaction for transaction category 4704 | trans-ChequePayment-NonCardTransOID  | 4704             |
#      | Validate Non card transaction for transaction category 4717 | trans-ManualPayment-NonCardTransOID  | 4717             |
#
#  @Smoke @Test
#  Scenario Outline:Validate Rebate Transaction data in Transaction page
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<ScenarioName>"
#    When User enter "UserName" and "PassWord"
#    And User click on login button
#    And User click on the "menu" "Transactions"
#    When User validate the count of transactions displayed in UI for date filter type "Last 30 days" with the database
#    And User get "<TransCategoryDescription>" from Transactions table using Transaction category cid "<TransCategoryCID>" for account selected in account picker
#    When User enter search keywords "REFERENCE" for Transaction type "<TransCategoryDescription>" by selecting search attribute as "Reference" in Transactions page
#    Then User click on search icon
#    And User validate the transaction level field values present in Card Transaction with database and click on it
#    And User validate "main" section fields "Total amount,Transaction type,GL account,DateOfTransaction,Posted time,Approved by" values of "NonCardTransaction" with database
#    And User validate "details" section fields "Cheque No,Remittance ID,Note" values of "NonCardTransaction" with database
#    And User click on Transaction Breakdown
#    And User validate "breakDownOfRebate" section fields "Product,Trans rebate type,Value,Rate,Rebate amount" values of "RebateTransaction" with database
#
#    Examples:
#      | ScenarioName                                            | TransCategoryDescription                 | TransCategoryCID |
#      | Validate card transaction for transaction category 4714 | trans-PeriodRebate-RebateNonCardTransOID | 4714             |
#
#  @Regression
#  Scenario: Validate "No results found, Please update your search keywords." message in search results by entering value in search text box which is not present in database
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    When User enter "UserName" and "PassWord"
#    And User click on login button
#    And User click on the "menu" "Transactions"
#    When User validate the count of transactions displayed in UI for date filter type "Last 90 days" with the database
#    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Card number"
#    Then User click on search icon
#    And User validate message of search results section "No results found,Please update your search keywords."
#    And User click on clear search icon
##    When User validate the count of transactions displayed in UI for date filter type "Last 30 days" with the database
#    Then User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Reference"
#    Then User click on search icon
#    And User validate message of search results section "No results found,Please update your search keywords."
#
#  @Regression
#  Scenario: Validate Product and Categories filters behavior in Transactions page
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    When User enter "UserName" and "PassWord"
#    And User click on login button
#    And User click on the "menu" "Transactions"
#    And User select filter values in Transactions module then click on Apply button and validate filter results in database for "Last 90 days"
#
#  @Regression
#  Scenario: Validate Sort functionality in Transactions page
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    When User enter "UserName" and "PassWord"
#    And User click on login button
#    And User click on the "menu" "Transactions"
##    And User verifies the "Drivers" page sort option Sort by Newest
##    And User verifies the "Drivers" page sort option Sort by Oldest
#
##  Scenario: Validate loaded Transactions getting exported in excel sheet
##    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
##    When User enter "UserName" and "PassWord"
##    And User click on login button
##    And User click on the "menu" "Transactions"
##    And User click on Download button then verify downloaded excel sheet