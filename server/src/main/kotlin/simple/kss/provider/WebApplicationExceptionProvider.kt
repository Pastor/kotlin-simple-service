package simple.kss.provider

import simple.kss.Constants.APPLICATION_JSON_UTF8
import simple.kss.Constants.APPLICATION_XML_UTF8
import simple.kss.MessageService
import simple.kss.error.StandardErrorCode
import simple.kss.model.ErrorModel
import java.time.format.DateTimeParseException
import javax.ws.rs.ClientErrorException
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
@Produces(APPLICATION_JSON_UTF8, APPLICATION_XML_UTF8)
class WebApplicationExceptionProvider(private val messageService: MessageService) : BaseResponseProvider(),
    ExceptionMapper<WebApplicationException> {
    override fun toResponse(exception: WebApplicationException): Response {
        val entity = ErrorModel(StandardErrorCode.UNKNOWN.generateCode(), exception.localizedMessage, listOf())
        var status = Response.Status.INTERNAL_SERVER_ERROR
        if (exception is ClientErrorException) {
            status = Response.Status.BAD_REQUEST
        }
        val cause = exception.cause
        if (cause != null) {
            if (cause is NumberFormatException || cause is DateTimeParseException) {
                status = setMessage(entity, cause, status)
            } else if (cause is IllegalArgumentException && cause.cause != null) {
                status = setMessage(entity, cause.cause, status)
            } else {
                entity.reasons = listOf(cause.localizedMessage)
            }
        }
        val builder = Response.status(status).entity(entity)
        processResponse(builder)
        return builder.build()
    }

    private fun setMessage(entity: ErrorModel, cause: Throwable?, status: Response.Status): Response.Status {
        var thinStatus = status
        if (cause is NumberFormatException || cause is DateTimeParseException) {
            entity.code = StandardErrorCode.PARAMETER_ILLEGAL_FORMAT.generateCode()
            entity.message = messageService.getMessage(StandardErrorCode.PARAMETER_ILLEGAL_FORMAT.messageCode(), "")
            thinStatus = Response.Status.fromStatusCode(StandardErrorCode.PARAMETER_ILLEGAL_FORMAT.httpCode())
        }
        return thinStatus
    }

}