Feature: SessionService class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare cart
    Given Prepare product with id: <id>, name: "<productName>", description: "<description>", price: <price>, quantity: <quantity>

    When session.getId
    When session.getAttribute

    Then sessionService.getCartInSession
    Then sessionService.getCart
    Then sessionService.getSessionId
    Then sessionService.addProduct
    Then sessionService.getQuantity
    Then sessionService.clearCart
    Then sessionService.removeCart
    Then sessionService.addProductToCart
    Then sessionService.addProduct
    Then sessionService.removeProduct

    Examples:
      | id | productName | description            | price   | quantity |
      | 1  | Epson       | Jakaś drukarka         | 300.0   | 10       |
      | 2  | GTX 1080 Ti | Super wydajna karta    | 1500.99 | 2        |
      | 3  | i5 4690k    | Super wydajny procesor | 1000.50 | 1        |
      | 4  | Skaner      | Fajny skaner           | 150.0   | 1        |
      | 5  | Mysz        | Myszka                 | 30.99   | 999      |
      | 6  | Klawiatura  | Klawiatura mechaniczna | 250.99  | 99       |
      | 7  | Pendrive    | Pendrive 32 GB         | 50.0    | 1000     |

