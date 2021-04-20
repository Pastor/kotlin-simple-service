package simple.kss.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import simple.kss.logger

@Configuration
open class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    companion object {
        @JvmStatic
        private val log = logger()
    }

    @Value("\${auth.enable:false}")
    private var authEnable = false

    @Value("\${auth.role:ADMIN}")
    private var roleName: String? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        if (authEnable) {
            log.info("Authorization ENABLED.")
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/status").permitAll()
                .antMatchers("/**/start").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/api-docs").permitAll()
                .antMatchers("/csrf").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().hasRole(roleName)
        } else {
            log.warn("Authorization DISABLED!")
            http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
        }
    }
}