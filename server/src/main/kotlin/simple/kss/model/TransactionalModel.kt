package simple.kss.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import org.slf4j.MDC
import simple.kss.Constants.REQUEST_ID
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_EMPTY)
abstract class TransactionalModel : Serializable {
    @ApiModelProperty("Идентификатор запроса")
    @JsonProperty("request_id")
    var requestId: String? = MDC.get(REQUEST_ID)

    companion object {
        fun <T : TransactionalModel?> clearing(value: T): T {
            value?.requestId = null
            return value
        }
    }
}