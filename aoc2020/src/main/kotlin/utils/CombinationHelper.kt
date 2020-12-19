package utils

// 1. Char.toString.toInt !!! Char.toInt X
// 2. toLong() !!!


// Careful of T! Check D01.kt: T can take any object type at the same time.
fun <T> genCombo(fields: List<List<T>>): List<List<T>> {
    if (fields.isEmpty()) {
        return listOf()
    }
    if (fields.size == 1) {
        return fields[0].map { listOf(it) }
    }

    return fields[0].flatMap { elem ->
        genCombo(fields.drop(1))
            .map {
                val tmp = mutableListOf(elem) // order matters, elem must be the first
                tmp.addAll(it)
                tmp.toList()
            }
    }
}