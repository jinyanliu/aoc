import utils.IoHelper
import kotlin.math.pow

class Day14 {
    private val inputs = IoHelper.getLines("d14.in")

    private fun getMaskFromString(it: String) = it.split(" = ")[1]

    private fun getInputValue(input: String) = input.split(" = ")[1].toLong()
    private fun getCharArrayFromInputValue(input: String) =
        getInputValue(input).toString(2).padStart(36, '0').toCharArray()

    private fun getInputAddress(input: String) = input.split("[")[1].split("]")[0].toLong()
    private fun getCharArrayFromInputAddress(input: String) =
        getInputAddress(input).toString(2).padStart(36, '0').toCharArray()

    private fun getRelevantMaskIndexMap(input: String, relevantChar: Pair<Char, Char>): MutableMap<Int, Char> {
        val map = mutableMapOf<Int, Char>()
        getMaskFromString(input).withIndex().forEach {
            if (it.value == relevantChar.first || it.value == relevantChar.second) {
                map[it.index] = it.value
            }
        }
        return map
    }

    fun getSolution1(): Long {
        var mapOfCurrentMask = mutableMapOf<Int, Char>()
        val mapOfStorage = mutableMapOf<Long, Long>()
        inputs.forEach { input ->
            if (input.contains("mask")) {
                mapOfCurrentMask = getRelevantMaskIndexMap(input, '0' to '1')
            } else {
                val chars = getCharArrayFromInputValue(input)
                mapOfCurrentMask.forEach {
                    chars[it.key] = it.value
                }
                mapOfStorage[getInputAddress(input)] = String(chars).toLong(2)
            }
        }
        return mapOfStorage.values.sum()
    }

    fun getSolution2(): Long {
        var mapOfCurrentMask = mutableMapOf<Int, Char>()
        val mapOfStorage = mutableMapOf<Long, Long>()
        inputs.forEach { input ->
            if (input.contains("mask")) {
                mapOfCurrentMask = getRelevantMaskIndexMap(input, 'X' to '1')
            } else {
                val chars = getCharArrayFromInputAddress(input)
                mapOfCurrentMask.forEach {
                    chars[it.key] = it.value
                }

                val charsCount = chars.count { it.toString() == "X" }
                val variationCount = 2.0.pow(charsCount).toInt()

                var resultList = mutableListOf<String>()
                repeat(variationCount) {
                    resultList.add(String(chars))
                }

                var currentStep = variationCount
                while (currentStep % 2 == 0) {
                    val currentList = arrayListOf<String>()
                    currentStep /= 2
                    val subListToZero = arrayListOf<String>()
                    val subListToOne = arrayListOf<String>()
                    repeat((variationCount / currentStep / 2)) {
                        subListToZero.addAll(resultList.subList(0, currentStep))
                        subListToOne.addAll(resultList.subList(currentStep, 2 * currentStep))
                        if (resultList.size != 0) {
                            resultList = resultList.subList(2 * currentStep, resultList.size)
                        }
                    }
                    for (item in subListToZero) {
                        val charArray = item.toCharArray()
                        for (char in charArray.withIndex()) {
                            if (char.value == 'X') {
                                charArray[char.index] = '0'
                                break
                            }
                        }
                        currentList.add(String(charArray))
                    }
                    for (item in subListToOne) {
                        val charArray = item.toCharArray()
                        for (char in charArray.withIndex()) {
                            if (char.value == 'X') {
                                charArray[char.index] = '1'
                                break
                            }
                        }
                        currentList.add(String(charArray))
                    }
                    resultList = currentList
                }

                val inputValue = getInputValue(input)
                resultList.map { it.toLong(2) }.forEach {
                    mapOfStorage[it] = inputValue
                }
            }
        }
        return mapOfStorage.values.sum()
    }

    fun getSolution2Alt(): Long {
        var mapOfCurrentMask = mutableMapOf<Int, Char>()
        val mapOfStorage = mutableMapOf<Long, Long>()
        inputs.forEach { input ->
            if (input.contains("mask")) {
                mapOfCurrentMask = getRelevantMaskIndexMap(input, 'X' to '1')
            } else {
                val chars = getCharArrayFromInputAddress(input)
                val arrayOfXIndex = arrayListOf<Int>()
                mapOfCurrentMask.forEach {
                    chars[it.key] = it.value
                    if (it.value == 'X') {
                        arrayOfXIndex.add(it.key)
                    }
                }

                val xCount = chars.count { it == 'X' }
                val variationCount = 2.0.pow(xCount).toInt()

                val combinationResultList = arrayListOf<Long>()
                for (i in 0 until variationCount) {
                    val newChars = chars.copyOf()
                    val charArray = i.toString(2).padStart(xCount, '0').toCharArray()
                    charArray.withIndex().forEach {
                        if (it.value == '1') {
                            newChars[arrayOfXIndex[it.index]] = '1'
                        } else {
                            newChars[arrayOfXIndex[it.index]] = '0'
                        }
                    }
                    combinationResultList.add(String(newChars).toLong(2))
                }

                val inputValue = getInputValue(input)
                combinationResultList.forEach { mapOfStorage[it] = inputValue }
            }
        }
        return mapOfStorage.values.sum()
    }
}

fun main() {
    //17765746710228
    println(Day14().getSolution1())
    //4401465949086
    println(Day14().getSolution2())
    println(Day14().getSolution2Alt())
}