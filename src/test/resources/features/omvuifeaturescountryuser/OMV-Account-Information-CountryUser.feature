Feature: UI:CountryUser - Account Information scenarios from Accounts module
   ################ must be able to view additional account related information in the account details ##############
  @UI-AccountInformation
  Scenario: TC-01 : Country User : OMV-1402 Validate user must be able to view additional account related information in the account details for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
#    Then User verify based on validation control user should be able to "View" fields then validate in database for section "ACCOUNT SUMMARY" for user "CSR"
#    Then User verify based on validation control user should be able to "View" fields then validate in database for section "ACCOUNT DETAILS" for user "CSR"
#    Then User verify based on validation control user should be able to "View" fields then validate in database for section "APPLICATION DETAILS" for user "CSR"
#    Then User verify based on validation control user should be able to "View" fields then validate in database for section "PRICING CONTROL VALUES" for user "CSR"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "ADDITIONAL DETAILS" for user "CSR"
    Then User verify based on validation control user should be able to "View" fields then validate in database for section "AUTHENTICATION" for user "CSR"

  @UI-AccountInformation
  Scenario: TC01: Country User : OMV-1005 : User must be able to edit 'Account Details' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "" status and "" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "ACCOUNT DETAILS" for user "CSR"


  @UI-AccountInformation
  Scenario: TC01: Country User : OMV-1007 : User must be able to edit 'ADDITIONAL DETAILS' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "ADDITIONAL DETAILS" for user "CSR"

  @UI-AccountInformation
  Scenario: TC01: Country User : OMV-1007 : User must be able to edit 'PRICING CONTROL VALUES' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "PRICING CONTROL VALUES" for user "CSR"

  @UI-AccountInformation
  Scenario: TC01: Country User : OMV-1007 : User must be able to edit 'AUTHENTICATION' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "AUTHENTICATION" for user "CSR"

  Scenario: TC01: Country User : OMV-1006 : User must be able to edit 'Application details' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Application details"

  @UI-AccountInformation
  Scenario Outline: TC01 : Country User : OMV-1003 Validate user is able to edit 'Account summary' details and approve them
    When User set property "testStatus" value as "" in "TestDataProperties"
    Then User modify flags "<flags>" for logged in user "<Approver user name>"
    Given User logout from the application
    Then User enter "<Approver user name>" and "<Approver password>"
    And User click on login button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User make the driver sleep for few seconds "1"
    And User select client and country based on logged in user "<Approver user name>"
    Then User get an account number which is having status "<statusOfRecord>" for approval requests for module "Customer" based on flag "<canApproveOwnFlag>" for logged in user "<Approver user name>"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User make the driver sleep for few seconds "5"
    Then User wait until the locator "(//div[@class='title-summary'])[1]" available by using locator type "xpath"
    When User skip few steps if property "isReqExistsForCustomer" value is "Yes"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "ACCOUNT SUMMARY" for user "CSR"
    And User scroll down in the page
    And User select date "effectiveFrom" based on client processing date future or past "past" days "0"
    Then User click on button "Save" using span text
    And User validate presence of "Account summary section has been updated" field with "div" tag
    Then User set property "testStatus" value as "" in "TestDataProperties"
    And User validate presence of "Needs approval" field with "span" tag
    When User click on button contains "Three dot icon" using locator "//div[@class='title-summary'][contains(text(),'ACCOUNT SUMMARY')]/mav-svg-icon/fa-icon" which has locator type "xpath" using method "click"
    Then User click on "<Action of approval>" based on tag name "<Tag name of action>"
    And User validate presence of "<Message>" field with "<Tag name of message>" tag

    Examples:
      | Approver user name | Approver password  | flags                                                                                                                                                                                | canApproveOwnFlag | Message                                                      | Tag name of message | Action of approval | Tag name of action | statusOfRecord |
      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'Y',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | Account summary approved successfully                        | div                 | Approve            | button             | notHavingPendingOrPosted |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'Y',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Approve            | button             | Pending                  |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'Y',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | Account summary approved successfully                        | div                 | Approve            | button             | Pending                  |
      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Approve            | button             | Pending                  |
      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | This user is not allowed to Approve or Decline these records | div                 | Approve            | button             | Pending                  |

      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'Y',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | Account summary decline successfully                         | div                 | Decline            | button             | Pending                  |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'Y',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Decline            | button             | Pending                  |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'Y',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | Account summary decline successfully                         | div                 | Decline            | button             | Pending                  |
      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Decline            | button             | Pending                  |
      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | This user is not allowed to Approve or Decline these records | div                 | Decline            | button             | Pending                  |

  Scenario: TC01: Country User :  OMV-1002 : User must be able to edit 'Account Summary' information for country user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User enter account number in corresponding field which "has", "1 - Active" status and "Active" based on logged in user "csrCountryUser"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User verify based on validation control Edit icon is enabled and modify eligible fields then validate in database for "account Summary"

  Scenario: TC01 : Country User : OMV-1004 Validate user is able to view approved upates in 'view updates' pop up of 'Account summary' details section
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User click on three dot icon for "Account Summary" section and click on "View updates" button then validate latest updated values are present or not


  Scenario: TC-01 : Country User : OMV-1009 Validate edit functionality of Authentication details section in 'Account information' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number based on status "" and substatus ""
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User verify based on validation control user should be able to "Edit" fields then validate in database for section "Authentication details"

    ##Approval flow for Account summary section
  Scenario: TC-01 : Country User : OMV-2030 Validate Mailing City and Mailing street are populating as expected after declining the request also
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User get account number which does not has pending status for "processingdate" and has "PhysicalAndMailingAddressSame"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    When User "uncheck" same as physical address checkbox
    Then User modify fields "mailingStreet,mailingCity" in Account Summary edit pop up
    And User click on "Submit" based on tag name "span"
    When User validate message "Account summary section has been updated" based on tag name "div"
    And User validate "mailingStreet,mailingCity" are "populated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Pending"
    And User "Decline" the Account summary record which has been recently "updated"
    And User validate message "Account summary section has been declined" based on tag name "div"
    And User validate "mailingStreet,mailingCity" populated in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Declined"

  Scenario: TC-02 : Country User : OMV-2030 Validate Approve flow for effective date as Processing date after updating fields only part of approval flow
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User get account number which does not has pending status for the effective date as "processingdate"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User modify fields "Included-In-ApprovalFlow" in  Account Summary edit pop up
    And User click on "Submit" based on tag name "span"
    When User validate message "Account summary section has been updated" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" are "notPopulated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Pending"
    Then User validate newly "updated" Account summary record "notpresent" in "main" table with approval status as "NA"
    And User "Approve" the Account summary record which has been recently "updated"
    And User validate message "Account summary section has been approved" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" populated in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Approved"
    Then User validate newly "updated" Account summary record "present" in "main" table with approval status as "NA"

  Scenario: TC-03 : Country User : OMV-2030 Validate Approve flow for effective date as future than Processing date after updating fields only part of approval flow
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User get account number which does not has pending status for the effective date as "futurethanprocessingdate"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User modify fields "Included-In-ApprovalFlow" in  Account Summary edit pop up
    And User click on "Submit" based on tag name "span"
    When User validate message "Account summary section has been updated" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" are "notPopulated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Pending"
    Then User validate newly "updated" Account summary record "notpresent" in "main" table with approval status as "NA"
    And User "Approve" the Account summary record which has been recently "updated"
    And User validate message "Account summary section has been approved" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" are "populated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Approved"
    Then User validate newly "updated" Account summary record "notpresent" in "main" table with approval status as "NA"

  Scenario: TC-04 : Country User : OMV-2030 Validate Approve flow for effective date as Processing date after updating fields which are included and not included in approval flow
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User get account number which does not has pending status for the effective date as "processingdate"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User modify fields "IncludedAndNotIncluded-In-ApprovalFlow" in  Account Summary edit pop up
    And User click on "Submit" based on tag name "span"
    When User validate message "Account summary section has been updated" based on tag name "div"
    And User validate "NotIncluded-In-ApprovalFlow" are "notPopulated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Pending"
    Then User validate newly "updated" Account summary record "notpresent" in "main" table with approval status as "NA"
    And User "Approve" the Account summary record which has been recently "updated"
    And User validate message "Account summary section has been approved" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" are "populated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Approved"
    Then User validate newly "updated" Account summary record "present" in "main" table with approval status as "NA"

  Scenario: TC-05 : Country User : OMV-2030 Validate Approve flow for effective date as future than Processing date after updating fields which are included and not included in approval flow
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User get account number which does not has pending status for the effective date as "futurethanprocessingdate"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    Then User modify fields "IncludedAndNotIncluded-In-ApprovalFlow" in  Account Summary edit pop up
    And User click on "Submit" based on tag name "span"
    When User validate message "Account summary section has been updated" based on tag name "div"
    And User validate "NotIncluded-In-ApprovalFlow" are "notPopulated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Pending"
    Then User validate newly "updated" Account summary record "notpresent" in "main" table with approval status as "NA"
    And User "Approve" the Account summary record which has been recently "updated"
    And User validate message "Account summary section has been approved" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" are "notPopulated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Approved"
    Then User validate newly "updated" Account summary record "notpresent" in "main" table with approval status as "NA"

  Scenario: TC-06 : Country User : OMV-2030 Validate Not Included in Approval flow fields are populating as expeted before approving request and after approving request physical and mailing address should be same and check box should be checked in edit account summary pop up
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User get account number which does not has pending status for "processingdate" and has "PhysicalAndMailingAddressNotSame"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate message "Account information" based on tag name "div"
    When User "Check" same as physical address checkbox
    And User click on "Submit" based on tag name "span"
    When User validate message "Account summary section has been updated" based on tag name "div"
    And User validate "NotIncluded-In-ApprovalFlow" are "Populated" in the account summary section
    And User validate "Included-In-ApprovalFlow" are "notPopulated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Pending"
    And User "Approve" the Account summary record which has been recently "updated"
    And User validate message "Account summary section has been approved" based on tag name "div"
    And User validate "Included-In-ApprovalFlow" are "Populated" in the account summary section
    Then User validate newly "updated" Account summary record "present" in "staging" table with approval status as "Approved"


