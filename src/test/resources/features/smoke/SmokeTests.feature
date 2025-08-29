@smoke
Feature: TC02 Smoke Test - Basic checks

  Scenario: TC02.1 Homepage loads successfully
    When The user navigates to the home-page
    Then The home page should be displayed
