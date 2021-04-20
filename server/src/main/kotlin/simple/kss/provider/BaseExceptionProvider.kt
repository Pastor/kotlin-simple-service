package simple.kss.provider

import simple.kss.Constants.APPLICATION_JSON_UTF8
import simple.kss.MessageService
import simple.kss.error.BaseMessageException
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
@Produces(APPLICATION_JSON_UTF8)
open class BaseExceptionProvider(private val messageService: MessageService) : BaseResponseProvider(),
    ExceptionMapper<BaseMessageException> {
    override fun toResponse(exception: BaseMessageException): Response {
        val builder: Response.ResponseBuilder = Response.status(exception.httCode())
            .entity(exception.toModel(messageService))
        processResponse(builder)
        return builder.build()
    }
}