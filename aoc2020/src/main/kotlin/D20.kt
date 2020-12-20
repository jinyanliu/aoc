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
        return mapOfTiles
    }

    private fun getMapOfTileStrings(): MutableMap<Long, List<String>> {
        val mapOfTiles = mutableMapOf<Long, List<String>>()
        for (section in sections) {
            val lines = section.lines()
            val tileKey = lines.get(0).split(" ")[1].dropLast(1).toLong()
            mapOfTiles[tileKey] = lines.drop(1)
        }
        return mapOfTiles
    }

    fun getSolution1(): Long {
        fixImage(getMapOfTileEdges())
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

                                        if (childTile.key !in mapOfLocations.values.map { it.name }) {

                                            val fixedChild = FixedTile(
                                                childTile.key,
                                                "",
                                                "",
                                                "",
                                                "",
                                                arrayListOf("")
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
                                                            newList.add(i, oldList[9 - i])
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
                                                            newList.add(i, oldList[9 - i].reversed())
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
                                                            newList.add(i, oldList[i].reversed())
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
                                                            newList.add(
                                                                i,
                                                                oldList.map { it.get(9 - i) }.joinToString("")
                                                            )
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
                                                            newList.add(
                                                                i,
                                                                oldList.map { it.get(9 - i) }.joinToString("")
                                                                    .reversed()
                                                            )
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
                                                            newList.add(i, oldList.map { it.get(i) }.joinToString(""))
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
                                                            newList.add(
                                                                i,
                                                                oldList.map { it.get(i) }.joinToString("").reversed()
                                                            )
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                }

                                                mapOfLocations[parentLocation.first to parentLocation.second + 1] =
                                                    fixedChild

                                            } else {

                                                when (childSide.index) {
                                                    0 ->//up
                                                    {
                                                        fixedChild.left = childTile.value.up
                                                        fixedChild.right = childTile.value.down
                                                        fixedChild.up = childTile.value.left
                                                        fixedChild.down = childTile.value.right

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(i, oldList.map { it.get(i) }.joinToString(""))
                                                        }
                                                        fixedChild.fixedStrings = newList


                                                    }
                                                    1 ->//upre
                                                    {
                                                        fixedChild.left = childTile.value.upRe
                                                        fixedChild.right = childTile.value.downRe
                                                        fixedChild.up = childTile.value.right
                                                        fixedChild.down = childTile.value.left

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(
                                                                i,
                                                                oldList.map { it.get(9 - i) }.joinToString("")
                                                            )
                                                        }
                                                        fixedChild.fixedStrings = newList


                                                    }

                                                    2 ->//down
                                                    {
                                                        fixedChild.left = childTile.value.down
                                                        fixedChild.right = childTile.value.up
                                                        fixedChild.up = childTile.value.leftRe
                                                        fixedChild.down = childTile.value.rightRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(
                                                                i,
                                                                oldList.map { it.get(i) }.joinToString("").reversed()
                                                            )
                                                        }
                                                        fixedChild.fixedStrings = newList


                                                    }
                                                    3 ->//downre
                                                    {
                                                        fixedChild.left = childTile.value.downRe
                                                        fixedChild.right = childTile.value.upRe
                                                        fixedChild.up = childTile.value.rightRe
                                                        fixedChild.down = childTile.value.leftRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(
                                                                i,
                                                                oldList.map { it.get(9 - i) }.joinToString("")
                                                                    .reversed()
                                                            )
                                                        }
                                                        fixedChild.fixedStrings = newList

                                                    }
                                                    4 ->//left
                                                    {
                                                        fixedChild.left = childTile.value.left
                                                        fixedChild.right = childTile.value.right
                                                        fixedChild.up = childTile.value.up
                                                        fixedChild.down = childTile.value.down
                                                        fixedChild.fixedStrings = mapOfTileStrings[childTile.key]!!
                                                    }
                                                    5 ->//leftre
                                                    {
                                                        fixedChild.left = childTile.value.leftRe
                                                        fixedChild.right = childTile.value.rightRe
                                                        fixedChild.up = childTile.value.down
                                                        fixedChild.down = childTile.value.up

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(i, oldList[9 - i])
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                    6 ->//right
                                                    {
                                                        fixedChild.left = childTile.value.right
                                                        fixedChild.right = childTile.value.left
                                                        fixedChild.up = childTile.value.upRe
                                                        fixedChild.down = childTile.value.downRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(i, oldList[i].reversed())
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                    7 ->//rightre
                                                    {
                                                        fixedChild.left = childTile.value.rightRe
                                                        fixedChild.right = childTile.value.leftRe
                                                        fixedChild.up = childTile.value.downRe
                                                        fixedChild.down = childTile.value.upRe

                                                        val oldList = mapOfTileStrings[childTile.key]!!
                                                        val newList = arrayListOf<String>()
                                                        for (i in 0..9) {
                                                            newList.add(i, oldList[9 - i].reversed())
                                                        }
                                                        fixedChild.fixedStrings = newList
                                                    }
                                                }

                                                mapOfLocations[parentLocation.first + 1 to parentLocation.second] =
                                                    fixedChild

                                            }

                                            if (fixedChild !in currentTilesToResolve) {
                                                currentTilesToResolve.add(fixedChild)
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
                tilesToResolve = currentTilesToResolve
            }
        }
    }

    fun getSolution2(): Long {
        fixImage(getMapOfTileEdges())

        val newMapOfLocations = mutableMapOf<Pair<Int, Int>, List<String>>()
        for (everyLocation in mapOfLocations) {
            val currentFixedTile = everyLocation.value
            val newStringContent = currentFixedTile.fixedStrings.drop(1).dropLast(1).map { it.substring(1..8) }
            newMapOfLocations[everyLocation.key] = newStringContent
        }

        val bigStringContent = arrayListOf<String>()

        for (k in 0..95 step 8) {
            for (i in k..k + 7) {
                val oneBigLine =
                    newMapOfLocations.filter { it.key.second == 11 - (k / 8) }.map { it.value[i % 8] }.joinToString("")
                bigStringContent.add(i, oneBigLine)
            }
        }

        val oldList = bigStringContent
        val newList = arrayListOf<String>()
        for (i in 0..95) {
            newList.add(i, oldList.map { it.get(i) }.joinToString(""))
        }

        val charLocation = mutableMapOf<Pair<Int, Int>, String>()
        for (y in 0..95) {
            for (x in 0..95) {
                charLocation[x to y] = newList[y][x].toString()
            }
        }

        var monsterCounter = 0
        for (line in newList.withIndex()) {
            for (i in 0..95) {
                if (i + 19 <= 95) {
                    if (line.value[i].toString() == "#"
                        && line.value[i + 5].toString() == "#"
                        && line.value[i + 6].toString() == "#"
                        && line.value[i + 11].toString() == "#"
                        && line.value[i + 12].toString() == "#"
                        && line.value[i + 17].toString() == "#"
                        && line.value[i + 18].toString() == "#"
                        && line.value[i + 19].toString() == "#"
                    ) {
                        val upperLine = newList[line.index - 1]
                        val lowerLine = newList[line.index + 1]

                        if (upperLine.get(i + 18).toString() == "#") {
                            if (lowerLine.get(i + 1).toString() == "#"
                                && lowerLine.get(i + 4).toString() == "#"
                                && lowerLine.get(i + 7).toString() == "#"
                                && lowerLine.get(i + 10).toString() == "#"
                                && lowerLine.get(i + 13).toString() == "#"
                                && lowerLine.get(i + 16).toString() == "#"
                            ) {
                                monsterCounter += 1

                                charLocation[i to line.index] = "O"
                                charLocation[i + 5 to line.index] = "O"
                                charLocation[i + 6 to line.index] = "O"
                                charLocation[i + 11 to line.index] = "O"
                                charLocation[i + 12 to line.index] = "O"
                                charLocation[i + 17 to line.index] = "O"
                                charLocation[i + 18 to line.index] = "O"
                                charLocation[i + 19 to line.index] = "O"

                                charLocation[i + 18 to line.index - 1] = "O"

                                charLocation[i + 1 to line.index + 1] = "O"
                                charLocation[i + 4 to line.index + 1] = "O"
                                charLocation[i + 7 to line.index + 1] = "O"
                                charLocation[i + 10 to line.index + 1] = "O"
                                charLocation[i + 13 to line.index + 1] = "O"
                                charLocation[i + 16 to line.index + 1] = "O"
                            }
                        }
                    }
                }
            }
        }
        return charLocation.values.count { it == "#" }.toLong()
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
    //1993
    println(Day20().getSolution2())
}