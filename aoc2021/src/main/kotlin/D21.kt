import java.util.*

object D21 {
    private const val player1StartingPoint = 7
    private const val player2StartingPoint = 8

    private val diceQueue = ArrayDeque<Int>()

    fun solveOne(): Long {
        var times = 0L
        var player1Score = 0
        var player2Score = 0
        var player1Position = player1StartingPoint
        var player2Position = player2StartingPoint

        for (i in 1..100) {
            diceQueue.add(i)
        }

        while (player1Score < 1000 && player2Score < 1000) {
            times += 1
            val move = rollDice() + rollDice() + rollDice()

            if (times % 2 == 0L) {
                val result2 = play(move = move, score = player2Score, position = player2Position)
                player2Score = result2.first
                player2Position = result2.second
            } else {
                val result1 = play(move = move, score = player1Score, position = player1Position)
                player1Score = result1.first
                player1Position = result1.second
            }
        }

        return listOf(player1Score, player2Score).min()!! * times * 3
    }

    fun solveTwo(): Long {
        var counter1 = 0L
        var counter2 = 0L

        val possibleRoll = mapOf<Int, Long>(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

        val resultMap = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Long>()
        resultMap[((0 to 0) to (player1StartingPoint to player2StartingPoint))] = 1L

        while (resultMap.isNotEmpty()) {
            val firstInMap = resultMap.keys.first()
            val current = resultMap.remove(firstInMap)!!
            val score = firstInMap.first
            val position = firstInMap.second

            for (roll1 in possibleRoll) {
                val resultOne = play(
                    move = roll1.key,
                    score = score.first,
                    position = position.first
                )

                if (resultOne.first >= 21) {
                    counter1 += roll1.value * current
                    continue
                }

                for (roll2 in possibleRoll) {
                    val resultTwo = play(
                        move = roll2.key,
                        score = score.second,
                        position = position.second
                    )

                    val count = roll1.value * roll2.value * current

                    if (resultTwo.first >= 21) {
                        counter2 += count
                    } else {
                        val result = (resultOne.first to resultTwo.first) to (resultOne.second to resultTwo.second)
                        if (resultMap[result] != null) {
                            resultMap[result] = resultMap[result]!! + count
                        } else {
                            resultMap[result] = count
                        }
                    }
                }
            }
        }
        return listOf(counter1, counter2).max()!!
    }

    private fun rollDice() = try {
        diceQueue.removeFirst()
    } catch (e: Exception) {
        for (i in 1..100) {
            diceQueue.add(i)
        }
        diceQueue.removeFirst()
    }

    private fun play(
        move: Int,
        score: Int,
        position: Int
    ): Pair<Int, Int> {
        val pos = if ((position + move) % 10 == 0) 10 else (position + move) % 10
        return score + pos to pos
    }
}

fun main() {
    //556206
    val solutionOne = D21.solveOne()
    //630797200227453
    val solutionTwo = D21.solveTwo()
    println("One=$solutionOne")
    println("Tw0=$solutionTwo")
}