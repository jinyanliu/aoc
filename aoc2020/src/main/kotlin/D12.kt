import utils.IoHelper
import kotlin.math.absoluteValue

enum class Direction(val key: String) { WEST("W"), EAST("E"), NORTH("N"), SOUTH("S") }
enum class XDirection(val key: String) { WEST("W"), EAST("E") }
enum class YDirection(val key: String) { NORTH("N"), SOUTH("S") }

class Day12 {
    private val inputs = IoHelper.getLines("d12.in")
    private val parsedInputs = inputs.map { it.get(0).toString() to it.drop(1).toInt() }

    fun getSolution1(): Int {
        val boat = Boat()
        println(parsedInputs)
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
            if (toVerify.first == "R") {
                boat.facing = boat.facing.turnRightTo(toVerify.second)
            }
            if (toVerify.first == "L") {
                boat.facing = boat.facing.turnLeftTo(toVerify.second)
            }
        }

        println("xCount=" + boat.xCount + "yCount=" + boat.yCount)
        return boat.xCount.absoluteValue + boat.yCount.absoluteValue
    }

    fun getSolution2(): Int {
        val boat = Boat()
        val wayPoint = WayPoint()

        parsedInputs.forEach {
            var toVerify = it
            if (toVerify.first == "F") {
                boat.xCount = boat.xCount + toVerify.second * wayPoint.xCount
                boat.yCount = boat.yCount + toVerify.second * wayPoint.yCount
            }
            when (toVerify.first) {
                Direction.NORTH.key -> wayPoint.yCount = wayPoint.yCount + toVerify.second
                Direction.SOUTH.key -> wayPoint.yCount = wayPoint.yCount - toVerify.second
                Direction.EAST.key -> wayPoint.xCount = wayPoint.xCount + toVerify.second
                Direction.WEST.key -> wayPoint.xCount = wayPoint.xCount - toVerify.second
            }
            if (toVerify.first == "R") {
                val (a, b) = wayPoint.turnRightTo(toVerify.second)
                wayPoint.xCount = a
                wayPoint.yCount = b
            }
            if (toVerify.first == "L") {
                val (a, b) = wayPoint.turnLeftTo(toVerify.second)
                wayPoint.xCount = a
                wayPoint.yCount = b
            }
        }

        println("xCount=" + boat.xCount + "yCount=" + boat.yCount)
        return boat.xCount.absoluteValue + boat.yCount.absoluteValue
    }
}

data class Boat(
    var facing: Direction = Direction.EAST,
    var xDirection: XDirection = XDirection.EAST,
    var xCount: Int = 0,
    var yDirection: YDirection = YDirection.NORTH,
    var yCount: Int = 0
)

data class WayPoint(
    var xDirection: XDirection = XDirection.EAST,
    var xCount: Int = 10,
    var yDirection: YDirection = YDirection.NORTH,
    var yCount: Int = 1
)

fun Direction.turnRightTo(degree: Int): Direction {
    when (this) {
        Direction.EAST -> {
            return when (degree) {
                90 -> Direction.SOUTH
                180 -> Direction.WEST
                270 -> Direction.NORTH
                else -> error("")
            }
        }
        Direction.SOUTH -> {
            return when (degree) {
                90 -> Direction.WEST
                180 -> Direction.NORTH
                270 -> Direction.EAST
                else -> error("")
            }
        }
        Direction.WEST -> {
            return when (degree) {
                90 -> Direction.NORTH
                180 -> Direction.EAST
                270 -> Direction.SOUTH
                else -> error("")
            }
        }
        Direction.NORTH -> {
            return when (degree) {
                90 -> Direction.EAST
                180 -> Direction.SOUTH
                270 -> Direction.WEST
                else -> error("")
            }
        }
    }
}

fun Direction.turnLeftTo(degree: Int): Direction {
    when (this) {
        Direction.EAST -> {
            return when (degree) {
                90 -> Direction.NORTH
                180 -> Direction.WEST
                270 -> Direction.SOUTH
                else -> error("")
            }
        }
        Direction.SOUTH -> {
            return when (degree) {
                90 -> Direction.EAST
                180 -> Direction.NORTH
                270 -> Direction.WEST
                else -> error("")
            }
        }
        Direction.WEST -> {
            return when (degree) {
                90 -> Direction.SOUTH
                180 -> Direction.EAST
                270 -> Direction.NORTH
                else -> error("")
            }
        }
        Direction.NORTH -> {
            return when (degree) {
                90 -> Direction.WEST
                180 -> Direction.SOUTH
                270 -> Direction.EAST
                else -> error("")
            }
        }
    }
}

fun WayPoint.turnRightTo(degree: Int): Pair<Int, Int> {
    return when (degree) {
        90 -> this.yCount to this.xCount * -1
        180 -> this.xCount * -1 to this.yCount * -1
        270 -> this.yCount * -1 to this.xCount
        else -> error("")
    }
}

fun WayPoint.turnLeftTo(degree: Int): Pair<Int, Int> {
    return when (degree) {
        90 -> this.yCount * -1 to this.xCount
        180 -> this.xCount * -1 to this.yCount * -1
        270 -> this.yCount to this.xCount * -1
        else -> error("")
    }
}

fun main() {
    //362
    println(Day12().getSolution1())
    //29895
    println(Day12().getSolution2())
}