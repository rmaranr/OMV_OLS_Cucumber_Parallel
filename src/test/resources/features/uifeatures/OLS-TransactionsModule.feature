Feature: OMV transactions scenarios

  @Test
  Scenario Outline: Validate Expanded section field values in Transaction module with out pinning an account for Purchase,Refund,rebate transaction
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User select account from account picker which has transactions contains transaction types "<transactionType>" and "<notContainsTransactionType>" based on field value "<fieldNameOfTransactionSearch>"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    And User validate message "Search for transactions" based on tag name "div"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    Then User get transaction record based on transaction type "<transactionType>" and "<notContainsTransactionType>" based on field name "<fieldNameOfTransactionSearch>"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "<OLSUserName>" in module "transactions"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Transactions" or not for field "<fieldNameOfTransactionSearch>" based on field value
    And User expand the record for which contains "<transactionType>" and not contains "<notContainsTransactionType>" which is at column position "2" in "Transactions" module
    And User validate "AdditionalInformation" omv section fields "costCentre,vehicleID,dr1iverID,fleetID,secondcardNO,OdoMeter" values of "<transactionType>" with database
    And User validate "CustomerTransactionBreakdown" omv section fields "numberOfUnits,unitPrice,Total(Incl. tax and rebate),taxRate,totalTaxes" values of "<transactionType>" with database
    And User validate "customerAmount" omv section fields "fees,customerAmount,taxRate,totalTaxes" values of "<transactionType>" with database
    Then User click on "View more details" in module "Transactions" based on tag name "div" which is at position "TransactionRecordPosition" and "is" mentioned in property file
    And User validate "TransactionDetail" omv section fields "Posted time,Location ID,Location address,Card type,Capture type,Account name,Authorization number,STAN,Batch number,Invoice number" values of "<transactionType>" with database
    And User validate "OriginalValue" omv section fields "Total amount" values of "<transactionType>" with database
    And User click on button "Close" using span text


    Examples:
      | Scenario                                                                                                                                  | OLSUserName | OLSPassWord | fieldNameOfTransactionSearch | transactionSearchFieldBehavior | transactionType | notContainsTransactionType      |
      | TC-01 : OMV-86,88 : Validate Transactions Expanded section field values and search using cardNO of transaction type "Purchase"            | OLSUserName | OLSPassWord | cardNO                       | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-02 : OMV-86,88 : Validate Transactions Expanded section field values and search using transRef of transaction type "Purchase"          | OLSUserName | OLSPassWord | transRef                     | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-03 : OMV-86,88 : Validate Transactions Expanded section field values and search using date of transaction type "Purchase"              | OLSUserName | OLSPassWord | date                         | Last 90 days                   | Purchase        | Purchase Return,Purchase Refund |
#      | TC-04 : OMV-86,88 : Validate Transactions Expanded section field values and search using Dname of transaction type "Purchase"             | OLSUserName | OLSPassWord | Dname                        | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-05 : OMV-86,88 : Validate Transactions Expanded section field values and search using vlPlate of transaction type "Purchase"           | OLSUserName | OLSPassWord | vlPlate                      | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-06 : OMV-86,88 : Validate Transactions Expanded section field values and search using SiteOr LocationID of transaction type "Purchase" | OLSUserName | OLSPassWord | siteLocationId               | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-07 : OMV-86,88 : Validate Transactions Expanded section field values and search using costCentre of transaction type "Purchase"        | OLSUserName | OLSPassWord | costCentre                   | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-07 : OMV-86,88 : Validate Transactions Expanded section field values and search using costCentre of transaction type "Purchase"        | OLSUserName | OLSPassWord | cAccountNo                   | text                           | Purchase        | Purchase Return,Purchase Refund |
#
#      | TC-08 : OMV-86,88 : Validate Transactions Expanded section field values and search using cardNO of transaction type "Refund"              | OLSUserName | OLSPassWord | cardNO                       | text                           | Purchase Refund | Purchase Return                 |
#      | TC-08 : OMV-86,88 : Validate Transactions Expanded section field values and search using cardNO of transaction type "Refund"              | OLSUserName | OLSPassWord | cAccountNo                   | text                           | Purchase Refund | Purchase Return                 |
#      | TC-09 : OMV-86,88 : Validate Transactions Expanded section field values and search using transRef of transaction type "Refund"            | OLSUserName | OLSPassWord | transRef                     | text                           | Purchase Refund | Purchase Return                 |
#      | TC-10 : OMV-86,88 : Validate Transactions Expanded section field values and search using date of transaction type "Refund"                | OLSUserName | OLSPassWord | date                         | Last 90 days                   | Purchase Refund | Purchase Return                 |
#      | TC-11 : OMV-86,88 : Validate Transactions Expanded section field values and search using Dname of transaction type "Refund"               | OLSUserName | OLSPassWord | Dname                        | text                           | Purchase Refund | Purchase Return                 |
#      | TC-12 : OMV-86,88 : Validate Transactions Expanded section field values and search using vlPlate of transaction type "Refund"             | OLSUserName | OLSPassWord | vlPlate                      | text                           | Purchase Refund | Purchase Return                 |
#      | TC-13 : OMV-86,88 : Validate Transactions Expanded section field values and search using siteLocationId of transaction type "Refund"      | OLSUserName | OLSPassWord | siteLocationId               | text                           | Purchase Refund | Purchase Return                 |
#      | TC-14 : OMV-86,88 : Validate Transactions Expanded section field values and search using costCentre of transaction type "Refund"          | OLSUserName | OLSPassWord | costCentre                   | text                           | Purchase Refund | Purchase Return                 |
#
#      | TC-15 : OMV-86,88 : Validate Transactions Expanded section field values and search using cardNO of transaction type "Return"              | OLSUserName | OLSPassWord | cardNO                       | text                           | Purchase Return | Purchase Refund                 |
#      | TC-15 : OMV-86,88 : Validate Transactions Expanded section field values and search using cardNO of transaction type "Return"              | OLSUserName | OLSPassWord | cAccountNo                   | text                           | Purchase Return | Purchase Refund                 |
#      | TC-16 : OMV-86,88 : Validate Transactions Expanded section field values and search using transRef of transaction type "Return"            | OLSUserName | OLSPassWord | transRef                     | text                           | Purchase Return | Purchase Refund                 |
#      | TC-17 : OMV-86,88 : Validate Transactions Expanded section field values and search using date of transaction type "Return"                | OLSUserName | OLSPassWord | date                         | Last 90 days                   | Purchase Return | Purchase Refund                 |
#      | TC-18 : OMV-86,88 : Validate Transactions Expanded section field values and search using Dname of transaction type "Return"               | OLSUserName | OLSPassWord | Dname                        | text                           | Purchase Return | Purchase Refund                 |
#      | TC-19 : OMV-86,88 : Validate Transactions Expanded section field values and search using vlPlate of transaction type "Return"             | OLSUserName | OLSPassWord | vlPlate                      | text                           | Purchase Return | Purchase Refund                 |
#      | TC-20 : OMV-86,88 : Validate Transactions Expanded section field values and search using siteLocationId of transaction type "Return"      | OLSUserName | OLSPassWord | siteLocationId               | text                           | Purchase Return | Purchase Refund                 |
#      | TC-21 : OMV-86,88 : Validate Transactions Expanded section field values and search using costCentre of transaction type "Return"          | OLSUserName | OLSPassWord | costCentre                   | text                           | Purchase Return | Purchase Refund                 |

  Scenario Outline: Validate Expanded section field values in Transaction module with out pinning an account for Sundry transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has transactions contains transaction types "<transactionType>" and "<notContainsTransactionType>" based on field value "<fieldNameOfTransactionSearch>"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "<transactionType>" and "<notContainsTransactionType>" based on field name "<fieldNameOfTransactionSearch>"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "<OLSUserName>" in module "transactions"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Transactions" or not for field "<fieldNameOfTransactionSearch>" based on field value
    And User expand the record for which contains "<transactionType>" and not contains "<notContainsTransactionType>" which is at column position "2" in "Transactions" module
    And User validate "TransactionDetails" omv section fields "postedTime,Approval,invoiceNumber,adjType,adjProduct,chequeNo,remittanceId" values of "<transactionType>" with database
    And User validate "customerTransactionBreakdown" omv section fields "product,taxTotal,customerTotal" values of "<transactionType>" with database
    Then User click on "View details" in module "Transactions" based on tag name "div" which is at position "TransactionRecordPosition" and "is" mentioned in property file
    And User validate "AdditionalDetails" omv section fields "Details" values of "<transactionType>" with database
    And User click on button "Close" using span text

    Examples:
      | Scenario                                                                                                                                     | OLSUserName | OLSPassWord | fieldNameOfTransactionSearch | transactionSearchFieldBehavior | transactionType | notContainsTransactionType |
      | TC-22 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using cardNO of transaction type "Purchase"         | OLSUserName | OLSPassWord | cardNO                       | text                           | Sundry          | Purchase                   |
      | TC-23 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using transRef of transaction type "Purchase"       | OLSUserName | OLSPassWord | transRef                     | text                           | Sundry          | Purchase                   |
      | TC-24 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using date of transaction type "Purchase"           | OLSUserName | OLSPassWord | date                         | Last 90 days                   | Sundry          | Purchase                   |
      | TC-25 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using Dname of transaction type "Purchase"          | OLSUserName | OLSPassWord | Dname                        | text                           | Sundry          | Purchase                   |
      | TC-26 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using vlPlate of transaction type "Purchase"        | OLSUserName | OLSPassWord | vlPlate                      | text                           | Sundry          | Purchase                   |
      | TC-27 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using siteLocationId of transaction type "Purchase" | OLSUserName | OLSPassWord | siteLocationId               | text                           | Sundry          | Purchase                   |
      | TC-28 : OMV-86,88 : Validate Sundry transaction Expanded section field values and search using costCentre of transaction type "Purchase"     | OLSUserName | OLSPassWord | costCentre                   | text                           | Sundry          | Purchase                   |

  Scenario: TC-29 : OMV-86,88 : Validate "No results found,Please update your search keywords." after searching using invalid field value
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    When User enter "OLSUserName" and "OLSPassWord"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has transactions contains transaction types "" and "" based on field value "transRef"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    And User enter value "782900017564738475937" using tag name "mav-input", attribute name "ng-reflect-name", attribute value "cardNO" with remaining locator address ">input"
    And User click on " Search " button
    And User validate message of search results section "No results found,Please update your search keywords."

#  Scenario Outline: Validate User is able to perform dispute action for transaction records which are in posted status
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
#    And User select account from account picker which has transactions contains transaction types "" and "" based on field value "<fieldNameOfTransactionSearch>"
#    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
#    When User click on the "menu" "Transactions"
#    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
#    Then User get transaction record based on transaction type "Purchase" and "Return,Refund" based on field name "status"
#    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "OLSUserName" in module "transactions"
#    And User click on " Search " button
#    And User click three dot icon of "1" record in module "transactions" based on "status"
#    Then User click on "Dispute transaction" based on tag name "button"
#    And User validate field value "reference" using tag "div", attributeName "class", attributeValue "ref-text" with the property value "LatestTransactionReference"
#    And User click on "Submit" button
#    And User validate "snackbar-text" message "Dispute has been submitted" in "transactions" module
#    Examples:
#      | Scenario                                                                                                                                              | OLSUserName        | OLSPassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
#      | TC-112 : OMV-93 : Validate User is able to perform dispute action for transaction records which are in posted status by logging in using client user  | OLSUserName        | OLSPassWord        | cardNO                       | text                           |

  Scenario Outline: User validate filters behaviour of Transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has transactions contains transaction types "" and "" based on field value "<fieldNameOfTransactionSearch>"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "OLSUserName" in module "transactions"
    And User click on " Search " button
    And User select filter values in OMV-Transactions module then click on Apply button and validate filter results in database

    Examples:
      | Scenario                                                                                   | OLSUserName | OLSPassWord | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-30 : OMV-86,88 : Validate All filters behavior for Client user with out pinning account | OLSUserName | OLSPassWord | date                         | Last 90 days                   |

  @Test
  Scenario Outline: User validate export behaviour of Transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "Purchase" and "Return,Refund" based on field name "status"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientOLSUserName" in module "transactions"
    And User click on " Search " button
    And User click on download button validate format of excel file is ".csv"
    And User open the excel sheet and validate the data present in it with the database for module "OMVTransactions" based on primary key "AccountNo"

    Examples:
      | Scenario                                                                                              | OLSUserName        | OLSPassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-118 : OMV-94 : Validate the export details of transaction for Client user with out pinning account | OLSUserName | CountryOLSPassWord | cAccountNo                   | text                           |