import utils.IoHelper
import utils.LocationHelper.getSurroundingNinePositions
import utils.LocationHelper.getSurroundingNineValues
import utils.PrintHelper.printStringMap
import java.util.*
import kotlin.math.abs

object D20 {
    private val inputs = IoHelper.getSections("d20.in")
    private val enhancementAlgorithm = inputs[0]
    private val inputImage = inputs[1]

    fun solve(repeatTimes: Int): Int {
        val mapOfAlgorithm = mutableMapOf<Int, String>()
        for (i in enhancementAlgorithm.withIndex()) {
            mapOfAlgorithm[i.index] = i.value.toString()
        }

        var inputMap = IoHelper.getStringMap(inputImage.lines())
        var result = mapOf<Pair<Int, Int>, String>()

        var step = 0
        repeat(repeatTimes) {
            step += 1
            val outputMap = mutableMapOf<Pair<Int, Int>, String>()
            val xRange = inputMap.keys.map { it.first }
            val yRange = inputMap.keys.map { it.second }

            val startingPoint = abs(xRange.max()!! - xRange.min()!!) / 2 to abs(yRange.max()!! - yRange.min()!!) / 2

            val queue = ArrayDeque<Pair<Int, Int>>()
            queue.add(startingPoint)

            while (queue.isNotEmpty()) {
                val currentPoint = queue.removeFirst()
                if (!outputMap.keys.contains(currentPoint)) {
                    val temp = if (step % 2 == 1) {
                        currentPoint.getSurroundingNineValues(map = inputMap, isInfinite = true, defaultValue = ".")
                            .joinToString("")
                    } else {
                        currentPoint.getSurroundingNineValues(map = inputMap, isInfinite = true, defaultValue = "#")
                            .joinToString("")
                    }

                    if (step % 2 == 1 && temp != ".........") {
                        val index = temp.replace(".", "0").replace("#", "1").toInt(2)
                        outputMap[currentPoint] = mapOfAlgorithm[index]!!
                        queue.addAll(
                            currentPoint.getSurroundingNinePositions(isInfinite = true)
                                .filter { !outputMap.keys.contains(it) })
                    }

                    if (step % 2 == 0 && temp != "#########") {
                        val index = temp.replace(".", "0").replace("#", "1").toInt(2)
                        outputMap[currentPoint] = mapOfAlgorithm[index]!!
                        queue.addAll(
                            currentPoint.getSurroundingNinePositions(isInfinite = true)
                                .filter { !outputMap.keys.contains(it) })
                    }
                }
            }
            inputMap = outputMap
            outputMap.printStringMap()
            println(outputMap.keys.map { it.first }.min()!!)
            println(outputMap.keys.map { it.first }.max()!!)
            println(outputMap.count { it.value == "#" })
            result = outputMap
        }

        return result.count { it.value == "#" }
    }
}

fun main() {
    //5461
    val solutionOne = D20.solve(2)
    //18226
    val solutionTwo = D20.solve(50)
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}