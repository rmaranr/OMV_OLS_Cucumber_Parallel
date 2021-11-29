Feature: Country User : Authorisation test cases

  Scenario Outline: Country User : Validate Default section field values in Authorisation module with out pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User validate message "Search for authorisations" based on tag name "div"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    And User select or enter value from a field "<fieldNameOfAuthorisationSearch>" based on its behavior "<authorisationSearchFieldBehavior>" for user "<UserName>" in module "authorisations"
    And User click on " Search " button
    And User validate "Default" omv section fields "Cardnumber,Account#,Tracenumber,Locationcode,Country,Status" values of Authorisation record with database
    And User validate "Authorisation Details" omv section fields "Authno.,Responsecode,Validationerror,Accountname,STAN,Batchnumber" values of Authorisation record with database
    And User validate "Authorisation Information" omv section fields "Costcentre,DriverID,VehicleID,Odometer,Secondcardnumber" values of Authorisation record with database
    And User validate "Authorisation BreakDown" omv section fields "Product,Numberofunits,Unitprice,total" values of Authorisation record with database

    Examples:
      | Scenario                                                                                                                                                                 | UserName        | PassWord        | fieldNameOfAuthorisationSearch | authorisationSearchFieldBehavior |
      | TC-01 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using cardNumber          | CountryUserName | CountryPassword | cardNumber                     | text                             |
      | TC-02 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using traceNumber         | CountryUserName | CountryPassword | traceNumber                    | text                             |
      | TC-03 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using date                | CountryUserName | CountryPassword | date                           | Last 30 days                     |
      | TC-04 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using driverName          | CountryUserName | CountryPassword | drivername                     | text                             |
      | TC-05 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using customerNumber      | CountryUserName | CountryPassword | customerNumber                 | text                             |
      | TC-06 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using locationId          | CountryUserName | CountryPassword | locationId                     | text                             |
      | TC-07 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using vehicleLicencePlate | CountryUserName | CountryPassword | licencePlate                   | text                             |
      | TC-08 : OMV-90,110 : Validate Authorisation default section field values with out pinning an account by logging in using csrCountry and search using costCentre          | CountryUserName | CountryPassword | costCentre                     | text                             |

  Scenario Outline: Country User : Validate Default section field values in Authorisation module after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountNumber" based on its behavior "text" for user "<UserName>" in module "authorisations"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User validate message "Search for authorisations" based on tag name "div"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    And User select or enter value from a field "<fieldNameOfAuthorisationSearch>" based on its behavior "<authorisationSearchFieldBehavior>" for user "<UserName>" in module "authorisations"
    And User click on " Search " button
    And User validate "Default" omv section fields "Cardnumber,Account#,Tracenumber,Locationcode,Country,Status" values of Authorisation record with database
    And User validate "Authorisation Details" omv section fields "Authno.,Responsecode,Validationerror,Accountname,STAN,Batchnumber" values of Authorisation record with database
    And User validate "Authorisation Information" omv section fields "Costcentre,DriverID,VehicleID,Odometer,Secondcardnumber" values of Authorisation record with database
    And User validate "Authorisation BreakDown" omv section fields "Product,Numberofunits,Unitprice,total" values of Authorisation record with database

    Examples:
      | Scenario                                                                                                                                                                | UserName        | PassWord        | fieldNameOfAuthorisationSearch | authorisationSearchFieldBehavior |
      | TC-09 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using cardNumber          | CountryUserName | CountryPassword | cardNumber                     | text                             |
      | TC-10 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using traceNumber         | CountryUserName | CountryPassword | traceNumber                    | text                             |
      | TC-11 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using date                | CountryUserName | CountryPassword | date                           | Last 90 days                     |
      | TC-12 : OMV-112,119 : Validate Author@isation default section field values after pinning the account by logging in using csrCountry and search using driverName         | CountryUserName | CountryPassword | driverName                     | text                             |
      | TC-13 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using vehicleLicencePlate | CountryUserName | CountryPassword | vehicleLicencePlate            | text                             |
      | TC-14 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using customerNumber      | CountryUserName | CountryPassword | customerNumber                 | text                             |
      | TC-15 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using locationId          | CountryUserName | CountryPassword | locationId                     | text                             |
      | TC-16 : OMV-112,119 : Validate Authorisation default section field values after pinning the account by logging in using csrCountry and search using costCentre          | CountryUserName | CountryPassword | costCentre                     | text                             |

  Scenario Outline: Country User : OMV-89 User validate pre-Search functionality at csr level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User validate header of module "Authorisations"
    And User enter values for fields "locationId,costCentre,driverName,vehicleLicencePlate" based on their behaviour "Last 90 days,text,text,text,text" in module "authorisations" and verify count of records after clicking on search button
    And User click on " Search " button
    And User validate record count of authorisations is displayed as mentioned in property "dbRecordCount"
    Examples:
      | Scenario                                                          | UserName        | PassWord        |
      | TC17 : OMV-89 User validate Search functionality for country user | CountryUserName | CountryPassword |

  Scenario Outline: Country User : OMV-89 User validate post-Search functionality at csr level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User select client and country based on logged in user "CountryUserName"
    And User make sure account is not pinned for logged in user
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User validate header of module "Authorisations"
    And User enter values for fields "locationId,costCentre,driverName,vehicleLicencePlate" based on their behaviour "Last 90 days,text,text,text,text" in module "authorisations" and verify count of records after clicking on search button
    And User click on " Search " button
    And User "expand" search bar and validate search fields with values "locationId,costCentre,driverName,vehicleLicencePlate"
    And User "collapse" search bar and validate search fields with values "locationId,costCentre,driverName,vehicleLicencePlate"
    Examples:
      | Scenario                                                               | UserName        | PassWord        |
      | TC18 : OMV-89 User validate post search functionality for country user | CountryUserName | CountryPassword |

  Scenario Outline: Country User : TC37: OMV-89 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    When User click on the "menu" "Transactions"
    When User click on the "subMenu" "Authorisations"
    And User enter value "782900017564738475937" using tag name "mav-input", attribute name "ng-reflect-name", attribute value "cardNumber" with remaining locator address ">input"
    And User click on " Search " button
    And User validate message of search results section "No results found,Please update your search keywords."
    Examples:
      | Scenario                                                                    | UserName        | PassWord        |
      | TC19 : OMV-89 User validate No results found error message for country user | CountryUserName | CountryPassword |

  Scenario Outline: Country User : OMV-116 User validate pre-Search functionality at single account level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter value from a field "accountNumber" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on " Search " button
    And User click three dot icon of "1" record in module "accounts" based on "accountNumber"
    Then User click on button "Select account"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User validate header of module "Authorisations"
    And User enter values for fields "locationId,costCentre,driverName,vehicleLicencePlate" based on their behaviour "Last 90 days,text,text,text,text" in module "authorisations" and verify count of records after clicking on search button
    And User click on " Search " button
    And User validate record count of authorisations is displayed as mentioned in property "dbRecordCount"
    Examples:
      | Scenario                                                          | UserName        | PassWord        |
      | TC20 : OMV-116 User validate Search functionality for country user | CountryUserName | CountryPassword |

  Scenario Outline: Country User : OMV-116 User validate post-Search functionality at single account level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get account Number which has records based on module "Authorisations"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on " Search " button
    And User click three dot icon of "1" record in module "accounts" based on "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User validate header of module "Authorisations"
    And User enter values for fields "costCentre,driverName,vehicleLicencePlate" based on their behaviour "Last 90 days,text,text,text,text" in module "authorisations" and verify count of records after clicking on search button
    And User click on " Search " button
    And User validate record count of authorisations is displayed as mentioned in property "dbRecordCount"
    And User "expand" search bar and validate search fields with values "locationId,costCentre,driverName,vehicleLicencePlate"
    And User "collapse" search bar and validate search fields with values "locationId,costCentre,driverName,vehicleLicencePlate"
    Examples:
      | Scenario                                                               | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC21 : OMV-116 User validate post search functionality for country user | CountryUserName | CountryPassword | accountNumber            | text                       |

  @Test
  Scenario Outline: Country User : Validate Filters in Authorisation module based on date field in client and country level with out pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User select client and country based on logged in user "CountryUserName"
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User select or enter value from a field "<fieldNameOfAuthorisationSearch>" based on its behavior "<authorisationSearchFieldBehavior>" for user "<UserName>" in module "authorisations"
    And User click on " Search " button
#    And User validate filter "All statuses" which is at position "1" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "All issuer countries" which is at position "2" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "All products" which is at position "3" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "Dynamic date" which is at position "4" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate records displayed based on the selected values from filters 'All statuses', 'All issuer countries', 'All products', 'Dynamic date'

    Examples:
      | Scenario                                                                                                                                         | UserName        | PassWord        | fieldNameOfAuthorisationSearch | authorisationSearchFieldBehavior |
      | TC-22 : OMV-117 : Validate Authorisation filters with out pinning an account by logging in using csrCountry and search using date 'Last 90 days' | CountryUserName | CountryPassword | date                           | Last 90 days                     |
#      | TC-23 : OMV-117 : Validate Authorisation filters with out pinning an account by logging in using csrCountry and search using date 'Custom'       | CountryUserName | CountryPassword | date                           | Custom                           |

  Scenario Outline: Country User : Validate Filters in Authorisation module based on date field in client and country level after pinning pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    And User get account Number which has records based on module "Authorisations"
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    Then User validate header of module "Accounts"
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
    And User validate presence of "Account information" field with "div" tag
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User select or enter value from a field "<fieldNameOfAuthorisationSearch>" based on its behavior "<authorisationSearchFieldBehavior>" for user "<UserName>" in module "authorisations"
    And User click on " Search " button
#    And User validate filter "All statuses" which is at position "1" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "All issuer countries" which is at position "2" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "All products" which is at position "3" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "Dynamic date" which is at position "4" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate records displayed based on the selected values from filters 'All statuses', 'All issuer countries', 'All products', 'Dynamic date'

    Examples:
      | Scenario                                                                                                                                      | UserName        | PassWord        | fieldNameOfAuthorisationSearch | authorisationSearchFieldBehavior |
      | TC-24 : OMV-118 : Validate Authorisation filters after pinning an account by logging in using csrCountry and search using date 'Last 90 days' | CountryUserName | CountryPassword | date                           | Last 90 days                     |
      | TC-25 : OMV-118 : Validate Authorisation filters after pinning an account by logging in using csrCountry and search using date 'Custom'       | CountryUserName | CountryPassword | date                           | Custom                           |

#  Scenario Outline: Country User : User validate export behaviour of Authorisations
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    And User click on button "Home" using span text
#    When User click on the "menu" "Transactions"
#    And  User click on the "submenu" "Authorisations"
#    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientUserName" in module "Authorisations"
#    And User click on " Search " button
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "OMVAuthorisations" based on primary key "AccountNo"
#
#
#    Examples:
#      | Scenario                                                                                                | UserName       | PassWord       | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
#      | TC-01 : OMV-96 : Validate the export details of authorisation for Client user with out pinning account | CountryUserName | CountryPassWord | customerNumber               | text                           |