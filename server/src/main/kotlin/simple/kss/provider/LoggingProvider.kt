package simple.kss.provider

import org.springframework.beans.factory.annotation.Autowired
import simple.kss.interceptor.LoggingInterceptor
import simple.kss.logger
import java.io.IOException
import java.util.regex.Pattern
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class LoggingProvider @Autowired constructor(private val interceptor: LoggingInterceptor) : ContainerRequestFilter,
    ContainerResponseFilter {

    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext) {
        val matcher = ACCEPT_PATTERN.matcher(requestContext.uriInfo.path)
        if (!matcher.matches()) {
            return
        }
        val list = requestContext.uriInfo.getQueryParameters(true)["request_id"]
        var requestId: String? = null
        if (list != null && list.isNotEmpty()) {
            requestId = list[0]
        }
        interceptor.register(requestId)
        log.info("START   : {} {}", requestContext.method, requestContext.uriInfo.requestUri)
    }

    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        val matcher = ACCEPT_PATTERN.matcher(requestContext.uriInfo.path)
        if (!matcher.matches()) {
            return
        }
        log.info("COMPLETE: {} {}", requestContext.method, requestContext.uriInfo.requestUri)
        interceptor.unregister()
    }

    companion object {
        @JvmStatic
        private val log = logger()
        private val ACCEPT_PATTERN = Pattern.compile("api/v1/.*")
    }

}