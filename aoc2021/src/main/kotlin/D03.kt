import utils.IoHelper

object D03 {
    private val inputs = IoHelper.getLines("d03.in")

    fun solveOne(): Int {
        var mostArray = ""
        var leastArray = ""

        for (i in inputs[0].indices) {
            val newList = inputs.map { it[i] }
            val oneCount = newList.count { it.toString() == "1" }
            val zeroCount = newList.count { it.toString() == "0" }
            if (oneCount > zeroCount) {
                mostArray += "1"
                leastArray += "0"
            } else {
                mostArray += "0"
                leastArray += "1"
            }
        }

        val most = mostArray.toInt(2)
        val least = leastArray.toInt(2)

        return most * least
    }

    fun solveTwo(): Int {
        val most = solve { oneCount, zeroCount -> oneCount >= zeroCount }
        val least = solve { oneCount, zeroCount -> oneCount < zeroCount }
        return most * least
    }

    private fun solve(comparison: (Int, Int) -> Boolean): Int {
        var currentList = inputs.toMutableList()
        for (i in inputs[0].indices) {
            val newList = currentList.map { it[i] }
            val oneCount = newList.count { it.toString() == "1" }
            val zeroCount = newList.count { it.toString() == "0" }
            currentList = if (comparison(oneCount, zeroCount)) {
                currentList.filter { it[i].toString() == "1" }.toMutableList()
            } else {
                currentList.filter { it[i].toString() == "0" }.toMutableList()
            }
            if (currentList.size == 1) {
                break
            }
        }
        return currentList[0].toInt(2)
    }
}

fun main() {
    val solutionOne = D03.solveOne()
    val solutionTwo = D03.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}