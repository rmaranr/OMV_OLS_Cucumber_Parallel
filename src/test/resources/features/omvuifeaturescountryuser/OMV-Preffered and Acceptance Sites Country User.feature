Feature: Preferred sites and Acceptance sites functionality will be available in the feature

  @Test
  Scenario Outline: OMV 216 : Validate the search of the preferred sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Preferred sites"
    Then User validate header of module "Preferred sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "preferredsites"
    And User click on "Search" button
    And User validate filter "All countries" which is at position "1" values are displayed and enabled or disabled based on searched filed

    Examples:
      | Scenario                                                                                                          | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC02 : OMV 216:  Validate the search of the preferred sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |

  @Test
  Scenario Outline: OMV 216 : Validate the sort functionality of preferred sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Preferred sites"
    Then User validate header of module "Preferred sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "preferredsites"
    And User click on "Search" button
    And User verifies the "Preferred sites" page sort option Sort by Newest
    And User verifies the "Preferred sites" page sort option Sort by Oldest

    Examples:
      | Scenario                                                                                                                 | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC04 : OMV 216: Validate the sort functionality of preferred sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |


  @Test
  Scenario Outline: OMV 299 : Validate the export functionality of preferred sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Preferred sites"
    Then User validate header of module "Preferred sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "preferredsites"
    And User click on "Search" button
    And User click on download button validate format of excel file is ".csv"

    Examples:
      | Scenario                                                                                                                   | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC02 : OMV 299: Validate the export functionality of preferred sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |

  @Test
  Scenario Outline: OMV 299 : Validate the remove site functionality of preferred sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Preferred sites"
    Then User validate header of module "Preferred sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "preferredsites"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "preferredsites" based on "preferredSites"
    And User click on button "Remove sites"
    And User click on "Delete" button
    Then User validate "snackbar-text" message "Site has been deleted" in "transactions" module


    Examples:
      | Scenario                                                                                                                        | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC04 : OMV 299: Validate the remove site functionality of preferred sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |


  @Test
  Scenario Outline: OMV 217 : Validate the Add site functionality of preferred sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Preferred sites"
    Then User validate header of module "Preferred sites"
    And  User click on button "+ Sites" using span text
    And User select or enter value from a field "<fieldNameOfPreferredSites>" based on its behavior "<siteNameSearchFieldBehavior>" for user "<UserName>" in module "preferredsites"
    And User click on "Search" button
    And User select a random accounts from the list of the of accounts
    And User click on button "Add"
    And User validate "snackbar-text" message "1 site(s) has been added" in "preferredSites" module

    Examples:
      | Scenario                                                                                                                     | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior | fieldNameOfPreferredSites | siteNameSearchFieldBehavior |
      | TC02 : OMV 217: Validate the Add site functionality of preferred sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       | country                   | dropDown                    |

    ######################################################################  Acceptance Sites test cases #################3Feature:
  @Test
  Scenario Outline: OMV 903 : Validate the search of the Acceptance sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Acceptance sites"
    Then User validate header of module "Acceptance sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "Acceptancesites"
    And User click on "Search" button
    And User validate filter "All countries" which is at position "1" values are displayed and enabled or disabled based on searched filed

    Examples:
      | Scenario                                                                                                          | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC02 : OMV 903:  Validate the search of the Acceptance sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |

  @Test
  Scenario Outline: OMV 903 : Validate the sort functionality of Acceptance sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Acceptance sites"
    Then User validate header of module "Acceptance sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "Acceptancesites"
    And User click on "Search" button
    And User verifies the "Acceptance sites" page sort option Sort by Newest
    And User verifies the "Acceptance sites" page sort option Sort by Oldest

    Examples:
      | Scenario                                                                                                                 | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC04 : OMV 903: Validate the sort functionality of Acceptance sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |


  @Test
  Scenario Outline: OMV 903 : Validate the export functionality of Acceptance sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Acceptance sites"
    Then User validate header of module "Acceptance sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "Acceptancesites"
    And User click on "Search" button
    And User click on download button validate format of excel file is ".csv"

    Examples:
      | Scenario                                                                                                                   | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC02 : OMV 903: Validate the export functionality of Acceptance sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |

  @Test
  Scenario Outline: OMV 903 : Validate the remove site functionality of Acceptance sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Acceptance sites"
    Then User validate header of module "Acceptance sites"
    And User select or enter value from a field "searchBySiteName" based on its behavior "text" for user "<UserName>" in module "Acceptancesites"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "Acceptancesites" based on "AcceptanceSites"
    And User click on button "Remove sites"
    And User click on "Delete" button
    Then User validate "snackbar-text" message "Site has been deleted" in "transactions" module


    Examples:
      | Scenario                                                                                                                        | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior |
      | TC04 : OMV 903: Validate the remove site functionality of Acceptance sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       |


  @Test
  Scenario Outline: OMV 905 : Validate the Add site functionality of Acceptance sites
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Acceptance sites"
    Then User validate header of module "Acceptance sites"
    And  User click on button "+ Sites" using span text
    And User select or enter value from a field "<fieldNameOfAcceptanceSites>" based on its behavior "<siteNameSearchFieldBehavior>" for user "<UserName>" in module "Acceptancesites"
    And User click on "Search" button
    And User select a random accounts from the list of the of accounts
    And User click on button "Add"
    And User validate "snackbar-text" message "1 site(s) has been added" in "AcceptanceSites" module

    Examples:
      | Scenario                                                                                                                     | UserName        | PassWord        | fieldNameOfAccountSearch | accountSearchFieldBehavior | fieldNameOfAcceptanceSites | siteNameSearchFieldBehavior |
      | TC02 : OMV 905: Validate the Add site functionality of Acceptance sites at country  level configuration after pinning account | CountryUserName | CountryPassWord | accountName              | text                       | country                   | dropDown                    |