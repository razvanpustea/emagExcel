Feature: Search 1 product
  Scenario: Search a product on eMAG, read from Excel, and write the name and price to the Excel file
    Given user is on eMAG's homepage
    When he enters the product from Excel file in the search bar and presses Enter
    Then he should see the product with some details or a message suggesting that the product isn't available
    And the Excel file should be updated with the name and prices of related products