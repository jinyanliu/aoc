import utils.IoHelper

@OptIn(ExperimentalStdlibApi::class)
object D05 {
    private val inputs = IoHelper.getRawContentWithEmptySpace("d05.in")

    fun solveOne(): String {
        val (map, instructions) = setupMap()

        for (instruction in instructions) {
            val (to, from, howMany) = getInstructions(instruction)
            repeat(howMany) {
                val fromList = map[from]!!.toMutableList()
                val removed = fromList.removeLast()
                map[from] = fromList
                val toList = map[to]!!.toMutableList()
                toList.add(removed)
                map[to] = toList
            }
        }
        return map.map { it.value.last() }.joinToString("")
    }

    fun solveTwo(): String {
        val (map, instructions) = setupMap()

        for (instruction in instructions) {
            val (to, from, howMany) = getInstructions(instruction)
            val fromList = map[from]!!.toMutableList()
            val removed = fromList.takeLast(howMany)
            repeat(howMany) {
                fromList.removeLast()
            }
            map[from] = fromList
            val toList = map[to]!!.toMutableList()
            toList.addAll(removed)
            map[to] = toList
        }
        return map.map { it.value.last() }.joinToString("")
    }

    private fun setupMap(): Pair<MutableMap<Int, List<String>>, List<String>> {
        val map = mutableMapOf<Int, List<String>>()
        map[1] = emptyList()
        map[2] = emptyList()
        map[3] = emptyList()
        map[4] = emptyList()
        map[5] = emptyList()
        map[6] = emptyList()
        map[7] = emptyList()
        map[8] = emptyList()
        map[9] = emptyList()

        val sections = inputs.split("\n\n")
        val lines = sections[0].replace(" ", "*").lines().reversed().drop(1)
        val instructions = sections[1].lines().map { it.filter { it.isDigit() } }
        for (line in lines) {
            for (item in line.withIndex()) {
                if (item.value.toString() in "A".."Z") {
                    if (item.index == 1) {
                        val list = map[1]!!.toMutableList()
                        list.add(item.value.toString())
                        map[1] = list
                    }
                    if (item.index == 5) {
                        val list = map[2]!!.toMutableList()
                        list.add(item.value.toString())
                        map[2] = list
                    }
                    if (item.index == 9) {
                        val list = map[3]!!.toMutableList()
                        list.add(item.value.toString())
                        map[3] = list
                    }
                    if (item.index == 13) {
                        val list = map[4]!!.toMutableList()
                        list.add(item.value.toString())
                        map[4] = list
                    }
                    if (item.index == 17) {
                        val list = map[5]!!.toMutableList()
                        list.add(item.value.toString())
                        map[5] = list
                    }
                    if (item.index == 21) {
                        val list = map[6]!!.toMutableList()
                        list.add(item.value.toString())
                        map[6] = list
                    }
                    if (item.index == 25) {
                        val list = map[7]!!.toMutableList()
                        list.add(item.value.toString())
                        map[7] = list
                    }
                    if (item.index == 29) {
                        val list = map[8]!!.toMutableList()
                        list.add(item.value.toString())
                        map[8] = list
                    }
                    if (item.index == 33) {
                        val list = map[9]!!.toMutableList()
                        list.add(item.value.toString())
                        map[9] = list
                    }
                }
            }
        }
        return Pair(map, instructions)
    }

    private fun getInstructions(instruction: String): Triple<Int, Int, Int> {
        val to: Int = instruction.takeLast(1).toInt()
        val from = instruction.dropLast(1).takeLast(1).toInt()
        val howMany = instruction.dropLast(2).toInt()
        return Triple(to, from, howMany)
    }
}

fun main() {
    val solutionOne = D05.solveOne()
    val solutionTwo = D05.solveTwo()
    //SHMSDGZVC
    println("One=$solutionOne")
    //VRZGHDFBQ
    println("Tw0=$solutionTwo")
}