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

interface Moveable {
    var xCount: Int
    var yCount: Int
}

class Day12 {
    private val inputs = IoHelper.getLines("d12.in")
    private val parsedInputs = inputs.map { it[0].toString() to it.drop(1).toInt() }

    fun getSolution1(): Int {
        val boat = Boat()
        parsedInputs.forEach {
            var toVerify = it
            if (toVerify.first == "R" || toVerify.first == "L") {
                boat.facing = boat.turnTo(toVerify)
            }
            if (toVerify.first == "F") {
                toVerify = boat.facing.key to toVerify.second
            }
            boat.move(toVerify)
        }
        return boat.xCount.absoluteValue + boat.yCount.absoluteValue
    }

    private fun Boat.move(instruction: Pair<String, Int>) {
        when (instruction.first) {
            Direction.NORTH.key -> this.yCount = this.yCount + instruction.second
            Direction.SOUTH.key -> this.yCount = this.yCount - instruction.second
            Direction.EAST.key -> this.xCount = this.xCount + instruction.second
            Direction.WEST.key -> this.xCount = this.xCount - instruction.second
        }
    }

    private fun WayPoint.move(instruction: Pair<String, Int>) {
        when (instruction.first) {
            Direction.NORTH.key -> this.yCount = this.yCount + instruction.second
            Direction.SOUTH.key -> this.yCount = this.yCount - instruction.second
            Direction.EAST.key -> this.xCount = this.xCount + instruction.second
            Direction.WEST.key -> this.xCount = this.xCount - instruction.second
        }
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
                val (xCount, yCount) = wayPoint.turnTo(it)
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

fun Boat.turnTo(instruction: Pair<String, Int>): Direction = when {
    (this.facing == Direction.NORTH) && (instruction.second == 180) -> Direction.SOUTH
    (this.facing == Direction.EAST) && (instruction.second == 180) -> Direction.WEST
    (this.facing == Direction.SOUTH) && (instruction.second == 180) -> Direction.NORTH
    (this.facing == Direction.WEST) && (instruction.second == 180) -> Direction.EAST

    (this.facing == Direction.EAST) && (instruction.second == 90) || (this.facing == Direction.WEST) && (instruction.second == 270) -> if (instruction.first == "R") Direction.SOUTH else Direction.NORTH
    (this.facing == Direction.SOUTH) && (instruction.second == 90) || (this.facing == Direction.NORTH) && (instruction.second == 270) -> if (instruction.first == "R") Direction.WEST else Direction.EAST
    (this.facing == Direction.EAST) && (instruction.second == 270) || (this.facing == Direction.WEST) && (instruction.second == 90) -> if (instruction.first == "R") Direction.NORTH else Direction.SOUTH
    (this.facing == Direction.SOUTH) && (instruction.second == 270) || (this.facing == Direction.NORTH) && (instruction.second == 90) -> if (instruction.first == "R") Direction.EAST else Direction.WEST
    else -> error("direction turn right to")
}

fun WayPoint.turnTo(instruction: Pair<String, Int>): Pair<Int, Int> = when {
    (instruction.second == 90 && instruction.first == "R") || (instruction.second == 270 && instruction.first == "L") -> this.yCount to this.xCount * -1
    instruction.second == 180 -> this.xCount * -1 to this.yCount * -1
    (instruction.second == 270 && instruction.first == "R") || (instruction.second == 90 && instruction.first == "L") -> this.yCount * -1 to this.xCount
    else -> error("way point ture right to")
}

fun main() {
    //362
    println(Day12().getSolution1())
    //29895
    println(Day12().getSolution2())
}