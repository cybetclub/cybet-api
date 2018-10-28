package club.cybet.endpoints.http_handlers.users;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.db.orm.UserAccount;
import club.cybet.protocols.UserAccountProtocols;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.Passwords;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 5:24 PM
 **/
public class CreateUser extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

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

        if(userAccount.getUserPassword() == null){
            send(exchange, new ExceptionRepresentation(
                    "Error: Field userPassword cannot be null.",
                    exchange.getRequestURI(),
                    "Bad request",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        //Check if user type is ROOT
        if(userAccount.getUserAccountTypeId() != null && userAccount.getUserAccountTypeId().equals(1)){
            send(exchange, new ExceptionRepresentation(
                    "Error: User accounts with account type 'ROOT' cannot be created via this channel",
                    exchange.getRequestURI(),
                    "Request forbidden",
                    StatusCodes.FORBIDDEN,
                    exchange.getRequestMethod()
            ), StatusCodes.FORBIDDEN);
            return;
        }

        // Hash password
        userAccount.setUserPassword(
                Passwords.getHash(userAccount.getUserPassword())
        );

        DbTransactionWrapper wrapper =
                Repository.save(UserAccount.class, userAccount);

        if(wrapper.hasErrors()){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to create user account.",
                    exchange.getRequestURI(),
                    wrapper.getErrors(),
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
            return;
        }else{
            UserAccountProtocols protocols =
                    new UserAccountProtocols((UserAccount) wrapper.getData());
            protocols.registration();
        }

        send(exchange, wrapper.getData(), StatusCodes.OK);

        JvmManager.gc(userAccount, wrapper, exchange);

    }
}
