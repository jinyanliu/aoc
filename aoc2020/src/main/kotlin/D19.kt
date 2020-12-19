import utils.IoHelper

class Day19 {
    private val inputs = IoHelper.getSections("d19.in")
    private val rules = inputs[0].lines()
    private val messages = inputs[1].lines()

    private fun inputsMap(): Map<Long, String> {
        val inputsMap = mutableMapOf<Long, String>()
        rules.map { inputsMap[it.split(": ")[0].toLong()] = it.split(": ")[1] }
        return inputsMap
    }


    fun getSolution1() {
/*        println(inputsMap())
        val zeroRule = inputsMap()[0]!!
        var possibleRules = arrayListOf<String>()
        possibleRules.addAll(zeroRule.split(" "))
        println(possibleRules)

        while (true){
            val newToCheck = arrayListOf<String>()

            for (possibleRule in possibleRules) {
                val innerRules = inputsMap().get(possibleRule.toLong())!!
                if(innerRules.startsWith("\"")){
                    newToCheck.add(innerRules)
                }else if(innerRules.contains("|")){
                    if(newToCheck.isNotEmpty()){

                    }
                }else {

                }
            }
        }*/


        var toCheck = mutableListOf<String>("0")
        while (toCheck.any { it.any { it.toString().toLongOrNull() != null } }) {
            var outerList = mutableListOf<String>()
            toCheck.forEach { oneString ->
                var newList = mutableListOf<String>()
                val elements = oneString.split(" ")
                elements.forEach { element ->
                    if(element=="") return@forEach
                    if (element.toLongOrNull() != null) {
                        val values = inputsMap().get(element.toLong())!!
                        if (!values.contains("|")) {
                            if (newList.isEmpty()) {
                                newList.add(values)
                            } else {
                                newList = newList.map { it + " " + values+" " }.toMutableList()
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
                            newList = newList.map { it + " " + element+ " " }.toMutableList()
                        }
                    }
                }
                outerList.addAll(newList)
            }
            toCheck = outerList
            println("c")
            for (check in toCheck) {
                println(check)
                println()
            }

            println()
            println()
        }

        val finalRules = toCheck.map {
            it.replace("\"", "").replace(" ", "")
        }
        println(finalRules)

        println(messages.count { finalRules.contains(it) })


    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day19().getSolution1())
    println(Day19().getSolution2())
}