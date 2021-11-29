@HomeDashboard
Feature: Homedashborad

  @getTotaltransactions
  Scenario Outline: OLS-1147-To get the total transactions for a selected period
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user must be able to get the sum of transactions based on the period range as "<periodRange>"
    Then validate the response status code "<statusCode>"
    Then verify the total spending amount of transactions from response based on period range as "<periodRange>"

    Examples: 
      | testCaseName                                                                                                                                                                 | statusCode | periodRange                         |
      | TC-01-OLS-1147-Verify user should able to get the total transaction for passing the customer number and date range based on period range as last month                       |        200 | lastMonth                           |
      | TC-02-OLS-1147-Verify user should able to get the total transaction for passing the customer number and date range based on period range as last three month                 |        200 | lastThreeMonth                      |
      | TC-03-OLS-1147-Verify user should able to get the total transaction for passing the customer number and date range based on period range as last six month                   |        200 | lastSixMonth                        |
      | TC-04-OLS-1147-Verify user should able to get the total transaction for passing the customer number and date range and period type as week for client processing date month  |        200 | clientProcessingDateMonthAsWeek     |
      | TC-05-OLS-1147-Verify user should able to get the total transaction for passing the customer number and date range and period type as month for client processing date month |        200 | clientProcessingDateMonthAsMonth    |
      | TC-06-OLS-1147-Verify user should able to get the total transaction for passing the customer number and date range and period type as week for last month                    |        200 | lastMonthAsWeek                     |
      | TC-07-OLS-1147-Verify user should not able to get the total transaction for passing the unassociated customer number and date range and period type as week for last month   |        401 | lastMonthAsWeekForUnassociatedCusNo |
