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
        var currentRange = 0 to 127
        for (char in targetString.dropLast(3)) {
            currentRange = when (char.toString()) {
                "B" -> getBiggerHalfStartingPoint(currentRange) to currentRange.second
                else -> currentRange.first to currentRange.first + (currentRange.second - currentRange.first) / 2
            }
        }
        val row = when (targetString.get(6).toString()) {
            "B" -> currentRange.second
            else -> currentRange.first
        }


        var currentColumnRange = 0 to 7
        for (char in targetString.drop(7)) {
            currentColumnRange = when (char.toString()) {
                "R" -> getBiggerHalfStartingPoint(currentColumnRange) to currentColumnRange.second
                else -> currentColumnRange.first to currentColumnRange.first + (currentColumnRange.second - currentColumnRange.first) / 2
            }
        }

        val column = when (targetString[9].toString()) {
            "R" -> currentColumnRange.second
            else -> currentColumnRange.first
        }

        return row * 8 + column
    }

    private fun getBiggerHalfStartingPoint(currentRange: Pair<Int, Int>) =
        currentRange.first + ceil((currentRange.second - currentRange.first).toDouble() / 2).toInt()
}

fun main() {
    println(Day05().getSolution1())
    println(Day05().getSolution2())
}