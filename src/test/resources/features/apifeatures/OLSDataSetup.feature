Feature: Data set up for all modules

  Scenario Outline: Data set up for reports module
    #    Given User gets the base url EndPoint and passing "<testName>" to create test
    When User execute and update query to insert data "<storedReportOId>", "<uniqueReportName>"

    Examples: 
      | storedReportOId | uniqueReportName |
      |           41934 |              004 |

  @reportAssignments
  Scenario Outline: Data set up for report assignments table
    When User execute and update query to insert data for "<reportAssignmentOid>" and "<reportTemplateName>" in report assignments table

    Examples: 
      | reportAssignmentOid | reportTemplateName |
      | | |
     