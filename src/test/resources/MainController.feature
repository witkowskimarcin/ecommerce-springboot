Feature: MainController class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare category with id: <id>, name: "<categoryName>"
    Given Prepare subcategory with id: <id>, name: "<subcategoryName>"
    Given Prepare product with id: <id>, name: "<productName>", description: "<description>", price: <price>, quantity: <quantity>
    Given Prepare opportunity with id: <id>, quantity: <quantity>, promotionCode: "<promotionCode>"
    Given Prepare promotedProduct with id: <id>

    When subcategoryRepository.findById
    When categoryRepository.findById
    When subcategoryRepository.findAll
    When categoryRepository.findAll
    When productRepository.findById
    When subcategoryRepository.findById
    When productRepository.findAllBySubcategory
    When opportunityRepository.findAll
    When promotedProductRepository.findAll

    Then thenGetAllCategories
    Then thenGetCategoryById
    Then thenGetAllSubcategoriesByCategoryId
    Then thenGetSubcategoryById
    Then thenGetOpportunity
    Then thenGetAllPromotedProducts
    Then thenGetProductsBySubcategoryIdTest
    Then thenGetCategoryBySubcategoryIdTest
    Then thenGetProductByIdTest
    Then thenGetCategoryOfProductTest

    Examples:
      | id | categoryName           | subcategoryName | productName | description         | price   | quantity | promotionCode |
      | 1  | Peryferia komputerowe  | Drukarki        | Epson       | Jakaś drukarka      | 300.0   | 10       | TEST01        |
      | 2  | Podzespoły komputerowe | Karty graficzne | GTX 1080 Ti | Super wydajna karta | 1500.99 | 2        | TEST02        |
