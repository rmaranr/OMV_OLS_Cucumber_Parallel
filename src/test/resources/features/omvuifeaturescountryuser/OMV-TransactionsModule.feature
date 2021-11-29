Feature: UI - Country User : OMV transactions scenarios

  @UI-Transactions @Test
  Scenario Outline: Country User : Validate Expanded section field values in Transaction module with out pinning an account for Purchase,Refund,rebate transaction
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "2"
    And User validate message "Search for transactions" based on tag name "div"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    Then User get transaction record based on transaction type "<transactionType>" and "<notContainsTransactionType>" based on field name "<fieldNameOfTransactionSearch>"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "<UserName>" in module "transactions"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Transactions" or not for field "<fieldNameOfTransactionSearch>" based on field value
    And User expand the record for which contains "<transactionType>" and not contains "<notContainsTransactionType>" which is at column position "2" in "Transactions" module
    And User validate "AdditionalInformation" omv section fields "costCentre,vehicleID,driverID,fleetID,secondcardNO,OdoMeter" values of "<transactionType>" with database
    And User validate "CustomerTransactionBreakdown" omv section fields "numberOfUnits,unitPrice,Total(Incl. tax and rebate),taxRate,totalTaxes" values of "<transactionType>" with database
    And User validate "customerAmount" omv section fields "fees,customerAmount,taxRate,totalTaxes" values of "<transactionType>" with database
    Then User click on "View more details" in module "Transactions" based on tag name "div" which is at position "TransactionRecordPosition" and "is" mentioned in property file
    And User validate "TransactionDetail" omv section fields "Posted time,Location ID,Location address,Card type,Capture type,Account name,Authorization number,STAN,Batch number,Invoice number" values of "<transactionType>" with database
    And User validate "OriginalValue" omv section fields "Total amount" values of "<transactionType>" with database
    And User validate "MerchantTransactionBreakdown" omv section fields "numberOfUnits,unitPrice,Total(Incl. tax and rebate),taxRate,totalTaxes" values of "<transactionType>" with database
    And User validate "MerchantAmount" omv section fields "fees,merchantAmount,taxRate,totalTaxes" values of "<transactionType>" with database
    And User click on button "Close" using span text

    Examples:
      | Scenario                                                                                                                                                                                           | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior | transactionType | notContainsTransactionType      |
      | TC-01 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using cardNO of transaction type "Purchase"         | CountryUserName | CountryPassword | cardNO                       | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-02 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using transRef of transaction type "Purchase"       | CountryUserName | CountryPassword | transRef                     | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-03 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using date of transaction type "Purchase"           | CountryUserName | CountryPassword | date                         | Last 90 days                   | Purchase        | Purchase Return,Purchase Refund |
#      | TC-04 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using Dname of transaction type "Purchase"          | CountryUserName | CountryPassword | Dname                        | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-05 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Purchase"        | CountryUserName | CountryPassword | vlPlate                      | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-06 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using cAccountNo of transaction type "Purchase"     | CountryUserName | CountryPassword | cAccountNo                   | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-07 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Purchase" | CountryUserName | CountryPassword | siteLocationId               | text                           | Purchase        | Purchase Return,Purchase Refund |
#      | TC-08 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using costCentre of transaction type "Purchase"     | CountryUserName | CountryPassword | costCentre                   | text                           | Purchase        | Purchase Return,Purchase Refund |
#
#      | TC-09 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using cardNO of transaction type "Refund"           | CountryUserName | CountryPassword | cardNO                       | text                           | Purchase Refund | Purchase Return                 |
#      | TC-10 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using transRef of transaction type "Refund"         | CountryUserName | CountryPassword | transRef                     | text                           | Purchase Refund | Purchase Return                 |
#      | TC-11 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using date of transaction type "Refund"             | CountryUserName | CountryPassword | date                         | Last 90 days                   | Purchase Refund | Purchase Return                 |
#      | TC-12 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using Dname of transaction type "Refund"            | CountryUserName | CountryPassword | Dname                        | text                           | Purchase Refund | Purchase Return                 |
#      | TC-13 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Refund"          | CountryUserName | CountryPassword | vlPlate                      | text                           | Purchase Refund | Purchase Return                 |
#      | TC-14 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using cAccountNo of transaction type "Refund"       | CountryUserName | CountryPassword | cAccountNo                   | text                           | Purchase Refund | Purchase Return                 |
#      | TC-15 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Refund"   | CountryUserName | CountryPassword | siteLocationId               | text                           | Purchase Refund | Purchase Return                 |
#      | TC-16 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using costCentre of transaction type "Refund"       | CountryUserName | CountryPassword | costCentre                   | text                           | Purchase Refund | Purchase Return                 |
#
#      | TC-17 : OMV-87,118 : Valida te Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using cardNO of transaction type "Return"          | CountryUserName | CountryPassword | cardNO                       | text                           | Purchase Return | Purchase Refund                 |
#      | TC-18 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using transRef of transaction type "Return"         | CountryUserName | CountryPassword | transRef                     | text                           | Purchase Return | Purchase Refund                 |
#      | TC-19 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using date of transaction type "Return"             | CountryUserName | CountryPassword | date                         | Last 90 days                   | Purchase Return | Purchase Refund                 |
#      | TC-20 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using Dname of transaction type "Return"            | CountryUserName | CountryPassword | Dname                        | text                           | Purchase Return | Purchase Refund                 |
#      | TC-21 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Return"          | CountryUserName | CountryPassword | vlPlate                      | text                           | Purchase Return | Purchase Refund                 |
#      | TC-22 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using cAccountNo of transaction type "Return"       | CountryUserName | CountryPassword | cAccountNo                   | text                           | Purchase Return | Purchase Refund                 |
#      | TC-23 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Return"   | CountryUserName | CountryPassword | siteLocationId               | text                           | Purchase Return | Purchase Refund                 |
#      | TC-24 : OMV-87,118 : Validate Transactions Expanded section field values with out pinning an account by logging in using csrCountry and search using costCentre of transaction type "Return"       | CountryUserName | CountryPassword | costCentre                   | text                           | Purchase Return | Purchase Refund                 |

  @UI-Transactions
  Scenario Outline: Validate Expanded section field values in Transaction module after pinning an account for Purchase,Refund,rebate transaction
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User set accountNumber value is property file like "accountNumberFromAccountPicker" in Transactions module
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "<transactionType>" and "<notContainsTransactionType>" based on field name "<fieldNameOfTransactionSearch>"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "<UserName>" in module "transactions"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Transactions" or not for field "<fieldNameOfTransactionSearch>" based on field value
    And User expand the record for which contains "<transactionType>" and not contains "<notContainsTransactionType>" which is at column position "2" in "Transactions" module
    And User validate "AdditionalInformation" omv section fields "costCentre,vehicleID,dr1iverID,fleetID,secondcardNO,OdoMeter" values of "<transactionType>" with database
    And User validate "CustomerTransactionBreakdown" omv section fields "numberOfUnits,unitPrice,Total(Incl. tax and rebate),taxRate,totalTaxes" values of "<transactionType>" with database
    And User validate "customerAmount" omv section fields "fees,customerAmount,taxRate,totalTaxes" values of "<transactionType>" with database
    Then User click on "View more details" in module "Transactions" based on tag name "div" which is at position "TransactionRecordPosition" and "is" mentioned in property file
    And User validate "TransactionDetail" omv section fields "Posted time,Location ID,Location address,Card type,Capture type,Account name,Authorization number,STAN,Batch number,Invoice number" values of "<transactionType>" with database
    And User validate "OriginalValue" omv section fields "Total amount" values of "<transactionType>" with database
    And User validate "MerchantTransactionBreakdown" omv section fields "numberOfUnits,unitPrice,Total(Incl. tax and rebate),taxRate,totalTaxes" values of "<transactionType>" with database
    And User validate "MerchantAmount" omv section fields "fees,merchantAmount,taxRate,totalTaxes" values of "<transactionType>" with database
    And User click on button "Close" using span text

    Examples:
      | Scenario                                                                                                                                                                                         | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior | transactionType | notContainsTransactionType      |
      | TC-25 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using cardNO of transaction type "Purchase"         | CountryUserName | CountryPassword | cardNO                       | text                           | Purchase        | Purchase Return,Purchase Refund |
      | TC-26 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using transRef of transaction type "Purchase"       | CountryUserName | CountryPassword | transRef                     | text                           | Purchase        | Purchase Return,Purchase Refund |
      | TC-27 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using date of transaction type "Purchase"           | CountryUserName | CountryPassword | date                         | Last 90 days                   | Purchase        | Purchase Return,Purchase Refund |
      | TC-28 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using Dname of transaction type "Purchase"          | CountryUserName | CountryPassword | Dname                        | text                           | Purchase        | Purchase Return,Purchase Refund |
      | TC-29 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Purchase"        | CountryUserName | CountryPassword | vlPlate                      | text                           | Purchase        | Purchase Return,Purchase Refund |
      | TC-30 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Purchase" | CountryUserName | CountryPassword | siteLocationId               | text                           | Purchase        | Purchase Return,Purchase Refund |
      | TC-31 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using costCentre of transaction type "Purchase"     | CountryUserName | CountryPassword | costCentre                   | text                           | Purchase        | Purchase Return,Purchase Refund |

      | TC-32 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using cardNO of transaction type "Refund"           | CountryUserName | CountryPassword | cardNO                       | text                           | Purchase Refund | Purchase Return                 |
      | TC-33 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using transRef of transaction type "Refund"         | CountryUserName | CountryPassword | transRef                     | text                           | Purchase Refund | Purchase Return                 |
      | TC-34 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using date of transaction type "Refund"             | CountryUserName | CountryPassword | date                         | Last 90 days                   | Purchase Refund | Purchase Return                 |
      | TC-35 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using Dname of transaction type "Refund"            | CountryUserName | CountryPassword | Dname                        | text                           | Purchase Refund | Purchase Return                 |
      | TC-36 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Refund"          | CountryUserName | CountryPassword | vlPlate                      | text                           | Purchase Refund | Purchase Return                 |
      | TC-37 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Refund"   | CountryUserName | CountryPassword | siteLocationId               | text                           | Purchase Refund | Purchase Return                 |
      | TC-38 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using costCentre of transaction type "Refund"       | CountryUserName | CountryPassword | costCentre                   | text                           | Purchase Refund | Purchase Return                 |

      | TC-39 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using cardNO of transaction type "Return"           | CountryUserName | CountryPassword | cardNO                       | text                           | Purchase Return | Purchase Refund                 |
      | TC-40 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using transRef of transaction type "Return"         | CountryUserName | CountryPassword | transRef                     | text                           | Purchase Return | Purchase Refund                 |
      | TC-41 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using date of transaction type "Return"             | CountryUserName | CountryPassword | date                         | Last 90 days                   | Purchase Return | Purchase Refund                 |
      | TC-42 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using Dname of transaction type "Return"            | CountryUserName | CountryPassword | Dname                        | text                           | Purchase Return | Purchase Refund                 |
      | TC-43 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Return"          | CountryUserName | CountryPassword | vlPlate                      | text                           | Purchase Return | Purchase Refund                 |
      | TC-44 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Return"   | CountryUserName | CountryPassword | siteLocationId               | text                           | Purchase Return | Purchase Refund                 |
      | TC-45 : OMV-115,117 : Validate Transactions Expanded section field values after pinning an account by logging in using csrCountry and search using costCentre of transaction type "Return"       | CountryUserName | CountryPassword | costCentre                   | text                           | Purchase Return | Purchase Refund                 |

  Scenario Outline: Validate Expanded section field values in Transaction module with out pinning an account for Sundry transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "<transactionType>" and "<notContainsTransactionType>" based on field name "<fieldNameOfTransactionSearch>"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "<UserName>" in module "transactions"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Transactions" or not for field "<fieldNameOfTransactionSearch>" based on field value
    And User expand the record for which contains "<transactionType>" and not contains "<notContainsTransactionType>" which is at column position "2" in "Transactions" module
    And User validate "TransactionDetails" omv section fields "postedTime,Approval,invoiceNumber,adjType,adjProduct,chequeNo,remittanceId" values of "<transactionType>" with database
    And User validate "customerTransactionBreakdown" omv section fields "product,taxTotal,customerTotal" values of "<transactionType>" with database
    Then User click on "View details" in module "Transactions" based on tag name "div" which is at position "TransactionRecordPosition" and "is" mentioned in property file
    And User validate "AdditionalDetails" omv section fields "Details" values of "<transactionType>" with database
    And User click on button "Close" using span text

    Examples:
      | Scenario                                                                                                                                                                                              | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior | transactionType | notContainsTransactionType |
      | TC-46 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using cardNO of transaction type "Purchase"         | CountryUserName | CountryPassword | cardNO                       | text                           | Sundry          | Purchase                   |
      | TC-47 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using transRef of transaction type "Purchase"       | CountryUserName | CountryPassword | transRef                     | text                           | Sundry          | Purchase                   |
      | TC-48 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using date of transaction type "Purchase"           | CountryUserName | CountryPassword | date                         | Last 90 days                   | Sundry          | Purchase                   |
      | TC-49 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using Dname of transaction type "Purchase"          | CountryUserName | CountryPassword | Dname                        | text                           | Sundry          | Purchase                   |
      | TC-50 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Purchase"        | CountryUserName | CountryPassword | vlPlate                      | text                           | Sundry          | Purchase                   |
      | TC-51 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Purchase" | CountryUserName | CountryPassword | siteLocationId               | text                           | Sundry          | Purchase                   |
      | TC-52 : OMV-113 : Validate Sundry transaction Expanded section field values with out pinning an account by logging in using csrCountry and search using costCentre of transaction type "Purchase"     | CountryUserName | CountryPassword | costCentre                   | text                           | Sundry          | Purchase                   |

#  @Test
  Scenario Outline: Validate Expanded section field values in Transaction module after pinning an account for Sundry transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "<transactionType>" and "<notContainsTransactionType>" based on field name "<fieldNameOfTransactionSearch>"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "<UserName>" in module "transactions"
    And User click on " Search " button
    Then User validate based on the search criteria the record count displayed in module "Transactions" or not for field "<fieldNameOfTransactionSearch>" based on field value
    And User expand the record for which contains "<transactionType>" and not contains "<notContainsTransactionType>" which is at column position "2" in "Transactions" module
    And User validate "TransactionDetails" omv section fields "postedTime,Approval,invoiceNumber,adjType,adjProduct,chequeNo,remittanceId" values of "<transactionType>" with database
    And User validate "customerTransactionBreakdown" omv section fields "product,taxTotal,customerTotal" values of "<transactionType>" with database
    Then User click on "View details" in module "Transactions" based on tag name "div" which is at position "TransactionRecordPosition" and "is" mentioned in property file
    And User validate "AdditionalDetails" omv section fields "Details" values of "<transactionType>" with database
    And User click on button "Close" using span text

    Examples:
      | Scenario                                                                                                                                                                                            | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-53 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using cardNO of transaction type "Purchase"         | CountryUserName | CountryPassword | cardNO                       | text                           |
      | TC-54 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using transRef of transaction type "Purchase"       | CountryUserName | CountryPassword | transRef                     | text                           |
      | TC-55 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using date of transaction type "Purchase"           | CountryUserName | CountryPassword | date                         | Last 90 days                   |
      | TC-56 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using Dname of transaction type "Purchase"          | CountryUserName | CountryPassword | Dname                        | text                           |
      | TC-57 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using vlPlate of transaction type "Purchase"        | CountryUserName | CountryPassword | vlPlate                      | text                           |
      | TC-58 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using siteLocationId of transaction type "Purchase" | CountryUserName | CountryPassword | siteLocationId               | text                           |
      | TC-59 : OMV-114 : Validate Sundry transaction Expanded section field values after pinning an account by logging in using csrCountry and search using costCentre of transaction type "Purchase"     | CountryUserName | CountryPassword | costCentre                   | text                           |

  Scenario: TC-60 : Validate "No results found,Please update your search keywords." after searching using invalid field value
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    When User enter "ClientUserName" and "ClientPassWord"
#    And User click on login button
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    And User enter value "782900017564738475937" using tag name "mav-input", attribute name "ng-reflect-name", attribute value "cardNO" with remaining locator address ">input"
    And User click on " Search " button
    And User validate message of search results section "No results found,Please update your search keywords."

  Scenario Outline: Validate User is able to perform dispute action for transaction records which are in posted status
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "Purchase" and "Return,Refund" based on field name "status"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientUserName" in module "transactions"
    And User click on " Search " button
    And User click three dot icon of "1" record in module "transactions" based on "status"
    Then User click on "Dispute transaction" based on tag name "button"
    And User validate field value "reference" using tag "div", attributeName "class", attributeValue "ref-text" with the property value "LatestTransactionReference"
    And User click on "Submit" button
    And User validate "snackbar-text" message "Dispute has been submitted" in "transactions" module
    Examples:
      | Scenario                                                                                                                                              | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-61 : OMV-93 : Validate User is able to perform dispute action for transaction records which are in posted status by logging in using country user | CountryUserName | CountryPassword | cardNO                       | text                           |

  Scenario Outline: Validate User is able to perform dispute action for transaction records which are in posted status
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "Purchase" and "Return,Refund" based on field name "status"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientUserName" in module "transactions"
    And User click on " Search " button
    And User click three dot icon of "1" record in module "transactions" based on "status"
    Then User click on "Dispute transaction" based on tag name "button"
    And User validate field value "reference" using tag "div", attributeName "class", attributeValue "ref-text" with the property value "LatestTransactionReference"
    And User click on "Submit" button
    And User validate "snackbar-text" message "Dispute has been submitted" in "transactions" module
    Examples:
      | Scenario                                                                                                                                                                    | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-62 : OMV-93 : Validate User is able to perform dispute action for transaction records which are in posted status by logging in using country user after pinning account | CountryUserName | CountryPassword | cardNO                       | text                           |

  Scenario Outline: User validate filters behaviour of Transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientUserName" in module "transactions"
    And User click on " Search " button
#    And User validate filter "All statuses" which is at position "1" values are displayed based on searched filed "<fieldNameOfTransactionSearch>" as per the database or not
    And User select filter values in OMV-Transactions module then click on Apply button and validate filter results in database

    Examples:
      | Scenario                                                                                   | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-63 : OMV-110 : Validate All filters behavior for Country user with out pinning account | CountryUserName | CountryPassword | date                         | Last 90 days                   |

#  @Test
  Scenario Outline: User validate export behaviour of Transactions
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Transactions"
    And User click button "Transactions" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Transactions" based on position "1"
    Then User get transaction record based on transaction type "Purchase" and "Return,Refund" based on field name "status"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientUserName" in module "transactions"
    And User click on " Search " button
    And User click on download button validate format of excel file is ".csv"
    And User open the excel sheet and validate the data present in it with the database for module "OMVTransactions" based on primary key "AccountNo"

    Examples:
      | Scenario                                                                                              | UserName        | PassWord        | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-64 : OMV-94 : Validate the export details of transaction for Client user with out pinning account | CountryUserName | CountryPassword | cAccountNo                   | text                           |