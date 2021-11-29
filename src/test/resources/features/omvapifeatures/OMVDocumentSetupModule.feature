@OMVDocumentSetupModule
Feature: Document Setup Module

  @CSRAssignReport
  Scenario Outline: Assign Report to the customer
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user assign report to the selected customer"<customerNumber>"
    Then validate and verify report type
#    Then user post request body to get cards and validate with "<statusMessage>" and <statusNumber> as expected values
#    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                   | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OMV-Verify user should able to assign report for an associated account               | associatedUser   | 200        | empty                                            | 0            |


  @CSREditReport
  Scenario Outline: Assign Report to the customer
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user edit report to the selected customer"<customerNumber>"
    Then verify the response status should be <statusCode>
    Then validate and verify updated fields "<deliveryType>" and "<contactHierarchy>" from db
#  Then user post request body to get cards and validate with "<statusMessage>" and <statusNumber> as expected values


    Examples:
      | testName                                                                                   | customerNumber   | statusCode | deliveryType | contactHierarchy |                                 | statusNumber |
      | TC-01-OMV-787-Verify user should able to edit report for an associated account             | associatedUser   | 200        | Email        | Management       |                                          | 0            |

