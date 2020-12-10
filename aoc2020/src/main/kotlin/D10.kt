import utils.IoHelper
import kotlin.math.log2
import kotlin.math.pow

class Day10 {
    private val inputs = IoHelper.getInts("d10.in")

    private val deviceRate = (inputs.max() ?: error("error 1")) + 3

    fun getSolution1(): Int {
        val newList = inputs.toMutableList()
        newList.add(deviceRate)

        var currentOutput = 0
        var oneJoltDifferenceCounter = 0
        var threeJoltDifferenceCounter = 0

        while (currentOutput < deviceRate) {
            if (containsDiffOne(newList, currentOutput)) {
                currentOutput += 1
                oneJoltDifferenceCounter += 1
            } else if (newList.contains(currentOutput + 3)) {
                currentOutput += 3
                threeJoltDifferenceCounter += 1
            }
        }

        return oneJoltDifferenceCounter * threeJoltDifferenceCounter
    }

    fun getSolution2() {
        val newList = inputs.toMutableList()
        newList.add(0)
        newList.add(deviceRate)
        newList.sort()

        println(newList)

        //val testList = arrayListOf(0, 1, 4, 5, 6, 7, 10, 11, 12)
        val testList = newList
        //val testList = arrayListOf(0, 1, 4, 5, 6)
        //filterStraightforwardLists(testList)

        val resultBags = arrayListOf<Int>()
        var keys = arrayListOf(0)
        while (!keys.all { it==testList.last()}) {
            println("keys=$keys")
            val newKeys = arrayListOf<Int>()
            for (key in keys) {
                println(key)
                if(key == testList.last()){
                    newKeys.add(key)

                }else {

                        if (testList.contains(key+1)) {
                            newKeys.add(key+1)
                            println("key+1=${key+1}")
                        }
                        if (testList.contains(key+2)) {
                            newKeys.add(key+2)
                            println("key+2=${key+2}")
                        }
                        if (testList.contains(key+3)) {
                            newKeys.add(key+3)
                            println("key+2=${key+2}")
                        }

                }

            }
            keys = newKeys
        }

        println("keys" + keys.toString())

        println(keys.size)


/*        val resultList = arrayListOf<ArrayList<Int>>(arrayListOf(0))
        for (list in resultList){
            val item = list.last()

            if (containsDiffOne(testList, item)
                && !containsDiffTwo(testList, item)
                && !containsDiffThree(testList, item)
            ) {
                val list1 = list.toMutableList()
                list1.add(item)
                resultList.add(list1)
            }

            if (containsDiffTwo(testList, item)
                && !containsDiffOne(testList, item)
                && !containsDiffThree(testList, item)
            ) {
                listOfPair.add(item to item + 2)
            }

            if (containsDiffThree(testList, item)
                && !containsDiffOne(testList, item)
                && !containsDiffTwo(testList, item)
            ) {
                listOfPair.add(item to item + 3)
            }

        }*/

    }

    private fun filterStraightforwardLists(newList: MutableList<Int>) {
        val listOfPair = arrayListOf<Pair<Int, Int>>()
        for (item in newList) {
            if (containsDiffOne(newList, item)
                && !containsDiffTwo(newList, item)
                && !containsDiffThree(newList, item)
            ) {
                println("$item -> ${item + 1}")
                listOfPair.add(item to item + 1)
            }

            if (containsDiffTwo(newList, item)
                && !containsDiffOne(newList, item)
                && !containsDiffThree(newList, item)
            ) {
                println("$item -> ${item + 2}")
                listOfPair.add(item to item + 2)
            }

            if (containsDiffThree(newList, item)
                && !containsDiffOne(newList, item)
                && !containsDiffTwo(newList, item)
            ) {
                println("$item -> ${item + 3}")
                listOfPair.add(item to item + 3)
            }
        }
        println(listOfPair)

        for (pairWithIndex in listOfPair.withIndex()) {
            if (pairWithIndex.index + 1 < listOfPair.size) {
                if (listOfPair.get(pairWithIndex.index + 1).first == pairWithIndex.value.second) {
                    println(
                        pairWithIndex.value.first.toString() + "->" + pairWithIndex.value.second.toString() + "->" + listOfPair.get(
                            pairWithIndex.index + 1
                        ).second
                    )
                }
            }
        }

        val listOfStraightLines = arrayListOf<ArrayList<Int>>()
        val pairToMap = listOfPair.toMap()
        for (item in newList) {
            if (pairToMap.keys.contains(item)) {
                var currentSecond = pairToMap.get(item) ?: error("error 2")
                val list = arrayListOf(item, currentSecond)
                while (currentSecond in pairToMap.keys) {
                    currentSecond = pairToMap.get(currentSecond) ?: error("error 3")
                    list.add(currentSecond)
                }

                listOfStraightLines.add(list)
                println(list.toString())
            }

        }

        val listToTheEnd = listOfStraightLines.filter { it.last() == deviceRate }
        val resultList = listToTheEnd.toMutableList()

        for (i in 1..listToTheEnd.size - 1) {
            if (listToTheEnd.get(i).all { listToTheEnd.first().contains(it) } && onlyOneStarter(
                    listToTheEnd.get(i).first()
                )) {
                resultList.remove(listToTheEnd.get(i))
            }
        }

        println(resultList.toString())
        if(resultList.isNotEmpty()){
            val listEnd = resultList.last().first()
            val newListWithIndex = newList.mapIndexed { index, i -> i to index }.toMap()
            val listEndIndex = newListWithIndex.get(listEnd) ?: error("")
            println(listEndIndex)
            newList.take(listEndIndex)
        }
    }

    private fun containsDiffOne(newList: MutableList<Int>, item: Int) =
        newList.contains(item + 1)

    private fun containsDiffTwo(newList: MutableList<Int>, item: Int) =
        newList.contains(item + 2)

    private fun containsDiffThree(newList: MutableList<Int>, item: Int) =
        newList.contains(item + 3)

    private fun onlyOneStarter(item:Int):Boolean{
        if (inputs.contains(item-1)&&!inputs.contains(item-2)&&!inputs.contains(item-3)) return true
        if(!inputs.contains(item-1)&&inputs.contains(item-2)&&!inputs.contains(item-3))return true
        if(!inputs.contains(item-1)&&!inputs.contains(item-2)&&inputs.contains(item-3))return true
        return false
    }
}

fun main() {
    println(Day10().getSolution1())
    println(Day10().getSolution2())
}