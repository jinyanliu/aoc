import utils.IoHelper

object D01 {
    private val inputs = IoHelper.getSections("d01.in")

    fun solveOne() = inputs.map { it.lines() }.map { it.map { it.toInt() }.sum() }.max()

    fun solveTwo() = inputs.map { it.lines() }.map { it.map { it.toInt() }.sum() }.sortedDescending().take(3).sum()
}

fun main() {
    val solutionOne = D01.solveOne()
    val solutionTwo = D01.solveTwo()
    //68923
    println("One=$solutionOne")
    //200044
    println("Tw0=$solutionTwo")
}