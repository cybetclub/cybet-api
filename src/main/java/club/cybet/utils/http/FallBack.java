package club.cybet.utils.http;

import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * sls-api (club.cybet.http_handlers)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 12:52 PM
 **/
public class FallBack extends ScedarHttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);
        send(exchange, new ExceptionRepresentation(
                "URI Not Found",
                exchange.getRequestURI(),
                "URI "+exchange.getRequestURI()+" not found on server",
                StatusCodes.NOT_FOUND,
                exchange.getRequestMethod()
        ), StatusCodes.NOT_FOUND);
    }
}
