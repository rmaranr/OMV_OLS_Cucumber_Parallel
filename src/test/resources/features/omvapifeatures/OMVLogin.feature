@loginmodule
Feature: API - Login Module

  @ValidLoginCredentials
  Scenario Outline: OMV-06 Successful login with valid user credentials
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When verify the logon status for the user is "2501"
    When the user posts the request for an authorization token "/login"
    Then the bearer token response status code should be 200
    Then the API should return a valid authentication token
    Then token type should be BearerType
    Then validated the LOGON_STATUS_CID in DB should be "2501"

    Examples:
      | testCaseName                                                                                     |
      | TC-01-OMV-06-Verify user should generate Authorized token with valid username and valid password |

#  @InvalidLoginAttempt
#  Scenario Outline: OMV-06 Invalid login attempts
#    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
#    When verify the logon status for the user is "2501"
#    When user enters the  Username "<Username>" and Password "<Password>"
#    When user enters the client ID "<clientID>" ,client Secret "<clientSecret>" and "/login"
#    Then the error message response status code should be "<status>"
#    Then error message response should be "<reponseErrorCode>" and "<responseErrorMessage>"
#
#    Examples:
#      | testCaseName                                                                                             | Username     | Password     | clientID      | clientSecret      | status | reponseErrorCode | responseErrorMessage                                           |
#      | TC-02-OMV-06-Verify user should not generate Authorized token with valid username and invalid password   | prop.invalid | prop.pwd     | prop.clientID | prop.clientSecret | 401    | 23               | The User ID or Password you entered does not match our records |
#      | TC-03-OMV-06-Verify user should not generate Authorized token  with  invalid username and valid password | prop.user    | prop.invalid | prop.clientID | prop.clientSecret | 401    | 23               | The User ID or Password you entered does not match our records |
#
