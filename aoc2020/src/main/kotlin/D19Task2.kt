import utils.IoHelper

class Day19Task2 {
    private val inputs = IoHelper.getSections("d19Test2Task2.in")
    private val rules = inputs[0].lines()
    private val messages = inputs[1].lines()

    private fun inputsMap(): Map<Long, String> {
        val inputsMap = mutableMapOf<Long, String>()
        rules.map { inputsMap[it.split(": ")[0].toLong()] = it.split(": ")[1] }
        return inputsMap
    }

    fun getRulesFor(start: String): List<String> {
        var toCheck = mutableListOf<String>(start)
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
        return finalRules
    }

    fun getTask2Solution() {
        var validCount:Long = 0
        var messagesToCheck = messages.toMutableList()

        // A
        val rulesOf42 = getRulesFor("42")
        // B
        val rulesOf31 = getRulesFor("31")
        println(rulesOf42)
        println(rulesOf31)

        val mapOfABList = mutableMapOf<String, List<String>>()
        mapOfABList["A"] = rulesOf42
        mapOfABList["B"] = rulesOf31

        val array8 = arrayListOf("42", "42 8").map {
            it.replace("42", "A").replace("31", "B")
        }
        val array11 = arrayListOf("42 31", "42 11 31").map {
            it.replace("42", "A").replace("31", "B")
        }

        println(array8)
        println(array11)

        val combinations = arrayListOf<String>()
        for (item8 in array8) {
            for (item11 in array11) {
                combinations.add(item8 + item11)
            }
        }
        println(combinations)

        val cleanedCom = combinations.map { it.replace(" ", "") }
        println(cleanedCom)
        cleanedCom.forEach {
            if (it.all { it.toString().toLongOrNull() == null }) {
/*                val mapOfContent = mutableMapOf<Long, List<String>>()
                for (char in it.withIndex()) {
                    mapOfContent[char.index.toLong()] = mapOfABList[char.toString()]!!
                }*/

                val combi = arrayListOf<String>()
                for (k1 in rulesOf42) {
                    for (k2 in rulesOf42) {
                        for (k3 in rulesOf31) {
                            combi.add(k1 + k2 + k3)
                        }
                    }
                }

                println(combi)
                val checkedSize = combi[0].length

                validCount += messages.count { combi.contains(it) }.toLong()

                messagesToCheck = messagesToCheck.filter { it.length > checkedSize }.toMutableList()

                println("valid count = "+validCount)
                println("valid messages = "+messages.filter { combi.contains(it) })
                println("checked size = "+ checkedSize)
                println("left messages = "+messagesToCheck)
                println("left messages size = "+messagesToCheck.size)

            }
        }


    }
}

fun main() {
    println(Day19Task2().getTask2Solution())
}