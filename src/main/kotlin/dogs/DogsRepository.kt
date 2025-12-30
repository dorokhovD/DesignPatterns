package dogs

import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import java.io.File
import kotlin.collections.maxOf

class DogsRepository private constructor() {

    private val file = File("dogs.json")

    private val dogsList: MutableList<Dog> = loadAllDogs()

//    private val _observers = mutableListOf<Observer<List<Dog>>>()
//    override val observers
//        get() = _observers.toList()
    private val _dogs = MutableObservable(dogsList.toList())
    val dogs: Observable<List<Dog>>
        get() = _dogs

    private fun loadAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())

    fun addDog(breed: String, name: String, weight: Double) {
        Thread.sleep(5_000)
        val id = dogsList.maxOf { it.id } + 1
        val dog = Dog(id, breed, name, weight)
        dogsList.add(dog)
        _dogs.currentValue = dogsList.toList()
    }

    fun deleteDog(id: Int) {
        Thread.sleep(5_000)
        dogsList.removeIf { it.id == id }
        _dogs.currentValue = dogsList.toList()
    }

    fun saveChanges() {
        val content = Json.encodeToString(dogsList)
        file.writeText(content)
    }

    companion object {

        private val lock = Any()
        private var instance: DogsRepository? = null

        fun getInstance(password: String): DogsRepository {
            val correctPassword = File("password_dogs.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                return DogsRepository().also {
                    instance = it
                }
            }
        }
    }
}