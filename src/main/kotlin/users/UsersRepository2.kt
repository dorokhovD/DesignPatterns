package users

import kotlinx.serialization.json.Json
import observer.Observer
import java.io.File

class UsersRepository2 private constructor() {

    private val file = File("users.json")

    private val observers = mutableListOf<(List<User>) -> Unit>()

    private val _users: MutableList<User> = loadAllUsers()
    val users
        get() = _users.toList()

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    private fun notifyObservers() {
        for (observer in observers) {
            observer(users)
        }
    }

    fun registerObserver(observer: (List<User>) -> Unit) {
        observers.add(observer)
        observer(users)

    }

    fun addUser(firstName: String, lastName: String, age: Int) {
        val id = users.maxOf { it.id } + 1
        val user = User(id, firstName, lastName, age)
        _users.add(user)
        notifyObservers()
    }

    fun deleteUser(id: Int) {
        _users.removeIf { it.id == id }
        notifyObservers()
    }

    fun saveChanges() {
        val content = Json.encodeToString(_users)
        file.writeText(content)
    }

    companion object {

        private val lock = Any()
        private var instance: UsersRepository2? = null

        fun getInstance(password: String): UsersRepository2 {
            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }

                return UsersRepository2().also {
                    instance = it
                }
            }

        }
    }
}