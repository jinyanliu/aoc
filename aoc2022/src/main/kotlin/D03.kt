import utils.IoHelper

object D03 {
    private val inputs = IoHelper.getLines("d03.in")

    fun solveOne() = inputs.map { getPriority(it) }.sum()

    fun solveTwo() = inputs.chunked(3).map { getPriority2(it) }.sum()

    private fun getPriority(string: String): Int {
        val halfLength = string.length / 2
        val first = string.take(halfLength)
        val second = string.takeLast(halfLength)
        val sameChar = first.toCharArray().intersect(second.toCharArray().toList().toSet()).first()
        return getNumber(sameChar)
    }

    private fun getPriority2(items: List<String>): Int {
        val sameChar = items[0].toCharArray().intersect(items[1].toCharArray().toList().toSet())
            .intersect(items[2].toCharArray().toList().toSet()).first()
        return getNumber(sameChar)
    }

    private fun getNumber(char: Char) = if (char.isLowerCase()) {
        char.toByte().toInt() - 96
    } else {
        char.toByte().toInt() - 38
    }
}

fun main() {
    val solutionOne = D03.solveOne()
    val solutionTwo = D03.solveTwo()
    //7878
    println("One=$solutionOne")
    //2760
    println("Tw0=$solutionTwo")
}