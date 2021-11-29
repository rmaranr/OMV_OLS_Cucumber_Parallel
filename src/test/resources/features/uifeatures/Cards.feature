Feature: OLS User : Cards Scenarios

  @UI-Cards
  Scenario Outline: OMV-780,781,782,783 : OLS User : Validate Default and Expanded section field values in Cards module with out pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    When User get an account which is having "cards" records based on user type "OLS"
    And User click on button "Home" using span text
    Then User get latest cardNo based on accountNo "accountNumberInCardsModule"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    And User select or enter value from a field "<fieldNameOfCardsearch>" based on its behavior "<CardsearchFieldBehavior>" for user "<UserName>" in module "Cards"
    And User click on " Search " button
#    Then User validate based on the search criteria the record count displayed in module "Cards" or not for field "<fieldNameOfCardsearch>" based on field value
    And User validate "Default" omv section field values "Cardnumber,CustomerNumber,Cardproduct,Costcentre,Drivername,Licenseplate,Expirydate,status" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "Card Details" omv section field values "cardOffer,cardProduct,cardType,expiryDate,identificationMethod,onlinePurchasesPassword" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "Additional Details" omv section field values "costCentre,additionalCode,vehicleDescription,licensePlate" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "Embossing" omv section field values "companyName" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "CardCControlProfile" omv section field values "cardControlProfile" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "POSPrompts" omv section field values "posPrompts" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
    And User validate "AllowedProducts" omv section field values "allowedFuelProducts,allowedNonFuelProducts,valueAddedServices" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
#    And User validate "PurchaseControls" omv section field values "monthlyVolumeLimit,monthlyRingLimit,dailyTransactionLimit,dailyRinggitLimit,dailyVolumeLimit" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
#    And User validate "Subscriptions" omv section field values "subscription" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"
#    And User validate "DeliveryAddress" omv section field values "cardAddress,pinAddress,mailIndicator" with database based on searched field "<fieldNameOfCardsearch>" based on user "<UserName>"

    Examples:
      | Scenario                                                                                                                                                                                          | UserName       | fieldNameOfCardsearch | CardsearchFieldBehavior |
      | TC-08 : OMV-780,781,782,772,784 : ClientUser : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using Customernumber | ClientUserName | customerNumber        | text                    |
      | TC-09 : OMV-780,781,782 : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using IssuingCountry                      | ClientUserName | issuingCountry        | text                    |
      | TC-10 : OMV-780,781,782 : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using Drivername                          | ClientUserName | driverName            | text                    |
      | TC-11 : OMV-780,781,782 : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using Licenseplate                        | ClientUserName | licensePlate          | text                    |
      | TC-12 : OMV-780,781,782 : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using Costcentr                           | ClientUserName | costCentreCode        | text                    |
      | TC-13 : OMV-780,781,782 : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using status                              | ClientUserName | onlineStatus          | text                    |
      | TC-14 : OMV-780,781,782 : Validate Cards Default and Expanded section field values with out pinning an account by logging in using csrClient and search using Cardnumber                          | ClientUserName | cardNumber            | text                    |

  @UI-Cards
  Scenario: OMV-885 ClientUser : User validate search criteria should be retained in cards module
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    When User get an account which is having "cards" records based on user type "OLS"
    And User select client and country based on logged in user "ClientUserName"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    Then User click on "Search" based on tag name "span"
    And User save the value for field "countOfRecords" from locator "div[class='view-page-list']" using locator type "cssSelector" in property file
    Then User click on "+ New" based on tag name "span"
    Then User click on "Cancel" based on tag name "a"
    And User validate presence of "countOfRecords" field with "div" tag from property file using property name "countOfRecords"

  @UI-Cards
  Scenario: OMV-864 ClientUser : Validate New button status before pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    Then User get an account number which is in "InActive" status for "OLS" user
    And User select account based on the account number from property file "accountNo"
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    Then User validate button "+ New" status "disabled" using tag name "mav-button", attribute name "ng-reflect-klass", attribute value "add-button" and get status of button using attribute "ng-reflect-disabled"

  @UI-Cards
  Scenario Outline: TC-01 : OMV-864,872,873,1091,1188 Customer User : Order a card
    When User clear property file "TestDataProperties"
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User get an account number which is eligible to order or edit a card based on "<cardReissueTypeCID>" and "<identificationMethod>" for form "add" as user "OLS"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User click on the "Add Cards button" " + New "
    Then User get field status based on validation controls in cards module
    And User verify the drop down "cardOffer" value is pre-selected if the drop down has one or more than one value and validate its status for "add" form
    And User verify the drop down "cardProduct" value is pre-selected if the drop down has one or more than one value and validate its status for "add" form
    And User verify the drop down "cardType" value is pre-selected if the drop down has one or more than one value and validate its status for "add" form
    Then User validate Expiry Date is populated as clientProcessingDate plus ExpiryMonths of card product and user can select until MaxCardExpiryDate reaches for "add" form
#    And User select "identification method" as radio button "<identificationMethod>" for "add" form when logged in user "<CSR>"
    And User handle field "webAuthCode" in cards module based on validation control in "add" form
    And User handle field "costCentreCode" in cards module based on validation control in "add" form
    And User enter "externalRef" as "RandomAlphanumeric" in "add" module having length "5" in "add" form
    And User handle field "driverName" in cards module based on validation control in "add" form
    And User handle field "driverId" in cards module based on validation control in "add" form
    And User handle field "vehicleDescription" in cards module based on validation control in "add" form
    And User handle field "licensePlate" in cards module based on validation control in "add" form
    When User validate EmbossingName is populated based on the account selected from account picker for "add" form
    And User handle section "PosPrompts" in cards module based on validation control in "add" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "productSelection" in cards module based on validation control in "add" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "locationRestriction" in cards module in "add" form for cardControl "<cardControlRadiobuttonName>" based on scenario "<locationRestrictionScenario>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "timeLimits" in cards module in "add" form for cardControl "<cardControlRadiobuttonName>" based on scenario "<timeLimits>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "velocityLimits" in cards module based on validation control in "add" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User select all subscriptions from the list if more than one subscription displayed based on customer and client for "add" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User validate delivery address stepper based on isAlternateAddressRequired "<isAlternateAddressRequired>" for form "add"
    Then User validate mail indicator value in "add" page
    And User click on "Review" button
#    Then User validate all stepper values in Review page in form "add"
    And User click on "Submit" based on tag name "span"
    Then User validate success message of "ordercard" for no of card "<noOfCard>"
    And User change the newly created card "<noOfCard>" status to Active

    Examples:
      | Scenario                                                                                                                                           | UserName       | identificationMethod | cardControlRadiobuttonName | actionOfCardControlPopUp | noOfCard              | isAlternateAddressRequired | locationRestrictionScenario | cardReissueTypeCID | timeLimits     |
      | TC01 : OMV-864,872,873,1188,1091 Client User Order a card which should be able to reissue with different card no with private card control profile | ClientUserName | system               | existing                   | private                  | reIssueWithDiffCardNo | Yes                        | onlyLocationCategory        | 5903               | Monday,Tuesday |
#      | TC02 : OMV-864,872,873,1188,1091 Client User Order a card which should be able to reissue with same card no with existing card control profile                  | ClientUserName | group                | existing                   | private                  | reIssueWithSameCardNo | Yes                        | bothValues                  | 5902               | Wednesday,Thursday      |
#      | TC03 : OMV-864,872,873,1188,1091 Client User Order a card with "Existing default profile"                                                                       | ClientUserName | system               | existing                   | private                  | 1                     | Yes                        | onlyLocationCategory        | 5901               | Friday,Saturday,Sunday  |
#      | TC04 : OMV-864,872,873,1188,1091 Client User Order a card with "Existing private profile"                                                                       | ClientUserName | group                | private                    | private                  | 2                     | Yes                        | noLocationRestriction       | 5901               | Monday,Tuesday,Friday   |
#      | TC05 : OMV-864,872,873,1188,1091 Client User Order a card with identification method "Self select pin" and "Existing profile" for new card control profile      | ClientUserName | system               | newPOSPrompts              | private                  | 3                     | Yes                        | bothValues                  | 5902               | Tuesday,Saturday        |
#      | TC06 : OMV-864,872,873,1188,1091 Client User Order a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | group                | newProductSelection        | private                  | 4                     | Yes                        | onlyLocationCategory        | 5901               | Wednesday,Sunday        |
#      | TC07 : OMV-864,872,873,1188,1091 Client User Order a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | system               | newVelocityLimitSelection  | private                  | 5                     | Yes                        | noLocationRestriction       | 5901               | Monday,Thursday,Sunday  |
#      | TC08 : OMV-864,872,873,1188,1091 Client User Order a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | group                | newLocationRestriction     | private                  | 6                     | Yes                        | bothValues                  | 5902               | Tuesday,Friday,Saturday |
#      | TC09 : OMV-864,872,873,1188,1091 Client User Order a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | system               | newTimeLimits              | private                  | 6                     | Yes                        | bothValues                  | 5903               | Friday,Sunday           |

  @UI-Cards
  Scenario Outline: OMV-888 Client User : Clone a selected card
    When User clear property file "TestDataProperties"
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User get an account number which is eligible to order or edit a card based on "<cardReissueTypeCID>" and "<identificationMethod>" for form "clone" as user "OLS"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on " Search " button
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Clone" based on tag name "button"
    Then User get field status based on validation controls in cards module
    And User verify the drop down "cardOffer,cardProduct,cardType" value is pre-selected if the drop down has one or more than one value and validate its status for "clone" form
    Then User validate Expiry Date is populated as clientProcessingDate plus ExpiryMonths of card product and user can select until MaxCardExpiryDate reaches for "edit" form
#    And User select "identification method" as radio button "<identificationRadioButton>" for "clone" form when logged in user "<CSR>"
#    And User select value "" in cost centre field from the list for "edit" form
    Then User validate expected value "clone-externalRef" from property file "Yes" actual value from locator "mav-input[ng-reflect-name='externalRef']>input" which has locator type "cssSelector" get value using attribute "ng-reflect-model"
    And User enter "externalRef" as "RandomAlphanumeric" in "clone" module having length "5" in "add" form
    And User handle field "driverName" in cards module based on validation control in "clone" form
    And User handle field "driverId" in cards module based on validation control in "clone" form
    And User handle field "vehicleDescription" in cards module based on validation control in "clone" form
    And User handle field "licensePlate" in cards module based on validation control in "clone" form
    When User validate EmbossingName is populated based on the account selected from account picker for "clone" form
    And User handle section "PosPrompts" in cards module based on validation control in "clone" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "productSelection" in cards module based on validation control in "clone" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "locationRestriction" in cards module in "clone" form for cardControl "<cardControlRadiobuttonName>" based on scenario "<locationRestrictionScenario>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "timeLimits" in cards module in "clone" form for cardControl "<cardControlRadiobuttonName>" based on scenario "<timeLimits>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "velocityLimits" in cards module based on validation control in "clone" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User select all subscriptions from the list if more than one subscription displayed based on customer and client for "clone" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User validate delivery address stepper based on isAlternateAddressRequired "<isAlternateAddressRequired>" for form "clone"
    Then User validate mail indicator value in "clone" page
    And User click on "Review" button
#    Then User validate all stepper values in Review page in form "clone"
    And User click on "Submit" button
    Then User validate success message of "cloneCard" for no of card "<noOfCard>"
    And User change the newly created card "<noOfCard>" status to Active

    Examples:
      | Scenario                                                                                                                                 | UserName       | identificationMethod | cardControlRadiobuttonName | actionOfCardControlPopUp | noOfCard              | isAlternateAddressRequired | locationRestrictionScenario | cardReissueTypeCID | timeLimits         |
      | TC01 : OMV-888 Client User Clone a card with identification method "Self select pin" and "Existing profile" for new card control profile | ClientUserName | opposite             | any                        | newPrivate               | reIssueWithDiffCardNo | Yes                        | bothValues                  | 5901               | Wednesday,Thursday |
#      | TC02 : OMV-888 Client User Clone a card which should be able to reissue with different card no with private card control profile              | ClientUserName | opposite             | existing                   | private                  | reIssueWithDiffCardNo | Yes                        | onlyLocationCategory        | 5903               | Monday,Tuesday          |
#      | TC03 : OMV-888 Client User Clone a card which should be able to reissue with same card no with existing card control profile                  | ClientUserName | opposite             | existing                   | newPrivate               | reIssueWithSameCardNo | Yes                        | bothValues                  | 5902               | Wednesday,Thursday      |
#      | TC04 : OMV-888 Client User Clone a card with "Existing default profile"                                                                       | ClientUserName | opposite             | existing                   | private                  | 1                     | Yes                        | onlyLocationCategory        | 5901               | Friday,Saturday,Sunday  |
#      | TC05 : OMV-888 Client User Clone a card with "Existing private profile"                                                                       | ClientUserName | opposite             | private                    | private                  | 2                     | Yes                        | noLocationRestriction       | 5901               | Monday,Tuesday,Friday   |
#      | TC06 : OMV-888 Client User Clone a card with identification method "Self select pin" and "Existing profile" for new card control profile      | ClientUserName | noChange             | newPOSPrompts              | private                  | 3                     | Yes                        | bothValues                  | 5902               | Tuesday,Saturday        |
#      | TC07 : OMV-888 Client User Clone a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | noChange             | newProductSelection        | private                  | 4                     | Yes                        | onlyLocationCategory        | 5901               | Wednesday,Sunday        |
#      | TC08 : OMV-888 Client User Clone a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | noChange             | newVelocityLimitSelection  | private                  | 5                     | Yes                        | noLocationRestriction       | 5901               | Monday,Thursday,Sunday  |
#      | TC09 : OMV-888 Client User Clone a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | opposite             | newLocationRestriction     | private                  | 6                     | Yes                        | bothValues                  | 5902               | Tuesday,Friday,Saturday |
#      | TC10 : OMV-888 Client User Clone a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | noChange             | newTimeLimits              | private                  | 6                     | Yes                        | bothValues                  | 5903               | Friday,Sunday           |

  @UI-Cards
  Scenario Outline: OMV-886 Client User : Edit a selected card
    When User clear property file "TestDataProperties"
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    Then User get an account number which is eligible to order or edit a card based on "<cardReissueTypeCID>" and "<identificationMethod>" for form "edit" as user "OLS"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on " Search " button
    Then User click on button contains "threeDotIcon" using locator "(//div/button/div/mav-svg-icon)[1]" which has locator type "xpath" using method "click"
    And User click on "Edit card details" based on tag name "button"
    Then User get field status based on validation controls in cards module
    And User verify the drop down "cardOffer,cardProduct,cardType" value is pre-selected if the drop down has one or more than one value and validate its status for "edit" form
    Then User validate Expiry Date is populated as clientProcessingDate plus ExpiryMonths of card product and user can select until MaxCardExpiryDate reaches for "edit" form
#    And User select "identification method" as radio button "<identificationRadioButton>" for "edit" form when logged in user "<CSR>"
#    And User select value "" in cost centre field from the list for "edit" form
    Then User validate expected value "edit-externalRef" from property file "Yes" actual value from locator "mav-input[ng-reflect-name='externalRef']>input" which has locator type "cssSelector" get value using attribute "ng-reflect-model"
    And User enter "externalRef" as "RandomAlphanumeric" in "edit" module having length "5" in "add" form
    And User handle field "driverName" in cards module based on validation control in "edit" form
    And User handle field "driverId" in cards module based on validation control in "edit" form
    And User handle field "vehicleDescription" in cards module based on validation control in "edit" form
    And User handle field "licensePlate" in cards module based on validation control in "edit" form
    When User validate EmbossingName is populated based on the account selected from account picker for "edit" form
    And User handle section "PosPrompts" in cards module based on validation control in "edit" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    And User handle section "productSelection" in cards module based on validation control in "edit" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "locationRestriction" in cards module in "edit" form for cardControl "<cardControlRadiobuttonName>" based on scenario "<locationRestrictionScenario>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "timeLimits" in cards module in "edit" form for cardControl "<cardControlRadiobuttonName>" based on scenario "<timeLimits>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    And User handle section "velocityLimits" in cards module based on validation control in "edit" form for cardControl "<cardControlRadiobuttonName>" with profile "<actionOfCardControlPopUp>"
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
#    Then User select all subscriptions from the list if more than one subscription displayed based on customer and client for "edit" form
    And User click "Next" button using Java Script executor which is present at position "1" using tag name "span"
    Then User validate and click Continue if reissue is required for form "edit"
#    Then User validate all stepper values in Review page in form "edit"
    And User click on "Submit" button
    Then User validate success message of "editCard" for no of card "<noOfCard>"

    Examples:
      | Scenario                                                                                                                                | UserName       | identificationMethod | cardControlRadiobuttonName | actionOfCardControlPopUp | noOfCard              | locationRestrictionScenario | cardReissueTypeCID | timeLimits     |
      | TC01 : OMV-886 Client User Edit a card with identification method "Self select pin" and "Existing profile" for new card control profile | ClientUserName | group                | existing                   | existing                 | reIssueWithDiffCardNo | onlyLocationCategory        | 5903               | Monday,Tuesday |
#      | TC02 : OMV-888 Client User Edit a card which should be able to reissue with different card no with private card control profile              | ClientUserName | opposite             | existing                   | private                  | reIssueWithDiffCardNo | onlyLocationCategory        | 5903               | Monday,Tuesday          |
#      | TC03 : OMV-888 Client User Edit a card which should be able to reissue with same card no with existing card control profile                  | ClientUserName | opposite             | existing                   | newPrivate               | reIssueWithSameCardNo | bothValues                  | 5902               | Wednesday,Thursday      |
#      | TC04 : OMV-888 Client User Edit a card with "Existing default profile"                                                                       | ClientUserName | opposite             | existing                   | private                  | 1                     | onlyLocationCategory        | 5901               | Friday,Saturday,Sunday  |
#      | TC05 : OMV-888 Client User Edit a card with "Existing private profile"                                                                       | ClientUserName | opposite             | private                    | private                  | 2                     | noLocationRestriction       | 5901               | Monday,Tuesday,Friday   |
#      | TC06 : OMV-888 Client User Edit a card with identification method "Self select pin" and "Existing profile" for new card control profile      | ClientUserName | noChange             | newPOSPrompts              | private                  | 3                     | bothValues                  | 5902               | Tuesday,Saturday        |
#      | TC07 : OMV-888 Client User Edit a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | noChange             | newProductSelection        | private                  | 4                     | onlyLocationCategory        | 5901               | Wednesday,Sunday        |
#      | TC08 : OMV-888 Client User Edit a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | noChange             | newVelocityLimitSelection  | private                  | 5                     | noLocationRestriction       | 5901               | Monday,Thursday,Sunday  |
#      | TC09 : OMV-888 Client User Edit a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | opposite             | newLocationRestriction     | private                  | 6                     | bothValues                  | 5902               | Tuesday,Friday,Saturday |
#      | TC10 : OMV-888 Client User Edit a card with identification method "System generated pin" and "Existing profile" for new card control profile | ClientUserName | noChange             | newTimeLimits              | private                  | 6                     | bothValues                  | 5903               | Friday,Sunday           |

###Validate Export cards for CSR and Single account level
#  Scenario: TC-01:OMV-862 Validate Export sheet data of Cards module with out pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    And User click on button "Home" using span text
#    And User make sure account is not pinned for logged in user
#    When User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User click on "Search" based on tag name "button"
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "cards" based on primary key "CardNo"
#
#  Scenario: TC-02:OMV-862 Validate Export sheet data of Cards module after pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
#    And User click on button "Home" using span text
#    And User make sure account is not pinned for logged in user
#    Then User select "Select account" option using tag name "div" from context picker if logged in user "<UserName>" has access to more than one customer
#    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#    When User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User click on "Search" based on tag name "button"
#    And User click on download button validate format of excel file is ".csv"
#    And User open the excel sheet and validate the data present in it with the database for module "cards" based on primary key "CardNo"

  @UI-Cards
  Scenario: TC-10 : ClientUser : Validate "No results found,Please update your search keywords." after searching using invalid field value
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    When User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User enter value "782900017564738475937" using tag name "mav-input", attribute name "ng-reflect-name", attribute value "cardNumber" with remaining locator address ">input"
    And User click on " Search " button
    And User validate message of search results section "No results found,Please update your search keywords."

  @UI-Cards
  Scenario Outline: OMV-882 : ClientUser : Validate User is able to submit Replace card request after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
    Then User get card number for which action applicable "Yes" and action type "ReplaceCard"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
    And User click on button "Replace card"
    And User validate action page or popup header is "Replace card ****" with last 5 digits of card for action "Replace card"
    And User skip this scenario if the following text "failure response" present using tag name "div"
    Then User click on button contains "replacementReason" using locator "mav-select[name='replacementReason']" which has locator type "cssSelector" using method "click"
    And User validate "ReplaceCard" drop down values are coming based on database or not and select one value based on cardReissueActionCid "<cardReissueActionCid>"
    Then User validate Expiry Date in action "replaceCard" as order card date and change it to "future" days "10"
    And User handle address component in "ReplaceCard" section in "ReplaceCard" module with "another" address
    Then User click on "Submit" button
    And User validate presence of "Card replacement request has been submitted" field with "div" tag
    And User validate specific field "REPLACE_CARD_OID" value "" in cards table from database

    Examples:
      | cardReissueActionCid | Scenario                                                                                                              |
      | 5902                 | OMV-882 Validate User is able to submit Replace card request after pinning an account for CardReissueActionCId - 5903 |

  @UI-Cards
  Scenario Outline: OMV-882 : ClientUser : Validate User is able to submit Replace card request after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario "<Scenario>"
    And User click on button "Home" using span text
    And User skip this scenario if card is not available to perform replaceCard scenario
    And User set property "replaceCardRequest" value as "futureRequest" in "TestDataProperties"
#    Then User get card number for which action applicable "Yes" and action type "ReplaceCard"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
    And User click on button "Replace card"
    And User validate action page or popup header is "Replace card ****" with last 5 digits of card for action "Replace card"
    And User skip this scenario if the following text "failure response" present using tag name "div"
    Then User click on button contains "replacementReason" using locator "mav-select[name='replacementReason']" which has locator type "cssSelector" using method "click"
    And User validate "ReplaceCard" drop down values are coming based on database or not and select one value based on cardReissueActionCid "<cardReissueActionCid>"
    Then User validate Expiry Date in action "replaceCard" as order card date and change it to "future" days "10"
    And User handle address component in "ReplaceCard" section in "ReplaceCard" module with "another" address
    Then User click on "Submit" button
    And User validate presence of "Card replacement request has been submitted" field with "div" tag
    And User validate specific field "REPLACE_CARD_OID" value "" in cards table from database
    Then User validate address stored in database for section "ReplaceCard" based on its action "replaceCardRequest" from propertyFile "Yes"

    Examples:
      | cardReissueActionCid | Scenario                                                                                                              |
      | 5903                 | OMV-882 Validate User is able to submit Replace card request after pinning an account for CardReissueActionCId - 5903 |

  @UI-Cards
  Scenario: OMV-882 : ClientUser : Validate Error messages in Replace card page after submitting the request after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User skip this scenario if card is not available to perform replaceCard scenario
    And User set property "replaceCardRequest" value as "futureRequest" in "TestDataProperties"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
    And User click on button "Replace card"
    And User skip this scenario if the following text "failure response" present using tag name "div"
    Then User click on button contains "replacementReason" using locator "mav-select[name='replacementReason']" which has locator type "cssSelector" using method "click"
    And User validate "ReplaceCard" drop down values are coming based on database or not and select one value based on cardReissueActionCid "5903"
    And User handle address component in "ReplaceCard" section in "ReplaceCard" module with "another" address
    Then User validate Expiry Date in action "replaceCard" as order card date and change it to "future" days "11"
    Then User click on "Submit" button
    And User validate presence of "Select an earlier date" field with "span" tag
    And User validate presence of "A request already exists for an earlier date" field with "div" tag
    Then User validate Expiry Date in action "replaceCard" as order card date and change it to "past" days "1"
    Then User click on "Submit" button
    And User validate presence of "Select an earlier date" field with "span" tag
    And User validate presence of "A request already exists for the selected date" field with "div" tag
    Then User validate Expiry Date in action "replaceCard" as order card date and change it to "past" days "6"
    Then User click on "Submit" button
    And User validate presence of "Card replacement request has been submitted" field with "div" tag
    And User validate specific field "REPLACE_CARD_OID" value "" in cards table from database
    Then User validate address stored in database for section "ReplaceCard" based on its action "replaceCardRequest" from propertyFile "Yes"

  @UI-Cards
  Scenario: OMV-882 : ClientUser : Validate Primary address and enter alternate address in Replace card functionality after pinning an account
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    And User skip this scenario if card is not available to perform replaceCard scenario
    And User set property "replaceCardRequest" value as "presentRequest" in "TestDataProperties"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    Then User get card number for which action applicable "Yes" and action type "ReplaceCard"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
    And User click on button "Replace card"
    And User skip this scenario if the following text "failure response" present using tag name "div"
    Then User click on button contains "replacementReason" using locator "mav-select[name='replacementReason']" which has locator type "cssSelector" using method "click"
    And User validate "ReplaceCard" drop down values are coming based on database or not and select one value based on cardReissueActionCid "5903"
    Then User validate Expiry Date in action "replaceCard" as order card date and change it to "future" days "0"
    And User handle address component in "ReplaceCard" section in "ReplaceCard" module with "another" address
    Then User click on "Submit" button
    Then User validate success message of "replaceCard" for no of card ""
    And User validate specific field "REPLACE_CARD_OID" value "not null" in cards table from database
    Then User validate address stored in database for section "ReplaceCard" based on its action "replaceCardRequest" from propertyFile "Yes"

    #Change pin scenarios after pinning an account -- Done
#  @UI-Cards
#  Scenario: OMV-874 ClientUser : Validate Change PIN positive flow for Client User after pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    And User click on button "Home" using span text
#    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
#    Then User get card number for which action applicable "Yes" and action type "changePinTypeWishPin"
#    And User select account based on the account number from property file "accountNumberInCardsModule"
#    And User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
#    And User click on button "Change PIN"
#    And User validate action page or popup header is "Change PIN for card ****" with last 5 digits of card for action "Change PIN"
#    And User enter values for "selfSelectPin" with "valid" values
##    And User click on button contains "sendMyPinToADifferenceAddress" using locator "//div[@class='toggle ng-star-inserted'][1]/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
##    And User validate specific address section "PIN contact address:"
##    And User validate specific address section "Primary account address:"
#    And User click on "Save" button
#    Then User validate "snackbar-text" message "PIN has been updated" in "cards" module
#
#    ##Done
#  @UI-Cards
#  Scenario: OMV-874 ClientUser : Validate Change PIN Negative flow for Client User after pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    And User click on button "Home" using span text
#    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
#    Then User get card number for which action applicable "Yes" and action type "changePinTypeWishPin"
#    And User select account based on the account number from property file "accountNumberInCardsModule"
#    And User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
#    And User click on button "Change PIN"
#    And User validate action page or popup header is "Change PIN for card ****" with last 5 digits of card for action "Change PIN"
#    And User enter values for "selfSelectPin" with "invalid" values
#    Then User validate presence of "PIN doesn" field with "mat-error" tag
#    Then User validate presence of "t match" field with "mat-error" tag

    #REIssue pin after pinning an account -- Done
  @UI-Cards
  Scenario: OMV-878 ClientUser : ReIssue pin after pinning account for Client User after pinning account
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
    Then User get card number for which action applicable "Yes" and action type "reissuePin"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
    And User click on button "Reissue PIN"
    And User validate action page or popup header is "Resend PIN for card ****" with last 5 digits of card for action "Change PIN"
#    And User validate specific address section "PIN contact address:"
#    And User validate specific address section "Primary account address:"
    And User click on "Submit" button
    Then User validate presence of "PIN resend request has been submitted" field with "div" tag

#  Scenario: OMV-880 Card Usage after pinning account for Client User after pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    And User click on button "Home" using span text
#    And User make sure account is not pinned for logged in user
#    And User set property "accountNumberInTransactionsModule" value as "is not null" in "TestDataProperties"
#     #    And User select client and country based on logged in user "ClientUserName"
#    Then User validate header of module "Accounts"
#    And User select or enter value from a field "accountName" based on its behavior "text" for user ClientUserName" in module "accounts"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#     #    And User store change PIN and Reissue PIN related field values in test data property file
#    And User "enable" change PIN and Reissue PIN options
#    And User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User validate message "Use the fields above to filter your results" based on tag name "div"
#    And User select or enter value from a field "<fieldNameOfCardsearch>" based on its behavior "<CardsearchFieldBehavior>" for user "<UserName>" in module "Cards"
#    And User click on " Search " button
#    And User click on Three dot icon in module "cards" based on search keywords "NewlyOrderCardNumber"
#    And User click on button "Card usage"
#    And User validate action page or popup header is "View usage for the card ****" with last 5 digits of card for action "Change PIN"
#    Then User validate Velocity limits populated as per database or not
#    And User click on "Close" button
#
##  @Test
#  Scenario: OMV-890 Validate in Change pin type action select system generated option for Client User after pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    And User click on button "Home" using span text
#    And User make sure account is not pinned for logged in user
#     #    Then User get card number for which action applicable "Yes" and action type "changePinTypeWishPin"
#    And User select client and country based on logged in user "ClientUserName"
#    Then User validate header of module "Accounts"
#    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#    And User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
#    And User click on button "Change PIN type"
#    And User validate action page or popup header is "Change PIN type for card ****" with last 5 digits of card for action "Change PIN"
##    And User select "identification method" as radio button "system" for "add" form
##    Then User validate message "receive a system generated PIN to the designated address" based on tag name "div"
##    And User validate specific address section "PIN contact address:"
##    And User validate specific address section "Primary account address:"
#    And User click on "Save" button
#    Then User validate success message of "changePinType" for no of card ""
#
##  @Test
#  Scenario: OMV-890 Validate in Change pin type action select self select pin option after pinning account for Client User after pinning an account
#    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
#    And User click on button "Home" using span text
#    And User make sure account is not pinned for logged in user
#     #    Then User get card number for which action applicable "Yes" and action type "changePinTypeSysPin"
#    And User select client and country based on logged in user "ClientUserName"
#    Then User validate header of module "Accounts"
#    And User select or enter field value "accountNumberInCardsModule" for a field "accountNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "accounts" based on "accountName"
#    Then User click on button "Select account"
#    And User click on the "menu" "Cards"
#    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
#    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
#    And User click on "Search" button
#    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
#    And User click on button "Change PIN type"
#    And User validate action page or popup header is "Change PIN type for card ****" with last 5 digits of card for action "Change PIN"
##    And User select "identification method" as radio button "self" for "add" form
##    And User click on button contains "sendMyPinToADifferenceAddress" using locator "//div[@class='toggle ng-star-inserted'][1]/mat-slide-toggle/label/div" which has locator type "xpath" using method "click"
##    And User validate specific address section "PIN contact address:"
##    And User validate specific address section "Primary account address:"
#    And User click on "Save" button
#    Then User validate success message of "changePinType" for no of card ""

  @UI-Cards
  Scenario: TC-01 : OMV-877 : Validate Reset PIN functionality after pinning account for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
    Then User get card number for which action applicable "Yes" and action type "resetPin"
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "Cards" based on "cardNumber"
    And User click on button "Reset pin"
    And User validate action page or popup header is "Reset pin for card ****" with last 5 digits of card for action "Reset pin"
#    And User validate specific address section "PIN contact address:"
#    And User validate specific address section "Primary account address:"
    And User click on "Save" button
    Then User validate message "PIN reset request has been submitted" based on tag name "div"

  @UI-Cards
  Scenario: TC-02 : OMV-881 : Validate User is able to change the card status after pinning account for Client User
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
    Then User get card number for which action applicable "Yes" and action type ""
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" button
    And User click three dot icon of "1" record in module "cards" based on "cardNumber"
    And User click on button "Change card status"
    And User validate action page or popup header is "Change card status for ****" with last 5 digits of card for action "Change Card Status"
    Then User click on button contains "status" using locator "mav-select[name='status']" which has locator type "cssSelector" using method "click"
    And User validate "ChangeCardStatus" drop down values are coming based on database or not and select one value based on cardReissueActionCid "5901"
    And User click on "Save" button
#    Then User validate success message of "ChangeCardStatus" for no of card ""
#    And User validate the status of card in the database after performing "ChangeCardStatus" card action

  @UI-Cards
  Scenario: ClientUser : Bulk card change status
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    And User click on button "Home" using span text
    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
    Then User get card number for which action applicable "Yes" and action type ""
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User select card statuses which are applicable for change card status action
    When User click on "Search" button
    Then User validate bulk card change status behavior when no of cards selected "0"
    Then User validate bulk card change status behavior when no of cards selected "1"
#    Then User validate bulk card change status behavior when no of cards selected "2"

  @UI-Cards
  Scenario: OMV-771 : Client User - Validate when user enter value in cardNumber and press tab then verify remaining field value are populated based on searched cardNumber
    Given User is on Login page and validate WEX logo text "OMV", for scenario ""
    When User set property "cardsModuleUserType" value as "OLS" in "TestDataProperties"
    Then User get card number for which action applicable "Yes" and action type ""
    And User select account based on the account number from property file "accountNumberInCardsModule"
    And User click on the "menu" "Cards"
    Then User get card number for which action applicable "Yes" and action type ""
    And User click button "Cards" using tag name "span", attribute name "ng-reflect-marquee-text_", attribute value "Cards" based on position "2"
    And User validate message "Use the fields above to filter your results" based on tag name "div"
    And User select or enter field value "cardNumberToPerformAnAction" for a field "cardNumber" based on its behavior "text" from "properties"
    And User click on "Search" based on tag name "span"
    Then User click on button contains "SearchBar" using locator "span[class='account-text']" which has locator type "cssSelector" using method "click"
    And User validate remaining search bar field values "cardNumber,driverName,licensePlate,costCentreCode,onlineStatus" are auto populated based on searched card number
