import utils.IoHelper

class Day19Task1 {
    private val inputs = IoHelper.getSections("d19.in")
    private val rules = inputs[0].lines()
    private val messages = inputs[1].lines()

    private fun inputsMap(): Map<Long, String> {
        val inputsMap = mutableMapOf<Long, String>()
        rules.map { inputsMap[it.split(": ")[0].toLong()] = it.split(": ")[1] }
        return inputsMap
    }

    fun getSolution1(): Long {
        var toCheck = mutableListOf<String>("0")
        while (toCheck.any { it.any { it.toString().toLongOrNull() != null } }) {
            val outerList = mutableListOf<String>()
            toCheck.forEach { oneString ->
                var newList = mutableListOf<String>()
                val elements = oneString.split(" ")
                elements.forEach { element ->
                    if (element == "") return@forEach
                    if (element.toLongOrNull() != null) {
                        val values = inputsMap().get(element.toLong())!!
                        if (!values.contains("|")) {
                            if (newList.isEmpty()) {
                                newList.add(values)
                            } else {
                                newList = newList.map { "$it $values " }.toMutableList()
                            }
                        } else {
                            if (newList.isEmpty()) {
                                val twoValues = values.split(" | ")
                                newList.add(twoValues[0])
                                newList.add(twoValues[1])
                            } else {
                                val twoValues = values.split(" | ")
                                newList = newList.flatMap { arrayListOf(it, it) }
                                    .mapIndexed { index, s -> s + " " + twoValues[index % 2] + " " }.toMutableList()

                            }
                        }
                    } else {
                        if (newList.isEmpty()) {
                            newList.add(element)
                        } else {
                            newList = newList.map { "$it $element " }.toMutableList()
                        }
                    }
                }
                outerList.addAll(newList)
            }
            toCheck = outerList
        }

        val finalRules = toCheck.map {
            it.replace("\"", "").replace(" ", "")
        }
        println(finalRules)
        return messages.count { finalRules.contains(it) }.toLong()
    }

    fun getSolution2() {
    }
}

fun main() {
    //109
    println(Day19Task1().getSolution1())
    println(Day19Task1().getSolution2())
}