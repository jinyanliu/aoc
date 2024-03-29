import utils.IoHelper

object D1 {
    private fun getInputs() = IoHelper.getInts("d01.in")
    fun solveOne(): Int = getInputs().zipWithNext().count { it.second > it.first }
    fun solveTwo(): Int = getInputs().windowed(size = 3).map { it.sum() }.zipWithNext().count { it.second > it.first }
}

fun main() {
    //1390
    val solutionOne = D1.solveOne()
    //1457
    val solutionTwo = D1.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}