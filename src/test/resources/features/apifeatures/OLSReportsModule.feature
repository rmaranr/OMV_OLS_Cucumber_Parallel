@ReportsModule
Feature: Search reports

  @SearchReportWithFilterCombination
  Scenario Outline: OLS-147-To get all the reports based on customer number
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get the reports based on the filter combination "<filterCombination>"
    Then validate the response status code "<statusCode>"
    Then Validate the stored report details from response

    Examples: 
      | testCaseName                                                                                                     | filterCombination              | statusCode |
      | TC-01-OLS-147-Verify user should able to get the reports based on customer number                                | associatedUser                 |        200 |
      | TC-02-OLS-147-Verify user should able to get the reports based on report type filter                             | reportTypeFilterWithCustomerNo |        200 |
      | TC-03-OLS-147-Verify user should able to get the reports based on date range filter                              | dateRangeWithCustomerNo        |        200 |
      | TC-04-OLS-147-Verify user should able to get the reports based on filename                                       | filenamewithCustomerNo         |        200 |
      | TC-05-OLS-147-Verify user should able to get the reports based on the combination of report type with date range | reportTypeWithdateRange        |        200 |
      | TC-06-OLS-147-Verify user should able to get the reports based on the combination of report type with filename   | reportTypeWithFileName         |        200 |
      | TC-07-OLS-147-Verify user should able to get the reports based on the combination of all filters                 | allFilterCombination           |        200 |
      | TC-08-OLS-147-Verify user should not able to get the reports for invalid customer number                         | invalidCustomerNo              |        401 |
      | TC-09-OLS-147-Verify user should not able to get the reports for invalid report type                             | invalidReportType              |        400 |

  @LookupAPIForReportTypes
  Scenario Outline: OLS-145-Get all the report description based on stored report type
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get all the report type description for "<reportTypeValue>" report
    Then validate the response status code "<statusCode>"

    Examples: 
      | testCaseName                                                                                  | statusCode | reportTypeValue |
      | TC-01-OLS-145-Verify user should able to get the report description based on stored report    |        200 | Stored          |
      | TC-02-OLS-145-Verify user should able to get the report description based on scheduled report |        200 | scheduled       |

  @GetReportCategoriesAndReportTypes
  Scenario Outline: OLS-146 Verify user should able to get the report categries and report types
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get the report categories
    Then validate the response status code "<statusCode>"
    Then Validate the report categories from response
    When user should able to get the report types based on report category
    Then validate the response status code "<statusCode>"
    Then validate the report types from response

    Examples: 
      | testCaseName                                                                                                                                                                    | statusCode |
      | TC-01-OLS-146, TC-02-OLS-146 and TC-03-OLS-146-Verify user should able to get the list of report categories , report types and validated the isAdhoc and isOnlineScheduled as Y |        200 |

  @GetReportParams
  Scenario Outline: OLS-148 Verify user should able to get the report params based on report types
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get the report params based on selected report types and customer number "<customerType>"
    Then validate the response status code "<statusCode>"
    Then validate the response message for status number "<statusNumber>" and statusMessage "<statusMessage>"

    Examples: 
      | testCaseName                                                                                                                   | customerType         | statusCode | statusNumber | statusMessage                                    |
      | TC-01-OLS-148- Verify user should able to the report params based on customer number and selected report type                  | associatedCustomerNo |        200 |              |                                                  |
      | TC-02-OLS-148- Verify user should not able to the report params based on invalid customer number and selected report type      | invalidCustomerNo    |        400 |         4018 | Invalid Customer Number                          |
      | TC-03-OLS-148- Verify user should not able to the report params based on unassociated customer number and selected report type | unassociatedCusNo    |        401 |        97102 | API User is unauthorized to access this customer |

  @CreateScheduleReport
  Scenario Outline: OLS-162 Verify user should able to create a scheduled report
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user should able to get the report params based on selected report types and customer number "<customerType>"
    When user wants to create scheduled report with key as "reportAssignmentParameters" and value as "<reportAssignmentParameters>"
    When user wants to create scheduled report with key as "reportType" and value as "<reportType>"
    When user wants to create scheduled report with key as "isEnabled" and value as "<isEnabled>"
    When user wants to create scheduled report with key as "isAdhocReport" and value as "<isAdhocReport>"
    When user wants to create scheduled report with key as "isAttachmentCompressed" and value as "<isAttachmentCompressed>"
    When user wants to create scheduled report with key as "emailAddress" and value as "<emailAddress>"
    When user wants to create scheduled report with key as "description" and value as "<description>"
    When user wants to create scheduled report with key as "frequencyDescription" and value as "<frequencyDescription>"
    When user wants to create scheduled report with key as "deliveryType" and value as "<deliveryType>"
    Then validate the response status code "<statusCode>"
    Then user validates the status number "<statusNumber>" and statusMessage "<statusMessage>" for created schedule report

    Examples: 
      | testCaseName                                                                                                | customerType         | statusCode | reportAssignmentParameters | reportType        | isEnabled | isAdhocReport | emailAddress      | description                | frequencyDescription | deliveryType        | isAttachmentCompressed | statusNumber | statusMessage                              |
       | TC-01-OLS-162-Verify user should able to create a schedule report with all valid inputs                     | associatedCustomerNo |        200 | validReportParams          | validReportType   | Y         | N             | validEmailAddress | validScheduleReportName    | validFrequency       | validDeliveryType   | N                      |              |                                            |
       | TC-02-OLS-162-Verify user should not able to create a schedule report with existing scheduled template name | associatedCustomerNo |        400 | validReportParams          | validReportType   | Y         | N             | validEmailAddress | existingScheduleReportName | validFrequency       | validDeliveryType   | N                      |        13004 | Schedule Name already exists for the user. |
       | TC-03-OLS-162-Verify user should not able to create a schedule report with invalid frequency                | associatedCustomerNo |        400 | validReportParams          | validReportType   | Y         | N             | validEmailAddress | validScheduleReportName    | invalidFrequency     | validDeliveryType   | N                      |            6 | Invalid entry                              |
       | TC-04-OLS-162-Verify user should not able to create a schedule report with invalid delivery type            | associatedCustomerNo |        400 | validReportParams          | validReportType   | Y         | N             | validEmailAddress | validScheduleReportName    | validFrequency       | invalidDeliveryType | N                      |            6 | Invalid entry                              |
       | TC-05-OLS-162-Verify user should not able to create a schedule report with invalid report name              | associatedCustomerNo |        400 | validReportParams          | invalidReportType | Y         | N             | validEmailAddress | validScheduleReportName    | validFrequency       | validDeliveryType   | N                      |            6 | Invalid entry                              |

  @searchScheduledReport
  Scenario Outline: OLS-163 Verify user should get the scheduled report template
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of scheduled reports based on fliter combination "<filterCombination>"
    Then validate the response status code "<statusCode>"
    Then validate the specific report details object from response

    Examples: 
      | testCaseName                                                                                                                                                              | filterCombination          | statusCode |
      | TC-01-OLS-163-Verify user should able to get the list of all scheduled reports                                                                                            | withoutPassingAnyfilters   |        200 |
      | TC-02-OLS-163-Verify user should able to get the list of scheduled reports based on report type and report template name filter                                           | reportTypeWithTemplateName |        200 |
      | TC-03-OLS-163-Verify user should able to get the list of scheduled reports based on report type, report template name ,status and date range with all combination fliters | allCombinationFilter       |        200 |
      | TC-04-OLS-163-Verify user should able to ge the list of scheduled report based on multiple report type filter                                                             | multipleReportTypeFilter   |        200 |

  #    | TC-05-OLS-163-Verify user should not able to get the report for passing the report template name is not associated with report type                                       | invalidTemplateNameWithReportType      |        400 |
  #    | TC-06-OLS-163-Verify user should not able to get the reports when passing unassociated report type description for logged on user                                         | unassociatedReportTypeWithTemplateName |        400 |
  @getScheduledReportDetails
  Scenario Outline: OLS-1069 Verify user should get the scheduled report template
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of scheduled reports based on fliter combination "<filterCombination>"
    When user should able to get the report details of user scheduled report with possible combination as "<possibleCombination>"
    Then validate the response status code "<statusCode>"
    Then validates the user scheduled report details from response

    Examples: 
      | testCaseName                                                                                                                                                         | statusCode | possibleCombination                        | filterCombination        |
      | TC-01-OLS-1069-Verify user should able to get the details of a user scheduled report for logged on user when passing valid report type desc and report template name |        200 | associatedReportTypeWithValidDescription   | withoutPassingAnyfilters |
      | TC-02-OLS-1069-Verify user should not able to get the details of  a user scheduled report for unassociated report type                                               |        204 | unassociatedReportTypeWithValidDescription | withoutPassingAnyfilters |
      | TC-03-OLS-1069-Verify user should not able to get the details of  a user scheduled report when passing invalid combination for report type and report template name  |        204 | associatedReportTypeWithInvalidDescription | withoutPassingAnyfilters |

  @editScheduledReport
  Scenario Outline: OLS-164 Verify user able to edit the scheduled report template
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of scheduled reports based on fliter combination "<filterCombination>"
    When user should able to get the report details of user scheduled report with possible combination as "<possibleCombination>"
    When user able to edit a scheduled report with all inputs based on scenario type as "<ScenarioType>"
   # Then validate the response status code "<statusCode>"
    Then validate the updated scheduled report details from response and DB with statusNumber as "<statusNumber>" and statusMessage "<statusMessage>"

    Examples: 
      | testCaseName                                                                                                     | filterCombination        | possibleCombination                      | ScenarioType         | statusCode | statusNumber | statusMessage                              |
      | TC-01-OLS-164-Verify user should able to edit a scheduled reports with all valid inputs for logged on user       | withoutPassingAnyfilters | associatedReportTypeWithValidDescription | validInputs          |        200 |              |                                            |
      | TC-02-OLS-164-Verify user should not able to edit a scheduled reports when passing existing schedule report name | withoutPassingAnyfilters | associatedReportTypeWithValidDescription | existingTemplateName |        400 |        13004 | Schedule Name already exists for the user. |
      | TC-03-OLS-164-Verify user should not able to edit a scheduled reports when tried to pass invalid frequency type  | withoutPassingAnyfilters | associatedReportTypeWithValidDescription | invalidFrequencyType |        400 |            6 | Invalid entry                              |
      | TC-04-OLS-164-Verify user should not able to edit a scheduled reports when tried to pass invalid delivery type   | withoutPassingAnyfilters | associatedReportTypeWithValidDescription | invalidDeliverType   |        400 |            6 | Invalid entry                              |
      
      
   @editScheduledReportStatus
  Scenario Outline: OLS-1141 Verify user able to edit the scheduled report status
    Given User gets the base url EndPoint and passing "<testCaseName>" to create test
    When user gets the list of scheduled reports based on fliter combination "<filterCombination>"
    Then user able to change the scheduled report status based on "<statusScenarioType>"
    Then validate the response status code "<statusCode>"
    Then Validate the updated scheduled report details from response

    Examples: 
      | testCaseName                                                                                                       | filterCombination        | statusCode | statusScenarioType |
      | TC-01-OLS-1141-Verify user should able to change the status from active to on hold for scheduled report            | withoutPassingAnyfilters |        200 | validStatus        |
      | TC-02-OLS-1141-Verify user should able to change the status from onhold to Active for scheduled report             | withoutPassingAnyfilters |        200 | validStatus        |
      | TC-03-OLS-1141-Verify user should not able to change the status from active to invalid status for scheduled report | withoutPassingAnyfilters |        400 | invalidStatus      |
