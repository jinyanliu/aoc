import utils.IoHelper
import kotlin.math.ceil

class Day03 {
    private fun getInputs() = IoHelper.getLines("d03.in")

    fun getSolution1(): Int {
        val inputsList = getInputs()
        val lines = inputsList.size
        val maxX = lines * 3
        val singleMaxX = inputsList[0].length
        val repetition = maxX / singleMaxX + 1

        return inputsList
            .mapIndexed { index, s -> s.repeat(repetition)[index * 3].toString() }
            .count { it == "#" }
    }

    fun getSolution2(): Long {
        val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        return slopes.map { getSlopeBy(it.first, it.second) }.reduce { acc, t -> acc * t }
    }

    private fun getSlopeBy(right: Int, down: Int): Long {
        val ratio = right.toDouble() / down
        val inputsList = getInputs()
        val lines = inputsList.size
        val maxX = lines * ratio
        val singleMaxX = inputsList[0].length
        val repetition = ceil(maxX / singleMaxX).toInt()

        return inputsList
            .mapIndexed { index, s ->
                if (index % down == 0) {
                    val countedIndex = (index.toDouble() * ratio).toInt()
                    s.repeat(repetition)[countedIndex].toString()
                } else "?"
            }
            .count { it == "#" }
            .toLong()
    }
}

fun main() {
    println(Day03().getSolution1())
    println(Day03().getSolution2())
}