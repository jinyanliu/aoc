import utils.IoHelper

class Day15 {
    private val inputs = IoHelper.getLines("d15.in")

    fun getSolution1(): Int {
        val map = inputs[0].split(",").withIndex().map { it.value.toInt() to arrayListOf(it.index + 1) }.toMap()
            .toMutableMap()

        var lastNumber = 4
        for (i in 8..2020) {
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
        val map =
            inputs[0].split(",").withIndex().map { it.value.toLong() to arrayListOf((it.index + 1).toLong()) }.toMap()
                .toMutableMap()

        var lastNumber = 4L
        for (i in 8..30000000) {
            val lastNumberIndex = map[lastNumber]
            if (lastNumberIndex == null || lastNumberIndex.size < 2) {
                map[0]?.add(i.toLong()) ?: let { map[0] = arrayListOf(i.toLong()) }
                lastNumber = 0
            } else {
                val numberList: ArrayList<Long> = map[lastNumber] ?: error("")
                numberList.sortDescending()

                val newList = arrayListOf<Long>()
                newList.add(numberList.get(0))
                newList.add(numberList.get(1))
                map[lastNumber] = newList

                val numberToSpeak = numberList.get(0) - numberList.get(1)
                map[numberToSpeak]?.add(i.toLong()) ?: let { map[numberToSpeak] = arrayListOf(i.toLong()) }
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