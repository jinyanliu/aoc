import utils.IoHelper

object D24 {
    private val inputs = IoHelper.getLines("d24.in")

    fun solveOne(x: Long, y: Long, z: Long, w: Long, instructions: List<String>): List<Long> {
        var x = x
        var y = y
        var z = z
        var w = w

        for (line in instructions) {
            val sec = line.split(" ")
            val ins = sec[0]
            val variable = sec[1]
            val numberOrNull = if (sec.size > 2) sec[2].toLongOrNull() else null
            when (ins) {
/*                    "inp" -> {
                        when (variable) {
                            "x" -> {
                                x = queue.removeFirst()
                            }
                            "y" -> {
                                y = queue.removeFirst()
                            }
                            "z" -> {
                                z = queue.removeFirst()
                            }
                            "w" -> {
                                w = queue.removeFirst()
                            }
                        }
                    }*/
                "add" -> {
                    when (variable) {
                        "x" -> {
                            if (numberOrNull != null) {
                                x = x + numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        x = x + x
                                    }
                                    "y" -> {
                                        x = x + y
                                    }
                                    "z" -> {
                                        x = x + z
                                    }
                                    "w" -> {
                                        x = x + w
                                    }
                                }
                            }

                        }
                        "y" -> {
                            if (numberOrNull != null) {
                                y = y + numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        y = y + x
                                    }
                                    "y" -> {
                                        y = y + y
                                    }
                                    "z" -> {
                                        y = y + z
                                    }
                                    "w" -> {
                                        y = y + w
                                    }
                                }
                            }
                        }
                        "z" -> {
                            if (numberOrNull != null) {
                                z = z + numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        z = z + x
                                    }
                                    "y" -> {
                                        z = z + y
                                    }
                                    "z" -> {
                                        z = z + z
                                    }
                                    "w" -> {
                                        z = z + w
                                    }
                                }
                            }
                        }
                        "w" -> {
                            if (numberOrNull != null) {
                                w = w * numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        w = w + x
                                    }
                                    "y" -> {
                                        w = w + y
                                    }
                                    "z" -> {
                                        w = w + z
                                    }
                                    "w" -> {
                                        w = w + w
                                    }
                                }
                            }
                        }
                    }
                }
                "mul" -> {
                    when (variable) {
                        "x" -> {
                            if (numberOrNull != null) {
                                x = x * numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        x = x * x
                                    }
                                    "y" -> {
                                        x = x * y
                                    }
                                    "z" -> {
                                        x = x * z
                                    }
                                    "w" -> {
                                        x = x * w
                                    }
                                }
                            }

                        }
                        "y" -> {
                            if (numberOrNull != null) {
                                y = y * numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        y = y * x
                                    }
                                    "y" -> {
                                        y = y * y
                                    }
                                    "z" -> {
                                        y = y * z
                                    }
                                    "w" -> {
                                        y = y * w
                                    }
                                }
                            }
                        }
                        "z" -> {
                            if (numberOrNull != null) {
                                z = z * numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        z = z * x
                                    }
                                    "y" -> {
                                        z = z * y
                                    }
                                    "z" -> {
                                        z = z * z
                                    }
                                    "w" -> {
                                        z = z * w
                                    }
                                }
                            }
                        }
                        "w" -> {
                            if (numberOrNull != null) {
                                w = w * numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        w = w * x
                                    }
                                    "y" -> {
                                        w = w * y
                                    }
                                    "z" -> {
                                        w = w * z
                                    }
                                    "w" -> {
                                        w = w * w
                                    }
                                }
                            }
                        }
                    }
                }
                "div" -> {
                    when (variable) {
                        "x" -> {
                            if (numberOrNull != null) {
                                x = x / numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        x = x / x
                                    }
                                    "y" -> {
                                        x = x / y
                                    }
                                    "z" -> {
                                        x = x / z
                                    }
                                    "w" -> {
                                        x = x / w
                                    }
                                }
                            }

                        }
                        "y" -> {
                            if (numberOrNull != null) {
                                y = y / numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        y = y / x
                                    }
                                    "y" -> {
                                        y = y / y
                                    }
                                    "z" -> {
                                        y = y / z
                                    }
                                    "w" -> {
                                        y = y / w
                                    }
                                }
                            }
                        }
                        "z" -> {
                            if (numberOrNull != null) {
                                z = z / numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        z = z / x
                                    }
                                    "y" -> {
                                        z = z / y
                                    }
                                    "z" -> {
                                        z = z / z
                                    }
                                    "w" -> {
                                        z = z / w
                                    }
                                }
                            }
                        }
                        "w" -> {
                            if (numberOrNull != null) {
                                w = w / numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        w = w / x
                                    }
                                    "y" -> {
                                        w = w / y
                                    }
                                    "z" -> {
                                        w = w / z
                                    }
                                    "w" -> {
                                        w = w / w
                                    }
                                }
                            }
                        }
                    }
                }
                "mod" -> {
                    when (variable) {
                        "x" -> {
                            if (numberOrNull != null) {
                                x = x % numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        x = x % x
                                    }
                                    "y" -> {
                                        x = x % y
                                    }
                                    "z" -> {
                                        x = x % z
                                    }
                                    "w" -> {
                                        x = x % w
                                    }
                                }
                            }

                        }
                        "y" -> {
                            if (numberOrNull != null) {
                                y = y % numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        y = y % x
                                    }
                                    "y" -> {
                                        y = y % y
                                    }
                                    "z" -> {
                                        y = y % z
                                    }
                                    "w" -> {
                                        y = y % w
                                    }
                                }
                            }
                        }
                        "z" -> {
                            if (numberOrNull != null) {
                                z = z % numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        z = z % x
                                    }
                                    "y" -> {
                                        z = z % y
                                    }
                                    "z" -> {
                                        z = z % z
                                    }
                                    "w" -> {
                                        z = z % w
                                    }
                                }
                            }
                        }
                        "w" -> {
                            if (numberOrNull != null) {
                                w = w % numberOrNull
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        w = w % x
                                    }
                                    "y" -> {
                                        w = w % y
                                    }
                                    "z" -> {
                                        w = w % z
                                    }
                                    "w" -> {
                                        w = w % w
                                    }
                                }
                            }
                        }
                    }
                }
                "eql" -> {
                    when (variable) {
                        "x" -> {
                            if (numberOrNull != null) {
                                x = if (x == numberOrNull) 1 else 0
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        x = if (x == x) 1 else 0
                                    }
                                    "y" -> {
                                        x = if (x == y) 1 else 0
                                    }
                                    "z" -> {
                                        x = if (x == z) 1 else 0
                                    }
                                    "w" -> {
                                        x = if (x == w) 1 else 0
                                    }
                                }
                            }

                        }
                        "y" -> {
                            if (numberOrNull != null) {
                                y = if (y == numberOrNull) 1 else 0
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        y = if (y == x) 1 else 0
                                    }
                                    "y" -> {
                                        y = if (y == y) 1 else 0
                                    }
                                    "z" -> {
                                        y = if (y == z) 1 else 0
                                    }
                                    "w" -> {
                                        y = if (y == w) 1 else 0
                                    }
                                }
                            }
                        }
                        "z" -> {
                            if (numberOrNull != null) {
                                z = if (z == numberOrNull) 1 else 0
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        z = if (z == x) 1 else 0
                                    }
                                    "y" -> {
                                        z = if (z == y) 1 else 0
                                    }
                                    "z" -> {
                                        z = if (z == z) 1 else 0
                                    }
                                    "w" -> {
                                        z = if (z == w) 1 else 0
                                    }
                                }
                            }
                        }
                        "w" -> {
                            if (numberOrNull != null) {
                                w = if (w == numberOrNull) 1 else 0
                            } else {
                                when (sec[2]) {
                                    "x" -> {
                                        w = if (w == x) 1 else 0
                                    }
                                    "y" -> {
                                        w = if (w == y) 1 else 0
                                    }
                                    "z" -> {
                                        w = if (w == z) 1 else 0
                                    }
                                    "w" -> {
                                        w = if (w == w) 1 else 0
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }






        return listOf<Long>(x, y, z, w)
    }

    fun solveTwo(): Int {
        return 0
    }

    fun solve(): Long {
        val inputs = IoHelper.getRawContent("d24.in").split("inp w").drop(1)



        val mapOfSection0 = mutableMapOf<Long, List<Long>>()
        val instructionsSection0 = inputs[0].lines().drop(1).dropLast(1)
        for (i in 9L downTo 1L) {
            val result = solveOne(0L, 0L, 0L, i, instructionsSection0)
            mapOfSection0[i] = result
        }
        println(mapOfSection0)

        for(section0 in mapOfSection0){
            val current0 = section0.key
            val lastResult = section0.value
            val mapOfSection1 = mutableMapOf<Long, List<Long>>()
            val instructionsSection1 = inputs[1].lines().drop(1).dropLast(1)
            for (i in 9L downTo 1L) {
                val result = solveOne(lastResult[0], lastResult[1], lastResult[2], i, instructionsSection1)
                mapOfSection1[i] = result
            }
            println("current0=$current0")
            println(mapOfSection1)


        }





        return 0L
    }
}

fun main() {
/*    val solutionOne = D24.solveOne()
    val solutionTwo = D24.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")*/
    D24.solve()
}