import utils.IoHelper

object D14 {
    private val inputs = IoHelper.getSections("d14.in")
    private val template = inputs[0].windowed(2)
    private val insertionRulesMap = getInsertionRulesMap()

    fun solve(times: Int): Long {
        var templateMap: Map<String, Long> = template.groupBy { it }.map { it.key to it.value.size.toLong() }.toMap()

        repeat(times) {
            val resultMap = mutableMapOf<String, Long>()
            templateMap.forEach { singleTemplate ->
                val templateKey = singleTemplate.key
                val templateCount = singleTemplate.value

                val produce1 = templateKey[0] + insertionRulesMap[templateKey]!!
                val produce2 = insertionRulesMap[templateKey]!! + templateKey[1]

                listOf(produce1, produce2).forEach { produced ->
                    if (resultMap[produced] != null) {
                        resultMap[produced] = resultMap[produced]!! + templateCount
                    } else {
                        resultMap[produced] = templateCount
                    }
                }
            }
            templateMap = resultMap
        }

        val charCountGroup: Map<Char, List<Pair<Char, Long>>> =
            templateMap.map { it.key[1] to it.value }.groupBy { it.first }
        val result =
            charCountGroup.map { it.key to it.value.map { it.second } }.associate { it.first to it.second.sum() }

        val newMap = mutableMapOf<Char, Long>()
        result.forEach {
            if (it.key == template[0][0]) {
                newMap[it.key] = it.value + 1
            } else {
                newMap[it.key] = it.value
            }
        }

        val sorted = newMap.map { it.value }.sorted()
        return sorted.max()!! - sorted.min()!!
    }

    private fun getInsertionRulesMap(): Map<String, String> {
        val insertionRulesMap = mutableMapOf<String, String>()
        inputs[1].split("\n").forEach {
            insertionRulesMap[it.split(" -> ")[0]] = it.split(" -> ")[1]
        }
        return insertionRulesMap
    }
}

fun main() {
    //3284
    val solutionOne = D14.solve(10)
    //4302675529689
    val solutionTwo = D14.solve(40)
    println("One=$solutionOne")
    println("Two=$solutionTwo")
}