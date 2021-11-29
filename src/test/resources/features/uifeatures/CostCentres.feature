Feature: OLS User : CostCentres scenarios

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Cost centres"

  @Smoke @Regression
  Scenario: OLS-451 Validate add cost centre functionality and the count of loaded records are matching with database or not
    Then User verifies the count of records in "Cost Centres" page matches with the database or not
    And User click on the "Add Cost Centres button" "+ Cost centre"
    And User enter "costCentre" value in "costCentre" module as "RandomAlphanumeric" having length "9" in "Add" form
    And User enter "name" value in "costCentre" module as "RandomAlphanumericWithFewSpecialChars" having length "10" in "Add" form
    And User enter "description" value in "costCentre" module as "RandomAlphanumeric" having length "50" in "Add" form
    Then User click on "Submit" button
    And User validated success message of module is "New cost centre has been created"
    And User refresh the window
    Then User verifies the count of records in "Cost Centres" page matches with the database or not
    And User validate cost center data is saved in database

  @Smoke @Regression
  Scenario: OLS-468 Validate Edit cost centre functionality
    When User enter search keywords "costCentre-costCentre" by selecting search attribute as "Cost centre"
    Then User click on search icon
    And User click on Three dot icon in module "costCentre" based on search keywords "costCentre-costCentre"
    And User click on button "Edit cost centre"
    And User enter "name" value in "costCentre" module as "RandomAlphanumericWithFewSpecialChars" having length "10" in "Edit" form
#    And User enter "costCentre" value in "costCentre" module as "RandomAlphanumeric" having length "9" in "Edit" form
    And User enter "description" value in "costCentre" module as "RandomAlphanumeric" having length "50" in "Edit" form
    Then User click on "Save" button
    And User validated success message of module is "Cost centre has been updated"
    And User validate cost center data is saved in database

  @Regression
  Scenario: Validate permutations and combinations of contacts module
    And User click on the "Add Cost Centres button" "+ Cost centre"
    When User enter wrong "costCentre" and validate error message "Enter a valid Contact name" in cost centre module
      | 12,ab,abd, |
    When User enter wrong "name" and validate error message "Enter a valid Email Address" in cost centre module
      | hsjeuidkl@##$%MAil.com,lkhasdf@-mail.com,lasjdfl;jkasd@mail.com-,.kjlahdsf@mail.com,lkjahsdf;lk.@gmail.com,akjshdf..k@gmail.com, |
    When User enter wrong "description" and validate error message "Enter a valid Phone number" in cost centre module
      | 452,4569874563214569875462, |

  @Regression
  Scenario: OLS-489 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    When User enter below three characters "ab" in search text box the search icon will not be enabled
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Name"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."
    When User enter wrong search keywords "InvalidSearchKeywordsOrValue" by selecting search attribute as "Cost centre"
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."

  @Regression
  Scenario: OLS-489 Validate Clear Search functionality
    Then User verifies the count of records in "Cost Centres" page matches with the database or not
    When User enter search keywords "costCentre-costCentre" by selecting search attribute as "Cost centre"
    Then User click on search icon
    And User click on clear search icon
    Then User verifies the count of records in "Cost Centres" page matches with the database or not

  @Regression
  Scenario: Validate the sort functionality
    And User verifies the "Cost Centres" page sort option Sort by Newest
    And User verifies the "Cost Centres" page sort option Sort by Oldest

  @Regression @Test
  Scenario: OLS-468 Validate search attribute functionality
    When User enter wrong search keywords "Test" by selecting search attribute as "Cost centre"
    Then User click on search icon
    Then User selects a random search attribute and verify whether the selected attribute is highlighted

#  @Regression
#  Scenario: Validate Export sheet data
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "CostCentres" based on primary key "CostCentre"

  Scenario: OLS User:OMV-1731: User click on "View linked cards" and validate list of cards displayed in cards module
    When User enter search keywords "costCentre-costCentre" by selecting search attribute as "Cost centre"
    Then User click on search icon
    And User click on Three dot icon in module "costCentre" based on search keywords "costCentre-costCentre"
    And User click on button "View linked cards"
    Then User validate no of cards displayed based on the cost centre for user type "OLS"