import utils.IoHelper

class Day06 {
    private fun getInputs() = IoHelper.getSections("d06.in")

    fun getSolution1() = getInputs().map { it.replace("\n", "").toList().distinct().size }.sum()

    fun getSolution2() = getInputs().map { validCount(it) }.sum()

    private fun validCount(input: String): Int {
        val toVerifyList = input.lines()
        val toVerifyShortestItem = toVerifyList.sortedBy { it.length }[0]
        return toVerifyShortestItem.count { toVerifyList.all { toVerify -> toVerify.contains(it) } }
    }
}

fun main() {
    println(Day06().getSolution1())
    println(Day06().getSolution2())
}