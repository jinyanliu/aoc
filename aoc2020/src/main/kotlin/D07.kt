import utils.IoHelper

class Day07 {
    private fun getInputs() = IoHelper.getLines("d07.in")

    private val mapOfBags = getInputs().map { generateBagSpecification(it) }.toMap()

    fun getSolution1(): Int {
        val resultBags = arrayListOf<String>()
        var keys = arrayListOf("shinygold")
        while (keys.size > 0) {
            val newKeys = arrayListOf<String>()
            for (key in keys) {
                for (bag in mapOfBags) {
                    if (bag.value.any { it.second == key }) {
                        resultBags.add(bag.key)
                        newKeys.add(bag.key)
                    }
                }
            }
            keys = newKeys
        }
        return resultBags.distinct().size
    }

    fun getSolution2(): Int {
        val resultBags = arrayListOf<Pair<Int, String>>()
        var keys = arrayListOf(1 to "shinygold")
        while (keys.size > 0) {
            val newKeys = arrayListOf<Pair<Int, String>>()
            for (key in keys) {
                repeat(key.first) {
                    resultBags.addAll(mapOfBags[key.second].orEmpty())
                    newKeys.addAll(mapOfBags[key.second].orEmpty())
                }
            }
            keys = newKeys
        }
        return resultBags.sumBy { it.first }
    }

    private fun generateBagSpecification(line: String): Pair<String, List<Pair<Int, String>>> {
        val bag = line.split(" ")[0] + line.split(" ")[1]
        val listOfBags =
            line.split("contain")[1].trim().split(",")
                .map { it.getBagNumber() to it.trim().split(" ")[1] + it.trim().split(" ")[2] }
        return bag to listOfBags
    }

    private fun String.getBagNumber() = this.trim().split(" ")[0].toIntOrNull() ?: 0
}

fun main() {
    println(Day07().getSolution1())
    println(Day07().getSolution2())
}