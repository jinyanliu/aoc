package org.example

import utils.IoHelper

object D03 {
    val inputs = IoHelper.getLines("d03.in")

    fun solveOne(): Int {
        var finalResult = 0
        inputs.forEach { input ->
            println(input)
            val result = calculate(input)
            println(result)
            finalResult += result.toInt()
        }
        return finalResult
    }

    private fun calculate(input: String): String {
        val sortedInput = input.toSortedSet().reversed().map { it.toString().toInt() }
        val max = sortedInput[0]
        println("max: $max")
        val maxIndex = getMaxIndex(input, max)
        if (maxIndex != input.lastIndex) {
            val secondMax = input.drop(maxIndex + 1).map { it.toString().toInt() }.max()
            val result = max.toString() + secondMax.toString()
            return result
        } else {
            val secondMax = sortedInput[1]
            val secondMaxIndex = getMaxIndex(input, secondMax)
            val nowSecondMax = input.drop(secondMaxIndex + 1).map { it.toString().toInt() }.max()
            val result = secondMax.toString() + nowSecondMax.toString()
            return result
        }
    }


    private fun getMaxIndex(input: String, max: Int): Int {
        input.forEachIndexed { index, s ->
            if (s.toString().toInt() == max) {
                return index
            }
        }
        throw IllegalStateException()
    }

    fun solveTwo(): Int {
        return 0
    }
}

fun main() {
    println(D03.solveOne())
    println(D03.solveTwo())
}