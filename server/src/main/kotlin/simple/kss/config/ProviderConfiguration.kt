package simple.kss.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import org.apache.cxf.jaxrs.provider.JavaTimeTypesParamConverterProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import simple.kss.MessageService
import simple.kss.interceptor.LoggingInterceptor
import simple.kss.provider.*
import simple.kss.provider.BaseExceptionProvider
import javax.ws.rs.ext.ParamConverterProvider
import javax.ws.rs.ext.Provider

@Provider
@Configuration
open class ProviderConfiguration @Autowired constructor(private val interceptor: LoggingInterceptor) {

    @Bean
    open fun jsonProvider(mapper: ObjectMapper): JacksonJsonProvider {
        return JacksonJsonProvider(mapper)
    }

    @Bean
    @Primary
    open fun baseExceptionProvider(messageService: MessageService): BaseExceptionProvider {
        return BaseExceptionProvider(messageService)
    }

    @Bean
    @Primary
    open fun webApplicationExceptionProvider(messageService: MessageService): WebApplicationExceptionProvider {
        return WebApplicationExceptionProvider(messageService)
    }

    @Bean
    @Primary
    open fun exceptionProvider(messageService: MessageService): ExceptionProvider {
        return ExceptionProvider(messageService)
    }

    @Bean
    @Primary
    open fun loggingProvider(): LoggingProvider {
        return LoggingProvider(interceptor)
    }

    @Bean
    @Primary
    open fun localDateParamConverter(): LocalDateParamConverter {
        return LocalDateParamConverter()
    }

    @Bean
    open fun timeProvider(): ParamConverterProvider {
        return JavaTimeTypesParamConverterProvider()
    }

}