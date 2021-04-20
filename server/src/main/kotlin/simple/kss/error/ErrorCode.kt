package simple.kss.error

import java.io.Serializable

interface ErrorCode : Serializable {
    fun generateCode(): String
    fun messageCode(): String
    fun httpCode(): Int
}