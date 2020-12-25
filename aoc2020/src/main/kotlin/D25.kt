import utils.IoHelper

class Day25 {
    private val inputs = IoHelper.getLines("d25Test.in").map { it.toLong() }

    fun getSolution1() {
        //card
        val subjectNumber = 7L
        var value = 1L
        var loopSizeCard = 0
        while(value!= 9033205L){
            loopSizeCard+=1
            value = value * subjectNumber
            value = value % 20201227
        }
        println(loopSizeCard)


        //door
        val subjectNumber2 = 7L
        var value2 = 1L
        var loopSizeDoor = 0
        while(value2!= 9281649L){
            loopSizeDoor+=1
            value2 = value2 * subjectNumber2
            value2 = value2 % 20201227
        }
        println(loopSizeDoor)


        val subjectNumberCard2 = 9033205L
        var valueCard2 = 1L
        repeat(loopSizeDoor){
            valueCard2 = valueCard2 * subjectNumberCard2
            valueCard2 = valueCard2 % 20201227
        }
        println(valueCard2)


    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day25().getSolution1())
    println(Day25().getSolution2())
}