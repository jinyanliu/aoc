import utils.IoHelper

class Day10 {
    private val inputs = IoHelper.getInts("d10.in")

    private val deviceRate = (inputs.max() ?: error("error 1")) + 3

    fun getSolution1(): Int {
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

    fun getSolution2():Long {
        val newList = inputs.toMutableList()
        newList.add(0)
        newList.add(deviceRate)
        newList.sort()

        println("newList=" + newList.toString())


        val multiplyList = arrayListOf<Long>()
        var toVerifyList = newList.toList()
        while (toVerifyList.isNotEmpty()) {
            if (toVerifyList[toVerifyList.size - 1] - toVerifyList[toVerifyList.size - 1 - 4] == 4) {
                toVerifyList = toVerifyList.dropLast(5)
                multiplyList.add(7L)
            } else if (toVerifyList[toVerifyList.size - 1] - toVerifyList[toVerifyList.size - 1 - 3] == 3) {
                toVerifyList=toVerifyList.dropLast(4)
                multiplyList.add(4L)
            } else if (toVerifyList[toVerifyList.size - 1] - toVerifyList[toVerifyList.size - 1 - 2] == 2) {
                toVerifyList=toVerifyList.dropLast(3)
                multiplyList.add(2L)
            } else {
                toVerifyList=toVerifyList.dropLast(1)
            }
        }
        println(multiplyList.toString())
        return multiplyList.reduce{ accumulator, element -> accumulator * element }
    }

    fun getTestSolution2():Int{
        val newList = IoHelper.getInts("d10Test2.in").toMutableList()
        val deviceRate = (newList.max() ?: error("error 1")) + 3
        newList.add(0)
        newList.add(deviceRate)
        newList.sort()
        println(newList.toString())

        var keys = arrayListOf(0)
        while (!keys.all { it == newList.last() }) {
            val newKeys = arrayListOf<Int>()
            for (key in keys) {
                if (key == newList.last()) {
                    newKeys.add(key)
                } else {
                    if (newList.contains(key + 1)) {
                        newKeys.add(key + 1)
                    }
                    if (newList.contains(key + 2)) {
                        newKeys.add(key + 2)
                    }
                    if (newList.contains(key + 3)) {
                        newKeys.add(key + 3)
                    }
                }

            }
            keys = newKeys
        }
        return keys.size
    }
}

fun main() {
    println(Day10().getSolution1())
    println(Day10().getSolution2())
    println(Day10().getTestSolution2())
}