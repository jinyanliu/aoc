import utils.IoHelper

class Day06 {
    private fun getInputs() = IoHelper.getSections("d06.in")

    fun getSolution1() = getInputs().map { it.replace("\n", "").toList().distinct().size }.sum()

    fun getSolution2() = getInputs().map { validCount(it) }.sum()

    private fun validCount(input: String): Int {
        var validCount = 0
        val toVerifyList = input.lines()
        val repeatationTimes = toVerifyList.size
        val toVerifyFirstKey = toVerifyList.get(0)
        for (char in toVerifyFirstKey) {
            var valid = true
            for (i in 0..repeatationTimes - 1) {
                if (!toVerifyList.get(i).contains(char)) {
                    valid = false
                }
            }
            if (valid) {
                validCount = validCount + 1
            }
        }
        return validCount
    }
}

fun main() {
    println(Day06().getSolution1())
    println(Day06().getSolution2())
}