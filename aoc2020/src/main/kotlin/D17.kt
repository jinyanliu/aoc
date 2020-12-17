import utils.IoHelper

class Day17 {
    private val inputs = IoHelper.getLines("d17.in")

    private fun neighboursOfXYZ(x: Int, y: Int, z: Int): ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>> {
        val neighboursOnMiddleLayer = arrayListOf(
            x to y + 1,
            x to y - 1,
            x + 1 to y,
            x - 1 to y,
            x - 1 to y + 1,
            x - 1 to y - 1,
            x + 1 to y + 1,
            x + 1 to y - 1
        )

        val neighboursOnUpperLayer = arrayListOf<Pair<Int, Int>>()
        neighboursOnUpperLayer.addAll(neighboursOnMiddleLayer)
        neighboursOnUpperLayer.add(x to y)

        val neighboursOnLowerLayer = arrayListOf<Pair<Int, Int>>()
        neighboursOnLowerLayer.addAll(neighboursOnMiddleLayer)
        neighboursOnLowerLayer.add(x to y)

        val neighboursOnThreeLayers = arrayListOf(
            z to neighboursOnMiddleLayer,
            z + 1 to neighboursOnUpperLayer,
            z - 1 to neighboursOnLowerLayer
        )
        return neighboursOnThreeLayers
    }

    fun getSolution1() {
        val mapOfActive = mutableMapOf<Int, ArrayList<Pair<Int, Int>>>()
        mapOfActive[0] = arrayListOf(1 to 0, 2 to 1, 0 to 2, 1 to 2, 2 to 2)
        val minX = mapOfActive.map { it.value.map { it.first }.min()!! }.min()!!-1
        val minY = mapOfActive.map { it.value.map { it.second }.min()!! }.min()!!-1
        val maxX = mapOfActive.map { it.value.map { it.first }.max()!! }.max()!!+1
        val maxY = mapOfActive.map { it.value.map { it.second }.max()!! }.max()!!+1
        val minZ = mapOfActive.keys.min()!!-1
        val maxZ = mapOfActive.keys.max()!!+1

        val listToCheck = arrayListOf<Pair<Int, ArrayList<Pair<Int, Int>>>>()
        for (z in minZ..maxZ) {
            listToCheck.add(z to getOneLayerToCheck(mapOfActive))
        }

        println(listToCheck)
        println(listToCheck.map { it.second.size }.sum())

        var currentMapOfActive = mapOfActive.toMap()
/*        for(i in 0..5){*/
            val newMap = mutableMapOf<Int, ArrayList<Pair<Int, Int>>>()

        currentMapOfActive.forEach {
            val newArrayList = arrayListOf<Pair<Int, Int>>()
            newArrayList.addAll(it.value.toList())
            newMap[it.key] = newArrayList
        }

            listToCheck.forEach { zLayered ->
                zLayered.second.forEach {
                    val itSelfActive = areYouActive(it.first, it.second, zLayered.first, currentMapOfActive)
                    val neighbours = neighboursOfXYZ(it.first, it.second, zLayered.first)
                    var neighboursActiveCount = 0
                    neighbours.forEach { neighboursZLayered->
                        neighboursZLayered.second.forEach {
                            if(areYouActive(it.first, it.second, neighboursZLayered.first, currentMapOfActive)) neighboursActiveCount+=1
                        }
                    }
                    if(itSelfActive){
                        if(neighboursActiveCount !in 2..3){
                            newMap[zLayered.first]?.remove(it.first to it.second)
                        }
                    }else {
                        if(neighboursActiveCount == 3){
                            if(newMap[zLayered.first] == null){
                                newMap[zLayered.first]= arrayListOf(it.first to it.second)
                            }else {
                                newMap[zLayered.first]?.add(it.first to it.second)
                            }

                        }
                    }

                }
            }
            //currentMapOfActive = newMap.toMap()
        //}

        println(newMap)

    }

    fun areYouActive(x: Int, y: Int, z: Int, mapOfActive: Map<Int, ArrayList<Pair<Int, Int>>>): Boolean {
        if (!mapOfActive[z].isNullOrEmpty() && mapOfActive[z]!!.contains(x to y)) {
            return true
        }
        return false
    }

    fun getOneLayerToCheck(mapOfActive: Map<Int, ArrayList<Pair<Int, Int>>>): ArrayList<Pair<Int, Int>> {
        val minX = mapOfActive.map { it.value.map { it.first }.min()!! }.min()!!-1
        val minY = mapOfActive.map { it.value.map { it.second }.min()!! }.min()!!-1
        val maxX = mapOfActive.map { it.value.map { it.first }.max()!! }.max()!!+1
        val maxY = mapOfActive.map { it.value.map { it.second }.max()!! }.max()!!+1

        val oneLayerToCheck = arrayListOf<Pair<Int, Int>>()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                oneLayerToCheck.add(x to y)
            }
        }

        return oneLayerToCheck


    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day17().getSolution1())
    println(Day17().getSolution2())
}