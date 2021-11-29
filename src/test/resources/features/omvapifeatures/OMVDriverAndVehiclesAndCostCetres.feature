Feature: API - OMV Drivers, Vehicles and cost center module

  @DriverDetailsForCSR
  Scenario Outline: OMV- 468 Driver details for CSR Login with single select account number
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to gets the driver details based on customer numbers "<scenarioType>"
    Then validate the response status code "<statusCode>"
    Then Verify the actual and expected response based on "<statusNumber>" and "<statusMessage>" and moduleType as "<moduleType>"
    Examples:
      | testCaseName                                                                                         | statusCode | scenarioType     | statusNumber | statusMessage           | moduleType |
      | TC-01-OMV-468- Verify user able to get the list of all driver details for single customer number     | 200        | singleCusNo      |              |                         | drivers    |
      | TC-02-OMV-468- Verify user not able to get the list of all driver details for invalid account number | 400        | invalidAccountNo | 4018         | Invalid Customer Number | drivers    |

  @VehiclesDetailsForCSR
  Scenario Outline: OMV-469 Vehicles details for CSR Login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to gets the vehicles details based on customer numbers for "<scenarioType>"
    Then validate the response status code "<statusCode>"
    Then Verify the actual and expected response based on "<statusNumber>" and "<statusMessage>" and moduleType as "<moduleType>"
    Examples:
      | testCaseName                                                                                       | statusCode | scenarioType     | statusNumber | statusMessage           | moduleType |
      | TC-01-OMV-469- Verify user able to get the list of all vehicles details for single customer number | 200        | singleCusNo      |              |                         | vehicles   |
      | TC-02-OMV-949- Verify user able to get the list of all vehicles details for invalid account number | 400        | invalidAccountNo | 4018         | Invalid Customer Number | vehicles   |


  @CostCentresDetailsForCSR
  Scenario Outline: OMV-470 List of cost centres details for CSR Login
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user able to gets the List of cost centres details based on customer numbers for "<scenarioType>"
    Then validate the response status code "<statusCode>"
    Then Verify the actual and expected response based on "<statusNumber>" and "<statusMessage>" and moduleType as "<moduleType>"
    Examples:
      | testCaseName                                                                                   | statusCode | scenarioType     | statusNumber | statusMessage           | moduleType  |
      | TC-01-OMV-469- Verify user able to get the list of all cost centres for single customer number | 200        | singleCusNo      |              |                         | costCentres |
      | TC-02-OMV-949- Verify user able to get the list of all cost centres for invalid account number | 400        | invalidAccountNo | 4018         | Invalid Customer Number | costCentres |