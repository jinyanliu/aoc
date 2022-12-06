import utils.IoHelper

object D04 {
    private val inputs = IoHelper.getLines("d04.in")

    fun solveOne() = inputs.map { count(it) }.sum()

    fun solveTwo() = inputs.map { count2(it) }.sum()

    private fun count(string: String): Int {
        val pair = getTwoPairs(string)
        if (pair.first.first <= pair.second.first && pair.first.second >= pair.second.second) {
            return 1
        }
        if (pair.second.first <= pair.first.first && pair.second.second >= pair.first.second) {
            return 1
        }
        return 0
    }

    private fun count2(string: String): Int {
        val pair = getTwoPairs(string)
        if (pair.first.second < pair.second.first) {
            return 0
        }
        if (pair.second.second < pair.first.first) {
            return 0
        }
        return 1
    }

    private fun getTwoPairs(string: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val first = string.split(",")[0]
        val second = string.split(",")[1]
        val firstPair = first.split("-")[0].toInt() to first.split("-")[1].toInt()
        val secondPair = second.split("-")[0].toInt() to second.split("-")[1].toInt()
        return firstPair to secondPair
    }
}

fun main() {
    val solutionOne = D04.solveOne()
    val solutionTwo = D04.solveTwo()
    //464
    println("One=$solutionOne")
    //770
    println("Tw0=$solutionTwo")
}