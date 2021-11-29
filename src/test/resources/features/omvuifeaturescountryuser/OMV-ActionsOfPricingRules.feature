Feature: This feature file contains scenarios related to pricing template actions

  Scenario Outline: User Validate eligible actions for private period templates which are in Active status
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing templates"
    Then User click three dot icon for a record which contain group as "<TypeOfTemplate>" and status "<Status>" in "Pricing templates" module
    And User verify button "Assign to account" status "<Assign to account status>" using tag name "button"
    And User verify button "Change status" status "<Change status>" using tag name "button"
    And User verify button "Clone" status "<Clone status>" using tag name "button"
    And User verify button "Edit" status "<Edit status>" using tag name "button"

    Examples:
      | Scenario                                                                                                                                                      | UserName        | PassWord        | TypeOfTemplate | Status  | Assign to account status | Change status | Clone status | Edit status |
      | TC-01 : OMV-136 : User login as client user with out pinning an account and validate eligible actions for private group template which are in Active status   | ClientUserName  | ClientPassWord  | Private        | Active  | disabled                 | enabled       | enabled      | enabled     |
      | TC-02 : OMV-136 : User login as client user with out pinning an account and validate eligible actions for private group template which are in Pending status  | ClientUserName  | ClientPassWord  | Private        | Pending | disabled                 | enabled       | enabled      | disabled    |
      | TC-03 : OMV-136 : User login as client user with out pinning an account and validate eligible actions for public group template which are in Active status    | ClientUserName  | ClientPassWord  | Private        | Active  | disabled                 | enabled       | enabled      | enabled     |
      | TC-04 : OMV-136 : User login as client user with out pinning an account and validate eligible actions for public group template which are in Pending status   | ClientUserName  | ClientPassWord  | Private        | Pending | disabled                 | enabled       | enabled      | disabled    |
      | TC-05 : OMV-136 : User login as country user with out pinning an account and validate eligible actions for private group template which are in Active status  | CountryUserName | CountryPassword | Private        | Active  | disabled                 | enabled       | enabled      | enabled     |
      | TC-06 : OMV-136 : User login as country user with out pinning an account and validate eligible actions for private group template which are in Pending status | CountryUserName | CountryPassword | Private        | Pending | disabled                 | enabled       | enabled      | disabled    |
      | TC-07 : OMV-136 : User login as country user with out pinning an account and validate eligible actions for public group template which are in Active status   | CountryUserName | CountryPassword | Private        | Active  | disabled                 | enabled       | enabled      | enabled     |
      | TC-08 : OMV-136 : User login as country user with out pinning an account and validate eligible actions for public group template which are in Pending status  | CountryUserName | CountryPassword | Private        | Pending | disabled                 | enabled       | enabled      | disabled    |


  Scenario Outline: User Validate eligible actions for private period templates which are in Active status
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate header of module "Account information"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing templates"
    Then User click three dot icon for a record which contain group as "<TypeOfTemplate>" and status "<Status>" in "Pricing templates" module
    And User verify button "Clone" status "<Clone status>" using tag name "button"
    And User verify button "Edit" status "<Edit status>" using tag name "button"

    Examples:
      | Scenario                                                                                                                                                   | UserName        | PassWord        | TypeOfTemplate | Status  | Clone status | Edit status |
      | TC-09 : OMV-136 : User login as client user after pinning an account and validate eligible actions for private group template which are in Active status   | ClientUserName  | ClientPassWord  | Private        | Active  | enabled      | enabled     |
      | TC-10 : OMV-136 : User login as client user after pinning an account and validate eligible actions for private group template which are in Pending status  | ClientUserName  | ClientPassWord  | Private        | Pending | enabled      | disabled    |
      | TC-11 : OMV-136 : User login as client user after pinning an account and validate eligible actions for public group template which are in Active status    | ClientUserName  | ClientPassWord  | Private        | Active  | enabled      | enabled     |
      | TC-12 : OMV-136 : User login as client user after pinning an account and validate eligible actions for public group template which are in Pending status   | ClientUserName  | ClientPassWord  | Private        | Pending | enabled      | disabled    |
      | TC-13 : OMV-136 : User login as country user after pinning an account and validate eligible actions for private group template which are in Active status  | CountryUserName | CountryPassword | Private        | Active  | enabled      | enabled     |
      | TC-14 : OMV-136 : User login as country user after pinning an account and validate eligible actions for private group template which are in Pending status | CountryUserName | CountryPassword | Private        | Pending | enabled      | disabled    |
      | TC-15 : OMV-136 : User login as country user after pinning an account and validate eligible actions for public group template which are in Active status   | CountryUserName | CountryPassword | Private        | Active  | enabled      | enabled     |
      | TC-16 : OMV-136 : User login as country user after pinning an account and validate eligible actions for public group template which are in Pending status  | CountryUserName | CountryPassword | Private        | Pending | enabled      | disabled    |

  Scenario Outline: User validate Bulk actions availability for pricing templates based on their type with out pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing templates"
    And User select check box which has fields "<Bulk action fields1>" and their values "<Bulk action values1>"
    And User select check box which has fields "<Bulk action fields2>" and their values "<Bulk action values2>"
    Then User validate "<Bulk actions>" icon is present and enabled

    Examples:
      | Scenario                                                                                                                                                                    | UserName        | PassWord        | Bulk action fields1 | Bulk action values1 | Bulk action fields2 | Bulk action values2 | Bulk actions                    |
      | TC-17 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for private group with active and non active statuses        | ClientUserName  | ClientPassWord  | Group,Status        | Private,Active      | Group,Status        | Private,NotActive   | Change Status                   |
      | TC-18 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for private group with active status                         | ClientUserName  | ClientPassWord  | Group,Status        | Private,Active      | Group,Status        | Private,Active      | Change Status                   |
      | TC-19 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for public,private groups with active status                 | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Private,Active      | Change Status                   |
      | TC-20 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for public,private groups with non active status             | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Private,NotActive   | Change Status                   |
      | TC-21 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for public,private groups with active and non active status  | ClientUserName  | ClientPassWord  | Group,Status        | Public,NotActive    | Group,Status        | Private,Active      | Change Status                   |
      | TC-22 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for public group with active and non active statuses         | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Public,NotActive    | Change Status                   |
      | TC-23 : OMV-136 : User login as client user with out pinning an account and validate Bulk actions availability for public group with active status                          | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Public,Active       | Assign to account,Change Status |
      | TC-24 : OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for private group with active and non active statuses       | CountryUserName | CountryPassword | Group,Status        | Private,Active      | Group,Status        | Private,NotActive   | Change Status                   |
      | TC-25 : OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for private group with active status                        | CountryUserName | CountryPassword | Group,Status        | Private,Active      | Group,Status        | Private,Active      | Change Status                   |
      | TC-26: OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for public,private groups with active status                 | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Private,Active      | Change Status                   |
      | TC-27 : OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for public,private groups with non active status            | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Private,NotActive   | Change Status                   |
      | TC-28 : OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for public,private groups with active and non active status | CountryUserName | CountryPassword | Group,Status        | Public,NotActive    | Group,Status        | Private,Active      | Change Status                   |
      | TC-29 : OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for public group with active and non active statuses        | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Public,NotActive    | Change Status                   |
      | TC-30 : OMV-136 : User login as country user with out pinning an account and validate Bulk actions availability for public group with active status                         | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Public,Active       | Assign to account,Change Status |

  Scenario Outline: User validate Bulk actions availability for pricing templates based on their type after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate header of module "Account information"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing templates"
    And User select check box which has fields "<Bulk action fields1>" and their values "<Bulk action values1>"
    And User select check box which has fields "<Bulk action fields2>" and their values "<Bulk action values2>"
    Then User validate "<Bulk actions>" icon is present and enabled

    Examples:
      | Scenario                                                                                                                                                                 | UserName        | PassWord        | Bulk action fields1 | Bulk action values1 | Bulk action fields2 | Bulk action values2 | Bulk actions |
      | TC-31 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for private group with active and non active statuses        | ClientUserName  | ClientPassWord  | Group,Status        | Private,Active      | Group,Status        | Private,NotActive   | NoAction     |
      | TC-32 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for private group with active status                         | ClientUserName  | ClientPassWord  | Group,Status        | Private,Active      | Group,Status        | Private,Active      | NoAction     |
      | TC-33 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for public,private groups with active status                 | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Private,Active      | NoAction     |
      | TC-34 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for public,private groups with non active status             | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Private,NotActive   | NoAction     |
      | TC-35 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for public,private groups with active and non active status  | ClientUserName  | ClientPassWord  | Group,Status        | Public,NotActive    | Group,Status        | Private,Active      | NoAction     |
      | TC-36 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for public group with active and non active statuses         | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Public,NotActive    | NoAction     |
      | TC-37 : OMV-136 : User login as client user after pinning an account and validate Bulk actions availability for public group with active status                          | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Public,Active       | NoAction     |
      | TC-38 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for private group with active and non active statuses       | CountryUserName | CountryPassword | Group,Status        | Private,Active      | Group,Status        | Private,NotActive   | NoAction     |
      | TC-39 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for private group with active status                        | CountryUserName | CountryPassword | Group,Status        | Private,Active      | Group,Status        | Private,Active      | NoAction     |
      | TC-40 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for public,private groups with active status                | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Private,Active      | NoAction     |
      | TC-41 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for public,private groups with non active status            | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Private,NotActive   | NoAction     |
      | TC-42 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for public,private groups with active and non active status | CountryUserName | CountryPassword | Group,Status        | Public,NotActive    | Group,Status        | Private,Active      | NoAction     |
      | TC-43 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for public group with active and non active statuses        | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Public,NotActive    | NoAction     |
      | TC-44 : OMV-136 : User login as country user after pinning an account and validate Bulk actions availability for public group with active status                         | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Public,Active       | NoAction     |

  Scenario Outline: User validate 'Unassign from Account' action for single and bulk records after pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate header of module "Account information"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
    And User click on button "Unassign from account"
    And User validate header of module "Unassign from account"
    And User select "endDate" as "today"
    And User vaildate "EffectiveEndDate" is set to "today" in database
    When User click on "Submit" button
    Then User validate message "Pricing rule has been unassigned" based on tag name "div"
    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
    And User select check box which is at position "2"
    And User select check box which is at position "3"
    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
    And User click on button "Unassign from account"
    And User validate header of module "Unassign from account"
    And User select "endDate" as "today+3"
    And User vaildate "EffectiveEndDate" is set to "today+3" in database
    When User click on "Submit" button
    Then User validate message "<#> pricing rule(s) has been unassigned" based on tag name "div"
    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
    And User select check box which is at position "1"
    And User click on 'Unassign from account" icon at header level
    And User select "endDate" as "today+10"
    And User vaildate "EffectiveEndDate" is set to "today+10" in database
    When User click on "Submit" button
    Then User validate message "All pricing rules has been unassigned" based on tag name "div"

    Examples:
      | Scenario | UserName | PassWord |
      | TC-01 : OMV-363 : User perform 'Unassign from account' option for Bulk,single and more records after pinning customer for client user| ClientUserName|ClientPassWord|
      | TC-02 : OMV-363 : User perform 'Unassign from account' option for Bulk,single and more records after pinning customer for country user| CountryUserName|CountryPassWord|


  Scenario Outline: User validate 'Add to account' action for single and bulk records after pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate header of module "Account information"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
    And User click on button "Add to account"
    And User validate header of module "Add to account"
    And User select "endDate" as "today"
    And User vaildate "EffectiveEndDate" is set to "today" in database
    When User click on "Submit" button
    Then User validate message "Pricing rule has been assigned" based on tag name "div"
    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
    And User select check box which is at position "2"
    And User select check box which is at position "3"
    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
    And User click on button "Add to account"
    And User validate header of module "Add to account"
    And User select "endDate" as "today+3"
    And User vaildate "EffectiveEndDate" is set to "today+3" in database
    When User click on "Submit" button
    Then User validate message "<#> pricing rule(s) has been assigned" based on tag name "div"
    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
    And User select check box which is at position "1"
    And User click on 'Add to account" icon at header level
    And User select "endDate" as "today+10"
    And User vaildate "EffectiveEndDate" is set to "today+10" in database
    When User click on "Submit" button
    Then User validate message "All pricing rules has been assigned" based on tag name "div"

    Examples:
      | Scenario | UserName | PassWord |
      | TC-01 : OMV-363 : User perform 'Add to account' option for Bulk,single and more records after pinning customer for client user| ClientUserName|ClientPassWord|
      | TC-02 : OMV-363 : User perform 'Add to account' option for Bulk,single and more records after pinning customer for country user| CountryUserName|CountryPassWord|

  Scenario Outline: OMV-29,365,393 User assign account to the public group
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing templates"
    And User select check box which has fields "<Bulk action fields1>" and their values "<Bulk action values1>"
    And User select check box which has fields "<Bulk action fields2>" and their values "<Bulk action values2>"
    Then User validate "<Bulk actions>" icon is present and enabled
    And User click on "Assign to account" option
    And User click on button "Add"
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User select a random accounts from the list of the of accounts
    And User click on button "Next"
    And  User select a "start" date
    And User click on button "Review"
    And User click on edit icon in the "Account selected"
    And User select a random accounts from the list of the of accounts
    And User click on button "Next"
    And  User select a "end" date
    And User click on button "Review"
    And User click on button "Submit"
    And User validate success message of "Pricing template have been assigned to account(s)"

    Examples:
      | Scenario                                                                                                                                                  | UserName        | PassWord        | Bulk action fields1 | Bulk action values1 | Bulk action fields2 | Bulk action values2 | Bulk actions      | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC-01 : OMV-29,365,393 : User login as client user with out pinning an account and assign accounts to public group with active status                     | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Public,Active       | Assign to account | accountName              | text                       |
      | TC-02 : OMV-29,365,393: User login as country user with out pinning an account and validate Bulk actions availability for public group with active status | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Public,Active       | Assign to account | accountName              | text                       |

  Scenario Outline: OMV-394 Vaidating the review page of assign account to the public group
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing templates"
    And User select check box which has fields "<Bulk action fields1>" and their values "<Bulk action values1>"
    And User select check box which has fields "<Bulk action fields2>" and their values "<Bulk action values2>"
    Then User validate "<Bulk actions>" icon is present and enabled
    And User click on "Assign to account" option
    And User click on button "Add"
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User select a "All accounts" from the list
    And User click on button "Next"
    And  User select a "start" date
    And User click on button "Review"
    And User verifies whether the "View more" option is avaiable when more than 10 accounts is selected
    And User click on button "View more"
    And User verfies whether the account selected are avaiable in review page
    And User click on button "Submit"
    And User validate success message of "Pricing template have been assigned to account(s)"

    Examples:
      | Scenario                                                                                                                                                  | UserName        | PassWord        | Bulk action fields1 | Bulk action values1 | Bulk action fields2 | Bulk action values2 | Bulk actions      | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC-01 : OMV-394 : User login as client user with out pinning an account and assign accounts to public group with active status                     | ClientUserName  | ClientPassWord  | Group,Status        | Public,Active       | Group,Status        | Public,Active       | Assign to account | accountName              | text                       |
      | TC-02 : OMV-394: User login as country user with out pinning an account and validate Bulk actions availability for public group with active status | CountryUserName | CountryPassword | Group,Status        | Public,Active       | Group,Status        | Public,Active       | Assign to account | accountName              | text                       |

  Scenario Outline: OMV-464 Validate change status option at csr level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search all records with group as "Public" and status "Pending" in "Pricing rules" module
    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
    And User click on button "Approve"
    And User validate header of module "Approve"
    And User select "Reject" from drop down
    When User click on "Submit" button
    Then User validate message "Pricing rule has been approved" based on tag name "div"
    Then User search all records with group as "Private" and status "Pending" in "Pricing rules"
    And User select check box which is at position "2"
    And User select check box which is at position "3"
    And User click on button "Approve"
    And User validate header of module "Approve"
    And User select "Reject" from dorp down
    When User click on "Submit" button
    Then User validate message "2 pricing rules has been rejected" based on tag name "div"

    Examples:
      | UserName       | PassWord       |
      | ClientUserName | ClientPassWord |

  Scenario Outline: OMV-464 Validate change status option at single account level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search all records with group as "Public" and status "Pending" in "Pricing rules" module
    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
    And User click on button "Approve"
    And User validate header of module "Approve"
    And User select "Reject" from drop down
    When User click on "Submit" button
    Then User validate message "Pricing rule has been approved" based on tag name "div"
    Then User search all records with group as "Private" and status "Pending" in "Pricing rules"
    And User select check box which is at position "2"
    And User select check box which is at position "3"
    And User click on button "Approve"
    And User validate header of module "Approve"
    And User select "Reject" from dorp down
    When User click on "Submit" button
    Then User validate message "2 pricing rules has been rejected" based on tag name "div"

    Examples:
      | UserName       | PassWord       | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | ClientUserName | ClientPassWord | accountName              | text                       |