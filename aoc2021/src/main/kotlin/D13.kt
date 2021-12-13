import utils.IoHelper
import utils.PrintHelper.print

object D13 {
    private val inputs = IoHelper.getSections("d13.in")
    private val dots = inputs[0].lines().map { it.split(",")[0].toInt() to it.split(",")[1].toInt() }.toSet()
    val foldInstructions = inputs[1].lines().map { it.replace("fold along ", "") }
        .map { it.split("=")[0] to it.split("=")[1].toInt() }

    fun solve(foldInstructions: List<Pair<String, Int>>, printDot: Boolean): Int {
        var set = dots
        foldInstructions.forEach {
            set = if (it.first == "y") {
                foldY(data = set, at = it.second)
            } else {
                foldX(data = set, at = it.second)
            }
        }
        if (printDot) set.print()
        return set.size
    }

    private fun foldY(data: Set<Pair<Int, Int>>, at: Int): Set<Pair<Int, Int>> = data.map { dot ->
        if (dot.second < at) {
            dot
        } else {
            dot.first to at - (dot.second - at)
        }
    }.toSet()

    private fun foldX(data: Set<Pair<Int, Int>>, at: Int): Set<Pair<Int, Int>> = data.map { dot ->
        if (dot.first < at) {
            dot
        } else {
            at - (dot.first - at) to dot.second
        }
    }.toSet()
}

fun main() {
    //755
    val solutionOne = D13.solve(foldInstructions = D13.foldInstructions.subList(0, 1), printDot = false)
    //BLKJRBAG
    val solutionTwo = D13.solve(foldInstructions = D13.foldInstructions, printDot = true)
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}