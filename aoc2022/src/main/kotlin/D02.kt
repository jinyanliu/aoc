import utils.IoHelper

object D02 {
    private val inputs = IoHelper.getLines("d02.in")

    private const val win = 6
    private const val draw = 3
    private const val lose = 0
    private const val x = 1
    private const val y = 2
    private const val z = 3
    private const val rock = 1
    private const val paper = 2
    private const val scissors = 3

    fun solveOne() = inputs.map { playOne(it.split(" ")[0], it.split(" ")[1]) }.sum()

    fun solveTwo() = inputs.map { playTwo(it.split(" ")[0], it.split(" ")[1]) }.sum()

    private fun playOne(elf: String, me: String): Int {
        return when (elf) {
            "A" -> when (me) {
                "X" -> x + draw
                "Y" -> y + win
                "Z" -> z + lose
                else -> 0
            }
            "B" -> when (me) {
                "X" -> x + lose
                "Y" -> y + draw
                "Z" -> z + win
                else -> 0
            }
            "C" -> when (me) {
                "X" -> x + win
                "Y" -> y + lose
                "Z" -> z + draw
                else -> 0
            }
            else -> 0
        }
    }

    private fun playTwo(elf: String, result: String): Int {
        return when (elf) {
            "A" -> when (result) {
                "X" -> scissors + lose
                "Y" -> rock + draw
                "Z" -> paper + win
                else -> 0
            }
            "B" -> when (result) {
                "X" -> rock + lose
                "Y" -> paper + draw
                "Z" -> scissors + win
                else -> 0
            }
            "C" -> when (result) {
                "X" -> paper + lose
                "Y" -> scissors + draw
                "Z" -> rock + win
                else -> 0
            }
            else -> 0
        }
    }
}

fun main() {
    val solutionOne = D02.solveOne()
    val solutionTwo = D02.solveTwo()
    //11475
    println("One=$solutionOne")
    //16862
    println("Tw0=$solutionTwo")
}