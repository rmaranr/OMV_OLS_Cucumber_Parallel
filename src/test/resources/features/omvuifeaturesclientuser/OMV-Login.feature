Feature: Client User : This feature contains Login related scenarios

  @UI-Login
  Scenario Outline: Client User : Login to the OMV application using valid credentials and verify the user is navigated to Home page or not
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
#    Then User validate Home page has been loaded

    Examples:
      | UserName       | PassWord       |
      | ClientUserName | ClientPassword |

  @UI-Login
  Scenario Outline: Client User : User enter valid user name and invalid password then verify error message
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    When User enter username is "<UserName>"
    Then User enter wrong password "<PassWord>"
    And User validated the eye icon status and click on password field
    And User click on login button
    And User verify the message header is "Invalid credentials", error message detail is "Login information is incorrect"

    Examples:
      | UserName       | PassWord       |
      | ClientUserName | ClientPassword |

  @UI-Login
  Scenario: Client User : OMV-2254 User validate Help and contact us links available and getting navigated to corresponding website before and after login to application
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User click on "Contact us" based on tag name "a"
    And User Navigate window "2" and close the window
    And User switched to a window number "1"
    Then User click on "Help" based on tag name "a"
    And User Navigate window "2" and close the window
    And User switched to a window number "1"
    When User enter "CountryUserName" and "CountryPassword"
    And User click on login button
    Then User click on "Contact us" based on tag name "a"
    And User Navigate window "2" and close the window
    And User switched to a window number "1"
    Then User click on "Help" based on tag name "a"
    And User Navigate window "2" and close the window
    And User switched to a window number "1"