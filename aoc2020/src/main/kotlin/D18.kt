import utils.IoHelper

class Day18 {
    private val inputs = IoHelper.getLines("d18.in")

    fun getSolution1(): Long {

        var finalResult: Long = 0
        inputs.forEach {
            val homework = it.replace(" ", "")
            var calculatingHomework = homework

            while (calculatingHomework.toLongOrNull() == null) {
                calculatingHomework = calculateHomework(calculatingHomework)
            }

            finalResult += calculatingHomework.toLong()
            println(calculatingHomework)
        }

        return finalResult
    }

    private fun calculateHomework(calculatingHomework: String): String {
        val indexMap = mutableMapOf<Int, String>()
        calculatingHomework.forEachIndexed { index, c ->
            if (c.toString() == "(" || c.toString() == ")") {
                indexMap[index] = c.toString()
            }
        }

        val endPMap = indexMap.filter { it.value == ")" }
        val startPMap = indexMap.filter { it.value == "(" }.toMutableMap()

        val listOfStartEndPair = arrayListOf<Pair<Int, Int>>()
        for (map in endPMap) {
            val start = startPMap.filter { it.key < map.key }.keys.last()
            startPMap.remove(start)
            listOfStartEndPair.add(start to map.key)
        }


        var smallSequence: CharSequence
        if (listOfStartEndPair.isNotEmpty()) {
            smallSequence = calculatingHomework.subSequence(listOfStartEndPair[0].first..listOfStartEndPair[0].second)
        } else {
            smallSequence = "(" + calculatingHomework + ")"
        }


        var calculatedResult: Long = 0

        var currentOpe: String = "+"
        var currentNumber: String = ""
        for (i in 0..smallSequence.length - 1) {
            when (smallSequence.get(i).toString()) {
                "+" -> {
                    val intNumber = currentNumber.toLong()
                    if (currentOpe == "+") {
                        calculatedResult += intNumber
                    } else {
                        calculatedResult *= intNumber
                    }
                    currentNumber = ""
                    currentOpe = "+"
                }
                "*" -> {
                    val intNumber = currentNumber.toLong()
                    if (currentOpe == "+") {
                        calculatedResult += intNumber
                    } else {
                        calculatedResult *= intNumber
                    }
                    currentNumber = ""
                    currentOpe = "*"
                }
                "(" -> {
                }
                ")" -> {
                    val intNumber = currentNumber.toLong()
                    if (currentOpe == "+") {
                        calculatedResult += intNumber
                    } else {
                        calculatedResult *= intNumber
                    }
                }
                else -> currentNumber += smallSequence.get(i).toString()
            }


        }


        if (listOfStartEndPair.isNotEmpty()) {
            return calculatingHomework.replace(smallSequence.toString(), calculatedResult.toString())
        } else {
            return calculatedResult.toString()
        }


    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day18().getSolution1())
    println(Day18().getSolution2())
}