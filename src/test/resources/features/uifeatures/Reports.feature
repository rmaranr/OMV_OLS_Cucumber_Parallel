Feature: Reports scenarios

  Scenario Outline: Validate Run Once functionality in Reports page
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<scenarioName>"
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "<reportsSubMenu>"
    Then User validate header of module "<subMenuHeader>"
    When User click on "+ Report" based on tag name "span"
    And User validate tool tip "<ReportCategory text>" for "reportType,runOnce,scheduleAsARecurringReport,frequency,category" if "Report category" contains more than one value which has "<IsOnline-Value>" and "<IsAdhoc-Value>"
    Then User validate Report category drop down value should be pre-selected if it has one value, else user need to select value from list
    Then User validate tool tip "<ReportType Text>" for "runOnce,scheduleAsARecurringReport,frequency,category" if "Report type" contains more than one value which has "<IsOnline-Value>" and "<IsAdhoc-Value>"
    When User validate Report type drop down value should be pre-selected if it has one value, else user need to select value which has "<IsOnline-Value>" and "<IsAdhoc-Value>"
    And User validate "<radioButtonName>" radio button must be auto selected for selected Report type else select "<selectRadioButtonName>" radio option
    Then User validate "frequency" drop down is enabled when "<selectRadioButtonName>" is selected, Then user selects one value "Report Monthly"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate field values in Add filters stepper for selected report type and radio button "<selectRadioButtonName>"
    Then User select all accounts from the list of accounts if more than one account displayed for logged in user "OLSUserName"
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    Then User handle delivery stepper based on report radio button "<selectRadioButtonName>" and action "<actionOfReport>"
    And User validate the success message or error message of adding report and validate header of module for action "<actionOfReport>", radio button "<selectRadioButtonName>"
    Examples:
      | scenarioName                                                                                                           | reportsSubMenu   | subMenuHeader    | ReportCategory text          | ReportType Text          | radioButtonName | selectRadioButtonName | IsOnline-Value | IsAdhoc-Value | actionOfReport    |
      | TC-01-Validate User is able to generate report using Run-Once option with filter validation in Reports module          | Reports          | Reports          | Select report category first | Select report type first | NoRadioButtons  | Run once              | Y              | Y             | Email full report |
      | TC-02-Validate User is able to generate report using Run-Once option in Reports module                                 | Reports          | Reports          | Select report category first | Select report type first | Run once        | Run once              | N              | Y             | Download          |
      | TC-05-Validate User is able to generate report using Run-Once option with filter validation in Report Templates module | Report Templates | Report templates | Select report category first | Select report type first | NoRadioButtons  | Run once              | Y              | Y             | Email full report |
      | TC-06-Validate User is able to generate report using Run-Once option in Report Templates module                        | Report Templates | Report templates | Select report category first | Select report type first | Run once        | Run once              | N              | Y             | Download          |

  Scenario Outline: Validate add, edit report functionality in Report Templates page
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<scenarioName>"
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "<reportsSubMenu>"
    Then User validate header of module "<subMenuHeader>"
    When User click on the "Add Report button" "+ Report"
    And User validate tool tip "<ReportCategory text>" for "reportType,runOnce,scheduleAsARecurringReport,frequency,category" if "Report category" contains more than one value which has "<IsOnline-Value>" and "<IsAdhoc-Value>"
    Then User validate Report category drop down value should be pre-selected if it has one value, else user need to select value from list
    Then User validate tool tip "<ReportType Text>" for "runOnce,scheduleAsARecurringReport,frequency,category" if "Report type" contains more than one value which has "<IsOnline-Value>" and "<IsAdhoc-Value>"
    When User validate Report type drop down value should be pre-selected if it has one value, else user need to select value which has "<IsOnline-Value>" and "<IsAdhoc-Value>"
    And User validate "<radioButtonName>" radio button must be auto selected for selected Report type else select "<selectRadioButtonName>" radio option
    Then User validate "frequency" drop down is enabled when "Schedule a recurring report" is selected, Then user selects one value "Report Monthly"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate field values in Add filters stepper for selected report type and radio button "<selectRadioButtonName>"
    Then User select all accounts from the list of accounts if more than one account displayed for logged in user "OLSUserName"
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    Then User handle delivery stepper based on report radio button "<selectRadioButtonName>" and action "<actionOfReport>"
    And User validate the success message or error message of adding report and validate header of module for action "<actionOfReport>", radio button "<selectRadioButtonName>"
    And User validate newly created report or updated report "newReportName" has been saved in the database or not for field "DESCRIPTION"
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Report Templates"
    Then User validate header of module "Report templates"
    When User enter search keywords "newReportName" in Reports module
    Then User click on search icon
    And User click on Three dot icon in module "reportTemplates" based on search keywords "newReportName"
    And User click on button "Edit template"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    When User validate field values in Add filters stepper for selected report type and radio button "<selectRadioButtonName>"
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User handle delivery stepper in Edit report template page
    And User click on "Submit" button
    And User validate message "Report template has been updated" based on tag name "div"
    And User validate newly created report or updated report "newReportName" has been saved in the database or not for field "DESCRIPTION"

    Examples:
      | scenarioName                                                                                                                              | reportsSubMenu   | subMenuHeader    | ReportCategory text          | ReportType Text          | radioButtonName                | selectRadioButtonName          | IsOnline-Value | IsAdhoc-Value | actionOfReport          |
      | TC-03-Validate User is able to generate report using Schedule a recurring report option in Reports module                                 | Reports          | Reports          | Select report category first | Select report type first | Schedule as a recurring report | Schedule as a recurring report | Y              | N             | Email full report       |
      | TC-04-Validate User is able to generate report using Schedule a recurring report option with filter validation in Reports module          | Reports          | Reports          | Select report category first | Select report type first | NoRadioButtons                 | Schedule as a recurring report | Y              | Y             | Email full report |
      | TC-07-Validate User is able to generate report using Schedule a recurring report option in Report Templates module                        | Report Templates | Report templates | Select report category first | Select report type first | Schedule as a recurring report | Schedule as a recurring report | Y              | N             | Email full report       |
      | TC-08-Validate User is able to generate report using Schedule a recurring report option with filter validation in Report Templates module | Report Templates | Report templates | Select report category first | Select report type first | NoRadioButtons                 | Schedule as a recurring report | Y              | Y             | Email full report |

  Scenario: Validate Change status action item for newly added report
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    Then User get newReportName from db
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Report Templates"
    Then User validate header of module "Report templates"
    When User enter search keywords "newReportName" in Reports module
    Then User click on search icon
    And User click on Three dot icon in module "reportTemplates" based on search keywords "newReportName"
    And User click on button "Change status"
    And User select another status "On hold" for module "reportTemplates"
    And User click on "Save" button
    And User validated success message of Edit status is "Report template status has been updated"
    And User validate newly created report or updated report "reportTemplates-status" has been saved in the database or not for field "IS_ENABLED"

  Scenario: Validate View reports action item for newly added report
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    Then User get newReportName from db
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Report Templates"
    Then User validate header of module "Report templates"
    When User enter search keywords "newReportName" in Reports module
    Then User click on search icon
    And User click on Three dot icon in module "reportTemplates" based on search keywords "newReportName"
    And User click on button "View reports"
    And User verify "1" filter value is selected as "propValue" of "Reports-reportType"
    And User verify "2" filter value is selected as "NonPropValue" of "Custom"

  Scenario: Validate search report and download functionality in Reports module
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    Then User select an account from account picker which "has", "Active" status and "Active" sub status
    And User click on button "Home" using span text
    Then User get newReportName from db
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Reports"
    Then User validate header of module "Reports"
    And User get report from database then search and validate the report in module "reports"
    Then User click on search icon
    And User click on Three dot icon in module "reports" based on search keywords "reports-reportTypeOfSearchedFileName"
    And User click on button "Download report"
#    Then User validate downloaded file name is equal to "fileName" value of "reports-fileName"

  Scenario: Validate download invoice functionality in Reports-Invoices module
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User click on button "Home" using span text
    Then User get a customer which has invoices
    Then User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Invoices"
    Then User validate header of module "Invoices"
    And User get report from database then search and validate the report in module "invoices"
    When User click on button contains "ThreeDotIcon" using locator "(//div[@class='header-menu ng-star-inserted']/button/div/mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "Xpath" using method "click"
    And User click on button "Download"
#    Then User validate downloaded file name is equal to "property" value of "invoices-fileName"

  Scenario Outline: Validate Cancel button functionality in "Reports" and "Report Templates" page
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<scenarioName>"
    And User click on button "Home" using span text
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "<reportsSubMenu>"
    Then User validate header of module "<subMenuHeader>"
    When User click on the "Add Report button" "+ Report"
    And User click "Cancel" button using Java Script executor which is present at position "1" using tag name "a"
    Then User validate header of module "<subMenuHeader>"

    Examples:
      | scenarioName                                                                                | reportsSubMenu   | subMenuHeader    |
      | TC-07- Validate User is navigated to Reports page when user click on Cancel button          | Reports          | Reports          |
      | TC-08- Validate User is navigated to Report Templates page when user click on Cancel button | Report Templates | Report templates |

  Scenario: Validate filter functionality in Report templates
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User click on button "Home" using span text
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Report Templates"
    Then User validate header of module "Report templates"
    And User validate report template data by applying filters and date of report template creation
    When User validate the count of reports displayed in UI for date filter type "Last 7 days" in the position "3" with the database
    When User validate the count of reports displayed in UI for date filter type "Last 14 days" in the position "3" with the database
    When User validate the count of reports displayed in UI for date filter type "Last 90 days" in the position "3" with the database

  Scenario: Validate filter functionality in Reports
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario ""
    And User click on button "Home" using span text
    And User click on the "menu" "Reports"
    And User click on Reports sub menu "Reports"
    Then User validate header of module "Reports"
    And User validate report data by applying filters and date of report creation