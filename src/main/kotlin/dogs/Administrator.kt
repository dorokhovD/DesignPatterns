package dogs

import dogs.Operation

class Administrator {

    private val repository = DogsRepository.Companion.getInstance("qwerty")

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
                    DogsInvoker.addCommand(AdministratorCommands.SaveChanges(repository))
                    break
                }
                Operation.ADD_DOG -> addDog()
                Operation.DELETE_DOG -> deleteDog()
            }
        }
    }
    private fun addDog() {

        print("Enter breed: ")
        val breedName = readln()
        print("Enter name: ")
        val dogName = readln()
        print("Enter weight: ")
        val weight = readln().toDouble()
        DogsInvoker.addCommand(AdministratorCommands.AddDog(
            repository = repository,
            breed = breedName,
            name = dogName,
            weight = weight,
        ))


    }
    private fun deleteDog() {
        print("Enter id: ")
        val id = readln().toInt()
        DogsInvoker.addCommand(AdministratorCommands.DeleteDog(
            repository = repository,
            id = id
        ))
    }
}