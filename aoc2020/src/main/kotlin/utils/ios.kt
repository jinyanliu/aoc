package utils

fun getLines(callerObject: Any, filename: String): List<String> {
    return getRawContent(callerObject, filename)?.lines().orEmpty()
}

fun getRawContent(callerObject: Any, filename: String): String? {
    return callerObject.javaClass.classLoader.getResource(filename)?.readText()?.trim()
}
