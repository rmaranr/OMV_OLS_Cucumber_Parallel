Feature: Validate the Footer Section

  @Regression
  Scenario: OLS-57 & OLS-153: Validate the Pre-Login Footer Link
    Given User refresh the window
    Given User logout from the application
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User change the password
    When User Click on the Login link in the footer section and navigate to the login page
    And Click on the contact us in the footer section and navigate to the contact us page
    And Click on Term of use in the footer section and navigate to the Term of use page
    And Click on Privacy Policy in the footer section and navigate to the Privacy policy page
    Then Click on Signup link in login page

  @Regression
  Scenario: OLS-101 : Validate the Post-Login Footer Links
    Given User refresh the window
    Given User logout from the application
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    When User enter "ChangeUserName" and "ChangePassWord"
    And User click on login button
    And User click on button "Home" using span text
    When User Click on the Home link in the footer section
    And Click on the contact us in the footer section and navigate to the contact us page
    And Click on Term of use in the footer section and navigate to the Term of use page
    And Click on Privacy Policy in the footer section and navigate to the Privacy policy page