Feature: AdminController class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare category with id: <id>, name: "<categoryName>"
    Given Prepare subcategory with id: <id>, name: "<subcategoryName>"
    Given Prepare product with id: <id>, name: "<productName>", description: "<description>", price: <price>, quantity: <quantity>
    Given Prepare opportunity with id: <id>, quantity: <quantity>, promotionCode: "<promotionCode>"
    Given Prepare order detail with id: <id>, quantity: <quantity>
    Given Prepare order with id: <id>, date: "<date>", firstname: "<firstname>", lastname: "<lastname>", locality: "<locality>", street: "<street>", zipCode: "<zipCode>", phone: "<phone>", shipment: <shipment>, description: "<description>"

    When subcategoryRepository.findById
    When categoryRepository.findById
    When subcategoryRepository.findAll
    When categoryRepository.findAll
    When productRepository.findById
    When subcategoryRepository.findById
    When productRepository.findAllBySubcategory
    When opportunityRepository.findAll
    When promotedProductRepository.findAll
    When orderRepository.findById
    When orderRepository.findAll

    Then addCategoryTest
    Then editCategoryTest
    Then removeCategoryTest
    Then addSubcategoryTest
    Then editSubcategoryTest
    Then removeSubcategoryTest
    Then addProductTest
    Then editProductTest
    Then removeProductTest
    Then promotedProductsAddTest
    Then promotedProductsRemoveTest
    Then setOpportunityTest
    Then unsetOpportunityTest
    Then ordersTest
    Then orderDetailsTest

    Examples:
      | id | categoryName           | subcategoryName | productName | description         | price   | quantity | promotionCode | date             | firstname | lastname  | locality | street         | zipCode | phone       | shipment | description             |
      | 1  | Peryferia komputerowe  | Drukarki        | Epson       | Jakaś drukarka      | 300.0   | 10       | TEST01        | 2012-12-12 10:00 | Marcin    | Witkowski | Łódź     | Zielona 1/1    | 90-777  | 600-700-800 | 1        | Proszę o szybką wysyłkę |
      | 2  | Podzespoły komputerowe | Karty graficzne | GTX 1080 Ti | Super wydajna karta | 1500.99 | 2        | TEST02        | 2020-01-01 20:12 | Adam      | Małysz    | Warszawa | Mazowiecka 2/2 | 80-700  | 500-600-700 | 2        | Opis                    |