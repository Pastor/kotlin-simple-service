package simple.kss.service

import org.slf4j.MDC
import org.springframework.stereotype.Service
import simple.kss.Constants.REQUEST_ID
import simple.kss.LoggingService
import simple.kss.error.MessageException
import simple.kss.error.StandardErrorCode
import java.util.*

@Service("loggingService")
class RequestLoggingService : LoggingService {
    override fun register(requestId: Optional<String>) {
        if (requestId.isEmpty) {
            MDC.put(REQUEST_ID, UUID.randomUUID().toString())
        } else {
            val id = requestId.get()
            if (isUUID(id)) {
                MDC.put(REQUEST_ID, id)
            } else {
                MDC.put(REQUEST_ID, UUID.randomUUID().toString())
                throw MessageException(StandardErrorCode.PARAMETER_ILLEGAL_FORMAT, REQUEST_ID)
            }
        }
    }

    override fun unregister() {
        MDC.clear()
    }

    companion object {
        private fun isUUID(text: String): Boolean {
            return try {
                UUID.fromString(text)
                true
            } catch (ex: Exception) {
                false
            }
        }
    }
}