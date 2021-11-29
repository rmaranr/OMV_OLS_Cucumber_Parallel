Feature: UI:CountryUser - Vat number scenarios from Accounts Module
  ################### Vat Numbers #########################
  @UI-VatNumbers
  Scenario Outline: TC-02 : Country User : OMV-1028 Validate user is able create new Vat number and validate error messages
    When User set property "testStatus" value as "" in "TestDataProperties"
    When User set property "fieldSetControlOid" value as "" in "TestDataProperties"
    Then User modify flags "<flags>" for logged in user "<Approver user name>"
    Given User logout from the application
    Then User enter "<Approver user name>" and "<Approver password>"
    And User click on login button
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account number which is having status "<statusOfRecord>" for approval requests for module "TaxNumber" based on flag "<canApproveOwnFlag>" for logged in user "<Approver user name>"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User make the driver sleep for few seconds "5"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate message "VAT numbers" based on tag name "div"
    When User skip few steps if property "isReqExistsForCustomer" value is "Yes"
    Then User click on button "+ New" using span text
    Then User perform action "edit" for fields based on validation control for section "addNewVatNumber"
    And User click on "Submit" based on tag name "span"
    And User validate message "VAT number has been created" based on tag name "div"
    Then User set property "testStatus" value as "" in "TestDataProperties"
    When User click on button Three dot icon when vat number record is matching with pending record or not
    Then User click on "<Action of approval>" based on tag name "<Tag name of action>"
    And User validate presence of "<Message>" field with "<Tag name of message>" tag
#    Then User verify in the database newly created Vat number has been added

    Examples:
      | Approver user name | Approver password  | flags                                                                                                                                                                                | canApproveOwnFlag | Message                                                      | Tag name of message | Action of approval | Tag name of action | statusOfRecord |
      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='Y',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | VAT number has been approved                                 | div                 | Approve            | button             | notHavingPendingOrPosted        |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='Y',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Approve            | button             | Pending   |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='Y',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | VAT number has been approved                                 | div                 | Approve            | button             | Pending   |
      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Approve            | button             | Pending   |
      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | This user is not allowed to Approve or Decline these records | div                 | Approve            | button             | Pending   |

      | ApproverUser01     | ApproverPassword01 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='Y',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | VAT number has been declined                                 | div                 | Decline            | button             | Pending       |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='Y',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Decline            | button             | Pending   |
      | ApproverUser02     | ApproverPassword02 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='Y',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | VAT number has been declined                                 | div                 | Decline            | button             | Pending   |
      | ApproverUser03     | ApproverPassword03 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'Y' | Y                 | This user is not allowed to Approve or Decline these records | div                 | Decline            | button             | Pending   |
      | ApproverUser04     | ApproverPassword04 | CAN_APPROVE_CUST_MASTER_DATA = 'N',CAN_APPROVE_ACCT_MASTER_DATA = 'N',CAN_APPROVE_TAX_MASTER_DATA ='N',CAN_APPROVE_CUST_HIER_DATA ='N',CAN_APPROVE_PRICING='N',CAN_APPROVE_OWN = 'N' | N                 | This user is not allowed to Approve or Decline these records | div                 | Decline            | button             | Pending   |

  @UI-VatNumbers
  Scenario: TC-01 : Country User : OMV-1025 User validate the "PRIMARY TAX NUMBER" and "COUNTRY SPECIFIC TAX NUMBER" sections are not present where there is no tax numbers at account and country level
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User get an account number for which account level taxnumber presence "No" and country level taxnumbers presence "No"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    Then User validate presence of "No VAT numbers" field with "div" tag
    Then User validate presence of "button to create a VAT number" field with "div" tag

  @UI-VatNumbers
  Scenario: TC-02 : Country User : OMV-1025 User validate the default and expanded field values of "COUNTRY SPECIFIC TAX NUMBERS" and "PRIMARY TAX NUMBER" section not present when there is no tax number at account level
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User get an account number for which account level taxnumber presence "No" and country level taxnumbers presence "Yes"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate "default" section of tax numbers module for "COUNTRY SPECIFIC TAX NUMBER" for record which is at position "1"
    And User validate "Expanded" section of tax numbers module for "COUNTRY SPECIFIC TAX NUMBER" for record which is at position "1"

  @UI-VatNumbers
  Scenario: TC-03 : Country User : OMV-1025 User validate "COUNTRY SPECIFIC TAX NUMBER" section not present when there is no tax number at country level and validate default and expanded field values of "PRIMARY TAX NUMBERS"
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User get an account number for which account level taxnumber presence "Yes" and country level taxnumbers presence "No"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate "default" section of tax numbers module for "PRIMARY TAX NUMBER" for record which is at position "1"
    And User validate "Expanded" section of tax numbers module for "PRIMARY TAX NUMBER" for record which is at position "1"

  @UI-VatNumbers
  Scenario: TC-04 : Country User : OMV-1025 User validate the default and expanded field values of "PRIMARY TAX NUMBERS" and "COUNTRY SPECIFIC TAX NUMBER" sections
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User get an account number for which account level taxnumber presence "Yes" and country level taxnumbers presence "Yes"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate "default" section of tax numbers module for "COUNTRY SPECIFIC TAX NUMBER" for record which is at position "2"
    And User validate "Expanded" section of tax numbers module for "COUNTRY SPECIFIC TAX NUMBER" for record which is at position "2"
    Then User expand the record based on number "2"
    And User validate "default" section of tax numbers module for "PRIMARY TAX NUMBER" for record which is at position "1"
    And User validate "Expanded" section of tax numbers module for "PRIMARY TAX NUMBER" for record which is at position "1"

  Scenario: TC-05 : Country User : OMV-1025 User validate actions visibility of tax number based on its status
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User get an account number for which account level taxnumber presence "Yes" and country level taxnumbers presence "Yes"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    Then User validate actions are visible based on status of tax number

  @UI-VatNumbers
  Scenario: TC-01 : Country User : OMV-1028 Validate user is able to navigate Vat Numbers module page after clicking 'Cancel' button on 'New Vat Numbers' page
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
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate message "VAT numbers" based on tag name "div"
    Then User click on button "+ New" using span text
    And User click on "Cancel" based on tag name "a"
    And User validate message "VAT numbers" based on tag name "div"

  Scenario: TC-01 : Country User : OMV-2030 User validate Decline flow on the system date for expire vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "systemDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User click on button contains "Three dot icon" using locator "(//div[@class='header-menu ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Expire" based on tag name "button"
    Then User validate message "Expire VAT number" based on tag name "h2"
    And User validate message "Please review the data of this account to ensure all settings are correct." based on tag name "div"
    And User click "endDate" then select date based on property value "expiryDateOfVatNo" of date format property "expiryDateFormat" no of days "0" of "Past" time
    Then User click on "Save" based on tag name "span"
    And User validate message "Expiration date has been updated" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Pending"
    And User "Decline" the vat number which has been recently "Updated"
    And User validate message "VAT number has been declined" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Declined"
    Then User validate newly "updated" vat number record "notPresent" "main" table with approval status as "NA"

  Scenario: TC-02 : Country User : OMV-2030 User validate Decline flow on the future date for expire vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "futureDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User click on button contains "Three dot icon" using locator "(//div[@class='header-menu ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Expire" based on tag name "button"
    Then User validate message "Expire VAT number" based on tag name "h2"
    And User validate message "Please review the data of this account to ensure all settings are correct." based on tag name "div"
    And User click "endDate" then select date based on property value "expiryDateOfVatNo" of date format property "expiryDateFormat" no of days "0" of "Past" time
    Then User click on "Save" based on tag name "span"
    And User validate message "Expiration date has been updated" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Pending"
    And User "Decline" the vat number which has been recently "Updated"
    And User validate message "VAT number has been declined" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Declined"
    Then User validate newly "updated" vat number record "notPresent" "main" table with approval status as "NA"

  Scenario: TC-03 : Country User : OMV-2030 User validate Approve flow on the system date for expire vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "systemDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User click on button contains "Three dot icon" using locator "(//div[@class='header-menu ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Expire" based on tag name "button"
    Then User validate message "Expire VAT number" based on tag name "h2"
    And User validate message "Please review the data of this account to ensure all settings are correct." based on tag name "div"
    And User click "endDate" then select date based on property value "expiryDateOfVatNo" of date format property "expiryDateFormat" no of days "0" of "Past" time
    Then User click on "Save" based on tag name "span"
    And User validate message "Expiration date has been updated" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Pending"
    And User "Approve" the vat number which has been recently "Updated"
    And User validate message "VAT number has been approved" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Approved"
    Then User validate newly "updated" vat number record "Present" "main" table with approval status as "NA"

  Scenario: TC-04 : Country User : OMV-2030 User validate Approve flow on the future date for expire vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "futureDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "taxNumber-AccountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User click on button contains "Three dot icon" using locator "(//div[@class='header-menu ng-star-inserted']/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Expire" based on tag name "button"
    Then User validate message "Expire VAT number" based on tag name "h2"
    And User validate message "Please review the data of this account to ensure all settings are correct." based on tag name "div"
    And User click "endDate" then select date based on property value "expiryDateOfVatNo" of date format property "expiryDateFormat" no of days "0" of "Past" time
    Then User click on "Save" based on tag name "span"
    And User validate message "Expiration date has been updated" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Pending"
    And User "Approve" the vat number which has been recently "Updated"
    And User validate message "VAT number has been approved" based on tag name "div"
    Then User validate newly "updated" vat number record "present" "staging" table with approval status as "Approved"
    Then User validate newly "updated" vat number record "notPresent" "main" table with approval status as "NA"

    ## Approve and decline approval flow scenarios for create vat number
  Scenario: TC01: Country User :OMV-2030 Validate Decline flow on the system date for newly created vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "systemDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate message "VAT numbers" based on tag name "div"
    Then User click on button "+ New" using span text
    Then User perform action "edit" for fields based on validation control for section "addNewVatNumber"
    And User click on "Submit" based on tag name "span"
    And User validate message "VAT number has been created" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Pending"
    And User "Decline" the vat number which has been recently "Created"
    And User validate message "VAT number has been declined" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Declined"
    Then User validate newly "created" vat number record "notPresent" "main" table with approval status as "NA"

  Scenario: TC02:  Country User :OMV-2030 Validate Decline flow on the future date for newly created vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "futureDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate message "VAT numbers" based on tag name "div"
    Then User click on button "+ New" using span text
    Then User perform action "edit" for fields based on validation control for section "addNewVatNumber"
    And User click on "Submit" based on tag name "span"
    And User validate message "VAT number has been created" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Pending"
    And User "Decline" the vat number which has been recently "Created"
    And User validate message "VAT number has been declined" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Declined"
    Then User validate newly "created" vat number record "notPresent" "main" table with approval status as "NA"

  Scenario: TC03:  Country User :OMV-2030 Validate Approve flow on the system date for newly created vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "systemDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate message "VAT numbers" based on tag name "div"
    Then User click on button "+ New" using span text
    Then User perform action "edit" for fields based on validation control for section "addNewVatNumber"
    And User click on "Submit" based on tag name "span"
    And User validate message "VAT number has been created" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Pending"
    And User "Approve" the vat number which has been recently "Created"
    And User validate message "VAT number has been Approved" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Approved"
    Then User validate newly "created" vat number record "present" "main" table with approval status as "NA"

  Scenario: TC04:  Country User :OMV-2030 Validate Approve flow on the future date for newly created vat number
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User get an account for which should not have pending vat number status for "futureDate"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter field value "commonAccountNo" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Admin"
    And User click on the "Submenu" "VAT numbers"
    And User validate message "VAT numbers" based on tag name "div"
    Then User click on button "+ New" using span text
    Then User perform action "edit" for fields based on validation control for section "addNewVatNumber"
    And User click on "Submit" based on tag name "span"
    And User validate message "VAT number has been created" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Pending"
    And User "Approve" the vat number which has been recently "Created"
    And User validate message "VAT number has been Approved" based on tag name "div"
    Then User validate newly "created" vat number record "present" "staging" table with approval status as "Approved"
    Then User validate newly "created" vat number record "notPresent" "main" table with approval status as "NA"

#    Scenario: Validate the vat numbers status will be inactive for which if the expire date is less than the client processing date