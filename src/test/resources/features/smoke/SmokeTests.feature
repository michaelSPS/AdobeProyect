@smoke
Feature: TC02 Smoke Test - Basic checks

  Scenario: TC02.1 Homepage loads successfully
    Given The user navigates to the homepage
    Then The homepage should be displayed
