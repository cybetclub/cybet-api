package club.cybet.endpoints.http_handlers.users.me;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.Passwords;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api
 * Created by: cybet
 * On: 04 Sep, 2018 9/4/18 9:55 PM
 **/
public class UpdateMe extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        String id = String.valueOf(getAuthenticatedUserId(exchange));

        UserAccount staleUserAccount = (UserAccount) Repository
                .findById(UserAccount.class, id);

        UserAccount userAccount = (UserAccount) getBodyObject(exchange, UserAccount.class);

        if(userAccount == null){
            send(exchange, new ExceptionRepresentation(
                    exchange.getQueryParameters().get(Constants.MARSHALL_ERROR).getFirst(),
                    exchange.getRequestURI(),
                    "Error: Unable to understand payload.",
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
            return;
        }

        assert staleUserAccount != null;

        // Check if update is a password change
        if(userAccount.getUserPassword() != null){
            // Hash password
            userAccount.setUserPassword(
                    Passwords.getHash(userAccount.getUserPassword())
            );
        }

        DbTransactionWrapper wrapper = Repository.update(staleUserAccount, userAccount);

        if(wrapper.hasErrors()){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to update user account.",
                    exchange.getRequestURI(),
                    wrapper.getErrors(),
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
            return;
        }

        send(exchange, wrapper.getData(), StatusCodes.OK);

        JvmManager.gc(userAccount, staleUserAccount, wrapper, id, exchange);
    }

}
