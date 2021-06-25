Feature: SubcategoryService class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare subcategory with id: <id>, name: "<subcategoryName>"
    Given Prepare category with id: <id>, name: "<categoryName>"

    When subcategoryRepository.findById
    When categoryRepository.findById

    Then subcategoryService.getAllSubcategoriesByCategoryId
    Then subcategoryService.getSubcategoryById
    Then subcategoryService.addSubcategory
    Then subcategoryService.editSubcategory
    Then subcategoryService.removeSubcategory
    Then subcategoryService.getCategoryBySubcategoryId

    Examples:
      | id | categoryName           | subcategoryName |
      | 1  | Peryferia komputerowe  | Drukarki        |
      | 2  | Podzespoły komputerowe | Karty graficzne |