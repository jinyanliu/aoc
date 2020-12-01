class Day01 {
    private fun getInputs(): List<String> = utils.getLines(this, "d01.in")
    
    fun getSolution() {
        val inputs = getInputs()
        println(inputs)
    }
}

fun main() {
    Day01().getSolution()
}