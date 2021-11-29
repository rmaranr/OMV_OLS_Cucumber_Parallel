@OMVCardsPinModule
Feature: API - Cards Pin Module

  @OMVCardsResetPin
  Scenario Outline: OMV reset pin with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<pinContact>" and "<type>" and "<pinOffset>" and "<confirmPinOffset>" as parameters to "ResetPin" API card pin
    Then verify the response status should be <statusCode>
    Then user put the reissue pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                                                               | type | customerNumber | cardNumber     | pinContact     | statusCode | statusMessage                               | statusNumber |
      | TC-01-OLS-742-OMVCardResetPin - Verify user should able to reset pin for a selected card with new card contact without customerNo      | CSR  | associatedUser | associatedCard | pinContact     | 200        | empty                                       | 0            |
      | TC-02-OLS-742-OMVCardResetPin - Verify user should able to reset pin for a selected card with existing card contact without customerNo | CSR  | associatedUser | associatedCard | primaryContact | 200        | empty                                       | 0            |
      | TC-03-OLS-742-OMVCardResetPin - Verify user should able to reset pin for a selected card with new card contact                         | OLS  | associatedUser | associatedCard | pinContact     | 200        | empty                                       | 0            |
      | TC-04-OLS-742-OMVCardResetPin - Verify user should able to reset pin for a selected card with existing card contact                    | OLS  | associatedUser | associatedCard | primaryContact | 200        | empty                                       | 0            |
      | TC-05-OLS-742-OMVCardResetPin - Verify user unable to reset pin while passing invalid account number                                   | OLS  | invalidCus     | associatedCard | primaryContact | 400        | Invalid Customer Number                     | 4018         |
      | TC-06-OLS-742-OMVCardResetPin - Verify user unable to reset pin while passing invalid card number                                      | OLS  | OMVValidCus    | invalidCard    | primaryContact | 400        | Invalid card number provided                | 2112         |
      | TC-07-OLS-742-OMVCardResetPin - Verify user unable to reset pin while passing not applicable card number                               | OLS  | associatedUser | notApplicable  | primaryContact | 400        | Action not applicable for the selected card | 2152         |


  @OMVCardsReissuePin
  Scenario Outline: OMV reissue pin with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<pinContact>" and "<type>" and "<pinOffset>" and "<confirmPinOffset>" as parameters to "ReissuePin" API card pin
    Then verify the response status should be <statusCode>
    Then user put the reissue pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                                                                   | type | customerNumber | cardNumber     | pinContact     | statusCode | statusMessage                               | statusNumber |
      | TC-01-OLS-744-OMVCardReissuePin - Verify user should able to reissue pin for a selected card with new card contact without customerNo      | CSR  | associatedUser | associatedCard | pinContact     | 200        | empty                                       | 0            |
      | TC-02-OLS-744-OMVCardReissuePin - Verify user should able to reissue pin for a selected card with existing card contact without customerNo | CSR  | associatedUser | associatedCard | primaryContact | 200        | empty                                       | 0            |
      | TC-03-OLS-744-OMVCardReissuePin - Verify user should able to reissue pin for a selected card with new card contact                         | OLS  | associatedUser | associatedCard | pinContact     | 200        | empty                                       | 0            |
      | TC-04-OLS-744-OMVCardReissuePin - Verify user should able to reissue pin for a selected card with existing card contact                    | OLS  | associatedUser | associatedCard | primaryContact | 200        | empty                                       | 0            |
      | TC-05-OLS-744-OMVCardReissuePin - Verify user unable to reissue pin while passing invalid account number                                   | OLS  | invalidCus     | associatedCard | primaryContact | 400        | Invalid Customer Number                     | 4018         |
      | TC-06-OLS-744-OMVCardReissuePin - Verify user unable to reissue pin while passing invalid card number                                      | OLS  | OMVValidCus    | invalidCard    | primaryContact | 400        | Invalid card number provided                | 2112         |
      | TC-07-OLS-744-OMVCardReissuePin - Verify user unable to reissue pin while passing not applicable card number                               | OLS  | associatedUser | notApplicable  | primaryContact | 400        | Action not applicable for the selected card | 2152         |

  @OMVCardsChangePin
  Scenario Outline: OMV change pin with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<pinContact>" and "<type>" and "<pinOffset>" and "<confirmPinOffset>" as parameters to "ChangePin" API card pin
    Then verify the response status should be <statusCode>
    Then user put the change pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                                                             | type | customerNumber | cardNumber     | pinContact     | pinOffset | confirmPinOffset | statusCode | statusMessage                               | statusNumber |
      | TC-01-OLS-740-OMVCardChangePin - Verify user should able to change pin for a selected card with all valid details without customerNo | CSR  | associatedUser | associatedCard | pinContact     | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-02-OLS-740-OMVCardChangePin - Verify user should able to change pin for a selected card with all valid details without customerNo | CSR  | associatedUser | associatedCard | primaryContact | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-03-OLS-740-OMVCardChangePin - Verify user should able to change pin for a selected card with all valid details                    | OLS  | associatedUser | associatedCard | pinContact     | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-04-OLS-740-OMVCardChangePin - Verify user should able to change pin for a selected card with all valid details                    | OLS  | associatedUser | associatedCard | primaryContact | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-05-OLS-740-OMVCardChangePin - Verify user unable to change pin while passing invalid account number                               | OLS  | invalidCus     | associatedCard | primaryContact | 1324      | 1324             | 400        | Invalid Customer Number                     | 4018         |
      | TC-06-OLS-740-OMVCardChangePin - Verify user unable to change pin while passing invalid card number                                  | OLS  | associatedUser | invalidCard    | primaryContact | 1324      | 1324             | 400        | Invalid card number provided                | 2112         |
      | TC-07-OLS-740-OMVCardChangePin - Verify user unable to change pin while passing empty value in New PIN field                         | OLS  | associatedUser | associatedCard | primaryContact |           | 1324             | 400        | Validation failed                           | 12           |
      | TC-08-OLS-740-OMVCardChangePin - Verify user unable to change pin while passing empty value in confirm PIN field                     | OLS  | associatedUser | associatedCard | primaryContact | 1324      |                  | 400        | Validation failed                           | 12           |
      | TC-09-OLS-740-OMVCardChangePin - Verify user unable to change pin when new pin and confirm pin field does not match                  | OLS  | associatedUser | associatedCard | primaryContact | 1234      | 7896             | 400        | Validation failed                           | 12           |
      | TC-10-OLS-740-OMVCardChangePin - Verify user unable to change pin while passing not applicable card number                           | OLS  | associatedUser | notApplicable  | primaryContact | 1324      | 1324             | 400        | Action not applicable for the selected card | 2152         |

#      | TC-05-OLS-598 - Verify user should getting error response  while passing unassociated card number            | associatedUser   | unAssociatedCard | 1324      | 1324             | 400        | Account is unauthorized to use this card         | 97109        |

  @OMVCardsChangePinTypeWishPin
  Scenario Outline: OMV change pin with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<pinContact>" and "<type>" and "<pinOffset>" and "<confirmPinOffset>" as parameters to "ChangePinTypeWishPin" API card pin
    Then verify the response status should be <statusCode>
    Then user put the change pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                                                                             | type | customerNumber | cardNumber     | pinContact     | pinOffset | confirmPinOffset | statusCode | statusMessage                               | statusNumber |
      | TC-01-OLS-891-OMVCardChangePinTypeWishPIn - Verify user should able to change pin type for a selected card with all valid details without customerNo | CSR  | associatedUser | associatedCard | pinContact     | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-02-OLS-891-OMVCardChangePinTypeWishPIn - Verify user should able to change pin type for a selected card with all valid details without customerNo | CSR  | associatedUser | associatedCard | primaryContact | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-03-OLS-891-OMVCardChangePinTypeWishPIn - Verify user should able to change pin type for a selected card with all valid details                    | OLS  | associatedUser | associatedCard | pinContact     | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-04-OLS-891-OMVCardChangePinTypeWishPIn - Verify user should able to change pin type for a selected card with all valid details                    | OLS  | associatedUser | associatedCard | primaryContact | 1324      | 1324             | 200        | empty                                       | 0            |
      | TC-05-OLS-891-OMVCardChangePinTypeWishPIn - Verify user unable to change pin type while passing invalid account number                               | OLS  | invalidCus     | associatedCard | primaryContact | 1324      | 1324             | 400        | Invalid Customer Number                     | 4018         |
      | TC-06-OLS-891-OMVCardChangePinTypeWishPIn - Verify user unable to change pin type while passing invalid card number                                  | OLS  | OMVValidCus    | invalidCard    | primaryContact | 1324      | 1324             | 400        | Invalid card number provided                | 2112         |
      | TC-07-OLS-891-OMVCardChangePinTypeWishPIn - Verify user unable to change pin type while passing empty value in New PIN field                         | OLS  | associatedUser | associatedCard | primaryContact |           | 1324             | 400        | Validation failed                           | 12           |
      | TC-08-OLS-891-OMVCardChangePinTypeWishPIn - Verify user unable to change pin type while passing empty value in confirm PIN field                     | OLS  | associatedUser | associatedCard | primaryContact | 1324      |                  | 400        | Validation failed                           | 12           |
      | TC-09-OLS-891-OMVCardChangePinTypeWishPIn - Verify user unable to change pin type when new pin and confirm pin field does not match                  | OLS  | associatedUser | associatedCard | primaryContact | 1234      | 7896             | 400        | Validation failed                           | 12           |
      | TC-10-OLS-891-OMVCardChangePinTypeWishPIn - Verify user unable to change pin type while passing not applicable card number                           | OLS  | associatedUser | notApplicable  | primaryContact | 1234      | 7896             | 400        | Action not applicable for the selected card | 2152         |

  @OMVCardsChangePinTypeSysPin
  Scenario Outline: OMV reset pin with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user pass "<customerNumber>" and "<cardNumber>" and "<pinContact>" and "<type>" and "<pinOffset>" and "<confirmPinOffset>" as parameters to "ChangePinTypeSysPin" API card pin
    Then verify the response status should be <statusCode>
    Then user put the reissue pin request to update the card pin of a specific card and validate with "<statusMessage>" and <statusNumber> as expected values

    Examples:
      | testName                                                                                                                                                | type | customerNumber | cardNumber     | pinContact     | statusCode | statusMessage                               | statusNumber |
      | TC-01-OLS-891-OMVCardChangePinTypeSysPIn - Verify user should able to change pin type for a selected card with new card contact without customerNo      | CSR  | associatedUser | associatedCard | pinContact     | 200        | empty                                       | 0            |
      | TC-02-OLS-891-OMVCardChangePinTypeSysPIn - Verify user should able to change pin type for a selected card with existing card contact without customerNo | CSR  | associatedUser | associatedCard | primaryContact | 200        | empty                                       | 0            |
      | TC-03-OLS-891-OMVCardChangePinTypeSysPIn - Verify user should able to change pin type for a selected card with new card contact                         | OLS  | associatedUser | associatedCard | pinContact     | 200        | empty                                       | 0            |
      | TC-04-OLS-891-OMVCardChangePinTypeSysPIn - Verify user should able to change pin type for a selected card with existing card contact                    | OLS  | associatedUser | associatedCard | primaryContact | 200        | empty                                       | 0            |
      | TC-05-OLS-891-OMVCardChangePinTypeSysPIn - Verify user unable to change pin type while passing invalid account number                                   | OLS  | invalidCus     | associatedCard | primaryContact | 400        | Invalid Customer Number                     | 4018         |
      | TC-06-OLS-891-OMVCardChangePinTypeSysPIn - Verify user unable to change pin type while passing invalid card number                                      | OLS  | OMVValidCus    | invalidCard    | primaryContact | 400        | Invalid card number provided                | 2112         |
      | TC-07-OLS-891-OMVCardChangePinTypeSysPIn - Verify user unable to change pin type while passing not applicable card number                               | OLS  | associatedUser | notApplicable  | primaryContact | 400        | Action not applicable for the selected card | 2152         |
