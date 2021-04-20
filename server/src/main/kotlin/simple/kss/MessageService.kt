package simple.kss

import java.io.Serializable

interface MessageService {
    fun getMessage(code: String, vararg args: Serializable): String
}