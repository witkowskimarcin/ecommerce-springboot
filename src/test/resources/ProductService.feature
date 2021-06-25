Feature: ProductService class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare category with id: <id>, name: "<categoryName>"
    Given Prepare subcategory with id: <id>, name: "<subcategoryName>"
    Given Prepare product with id: <id>, name: "<productName>", description: "<description>", price: <price>, quantity: <quantity>
    Given Prepare opportunity with id: <id>, quantity: <quantity>, promotionCode: "<promotionCode>"
    Given Prepare promotedProduct with id: <id>
    Given Prepare image with id: <id>, image: "<image>"

    When productRepository.findById
    When subcategoryRepository.findById
    When productRepository.findAllBySubcategory
    When opportunityRepository.findAll
    When promotedProductRepository.findAll

    Then productService.getProductById
    Then productService.getProductsBySubcategoryId
    Then productService.addProduct
    Then productService.editProduct
    Then productService.removeProduct
    Then imageService.saveImage
    Then imageService.saveImages
    Then productService.getCategoryOfProduct
    Then productService.getSubcategoryOfProduct
    Then opportunityService.getOpportunity
    Then opportunityService.setOpportunity
    Then opportunityService.unsetOpportunity
    Then promotedProductService.addPromotedProduct
    Then promotedProductService.removePromotedProduct
    Then promotedProductService.getAllPromotedProducts

    Examples:
      | id | categoryName           | subcategoryName | productName | description         | price   | quantity | promotionCode | image   |
      | 1  | Peryferia komputerowe  | Drukarki        | Epson       | Jakaś drukarka      | 300.0   | 10       | TEST01        | image01 |
      | 2  | Podzespoły komputerowe | Karty graficzne | GTX 1080 Ti | Super wydajna karta | 1500.99 | 2        | TEST02        | image02 |
