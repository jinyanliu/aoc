import utils.IoHelper
import utils.PrintHelper.printStringMap
import java.util.*
import kotlin.math.abs

object D23 {
    val inputs = IoHelper.getStringMap(IoHelper.getLines("d23Test.in"))
    val src = IoHelper.getStringMap(IoHelper.getLines("d23.in"))
    val dest = IoHelper.getStringMap(IoHelper.getLines("d23Dest.in"))

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

        if (location == 3 to 3 && value == "A") {
            return true
        }

        if (location == 5 to 3 && value == "B") {
            return true
        }

        if (location == 7 to 3 && value == "C") {
            return true
        }

        if (location == 9 to 3 && value == "D") {
            return true
        }

        if (location == 3 to 2 && value == "A" && map[3 to 3] == "A") {
            return true
        }

        if (location == 5 to 2 && value == "B" && map[5 to 3] == "B") {
            return true
        }

        if (location == 7 to 2 && value == "C" && map[7 to 3] == "C") {
            return true
        }

        if (location == 9 to 2 && value == "D" && map[9 to 3] == "D") {
            return true
        }
        return false
    }

    fun solveOne(): Long {
        inputs.printStringMap()
        var lowestCount: Long = 0L

        val bigQueue = ArrayDeque<Pair<Map<Pair<Int, Int>, String>, Long>>()

        bigQueue.add(inputs to 0L)

        var times = 0
        while (bigQueue.isNotEmpty()) {
            //repeat(4) {
            times += 1
            val firstInQueue = bigQueue.removeFirst()
            val currentMap = firstInQueue.first
            val count = firstInQueue.second
            //println("firstInQueue.count=$count")
            val startingPointList =
                //if (times == 1) listOf(7 to 2) else listOf(5 to 2)
                currentMap.filter { it.value != "." && it.value != "#" && it.value != " " }.map { it.key }
            for (startingPoint in startingPointList) {
                for (stay in findPossibleStay(startingPoint, currentMap)) {
                    val newMap = currentMap.toMutableMap()
                    var newCount = count
                    newMap[startingPoint] = "."
                    newMap[stay] = currentMap[startingPoint]!!

                    val number = getNumberForABCD(currentMap[startingPoint]!!)
                    val steps = calculateDistance(startingPoint, stay)
                    newCount = count + number * steps

                    if (newMap[3 to 2] == "A"
                        && newMap[3 to 3] == "A"
                        && newMap[5 to 2] == "B"
                        && newMap[5 to 3] == "B"
                        && newMap[7 to 2] == "C"
                        && newMap[7 to 3] == "C"
                        && newMap[9 to 2] == "D"
                        && newMap[9 to 3] == "D"
                    ) {
                        newMap.printStringMap()
                        println(newCount)
                        if (lowestCount == 0L) {
                            lowestCount = newCount
                        } else {
                            if (lowestCount > newCount) {
                                lowestCount = newCount
                            }
                        }
                    } else {
                        if (lowestCount != 0L && newCount >= lowestCount) {
                            //Do nothing
                        } else {
                            bigQueue.add(newMap.toMap() to newCount)
                        }
                    }
                }
            }
        }
        return lowestCount
    }

    private fun calculateDistance(start: Pair<Int, Int>, destination: Pair<Int, Int>): Int {
        val startX = start.first
        val desX = destination.first
        val startY = start.second
        val desY = destination.second
        return when {
            startY == 2 && desY == 2 -> abs(startX - desX) + 2
            startY == 2 && desY == 3 -> abs(startX - desX) + 3
            startY == 3 && desY == 2 -> abs(startX - desX) + 3
            startY == 3 && desY == 3 -> abs(startX - desX) + 4
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

        if (currentMap[location] == "A" && currentMap[3 to 2] == "." && currentMap[3 to 3] == ".") {
            if (currentCanGoToIt(location, 3 to 3, currentMap)) possibleStay.add(3 to 3)
        }

        if (currentMap[location] == "A" && currentMap[3 to 2] == "." && (currentMap[3 to 3] == "A")) {
            if (currentCanGoToIt(location, 3 to 2, currentMap)) possibleStay.add(3 to 2)
        }

        if (currentMap[location] == "B" && currentMap[5 to 2] == "." && currentMap[5 to 3] == ".") {
            if (currentCanGoToIt(location, 5 to 3, currentMap)) possibleStay.add(5 to 3)
        }

        if (currentMap[location] == "B" && currentMap[5 to 2] == "." && (currentMap[5 to 3] == "B")) {
            if (currentCanGoToIt(location, 5 to 2, currentMap)) possibleStay.add(5 to 2)
        }

        if (currentMap[location] == "C" && currentMap[7 to 2] == "." && currentMap[7 to 3] == ".") {
            if (currentCanGoToIt(location, 7 to 3, currentMap)) possibleStay.add(7 to 3)
        }

        if (currentMap[location] == "C" && currentMap[7 to 2] == "." && (currentMap[7 to 3] == "C")) {
            if (currentCanGoToIt(location, 7 to 2, currentMap)) possibleStay.add(7 to 2)
        }

        if (currentMap[location] == "D" && currentMap[9 to 2] == "." && currentMap[9 to 3] == ".") {
            if (currentCanGoToIt(location, 9 to 3, currentMap)) possibleStay.add(9 to 3)
        }

        if (currentMap[location] == "D" && currentMap[9 to 2] == "." && (currentMap[9 to 3] == "D")) {
            if (currentCanGoToIt(location, 9 to 2, currentMap)) possibleStay.add(9 to 2)
        }
        return possibleStay
    }

    fun solveTwo(): Int {
        return 0
    }
}

fun main() {
    //15322
    //65 seconds
    println(D23.getShortestDistance(D23.src, D23.dest))
}