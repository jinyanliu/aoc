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

    fun getSolution(): Long {
        val allRules = Day19().getAllRulesFor(inputsMap(), "0")
        return messages.count { allRules.contains(it) }.toLong()
    }
}

fun main() {
    //109
    println(Day19Task1().getSolution())
}