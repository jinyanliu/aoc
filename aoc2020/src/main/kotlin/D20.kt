import utils.IoHelper

class Day20 {
    private val sections = IoHelper.getSections("d20.in")
    private val whoHasNoFriendsOn2Sides = mutableListOf<Long>()

    private fun getAllSides(mapOfTiles: MutableMap<Long, Tile>): MutableList<String> = mapOfTiles.flatMap { it.value.toSidesList() }.toMutableList()

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
        println(whoHasNoFriendsOn2Sides)
        return whoHasNoFriendsOn2Sides.reduce { acc, l -> acc * l }
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
            println(tile.key)
            mapOfSideInOthers[tile.key] = arrayListOf()

            val tileSides =tile.value.toSidesList()

            var sideInOthersCount: Long = 0

            for (side in tileSides) {
                if (allSides.count { it == side } > 1) {
                    sideInOthersCount += 1
                    mapOfSideInOthers[tile.key]!!.add(side)
                }
            }
            //println(sideInOthersCount)

            if (sideInOthersCount == 4L) {
                whoHasNoFriendsOn2Sides.add(tile.key)
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

/*        for(side in mapOfTiles.get(3083L)!!){

        }*/

        for(side in mapOfSideInOthers.get(3083L)!!.withIndex()){
            for(tile in mapOfTiles){
                if(tile.key != 3083L){
                    val tileSides = tile.value.toSidesList()
                    if(tileSides.contains(side.value)){
                        println("tilekey"+tile.key)
                        println(side.value)
                        println(side.index)
                    }
                }
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