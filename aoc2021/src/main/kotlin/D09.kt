import utils.IoHelper

object D9 {
    private val inputs = IoHelper.getLines("d09.in")
    private val map = initMap()
    private val lowestPoints =
        map.filter { it.key.get4AdjacentValues().all { adjacentValue -> adjacentValue > it.value } }

    fun solveOne() = lowestPoints.map { it.value + 1 }.sum()

    fun solveTwo() =
        lowestPoints.map { it.key.countBasin() }.sortedDescending().subList(0, 3).reduce(Int::times)

    private fun initMap(): Map<Pair<Int, Int>, Int> {
        val map = mutableMapOf<Pair<Int, Int>, Int>()
        for (y in inputs.indices) {
            val oneLine = inputs[y]
            for (x in oneLine.indices) {
                map[x to y] = inputs[y][x].toString().toInt()
            }
        }
        return map.toMap()
    }

    private fun Pair<Int, Int>.getValue() = map[this] ?: 10

    private fun Pair<Int, Int>.get4AdjacentValues() = get4AdjacentPositions().map { it.getValue() }

    private fun Pair<Int, Int>.get4AdjacentPositions(): List<Pair<Int, Int>> {
        val x = this.first
        val y = this.second
        val up = (x) to (y - 1)
        val down = (x) to (y + 1)
        val left = (x - 1) to y
        val right = (x + 1) to y
        return listOf(up, down, left, right)
    }

    private fun Pair<Int, Int>.countBasin(): Int {
        var basinCount = 1
        var currentList = mutableListOf(this)
        val countedList = mutableListOf(this)

        while (currentList.isNotEmpty()) {
            val newList = mutableListOf<Pair<Int, Int>>()
            for (item in currentList) {
                for (position in item.get4AdjacentPositions()) {
                    val positionValue = position.getValue()
                    if (positionValue != 9 && positionValue != 10 && !countedList.contains(position)) {
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