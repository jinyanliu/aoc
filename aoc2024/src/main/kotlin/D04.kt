package org.example

import utils.IoHelper

object D04 {
    private val inputs = IoHelper.getStringMap("d04.in")

    fun solveOne() = inputs.filter { it.value == "X" }.keys.map { it.validSolutionOnePatternCount() }.sum()

    private fun Pair<Int, Int>.validSolutionOnePatternCount(): Int =
        XMASFindInTheDirection(0 to -1) +
                XMASFindInTheDirection(0 to 1) +
                XMASFindInTheDirection(-1 to 0) +
                XMASFindInTheDirection(1 to 0) +
                XMASFindInTheDirection(1 to -1) +
                XMASFindInTheDirection(1 to 1) +
                XMASFindInTheDirection(-1 to 1) +
                XMASFindInTheDirection(-1 to -1)

    private fun Pair<Int, Int>.XMASFindInTheDirection(pairOfInt: Pair<Int, Int>): Int {
        val stepOne = inputs[first + pairOfInt.first to second + pairOfInt.second]
        if (stepOne != null && stepOne == "M") {
            val stepTwo = inputs[first + (2 * pairOfInt.first) to second + (2 * pairOfInt.second)]
            if (stepTwo != null && stepTwo == "A") {
                val stepThree = inputs[first + (3 * pairOfInt.first) to second + (3 * pairOfInt.second)]
                if (stepThree != null && stepThree == "S") {
                    return 1
                }
            }
        }
        return 0
    }

    fun solveTwo() = inputs.filter { it.value == "A" }.keys.count { it.isValidSolutionTwoPattern() }

    private fun Pair<Int, Int>.isValidSolutionTwoPattern() =
        isTheCenterOfPattern(listOf("M", "S", "M", "S")) ||
                isTheCenterOfPattern(listOf("M", "S", "S", "M")) ||
                isTheCenterOfPattern(listOf("S", "M", "S", "M")) ||
                isTheCenterOfPattern(listOf("S", "M", "M", "S"))

    private fun Pair<Int, Int>.isTheCenterOfPattern(listOfString: List<String>): Boolean {
        val leftUp = inputs[first - 1 to second - 1]
        if (leftUp != null && leftUp == listOfString[0]) {
            val rightDown = inputs[first + 1 to second + 1]
            if (rightDown != null && rightDown == listOfString[1]) {
                val rightUp = inputs[first + 1 to second - 1]
                if (rightUp != null && rightUp == listOfString[2]) {
                    val leftDown = inputs[first - 1 to second + 1]
                    if (leftDown != null && leftDown == listOfString[3]) {
                        return true
                    }
                }
            }
        }
        return false
    }
}

fun main() {
    //2599
    val solutionOne = D04.solveOne()
    //1948
    val solutionTwo = D04.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}