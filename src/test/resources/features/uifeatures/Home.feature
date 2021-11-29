Feature: Home Page positive scenarios

  Background:
    Given User logout from the application

  @Smoke
  Scenario Outline: OLS-449 : Modify the password using change password option from user badge.
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User change the password
    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on user badge
    And User click on change password option from user badge
    And User enter current password "ChangePassWord"
    Then User validate the presence of new password "<Specifications>" along with their radio button status not Selected "false"
    And User enter new password "New_PassWord" and validate that it contains all specifications "<Specifications>" and corresponding radio button status Selected "true"
    And User enter confirm new password "New_PassWord"
    Then User validates and click on submit button enabled if new password and confirm new password are same
    And User verifies the Password Success message "Password has been changed"
    When User click on user badge
    When User click on Log out button
    When User enter "ChangeUserName" and "New_PassWord"
    When User click on login button
    Then User verifies weather user badge contains first letter of username "ChangeUserName"
#    And User verifies new password "New_PassWord" has been updated in database for user "ChangeUserName"
    Examples:
      | Specifications                                                                    |
      | 8 or more characters,One special character,One uppercase,One lowercase,One number |

  Scenario Outline: OLS-449 : Verify Error message "Password cannot be any of the last 6 passwords previously used" when user tries to change the password which matched old 6 passwords.
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on user badge
    And User click on change password option from user badge
    And User enter current password "ChangePassWord"
    Then User validate the presence of new password "<Specifications>" along with their radio button status not Selected "false"
    And User enter new password and confirm new password which matches the old 6 passwords used
    And User validates entered new password, confirm new password and click on submit button
    And User verify the presence of text "Password cannot be any of the last 10 passwords previously used" in the page
    Examples:
      | Specifications                                                                    |
      | 8 or more characters,One special character,One uppercase,One lowercase,One number |

  Scenario: OLS-449 : Verify Error message "Password doesn't match" when user enter new password and confirm passwords are different.
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on user badge
    And User click on change password option from user badge
    And User enter current password "ChangePassWord"
    And User enter the new password "New_PassWord"
    And User enter confirm new password "ChangePassWord"
#    And User click on "Submit" button
    And User validate Confirm new password error message "Password doesn't match"

  Scenario: OLS-449 : Enter CurrentPassword is wrong and New Password, confirm new passwords are same and click on submit button then verify error message "Password doesn't match"
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on user badge
    And User click on change password option from user badge
    And User enter current password "New_PassWord"
    And User enter the new password "ChangePassWord"
    And User enter confirm new password "ChangePassWord"
    And User validates entered new password, confirm new password and click on submit button
    And User validate Current Password error message "Password doesn't match"

  Scenario: OLS-97 & OLS-99 : Verify options present in the user badge and logout functionality
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on user badge
    And User verifies presence of Edit profile option
    And User verifies presence of Change password option
    And User verifies presence of Help option
    And User click on Log out button
    And User verify the presence of text "Log in" in the page

  @Test
  Scenario: OLS-441 Validate the menus and submenus after logged in to the application
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User verify the presence of "menu" "Home"
    And User verify the presence of "menu" "Cards"
    And User verify the presence of "menu" "Drivers"
    And User verify the presence of "menu" "Vehicles"
    And User verify the presence of "menu" "Transactions"
    And User verify the presence of "menu" "Reports"
    And User verify the presence of "menu" "Admin"

  Scenario: OLS-441 Validate the submenus of Report menu after logged in to the application
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on the "menu" "Reports"
    And User verify the presence of "submenu" "Reports"
    And User verify the presence of "submenu" "Report Templates"
    And User verify the presence of "submenu" "Invoices"

  Scenario: OLS-441 Validate the submenus of Admin menu after logged in to the application
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    When User click on login button
    And User click on the "menu" "Admin"
    And User verify the presence of "submenu" "Users"
    And User verify the presence of "submenu" "Account Information"
    And User verify the presence of "submenu" "Contacts"
    And User verify the presence of "submenu" "Cost Centres"


  Scenario: Validate whether the user is navigating to Home Page in all process of application when we select another account from account picker
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
#    And User unlock the account for user "ChangeUserName" if it is locked
    When User enter "ChangeUserName" and "ChangePassWord"
    And User click on login button
    And User verifies whether the list of accounts for "ChangeUserName" is matching with db


  Scenario: Validate whether the user is navigating to Home Page in all process of application when we select another account from account picker
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User enter "ChangeUserName" and "ChangePassWord"
    And User click on login button
    And User select a random account from account picker associated with the "ChangeUserName"
    Then User verifies whether the user is in the Home Page

  Scenario: OLS User : Validate another window is opened when site locator clicked from Admin module
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User enter "ChangeUserName" and "ChangePassWord"
    And User click on login button
    And User click on the "menu" "Admin"
    Then User click on button "Site locator" using span text
    And User Navigate window "2" and close the window