#Feature: This feature file contains add pricing groups and pricing rebates scenarios
#
#  @Test
#  Scenario Outline: OMV 38: Validate the creating Period templates at client level with rebate configuration
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<UserName>" and "<PassWord>"
#    And User click on login button
#    When User click on the "menu" "Pricing"
#    And  User click on the "submenu" "Discounts"
#    And  User click on button "+ New " using span text
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
#    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
##    Then User click drop down "evalRule" then select value "BO" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "RDMethod" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
#    And User click on button "+Add rebate" using span text
#    And User click on date field "startDate" and select value no of days "0" of "Past" time
#    And User click on date field "endDate" and select value no of days "2" of "Future" time
#    Then User click drop down "band" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    Then User click drop down "method" then select value "<valueMethod>" using tag "mat-select" and attribute "ng-reflect-name"
#    And User enter "minValue" as "<minValue>" in "pricingTemplate" module having length "2" in "add" form
#    And User enter "maxValue" as "<maxValue>" in "pricingTemplate" module having length "2" in "add" form
#    And User enter "value" as "<value>" in "pricingTemplate" module having length "2" in "add" form
#    And User click on button " Add" using span text
#    And User validate "snackbar-text" message "New rebate has been added" in "pricingTemplate" module
#    And User click button "threeDotIcon" using tag name "div", attribute name "class", attribute value "header-menu ng-star-inserted"
#    Then User click on "Edit" based on tag name "button"
#    And User click on date field "endDate" and select value no of days "3" of "Future" time
#    Then User click on button "Update" using span text
#    And User validate "snackbar-text" message "Rebate has been updated" in "pricingTemplate" module
#    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
#    And User enter ""RandomAlphanumeric"" as ""RandomAlphanumeric"" in "pricingBonuses" module having length "<any>" in "<any>" form using input tag
#    Examples:
#      | Scenario                                                                                                                            | UserName       | PassWord       | valueMethod | minValue | maxValue | value |
#      | TC01 : OMV 38: Validate the creating Period templates at client level configuration with positive value for CPL Value Method        | ClientUserName | ClientPassWord | CPL         | 5        | 10       | 10    |
##      | TC02 : OMV 38: Validate the creating Period templates at client level configuration with negative value for CPL Value Method        | ClientUserName | ClientPassWord | CPL         | 6        | 8        | -6    |
##      | TC03 : OMV 38: Validate the creating Period templates at client level configuration with negative value for Percentage Value Method | ClientUserName | ClientPassWord | Percentage  | 5        | 10       | -6    |
##      | TC04 : OMV 38: Validate the creating Period templates at client level configuration with negative value for Percentage Value Method | ClientUserName | ClientPassWord | Percentage  | 6        | 8        | -6    |
#
#  Scenario Outline: OMV 38: Validate the creating Pricing templates at client level rebate edit and delete
#    When User click on the "menu" "Pricing"
#    And  User click on the "submenu" "Period rebate"
#    And  User click on button "New"
#    And  User verifies whether the "segment" field is loaded based on the client level and user select a value from the list
#    And  User verifies whether the "cardProductGroup" field is loaded based on the client level and user select a value from the list
#    And  User verifies whether the "acceptanceCountry is loaded based on the selection of "cardProductGroup" and user select a value from the list
#    And  User verifies whether the "product/Group" is loaded based on the selection of "acceptanceCountry" and user select a value from the list
#    And  User verifies whether the "merchant" is loaded based on the selection of "product/Group" and user select a value from the list
#    And  User verifies whether the "network" is loaded based on the selection of "merchant" and user select a value from the list
#    And  User verifies whether the "siteGroup" is loaded based on the selection of "network" and user select a value from the list
#    And  User click on button "Next"
#    And  User select a random option from the "Method" drop down
#    And  User click on "Add"
#    And  User click on "Add rebate" option
#    And  User select a "effectiveStart" date
#    And  User select a "effectiveEnd" date
#    And  User select a random option from the <Band> drop down
#    And  User select "<valueMethod>" from the "Value Method" drop down
#    And  User select "<Operator>" operator from the list
#    And  User enter "<maxLitre>" value if "Between" operator is selected
#    And  User enter "<minLitre>" value if "Between" operator is selected
#    And  User enter "<litre>" value if other than Between operator is selected
#    And  User enter "<value>"
#    And  User click on button "Add"
#    And  User validated success message of Edit status is "New rebate has been Added"
#    And  User click on Three dot icon in module "periodRebate" based on search keywords "<Edit>"
#    And  User changes the value for "<value>"
#    And  User click on button "Update"
#    And  User validated success message of Edit status is "Rebate has been updated"
#    And  User click on Three dot icon in module "periodRebate" based on search keywords "<Delete>"
#    And User click on "Delete" button
#    And  User validated success message of Edit status is "Rebate has been deleted"
#    And  User click on button "Next"
#
#    Examples:
#      | Band   | valueMethod | Operator | minLitre | maxLitre | litre | value |
#      | Gold   | CPL         | Between  | 10       | 15       | 0     | 30    |
#      | Silver | Fixed       | Equals   | 0        | 0        | 0     | 50    |
#      | Bronze | Percentage  | LessThan | 0        | 0        | 10    | 18    |