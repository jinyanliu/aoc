import utils.IoHelper
import kotlin.math.pow

class Day14 {
    private val inputs = IoHelper.getLines("d14.in")

    private fun getMaskFromString(it: String) = it.split(" = ")[1]

    private fun getInputValue(input: String) = input.split(" = ")[1].toLong()

    private fun getInputAddress(input: String) = input.split("[")[1].split("]")[0].toLong()

    fun getSolution1(): Long {
        var mapOfCurrentMask = mutableMapOf<Int, Char>()
        val mapOfStorage = mutableMapOf<Long, Long>()
        inputs.forEach { input ->
            if (input.contains("mask")) {
                val mapOfNewMask = mutableMapOf<Int, Char>()
                getMaskFromString(input).withIndex().forEach {
                    if (it.value.toString() == "0" || it.value.toString() == "1") {
                        mapOfNewMask[it.index] = it.value
                    }
                }
                mapOfCurrentMask = mapOfNewMask
            } else {
                val address = getInputAddress(input)
                val value = getInputValue(input)
                var bitNumber = value.toString(2)
                while (bitNumber.length != 36) {
                    bitNumber = "0$bitNumber"
                }
                val chars = bitNumber.toCharArray()
                mapOfCurrentMask.forEach {
                    chars[it.key] = it.value
                }
                mapOfStorage[address] = String(chars).toLong(2)
            }
        }
        return mapOfStorage.values.sum()
    }

    fun getSolution2(): Long {
        var mapOfCurrentMask = mutableMapOf<Int, Char>()
        val mapOfStorage = mutableMapOf<Long, Long>()
        inputs.forEach {
            if (it.contains("mask")) {
                val mapOfNewMask = mutableMapOf<Int, Char>()
                getMaskFromString(it).withIndex().forEach {
                    if (it.value.toString() == "X" || it.value.toString() == "1") {
                        mapOfNewMask[it.index] = it.value
                    }
                }
                mapOfCurrentMask = mapOfNewMask
            } else {
                val storeValue = getInputValue(it)
                val address = getInputAddress(it)
                var bitNumber = address.toString(2)
                while (bitNumber.length != 36) {
                    bitNumber = "0$bitNumber"
                }
                val chars = bitNumber.toCharArray()
                mapOfCurrentMask.forEach {
                    chars[it.key] = it.value
                }
                val charsCount = chars.count { it.toString() == "X" }
                var resultList = mutableListOf<String>()
                val variationCount = 2.0.pow(charsCount)
                repeat(2.0.pow(charsCount).toInt()) {
                    resultList.add(String(chars))
                }
                var currentStep = 2.0.pow(charsCount)
                while (currentStep % 2 == 0.0) {
                    val currentList = arrayListOf<String>()
                    currentStep /= 2
                    val subListToZero = arrayListOf<String>()
                    val subListToOne = arrayListOf<String>()
                    repeat((variationCount / currentStep / 2).toInt()) {
                        subListToZero.addAll(resultList.subList(0, currentStep.toInt()))
                        subListToOne.addAll(resultList.subList(currentStep.toInt(), 2 * currentStep.toInt()))
                        if (resultList.size != 0) {
                            resultList = resultList.subList(2 * currentStep.toInt(), resultList.size)
                        }
                    }
                    for (item in subListToZero) {
                        val charArray = item.toCharArray()
                        for (char in charArray.withIndex()) {
                            if (char.value.toString() == "X") {
                                charArray[char.index] = '0'
                                break
                            }
                        }
                        currentList.add(String(charArray))
                    }
                    for (item in subListToOne) {
                        val charArray = item.toCharArray()
                        for (char in charArray.withIndex()) {
                            if (char.value.toString() == "X") {
                                charArray[char.index] = '1'
                                break
                            }
                        }
                        currentList.add(String(charArray))
                    }
                    resultList = currentList
                }
                resultList.map { it.toLong(2) }.forEach {
                    mapOfStorage[it] = storeValue
                }
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
}