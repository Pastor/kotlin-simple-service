package simple.kss.config

import org.apache.cxf.jaxrs.swagger.Swagger2Feature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import simple.kss.logger
import simple.kss.model.VersionModel
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Configuration
@PropertySource(value = ["classpath:swagger.properties"], encoding = "UTF-8")
open class SwaggerConfiguration @Autowired constructor(private val version: @NotNull VersionModel) {
    companion object {
        @JvmStatic
        private val log = logger()
    }

    @Value("\${swagger.base.host:127.0.0.1:8070}")
    private lateinit var baseHost: String

    @Value("\${cxf.path:/}")
    private lateinit var basePath: String

    @Value("\${contact.name}")
    private lateinit var contactName: @NotEmpty String

    @Value("\${contact.url}")
    private lateinit var contactUrl: @NotEmpty String

    @Value("\${contact.email}")
    private lateinit var contactEmail: @NotEmpty String

    @Value("\${product.title}")
    private lateinit var productTitle: String

    @Value("\${product.description}")
    private lateinit var productDescription: String

    @Value("\${server.port}")
    private lateinit var serverPort: String

    @Bean
    open fun swaggerFeature(): Swagger2Feature {
        val swagger = Swagger2Feature()
        swagger.version = version.version
        swagger.basePath = basePath
        swagger.isPrettyPrint = true
        swagger.schemes = arrayOf("http", "https")
        swagger.contact = contactEmail
        swagger.description = productDescription
        swagger.title = productTitle
        swagger.isSupportSwaggerUi = true
        if (baseHost.isNotEmpty()) swagger.host = baseHost
        log.warn("http://{}{}api-docs?url={}swagger.json", baseHost, basePath, basePath)
        return swagger
    }

}