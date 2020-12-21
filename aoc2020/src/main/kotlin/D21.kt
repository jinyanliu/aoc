import utils.IoHelper

class Day21 {
    private val inputs = IoHelper.getLines("d21.in")

    fun getSolution1(): Int {
        var inputsPairList = inputs.map {
            val parts = it.dropLast(1).split(" (contains ")
            val ingredientsList = parts[0].split(" ").toMutableList()
            val allergensList = parts[1].split(", ").toMutableList()
            ingredientsList to allergensList
        }
        println(inputsPairList)
        val allAllergens = inputsPairList.flatMap { it.second }.toSet()
        println(allAllergens)

        for (allergen in allAllergens) {
            val currentAllergenList = inputsPairList.filter { it.second.contains(allergen) }
            println("currentAllergenList=" + currentAllergenList)

            val ingredientsList = currentAllergenList.map { it.first }
            println("ingredientsList=" + ingredientsList)

            var currentIntersect = ingredientsList[0].toSet()

            for (i in 0..ingredientsList.size - 1) {
                currentIntersect = ingredientsList[i].intersect(currentIntersect)
            }

            println("currentIntersect=" + currentIntersect)

            inputsPairList = inputsPairList.map {
                val ingredientsList = it.first
                ingredientsList.removeAll(currentIntersect.toMutableList())
                val allergenList = it.second
                allergenList.remove(allergen)
                ingredientsList to allergenList
            }



            println()
            println()
            println()
        }

        return inputsPairList.map { it.first.count() }.sum()
    }

    fun getSolution2():String {
        var inputsPairList = inputs.map {
            val parts = it.dropLast(1).split(" (contains ")
            val ingredientsList = parts[0].split(" ").toMutableList()
            val allergensList = parts[1].split(", ").toMutableList()
            ingredientsList to allergensList
        }
        println(inputsPairList)

        val result = arrayListOf<Pair<String, String>>()

        while (inputsPairList.flatMap { it.second }.isNotEmpty()) {

            val allAllergens = inputsPairList.flatMap { it.second }.toSet()
            println(allAllergens)

            for (allergen in allAllergens) {
                val currentAllergenList = inputsPairList.filter { it.second.contains(allergen) }
                println("currentAllergenList=" + currentAllergenList)

                val ingredientsList = currentAllergenList.map { it.first }
                println("ingredientsList=" + ingredientsList)

                var currentIntersect = ingredientsList[0].toSet()

                for (i in 0..ingredientsList.size - 1) {
                    currentIntersect = ingredientsList[i].intersect(currentIntersect)
                }

                println("currentIntersect=" + currentIntersect)

                if (currentIntersect.size == 1) {
                    inputsPairList = inputsPairList.map {
                        val ingredientsList = it.first
                        ingredientsList.removeAll(currentIntersect.toMutableList())
                        val allergenList = it.second
                        allergenList.remove(allergen)
                        ingredientsList to allergenList
                    }

                    result.add(currentIntersect.toMutableList()[0] to allergen)
                }
            }
        }

        result.sortBy { it.second }

        println(inputsPairList.map { it.first.count() }.sum())

        return result.map { it.first }.joinToString(",")

    }
}

fun main() {
    // 2203
    println(Day21().getSolution1())
    // fqfm,kxjttzg,ldm,mnzbc,zjmdst,ndvrq,fkjmz,kjkrm
    println(Day21().getSolution2())
}