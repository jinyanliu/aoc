import utils.IoHelper

class Day20 {
    private val sections = IoHelper.getSections("d20.in")

    fun getSolution1():Long {
        // Up, Down, Left, Right
        val mapOfTiles = mutableMapOf<Long, Tile>()
        for (section in sections) {
            val lines = section.lines()
            val tileKey = lines.get(0).split(" ")[1].dropLast(1).toLong()
            val left = lines.drop(1).map { it[0].toString() }.joinToString("")
            val right = lines.drop(1).map { it.last().toString() }.joinToString("")
            mapOfTiles[tileKey] = Tile(up = lines[1], down = lines.last(), left = left, right = right)
        }
        println(mapOfTiles)
        println(mapOfTiles.size)

        val whoHasNoFriendsOn2Sides = mutableListOf<Long>()

        for (tile in mapOfTiles) {
            println(tile.key)

            val allSides =
                mapOfTiles.flatMap {
                    mutableListOf(
                        it.value.up,
                        it.value.upRe,
                        it.value.down,
                        it.value.downRe,
                        it.value.left,
                        it.value.LeftRe,
                        it.value.right,
                        it.value.rightRe
                    )
                }
                    .toMutableList()

            println(allSides)

            val tileSides = mutableListOf(
                tile.value.up,
                tile.value.upRe,
                tile.value.down,
                tile.value.downRe,
                tile.value.left,
                tile.value.LeftRe,
                tile.value.right,
                tile.value.rightRe
            )

            println(tileSides)



            println(allSides)


            var sideInOthersCount = 0
            for (side in tileSides) {
                if (allSides.count { it == side }>1) {
                    sideInOthersCount += 1
                }
            }
            println(sideInOthersCount)

            if(sideInOthersCount == 4){
                whoHasNoFriendsOn2Sides.add(tile.key)
            }

            println()
        }

        println(whoHasNoFriendsOn2Sides)
        return whoHasNoFriendsOn2Sides.reduce { acc, l ->acc*l  }

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

fun main() {
    println(Day20().getSolution1())
    println(Day20().getSolution2())
}