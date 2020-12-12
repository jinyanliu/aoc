import utils.IoHelper
import kotlin.math.absoluteValue

enum class Direction(val key: String) { WEST("W"), EAST("E"), NORTH("N"), SOUTH("S") }

// NORTH xCount >0, SOUTH xCount <0, EAST yCount >0, WEST yCount <0
data class Boat(
    var facing: Direction = Direction.EAST,
    var xCount: Int = 0,
    var yCount: Int = 0
)

// NORTH xCount >0, SOUTH xCount <0, EAST yCount >0, WEST yCount <0
data class WayPoint(
    var xCount: Int = 10,
    var yCount: Int = 1
)

class Day12 {
    private val inputs = IoHelper.getLines("d12.in")
    private val parsedInputs = inputs.map { it[0].toString() to it.drop(1).toInt() }

    fun getSolution1(): Int {
        val boat = Boat()
        parsedInputs.forEach {
            var toVerify = it
            if (toVerify.first == "F") {
                toVerify = boat.facing.key to toVerify.second
            }
            when (toVerify.first) {
                Direction.NORTH.key -> boat.yCount = boat.yCount + toVerify.second
                Direction.SOUTH.key -> boat.yCount = boat.yCount - toVerify.second
                Direction.EAST.key -> boat.xCount = boat.xCount + toVerify.second
                Direction.WEST.key -> boat.xCount = boat.xCount - toVerify.second
            }
            if (toVerify.first == "R" || toVerify.first == "L") {
                boat.facing = boat.turnTo(toVerify.second, toVerify.first)
            }
        }
        return boat.xCount.absoluteValue + boat.yCount.absoluteValue
    }

    fun getSolution2(): Int {
        val boat = Boat()
        val wayPoint = WayPoint()

        parsedInputs.forEach {
            if (it.first == "F") {
                boat.xCount = boat.xCount + it.second * wayPoint.xCount
                boat.yCount = boat.yCount + it.second * wayPoint.yCount
            }
            if (it.first == "R" || it.first == "L") {
                val (xCount, yCount) = wayPoint.turnTo(it.second, it.first)
                wayPoint.xCount = xCount
                wayPoint.yCount = yCount
            }
            when (it.first) {
                Direction.NORTH.key -> wayPoint.yCount = wayPoint.yCount + it.second
                Direction.SOUTH.key -> wayPoint.yCount = wayPoint.yCount - it.second
                Direction.EAST.key -> wayPoint.xCount = wayPoint.xCount + it.second
                Direction.WEST.key -> wayPoint.xCount = wayPoint.xCount - it.second
            }
        }
        return boat.xCount.absoluteValue + boat.yCount.absoluteValue
    }
}

fun Boat.turnTo(degree: Int, rightLeftKey: String): Direction = when {
    (this.facing == Direction.NORTH) && (degree == 180) -> Direction.SOUTH
    (this.facing == Direction.EAST) && (degree == 180) -> Direction.WEST
    (this.facing == Direction.SOUTH) && (degree == 180) -> Direction.NORTH
    (this.facing == Direction.WEST) && (degree == 180) -> Direction.EAST

    (this.facing == Direction.EAST) && (degree == 90) || (this.facing == Direction.WEST) && (degree == 270) -> if (rightLeftKey == "R") Direction.SOUTH else Direction.NORTH
    (this.facing == Direction.SOUTH) && (degree == 90) || (this.facing == Direction.NORTH) && (degree == 270) -> if (rightLeftKey == "R") Direction.WEST else Direction.EAST
    (this.facing == Direction.EAST) && (degree == 270) || (this.facing == Direction.WEST) && (degree == 90) -> if (rightLeftKey == "R") Direction.NORTH else Direction.SOUTH
    (this.facing == Direction.SOUTH) && (degree == 270) || (this.facing == Direction.NORTH) && (degree == 90) -> if (rightLeftKey == "R") Direction.EAST else Direction.WEST
    else -> error("direction turn right to")
}

fun WayPoint.turnTo(degree: Int, rightLeftKey: String): Pair<Int, Int> = when {
    (degree == 90 && rightLeftKey == "R") || (degree == 270 && rightLeftKey == "L") -> this.yCount to this.xCount * -1
    degree == 180 -> this.xCount * -1 to this.yCount * -1
    (degree == 270 && rightLeftKey == "R") || (degree == 90 && rightLeftKey == "L") -> this.yCount * -1 to this.xCount
    else -> error("way point ture right to")
}

fun main() {
    //362
    println(Day12().getSolution1())
    //29895
    println(Day12().getSolution2())
}