import utils.IoHelper

class Day23 {
    private val inputs = IoHelper.getLines("d23Test.in")

/*    private fun getIndexOf1(currentList: Sequence<Long>): Int {
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
*//*        return (currentList.subList(getIndexOf1(currentList) + 1, currentList.size) + currentList.subList(
            0,
            getIndexOf1(currentList)
        )).joinToString("")*//*
        return ""
    }*/

    fun getSolution2(): String {
        val inputList = inputs[0].split(" ").map { it.toLong() }.toMutableList()
        //val inputList = (inputs[0].split(" ").map { it.toLong() } + (10L..1000000L).toList())
        //println(inputList)
        return play2(inputList)
    }

/*    private fun play(inputList: List<Long>): String {
        val size = inputList.size
        println("Size: "+size)
        var currentList = inputList.asSequence()

        var currentCup = currentList.first()
        var currentPickup = mutableListOf<Long>()
        var destinationCup: Long


        var i = 0
        repeat(10000000) {
            i += 1
            if(i % 1000 ==0){
                println(i)
            }

            //println("cups: " + currentList.toList())
            // Finds pick up
            val currentCupIndex = currentList.indexOf(currentCup)
            when {
                currentCupIndex <= size - 4 -> {
                    currentPickup = mutableListOf(
                        currentList.elementAt(currentCupIndex + 1),
                        currentList.elementAt(currentCupIndex + 2),
                        currentList.elementAt(currentCupIndex + 3)
                    )
                }
                currentCupIndex == size - 3 -> {
                    currentPickup =
                        mutableListOf(
                            currentList.elementAt(currentCupIndex + 1),
                            currentList.elementAt(currentCupIndex + 2),
                            currentList.elementAt(0)
                        )
                }
                currentCupIndex == size - 2 -> {
                    currentPickup = mutableListOf(
                        currentList.elementAt(currentCupIndex + 1),
                        currentList.elementAt(0),
                        currentList.elementAt(1)
                    )
                }
                currentCupIndex == size - 1 -> {
                    currentPickup =
                        mutableListOf(currentList.elementAt(0), currentList.elementAt(1), currentList.elementAt(2))
                }
            }

            currentList = currentList.filter { it !in currentPickup }

            //println("pick up: " + currentPickup)

            // Finds destination
            destinationCup = currentCup - 1
            while (destinationCup in currentPickup) {
                destinationCup = destinationCup - 1
            }
            if (destinationCup < 1) {
                destinationCup = currentList.max()!!
            }

            //println("destination: " + destinationCup)

            // Place picked up cup
            val destinationIndex = currentList.indexOf(destinationCup)
            val list = currentList.toList()
            val beforeDes = list.subList(0, destinationIndex + 1)
            val afterDes = list.subList(destinationIndex + 1, list.size)
            val newList = (afterDes + beforeDes).toMutableList()
            newList.addAll(currentPickup)

            currentList = newList.asSequence()

            val currentCupIndexNow = currentList.indexOf(currentCup)
            if (currentCupIndexNow <= size - 2) {
                currentCup = currentList.elementAt(currentCupIndexNow + 1)
            } else {
                currentCup = currentList.elementAt(0)
            }
        }

        //println(currentList.toList())
        val indexOf1 = currentList.indexOf(1L)
        if (indexOf1 <= size - 3) {
            println(currentList.elementAt(indexOf1))
            println(currentList.elementAt(indexOf1 + 1))
            println(currentList.elementAt(indexOf1 + 2))
        } else if (indexOf1 == size - 2) {
            println(currentList.elementAt(indexOf1))
            println(currentList.elementAt(indexOf1 + 1))
            println(currentList.elementAt(0))
        } else if (indexOf1 == size - 1) {
            println(currentList.elementAt(indexOf1))
            println(currentList.elementAt(0))
            println(currentList.elementAt(1))
        }

        return ""
    }*/


    fun play2(inputList: List<Long>): String {
        var currentList = inputList.toMutableList()
        var currentPickup = mutableListOf<Long>()
        var destinationCup: Long
        val size = inputList.size - 3

        var i = 0
        repeat(100) {

            i += 1
            if (i % 1000 == 0) {
                println(i)
            }
            //println("cups: " + currentList)
            // Finds pick up

            val currentCup = currentList.removeAt(0)
            println("CurrentList"+currentList)

            currentPickup = mutableListOf(
                currentList.removeAt(0),
                currentList.removeAt(0),
                currentList.removeAt(0)
            )
            currentList.add(currentCup)

            println("CurrentList"+currentList)

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

            val desIndex = currentList.asSequence().indexOf(destinationCup)
            val listAfterDes = currentList.subList(desIndex+1, size)
            val listAfterDesSize = listAfterDes.size
            repeat(listAfterDesSize){
                currentList.add(0,currentList.removeAt(currentList.size-1))
            }

            //currentList = (listAfterDes + currentList).toMutableList()
            currentList.addAll(currentPickup)
            println("CurrentList"+currentList)



            //val listBeforeCurrentCupSize = currentList.subList(0, listAfterDes.size).size
            //currentList = currentList.drop(listAfterDes.size).toMutableList()
            //currentList.addAll(listBeforeCurrentCup)

            repeat(listAfterDesSize){
                currentList.add(currentList.removeAt(0))
            }
            println("CurrentList"+currentList)
            println()
            println()

        }

        println(currentList)
        return ""
    }
}

fun main() {
    // 26354798
    //println(Day23().getSolution1())
    Day23().getSolution2()
}