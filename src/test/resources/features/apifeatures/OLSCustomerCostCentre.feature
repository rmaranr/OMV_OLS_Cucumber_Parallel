@OLSCustomerCostCentreModule
Feature: Customer CostCentre Module

  @OLSCostCentreCreation,@Regression
  Scenario Outline: Creating costcentre with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user pass "<paramCostCentreCode>" string parameter for the key "customerCostCentreCode" to request body
    Then user pass "<paramDescription>" string parameter for the key "description" to request body
    Then user pass "<paramShortDescription>" string parameter for the key "shortDescription" to request body
    Then user post request body to create cost centre and validate with "<paramCostCentreCode>"and"<paramDescription>"and"<paramShortDescription>"and"<statusMessage>"and<statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                                        | customerNumber   | statusCode | paramCostCentreCode | paramDescription | paramShortDescription | statusMessage                                    | statusNumber |
      | TC-01-OLS-536-Verify user should able to create new cost centre with all mandatory fields                       | associatedUser   | 200        | new                 | new              | new                   | empty                                            | 0            |
      | TC-02-OLS-536-Validate the expected error message for invalid account number                                    | invalidCus       | 400        | new                 | new              | new                   | Invalid Customer Number                          | 4018         |
      | TC-03-OLS-536-Validate the expected error message for passing existing costcentrecode in customerCostCentreCode | associatedUser   | 400        | existing            | new              | new                   | Must be unique                                   | 12           |
      | TC-04-OLS-536-Validate the expected error message for passing empty in customerCostCentreCode                   | associatedUser   | 400        | empty               | new              | new                   | Must not be blank                                | 12           |
      | TC-05-OLS-536-Validate the expected error message for passing empty in description                              | associatedUser   | 400        | new                 | empty            | new                   | Must not be blank                                | 12           |
      | TC-06-OLS-536-Validate the expected error message for passing empty in shortDescription                         | associatedUser   | 400        | new                 | new              | empty                 | Must not be blank                                | 12           |
      | TC-07-OLS-536-Validate the expected error message for passing less than 2 characters in customerCostCentreCode  | associatedUser   | 200        | invalid             | new              | new                   | empty                                            | 12           |
      | TC-08-OLS-536-Validate the expected error message for passing less than 2 characters in description             | associatedUser   | 200        | new                 | invalid          | new                   | empty                                            | 12           |
      | TC-09-OLS-536-Validate the expected error message for passing less than 2 characters in shortDescription        | associatedUser   | 200        | new                 | new              | invalid               | empty                                            | 12           |
#      | TC-10-OLS-536-Validate the expected error message for passing more than 12 characters in shortDescription       | associatedUser   | 200        | new                 | new              | long                  | empty                                            | 12           |
      | TC-11-OLS-536 validate the expected error message for passing special characters in customerCostCentrecode      | associatedUser   | 200        | specialChar         | new              | new                   | empty                                            | 12           |
      | TC-12-OLS-536-Validate the expected error message for passing unassociated account number                       | unAssociatedUser | 401        | new                 | new              | new                   | API User is unauthorized to access this customer | 97102        |
      | TC-13-OLS-536-Validate the expected error message for passing more than 10 characters in customerCostCentreCode | associatedUser   | 400        | long11              | new              | new                   | Validation failed                                | 12           |

  @OLSCostCentreUpdation,@Regression
  Scenario Outline: Editing costcentre with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user pass "<paramCostCentreCode>" string parameter for the key "customerCostCentreCode" to request body
    Then user pass "<paramDescription>" string parameter for the key "description" to request body
    Then user pass "<paramShortDescription>" string parameter for the key "shortDescription" to request body
    Then user pass "costCentreCode" string parameter for the key "existingCustomerCostCentreCode" to request body
    Then user put request body to edit cost centre and validate with "<paramCostCentreCode>"and"<paramDescription>"and"<paramShortDescription>"and"<statusMessage>"and<statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                                        | customerNumber   | statusCode | paramCostCentreCode | paramDescription | paramShortDescription | statusMessage                                    | statusNumber |
      | TC-01-OLS-539-Verify user should able to update existing cost centre details with all mandatory fields          | associatedUser   | 200        | new                 | new              | new                   | empty                                            | 0            |
      | TC-02-OLS-539-Validate the expected error message for invalid account number                                    | invalidCus       | 400        | new                 | new              | new                   | Invalid Customer Number                          | 4018         |
      | TC-03-OLS-539-Validate the expected error message for passing existing costcentrecode in customerCostCentreCode | associatedUser   | 200        | existing            | new              | new                   | Must be unique                                   | 12           |
      | TC-04-OLS-539-Validate the expected error message for passing empty in customerCostCentreCode                   | associatedUser   | 400        | empty               | new              | new                   | Must not be blank                                | 12           |
      | TC-05-OLS-539-Validate the expected error message for passing empty in description                              | associatedUser   | 400        | new                 | empty            | new                   | Must not be blank                                | 12           |
      | TC-06-OLS-539-Validate the expected error message for passing empty in shortDescription                         | associatedUser   | 400        | new                 | new              | empty                 | Must not be blank                                | 12           |
      | TC-07-OLS-539-Validate the expected error message for passing less than 2 characters in customerCostCentreCode  | associatedUser   | 200        | invalid             | new              | new                   | empty                                            | 12        |
      | TC-08-OLS-539-Validate the expected error message for passing less than 2 characters in description             | associatedUser   | 200        | new                 | invalid          | new                   | empty                                            | 12        |
      | TC-09-OLS-539-Validate the expected error message for passing less than 2 characters in shortDescription        | associatedUser   | 200        | new                 | new              | invalid               | empty                                            | 12       |
#      | TC-10-OLS-539-Validate the expected error message for passing more than 12 characters in shortDescription       | associatedUser   | 200        | new                 | new              | long                  | empty                                             | 12       |
      | TC-11-OLS-539 validate the expected error message for passing special characters in customerCostCentrecode      | associatedUser   | 200        | specialChar         | new              | new                   | empty                                              | 12      |
      | TC-12-OLS-539-Validate the expected error message for passing unassociated account number                       | unAssociatedUser | 401        | new                 | new              | new                   | API User is unauthorized to access this customer | 97102        |
      | TC-13-OLS-539-Validate the expected error message for passing more than 10 characters in customerCostCentreCode | associatedUser   | 400        | long11              | new              | new                   | Validation failed  | 12       |

  @OLSGetCostCentreDetails,@Smoke,@Regression
  Scenario Outline: Get costcentre with different senarios(Both postive and negative scenarios)
    Given User gets the base url EndPoint and passing "<testName>" to create test
    When user pass customer number"<customerNumber>" as parameter
    Then user hit the get request to get the list of cost centre details and validate with "<statusMessage>" and <statusNumber> as expected values
    Then verify the response status should be <statusCode>

    Examples:
      | testName                                                                                         | customerNumber   | statusCode | statusMessage                                    | statusNumber |
      | TC-01-OLS-533-Verify user should able to get cost centre details with expected fields and values | associatedUser   | 200        | empty                                            | 0            |
      | TC-02-OLS-533-Validate the expected error message for invalid account number                     | invalidCus       | 400        | Invalid Customer Number                          | 4018         |
      | TC-03-OLS-533-Validate the expected error message for passing unassociated account number        | unAssociatedUser | 401        | API User is unauthorized to access this customer | 97102        |