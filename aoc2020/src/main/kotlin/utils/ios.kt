package utils

fun getInts(callerObject: Any, filename: String): List<Int> {
    return getLines(callerObject, filename).map { it.toInt() }
}

fun getLines(callerObject: Any, filename: String): List<String> {
    return getRawContent(callerObject, filename)?.lines().orEmpty()
}

fun getRawContent(callerObject: Any, filename: String): String? {
    return callerObject.javaClass.classLoader.getResource(filename)?.readText()?.trim()
}
