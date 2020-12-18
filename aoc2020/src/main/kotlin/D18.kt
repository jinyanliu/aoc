import utils.IoHelper

class Day18 {
    private val inputs = IoHelper.getLines("d18.in")

    private fun toCalculatePart(currentHomework: String) = if (currentHomework.contains("(")) {
        val firstPairEnd = currentHomework.withIndex().first { it.value.toString() == ")" }.index
        val firstPairStart =
            currentHomework.withIndex().last { it.value.toString() == "(" && it.index < firstPairEnd }.index
        currentHomework.subSequence(firstPairStart..firstPairEnd)
    } else {
        "($currentHomework)"
    }

    fun getSolution1() = inputs.map {
        var currentHomework = it.replace(" ", "")
        while (currentHomework.toLongOrNull() == null) {
            currentHomework = calculateHomework(currentHomework)
        }
        currentHomework.toLong()
    }.sum()

    private fun calculateHomework(currentHomework: String): String {
        val toCalculate = toCalculatePart(currentHomework)

        var calculatedResult: Long = 0
        var currentOpe: String = "+"
        var currentNumber: String = ""
        for (i in toCalculate.indices) {
            when (toCalculate.get(i).toString()) {
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
                else -> currentNumber += toCalculate.get(i).toString()
            }
        }

        return if (currentHomework.contains("(")) {
            currentHomework.replace(toCalculate.toString(), calculatedResult.toString())
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

    private fun calculateAdvancedHomework(currentHomework: String): String {
        val toCalculate = toCalculatePart(currentHomework)

        val calculatedResult: Long = calculateSmallSequenceAdvanced(toCalculate)

        return if (currentHomework.contains("(")) {
            currentHomework.replace(toCalculate.toString(), calculatedResult.toString())
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