import utils.IoHelper

class Day03 {
    private fun getInputs() = IoHelper.getLines("d03.in")

    fun getSolution1(): Int {
        val inputsList = getInputs()
        val lines = inputsList.size
        val maxX = lines * 3
        val singleMaxX = inputsList.get(0).length
        val repetition = maxX / singleMaxX + 1

        var counter = 0
        inputsList.forEachIndexed { index, s ->
            val toVerify = s.repeat(repetition).get(index * 3).toString()
            if (toVerify == "#") {
                counter += 1
            }
        }
        return counter
    }

    fun getSolution2() {
        val slope11 = getSlope(1, 1)
        val slope31 = getSlope(3, 1)
        val slope51 = getSlope(5, 1)
        val slope71 = getSlope(7, 1)
        val slope12 = getSlope(1, 2)
    }

    private fun getSlope(right: Int, down: Int): Long {
        val inputsList = IoHelper.getLines("d03.in")
        val lines = inputsList.size
        val maxX = lines * (right / down)
        val singleMaxX = inputsList.get(0).length
        val repetition = maxX / singleMaxX + 1

        var counter:Long = 0
        inputsList.forEachIndexed { index, s ->
            val toVerify = s.repeat(repetition).get(index * (right / down)).toString()
            if (toVerify == "#") {
                counter += 1
            }
        }
        return counter
    }

    private fun getSlopeDown2(right: Int, down: Int): Long {
        val inputsList = IoHelper.getLines("d03.in")
        val lines = inputsList.size
        val maxX = lines * 0.5
        val singleMaxX = inputsList.get(0).length
        val repetition = maxX.toInt() / singleMaxX + 1

        var counter:Long = 0
        inputsList.forEachIndexed { index, s ->
            if (index % 2 == 0) {
                val correctIndex = index/2
                val toVerify = s.repeat(repetition).get(correctIndex).toString()
                println(toVerify+" "+index.toString())
                if (toVerify == "#") {
                    counter += 1
                }
            }

        }
        return counter
    }

    fun testD03(): Long {
        val slope11 = getSlope(1, 1)
        val slope31 = getSlope(3, 1)
        val slope51 = getSlope(5, 1)
        val slope71 = getSlope(7, 1)
        val slope12 = getSlopeDown2(1, 2)
        println(slope11)
        println(slope31)
        println(slope51)
        println(slope71)
        println(slope12)
        println(slope11 * slope31 * slope51 * slope71 * slope12)
        return slope11 * slope31 * slope51 * slope71 * slope12
    }
}

fun main() {
/*    println(Day03().getSolution1())
    println(Day03().getSolution2())*/

    println(Day03().testD03())

}