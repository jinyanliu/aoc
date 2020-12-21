import utils.IoHelper

class Day21 {
    private val inputs = IoHelper.getLines("d21Test.in")

    fun getSolution1() {
        val inputsPairList = inputs.map {
            val parts = it.dropLast(1).split(" (contains ")
            val ingredientsList = parts[0].split(" ").toMutableList()
            val allergensList = parts[1].split(", ").toMutableList()
            ingredientsList to allergensList
        }
        println(inputsPairList)
        val allAllergens = inputsPairList.flatMap { it.second }.toSet()
        println(allAllergens)

        for(allergen in allAllergens){
            val currentAllergenList = inputsPairList.filter { it.second.contains(allergen)}
            println("currentAllergenList"+currentAllergenList)

            val ingredientsList = currentAllergenList.map { it.first }
            println("ingredientsList"+ingredientsList)

            var currentIntersect = ingredientsList[0].toSet()

            for(i in 0..ingredientsList.size-1){
                currentIntersect= ingredientsList[i].intersect(currentIntersect)
            }

            println("currentIntersect"+currentIntersect)

            if(currentIntersect.size == 1){
                println(currentIntersect)
                println(allergen)
            }

            println()
            println()
            println()
        }
    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day21().getSolution1())
    println(Day21().getSolution2())
}