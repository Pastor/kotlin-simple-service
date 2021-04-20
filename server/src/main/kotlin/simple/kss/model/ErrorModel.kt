package simple.kss.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Проект")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorModel(
    @field:JsonProperty("code") @field:ApiModelProperty("Код ошибки") var code: String,
    @field:JsonProperty("message") @field:ApiModelProperty("Сообщение об ошибке") var message: String,
    @field:ApiModelProperty("Возможные причины ошибки") @field:JsonProperty("reasons") var reasons: List<String>
) : TransactionalModel() {
}