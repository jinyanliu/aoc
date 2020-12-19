import utils.IoHelper

class Day19 {
    private val inputs = IoHelper.getSections("d19Test.in")
    private val rules = inputs[0].lines()
    private val messages = inputs[1].lines()

    private fun inputsMap(): Map<Long, String> {
        val inputsMap = mutableMapOf<Long, String>()
        rules.map { inputsMap[it.split(": ")[0].toLong()] = it.split(": ")[1] }
        return inputsMap
    }


    fun getSolution1() {
/*        println(inputsMap())
        val zeroRule = inputsMap()[0]!!
        var possibleRules = arrayListOf<String>()
        possibleRules.addAll(zeroRule.split(" "))
        println(possibleRules)

        while (true){
            val newToCheck = arrayListOf<String>()

            for (possibleRule in possibleRules) {
                val innerRules = inputsMap().get(possibleRule.toLong())!!
                if(innerRules.startsWith("\"")){
                    newToCheck.add(innerRules)
                }else if(innerRules.contains("|")){
                    if(newToCheck.isNotEmpty()){

                    }
                }else {

                }
            }
        }*/


        var toCheck = arrayListOf<String>("0")
        while (toCheck.any { it.any { it.toString().toLongOrNull()!= null } } ){
            val newList = arrayListOf<String>()
            toCheck.forEach {
                if(it.toLongOrNull()!= null){
                    val values = inputsMap().get(it.toLong())!!
                    if(!values.contains("|")){
                        newList.add(values)
                    }
                }
            }
            toCheck = newList
            println(toCheck)
        }

    }

    fun getSolution2() {
    }
}

fun main() {
    println(Day19().getSolution1())
    println(Day19().getSolution2())
}