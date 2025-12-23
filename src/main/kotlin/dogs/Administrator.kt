package dogs

import dogs.Operation
import users.UsersRepository

class Administrator {

    private val repository = DogsRepository.Companion.getInstance("qwerty")

    fun work() {
        while (true) {
            println("Enter an operation: ")
            val operations = dogs.Operation.entries
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
                dogs.Operation.EXIT -> {
                    repository.saveChanges()
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
        repository.addDog(breed = breedName, name = dogName, weight = weight)

    }
    private fun deleteDog() {
        print("Enter id: ")
        val id = readln().toInt()
        repository.deleteDog(id = id)
    }
}