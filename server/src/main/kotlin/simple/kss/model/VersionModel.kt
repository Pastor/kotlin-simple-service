package simple.kss.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("Версия")
data class VersionModel(
    @field:ApiModelProperty("Ветка сборки") @field:JsonProperty("branch")
    val buildBranch: String,
    @field:ApiModelProperty("Коммит сборки") @field:JsonProperty("commit")
    val buildCommit: String,
    @field:ApiModelProperty("Время сборки") @field:JsonProperty("timestamp")
    val buildTimestamp: String,
    @field:ApiModelProperty("Версия сервиса") @field:JsonProperty("version")
    val version: String,
    @field:ApiModelProperty("Актуальная версия API") @field:JsonProperty("api.actual")
    val apiActual: String,
    @field:ApiModelProperty("Начиная с какой версии API считается устаревшей") @field:JsonProperty("api.deprecated")
    val apiDeprecated: String
)