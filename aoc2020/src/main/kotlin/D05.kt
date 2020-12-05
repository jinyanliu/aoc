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
        var currentRowRange = 0 to 127
        for (char in targetString.dropLast(4)) {
            currentRowRange = when (char.toString()) {
                "B" -> updateRangeToBiggerHalf(currentRowRange)
                else -> updateRangeToSmallerHalf(currentRowRange)
            }
        }
        val row = when (targetString[6].toString()) {
            "B" -> currentRowRange.second
            else -> currentRowRange.first
        }


        var currentColumnRange = 0 to 7
        for (char in targetString.drop(7).dropLast(1)) {
            currentColumnRange = when (char.toString()) {
                "R" -> updateRangeToBiggerHalf(currentColumnRange)
                else -> updateRangeToSmallerHalf(currentColumnRange)
            }
        }

        val column = when (targetString[9].toString()) {
            "R" -> currentColumnRange.second
            else -> currentColumnRange.first
        }

        return row * 8 + column
    }

    private fun updateRangeToSmallerHalf(currentRange: Pair<Int, Int>) =
        currentRange.first to currentRange.first + (currentRange.second - currentRange.first) / 2

    private fun updateRangeToBiggerHalf(currentRange: Pair<Int, Int>) =
        currentRange.first + ceil((currentRange.second - currentRange.first).toDouble() / 2).toInt() to currentRange.second
}

fun main() {
    println(Day05().getSolution1())
    println(Day05().getSolution2())
}

object Column {
    var start = 0
    var end = 7
}

object Row {
    var start = 0
    var end = 127
}