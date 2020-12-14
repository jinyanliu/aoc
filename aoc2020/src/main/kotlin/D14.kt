import utils.IoHelper

class Day14 {
    private val inputs = IoHelper.getLines("d14.in")

    private var mapOfCurrentMask = mutableMapOf<Int, Char>()
    private var mapOfStorage = mutableMapOf<Long, Long>()

    fun getSolution1() {
        inputs.forEach{
            if(it.contains("mask")){
                val mapOfNewMask = mutableMapOf<Int, Char>()
                val mask = it.split("=").get(1).trim()
                println("mask="+mask)
                mask.withIndex().forEach {
                    if(it.value.toString()=="0"||it.value.toString()=="1"){
                        mapOfNewMask[it.index] = it.value
                    }
                }
                mapOfCurrentMask=mapOfNewMask
            }else {
                val storePlace = it.split("[").get(1).split("]").get(0).trim().toLong()
                println("storePlace"+storePlace)




                val decimalNumber = it.split("=").get(1).trim().toLong()
                println("decimalNumber="+decimalNumber)
                var bitNumber = decimalNumber.toString(2)
                while(bitNumber.length!=36){
                    bitNumber = "0$bitNumber"
                }
                println("bitNumber"+bitNumber)
                val chars = bitNumber.toCharArray()
                mapOfCurrentMask.forEach {
                    chars[it.key] = it.value
                }
                println("Stringcha"+String(chars))
                println(String(chars).toLong(2))
                println()


                mapOfStorage[storePlace] =String(chars).toLong(2)
            }
        }

        println("sum="+mapOfStorage.values.sum())
    }

    fun getSolution2() {
    }
}

fun main() {
    //17765746710228
    println(Day14().getSolution1())
    println(Day14().getSolution2())
}