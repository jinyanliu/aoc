import utils.IoHelper

object D6 {
    private val inputs = IoHelper.getRawContent("d06.in").split(",").map { it.toInt() }

    fun solveOne(): Int {
        var currentList = inputs.toMutableList()

        repeat(80) {
            val zeroCount = currentList.count { it == 0 }
            currentList = currentList.map {
                if (it == 0) {
                    6
                } else {
                    it - 1
                }
            }.toMutableList()
            repeat(zeroCount) {
                currentList.add(8)
            }
        }
        return currentList.size
    }

    fun solveTwo(): Long {
        val days = 256

        val daysProducedEight = mutableListOf<Int>()
        for (i in inputs) {
            var addOnMultiple = 0
            while (i + 1 + (7 * addOnMultiple) <= days) {
                daysProducedEight.add(i + 1 + (7 * addOnMultiple))
                addOnMultiple += 1
            }
        }

        val firstRoundCount = daysProducedEight.size + inputs.size

        var daysAddedEight: Map<Int, Long> =
            daysProducedEight.groupBy { it }.toMap().mapValues { it.value.size.toLong() }
        var count = 0L
        while (daysAddedEight.keys.min()!! + 9 <= days) {

            val eightProducedEight = mutableMapOf<Int, Long>()

            for (i in daysAddedEight) {
                if (i.key + 9 > days) continue
                var addOnMultiple = 0
                while (i.key + 9 + (7 * addOnMultiple) <= days) {
                    if (eightProducedEight.containsKey(i.key + 9 + (7 * addOnMultiple))) {
                        eightProducedEight[i.key + 9 + (7 * addOnMultiple)] =
                            eightProducedEight[i.key + 9 + (7 * addOnMultiple)]!! + i.value
                    } else {
                        eightProducedEight.put(i.key + 9 + (7 * addOnMultiple), i.value)
                    }
                    addOnMultiple += 1
                }
            }
            count += eightProducedEight.values.sum()
            daysAddedEight = eightProducedEight
        }
        return firstRoundCount + count
    }
}

fun main() {
    //360268
    val solutionOne = D6.solveOne()
    //1632146183902
    val solutionTwo = D6.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}