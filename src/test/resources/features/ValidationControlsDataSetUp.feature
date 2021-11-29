Feature: Validation control data set up

  @Test
  Scenario: Generate an extent Report based on property values
    When User generate an extent Report based on property values

#  @Test
  Scenario Outline: Config data for menu scripts
    When User insert menu scripts config data based on fields clientGroupMid "<Client Group Name>" get menu oid using "<Menu Title>","<Parent Menu Title>", "<Screen Action Key>" , "<Endpoint>", "<Method>" and get accessLevelCid using "<Access Level Description>" and get accessGroupOid using "<Access Group Description>" with Serial No "<SNO>"

    Examples:
      | Client Group Name | Access Group Description | Menu Title | Parent Menu Title | Screen Action Key | Endpoint | Method | Access Level Description | SNO |

#  @Test
  Scenario Outline: Config data for menu scripts (update)
    When User update menu scripts config data based on fields clientGroupMid "<Client Group Name>" get menu oid using "<Menu Title>","<Parent Menu Title>", "<Screen Action Key>" , "<Endpoint>", "<Method>" and get accessLevelCid using "<Access Level Description>" and get accessGroupOid using "<Access Group Description>" with Serial No "<SNO>"

    Examples:
      | Client Group Name | Access Group Description | Menu Title            | Parent Menu Title | Screen Action Key   | Endpoint                                   | Method | Access Level Description | SNO |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | CHANGE_PWD          | login/change-password                      | POST   | Full Access              | 413 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | VIEW                | customer/.*/hierarchies                    | GET    | Full Access              | 414 |
      | OMV               | CARD ADMINISTRATOR       | Reports               | Reports           | ADD                 | report/adhoc-report                        | POST   | Full Access              | 415 |
      | OMV               | CARD ADMINISTRATOR       | Report Templates      | Reports           | ADD                 | report/create-scheduled-report             | POST   | Full Access              | 416 |
      | OMV               | CARD ADMINISTRATOR       | Reports               | Reports           | DOWNLOAD            | report/download                            | POST   | Full Access              | 417 |
      | OMV               | CARD ADMINISTRATOR       | Invoices              | Reports           | DOWNLOAD            | report/download                            | POST   | No Access                | 418 |
      | OMV               | CARD ADMINISTRATOR       | Report Templates      | Reports           | STATUS              | report/edit-status                         | PUT    | Full Access              | 419 |
      | OMV               | CARD ADMINISTRATOR       | Report Templates      | Reports           | EDIT                | report/scheduled-report                    | POST   | Full Access              | 420 |
      | OMV               | CARD ADMINISTRATOR       | Report Templates      | Reports           | EDIT                | report/scheduled-report                    | PUT    | Full Access              | 421 |
      | OMV               | CARD ADMINISTRATOR       | Reports               | Reports           | VIEW                | report/search                              | POST   | Full Access              | 422 |
      | OMV               | CARD ADMINISTRATOR       | Invoices              | Reports           | VIEW                | report/search                              | POST   | No Access                | 423 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | SEL_ACCOUNT         | transaction/search                         | POST   | Full Access              | 424 |
      | OMV               | CARD ADMINISTRATOR       | Authorisations        | Transactions      | VIEW                | transaction/search                         | POST   | Read Only                | 425 |
      | OMV               | CARD ADMINISTRATOR       | Transactions          | Transactions      | VIEW                | transaction/search                         | POST   | Read Only                | 426 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW_TRANSACTIONS   | transaction/search                         | POST   | Full Access              | 427 |
      | OMV               | CARD ADMINISTRATOR       | Transactions          | Transactions      | DISPUTE_TRANSACTION | transaction/dispute                        | POST   | No Access                | 428 |
      | OMV               | CARD ADMINISTRATOR       | Authorisations        | Transactions      | DOWNLOAD            | transaction/export                         | POST   | Read Only                | 429 |
      | OMV               | CARD ADMINISTRATOR       | Transactions          | Transactions      | DOWNLOAD            | transaction/export                         | POST   | Read Only                | 430 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | EDIT                | user/edit-profile                          | PUT    | Full Access              | 431 |
      | OMV               | CARD ADMINISTRATOR       | Users                 | Admin             | EDIT                | user/edit-profile                          | PUT    | No Access                | 432 |
      | OMV               | CARD ADMINISTRATOR       | Users                 | Admin             | STATUS              | user/edit-status                           | PUT    | No Access                | 433 |
      | OMV               | CARD ADMINISTRATOR       | Users                 | Admin             | ADD                 | user                                       | POST   | No Access                | 434 |
      | OMV               | CARD ADMINISTRATOR       | Users                 | Admin             | ACC_ACCESS          | user/account-profile                       | PUT    | No Access                | 435 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | ADD                 | lookup/card-offers                         | GET    | Full Access              | 436 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | lookup/card-offers                         | GET    | Full Access              | 437 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | EDIT                | lookup/card-offers                         | GET    | Full Access              | 438 |
      | OMV               | CARD ADMINISTRATOR       | Group PIN             | Cards             | VIEW                | lookup/card-offers                         | GET    | Full Access              | 439 |
      | OMV               | CARD ADMINISTRATOR       | Bulk order            | Cards             | VIEW                | lookup/card-offers                         | GET    | Full Access              | 440 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | ADD                 | lookup/card-offers                         | GET    | Full Access              | 441 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | VIEW                | lookup/card-offers                         | GET    | Full Access              | 442 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | DELETE              | lookup/card-offers                         | GET    | Full Access              | 443 |
      | OMV               | CARD ADMINISTRATOR       | Authorisations        | Transactions      | VIEW                | transaction/search-authorisations          | POST   | Read Only                | 444 |
      | OMV               | CARD ADMINISTRATOR       | Transactions          | Transactions      | VIEW                | transaction/details/*                      | GET    | Read Only                | 445 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW_TRANSACTIONS   | transaction/details/*                      | GET    | Full Access              | 446 |
      | OMV               | CARD ADMINISTRATOR       | Authorisations        | Transactions      | DOWNLOAD            | transaction/export-authorisations          | POST   | Read Only                | 447 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | APPROVE             | account/approve-hierarchy                  | PUT    | No Access                | 448 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | ADD                 | customer/create-hierarchy                  | POST   | No Access                | 449 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | ADD_CHILD           | customer/create-hierarchy                  | POST   | No Access                | 450 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | EDIT                | customer/update-account-info/*             | PUT    | No Access                | 451 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | EDIT                | customer/update-hierarchy                  | PUT    | No Access                | 452 |
      | OMV               | CARD ADMINISTRATOR       | Drivers               |                   | VIEW                | customer/drivers                           | POST   | Full Access              | 453 |
      | OMV               | CARD ADMINISTRATOR       | Vehicles              |                   | VIEW                | customer/vehicles                          | POST   | Full Access              | 454 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/search                                | POST   | Full Access              | 455 |
      | OMV               | CARD ADMINISTRATOR       | Group PIN             | Cards             | ADD                 | card/group-pin                             | POST   | Full Access              | 456 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/vas-subscriptions                     | POST   | Full Access              | 457 |
      | OMV               | CARD ADMINISTRATOR       | Manage auto-reissue   | Cards             | EDIT                | card/manage-auto-reissue                   | POST   | Full Access              | 458 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | CARD_USAGE          | card/velocity-values                       | POST   | Full Access              | 459 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/validate-private-profiles             | POST   | Full Access              | 460 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/auto-reissue-cards                    | POST   | Full Access              | 461 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/delivery-address/*                    | GET    | Full Access              | 462 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/velocity-value-types                  | GET    | Full Access              | 463 |
      | OMV               | CARD ADMINISTRATOR       | Group PIN             | Cards             | CHANGE_PIN          | card/group-pin                             | PUT    | Full Access              | 464 |
      | OMV               | CARD ADMINISTRATOR       | Group PIN             | Cards             | CHANGE_NAME         | card/group-pin                             | PUT    | Full Access              | 465 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | RESET_PIN           | card/reset-pin                             | PUT    | Full Access              | 466 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | CHANGE_PIN_TYPE     | card/change-pin-type                       | PUT    | Full Access              | 467 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | VIEW                | user/context                               | GET    | Full Access              | 468 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | VIEW                | accounts/search                            | POST   | No Access                | 469 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | SEL_ACCOUNT         | accounts/search                            | POST   | Full Access              | 470 |
      | OMV               | CARD ADMINISTRATOR       | Financial information | Admin             | VIEW                | accounts/search                            | POST   | No Access                | 471 |
      | OMV               | CARD ADMINISTRATOR       | List prices           | Admin             | VIEW                | accounts/search                            | POST   | No Access                | 472 |
      | OMV               | CARD ADMINISTRATOR       | Site locator          | Admin             | VIEW                | accounts/search                            | POST   | Full Access              | 473 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | VIEW                | account/details                            | POST   | No Access                | 474 |
      | OMV               | CARD ADMINISTRATOR       | Financial information | Admin             | VIEW                | account/details                            | POST   | No Access                | 475 |
      | OMV               | CARD ADMINISTRATOR       | Reports               | Reports           | VIEW                | report/configured-report-options           | POST   | Full Access              | 476 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | customer/validate-hierarchy                | POST   | Full Access              | 477 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | DOWNLOAD            | card/export                                | POST   | Full Access              | 478 |
      | OMV               | CARD ADMINISTRATOR       | VAT numbers           | Admin             | ADD                 | account/create-tax-number                  | POST   | No Access                | 479 |
      | OMV               | CARD ADMINISTRATOR       | VAT numbers           | Admin             | EXPIRE              | account/.*/expiry-tax-number               | PUT    | No Access                | 480 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | VIEW                | lookup/*                                   | GET    | Full Access              | 481 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | VIEW                | account/approve                            | PUT    | Full Access              | 482 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/velocity-types                        | GET    | Full Access              | 483 |
      | OMV               | CARD ADMINISTRATOR       | VAT numbers           | Admin             | VIEW                | account/*/tax-numbers                      | GET    | No Access                | 484 |
      | OMV               | CARD ADMINISTRATOR       | VAT numbers           | Admin             | APPROVE             | account/*/tax-numbers                      | GET    | No Access                | 485 |
      | OMV               | CARD ADMINISTRATOR       | VAT numbers           | Admin             | DECLINE             | account/*/tax-numbers                      | GET    | No Access                | 486 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | DECLINE             | account/update-hierarchy                   | PUT    | No Access                | 487 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | EXPIRE              | account/update-hierarchy                   | PUT    | No Access                | 488 |
      | OMV               | CARD ADMINISTRATOR       | Cost Centres          | Admin             | VIEW                | customer/cost-centres                      | POST   | Read Only                | 489 |
      | OMV               | CARD ADMINISTRATOR       | Vehicles              |                   | EDIT                | customer/update-vehicle/.*                 | PUT    | Full Access              | 490 |
      | OMV               | CARD ADMINISTRATOR       | Financial information | Admin             | EDIT                | customer/update-financial-info/.*          | PUT    | No Access                | 491 |
      | OMV               | CARD ADMINISTRATOR       | Bulk order            | Cards             | VIEW                | card/download-bulk-template                | POST   | Full Access              | 492 |
      | OMV               | CARD ADMINISTRATOR       | Bulk order            | Cards             | DOWNLOAD            | card/download-bulk-template                | POST   | Full Access              | 493 |
      | OMV               | CARD ADMINISTRATOR       | Users                 | Admin             | VIEW                | user/account-profile                       | GET    | No Access                | 494 |
      | OMV               | CARD ADMINISTRATOR       | Group PIN             | Cards             | VIEW                | card/group-pin/.*                          | GET    | Full Access              | 495 |
      | OMV               | CARD ADMINISTRATOR       | Bulk order            | Cards             | UPLOAD              | card/upload-bulk-template/*                | POST   | Full Access              | 496 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | pricing/site-groups                        | GET    | Full Access              | 497 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | pricing/networks                           | GET    | Full Access              | 498 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | VIEW                | lookup/*                                   | POST   | Full Access              | 499 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | VIEW                | wfhierarchy/.*/hierarchies                 | GET    | Full Access              | 500 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | ADD                 | wfhierarchy/create-wfhierarchy             | POST   | No Access                | 501 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | APPROVE             | wfhierarchy/approve-wfhierarchy            | POST   | No Access                | 502 |
      | OMV               | CARD ADMINISTRATOR       | Hierarchy             | Admin             | EDIT                | wfhierarchy/update-wfhierarchy             | PUT    | No Access                | 503 |
      | OMV               | CARD ADMINISTRATOR       | Invoices              | Reports           | VIEW                | report/stored-report                       | POST   | No Access                | 504 |
      | OMV               | CARD ADMINISTRATOR       | Bulk order            | Cards             | STATUS              | card/bulk-card-status                      | PUT    | Full Access              | 505 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/details                               | POST   | Full Access              | 506 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | VIEW                | user                                       | GET    | Full Access              | 507 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | VIEW                | account/.*                                 | GET    | No Access                | 508 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | APPROVE             | account/.*                                 | GET    | No Access                | 509 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | DECLINE             | account/.*                                 | GET    | No Access                | 510 |
      | OMV               | CARD ADMINISTRATOR       | Transactions          | Transactions      | VIEW                | transaction/total-transactions             | POST   | Read Only                | 511 |
      | OMV               | CARD ADMINISTRATOR       | Vehicles              |                   | VIEW                | customer/.*/vehicles                       | GET    | Full Access              | 512 |
      | OMV               | CARD ADMINISTRATOR       | Drivers               |                   | VIEW                | customer/.*/drivers                        | GET    | Full Access              | 513 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | customer/.*/cards                          | POST   | Full Access              | 514 |
      | OMV               | CARD ADMINISTRATOR       | Users                 | Admin             | VIEW                | user/.*                                    | GET    | Full Access              | 515 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | CONTACT_INFO        | customer/*                                 | PUT    | No Access                | 516 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | VIEW                | customer/*                                 | GET    | Full Access              | 517 |
      | OMV               | CARD ADMINISTRATOR       | Account Information   | Admin             | VIEW                | customer/*                                 | GET    | No Access                | 518 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | VIEW                | customer/*                                 | GET    | Read Only                | 519 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | CONTACT_INFO        | customer/.*/validate-contact               | POST   | Read Only                | 520 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | ADD                 | customer/.*/contact                        | POST   | Read Only                | 521 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | EDIT                | customer/.*/edit-contact                   | PUT    | Read Only                | 522 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | ADDRESS_INFO        | customer/.*/edit-contact                   | PUT    | Read Only                | 523 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | CONTACT_INFO        | customer/.*/edit-contact                   | PUT    | Read Only                | 524 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/lookup/product-restrictions           | GET    | Full Access              | 525 |
      | OMV               | CARD ADMINISTRATOR       | Contacts              | Admin             | DELETE              | customer/.*/contact                        | DELETE | Read Only                | 526 |
      | OMV               | CARD ADMINISTRATOR       | Cost Centres          | Admin             | VIEW                | customer/.*/cost-centres                   | GET    | Read Only                | 527 |
      | OMV               | CARD ADMINISTRATOR       | Cost Centres          | Admin             | EDIT                | customer/.*/cost-centres                   | PUT    | Read Only                | 528 |
      | OMV               | CARD ADMINISTRATOR       | Cost Centres          | Admin             | VIEW_CARD           | customer/.*/cost-centres                   | PUT    | Read Only                | 529 |
      | OMV               | CARD ADMINISTRATOR       | Cost Centres          | Admin             | ADD                 | customer/.*/cost-centres                   | POST   | Read Only                | 530 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | ADD                 | customer/.*/cost-centres                   | POST   | Full Access              | 531 |
      | OMV               | CARD ADMINISTRATOR       | Reports               | Reports           | VIEW                | report/report-params/*                     | GET    | Full Access              | 532 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | STATUS              | card/status/valid-changes                  | POST   | Full Access              | 533 |
      | OMV               | CARD ADMINISTRATOR       | Report Templates      | Reports           | VIEW                | report/search-scheduled-report             | POST   | Full Access              | 534 |
      | OMV               | CARD ADMINISTRATOR       | Home                  |                   | VIEW                | accounts                                   | GET    | Full Access              | 535 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | ADD                 | card/order                                 | POST   | Full Access              | 536 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/order                                 | POST   | Full Access              | 537 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | EDIT                | card/order                                 | POST   | Full Access              | 538 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | CLONE               | card/order                                 | POST   | Full Access              | 539 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | STATUS              | card/status                                | PUT    | Full Access              | 540 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | REPLACE_CARD        | card/status                                | PUT    | Full Access              | 541 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | STATUS              | card/status/valid-changes                  | GET    | Full Access              | 542 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/profiles                              | POST   | Full Access              | 543 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/velocity-value-options                | GET    | Full Access              | 544 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | EDIT                | card/update                                | PUT    | Full Access              | 545 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | VIEW                | card/product-restrictions                  | POST   | Full Access              | 546 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | REISSUE_PIN         | card/reissue-pin                           | PUT    | Full Access              | 547 |
      | OMV               | CARD ADMINISTRATOR       | Cards                 | Cards             | CHANGE_PIN          | card/change-pin                            | PUT    | Full Access              | 548 |
      | OMV               | CARD ADMINISTRATOR       | Approver dashboard    | Admin             | VIEW                | field-groups/account-approver-dashboard/   | GET    | Full Access              | 549 |
      | OMV               | CARD ADMINISTRATOR       | Approver dashboard    | Admin             | APPROVE             | field-groups/account-approver-dashboard/   | GET    | Full Access              | 550 |
      | OMV               | CARD ADMINISTRATOR       | Approver dashboard    | Admin             | DECLINE             | field-groups/account-approver-dashboard/   | GET    | Full Access              | 551 |
      | OMV               | CARD ADMINISTRATOR       | Approver dashboard    | Admin             | REVIEW              | field-groups/account-approver-dashboard/   | GET    | Full Access              | 552 |
      | OMV               | CARD ADMINISTRATOR       | Approver dashboard    | Admin             | VIEW                | wfhierarchy/hierarchies-approver-dashboard | GET    | Full Access              | 553 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | CLONE               | card/acceptance-sites                      | POST   | Full Access              | 554 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | VIEW                | location/search-sites                      | POST   | Full Access              | 555 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | ADD                 | card/acceptance-site                       | POST   | Full Access              | 556 |
      | OMV               | CARD ADMINISTRATOR       | Acceptance sites      | Cards             | EDIT                | card/acceptance-site                       | PUT    | Full Access              | 557 |