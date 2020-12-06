import utils.IoHelper

class Day06 {
    private fun getInputs() = IoHelper.getSections("d06.in")

    fun getSolution1() = getInputs().map { it.replace("\n", "").toList().distinct().size }.sum()

    fun getSolution2() = getInputs()
        .map { it.lines().sortedBy { line -> line.length } }
        .map { it[0].count { char -> it.all { toVerify -> toVerify.contains(char) } } }
        .sum()
}

fun main() {
    println(Day06().getSolution1())
    println(Day06().getSolution2())
}