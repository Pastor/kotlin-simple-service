package simple.kss

import java.util.*

interface LoggingService {
    fun register(requestId: Optional<String>)
    fun unregister()
}