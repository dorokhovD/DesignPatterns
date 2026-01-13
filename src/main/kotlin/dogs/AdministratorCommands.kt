package dogs

import command.Command

sealed interface AdministratorCommands: Command {

    data class AddDog(
        val repository: DogsRepository,
        val breed: String,
        val name: String,
        val weight: Double
    ): AdministratorCommands {
        override fun execute() {
            repository.addDog(breed, name, weight)
        }
    }

    data class DeleteDog(
        val repository: DogsRepository,
        val id: Int
    ) : AdministratorCommands {
        override fun execute() {
            repository.deleteDog(id)
        }
    }

    data class SaveChanges(
        val repository: DogsRepository
    ) : AdministratorCommands {
        override fun execute() {
            repository.saveChanges()
        }
    }
}