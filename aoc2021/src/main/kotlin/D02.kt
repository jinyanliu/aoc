import utils.IoHelper

object D02 {
    private val inputs = IoHelper.getLines("d02.in")

    fun solveOne(): Int {
        val forwardList = mutableListOf<Int>()
        val upList = mutableListOf<Int>()
        val downList = mutableListOf<Int>()

        solve(
            forwardAction = { value ->
                forwardList.add(value)
            },
            upAction = { value ->
                upList.add(value)
            },
            downAction = { value ->
                downList.add(value)
            }
        )

        val horizontal = forwardList.sum()
        val vertical = downList.sum() - upList.sum()

        return horizontal * vertical
    }

    fun solveTwo(): Int {
        var depth = 0
        var aim = 0

        val forwardList = mutableListOf<Int>()

        solve(
            forwardAction = { value ->
                forwardList.add(value)
                depth += (aim * value)
            },
            upAction = { value ->
                aim -= value
            },
            downAction = { value ->
                aim += value
            }
        )

        val horizontal = forwardList.sum()
        val vertical = depth

        return horizontal * vertical
    }

    private fun solve(forwardAction: (Int) -> Unit, upAction: (Int) -> Unit, downAction: (Int) -> Unit) {
        inputs.forEach {
            val instructions = it.split(" ")
            val direction = instructions[0]
            val value = instructions[1].toInt()
            when (direction) {
                "forward" -> {
                    forwardAction(value)
                }
                "up" -> {
                    upAction(value)
                }
                "down" -> {
                    downAction(value)
                }
            }
        }
    }
}

fun main() {
    //1924923
    val solutionOne = D02.solveOne()
    //1982495697
    val solutionTwo = D02.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}