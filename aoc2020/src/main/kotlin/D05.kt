import utils.IoHelper
import kotlin.math.ceil

class Day05 {
    private fun getInputs() = IoHelper.getLines("d05.in")

    fun getSolution1() = getInputs().map { getSeatId(it) }.max() ?: 0

    fun getSolution2(): Int {
        val ids = getInputs().map { getSeatId(it) }
        return (0..883).filter { !ids.contains(it) && ids.contains(it + 1) && ids.contains(it - 1) }[0]
    }

    private fun getSeatId(targetString: String): Int {
        val row = SeatPosition(0, 127)
        for (char in targetString.dropLast(4)) {
            calculateSeatPosition(char, row)
        }

        val rowNumber = when (targetString[6].toString()) {
            "B" -> row.end
            else -> row.start
        }

        val column = SeatPosition(0, 7)
        for (char in targetString.drop(7).dropLast(1)) {
            calculateSeatPosition(char, column)
        }
        val columnNumber = when (targetString[9].toString()) {
            "R" -> column.end
            else -> column.start
        }

        return rowNumber * 8 + columnNumber
    }

    private fun calculateSeatPosition(char: Char, seatPosition: SeatPosition) {
        when (char.toString()) {
            "B", "R" -> updateRangeToBiggerHalf(seatPosition)
            else -> updateRangeToSmallerHalf(seatPosition)
        }
    }

    private fun updateRangeToSmallerHalf(position: SeatPosition) {
        position.end = position.start + (position.end - position.start) / 2
    }

    private fun updateRangeToBiggerHalf(position: SeatPosition) {
        position.start = position.start + ceil((position.end - position.start).toDouble() / 2).toInt()
    }
}

data class SeatPosition(var start: Int, var end: Int)

fun main() {
    println(Day05().getSolution1())
    println(Day05().getSolution2())
}
