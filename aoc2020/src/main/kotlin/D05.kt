import utils.IoHelper
import kotlin.math.ceil

class Day05 {
    private fun getInputs() = IoHelper.getLines("d05.in")

    fun getSolution1() = getInputs().map { getSeatId(it) }.max() ?: 0

    fun getSolution2(): Int {
        val ids = getInputs().map { getSeatId(it) }
        return (0..883).filter { !ids.contains(it) && ids.contains(it + 1) && ids.contains(it - 1) }[0]
    }

    private fun getSeatId(instructionString: String): Int {
        val row = Range(0, 127)
        val column = Range(0, 7)
        for (char in instructionString.dropLast(3)) { calculateSeatRange(char, row) }
        for (char in instructionString.drop(7)) { calculateSeatRange(char, column) }
        return row.start * 8 + column.start
    }

    private fun calculateSeatRange(char: Char, range: Range) = when (char.toString()) {
        "B", "R" -> updateRangeToBiggerHalf(range)
        else -> updateRangeToSmallerHalf(range)
    }

    private fun updateRangeToSmallerHalf(position: Range) {
        position.end = position.start + (position.end - position.start) / 2
    }

    private fun updateRangeToBiggerHalf(position: Range) {
        position.start = position.start + ceil((position.end - position.start).toDouble() / 2).toInt()
    }
}

data class Range(var start: Int, var end: Int)

fun main() {
    println(Day05().getSolution1())
    println(Day05().getSolution2())
}
