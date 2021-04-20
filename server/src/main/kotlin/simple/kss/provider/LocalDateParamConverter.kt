package simple.kss.provider

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.ws.rs.ext.ParamConverter
import javax.ws.rs.ext.Provider

@Provider
class LocalDateParamConverter : ParamConverter<LocalDate?> {
    override fun fromString(value: String): LocalDate? {
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun toString(value: LocalDate?): String {
        return value?.format(DateTimeFormatter.ISO_LOCAL_DATE)!!
    }
}