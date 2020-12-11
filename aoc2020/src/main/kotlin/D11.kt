import utils.IoHelper

class Day11 {
    enum class XDirection { RIGHT, LEFT }
    enum class YDirection { UP, DOWN }

    private val inputs = IoHelper.getLines("d11.in")

    private fun mapOfLocation(): Map<Pair<Int, Int>, String> {
        val mapOfLocation = mutableMapOf<Pair<Int, Int>, String>()
        inputs.withIndex().forEach { lineWithIndex ->
            lineWithIndex.value.withIndex()
                .map { charWithIndex ->
                    mapOfLocation[(charWithIndex.index to lineWithIndex.index)] = charWithIndex.value.toString()
                }
        }
        return mapOfLocation
    }

    private val eightAdjacentDirection = arrayListOf(
        XDirection.LEFT to YDirection.UP,
        null to YDirection.UP,
        XDirection.RIGHT to YDirection.UP,
        XDirection.LEFT to null,
        XDirection.RIGHT to null,
        XDirection.LEFT to YDirection.DOWN,
        null to YDirection.DOWN,
        XDirection.RIGHT to YDirection.DOWN
    )

    private fun eightAdjacentSeats(location: Pair<Int, Int>) =
        eightAdjacentDirection.map { updateCurrentLocation(location, moveX = it.first, moveY = it.second) }

    private fun eightAdjacentSeatsAllNotOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ) = eightAdjacentSeats(location).all { mapOfLocation[it] != "#" }

    private fun fourOrMoreAdjacentSeatsOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ) = eightAdjacentSeats(location).count { mapOfLocation[it] == "#" } >= 4

    private fun adjacentDirectionSeatsAllNotOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ) = eightAdjacentDirection.map { hasOccupiedSeatInDirection(location, it.first, it.second, mapOfLocation) }
        .all { !it }

    private fun fiveOrMoreAdjacentDirectionSeatsOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ) = eightAdjacentDirection.count { hasOccupiedSeatInDirection(location, it.first, it.second, mapOfLocation) } >= 5

    private fun updateCurrentLocation(
        location: Pair<Int, Int>,
        moveX: XDirection? = null,
        moveY: YDirection? = null
    ): Pair<Int, Int> {
        var currentSeatLocation = location
        moveX?.let {
            currentSeatLocation = when (moveX) {
                XDirection.LEFT -> (currentSeatLocation.first - 1) to currentSeatLocation.second
                XDirection.RIGHT -> (currentSeatLocation.first + 1) to currentSeatLocation.second
            }
        }
        moveY?.let {
            currentSeatLocation = when (moveY) {
                YDirection.UP -> (currentSeatLocation.first) to currentSeatLocation.second - 1
                YDirection.DOWN -> (currentSeatLocation.first) to currentSeatLocation.second + 1
            }
        }
        return currentSeatLocation
    }

    private fun hasOccupiedSeatInDirection(
        seatLocation: Pair<Int, Int>,
        moveX: XDirection? = null,
        moveY: YDirection? = null,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentSeatLocation = seatLocation
        while (mapOfLocation[currentSeatLocation] != null) {
            currentSeatLocation = updateCurrentLocation(currentSeatLocation, moveX, moveY)
            if (mapOfLocation[currentSeatLocation] == "L") {
                return false
            }
            if (mapOfLocation[currentSeatLocation] == "#") {
                return true
            }
        }
        return false
    }

    fun getSolution(
        calOne: (location: Pair<Int, Int>, map: Map<Pair<Int, Int>, String>) -> Boolean,
        calTwo: (location: Pair<Int, Int>, map: Map<Pair<Int, Int>, String>) -> Boolean
    ): Int {

        var seatChanged = true
        var currentResultMap = mapOfLocation()
        while (seatChanged) {
            seatChanged = false
            val resultMap = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "L" && calOne(location.key, currentResultMap)) {
                    resultMap[location.key] = "#"
                    seatChanged = true
                }
            }
            currentResultMap = resultMap
            val resultMap2 = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "#" && calTwo(location.key, currentResultMap)) {
                    resultMap2[location.key] = "L"
                    seatChanged = true
                }
            }
            currentResultMap = resultMap2
        }
        return currentResultMap.count { it.value == "#" }

    }

    fun getSolution1() = getSolution(calOne = { location, map -> eightAdjacentSeatsAllNotOccupied(location, map) },
        calTwo = { location, map -> fourOrMoreAdjacentSeatsOccupied(location, map) })

    fun getSolution2() = getSolution(calOne = { location, map -> adjacentDirectionSeatsAllNotOccupied(location, map) },
        calTwo = { location, map -> fiveOrMoreAdjacentDirectionSeatsOccupied(location, map) })
}

fun main() {
    //2152
    println(Day11().getSolution1())
    //1937
    println(Day11().getSolution2())
}