package stepik

import kotlin.concurrent.thread

fun main() {

    val mapOfThreads = mutableMapOf<String, String>()
    mapOfThreads[Thread.currentThread().name] = "Главный поток, который управляет выполнением"
    val threadFirst = thread {
        mapOfThreads[Thread.currentThread().name] = "Выполняет вычисления 1"
    }


    val threadSecond = thread {
        mapOfThreads[Thread.currentThread().name] = "Выполняет вычисления 2"

    }

    val threadThird = thread {
        mapOfThreads[Thread.currentThread().name] = "Выполняет вычисления 3"
    }
}

class ThreadRunner {
    fun runThreads(): Map<String, String> {
        val threadInfo = mutableMapOf<String, String>()
        // Добавьте в Map имя главного потока и описание его работы
        threadInfo[Thread.currentThread().name] = "Главный поток, который управляет выполнением"
        // Запустите три потока, добавляя в Map имя потока и описание его работы
        val threadFirst = thread {
            threadInfo[Thread.currentThread().name] = "Выполняет вычисления 1"
        }
        threadFirst.join()
        val threadSecond = thread {
            threadInfo[Thread.currentThread().name] = "Выполняет вычисления 2"
        }
        threadSecond.join()
        val threadThird = thread {
            threadInfo[Thread.currentThread().name] = "Выполняет вычисления 3"
        }
        threadThird.join()
        // Дождитесь завершения потоков, чтобы они успели записать свои имена в Map

        return threadInfo
    }
}