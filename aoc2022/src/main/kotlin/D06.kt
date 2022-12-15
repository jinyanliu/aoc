import utils.IoHelper

object D06 {
    private val inputs = IoHelper.getRawContent("d06.in")

    fun solveOne() = solve(4)

    fun solveTwo() = solve(14)

    private fun solve(count: Int): Int {
        for (i in inputs.indices) {
            val list = mutableListOf<Char>()
            for (y in 0 until count) {
                list.add(inputs[i + y])
            }
            if (list.toSet().size == count) {
                return i + count
            }
        }
        return 0
    }
}

fun main() {
    val solutionOne = D06.solveOne()
    val solutionTwo = D06.solveTwo()
    //1779
    println("One=$solutionOne")
    //2635
    println("Tw0=$solutionTwo")
}