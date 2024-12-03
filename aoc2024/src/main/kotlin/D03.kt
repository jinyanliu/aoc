package org.example

import utils.IoHelper

object D03 {
    private val inputs = IoHelper.getRawContent("d03.in")

    fun solveOne(): Int = allMatchesSum(Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(inputs).map { it.value }.toList())

    fun solveTwo(): Int {
        val inputs = "do()$inputs"
        val mapOfDoDont = mutableMapOf<Int, Boolean>()
        val validRanges = mutableListOf<IntRange>()

        Regex("""do\(\)""").findAll(inputs).forEach { matchResult ->
            mapOfDoDont[matchResult.range.first] = true
        }
        Regex("""don't\(\)""").findAll(inputs).forEach { matchResult ->
            mapOfDoDont[matchResult.range.first] = false
        }

        mapOfDoDont.keys.forEach { key ->
            if (mapOfDoDont[key]!!) {
                val endKey = mapOfDoDont.keys.filter { it > key }.find { findKey -> !mapOfDoDont[findKey]!! }
                    ?: Int.MAX_VALUE
                validRanges.add(IntRange(start = key, endInclusive = endKey))
            }
        }

        val allMatches = mutableListOf<String>()

        Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(inputs).forEach { matchResult ->
            val index = matchResult.range.first
            if (validRanges.find { index in it } != null) {
                allMatches.add(matchResult.value)
            }
        }
        
        return allMatchesSum(allMatches)
    }

    private fun allMatchesSum(allMatches: List<String>) = allMatches.map {
        val endPart = it.split("(")[1]
        val firstDigit = endPart.split(",")[0].toInt()
        val secondDigit = endPart.split(",")[1].split(")")[0].toInt()
        firstDigit * secondDigit
    }.sum()
}

fun main() {
    //183788984
    val solutionOne = D03.solveOne()
    //62098619
    val solutionTwo = D03.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}