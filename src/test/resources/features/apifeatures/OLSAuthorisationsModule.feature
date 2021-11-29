@OLSAuthorisationsModule
Feature: Authorisations search and Authorisations details

  @OLSSearchAuthorisations
  Scenario Outline: OLS-374-Authorisations search API
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user gets the list of authorisation transactions based on filters and searches "<filterCombination>"
    Then verify the response status should be <statusCode>
#    Then user validates the authorisations default view details with "<statusMessage>" and "<statusNumber>" and based on filter search as "<filterCombination>"

    Examples:
      | testName                                                                                                                                         | statusCode | filterCombination                 | statusMessage           | statusNumber |
      | TC-01-OLS-374-Authorisations - Verify user should able to get the transaction details based on all filters and search fields                     | 200        | allFiltersAndSearches             |                         |              |
      | TC-02-OLS-374-Authorisations - Verify user should able to get the transaction details based on customer number                                   | 200        | customerNumberAsSingleSelect      |                         |              |
      | TC-03-OLS-374-Authorisations - Verify user should able to get the transaction details based on combination of customer number and card number    | 200        | customerNoWithCardNo              |                         |              |
      | TC-04-OLS-374-Authorisations - Verify user should able to get the transaction details based on full card  number search                          | 200        | fullCardNumberSearch              |                         |              |
      | TC-05-OLS-374-Authorisations - Verify user should able to get the transaction details based on last 5 digit card number search                   | 200        | last5DigitCardNumberPartialSearch |                         |              |
      | TC-06-OLS-374-Authorisations - Verify user should able to get the transaction details based on reference search                                  | 200        | fullReferenceSearch               |                         |              |
      | TC-07-OLS-374-Authorisations - Verify user should able to get the transaction details based on wildcard search for reference field               | 200        | wildCardSearchReferencePartial    |                         |              |
      | TC-08-OLS-374-Authorisations - Verify user should able to get the transaction details based on location number as single selected transaction    | 200        | locationNumberAsSingleSelect      |                         |              |
      | TC-09-OLS-374-Authorisations - Verify user should able to get the transaction details based on location number as Multi selected transaction     | 200        | locationNumberAsMultiSelect       |                         |              |
      | TC-10-OLS-374-Authorisations - Verify user should able to get the transaction details based on location name as single selected transaction      | 200        | locationNameAsSingleSelect        |                         |              |
      | TC-11-OLS-374-Authorisations - Verify user should able to get the transaction details based on location name as multi selected transaction       | 200        | locationNameAsMultiSelect         |                         |              |
      | TC-12-OLS-374-Authorisations - Verify user should able to get the transaction details based on cost centre field  as single selected transaction | 200        | costCentreAsSingleSelect          |                         |              |
      | TC-13-OLS-374-Authorisations - Verify user should able to get the transaction details based on cost centre as multi selected transaction         | 200        | costCentreAsMultiSelect           |                         |              |
      | TC-14-OLS-374-Authorisations - Verify user should able to get the transaction details based on customer number as multi selected transaction     | 200        | customerNumberAsMultiSelect       |                         |              |
      | TC-15-OLS-374-Authorisations - Verify user should able to get the transaction details based on driver name search                                | 200        | driverNameSearch                  |                         |              |
      | TC-16-OLS-374-Authorisations - Verify user should able to get the transaction details based on driver name field as partial search               | 200        | driverNameAsPartialSearch         |                         |              |
      | TC-17-OLS-374-Authorisations - Verify user should able to get the transaction details based on license plate search                              | 200        | licensePlateSearch                |                         |              |
      | TC-18-OLS-374-Authorisations - Verify user should able to get the transaction details based on license plate as partial search                   | 200        | licensePlateAsPartialSearch       |                         |              |
      | TC-19-OLS-374-Authorisations - Verify user should not able to get the transaction details based on unassociatedCardNumber                        | 200        | unassociatedCardNo                |                         |              |
      | TC-20-OLS-374-Authorisations - Verify user should not able to get the transaction details based on invalid customer number                       | 400        | invalidCusNo                      | Invalid Account Number  | 18018         |
      | TC-21-OLS-374-Authorisations - Verify user should not able to get the transaction details based on invalid location number                       | 200        | invalidLocationNo                 |                         |              |


#  @OLSAuthorisationDetails
#  Scenario Outline: OLS-374-Authorization details API
#    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user able to view the expanded section transaction details with possible scenarios as "<possibleCombination>"
#    Then verify the response status should be <statusCode>
#    Then user validates the transaction details from response and DB with "<statusNumber>" and "<statusMessage>"
#
#    Examples:
#      | testName                                                                                                | statusCode | statusMessage           | statusNumber | possibleCombination |
#      | TC-01-OLS-374-Verify user should able to get the transaction details by passing valid transaction_oid   | 200        |                         |              | validTransOid       |
#      | TC-02-OLS-374-Verify user should able to get the transaction details by passing invalid transaction_oid | 400        | TransactionId not found | 19059        | invalidTransOid     |

#  @OLSAuthorisationExport
#  Scenario Outline: OLS-100-Authorisations Export details API
#    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user able to export authorisation details with possible scenarios as "<possibleCombination>"
#    Then verify the response status should be <statusCode>
#    Then user validates the transaction details from response and DB with "<statusNumber>" and "<statusMessage>"

#    Examples:
#      | testName                                                                                                       | statusCode | statusMessage | statusNumber | possibleCombination |
#      | TC-01-OLS-100-Authorisations Export-Verify user should able to export details by passing valid transaction_oid | 200        |               |              | validTransOid       |
#      | TC-01-OLS-100-Authorisations Export-Verify user should able to export details by passing valid transaction_oid | 200        |               |              | validTransOid       |