@Accounts
Feature: Accounts module
	@getAllAccounts
  Scenario Outline: OLS- 587- To get the list of accounts
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get the list of accounts for associated user
    Then validate the response status code "<statusCode>"

    Examples: 
      | testCaseName                                                                            | statusCode |
      | TC-01-OLS-587-Verify user should able to get the account details associated to the user |        200 |

  @specificAccountInformation
  Scenario Outline: OLS-587-To get the specific account information
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get the specific account information based on scenario "<ScenarioType>"
    Then validate the response status code "<statusCode>"
    Then validate the account details and error response status number "<statusNumber>" and status message "<statusMessage>"

    Examples: 
      | testCaseName                                                                                            | statusCode | statusNumber | statusMessage                                    | ScenarioType     |
      | TC-02-OLS-587-Verify user should able to get the specific account information for associted user        |        200 |              |                                                  | associatedUser   |
      | TC-03-OLS-587-Verify user should not able to get the specific account information for invalid user      |        400 |        18018 | Invalid Account Number                           | invalidAccountNo      |
      | TC-04-OLS-587-Verify user should not able to get the specific account information for unassociated user |        401 |        97102 | API User is unauthorized to access this customer | unassociatedAccountNo |

  @updateAccountInformation
  Scenario Outline: OLS-587-Update the specific account details
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user can able to update the account information and contact details
    Then validate the response status code "<statusCode>"

    Examples: 
      | testCaseName                                                           |statusCode|
      | TC-05-OLS-587-verify user can update the account and contact details   |200       |
