import utils.IoHelper

class Day15 {
    private val inputs = IoHelper.getLines("d15.in")

    private val map =
        inputs[0].split(",").withIndex().map { it.value.toLong() to arrayListOf((it.index + 1).toLong()) }.toMap()
            .toMutableMap()

    fun getSolution1(): Long {

        var lastNumber = 4L
        for (i in 8L..2020L) {
            val lastNumberIndex = map[lastNumber]
            if (lastNumberIndex == null || lastNumberIndex.size < 2) {
                map[0]?.add(i) ?: let { map[0] = arrayListOf(i) }
                lastNumber = 0
            } else {
                val numberList = map[lastNumber] ?: error("")
                numberList.sortDescending()
                val numberToSpeak = numberList.get(0) - numberList.get(1)
                map[numberToSpeak]?.add(i) ?: let { map[numberToSpeak] = arrayListOf(i) }
                lastNumber = numberToSpeak
            }
        }
        return lastNumber
    }

    fun getSolution2(): Long {


        var lastNumber = 4L
        for (i in 8L..30000000L) {
            val lastNumberIndex = map[lastNumber]
            if (lastNumberIndex == null || lastNumberIndex.size < 2) {
                map[0]?.add(i) ?: let { map[0] = arrayListOf(i) }
                lastNumber = 0
            } else {
                val numberList: ArrayList<Long> = map[lastNumber] ?: error("")
                numberList.sortDescending()

                val newList = arrayListOf<Long>()
                newList.add(numberList[0])
                newList.add(numberList[1])
                map[lastNumber] = newList

                val numberToSpeak = numberList[0] - numberList[1]
                map[numberToSpeak]?.add(i) ?: let { map[numberToSpeak] = arrayListOf(i) }
                lastNumber = numberToSpeak
            }
        }
        return lastNumber
    }
}

fun main() {
    //1522
    println(Day15().getSolution1())
    //18234
    println(Day15().getSolution2())
}