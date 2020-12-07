import utils.IoHelper

class Day07 {
    private fun getInputs() = IoHelper.getLines("d07.in")

    fun getSolution2() :Int{
        val listOfBags = getInputs().map { generateBagSpecification2(it) }
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

    fun getSolution1():Int {
        val listOfBags = getInputs().map { generateBagSpecification1(it) }
        val resultBags = arrayListOf<String>()
        var keys = arrayListOf("shinygold")
        while (keys.size > 0) {
            val newKeys = arrayListOf<String>()
            for (key in keys) {
                for (bag in listOfBags) {
                    if (bag.contains.contains(key)) {
                        resultBags.add(bag.bag)
                        newKeys.add(bag.bag)
                    }
                }
            }
            keys = newKeys
        }

        return resultBags.distinct().size


    }

    private fun generateBagSpecification1(line: String): BagSpecification1 {
        val bag = line.split(" ").get(0) + line.split(" ").get(1)
        val listOfBags =
            line.split("contain").get(1).trim().split(",").map { it.trim().split(" ")[1] + it.trim().split(" ")[2] }
        val BagSpecification = BagSpecification1(bag, listOfBags)
        return BagSpecification
    }

    private fun generateBagSpecification2(line: String): BagSpecification2 {
        val bag = line.split(" ").get(0) + line.split(" ").get(1)
        val listOfBags =
            line.split("contain").get(1).trim().split(",")
                .map { it.getBagNumber() to it.trim().split(" ")[1] + it.trim().split(" ")[2] }
        val BagSpecification = BagSpecification2(bag, listOfBags)
        return BagSpecification
    }

    fun String.getBagNumber() = this.trim().split(" ")[0].toIntOrNull() ?: 0



}

data class BagSpecification1(val bag: String, val contains: List<String>)
data class BagSpecification2(val bag: String, val contains: List<Pair<Int, String>>)

fun main() {
    println(Day07().getSolution1())
    println(Day07().getSolution2())
}