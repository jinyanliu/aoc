import utils.IoHelper

class Day18 {
    private val inputs = IoHelper.getLines("d18Test.in")

    fun getSolution1() {
        val homework = inputs.get(0).replace(" ", "")

        val indexMap = mutableMapOf<Int, String>()
        homework.forEachIndexed { index, c ->
            if (c.toString() == "(" || c.toString() == ")") {
                indexMap[index] = c.toString()
            }
        }
        println(indexMap)

        val endPMap = indexMap.filter { it.value == ")" }
        val startPMap = indexMap.filter { it.value == "(" }.toMutableMap()
        println(endPMap)
        println(startPMap)

        val mapOfStartEndPair = mutableMapOf<Int, Int>()
        for (map in endPMap) {
            val start = startPMap.filter { it.key < map.key }.keys.last()
            startPMap.remove(start)
            mapOfStartEndPair[start] = map.key
        }
        println(mapOfStartEndPair)


        //((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2

        var calculatingHomework = homework

        mapOfStartEndPair.forEach {
            println(homework.subSequence(it.key..it.value))
            val smallSequence = homework.subSequence(it.key..it.value)

            var calculatedResult = 0
            for (i in 0..smallSequence.length - 1 step 2) {
                if(i+1 <= smallSequence.length - 1){
                    when (smallSequence.get(i).toString()) {
                        "+" -> calculatedResult += smallSequence.get(i + 1).toString().toInt()
                        "*" -> calculatedResult *= smallSequence.get(i + 1).toString().toInt()
                        else -> calculatedResult += smallSequence.get(i + 1).toString().toInt()
                    }
                }

            }
            println(calculatedResult)

            println(calculatingHomework.replace(smallSequence.toString(), calculatedResult.toString()))
            calculatingHomework = calculatingHomework.replace(smallSequence.toString(), calculatedResult.toString())

        }

    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day18().getSolution1())
    println(Day18().getSolution2())
}