package org.example

import utils.IoHelper
import kotlin.math.abs

object D02 {
    private val inputs = IoHelper.getLines("d02.in")
    private val numberLists = inputs.map { it.split(" ").map { it.toInt() } }

    fun solveOne(): Int = numberLists.count { it.isSafe() }

    fun solveTwo(): Int {
        val nowSafeLists = mutableListOf<List<Int>>()

        val unsafeLists = numberLists.filter { !it.isSafe() }
        unsafeLists.forEach { unsafeList ->
            val lastIndex = unsafeList.lastIndex
            for (i in 0..lastIndex) {
                val newList = unsafeList.toMutableList()
                newList.removeAt(i)
                if (newList.isSafe()) {
                    nowSafeLists.add(unsafeList)
                    break
                }
            }
        }

        return numberLists.count { it.isSafe() } + nowSafeLists.count()
    }
}

private fun List<Int>.isSafe(): Boolean {
    val sortedNL = sorted()
    val sortedDNL = sortedDescending()
    return if (sortedNL == this || sortedDNL == this) {
        zipWithNext { a, b -> abs(b - a) }.all { it in 1..3 }
    } else {
        false
    }
}

fun main() {
    //306
    val solutionOne = D02.solveOne()
    //366
    val solutionTwo = D02.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}