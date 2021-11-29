Feature: UI:CountryUser - Manage auto-reissue scenarios from Cards module

  ########################## Manage Auto Reissue ################################
  @UI-ManageAutoReissue
  Scenario: TC-01 : OMV-911 User validate cards displayed when account has been selected which contain autoReissue profile,autoReissue flag 'Y'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    When User get account number "is" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is "Y" atleast "NoLimit", auto reissue flag "Y"
    And User select or enter field value "autoReissue-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User click on the "menu" "Cards"
    And User click on the "submenu" "Manage auto-reissue"
    And User validate section "countOfRecords" in manage auto reissue module based on account "autoReissue-accountNumber"
    And User validate section "DefaultSection" in manage auto reissue module based on account "autoReissue-accountNumber"
    And User validate section "AllCardProducts" in manage auto reissue module based on account "autoReissue-accountNumber"
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User validate presence of "Change auto-reissue" field with "button" tag
    And User press "tab" key from keyboard
    And User press "escape" key from keyboard

  @UI-ManageAutoReissue
  Scenario: TC 02: OMV-911 User validate "No cards are available for auto-reissue" message when account has been selected which contain autoReissue profile,autoReissue flag 'Y'
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    When User get account number "not" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is "Y" atleast "NoLimit", auto reissue flag "Y"
    And User select or enter field value "autoReissue-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User click on the "menu" "Cards"
    And User click on the "submenu" "Manage auto-reissue"
    And User validate message "No cards are available for auto-reissue" based on tag name "div"

  @UI-ManageAutoReissue
  Scenario: TC03 : OMV-911 Validate "Auto-reissue is not enabled for the selected account" message when selected account does not contain "Auto Reissue profile"
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    When User get account number "is" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is "Y" atleast "NoLimit", auto reissue flag "N"
    And User select or enter field value "autoReissue-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User click on the "menu" "Cards"
    And User click on the "submenu" "Manage auto-reissue"
    And User validate message "Auto-reissue is not enabled for the selected account" based on tag name "div"

  @UI-ManageAutoReissue
  Scenario: TC01 : OMV-916 Validate "Card <**** last 5 digits> has been updated" message when perform "Change auto-reissue" for one card
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    When User get account number "is" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is "Y" atleast "3", auto reissue flag "Y"
    And User select or enter field value "autoReissue-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User click on the "menu" "Cards"
    And User click on the "submenu" "Manage auto-reissue"
    Then User select or validate "checkbox" in manage auto reissue module for "1" records which has EXCLUDE_FROM_BULK_REISSUE flag "Y"
    And User validate presence of "1 card(s) selected" field with "div" tag which contains special characters so verify using pageSource
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User validate presence of "Auto-reissue card ****" with property value "autoReissueRecordCardNo" from "testDataProperties" using tag ""
    Then User validate presence of "No" field with "span" tag
    And User click on "Cancel" based on tag name "a"
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    Then User click on button contains "X-IconFromPopUp" using locator "a>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "click"
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User click on button contains "status dropDown" using locator "mav-select[name='status']>mat-select" which has locator type "cssSelector" using method "click"
    And User click on "Yes" based on tag name "span"
    And User click on button "Save" using span text
    Then User validate success message of "ManageAutoReissue" for no of card "1"

  @UI-ManageAutoReissue
  Scenario: TC02 : OMV-916 Validate "2 card(s) has been updated" message when perform "Change auto-reissue" for two cards
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    When User get account number "is" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is "N" atleast "3", auto reissue flag "Y"
    And User select or enter field value "autoReissue-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User click on the "menu" "Cards"
    And User click on the "submenu" "Manage auto-reissue"
    Then User select or validate "checkbox" in manage auto reissue module for "2" records which has EXCLUDE_FROM_BULK_REISSUE flag "N"
    And User validate presence of "1 card(s) selected" field with "div" tag which contains special characters so verify using pageSource
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User validate presence of "Auto-reissue 2 card(s)" field with "h2" tag
    Then User validate presence of "Yes" field with "span" tag
    And User click on "Cancel" based on tag name "a"
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    Then User click on button contains "X-IconFromPopUp" using locator "a>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "click"
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User click on button contains "status dropDown" using locator "mav-select[name='status']>mat-select" which has locator type "cssSelector" using method "click"
    And User click on "No" based on tag name "span"
    And User click on button "Save" using span text
    Then User validate presence of "2 card(s) has been updated" field with "div" tag

  @UI-ManageAutoReissue
  Scenario: TC03 : OMV-916 Validate "All card(s) has been updated" message when perform "Change auto-reissue" for two cards
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User make sure account is not pinned for logged in user
    Then User select "Select account" option using tag name "div" from context picker if logged in user "CountryUserName" has access to more than one customer
    And User select client and country based on logged in user "CountryUserName"
    When User get account number "is" having autoReissue cards with EXCLUDE_FROM_BULK_REISSUE is "N" atleast "All", auto reissue flag "Y"
    And User select or enter field value "autoReissue-accountNumber" for a field "accountNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "accounts" based on "accountName"
    Then User click on button "Select account"
    Then User click on the "menu" "Cards"
    And User click on the "submenu" "Manage auto-reissue"
    Then User select or validate "checkbox" in manage auto reissue module for "All" records which has EXCLUDE_FROM_BULK_REISSUE flag "N"
    And User validate presence of "All card(s) selected" field with "div" tag which contains special characters so verify using pageSource
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User validate presence of "Auto-reissue all card(s)" field with "h2" tag
    Then User validate presence of "Yes" field with "span" tag
    And User click on "Cancel" based on tag name "a"
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    Then User click on button contains "X-IconFromPopUp" using locator "a>mav-svg-icon>fa-icon" which has locator type "cssSelector" using method "click"
    When User click on button contains "ChangeReissueIconFromTopMenu" using locator "//span[@ng-reflect-message='Change auto-reissue']/mav-svg-icon/fa-icon" which has locator type "xpath" using method "jsExecutor"
    And User click on button contains "status dropDown" using locator "mav-select[name='status']>mat-select" which has locator type "cssSelector" using method "click"
    And User click on "No" based on tag name "span"
    And User click on button "Save" using span text
    Then User validate presence of "All card(s) has been updated" field with "div" tag