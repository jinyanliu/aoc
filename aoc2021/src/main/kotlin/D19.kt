import utils.IoHelper
import java.util.*
import kotlin.math.abs

object D19 {
    private val bigMap = mutableMapOf<Int, List<List<Int>>>()
    fun initBigMap() {
        IoHelper.getSections("d19.in").map { it.lines().drop(1).map { it.split(",").map { it.toInt() } } }
            .forEachIndexed { index, lists -> bigMap[index] = lists }
    }

    val listOfBeacons = mutableSetOf<List<Int>>()
    private val listOfScanners = mutableListOf<List<Int>>(listOf(0, 0, 0))

    fun solveOne() {
        val solvedIndex = ArrayDeque<Int>()
        var unsolvedIndex = mutableListOf<Int>()

        solvedIndex.add(0)
        listOfBeacons.addAll(bigMap[0]!!)

        for (a in 1..37) {
            unsolvedIndex.add(a)
        }

        while (unsolvedIndex.isNotEmpty()) {
            val next = solvedIndex.removeFirst()
            for (j in unsolvedIndex) {
                val result = solve(next, j)
                if (result != -1) {
                    solvedIndex.add(result)
                }
            }
            unsolvedIndex = unsolvedIndex.filter { !solvedIndex.contains(it) }.toMutableList()
        }
    }

    fun solve(indexI: Int, indexJ: Int): Int {
        val scanner0: List<List<Int>> = bigMap[indexI]!!
        val scanner1 = bigMap[indexJ]!!

        val listOfScanner1Variations = mutableListOf<List<List<Int>>>()
        for (i in 0..23) {
            listOfScanner1Variations.add(scanner1.map { findRotation24(it)[i] })
        }

        val bigList = listOfScanner1Variations.map { getDistanceLogIn(it) }
        val scanner0DistanceLog: List<DistanceLog> = getDistanceLogIn(scanner0)

        for (item in bigList) {
            val mapCount = mutableMapOf<Int, Int>()
            for (log0 in scanner0DistanceLog) {
                for (log1 in item) {
                    if (log0.xyzD == log1.xyzD) {
                        val startPoint0 = log0.meAndYou.first
                        if (mapCount[startPoint0] != null) {
                            mapCount[startPoint0] = mapCount[startPoint0]!! + 1
                        } else {
                            mapCount[startPoint0] = 1
                        }

                        if (mapCount[startPoint0] == 11) {
                            val log0MeAndYou = log0.meAndYou
                            val log1MeAndYou = log1.meAndYou

                            val target1 = scanner0[log0MeAndYou.first]
                            val target2 = scanner0[log0MeAndYou.second]
                            val rotate1 = scanner1[log1MeAndYou.first]
                            val rotate2 = scanner1[log1MeAndYou.second]

                            val rotate = rotate(target1, target2, rotate1, rotate2)
                            val rotationIndex = rotate.first

                            val scanner1Location = rotate.second
                            listOfScanners.add(scanner1Location)
                            if (rotationIndex != -1) {
                                val finalDots = scanner1.map { findRotation24(it)[rotationIndex] }.map {
                                    val x = it[0] + scanner1Location[0]
                                    val y = it[1] + scanner1Location[1]
                                    val z = it[2] + scanner1Location[2]
                                    listOf<Int>(x, y, z)
                                }

                                listOfBeacons.addAll(finalDots)

                                bigMap[indexJ] = finalDots

                                return indexJ
                            }
                        }
                    }
                }
            }
        }
        return -1
    }

    private fun rotate(
        target1: List<Int>,
        target2: List<Int>,
        rotate1: List<Int>,
        rotate2: List<Int>
    ): Pair<Int, List<Int>> {
        val resul1 = findRotation24(rotate1)
        val result2 = findRotation24(rotate2)

        var rotationIndex = -1
        var scanner1 = emptyList<Int>()
        for (i in 0..23) {
            val item1 = resul1[i]
            val item2 = result2[i]

            val t1X = target1[0]
            val t1Y = target1[1]
            val t1Z = target1[2]

            val t2X = target2[0]
            val t2Y = target2[1]
            val t2Z = target2[2]

            val i1X = item1[0]
            val i1Y = item1[1]
            val i1Z = item1[2]

            val i2X = item2[0]
            val i2Y = item2[1]
            val i2Z = item2[2]

            if (t1X - i1X == t2X - i2X && t1Y - i1Y == t2Y - i2Y && t1Z - i1Z == t2Z - i2Z) {
                rotationIndex = i
                scanner1 = listOf(t1X - i1X, t1Y - i1Y, t1Z - i1Z)
                break
            }

            if (t1X - i2X == t2X - i1X && t1Y - i2Y == t2Y - i1Y && t1Z - i2Z == t2Z - i1Z) {
                rotationIndex = i
                scanner1 = listOf(t1X - i2X, t1Y - i2Y, t1Z - i2Z)
                break
            }
        }
        return rotationIndex to scanner1
    }

    private fun findRotation24(xyz: List<Int>): List<List<Int>> {
        val x = xyz[0]
        val y = xyz[1]
        val z = xyz[2]
        return listOf(
            listOf(x, y, z),
            listOf(x, -z, y),
            listOf(x, -y, -z),
            listOf(x, z, -y),

            listOf(-x, -y, z),
            listOf(-x, -z, -y),
            listOf(-x, y, -z),
            listOf(-x, z, y),

            listOf(z, x, y),
            listOf(-y, x, z),
            listOf(-z, x, -y),
            listOf(y, x, -z),

            listOf(y, -x, z),
            listOf(z, -x, -y),
            listOf(-y, -x, -z),
            listOf(-z, -x, y),


            listOf(y, z, x),
            listOf(z, -y, x),
            listOf(-y, -z, x),
            listOf(-z, y, x),

            listOf(z, y, -x),
            listOf(-y, z, -x),
            listOf(-z, -y, -x),
            listOf(y, -z, -x)
        )
    }

    private fun getDistanceLogIn(scanner: List<List<Int>>): List<DistanceLog> {
        val list = mutableListOf<DistanceLog>()
        val scannerSize = scanner.size

        for (a in 0 until scannerSize - 1) {
            for (b in a + 1 until scannerSize) {
                val x1 = scanner[a][0]
                val y1 = scanner[a][1]
                val z1 = scanner[a][2]
                val x2 = scanner[b][0]
                val y2 = scanner[b][1]
                val z2 = scanner[b][2]

                val xD = getDistanceBetween(x1, x2)
                val yD = getDistanceBetween(y1, y2)
                val zD = getDistanceBetween(z1, z2)

                list.add(DistanceLog(a to b, listOf(xD, yD, zD)))
            }
        }
        return list
    }

    private fun getDistanceBetween(x1: Int, x2: Int) =
        if (x1 > 0 && x2 > 0) {
            abs(x1 - x2)
        } else if (x1 < 0 && x2 < 0) {
            abs(abs(x1) - abs(x2))
        } else if (x1 > 0 && x2 < 0) {
            abs(x1) + abs(x2)
        } else if (x1 < 0 && x2 > 0) {
            abs(x1) + abs(x2)
        } else {
            abs(x1 - x2)
        }

    fun solveTwo(): Int {
        val list1 = listOfScanners
        val list2 = listOfScanners

        val manhattanDistances = mutableListOf<Int>()

        for (i in list1) {
            for (j in list2) {
                if (i == j) continue
                val x = abs(i[0] - j[0])
                val y = abs(i[1] - j[1])
                val z = abs(i[2] - j[2])
                manhattanDistances.add(x + y + z)
            }
        }
        return manhattanDistances.max()!!
    }
}

fun main() {
    D19.initBigMap()
    D19.solveOne()
    val solutionTwo = D19.solveTwo()
    //462
    println("One=${D19.listOfBeacons.size}")
    //12158
    println("Tw0=$solutionTwo")
}

data class DistanceLog(
    val meAndYou: Pair<Int, Int> = 0 to 0,
    val xyzD: List<Int> = emptyList()
)