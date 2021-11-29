Feature: This feature file contains add pricing rules and Bonuses scenarios

  @Test
  Scenario Outline: OMV 202,203,204,205,455,418 : Validate the creating pricing rules at client level with Discount
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mat-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mat-select" and attribute "ng-reflect-name"
#    Then User enter value "OMV" using locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form and click on "Apply" button using tag "span"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
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
    And User click on button contains "ThreeDot Icon of Discount option" using locator "button[class='menu-button-style mat-focus-indicator mat-menu-trigger mat-button mat-button-base']>span>div>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "click"
    Then User click on "Edit" based on tag name "button"
    And User enter "maxValue" as "15" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    And User enter value "22" in text field "PricingRule-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
    And User enter value "36" in text field "PricingRule-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
#    And User validate message "Pricing rule has been created" based on tag name "div"
    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    Examples:
      | Scenario                                                                                                                                     | UserName        | PassWord        | valueMethod | category  | AddCategoryMessage       | UpdateCategoryMessage      | categoryLocator                                                  |
      | TC01 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration before pinning account with discount   | ClientUserName  | ClientPassWord  | Fixed       | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
#      | TC02 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration before pinning account with discount  | CountryUserName | CountryPassword | Fixed       | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
      | TC03 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration before pinning account with Surcharge  | ClientUserName  | ClientPassWord  | Fixed       | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |
#      | TC04 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration before pinning account with Surcharge | CountryUserName | CountryPassword | Fixed       | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |

  Scenario Outline: OMV 202,203,204,205,455,418 : Create pricing rule at CSR level using CPL option using discount and Surcharge option
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "minValue" as "<minLiters>" in "pricingRules" module having length "2" in "add" form
    And User enter "maxValue" as "<maxLiters>" in "pricingRules" module having length "2" in "add" form
    And User click on button contains "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "All" using locator "//mat-option[@ng-reflect-value='All']/span[contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User enter value "12" in text field "PricingRule-Band-Value" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "pricingRules" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "button[class='menu-button-style mat-focus-indicator mat-menu-trigger mat-button mat-button-base']>span>div>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "jsExecutor"
    Then User click on "Edit" based on tag name "button"
    And User enter "minValue" as "<minLiters>" in "pricingRules" module having length "2" in "add" form
#    #Enter max liters is less than min liters
    And User enter "maxValue" as "<maxLitersIsLessThanMinLiters>" in "pricingRules" module having length "2" in "add" form
    And User validate message "Enter a valid max liters" based on tag name "mat-error"
    And User enter "maxValue" as "<maxLiters>" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-Value" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate message "Pricing rule has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    Examples:
      | Scenario                                                                                                                                                                   | UserName        | PassWord        | valueMethod | minLiters | maxLiters | value | maxLitersIsLessThanMinLiters | category  | AddCategoryMessage       | UpdateCategoryMessage      | categoryLocator                                                  |
      | TC05 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration before pinning account with CPL Value Method for Discount category   | ClientUserName  | ClientPassWord  | CPL         | 5         | 10        | 10    | 2                            | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
#      | TC06 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration before pinning account with CPL Value Method for Discount Category  | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
      | TC07 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration before pinning account with CPL Value Method for Surcharge category  | ClientUserName  | ClientPassWord  | CPL         | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |
#      | TC08 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration before pinning account with CPL Value Method for Surcharge Category | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |

  Scenario Outline: OMV 202,203,204,205,455,418 : Validate the creating pricing rules at account level with Discount
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "minValue" as "<minLiters>" in "pricingRules" module having length "2" in "add" form
    And User enter "maxValue" as "<maxLiters>" in "pricingRules" module having length "2" in "add" form
    And User click on button contains "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "All" using locator "//mat-option[@ng-reflect-value='All']/span[contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User enter value "12" in text field "PricingRule-Band-Value" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "pricingRules" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "button[class='menu-button-style mat-focus-indicator mat-menu-trigger mat-button mat-button-base']>span>div>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "jsExecutor"
    Then User click on "Edit" based on tag name "button"
    And User enter "minValue" as "<minLiters>" in "pricingRules" module having length "2" in "add" form
#    #Enter max liters is less than min liters
    And User enter "maxValue" as "<maxLitersIsLessThanMinLiters>" in "pricingRules" module having length "2" in "add" form
    And User validate message "Enter a valid max liters" based on tag name "mat-error"
    And User enter "maxValue" as "<maxLiters>" in "pricingRules" module having length "2" in "add" form
    And User enter value "16" in text field "PricingRule-Band-Value" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "pricingRules" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click " Edit" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate message "Pricing rule has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    Examples:
      | Scenario                                                                                                                                                                  | UserName        | PassWord        | valueMethod | minLiters | maxLiters | value | maxLitersIsLessThanMinLiters | category  | AddCategoryMessage       | UpdateCategoryMessage      | categoryLocator                                                  |
      | TC09 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration after pinning account with CPL Value Method for Discount category   | ClientUserName  | ClientPassWord  | CPL         | 5         | 10        | 10    | 2                            | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
#      | TC10 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration after pinning account with CPL Value Method for Discount Category  | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
      | TC11 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration after pinning account with CPL Value Method for Surcharge category  | ClientUserName  | ClientPassWord  | CPL         | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |
#      | TC12 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration after pinning account with CPL Value Method for Surcharge Category | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |
      | TC15 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration after pinning account with CPL Value Method for Surcharge category  | ClientUserName  | ClientPassWord  | Percentage  | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |
#      | TC16 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration after pinning account with CPL Value Method for Surcharge Category | CountryUserName | CountryPassword | Percentage  | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |

  Scenario Outline: OMV 202,203,204,205,455,418 : Validate the creating pricing rules at account level for Fixed value
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Pricing rules"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
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
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "PricingRule-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
    And  User click on " +Add" based on tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    And User click "Add" button using Java Script executor which is present at position "2" using tag name "span"
    And User validate "snackbar-text" message "Day and time restrictions has been added" in "pricingRules" module
    And User click "Edit" button using Java Script executor which is present at position "1" using tag name "div"
    And User click on button contains "Monday 24 hrs" using locator "//div[@class='price-value ng-star-inserted'][1]/div/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "Day and time restrictions has been updated" in "pricingRules" module
    And User click "Next" button using Java Script executor which is present at position "3" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "pricingRules" module having length "8" in "add" form using input tag
    And User enter value "National CRT" in text field "PricingRule-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Pricing rules
    And User click on button "Submit" using span text
    And User validate message "Pricing rule has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Pricing rule has been created" in "pricingRules" module
    Examples:
      | Scenario | UserName | PassWord | valueMethod | category | AddCategoryMessage | UpdateCategoryMessage | categoryLocator |
      | TC13 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at client level configuration after pinning account with discount | ClientUserName | ClientPassWord | Fixed       | Discount | Discount has been added | Discount has been updated |(//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]|
#      | TC14 : OMV 202,203,204,205,455,418: Validate the creating Pricing rules at country level configuration after pinning account with discount | CountryUserName | CountryPassword | Fixed       | Discount | Discount has been added | Discount has been updated |(//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]|

#  Scenario Outline: OMV 202,203,204,205 : Validate the Delete band of pricing rules
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User click on the "menu" "Pricing"
#    And  User click on the "submenu" "Pricing rules"
#    And  User click on button "+ New" using span text
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
#    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "method" then select value "Random" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "valueMethod" then select value "<valueMethod>" using tag "mat-select" and attribute "ng-reflect-name"
#    And User click on button "+Add new" using span text
#    And User enter "minValue" as "<minValue>" in "pricingRules" module having length "2" in "add" form
#    And User enter "maxValue" as "<maxValue>" in "pricingRules" module having length "2" in "add" form
#    Then User click drop down "band" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User enter "value" as "<value>" in "pricingRules" module having length "2" in "add" form
#    And User click on button "+Add new"
#    Then User click drop down "band" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User enter "value" as "<value>" in "pricingRules" module having length "2" in "add" form
#    And User click on button " Add" using span text
#    And User validate "snackbar-text" message "Discount has been added" in "pricingRules" module
#    And User click button "threeDotIcon" using tag name "div", attribute name "class", attribute value "header-menu ng-star-inserted"
#    Then User click on "Edit" based on tag name "button"
#    And User enter "minValue" as "<minLiters>" in "pricingRules" module having length "2" in "add" form
#    And User enter "maxValue" as "<maxLitersIsLessThanMinLiters>" in "pricingRules" module having length "2" in "add" form
#    And User validate the error message "Enter a valid max liters"
#    And User enter "maxValue" as "<maxLiters>" in "pricingRules" module having length "2" in "add" form
#    Then User click on button "Update" using span text
#    And User validate "snackbar-text" message "Discount has been updated" in "pricingRules" module
#    And  User click on Three dot icon in module "pricingTemplate" based on search keywords "<Delete>"
#    And User click on "Delete" button
#    And  User validated success message of Edit status is "Discount has been deleted"
#    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
#    And  User click on "Add day and time restrictions"
#    And  User click on "Enable" option for the day "Monday"
#    And  User choose a Random option from the "Start time"
#    And  User chooses a  Random option from the "End time"
#    And  User click on "Add"
#    And  User validated success message of Edit status is "Day and time restrictions has been added"
#    And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
#    And User click on date field "effectiveStartDate" and select value no of days "0" of "Past" time
#    And User click on date field "effectiveEndDate" and select value no of days "2" of "Future" time
#    And User enter ""RandomAlphanumeric"" as ""RandomAlphanumeric"" in "pricingRules" module having length "<any>" in "<any>" form using input tag
#    And User click on button "<Review>"
#    And User validate all stepper values in Review page at Pricing rules
#    And User click on button "Submit"
#    And User validate "snackbar-text" message "Pricing rule has been added" in "pricingRules" module
##    And User validate newly added "pricingRules" with the database
#    Examples:
#      | Scenario                                                                                                            | UserName        | PassWord        | valueMethod | minLiters | maxLiters | value | maxLitersIsLessThanMinLiters |
#      | TC05 : OMV 202,203,204,205: Validate the band deletion at client level configuration before pinning account         | ClientUserName  | ClientPassWord  | CPL         | 5         | 10        | 10    | 2                            |
#      | TC06 : OMV 202,203,204,205: Validate the band deletion rules at country  level configuration before pinning account | CountryUserName | CountryPassword | CPL         | 6         | 8         | 6     | 5                            |
#
#  Scenario Outline: User validate default view and expanded view sections values and filters behaviour of pricing rules
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
#    Then User validate header of module "Accounts"
#    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#    And User set accountNumber value is property file like "accountNumberFromAccountPicker" in Transactions module
#    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
#    When User click on the "menu" "Pricing"
#    When User click on the "menu" "Pricing rules"
#    And User select or enter value from a field "<fieldNameOfPricingRuleSearch>" based on its behavior "<pricingRuleSearchFieldBehavior>" for user "<UserName>" in module "pricingRules"
#    And User click on "Search" button
#    And User validate "Default" omv section fields "Rule name,Labels,Product,Acquiring country,Merchant,Network,Site group,Eval rule,Status" values of PricingRule record with database
#    And User validate "Summary" omv section fields "Issuing country,Card product group,Segment" values of PricingRule record with database
#    And User validate "ADDITIONAL INFORMATION" omv section fields "Effective start,Effective end,Eval rule,Method,Value Method,Category" values of PricingRule record with database
#    And User validate "Tier configuration" omv section fields "Header,description" values of PricingRule record with database
#    And User validate "MERCHANT FUNDED" omv section fields "Contribution type,Value" values of PricingRule record with database
#    And User validate "PRICE VALID AT" omv section fields "description" values of PricingRule record with database
#    Then User validate filters "All labels,All evaluation rules,All issuing countries, All statuses", behavior in "pricingRules" module
#
#    Examples:
#      | Scenario                                                                                                                       | UserName        | PassWord        | fieldNameOfPricingRuleSearch | pricingRuleSearchFieldBehavior |
#      | TC-01 : OMV-353 User validate default,Expanded field values and filters od Pricingrules for client user after pinning account  | ClientUserName  | ClientPassWord  | Network                      | AllNetworks                    |
#      | TC-02 : OMV-353 User validate default,Expanded field values and filters od Pricingrules for country user after pinning account | CountryUserName | CountryPassword | Network                      | AllNetworks                    |
#
#  Scenario Outline: User validate default view and expanded view sections values and filters behaviour of pricing rules
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User click on the "menu" "Pricing"
#    When User click on the "menu" "Pricing rules"
#    And User select or enter value from a field "<fieldNameOfPricingRuleSearch>" based on its behavior "<pricingRuleSearchFieldBehavior>" for user "<UserName>" in module "pricingRules"
#    And User click on "Search" button
#    And User validate "Default" omv section fields "Rule name,Labels,Product,Acquiring country,Merchant,Network,Site group,Eval rule,Status" values of PricingRule record with database
#    And User validate "Summary" omv section fields "Issuing country,Card product group,Segment" values of PricingRule record with database
#    And User validate "ADDITIONAL INFORMATION" omv section fields "Start date,End date,Eval rule,Method,Value Method,Category" values of PricingRule record with database
#    And User validate "Tier configuration" omv section fields "Header,description" values of PricingRule record with database
#    And User validate "MERCHANT FUNDED" omv section fields "Contribution type,Value" values of PricingRule record with database
#    And User validate "PRICE VALID AT" omv section fields "description" values of PricingRule record with database
#    And User validate "Date section" omv section fields "Start date, End date" values of PricingRule record with database
#    Then User validate filters "All labels,All evaluation rules,All issuing countries, All statuses", behavior in "pricingRules" module
#
#    Examples:
#      | Scenario                                                                                                                          | UserName        | PassWord        | fieldNameOfPricingRuleSearch | pricingRuleSearchFieldBehavior |
#      | TC-03 : OMV-353 User validate default,Expanded field values and filters od Pricingrules for client user with out pinning account  | ClientUserName  | ClientPassWord  | Network                      | AllNetworks                    |
#      | TC-04 : OMV-353 User validate default,Expanded field values and filters od Pricingrules for country user with out pinning account | CountryUserName | CountryPassword | Network                      | AllNetworks                    |
#
#  Scenario Outline: Bonuses-User validate 'Unassign from Account' action for single and bulk records after pinning account in Bonuses module
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
#    Then User validate header of module "Accounts"
#    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#    And User validate account is displayed at the context picker based on "property" value "accounts-accountName"
#    And User validate header of module "Account information"
#    When User click on the "menu" "Pricing"
#    And  User click on the "submenu" "Bonus"
#    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
#    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
#    And User click on button "Unassign from account"
#    And User validate header of module "Unassign from account"
#    And User select "endDate" as "today"
#    And User vaildate "EffectiveEndDate" is set to "today" in database
#    When User click on "Submit" button
#    Then User validate message "Bonus has been unassigned" based on tag name "div"
#    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
#    And User select check box which is at position "2"
#    And User select check box which is at position "3"
#    And User click three dot icon of "1" record in module "Bonuses" based on "Bonuses"
#    And User click on button "Unassign from account"
#    And User validate header of module "Unassign from account"
#    And User select "endDate" as "today+3"
#    And User vaildate "EffectiveEndDate" is set to "today+3" in database
#    When User click on "Submit" button
#    Then User validate message "<#> pricing rule(s) has been unassigned" based on tag name "div"
#    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
#    And User select check box which is at position "1"
#    And User click on 'Unassign from account" icon at header level
#    And User select "endDate" as "today+10"
#    And User vaildate "EffectiveEndDate" is set to "today+10" in database
#    When User click on "Submit" button
#    Then User validate message "All pricing rules has been unassigned" based on tag name "div"
#
#    Examples:
#      | Scenario                                                                                                                                       | UserName        | PassWord        |
#      | TC-01 : OMV-363 : Bonuses-User perform 'Unassign from account' option for Bulk,single and more records after pinning customer for client user  | ClientUserName  | ClientPassWord  |
#      | TC-02 : OMV-363 : Bonuses-User perform 'Unassign from account' option for Bulk,single and more records after pinning customer for country user | CountryUserName | CountryPassword |
#
#  Scenario Outline: Bonuses-User validate 'Unassign from Account' action for single and bulk records before pinning account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User click on the "menu" "Pricing"
#    And  User click on the "submenu" "Bonus"
#    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
#    And User click three dot icon of "1" record in module "Pricing rules" based on "PricingRule"
#    And User click on button "Unassign from account"
#    And User validate header of module "Unassign from account"
#    And User select "endDate" as "today"
#    And User vaildate "EffectiveEndDate" is set to "today" in database
#    When User click on "Submit" button
#    Then User validate message "Bonus has been unassigned" based on tag name "div"
#    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
#    And User select check box which is at position "2"
#    And User select check box which is at position "3"
#    And User click three dot icon of "1" record in module "Bonuses" based on "Bonuses"
#    And User click on button "Unassign from account"
#    And User validate header of module "Unassign from account"
#    And User select "endDate" as "today+3"
#    And User vaildate "EffectiveEndDate" is set to "today+3" in database
#    When User click on "Submit" button
#    Then User validate message "<#> pricing rule(s) has been unassigned" based on tag name "div"
#    Then User search all records with group as "Public" and status "Active" in "Pricing rules" module which is "assignedToAnAccount"
#    And User select check box which is at position "1"
#    And User click on 'Unassign from account" icon at header level
#    And User select "endDate" as "today+10"
#    And User vaildate "EffectiveEndDate" is set to "today+10" in database
#    When User click on "Submit" button
#    Then User validate message "All pricing rules has been unassigned" based on tag name "div"
#
#    Examples:
#      | Scenario                                                                                                                                        | UserName        | PassWord        |
#      | TC-01 : OMV-363 : Bonuses-User perform 'Unassign from account' option for Bulk,single and more records before pinning customer for client user  | ClientUserName  | ClientPassWord  |
#      | TC-02 : OMV-363 : Bonuses-User perform 'Unassign from account' option for Bulk,single and more records before pinning customer for country user | CountryUserName | CountryPassword |