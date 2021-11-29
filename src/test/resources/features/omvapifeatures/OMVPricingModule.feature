@OMVPricingModule
Feature: Pricing Module


  @CSRAddPricingRule
  Scenario Outline: Assign Report to the customer
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user add pricing rules for the client
#    Then validate and verify report type
#    Then user post request body to get cards and validate with "<statusMessage>" and <statusNumber> as expected values
#    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                   | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OMV-Verify user should able to add pricing rules                                     | associatedUser   | 200        | empty                                            | 0            |
