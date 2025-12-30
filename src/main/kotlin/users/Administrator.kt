package users

class Administrator {

    private val repository = UsersRepository.getInstance("qwerty")

    fun work() {
        while (true) {
            println("Enter an operation: ")
            val operations = Operation.entries
            for ((index, operation) in operations.withIndex()) {
                print("$index - ${operation.title}")
                if (index == operations.lastIndex) {
                    print(": ")
                } else {
                    print(", ")
                }
            }
            val operationsIndex = readln().toInt()
            val operation = operations[operationsIndex]

            when (operation) {
                Operation.EXIT -> {
                    repository.saveChanges()
                    break
                }
                Operation.ADD_USER -> addUser()
                Operation.DELETE_USER -> deleteUser()
            }
        }
    }
    private fun addUser() {

        print("Enter firstname: ")
        val firstName = readln()
        print("Enter lastname: ")
        val lastName = readln()
        print("Enter age: ")
        val age = readln().toInt()
        UsersInvoker.addCommand {
            repository.addUser(firstName = firstName, lastName = lastName, age = age)
        }
    }
    private fun deleteUser() {

        print("Enter id: ")
        val id = readln().toInt()
        UsersInvoker.addCommand {
            repository.deleteUser(id = id)
        }

    }
}