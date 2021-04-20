package simple.kss.provider

import simple.kss.Constants.APPLICATION_JSON_UTF8
import simple.kss.Constants.APPLICATION_XML_UTF8
import simple.kss.MessageService
import simple.kss.error.StandardErrorCode
import simple.kss.logger
import simple.kss.model.ErrorModel
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
@Produces(APPLICATION_JSON_UTF8, APPLICATION_XML_UTF8)
class ExceptionProvider constructor(private val messageService: MessageService) : BaseResponseProvider(),
    ExceptionMapper<Exception> {
    companion object {
        @JvmStatic
        private val log = logger()
    }
    override fun toResponse(exception: Exception): Response {
        log.error("ExceptionProvider catch", exception)
        val entity = ErrorModel(
            StandardErrorCode.UNKNOWN.generateCode(),
            if (log.isDebugEnabled) exception.localizedMessage else messageService.getMessage(
                StandardErrorCode.UNKNOWN.messageCode()
            ),
            listOf()
        )
        var status = Response.Status.INTERNAL_SERVER_ERROR.statusCode
        val builder = Response.status(status).entity(entity)
        processResponse(builder)
        return builder.build()
    }

}