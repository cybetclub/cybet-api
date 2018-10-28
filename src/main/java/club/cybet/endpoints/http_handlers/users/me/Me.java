package club.cybet.endpoints.http_handlers.users.me;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import static club.cybet.utils.Constants.REQ_ID;

/**
 * cybet-api (club.cybet.http_handlers.users)
 * Created by: cybet
 * On: 16 Aug, 2018 8/16/18 8:55 PM
 **/
public class Me extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        String id = exchange.getQueryParameters().get(REQ_ID).getFirst();

        UserAccount user = (UserAccount) Repository
                .findByUniqueField(UserAccount.class, "id", id);

        send(exchange, user, StatusCodes.OK);

        JvmManager.gc(id, exchange, user);
    }
}
