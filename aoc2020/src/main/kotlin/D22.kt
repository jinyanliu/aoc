import utils.IoHelper

enum class PLAYER {
    PLAYER_ONE, PLAYER_TWO
}

class Day22 {
    private val inputs = IoHelper.getSections("d22.in")

    fun getSolution1(): Long {
        val playerOne = inputs[0].lines().drop(1).map { it.toLong() }.toMutableList()
        val playerTwo = inputs[1].lines().drop(1).map { it.toLong() }.toMutableList()

        val winner = playSubGame(playerOne, playerTwo)

        return if (winner == PLAYER.PLAYER_ONE) {
            playerOne.mapIndexed { index, l -> l * (playerOne.size - index) }.sum()
        } else {
            playerTwo.mapIndexed { index, l -> l * (playerTwo.size - index) }.sum()
        }
    }

    fun getSolution2() {

    }

    private fun playSubGame(
        playerOne: MutableList<Long>,
        playerTwo: MutableList<Long>
    ): PLAYER {
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
        return if (playerOne.isNotEmpty()) PLAYER.PLAYER_ONE else PLAYER.PLAYER_TWO
    }
}

fun main() {
    //32102
    println(Day22().getSolution1())
    println(Day22().getSolution2())
}