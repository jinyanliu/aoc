package utils

class IoHelper {
    companion object {
        fun getInts(filename: String): List<Int> {
            return getLines(filename).map { it.toInt() }
        }

        fun getLines(filename: String): List<String> {
            return getRawContent(filename)?.lines().orEmpty()
        }

        fun getRawContent(filename: String): String? {
            return IoHelper.javaClass.classLoader.getResource(filename)?.readText()?.trim()
        }
    }
}


