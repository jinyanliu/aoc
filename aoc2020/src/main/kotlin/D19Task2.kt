import utils.IoHelper

class Day19Task2 {
    private val inputs = IoHelper.getSections("d19Task2.in")
    private val rules = inputs[0].lines()
    private val messages = inputs[1].lines()

    private fun inputsMap(): Map<Long, String> {
        val inputsMap = mutableMapOf<Long, String>()
        rules.map { inputsMap[it.split(": ")[0].toLong()] = it.split(": ")[1] }
        return inputsMap
    }

    private val rulesOfA = Day19().getAllRulesFor(inputsMap(), "42")
    private val rulesOfB = Day19().getAllRulesFor(inputsMap(), "31")
    private val mapOfABList = mutableMapOf("A" to rulesOfA, "B" to rulesOfB)
    private val charSizeOfAB = rulesOfA[0].length

    private val array8 = arrayListOf("42", "42 8").map {
        it.replace("42", "A").replace("31", "B")
    }
    private val array11 = arrayListOf("42 31", "42 11 31").map {
        it.replace("42", "A").replace("31", "B")
    }

    fun getSolution(): Long {
        var validCount: Long = 0
        var messagesToCheck = messages.toMutableList()
        var combinations = arrayListOf("8 11")
        while (messagesToCheck.isNotEmpty()) {
            combinations = getCombinations(combinations, array8, array11)
            var toDo = combinations.filter { it.split(" ").all { it.toLongOrNull() == null } }.minBy { it.length }!!
            combinations.remove(toDo)
            toDo = toDo.replace(" ", "")
            val toDoSize = toDo.length
            val targetStringSize = toDoSize * charSizeOfAB
            val targetMessages = messagesToCheck.filter { it.length == targetStringSize }

            var targetMessagesValidCount = 0
            targetMessages.forEach { singleMessage ->
                var toDoIndex = 0
                var valid = true
                for (i in singleMessage.indices step charSizeOfAB) {
                    val currentTodoKey = toDo[toDoIndex].toString()
                    if (!mapOfABList[currentTodoKey]!!.contains(singleMessage.substring(i until i + charSizeOfAB))) {
                        valid = false
                    }
                    toDoIndex += 1
                }
                if (valid) {
                    targetMessagesValidCount += 1
                    messagesToCheck.remove(singleMessage)
                }
            }
            validCount += targetMessagesValidCount
            messagesToCheck = messagesToCheck.filter { it.length >= targetStringSize }.toMutableList()
        }
        return validCount
    }

    private fun getCombinations(
        combinations: ArrayList<String>,
        array8: List<String>,
        array11: List<String>
    ): ArrayList<String> {
        val combinationsResult = arrayListOf<String>()
        combinations.forEach { oneCheck ->
            var combi = mutableListOf<String>()
            val elements = oneCheck.split(" ")
            for (char in elements) {
                if (char == "8") {
                    combi = transform(combi, array8)
                } else if (char == "11") {
                    combi = transform(combi, array11)
                } else if (char == "A" || char == "B") {
                    if (combi.isEmpty()) {
                        combi.add(char)
                    } else {
                        combi = combi.map { "$it $char" }.toMutableList()
                    }
                }
            }
            combinationsResult.addAll(combi)
        }
        return combinationsResult
    }

    private fun transform(
        combi: MutableList<String>,
        array8: List<String>
    ): MutableList<String> {
        var transformedCombi = combi
        if (transformedCombi.isEmpty()) {
            transformedCombi.addAll(array8)
        } else {
            transformedCombi = transformedCombi.flatMap { origin ->
                val newL = mutableListOf<String>()
                for (item in array8) {
                    newL.add("$origin $item")
                }
                newL
            }.toMutableList()
        }
        return transformedCombi
    }
}

fun main() {
    //301
    println(Day19Task2().getSolution())
}