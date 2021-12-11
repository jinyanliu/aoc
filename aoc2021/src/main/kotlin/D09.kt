import utils.IoHelper
import utils.LocationHelper.get4AdjacentPositions
import utils.LocationHelper.get4AdjacentValues
import utils.LocationHelper.getMaxX
import utils.LocationHelper.getMaxY

object D9 {
    private val inputs = IoHelper.getMap("d09.in")
    private val maxX = getMaxX(inputs)
    private val maxY = getMaxY(inputs)

    private val lowestPoints =
        inputs.filter {
            it.key.get4AdjacentValues(inputs, maxX, maxY).all { adjacentValue -> adjacentValue > it.value }
        }

    fun solveOne() = lowestPoints.map { it.value + 1 }.sum()

    fun solveTwo() =
        lowestPoints.map { it.key.countBasin() }.sortedDescending().subList(0, 3).reduce(Int::times)

    private fun Pair<Int, Int>.countBasin(): Int {
        var basinCount = 1
        var currentList = mutableListOf(this)
        val countedList = mutableListOf(this)

        while (currentList.isNotEmpty()) {
            val newList = mutableListOf<Pair<Int, Int>>()
            for (item in currentList) {
                for (position in item.get4AdjacentPositions(maxX, maxY)) {
                    val positionValue = inputs[position]!!
                    if (positionValue != 9 && !countedList.contains(position)) {
                        newList.add(position)
                        countedList.add(position)
                    }
                }
            }
            basinCount += newList.size
            currentList = newList
        }

        return basinCount
    }
}

fun main() {
    //494
    val solutionOne = D9.solveOne()
    //1048128
    val solutionTwo = D9.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}