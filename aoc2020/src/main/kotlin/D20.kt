import utils.IoHelper

class Day20 {
    private val sections = IoHelper.getSections("d20.in")
    private val whoAreTheCorners = mutableListOf<Long>()
    private val resolvedTiles = arrayListOf<Long>()
    private var tilesToResolve = arrayListOf<FixedTile>()

    private val mapOfLocations = mutableMapOf<Pair<Int, Int>, FixedTile>()

    private val mapOfTileStrings = getMapOfTileStrings()

    private fun getAllSides(mapOfTiles: MutableMap<Long, Tile>): MutableList<String> =
        mapOfTiles.flatMap { it.value.toSidesList() }.toMutableList()

    private fun getMapOfTileEdges(): MutableMap<Long, Tile> {
        val mapOfTiles = mutableMapOf<Long, Tile>()
        for (section in sections) {
            val lines = section.lines()
            val tileKey = lines.get(0).split(" ")[1].dropLast(1).toLong()
            val left = lines.drop(1).map { it[0].toString() }.joinToString("")
            val right = lines.drop(1).map { it.last().toString() }.joinToString("")
            mapOfTiles[tileKey] = Tile(up = lines[1], down = lines.last(), left = left, right = right)
        }
        println(mapOfTiles.size)
        return mapOfTiles
    }

    private fun getMapOfTileStrings(): MutableMap<Long, List<String>> {
        val mapOfTiles = mutableMapOf<Long, List<String>>()
        for (section in sections) {
            val lines = section.lines()
            val tileKey = lines.get(0).split(" ")[1].dropLast(1).toLong()
            mapOfTiles[tileKey] = lines.drop(1)
        }
        println(mapOfTiles.size)
        return mapOfTiles
    }

    fun getSolution1(): Long {
        fixImage(getMapOfTileEdges())
        println(whoAreTheCorners)
        return whoAreTheCorners.reduce { acc, l -> acc * l }
    }

    private fun fixImage(
        mapOfTiles: MutableMap<Long, Tile>
    ) {
        //Key: Side Count, Value: Tile Numbers
        val sideCountsMap = mutableMapOf<Long, ArrayList<Long>>(
            4L to arrayListOf(),
            6L to arrayListOf(),
            8L to arrayListOf()
        )

        //Key: Tile number, Value: sides shared with others
        val mapOfSideInOthers = mutableMapOf<Long, ArrayList<String>>()

        val allSides = getAllSides(mapOfTiles)

        for (tile in mapOfTiles) {
            //println(tile.key)
            mapOfSideInOthers[tile.key] = arrayListOf()

            val tileSides = tile.value.toSidesList()

            var sideInOthersCount: Long = 0

            for (side in tileSides) {
                if (allSides.count { it == side } > 1) {
                    sideInOthersCount += 1
                    mapOfSideInOthers[tile.key]!!.add(side)
                }
            }
            //println(sideInOthersCount)

            if (sideInOthersCount == 4L) {
                whoAreTheCorners.add(tile.key)
                sideCountsMap[4L]!!.add(tile.key)
            }
            if (sideInOthersCount == 6L) {
                sideCountsMap[6L]!!.add(tile.key)
            }
            if (sideInOthersCount == 8L) {
                sideCountsMap[8L]!!.add(tile.key)
            }
        }
        println(sideCountsMap)
        println(sideCountsMap[4L]!!.size)
        println(sideCountsMap[6L]!!.size)
        println(sideCountsMap[8L]!!.size)

        println(mapOfSideInOthers)

        val fixed2287 = FixedTile(
            2287L,
            mapOfTiles[2287L]!!.up,
            mapOfTiles[2287L]!!.down,
            mapOfTiles[2287L]!!.left,
            mapOfTiles[2287L]!!.right,
            mapOfTileStrings[2287L]!!
        )

        tilesToResolve.add(fixed2287)
        mapOfLocations[0 to 0] = fixed2287


        while (tilesToResolve.isNotEmpty()) {
            val currentTilesToResolve = arrayListOf<FixedTile>()

            for (parentTile in tilesToResolve) {
                println()
                println(parentTile)
                if (parentTile.name !in resolvedTiles) {
                    resolvedTiles.add(parentTile.name)
                }

                val parentLocation = mapOfLocations.filter { it.value.name == parentTile.name }.map { it.key }[0]


                val childrenList = mutableListOf<Long>()

                for (parentSide in parentTile.toSidesList().withIndex()) {
                    if (parentSide.value in mapOfSideInOthers.get(parentTile.name)!!) {

                        for (childTile in mapOfTiles) {
                            if (childTile.key !in resolvedTiles) {
                                val tileSides = childTile.value.toSidesList()
                                for (childSide in tileSides.withIndex()) {
                                    if (childSide.value == parentSide.value) {
                                        println()
                                        println("Find children...")
                                        println("Child Tile Key=" + childTile.key)
                                        println("Parent Side Index=" + parentSide.index)
                                        println("Child Side Index=" + childSide.index)

                                        if (childTile.key !in mapOfLocations.values.map { it.name }) {

                                            val fixedChild = FixedTile(
                                                childTile.key,
                                                childTile.value.up,
                                                childTile.value.down,
                                                childTile.value.left,
                                                childTile.value.right,
                                                mapOfTileStrings[childTile.key]!!
                                            )

                                            if (parentSide.index == 0) {

                                                when (childSide.index) {
                                                    0 ->//up
                                                    {
                                                        fixedChild.down = childTile.value.up
                                                        fixedChild.up = childTile.value.down
                                                        fixedChild.left = childTile.value.leftRe
                                                        fixedChild.right = childTile.value.rightRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList[9 - i]
                                                        }
                                                        fixedChild.fixedStrings = newList

                                                    }
                                                    1 ->//upre
                                                    {
                                                        fixedChild.down = childTile.value.upRe
                                                        fixedChild.up = childTile.value.downRe
                                                        fixedChild.left = childTile.value.rightRe
                                                        fixedChild.right = childTile.value.leftRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList[9 - i].reversed()
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }

                                                    2 ->//down
                                                    {
                                                        fixedChild.down = childTile.value.down
                                                        fixedChild.up = childTile.value.up
                                                        fixedChild.left = childTile.value.left
                                                        fixedChild.right = childTile.value.right
                                                        fixedChild.fixedStrings = mapOfTileStrings[childTile.key]!!
                                                    }
                                                    3 ->//downre
                                                    {
                                                        fixedChild.down = childTile.value.downRe
                                                        fixedChild.up = childTile.value.upRe
                                                        fixedChild.left = childTile.value.right
                                                        fixedChild.right = childTile.value.left

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList[i].reversed()
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                    4 ->//left
                                                    {
                                                        fixedChild.down = childTile.value.left
                                                        fixedChild.up = childTile.value.right
                                                        fixedChild.left = childTile.value.upRe
                                                        fixedChild.right = childTile.value.downRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList.map { it.get(9-i) }.joinToString("")
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                    5 ->//leftre
                                                    {
                                                        fixedChild.down = childTile.value.leftRe
                                                        fixedChild.up = childTile.value.rightRe
                                                        fixedChild.left = childTile.value.downRe
                                                        fixedChild.right = childTile.value.upRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList.map { it.get(9-i) }.joinToString("").reversed()
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                    6 ->//right
                                                    {
                                                        fixedChild.down = childTile.value.right
                                                        fixedChild.up = childTile.value.left
                                                        fixedChild.left = childTile.value.up
                                                        fixedChild.right = childTile.value.down

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList.map { it.get(i) }.joinToString("")
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                    7 ->//rightre
                                                    {
                                                        fixedChild.down = childTile.value.rightRe
                                                        fixedChild.up = childTile.value.leftRe
                                                        fixedChild.left = childTile.value.down
                                                        fixedChild.right = childTile.value.up

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList[i] = oldList.map { it.get(i) }.joinToString("").reversed()
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                }

                                                mapOfLocations[parentLocation.first + 1 to parentLocation.second] =
                                                    fixedChild

                                            } else {

                                                when (childSide.index) {
                                                    0 ->//up
                                                    {
                                                        fixedChild.left = childTile.value.up
                                                        fixedChild.right = childTile.value.down
                                                        fixedChild.up = childTile.value.left
                                                        fixedChild.down = childTile.value.right
                                                    }
                                                    1 ->//upre
                                                    {
                                                        fixedChild.left = childTile.value.upRe
                                                        fixedChild.right = childTile.value.downRe
                                                        fixedChild.up = childTile.value.right
                                                        fixedChild.down = childTile.value.left
                                                    }

                                                    2 ->//down
                                                    {
                                                        fixedChild.left = childTile.value.down
                                                        fixedChild.right = childTile.value.up
                                                        fixedChild.up = childTile.value.leftRe
                                                        fixedChild.down = childTile.value.rightRe
                                                    }
                                                    3 ->//downre
                                                    {
                                                        fixedChild.left = childTile.value.downRe
                                                        fixedChild.right = childTile.value.upRe
                                                        fixedChild.up = childTile.value.rightRe
                                                        fixedChild.down = childTile.value.leftRe
                                                    }
                                                    4 ->//left
                                                    {
                                                        fixedChild.left = childTile.value.left
                                                        fixedChild.right = childTile.value.right
                                                        fixedChild.up = childTile.value.up
                                                        fixedChild.down = childTile.value.down
                                                    }
                                                    5 ->//leftre
                                                    {
                                                        fixedChild.left = childTile.value.leftRe
                                                        fixedChild.right = childTile.value.rightRe
                                                        fixedChild.up = childTile.value.down
                                                        fixedChild.down = childTile.value.up
                                                    }
                                                    6 ->//right
                                                    {
                                                        fixedChild.left = childTile.value.right
                                                        fixedChild.right = childTile.value.left
                                                        fixedChild.up = childTile.value.upRe
                                                        fixedChild.down = childTile.value.downRe
                                                    }
                                                    7 ->//rightre
                                                    {
                                                        fixedChild.left = childTile.value.rightRe
                                                        fixedChild.right = childTile.value.leftRe
                                                        fixedChild.up = childTile.value.downRe
                                                        fixedChild.down = childTile.value.upRe
                                                    }
                                                }

                                                mapOfLocations[parentLocation.first to parentLocation.second + 1] =
                                                    fixedChild

                                            }

                                            if (fixedChild !in currentTilesToResolve) {
                                                currentTilesToResolve.add(fixedChild)
                                                println("Fixed child=" + fixedChild)
                                            }
                                        }

                                        if (childTile.key !in childrenList) {
                                            childrenList.add(childTile.key)
                                        }



                                        if (childTile.key !in resolvedTiles) {
                                            resolvedTiles.add(childTile.key)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                println("children list=" + childrenList)

/*            for (middleTile in mapOfTiles) {
                if (middleTile.key !in resolvedTiles) {
                    var middleTileShareSidesCount = 0
                    val middleTileSides = middleTile.value.toSidesList()
                    for (child in childrenList) {

                        val childSides = mapOfTiles.get(child)!!.toSidesList()

                        for (side in middleTileSides.withIndex()) {

                            for(childside in childSides.withIndex()){
                                if(side.value == childside.value){
                                    println()
                                    println("Find middle tile...")
                                    middleTileShareSidesCount += 1
                                    println("Current testing tile="+middleTile.key)
                                    println("child"+child)
                                    println("Middle tile side index=" + side.index)
                                    println("Child tile side index="+childside.index)
                                }

                            }
                        }
                    }
                    if (middleTileShareSidesCount == 4) {
                        println("Middle tile=" + middleTile.key)

                        if (middleTile.key !in currentTilesToResolve) {
                            currentTilesToResolve.add(middleTile.key)
                        }

                        if (middleTile.key !in resolvedTiles) {
                            resolvedTiles.add(middleTile.key)
                        }
                    }
                }
            }*/

                tilesToResolve = currentTilesToResolve
                println("currentTilesToResolve" + currentTilesToResolve.map { it.name })

                println()
                println()
                println()
                println()
            }

            println(mapOfLocations)
            println(mapOfLocations.size)
            println()
            println()
            println()
            println()
        }


    }

    fun getSolution2() {
    }
}

data class Tile(
    val up: String,
    val upRe: String = up.reversed(),
    val down: String,
    val downRe: String = down.reversed(),
    val left: String,
    val leftRe: String = left.reversed(),
    val right: String,
    val rightRe: String = right.reversed()
)

fun Tile.toSidesList() = mutableListOf(
    this.up,
    this.upRe,
    this.down,
    this.downRe,
    this.left,
    this.leftRe,
    this.right,
    this.rightRe
)

data class FixedTile(
    val name: Long,
    var up: String,
    var down: String,
    var left: String,
    var right: String,
    var fixedStrings: List<String>
)

fun FixedTile.toSidesList() = mutableListOf(
    this.up,
    this.down,
    this.left,
    this.right
)

fun main() {
    //Test Result: 20899048083289
    //83775126454273
    println(Day20().getSolution1())
    println(Day20().getSolution2())
}