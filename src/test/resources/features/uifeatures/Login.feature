Feature: Login scenarios

  Background:
    Given User logout from the application

  @Smoke @Regression
  Scenario Outline: OLS-50 & OLS-52: Validate user is able to create a new password for expired password
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    And User change the password created date past "100" days for user "ChangeUserName"
    And User click on login button
    And User verifies the message header "Password expired" and message detail "Please change your password"
    Then User validate the presence of new password "<Specifications>" along with their radio button status not Selected "false"
    And User enter new password "New_PassWord" and validate that it contains all specifications "<Specifications>" and corresponding radio button status Selected "true"
    And User enter confirm new password "New_PassWord"
    Then User validates and click on submit button enabled if new password and confirm new password are same
    And User verifies the message header "Password has been changed" and message detail "Please login with your new credentials"
#    Then User login to gmail with valid username "EmailID" and password "EmailPassword"
#    And User verifies the displayed message "Your password has been changed successfully." along with username "ChangeUserName" in generated email with search text "Z Business Online password change"
#    And User verifies new password "New_PassWord" has been updated in database for user "ChangeUserName"
    Examples:
      | Specifications                                                                    |
      | 8 or more characters,One special character,One uppercase,One lowercase,One number |

  @Smoke
  Scenario: OLS-98 : Login to application with valid credential and validate the letter present in UserBadge
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
#    And User unlock the account for user "UserNameAPI" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    And User click on login button
    Then User verifies weather user badge contains first letter of username "ChangeUserName"
    And User navigated to Home page
    And User click on user badge
    And User click on Log out button
    And User verify the presence of text "Log in" in the page
    And Verify user "ChangeUserName" logged in time in database

  @Smoke
  Scenario: OLS-53 : Validate Forgot password page functionality
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User click on Forgot password link
    And User verify the presence of text "Forgot password" in the page
    And User verify the presence of text "Enter your username to receive a reset link" in the page
    And User verify Send reset email button status by entering username "aab"
    And User enter username is "ChangeUserName"
    And User click on Send reset email button
    And User verify the presence of text "Password reset email sent" in the page
    And User verify the presence of text "Check your email for instructions" in the page
    Then User click on Send reset email button
#    And Verify the button presence Re - send reset email
    And User click on return to login link
    And User verify the presence of text "Log in" in the page

#    @ForgotPassword
#  Scenario Outline: OLS-54 & OLS-55 & OLS-56 :Validate user is able to reset password using ForgotPassword link
#    Given User is on Login page and validate title ""
#    And User unlock the account for user "ChangeUserName" if it is locked
#    When User click on Forgot password link
#    Then User enter username is "ChangeUserName"
#    And User click on Send reset email button
#    Then User login to gmail with valid username "EmailID" and password "EmailPassword"
#    And User reset the password by clicking on here link by using searchText "zenergy-no-reply"
#    And User switched to a window number "2"
#    Then User validate the presence of new password "<Specifications>" along with their radio button status not Selected "false"
#    And User enter new password "New_PassWord" and validate that it contains all specifications "<Specifications>" and corresponding radio button status Selected "true"
#    And User enter confirm new password "New_PassWord"
#    Then User validates and click on submit button enabled if new password and confirm new password are same
#    And User verifies the message header "Password has been changed" and message detail "Please login with your new credentials"
#    And User switched to a window number "1"
#    And User reset the password by clicking on here link by using searchText "zenergy-no-reply"
#    And User switched to a window number "2"
#    And User verify the page header is "Reset password", error message header is "Password reset link has expired"
#    And User verifies new password "New_PassWord" has been updated in database for user "ChangeUserName"
#    Examples:
#      | Specifications                                                                    |
#      | 8 or more characters,One special character,One uppercase,One lowercase,One number |

  @Smoke
  Scenario: OLS-150 OLS-271: Validate if user enter wrong password reached maximum password attempts which is partner specific then account will be locked and user will unlock the account
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter valid userName "ChangeUserName" and wrong password "ChangePassWord" and click on login button for "MaxTimes"
    And User validate presence of "Login Error" field with "div" tag
    And User validate presence of "Your account will be locked after one more failed attempt" field with "div" tag
    When User enter valid userName "ChangeUserName" and wrong password "ChangePassWord" and click on login button for "MaxTimes+1"
    And User validate presence of "User is locked" field with "h1" tag
    And User validate presence of "User is locked due to invalid login attempts" field with "div" tag
    Given User update the LOGON_STATUS_CID to "2501" for user "ChangeUserName"

  @Smoke
  Scenario: OLS-272 : Validate for locked account user will not be able to generate reset password link to their email
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
#    When User enter valid userName "ChangeUserName" and wrong password "ChangePassWord" and click on login button for "MaxTimes"
#    And User verify the page header is "Account locked", error message header is "Your account has been locked."
#    And User refresh the window
    Given User update the LOGON_STATUS_CID to "2503" for user "ChangeUserName"
    When User click on Forgot password link
    Then User enter username is "ChangeUserName"
    And User click on Send reset email button
    And User verify the presence of text "User is locked" in the page
    And User verify the presence of text "User is locked due to invalid login attempts" in the page
    Given User update the LOGON_STATUS_CID to "2501" for user "ChangeUserName"

  @Smoke
  Scenario: OLS-378 : Validate in Login Page "User is locked" error message for Permanent locked out account
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    Given User update the LOGON_STATUS_CID to "2504" for user "ChangeUserName"
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User verify the presence of text "User is locked" in the page
    And User verify the presence of text "User is permanently locked" in the page
    Given User update the LOGON_STATUS_CID to "2501" for user "ChangeUserName"

  @Smoke
  Scenario: OLS-379 & OLS-384 : Validate in Login Page "Access Denied" error message for InActive account in login page
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User enter "ChangeUserName" and "ChangePassWord"
    Then User update the LOGON_STATUS_CID to "2506" for user "ChangeUserName"
    When User click on login button
    And User validate presence of "Access Denied" field with "div" tag
    And User validate presence of "You do not have access at this time" field with "div" tag
    And User refresh the window
    When User click on Forgot password link
    When User enter username is "ChangeUserName"
    And User click on Send reset email button
    And User validate presence of "Access Denied" field with "div" tag
    And User validate presence of "You do not have access at this time" field with "div" tag
    Given User update the LOGON_STATUS_CID to "2501" for user "ChangeUserName"

    ##############    Login Negative scenarios    ###############
  @Smoke
  Scenario: OLS-47 : Login to application with invalid username and invalid password
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter username is "ChangeUserName"
    Then User enter wrong password "ChangePassWord"
    And User validated the eye icon status and click on password field
    And User click on login button
    And User verify the message header is "Invalid credentials", error message detail is "Login information is incorrect"

  @Smoke
  Scenario: Validate login button is enabled if username and password entered 3 or more than 3 characters
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User verify login button status by entering username "a" and password "b"
    And User verify login button status by entering username "aa" and password "bb"
    And User verify login button status by entering username "aaa" and password "bbb"
    And User verify login button status by entering username "aaaa" and password "bbbb"

  @Smoke
  Scenario: OLS-47 : Enter username which is having more than 30 characters and validate the error message
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter wrong username "jshkeuikslahehsjlgkshiljhksnjlklaksjdfalskdjf;alksjd;lfaksdfj"
    Then User enter wrong password "ChangePassWord"
    And User click on login button
    And User verify the message header is "Invalid credentials", error message detail is "Login information is incorrect"

  @LoginModule
  Scenario: OLS-475: Expired Password Page: Validate error message "Password cannot be any of the last 6 passwords previously used" when user tries to change the password which matched old 6 passwords
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    And User change the password created date past "90" days for user "ChangeUserName"
    And User click on login button
    And User verifies the message header "Password expired" and message detail "Please change your password"
    And User enter new password and confirm new password which matches the old 6 passwords used
    And User validates entered new password, confirm new password and click on submit button
    Then User validate presence of "Password cannot be any of the last 6 passwords previously used" field with "mat-error" tag
    And User change the password created date past "10" days for user "ChangeUserName"

#  @LangTrans
#  Scenario: Google translator words
#    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    When User selects languages from "English" to "French"