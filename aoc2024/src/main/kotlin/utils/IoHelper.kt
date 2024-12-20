package utils

/*
How to create a new module:
1. Create module, Choose Gradle->Java!
2. Copy build.gradle file
3. Create Kotlin folder
*/
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

    fun getRawContentWithEmptySpace(filename: String): String {
        return IoHelper.javaClass.classLoader.getResource(filename)?.readText().orEmpty()
    }

    fun getIntMap(filename: String): Map<Pair<Int, Int>, Int> {
        val lines = getLines(filename)
        return getIntMap(lines)
    }

    fun getStringMap(filename: String): Map<Pair<Int, Int>, String> {
        val lines = getLines(filename)
        return getStringMap(lines)
    }

    private fun getIntMap(lines: List<String>): Map<Pair<Int, Int>, Int> {
        val map = mutableMapOf<Pair<Int, Int>, Int>()
        for (y in lines.indices) {
            val oneLine = lines[y]
            for (x in oneLine.indices) {
                map[x to y] = lines[y][x].toString().toInt()
            }
        }
        return map.toMap()
    }

    fun getStringMap(lines: List<String>): Map<Pair<Int, Int>, String> {
        val map = mutableMapOf<Pair<Int, Int>, String>()
        for (y in lines.indices) {
            val oneLine = lines[y]
            for (x in oneLine.indices) {
                map[x to y] = lines[y][x].toString()
            }
        }
        return map.toMap()
    }
}

