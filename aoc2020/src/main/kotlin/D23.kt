import utils.IoHelper

class Day23 {
    private val inputs = IoHelper.getLines("d23Test.in")

    private fun getIndexOf1(currentList: Sequence<Long>): Int {
        for (item in currentList.withIndex()) {
            if (item.value == 1L) {
                return item.index
            }
        }
        return -1
    }

    fun getSolution1(): String {
        var currentList = inputs[0].split(" ").map { it.toLong() }.toMutableList()
        var currentCup = currentList[0]
        var currentPickup = mutableListOf<Long>()
        var destinationCup: Long

        repeat(100) {
            println("cups: " + currentList)
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
/*        return (currentList.subList(getIndexOf1(currentList) + 1, currentList.size) + currentList.subList(
            0,
            getIndexOf1(currentList)
        )).joinToString("")*/
        return ""
    }

    fun getSolution2(): String {
        val inputList = inputs[0].split(" ").map { it.toLong() }.toMutableList()
        //var currentList = (inputList + (10L..1000000L).toList()).asSequence()
        var currentList = inputList.asSequence()
        var currentCup = currentList.first()
        var currentPickup = mutableListOf<Long>()
        var destinationCup: Long


        var i = 0
        repeat(100) {
            i += 1
            println(i)
            println("cups: " + currentList.toList())
            // Finds pick up
            val currentCupIndex = currentList.indexOf(currentCup)
            when {
                currentCupIndex <= 9 - 4 -> {
                    currentPickup = mutableListOf(
                        currentList.elementAt(currentCupIndex + 1),
                        currentList.elementAt(currentCupIndex + 2),
                        currentList.elementAt(currentCupIndex + 3)
                    )
                }
                currentCupIndex == 9 - 3 -> {
                    currentPickup =
                        mutableListOf(
                            currentList.elementAt(currentCupIndex + 1),
                            currentList.elementAt(currentCupIndex + 2),
                            currentList.elementAt(0)
                        )
                }
                currentCupIndex == 9 - 2 -> {
                    currentPickup = mutableListOf(
                        currentList.elementAt(currentCupIndex + 1),
                        currentList.elementAt(0),
                        currentList.elementAt(1)
                    )
                }
                currentCupIndex == 9 - 1 -> {
                    currentPickup =
                        mutableListOf(currentList.elementAt(0), currentList.elementAt(1), currentList.elementAt(2))
                }
            }

            currentList = currentList.filter { it !in currentPickup }

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

            currentList = newList.asSequence()

            for (item in currentList.withIndex()) {
                if (item.value == currentCup) {
                    if (item.index <= 9 - 2) {
                        currentCup = currentList.elementAt(item.index + 1)
                    } else {
                        currentCup = currentList.elementAt(0)
                    }

                    break
                }
            }
        }

        println(currentList.toList())
        println(currentList.elementAt(currentList.indexOf(1L)))
        println(currentList.elementAt(currentList.indexOf(1L)+1))
        println(currentList.elementAt(currentList.indexOf(1L)+2))
        return ""
    }
}

fun main() {
    // 26354798
    //println(Day23().getSolution1())
    Day23().getSolution2()
}