import utils.IoHelper

class D23 {
    val inputs = IoHelper.getLines("d23.in")[0]

    fun getSolution1(): Int {
        val cups = inputs.map { it.toString().toInt() }.toMutableList()
        val minCup = cups.min()!!
        var currentCup = cups.first()
        var round = 1
        val maxRound = 100
        while (round <= maxRound) {
            // prepare for next round
            val currentCupIdx = cups.indexOf(currentCup)
            val pickupsIndexes = listOf(
                (currentCupIdx + 1) % cups.size,
                (currentCupIdx + 2) % cups.size,
                (currentCupIdx + 3) % cups.size
            )

            var pickups = pickupsIndexes.map { cups[it] }
            println(round)
            println(cups)
            println(pickups)
            pickups.forEach { cups.remove(it) }
            var nextValue = currentCup - 1
            if (nextValue < minCup) {
                nextValue = cups.max()!!
            }
            while (nextValue in pickups) {
                nextValue--
                if (nextValue < minCup) {
                    nextValue = cups.max()!!
                }
            }

            println("$nextValue")
            println()

            val destinationCupIdx = cups.indexOf(nextValue)
            cups.addAll(destinationCupIdx + 1, pickups)
            currentCup = cups[(cups.indexOf(currentCup) + 1) % cups.size]

            round++

            if (round == maxRound + 1) {
                val splits = cups.joinToString("").split("1")
                val r = if (splits.size == 1) splits[0] else splits[1] + splits[0]
                println("final: $r")
            }
        }
        return 0
    }

    fun getSolution2V3(): Int {
        // The correct solution!
        val totalElements = 1000000
        var cups = inputs.map { it.toString().toInt() }.toMutableList()
        var valueToNode = mutableMapOf<Int, Node2>()
        // start with linked list
        val head = Node2(cups.first(), null)
        valueToNode[cups.first()] = head

        var tail = head
        var currentCupNode = head
        for (i in 1 until cups.size) {
            val newNode = Node2(cups[i], null)
            tail.next = newNode
            tail = newNode
            valueToNode[newNode.value] = newNode
        }

        for (i in 10..totalElements) {
            val newNode = Node2(i, null)
            tail.next = newNode
            tail = newNode
            valueToNode[newNode.value] = newNode
        }

        val minCup = 1
        var round = 1
        val maxRound = 10 * totalElements
        tail.next = head

        while (round <= maxRound) {
            // println(printLinkedList(head))
            /*
        if (round > 10000 && round % 10000 == 0) {
            println("round: $round")
        }*/
            var maxCupInRemaining = totalElements
            var p1 = currentCupNode.next!!
            var p2 = p1.next!!
            var p3 = p2.next!!

            currentCupNode.next = p3.next!!

            var pickups = listOf(p1.value, p2.value, p3.value)
            while (maxCupInRemaining in pickups) {
                maxCupInRemaining -= 1
            }

            var destinationValue = currentCupNode.value - 1
            if (destinationValue < minCup) {
                destinationValue = maxCupInRemaining
            }
            while (destinationValue in pickups) {
                destinationValue--
                if (destinationValue < minCup) {
                    destinationValue = maxCupInRemaining
                }
            }

            val destinationNode = findNode(valueToNode, destinationValue)
            p3.next = destinationNode.next
            destinationNode.next = p1

            currentCupNode = currentCupNode.next!!
            round++
        }

        val a = valueToNode[1]!!.next!!
        val b = a.next!!
        val result = a.value.toLong() * b.value

        println("final: a: ${a.value}, b:${b.value}")
        println(result)

        return 0
    }

    private fun findNode(head: MutableMap<Int, Node2>, value: Int): Node2 {
        return head[value]!!
    }

    fun getSolution2(): Int {
        val p = false

        // init
        val cupsValueToIdx = "389125467".mapIndexed { index, c -> c.toString().toInt() to index }.toMap().toMutableMap()
        val cupsIdxToValue = cupsValueToIdx.entries.associate { it.value to it.key }.toMutableMap()

        val minCup = 1
        val totalElements = 40
        var maxCupInRemaining = totalElements
        var currentCupValue = 3
        var round = 1
        val maxRound = 10 * totalElements

        // do rounds
        while (round <= maxRound) {
            // prepare for next round
            val currentCupIdx =
                if (currentCupValue in cupsValueToIdx) cupsValueToIdx[currentCupValue]!! else currentCupValue
            val pc = 100
            if (round > pc && round % pc == 0) {
                println("round: $round, idx: $currentCupIdx")
            }

            val firstPickupIdx = (currentCupIdx + 1) % totalElements
            val secondPickupIdx = (currentCupIdx + 2) % totalElements
            val thirdPickupIdx = (currentCupIdx + 3) % totalElements

            val pickupsIndexes = listOf(firstPickupIdx, secondPickupIdx, thirdPickupIdx)
            pickupsIndexes.forEach {
                if (it !in cupsIdxToValue) {
                    cupsIdxToValue[it] = it
                    cupsValueToIdx[it] = it
                }
            }

            var pickupsValues = pickupsIndexes.map { cupsIdxToValue[it] }

            if (p) {
                println(round)
                println(cupsValueToIdx)
                println(pickupsValues)
            }

            // 1. remove (or remember adjustment start pt)
            // pickups.forEach { cups.remove(it) }

            // 2. find destination cup
            while (maxCupInRemaining in pickupsValues) {
                maxCupInRemaining -= 1
            }
            var destinationValue = currentCupValue - 1
            if (destinationValue < minCup) {
                destinationValue = maxCupInRemaining
            }
            while (destinationValue in pickupsValues) {
                destinationValue--
                if (destinationValue < minCup) {
                    destinationValue = maxCupInRemaining
                }
            }

            if (p) {
                println("$destinationValue")
                println()
            }

            // 3. find destination cup index(before index adjustment)

            // 3. insert back at destination
            if (destinationValue !in cupsValueToIdx) {
                cupsValueToIdx[destinationValue] = destinationValue
                cupsIdxToValue[destinationValue] = destinationValue
            }

            val destinationCupIdx = cupsValueToIdx[destinationValue]!!
            println()
            if (round in 7..13) {
                println(cupsIdxToValue.values)
                //println(cupsValueToIdx)
            }

            //println("round=$round, currentIdx=$currentCupIdx, currentValue=$currentCupValue, pickups: $pickupsValues, first=$firstPickupIdx, destValue=$destinationValue, destIdx=$destinationCupIdx")
            if (firstPickupIdx < destinationCupIdx) {
                // 1. only elements in between are affected, left shift
                // 1.1 shift
                for (affectedCupIdx in firstPickupIdx..(destinationCupIdx - 3)) {
                    if (affectedCupIdx !in cupsIdxToValue) {
                        val value = affectedCupIdx + 3
                        cupsIdxToValue[affectedCupIdx] = value
                        cupsValueToIdx[value] = affectedCupIdx
                    } else {
                        val idx = affectedCupIdx + 3
                        val value = if (idx in cupsIdxToValue) cupsIdxToValue[idx]!! else idx
                        cupsIdxToValue[affectedCupIdx] = value
                        cupsValueToIdx[value] = affectedCupIdx
                    }
                }
                // 1.2 fill in picked values

                cupsIdxToValue[destinationCupIdx - 2] = pickupsValues[0]!!
                cupsIdxToValue[destinationCupIdx - 1] = pickupsValues[1]!!
                cupsIdxToValue[destinationCupIdx] = pickupsValues[2]!!
                cupsValueToIdx[pickupsValues[0]!!] = destinationCupIdx - 2
                cupsValueToIdx[pickupsValues[1]!!] = destinationCupIdx - 1
                cupsValueToIdx[pickupsValues[2]!!] = destinationCupIdx
            } else {
                // 2. from destIdx to startIdx, right shift
                // 2.1 shift
                for (affectedCupIdx in (destinationCupIdx + 3) until firstPickupIdx) {
                    if (affectedCupIdx !in cupsIdxToValue) {
                        val value = affectedCupIdx - 3
                        cupsIdxToValue[affectedCupIdx] = value
                        cupsValueToIdx[value] = affectedCupIdx
                    } else {
                        val idx = affectedCupIdx - 3
                        val value = cupsIdxToValue[idx]!!
                        cupsIdxToValue[affectedCupIdx] = value
                        cupsValueToIdx[value] = affectedCupIdx
                    }
                }
                // 2.2 fill in picked ones
                val firstIdx = (destinationCupIdx + 1) % totalElements
                val secondIdx = (destinationCupIdx + 2) % totalElements
                val thirdIdx = (destinationCupIdx + 3) % totalElements
                cupsIdxToValue[firstIdx] = pickupsValues[0]!!
                cupsIdxToValue[secondIdx] = pickupsValues[1]!!
                cupsIdxToValue[thirdIdx] = pickupsValues[2]!!
                cupsValueToIdx[pickupsValues[0]!!] = firstIdx
                cupsValueToIdx[pickupsValues[1]!!] = secondIdx
                cupsValueToIdx[pickupsValues[2]!!] = thirdIdx
            }

            //cups.addAll(destinationCupIdx + 1, pickups)
            currentCupValue = cupsIdxToValue[firstPickupIdx]!!

            // 4. adjust affected element indexes

            // 5. increase round
            round++

            if (round == maxRound + 1) {
                val idxOfOne = cupsValueToIdx[1]!!
                val aIdx = (idxOfOne + 1) % totalElements
                val bIdx = (idxOfOne + 2) % totalElements
                val r = cupsIdxToValue[aIdx]!!.toLong() * cupsIdxToValue[bIdx]!!.toLong()
                println("Solution 2: $r")
            }
        }
        return 0
    }

}

class Node2(val value: Int, var next: Node2?)

fun main() {
    // 166298218695
    D23().getSolution2V3()
}