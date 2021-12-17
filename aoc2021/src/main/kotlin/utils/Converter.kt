package utils

import java.math.BigInteger

object Converter {
    fun hexToBinary(hex: String, length: Int): String {
        return BigInteger(hex, 16).toString(2).padStart(length, '0')
    }
}