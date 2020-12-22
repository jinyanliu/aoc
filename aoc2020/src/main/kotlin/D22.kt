import utils.IoHelper

enum class PLAYER {
    PLAYER_ONE, PLAYER_TWO
}

class Day22 {
    private val inputs = IoHelper.getSections("d22.in")

    fun getSolution1(): Long {
        val playerOne = inputs[0].lines().drop(1).map { it.toLong() }.toMutableList()
        val playerTwo = inputs[1].lines().drop(1).map { it.toLong() }.toMutableList()
        val winner = playGame(playerOne, playerTwo)
        return if (winner == PLAYER.PLAYER_ONE) {
            playerOne.mapIndexed { index, l -> l * (playerOne.size - index) }.sum()
        } else {
            playerTwo.mapIndexed { index, l -> l * (playerTwo.size - index) }.sum()
        }
    }

    fun getSolution2(): Long {
        val playerOne = inputs[0].lines().drop(1).map { it.toLong() }.toMutableList()
        val playerTwo = inputs[1].lines().drop(1).map { it.toLong() }.toMutableList()

        val winner = playRecursiveGame(playerOne, playerTwo, arrayListOf(playerOne.toList() to playerTwo.toList()))

        return if (winner == PLAYER.PLAYER_ONE) {
            playerOne.mapIndexed { index, l -> l * (playerOne.size - index) }.sum()
        } else {
            playerTwo.mapIndexed { index, l -> l * (playerTwo.size - index) }.sum()
        }
    }

    private fun playGame(
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

    private fun playRecursiveGame(
        playerOne: MutableList<Long>,
        playerTwo: MutableList<Long>,
        gameHistory: ArrayList<Pair<List<Long>, List<Long>>>
    ): PLAYER {
        while (playerOne.isNotEmpty() && playerTwo.isNotEmpty()) {
            val playerOneFirst = playerOne.first()
            val playerTwoFirst = playerTwo.first()
            playerOne.removeAt(0)
            playerTwo.removeAt(0)

            if (playerOne.size.toLong() >= playerOneFirst && playerTwo.size.toLong() >= playerTwoFirst) {
                val winner = playRecursiveGame(
                    playerOne.subList(0, playerOneFirst.toInt()).toList().toMutableList(),
                    playerTwo.subList(0, playerTwoFirst.toInt()).toList().toMutableList(),
                    arrayListOf(playerOne.toList() to playerTwo.toList())
                )
                if (winner == PLAYER.PLAYER_ONE) {
                    playerOne.add(playerOneFirst)
                    playerOne.add(playerTwoFirst)
                } else {
                    playerTwo.add(playerTwoFirst)
                    playerTwo.add(playerOneFirst)
                }
            } else {
                if (playerOneFirst > playerTwoFirst) {
                    playerOne.add(playerOneFirst)
                    playerOne.add(playerTwoFirst)
                } else {
                    playerTwo.add(playerTwoFirst)
                    playerTwo.add(playerOneFirst)
                }
            }
            if (playerOne to playerTwo in gameHistory) return PLAYER.PLAYER_ONE
            gameHistory.add(playerOne.toList() to playerTwo.toList())
        }
        return if (playerOne.isNotEmpty()) PLAYER.PLAYER_ONE else PLAYER.PLAYER_TWO
    }
}

fun main() {
    //32102
    println(Day22().getSolution1())
    //34173
    println(Day22().getSolution2())
}