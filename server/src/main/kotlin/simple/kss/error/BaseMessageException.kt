package simple.kss.error

import simple.kss.MessageService
import simple.kss.model.ErrorModel
import java.io.Serializable

abstract class BaseMessageException protected constructor(
    private val errorCode: ErrorCode,
    cause: Throwable?,
    private vararg val args: Serializable
) : RuntimeException(cause), Serializable {

    protected constructor(errorCode: ErrorCode, vararg args: Serializable) : this(errorCode, null, *args)

    private fun toMessage(messageService: MessageService): String {
        return messageService.getMessage(errorCode.messageCode(), *args)
    }

    fun toModel(service: MessageService): ErrorModel {
        return ErrorModel(errorCode.generateCode(), toMessage(service), listOf())
    }

    fun toModel(service: MessageService, messages: List<String>): ErrorModel {
        return ErrorModel(errorCode.generateCode(), toMessage(service), messages)
    }

    fun code(): String {
        return errorCode.generateCode()
    }

    fun httCode(): Int {
        return errorCode.httpCode()
    }

}