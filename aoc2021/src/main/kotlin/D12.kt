import utils.IoHelper
import java.util.*

object D12 {
    private val inputs = IoHelper.getLines("d12.in")

    fun solve(validateChild: (String, List<String>) -> Boolean): Int {
        val map = getPathMap()

        val queue = ArrayDeque<List<String>>()
        queue.add(listOf("start"))
        var count = 0

        while (queue.isNotEmpty()) {
            val currentList = queue.removeFirst()
            val lastItemOfCurrentList = currentList.last()
            val validChildrenOfLastItem = map[lastItemOfCurrentList]!!.filter { validateChild(it, currentList) }
            for (child in validChildrenOfLastItem) {
                val newList: List<String> = currentList + child
                if (newList.last() == "end") {
                    count += 1
                } else {
                    queue.add(newList)
                }
            }
        }

        return count
    }

    private fun getPathMap(): Map<String, List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()

        for (line in inputs) {
            val a = line.split("-")[0]
            val b = line.split("-")[1]

            if (map[a] != null) {
                map[a]!!.add(b)
            } else {
                map[a] = mutableListOf(b)
            }

            if (map[b] != null) {
                map[b]!!.add(a)
            } else {
                map[b] = mutableListOf(a)
            }
        }

        for (element in map) {
            map[element.key]!!.removeIf { it == "start" }
        }

        return map.filter { it.key != "end" }
    }
}

private fun String.isLowerCased() = this.all { it.isLowerCase() }

fun main() {
    //4573
    val solutionOne = D12.solve { child, currentList ->
        !(child.isLowerCased() && currentList.contains(child))
    }
    //117509
    val solutionTwo = D12.solve { child, currentList ->
        val cannotAddLowercaseTwice = currentList.filter { it.isLowerCased() }.groupBy { it }.any { it.value.size == 2 }
        !(child.isLowerCased() && currentList.contains(child) && cannotAddLowercaseTwice)
    }
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}