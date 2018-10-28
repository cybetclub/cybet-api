package club.cybet.endpoints.http_handlers.sys;

import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.sys)
 * Created by: cybet
 * On: 11 Sep, 2018 9/11/18 6:41 PM
 **/
public class Cdn extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);
        send(exchange, "{\"cdn\": \""+Constants.getPublicAssetsServer()+"\"}", StatusCodes.OK, Constants.applicationJson);
    }
}
