@e2e
Feature: TC03 End-to-End Purchase Flow

  Scenario: TC03.1 Complete purchase flow for PS5 console
    Given The Client navigate to home-page
    When The Client write playstation in the searchbar
    And The Client type ENTER
    Then The Client verify that the results displayed include games for PlayStation 5 and PlayStation consoles
    And The Client selects a console in the results listed
    And The Client selects a ps5 in the results
    And The Client clicks on button add to cart
    And The Client clicks add warranty
    And The Client goes to see the shopping-cart
    And The Client proceeds to checkout
    Then The Client should see the login page to continue
