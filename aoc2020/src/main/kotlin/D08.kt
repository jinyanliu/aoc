import utils.IoHelper

class Day08 {
    private fun getInputs() = IoHelper.getLines("d08.in")

    val mapLines = getInputs()
        .mapIndexed { index, s -> index to (s.split(" ")[0] to s.split(" ")[1].toIntOrNull()) }
        .toMap()
        .toMutableMap()

    fun getSolution1():Int {
        return getCurrentValue(mapLines)
    }

    fun getSolution2() {
        for (line in mapLines) {
            when (line.value.first) {
                "nop" -> {
                    val newInstructions = mutableMapOf<Int, Pair<String, Int?>>()
                    newInstructions.putAll(mapLines.toMap())
                    newInstructions[line.key] = "jmp" to line.value.second
                    getCurrentValue(newInstructions)
                }
                "jmp" -> {
                    val newInstructions = mutableMapOf<Int, Pair<String, Int?>>()
                    newInstructions.putAll(mapLines.toMap())
                    newInstructions[line.key] = "nop" to line.value.second
                    getCurrentValue(newInstructions)
                }
                "acc" -> {
                }
            }
        }

    }

    private fun getCurrentValue(mapLines: MutableMap<Int, Pair<String, Int?>>): Int {
        var i = 0
        var currentValue = 0
        while (mapLines[i]?.first != "visited") {
            val currentMapI = mapLines[i]
            when (currentMapI?.first) {
                "nop" -> {
                    mapLines[i] = "visited" to 0
                    i += 1
                }
                "acc" -> {
                    mapLines[i] = "visited" to 0
                    currentValue += currentMapI.second ?: 0
                    i += 1
                }
                "jmp" -> {
                    mapLines[i] = "visited" to 0
                    i += currentMapI.second ?: 0
                }
                null -> {
                    mapLines[i] = "visited" to 0
                    println("null$currentValue")
                }
            }
        }
        return currentValue
    }
}

fun main() {
    println(Day08().getSolution1())
    println(Day08().getSolution2())
}