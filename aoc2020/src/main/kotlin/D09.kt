import utils.IoHelper

class Day09 {
    private fun getInputs() = IoHelper.getLongs("d09.in")

    //177777905
    fun getSolution1() :Long{
        val mapIntegerList = getInputs().mapIndexed { index, integer -> index to integer }.toMap()
        for (integ in mapIntegerList) {
            if (integ.key > 25) {
                val resultList = arrayListOf<Long>()

                val indexKey = integ.key
                val indexRange = (indexKey - 25..indexKey - 1).toList()
                val list1 = indexRange.map { mapIntegerList.get(it) }
                val list2 = indexRange.map { mapIntegerList.get(it) }.toMutableList()
                list1.forEach { i ->
                    list2.forEach { j ->
                        if ((i!! + j!!) != (i + i)) {
                            resultList.add(i + j)
                        }
                    }
                }

                if (!resultList.contains(integ.value)) {
                    return (integ.value)
                }
            }

        }
        return 0
    }

    fun getSolution2():Long {
        val mapIntegerList = getInputs().mapIndexed { index, integer -> index to integer }.toMap()
        for(integ in mapIntegerList){
            val currentIntegKey = integ.key
            var currentResult:Long = integ.value
            var currentStep= 1
            while(currentResult<177777905L){
                val currentEndKey = currentIntegKey+currentStep

                    currentResult += mapIntegerList[currentEndKey]!!
                    currentStep +=1
                    if(currentResult == 177777905L){
                        val a = (currentIntegKey..currentEndKey).map { mapIntegerList.get(it)!! }.max()!!
                        val b = (currentIntegKey..currentEndKey).map { mapIntegerList.get(it)!! }.min()!!
                        return a+b
                }
            }
        }
        return 0
    }
}

fun main() {
    println(Day09().getSolution1())
    println(Day09().getSolution2())
}