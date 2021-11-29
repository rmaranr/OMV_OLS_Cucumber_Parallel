Feature: Client User : Users Module

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User select client and country based on logged in user "CountryUserName"
    And User click on button "Home" using span text

  Scenario: Validate add user functionality without Pinning account
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    And User make sure account is not pinned for logged in user
    And User click on the "Add Users button" "+ User"
    And User enter "fullName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Add" form
    And User enter "emailAddress" as "sivathanu.sankarmal@wexinc.com" in "users" module having length "20" in "Add" form
    And User enter "mobilePhone" as "Numeric" in "users" module having length "10" in "Add" form
    And User enter "otherPhone" as "Numeric" in "users" module having length "8" in "Add" form
    And User enter "userName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Add" form
    And User select value "usersRoleDescription" in "users" module by clicking drop down "role"
    Then User click on Next button
    Then User click on "+Add" based on tag name "a"
    And User click button "checkbox" using tag name "div", attribute name "class", attribute value "mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin" based on position "2"
    Then User click on "Add" based on tag name "span"
    And User click on "Submit" button
#    And User validated success message of module is "New user has been created"

  Scenario: Validate add user functionality with Pinning account
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select or enter value from a field "country" based on its behavior "dropdown" for user "CountryUserName" in module "accounts"
    Then User get an account number which is in "Active" status for "OMV" user
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    And User click on the "Add Users button" "+ User"
    And User enter "fullName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Add" form
    And User enter "emailAddress" as "sivathanu.sankarmal@wexinc.com" in "users" module having length "20" in "Add" form
    And User enter "mobilePhone" as "Numeric" in "users" module having length "10" in "Add" form
    And User enter "otherPhone" as "Numeric" in "users" module having length "8" in "Add" form
    And User enter "userName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Add" form
    And User select value "usersRoleDescription" in "users" module by clicking drop down "role"
    Then User click on Next button
    Then User click on "+Add" based on tag name "a"
    And User click button "checkbox" using tag name "div", attribute name "class", attribute value "mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin" based on position "5"
    Then User click on "Add" based on tag name "span"
    And User click on "Submit" button
#    And User validated success message of module is "New user has been created"

  Scenario: Validate edit functionality of Users
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on Three dot icon in module "users" based on search keywords "users-fullName"
    And User click on button "Edit profile"
    And User enter "fullName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Edit" form
    And User enter "emailAddress" as "sivathanu.sankarmal@wexinc.com" in "users" module having length "20" in "Edit" form
    And User enter "mobilePhone" as "Numeric" in "users" module having length "8" in "Edit" form
    And User enter "otherPhone" as "Numeric" in "users" module having length "8" in "Edit" form
    Then User click on "Save" button
    And User validated success message of module is "User profile has been updated"

    Scenario: Validate Account Access functionality in Users
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    When User enter search keywords "Users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on button contains Three dot icon
    And User click on button " Account access "
    Then User click on "+Add" based on tag name "a"
    Then User click on "Add" based on tag name "span"
    And User click on "Save" button
    And User validated success message of Edit status is "User profile has been updated"

  Scenario: User select status from dropdown for newly created user and change the status
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    When User enter search keywords "Users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on button contains Three dot icon
    And User click on button "Change status"
    Then User verify the selected status is match with the user status "users-status"
    And User select another status "Inactive" for user "users-userName"
    And User click on "Submit" button
    Then User validate "snackbar-text" message "User status has been updated" in "users" module
    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on button contains Three dot icon
    And User click on button "Change status"
    And User select another status "Permanent lockout" for user "users-userName"
    And User click on "Submit" button
    And User validated success message of Edit status is "User status has been updated"
    When User enter search keywords "users-EmailAddress" by selecting search attribute as "Email address"
    Then User click on search icon
    And User click on button contains Three dot icon
    And User click on button "Change status"
    Then User verify the selected status is match with the user status "users-status"
    And User select another status "Active" for user "users-userName"
    And User click on "Submit" button
    And User validated success message of Edit status is "User status has been updated"

#  Scenario: Validate "All status" and "All roles" filter functionality
#    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
#    And User select or enter value from a field "country" based on its behavior "dropdown" for user "CountryUserName" in module "accounts"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#    And User click on the "menu" "Admin"
#    And User click on the "submenu" "Users"
#    When User select filter values then click on Apply button and validate filter results in database

  Scenario: validate the sort functionality
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select or enter value from a field "country" based on its behavior "dropdown" for user "CountryUserName" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    And User verifies the "Users" page sort option Sort by Newest
    And User verifies the "Users" page sort option Sort by Oldest
