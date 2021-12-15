import utils.IoHelper
import utils.LocationHelper
import java.util.*
import utils.LocationHelper.get4AdjacentPositions
import utils.LocationHelper.getRightAndDownNeighbours

object D15Alt {
    private val inputs = IoHelper.getMap("d15.in")

    fun solveOne(): Int {
        val maxX = LocationHelper.getMaxX(inputs)
        val maxY = LocationHelper.getMaxY(inputs)

        //已知点到原点的距离
        val map = mutableMapOf<Pair<Int, Int>, Int>((0 to 0) to 0)



        val queue=ArrayDeque<Pair<Int, Int>>()
        queue.add((0 to 0))

        while(queue.isNotEmpty()){
            val currentPoint = queue.removeFirst()

            currentPoint.getRightAndDownNeighbours(maxX, maxY).forEach {

                    if(map[it]== null){
                        map[it] = map[currentPoint]!!+inputs[it]!!
                    }else {
                        if(map[it]!!>map[currentPoint]!!+inputs[it]!!){
                            map[it] = map[currentPoint]!!+inputs[it]!!
                        }
                    }
                    queue.add(it)


            }

        }

        return map[9 to 9]!!
    }

    fun solveTwo(): Int {
        val maxX = LocationHelper.getMaxX(inputs)
        val maxY = LocationHelper.getMaxY(inputs)

        //已知点到原点的距离
        val map = mutableMapOf<Pair<Int, Int>, Int>((0 to 0) to 0)

        for(item in inputs){
            val currentPoint = item.key

            currentPoint.get4AdjacentPositions(maxX, maxY).forEach {

                if(map[it]== null){
                    map[it] = map[currentPoint]!!+inputs[it]!!
                }else {
                    if(map[it]!!>map[currentPoint]!!+inputs[it]!!){
                        map[it] = map[currentPoint]!!+inputs[it]!!
                    }
                }



            }
        }

        return map[9 to 9]!!
    }
}

fun main() {
    //val solutionOne = D15Alt.solveOne()
    val solutionTwo = D15Alt.solveTwo()
    //println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}