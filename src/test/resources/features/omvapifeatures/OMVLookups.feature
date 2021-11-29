Feature: API - Look ups

  @Countrieslookup
  Scenario Outline: OMV-47 To get all the countries
   # Given user gets the base url for lookup API and passing "<testCaseName>" to create test
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to get the list of countries based on user access level "<accessLevel>"
    Then validate the response status code "<statusCode>"
    Then Validates the countries from response and DB based on access level "<accessLevel>"

    Examples:
      | testCaseName                                                                    | statusCode | accessLevel  |
      | Tc-01-OMV-47- Verify user able to get the list countries based on client level  | 200        | clientLevel  |
      | Tc-02-OMV-47- Verify user able to get the list countries based on country level | 200        | countryLevel |


  @TransactionSearchLookupForCostCentre
  Scenario Outline: OMV-101 To get all the cost centre values
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to get the list of cost centres based on user access level "<accessLevel>"
    Then validate the response status code "<statusCode>"
    Then Validate the cost centre values from response and DB based on access level "<accessLevel>"

    Examples:
      | testCaseName                                                                                | statusCode | accessLevel  |
      | Tc-01-OMV-101- Verify user able to get the list of cost centre values based on client level | 200        | clientLevel  |
      | Tc-02-OMV-101- Verify user able to get the list cost centre values based on country level   | 200        | countryLevel |

  @TransactionSearchLookupForCustomerNumbers
  Scenario Outline: OMV-101 To get all the customer numbers
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to get the list of customer numbers based on user access level "<accessLevel>"
    Then validate the response status code "<statusCode>"
    Then Validate the customer number from response and DB based on access level "<accessLevel>"

    Examples:
      | testCaseName                                                                              | statusCode | accessLevel  |
      | Tc-03-OMV-101- Verify user able to get the list of customer numbers based on client level | 200        | clientLevel  |
      | Tc-04-OMV-101- Verify user able to get the list customer numbers based on country level   | 200        | countryLevel |


  @TransactionSearchLookupForLocationNumbers
  Scenario Outline: OMV-101 To get all the location numbers
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to get the list of location numbers based on user access level "<accessLevel>"
    Then validate the response status code "<statusCode>"
    Then Validate the location numbers from response and DB based on access level "<accessLevel>"

    Examples:
      | testCaseName                                                                              | statusCode | accessLevel  |
      | Tc-05-OMV-101- Verify user able to get the list of location numbers based on client level | 200        | clientLevel  |
      | Tc-06-OMV-101- Verify user able to get the list location numbers based on country level   | 200        | countryLevel |