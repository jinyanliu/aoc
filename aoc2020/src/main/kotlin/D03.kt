import utils.IoHelper

class Day03 {
    private fun getInputs() = IoHelper.getLines("d03.in")

    fun getSolution1(): Int {
        val inputsList = getInputs()
        val lines = inputsList.size
        val maxX = lines * 3
        val singleMaxX = inputsList[0].length
        val repetition = maxX / singleMaxX + 1

        var counter = 0
        inputsList.forEachIndexed { index, s ->
            val toVerify = s.repeat(repetition)[index * 3].toString()
            if (toVerify == "#") {
                counter += 1
            }
        }
        return counter
    }

    fun getSolution2(): Long {
        val slope11 = getSlopeBy(1, 1)
        val slope31 = getSlopeBy(3, 1)
        val slope51 = getSlopeBy(5, 1)
        val slope71 = getSlopeBy(7, 1)
        val slope12 = getSlopeBy(1, 2)
        return slope11 * slope31 * slope51 * slope71 * slope12
    }

    private fun getSlopeBy(right: Int, down: Int): Long {
        val ratio = right.toDouble() / down
        val inputsList = getInputs()
        val lines = inputsList.size
        val maxX = lines * ratio
        val singleMaxX = inputsList[0].length
        val repetition = maxX.toInt() / singleMaxX + 1

        var counter: Long = 0
        inputsList.forEachIndexed { index, s ->
            if (index % down == 0) {
                val countedIndex = (index.toDouble() * ratio).toInt()
                val toVerify = s.repeat(repetition)[countedIndex].toString()
                if (toVerify == "#") {
                    counter += 1
                }
            }

        }
        return counter
    }
}

fun main() {
    println(Day03().getSolution1())
    println(Day03().getSolution2())
}