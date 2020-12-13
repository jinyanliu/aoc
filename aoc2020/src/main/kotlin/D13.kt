import utils.IoHelper

class Day13 {
    private val inputs = IoHelper.getLines("d13.in")

    fun getSolution1(): Long {
        val departTime = inputs[0].toLong()
        val busIds = inputs[1].split(",").filter { it.toIntOrNull() != null }.map { it.toLong() }
        var currentToVerify = departTime
        while (true) {
            for (id in busIds) {
                if (currentToVerify % id == 0L) {
                    println(id)
                    println(currentToVerify)
                    println(departTime)
                    return (currentToVerify - departTime) * id
                }
            }
            currentToVerify += 1
        }
    }

    fun getSolution2Test(): Long {
        val inputsWithIndex = IoHelper.getLines("d13Test.in")[1].split(",").withIndex()
            .map { indexedValue -> indexedValue.index to indexedValue.value }
            .filter { it.second.toIntOrNull() != null }.map { it.first.toLong() to it.second.toLong() }

        println(inputsWithIndex)

        var currentToVerify = 944L
        while (true) {
            var validated = true
            for (inputWithIndex in inputsWithIndex) {
                if ((currentToVerify + inputWithIndex.first) % inputWithIndex.second != 0L) {
                    validated = false
                }
            }

            if (validated) {
                println(currentToVerify)
                return currentToVerify
            }

            currentToVerify += 1

        }
    }

    fun getSolution2(): Long {
        val inputsWithIndex =
            inputs[1].split(",").withIndex().map { indexedValue -> indexedValue.index to indexedValue.value }
                .filter { it.second.toIntOrNull() != null }.map { it.first.toLong() to it.second.toLong() }


        val resultMap = inputsWithIndex.map { it.first to arrayListOf(it.second) }.toMap().toMutableMap()
        val indexMax = inputsWithIndex.last().first
        for (i in 0..indexMax) {
            for (input in inputsWithIndex) {
                if (input.second > indexMax) continue
                if(input.first == i)continue

                var offset = 0L
                if (input.first > input.second) {
                    if (input.second != 0L) {
                        offset = input.first % input.second
                    } else {
                        offset = input.first
                    }
                }

                if (((i - offset) % input.second == 0L || (i == offset))) {
                    resultMap[i]?.add(input.second) ?: let { resultMap[i] = arrayListOf(input.second) }
                }

            }
        }

        println(resultMap.toSortedMap())

/*        var currentToVerify = 41L * 659L * 13L * 19L * 29L
        while (true) {
            var validated = true
            for (inputWithIndex in inputsWithIndex) {
                if ((currentToVerify - 41 + inputWithIndex.first) % inputWithIndex.second != 0L) {
                    validated = false
                }
            }

            if (validated) {
                println(currentToVerify - 41)
                return currentToVerify - 41
            }

            currentToVerify += 41L * 659L * 13L * 19L * 29L
        }*/

        return 0L
    }
}

fun main() {
    //115
    println(Day13().getSolution1())
    //1068781
    println(Day13().getSolution2Test())
    //756261495958122
    println(Day13().getSolution2())
}