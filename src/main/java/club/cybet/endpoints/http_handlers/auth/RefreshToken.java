package club.cybet.endpoints.http_handlers.auth;

import club.cybet.domain.db.enums.UserTypes;
import club.cybet.repository.AccessTokenRepository;
import club.cybet.utils.http.ScedarHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * sls-api (club.cybet.http_handlers.auth)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 12:43 PM
 **/
public class RefreshToken extends ScedarHttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        send(exchange, AccessTokenRepository.refresh(exchange.getRequestHeaders()
                .get("Authorization").getFirst().replace(
                        "Bearer ", "")), StatusCodes.OK);
    }
}
