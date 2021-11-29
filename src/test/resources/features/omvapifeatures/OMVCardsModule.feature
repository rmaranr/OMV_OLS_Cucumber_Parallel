Feature: API - OMV Cards module

  @SearchCardsForCSR
  Scenario Outline: OMV-466 Search cards for CSR Login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to get the list of cards based on possible combination "<possibleCombination>"
    Then validate the response status code "<statusCode>"
    Then Verify the card search default view response and validate the expected details in DB with "<statusNumber>" and "<statusMessage>" based on possible combination as "<possibleCombination>"

    Examples:
      | testCaseName                                                                                   | statusCode | possibleCombination           | statusNumber | statusMessage |
      | TC-01-OMV-466- Verify user able to get the list of all cards for passing empty request payload | 200        | emptyPayload                  |              |               |
      | TC-02-OMV-466- Verify user able to search the cards with all valid inputs                      | 200        | validInputs                   |              |               |
      | TC-03-OMV-466- Verify user able to  search the cards with full card number                     | 200        | fullCardNo                    |              |               |
      | TC-04-OMV-466- Verify user able to search the cards with partial card number                   | 200        | partialCardNo                 |              |               |
      | TC-05-OMV-466- Verify user able to search the cards with cost centre filter as single input    | 200        | costCentreFilterAsSingleInput |              |               |
      | TC-06-OMV-466- Verify user able to search the cards with driver name search                    | 200        | driverNameSearch              |              |               |
      | TC-07-OMV-466- Verify user able to search the cards with driver name as partial search         | 200        | driverNamePartialSearch       |              |               |
      | TC-08-OMV-466- Verify user able to search the cards with license plate search                  | 200        | licensePlateSearch            |              |               |
      | TC-09-OMV-466- Verify user able to search the cards with license plate as partial search       | 200        | licensePlateAsPartialSearch   |              |               |
      | TC-10-OMV-466- Verify user able to search the cards with countries filter                      | 200        | countriesFilter               |              |               |
      | TC-11-OMV-466- Verify user able to search the cards with status filter as single input         | 200        | statusAsSingleInput           |              |               |
      | TC-12-OMV-466- Verify user able to search the cards with all multiple inputs                   | 200        | multipleInputs                |              |               |

  @OMVGetCardDetails
  Scenario Outline: To get the specific card details
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user able to get the card details based on the card scenario "<cardNumber>"
    Then verify the response status should be <statusCode>
    Then validate and verify card product offers from response with "<statusMessage>" and <statusNumber>

    Examples:
      | testName                                                                                   | cardNumber    | statusCode | statusMessage                | statusNumber |
      | TC-01-OLS-592-Verify user should able to get the card details for valid card number       | validCardNo   | 200        | empty                        | 0            |
      | TC-02-OLS-592-Verify user should not able to get the card details with invalid card number | invalidCardNo | 400        | Invalid card number provided | 2112         |


  @CreateCardControlProfileForCSRWithPositiveScenarios
  Scenario Outline: OMV-736 Create and edit Card control profile - Positive scenarios
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the product group restriction to order a card for specific customer
    When user gets the list of location restriction values based on selected location group
    When user gets the time-limit API based on client
    When user able to create a new card control profile with all valid inputs based on card offer and card product as "<paramCardOffer>" and "<paramCardProduct>" and "<paramCusNo>" and "<paramDescription>"
#    Then validate the response status code "<statusCode>"
#    Then user validates the card control profile response message based on status number "<statusNumber>" and "<statusMessage>"
    When User gets the specific card control profile details based on card control profile oid and valid scenarios "<validScenarios>"
#    Then Validates the specific card control details from response and DB based on "<statusNumber>" and "<statusMessage>"
    Examples:
      | testCaseName                                                                                                       | statusCode | paramCardOffer | paramCardProduct   | paramCusNo | paramDescription        | statusNumber | statusMessage | validScenarios |
      | TC-01-OMV-736- Verify user able to create the new card control profile details with all valid inputs               | 200        | validCardOffer | validCardProduct   | validCusNo | validDescription        |              |               | validCusNo     |
      | TC-02-OMV-736- Verify user able to create the new card control profile details with card offer                     | 200        | validCardOffer | cardProductAsEmpty | validCusNo | validDescription        |              |               | validCusNo     |
      | TC-03-OMV-736- Verify user able to create the new card control profile details with card offer and new description | 200        | validCardOffer | validCardProduct   | validCusNo | newGeneratedDescription |              |               | validCusNo     |


  @CreateCardControlProfileForCSRWithNegativeScenarios
  Scenario Outline: OMV-736 Create and edit Card control profile - Negative scenarios
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to create a new card control profile with all invalid inputs for negative scenarios based on card offer and card product and customer number as "<paramCardOffer>" and "<paramCardProduct>" and "<paramCusNo>" and "<paramDescription>"
    Then validate the response status code "<statusCode>"
    Then user validates the card control profile response message based on status number "<statusNumber>" and "<statusMessage>"

    Examples:
      | testCaseName                                                                                                   | statusCode | paramCardOffer   | paramCardProduct   | paramCusNo            | paramDescription | statusNumber | statusMessage                              |
      | TC-04-OMV-736- Verify user should not create the new card control profile details with invalid card offer      | 400        | invalidCardOffer | validCardProduct   | validDescription      | validDescription | 98001        | Card Offer is not defined for the Customer |
      | TC-05-OMV-736- Verify user should not create the new card control profile details with invalid card product    | 400        | validCardOffer   | invalidCardProduct | validDescription      | validDescription | 6            | Invalid entry                              |
      | TC-06-OMV-736- Verify user should not create the new card control profile details with invalid customer number | 400        | validCardOffer   | validCardProduct   | invalidCustomerNumber | validDescription | 18018        | Invalid Account Number                     |

  @OMVBulkStatusChangeAndLookup
  Scenario Outline: OMV- 869 Bulk cards lookup and Bulk status change API
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user able to get the valid changes status for bulk status change lookup API based on card status "<cardStatus>"
    Then validate the response status code "<statusCodeForLookUp>"
    Then user able to update the status for multiple cards with param as card number based on bulk card status lookup API "<ScenarioType>"
    Then validate the expected response for bulk cards and error response statusNumber "<statusNumber>" and statusMessage "<statusMessage>"

    Examples:
      | testName                                                                                                                  | statusCodeForLookUp | statusNumber | statusMessage                               | cardStatus                          | ScenarioType                   |
      | TC-01-OMV-869-Verify user able to get the possible combinations and changing the status for active cards                  | 200                 |              |                                             | validCardWithActiveStatus           | associatedCardNumberAndCusNo   |
      | TC-02-OMV-869-Verify user able to get the possible combinations and changing the status for stolen and damaged cards      | 204                 |              |                                             | validCardWithStolenAndDamagedStatus | associatedCardNumberAndCusNo   |
      | TC-03-OMV-869-Verify user able to get the possible combinations and changing the status for lost with active cards        | 204                 |              |                                             | validCardWithLostAndActiveStatus    | associatedCardNumberAndCusNo   |
      | TC-04-OMV-869-Verify user able to get the possible combinations and changing the status for unassociated card number      | 200                 | 2152         | Action not applicable for the selected card | validCardWithActiveStatus           | unassociatedCardNumberAndCusNo |
      | TC-05-OMV-869-Verify user able to get the possible combinations and changing the status for temporary block request cards | 200                 |              |                                             | validCardWithTemporaryBlockStatus   | associatedCardNumberAndCusNo   |

  @ChangeCardStatusAndReplaceCardForCSR
  Scenario Outline: OLS-1084-Change card status and replace card
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user gets the list of card status based on valid changes API "<scenarioTypeForLookup>"
#    Then validate the response status code "<statusCodeForLookUp>"
    Then user can change the card status and replace actions based on possible scenarios "<scenarioType>"
#    Then validate the response status code "<statusCodeForChangeStatus>"
    Then validate the response status number "<statusNumber>" and  status message "<statusMessage>" for change card status

    Examples:
      | testName                                                                                                           | statusCodeForLookUp | scenarioTypeForLookup | scenarioType                         | statusNumber | statusMessage                                | statusCodeForChangeStatus |
      | TC-01-OMV-1084- Verify user should able to change the status for valid scenario                                    | 200                 | validStatus           | validCardNumber                      |              | 200                                          |                           |
      | TC-02-OMV-1084- Verify user should not able to change the status for unassociated card number                      | 200                 | validStatus           | unassociatedCardNoWithCusNoAsInvalid | 97103        | API User is unauthorized to access this card | 401                       |
      | TC-03-OMV-1084- Verify user should not able to change the invalid status for associated card number                | 204                 | invalidStatus         | validCardWithInvalidStatus           | 6            | Invalid entry                                | 400                       |
      | TC-04-OMV-1084- Verify user should able to changing the status based on future date and validate expected response | 200                 | validStatus           | validCardNumberWithFutureDate        |              |                                              | 200                       |


  @ListOfCardControlProfileForCSR
  Scenario Outline: OMV-734  Card control profiles search
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of card control profiles based on customer number based on possible scenarios "<possibleScenarios>"
    Then validate the response status code "<statusCode>"
    Then verify the card control profiles response message from response and DB based on "<statusNumber>" and "<statusMessage>"

    Examples:
      | testCaseName                                                                                                                                | statusCode | possibleScenarios                                 | statusNumber | statusMessage           |
      | TC-01-OMV-734- Verify user should able to get the list of card control profiles based on valid customer number, card offer and card product | 200        | validCusNoWithCardOfferAndCardProduct             |              |                         |
      | TC-02-OMV-734- Verify user should able to get the list of card control profiles based on valid card offer                                   | 200        | validCusNoWithCardOffer                           |              |                         |
      | TC-03-OMV-734- Verify user should not able to get the list of card control profiles for unasssociated card offer                            | 400        | validCusNoWithUnassociatedCardOffer               | 6            | Invalid entry           |
      | TC-04-OMV-734- Verify user should not able to get the list of card control profiles for unassociated card product                           | 400        | validCusNoWithCardOfferAndUnassociatedCardProduct | 2111         | Invalid card product    |
      | TC-05-OMV-734- Verify user should not able to get the list of card control profiles for invalid customer number                             | 400        | invalidCusNo                                      | 4018         | Invalid Customer Number |

  @GetCardControlProfileForCSR
  Scenario Outline: OMV-738 Get the specific Card control profile details
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of card control profiles based on customer number based on possible scenarios "<possibleScenarios>"
    When User gets the specific card control profile details based on card control profile oid and valid scenarios "<validScenarios>"
#    Then validate the response status code "<statusCode>"
    Then Validates the specific card control details from response and DB based on "<statusNumber>" and "<statusMessage>"

    Examples:
      | testCaseName                                                                                                 | statusCode | possibleScenarios                     | statusNumber | statusMessage           | validScenarios |
      | TC-01-OMV-738- Verify user should able to get the specific card control profiles for valid customer number   |    200        | validCusNoWithCardOfferAndCardProduct |              |                         | validCusNo     |
#      | TC-02-OMV-738- Verify user should able to get the specific card control profiles for invalid customer number | 400        | invalidCusNo                          | 4018         | Invalid Customer Number | invalidCusNo   |

  @ValidateProfileExist
  Scenario Outline: OMV-385 Validate the private profile API if the account does not support to create exiting profile
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When User can validates the private profile is exist or not based on possible scenarios "<possibleScenarios>"
    Then validate the private profile is existing or not response message

    Examples:
      | testCaseName                                                                                                                                                       | possibleScenarios                                          |
      | TC-01-OMV-385- Verify user should validate the card control private profile is exist or not with valid customer number,card offer and card product                 | validCusNoWithCardOfferAndCardProduct                      |
      | TC-02-OMV-385- Verify user should validate the card control private profile is exist or not with customer number and card offer                                    | validCusNoWithCardOffer                                    |
      | TC-03-OMV-385- Verify user should validate the card control private profile is exist or not with customer number, card offer, card product and profile description | validCusNoWithCardOfferAndCardProductAndProfileDescription |


  @cardsVelocityValues
  Scenario Outline: OMV-870 To get all the velocity type values
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of velocity type values
    Then validate the response status code "<statusCode>"
    Examples:
      | testCaseName                                                                      | statusCode |
      | Tc-01-OMV-870- Verify user able to get the list of velocity type values for cards | 200        |


  @ListOfReissueCardsForCSR
  Scenario Outline: OMV-1461 List of reissue cards for CSR country Login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When User able to get the list  of reissue cards based on customer no "<CustomerNo>"
    Then validate the response status code "<statusCode>"
   # Then Verify the "<statusMessage>" and "<statusNumber>" from response

    Examples:
      | testCaseName                                                                                             | statusCode | CustomerNo | statusNumber | statusMessage                           |
      | TC-01-OMV-1641- Verify user able to get the list of all reissue cards by passing valid customer number   | 200        | valid      |              | No cards are available for auto-reissue |
      | TC-02-OMV-1641- Verify user able to get the list of all reissue cards by passing invalid customer number | 400        | invalid    |              | Invalid Account Number                  |


  @cardAddressAPI
  Scenario Outline: OMV-871 To get the Address for customer number
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of addresses based on customer number based on "<customerNumberScenario>"
    Then validate the response status code "<statusCode>"
    Examples:
      | testCaseName                                                                               | statusCode | customerNumberScenario |
      | TC-01-OMV-871- Verify user able to get the list of address for valid customer number       | 200        | validCustomer          |
      | TC-02-OMV-871- Verify user should not able to get the list of address for invalid customer | 400        | invalidCustomer        |


  @MailIndicatorAPI
  Scenario Outline: OMV-801 To get the list of mail indicator values based on client level and customer level
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of mail indicators based on access level as "<accessLevel>"
#    Then validate the response status code "<statusCode>"
    Examples:
      | testCaseName                                                                               | statusCode | accessLevel   |
      | TC-01-OMV-801- Verify user able to get the list of mail indicators based on client level   | 200        | clientLevel   |
      | TC-02-OMV-801- Verify user able to get the list of mail indicators based on customer level | 200        | customerLevel |

  @ProductRestrictionAPI
  Scenario Outline: To get the product restriction
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the product group restriction to order a card for specific customer
    Then validate the response status code "<statusCode>"
    Then Validate the product group restriction values from response and database

    Examples:
      | testCaseName                                                   | statusCode |
      | TC-01-OMV-801- Verify user able to get the product restriction | 200        |

  @LocationRestrictionAPI
  Scenario Outline: OMV-2435 To get the location restriction type
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of location restriction values based on selected location group
    Then validate the response status code "<statusCode>"

    Examples:
      | testCaseName                                                                                     | statusCode |
      | TC-01-OMV-2435- Verify user able to get the location restriction based on selected location type | 200        |

  @TimeLimitAPI
  Scenario Outline: OMV-2435 To get the time limit description
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the time-limit API based on client
    Then validate the response status code "<statusCode>"
    Examples:
      | testCaseName                                                       | statusCode |
      | TC-01-OMV-2435- Verify user able to get the time limit description | 200        |


  @OMVOrderCard
  Scenario Outline: OMV-800-Order card details with different scenarios(Both positive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user gets the list of card control profiles based on customer number based on possible scenarios "<possibleScenarios>"
    When User gets the specific card control profile details based on card control profile oid and valid scenarios "<validScenarios>"
    When user gets the list of location restriction values based on selected location group
    When user gets the time-limit API based on client
    Then user order a card with the all valid details and validate with "<customerNumber>" and "<expiryDate>" and "<cardCtrlProfiles>"
    Then verify the response status should be <statusCode>
    Then user hit the get card API for validation
#    Then validate and verify card details from response with "<statusMessage>" and <statusNumber>
#    Then validate and verify card driver details from response with "<statusMessage>" and <statusNumber>
#    Then validate and verify card vehicle details from response with "<statusMessage>" and <statusNumber>
#    Then validate and verify card address from response with "<statusMessage>" and <statusNumber>
#    Then validate and verify card product offers from response with "<statusMessage>" and <statusNumber>
#    Then validate and verify card control profile from response with "<cardCtrlProfiles>" and "<statusMessage>" and <statusNumber>
    Examples:
      | testName                                                                                                                                                     | customerNumber | expiryDate | cardCtrlProfiles | statusCode | statusMessage | statusNumber |possibleScenarios|validScenarios|
      | TC-01-OMV-800-Verify user should able to order the card when expire date is greater than the today's client processing date and less than cards expires date | validCustomerNo | valid      | existing         | 200        | empty         | 0            |validCusNoWithCardOffer|validCusNo|
#      | TC-02-OMV-800-Verify user should able to order the card to pass the card product based on card offer with existing card control profile                       | associatedUser | valid           | existing        | 200        | empty             | 0            |validCusNoWithCardOffer|validCusNo|
#      | TC-03-OMV-800-Verify user should able to order the card to pass the card product based on card offer with new card control profile                            | associatedUser | valid           | new              | 200        | empty             | 0            |validCusNoWithCardOffer|validCusNo|
#      | TC-04-OMV-800-Verify user should not able to order the card when expire date is less than the client processing date and validate the expected error response | associatedUser | MoreThanProduct | existing        | 400        | Validation failed | 12           |validCusNoWithCardOffer|validCusNo|
#      | TC-05-OMV-800-Verify user should not able to order the card when the expire date is more than card expiry date and validate the expected error response       | associatedUser | invalid         | existing        | 400        | Validation failed | 12           |validCusNoWithCardOffer|validCusNo|





