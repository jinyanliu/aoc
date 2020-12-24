import utils.IoHelper
import kotlin.math.absoluteValue

class Day24 {
    private val inputs = IoHelper.getLines("d24.in")
    private val validDi = arrayListOf("e", "se", "sw", "w", "nw", "ne")

    // colors: "w", "b"
    private val mapOfLoAndColor = mutableMapOf<Pair<Long, Long>, String>()

    fun getSolution1(): Int {
        val inputsOfDis = inputs.map { it.toMutableList() }.map { line ->
            val listOfDirections = arrayListOf<String>()
            while (line.isNotEmpty()) {
                var r = line.removeAt(0).toString()
                if (r in validDi) {
                    listOfDirections.add(r)
                } else {
                    r += line.removeAt(0).toString()
                    listOfDirections.add(r)
                }
            }
            listOfDirections
        }

        val inputsOfLo = inputsOfDis.map { di ->
            var lo = 0L to 0L
            di.forEach {
                lo = move(lo, it)
            }
            lo
        }

        inputsOfLo.forEach { lo ->
            if (lo !in mapOfLoAndColor.keys) {
                mapOfLoAndColor[lo] = "b"
            } else {
                if (mapOfLoAndColor[lo] == "b") {
                    mapOfLoAndColor[lo] = "w"
                } else {
                    mapOfLoAndColor[lo] = "b"
                }
            }
        }

        return mapOfLoAndColor.values.count { it == "b" }
    }

    fun getSolution2(): Int {
        getSolution1()
        var currentMap = mapOfLoAndColor
        repeat(100) {
            val minX = currentMap.keys.map { it.first }.min()!! - 1
            val maxX = currentMap.keys.map { it.first }.max()!! + 1
            val minY = currentMap.keys.map { it.second }.min()!! - 2
            val maxY = currentMap.keys.map { it.second }.max()!! + 2
            val xRange = minX..maxX
            val yRange = minY..maxY
            val toCheck = arrayListOf<Pair<Long, Long>>()
            for (x in xRange) {
                if (x.absoluteValue % 2 == 0L) {
                    for (y in yRange) {
                        if (y.absoluteValue % 2 == 0L) {
                            toCheck.add(x to y)
                        }
                    }
                } else {
                    for (y in yRange) {
                        if (y.absoluteValue % 2 != 0L) {
                            toCheck.add(x to y)
                        }
                    }
                }
            }

            val daysMap = mutableMapOf<Pair<Long, Long>, String>()
            toCheck.forEach { it ->
                val color = if (currentMap[it] != null) currentMap[it]!! else "w"
                if (color == "b") {
                    val neighbourBlackCount = whoAreYourNeighbours(it).count { currentMap[it] == "b" }
                    if (neighbourBlackCount == 0 || neighbourBlackCount > 2) {
                        daysMap[it] = "w"
                    } else {
                        daysMap[it] = "b"
                    }
                } else {
                    val neighbourBlackCount = whoAreYourNeighbours(it).count { currentMap[it] == "b" }
                    if (neighbourBlackCount == 2) {
                        daysMap[it] = "b"
                    } else {
                        daysMap[it] = "w"
                    }
                }
            }
            currentMap = daysMap
        }
        return currentMap.values.count { it == "b" }
    }


    private fun move(lo: Pair<Long, Long>, di: String): Pair<Long, Long> {
        return when (di) {
            "e" -> lo.first to lo.second + 2
            "se" -> lo.first + 1 to lo.second + 1
            "sw" -> lo.first + 1 to lo.second - 1
            "w" -> lo.first to lo.second - 2
            "nw" -> lo.first - 1 to lo.second - 1
            "ne" -> lo.first - 1 to lo.second + 1
            else -> error("error move")
        }
    }

    private fun whoAreYourNeighbours(you: Pair<Long, Long>): List<Pair<Long, Long>> {
        return listOf(
            you.first to you.second + 2,
            you.first + 1 to you.second + 1,
            you.first + 1 to you.second - 1,
            you.first to you.second - 2,
            you.first - 1 to you.second - 1,
            you.first - 1 to you.second + 1
        )
    }
}

fun main() {
    //455
    println(Day24().getSolution1())
    //3904
    println(Day24().getSolution2())
}