import utils.IoHelper

class Day09 {
    private fun getInputs() = IoHelper.getLongs("d09.in")

    private val mapLongList = getInputs().mapIndexed { index, long -> index to long }.toMap()

    fun getSolution1(): Long {
        for (mapLong in mapLongList) {
            if (mapLong.key >= 25) {
                val indexRange = (mapLong.key - 25 until mapLong.key)
                val targetList = indexRange.map { mapLongList[it] ?: error("error 1") }
                val resultList = getResultListAlt(targetList)
                if (!resultList.contains(mapLong.value)) {
                    return (mapLong.value)
                }
            }
        }
        return 0
    }

    private fun getResultList(
        targetList: List<Long>
    ): ArrayList<Long> {
        val resultList = arrayListOf<Long>()
        targetList.forEach { i ->
            targetList.forEach { j ->
                if ((i + j) != (i + i)) {
                    resultList.add(i + j)
                }
            }
        }
        return resultList
    }

    private fun getResultListAlt(
        targetList: List<Long>
    ): ArrayList<Long> {
        val resultList = arrayListOf<Long>()
        for (i in targetList.indices) {
            for (j in i + 1 until targetList.size) {
                resultList.add(targetList.get(i) + targetList.get(j))
            }
        }
        return resultList
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