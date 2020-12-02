import utils.IoHelper

class Day02 {
    private fun getInputs() = IoHelper.getLines("d02.in")

    fun getSolution1() = getInputs().count { isValidForSolution1(it) }
    fun getSolution2() = getInputs().count { isValidForSolution2(it) }

    private fun isValidForSolution1(sample: String): Boolean {
        val chunked = sample.split(" ")
        val rule = chunked[0]
        val keyChar = chunked[1][0]
        val toVerify = chunked[2]

        val chunkedRule = rule.split("-")
        val minRule = chunkedRule[0].toInt()
        val maxRule = chunkedRule[1].toInt()

        return toVerify.count { it == keyChar } in minRule..maxRule
    }

    private fun isValidForSolution2(sample: String): Boolean {
        val chunked = sample.split(" ")
        val rule = chunked[0]
        val keyChar = chunked[1][0]
        val toVerify = chunked[2]

        val chunkedRule = rule.split("-")
        val minRule = chunkedRule[0].toInt()
        val maxRule = chunkedRule[1].toInt()

        val toVerifyChar1 = toVerify[minRule - 1]
        val toVerifyChar2 = toVerify[maxRule - 1]

        return (toVerifyChar1 == keyChar).xor(toVerifyChar2 == keyChar)
    }
}

fun main() {
    println(Day02().getSolution1())
    println(Day02().getSolution2())
}