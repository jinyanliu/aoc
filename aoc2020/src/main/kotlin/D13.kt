import utils.IoHelper

class Day13 {
    private val inputs = IoHelper.getLines("d13.in")
    private val departTime = inputs.get(0).toLong()

    fun getSolution1(): Long {
        val busIds = inputs.get(1).split(",").filter { it.toIntOrNull() != null }.map { it.toLong() }
        var currentToVerify = departTime
        while (true) {
            for (id in busIds) {
                if (currentToVerify % id == 0L) {
                    println(id)
                    println(currentToVerify)
                    println(departTime)
                    return (currentToVerify - departTime) * id
                }
            }
            currentToVerify += 1
        }
    }

    fun getSolution2():Long{
        println(7*13*59*31*19)
        val inputsWithIndex = inputs.get(1).split(",").withIndex().map { indexedValue -> indexedValue.index to indexedValue.value }
            .filter { it.second.toIntOrNull()!= null}.map { it.first.toLong() to it.second.toLong() }

        println(inputsWithIndex)

        var currentToVerify = 944L
        while (true) {
            var validated = true
            for (inputWithIndex in inputsWithIndex) {
                if((currentToVerify + inputWithIndex.first)%inputWithIndex.second != 0L){
                    validated = false
                }
            }

            if(validated){
                println(currentToVerify)
                return currentToVerify
            }

            currentToVerify += 1

        }
    }

    fun getSolution2Alt():Long{

        val inputsWithIndex = inputs.get(1).split(",").withIndex().map { indexedValue -> indexedValue.index to indexedValue.value }
            .filter { it.second.toIntOrNull()!= null}.map { it.first.toLong() to it.second.toLong() }

        println(inputsWithIndex)

        var currentToVerify = 41L*659L*13L*19L*29L
        while (true) {
            var validated = true
            for (inputWithIndex in inputsWithIndex) {
                if((currentToVerify-41 + inputWithIndex.first)%inputWithIndex.second != 0L){
                    validated = false
                }
            }

            if(validated){
                println(currentToVerify-41)
                return currentToVerify-41
            }

            currentToVerify += 41L*659L*13L*19L*29L
            println(currentToVerify)

        }






    }
}

fun main() {
    //115
    println(Day13().getSolution1())
    //756261495958122
    println(Day13().getSolution2Alt())
}