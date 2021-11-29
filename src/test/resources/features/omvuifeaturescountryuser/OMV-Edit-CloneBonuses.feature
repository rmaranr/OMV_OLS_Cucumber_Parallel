Scenario Outline: OMV 23,359 : Validate the edit Bonuses at single level with Discount
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
And  User click on the "submenu" "Discounts"
Then User click three dot icon for a record which contain group as status "Active" in "Bonuses" module
And User click three dot icon of "1" record in module "Bonuses" based on "BonusName"
And User click on button "Edit" using span text
And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
And User click on radio button contains "<category>" using locator "(//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1]" which has locator type "xpath"
And User click on button " +Add new" using span text
And User enter "maxValue" as "10" in "bonuses" module having length "2" in "add" form
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Edit" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Edit" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Edit" form
And User click on button " Add" using span text
And User validate "snackbar-text" message "<AddCategoryMessage>" in "bonuses" module
And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
Then User click on "Edit" based on tag name "button"
And User enter "maxValue" as "15" in "bonuses" module having length "2" in "add" form
And User enter value "16" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
And User enter value "22" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
And User enter value "36" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
Then User click on button "Update" using span text
And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "bonuses" module
And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
Then User click drop down "contributionType" then select value "Fixed amount" using tag "mat-select" and attribute "ng-reflect-name"
And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])[4]/input" which has locator type is "xpath" in "Edit" form
And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
And User click on date field "startDate" and select value no of days "0" of "Past" time
And User click on date field "endDate" and select value no of days "2" of "Future" time
And User enter "bonusName" as "RandomAlphanumeric" in "bonuses" module having length "8" in "add" form using input tag
Then User click drop down "rebatePeriod" then select value "Week(s)" using tag "mav-select" and attribute "ng-reflect-name"
And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Edit" form
And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonuses
And User click on button "Submit" using span text
And User validate "snackbar-text" message "Bonuses has been edited" in "bonuses" module
Examples:
| Scenario                                                                                                                 | UserName        | PassWord        | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | fieldNameOfAccountSearch | accountSearchFieldBehavior |
| TC01 : OMV 23,359: Validate the edit Bonuses at client level configuration before after account with discount and fixed  | ClientUserName  | ClientPassWord  | Fixed       | Discount | Discount has been added | Discount has been updated | accountName              | text                       |
| TC02 : OMV 23,359: Validate the edit Bonuses at country level configuration before after account with discount and fixed | CountryUserName | CountryPassWord | Fixed       | Discount | Discount has been added | Discount has been updated | accountName              | text                       |

Scenario Outline: OMV 39,359 : Validate the edit Bonuses at client level with Discount
Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
When User enter "<UserName>" and "<PassWord>"
And User click on login button
When User click on the "menu" "Pricing"
And  User click on the "submenu" "Discounts"
Then User click three dot icon for a record which contain group as status "Active" in "Bonuses" module
And User click three dot icon of "1" record in module "Bonuses" based on "BonusName"
And User click on button "Edit" using span text
And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
And User click on radio button contains "<category>" using locator "(//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1]" which has locator type "xpath"
And User click on button " +Add new" using span text
And User enter "maxValue" as "10" in "bonuses" module having length "2" in "add" form
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Edit" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Edit" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Edit" form
And User click on button " Add" using span text
And User validate "snackbar-text" message "<AddCategoryMessage>" in "bonuses" module
And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
Then User click on "Edit" based on tag name "button"
And User enter "maxValue" as "15" in "bonuses" module having length "2" in "add" form
And User enter value "16" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Edit" form
And User enter value "22" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Edit" form
And User enter value "36" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Edit" form
Then User click on button "Update" using span text
And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "bonuses" module
And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
Then User click drop down "contributionType" then select value "Fixed amount" using tag "mat-select" and attribute "ng-reflect-name"
And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])[4]/input" which has locator type is "xpath" in "Edit" form
And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
And User click on date field "startDate" and select value no of days "0" of "Past" time
And User click on date field "endDate" and select value no of days "2" of "Future" time
And User enter "bonusName" as "RandomAlphanumeric" in "bonuses" module having length "8" in "add" form using input tag
Then User click drop down "rebatePeriod" then select value "Week(s)" using tag "mav-select" and attribute "ng-reflect-name"
And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Edit" form
And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonuses
And User click on button "Submit" using span text
And User validate "snackbar-text" message "Bonuses has been edited" in "bonuses" module
Examples:
| Scenario                                                                                                                   | UserName        | PassWord        | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     |
| TC01 : OMV 39,359: Validate the edit Bonuses at client level configuration before pinning account with discount and fixed  | ClientUserName  | ClientPassWord  | Fixed       | Discount | Discount has been added | Discount has been updated |
| TC02 : OMV 39,359: Validate the edit Bonuses at country level configuration before pinning account with discount and fixed | CountryUserName | CountryPassWord | Fixed       | Discount | Discount has been added | Discount has been updated |


Scenario Outline: OMV 140,360 : Validate the clone Bonuses at client level with Discount
Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
When User enter "<UserName>" and "<PassWord>"
And User click on login button
When User click on the "menu" "Pricing"
And  User click on the "submenu" "Discounts"
And User click three dot icon of "1" record in module "Bonuses" based on "BonusName"
And User click on button "Clone" using span text
And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
And User click on radio button contains "<category>" using locator "(//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1]" which has locator type "xpath"
And User click on button " +Add new" using span text
And User enter "maxValue" as "10" in "bonuses" module having length "2" in "Clone" form
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Clone" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Clone" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Clone" form
And User click on button " Add" using span text
And User validate "snackbar-text" message "<AddCategoryMessage>" in "bonuses" module
And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
Then User click on "clone" based on tag name "button"
And User enter "maxValue" as "15" in "bonuses" module having length "2" in "Clone" form
And User enter value "16" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "clone" form
And User enter value "22" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "clone" form
And User enter value "36" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "clone" form
Then User click on button "Update" using span text
And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "bonuses" module
And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
Then User click drop down "contributionType" then select value "Fixed amount" using tag "mat-select" and attribute "ng-reflect-name"
And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])[4]/input" which has locator type is "xpath" in "Clone" form
And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
And User click on date field "startDate" and select value no of days "0" of "Past" time
And User click on date field "endDate" and select value no of days "2" of "Future" time
And User enter "bonusName" as "RandomAlphanumeric" in "bonuses" module having length "8" in "Clone" form using input tag
Then User click drop down "rebatePeriod" then select value "Week(s)" using tag "mav-select" and attribute "ng-reflect-name"
And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Clone" form
And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonuses
And User click on button "Submit" using span text
And User validate "snackbar-text" message "Bonuses has been created" in "bonuses" module
Examples:
| Scenario                                                                                                                     | UserName        | PassWord        | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     |
| TC01 : OMV 140,360: Validate the clone Bonuses at client level configuration before pinning account with discount and fixed  | ClientUserName  | ClientPassWord  | Fixed       | Discount | Discount has been added | Discount has been updated |
| TC02 : OMV 140,360: Validate the clone Bonuses at country level configuration before pinning account with discount and fixed | CountryUserName | CountryPassWord | Fixed       | Discount | Discount has been added | Discount has been updated |

Scenario Outline: OMV 141,360 : Validate the clone Bonuses at single level with Discount
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
And  User click on the "submenu" "Discounts"
And User click three dot icon of "1" record in module "Bonuses" based on "BonusName"
And User click on button "Clone" using span text
And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
And User click on radio button contains "<category>" using locator "(//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1]" which has locator type "xpath"
And User click on button " +Add new" using span text
And User enter "maxValue" as "10" in "bonuses" module having length "2" in "Clone" form
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Clone" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Clone" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Clone" form
And User click on button " Add" using span text
And User validate "snackbar-text" message "<AddCategoryMessage>" in "bonuses" module
And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
Then User click on "clone" based on tag name "button"
And User enter "maxValue" as "15" in "bonuses" module having length "2" in "Clone" form
And User enter value "16" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "clone" form
And User enter value "22" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "clone" form
And User enter value "36" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "clone" form
Then User click on button "Update" using span text
And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "bonuses" module
And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
Then User click drop down "contributionType" then select value "Fixed amount" using tag "mat-select" and attribute "ng-reflect-name"
And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])[4]/input" which has locator type is "xpath" in "Clone" form
And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
And User click on date field "startDate" and select value no of days "0" of "Past" time
And User click on date field "endDate" and select value no of days "2" of "Future" time
And User enter "bonusName" as "RandomAlphanumeric" in "bonuses" module having length "8" in "Clone" form using input tag
Then User click drop down "rebatePeriod" then select value "Week(s)" using tag "mav-select" and attribute "ng-reflect-name"
And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Clone" form
And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonuses
And User click on button "Submit" using span text
And User validate "snackbar-text" message "Bonuses has been created" in "bonuses" module
Examples:
| Scenario                                                                                                                    | UserName        | PassWord        | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | fieldNameOfAccountSearch | accountSearchFieldBehavior |
| TC01 : OMV 141,360: Validate the clone Bonuses at client level configuration after pinning account with discount and fixed  | ClientUserName  | ClientPassWord  | Fixed       | Discount | Discount has been added | Discount has been updated | accountName              | text                       |
| TC02 : OMV 141,360: Validate the clone Bonuses at country level configuration after pinning account with discount and fixed | CountryUserName | CountryPassWord | Fixed       | Discount | Discount has been added | Discount has been updated | accountName              | text                       |

Scenario Outline: OMV 362 : Validate the Replace Bonuses at single account level with Discount
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
And  User click on the "submenu" "Discounts"
Then User click three dot icon for a record which contain group as "Public" and status "Active" in "Pricing templates" module
And User click three dot icon of "1" record in module "Bonuses" based on "bonusName"
And User click on button "Replace" using span text
And User Validate header text "Replace Pricing Rule"
And User validate warning message "Replacing a pricing rule deletes the selected pricing rule, this action cannot be reveresed"
Then User click on button "Yes" using span text
And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
Then User click drop down "VMethod" then select value "<valueMethod>" using tag "mav-select" and attribute "ng-reflect-name"
And User click on radio button contains "<category>" using locator "(//mat-radio-button[@ng-reflect-value='Surcharge']/label/div)[1]" which has locator type "xpath"
And User click on button " +Add new" using span text
And User enter "maxValue" as "10" in "bonuses" module having length "2" in "Replace" form
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[1]" which has locator type "xpath" then select value "Gold"
And User enter value "12" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]/input" which has locator type is "xpath" in "Replace" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[2]" which has locator type "xpath" then select value "Silver"
And User enter value "15" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]/input" which has locator type is "xpath" in "Replace" form
And User click " +Add new" button using Java Script executor which is present at position "2" using tag name "span"
Then User click drop down "Band" using locator "(//mat-select[@name='dropdown'])[3]" which has locator type "xpath" then select value "Bronze"
And User enter value "8" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]/input" which has locator type is "xpath" in "Replace" form
And User click on button " Add" using span text
And User validate "snackbar-text" message "<AddCategoryMessage>" in "bonuses" module
And User click on button contains "ThreeDot Icon of Discount option" using locator "(//mav-svg-icon[@class='mat-icon']/fa-icon)[1]" which has locator type "xpath"
Then User click on "Replace" based on tag name "button"
And User enter "maxValue" as "15" in "bonuses" module having length "2" in "Replace" form
And User enter value "16" in text field "Bonuses-Band-GoldValue" based on locator "(//mav-input[@name='value'])[1]" which has locator type is "xpath" in "Replace" form
And User enter value "22" in text field "Bonuses-Band-SilverValue" based on locator "(//mav-input[@name='value'])[2]" which has locator type is "xpath" in "Replace" form
And User enter value "36" in text field "Bonuses-Band-BronzeValue" based on locator "(//mav-input[@name='value'])[3]" which has locator type is "xpath" in "Replace" form
Then User click on button "Update" using span text
And User validate "snackbar-text" message "<UpdateCategoryMessage>" in "bonuses" module
And User click on button contains "Merchant Funded" using locator "div[class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']" which has locator type "cssSelector"
Then User click drop down "contributionType" then select value "Fixed amount" using tag "mat-select" and attribute "ng-reflect-name"
And User enter value "25" in text field "Bonuses-contributionType" based on locator "(//mav-input[@name='value'])[4]/input" which has locator type is "xpath" in "Replace" form
And User click "Next" button using Java Script executor which is present at position "2" using tag name "span"
And User click on date field "startDate" and select value no of days "0" of "Past" time
And User click on date field "endDate" and select value no of days "2" of "Future" time
And User enter "bonusName" as "RandomAlphanumeric" in "bonuses" module having length "8" in "Replace" form using input tag
Then User click drop down "rebatePeriod" then select value "Week(s)" using tag "mav-select" and attribute "ng-reflect-name"
And User enter value "National CRT" in text field "Bonuses-Label" based on locator "//ols-autocomplete[@ng-reflect-name='label']/mat-form-field/div/div/div[3]/input" which has locator type is "xpath" in "Replace" form
And User click on button contains "CheckBox" using locator "(//div[@class='mat-checkbox-inner-container'])[1]" which has locator type "xpath"
And User click on button "Review" using span text
#    And User validate all stepper values in Review page at Bonuses
And User click on button "Submit" using span text
And User validate "snackbar-text" message "Bonuses has been created" in "bonuses" module
Examples:
| Scenario                                                                                                                  | UserName        | PassWord        | valueMethod | category | AddCategoryMessage      | UpdateCategoryMessage     | fieldNameOfAccountSearch | accountSearchFieldBehavior |
| TC01 : OMV 362: Validate the Replace Bonuses at client level configuration after pinning account with discount and fixed  | ClientUserName  | ClientPassWord  | Fixed       | Discount | Discount has been added | Discount has been updated | accountName              | text                       |
| TC02 : OMV 362: Validate the Replace Bonuses at country level configuration after pinning account with discount and fixed | CountryUserName | CountryPassWord | Fixed       | Discount | Discount has been added | Discount has been updated | accountName              | text                       |
