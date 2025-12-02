package org.example

import utils.IoHelper

object D01 {
    val inputs = IoHelper.getLines("d01.in")

    fun solveOne(): Int {
        var current = 50
        var zeroCount = 0
        inputs.forEach { input ->
            val direction = input.get(0)
            if (direction == 'L') {
                current -= input.drop(1).toInt() % 100
                if (current < 0) {
                    current = 100 + current
                }
            } else {
                current += input.drop(1).toInt() % 100
                if (current > 99) {
                    current = current - 100
                }
            }
            if (current == 0) {
                zeroCount += 1
            }
        }
        return zeroCount
    }

    fun solveTwo(): Int {
        var current = 50
        var zeroCount = 0

        inputs.forEach { input ->
            println()
            println("input = $input")
            val direction = input.get(0)
            val laps = input.drop(1).toInt() / 100
            zeroCount += laps
            val remaining = input.drop(1).toInt() % 100
            if (direction == 'L') {
                val previousCurrent = current
                current -= remaining
                if (current < 0) {
                    if (previousCurrent != 0) {
                        zeroCount += 1
                    }
                    current = 100 + current
                } else if (current == 0) {
                    zeroCount += 1
                }
            } else {
                current += remaining
                if (current > 99) {
                    zeroCount += 1
                    current = current - 100
                }
            }
            println("current = $current")
            println("zeroCount = $zeroCount")
        }
        return zeroCount
    }
}

fun main() {
    //999
    println(D01.solveOne())
    //6099
    println(D01.solveTwo())
}