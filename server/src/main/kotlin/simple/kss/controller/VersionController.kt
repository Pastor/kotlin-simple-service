package simple.kss.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import simple.kss.model.VersionModel
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Api(tags = ["Сервисные функции"])
@Path("/api/version")
@RestController
class VersionController @Autowired constructor(private val version: VersionModel) {
    @CrossOrigin(methods = [RequestMethod.GET])
    @ApiOperation(value = "Информация о версии сервиса", produces = "application/json", consumes = "application/json")
    @ApiResponses(
        ApiResponse(
            code = 200,
            message = "Запрос выполнен успешно, тело ответа содержит запрошенный ресурс или массив ресурсов"
        ),
        ApiResponse(
            code = 500,
            message = "Вызов метода завершился с ошибкой: произошла внутренняя ошибка на стороне сервера. Информация об ошибке включена в тело ответа в формате Error"
        )
    )
    @GET
    @Produces("application/json")
    fun version(): VersionModel {
        return version
    }

}