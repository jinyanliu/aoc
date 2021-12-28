import utils.IoHelper
import kotlin.math.abs

object D23Part2 {
    val inputs = IoHelper.getStringMap(IoHelper.getLines("d23Part2Test.in"))
    val src = IoHelper.getStringMap(IoHelper.getLines("d23Part2.in"))
    val dest = IoHelper.getStringMap(IoHelper.getLines("d23Part2Dest.in"))

    fun getShortestDistance(src: Map<Pair<Int, Int>, String>, dest: Map<Pair<Int, Int>, String>): Long {
        val startTimestamp = System.currentTimeMillis()
        //中间map到src map的距离
        val distToSrc = mutableMapOf<Map<Pair<Int, Int>, String>, Long>()
        //已知最短距离
        val solvedDistToSrc = mutableMapOf(src to 0L)

        var currentMap = src
        while (currentMap != dest) {
            val newNeighbours = getNeighbours(currentMap).filterNot { it.first in solvedDistToSrc }
            newNeighbours.forEach { neighbour ->
                val srcToCurrentMap = solvedDistToSrc[currentMap]!!
                val edge = neighbour.second

                if (neighbour.first !in distToSrc) {
                    distToSrc[neighbour.first] = edge + srcToCurrentMap
                } else {
                    val srcToNeighbour = distToSrc[neighbour.first]!!
                    if (srcToNeighbour > edge + srcToCurrentMap) {
                        distToSrc[neighbour.first] = edge + srcToCurrentMap
                    }
                }
            }
            currentMap = distToSrc.minBy { it.value }!!.key
            solvedDistToSrc[currentMap] = distToSrc[currentMap]!!
            distToSrc.remove(currentMap)
        }
        val endTimestamp = System.currentTimeMillis()
        println("Time=${(endTimestamp - startTimestamp) / 1000.0}")
        return solvedDistToSrc[dest]!!
    }

    private fun getNeighbours(currentMap: Map<Pair<Int, Int>, String>): List<Pair<Map<Pair<Int, Int>, String>, Long>> {

        val neighbours = mutableListOf<Pair<Map<Pair<Int, Int>, String>, Long>>()

        val startingPointList =
            currentMap.filter { it.value != "." && it.value != "#" && it.value != " " }.map { it.key }
        for (startingPoint in startingPointList) {
            for (stay in findPossibleStay(startingPoint, currentMap)) {
                val newMap = currentMap.toMutableMap()
                newMap[startingPoint] = "."
                newMap[stay] = currentMap[startingPoint]!!

                val number = getNumberForABCD(currentMap[startingPoint]!!)
                val steps = calculateDistance(startingPoint, stay)

                neighbours.add(newMap to number * steps)
            }
        }
        return neighbours
    }

    private fun settled(location: Pair<Int, Int>, map: Map<Pair<Int, Int>, String>): Boolean {
        val value = map[location]

        if (location == 3 to 5 && value == "A") {
            return true
        }

        if (location == 5 to 5 && value == "B") {
            return true
        }

        if (location == 7 to 5 && value == "C") {
            return true
        }

        if (location == 9 to 5 && value == "D") {
            return true
        }

        if (location == 3 to 4 && value == "A" && map[3 to 5] == "A") {
            return true
        }

        if (location == 5 to 4 && value == "B" && map[5 to 5] == "B") {
            return true
        }

        if (location == 7 to 4 && value == "C" && map[7 to 5] == "C") {
            return true
        }

        if (location == 9 to 4 && value == "D" && map[9 to 5] == "D") {
            return true
        }

        if (location == 3 to 3 && value == "A" && map[3 to 4] == "A" && map[3 to 5] == "A") {
            return true
        }

        if (location == 5 to 3 && value == "B" && map[5 to 4] == "B" && map[5 to 5] == "B") {
            return true
        }

        if (location == 7 to 3 && value == "C" && map[7 to 4] == "C" && map[7 to 5] == "C") {
            return true
        }

        if (location == 9 to 3 && value == "D" && map[9 to 4] == "D" && map[9 to 5] == "D") {
            return true
        }

        if (location == 3 to 2 && value == "A" && map[3 to 3] == "A" && map[3 to 4] == "A" && map[3 to 5] == "A") {
            return true
        }

        if (location == 5 to 2 && value == "B" && map[5 to 3] == "B" && map[5 to 4] == "B" && map[5 to 5] == "B") {
            return true
        }

        if (location == 7 to 2 && value == "C" && map[7 to 3] == "C" && map[7 to 4] == "C" && map[7 to 5] == "C") {
            return true
        }

        if (location == 9 to 2 && value == "D" && map[9 to 3] == "D" && map[9 to 4] == "D" && map[9 to 5] == "D") {
            return true
        }
        return false
    }

    private fun calculateDistance(start: Pair<Int, Int>, destination: Pair<Int, Int>): Int {
        val startX = start.first
        val desX = destination.first
        val startY = start.second
        val desY = destination.second
        return when {
            startY == 2 && desY == 2 -> abs(startX - desX) + 2
            startY == 2 && desY == 3 -> abs(startX - desX) + 3
            startY == 2 && desY == 4 -> abs(startX - desX) + 4
            startY == 2 && desY == 5 -> abs(startX - desX) + 5

            startY == 3 && desY == 2 -> abs(startX - desX) + 3
            startY == 3 && desY == 3 -> abs(startX - desX) + 4
            startY == 3 && desY == 4 -> abs(startX - desX) + 5
            startY == 3 && desY == 5 -> abs(startX - desX) + 6

            startY == 4 && desY == 2 -> abs(startX - desX) + 4
            startY == 4 && desY == 3 -> abs(startX - desX) + 5
            startY == 4 && desY == 4 -> abs(startX - desX) + 6
            startY == 4 && desY == 5 -> abs(startX - desX) + 7

            startY == 5 && desY == 2 -> abs(startX - desX) + 5
            startY == 5 && desY == 3 -> abs(startX - desX) + 6
            startY == 5 && desY == 4 -> abs(startX - desX) + 7
            startY == 5 && desY == 5 -> abs(startX - desX) + 8

            else -> abs(startX - desX) + abs(startY - desY)
        }
    }

    private fun currentCanGoToIt(
        current: Pair<Int, Int>,
        destination: Pair<Int, Int>,
        map: Map<Pair<Int, Int>, String>
    ): Boolean {
        if (current.second == 2) {
            if (current.first < destination.first) {
                return (current.first..destination.first).all { map[it to 1] == "." }
            } else {
                return (destination.first..current.first).all { map[it to 1] == "." }
            }
        } else if (current.second == 3) {
            if (current.first < destination.first) {
                return (current.first..destination.first).all { map[it to 1] == "." } && map[current.first to current.second - 1] == "."
            } else {
                return (destination.first..current.first).all { map[it to 1] == "." } && map[current.first to current.second - 1] == "."
            }
        } else if (current.second == 4) {
            if (current.first < destination.first) {
                return (current.first..destination.first).all { map[it to 1] == "." } && map[current.first to current.second - 1] == "." && map[current.first to current.second - 2] == "."
            } else {
                return (destination.first..current.first).all { map[it to 1] == "." } && map[current.first to current.second - 1] == "." && map[current.first to current.second - 2] == "."
            }
        } else if (current.second == 5) {
            if (current.first < destination.first) {
                return (current.first..destination.first).all { map[it to 1] == "." } && map[current.first to current.second - 1] == "." && map[current.first to current.second - 2] == "." && map[current.first to current.second - 3] == "."
            } else {
                return (destination.first..current.first).all { map[it to 1] == "." } && map[current.first to current.second - 1] == "." && map[current.first to current.second - 2] == "." && map[current.first to current.second - 3] == "."
            }
        } else {
            if (current.first < destination.first) {
                return (current.first + 1..destination.first).all { map[it to 1] == "." }
            } else {
                return (destination.first..current.first - 1).all { map[it to 1] == "." }
            }
        }
    }

    private fun getNumberForABCD(string: String): Long {
        return when (string) {
            "A" -> 1
            "B" -> 10
            "C" -> 100
            "D" -> 1000
            else -> -100000
        }
    }

    private fun findPossibleStay(
        location: Pair<Int, Int>,
        currentMap: Map<Pair<Int, Int>, String>
    ): List<Pair<Int, Int>> {
        if (settled(location, currentMap)) return emptyList()
        val possibleStay = mutableListOf<Pair<Int, Int>>()
        if (location.second != 1) {
            possibleStay.addAll(
                listOf(
                    1 to 1,
                    2 to 1,
                    4 to 1,
                    6 to 1,
                    8 to 1,
                    10 to 1,
                    11 to 1
                ).filter { currentCanGoToIt(location, it, currentMap) })
        }

        if (currentMap[location] == "A" && currentMap[3 to 5] == "." && currentMap[3 to 4] == "." && currentMap[3 to 3] == "." && currentMap[3 to 2] == ".") {
            if (currentCanGoToIt(location, 3 to 5, currentMap)) possibleStay.add(3 to 5)
        }

        if (currentMap[location] == "A" && currentMap[3 to 5] == "A" && currentMap[3 to 4] == "." && currentMap[3 to 3] == "." && currentMap[3 to 2] == ".") {
            if (currentCanGoToIt(location, 3 to 4, currentMap)) possibleStay.add(3 to 4)
        }

        if (currentMap[location] == "A" && currentMap[3 to 5] == "A" && currentMap[3 to 4] == "A" && currentMap[3 to 3] == "." && currentMap[3 to 2] == ".") {
            if (currentCanGoToIt(location, 3 to 3, currentMap)) possibleStay.add(3 to 3)
        }

        if (currentMap[location] == "A" && currentMap[3 to 5] == "A" && currentMap[3 to 4] == "A" && currentMap[3 to 3] == "A" && currentMap[3 to 2] == ".") {
            if (currentCanGoToIt(location, 3 to 2, currentMap)) possibleStay.add(3 to 2)
        }

        if (currentMap[location] == "B" && currentMap[5 to 5] == "." && currentMap[5 to 4] == "." && currentMap[5 to 3] == "." && currentMap[5 to 2] == ".") {
            if (currentCanGoToIt(location, 5 to 5, currentMap)) possibleStay.add(5 to 5)
        }

        if (currentMap[location] == "B" && currentMap[5 to 5] == "B" && currentMap[5 to 4] == "." && currentMap[5 to 3] == "." && currentMap[5 to 2] == ".") {
            if (currentCanGoToIt(location, 5 to 4, currentMap)) possibleStay.add(5 to 4)
        }

        if (currentMap[location] == "B" && currentMap[5 to 5] == "B" && currentMap[5 to 4] == "B" && currentMap[5 to 3] == "." && currentMap[5 to 2] == ".") {
            if (currentCanGoToIt(location, 5 to 3, currentMap)) possibleStay.add(5 to 3)
        }

        if (currentMap[location] == "B" && currentMap[5 to 5] == "B" && currentMap[5 to 4] == "B" && currentMap[5 to 3] == "B" && currentMap[5 to 2] == ".") {
            if (currentCanGoToIt(location, 5 to 2, currentMap)) possibleStay.add(5 to 2)
        }

        if (currentMap[location] == "C" && currentMap[7 to 5] == "." && currentMap[7 to 4] == "." && currentMap[7 to 3] == "." && currentMap[7 to 2] == ".") {
            if (currentCanGoToIt(location, 7 to 5, currentMap)) possibleStay.add(7 to 5)
        }

        if (currentMap[location] == "C" && currentMap[7 to 5] == "C" && currentMap[7 to 4] == "." && currentMap[7 to 3] == "." && currentMap[7 to 2] == ".") {
            if (currentCanGoToIt(location, 7 to 4, currentMap)) possibleStay.add(7 to 4)
        }

        if (currentMap[location] == "C" && currentMap[7 to 5] == "C" && currentMap[7 to 4] == "C" && currentMap[7 to 3] == "." && currentMap[7 to 2] == ".") {
            if (currentCanGoToIt(location, 7 to 3, currentMap)) possibleStay.add(7 to 3)
        }

        if (currentMap[location] == "C" && currentMap[7 to 5] == "C" && currentMap[7 to 4] == "C" && currentMap[7 to 3] == "C" && currentMap[7 to 2] == ".") {
            if (currentCanGoToIt(location, 7 to 2, currentMap)) possibleStay.add(7 to 2)
        }

        if (currentMap[location] == "D" && currentMap[9 to 5] == "." && currentMap[9 to 4] == "." && currentMap[9 to 3] == "." && currentMap[9 to 2] == ".") {
            if (currentCanGoToIt(location, 9 to 5, currentMap)) possibleStay.add(9 to 5)
        }

        if (currentMap[location] == "D" && currentMap[9 to 5] == "D" && currentMap[9 to 4] == "." && currentMap[9 to 3] == "." && currentMap[9 to 2] == ".") {
            if (currentCanGoToIt(location, 9 to 4, currentMap)) possibleStay.add(9 to 4)
        }

        if (currentMap[location] == "D" && currentMap[9 to 5] == "D" && currentMap[9 to 4] == "D" && currentMap[9 to 3] == "." && currentMap[9 to 2] == ".") {
            if (currentCanGoToIt(location, 9 to 3, currentMap)) possibleStay.add(9 to 3)
        }

        if (currentMap[location] == "D" && currentMap[9 to 5] == "D" && currentMap[9 to 4] == "D" && currentMap[9 to 3] == "D" && currentMap[9 to 2] == ".") {
            if (currentCanGoToIt(location, 9 to 2, currentMap)) possibleStay.add(9 to 2)
        }
        return possibleStay
    }
}

fun main() {
    //56324
    //66 seconds
    println(D23Part2.getShortestDistance(D23Part2.src, D23Part2.dest))
}