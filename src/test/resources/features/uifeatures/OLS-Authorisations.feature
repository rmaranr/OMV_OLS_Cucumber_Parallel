Feature: This feature file contains Pre-Search, Search and Post-Search functionality of transaction

  Scenario Outline: Validate Default section field values in Authorisation module with out pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User select account from account picker which has authorisations based on field "<fieldNameOfAuthorisationSearch>"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User validate message "Search for authorisations" based on tag name "div"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    And User select or enter value from a field "<fieldNameOfAuthorisationSearch>" based on its behavior "<authorisationSearchFieldBehavior>" for user "<OLSUserName>" in module "authorisations"
    And User click on " Search " button
    And User validate "Default" omv section fields "Cardnumber,Account#,Tracenumber,Locationcode,Country,Status" values of Authorisation record with database
    And User validate "Authorisation Details" omv section fields "Authno.,Responsecode,Validationerror,Accountname,STAN,Batchnumber" values of Authorisation record with database
    And User validate "Authorisation Information" omv section fields "Costcentre,DriverID,VehicleID,Odometer,Secondcardnumber" values of Authorisation record with database
    And User validate "Authorisation BreakDown" omv section fields "Product,Numberofunits,Unitprice,total" values of Authorisation record with database

    Examples:
      | Scenario                                                                                                                                                          | OLSUserName | OLSPassWord | fieldNameOfAuthorisationSearch | authorisationSearchFieldBehavior |
      | TC-01 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using cardNumber     | OLSUserName | OLSPassWord | cardNumber                     | text                             |
      | TC-02 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using traceNumber    | OLSUserName | OLSPassWord | traceNumber                    | text                             |
      | TC-03 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using customerNumber | OLSUserName | OLSPassWord | customerNumber                 | text                             |
##      | TC-04 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using locationId     | OLSUserName | OLSPassWord | locationId                     | text                             |
      | TC-05 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using date           | OLSUserName | OLSPassWord | date                           | Last 90 days                     |
      | TC-06 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using driverName     | OLSUserName | OLSPassWord | driverName                     | text                             |
      | TC-07 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using licencePlate   | OLSUserName | OLSPassWord | licencePlate                   | text                             |
      | TC-08 : OMV-91,92 : Validate Authorisation default section field values with out pinning an account by logging in using csrClient and search using costCentre     | OLSUserName | OLSPassWord | costCentre                     | text                             |

  Scenario Outline: OMV-91,92 User validate pre-Search functionality at csr level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has authorisations based on field "cardNumber"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User validate header of module "Authorisations"
    And User enter values for fields "costCentre,driverName,vehicleLicencePlate" based on their behaviour "Last 90 days,text,text,text,text" in module "authorisations" and verify count of records after clicking on search button
    And User click on " Search " button
    And User validate record count of authorisations is displayed as mentioned in property "dbRecordCount"
    Examples:
      | Scenario                                                           | OLSUserName | OLSPassWord |
      | TC9 : OMV-91,92 User validate Search functionality for client user | OLSUserName | OLSPassWord |

  Scenario Outline: OMV-89 User validate post-Search functionality at csr level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has authorisations based on field "cardNumber"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    And User click on the "menu" "Transactions"
    And User click on the "submenu" "Authorisations"
    Then User validate header of module "Authorisations"
    And User enter values for fields "costCentre,driverName,vehicleLicencePlate" based on their behaviour "Last 90 days,text,text,text,text" in module "authorisations" and verify count of records after clicking on search button
    And User click on " Search " button
    And User "expand" search bar and validate search fields with values "locationId,costCentre,driverName,vehicleLicencePlate"
    And User "collapse" search bar and validate search fields with values "locationId,costCentre,driverName,vehicleLicencePlate"
    Examples:
      | Scenario                                                                 | OLSUserName | OLSPassWord |
      | TC10 : OMV-91,92 User validate post search functionality for client user | OLSUserName | OLSPassWord |

  Scenario Outline: TC37: OMV-89 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has authorisations based on field "cardNumber"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    When User click on the "subMenu" "Authorisations"
    And User enter value "782900017564738475937" using tag name "mav-input", attribute name "ng-reflect-name", attribute value "cardNumber" with remaining locator address ">input"
    And User click on " Search " button
    And User validate message of search results section "No results found,Please update your search keywords."
    Examples:
      | Scenario                                                                      | OLSUserName | OLSPassWord |
      | TC11 : OMV-91,92 User validate No results found error message for client user | OLSUserName | OLSPassWord |

  Scenario Outline: Validate Filters in Authorisation module based on date field in client and country level with out pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User enter "<OLSUserName>" and "<OLSPassWord>"
#    And User click on login button
    And User click on button "Home" using span text
    And User select account from account picker which has authorisations based on field "cardNumber"
    And User set property "accountNumberInTransactionsModule" value as "pinnedAccountNumberInUI" in "TestDataProperties"
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User select or enter value from a field "<fieldNameOfAuthorisationSearch>" based on its behavior "<authorisationSearchFieldBehavior>" for user "<OLSUserName>" in module "authorisations"
    And User click on " Search " button
    And User validate filter "All statuses" which is at position "1" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "All issuer countries" which is at position "2" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "All products" which is at position "3" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate filter "Dynamic date" which is at position "4" values are displayed and enabled or disabled based on searched filed "<fieldNameOfAuthorisationSearch>" as per the database or not
#    And User validate records displayed based on the selected values from filters 'All statuses', 'All issuer countries', 'All products', 'Dynamic date'

    Examples:
      | Scenario                                                                                                                                          | OLSUserName | OLSPassWord | fieldNameOfAuthorisationSearch | authorisationSearchFieldBehavior |
      | TC-12 : OMV-91,92 : Validate Authorisation filters with out pinning an account by logging in using csrClient and search using date 'Last 90 days' | OLSUserName | OLSPassWord | date                           | Last 90 days                     |
      | TC-13 : OMV-91,92 : Validate Authorisation filters with out pinning an account by logging in using csrClient and search using date 'Custom'       | OLSUserName | OLSPassWord | date                           | Custom                           |

  @Test
  Scenario Outline: User validate export behaviour of Authorisations
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User click on the "menu" "Transactions"
    And  User click on the "submenu" "Authorisations"
    And User select or enter value from a field "<fieldNameOfTransactionSearch>" based on its behavior "<transactionSearchFieldBehavior>" for user "ClientOLSUserName" in module "Authorisations"
    And User click on " Search " button
    And User click on download button validate format of excel file is ".csv"
    And User open the excel sheet and validate the data present in it with the database for module "OMVAuthorisations" based on primary key "AccountNo"


    Examples:
      | Scenario                                                                                                | OLSUserName       | OLSPassWord       | fieldNameOfTransactionSearch | transactionSearchFieldBehavior |
      | TC-01 : OMV-96 : Validate the export details of authorisation for Client user with out pinning account | OLSUserName | ClientOLSPassWord | customerNumber               | text                           |