import utils.IoHelper

class Day22 {
    private val inputs = IoHelper.getSections("d22.in")

    fun getSolution1() {
        val playerOne = inputs[0].lines().drop(1).map { it.toLong() }.toMutableList()
        val playerTwo = inputs[1].lines().drop(1).map { it.toLong() }.toMutableList()
        println(playerOne)
        println(playerTwo)

        while (playerOne.isNotEmpty() && playerTwo.isNotEmpty()) {
            val playerOneFirst = playerOne.first()
            val playerTwoFirst = playerTwo.first()
            playerOne.removeAt(0)
            playerTwo.removeAt(0)

            if (playerOneFirst > playerTwoFirst) {
                playerOne.add(playerOneFirst)
                playerOne.add(playerTwoFirst)

            } else {
                playerTwo.add(playerTwoFirst)
                playerTwo.add(playerOneFirst)
            }

        }

        if(playerOne.isNotEmpty()){
            println(playerOne.mapIndexed { index, l -> l*(playerOne.size-index) }.sum())
        }else {
            println(playerTwo.mapIndexed { index, l -> l*(playerTwo.size-index) }.sum())
        }


    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day22().getSolution1())
    println(Day22().getSolution2())
}