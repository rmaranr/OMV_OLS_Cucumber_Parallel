Feature: Component values search related scenarios

  Scenario Outline: OMV-31 User search validate Component values for client and country level accesses after pinned customer to the logged in user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option from context picker if logged in user has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "accounts" have been listed or not
    And User click on Three dot icon in module "accounts" based on search keywords "<fieldNameOfAccountSearch>"
    Then User click on button "Select account"
    And User validate account is displayed at the context picker based on "<fieldName>"
    And User validate header of module "Account information"
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Component values"
    Then User validate header of module "Component values"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingGroups>" based on its behavior "<pricingGroupSearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "pricingGroups" have been listed or not
    Then User verifies the count of records in "pricingGroups" and page navigation based on the count
    And User validate "default" section of fields "Group,Product,Country,Merchant,Network,SiteGroup,Status" in "pricingGroup" module
    And User validate "Summary" section of fields "StartDate,EndDate,EvaluationRule,Method,ValueMethod" in "pricingGroup" module
    And User validate "DiscountsAndRestrictions" section of fields "Discount1,PurchasesAllowedOn" in "pricingGroup" module

    Examples:
      | UserName     | PassWord | fieldNameOfAccountSearch | accountSearchFieldBehavior | fieldNameOfPricingGroups | pricingGroupSearchFieldBehavior |
    #Client level login to select account
      | csrClient02  | Test@123 | accountName              | text                       | PricingName              | text                            |
      | csrClient02  | Test@123 | accountNumber            | text                       | Country                  | dropDown                        |
      | csrClient02  | Test@123 | country                  | dropDown                   | Merchant                 | dropDown                        |
      | csrClient02  | Test@123 | cardNumber               | text                       | Network                  | dropDown                        |
      | csrClient02  | Test@123 | cardNumber               | text                       | SiteGroup                | dropDown                        |
    #Country level login to select account
      | csrCountry02 | Test@123 | accountName              | text                       | PricingName              | text                            |
      | csrCountry02 | Test@123 | accountNumber            | text                       | Country                  | dropDown                        |
      | csrCountry02 | Test@123 | country                  | dropDown                   | Merchant                 | dropDown                        |
      | csrCountry02 | Test@123 | cardNumber               | text                       | Network                  | dropDown                        |
      | csrCountry02 | Test@123 | cardNumber               | text                       | SiteGroup                | dropDown                        |

  Scenario Outline: OMV-31 User search validate Component values for client and country level accesses do not pin any customer to the logged in user
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User change the password created date past "90" days for user "UserName"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    And User click on the "menu" "Pricing"
    And User click on the "menu" "Component values"
    When User validate presence of "Search for pricing/period rebates" field with "<any>" tag
    And User select or enter value from a field "<fieldNameOfPricingGroups>" based on its behavior "<pricingGroupSearchFieldBehavior>"
    And User click on "Search" button
    Then Validate based on the search criteria the "pricingGroups" have been listed or not
    Then User verifies the count of records in "pricingGroups" and validate pagination by next and previous buttons and corresponding footer text
    #Description of above steps
    #User must be able to choose between 10,20 and 50 number of records to be displayed per page
    #Footer must indicate number of records loaded in the page out of the total records
    #As the user paginate the number of records displayed must keep updated. Ex: Initial load must have 1-10 of 100 as user moved to second page
    #it must be updated as 11 to 20 of 100
    And User validate "default" section of fields "Group,Product,Country,Merchant,Network,SiteGroup,Status" in "pricingGroup" module
    And User validate "Summary" section of fields "StartDate,EndDate,EvaluationRule,Method,ValueMethod" in "pricingGroup" module
    And User validate "DiscountsAndRestrictions" section of fields "Discount1,PurchasesAllowedOn" in "pricingGroup" module

    Examples:
      | UserName     | PassWord | fieldNameOfPricingGroups | pricingGroupSearchFieldBehavior |
    #Client level login to select account
      | csrClient02  | Test@123 | PricingName              | text                            |
      | csrClient02  | Test@123 | Country                  | dropDown                        |
      | csrClient02  | Test@123 | Merchant                 | dropDown                        |
      | csrClient02  | Test@123 | Network                  | dropDown                        |
      | csrClient02  | Test@123 | SiteGroup                | dropDown                        |
    #Country level login to select account
      | csrCountry02 | Test@123 | PricingName              | text                            |
      | csrCountry02 | Test@123 | Country                  | dropDown                        |
      | csrCountry02 | Test@123 | Merchant                 | dropDown                        |
      | csrCountry02 | Test@123 | Network                  | dropDown                        |
      | csrCountry02 | Test@123 | SiteGroup                | dropDown                        |