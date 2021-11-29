Feature: CSR User : Country Users- Contact Scenarios

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User click on button "Home" using span text
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    When User get an account number which has option "IS_ACCEPT_CONTACTS = 'N'" in hierarchy
    And User select or enter field value "accountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Contacts"

  @Smoke @Regression
  Scenario: Validate user is able to delete the contact or not and verify in database also the deleted contact will not be available
    And User click on the "Add Contacts button" "+ Contact"
    And User enter "name" as "RandomAlphanumeric" in module "contacts" having length "10" in "Add" form
    And User enter "jobTitle" as "RandomAlphanumeric" in module "contacts" having length "10" in "Add" form
    And User enter "mobilePhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "otherPhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "fax" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in module "contacts" having length "10" in "Add" form
    And User select value "contactTypeDescription" in "contacts" module by clicking drop down "contactType"
    And User validate the non editable value of field "accountNumber" in module "contacts" in form "Add"
    And User click on Next button
    And User enter "addressLine" as "5919Trussville" in module "contacts" having length "90" in "Add" form
    And User enter "suburb" as "Birmingham" in module "contacts" having length "10" in "Add" form
    And User enter "postalCode" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User clicks the state drop down and select random value
    Then User click drop down "Country" using locator "mav-select[ng-reflect-name='country']>mat-select" which has locator type "cssSelector" then select value "Random"
    And User verify by default "Use same as Contact Address" check box is selected or not in "add" form
    And User click on "Submit" button
    When User enter search keywords "contacts-name" by selecting search attribute as "Name"
    Then User click on search icon
    And User click on Three dot icon in module "contacts" based on search keywords "contacts-name"
    And User click on button "Delete contact"
    And User verifies the text "Once this is deleted it cannot be undone." present in Delete contact pop up
    And User click on button "Delete" using span text
    And User validated success message of Edit status is "Contact has been deleted"

#  @Regression
#  Scenario: Validate permutations and combinations of contacts module
#    And User click on the "Add Contacts button" "+ Contact"
#    When User enter wrong "contactName" and validate error message "Enter a valid contact name"
#      |  |
#    When User enter wrong "emailAddress" and validate error message "Enter a valid Email Address"
#      | hsjeuidkl@##$%MAil.com,lkhasdf@-mail.com,lasjdfl;jkasd@mail.com-,.kjlahdsf@mail.com,lkjahsdf;lk.@gmail.com,akjshdf..k@gmail.com, |
#    When User enter wrong "mobilePhone" and validate error message "Enter a valid Phone number"
#      | 452,4569874563214569875462, |
#    And User enter "contactName" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Add" form
#    And User enter "jobTitle" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Add" form
#    And User enter "mobilePhone" as "Numeric" in module "contacts" having length "10" in "Add" form
#    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in module "contacts" having length "10" in "Add" form
#    And User select value "contactTypeDescription" in "contacts" module by clicking drop down "contactType"
#    And User click on Next button
#    When User enter wrong "addressLine" and validate error message "Enter a valid address"
#      | ,as |
#    When User enter wrong "suburb" and validate error message "Enter a valid city name"
#      | ,as |
#    When User enter wrong "postalCode" and validate error message "Enter a valid postal zip code"
#      | ,1,12,234 |

  @Regression
  Scenario: Validate add contact functionality and the count of loaded records are matching with database or not
#    Then User verifies the count of records in "contacts" page matches with the database or not
    And User click on the "Add Contacts button" "+ Contact"
    And User enter "contactName" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Add" form
    And User enter "jobTitle" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Add" form
    And User enter "mobilePhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "otherPhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "fax" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in module "contacts" having length "10" in "Add" form
    And User select value "contactTypeDescription" in "contacts" module by clicking drop down "contactType"
    And User validate the non editable value of field "accountNumber" in module "contacts" in form "Add"
    And User click on Next button
    And User enter "addressLine" as "5919Trussville" in module "contacts" having length "90" in "Add" form
    And User enter "suburb" as "Birmingham" in module "contacts" having length "90" in "Add" form
    And User enter "postalCode" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User clicks the state drop down and select random value
    Then User click drop down "Country" using locator "mav-select[ng-reflect-name='country']>mat-select" which has locator type "cssSelector" then select value "Random"
    And User verify by default "Use same as Contact Address" check box is selected or not in "add" form
    And User click on "Submit" button
    And User validated success message of module is "New contact has been created"
#    And User validate contact data is saved in database

#  @Regression
#  Scenario: Validate Export Functionality of Contacts module
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "contacts" based on primary key "NAME"


  @Smoke @Regression
  Scenario: OLS-469 Validate edit contact and contact address form functionality
    When User enter search keywords "contacts-contactName" by selecting search attribute as "Name"
    Then User click on search icon
    And User click on Three dot icon in module "contacts" based on search keywords "contacts-contactName"
    And User click on button "Edit contact information"
#    And User verifies the "Save" button is disabled using locator "button"
    And User enter "contactName" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Edit" form
    And User enter "jobTitle" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Edit" form
    And User enter "mobilePhone" as "Numeric" in module "contacts" having length "10" in "Edit" form
    And User enter "otherPhone" as "Numeric" in module "contacts" having length "10" in "Edit" form
    And User enter "fax" as "Numeric" in module "contacts" having length "10" in "Edit" form
    And User enter "emailAddress" as "test@wexinc.com" in module "contacts" having length "10" in "Edit" form
    And User verify the text field "accountNumber" is disabled in module "contacts" in form "Edit"
    And User verify dropdown "contactType" is selected with value "contacts-contactType" and disabled in module "contacts"
    And User select the contact as primary by selecting the checkbox of "Set as primary contact"
    And User click on "Save" button
    And User validated success message of module is "Contact has been updated"
    When User enter search keywords "contacts-contactName" by selecting search attribute as "Name"
    Then User click on search icon
    And User click on Three dot icon in module "contacts" based on search keywords "contacts-contactName"
    And User click on button "Edit contact address"
#    And User verifies the "Save" button is disabled using locator "button"
    And User enter "addressLine" as "70 Sutton Wick Lane" in module "contacts" having length "90" in "Edit" form
    And User enter "suburb" as "Brridgemere" in module "contacts" having length "90" in "Edit" form
    And User enter "postalCode" as "Numeric" in module "contacts" having length "10" in "Edit" form
    And User verify by default "Use same as Contact Address" check box is selected or not in "Edit" form
    And User click on "Save" button
    And User validated success message of module is "Contact has been updated"
#    And User validate contact data is saved in database

  @Regression
  Scenario: OLS-452 Validate "All contact levels" and "All contact types" filter functionalities
    When User select filter values in contact module then click on Apply button and validate filter results in database

  @Regression
  Scenario: OLS-452 Validate "No results found,Please update your search keywords." message when user enter invalid search text for "Name" attribute
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Name"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."

  @Regression
  Scenario: OLS-452 Validate "No results found,Please update your search keywords." message when user enter invalid search text for ""Email address" attribute
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Email address"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."

  @Regression
  Scenario: OLS-452 Validate Clear Search functionality
#    Then User verifies the count of records in "contacts" page matches with the database or not
    When User enter search keywords "contacts-name" by selecting search attribute as "Name"
    Then User click on search icon
#    And User validate searched record in the database for module "contacts" based on search keywords "contacts-contactName"
    And User click on clear search icon
#    Then User verifies the count of records in "contacts" page matches with the database or not

  @Regression
  Scenario: Validate the sort functionality
    And User verifies the "Contacts" page sort option Sort by Newest
    And User verifies the "Contacts" page sort option Sort by Oldest

  @Regression
  Scenario: OLS-468 Validate search attribute functionality
    When User enter wrong search keywords "Attribute" by selecting search attribute as "Email address"
    Then User click on search icon
    Then User selects a random search attribute and verify whether the selected attribute is highlighted

  @Regression
  Scenario: Validate the back button
#    Then User verifies the count of records in "contacts" page matches with the database or not
    And User click on the "Add Contacts button" "+ Contact"
    And User enter "name" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Add" form
    And User enter "jobTitle" as "RandomAlphanumericWithFewSpecialChars" in module "contacts" having length "10" in "Add" form
    And User enter "mobilePhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "otherPhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "fax" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User select value "contactTypeDescription" in "contacts" module by clicking drop down "contactType"
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in module "contacts" having length "10" in "Add" form
    And User validate the non editable value of field "accountNumber" in module "contacts" in form "Add"
    And User click on Next button
    And User click on button "Back"
    And User verify whether the Next button is enabled