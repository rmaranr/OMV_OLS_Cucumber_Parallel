Feature: Country User : Approval Dashboard scenarios

  @UI-ApproverDashBoard
  Scenario:01 TaxNumberWorkFlow - User Validate tax number records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Tax Number Workflow", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Tax Number Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "VAT number has been approved" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:02 TaxNumberWorkFlow - User Validate tax number records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Tax Number Workflow", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Tax Number Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "VAT number has been declined" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:03 TaxNumberWorkFlow - Country User : User Validate tax number records visible and able to approve which isnot created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Tax Number Workflow", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Tax Number Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "VAT number has been approved" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:04 TaxNumberWorkFlow - User Validate tax number records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Tax Number Workflow", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Tax Number Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "VAT number has been declined" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:05 TaxNumberWorkFlow - User Validate tax number records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser02"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Tax Number Workflow", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Tax Number Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "VAT number has been approved" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:06 TaxNumberWorkFlow - User Validate tax number records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Tax Number Workflow", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Tax Number Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "VAT number has been declined" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:07 ApproverDashboardCommon Scenario - User Validate records are not visible if the logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = N CAN_APPROVE_ACCT_MASTER_DATA = N CAN_APPROVE_TAX_MASTER_DATA = N CAN_APPROVE_CUST_HIER_DATA = N CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = N,CAN_APPROVE_ACCT_MASTER_DATA = N,CAN_APPROVE_TAX_MASTER_DATA =N,CAN_APPROVE_CUST_HIER_DATA =N,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser03"
    When User enter "ApproverUser03" and "ApproverPassword03"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser03"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    Then User validate presence of "No pending request" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:08 ApproverDashboardCommon Scenario -  User Validate records are not visible if the logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = N CAN_APPROVE_ACCT_MASTER_DATA = N CAN_APPROVE_TAX_MASTER_DATA = N CAN_APPROVE_CUST_HIER_DATA = N CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = N,CAN_APPROVE_ACCT_MASTER_DATA = N,CAN_APPROVE_TAX_MASTER_DATA =N,CAN_APPROVE_CUST_HIER_DATA =N,CAN_APPROVE_OWN = N" for logged in user "ApproverUser04"
    When User enter "ApproverUser04" and "ApproverPassword04"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser04"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    Then User validate presence of "No pending request" field with "div" tag
    #===============================================================================================

  @UI-ApproverDashBoard
  Scenario:01 CustomerWorkFlow - User Validate customer records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Customer Workflow", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Customer Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Account summary has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:02 CustomerWorkFlow - User Validate customer records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Customer Workflow", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Customer Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Account summary has been declined successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:03 CustomerWorkFlow - Country User : User Validate customer records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Customer Workflow", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Customer Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Account summary has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:04 CustomerWorkFlow - User Validate customer records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Customer Workflow", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Customer Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Account summary has been declined successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:05 CustomerWorkFlow - User Validate customer records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser02"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Customer Workflow", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Customer Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Account summary has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:06 CustomerWorkFlow - User Validate customer records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Customer Workflow", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Customer Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Account summary has been declined successfully" field with "div" tag

#===========================================================================================================
  @UI-ApproverDashBoard
  Scenario:01 AccountWorkFlow - User Validate Account records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Account Workflow", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Account Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Financial has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:02 AccountWorkFlow - User Validate Account records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Account Workflow", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Account Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Financial has been declined successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:03 AccountWorkFlow - Country User : User Validate Account records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Account Workflow", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Account Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Financial has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:04 AccountWorkFlow - User Validate Account records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Account Workflow", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Account Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Financial has been declined successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:05 AccountWorkFlow - User Validate Account records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser02"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Account Workflow", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Account Workflow"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Financial has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:06 AccountWorkFlow - User Validate Account records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser02"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    When User click three dot icon of latest pending record of "Update Account Workflow", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    Then User validate data populated for record "Update Account Workflow"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Financial has been declined successfully" field with "div" tag

    #===========================================================================================================

  @UI-ApproverDashBoard
  Scenario:01 CustomerHierachyWorkFlow - User Validate Customer Hierarchy records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    And User click on "Hierarchy" based on tag name "div"
    When User click three dot icon of latest pending record of "Customer Hierarchy", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Hierarchy has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:02 CustomerHierachyWorkFlow - User Validate Customer Hierarchy records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    And User click on "Hierarchy" based on tag name "div"
    When User click three dot icon of latest pending record of "Customer Hierarchy", which "is" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Hierarchy has been declined successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:03 CustomerHierachyWorkFlow - Country User : User Validate Customer Hierarchy records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    And User click on "Hierarchy" based on tag name "div"
    When User click three dot icon of latest pending record of "Customer Hierarchy", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Hierarchy has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:04 CustomerHierachyWorkFlow - User Validate Customer Hierarchy records visible and able to approve which is created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = Y
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA = Y,CAN_APPROVE_CUST_HIER_DATA = Y,CAN_APPROVE_OWN = Y" for logged in user "ApproverUser01"
    When User enter "ApproverUser01" and "ApproverPassword01"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser01"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    And User click on "Hierarchy" based on tag name "div"
    When User click three dot icon of latest pending record of "Customer Hierarchy", which "isnot" created by logged in user "ApproverUser01"
    And User click on "Review" based on tag name "button"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Hierarchy has been declined successfully" field with "div" tag

  @UI-ApproverDashBoard
  Scenario:05 CustomerHierachyWorkFlow - User Validate Customer Hierarchy records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser02"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    And User click on "Hierarchy" based on tag name "div"
    When User click three dot icon of latest pending record of "Customer Hierarchy", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    And User click on "Approve" based on tag name "span"
    Then User validate presence of "Hierarchy has been approved successfully" field with "div" tag

  @UI-ApproverDashBoard @Test
  Scenario:06 CustomerHierachyWorkFlow - User Validate Customer Hierarchy records visible and able to approve which is not created by logged in user who has following flags CAN_APPROVE_CUST_MASTER_DATA = Y CAN_APPROVE_ACCT_MASTER_DATA = Y CAN_APPROVE_TAX_MASTER_DATA = Y CAN_APPROVE_CUST_HIER_DATA = Y CAN_APPROVE_OWN = N
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    Then User update flags "CAN_APPROVE_CUST_MASTER_DATA = Y,CAN_APPROVE_ACCT_MASTER_DATA = Y,CAN_APPROVE_TAX_MASTER_DATA =Y,CAN_APPROVE_CUST_HIER_DATA =Y,CAN_APPROVE_OWN = N" for logged in user "ApproverUser02"
    When User enter "ApproverUser02" and "ApproverPassword02"
    And User click on login button
    And User select client and country based on logged in user "ApproverUser02"
    And User click on the "menu" "Admin"
    And User click on the "menu" "Approver dashboard"
    And User click on "Hierarchy" based on tag name "div"
    When User click three dot icon of latest pending record of "Customer Hierarchy", which "isnot" created by logged in user "ApproverUser02"
    And User click on "Review" based on tag name "button"
    And User click on "Decline" based on tag name "span"
    Then User validate presence of "Hierarchy has been declined successfully" field with "div" tag


