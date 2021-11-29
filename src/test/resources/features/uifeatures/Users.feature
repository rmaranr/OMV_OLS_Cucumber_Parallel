Feature: OLS User : Users Module

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"

  @Smoke @Regression
  Scenario: OLS-177 Validate add user functionality and the count of loaded records are matching with database or not
    Then User verifies the count of records in "Users" page matches with the database or not
    And User click on the "Add Users button" "+ User"
    And User enter "fullName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Add" form
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in "users" module having length "20" in "Add" form
    And User enter "mobilePhone" as "Numeric" in "users" module having length "8" in "Add" form
    And User enter "otherPhone" as "Numeric" in "users" module having length "8" in "Add" form
    And User select value "usersRoleDescription" in "users" module by clicking drop down "role"
    And User enter "userName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Add" form
    Then User click on Next button
    And User click on radio button based on the account number "accountNumber"
    And User click on "Submit" button
    And User validated success message of module is "New user has been created"
    And User validate user data is saved in database

  @Smoke @Regression
  Scenario: OLS-177 Validate edit user functionality
    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on Three dot icon in module "users" based on search keywords "users-fullName"
    And User click on button "Edit profile"
    And User enter "fullName" as "RandomAlphanumericWithFewSpecialChars" in "users" module having length "8" in "Edit" form
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in "users" module having length "20" in "Edit" form
    And User enter "mobilePhone" as "Numeric" in "users" module having length "8" in "Edit" form
    And User enter "otherPhone" as "Numeric" in "users" module having length "8" in "Edit" form
#    And User select value "usersRoleDescription" in "users" module by clicking drop down "role"
    Then User click on "Save" button
    And User validated success message of module is "User profile has been updated"
    And User validate user data is saved in database

  @Regression @Smoke @Users
  Scenario: OLS-177 Validate Account Access functionality in Users
    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on Three dot icon in module "users" based on search keywords "users-fullName"
    And User click on button "Account access"
    Then User select all accounts from the list of accounts if more than one account displayed for logged in user "UserName"
    And User click on "Save" button
    And User validated success message of Edit status is "User profile has been updated"
#    And User validate the accounts should match for logged in user "UserName" and latestUpdated user "users-userName"

  @Regression
  Scenario: OLS-177 User select status from dropdown for newly created user in Change Status form
    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User click on Three dot icon in module "users" based on search keywords "users-fullName"
    And User click on button "Change status"
    Then User verify the selected status is match with the user status "users-status"
    And User select another status "Permanent lockout" for user "users-userName"
    And User click on "Submit" button
    Then User validate "snackbar-text" message "User status has been updated" in "users" module
    And User validate the newly selected status "Permanent Lockout" for user "users-userName" is stored in database or not
#    And User click on clear search icon
#    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
#    Then User click on search icon
#    And User click on Three dot icon in module "users" based on search keywords "users-fullName"
#    And User click on button "Change status"
#    Then User verify the selected status is match with the user status "users-status"
#    And User select another status "Inactive" for user "users-userName"
#    And User click on "Submit" button
#    And User validated success message of Edit status is "User status has been updated"
#    And User validate the newly selected status "Inactive" for user "users-userName" is stored in database or not
#    And User click on clear search icon
#    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
#    Then User click on search icon
#    And User click on Three dot icon in module "users" based on search keywords "users-fullName"
#    And User click on button "Change status"
#    Then User verify the selected status is match with the user status "users-status"
#    And User select another status "Active" for user "users-userName"
#    And User click on "Submit" button
#    And User validated success message of Edit status is "User status has been updated"
#    And User validate the newly selected status "Active" for user "users-userName" is stored in database or not

  @Regression
  Scenario: OLS-197 Validate "All status" and "All roles" filter functionality
    When User select filter values then click on Apply button and validate filter results in database

  @Regression
  Scenario: Validate permutations and combinations of users module
    And User click on the "Add Users button" "+ User"
    When User enter wrong "userName" and validate error message "Enter a valid User Name"
      | abcde,ABCD,aB._-,12a._,1234ABCDagebd._-sk._-jeuBHt._jkllkajsd,124hkajs.,.234kkdfg,AvChjk1&267._-Ahjkl,AvChjk=1267._-Ahjkl,AvChjk+1267._-Ahjkl,AvChjk'1267._-Ahjkl,AvChjk<1267._-Ahjkl,AvChjk>1267._-Ahjkl |
    When User enter wrong "fullName" and validate error message "Enter a valid Full Name"
      | Ab,AbGHjuDk SuejDks SehikdjwdkjEylakjsdf alskdfj,AbGHjuDkSuejDksSehikdjwdkjEyujklasjdfhlkj;asdfkl |
    When User enter wrong "emailAddress" and validate error message "Enter a valid Email Address"
      | hsjeuidkl@##$%MAil.com,lkhasdf@-mail.com,lasjdfl;jkasd@mail.com-,.kjlahdsf@mail.com,lkjahsdf;lk.@gmail.com,akjshdf..k@gmail.com |
    When User enter wrong "mobilePhone" and validate error message "Enter a valid Mobile Phone"
      | 452,4569874563214569875462,lkajdf |
    When User enter wrong "otherPhone" and validate error message "Enter a valid phone number"
      | 587452632541896542365987,12 |

  @Regression
  Scenario: OLS-197 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Full name"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "User name"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Email address"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."

  @Regression
  Scenario: OLS-197  Clear Search functionality
    Then User verifies the count of records in "Users" page matches with the database or not
    When User enter search keywords "users-fullName" by selecting search attribute as "Full name"
    Then User click on search icon
    And User validate searched record in the database for module "users" based on search keywords "users-fullName"
    And User click on clear search icon
    Then User verifies the count of records in "Users" page matches with the database or not

#  @Regression
#   Scenario: Validate Export sheet data
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "users" based on primary key "UserName"

  @Regression
  Scenario: Validate the sort functionality
    And User verifies the "Users" page sort option Sort by Newest
    And User verifies the "Users" page sort option Sort by Oldest

  @Regression
  Scenario: OLS-468 Validate search attribute functionality
    When User enter wrong search keywords "Attribute Testing" by selecting search attribute as "Full name"
    Then User click on search icon
    Then User selects a random search attribute and verify whether the selected attribute is highlighted
