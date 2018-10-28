package club.cybet.endpoints.http_handlers.auth;

import club.cybet.repository.AccessTokenRepository;
import club.cybet.utils.http.ScedarHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;

/**
 * sls-api (club.cybet.http_handlers.auth)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 12:44 PM
 **/
public class RevokeToken extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        printRequestInfo(exchange);

        HeaderValues authorizationHeader = exchange.getRequestHeaders()
                .get("Authorization");

        if(authorizationHeader != null){
            AccessTokenRepository.revoke(authorizationHeader
                    .getFirst().replace("Bearer ", ""));
        }
    }
}
