import utils.IoHelper

class Day18 {
    private val inputs = IoHelper.getLines("d18.in").map { it.replace(" ", "") }

    fun getSolution1() = getSolution(::calculateHomework)
    fun getSolution2() = getSolution(::calculateAdvancedHomework)

    private fun calculateHomework(currentHomework: String): String {
        val toCalculate = toCalculatePart(currentHomework)
        val calculatedResult: Long = calculateCurrentHomework(toCalculate)
        return currentCalculatedHomework(currentHomework, toCalculate, calculatedResult)
    }

    private fun calculateAdvancedHomework(currentHomework: String): String {
        val toCalculate = toCalculatePart(currentHomework)
        val calculatedResult: Long = calculateCurrentHomeWorkAdvanced(toCalculate)
        return currentCalculatedHomework(currentHomework, toCalculate, calculatedResult)
    }

    private fun getSolution(calculation: (homework: String) -> String) = inputs.map {
        var currentHomework = it
        while (currentHomework.toLongOrNull() == null) {
            currentHomework = calculation(currentHomework)
        }
        currentHomework.toLong()
    }.sum()

    private fun toCalculatePart(currentHomework: String) = if (currentHomework.contains("(")) {
        val firstPairEnd = currentHomework.withIndex().first { it.value.toString() == ")" }.index
        val firstPairStart =
            currentHomework.withIndex().last { it.value.toString() == "(" && it.index < firstPairEnd }.index
        currentHomework.subSequence(firstPairStart..firstPairEnd)
    } else {
        "($currentHomework)"
    }

    private fun currentCalculatedHomework(
        currentHomework: String,
        toCalculate: CharSequence,
        calculatedResult: Long
    ) = if (currentHomework.contains("(")) {
        currentHomework.replace(toCalculate.toString(), calculatedResult.toString())
    } else {
        calculatedResult.toString()
    }

    private fun calculateCurrentHomework(toCalculate: CharSequence): Long {
        var calculatedResult: Long = 0
        var currentOpe = "+"
        var currentNumber = ""
        for (char in toCalculate) {
            when (char.toString()) {
                "+" -> {
                    calculatedResult = calculateOneOperand(currentNumber.toLong(), currentOpe, calculatedResult)
                    currentNumber = ""
                    currentOpe = "+"
                }
                "*" -> {
                    calculatedResult = calculateOneOperand(currentNumber.toLong(), currentOpe, calculatedResult)
                    currentNumber = ""
                    currentOpe = "*"
                }
                "(" -> {
                }
                ")" -> {
                    calculatedResult = calculateOneOperand(currentNumber.toLong(), currentOpe, calculatedResult)
                }
                else -> currentNumber += char.toString()
            }
        }
        return calculatedResult
    }

    private fun calculateOneOperand(
        currentNumber: Long,
        currentOpe: String,
        calculatedResult: Long
    ): Long {
        var currentResult = calculatedResult
        if (currentOpe == "+") {
            currentResult += currentNumber
        } else {
            currentResult *= currentNumber
        }
        return currentResult
    }

    private fun calculateCurrentHomeWorkAdvanced(currentHomework: CharSequence): Long {
        val result: Long
        if (currentHomework.toString().contains("+")) {
            var calculatingHomework = currentHomework.toString()
            while (calculatingHomework.contains("+")) {
                val firstSequencesWithPlus =
                    calculatingHomework.drop(1).dropLast(1).split("*").filter { it.toLongOrNull() == null }.first()
                val smallResult = calculatePlus(firstSequencesWithPlus)
                calculatingHomework = calculatingHomework.replace(firstSequencesWithPlus, smallResult.toString())
            }
            result = calculateHomeworkWithOnlyMulti(calculatingHomework)
        } else {
            result = calculateHomeworkWithOnlyMulti(currentHomework)
        }
        return result
    }

    private fun calculateHomeworkWithOnlyMulti(smallSequence: CharSequence): Long {
        var calculatedResult: Long = 1
        var currentNumber = ""
        for (char in smallSequence) {
            when (char.toString()) {
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
                else -> currentNumber += char.toString()
            }
        }
        return calculatedResult
    }

    private fun calculatePlus(smallSequence: CharSequence): Long {
        val toDo = "$smallSequence+"
        var calculatedResult: Long = 0
        var currentNumber = ""
        for (char in toDo) {
            when (char.toString()) {
                "+" -> {
                    val intNumber = currentNumber.toLong()
                    calculatedResult += intNumber
                    currentNumber = ""
                }
                else -> currentNumber += char.toString()
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