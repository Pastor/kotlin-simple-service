package simple.kss.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import simple.kss.interceptor.LoggingInterceptor
import simple.kss.model.VersionModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.annotation.PostConstruct

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@PropertySource(value = ["classpath:version.properties"], encoding = "UTF-8")
@ComponentScan("simple.kss.service", "simple.kss.controller")
open class BackendConfiguration(private val mapper: ObjectMapper) : WebMvcConfigurer {
    @Value("\${build_branch:unknown}")
    private lateinit var buildBranch: String

    @Value("\${build_commit:unknown}")
    private lateinit var buildCommit: String

    @Value("\${build_timestamp:unknown}")
    private lateinit var buildTimestamp: String

    @Value("\${project.version:unknown}")
    private lateinit var projectVersion: String

    @Value("\${api.version.actual:unknown}")
    private lateinit var apiVersionActual: String

    @Value("\${api.version.deprecated:unknown}")
    private lateinit var apiVersionDeprecated: String

    @Bean
    open fun createVersion(): VersionModel {
        var ts = buildTimestamp
        try {
            ts = LocalDateTime.ofInstant(Instant.ofEpochMilli(buildTimestamp.toLong()), ZoneId.systemDefault())
                .toString()
        } catch (ex: Exception) {
            //Empty
        }
        return VersionModel(buildBranch, buildCommit, ts, projectVersion, apiVersionActual, apiVersionDeprecated)
    }

    @Bean
    open fun messageSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.addBasenames("documentation")
        messageSource.setCacheSeconds(60)
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    open fun requestLoggingFilter(): CommonsRequestLoggingFilter {
        val loggingFilter = CommonsRequestLoggingFilter()
        loggingFilter.setIncludeClientInfo(true)
        loggingFilter.setIncludeQueryString(true)
        loggingFilter.setIncludePayload(true)
        loggingFilter.setIncludeHeaders(true)
        return loggingFilter
    }

    @Bean
    open fun defaultRuLocale(): Locale {
        return Locale("ru", "RU")
    }

    @PostConstruct
    @Autowired
    private fun completeMapper() {
        mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }

    @Bean
    open fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor())
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns("/api/status", "/api/version")
    }

}