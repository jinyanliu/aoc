import utils.IoHelper

class Day21 {
    private val inputs = IoHelper.getLines("d21.in")

    var inputsPairList = inputs.map {
        val parts = it.dropLast(1).split(" (contains ")
        val ingredientsList = parts[0].split(" ").toMutableList()
        val allergensList = parts[1].split(", ").toMutableList()
        ingredientsList to allergensList
    }

    // ingredient to allergen
    private val allergicIngredientsResult = arrayListOf<Pair<String, String>>()

    fun getSolution1(): Int {
        findAllergicIngredient()
        return inputsPairList.map { it.first.count() }.sum()
    }

    fun getSolution2(): String {
        findAllergicIngredient()
        allergicIngredientsResult.sortBy { it.second }
        return allergicIngredientsResult.joinToString(",") { it.first }
    }

    private fun findAllergicIngredient() {
        while (inputsPairList.flatMap { it.second }.isNotEmpty()) {
            val allAllergens = inputsPairList.flatMap { it.second }.toSet()
            for (allergen in allAllergens) {
                val currentAllergenList = inputsPairList.filter { it.second.contains(allergen) }
                val currentAllergenIngredientsList = currentAllergenList.map { it.first }
                var currentIntersect = currentAllergenIngredientsList[0].toSet()
                for (element in currentAllergenIngredientsList) {
                    currentIntersect = element.intersect(currentIntersect)
                }
                if (currentIntersect.size == 1) {
                    inputsPairList = inputsPairList.map {
                        val ingredientsList = it.first
                        ingredientsList.removeAll(currentIntersect.toMutableList())
                        val allergenList = it.second
                        allergenList.remove(allergen)
                        ingredientsList to allergenList
                    }
                    allergicIngredientsResult.add(currentIntersect.toMutableList()[0] to allergen)
                }
            }
        }
    }
}

fun main() {
    // 2203
    println(Day21().getSolution1())
    // fqfm,kxjttzg,ldm,mnzbc,zjmdst,ndvrq,fkjmz,kjkrm
    println(Day21().getSolution2())
}