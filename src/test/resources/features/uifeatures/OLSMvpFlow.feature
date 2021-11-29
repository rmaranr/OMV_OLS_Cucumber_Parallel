Feature: OLSMvp Positive flow

  Background:
    Given User is on Login page and validate WEX logo text "Fleet Online", for scenario "<ScenarioName>"
    And User click on button "Home" using span text

  @PositiveFlow
  Scenario: Validate OLS MVP positive scenario flow
    # Add a new user
    When User select an account from account picker which "has", "1 - Active" status and "Active" sub status
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Users"
    Then User verifies the count of records in "Users" page matches with the database or not
    And User click on the "Add Users button" "+ User"
    And User enter "fullName" as "DemoFullName08" in "users" module having length "8" in "Add" form
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in "users" module having length "20" in "Add" form
    And User enter "mobilePhone" as "Numeric" in "users" module having length "8" in "Add" form
    And User enter "otherPhone" as "Numeric" in "users" module having length "8" in "Add" form
    And User select value "usersRoleDescription" in "users" module by clicking drop down "role"
    And User enter "userName" as "DemoUser08" in "users" module having length "8" in "Add" form
    Then User click on Next button
    Then User select all accounts from the list of accounts if more than one account displayed for logged in user "UserName"
    And User click on "Submit" button
    And User validated success message of module is "New User has been created"
    When User click on user badge
    When User click on Log out button

#     Login to gmail and create a new password for newly created user
    When User login to gmail with valid username "EmailID" and password "EmailPassword"
    And User reset the password by clicking on here link by using searchText "caltex-no-reply" and subject "Your StarCard Online Portal Password"
    And User switched to a window number "2"
    And User enter new password "New_PassWord" and validate that it contains all specifications "<Specifications>" and corresponding radio button status Selected "true"
    And User enter confirm new password "New_PassWord"
    Then User validates and click on submit button enabled if new password and confirm new password are same
    And User navigate to url "url"
    When User enter "NewlyCreatedUser" and "New_PassWord"
    And User click on login button
    When User select account based on the account number "0800000008"

    #Validate primary account information
    And User click on the "menu" "Admin"
    And User click on the "menu" "Account Information"
    And User validate whether the account details section has "Account name" "Doing business as" "Account number" "Status"

    #Add a new cost centre
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Cost centres"
    And User click on the "Add Cost Centres button" "+ Cost centre"
    And User enter "costCentre" value in "costCentre" module as "DemoCost08" having length "9" in "Add" form
    And User enter "name" value in "costCentre" module as "DemoName08" having length "10" in "Add" form
    And User enter "description" value in "costCentre" module as "DemoDescription" having length "50" in "Add" form
    Then User click on "Submit" button
    And User validated success message of module is "New Cost centre has been created"

    #Edit newly created cost centre
    When User enter search keywords "costCentre-costCentre" by selecting search attribute as "Cost centre"
    Then User click on search icon
    And User click on Three dot icon in module "costCentre" based on search keywords "costCentre-costCentre"
    And User click on button "Edit cost centre"
    And User enter "costCentre" value in "costCentre" module as "EditCost08" having length "9" in "Edit" form
    And User enter "name" value in "costCentre" module as "EditName08" having length "10" in "Edit" form
    And User enter "description" value in "costCentre" module as "EditDescription" having length "50" in "Edit" form
    Then User click on "Save" button
    And User validated success message of module is "Cost centre has been updated"
    And User validate cost center data is saved in database

  #Create new card delivery contact
    And User click on the "menu" "Admin"
    And User click on the "submenu" "Contacts"
    Then User delete card delivery contact from database if it is present
    And User click on the "Add Contacts button" "+ Contact"
    And User enter "contactName" as "DemoContact08" in module "contacts" having length "10" in "Add" form
    And User enter "jobTitle" as "DemoJobTitle" in module "contacts" having length "10" in "Add" form
    And User enter "mobilePhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "otherPhone" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "fax" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User enter "emailAddress" as "srilatha.nerella@wexinc.com" in module "contacts" having length "10" in "Add" form
    And User select value "Card Delivery" by clicking drop down "contactType"
    And User click on Next button
    And User enter "addressLine" as "5919 Trussville Crossings Pkwy,Birmingham AL 35235" in module "contacts" having length "90" in "Add" form
    And User enter "suburb" as "Birmingham" in module "contacts" having length "90" in "Add" form
    And User enter "postalCode" as "Numeric" in module "contacts" having length "10" in "Add" form
    And User select value "Johor" by clicking drop down "state"
    And User verify by default "Use same as Contact Address" check box is selected or not
    And User click on "Submit" button

  #Order a card
    And User click on the "menu" "Cards"
    And User click on the "Add Cards button" "+ Cards"
    And User verify the drop down "cardOffer" value is pre-selected if the drop down has one or more than one value and validate its status
    And User verify the drop down "cardProduct" value is pre-selected if the drop down has one or more than one value and validate its status
    And User verify the drop down "cardType" value is pre-selected if the drop down has one or more than one value and validate its status
    Then User validate Expiry Date is populated as clientProcessingDate plus ExpiryMonths of card product and user can select until MaxCardExpiryDate reaches
    And User select value "costCentre-costCentre" in cost centre field from the list
    And User validate identification method
    And User enter "externalRef" as "DemoRe01" in "orderCard" module having length "8" in "Add" form
    And User enter "driverId" as "DemoID" in "orderCard" module having length "8" in "Add" form
    And User enter "driverName" as "DemoDriverName08" in "orderCard" module having length "8" in "Add" form
    And User enter "vehicleDescription" as "DemoVehicle08" in "orderCard" module having length "8" in "Add" form
    And User enter "licensePlate" as "DemoLi08" in "orderCard" module having length "8" in "Add" form
    When User validate EmbossingName is populated based on the account selected from account picker
    Then User validate POS Prompts in card details stepper
    And User click "Next" button using Java Script executor which is present at position "1"
    Then User validate default products selected in Product Selection stepper based on the client customer
    And User click "Next" button using Java Script executor which is present at position "2"
    And User validate "timeLimit" field value using "mat-select" tag name
    And User validate "locationRestriction" field value using "mat-select" tag name
    And User validate Purchase Controls section fields and their values
    |velocityTransactionLimit0,velocityTransactionLimit1,velocityPurchaseLimit0,velocityPurchaseLimit1,velocityPurchaseLimit2,velocityPurchaseLimit3,velocityPurchaseLimit4|
    And User click "Next" button using Java Script executor which is present at position "3"
    And User validate primary account address in Delivery address stepper
    And User click on "Review" button
    Then User validate all stepper values in Review page
    And User click on "Submit" button
    Then User validate success message of order card

    #Navigate to Drivers module then search newly created driver
    And User click on the "menu" "Drivers"
    When User enter search keywords "orderCard-driverName" by selecting search attribute as "Driver name"
    Then User click on search icon
    And User click on Three dot icon in module "Drivers" based on search keywords "orderCard-driverName"
    And User click on button "View card details"

    #Navigate to Vehicles module then search newly created vehicle
    And User click on the "menu" "Vehicles"
    When User enter search keywords "orderCard-vehicleDescription" by selecting search attribute as "Vehicle description"
    Then User click on search icon
    And User click on Three dot icon in module "Vehicles" based on search keywords "orderCard-vehicleDescription"
    And User click on button "View card transactions"

   #Search a card which has transactions
    And User click on clear search icon
    When User validate the count of transactions displayed in UI for date filter type "Last 90 days" with the database
    And User get "<TransCategoryDescription>" from Transactions table using Transaction category cid "4701" for account selected in account picker
    When User enter search keywords "CARD_NUMBER" for Transaction type "trans-Purchase-CardTransOID" by selecting search attribute as "Card number" in Transactions page
    Then User click on search icon
    And User validate the transaction level field values present in Card Transaction with database and click on it
    And User validate "main" section fields "Total amount,DateOfTransaction,Cost centre,Driver name,Vehicle description" values of "CardTransaction" with database
    And User validate "details" section fields "Location,Location address,Posted time,Reference number,Terminal ID,Capture type,Transaction type,Authorisation number,Odometer" values of "CardTransaction" with database
    And User click on Transaction Breakdown
    And User validate "breakDownBorderForAllLines" section fields "Product,Unit price,Total (incl of tax & rebate)" values of "CardTransaction" with database
    And User validate "breakDownAmountFields" section fields "Fees,Transaction amount,Total taxes,Total rebate" values of "CardTransaction" with database
