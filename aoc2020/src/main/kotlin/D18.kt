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

        val smallSequence = if (listOfStartEndPair.isNotEmpty()) {
            calculatingHomework.subSequence(listOfStartEndPair[0].first..listOfStartEndPair[0].second)
        } else {
            "($calculatingHomework)"
        }


        var calculatedResult: Long = 0

        var currentOpe: String = "+"
        var currentNumber: String = ""
        for (i in smallSequence.indices) {
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

        return if (listOfStartEndPair.isNotEmpty()) {
            calculatingHomework.replace(smallSequence.toString(), calculatedResult.toString())
        } else {
            calculatedResult.toString()
        }
    }

    fun getSolution2(): Long {
        var finalResult: Long = 0
        inputs.forEach {
            val homework = it.replace(" ", "")
            var calculatingHomework = homework

            while (calculatingHomework.toLongOrNull() == null) {
                calculatingHomework = calculateAdvancedHomework(calculatingHomework)
            }

            finalResult += calculatingHomework.toLong()
        }

        return finalResult
    }

    private fun calculateAdvancedHomework(calculatingHomework: String): String {
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

        val smallSequence = if (listOfStartEndPair.isNotEmpty()) {
            calculatingHomework.subSequence(listOfStartEndPair[0].first..listOfStartEndPair[0].second)
        } else {
            "($calculatingHomework)"
        }

        val calculatedResult: Long = calculateSmallSequenceAdvanced(smallSequence)


        return if (listOfStartEndPair.isNotEmpty()) {
            calculatingHomework.replace(smallSequence.toString(), calculatedResult.toString())
        } else {
            calculatedResult.toString()
        }
    }

    private fun calculateSmallSequenceAdvanced(smallSequence: CharSequence): Long {
        val result: Long

        if (smallSequence.toString().contains("+")) {

            var currentDoingSequence = smallSequence.toString()

            while (currentDoingSequence.contains("+")) {
                val sequencesWithPlus =
                    currentDoingSequence.drop(1).dropLast(1).split("*").filter { it.toLongOrNull() == null }

                val firstSequencesWithPlus = sequencesWithPlus.first()
                val smallResult = calculateHomeworkWithOnlyPlus(firstSequencesWithPlus)

                currentDoingSequence =
                    currentDoingSequence.replace(firstSequencesWithPlus.toString(), smallResult.toString())
            }

            result = calculateHomeworkWithOnlyMulti(currentDoingSequence)

        } else {
            result = calculateHomeworkWithOnlyMulti(smallSequence)
        }

        return result
    }

    private fun calculateHomeworkWithOnlyMulti(smallSequence: CharSequence): Long {
        var calculatedResult: Long = 1
        var currentNumber: String = ""
        for (i in smallSequence.indices) {
            when (smallSequence[i].toString()) {
                "*" -> {
                    val intNumber = currentNumber.toLong()
                    calculatedResult *= intNumber
                    currentNumber = ""
                }
                "(" -> {
                }
                ")" -> {
                    val intNumber = currentNumber.toLong()
                    calculatedResult *= intNumber

                }
                else -> currentNumber += smallSequence[i].toString()
            }
        }
        return calculatedResult
    }

    private fun calculateHomeworkWithOnlyPlus(smallSequence: CharSequence): Long {
        val toDo = "$smallSequence+"
        var calculatedResult: Long = 0
        var currentNumber: String = ""
        for (i in toDo.indices) {
            when (toDo[i].toString()) {
                "+" -> {
                    val intNumber = currentNumber.toLong()
                    calculatedResult += intNumber
                    currentNumber = ""
                }
                else -> currentNumber += toDo[i].toString()
            }
        }
        return calculatedResult
    }
}

fun main() {
    //6811433855019
    println(Day18().getSolution1())
    //129770152447927
    println(Day18().getSolution2())
}