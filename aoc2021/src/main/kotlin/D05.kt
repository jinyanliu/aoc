import utils.IoHelper

object D5 {
    private val inputs = IoHelper.getLines("d05.in").map {
        it.split(" -> ").map { dot ->
            Pair(
                dot.split(",")[0],
                dot.split(",")[1]
            )
        }
    }

    private fun solve(lines: List<List<Pair<String, String>>>): Int {
        val coveredDotsList = mutableListOf<Pair<Int, Int>>()

        for (line in lines) {
            val firstX = line[0].first.toInt()
            val firstY = line[0].second.toInt()
            val secondX = line[1].first.toInt()
            val secondY = line[1].second.toInt()

            if (line.isVertical()) {
                val yList = arrayListOf(firstY, secondY)
                for (i in yList.min()!!..yList.max()!!) {
                    coveredDotsList.add(firstX to i)
                }
            } else if (line.isHorizontal()) {
                val xList = arrayListOf(firstX, secondX)
                for (i in xList.min()!!..xList.max()!!) {
                    coveredDotsList.add(i to firstY)
                }
            } else {
                var currentX = firstX
                var currentY = firstY
                coveredDotsList.add(currentX to currentY)

                if (firstX < secondX && firstY < secondY) {
                    for (i in 0 until secondX - firstX) {
                        currentX += 1
                        currentY += 1
                        coveredDotsList.add(currentX to currentY)
                    }
                }

                if (firstX < secondX && firstY > secondY) {
                    for (i in 0 until secondX - firstX) {
                        currentX += 1
                        currentY -= 1
                        coveredDotsList.add(currentX to currentY)
                    }
                }

                if (firstX > secondX && firstY < secondY) {
                    for (i in 0 until firstX - secondX) {
                        currentX -= 1
                        currentY += 1
                        coveredDotsList.add(currentX to currentY)
                    }
                }

                if (firstX > secondX && firstY > secondY) {
                    for (i in 0 until firstX - secondX) {
                        currentX -= 1
                        currentY -= 1
                        coveredDotsList.add(currentX to currentY)
                    }
                }
            }
        }
        return coveredDotsList.groupingBy { it }.eachCount().filter { it.value > 1 }.size
    }

    fun solveOne() = solve(inputs.filter { it.isVertical() || it.isHorizontal() })
    fun solveTwo() = solve(inputs)

    private fun List<Pair<String, String>>.isVertical() = this[0].first == this[1].first
    private fun List<Pair<String, String>>.isHorizontal() = this[0].second == this[1].second
}

fun main() {
    val solutionOne = D5.solveOne()
    val solutionTwo = D5.solveTwo()
    //5576
    println("One=$solutionOne")
    //18144
    println("Tw0=$solutionTwo")
}