import utils.IoHelper

class Day15 {
    private val inputs = IoHelper.getLines("d15.in")

    private val map = inputs[0].split(",").withIndex()
        .map { it.value.toLong() to arrayListOf((it.index + 1).toLong()) }.toMap().toMutableMap()

    private var currentLastValue = inputs[0].split(',').last().toLong()
    private var lastIndexFromInput = inputs[0].split(',').lastIndex.toLong()

    fun getSolution1() = getSolution(2020L)

    fun getSolution2() = getSolution(30000000L)

    private fun getSolution(endTurn: Long): Long {
        for (i in lastIndexFromInput + 2..endTurn) {
            val lastNumberIndex = map[currentLastValue]
            if (lastNumberIndex == null || lastNumberIndex.size < 2) {
                map[0]?.add(i) ?: let { map[0] = arrayListOf(i) }
                currentLastValue = 0
            } else {
                val numberList: ArrayList<Long> = map[currentLastValue] ?: error("")
                numberList.sortDescending()

                val newList = arrayListOf<Long>()
                newList.add(numberList[0])
                newList.add(numberList[1])
                map[currentLastValue] = newList

                val numberToSpeak = numberList[0] - numberList[1]
                map[numberToSpeak]?.add(i) ?: let { map[numberToSpeak] = arrayListOf(i) }
                currentLastValue = numberToSpeak
            }
        }
        return currentLastValue
    }
}

fun main() {
    //1522
    println(Day15().getSolution1())
    //18234
    println(Day15().getSolution2())
}