import utils.IoHelper

class Day16 {
    private val inputs = IoHelper.getSections("d16.in")
    private val section1 = inputs[0]
    private val flatRules = section1.lines().map { it.split(": ")[1] }.flatMap { it.split(" or ") }
        .map { it.split("-")[0].toInt()..it.split("-")[1].toInt() }
    private val rules = section1.lines().map { it.split(": ")[1] }
        .map { ruleValue -> ruleValue.split(" or ").map { it.split("-")[0].toInt()..it.split("-")[1].toInt() } }

    fun getSolution1(): Int {
        return getInvalidInts().sum()
    }

    private fun getInvalidInts(): ArrayList<Int> {
        val nearbyInts = inputs[2].lines().drop(1).flatMap { it.split(",") }.map { it.toInt() }
        val invalidIntList = arrayListOf<Int>()
        nearbyInts.forEach { int ->
            if (!flatRules.any { singleRule -> int in singleRule }) {
                invalidIntList.add(int)
            }
        }
        return invalidIntList
    }

    fun getSolution2(): Long {
        val invalidInts = getInvalidInts()
        val nearbyTickets = inputs[2]
        val validNearbyTickets = nearbyTickets.lines().drop(1)
            .map { nearbyTicket ->
                nearbyTicket.split(",")
                    .map { it.toInt() }
            }
            .filter { it.all { it !in invalidInts } }
        val indexMap = mutableMapOf<Int, ArrayList<Int>>()
        for (i in rules.indices) {
            indexMap[i] = arrayListOf()
        }

        validNearbyTickets.forEach { ints ->
            ints.withIndex().forEach { indexMap[it.index]?.add(it.value) }
        }

        val leftOverRules = rules.toMutableList()
        while (leftOverRules.size > 0) {
            indexMap.forEach { mapElement ->
                var newRules = leftOverRules
                mapElement.value.forEach { element ->
                    newRules = newRules.filter { element in it[0] || element in it[1] }.toMutableList()
                }
                if (newRules.size == 1) {
                    leftOverRules.remove(newRules[0])
                }
            }
        }

        val myTicket = inputs.get(1).lines().get(1).split(",").map { it.toInt() }
        return myTicket.withIndex().filter { it.index in arrayListOf(16, 2, 1, 5, 17, 13) }.map { it.value.toLong() }
            .reduce { a, b -> a * b }
    }
}

fun main() {
    //25961
    println(Day16().getSolution1())
    //603409823791
    println(Day16().getSolution2())
}