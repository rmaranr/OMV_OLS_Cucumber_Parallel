Feature: Add Pricing Rule and search Pricing Rules scenarios will be available in this feature file

  Scenario Outline: OMV-58,120 User validate search functionality Pricing rules for client and country level accesses after pinned customer to the logged in user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    Then User select "Select account" option from context picker if logged in user has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "accounts" have been listed or not
    And User click on Three dot icon in module "accounts" based on search keywords "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "<fieldName>"
    And User validate header of module "Account information"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    Then User validate header of module "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "pricingrules" have been listed or not
    Then User verifies the count of records in "pricingrules" and page navigation based on the count
    And User validate "default" section of fields "Group,Product,Country,Merchant,Network,SiteGroup,Status" in "pricingRule" module
    And User validate "Summary" section of fields "StartDate,EndDate,EvaluationRule,Method,ValueMethod" in "pricingRule" module
    And User validate "Tier configuration" section of fields "All Bands field values" in "pricingRule" module
    And User validate "DayAndTimeconfiguration" section of fields "DayName, TimeAlongWithFormat" in "pricingRule" module

    Examples:
      | Scenario                                                                                                                                                              | UserName     | PassWord | fieldNameOfAccountSearch | pricingrulesearchFieldBehavior | fieldNameOfPricingrules | pricingrulesearchFieldBehavior |
    #Client level login to select account
      | TC01 :OMV-58,120: User search PricingRule with PricingName and validate default and expanded field values by logging in using client user after pinning the customer  | csrClient02  | Test@123 | accountName              | text                           | PricingName             | text                           |
      | TC02 :OMV-58,120: User search PricingRule with Country and validate default and expanded field values by logging in using client user after pinning the customer      | csrClient02  | Test@123 | accountNumber            | text                           | Country                 | dropDown                       |
      | TC03 :OMV-58,120: User search PricingRule with Merchant and validate default and expanded field values by logging in using client user after pinning the customer     | csrClient02  | Test@123 | country                  | dropDown                       | Merchant                | dropDown                       |
      | TC04 :OMV-58,120: User search PricingRule with Network and validate default and expanded field values by logging in using client user after pinning the customer      | csrClient02  | Test@123 | cardNumber               | text                           | Network                 | dropDown                       |
      | TC05 :OMV-58,120: User search PricingRule with SiteGroup and validate default and expanded field values by logging in using client user after pinning the customer    | csrClient02  | Test@123 | cardNumber               | text                           | SiteGroup               | dropDown                       |
    #Country level login to select account
      | TC06 :OMV-58,120: User search PricingRule with PricingName and validate default and expanded field values by logging in using country user after pinning the customer | csrCountry02 | Test@123 | accountName              | text                           | PricingName             | text                           |
      | TC07 :OMV-58,120: User search PricingRule with Country and validate default and expanded field values by logging in using country user after pinning the customer     | csrCountry02 | Test@123 | accountNumber            | text                           | Country                 | dropDown                       |
      | TC08 :OMV-58,120: User search PricingRule with Merchant and validate default and expanded field values by logging in using country user after pinning the customer    | csrCountry02 | Test@123 | country                  | dropDown                       | Merchant                | dropDown                       |
      | TC09 :OMV-58,120: User search PricingRule with Network and validate default and expanded field values by logging in using country user after pinning the customer     | csrCountry02 | Test@123 | cardNumber               | text                           | Network                 | dropDown                       |
      | TC10 :OMV-58,120: User search PricingRule with SiteGroup and validate default and expanded field values by logging in using country user after pinning the customer   | csrCountry02 | Test@123 | cardNumber               | text                           | SiteGroup               | dropDown                       |

  Scenario Outline: OMV-58,120 User validate search functionality Pricing rules for client and country level accesses do not pin any customer to the logged in user
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "pricingrules" have been listed or not
    Then User verifies the count of records in "pricingrules" and validate pagination by next and previous buttons and corresponding footer text
    #Description of above steps
    #User must be able to choose between 10,20 and 50 number of records to be displayed per page
    #Footer must indicate number of records loaded in the page out of the total records
    #As the user paginate the number of records displayed must keep updated. Ex: Initial load must have 1-10 of 100 as user moved to second page
    #it must be updated as 11 to 20 of 100
    And User validate "default" section of fields "Group,Product,Country,Merchant,Network,SiteGroup,Status" in "pricingRule" module
    And User validate "Summary" section of fields "StartDate,EndDate,EvaluationRule,Method,ValueMethod" in "pricingRule" module
    And User validate "Tier configuration" section of fields "All Bands field values" in "pricingRule" module
    And User validate "DayAndTimeconfiguration" section of fields "DayName, TimeAlongWithFormat" in "pricingRule" module

    Examples:
      | Scenario                                                                                                                                                                 | UserName     | PassWord | fieldNameOfPricingrules | pricingrulesearchFieldBehavior |
    #Client level login to select account
      | TC01 : OMV-58,120 : User search PricingRule with PricingName and validate default and expanded field values by logging in using client user with out pinning the customer | csrClient02  | Test@123 | PricingName             | text                           |
      | TC02 : OMV-58,120 :User search PricingRule with Country and validate default and expanded field values by logging in using client user with out pinning the customer      | csrClient02  | Test@123 | Country                 | dropDown                       |
      | TC03 : OMV-58,120 :User search PricingRule with Merchant and validate default and expanded field values by logging in using client user with out pinning the customer     | csrClient02  | Test@123 | Merchant                | dropDown                       |
      | TC04 : OMV-58,120 :User search PricingRule with Network and validate default and expanded field values by logging in using client user with out pinning the customer      | csrClient02  | Test@123 | Network                 | dropDown                       |
      | TC05 : OMV-58,120 :User search PricingRule with SiteGroup and validate default and expanded field values by logging in using client user with out pinning the customer    | csrClient02  | Test@123 | SiteGroup               | dropDown                       |
    #Country level login to select account
      | TC06 : OMV-58,120 :User search PricingRule with PricingName and validate default and expanded field values by logging in using country user with out pinning the customer | csrCountry02 | Test@123 | PricingName             | text                           |
      | TC07 : OMV-58,120 :User search PricingRule with Country and validate default and expanded field values by logging in using country user with out pinning the customer     | csrCountry02 | Test@123 | Country                 | dropDown                       |
      | TC08 : OMV-58,120 :User search PricingRule with Merchant and validate default and expanded field values by logging in using country user with out pinning the customer    | csrCountry02 | Test@123 | Merchant                | dropDown                       |
      | TC09 : OMV-58,120 :User search PricingRule with Network and validate default and expanded field values by logging in using country user with out pinning the customer     | csrCountry02 | Test@123 | Network                 | dropDown                       |
      | TC10 : OMV-58,120 :User search PricingRule with SiteGroup and validate default and expanded field values by logging in using country user with out pinning the customer   | csrCountry02 | Test@123 | SiteGroup               | dropDown                       |

  Scenario Outline: OMV-31 Validate user is able to select All,Multi,Single,No Selection of pricing rule records at client and country level
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "pricingrules" have been listed or not
    And User select check box which is at position "2"
    And User select check box which is at position "3"
    And User select check box which is at position "1"

    Examples:
      | Scenario                                                                                                                                                   | UserName     | PassWord | fieldNameOfPricingrules | pricingrulesearchFieldBehavior |
    #Client level login to select account
      | TC01 : OMV-58,120 : Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer | csrClient02  | Test@123 | PricingName             | text                           |
#      |TC02 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | Country                                        | dropDown                                 |
#      |TC03 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | Merchant                                       | dropDown                                 |
#      |TC04 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | Network                                        | dropDown                                 |
#      |TC05 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | SiteGroup                                      | dropDown                                 |
#      |TC06 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
    #Country level login to select account
      | TC07 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using country user with out pinning customer    | csrCountry02 | Test@123 | PricingName             | text                           |
#      |TC08 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using country user with out pinning customer| csrCountry02 | Test@123 | Country                                        | dropDown                                 |
#      |TC09 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using country user with out pinning customer| csrCountry02 | Test@123 | Merchant                                       | dropDown                                 |
#      |TC10 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using country user with out pinning customer| csrCountry02 | Test@123 | Network                                        | dropDown                                 |
#      |TC11 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using country user with out pinning customer| csrCountry02 | Test@123 | SiteGroup                                      | dropDown                                 |
#      |TC12 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using country user with out pinning customer| csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |

  Scenario Outline: OMV-31 Validate user is able to select All,Multi,Single,No Selection of pricing group records at client and country level
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    Then User select "Select account" option from context picker if logged in user has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "accounts" have been listed or not
    And User click on Three dot icon in module "accounts" based on search keywords "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "<fieldName>"
    And User validate header of module "Account information"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "pricingrules" have been listed or not
    And User select check box which is at position "2"
    And User select check box which is at position "3"
    And User select check box which is at position "1"

    Examples:
      | Scenario                                                                                                                                               | UserName     | PassWord | fieldNameOfPricingrules | pricingrulesearchFieldBehavior |
    #Client level login to select account
      | TC01 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer | csrClient02  | Test@123 | PricingName             | text                           |
#      |TC02 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | Country                                        | dropDown                                 |
#      |TC03 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | Merchant                                       | dropDown                                 |
#      |TC04 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | Network                                        | dropDown                                 |
#      |TC05 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | SiteGroup                                      | dropDown                                 |
#      |TC06 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
    #Country level login to select account
      | TC07 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer | csrCountry02 | Test@123 | PricingName             | text                           |
#      |TC08 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrCountry02 | Test@123 | Country                                        | dropDown                                 |
#      |TC09 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrCountry02 | Test@123 | Merchant                                       | dropDown                                 |
#      |TC10 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrCountry02 | Test@123 | Network                                        | dropDown                                 |
#      |TC11 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrCountry02 | Test@123 | SiteGroup                                      | dropDown                                 |
#      |TC12 : OMV-31: Validate user is able to select All,Multi,Single,No Selection of pricing rule by logging in using client user with out pinning customer| csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |

  Scenario Outline: OMV-31 Validate warning message "The selected criteria has more than <> results. Limit the results by refining the criteria or use the export feature to download all results.:
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then User validate warning message when the records count is more than expected in module "pricingrules"

    Examples:
      | UserName     | PassWord | fieldNameOfPricingrules                        | pricingrulesearchFieldBehavior           |
      | csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
      | csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |

  Scenario Outline: OMV-31 Validate warning message "The selected criteria has more than <> results. Limit the results by refining the criteria or use the export feature to download all results.:
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    Then User select "Select account" option from context picker if logged in user has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "accounts" have been listed or not
    And User click on Three dot icon in module "accounts" based on search keywords "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "<fieldName>"
    And User validate header of module "Account information"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then User validate warning message when the records count is more than expected in module "pricingrules"

    Examples:
      | UserName     | PassWord | fieldNameOfPricingrules                        | pricingrulesearchFieldBehavior           |
      | csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
      | csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |


  Scenario Outline: OMV-31 Export pricing rules and validate all records is exported based on database or not
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    When User click on sort option "Ascending"
    And User click on download button validate format of excel file is ".csv"
    And User open the excel sheet and validate the data present in it with the database for module "pricingrules" based on primary key "pricingRuleOID"

    Examples:
      | UserName     | PassWord | fieldNameOfPricingrules                        | pricingrulesearchFieldBehavior           |
      | csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
      | csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |

  Scenario Outline: OMV-31 Export pricing rules and validate all records is exported based on database or not
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    Then User select "Select account" option from context picker if logged in user has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "accounts" have been listed or not
    And User click on Three dot icon in module "accounts" based on search keywords "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "<fieldName>"
    And User validate header of module "Account information"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    When User click on sort option "Ascending"
    And User click on download button validate format of excel file is ".csv"
    And User open the excel sheet and validate the data present in it with the database for module "pricingrules" based on primary key "pricingRuleOID"

    Examples:
      | UserName     | PassWord | fieldNameOfPricingrules                        | pricingrulesearchFieldBehavior           |
      | csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
      | csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |

  Scenario Outline: Validate presence of actions for a pricing group for client and customer level user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then User click three dot icon of "1" record in module "pricingrules" based on "Name"
    And User verify button "Assign to account" status "enabled" using tag name "button"
    And User verify button "Change status" status "enabled" using tag name "button"
    And User verify button "Clone" status "enabled" using tag name "button"
    And User verify button "Edit" status "enabled" using tag name "button"

    Examples:
      | UserName     | PassWord | fieldNameOfPricingrules                        | pricingrulesearchFieldBehavior           |
      | csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
      | csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |

  Scenario Outline: Validate presence of actions for a pricing group for client and customer level user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    And User click on button "Home" using span text
    Then User select "Select account" option from context picker if logged in user has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "accounts" have been listed or not
    And User click on Three dot icon in module "accounts" based on search keywords "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "<fieldName>"
    And User validate header of module "Account information"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Pricing rules"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingrules>" based on its behavior "<pricingrulesearchFieldBehavior>"
    And User click on "Search" button
    Then User click three dot icon of "1" record in module "pricingrules" based on "Name"
    And User verify button "Assign to account" status "enabled" using tag name "button"
    And User verify button "Change status" status "enabled" using tag name "button"
    And User verify button "Clone" status "enabled" using tag name "button"
    And User verify button "Edit" status "enabled" using tag name "button"

    Examples:
      | UserName     | PassWord | fieldNameOfPricingrules                        | pricingrulesearchFieldBehavior           |
      | csrClient02  | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |
      | csrCountry02 | Test@123 | PricingName,Country,Merchant,Network,SiteGroup | text,dropDown,dropDown,dropDown,dropDown |