package org.example

import utils.IoHelper

object D02 {
    val inputs = IoHelper.getRawContent("d02.in").replace("\n", "").split(",")

    fun solveOne(): Long {
        var invalidSum = 0L
        inputs.forEach { input ->
            val numbers = input.split("-").map(String::toLong)
            val startNumber = numbers.first()
            val endNumber = numbers.last()
            for (i in startNumber until endNumber + 1) {
                val length = i.toString().length
                val firstHalf = i.toString().take(length / 2)
                val secondHalf = i.toString().drop(length / 2)
                if (firstHalf == secondHalf) {
                    invalidSum += (firstHalf + secondHalf).toLong()
                }
            }
        }
        return invalidSum
    }

    fun solveTwo(): Long {
        var invalidSum = 0L
        val invalidHistory = mutableSetOf<Long>()
        inputs.forEach { input ->
            val numbers = input.split("-").map(String::toLong)
            val startNumber = numbers.first()
            val endNumber = numbers.last()
            for (i in startNumber until endNumber + 1) {
                val length = i.toString().length
                for (j in 1 until length/2+1) {
                    var string = ""
                    val section = (i.toString().take(j))
                    repeat(10) {
                        string += section
                    }
                    if ((string.take(length) == i.toString()) && (length % section.length == 0)) {
                        if(!invalidHistory.contains(string.take(length).toLong())){
                            invalidSum += string.take(length).toLong()
                            invalidHistory.add(string.take(length).toLong())
                        }
                    }
                }
            }
        }
        return invalidSum
    }
}

fun main() {
    //41294979841
    println(D02.solveOne())
    //66500947346
    println(D02.solveTwo())
}