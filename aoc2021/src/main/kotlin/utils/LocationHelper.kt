package utils

object LocationHelper {

    fun getMaxX(map: Map<Pair<Int, Int>, Int>) = map.map { it.key.first }.max()!!
    fun getMaxY(map: Map<Pair<Int, Int>, Int>) = map.map { it.key.second }.max()!!

    fun Pair<Int, Int>.get4AdjacentValues(
        map: Map<Pair<Int, Int>, Int>,
        maxX: Int,
        maxY: Int
    ) = get4AdjacentPositions(maxX, maxY).map { map[it]!! }

    fun Pair<Int, Int>.get4AdjacentPositions(maxX: Int, maxY: Int): List<Pair<Int, Int>> {
        val x = this.first
        val y = this.second
        val up = (x) to (y - 1)
        val down = (x) to (y + 1)
        val left = (x - 1) to y
        val right = (x + 1) to y
        return listOf(up, down, left, right).filter {
            it.first in 0..maxX && it.second in 0..maxY
        }
    }

    fun Pair<Int, Int>.getAllAdjacentValues(
        map: Map<Pair<Int, Int>, Int>,
        maxX: Int,
        maxY: Int
    ) = getAllAdjacentPositions(maxX, maxY).map { map[it]!! }

    fun Pair<Int, Int>.getAllAdjacentPositions(maxX: Int, maxY: Int): List<Pair<Int, Int>> {
        val x = this.first
        val y = this.second
        val up = (x) to (y - 1)
        val down = (x) to (y + 1)
        val left = (x - 1) to y
        val right = (x + 1) to y
        val upRight = (x + 1) to (y - 1)
        val upLeft = (x - 1) to (y - 1)
        val downRight = (x + 1) to (y + 1)
        val downLeft = (x - 1) to (y + 1)

        return listOf(up, down, left, right, upRight, upLeft, downRight, downLeft).filter {
            it.first in 0..maxX && it.second in 0..maxY
        }
    }
}