package stepik

import kotlin.concurrent.thread
import kotlin.math.round
import kotlin.random.Random

fun interface Observer<T> {
    fun onChanged(newValue: T)
}
interface Observable<T> {
    val currentValue: T
    val observers: List<Observer<T>>

    fun registerObserver(observer: Observer<T>)
    fun unregisterObserver(observer: Observer<T>)
    fun notifyObservers() {
        for (observer in observers) {
            observer.onChanged(currentValue)
        }
    }
}
class MutableObservable<T>(initialValue: T) : Observable<T> {

    override var currentValue: T = initialValue
        set(value) {
            field = value
            notifyObservers()
        }
    private val _observers = mutableListOf<Observer<T>>()
    override val observers: List<Observer<T>>
        get() = _observers.toList()

    override fun registerObserver(observer: Observer<T>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun unregisterObserver(observer: Observer<T>) {
        _observers.remove(observer)
    }
}
// Репозиторий данных
object DataRepository {

    val userData = MutableObservable("User_Initial")
    val orderData = MutableObservable(100)
    val priceData = MutableObservable(99.99)
    // Метод обновления данных
    fun updateData(newUser: String? = null, newOrder: Int? = null, newPrice: Double? = null) {
        newUser?.let { userData.currentValue = it }
        newOrder?.let { orderData.currentValue = it }
        newPrice?.let { priceData.currentValue = round(it * 100) / 100 }
    }
}

// Мониторинг данных с периодическим опросом
//class UserMonitor(private val repository: DataRepository) {
//    init {
//        thread {
//            var lastValue = repository.userData
//            while (true) {
//                println("UserMonitor: запрос к DataRepository")
//                if (repository.userData != lastValue) {
//                    println("UserMonitor: Обнаружено изменение данных пользователя: ${repository.userData}")
//                    lastValue = repository.userData
//                }
//                Thread.sleep(1000)
//            }
//        }
//    }
//}

//class OrderMonitor(private val repository: DataRepository) {
//    init {
//        thread {
//            var lastValue = repository.orderData
//            while (true) {
//                println("OrderMonitor: запрос к DataRepository")
//                if (repository.orderData != lastValue) {
//                    println("OrderMonitor: Обнаружено изменение данных заказа: ${repository.orderData}")
//                    lastValue = repository.orderData
//                }
//                Thread.sleep(1000)
//            }
//        }
//    }
//}

//class PriceMonitor(private val repository: DataRepository) {
//    init {
//        thread {
//            var lastValue = repository.priceData
//            while (true) {
//                println("PriceMonitor: запрос к DataRepository")
//                if (repository.priceData != lastValue) {
//                    println("PriceMonitor: Обнаружено изменение цены: ${repository.priceData}")
//                    lastValue = repository.priceData
//                }
//                Thread.sleep(1000)
//            }
//        }
//    }
//}

// Класс для автоматического обновления данных
class DataUpdater(private val repository: DataRepository) {
    init {
        thread {
            while (true) {
                when (Random.nextInt(3)) {
                    0 -> repository.updateData(newUser = "User_${Random.nextInt(1, 100)}")
                    1 -> repository.updateData(newOrder = Random.nextInt(100, 200))
                    2 -> repository.updateData(newPrice = Random.nextDouble(50.0, 150.0))
                }
                val millis = Random.nextLong(10000L)
                Thread.sleep(millis) // Симуляция времени между обновлениями
            }
        }
    }
}

fun main() {
    // Запуск обновления данных
    DataUpdater(DataRepository)

    // Подписка на обновления данных пользователя
    DataRepository.userData.registerObserver { newValue ->
        println("UserMonitor: Обнаружено изменение данных пользователя: $newValue")
    }

    // Подписка на обновления данных заказов
    DataRepository.orderData.registerObserver { newValue ->
        println("OrderMonitor: Обнаружено изменение данных заказа: $newValue")
    }

    // Подписка на обновления данных цены
    DataRepository.priceData.registerObserver { newValue ->
        println("PriceMonitor: Обнаружено изменение цены: $newValue")
    }

}