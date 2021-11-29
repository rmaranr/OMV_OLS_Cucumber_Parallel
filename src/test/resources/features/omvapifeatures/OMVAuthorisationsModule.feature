@OMVTransactionsModule
Feature: API - Authorisations search and Authorisations details

  @OMVSearchAuthorisations
  Scenario Outline: OMV-83-Authorisations search API
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user gets the list of OMV authorisation transactions based on filters and searches "<filterCombination>"
    Then verify the response status should be <statusCode>
#    Then user validates the authorisations default view details with "<statusMessage>" and "<statusNumber>" and based on filter search as "<filterCombination>"

    Examples:
      | testName                                                                                                                                        | statusCode | filterCombination                 | statusMessage           | statusNumber |
      | TC-01-OMV-83-Authorisations - Verify user should able to get the authorization details based on all filters and search fields                     | 200        | allFiltersAndSearches             |                         |              |
      | TC-02-OMV-83-Authorisations - Verify user should able to get the authorization details based on customer number                                   | 200        | customerNumberAsSingleSelect      |                         |              |
      | TC-03-OMV-83-Authorisations - Verify user should able to get the authorization details based on combination of customer number and card number    | 200        | customerNoWithCardNo              |                         |              |
      | TC-04-OMV-83-Authorisations - Verify user should able to get the authorization details based on full card  number search                          | 200        | fullCardNumberSearch              |                         |              |
      | TC-05-OMV-83-Authorisations - Verify user should able to get the authorization details based on last 5 digit card number search                   | 200        | last5DigitCardNumberPartialSearch |                         |              |
      | TC-06-OMV-83-Authorisations - Verify user should able to get the authorization details based on reference search                                  | 200        | fullReferenceSearch               |                         |              |
      | TC-07-OMV-83-Authorisations - Verify user should able to get the authorization details based on wildcard search for reference field               | 200        | wildCardSearchReferencePartial    |                         |              |
      | TC-08-OMV-83-Authorisations - Verify user should able to get the authorization details based on location number as single selected transaction    | 200        | locationNumberAsSingleSelect      |                         |              |
      | TC-09-OMV-83-Authorisations - Verify user should able to get the authorization details based on location number as Multi selected transaction     | 200        | locationNumberAsMultiSelect       |                         |              |
      | TC-10-OMV-83-Authorisations - Verify user should able to get the authorization details based on location name as single selected transaction      | 200        | locationNameAsSingleSelect        |                         |              |
      | TC-11-OMV-83-Authorisations - Verify user should able to get the authorization details based on location name as multi selected transaction       | 200        | locationNameAsMultiSelect         |                         |              |
      | TC-12-OMV-83-Authorisations - Verify user should able to get the authorization details based on cost centre field  as single selected transaction | 200        | costCentreAsSingleSelect          |                         |              |
      | TC-13-OMV-83-Authorisations - Verify user should able to get the authorization details based on cost centre as multi selected transaction         | 200        | costCentreAsMultiSelect           |                         |              |
      | TC-14-OMV-83-Authorisations - Verify user should able to get the authorization details based on customer number as multi selected transaction     | 200        | customerNumberAsMultiSelect       |                         |              |
      | TC-15-OMV-83-Authorisations - Verify user should able to get the authorization details based on driver name search                                | 200        | driverNameSearch                  |                         |              |
      | TC-16-OMV-83-Authorisations - Verify user should able to get the authorization details based on driver name field as partial search               | 200        | driverNameAsPartialSearch         |                         |              |
      | TC-17-OMV-83-Authorisations - Verify user should able to get the authorization details based on license plate search                              | 200        | licensePlateSearch                |                         |              |
      | TC-18-OMV-83-Authorisations - Verify user should able to get the authorization details based on license plate as partial search                   | 200        | licensePlateAsPartialSearch       |                         |              |
      | TC-19-OMV-83-Authorisations - Verify user should not able to get the authorization details based on unassociatedCardNumber                        | 200        | unassociatedCardNo                |                         |              |
      | TC-20-OMV-83-Authorisations - Verify user should not able to get the authorization details based on invalid customer number                       | 400        | invalidCusNo                      | Invalid Customer Number | 4018         |
      | TC-21-OMV-83-Authorisations - Verify user should not able to get the authorization details based on invalid location number                       | 200        | invalidLocationNo                 |                         |              |

  
#  @OMVAuthorisationDetails
#  Scenario Outline: OMV-82-Transaction details API
#    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user able to view the expanded section transaction details with possible scenarios as "<possibleCombination>"
#    Then verify the response status should be <statusCode>
#    Then user validates the transaction details from response and DB with "<statusNumber>" and "<statusMessage>"
#
#    Examples:
#      | testName                                                                                               | statusCode | statusMessage           | statusNumber | possibleCombination |
#      | TC-01-OMV-82-Verify user should able to get the transaction details by passing valid transaction_oid   | 200        |                         |              | validTransOid       |
#      | TC-02-OMV-82-Verify user should able to get the transaction details by passing invalid transaction_oid | 400        | TransactionId not found | 19059        | invalidTransOid     |

#  @OMVAuthorisationExport
#  Scenario Outline: OMV-100-Authorisations Export details API
#    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user able to export authorisation details with possible scenarios as "<possibleCombination>"
#    Then verify the response status should be <statusCode>
#    Then user validates the transaction details from response and DB with "<statusNumber>" and "<statusMessage>"

#    Examples:
#      | testName                                                                                                       | statusCode | statusMessage | statusNumber | possibleCombination |
#      | TC-01-OMV-100-Authorisations Export-Verify user should able to export details by passing valid transaction_oid | 200        |               |              | validTransOid       |
#      | TC-01-OMV-100-Authorisations Export-Verify user should able to export details by passing valid transaction_oid | 200        |               |              | validTransOid       |