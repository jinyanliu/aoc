import utils.IoHelper
import java.util.concurrent.TimeUnit

class D15Alt(val inputs: String) {
    fun s1() = Graph(100).init(IoHelper.getLines(inputs)).getShortestDistance(0 to 0, 99 to 99)
    fun s2() = Graph(500).init(IoHelper.getLines(inputs)).getShortestDistance(0 to 0, 499 to 499)
}

class Graph(private val width: Int) {
    private val nodes = mutableMapOf<Pair<Int, Int>, Int>()

    private fun getNeighbours(me: Pair<Int, Int>): List<Pair<Int, Int>> = listOf(
        me.first to me.second + 1,
        me.first to me.second - 1,
        me.first + 1 to me.second,
        me.first - 1 to me.second
    ).filter { it.first in 0 until width && it.second in 0 until width }

    fun init(lines: List<String>): Graph {
        lines.withIndex().forEach { (y, line) ->
            line.withIndex().forEach { (x, c) ->
                nodes[x to y] = c.toString().toInt()
            }
        }
        return this
    }

    fun getShortestDistance(src: Pair<Int, Int>, dst: Pair<Int, Int>): Int {
        val startTimestamp = System.currentTimeMillis()

        //点到原点的距离
        val distToSrc = mutableMapOf<Pair<Int, Int>, Int>()
        //已知到原点的最短距离
        val solvedDistToSrc = mutableMapOf(src to 0)

        var currentNode = src
        while (currentNode != dst) {
            val newNeighbours = getNeighbours(currentNode).filterNot { it in solvedDistToSrc }
            newNeighbours.forEach { neighbour ->
                val srcToCurrentNode = solvedDistToSrc[currentNode]!!
                val edge = nodes[neighbour]!!

                if (neighbour !in distToSrc) {
                    distToSrc[neighbour] = edge + srcToCurrentNode
                } else {
                    val srcToNeighbour = distToSrc[neighbour]!!
                    if (srcToNeighbour > srcToCurrentNode + edge) {
                        distToSrc[neighbour] = srcToCurrentNode + edge
                    }
                }
            }
            currentNode = distToSrc.minBy { it.value }!!.key
            solvedDistToSrc[currentNode] = distToSrc[currentNode]!!
            distToSrc.remove(currentNode)
        }

        val endTimestamp = System.currentTimeMillis()
        val minutes = TimeUnit.MILLISECONDS.toMinutes(endTimestamp - startTimestamp)
        println("Minutes = $minutes")
        return solvedDistToSrc[dst]!!
    }
}

fun main() {
    //604
    println(D15Alt("d15.in").s1())
    //2907
    println(D15Alt("d15Alt.in").s2())
}