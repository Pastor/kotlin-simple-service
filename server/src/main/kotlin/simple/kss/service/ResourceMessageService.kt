package simple.kss.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import simple.kss.Constants.CURRENT_VERSION
import simple.kss.MessageService
import java.io.Serializable
import java.util.*

/**
 * Сервис работы с сообщениями для отображения пользователю или записи в лог
 */
@Service("resourceManager$CURRENT_VERSION")
class ResourceMessageService @Autowired constructor(
    private val defaultLocale: Locale,
    private val messageSource: MessageSource
) :
    MessageService {

    /**
     * Получение текст сообщения
     *
     * @param code код сообщения
     * @param args список аргументов
     * @return сформированный текст
     */
    override fun getMessage(code: String, vararg args: Serializable): String {
        return messageSource.getMessage(code, args, defaultLocale)
    }

}