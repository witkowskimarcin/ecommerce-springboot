Feature: CategoryService class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare category with id: <id>, name: "<categoryName>"

    When categoryRepository.findAll
    When categoryRepository.findById

    Then categoryService.getAllCategories
    Then categoryService.getCategoryById
    Then categoryService.addCategory
    Then categoryService.editCategory
    Then categoryService.removeCategoryById

    Examples:
      | id | categoryName           |
      | 1  | Peryferia komputerowe  |
      | 2  | Podzespoły komputerowe |