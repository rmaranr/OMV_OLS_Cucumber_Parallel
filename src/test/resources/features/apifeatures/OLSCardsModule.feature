@OLSCardsModule
Feature: Customer Cards Module

  @OLSSearchCards
  Scenario Outline: Search cards with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user post request body to get cards and validate with "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                   | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-787-Verify user should able to get the card details for an associated account    | associatedUser   | 200        | empty                                            | 0            |
      | TC-02-OLS-787-Verify user should not able to get the card details for an invalid card      | unAssociatedUser | 401        | API User is unauthorized to access this customer | 97102        |
      | TC-03-OLS-787-Verify user should not able to get the card details for unassociated account | invalidCus       | 400        | Invalid Customer Number                          | 4018         |

  @OLSGetCardDetails
  Scenario Outline: Get card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user get the card details and validate with "<customerNumber>" and "<cardNumber>" and "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>
    Then validate and verify card product offers from response with "<statusMessage>" and <statusNumber>

    Examples:
      | testName                                                                                   | customerNumber   | cardNumber     | statusCode | statusMessage                                | statusNumber |
      | TC-01-OLS-592-Verify user should able to get the card details for an associated account    | associatedUser   | associated     | 200        | empty                                        | 0            |
      | TC-02-OLS-592-Verify user should not able to get the card details for unassociated account | unAssociatedUser | associatedCard | 401        | API User is unauthorized to access this card | 97103        |
      | TC-03-OLS-592-Verify user should not able to get the card details for an invalid card      | associatedUser   | invalidCard    | 400        | Invalid card number provided                 | 2112         |

  @OLSDefaultCardCtrlProf
  Scenario Outline: Get card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user hit default card ctrl profile API and validate with "<customerNumber>" and "<cardOffer>" and "<cardProduct>" and <statusCode> as expected values
    Then verify the response status should be <statusCode>
    Then validate and verify Cards API response with "<statusMessage>" and <statusNumber>

    Examples:
      | testName                                                                                                                                                                                                                          | customerNumber   | cardOffer | cardProduct | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-802 - Verify user should able to get default card control profile details based on card offer, TC-02-OLS-802 - Verify user should able to get default card control profile details based on card offer and cardProducts | associatedUser   | valid     | valid       | 200        | empty                                            | 0            |
      | TC-03-OLS-802 - Verify user should get error response when passing unassociated customerNo                                                                                                                                        | unAssociatedUser | valid     | valid       | 401        | API User is unauthorized to access this customer | 97102        |
      | TC-04-OLS-802 - Verify user should get error response when passing invalid customerNo                                                                                                                                             | invalidCus       | valid     | valid       | 400        | Invalid Customer Number                          | 4018         |
      | TC-05-OLS-802 - Verify user should get error response when passing invalid cardoffer                                                                                                                                              | associatedUser   | invalid   | valid       | 400        | Invalid entry                                    | 6            |
      | TC-06-OLS-802 - Verify user should get error response when passing invalid cardProduct                                                                                                                                            | associatedUser   | valid     | invalid     | 400        | Invalid card product                             | 2111         |

  @OLSOrderCardDetails
  Scenario Outline: Get card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user order a card with the card details and validate with "<customerNumber>" and "<expiryDate>" and "<cardCtrlProfiles>" and "<statusMessage>" and <statusNumber> and <statusCode> as expected values
    Then verify the response status should be <statusCode>
    Then user hit the get card API for validation
    Then validate and verify card details from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card driver details from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card vehicle details from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card address from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card product offers from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card control profile from response with "<cardCtrlProfiles>" and "<statusMessage>" and <statusNumber>

    Examples:
      | testName                                                                                                                                                      | customerNumber | expiryDate      | cardCtrlProfiles | statusCode | statusMessage     | statusNumber |
      | TC-01-OLS-601-Verify user should able to order the card when expire date is greater than the today's client processing date and less than cards expires date  | associatedUser | valid           | exsisting        | 200        | empty             | 0            |
      | TC-02-OLS-601-Verify user should able to order the card to pass the card product based on card offer with existing card control profile                       | associatedUser | valid           | exsisting        | 200        | empty             | 0            |
      | TC-03-OLS-601-Verify user should able to order the card to pass the card product based on card offer with new card control profile                            | associatedUser | valid           | new              | 200        | empty             | 0            |
      | TC-04-OLS-601-Verify user should not able to order the card when expire date is less than the client processing date and validate the expected error response | associatedUser | MoreThanProduct | exsisting        | 400        | Validation failed | 12           |
      | TC-05-OLS-601-Verify user should not able to order the card when the expire date is more than card expiry date and validate the expected error response       | associatedUser | invalid         | exsisting        | 400        | Validation failed | 12           |

  @OLSProductRestriction
  Scenario Outline: Get product restriction code with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user get the product restriction code by post with card details and validate with "<customerNumber>" and "<cardOffers>" and "<products>" and "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                            | customerNumber | cardOffers | products  | statusCode | statusMessage | statusNumber |
      | TC-01-OLS-535-Verify user should able to get the default profiles to order the card | associatedUser | valid      | exsisting | 200        | empty         | 0            |
#      | TC-02-OLS-535-Verify user should able to get the default profiles to order the card | associatedUser | valid      | exsisting | 200        | empty         | 0            |
#      | TC-03-OLS-535-Verify user should able to get the default profiles to order the card | associatedUser | valid      | exsisting | 200        | empty         | 0            |
#      | TC-04-OLS-535-Verify user should able to get the default profiles to order the card | associatedUser | valid      | exsisting | 200        | empty         | 0            |
#      | TC-05-OLS-535-Verify user should able to get the default profiles to order the card | associatedUser | valid      | exsisting | 200        | empty         | 0            |

  @OLSEditCardDetails
  Scenario Outline: Edit card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user put the edit card request to update the card details of specific card and validate with "<customerNumber>" and "<cardNumber>" and "<newCard>" and "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>
    Then user hit the get card API for validation
    Then validate and verify card details from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card driver details from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card vehicle details from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card address from response with "<statusMessage>" and <statusNumber>
    Then validate and verify card product offers from response with "<statusMessage>" and <statusNumber>
#    Then validate and verify card details from response with "<customerNumber>" and "<statusMessage>" and <statusNumber>

    Examples:
      | testName                                                                                                                                                                                                                                                                                                                            | customerNumber | cardNumber     | newCard | statusCode | statusMessage | statusNumber |
      | TC-01-OLS-595 - Verify user should able to add card contact address and contact name fields,TC-02-OLS-595 - Verify user should able to update driver details, TC-03-OLS-595 - Verify user should able to update vehicle details, TC-04-OLS-595 - Verify new card will be generated-when the user updated the embossing name of card | associatedUser | associatedCard | new     | 200        | empty         | 0            |

  @OLSEditCardControlProfiles
  Scenario Outline: Edit card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user put the edit card request to update the card control profiles of specific card and validate with "<customerNumber>" and "<cardNumber>" and "<cardCtrlProfiles>" and "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>
    Then user hit the get card API for validation
    Then validate and verify card control profile from response with "<cardNumber>" and "<statusMessage>" and <statusNumber>
#    Then validate and verify card driver details from response with "<statusMessage>" and <statusNumber>

    Examples:
      | testName                                                                                                                              | customerNumber | cardNumber  | statusCode | statusMessage | statusNumber | cardCtrlProfiles |
      | TC-04-OLS-595 - Verify user should able to update some values in cardControlProfiles, for a card having default card control profiles | associatedUser | defaultProf | 200        | empty         | 0            | new              |
      | TC-05-OLS-595 - Verify user should able to update some values in cardControlProfiles, for a card having private card control profiles | associatedUser | privateProf | 200        | empty         | 0            | exsisting        |

  @OLSCardsChangePin
  Scenario Outline: Edit card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<pinOffset>" and "<confirmPinOffset>" as parameters to change card pin
    Then verify the response status should be <statusCode>
    Then user put the change pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                                     | customerNumber   | cardNumber       | pinOffset | confirmPinOffset | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-598 - Verify user should able to create pin for a selected card with all valid details             | associatedUser   | associatedCard   | 1324      | 1324             | 200        | empty                                            | 0            |
      | TC-02-OLS-598 - Verify user should getting error response  while passing invalid account number              | invalidCus       | associatedCard   | 1324      | 1324             | 400        | Invalid Customer Number                          | 4018         |
      | TC-03-OLS-598 - Verify user should getting error response  while passing unassociated account number         | unAssociatedUser | associatedCard   | 1324      | 1324             | 401        | API User is unauthorized to access this customer | 97102        |
      | TC-04-OLS-598 - Verify user should getting error response  while passing invalid card number                 | associatedUser   | invalidCard      | 1324      | 1324             | 401        | API User is unauthorized to access this card     | 97103        |
      | TC-05-OLS-598 - Verify user should getting error response  while passing unassociated card number            | associatedUser   | unAssociatedCard | 1324      | 1324             | 401        | API User is unauthorized to access this card     | 97103        |
      | TC-06-OLS-598 - Verify user should getting error response  while passing empty value in New PIN field        | associatedUser   | associatedCard   |           | 1324             | 400        | Validation failed                                | 12           |
      | TC-07-OLS-598 - Verify user should getting error response  while passing empty value in confirm PIN field    | associatedUser   | associatedCard   | 1324      |                  | 400        | Validation failed                                | 12           |
      | TC-08-OLS-598 - Verify user should getting error response  when new pin and confirm pin field does not match | associatedUser   | associatedCard   | 1234      | 7896             | 400        | Validation failed                                | 12           |

#      | TC-05-OLS-598 - Verify user should getting error response  while passing unassociated card number            | associatedUser   | unAssociatedCard | 1324      | 1324             | 400        | Account is unauthorized to use this card         | 97109        |


  @OLSCardsReissuePin
  Scenario Outline: Edit card details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<cardContact>" as parameters to "ReissuePinEndpoint" card pin
    Then verify the response status should be <statusCode>
    Then user put the reissue pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                              | customerNumber   | cardNumber       | cardContact    | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-599 - Verify user should able to Reissue Pin for a selected card with new card contact      | associatedUser   | associatedCard   | cardContact    | 200        | empty                                            | 0            |
      | TC-02-OLS-599 - Verify user should able to Reissue Pin for a selected card with existing card contact | associatedUser   | associatedCard   | primaryContact | 200        | empty                                            | 0            |
      | TC-03-OLS-599 - Verify user should getting error response while passing invalid account number        | invalidCus       | associatedCard   | primaryContact | 400        | Invalid Customer Number                          | 4018         |
      | TC-04-OLS-599 - Verify user should getting error response while passing unassociated account number   | unAssociatedUser | associatedCard   | primaryContact | 401        | API User is unauthorized to access this customer | 97102        |
      | TC-05-OLS-599 - Verify user should getting error response while passing invalid card number           | associatedUser   | invalidCard      | primaryContact | 401        | API User is unauthorized to access this card     | 97103        |
      | TC-06-OLS-599 - Verify user should getting error response while passing unassociated card number      | associatedUser   | unAssociatedCard | primaryContact | 401        | API User is unauthorized to access this card     | 97103        |

#      | TC-06-OLS-599 - Verify user should getting error response while passing unassociated card number         | associatedUser   | unAssociatedCard | primaryContact | 400        | Account is unauthorized to use this card         | 97109        |

  @ChangeCardStatusAndReplaceCard
  Scenario Outline: OLS-595-Change card status and replace card
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user able to get the valid changes based on cards current status
    Then validate the response status code "<statusCodeForLookUp>"
    Then user can change the status with customerNumber as param "<customerNumber>" and based on changeCardStatus scenarios "<scenarioType>"
    Then validate the response status code "<statusCodeForChangeStatus>"
    Then valdiate the response status number "<statusNumber>" and  status message "<statusMessage>" for change card status

    Examples:
      | testName                                                                                                          | statusCodeForLookUp | customerNumber | scenarioType                              | statusNumber | statusMessage                                | statusCodeForChangeStatus |
      | TC-01-OLS-596- Verify user should able to change the status from card status to card status                       | 200                 | associatedUser | validCardWithAssociatedCusNo              |              |                                              | 200                       |
      | TC-02-OLS-596- Verify user should not able to change the status for unassociated customer number                  | 200                 | associatedUser | validCardWithUnassociatedCusNoAsInvalid   | 18018        | Invalid Account Number                       | 400                       |
      | TC-03-OLS-596- Verify user should not able to change the status for unassociated card number                      | 200                 | associatedUser | unassociatedCardNoWithCusNoAsInvalid      | 97103        | API User is unauthorized to access this card | 401                       |
      | TC-04-OLS-596- Verify user should not able to change the invalid status for associated card number                | 200                 | associatedUser | validCardWithInvalidStatus                | 6            | Invalid entry                                | 400                       |
      | TC-05-OLS-596- Verify user should able to changing the status based on future date and validate expected response | 200                 | associatedUser | validCardWithAssociatedCusNoAndFutureDate |              |                                              | 200                       |

  @OLSSearchCardsWithActionItems
  Scenario Outline: OLS-594-Search cards with action items based on cards current status
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user post the request for list of cards based on customer number
    Then validate the response status code "<statusCode>"
    Then validate the response for search cards and field values with statusNumber as "<statusNumber>" and statusMesage as "<statusMessage>" as expected

    Examples:
      | testName                                                                                | customerNumber | statusCode | statusNumber | statusMessage |
      | TC-01-OLS-594-Verify user should able to get the card details for an associated account | associatedUser | 200        |              |               |

  @OLSBulkStatusChangeAndLookup
  Scenario Outline: OLS-1353 and OLS-1354-Bulk cards lookup and Bulk status change API
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user able to get the valid changes status for bulk status change lookup API based on card status "<cardStatus>"
    Then validate the response status code "<statusCodeForLookUp>"
    Then user can able to change the status for generated new card number
    Then user able to change the status for bulk cards with param as card number and customer number based on bulk card status lookup API "<ScenarioType>"
    Then validate the expected response for bulk cards and error response statusNumber "<statusNumber>" and statusMessage "<statusMessage>"

    Examples:
      | testName                                                                                                                                       | statusCodeForLookUp | statusNumber | statusMessage                                   | cardStatus                           | ScenarioType                             |
      | TC-01-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for active cards                         | 200                 |              |                                                 | validCardWithActiveStatus            | associatedCardNumberAndCusNo             |
      | TC-02-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for stolen and damaged cards             | 204                 |              |                                                 | validCardWithStolenAndDamagedStatus  | associatedCardNumberAndCusNo             |
      | TC-03-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for requested not issue and active cards | 200                 |              |                                                 | validCardWithRequestedNotIssueStatus | associatedCardNumberAndCusNo             |
      | TC-04-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for lost with active cards               | 204                 |              |                                                 | validCardWithLostAndActiveStatus     | associatedCardNumberAndCusNo             |
      | TC-05-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for unassociated card number             | 200                 | 97110        | API User is unauthorized to access this card(s) | validCardWithActiveStatus            | unassociatedCardNumberAndCusNo           |
      | TC-06-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for unassociated customer number         | 200                 | 18018        | Invalid Account Number                          | validCardWithActiveStatus            | associatedCardNumberAndUnassociatedCusNo |
      | TC-07-OLS-1353 and OLS-1354-Verify user able to get the possible combinations and changing the status for temporary block request cards        | 200                 |              |                                                 | validCardWithTemporaryBlockStatus    | associatedCardNumberAndCusNo             |


  @OLSCreategroupPin
  Scenario Outline: OLS-100-Create Group Pin
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user post the request for create group pin for the selected customer number"<customerNumber>"and"<PinName>"as parameter
    Then validate the response status code "<statusCode>"
    Then validate and verify group pin name
#    Then validate the response for search cards and field values with statusNumber as "<statusNumber>" and statusMesage as "<statusMessage>" as expected

    Examples:
      | testName                                                                                | customerNumber | PinName    | statusCode | statusNumber | statusMessage |
      | TC-01-OLS-100-Verify user should able to create group pin for an associated account     | associatedUser | New        |200         |              |               |
      | TC-02-OLS-101-Verify user should not able to create group pin for existing pin name     | associatedUser | Exist      |400         |              |               |
