import utils.IoHelper

class Day11 {
    private val inputs = IoHelper.getLines("d11.in")

    fun getSolution1(): Int {
        println(inputs)
        val mapOfLocation = mutableMapOf<Pair<Int, Int>, String>()
        inputs.withIndex().forEach { lineWithIndex ->
            lineWithIndex.value.withIndex()
                .map { charWithIndex ->
                    mapOfLocation[(charWithIndex.index to lineWithIndex.index)] = charWithIndex.value.toString()
                }
        }
        println(mapOfLocation)

        var seatChanged = true
        var currentResultMap = mapOfLocation.toMutableMap()
        while (seatChanged) {
            seatChanged = false
            val resultMap = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "L" && adjacentSeatsAllNotOccupied(location.key, currentResultMap)) {
                    resultMap[location.key] = "#"
                    seatChanged = true
                }
            }
            println(resultMap)
            currentResultMap = resultMap
            val resultMap2 = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "#" && fourOrMoreAdjacentSeatsOccupied(location.key, currentResultMap)) {
                    resultMap2[location.key] = "L"
                    seatChanged = true
                }
            }
            println(resultMap2)
            currentResultMap = resultMap2
        }
        return currentResultMap.count { it.value == "#" }
    }

    private fun adjacentSeatsAllNotOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        val seatX = location.first
        val seatY = location.second
        if (mapOfLocation[seatX - 1 to seatY - 1] == "#") return false
        if (mapOfLocation[seatX to seatY - 1] == "#") return false
        if (mapOfLocation[seatX + 1 to seatY - 1] == "#") return false

        if (mapOfLocation[seatX - 1 to seatY] == "#") return false
        if (mapOfLocation[seatX + 1 to seatY] == "#") return false

        if (mapOfLocation[seatX - 1 to seatY + 1] == "#") return false
        if (mapOfLocation[seatX to seatY + 1] == "#") return false
        if (mapOfLocation[seatX + 1 to seatY + 1] == "#") return false

        return true
    }

    private fun fourOrMoreAdjacentSeatsOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        val seatX = location.first
        val seatY = location.second
        var seatOccupiedCount = 0

        if (mapOfLocation[seatX - 1 to seatY - 1] == "#") seatOccupiedCount += 1
        if (mapOfLocation[seatX to seatY - 1] == "#") seatOccupiedCount += 1
        if (mapOfLocation[seatX + 1 to seatY - 1] == "#") seatOccupiedCount += 1

        if (mapOfLocation[seatX - 1 to seatY] == "#") seatOccupiedCount += 1
        if (mapOfLocation[seatX + 1 to seatY] == "#") seatOccupiedCount += 1

        if (mapOfLocation[seatX - 1 to seatY + 1] == "#") seatOccupiedCount += 1
        if (mapOfLocation[seatX to seatY + 1] == "#") seatOccupiedCount += 1
        if (mapOfLocation[seatX + 1 to seatY + 1] == "#") seatOccupiedCount += 1

        return seatOccupiedCount >= 4
    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day11().getSolution1())
    println(Day11().getSolution2())
}