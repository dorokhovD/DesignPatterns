package builder

fun main() {
    val drink = Drink.Builder()
        .type("Tea")
        .temperature("Cold")
        .build()
    print(drink)
}