import utils.IoHelper

data class SuperCube(
    val x: Int,
    val y: Int,
    val z: Int,
    val w: Int = 0
)

class Day17 {
    private val inputs = IoHelper.getLines("d17.in")

    private fun initialActiveCubes(): ArrayList<SuperCube> {
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

    private fun neighbourCubes(x: Int, y: Int, z: Int, w: Int, fourDimen: Boolean): ArrayList<SuperCube> {
        val xs = arrayListOf(x - 1, x, x + 1)
        val ys = arrayListOf(y - 1, y, y + 1)
        val zs = arrayListOf(z - 1, z, z + 1)
        val ws = if (fourDimen) {
            arrayListOf(w - 1, w, w + 1)
        } else arrayListOf(0)

        val neighbourCubes = arrayListOf<SuperCube>()
        xs.forEach { xx ->
            ys.forEach { yy ->
                zs.forEach { zz ->
                    ws.forEach { ww ->
                        if (!(xx == x && yy == y && zz == z && ww == w)) {
                            neighbourCubes.add(SuperCube(xx, yy, zz, ww))
                        }
                    }
                }
            }
        }
        return neighbourCubes
    }

    private fun areYouActive(x: Int, y: Int, z: Int, w: Int, activeCubes: List<SuperCube>) =
        activeCubes.contains(SuperCube(x, y, z, w))

    private fun getSolution(fourDimen: Boolean): Int {
        var currentActiveCubes = initialActiveCubes()
        repeat(6) {
            val newActiveCubes = arrayListOf<SuperCube>()
            currentActiveCubes.forEach {
                newActiveCubes.add(it.copy())
            }

            cubesToCheck(currentActiveCubes, fourDimen).forEach { cubeToCheck ->
                val itSelfActive =
                    areYouActive(
                        cubeToCheck.x,
                        cubeToCheck.y,
                        cubeToCheck.z,
                        cubeToCheck.w,
                        currentActiveCubes.toList()
                    )
                val neighbours = neighbourCubes(cubeToCheck.x, cubeToCheck.y, cubeToCheck.z, cubeToCheck.w, fourDimen)
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

    private fun cubesToCheck(
        currentActiveCubes: ArrayList<SuperCube>,
        fourDimen: Boolean
    ): ArrayList<SuperCube> {
        val listToCheck = arrayListOf<SuperCube>()
        for (xx in xRangeToCheck(currentActiveCubes)) {
            for (yy in yRangeToCheck(currentActiveCubes)) {
                for (zz in zRangeToCheck(currentActiveCubes)) {
                    for (ww in wRangeToCheck(currentActiveCubes, fourDimen)) {
                        listToCheck.add(SuperCube(xx, yy, zz, ww))
                    }
                }
            }
        }
        return listToCheck
    }

    private fun wRangeToCheck(
        currentActiveCubes: ArrayList<SuperCube>,
        fourDimen: Boolean
    ): IntRange {
        val ws = currentActiveCubes.map { it.w }
        val minW = if (fourDimen) ws.min()!! - 1 else 0
        val maxW = if (fourDimen) ws.max()!! + 1 else 0
        return IntRange(minW, maxW)
    }

    private fun zRangeToCheck(currentActiveCubes: ArrayList<SuperCube>): IntRange {
        val zs = currentActiveCubes.map { it.z }
        val minZ = zs.min()!! - 1
        val maxZ = zs.max()!! + 1
        return IntRange(minZ, maxZ)
    }

    private fun yRangeToCheck(currentActiveCubes: ArrayList<SuperCube>): IntRange {
        val ys = currentActiveCubes.map { it.y }
        val minY = ys.min()!! - 1
        val maxY = ys.max()!! + 1
        return IntRange(minY, maxY)
    }

    private fun xRangeToCheck(currentActiveCubes: ArrayList<SuperCube>): IntRange {
        val xs = currentActiveCubes.map { it.x }
        val minX = xs.min()!! - 1
        val maxX = xs.max()!! + 1
        return IntRange(minX, maxX)
    }

    fun getSolution1() = getSolution(false)
    fun getSolution2() = getSolution(true)
}

fun main() {
    //322
    println(Day17().getSolution1())
    //2000
    println(Day17().getSolution2())
}