@OLSAccessGroupModule
Feature: Access group

  @OLSAccessGroups
  Scenario Outline: User can able to check the all access groups
    Given User gets the base url EndPoint and passing "<testName>" to create test
    Then user hit the API and verify the user with access group as "<accessGrp>"

    Examples:
      | testName                                                                                                    | statusCode | accessGrp          |
      | TC-01-OLS-FullAccessGroup- Verify user should able to check the access for all end points                                           | 200        | fullAccess         |
      | TC-02-OLS-ReadOnlyAccessGroup- Verify user should able to check the access for all end points     | 200        | readOnlyAccess     |
      | TC-03-OLS-CustomerUserAccessGroup- Verify user should able to check the access for all end points | 200        | customerUserAccess |