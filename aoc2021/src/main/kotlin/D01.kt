import utils.IoHelper

class Day01 {
    private val inputs = IoHelper.getInts("d01.in")

    fun getSolution1() = getSolution(inputs)

    fun getSolution2(): Int {
        val newList = mutableListOf<Int>()
        for (i in 0..inputs.size - 3) {
            val newItem = inputs[i] + inputs[i + 1] + inputs[i + 2]
            newList.add(newItem)
        }
        return getSolution(newList)
    }

    private fun getSolution(inputs: List<Int>): Int {
        var result = 0
        for (i in 1 until inputs.size) {
            if (inputs[i] > inputs[i - 1]) {
                result += 1
            }
        }

        return result
    }
}

fun main() {
    println(Day01().getSolution1())
    println(Day01().getSolution2())
}