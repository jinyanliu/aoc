import utils.IoHelper

class Day19Task2 {
    private val inputs = IoHelper.getSections("d19Test2Task2.in")
    private val rules = inputs[0].lines()
    private val messages = inputs[1].lines()

    // A
    private val rulesOf42 = getRulesFor("42")

    // B
    private val rulesOf31 = getRulesFor("31")

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

        var validCount: Long = 0
        var messagesToCheck = messages.toMutableList()
/*        messagesToCheck = arrayListOf(
            "abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa", "babbbbaabbbbbabbbbbbaabaaabaaa", "aaabbbbbbaaaabaababaabababbabaaabbababababaaa", "bbbbbbbaaaabbbbaaabbabaaa", "bbbababbbbaaaaaaaabbababaaababaabab", "baabbaaaabbaaaababbaababb", "abbbbabbbbaaaababbbbbbaaaababb", "aaaaabbaabaaaaababaa", "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa", "babaaabbbaaabaababbaabababaaab", "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba")*/

        var combinations = arrayListOf<String>("8 11")

        while (messagesToCheck.isNotEmpty()) {

            var combinationsResult: MutableList<String>
            if (combinations.any { it.split(" ").all { it.toString().toLongOrNull() == null } }) {
                combinationsResult =
                    combinations.filter { it.split(" ").all { it.toString().toLongOrNull() == null } }.toMutableList()
            } else {
                combinationsResult = getCombinationResults(combinations, array8, array11)
                val uncheckedCom = arrayListOf<String>()
                uncheckedCom.addAll(combinationsResult)
                combinations = uncheckedCom
            }


            var toDo = combinationsResult.filter { it.split(" ").all { it.toString().toLongOrNull() == null } }.sortedBy { it.length }.first()

            combinations.remove(toDo)

            toDo = toDo.replace(" ", "")


/*                    var combi = mutableListOf<String>()

                    for(char in toDo){
                        if(char.toString() == "A"){
                            if(combi.isEmpty()){
                                combi.addAll(rulesOf42)
                            }else {
                                combi = combi.flatMap {origin->
                                    val newL = mutableListOf<String>()
                                    for(item in rulesOf42){
                                        newL.add(origin+item)
                                    }
                                    newL
                                }.toMutableList()
                            }
                        }else {
                            if(combi.isEmpty()){
                                combi.addAll(rulesOf31)
                            }else {
                                combi = combi.flatMap {origin->
                                    val newL = mutableListOf<String>()
                                    for(item in rulesOf31){
                                        newL.add(origin+item)
                                    }
                                    newL
                                }.toMutableList()
                            }
                        }

                    }*/

            val toDoSize = toDo.length
            val targetStringSize = toDoSize*5
            val targetMessages = messagesToCheck.filter { it.length == targetStringSize }

            var targetMessagesValidCount = 0
            targetMessages.forEach {singleMessage->
                var toDoIndex = 0
                var valid = true
                for (i in 0.. singleMessage.length-1 step 5){
                    val currentTodoKey = toDo.get(toDoIndex).toString()
                    if(!mapOfABList[currentTodoKey]!!.contains(singleMessage.substring(i..i+4))){
                        valid = false
                    }
                    toDoIndex+=1
                }
                if(valid){
                    targetMessagesValidCount+=1
                }
            }


            validCount += targetMessagesValidCount

            messagesToCheck = messagesToCheck.filter { it.length != targetStringSize }.toMutableList()

            println("valid count = " + validCount)
            println("checked size = " + targetStringSize)
            println("left messages = " + messagesToCheck)
            println("left messages size = " + messagesToCheck.size)


        }


    }

    private fun getCombinationResults(
        combinations: ArrayList<String>,
        array8: List<String>,
        array11: List<String>
    ): ArrayList<String> {
        var combinationsResult = arrayListOf<String>()
        combinations.forEach { oneCheck ->
            var combi = mutableListOf<String>()
            val elements = oneCheck.split(" ")
            for (char in elements) {
                if (char.toString() == "8") {
                    if (combi.isEmpty()) {
                        combi.addAll(array8)
                    } else {
                        combi = combi.flatMap { origin ->
                            val newL = mutableListOf<String>()
                            for (item in array8) {
                                newL.add(origin + " " + item)
                            }
                            newL
                        }.toMutableList()
                    }
                } else if (char.toString() == "11") {
                    if (combi.isEmpty()) {
                        combi.addAll(array11)
                    } else {
                        combi = combi.flatMap { origin ->
                            val newL = mutableListOf<String>()
                            for (item in array11) {
                                newL.add(origin + " " + item)
                            }
                            newL
                        }.toMutableList()
                    }
                } else if (char.toString() == "A" || char.toString() == "B") {
                    if (combi.isEmpty()) {
                        combi.add(char.toString())
                    } else {
                        combi = combi.map { it + " " + char.toString() }.toMutableList()
                    }
                }
            }
            combinationsResult.addAll(combi.filter { it.split(" ").size <= 9 })
        }
        println(combinationsResult)
        return combinationsResult
    }
}

fun main() {
    println(Day19Task2().getTask2Solution())
}