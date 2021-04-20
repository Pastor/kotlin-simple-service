package simple.kss.provider

import simple.kss.Constants.APPLICATION_JSON_UTF8
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

abstract class BaseResponseProvider {
    @field:Context
    private var headers: HttpHeaders? = null

    protected fun processResponse(builder: Response.ResponseBuilder) {
        var acceptableType = false
        if (headers == null) return
        val types = headers?.acceptableMediaTypes
        if (types != null && types.isNotEmpty()) {
            for (type in types) {
                var thinType = type
                if ("xml" == type.subtype || "json" == type.subtype) {
                    acceptableType = true
                    val parameters = type.parameters
                    if (!parameters.containsKey("charset")) {
                        thinType = type.withCharset("utf-8")
                    }
                    builder.type(thinType)
                    break
                }
            }
        }
        if (!acceptableType) {
            builder.type(APPLICATION_JSON_UTF8)
        }
    }
}