package club.cybet.endpoints.http_handlers.sys;

import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.HashUtils;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.sys)
 * Created by: cybet
 * On: 08 Sep, 2018 9/8/18 2:15 AM
 **/
public class Ftp extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        String accessToken = HashUtils.base64Decode(getPathVar(exchange, "aT"));
        exchange.getRequestHeaders().put(Headers.AUTHORIZATION, accessToken);

        if(requestIsNotAuthentic(exchange)) return;

        String exposedPath = getPathVar(exchange, "eP");

        send(exchange, exposedPath);

        JvmManager.gc(accessToken, exposedPath, exchange);
    }
}
