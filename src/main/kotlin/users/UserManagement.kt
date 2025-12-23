package users


fun main() {
    // получение коллекции сотрудников и вызов функции высшего порядка для вывода каждого элемента коллекции в консоль
    //UsersRepository.getInstance("qwerty").users.forEach(::println)
    Display().show()
    Administrator().work()
}