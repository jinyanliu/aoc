import utils.IoHelper

object D8 {
    private val inputs = IoHelper.getLines("d08.in")
    private val lineTest = IoHelper.getRawContent("d08Test2.in")
    private val system =
        listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
    private val systemPair = listOf(
        0 to "abcefg",
        1 to "cf",
        2 to "acdeg",
        3 to "acdfg",
        4 to "bcdf",
        5 to "abdfg",
        6 to "abdefg",
        7 to "acf",
        8 to "abcdefg",
        9 to "abcdfg"
    )

    fun solveOne(): Int = inputs.map {
        it.split(" | ")[1].split(" ").map { it.length }.count { it == 2 || it == 4 || it == 3 || it == 7 }
    }.sum()

    fun solveTwo() = inputs.map { solveOneLine(it) }.sum()

    private fun solveOneLine(line: String): Long {
        val puzzles = line.split(" | ")[0].split(" ")
        val outputs = line.split(" | ")[1].split(" ")

        val current = mutableMapOf<Int, String>()
        //1
        val currentOne = puzzles.filter { it.length == 2 }[0]
        //7
        val currentSeven = puzzles.filter { it.length == 3 }[0]
        //4
        val currentFour = puzzles.filter { it.length == 4 }[0]
        //8
        val currentEight = puzzles.filter { it.length == 7 }[0]
        current[1] = currentOne
        current[7] = currentSeven
        current[4] = currentFour
        current[8] = currentEight

        val matchingMap = mutableMapOf<String, String>()

        for (key in current.keys) {
            val currentCharList = current[key]!!.toCharArray().toMutableList()
            val systemCharList = system[key].toCharArray().toMutableList()
            val currentMap = matchingMap.toMap()
            for (entry in currentMap) {
                val keyCharList = entry.key.toCharArray().toList()
                val valueCharList = entry.value.toCharArray().toList()
                if (currentCharList.containsAll(keyCharList)) {
                    currentCharList.removeAll(keyCharList)
                    systemCharList.removeAll(valueCharList)
                    matchingMap[currentCharList.joinToString("")] = systemCharList.joinToString("")
                }
            }
            matchingMap[current[key]!!] = system[key]
        }

        var resultDigit = ""
        for (output in outputs) {
            val outputCharList = output.toCharArray().toMutableList()
            val resultChar = mutableListOf<Char>()

            for (entry in matchingMap) {
                val key = entry.key
                val value = entry.value
                val keyCharList = key.toCharArray().toList()
                if (outputCharList.containsAll(keyCharList)) {
                    outputCharList.removeAll(keyCharList)
                    resultChar.addAll(value.toCharArray().toList())
                }
            }

            val result = systemPair.filter { it.second.length == output.length }
                .map { it.first to it.second.toCharArray().toList() }
                .filter { it.second.containsAll(resultChar) }[0].first

            resultDigit += result.toString()
        }
        return resultDigit.toLong()
    }
}

fun main() {
    //375
    val solutionOne = D8.solveOne()
    //1019355
    val solutionTwo = D8.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}