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

        var currentToVerify = 0L
        while (true) {
            var validated = true
            for (inputWithIndex in inputsWithIndex) {
                if ((currentToVerify + inputWithIndex.first) % inputWithIndex.second != 0L) {
                    validated = false
                }
            }
            if (validated) {
                return currentToVerify
            }
            currentToVerify += 1
        }
    }

    fun getSolution2(): Long {
        val inputsWithIndex = inputs[1].split(",").withIndex()
            .map { indexedValue -> indexedValue.index to indexedValue.value }
            .filter { it.second.toIntOrNull() != null }.map { it.first.toLong() to it.second.toLong() }

        val indexMax = inputsWithIndex.last().first
        val arrangedList = arrayListOf<Pair<Long, Long>>()
        val repetitionMap = mutableMapOf<Long, ArrayList<Long>>()

        for (item in inputsWithIndex) {
            if (item.second > indexMax) {
                repetitionMap[item.first] = arrayListOf(item.second)
            } else if (item.second > item.first && item.second < indexMax) {
                arrangedList.add((item.first + item.second) to item.second)
            } else {
                arrangedList.add(item.first to item.second)
            }
        }

        for (i in 0..indexMax) {
            for (input in arrangedList) {
                val offset = input.first % input.second
                if (((i - offset) % input.second == 0L || (i == offset))) {
                    repetitionMap[i]?.add(input.second) ?: let { repetitionMap[i] = arrayListOf(input.second) }
                }
            }
        }

        println(repetitionMap.toSortedMap())

        val listOfResult = mutableListOf<Pair<Long, Long>>()
        for (item in repetitionMap.toSortedMap()) {
            listOfResult.add(item.key to item.value.reduce { a, b -> a * b })
        }
        listOfResult.sortBy { it.second }
        val biggestTimes = listOfResult.last()

        var currentToVerify = biggestTimes.second
        while (true) {
            var validated = true
            for (inputWithIndex in inputsWithIndex) {
                if ((currentToVerify - biggestTimes.first + inputWithIndex.first) % inputWithIndex.second != 0L) {
                    validated = false
                }
            }
            if (validated) {
                return currentToVerify - biggestTimes.first
            }
            currentToVerify += biggestTimes.second
        }
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