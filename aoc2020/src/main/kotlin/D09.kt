import utils.IoHelper

class Day09 {
    private fun getInputs() = IoHelper.getLongs("d09.in")

    private val mapLongList = getInputs().mapIndexed { index, long -> index to long }.toMap()

    fun getSolution1(): Long {
        for (mapLong in mapLongList) {
            if (mapLong.key >= 25) {
                val resultList = arrayListOf<Long>()
                val indexRange = (mapLong.key - 25 until mapLong.key)
                val targetList = indexRange.map { mapLongList[it] ?: error("error 1") }
                targetList.forEach { i ->
                    targetList.forEach { j ->
                        if ((i + j) != (i + i)) {
                            resultList.add(i + j)
                        }
                    }
                }
                if (!resultList.contains(mapLong.value)) {
                    return (mapLong.value)
                }
            }
        }
        return 0
    }

    fun getSolution2(): Long {
        for (mapLong in mapLongList) {
            var currentResult: Long = mapLong.value
            var currentStep = 1
            while (currentResult < 177777905L) {
                val currentEndKey = mapLong.key + currentStep
                currentResult += mapLongList[currentEndKey] ?: error("error 2")
                if (currentResult == 177777905L) {
                    val resultValueList = (mapLong.key..currentEndKey).map { mapLongList[it] ?: error("") }
                    return (resultValueList.max() ?: 0) + (resultValueList.min() ?: 0)
                }
                currentStep += 1
            }
        }
        return 0
    }
}

fun main() {
    println(Day09().getSolution1())
    println(Day09().getSolution2())
}