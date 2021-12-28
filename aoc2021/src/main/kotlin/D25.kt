import utils.IoHelper
import utils.LocationHelper

object D25 {
    private val inputs = IoHelper.getStringMap("d25.in")
    private val maxX = LocationHelper.getMaxX(inputs.keys)
    private val maxY = LocationHelper.getMaxY(inputs.keys)

    fun solveOne(): Int {
        var step = 0
        var oldMap = inputs
        while (true) {
            step += 1
            val dots = oldMap.filter { it.value == "." }
            val newMap = oldMap.toMutableMap()
            for (dot in dots) {
                val dotPosition = dot.key
                val dotX = dotPosition.first
                val dotY = dotPosition.second
                if (oldMap[dotX - 1 to dotY] == null) {
                    if (oldMap[maxX to dotY] == ">") {
                        newMap[maxX to dotY] = "."
                        newMap[dotPosition] = ">"
                    }
                } else {
                    if (oldMap[dotX - 1 to dotY] == ">") {
                        newMap[dotX - 1 to dotY] = "."
                        newMap[dotPosition] = ">"
                    }
                }
            }
            val newNewMap = newMap.toMutableMap()
            val newDots = newMap.filter { it.value == "." }
            for (dot in newDots) {
                val dotPosition = dot.key
                val dotX = dotPosition.first
                val dotY = dotPosition.second
                if (newMap[dotX to dotY - 1] == null) {
                    if (newMap[dotX to maxY] == "v") {
                        newNewMap[dotX to maxY] = "."
                        newNewMap[dotPosition] = "v"
                    }
                } else {
                    if (newMap[dotX to dotY - 1] == "v") {
                        newNewMap[dotX to dotY - 1] = "."
                        newNewMap[dotPosition] = "v"
                    }
                }
            }

            if (oldMap == newNewMap) {
                return step
            } else {
                oldMap = newNewMap
            }
        }
    }

    fun solveTwo(): Int {
        return 0
    }
}

fun main() {
    //549
    val solutionOne = D25.solveOne()
    val solutionTwo = D25.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}