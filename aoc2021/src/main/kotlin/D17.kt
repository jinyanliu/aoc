import utils.IoHelper

object D17 {
    //target area: x=20..30, y=-10..-5
    private val inputs = IoHelper.getRawContent("d17.in")
     var counter = 0

    fun moveBy(vX: Int, vY: Int, targetX: IntRange, targetY: IntRange): Pair<Int, Int> {
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

            if (x in targetX && y in targetY) {
                counter+=1
                println(x to y)
                break
            }

            if(y< targetY.first){
                break
            }
        }
        return x to y
    }

    fun solveOne(): Int {
        // moveBy(7,2,7)
/*        println(moveBy(6,9,20))
        println(moveBy(6,0,20))
        println(moveBy(6,1,20))
        println(moveBy(6,2,20))*/
        //println(moveBy(6,9))


        println(getVX(20..30))
        println(getVY())
        //vx需要大于等于16
        //println(moveBy(18,0))

        println(moveBy(9, 0, 20..30, -10..-5))
        //println(moveBy(17,101, 135..155,-102..-78))

        return 0

    }


    fun getVX(targetX: IntRange) {
        var count = 0
        var step = 0
        while (count <= targetX.last) {
            step += 1
            count = count + step

            if (targetX.first <= count && count <= targetX.last) {
                println("step=$step")
            }
        }
    }

    fun getVY() {
        var count = 0
        var step = 0
        while (count >= -10) {
            step -= 1
            count = count + step

            if (-10 <= count && count <= -5) {
                println("step=$step")
            }
        }
    }

    fun solveTwo(): Long {
        for(x in 16..155){
            for(y in -102..101){
                moveBy(x, y, 135..155, -102..-78)
            }
        }
        return 0
    }

    fun solution1(): Long {
        var count = 0L
        for (i in 0..101) {
            count += i
        }
        return count
    }
}

fun main() {
    //5151
    val solutionOne = D17.solution1()
    //968
    val solutionTwo = D17.solveTwo()
    println("One=$solutionOne")
    println("Tw0=${D17.counter}")
}