import utils.IoHelper

class Day04 {
    private fun getInputs() = IoHelper.getSections("d04.in")

    fun getSolution1() = getInputs().count { isValidSolution1(it) }

    fun getSolution2() = getInputs().count { isValidSolution2(it) }

    private fun isValidSolution1(toVerify: String): Boolean {
        return toVerify.contains("byr:")
                && toVerify.contains("iyr:")
                && toVerify.contains("eyr:")
                && toVerify.contains("hgt:")
                && toVerify.contains("hcl:")
                && toVerify.contains("ecl:")
                && toVerify.contains("pid:")
    }

    private fun isValidSolution2(toVerify: String): Boolean {
        if (!isValidSolution1(toVerify)) return false

        var isByrValid = false
        var isIyrValid = false
        var isEyrValid = false
        var isHgtValid = false
        var isHclValid = false
        var isEclValid = false
        var isPidValid = false

        val fields = toVerify.split("\n").flatMap { it.split(" ") }
        for (field in fields) {

            if (field.contains("byr:")) {
                isByrValid = (field.drop(4).length == 4) && (field.drop(4).toInt() in 1920..2002)
            }

            if (field.contains("iyr:")) {
                isIyrValid = (field.drop(4).length == 4) && (field.drop(4).toInt() in 2010..2020)
            }

            if (field.contains("eyr:")) {
                isEyrValid = (field.drop(4).length == 4) && (field.drop(4).toInt() in 2020..2030)
            }

            if (field.contains("hgt:")) {
                isHgtValid = when {
                    field.drop(4).contains("cm") -> {
                        (field.drop(4).dropLast(2).toInt() in 150..193)
                    }
                    field.drop(4).contains("in") -> {
                        (field.drop(4).dropLast(2).toInt() in 59..76)
                    }
                    else -> {
                        false
                    }
                }
            }

            if (field.contains("hcl:")) {
                val listOfValidNumber =
                    listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")
                var isCharValid = false
                for (char in field.drop(5)) {
                    isCharValid = listOfValidNumber.contains(char.toString())
                }
                isHclValid = (field.drop(4).contains("#")) && (field.drop(5).length == 6) && isCharValid
            }

            if (field.contains("ecl:")) {
                isEclValid =
                    field.drop(4) == "amb" || field.drop(4) == "blu" || field.drop(4) == "brn" || field.drop(4) == "gry" || field.drop(
                        4
                    ) == "grn" || field.drop(4) == "hzl" || field.drop(4) == "oth"
            }

            if (field.contains("pid:")) {
                isPidValid = field.drop(4).length == 9 && field.filter { it.isDigit() }.length == 9
            }
        }

        return isByrValid && isIyrValid && isEyrValid && isHgtValid && isHclValid && isEclValid && isPidValid
    }
}

fun main() {
    println(Day04().getSolution1())
    println(Day04().getSolution2())
}