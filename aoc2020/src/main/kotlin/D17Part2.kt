import utils.IoHelper

class Day17Part2 {
    private val inputs = IoHelper.getLines("d17Test.in")

    private fun neighboursOfXYZ(
        x: Int,
        y: Int,
        z: Int,
        w: Int
    ): ArrayList<Pair<Int, ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>>>> {
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


        val neighboursOnMiddleLayer2 = arrayListOf(
            x to y,
            x to y + 1,
            x to y - 1,
            x + 1 to y,
            x - 1 to y,
            x - 1 to y + 1,
            x - 1 to y - 1,
            x + 1 to y + 1,
            x + 1 to y - 1
        )


        val neighboursOnWUpperLayer = arrayListOf<Pair<Int, ArrayList<Pair<Int, Int>>>>()
        neighboursOnWUpperLayer.addAll(
            arrayListOf(
                z to neighboursOnMiddleLayer2,
                z + 1 to neighboursOnUpperLayer,
                z - 1 to neighboursOnLowerLayer
            )
        )

        val neighboursOnWLowerLayer = arrayListOf<Pair<Int, ArrayList<Pair<Int, Int>>>>()
        neighboursOnWLowerLayer.addAll(
            arrayListOf(
                z to neighboursOnMiddleLayer2,
                z + 1 to neighboursOnUpperLayer,
                z - 1 to neighboursOnLowerLayer
            )
        )

        val neighboursOnFourDimens = arrayListOf(
            w to neighboursOnThreeLayers,
            w + 1 to neighboursOnWUpperLayer,
            w - 1 to neighboursOnWLowerLayer
        )


        return neighboursOnFourDimens
    }

    fun getSolution1() {

        val mapOfActive = mutableMapOf<Int, ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>>>()



        mapOfActive[0] = arrayListOf<Pair<Int, ArrayList<Pair<Int, Int>>>>()
        val arrayListOf2dimes = arrayListOf<Pair<Int, Int>>()
        inputs.forEachIndexed { yIndex, s ->
            s.forEachIndexed { xIndex, c ->
                if (c.toString() == "#") {
                    arrayListOf2dimes.add(xIndex to yIndex)
                }
            }
        }
        mapOfActive[0]?.add(0 to arrayListOf2dimes)


        //mapOfActive[0] = arrayListOf(1 to 0, 2 to 1, 0 to 2, 1 to 2, 2 to 2)

        var currentMapOfActive = mapOfActive.toMap()
        for (i in 0..5) {

            val minW = currentMapOfActive.keys.min()!! - 1
            val maxW = currentMapOfActive.keys.max()!! + 1

            val minZ = currentMapOfActive.values.flatten().map { it.first }.min()!!-1
            val maxZ = currentMapOfActive.values.flatten().map { it.first }.max()!!+1

            val listToCheck4dimens = arrayListOf<Pair<Int, ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>>>>()


            val listToCheck = arrayListOf<Pair<Int, ArrayList<Pair<Int, Int>>>>()
            for (z in minZ..maxZ) {
                listToCheck.add(z to getOneLayerToCheck(currentMapOfActive))
            }

            for(w in minW..maxW){
                listToCheck4dimens.add(w to listToCheck)
            }

            println(listToCheck)
            println(listToCheck.map { it.second.size }.sum())


            val newMap = mutableMapOf<Int, ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>>>()

            currentMapOfActive.forEach {
                val newArrayList = arrayListOf<Pair<Int, ArrayList<Pair<Int, Int>>>>()
                newArrayList.addAll(it.value.toList().toMutableList())
                newMap[it.key] = newArrayList
            }

            listToCheck4dimens.forEach{wLayered->
                wLayered.second.forEach { zLayered ->
                    zLayered.second.forEach {
                        val itSelfActive = areYouActive(it.first, it.second, zLayered.first, wLayered.first, currentMapOfActive)
                        val neighbours = neighboursOfXYZ(it.first, it.second, zLayered.first, wLayered.first)
                        var neighboursActiveCount = 0

                        neighbours.forEach {neighboursWLayered ->
                            neighboursWLayered.second.forEach { neighboursZLayered ->
                                neighboursZLayered.second.forEach {
                                    if (areYouActive(
                                            it.first,
                                            it.second,
                                            neighboursZLayered.first,
                                            neighboursWLayered.first,
                                            currentMapOfActive
                                        )
                                    ) neighboursActiveCount += 1
                                }
                            }
                        }


                        if (itSelfActive) {
                            if (neighboursActiveCount !in 2..3) {
                                newMap[wLayered.first]!!.forEach {z->
                                    if(z.first == zLayered.first){
                                        z.second.remove(it.first to it.second)
                                    }
                                }
                            }
                        } else {
                            if (neighboursActiveCount == 3) {
                                if(newMap[wLayered.first] == null){
                                    newMap[wLayered.first] = arrayListOf(zLayered.first to arrayListOf(it.first to it.second))
                                } else if(newMap[wLayered.first]!!.map { it.first }.contains(zLayered.first)){
                                    newMap[wLayered.first]!!.forEach {z->
                                        if(z.first == zLayered.first){
                                            z.second.add(it.first to it.second)
                                        }
                                    }
                                }else {
                                    newMap[wLayered.first]!!.add(zLayered.first to arrayListOf(it.first to it.second))
                                }

                            }
                        }

                    }
                }



            }


            currentMapOfActive = newMap.toMap()
        }

        println()
        println("c    "+currentMapOfActive)
        println(currentMapOfActive.values.flatten().map { it.second.size }.sum())

    }

    fun areYouActive(x: Int, y: Int, z: Int, w:Int, mapOfActive: Map<Int, ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>>>): Boolean {
        if(!mapOfActive[w].isNullOrEmpty() && mapOfActive[w]!!.map { it.first }.contains(z) && mapOfActive[w]!!.map { it.second }.flatten().contains(x to y)) {
            return true
        }
        return false
    }

    fun getOneLayerToCheck(mapOfActive: Map<Int, ArrayList<Pair<Int, ArrayList<Pair<Int, Int>>>>>): ArrayList<Pair<Int, Int>> {




        val minX = mapOfActive.map { it.value.map { it.second } .flatten().map { it.first }.min()!!}.min()!! - 1
        val minY =mapOfActive.map { it.value.map { it.second } .flatten().map { it.second }.min()!!}.min()!! - 1
        val maxX = mapOfActive.map { it.value.map { it.second } .flatten().map { it.first }.max()!!}.max()!! + 1
        val maxY = mapOfActive.map { it.value.map { it.second } .flatten().map { it.second }.max()!!}.max()!! + 1

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
    //322
    println(Day17Part2().getSolution1())
    println(Day17().getSolution2())
}