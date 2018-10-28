package club.cybet.endpoints.http_handlers.users;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.protocols.UserAccountProtocols;
import club.cybet.repository.Repository;
import club.cybet.repository.UserAccountRepository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.Deque;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 11:57 PM
 **/
public class ForgotPassword extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        String email = getQueryParam(exchange, "email");

        if(email == null){
            send(exchange, new ExceptionRepresentation(
                    "Kindly provide GET parameter 'email'",
                    exchange.getRequestURI(),
                    "Bad request.",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        UserAccount userAccount = UserAccountRepository.getByUsernameFields(email);

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

        UserAccountProtocols protocols = new UserAccountProtocols(userAccount);
        protocols.resetPassword();

        send(exchange, "{\"detail\": \"Password reset email has been sent\"}",
                StatusCodes.OK, Constants.applicationJson);
    }
}
