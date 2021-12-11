package utils

object IoHelper {
    fun getInts(filename: String): List<Int> {
        return getLines(filename).map { it.toInt() }
    }

    fun getLongs(filename: String): List<Long> {
        return getLines(filename).map { it.toLong() }
    }

    fun getLines(filename: String): List<String> {
        return getRawContent(filename).lines()
    }

    fun getSections(filename: String): List<String> {
        return getRawContent(filename).split("\n\n")
    }

    fun getRawContent(filename: String): String {
        return IoHelper.javaClass.classLoader.getResource(filename)?.readText()?.trim().orEmpty()
    }

    fun getMap(filename: String): Map<Pair<Int, Int>, Int> {
        val lines = getLines(filename)
        val map = mutableMapOf<Pair<Int, Int>, Int>()
        for (y in lines.indices) {
            val oneLine = lines[y]
            for (x in oneLine.indices) {
                map[x to y] = lines[y][x].toString().toInt()
            }
        }
        return map.toMap()
    }
}