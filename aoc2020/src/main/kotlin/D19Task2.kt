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

    fun getSolution() {
        val mapOfABList = mutableMapOf<String, List<String>>()
        mapOfABList["A"] = rulesOfA
        mapOfABList["B"] = rulesOfB
        println(rulesOfA)
        println(rulesOfB)

        val array8 = arrayListOf("42", "42 8").map {
            it.replace("42", "A").replace("31", "B")
        }
        val array11 = arrayListOf("42 31", "42 11 31").map {
            it.replace("42", "A").replace("31", "B")
        }

        println(array8)
        println(array11)

        var validCount: Long = 0
        var messagesToCheck = messages.toMutableList()

        var combinations = arrayListOf("8 11")

        while (messagesToCheck.isNotEmpty()) {

            combinations = getCombinationResults(combinations, array8, array11)

            var toDo =
                combinations.filter { it.split(" ").all { it.toLongOrNull() == null } }.sortedBy { it.length }.first()

            combinations.remove(toDo)

            toDo = toDo.replace(" ", "")

            val toDoSize = toDo.length
            val targetStringSize = toDoSize * 8
            val targetMessages = messagesToCheck.filter { it.length == targetStringSize }

            var targetMessagesValidCount = 0
            targetMessages.forEach { singleMessage ->
                var toDoIndex = 0
                var valid = true
                for (i in singleMessage.indices step 8) {
                    val currentTodoKey = toDo.get(toDoIndex).toString()
                    if (!mapOfABList[currentTodoKey]!!.contains(singleMessage.substring(i..i + 7))) {
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

            println("valid count = $validCount")
            println("checked size = $targetStringSize")
            println("left messages = $messagesToCheck")
            println("left messages size = " + messagesToCheck.size)
        }
    }

    private fun getCombinationResults(
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
                    if (combi.isEmpty()) {
                        combi.addAll(array8)
                    } else {
                        combi = combi.flatMap { origin ->
                            val newL = mutableListOf<String>()
                            for (item in array8) {
                                newL.add("$origin $item")
                            }
                            newL
                        }.toMutableList()
                    }
                } else if (char == "11") {
                    if (combi.isEmpty()) {
                        combi.addAll(array11)
                    } else {
                        combi = combi.flatMap { origin ->
                            val newL = mutableListOf<String>()
                            for (item in array11) {
                                newL.add("$origin $item")
                            }
                            newL
                        }.toMutableList()
                    }
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
}

fun main() {
    //301
    println(Day19Task2().getSolution())
}