package club.cybet.endpoints.http_handlers.users;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.security.Passwords;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 11:56 PM
 **/
public class VerifyPassword extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        String id = getQueryParam(exchange, "id");
        String password = getQueryParam(exchange, "password");

        if(id == null){
            send(exchange, new ExceptionRepresentation(
                    "Kindly provide GET parameter 'id'",
                    exchange.getRequestURI(),
                    "Bad request.",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        if(password == null){
            send(exchange, new ExceptionRepresentation(
                    "Kindly provide GET parameter 'password'",
                    exchange.getRequestURI(),
                    "Bad request.",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        UserAccount userAccount = (UserAccount)
                Repository.findById(UserAccount.class, id);

        if(userAccount == null){
            send(exchange, new ExceptionRepresentation(
                    "User account not found",
                    exchange.getRequestURI(),
                    "User not found.",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        if(Passwords.check(password, userAccount.getUserPassword())){
            send(exchange, "{\"detail\": true }",
                    StatusCodes.OK, Constants.applicationJson);
        }else{
            send(exchange, "{\"detail\": false }",
                    StatusCodes.OK, Constants.applicationJson);
        }

    }
}
