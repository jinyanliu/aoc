import utils.IoHelper

object D4 {
    private val inputs = IoHelper.getSections("d04.in")

    //First to win
    fun solveOne() = solve { it.min()!! }

    //Last to win
    fun solveTwo() = solve { it.max()!! }

    private fun solve(find: (List<Int>) -> Int): Int {
        val listOfCall = inputs[0].split(",").map { it.toInt() }

        val originalCards = inputs.drop(1)

        val intCubes =
            originalCards.map { it.replace("\n", " ").split(" ").filter { it.isNotBlank() }.map { it.toInt() } }

        val indexCubes = intCubes.map { it.map { listOfCall.indexOf(it) } }

        // 5 horizontal, 5 vertical
        val validListsInCubes = mutableListOf<List<List<Int>>>()

        for (indexCube in indexCubes) {
            val validList = mutableListOf<List<Int>>()
            val a = indexCube.subList(0, 5)
            val b = indexCube.subList(5, 10)
            val c = indexCube.subList(10, 15)
            val d = indexCube.subList(15, 20)
            val e = indexCube.subList(20, 25)

            val f =
                listOf(indexCube.get(0), indexCube.get(5), indexCube.get(10), indexCube.get(15), indexCube.get(20))
            val g =
                listOf(indexCube.get(1), indexCube.get(6), indexCube.get(11), indexCube.get(16), indexCube.get(21))
            val h =
                listOf(indexCube.get(2), indexCube.get(7), indexCube.get(12), indexCube.get(17), indexCube.get(22))
            val i =
                listOf(indexCube.get(3), indexCube.get(8), indexCube.get(13), indexCube.get(18), indexCube.get(23))
            val j =
                listOf(indexCube.get(4), indexCube.get(9), indexCube.get(14), indexCube.get(19), indexCube.get(24))

            if (a.valid()) validList.add(a.sorted())
            if (b.valid()) validList.add(b.sorted())
            if (c.valid()) validList.add(c.sorted())
            if (d.valid()) validList.add(d.sorted())
            if (e.valid()) validList.add(e.sorted())

            if (f.valid()) validList.add(f.sorted())
            if (g.valid()) validList.add(g.sorted())
            if (h.valid()) validList.add(h.sorted())
            if (i.valid()) validList.add(i.sorted())
            if (j.valid()) validList.add(j.sorted())

            validListsInCubes.add(validList)
        }

        val arrayOfMin = mutableListOf<Int>()
        for (validListsInCube in validListsInCubes) {
            val min = validListsInCube.map { it.last() }.min()
            arrayOfMin.add(min!!)
        }

        val theFinishIndexInMater = find(arrayOfMin)
        val theFinishCardIndex = arrayOfMin.indexOf(theFinishIndexInMater)
        val theFinishCardIndexCube = indexCubes[theFinishCardIndex].sorted()
        val unmarkedIndexes = theFinishCardIndexCube.filter { it > theFinishIndexInMater!! }
        val sum = unmarkedIndexes.map { listOfCall.get(it) }.sum()
        val justCalled = listOfCall.get(theFinishIndexInMater!!)

        return sum * justCalled
    }

    private fun List<Int>.valid(): Boolean {
        return this.all { it >= 0 }
    }
}

fun main() {
    val solutionOne = D4.solveOne()
    val solutionTwo = D4.solveTwo()
    //8136
    println("One=$solutionOne")
    //12738
    println("Tw0=$solutionTwo")
}