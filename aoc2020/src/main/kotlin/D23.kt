import utils.IoHelper

class Day23 {
    private val inputs = IoHelper.getLines("d23.in")[0]

    private fun getIndexOf1(currentList: MutableList<Long>): Int {
        for (item in currentList.withIndex()) {
            if (item.value == 1L) {
                return item.index
            }
        }
        return -1
    }

    fun getSolution1(): String {
        var currentList = inputs.map { it.toString().toLong() }.toMutableList()
        var currentCup = currentList[0]
        var currentPickup = mutableListOf<Long>()
        var destinationCup: Long

        repeat(100) {
            //println("cups: " + currentList)
            // Finds pick up
            for (item in currentList.withIndex()) {
                if (item.value == currentCup) {
                    when {
                        item.index <= 5 -> {
                            currentPickup = mutableListOf(
                                currentList[item.index + 1],
                                currentList[item.index + 2],
                                currentList[item.index + 3]
                            )
                        }
                        item.index == 6 -> {
                            currentPickup =
                                mutableListOf(currentList[item.index + 1], currentList[item.index + 2], currentList[0])
                        }
                        item.index == 7 -> {
                            currentPickup = mutableListOf(currentList[item.index + 1], currentList[0], currentList[1])
                        }
                        item.index == 8 -> {
                            currentPickup = mutableListOf(currentList[0], currentList[1], currentList[2])
                        }
                    }
                }
            }
            currentList.removeAll(currentPickup)
            println("pick up: " + currentPickup)

            // Finds destination
            destinationCup = currentCup - 1
            while (destinationCup in currentPickup) {
                destinationCup = destinationCup - 1
            }
            if (destinationCup < 1) {
                destinationCup = currentList.max()!!
            }
            println("destination: " + destinationCup)

            val newList = mutableListOf<Long>()

            // Place picked up cup
            for (item in currentList.withIndex()) {
                newList.add(item.value)
                if (item.value == destinationCup) {
                    newList.add(currentPickup[0])
                    newList.add(currentPickup[1])
                    newList.add(currentPickup[2])
                }
            }

            currentList = newList

            for (item in currentList.withIndex()) {
                if (item.value == currentCup) {
                    if (item.index <= 7) {
                        currentCup = currentList[item.index + 1]
                    } else {
                        currentCup = currentList[0]
                    }

                    break
                }
            }
        }

        println(currentList)
        return (currentList.subList(getIndexOf1(currentList) + 1, currentList.size) + currentList.subList(
            0,
            getIndexOf1(currentList)
        )).joinToString("")
    }

    fun getSolution2() {
    }
}

fun main() {
    // 26354798
    println(Day23().getSolution1())
    println(Day23().getSolution2())
}