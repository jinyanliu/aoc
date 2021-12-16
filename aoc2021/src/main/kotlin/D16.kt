import utils.IoHelper
import java.lang.IllegalStateException

enum class Operation{
    SUM, PRODUCT, MINIMUM, MAXIMUM, GREATERTHAN, LESSTHAN, EQUALTO
}

object D16 {
     val inputs = IoHelper.getRawContent("d16.in")

    private val res = IoHelper.getLines("d16res.in")

    val list = mutableListOf<Long>()

     val resMap = getRes()
    private val reversedResMap = getReversedResMap()

    private fun getRes(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        res.forEach {
            val sections = it.split(" = ")
            map[sections[0]] = sections[1]
        }
        return map
    }

    private fun getReversedResMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        res.forEach {
            val sections = it.split(" = ")
            map[sections[1].toInt().toString()] = sections[0]
        }
        return map
    }

    fun solvePackage(remaining: String): Pair<String, Long> {
        println(remaining)
        var toBit = remaining

        val version = toBit.substring(0..2).toInt(2)
        toBit = toBit.drop(3)
        println("version=$version")
        list.add(version.toLong())

        val type = toBit.substring(0..2).toInt(2)
        toBit = toBit.drop(3)
        println("type=$type")

        if (type == 4) {
            return getLiteralValue(toBit)
        } else {
            return when(type){
                0 -> getOperatorValue(toBit, Operation.SUM)
                1 -> getOperatorValue(toBit, Operation.PRODUCT)
                2-> getOperatorValue(toBit,Operation.MINIMUM)
                3-> getOperatorValue(toBit,Operation.MAXIMUM)
                5-> getOperatorValue(toBit,Operation.GREATERTHAN)
                6-> getOperatorValue(toBit, Operation.LESSTHAN)
                7-> getOperatorValue(toBit, Operation.EQUALTO)
                else -> throw(IllegalStateException(""))
            }
            //return getOperatorValue(toBit)

        }
    }

    private fun getOperatorValue(remaining: String, operation: Operation): Pair<String, Long> {
        var toBit = remaining
        val lengthTypeId = remaining.first()
        toBit = toBit.drop(1)
        return when (lengthTypeId) {
            '0' -> goTo15Flow(toBit,operation)
            '1' -> goTo11Flow(toBit,operation)
            else -> throw(IllegalStateException(""))
        }
    }

    private fun goTo11Flow(remaining: String, operation: Operation):Pair<String, Long>{
        var value = 0L
        println("goTo11Flow")
        var toBit = remaining
        val totalNumberOfSubs = remaining.substring(0..10).toInt(2)
        println("totalNumberOfSubs=$totalNumberOfSubs")
        toBit=toBit.drop(11)

        var step = 0
        repeat(totalNumberOfSubs){
            step+=1
           val result = solvePackage(toBit)
            if(step ==1){
                value = result.second
            }else {
                value = calculate(value,operation,result.second)
            }
            toBit= result.first

        }

        return toBit to value
    }

    private fun calculate(value:Long, operation: Operation, addition:Long):Long{
        return when(operation){
            Operation.SUM-> value + addition
            Operation.PRODUCT->value*addition
            Operation.MINIMUM-> listOf(value, addition).min()!!
            Operation.MAXIMUM-> listOf(value,addition).max()!!
            Operation.GREATERTHAN -> if(value>addition) 1 else 0
            Operation.LESSTHAN->if(value<addition) 1 else 0
            Operation.EQUALTO->if(value==addition) 1 else 0
        }
    }

    private fun goTo15Flow(remaining: String, operation: Operation):Pair<String, Long>{
        var value = 0L
        println("goTo15Flow")
        var toBit = remaining
        val totalLengthInBits = remaining.substring(0..14).toInt(2)
        println("totalLengthInBits=$totalLengthInBits")
        toBit=toBit.drop(15)
        val remainingLength = toBit.length

        var step = 0
        while (remainingLength - toBit.length!=totalLengthInBits){
            step+=1
            val result = solvePackage(toBit)


            if(step ==1){
                value = result.second
            }else {
                value = calculate(value,operation,result.second)
            }

            toBit= result.first
        }

       return toBit to value

    }

    private fun getLiteralValue(remaining: String): Pair<String, Long> {
        var toBit = remaining
        var newString = ""
        while (true) {
            val current5 = toBit.substring(0..4)
            toBit = toBit.drop(5)
            newString += current5.drop(1)
            if (current5.first() == '0') {
                break
            }
        }
        val literalValue= newString.toLong(2)
        println("literalValue=$literalValue")
        return toBit to literalValue
    }
}


fun main() {

    val solvePackage = D16.solvePackage( D16.inputs.map { D16.resMap[it.toString()] }.joinToString(""))

    //938
    println("One=${D16.list.sum()}")
    //1495959086337
    println("Tw0=${solvePackage.second}")
}