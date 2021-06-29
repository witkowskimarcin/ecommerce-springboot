Feature: UserService class unit tests

  Scenario Outline: Scenariusz 01
    Given Prepare user with id: <id>, username: "<username>", email: "<email>", password: "<password>", firstname: "<firstname>", lastname: "<lastname>", locality: "<locality>", street: "<street>", zipCode: "<zipCode>", phone: "<phone>", active: <active>

    When getAuthentication.getName
    When userRepository.findByUsername
    When userRepository.findByEmail
    When userRepository.existsByUsername
    When userRepository.existsByEmail

    Then userService.findByUsername
    Then userService.findByEmail
    Then userService.existsByUsername
    Then userService.existsByEmail
    Then userService.getCurrentUserName
    Then userService.getCurrentUser
    Then userService.register
#    Then userService.logged

    Examples:
      | id | username | email         | password | firstname  | lastname  | locality | street         | zipCode | phone       | active |
      | 1  | user1    | user@user.pl  | password | Marcin     | Witkowski | Lódź     | Zielona 1/1    | 90-777  | 600-700-800 | 1      |
      | 2  | user2    | user@user.de  | password | Adam       | Małysz    | Warszawa | Mazowiecka 2/2 | 80-665  | 500-600-700 | 0      |
      | 3  | user3    | user3@user.de | password | Adam3      | Małysz    | Warszawa | Mazowiecka 2/2 | 80-665  | 500-600-700 | 1      |
      | 4  | user4    | user4@user.de | password | Adam4      | Małysz    | Warszawa | Mazowiecka 2/2 | 80-665  | 500-600-700 | 1      |
      | 5  | user5    | user5@user.de | password | Adam5      | Małysz    | Warszawa | Mazowiecka 2/2 | 80-665  | 500-600-700 | 1      |
      | 6  | user6    | user6@user.de | password | Adam6      | Małysz    | Warszawa | Mazowiecka 2/2 | 80-665  | 500-600-700 | 1      |