import kotlin.math.abs

object D17 {
    private val xRange = 135..155
    private val yRange = -102..-78
    var counter = 0

    fun solveOne(): Long {
        var count = 0L
        for (i in 0 until abs(yRange.first)) {
            count += i
        }
        return count
    }

    fun solveTwo() {
        for (x in getMinXVelocity()..xRange.last) {
            for (y in yRange.first until abs(yRange.first)) {
                moveBy(x, y)
            }
        }
    }

    private fun getMinXVelocity(): Int {
        var count = 0
        var step = 0
        while (true) {
            step += 1
            count += step
            if (xRange.first <= count) {
                break
            }
        }
        return step
    }

    private fun moveBy(vX: Int, vY: Int): Pair<Int, Int> {
        var velocityX = vX
        var velocityY = vY
        var x = 0
        var y = 0
        while (true) {
            x += velocityX
            if (velocityX > 0) {
                velocityX -= 1
            } else if (velocityX < 0) {
                velocityX += 1
            }

            y += velocityY
            velocityY -= 1

            if (x in xRange && y in yRange) {
                counter += 1
                break
            }

            if (y < yRange.first || x > xRange.last) {
                break
            }
        }
        return x to y
    }
}

fun main() {
    //5151
    val solutionOne = D17.solveOne()
    println("One=$solutionOne")
    //968
    D17.solveTwo()
    println("Tw0=${D17.counter}")
}