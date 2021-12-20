import utils.IoHelper
import utils.LocationHelper.get4AdjacentPositions
import utils.LocationHelper.getMaxX
import utils.LocationHelper.getMaxY
import utils.PrintHelper.printIntMap
import java.util.*
import java.util.concurrent.TimeUnit

object D15 {
    val inputs = IoHelper.getIntMap("d15.in")

    fun solve(inputs: Map<Pair<Int, Int>, Int>): Int {
        val maxX = getMaxX(inputs.keys)
        val maxY = getMaxY(inputs.keys)

        val startTimestamp = System.currentTimeMillis()

        var lowestCount = 0

        val queue = ArrayDeque<List<Pair<Pair<Int, Int>, Int>>>()

        queue.add(listOf((0 to 0) to 0))

        while (queue.isNotEmpty()) {
            val currentPath = queue.removeFirst()
            val currentPoints = currentPath.map { it.first }
            val currentCount = currentPath.last().second

            if (lowestCount != 0 && currentCount >= lowestCount) continue

            val neighbours = currentPoints.last().get4AdjacentPositions(maxX, maxY)

            for (newPoint in neighbours) {
                if (currentPoints.contains(newPoint)) continue

                val newCount = currentCount + inputs[newPoint]!!

                if (lowestCount != 0 && newCount >= lowestCount) continue

                queue.removeIf { innerList ->
                    (innerList.find { listItem -> listItem.first == newPoint }?.second ?: 0) >= newCount
                }

                if (queue.any { innerList ->
                        val find = innerList.find { listItem -> listItem.first == newPoint }
                        find != null && find.second < newCount
                    }) continue

                if (newPoint == maxX to maxY) {

                    lowestCount = newCount

                    queue.removeIf { innerList ->
                        innerList.any { it.second >= lowestCount }
                    }

                } else {
                    queue.add(currentPath + (newPoint to newCount))
                }
            }
        }

        val endTimestamp = System.currentTimeMillis()
        val minutes = TimeUnit.MILLISECONDS.toMinutes(endTimestamp - startTimestamp)
        println("Minutes = $minutes")
        return lowestCount
    }

    fun solveTwo(): Int {
        inputs.printIntMap()
        val newMap = mutableMapOf<Pair<Int, Int>, Int>()
        inputs.forEach {
            newMap[it.key] = it.value
        }

        val maxX = getMaxX(inputs.keys)

        for (x in (maxX + 1) until (maxX + 1) * 5) {
            for (y in 0..maxX) {
                val startValue = newMap[(x - (maxX + 1)) to (y)]!!
                val endValue = if (startValue == 9) {
                    1
                } else {
                    startValue + 1
                }
                newMap[x to y] = endValue
            }
        }

        for (x in 0 until (maxX + 1) * 5) {
            for (y in (maxX + 1) until (maxX + 1) * 5) {
                val startValue = newMap[x to (y - (maxX + 1))]!!
                val endValue = if (startValue == 9) {
                    1
                } else {
                    startValue + 1
                }
                newMap[x to y] = endValue
            }
        }

        newMap.printIntMap()
        return solve(newMap)
    }
}

fun main() {
    //604
    //3 minutes
    val solutionOne = D15.solve(D15.inputs)
    println("One=$solutionOne")
/*    val solutionTwo = D15.solveTwo()
    println("Tw0=$solutionTwo")*/
}