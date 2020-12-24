import utils.IoHelper


class Day24 {
    //se se nw ne ne ne w se e sw w sw sw w ne ne ws ew sw
    //e, se, sw, w, nw, ne
    private val inputs = IoHelper.getLines("d24.in")

    private val validDi = arrayListOf("e", "se", "sw", "w", "nw", "ne")

    // colors: "w", "b"
    private val mapOfLoAndColor = mutableMapOf<Pair<Long, Long>, String>()

    fun getSolution1(): Int {

        val inputsOfDis = inputs.map { it.toMutableList() }.map { line ->
            val listOfDirections = arrayListOf<String>()
            while (line.isNotEmpty()) {
                var r = line.removeAt(0).toString()
                if (r in validDi) {
                    listOfDirections.add(r)
                } else {
                    r += line.removeAt(0).toString()
                    listOfDirections.add(r)
                }
            }
            listOfDirections
        }

        println(inputsOfDis)
        println(inputsOfDis.size)

        val inputsOfLo = inputsOfDis.map { di ->
            var lo = 0L to 0L
            di.forEach {
                lo = move(lo, it)
            }
            lo
        }

        println(inputsOfLo)
        println(inputsOfLo.size)

        inputsOfLo.forEach { lo ->
            if (lo !in mapOfLoAndColor.keys) {
                mapOfLoAndColor[lo] = "b"
            } else {
                if (mapOfLoAndColor[lo] == "b") {
                    mapOfLoAndColor[lo] = "w"
                } else {
                    mapOfLoAndColor[lo] = "b"
                }
            }
        }

        println(mapOfLoAndColor.size)
        return mapOfLoAndColor.values.count { it == "b" }
    }

    private fun move(lo: Pair<Long, Long>, di: String): Pair<Long, Long> {
        return when (di) {
            "e" -> lo.first to lo.second + 2
            "se" -> lo.first + 1 to lo.second + 1
            "sw" -> lo.first + 1 to lo.second - 1
            "w" -> lo.first to lo.second - 2
            "nw" -> lo.first - 1 to lo.second - 1
            "ne" -> lo.first - 1 to lo.second + 1
            else -> error("error move")
        }
    }

    fun getSolution2() {
    }
}

fun main() {
    //455
    println(Day24().getSolution1())
    println(Day24().getSolution2())
}