Feature: This feature file contains scenarios od Edit and one Pricing Rules

  Scenario Outline: OMV-356 Edit Pricing Rule based on Rule name then validate in the database
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search the newly created pricing rule using search field name "RuleName"
    And User click three dot icon of "1" record in module "Pricingrule" based on "RuleName"
    And User click on button "Edit" using span text
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "maxValue" as "10" in "pricingRules" module having length "2" in "add" form
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
    And User enter value "12" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
    And User enter value "15" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
    And User enter value "8" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "pricingRules" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
    Then User click on "Edit" based on tag name "button"
    And User enter "maxValue" as "15" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    And User enter value "22" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
    And User enter value "36" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add day and time restrictions" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit day and time restrictions" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate "snackbar-text" message "Pricing rule has been edited" in "pricingRules" module
    And User validate edited "pricingRules" with the database

    Examples:
      | Scenario                                                                                | UserName     | PassWord | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | categoryLocator                                                 |
      | TC01: OMV-27,356 Edit pricing rule by login using Client user with out pinning account  | csrClient02  | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |
      | TC02: OMV-27,356 Edit Pricing rule by login using Country user with out pinning account | csrCountry02 | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |

  Scenario Outline: Edit Pricing Rule based on Rule name then validate in the database after pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search the newly created pricing rule using search field name "RuleName"
    And User click three dot icon of "1" record in module "Pricingrule" based on "RuleName"
    And User click on button "Edit" using span text
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "maxValue" as "10" in "pricingRules" module having length "2" in "add" form
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
    And User enter value "12" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
    And User enter value "15" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
    And User enter value "8" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "pricingRules" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
    Then User click on "Edit" based on tag name "button"
    And User enter "maxValue" as "15" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    And User enter value "22" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
    And User enter value "36" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add day and time restrictions" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit day and time restrictions" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate "snackbar-text" message "Pricing rule has been edited" in "pricingRules" module
    And User validate edited "pricingRules" with the database

    Examples:
      | Scenario                                                                                | UserName     | PassWord | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | categoryLocator                                                 |
      | TC03: OMV-27,356 Edit pricing rule by login using Client user after pinning account  | csrClient02  | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |
      | TC04: OMV-27,356 Edit Pricing rule by login using Country user after pinning account | csrCountry02 | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |

  Scenario Outline: OMV-27,357 Clone Pricing Rule based on Rule name then validate in the database
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search the newly created pricing rule using search field name "RuleName"
    And User click three dot icon of "1" record in module "Pricingrule" based on "RuleName"
    And User click on button "Clone" using span text
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "maxValue" as "10" in "pricingRules" module having length "2" in "add" form
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
    And User enter value "12" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
    And User enter value "15" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
    And User enter value "8" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "pricingRules" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
    Then User click on "Edit" based on tag name "button"
    And User enter "maxValue" as "15" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    And User enter value "22" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
    And User enter value "36" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add day and time restrictions" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit day and time restrictions" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    And User validate edited "pricingRules" with the database

    Examples:
      | Scenario                                                                                | UserName     | PassWord | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | categoryLocator                                                 |
      | TC05: OMV-27,357 Clone pricing rule by login using Client user with out pinning account  | csrClient02  | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |
      | TC06: OMV-27,357 Clone Pricing rule by login using Country user with out pinning account | csrCountry02 | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |

  Scenario Outline: OMV-27,357 Clone Pricing Rule based on Rule name then validate in the database after pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "<fieldNameOfAccountSearch>" based on its behavior "<accountSearchFieldBehavior>" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    Then User validate based on the search criteria the record count displayed in module "accounts" or not for field "<fieldNameOfAccountSearch>" based on field value
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User search the newly created pricing rule using search field name "RuleName"
    And User click three dot icon of "1" record in module "Pricingrule" based on "RuleName"
    And User click on button "Clone" using span text
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "maxValue" as "10" in "pricingRules" module having length "2" in "add" form
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
    And User enter value "12" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
    And User enter value "15" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
    And User enter value "8" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "pricingRules" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
    Then User click on "Edit" based on tag name "button"
    And User enter "maxValue" as "15" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    And User enter value "22" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
    And User enter value "36" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add day and time restrictions" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit day and time restrictions" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    And User validate edited "pricingRules" with the database

    Examples:
      | Scenario                                                                                | UserName     | PassWord | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | categoryLocator                                                 |
      | TC07: OMV-27,357 Clone pricing rule by login using Client user with out pinning account  | csrClient02  | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |
      | TC08: OMV-27,357 Clone Pricing rule by login using Country user with out pinning account | csrCountry02 | Test@123 | Fixed       | Discount | Discount has been added | Discount has been updated | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1] |

  Scenario Outline: Replace Pricing Rule based on Rule name then validate in the database
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User enter "<UserName>" and "<PassWord>"
    And User click on login button
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    Then User click three dot icon for a record which contain group as "Public" and status "Active" in "Pricing templates" module
    And User click three dot icon of "1" record in module "Pricingrule" based on "RuleName"
    And User click on button "Replace" using span text
    And User Validate header text "Replace Pricing Rule"
    And User validate warning message "Replacing a pricing rule deletes the selected pricing rule, this action cannot be reveresed"
    Then User click on button "Yes" using span text
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User delete the category in "Edit" pricing rule pop up which is at position "1"
    And User click on button "+Add new"
    Then User click drop down "band" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
    And User enter "value" as "<value>" in "pricingRules" module having length "2" in "add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "Surchage has been added" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on "Edit day and time restrictions"
    And  User click on "Enable" option for the day "Tuesday"
    And  User choose a Random option from the "Start time"
    And  User chooses a  Random option from the "End time"
    And  User click on "Update"
    And  User validated success message of Edit status is "Day and time restrictions has been added"
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "effectiveStartDate" and select value no of days "1" of "Future" time
    And User validate "effectiveEndDate" value selected as today
    And User click on date field "effectiveEndDate" and select value no of days "3" of "Future" time
    And User enter ""RandomAlphanumeric"" as ""RandomAlphanumeric"" in "pricingRules" module having length "<any>" in "<any>" form using input tag
    And User click on button "<Review>"
    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit"
    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    And User validate edited "pricingRules" with the database

    Examples:
      | Scenario                                                                                | UserName     | PassWord |
      | TC01: OMV-361 : Clone pricing rule by login using Client user with out pinning account  | csrClient02  | Test@123 |
      | TC02: OMV-361 : Clone Pricing rule by login using Country user with out pinning account | csrCountry02 | Test@123 |

