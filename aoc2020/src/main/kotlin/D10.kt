import utils.IoHelper

class Day10 {
    private val inputs = IoHelper.getInts("d10.in")

    fun getSolution1(): Int {
        val deviceRate = (inputs.max() ?: error("error 1")) + 3
        val newList = inputs.toMutableList()
        newList.add(deviceRate)

        var currentOutput = 0
        var oneJoltDifferenceCounter = 0
        var threeJoltDifferenceCounter = 0

        while (currentOutput < deviceRate) {
            if (newList.contains(currentOutput + 1)) {
                currentOutput += 1
                oneJoltDifferenceCounter += 1
            } else if (newList.contains(currentOutput + 3)) {
                currentOutput += 3
                threeJoltDifferenceCounter += 1
            }
        }

        return oneJoltDifferenceCounter * threeJoltDifferenceCounter
    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day10().getSolution1())
    println(Day10().getSolution2())
}