import utils.IoHelper

class Day17Rework {
    private val inputs = IoHelper.getLines("d17.in")

    private fun neighboursOfXYZ(x: Int, y: Int, z: Int): ArrayList<Cube> {

        val xs = arrayListOf<Int>(x - 1, x, x + 1)
        val ys = arrayListOf<Int>(y - 1, y, y + 1)
        val zs = arrayListOf<Int>(z - 1, z, z + 1)

        val resultList = arrayListOf<Cube>()
        xs.forEach { xx ->
            ys.forEach { yy ->
                zs.forEach { zz ->
                    if (!(xx == x && yy == y && zz == z)) {
                        resultList.add(Cube(xx, yy, zz))
                    }
                }
            }
        }
        println("neighbour"+resultList)
        println(resultList.size)
        return resultList
    }

    private fun getInitialActiveCubes(): ArrayList<Cube> {
        val activeCubes = arrayListOf<Cube>()
        inputs.forEachIndexed { yIndex, s ->
            s.forEachIndexed { xIndex, c ->
                if (c.toString() == "#") {
                    activeCubes.add(Cube(xIndex, yIndex, 0))
                }
            }
        }
        return activeCubes
    }

    fun getSolution1() {
        val initialActiveCubes = getInitialActiveCubes()
        println(initialActiveCubes)


        var currentActiveCubes = initialActiveCubes.toList().toMutableList()

        for (i in 0..5) {

            val xs = currentActiveCubes.map { it.x }
            val minX = xs.min()!! - 1
            val maxX = xs.max()!! + 1

            val ys = currentActiveCubes.map { it.y }
            val minY = ys.min()!! - 1
            val maxY = ys.max()!! + 1

            val zs = currentActiveCubes.map { it.z }
            val minZ = zs.min()!! - 1
            val maxZ = zs.max()!! + 1

            val listToCheck = arrayListOf<Cube>()
            for (xx in minX..maxX) {
                for (yy in minY..maxY) {
                    for (zz in minZ..maxZ) {
                        listToCheck.add(Cube(xx, yy, zz))
                    }
                }
            }

            println(listToCheck)
            println(listToCheck.size)


            val newActiveCubes = arrayListOf<Cube>()
            currentActiveCubes.forEach {
                newActiveCubes.add(it.copy())
            }

            listToCheck.forEach { cubeToCheck ->
                val itSelfActive = areYouActive(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z, currentActiveCubes.toList())
                val neighbours = neighboursOfXYZ(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z)
                var neighboursActiveCount = 0
                neighbours.forEach { cubeNeighbour ->
                    if (areYouActive(
                            cubeNeighbour.x,
                            cubeNeighbour.y,
                            cubeNeighbour.z,
                            currentActiveCubes.toList()
                        )
                    ) neighboursActiveCount += 1
                }
                if (itSelfActive) {
                    if (neighboursActiveCount !in 2..3) {
                        newActiveCubes.remove(Cube(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z))
                    }
                } else {
                    if (neighboursActiveCount == 3) {
                        newActiveCubes.add(Cube(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z))
                    }
                }
            }


            currentActiveCubes = newActiveCubes
        }

        println(currentActiveCubes.size)
    }


    fun areYouActive(x: Int, y: Int, z: Int, listOfActive: List<Cube>): Boolean {
        val cube = Cube(x, y, z)
        if (listOfActive.contains(cube)) {
            return true
        }
        return false
    }

    fun getSolution2() {

    }
}

data class Cube(
    val x: Int,
    val y: Int,
    val z: Int
)

fun main() {
    //322
    println(Day17Rework().getSolution1())
    println(Day17Rework().getSolution2())
}