@Accounts
Feature: API - Accounts module
  @OMVAccountsSearch
  Scenario Outline: OLS- 587- To get the list of accounts
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
#    When User hit the accounts search API without any filters
    When User hit the accounts search API with filter parameter as "<filterSearch>"
    Then validate the response status code "<statusCode>"

    Examples:
      | testCaseName                                                                              | statusCode | filterSearch        |
      | TC-01-OMV-20-Verify user should able to get account based on accountNo                    | 200        | accountNo           |
      | TC-02-OMV-20-Verify user should able to get account based on Country                      | 200        | country             |
      | TC-03-OMV-20-Verify user should able to get account based on cardNo                       | 200        | cardNo              |
      | TC-04-OMV-20-Verify user should able to get account based on accountName                  | 200        | accountName         |
      | TC-05-OMV-20-Verify user should able to get account based on accountNo(wildCard search)   | 200        | accountNoWildChar   |
      | TC-06-OMV-20-Verify user should able to get account based on cardNo(last 5 digit)         | 200        | cardNoWildChar      |
      | TC-07-OMV-20-Verify user should able to get account based on accountName(wildCard search) | 200        | accountNameWildChar |

  @AccountDetails
  Scenario Outline: OMV-51- To get the Specific account details
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When User hit the accounts search API with filter parameter as "<filterSearch>"
    When user gets the specific account details based on country Id as "<filterParam>"
    Then validate the response status code "<statusCode>"
    Then validate the account details and error response status number "<statusNumber>" and status message "<statusMessage>"
    Examples:
      | testCaseName                                                                                    | statusCode | filterSearch | statusNumber | statusMessage | filterParam      |
      | TC-01-OMV-51-Verify user should able to get the account details based on valid country Id       | 200        | accountNo    |              |               | validCountryId   |
      | TC-02-OMV-51-Verify user should not able to get the account details based on invalid country Id | 400        | accountNo    | 6            | Invalid entry | invalidCountryId |


  @GetSpecificAccountInformation
  Scenario Outline: OMV-1051- To get the Specific account information
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the specific account information based on customer number "<filterSearch>"
    Then validate the response status code "<statusCode>"
    Then validate the specific account information response with "<statusNumber>" and status message "<statusMessage>"
    Examples:
      | testCaseName                                                                                      | statusCode | filterSearch    | statusNumber | statusMessage |
      | TC-01-OMV-1051-Verify user should able to get the account information for valid customer          | 200        | validCustomer   |              |               |
      | TC-02-OMV-1051-Verify user should not able to get the account information for invalid customer | 400        | invalidCustomer    | 4018           | Invalid Customer Number |

  @UpdateAccountInformation
  Scenario Outline: OMV-1042- Edit Account information
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user updates the account information based on customer number "<filterSearch>"
    Then validate the response status code "<statusCode>"
   Then validate the specific account information response with "<statusNumber>" and status message "<statusMessage>"
    Examples:
      | testCaseName                                                                                      | statusCode | filterSearch    | statusNumber | statusMessage |
      | TC-01-OMV-1051-Verify user should able to update the account information for valid customer       | 200        | validCustomer   |              |               |
      | TC-02-OMV-1051-Verify user should not able to update the account information for invalid customer | 400        | invalidCustomer | 18018        | Invalid Account Number |


  @GetSpecificFinancialInformation
  Scenario Outline: OMV-1051- To get the Specific financial information
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the specific financial information based on customer number "<filterSearch>"
    Then validate the response status code "<statusCode>"
#    Then validate the specific financial information response with "<statusNumber>" and status message "<statusMessage>"
     Examples:
      | testCaseName                                                                                     | statusCode | filterSearch    | statusNumber | statusMessage           |
      | TC-03-OMV-1051-Verify user should able to get the financial information for valid customer       | 200        | validCustomer   |              |                         |
      | TC-04-OMV-1051-Verify user should not able to get the financial information for invalid customer | 400        | invalidCustomer    | 4018           | Invalid Customer Number |


#  @UpdateFinancialInformation
#  Scenario Outline: OMV-1044- Edit financial information
#    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
#    When user updates the financial information based on customer number "<filterSearch>"
#    Then validate the response status code "<statusCode>"
#    Then validate the specific financial information response with "<statusNumber>" and status message "<statusMessage>"
#    Examples:
#      | testCaseName                                                                                     | statusCode | filterSearch    | statusNumber | statusMessage           |
#      | TC-01-OMV-1044-Verify user should able to update the financial information for valid customer       | 200        | validCustomer   |              |                         |
#      | TC-02-OMV-1044-Verify user should not able to update the financial information for invalid customer | 400        | invalidCustomer    | 4018           | Invalid Customer Number |


  @CSRGetSuscription
  Scenario Outline: Get Subscription to the customer
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user get subscription to the selected customer"<customerNumber>"
#    Then validate and verify subscriptions for the customer
#    Then user post request body to get cards and validate with "<statusMessage>" and <statusNumber> as expected values
#    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                   | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OMV-Verify user should able get subscriptions for an associated account               | associatedUser   | 200        | empty                                            | 0            |

  @CSRAddSuscription
  Scenario Outline: Get Subscription to the customer
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user add subscription to the selected customer"<customerNumber>"
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                   | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OMV-Verify user should able add subscriptions for an associated account               | associatedUser   | 200        | empty                                            | 0            |

  @CSREditSuscription
  Scenario Outline: Get Subscription to the customer
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user edit subscription to the selected customer"<customerNumber>"
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                   | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OMV-Verify user should able add subscriptions for an associated account               | associatedUser   | 200        | empty                                            | 0            |
