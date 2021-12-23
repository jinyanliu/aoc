package utils

object PrintHelper {
    fun Set<Pair<Int, Int>>.printIntMap() {
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

    fun Map<Pair<Int, Int>, Int>.printIntMap(){
        val maxX = this.map { it.key.first }.max()!!
        val maxY = this.map { it.key.second }.max()!!

        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                if (x to y in this) {
                    print(this[x to y])
                } else {
                    print(" ")
                }
            }
            print("\n")
        }
    }

    fun Map<Pair<Int, Int>, String>.printStringMap(){
        println()
        val maxX = this.map { it.key.first }.max()!!
        val maxY = this.map { it.key.second }.max()!!
        val minX = this.map { it.key.first }.min()!!
        val minY = this.map { it.key.second }.min()!!

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (x to y in this) {
                    print(this[x to y])
                } else {
                    print(" ")
                }
            }
            print("\n")
        }
    }
}