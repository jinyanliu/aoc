import utils.IoHelper

class Day05Alt {
    private fun getInputs() = IoHelper.getLines("d05.in")

    private val sortedIds = getInputs().map { getSeatId(it) }.sorted()

    fun getSolution1() = sortedIds.last()

    fun getSolution2() = (sortedIds[0]..sortedIds.last()).asSequence()
        .filter { !sortedIds.contains(it) && sortedIds.contains(it + 1) && sortedIds.contains(it - 1) }.first()

    private fun getSeatId(instructionString: String) = instructionString.replace("B", "1")
        .replace("R", "1")
        .replace("F", "0")
        .replace("L", "0")
        .toInt(2)
}

fun main() {
    println(Day05Alt().getSolution1())
    println(Day05Alt().getSolution2())
}