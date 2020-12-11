import utils.IoHelper

class Day11 {
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

    private fun adjacentDirectionSeatsAllNotOccupied(
        location: Pair<Int, Int>,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        val seatX = location.first
        val seatY = location.second

        if (hasOccupiedSeatLeftUpp(seatX, seatY, mapOfLocation)) return false
        if (hasOccupiedSeatUpp(seatX, seatY, mapOfLocation)) return false
        if (hasOccupiedSeatRightUpp(seatX, seatY, mapOfLocation)) return false

        if (hasOccupiedSeatLeft(seatX, seatY, mapOfLocation)) return false
        if (hasOccupiedSeatRight(seatX, seatY, mapOfLocation)) return false


        if (hasOccupiedSeatLeftDown(seatX, seatY, mapOfLocation)) return false
        if (hasOccupiedSeatDown(seatX, seatY, mapOfLocation)) return false
        if (hasOccupiedSeatRightDown(seatX, seatY, mapOfLocation)) return false

        return true
    }

    private fun hasOccupiedSeatLeftDown(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX - 1 to currentY + 1] == "L") {
                return false
            }
            if (mapOfLocation[currentX - 1 to currentY + 1] == "#") {
                return true
            }
            currentX -= 1
            currentY += 1
        }
        return false
    }

    private fun hasOccupiedSeatDown(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX to currentY + 1] == "L") {
                return false
            }
            if (mapOfLocation[currentX to currentY + 1] == "#") {
                return true
            }

            currentY += 1
        }
        return false
    }

    private fun hasOccupiedSeatRightDown(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX + 1 to currentY + 1] == "L") {
                return false
            }
            if (mapOfLocation[currentX + 1 to currentY + 1] == "#") {
                return true
            }
            currentX += 1
            currentY += 1
        }
        return false
    }

    private fun hasOccupiedSeatLeftUpp(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX - 1 to currentY - 1] == "L") {
                return false
            }
            if (mapOfLocation[currentX - 1 to currentY - 1] == "#") {
                return true
            }
            currentX -= 1
            currentY -= 1
        }
        return false
    }

    private fun hasOccupiedSeatUpp(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX to currentY - 1] == "L") {
                return false
            }
            if (mapOfLocation[currentX to currentY - 1] == "#") {
                return true
            }

            currentY -= 1
        }
        return false
    }

    private fun hasOccupiedSeatRightUpp(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX + 1 to currentY - 1] == "L") {
                return false
            }
            if (mapOfLocation[currentX + 1 to currentY - 1] == "#") {
                return true
            }
            currentX += 1
            currentY -= 1
        }
        return false
    }

    private fun hasOccupiedSeatLeft(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX - 1 to currentY] == "L") {
                return false
            }
            if (mapOfLocation[currentX - 1 to currentY] == "#") {
                return true
            }
            currentX -= 1
        }
        return false
    }

    private fun hasOccupiedSeatRight(
        seatX: Int,
        seatY: Int,
        mapOfLocation: Map<Pair<Int, Int>, String>
    ): Boolean {
        var currentX = seatX
        var currentY = seatY

        while (mapOfLocation[currentX to currentY] != null) {
            if (mapOfLocation[currentX + 1 to currentY] == "L") {
                return false
            }
            if (mapOfLocation[currentX + 1 to currentY] == "#") {
                return true
            }
            currentX += 1
        }
        return false
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
        val seatX = location.first
        val seatY = location.second
        var seatOccupiedCount = 0

        if (hasOccupiedSeatLeftUpp(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeatUpp(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeatRightUpp(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1

        if (hasOccupiedSeatLeft(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeatRight(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1


        if (hasOccupiedSeatLeftDown(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeatDown(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1
        if (hasOccupiedSeatRightDown(seatX, seatY, mapOfLocation)) seatOccupiedCount += 1

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