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
            if (containsDiffOne(newList, currentOutput)) {
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
        val newList = inputs.toMutableList()
        newList.add(0)
        newList.add(deviceRate)
        newList.sort()

        println("newList=" + newList.toString())

        val liiist = arrayListOf(
            0,
            1,
            2,
            3,
            4,
            7,
            8,
            11,
            12,
            13,
            16,
            17,
            18,
            19,
            20,
            23,
            26,
            27,
            28,
            31,
            34,
            35,
            36,
            37,
            40,
            41,
            42,
            43,
            46,
            49
        )
        val originList = arrayListOf(
            0,
            1,
            2,
            3,
            4,
            7,
            8,
            11,
            12,
            13,
            16,
            17,
            18,
            19,
            20,
            23,
            26,
            27,
            28,
            31,
            34,
            35,
            36,
            37,
            40,
            41,
            42,
            43,
            46,
            49
        )

        val result: Long = 3136L * 7 * 7 * 4 * 7 * 7 * 2 * 7 * 7 * 4 * 2 * 4 * 2 * 7
        println("RESULT" + result.toString())

        val testList = liiist

        var keys = arrayListOf(0)
        while (!keys.all { it == testList.last() }) {
            val newKeys = arrayListOf<Int>()
            for (key in keys) {
                if (key == testList.last()) {
                    newKeys.add(key)

                } else {

                    if (testList.contains(key + 1)) {
                        newKeys.add(key + 1)
                    }
                    if (testList.contains(key + 2)) {
                        newKeys.add(key + 2)
                    }
                    if (testList.contains(key + 3)) {
                        newKeys.add(key + 3)
                    }

                }

            }
            keys = newKeys
        }
        println(keys.size)
    }

    private fun containsDiffOne(newList: MutableList<Int>, item: Int) =
        newList.contains(item + 1)
}

fun main() {
    println(Day10().getSolution1())
    //1322306994176
    println(Day10().getSolution2())
}