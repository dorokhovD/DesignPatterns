package stepik

import kotlin.math.log

// Хранилище пользователей
class UserRepository {
    private val users = mutableListOf<String>()

    // TODO: Добавить список подписчиков (наблюдателей) UserLogger
    private val subscribers = mutableListOf<UserLogger>()

    fun addUser(user: String) {
        users.add(user)
        // TODO: уведомить подписчиков об изменении списка пользователей
        notifySubscribers()

    }

    fun removeUser(user: String) {
        users.remove(user)
        // TODO: уведомить подписчиков об изменении списка пользователей
        notifySubscribers()

    }


    // TODO: Реализовать метод подписки
    fun subscribe(logger: UserLogger) {
        subscribers.add(logger)
        logger.onUsersChanged(users)
    }
    // TODO: Реализовать метод отписки
    fun unsubscribe(logger: UserLogger) {
        subscribers.remove(logger)
    }

    // TODO: Реализовать метод уведомления подписчиков
    private fun notifySubscribers() {
        for (subscriber in subscribers) {
            subscriber.onUsersChanged(users)
        }
    }
}

// Класс наблюдателя, который подписывается на изменения в UserRepository
class UserLogger {
    // TODO: Реализовать метод onUsersChanged(), который выводит сообщение в консоль в формате:
    // [LOG] Пользователи обновлены: <список пользователей>
    fun onUsersChanged(users: List<String>) {
        println("[LOG] Пользователи обновлены: $users")
    }

}

fun main() {
    val user = UserRepository()

    val logger = UserLogger()
    val logger2 = UserLogger()
    user.subscribe(logger)
    user.subscribe(logger2)

    user.addUser("Dima")
    user.addUser("Max")
    user.removeUser("Dima")
    user.addUser("Misha")
    user.addUser("Pavel")
    user.removeUser("Misha")
    user.unsubscribe(logger2)
    user.addUser("Dima")
}