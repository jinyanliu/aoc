class Day19 {
    fun getAllRulesFor(inputsMap: Map<Long, String>, start: String): List<String> {
        var toCheck = mutableListOf(start)
        while (toCheck.any { it.any { it.toString().toLongOrNull() != null } }) {
            val outerList = mutableListOf<String>()
            toCheck.forEach { oneString ->
                var newList = mutableListOf<String>()
                val elements = oneString.split(" ")
                elements.forEach { element ->
                    if (element == "") return@forEach
                    if (element.toLongOrNull() != null) {
                        val values = inputsMap[element.toLong()]!!
                        if (!values.contains("|")) {
                            if (newList.isEmpty()) {
                                newList.add(values)
                            } else {
                                newList = newList.map { "$it $values " }.toMutableList()
                            }
                        } else {
                            val twoValues = values.split(" | ")
                            if (newList.isEmpty()) {
                                newList.add(twoValues[0])
                                newList.add(twoValues[1])
                            } else {
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
                for (item in newList) {
                    println(item)
                }
            }
            toCheck = outerList
        }

        return toCheck.map {
            it.replace("\"", "").replace(" ", "")
        }
    }
}