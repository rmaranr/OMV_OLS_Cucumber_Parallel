@UserCreation
Feature: User module

  @Createuser
  Scenario Outline: OLS-604-Create a new user after successful login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user wants to pass the params as "userId" by value "<userId>"
    When user wants to pass the params as "emailAddress" by value "<emailAddress>"
    When user wants to pass the params as "displayName" by value "<displayName>"
    When user wants to pass the params as "otherPhone" by value "<otherPhone>"
    When user wants to pass the params as "mobilePhone" by value "<mobilePhone>"
    When user wants to pass the params as "status" by value "<status>"
    When user wants to pass the params as "role" by value "<role>"
    When user wants to pass the params as "accountProfiles" by value "<accountProfiles>"
    Then validate the response status code "<statusCode>"
    Then validate the user module error response status number "<statusNumber>" and status message "<statusMessage>"

    Examples:
      | testCaseName                                                                                                          | userId                      | emailAddress                 | displayName                       | otherPhone                       | mobilePhone                       | status      | role        | accountProfiles                    | statusCode | statusNumber | statusMessage                                    |
      | TC-01-OLS-604-Verify user should able to create a new OLS user with all valid inputs                                  | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 200        |              |                                                  |
      | TC-02-OLS-604-Verify user should not able to create a new OLS user when User Name field is Empty                      | UserIdAsEmpty               | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed:logonId-Must not be blank      |
      | TC-03-OLS-604-Verify user should not able to create a new OLS user when User Name field is less than 3 characters     | UserIdAsLessThan3Characters | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-04-OLS-604-Verify user should not able to create a new OLS user when User Name field is Existing UserName          | UserIdAsExisting            | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 230          | Username already exists                          |
      | TC-05-OLS-604-Verify user should not able to create a new OLS user when User Name field having special characters     | UserIdAsSpecialCharacter    | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-06-OLS-604-Verify user should not able to create a new OLS user when Email Address field is Empty                  | validUserId                 | EmailAddressAsEmpty          | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed:emailAddress-Must not be blank |
      | TC-07-OLS-604-Verify user should not able to create a new OLS user when Email Address field with Invalid Mail id      | validUserId                 | EmailAddressAsNumbersAndName | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-08-OLS-604-Verify user should not able to create a new OLS user when Full Name field is Empty                      | validUserId                 | validEmailAddress            | DisplayNameAsEmpty                | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed:name-Must not be blank         |
      | TC-09-OLS-604-Verify user should not able to create a new OLS user when Full Name field is more than 30 characters    | validUserId                 | validEmailAddress            | DisplayNameAsMoreThan30Characters | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-10-OLS-604-Verify user should not able to create a new OLS user when Mobile Phone field is Empty                   | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | MobilePhoneAsEmpty                | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed:mobilePhone-Must not be blank  |
      | TC-11-OLS-604-Verify user should not able to create a new OLS user when Mobile Phone field is less than 4 characters  | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | MobilePhoneAsLessThan3Characters  | validStatus | validRole   | validaccountsForOtherUser          | 200        |              |                                                  |
      | TC-12-OLS-604-Verify user should not able to create a new OLS user when Mobile Phone field is more than 15 characters | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | MobilePhoneAsMoreThan15Characters | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-13-OLS-604-Verify user should not able to create a new OLS user when Mobile Phone field with special charaters     | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | MobilePhoneAsSpecialCharacters    | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-14-OLS-604-Verify user should not able to create a new OLS user when Other Phone field is more than 15 characters  | validUserId                 | validEmailAddress            | validDisplayName                  | OtherPhoneAsMorethan30Characters | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 400        | 12           | Validation failed                                |
      | TC-15-OLS-604-Verify user should able to create a new OLS user when other phone field is Empty                        | validUserId                 | validEmailAddress            | validDisplayName                  | OtherPhoneAsEmpty                | validMobilePhone                  | validStatus | validRole   | validaccountsForOtherUser          | 200        |              |                                                  |
      | TC-16-OLS-604-Verify user should not able to create a new OLS user when Role field is Invalid Role                    | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | roleAsEmpty | validaccountsForOtherUser          | 400        | 237          | Invalid User Role                                |
      | TC-17-OLS-604-Verify user should not able to create a new OLS user when accountNumber field is Invalid                | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | accountsForOtherUserAsInvalid      | 400        | 18011        | Invalid Account(s)                               |
      | TC-18-OLS-604-Verify user should not able to create a new OLS user when accountNumber field is Empty                  | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | accountsForOtherUserEmptyAsInvalid | 400        | 12           | Validation failed:accountNo-Must not be blank    |
      | TC-19-OLS-604-Verify user should not able to create a new OLS user when accountNumber field is UnassociatedAccountNo  | validUserId                 | validEmailAddress            | validDisplayName                  | validOtherPhone                  | validMobilePhone                  | validStatus | validRole   | accountsForLoggedOnUserInvalid     | 400        | 18011        | Invalid Account(s)                               |

  @PasswordCreationForNewUser
  Scenario: OLS-604- Generate a password for the new user
    When user able to generate a password for the new user using login end point "/login"
    Then validate the response status code should be "200"

  @updateUserDetails
  Scenario Outline: OLS-605- Update the new user details after successful login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user wants to update the user details to pass the params as "userId" by value "<userId>"
    When user wants to update the user details to pass the params as "emailAddress" by value "<emailAddress>"
    When user wants to update the user details to pass the params as "displayName" by value "<displayName>"
    When user wants to update the user details to pass the params as "otherPhone" by value "<otherPhone>"
    When user wants to update the user details to pass the params as "mobilePhone" by value "<mobilePhone>"
    When user wants to update the user details to pass the params as "role" by value "<role>"
    Then validate the response status code "<statusCode>"
    Then validate the valid user details are updated in DB and validate error response status number "<statusNumber>" and status message "<statusMessage>"

    Examples: 
      | testCaseName                                                                                                             | userId         | emailAddress                 | displayName                       | otherPhone        | mobilePhone                      | role        | statusCode | statusNumber | statusMessage                                    |
      | TC-01-OLS-605-Verify user should able to update the valid user details                                                   | existingUserId | validEmailAddress            | validDisplayName                  | validOtherPhone   | validMobilePhone                 | validRole   |        200 |              |                                                  |
      | TC-02-OLS-605-Verify user should not able to update the user details when passing email address as empty                 | existingUserId | EmailAddressAsEmpty          | validDisplayName                  | validOtherPhone   | validMobilePhone                 | validRole   |        400 |           12 | Validation failed:emailAddress-Must not be blank |
      | TC-03-OLS-605-Verify user should not able to update the user details when passing displayname as empty                   | existingUserId | validEmailAddress            | DisplayNameAsEmpty                | validOtherPhone   | validMobilePhone                 | validRole   |        400 |           12 | Validation failed:name-Must not be blank         |
      | TC-04-OLS-605-Verify user should not able to update the user details when passing mobile phone as empty                  | existingUserId | validEmailAddress            | validDisplayName                  | validOtherPhone   | MobilePhoneAsEmpty               | validRole   |        400 |           12 | Validation failed:mobilePhone-Must not be blank  |
      | TC-05-OLS-605-Verify user should  able to update the user details when passing other phone field as empty                | existingUserId | validEmailAddress            | validDisplayName                  | OtherPhoneAsEmpty | validMobilePhone                 | validRole   |        200 |              |                                                  |
      | TC-06-OLS-605-Verify user should not able to update the user details when passing role field as empty                    | existingUserId | validEmailAddress            | validDisplayName                  | validOtherPhone   | validMobilePhone                 | roleAsEmpty |        400 |          237 | Invalid User Role                                |
      | TC-07-OLS-605-Verify user should not able to update the user details when passing invalid email address                  | existingUserId | EmailAddressAsNumbersAndName | validDisplayName                  | validOtherPhone   | validMobilePhone                 | validRole   |        400 |           12 | Validation failed                                |
      | TC-08-OLS-605-Verify user should not able to update the user details when passing more than 30 characters in full name   | existingUserId | validEmailAddress            | DisplayNameAsMoreThan30Characters | validOtherPhone   | validMobilePhone                 | validRole   |        400 |           12 | Validation failed                                |
      | TC-09-OLS-605-Verify user should not able to update the user details when passing less than 4 characters in mobile phone | existingUserId | validEmailAddress            | validDisplayName                  | validOtherPhone   | MobilePhoneAsLessThan3Characters | validRole   |        200 |              |                                                  |
      | TC-10-OLS-605-Verify user should not able to update the user details when passing special characters in mobile phone     | existingUserId | validEmailAddress            | validDisplayName                  | validOtherPhone   | MobilePhoneAsSpecialCharacters   | validRole   |        400 |           12 | Validation failed                                |

  @UpdateStatus
  Scenario Outline: OLS-605- Update the new status for other user after successful login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to change the status for other user from status "<fromStatus>" to status "<toStatus>"
    Then validate the response status code "<statusCode>"
    Then validate the status updation error response statusNumber "<statusNumber>" and stausMessage "<statusMessage>"

    Examples: 
      | testCaseName                                                                                                 | fromStatus         | toStatus          | statusCode | statusNumber | statusMessage                  |
      | TC-11-OLS-605-Verify user should able to change the status from logged on to active for other user           | Logged on          | Active            |        200 |              |                                |
      | TC-12-OLS-605-Verify user should able to change the status from active to inactive for other user            | Active             | Inactive          |        200 |              |                                |
      | TC-13-OLS-605-Verify user should able to change the status from active to temporary lockout for other user   | Active             | Temporary Lockout |        200 |              |                                |
      | TC-14-OLS-605-Verify user should able to change the status from active to permanent Lockout for other user   | Active             | Permanent Lockout |        200 |              |                                |
      | TC-15-OLS-605-Verify user should not able to change the status from active to invalid status for other user  | Active             | invalidStatus     |        400 |            6 | Invalid entry:status-Bad value |
      | TC-16-OLS-605-Verify user should able to change the status for inactive to active for other user             | Inactive           | Active            |        200 |              |                                |
      | TC-17-OLS-605-Verify user should able to change the status for inactive to permanent Lockout for other user  | Inactive           | Permanent Lockout |        200 |              |                                |
      | TC-18-OLS-605-Verify user should able to change the status for permanent Lockout to active for other user    | Permanent Lockout  | Active            |        200 |              |                                |
      | TC-19-OLS-605-Verify user should able to change the status for permanent Lockout to inactive for other user  | Permanent Lockout  | Inactive          |        200 |              |                                |
      | TC-20-OLS-605-Verify user should able to change the status for temporary Lockout to active for other user    | Temporary Lockout  | Active            |        200 |              |                                |
      | TC-21-OLS-605-Verify user should able to change the status for temporary Lockout to inactive for other user  | Temporary Lockout  | Inactive          |        200 |              |                                |
      | TC-22-OLS-605-Verify user should able to change the status for temporary Lockout to loggedon for other user  | Temporary Lockout  | Logged on         |        200 |              |                                |
      | TC-23-OLS-605-Verify user should able to change the status for temporary Lockout to loggedoff for other user | Temporary Lockout  | Logged off        |        200 |              |                                |
      | TC-24-OLS-605-Verify user should not able to change the status for logged on user                            | loggedOnUserStatus | Active            |        400 |          236 | Invalid User Status            |

  @positiveScenarioForGetusers
  Scenario Outline: Verify user should able to get the valid user details
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user try to get the user details with user endPoint and logonId "<logonIdScenario>"
    Then validate the response status code "<statusCode>"
    Then validate the  logonId user details from response
    Then validate the account profile details from response

    Examples: 
      | testCaseName                                                   | logonIdScenario | statusCode |
      | TC-01-OLS-532-Verify user should able to get the list of users | valid           |        200 |

  @negativeScenarioForGetUsers
  Scenario Outline: Verify user should not able to get the user details and validate the expected error response
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user try to get the user details with user endPoint and logonId "<logonIdScenario>"
    Then validate the response status code "<statusCode>"
    Then validate the error response status number "<statusNumber>" and status message "<statusMessage>"

    Examples: 
      | testCaseName                                                                                                                    | logonIdScenario | statusCode | statusMessage        | statusNumber |
      | TC-02-OLS-532-Verify user should not able to get the list of users for invalid logOnId and validate the expected error response | Invalid         |        401 | User is Invalid      |          227 |
      #| TC-03-OLS-532-Verify user should not able to get the list of users for invalid token and validate the expected error response   | InvalidToken    |        401 | Invalid Access Token |              |

  @AccountProfiles
  Scenario Outline: Verify user should able to update the accounts for associated user
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user try to update the accounts as params "accountProfiles" and value as "<accounts>" for associated user
    Then validate the response status code "<statusCode>"
    Then validate the error response status number "<statusNumber>" and status message "<statusMessage>"

    Examples: 
      | testCaseName                                                                            | accounts                              | statusCode | statusNumber | statusMessage                                   |
      | TC-01-OLS-268-Verify user should able to update the valid accounts for other user       | validAccountsForOtherUser             |        200 |              |                                                 |
      | TC-02-OLS-268-Verify user should not able to update the invalid accounts for other user | accountsForOtherUserAsInvalid         |        400 |        18011 | Invalid Account(s)                              |
      | TC-03-OLS-268-Verify user should not able to update the empty string for other user     | accountsForOtherUserAsEmptyForInvalid |        400 |        18011 | Invalid Account(s)                              |
      | TC-04-OLS-268-Verify user should not able to update the accounts for logged on user     | accountsForLoggedOnUserInvalid        |        401 |        97108 | API User is unauthorized to access this feature |
