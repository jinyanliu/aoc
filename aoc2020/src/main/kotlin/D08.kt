import utils.IoHelper

class Day08 {
    private fun getInputs() = IoHelper.getLines("d08.in")

    fun getSolution1() {
        val mapLines =
            getInputs().mapIndexed { index, s -> index to (s.split(" ")[0] to s.split(" ")[1].toIntOrNull()) }.toMap()
                .toMutableMap()

        var i = 0
        var currentValue = 0
        while (mapLines[i]?.first != "visited") {
            val currentMapI = mapLines[i]
            when (currentMapI?.first) {
                "nop" -> {
                    mapLines[i] = "visited" to 0
                    println(currentMapI)
                    i += 1
                }
                "acc" -> {
                    mapLines[i] = "visited" to 0
                    println(currentMapI)
                    currentValue += currentMapI.second ?: 0
                    i += 1
                    println(currentValue)
                }
                else -> {
                    mapLines[i] = "visited" to 0
                    println(currentMapI)
                    i += currentMapI?.second ?: 0
                }
            }
        }
        println(currentValue)
    }

    fun getSolution2() {
        val mapLines =
            getInputs().mapIndexed { index, s -> index to (s.split(" ")[0] to s.split(" ")[1].toIntOrNull()) }.toMap()
                .toMutableMap()



        for (line in mapLines){
            when(line.value.first){
                "nop"->{
                    val newInstructions = mutableMapOf<Int, Pair<String, Int?>>()
                    newInstructions.putAll(mapLines.toMap())
                    newInstructions[line.key] = "jmp" to line.value.second
                    getCurrentValue(newInstructions)
                }
                "jmp"->{
                    val newInstructions = mutableMapOf<Int, Pair<String, Int?>>()
                    newInstructions.putAll(mapLines.toMap())
                    newInstructions[line.key] = "nop" to line.value.second
                    getCurrentValue(newInstructions)
                }
                "acc"->{}
            }
        }

    }

    private fun getCurrentValue(mapLines: MutableMap<Int, Pair<String, Int?>>) {
        var i = 0
        var currentValue = 0
        while (mapLines[i]?.first != "visited") {
            val currentMapI = mapLines[i]
            when (currentMapI?.first) {
                "nop" -> {
                    mapLines[i] = "visited" to 0
                    println(currentMapI)
                    i += 1
                }
                "acc" -> {
                    mapLines[i] = "visited" to 0
                    println(currentMapI)
                    currentValue += currentMapI.second ?: 0
                    i += 1
                    println(currentValue)
                }
                "jmp" -> {
                    mapLines[i] = "visited" to 0
                    println(currentMapI)
                    i += currentMapI?.second ?: 0
                }
                null ->{
                    mapLines[i] = "visited" to 0
                    println("null"+currentValue)
                }
            }
        }
        println(currentValue)
    }
}

fun main() {
    //println(Day08().getSolution1())
    println(Day08().getSolution2())
}