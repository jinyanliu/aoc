import utils.IoHelper

class Day01 {
    private fun getInputs() = IoHelper.getInts("d01.in")

    fun getSolution1(): Int {
        val inputs = getInputs()
        println(inputs)

        for (input1 in inputs) {
            for (input2 in inputs) {
                if (input1 + input2 == 2020) {
                    return input1 * input2
                }
            }
        }
        return 0
    }

    fun getSolution2(): Int {
        val inputs = getInputs()
        println(inputs)

        for (input1 in inputs) {
            for (input2 in inputs) {
                for (input3 in inputs) {
                    if (input1 + input2 + input3 == 2020) {
                        return input1 * input2 * input3
                    }
                }
            }
        }
        return 0
    }
}

fun main() {
    val solution1 = Day01().getSolution1()
    println(solution1)

    val solution2 = Day01().getSolution2()
    println(solution2)
}