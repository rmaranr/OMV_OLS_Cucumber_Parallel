Feature: This feature file contains add Bonuses scenarios

  @Test
  Scenario Outline: OMV 202,203,204,205,455,418 : Validate the creating Bonus at client level with Discount
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Discounts"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "maxValue" as "10" in "Bonuses" module having length "2" in "add" form
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
    And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
    And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
    And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "Bonuses" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "button[class='menu-button-style mat-focus-indicator mat-menu-trigger mat-button mat-button-base']>span>div>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "click"
    Then User click on "Edit" based on tag name "button"
    And User enter "maxValue" as "15" in "Bonuses" module having length "2" in "add" form
    And User enter value "16" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    And User enter value "22" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
    And User enter value "36" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "Bonuses" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "Bonuses" module having length "8" in "add" form using input tag
    Then User click drop down "period" then select value "Year(s)" using tag "mav-select" and attribute "ng-reflect-name"
    And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Apply" using span text
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonus
    And User click on button "Submit" using span text
    And User validate message "Bonus has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Bonus has been created" in "Bonuses" module
    Examples:
      | Scenario                                                                                                                                     | UserName        | PassWord        | valueMethod | category  | AddCategoryMessage       | UpdateCategoryMessage      | categoryLocator                                                  |
      | TC02 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration before pinning account with discount  | CountryUserName | CountryPassword | Fixed       | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
      | TC04 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration before pinning account with Surcharge | CountryUserName | CountryPassword | Fixed       | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |

  Scenario Outline: OMV 202,203,204,205,455,418 : Create Bonus at CSR level using CPL option using discount and Surcharge option
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Discounts"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "minValue" as "<minLiters>" in "Bonuses" module having length "2" in "add" form
    And User enter "maxValue" as "<maxLiters>" in "Bonuses" module having length "2" in "add" form
    And User click on button contains "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "All" using locator "//mat-option[@ng-reflect-value='All']/span[contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User enter value "12" in text field "Bonuses-Band-Value" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "Bonuses" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "button[class='menu-button-style mat-focus-indicator mat-menu-trigger mat-button mat-button-base']>span>div>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "jsExecutor"
    Then User click on "Edit" based on tag name "button"
    And User enter "minValue" as "<minLiters>" in "Bonuses" module having length "2" in "add" form
#    #Enter max liters is less than min liters
    And User enter "maxValue" as "<maxLitersIsLessThanMinLiters>" in "Bonuses" module having length "2" in "add" form
    And User validate message "Enter a valid max liters" based on tag name "mat-error"
    And User enter "maxValue" as "<maxLiters>" in "Bonuses" module having length "2" in "add" form
    And User enter value "16" in text field "Bonuses-Band-Value" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "Bonuses" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "Bonuses" module having length "8" in "add" form using input tag
    Then User click drop down "period" then select value "Year(s)" using tag "mav-select" and attribute "ng-reflect-name"
    And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Apply" using span text
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonus
    And User click on button "Submit" using span text
    And User validate message "Bonus has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Bonus has been created" in "Bonuses" module
    Examples:
      | Scenario                                                                                                                                                                   | UserName        | PassWord        | valueMethod | minLiters | maxLiters | value | maxLitersIsLessThanMinLiters | category  | AddCategoryMessage       | UpdateCategoryMessage      | categoryLocator                                                  |
      | TC06 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration before pinning account with CPL Value Method for Discount Category  | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
      | TC08 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration before pinning account with CPL Value Method for Surcharge Category | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |

  Scenario Outline: OMV 202,203,204,205,455,418 : Validate the creating Bonus at account level with Discount
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Discounts"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "minValue" as "<minLiters>" in "Bonuses" module having length "2" in "add" form
    And User enter "maxValue" as "<maxLiters>" in "Bonuses" module having length "2" in "add" form
    And User click on button contains "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "All" using locator "//mat-option[@ng-reflect-value='All']/span[contains(text(),'All')]" which has locator type "xpath" using method "click"
    And User enter value "12" in text field "Bonuses-Band-Value" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "Bonuses" module
    And User click on button contains "ThreeDot Icon of Discount option" using locator "button[class='menu-button-style mat-focus-indicator mat-menu-trigger mat-button mat-button-base']>span>div>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "jsExecutor"
    Then User click on "Edit" based on tag name "button"
    And User enter "minValue" as "<minLiters>" in "Bonuses" module having length "2" in "add" form
#    #Enter max liters is less than min liters
    And User enter "maxValue" as "<maxLitersIsLessThanMinLiters>" in "Bonuses" module having length "2" in "add" form
    And User validate message "Enter a valid max liters" based on tag name "mat-error"
    And User enter "maxValue" as "<maxLiters>" in "Bonuses" module having length "2" in "add" form
    And User enter value "16" in text field "Bonuses-Band-Value" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
    Then User click on button "Update" using span text
    And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "Bonuses" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "Bonuses" module having length "8" in "add" form using input tag
    Then User click drop down "period" then select value "Year(s)" using tag "mav-select" and attribute "ng-reflect-name"
    And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Apply" using span text
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonus
    And User click on button "Submit" using span text
    And User validate message "Bonus has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Bonus has been created" in "Bonuses" module
    Examples:
      | Scenario                                                                                                                                                                  | UserName        | PassWord        | valueMethod | minLiters | maxLiters | value | maxLitersIsLessThanMinLiters | category  | AddCategoryMessage       | UpdateCategoryMessage      | categoryLocator                                                  |
      | TC10 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration after pinning account with CPL Value Method for Discount Category  | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Discount  | Discount has been added  | Discount has been updated  | (//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]  |
      | TC12 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration after pinning account with CPL Value Method for Surcharge Category | CountryUserName | CountryPassword | CPL         | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |
      | TC14 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration after pinning account with CPL Value Method for Surcharge Category | CountryUserName | CountryPassword | Percentage  | 5         | 10        | 10    | 2                            | Surcharge | Surcharge has been added | Surcharge has been updated | (//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1] |

  Scenario Outline: OMV 202,203,204,205,455,418 : Validate the creating Bonus at account level for Fixed value
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
    And User select or enter value from a field "accountName" based on its behavior "text" for user "<UserName>" in module "accounts"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    When User click on the "menu" "Pricing"
    And  User click on the "submenu" "Discounts"
    And  User click on button "+ New" using span text
#    Then User click drop down "segment" then select value "Segment 1" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
    Then User enter value "OMV" in text field "sites" based on locator "ols-autocomplete[ng-reflect-name='sites']>mat-form-field>div>div>div>input" which has locator type is "cssSelector" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User click drop down "evalRule" then select value "Force" using tag "mav-select" and attribute "ng-reflect-name"
#    Then User click drop down "RDMethod" then select value "Platts" using tag "mav-select" and attribute "ng-reflect-name"
    Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
    And User click on radio button contains "<category>" using locator "<categoryLocator>" which has locator type "xpath"
    And User click on button " +Add new" using span text
    And User enter "maxValue" as "10" in "Bonuses" module having length "2" in "add" form
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
    And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
    And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Add" form
    And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
    Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
    And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button " Add" using span text
    And User validate "snackbar-text" message "<AddCategoryMessage>" in "Bonuses" module
    And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector" using method "click"
    Then User click drop down "contributionType" using locator "mav-select[ng-reflect-name='contributionType']>mat-select" which has locator type "cssSelector" then select value "Fixed amount"
    And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])/input" which has locator type is "xpath" in "Add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User click on date field "startDate" and select value no of days "0" of "Past" time
    And User click on date field "endDate" and select value no of days "2" of "Future" time
    And User enter "pricingName" as "RandomAlphanumeric" in "Bonuses" module having length "8" in "add" form using input tag
    Then User click drop down "period" then select value "Year(s)" using tag "mav-select" and attribute "ng-reflect-name"
    And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Add" form
    And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath" using method "click"
    And User click on button "Apply" using span text
    And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonus
    And User click on button "Submit" using span text
    And User validate message "Bonus has been created" based on tag name "div"
#    And User validate "snackbar-text" message "Bonus has been created" in "Bonuses" module
    Examples:
      | Scenario | UserName | PassWord | valueMethod | category | AddCategoryMessage | UpdateCategoryMessage | categoryLocator |
      | TC16 : OMV 202,203,204,205,455,418: Validate the creating Bonus at country level configuration after pinning account with discount | CountryUserName | CountryPassword | Fixed       | Discount | Discount has been added | Discount has been updated |(//mat-radio-button[@ng-reflect-value='Discount']/label/div)[1]|
#
#  Scenario Outline: OMV 219,220,221,222 : Validate the Delete band of Bonuses
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    When User click on the "menu" "Pricing"
#    And  User click on the "submenu" "Discounts"
#    And  User click on button "+ New" using span text
#    Then User click drop down "cardProductGroup" then select value "MY Fleet" using tag "mav-select" and attribute "ng-reflect-name"
#    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User click drop down "valueMethod" then select value "<valueMethod>" using tag "mat-select" and attribute "ng-reflect-name"
#    And User click on button "+Add new" using span text
#    And User enter "minValue" as "<minValue>" in "bonuses" module having length "2" in "add" form
#    And User enter "maxValue" as "<maxValue>" in "bonuses" module having length "2" in "add" form
#    Then User click drop down "band" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User enter "value" as "<value>" in "bonuses" module having length "2" in "add" form
#    And User click on button "+Add new"
#    Then User click drop down "band" then select value "Random" using tag "mat-select" and attribute "ng-reflect-name"
#    And User enter "value" as "<value>" in "bonuses" module having length "2" in "add" form
#    And User click on button " Add" using span text
#    And User validate "snackbar-text" message "Discount has been added" in "bonuses" module
#    And User click button "threeDotIcon" using tag name "div", attribute name "class", attribute value "header-menu ng-star-inserted"
#    Then User click on "Edit" based on tag name "button"
#    And User enter "minValue" as "<minLiters>" in "bonuses" module having length "2" in "add" form
#    And User enter "maxValue" as "<maxLitersIsLessThanMinLiters>" in "bonuses" module having length "2" in "add" form
#    And User validate the error message "Enter a valid max liters"
#    And User enter "maxValue" as "<maxLiters>" in "bonuses" module having length "2" in "add" form
#    Then User click on button "Update" using span text
#    And User validate "snackbar-text" message "Discount has been updated" in "bonuses" module
#    And  User click on Three dot icon in module "bonuses" based on search keywords "<Delete>"
#    And User click on "Delete" button
#    And  User validated success message of Edit status is "Discount has been deleted"
#    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    And User click on date field "effectiveStartDate" and select value no of days "0" of "Past" time
#    And User click on date field "effectiveEndDate" and select value no of days "2" of "Future" time
#    And User enter ""RandomAlphanumeric"" as ""RandomAlphanumeric"" in "bonuses" module having length "<any>" in "<any>" form using input tag
#    Then User click drop down "rebatePeriod" then select value "Week(s)" using tag "mav-select" and attribute "ng-reflect-name"
#    And User click on button "<Review>"
#    And User validate all stepper values in Review page at Bonuses
#    And User click on button "Submit"
#    And User validate "snackbar-text" message "Bonuses has been added" in "bonuses" module
##    And User validate newly added "bonuses" with the database
#    Examples:
#      | Scenario                                                                                                             | UserName        | PassWord        | valueMethod | minLiters | maxLiters | value | maxLitersIsLessThanMinLiters |
#      | TC05 : OMV 219,220,221,222: Validate the band deletion at client level configuration before pinning account         | ClientUserName  | ClientPassWord  | CPL         | 5         | 10        | 10    | 2                            |
#      | TC06 : OMV 219,220,221,222: Validate the band deletion rules at country  level configuration before pinning account | CountryUserName | CountryPassWord | CPL         | 6         | 8         | 6     | 5                            |
