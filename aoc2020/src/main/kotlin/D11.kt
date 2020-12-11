import utils.IoHelper

class Day11 {

    enum class XDirection {
        RIGHT, LEFT
    }

    enum class YDirection {
        UP, DOWN
    }

    private val inputs = IoHelper.getLines("d11.in")

    fun getSolution1(): Int {
        val mapOfLocation = mutableMapOf<Pair<Int, Int>, String>()
        inputs.withIndex().forEach { lineWithIndex ->
            lineWithIndex.value.withIndex()
                .map { charWithIndex ->
                    mapOfLocation[(charWithIndex.index to lineWithIndex.index)] = charWithIndex.value.toString()
                }
        }

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
            currentResultMap = resultMap
            val resultMap2 = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "#" && fourOrMoreAdjacentSeatsOccupied(location.key, currentResultMap)) {
                    resultMap2[location.key] = "L"
                    seatChanged = true
                }
            }
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
        if (mapOfLocation[updateCurrentLocation(
                location,
                moveX = XDirection.LEFT,
                moveY = YDirection.UP
            )] == "#"
        ) return false
        if (mapOfLocation[updateCurrentLocation(location, moveY = YDirection.UP)] == "#") return false
        if (mapOfLocation[updateCurrentLocation(
                location,
                moveX = XDirection.RIGHT,
                moveY = YDirection.UP
            )] == "#"
        ) return false

        if (mapOfLocation[updateCurrentLocation(location, moveX = XDirection.LEFT)] == "#") return false
        if (mapOfLocation[updateCurrentLocation(location, moveX = XDirection.RIGHT)] == "#") return false

        if (mapOfLocation[updateCurrentLocation(
                location,
                moveX = XDirection.LEFT,
                moveY = YDirection.DOWN
            )] == "#"
        ) return false
        if (mapOfLocation[updateCurrentLocation(location, moveY = YDirection.DOWN)] == "#") return false
        if (mapOfLocation[updateCurrentLocation(
                location,
                moveX = XDirection.RIGHT,
                moveY = YDirection.DOWN
            )] == "#"
        ) return false

        return true
    }

    private fun adjacentDirectionSeatsAllNotOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        if (hasOccupiedSeat(
                location,
                moveX = XDirection.LEFT,
                moveY = YDirection.UP,
                mapOfLocation = mapOfLocation
            )
        ) return false
        if (hasOccupiedSeat(location, moveY = YDirection.UP, mapOfLocation = mapOfLocation)) return false
        if (hasOccupiedSeat(
                location,
                moveX = XDirection.RIGHT,
                moveY = YDirection.UP,
                mapOfLocation = mapOfLocation
            )
        ) return false

        if (hasOccupiedSeat(location, moveX = XDirection.LEFT, mapOfLocation = mapOfLocation)) return false
        if (hasOccupiedSeat(location, moveX = XDirection.RIGHT, mapOfLocation = mapOfLocation)) return false

        if (hasOccupiedSeat(
                location,
                moveX = XDirection.LEFT,
                moveY = YDirection.DOWN,
                mapOfLocation = mapOfLocation
            )
        ) return false
        if (hasOccupiedSeat(location, moveY = YDirection.DOWN, mapOfLocation = mapOfLocation)) return false
        if (hasOccupiedSeat(
                location,
                moveX = XDirection.RIGHT,
                moveY = YDirection.DOWN,
                mapOfLocation = mapOfLocation
            )
        ) return false

        return true
    }

    private fun hasOccupiedSeat(
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

    private fun updateCurrentLocation(
        currentSeatLocation: Pair<Int, Int>,
        moveX: XDirection? = null,
        moveY: YDirection? = null
    ): Pair<Int, Int> {
        var currentSeatLocation1 = currentSeatLocation
        moveX?.let {
            currentSeatLocation1 = when (moveX) {
                XDirection.LEFT -> (currentSeatLocation1.first - 1) to currentSeatLocation1.second
                XDirection.RIGHT -> (currentSeatLocation1.first + 1) to currentSeatLocation1.second
            }
        }
        moveY?.let {
            currentSeatLocation1 = when (moveY) {
                YDirection.UP -> (currentSeatLocation1.first) to currentSeatLocation1.second - 1
                YDirection.DOWN -> (currentSeatLocation1.first) to currentSeatLocation1.second + 1
            }
        }
        return currentSeatLocation1
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

    private fun fiveOrMoreAdjacentDirectionSeatsOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var seatOccupiedCount = 0

        if (hasOccupiedSeat(
                location,
                moveX = XDirection.LEFT,
                moveY = YDirection.UP,
                mapOfLocation = mapOfLocation
            )
        ) seatOccupiedCount += 1
        if (hasOccupiedSeat(location, moveY = YDirection.UP, mapOfLocation = mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeat(
                location,
                moveX = XDirection.RIGHT,
                moveY = YDirection.UP,
                mapOfLocation = mapOfLocation
            )
        ) seatOccupiedCount += 1

        if (hasOccupiedSeat(location, moveX = XDirection.LEFT, mapOfLocation = mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeat(location, moveX = XDirection.RIGHT, mapOfLocation = mapOfLocation)) seatOccupiedCount += 1

        if (hasOccupiedSeat(
                location,
                moveX = XDirection.LEFT,
                moveY = YDirection.DOWN,
                mapOfLocation = mapOfLocation
            )
        ) seatOccupiedCount += 1
        if (hasOccupiedSeat(location, moveY = YDirection.DOWN, mapOfLocation = mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeat(
                location,
                moveX = XDirection.RIGHT,
                moveY = YDirection.DOWN,
                mapOfLocation = mapOfLocation
            )
        ) seatOccupiedCount += 1

        return seatOccupiedCount >= 5
    }

    fun getSolution2(): Int {
        val mapOfLocation = mutableMapOf<Pair<Int, Int>, String>()
        inputs.withIndex().forEach { lineWithIndex ->
            lineWithIndex.value.withIndex()
                .map { charWithIndex ->
                    mapOfLocation[(charWithIndex.index to lineWithIndex.index)] = charWithIndex.value.toString()
                }
        }

        var seatChanged = true
        var currentResultMap = mapOfLocation.toMutableMap()
        while (seatChanged) {
            seatChanged = false

            val resultMap = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "L" && adjacentDirectionSeatsAllNotOccupied(location.key, currentResultMap)) {
                    resultMap[location.key] = "#"
                    seatChanged = true
                }
            }
            currentResultMap = resultMap

            val resultMap2 = currentResultMap.toMutableMap()
            for (location in currentResultMap) {
                if (location.value == "#" && fiveOrMoreAdjacentDirectionSeatsOccupied(location.key, currentResultMap)) {
                    resultMap2[location.key] = "L"
                    seatChanged = true
                }
            }
            currentResultMap = resultMap2
        }
        return currentResultMap.count { it.value == "#" }
    }
}

fun main() {
    //2152
    println(Day11().getSolution1())
    //1937
    println(Day11().getSolution2())
}