package utils

class IoHelper {
    companion object {
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
    }
}


