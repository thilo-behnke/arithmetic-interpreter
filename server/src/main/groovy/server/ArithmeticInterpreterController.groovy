package server

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.reactivex.Single

import javax.inject.Inject

@Controller("/api/interpreter")
class ArithmeticInterpreterController {

    @Inject
    ArithmeticInterpreterService arithmeticInterpreterService

    @Post(value = "/evaluate", consumes = MediaType.TEXT_PLAIN)
    HttpResponse interpretExpression(@Body String expression) {
        try {
            def result = arithmeticInterpreterService.evaluateExpression(expression)
            return HttpResponse.ok(result)
        } catch (Exception ex) {
            return HttpResponse.badRequest(ex.message)
        }
    }

    @Get("/command/{commandName}")
    Single<Object> executeCommand(String commandName) {
        def command = arithmeticInterpreterService.executeCommand(commandName)
        return Single.just(command.getValue())
    }
}
