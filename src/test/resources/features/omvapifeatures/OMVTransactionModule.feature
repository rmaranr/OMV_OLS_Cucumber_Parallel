@OMVTransactionsModule
Feature: API - Transaction search and Transaction details

@OMVSearchTransactions
  Scenario Outline: OMV-81-Transaction search API
  Given User gets the base url EndPoint and passing "<testName>" to create test
  When user gets the list of transactions based on filters and searches "<filterCombination>"
  Then verify the response status should be <statusCode>
  Then user validates the transaction default view details with "<statusMessage>" and "<statusNumber>" and based on filter search as "<filterCombination>"
  Examples:
  | testName                                                                                                                         | statusCode | filterCombination            | statusMessage           | statusNumber |
  | TC-01-OMV-81- Verify user should able to get the transaction details based on all filters and search fields                      | 200        | allFiltersAndSearches        |                         |              |
  | TC-02-OMV-81- Verify user should able to get the transaction details based on customer number                                    | 200        | customerNumberAsSingleSelect |                         |              |
  | TC-03-OMV-81- Verify user should able to get the transaction details based on combination of customer number and card number     | 200        | customerNoWithCardNo         |                         |              |
  | TC-04-OMV-81-  Verify user should able to get the transaction details based on full card  number search                          | 200        | fullCardNumberSearch         |                         |              |
  | TC-05-OMV-81-  Verify user should able to get the transaction details based on last 5 digit card number search                   | 200        | last5DigitCardNumberSearch   |                         |              |
  | TC-06-OMV-81-  Verify user should able to get the transaction details based on reference search                                  | 200        | fullReferenceSearch          |                         |              |
  | TC-07-OMV-81-  Verify user should able to get the transaction details based on wildcard search for reference field               | 200        | wildCardSearchReference      |                         |              |
  | TC-08-OMV-81-  Verify user should able to get the transaction details based on location number as single selected transaction    | 200        | locationNumberAsSingleSelect |                         |              |
  | TC-09-OMV-81-  Verify user should able to get the transaction details based on location number as Multi selected transaction     | 200        | locationNumberAsMultiSelect  |                         |              |
  | TC-10-OMV-81-  Verify user should able to get the transaction details based on location name as single selected transaction      | 200        | locationNameAsSingleSelect   |                         |              |
  | TC-11-OMV-81-  Verify user should able to get the transaction details based on location name as multi selected transaction       | 200        | locationNameAsMultiSelect    |                         |              |
  | TC-12-OMV-81-  Verify user should able to get the transaction details based on cost centre field  as single selected transaction | 200        | costCentreAsSingleSelect     |                         |              |
  | TC-13-OMV-81-  Verify user should able to get the transaction details based on cost centre as multi selected transaction         | 200        | costCentreAsMultiSelect      |                         |              |
  | TC-14-OMV-81-  Verify user should able to get the transaction details based on customer number as multi selected transaction     | 200        | customerNumberAsMultiSelect  |                         |              |
  | TC-15-OMV-81-  Verify user should able to get the transaction details based on driver name search                                | 200        | driverNameSearch             |                         |              |
  | TC-16-OMV-81-  Verify user should able to get the transaction details based on driver name field as partial search               | 200        | driverNameAsPartialSearch    |                         |              |
  | TC-17-OMV-81-  Verify user should able to get the transaction details based on license plate search                              | 200        | licensePlateSearch           |                         |              |
  | TC-18-OMV-81-  Verify user should able to get the transaction details based on license plate as partial search                   | 200        | licensePlateAsPartialSearch  |                         |              |
  | TC-19-OMV-81-  Verify user should not  able to get the transaction details based on unassociatedCardNumber                       | 200        | unassociatedCardNo           |                         |              |
  | TC-20-OMV-81-  Verify user should not able to get the transaction details based on invalid customer number                       | 400        | invalidCusNo                 | Invalid Customer Number | 4018         |
  | TC-21-OMV-81-  Verify user should not able to get the transaction details based on invalid location number                       | 200        | invalidLocationNo            |                         |              |



  @OMVTransactionsDetails
  Scenario Outline: OMV-82-Transaction details API
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user able to view the expanded section transaction details with possible scenarios as "<possibleCombination>"
    Then verify the response status should be <statusCode>
    Then user validates the transaction details from response and DB with "<statusNumber>" and "<statusMessage>"

    Examples:
      | testName                                                                                               | statusCode | statusMessage           | statusNumber | possibleCombination |
      | TC-01-OMV-82-Verify user should able to get the transaction details by passing valid transaction_oid   | 200        |                         |              | validTransOid       |
      | TC-02-OMV-82-Verify user should able to get the transaction details by passing invalid transaction_oid | 400        | TransactionId not found | 19059        | invalidTransOid     |

#  @DisputeTransactionAPI
#  Scenario Outline: OMV-515-Dispute Transaction API
#    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user able to get the list of dispute reasons to raise the dispute status
#    Then Validate the dispute reason response
#    Then user able to post the dispute status based on possible combination "<possibleCombination>"
#    Then verify the response status should be <statusCode>
#
#    Examples:
#      | testName                                                                                   | statusCode | statusMessage                            | statusNumber | possibleCombination              |
#      | TC-01-OMV-515-Verify user can able to raise the dispute status with valid dispute reason   | 200        |                                          |              | validReasonForDisputeTransaction |
   #   | TC-02-OMV-515-Verify user cannot raise the dispute status for existing dispute transaction | 400        | Error occurred: only one record expected | 137          | existingDisputeTransaction       |

