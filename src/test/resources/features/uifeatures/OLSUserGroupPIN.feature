Feature: OLS Users- Group Pin Test Cases
      ### Group PIN Test cases

  Scenario: TC-01 : OLSUser : OMV-898 :User create a group pin and validate success message
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account number which is in "Active" status for "OLS" user
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    And User validate presence of "Group PIN" field with "div" tag
    Then User click on "+ New" based on tag name "span"
    And User validate presence of "Add new Group PIN" field with "h2" tag
    When User enter "pinName" as "RandomAlphanumeric" in "groupPin" module having length "10" in "add" form
    And User enter values for "groupPin" with "valid" values
    And User click on button contains "EyeIconOfPIN" using locator "(//mav-svg-icon[@ng-reflect-value='fas eye-slash'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "EyeIconOfConfirm PIN" using locator "(//mav-svg-icon[@ng-reflect-value='fas eye-slash'])[1]" which has locator type "xpath" using method "click"
    Then User click on "Save" based on tag name "span"
    And User validate presence of "New group PIN has been added" field with "div" tag
    Then User validate newly created group pin is saved in the database or not

  Scenario:TC-02: OLSUser: OMV-898 : User validate error message "PIN doesn't match" in 'Add new Group Pin' module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account number which is in "Active" status for "OLS" user
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    And User validate presence of "Group PIN" field with "div" tag
    Then User click on "+ New" based on tag name "span"
    And User validate presence of "Add new Group PIN" field with "h2" tag
    When User enter "pinName" as "RandomAlphanumeric" in "groupPin" module having length "10" in "add" form
    And User enter values for "groupPin" with "invalid" values
    Then User validate presence of "PIN doesn't match" field with "mat-error" tag
    And User click on "Cancel" based on tag name "a"
    And User validate presence of "Group PIN" field with "div" tag

  Scenario: TC-03: OLSUser: OMV-898: User verify '+ New' button is disabled when an inactive account is selected
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account number which is in "inActive" status for "OLS" user
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    And User validate presence of "Group PIN" field with "div" tag
    Then User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"

  Scenario: TC-04: OLSUser :OMV-900 User change the pin for an existing pin in Group PIN module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account which "ishaving" "groupPinRecords" for user type "OLS"
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    Then User enter value "groupPinName" using locator "mav-input[id='search-top-input']>input" with locator type "cssSelector" is from property file "Yes"
    And User press "enter" key from keyboard
    When User click on button contains "Three dot icon" using locator "(//button/div/mav-svg-icon/fa-icon)[1]" which has locator type "xpath" using method "click"
    Then User click on "Change PIN" based on tag name "button"
    And User validate presence of "Change PIN" field with "h2" tag
    And User enter values for "groupPin" with "valid" values
    And User click on button contains "EyeIconOfPIN" using locator "(//mav-svg-icon[@ng-reflect-value='fas eye-slash'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "EyeIconOfConfirm PIN" using locator "(//mav-svg-icon[@ng-reflect-value='fas eye-slash'])[1]" which has locator type "xpath" using method "click"
    Then User click on "Save" based on tag name "span"
    And User validate presence of "Group PIN has been updated" field with "div" tag

  Scenario:TC-05: OLSUser: OMV-900 : User validate error message "PIN doesn't match" in 'Change group PIN' action
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account which "ishaving" "groupPinRecords" for user type "OLS"
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    Then User enter value "groupPinName" using locator "mav-input[id='search-top-input']>input" with locator type "cssSelector" is from property file "Yes"
    And User press "enter" key from keyboard
    When User click on button contains "Three dot icon" using locator "(//button/div/mav-svg-icon/fa-icon)[1]" which has locator type "xpath" using method "click"
    Then User click on "Change PIN" based on tag name "button"
    And User validate presence of "Change PIN" field with "h2" tag
    And User enter values for "groupPin" with "invalid" values
    Then User validate presence of "PIN doesn't match" field with "mat-error" tag
    And User click on "Cancel" based on tag name "a"
    And User validate presence of "Group PIN" field with "div" tag

  Scenario: TC-06 OLSUser: OMV-902: User change the pin name in 'change Name' action
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account which "ishaving" "groupPinRecords" for user type "OLS"
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    Then User enter value "groupPinName" using locator "mav-input[id='search-top-input']>input" with locator type "cssSelector" is from property file "Yes"
    And User press "enter" key from keyboard
    Then User click on search icon
    When User click on button contains "Three dot icon" using locator "(//button/div/mav-svg-icon/fa-icon)[1]" which has locator type "xpath" using method "click"
    Then User click on "Change Name" based on tag name "button"
    And User validate presence of "Change Group Name" field with "h2" tag
    Then User validate expected value "groupPinName" from property file "Yes" actual value from locator "mav-input[ng-reflect-name='pinName']>input" which has locator type "cssSelector" get value using attribute "ng-reflect-model"
    When User enter "pinName" as "RandomAlphanumeric" in "groupPin" module having length "10" in "add" form
    Then User click on "Save" based on tag name "span"
    And User validate presence of "Group PIN name has been updated" field with "div" tag

  Scenario: TC-07 OLS User: OMV-902 User validate 'Group PIN name must be unique', 'Enter a different name' error messages in change pin name flow
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account which "ishaving" "groupPinRecords" for user type "OLS"
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    Then User enter value "groupPinName" using locator "mav-input[id='search-top-input']>input" with locator type "cssSelector" is from property file "Yes"
    And User press "enter" key from keyboard
    Then User click on search icon
    When User click on button contains "Three dot icon" using locator "(//button/div/mav-svg-icon/fa-icon)[1]" which has locator type "xpath" using method "click"
    Then User click on "Change Name" based on tag name "button"
    And User validate presence of "Change Group Name" field with "h2" tag
    Then User validate expected value "groupPinName" from property file "Yes" actual value from locator "mav-input[ng-reflect-name='pinName']>input" which has locator type "cssSelector" get value using attribute "ng-reflect-model"
    When User enter value "groupPinName1" in text field "NameOfGroupPin" based on locator "mav-input[ng-reflect-name='pinName']>input" which has locator type is "cssSelector" in "add" form
    Then User click on "Save" based on tag name "span"
    And User validate presence of "Group PIN name must be unique" field with "div" tag
    And User validate presence of "Enter a different name" field with "mat-error" tag

  Scenario: TC-08 OLS User: OMV-902 User validate 'Group PIN name must be unique', 'Enter a different name' error messages in Create Group Pin flow
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account which "ishaving" "groupPinRecords" for user type "OLS"
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    And User validate presence of "Group PIN" field with "div" tag
    Then User click on "+ New" based on tag name "span"
    And User validate presence of "Add new Group PIN" field with "h2" tag
    When User enter value "groupPinName1" in text field "NameOfGroupPin" based on locator "mav-input[ng-reflect-name='pinName']>input" which has locator type is "cssSelector" in "add" form
    And User enter values for "groupPin" with "valid" values
    And User click on button contains "EyeIconOfPIN" using locator "(//mav-svg-icon[@ng-reflect-value='fas eye-slash'])[1]" which has locator type "xpath" using method "click"
    And User click on button contains "EyeIconOfConfirm PIN" using locator "(//mav-svg-icon[@ng-reflect-value='fas eye-slash'])[1]" which has locator type "xpath" using method "click"
    Then User click on "Save" based on tag name "span"
    And User validate presence of "Group PIN name must be unique" field with "div" tag
    And User validate presence of "Enter a different name" field with "mat-error" tag

  Scenario: OLS-902 Validate "No results found,Please update your search keywords." message when user enter invalid search text
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account which "ishaving" "groupPinRecords" for user type "OLS"
    When User select an account from account picker based on value "accountNo" is from property file "Yes"
    And User click on the "menu" "Cards"
    And User click on the "submenu" "Group pin"
    When User enter below three characters "ab" in search text box the search icon will not be enabled
    When User enter value "kljakhsldjfhkljhjkagksjdhkljhksjdhfklgjkhlasjdhflkj" in text field "NameOfGroupPin" based on locator "div[class='mat-form-field-infix']>span>mav-input>input" which has locator type is "cssSelector" in "add" form
    Then User click on search icon
    And User validate message of search results section "No results found,Please update your search keywords."