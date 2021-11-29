@LogoutModule
Feature:  Logout Module

  Scenario: User Logout from the IFCS API's
    Given Get access token from the login API
    When user post the request for logout endpoint "/logout"
    Then the response status code should be 200
    Then validated the LOGON_STATUS_CID in DB should be "2502"