package utils

class D01 {
    private val inputs = IoHelper.getInts("d01.in")

    fun getSolution1() = inputs.sumBy { it.div(3) - 2 }

    fun getSolution2() = inputs.sumBy { howMuchFuel(it) }

    private fun howMuchFuel(mass: Int): Int {
        var currentMass = mass
        var totalFuelRequired = 0
        while (currentMass > 0) {
            val fuelRequired = currentMass.div(3) - 2
            currentMass = fuelRequired
            if (fuelRequired > 0) {
                totalFuelRequired += fuelRequired
            }
        }
        return totalFuelRequired
    }
}

fun main() {
    //3325156
    println(D01().getSolution1())
    //4984866
    println(D01().getSolution2())
}