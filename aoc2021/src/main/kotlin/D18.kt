import utils.IoHelper
import java.util.*

object D18 {
    val inputs = IoHelper.getLines("d18.in")

    fun doHomework(inputs: List<String>): Int {
        val queue = ArrayDeque<String>()
        inputs.forEach { queue.add(it) }

        var first = queue.removeFirst()
        while (queue.isNotEmpty()) {
            val second = queue.removeFirst()
            var add = "[$first,$second]"
            var oneProcessResult = oneProcess(add)

            while (add != oneProcessResult) {
                add = oneProcessResult
                oneProcessResult = oneProcess(oneProcessResult)
            }
            first = add
        }
        return calculateMagnitude(first)
    }

    private fun calculateMagnitude(string: String): Int {
        val queue = ArrayDeque<String>()
        for (char in string) {
            val charString = char.toString()
            if (charString != "]" && charString != ",") {
                queue.add(charString)
            } else if (charString == "]") {
                val number2 = queue.removeLast().toInt()
                val number1 = queue.removeLast().toInt()
                queue.removeLast()
                queue.add((number1 * 3 + number2 * 2).toString())
            }
        }
        return queue.first.toInt()
    }

    private fun oneProcess(input: String): String {
        var add = input
        var maybeExplode = explodeIfNeeded(add)
        while (add != maybeExplode) {
            add = maybeExplode
            maybeExplode = explodeIfNeeded(maybeExplode)
        }
        return splitIfNeeded(add)
    }

    private fun splitIfNeeded(string: String): String {
        val map = mutableMapOf<Int, String>()
        string.forEachIndexed { index, c -> map[index] = c.toString() }

        var latestDigitIndex = -10

        for (element in map) {
            val index = element.key
            val charString = element.value
            if (charString.toIntOrNull() != null) {
                if (latestDigitIndex == index - 1) {
                    val number = (map[latestDigitIndex] + map[index]).toInt()
                    val newNumber1 = number / 2
                    val newNumber2 = number / 2 + number % 2
                    map[latestDigitIndex] = "["
                    map[index] = "$newNumber1,$newNumber2]"
                    break
                }
                latestDigitIndex = index
            }
        }

        return map.map { it.value }.joinToString("")
    }

    private fun explodeIfNeeded(string: String): String {
        val tempMap = mutableMapOf<Int, String>()
        string.forEachIndexed { index, c -> tempMap[index] = c.toString() }

        val listOfString = mutableListOf<String>()
        var tempLatestDigitIndex = -10

        for (element in tempMap) {
            val index = element.key
            val charString = element.value

            if (charString == "[" || charString == "]" || charString == ",") {
                listOfString.add(charString)
            }

            if (charString.toIntOrNull() != null) {
                if (index + 1 <= tempMap.size - 1 && tempMap[index + 1]!!.toIntOrNull() != null) {
                    listOfString.add(tempMap[index]!! + tempMap[index + 1]!!)
                } else if (tempLatestDigitIndex == index - 1) {
                    //Do nothing
                } else {
                    listOfString.add(charString)
                }

                tempLatestDigitIndex = index
            }
        }

        val map = mutableMapOf<Int, String>()
        listOfString.forEachIndexed { index, s -> map[index] = s }

        var counter = 0
        var latestDigitIndex = -1
        var explodeSecond = -3
        var shouldAddRightSide = false

        for (element in map) {
            val index = element.key
            val charString = element.value

            if (charString == "[") {
                counter += 1

                if (counter == 5) {
                    map[index] = "*"
                }
            }

            if (counter == 5 && charString.toIntOrNull() != null) {
                if (latestDigitIndex >= 0) {
                    map[latestDigitIndex] = (map[latestDigitIndex]!!.toInt() + charString.toInt()).toString()
                    latestDigitIndex = -2
                }

                explodeSecond = charString.toInt()
                map[index] = "*"
            } else if (charString.toIntOrNull() != null) {
                latestDigitIndex = index
            }

            if (charString.toIntOrNull() != null && shouldAddRightSide) {
                map[index] = (charString.toInt() + explodeSecond).toString()
                break
            }

            if (counter == 5 && charString == ",") {
                map[index] = "*"
            }

            if (counter == 5 && charString == "]") {
                counter = -100
                map[index] = "*"
                shouldAddRightSide = true
            } else if (charString == "]") {
                counter -= 1
            }
        }

        return map.map { it.value }.joinToString("").replace("*****", "0")
    }

    fun solveTwo(): Int {
        val resultList = mutableListOf<Int>()

        val xList = inputs
        val yList = inputs

        for (x in xList) {
            for (y in yList) {
                if (x == y) {
                    continue
                }
                resultList.add(doHomework(listOf(x, y)))
                resultList.add(doHomework(listOf(y, x)))
            }
        }

        return resultList.max()!!
    }
}

fun main() {
    //3359
    val solutionOne = D18.doHomework(D18.inputs)
    //4616
    val solutionTwo = D18.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}