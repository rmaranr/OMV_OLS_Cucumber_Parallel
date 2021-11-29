@OLSDriverAndVehicleModule
Feature: Customer CostCentre Module

  @OLSGetDriverDetails
  Scenario Outline: Get Driver details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user pass account endpoint along with customer number"<customerNumber>" as parameter
    When user pass customer number"<customerNumber>" as parameter
    Then user hit the get request to get the list of driver details and validate with "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                     | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-739-Verify user should able to get drivers details with expected fields and values | associatedUser   | 200        | empty                                            | 0            |
      | TC-02-OLS-739-Validate the expected error message for invalid account number                 | invalidCus       | 400        | Invalid Customer Number                          | 4018         |
      | TC-03-OLS-739-Validate the expected error message for passing unassociated account number    | unAssociatedUser | 401        | API User is unauthorized to access this customer | 97102        |

  @OLSGetVehicleDetails
  Scenario Outline: Get Vehicle details with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
#    When user pass account endpoint along with customer number"<customerNumber>" as parameter
    When user pass customer number"<customerNumber>" as parameter
    Then user hit the get request to get the list of vehicle details and validate with "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                      | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-740-Verify user should able to get vehicles details with expected fields and values | associatedUser   | 200        | empty                                            | 0            |
      | TC-02-OLS-740-Validate the expected error message for invalid account number                  | invalidCus       | 400        | Invalid Customer Number                          | 4018         |
      | TC-03-OLS-740-Validate the expected error message for passing unassociated account number     | unAssociatedUser | 401        | API User is unauthorized to access this customer | 97102        |