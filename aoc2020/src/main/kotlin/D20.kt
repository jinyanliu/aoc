import utils.IoHelper

class Day20 {
    private val sections = IoHelper.getSections("d20.in")
    private val whoAreTheCorners = mutableListOf<Long>()
    private val resolvedTiles = arrayListOf<Long>()
    private var tilesToResolve = arrayListOf<Long>()

    private fun getAllSides(mapOfTiles: MutableMap<Long, Tile>): MutableList<String> =
        mapOfTiles.flatMap { it.value.toSidesList() }.toMutableList()

    private fun getMapOfTiles(): MutableMap<Long, Tile> {
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

    fun getSolution1(): Long {
        fixImage(getMapOfTiles())
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

        tilesToResolve.addAll(whoAreTheCorners)

        while(tilesToResolve.isNotEmpty()){
        for (parentTile in tilesToResolve) {
            println()
            println(parentTile)
            if (parentTile !in resolvedTiles) {
                resolvedTiles.add(parentTile)
            }

            val currentTilesToResolve = arrayListOf<Long>()

            val childrenList = mutableListOf<Long>()

            for (parentSide in mapOfTiles[parentTile]!!.toSidesList().withIndex()) {
                if (parentSide.value in mapOfSideInOthers.get(parentTile)!!) {

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

                                    if (childTile.key !in childrenList) {
                                        childrenList.add(childTile.key)
                                    }

                                    if (childTile.key !in currentTilesToResolve) {
                                        currentTilesToResolve.add(childTile.key)
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

            for (middleTile in mapOfTiles) {
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
            }

            tilesToResolve = currentTilesToResolve


            println()
            println()
            println()
            println()
        }
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
    val LeftRe: String = left.reversed(),
    val right: String,
    val rightRe: String = right.reversed()
)

fun Tile.toSidesList() = mutableListOf(
    this.up,
    this.upRe,
    this.down,
    this.downRe,
    this.left,
    this.LeftRe,
    this.right,
    this.rightRe
)

fun main() {
    //Test Result: 20899048083289
    //83775126454273
    println(Day20().getSolution1())
    println(Day20().getSolution2())
}