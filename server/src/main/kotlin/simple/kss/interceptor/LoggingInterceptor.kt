package simple.kss.interceptor

import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import simple.kss.Constants.REQUEST_ID
import simple.kss.error.MessageException
import simple.kss.error.StandardErrorCode
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoggingInterceptor : HandlerInterceptor {
    fun register(requestId: String?) {
        if (requestId == null) {
            MDC.put(REQUEST_ID, UUID.randomUUID().toString())
        } else {
            if (isUUID(requestId)) {
                MDC.put(REQUEST_ID, requestId)
            } else {
                MDC.put(REQUEST_ID, UUID.randomUUID().toString())
                throw MessageException(StandardErrorCode.PARAMETER_ILLEGAL_FORMAT, REQUEST_ID)
            }
        }
    }

    fun unregister() {
        MDC.clear()
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestId: String = request.getParameter(REQUEST_ID)
        register(requestId)
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        exception: Exception
    ) {
        unregister()
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