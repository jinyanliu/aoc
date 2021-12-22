import utils.IoHelper
import java.lang.Long.max
import java.lang.Long.min
import kotlin.math.abs

object D22 {
    private val inputsOne = IoHelper.getLines("d22.in").take(20)
    private val inputsTwo = IoHelper.getLines("d22.in")

    fun solveOne(): Int {
        val listOn = mutableSetOf<List<Int>>()
        for (line in inputsOne) {
            val sections = line.split(" ")
            val ranges = sections[1].split(",")
            val xRange = ranges[0].split("=")[1].split("..")[0].toInt()..ranges[0].split("=")[1].split("..")[1].toInt()
            val yRange = ranges[1].split("=")[1].split("..")[0].toInt()..ranges[1].split("=")[1].split("..")[1].toInt()
            val zRange = ranges[2].split("=")[1].split("..")[0].toInt()..ranges[2].split("=")[1].split("..")[1].toInt()

            if (sections[0] == "on") {
                for (x in xRange) {
                    for (y in yRange) {
                        for (z in zRange) {
                            listOn.add(listOf(x, y, z))
                        }
                    }
                }
            } else {
                for (x in xRange) {
                    for (y in yRange) {
                        for (z in zRange) {
                            if (listOn.contains(listOf(x, y, z))) {
                                listOn.remove(listOf(x, y, z))
                            }

                        }
                    }
                }
            }
        }
        return listOn.size
    }

    fun solveTwo(): Long {
        val startTimestamp = System.currentTimeMillis()
        val listOfCube = inputsTwo.map { line ->
            val section = line.split(" ")
            val switch = section[0] == "on"
            val sentence = section[1]
            val x = sentence.split(",")[0]
            val y = sentence.split(",")[1]
            val z = sentence.split(",")[2]
            val minX = x.split("=")[1].split("..")[0].toLong()
            val maxX = x.split("=")[1].split("..")[1].toLong()
            val minY = y.split("=")[1].split("..")[0].toLong()
            val maxY = y.split("=")[1].split("..")[1].toLong()
            val minZ = z.split("=")[1].split("..")[0].toLong()
            val maxZ = z.split("=")[1].split("..")[1].toLong()
            Cube(switch, minX, maxX, minY, maxY, minZ, maxZ)
        }

        var totalCounter = 0L
        for (i in listOfCube.indices) {
            val firstCube = listOfCube[i]
            var counter = countCube(firstCube)
            if (!firstCube.switch) continue
            val shouldBeRemovedFromFirst = mutableListOf<Cube>()

            if (i == listOfCube.size - 1) {
                totalCounter += counter
            } else {
                for (j in i + 1 until listOfCube.size) {
                    val secondCube = listOfCube[j]
                    val maxX = min(firstCube.maxX, secondCube.maxX)
                    val minX = max(firstCube.minX, secondCube.minX)
                    val maxY = min(firstCube.maxY, secondCube.maxY)
                    val minY = max(firstCube.minY, secondCube.minY)
                    val maxZ = min(firstCube.maxZ, secondCube.maxZ)
                    val minZ = max(firstCube.minZ, secondCube.minZ)

                    if (maxX < minX || maxY < minY || maxZ < minZ) {
                        continue
                    } else {
                        shouldBeRemovedFromFirst.add(Cube(false, minX, maxX, minY, maxY, minZ, maxZ))
                    }
                }
                counter -= countDistinctAll(shouldBeRemovedFromFirst)
                totalCounter += counter
            }
        }
        val endTimestamp = System.currentTimeMillis()
        println("Time=${(endTimestamp - startTimestamp) / 1000.0}")
        return totalCounter
    }

    private fun countDistinctAll(list: List<Cube>): Long {
        if (list.isEmpty()) return 0L
        if (list.size == 1) {
            return countCube(list[0])
        }

        var totalCount = 0L
        for (i in list.indices) {
            val firstCube = list[i]
            var count = countCube(firstCube)
            val intersection = mutableListOf<Cube>()
            if (i == list.size - 1) {
                totalCount += count
            } else {
                for (j in i + 1 until list.size) {
                    val secondCube = list[j]
                    val maxX = min(firstCube.maxX, secondCube.maxX)
                    val minX = max(firstCube.minX, secondCube.minX)
                    val maxY = min(firstCube.maxY, secondCube.maxY)
                    val minY = max(firstCube.minY, secondCube.minY)
                    val maxZ = min(firstCube.maxZ, secondCube.maxZ)
                    val minZ = max(firstCube.minZ, secondCube.minZ)

                    if (maxX < minX || maxY < minY || maxZ < minZ) {
                        continue
                    } else {
                        intersection.add(Cube(false, minX, maxX, minY, maxY, minZ, maxZ))
                    }
                }
                count -= countDistinctAll(intersection)
                totalCount += count
            }
        }
        return totalCount
    }

    private fun countCube(cube: Cube): Long {
        return (abs(cube.maxX - cube.minX) + 1) * abs((cube.maxY - cube.minY) + 1) * abs((cube.maxZ - cube.minZ) + 1)
    }
}

fun main() {
    //537042
    val solutionOne = D22.solveOne()
    //1304385553084863
    val solutionTwo = D22.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}

data class Cube(
    val switch: Boolean,
    val minX: Long,
    val maxX: Long,
    val minY: Long,
    val maxY: Long,
    val minZ: Long,
    val maxZ: Long
)