package utils

object PrintHelper {
    fun Set<Pair<Int, Int>>.print() {
        val maxX = this.map { it.first }.max()!!
        val maxY = this.map { it.second }.max()!!

        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                if (x to y in this) {
                    print("x")
                } else {
                    print(" ")
                }
            }
            print("\n")
        }
    }
}