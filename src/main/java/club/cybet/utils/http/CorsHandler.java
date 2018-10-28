package club.cybet.utils.http;

import club.cybet.utils.Constants;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

/**
 * cybet-api (club.cybet.utils.http)
 * Created by: cybet
 * On: 26 Oct, 2018 10/26/18 11:14 PM
 **/
public class CorsHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"), "POST, GET, OPTIONS, PUT, PATCH, DELETE");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Headers"), "Content-Type, Accept, Authorization");
        exchange.setStatusCode(200);
    }
}
