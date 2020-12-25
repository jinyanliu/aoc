import utils.IoHelper

class Day25 {
    private val inputs = IoHelper.getLines("d25.in")
    private val publicKeys = inputs[0].toLong() to inputs[1].toLong()

    private fun whatIsYourLoopSize(publicKey: Long): Int {
        val subjectNumber = 7L
        var value = 1L
        var loopSize = 0
        while (value != publicKey) {
            loopSize += 1
            value *= subjectNumber
            value %= 20201227
        }
        return loopSize
    }

    fun getSolution1(): Long {
        val cardLoopSize = whatIsYourLoopSize(publicKeys.first)
        val doorLoopSize = whatIsYourLoopSize(publicKeys.second)

        var value = 1L
        repeat(doorLoopSize) {
            value *= publicKeys.first
            value %= 20201227
        }
        return value
    }

    fun getSolution2() {
    }
}

fun main() {
    //9714832
    println(Day25().getSolution1())
    println(Day25().getSolution2())
}