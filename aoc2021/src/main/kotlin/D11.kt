import utils.IoHelper
import utils.LocationHelper.getAllAdjacentPositions
import utils.LocationHelper.getMaxX
import utils.LocationHelper.getMaxY

object D11 {
    private val inputs = IoHelper.getMap("d11.in")
    private val maxX = getMaxX(inputs)
    private val maxY = getMaxY(inputs)

    fun solve(): Pair<Int, Int> {
        val map = inputs.toMutableMap()
        // Solution1 to Solution2
        var resultPair = 0 to 0

        var totalFlashedCount = 0
        var step = 0
        while (true) {
            step += 1
            map.forEach { map[it.key] = it.value + 1 }
            val flashed = map.filter { it.value > 9 }.map { it.key }.toMutableList()
            var newFlashes = map.filter { it.value > 9 }.map { it.key }

            while (newFlashes.isNotEmpty()) {
                newFlashes.forEach { newFlash ->
                    newFlash.getAllAdjacentPositions(maxX, maxY).forEach {
                        map[it] = map[it]!! + 1
                    }
                }
                val thisRoundFlashes = map.filter { it.value > 9 && !flashed.contains(it.key) }.map { it.key }
                newFlashes = thisRoundFlashes
                flashed.addAll(thisRoundFlashes)
            }

            flashed.forEach { map[it] = 0 }
            totalFlashedCount += flashed.size

            if (step == 100) {
                resultPair = totalFlashedCount to resultPair.second
            }
            if (flashed.size == 100) {
                resultPair = resultPair.first to step
            }
            if (resultPair.first != 0 && resultPair.second != 0) {
                return resultPair
            }
        }
    }
}

fun main() {
    //1702
    val solutionOne = D11.solve().first
    //251
    val solutionTwo = D11.solve().second
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}