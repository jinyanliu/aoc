import utils.IoHelper

class Day16 {
    private val inputs = IoHelper.getSections("d16Test2.in")

    fun getSolution1(): Int {
        return getInvalidInts().sum()
    }

    private fun getInvalidInts(): ArrayList<Int> {
        val classes = inputs.get(0)
        val rules = classes.lines().map { it.split(": ").get(1) }.flatMap { it.split(" or ") }
            .map { it.split("-").get(0).toInt()..it.split("-").get(1).toInt() }

        val nearbyTickets = inputs.get(2)
        val nearbyInts = nearbyTickets.lines().drop(1).flatMap { it.split(",") }.map { it.toInt() }

        val invalidIntList = arrayListOf<Int>()
        nearbyInts.forEach { int ->
            if (!rules.any { rule -> int in rule }) {
                invalidIntList.add(int)
            }
        }
        return invalidIntList
    }

    fun getSolution2() {

        val rules = inputs.get(0).lines().map { it.split(": ").get(1) }.map { it.split(" or ") .map { it.split("-").get(0).toInt()..it.split("-").get(1).toInt() }}
        println(rules)
        println(rules.size)
        val invalidInts = getInvalidInts()
        val nearbyTickets = inputs.get(2)
        val validNearbyTickets = nearbyTickets.lines().drop(1).map { it.split(",").map { it.toInt() } }
            .filter { it.all { it !in invalidInts } }


        val indexMap = mutableMapOf<Int,ArrayList<Int>>()
        for(i in 0..rules.size-1){
            indexMap[i]= arrayListOf()
        }

        println(validNearbyTickets)
        validNearbyTickets.forEach { listofInts-> listofInts.withIndex().forEach { indexMap[it.index]?.add(it.value) } }
        println(indexMap)

        val leftOverRules = rules.toMutableList()
        indexMap.forEach { mapElement->
            var newRules = leftOverRules
            mapElement.value.forEach {element->
                newRules= newRules.filter { element in it[0] || element in it[1] }.toMutableList()
            }
            if(newRules.size==1){
                println("index="+mapElement.key+", rules fit="+ newRules)
                leftOverRules.remove(newRules[0])
            }
        }

    }
}

fun main() {
    println(Day16().getSolution1())
    println(Day16().getSolution2())
}