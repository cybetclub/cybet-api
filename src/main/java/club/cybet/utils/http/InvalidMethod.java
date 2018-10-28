package club.cybet.utils.http;

import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * sls-api (club.cybet.http_handlers)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 12:41 PM
 **/
public class InvalidMethod extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        printRequestInfo(exchange);

        send(exchange, new ExceptionRepresentation(
                "Method Not Allowed",
                exchange.getRequestURI(),
                "Method "+exchange.getRequestMethod()+" not allowed",
                StatusCodes.METHOD_NOT_ALLOWED,
                exchange.getRequestMethod()
        ), StatusCodes.METHOD_NOT_ALLOWED);
    }
}
