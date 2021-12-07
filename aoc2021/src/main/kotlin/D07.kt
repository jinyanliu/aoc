import utils.IoHelper
import kotlin.math.abs

object D7 {
    private val inputs = IoHelper.getRawContent("d07.in").split(",").map { it.toInt() }.sorted().groupBy { it }
        .mapValues { it.value.size }.toMap()

    private fun solve(calculate: (Int, Int, Int) -> Int): Int {
        var min = 0
        for (moveToPosition in inputs.keys.min()!!..inputs.keys.max()!!) {
            val newList = inputs.map {
                if (it.key == moveToPosition) {
                    0
                } else {
                    calculate(moveToPosition, it.key, it.value)
                }
            }

            val currentMin = newList.sum()
            if (moveToPosition == inputs.keys.min()) {
                min = currentMin
            }
            if (min > currentMin) {
                min = currentMin
            }
        }
        return min
    }

    fun solveOne() = solve(
        calculate = { moveToPosition, currentPosition, count ->
            abs(moveToPosition - currentPosition) * count
        }
    )

    fun solveTwo() = solve(
        calculate = { moveToPosition, currentPosition, count ->
            (1..abs(moveToPosition - currentPosition)).sum() * count
        }
    )
}

fun main() {
    //352997
    val solutionOne = D7.solveOne()
    //101571302
    val solutionTwo = D7.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}