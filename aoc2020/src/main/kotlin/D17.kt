import utils.IoHelper

class Day17 {
    private val inputs = IoHelper.getLines("d17.in")

    fun areYouActive(x: Int, y: Int, z: Int, w: Int, listOfActive: List<SuperCube>): Boolean {
        val superCube = SuperCube(x, y, z, w)
        if (listOfActive.contains(superCube)) {
            return true
        }
        return false
    }

    private fun neighboursOfXYZ(x: Int, y: Int, z: Int, w: Int, fourDimen: Boolean): ArrayList<SuperCube> {

        val xs = arrayListOf<Int>(x - 1, x, x + 1)
        val ys = arrayListOf<Int>(y - 1, y, y + 1)
        val zs = arrayListOf<Int>(z - 1, z, z + 1)
        var ws = arrayListOf<Int>(0)
        if(fourDimen){
            ws = arrayListOf<Int>(w - 1, w, w + 1)
        }



        val resultList = arrayListOf<SuperCube>()
        xs.forEach { xx ->
            ys.forEach { yy ->
                zs.forEach { zz ->
                    ws.forEach { ww ->
                        if (!(xx == x && yy == y && zz == z && ww == w)) {
                            resultList.add(SuperCube(xx, yy, zz, ww))
                        }
                    }
                }
            }
        }
        return resultList
    }

    private fun getInitialActiveCubes(): ArrayList<SuperCube> {
        val activeCubes = arrayListOf<SuperCube>()
        inputs.forEachIndexed { yIndex, s ->
            s.forEachIndexed { xIndex, c ->
                if (c.toString() == "#") {
                    activeCubes.add(SuperCube(xIndex, yIndex, 0, 0))
                }
            }
        }
        return activeCubes
    }

    private fun getSolution(fourDimen: Boolean): Int {
        val initialActiveCubes = getInitialActiveCubes()

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

            var minW = 0
            var maxW = 0

            if (fourDimen) {
                val ws = currentActiveCubes.map { it.w }
                minW = ws.min()!! - 1
                maxW = ws.max()!! + 1
            }

            val listToCheck = arrayListOf<SuperCube>()
            for (xx in minX..maxX) {
                for (yy in minY..maxY) {
                    for (zz in minZ..maxZ) {
                        for (ww in minW..maxW) {
                            listToCheck.add(SuperCube(xx, yy, zz, ww))
                        }
                    }
                }
            }

            val newActiveCubes = arrayListOf<SuperCube>()
            currentActiveCubes.forEach {
                newActiveCubes.add(it.copy())
            }

            listToCheck.forEach { cubeToCheck ->
                val itSelfActive =
                    areYouActive(
                        cubeToCheck.x,
                        cubeToCheck.y,
                        cubeToCheck.z,
                        cubeToCheck.w,
                        currentActiveCubes.toList()
                    )
                val neighbours = neighboursOfXYZ(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z, cubeToCheck.w,fourDimen)
                var neighboursActiveCount = 0
                neighbours.forEach { cubeNeighbour ->
                    if (areYouActive(
                            cubeNeighbour.x,
                            cubeNeighbour.y,
                            cubeNeighbour.z,
                            cubeNeighbour.w,
                            currentActiveCubes.toList()
                        )
                    ) neighboursActiveCount += 1
                }
                if (itSelfActive) {
                    if (neighboursActiveCount !in 2..3) {
                        newActiveCubes.remove(SuperCube(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z, cubeToCheck.w))
                    }
                } else {
                    if (neighboursActiveCount == 3) {
                        newActiveCubes.add(SuperCube(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z, cubeToCheck.w))
                    }
                }
            }


            currentActiveCubes = newActiveCubes
        }

        return currentActiveCubes.size
    }


    fun getSolution1() = getSolution(false)
    fun getSolution2() = getSolution(true)
}

data class SuperCube(
    val x: Int,
    val y: Int,
    val z: Int,
    val w: Int = 0
)

fun main() {
    //322
    println(Day17().getSolution1())
    //2000
    println(Day17().getSolution2())
}