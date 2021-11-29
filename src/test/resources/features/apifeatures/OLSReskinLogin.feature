@loginmodule
Feature: Login Module

  @ValidLogincredentials
  Scenario Outline: OLS-48 Successful login with valid user credentials
  Given User gets the base url EndPoint and passing "<testCaseName>" to create test
  When verify the logon status for the user is "2501"
  When the user posts the request for an authorization token "/login"
  Then the bearer token response status code should be 200
  Then the API should return a valid authentication token
  Then token type should be BearerType
  Then validated the LOGON_STATUS_CID in DB should be "2501"
  Examples:
  | testCaseName                                                                                     |
  | TC-01-OLS-48-Verify user should generate Authorized token with valid username and valid password |
  @Invalidloginattempt
  Scenario Outline: OLS-48 Invalid login attempts
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When verify the logon status for the user is "2501"
    When user enters the  Username "<Username>" and Password "<Password>"
    When user enters the client ID "<clientID>" ,client Secret "<clientSecret>" and "/login"
    Then the error message response status code should be "<status>"
    Then error message response should be "<reponseErrorCode>" and "<responseErrorMessage>"

    Examples: 
      | testCaseName                                                                                             | Username     | Password     | clientID      | clientSecret      | status | reponseErrorCode | responseErrorMessage  |
      | TC-02-OLS-48-Verify user should not generate Authorized token with valid username and invalid password   | prop.invalid | prop.pwd     | prop.clientID | prop.clientSecret |    401 |               23 | Invalid logon attempt |
      | TC-03-OLS-48-Verify user should not generate Authorized token  with  invalid username and valid password | prop.user    | prop.invalid | prop.clientID | prop.clientSecret |    401 |               23 | Invalid logon attempt |

  @ExpiredPassword
  Scenario Outline: OLS-51 Expired password
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When verify the logon status for the user is "2501"
    When user enters the Username "<Username>" and Expiredpassword "<Expiredpassword>"
    When user enters the client ID "<clientID>" ,client Secret "<clientSecret>" and "/login"
    Then the error message response status code should be "<status>"
    Then user will update the valid password if it is expired "<Expiredpassword>"
    Then error message response should be "<reponseErrorCode>" and "<responseErrorMessage>"

    Examples: 
      | testCaseName                                                                                           | Username  | Expiredpassword      | clientID      | clientSecret      | status | reponseErrorCode | responseErrorMessage                                 |
      | TC-01-OLS-51-Verify user should not generate Authorized token with valid username and expired password | prop.user | validExpiredPassword | prop.clientID | prop.clientSecret |    401 |              154 | Password has expired. You must change your password. |
      | TC-02-OLS-51-Verify user should not generate Authorized token with valid username and invalid password | prop.user | invalidPassword      | prop.clientID | prop.clientSecret |    401 |               23 | Invalid logon attempt                                |

  @MaxPasswordAttempts
  Scenario Outline: OLS-383 Verify if user has reached max password attempts for invalid login and validate the expected response
    Given user wants to be set base url to access the API
   	When the user posts the request for an authorization token "/login"
    When user able to enters the credentials based on max_password_attempts and endpoint "/login"
    Then validates the error response message based on max password attempts as beforLastAttempt "<maxPasswordAttempt-1>" and maxPasswordAttempt "<maxPasswordAttempt>"

    Examples: 
      | maxPasswordAttempt-1                                            | maxPasswordAttempt                                           |
      | 30022:Your account will be locked after one more failed attempt | 156:Invalid logon attempt. Userid is now temporarily locked. |

  @UserStatus
  Scenario Outline: OLS-388 and OLS-273 Verify user will be able to generate reset password and validate the expected response for all user status
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to generate reset password mail based on user logon status "<UserStatus>" and "/login"
    Then validate the response status code for the user "<Status>"
    Then validate the error message response statusnumber "<statusNumber>" and statusmessage "<statusMessage>"

    Examples: 
      | testCaseName                                                                       | UserStatus    | Status | statusNumber | statusMessage              |
      | TC-01-OLS-388-Validate the expected error message for Active user status           | Active        |    200 |              |                            |
      | TC-02-OLS-388-Validate the expected error message for inactive user status         | Inactive      |    401 |          226 | User is Inactive           |
      | TC-03-OLS-388-Validate the expected error message for Temp Locked user status      | TemporaryLock |    401 |          151 | User is temporarily locked |
      | TC-04-OLS-388-Validate the expected error message for Permanent Locked user status | PermenantLock |    401 |          152 | User is permanently locked |
      | TC-05-OLS-388-Validate the expected error message for  Logged On  user status      | LoggedOn      |    200 |              |                            |
      | TC-06-OLS-388-Validate the expected error message for  logged Off user status      | LoggedOff     |    200 |              |                            |

  @PositiveScenarioforTokenValidation
  Scenario Outline: OLS-494 and OLS-246 Verify user will be able to validate the token and generate reset password
    Given user wants to be set base url to access the API
    When user able to generate reset password mail based on user logon status "<UserStatus>" and "/login"
    Then validate the response status code should be "<status>"
    When user able to validate the token link is active or expired using login  "/login" endpoint and  "<tokenType>"
    Then validate the response status code should be "<status>"
    Then user able to generate reset password using login end point "/login"
    Then validate the response status code should be "<status>"
    Then verify the generated token used "<used>" column value in database

    Examples: 
      | UserStatus | tokenType  | status | used |
      | Active     | validToken |    200 | Y    |

  @NegativeScenarioforTokenValidation
  Scenario Outline: OLS-494 and OLS-246 Verify user should not able to generate reset password using invalid token
    Given user wants to be set base url to access the API
    When user able to generate reset password mail based on user logon status "<UserStatus>" and "/login"
    When user able to validate the token link is active or expired using login  "/login" endpoint and  "<tokenType>"
    Then validate the response status code should be "<status>"
    Then validate the error response statusNumber "<statusNumber>" and statusMessage "<statuMessage>"

    Examples: 
      | UserStatus | tokenType    | statusNumber | statuMessage                   | status |
      | Active     | invalidToken |       151000 | Invalid password reset request |    401 |

  @ChangePassword
  Scenario Outline: OLS-485 Change Password
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to change the password for logged on user with password type "<Password Type>" and login endpoint "/login"
    Then validate the response status code should be "<status>"
    Then validate the statusNumber "<statusNumber>" and statusMessage "<statuMessage>" from response

    Examples: 
      | testCaseName                                                                                                             | Password Type          | status | statusNumber | statuMessage               |
      | TC-01-OLS-485-Verify user should able to change the password                                                             | ValidConfirmPassword   |    200 |              |                            |
      | TC-02-OLS-485-Verify the confirm password should not match with the new password and validate the expected error message | InvalidConfirmPassword |    400 |           87 | New Passwords must match-8 |

  @generateAndValidatesUserPasswords
  Scenario Outline: OLS-484- Generating the new password based on user unique password constraints
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to change the password based on logged on user role access
    Then validate the expected response for user new password is used any of the previous passwords based on "<passwordCount>"

    Examples: 
      | testCaseName                                                                                            | passwordCount |
      | TC-01-OLS-484- Verify user can able to change the password based on logged on user password constraints |               |

  @ValidationsForUserPreviousPasswords
  Scenario Outline: OLS-484- Validate if the user's new password is used as any of the previous passwords
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    Then validate the expected response for user new password is used any of the previous passwords based on "<passwordCount>"

    Examples: 
      | testCaseName                                                                                                   | passwordCount     |
      | TC-02-OLS-484- Validate by entering the password as user used last password and validate the expected response | userLastPassword  |
      | TC-03-OLS-484- Validate by entering the password user can able to uniquePasswordCount                          | userFirstPassword |
