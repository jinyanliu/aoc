import utils.IoHelper
import java.util.*

object D10 {
    private val inputs = IoHelper.getLines("d10.in")
    private val leftGroup: List<Char> = listOf('[', '(', '{', '<')

    fun solve(): Pair<Long, Long> {
        var task1Score = 0L
        val task2ScoreList = mutableListOf<Long>()

        for (line in inputs) {
            val queue = ArrayDeque<Char>()

            for (item in line) {
                if (leftGroup.contains(item)) {
                    queue.add(item)
                } else {
                    if (queue.last() == item.left()) {
                        queue.removeLast()
                    } else {
                        //Corrupted
                        queue.clear()
                        task1Score += item.score()
                        break
                    }
                }
            }

            if (queue.isNotEmpty()) {
                //Incomplete
                var task2LineScore = 0L
                val completion: List<Char> = queue.reverse()
                for (char in completion) {
                    task2LineScore = task2LineScore * 5 + char.score2()
                }
                task2ScoreList.add(task2LineScore)
            }
        }

        val sortedScoreList = task2ScoreList.sorted()
        val task2Result = sortedScoreList[(sortedScoreList.size - 1) / 2]
        return task1Score to task2Result
    }

    private fun Char.right() = when (this) {
        '[' -> ']'
        '(' -> ')'
        '{' -> '}'
        '<' -> '>'
        else -> throw IllegalStateException("Check your code!")
    }

    private fun Char.left() = when (this) {
        ']' -> '['
        ')' -> '('
        '}' -> '{'
        '>' -> '<'
        else -> throw IllegalStateException("Check your code!")
    }

    private fun Char.score() = when (this) {
        ']' -> 57
        ')' -> 3
        '}' -> 1197
        '>' -> 25137
        else -> throw IllegalStateException("Check your code!")
    }

    private fun Char.score2() = when (this) {
        ']' -> 2
        ')' -> 1
        '}' -> 3
        '>' -> 4
        else -> throw IllegalStateException("Check your code!")
    }

    private fun ArrayDeque<Char>.reverse() = this.map { it.right() }.reversed()
}

fun main() {
    //166191
    val solutionOne = D10.solve().first
    //1152088313
    val solutionTwo = D10.solve().second
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}