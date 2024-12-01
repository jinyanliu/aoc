package org.example

import utils.IoHelper
import kotlin.math.abs

object D01 {
    private val inputs = IoHelper.getLines("d01.in")

    private fun getListOneAndTwo(): Pair<List<Long>, List<Long>> {
        val listOne = mutableListOf<Long>()
        val listTwo = mutableListOf<Long>()
        inputs.forEach {
            val input = it.split("   ")
            listOne.add(input[0].toLong())
            listTwo.add(input[1].toLong())
        }
        listOne.sort()
        listTwo.sort()
        return listOne to listTwo
    }

    fun solveOne(): Long {
        val listOne = getListOneAndTwo().first
        val listTwo = getListOneAndTwo().second
        return listOne.zip(listTwo) { x, y -> abs(x - y) }.sum()
    }

    fun solveTwo(): Long {
        val listOne = getListOneAndTwo().first
        val listTwo = getListOneAndTwo().second
        return listOne.map { number ->
            number * listTwo.count { it == number }
        }.sum()
    }
}

fun main() {
    //2264607
    val solutionOne = D01.solveOne()
    //19457120
    val solutionTwo = D01.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}