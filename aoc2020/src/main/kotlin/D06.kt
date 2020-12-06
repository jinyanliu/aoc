import utils.IoHelper

class Day06 {
    private fun getInputs() = IoHelper.getSections("d06.in")

    fun getSolution1() = getInputs().map { it.replace("\n", "").toList().distinct().size }.sum()

    fun getSolution2() = getInputs().map { it.lines() }.map { charInEveryItemCount(it) }.sum()

    private fun charInEveryItemCount(toVerifyList: List<String>): Int {
        val toVerifyShortestItem = toVerifyList.sortedBy { it.length }[0]
        return toVerifyShortestItem.count { toVerifyList.all { toVerify -> toVerify.contains(it) } }
    }
}

fun main() {
    println(Day06().getSolution1())
    println(Day06().getSolution2())
}