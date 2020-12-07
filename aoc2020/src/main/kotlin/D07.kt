import utils.IoHelper

class Day07 {
    private fun getInputs() = IoHelper.getLines("d07.in")

    fun getSolution1(): Int {
        val listOfBags = getInputs().map { generateBagSpecification(it) }
        val resultBags = arrayListOf<String>()
        var keys = arrayListOf("shinygold")
        while (keys.size > 0) {
            val newKeys = arrayListOf<String>()
            for (key in keys) {
                for (bag in listOfBags) {
                    if (bag.contains.any { it.second == key }) {
                        resultBags.add(bag.bag)
                        newKeys.add(bag.bag)
                    }
                }
            }
            keys = newKeys
        }
        return resultBags.distinct().size
    }

    fun getSolution2(): Int {
        val listOfBags = getInputs().map { generateBagSpecification(it) }
        val resultBags = arrayListOf(1 to "shinygold")
        var keys = arrayListOf(1 to "shinygold")
        while (keys.size > 0) {
            val newKeys = arrayListOf<Pair<Int, String>>()
            for (key in keys) {
                for (bag in listOfBags) {
                    if (bag.bag == key.second) {
                        repeat(key.first) {
                            resultBags.addAll(bag.contains)
                            newKeys.addAll(bag.contains)
                        }

                    }
                }
            }
            keys = newKeys
        }
        return resultBags.sumBy { it.first } - 1
    }

    private fun generateBagSpecification(line: String): BagSpecification {
        val bag = line.split(" ")[0] + line.split(" ")[1]
        val listOfBags =
            line.split("contain")[1].trim().split(",")
                .map { it.getBagNumber() to it.trim().split(" ")[1] + it.trim().split(" ")[2] }
        return BagSpecification(bag, listOfBags)
    }

    private fun String.getBagNumber() = this.trim().split(" ")[0].toIntOrNull() ?: 0
}

data class BagSpecification(val bag: String, val contains: List<Pair<Int, String>>)

fun main() {
    println(Day07().getSolution1())
    println(Day07().getSolution2())
}