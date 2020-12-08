import utils.IoHelper

class Day08 {
    private fun getInputs() = IoHelper.getLines("d08.in")

    private val mapLines = getInputs()
        .mapIndexed { index, s -> index to (s.split(" ")[0] to s.split(" ")[1].toIntOrNull()) }
        .toMap().toMutableMap()

    fun getSolution1(): Int = getValue(mapLines).second

    fun getSolution2(): Int {
        for (line in mapLines) {
            when (line.value.first) {
                "nop" -> {
                    val result = swapTo(line, "jmp")
                    when (result.first) {
                        true -> return result.second
                    }
                }
                "jmp" -> {
                    val result = swapTo(line, "nop")
                    when (result.first) {
                        true -> return result.second
                    }
                }
            }
        }
        return 0
    }

    private fun swapTo(line: MutableMap.MutableEntry<Int, Pair<String, Int?>>, keyString: String): Pair<Boolean, Int> {
        val newInstructions = mutableMapOf<Int, Pair<String, Int?>>()
        newInstructions.putAll(mapLines.toMap())
        newInstructions[line.key] = keyString to line.value.second
        return getValue(newInstructions)
    }

    // return Boolean: true = finite loop, false= infinite loop
    private fun getValue(mapLines: MutableMap<Int, Pair<String, Int?>>): Pair<Boolean, Int> {
        var i = 0
        var currentValue = 0
        while (mapLines[i]?.first != "visited") {
            val currentMapI = mapLines[i]
            mapLines[i] = "visited" to 0
            when (currentMapI?.first) {
                "nop" -> i += 1
                "acc" -> {
                    currentValue += currentMapI.second ?: 0
                    i += 1
                }
                "jmp" -> i += currentMapI.second ?: 0
                null -> return true to currentValue
            }
        }
        return false to currentValue
    }
}

fun main() {
    println(Day08().getSolution1())
    println(Day08().getSolution2())
}